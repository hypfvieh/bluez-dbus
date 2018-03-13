package com.github.hypfvieh.bluetooth;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.bluez.Adapter1;
import org.bluez.Device1;
import org.bluez.exceptions.BluezDoesNotExistException;
import org.bluez.exceptions.BluezFailedException;
import org.bluez.exceptions.BluezInvalidArgumentsException;
import org.bluez.exceptions.BluezNotReadyException;
import org.bluez.exceptions.BluezNotSupportedException;
import org.freedesktop.dbus.connections.impl.DBusConnection;
import org.freedesktop.dbus.connections.impl.DBusConnection.DBusBusType;
import org.freedesktop.dbus.exceptions.DBusException;
import org.freedesktop.dbus.handlers.AbstractInterfacesAddedHandler;
import org.freedesktop.dbus.handlers.AbstractInterfacesRemovedHandler;
import org.freedesktop.dbus.handlers.AbstractSignalHandlerBase;
import org.freedesktop.dbus.interfaces.ObjectManager.InterfacesAdded;
import org.freedesktop.dbus.interfaces.ObjectManager.InterfacesRemoved;
import org.freedesktop.dbus.interfaces.Properties.PropertiesChanged;
import org.freedesktop.dbus.messages.DBusSignal;
import org.freedesktop.dbus.types.Variant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.hypfvieh.DbusHelper;
import com.github.hypfvieh.bluetooth.container.BtAdapterContainer;
import com.github.hypfvieh.bluetooth.container.CallbackSignalContainer;
import com.github.hypfvieh.bluetooth.wrapper.BluetoothAdapter;
import com.github.hypfvieh.bluetooth.wrapper.BluetoothDevice;
import com.github.hypfvieh.system.NativeLibraryLoader;

/**
 * The 'main' class to get access to all DBus/bluez related objects.
 *
 * @author hypfvieh
 *
 */
public class DeviceManager implements Closeable {

    private static DeviceManager INSTANCE;
    private DBusConnection dbusConnection;

    private final BtAdapterContainer btAdapters = new BtAdapterContainer();
    
    /** MacAddress of BT-adapter <-> List of connected bluetooth device objects */
    private final Map<String, List<BluetoothDevice>> bluetoothDeviceByAdapterMac = new LinkedHashMap<>();

    /**
     * MacAddress of BT-Adapter <-> Registerd callbacks (only internally created callbacks, not user defined callbacks)
     */
    private final Map<String, CallbackSignalContainer> internalCallbacks = new ConcurrentHashMap<>();
    
    private String defaultAdapterMac;

    private static boolean libraryLoaded = false;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private CallbackSignalContainer adapterSignals;
    
    static {
        // disable automatic loading of unix-socket library, we will load it if we need it
        NativeLibraryLoader.setEnabled(false);
    }

    /**
     * Load native library is necessary.
     */
    private static void loadLibrary() {
        if (!libraryLoaded) {
            NativeLibraryLoader.setEnabled(true);
            NativeLibraryLoader.loadLibrary(true, "libunix-java.so", "lib/");
            libraryLoaded = true;
        }
    }

    /**
     * Private constructor for singleton pattern.
     *
     * @param _connection
     */
    private DeviceManager(DBusConnection _connection) {
        dbusConnection = _connection;
        registerAdapterCallbacks();
    }

    /**
     * Create a new {@link DeviceManager} using the UnixDomainSockets and use either the global SYSTEM interface
     * or create a new interface just for this session (if _sessionConnection = true).
     *
     * @param _sessionConnection true to create user-session, false to use system session
     * @return {@link DeviceManager}
     *
     * @throws DBusException on error
     */
    public static DeviceManager createInstance(boolean _sessionConnection) throws DBusException {
        loadLibrary();
        INSTANCE = new DeviceManager(DBusConnection.getConnection(_sessionConnection ? DBusBusType.SESSION : DBusBusType.SYSTEM));
        return INSTANCE;
    }
   
    /**
     * Create a new {@link DeviceManager} instance using the given DBus address (e.g. tcp://127.0.0.1:13245)
     * @param _address address to connect to
     * @throws DBusException on error
     *
     * @return {@link DeviceManager}
     */
    public static DeviceManager createInstance(String _address) throws DBusException {
        if (_address == null) {
            throw new DBusException("Null is not a valid address");
        }
        if (_address.contains("unix://")) {
            loadLibrary();
        }
        INSTANCE = new DeviceManager(DBusConnection.getConnection(_address));
        return INSTANCE;
    }

    private void registerCallbacks(String _adapterMac) {
        if (internalCallbacks.containsKey(_adapterMac)) { // signals already registered
            return;
        }
        
        AbstractInterfacesAddedHandler deviceAdded = new AbstractInterfacesAddedHandler() {
            
            @Override
            public void handle(InterfacesAdded _s) {
                if (_s != null) {
                    Map<String, Map<String, Variant<?>>> interfaces = _s.getInterfaces();
                    interfaces.entrySet().stream().filter(e -> e.getKey().equals(Device1.class.getName()))
                        .forEach(e -> {
                            Variant<?> address = e.getValue().get("Address");
                            if (address != null && address.getValue() != null) {
                                logger.debug("New bluetooth device: {}", _s.getSignalSource().getPath());
                                Device1 device1;
                                try {
                                    device1 = dbusConnection.getRemoteObject("org.bluez", _s.getSignalSource().getPath(), Device1.class);
                                    bluetoothDeviceByAdapterMac.get(_adapterMac).add(new BluetoothDevice(device1, getAdapter(_adapterMac), _s.getSignalSource().getPath(), dbusConnection));
                                    logger.debug("New bluetooth device: {} added to internal list for adapter {}", _s.getSignalSource().getPath(), _adapterMac);
                                } catch (DBusException _ex) {
                                    logger.warn("Cannot create Device1 object for added bluez device {}", _s.getSignalSource().getPath(), _ex);
                                }

                            }
                        });
                }
            }
        };
        
        AbstractInterfacesRemovedHandler deviceRemoved = new AbstractInterfacesRemovedHandler() {
            @Override
            public void handle(InterfacesRemoved _s) {
                if (_s != null) {
                    if (_s.getInterfaces().contains(Device1.class.getName())) {     
                        logger.debug("Bluetooth device {} removed", _s.getSignalSource().getPath());
                        
                        List<BluetoothDevice> list = bluetoothDeviceByAdapterMac.get(_adapterMac);
                        Iterator<BluetoothDevice> iterator = list.iterator();
                        while (iterator.hasNext()) {
                            BluetoothDevice dev = iterator.next();
                            if (dev.getDbusPath().equals(_s.getSignalSource().getPath())) {
                                logger.debug("Bluetooth device {} removed from internal list", _s.getSignalSource().getPath());
                                iterator.remove();
                                break; // devices should only appear once
                            }
                        }
                    }
                }
            }
        };
        
        try {            
            dbusConnection.addSigHandler(deviceAdded.getImplementationClass(), deviceAdded);
            dbusConnection.addSigHandler(deviceRemoved.getImplementationClass(), deviceRemoved);
            internalCallbacks.put(_adapterMac, new CallbackSignalContainer(deviceAdded, deviceRemoved));
        } catch (DBusException _ex) {
            logger.error("Could not register signal handlers.", _ex);
        }
    }
    
    private void registerAdapterCallbacks() {
        AbstractInterfacesAddedHandler adapterAdded = new AbstractInterfacesAddedHandler() {
            
            @Override
            public void handle(InterfacesAdded _s) {
                if (_s == null) {
                    return;
                }
                
                Map<String, Map<String, Variant<?>>> interfaces = _s.getInterfaces();
                interfaces.entrySet().stream().filter(e -> e.getKey().equals(Adapter1.class.getName())).forEach(e -> {
                    Variant<?> address = e.getValue().get("Address");
                    if (address != null && address.getValue() != null) {
                        logger.debug("New bluetooth adapter: {}, MAC: {}", _s.getSignalSource().getPath(), address.getValue());
                        
                        Adapter1 adapter1 = DbusHelper.getRemoteObject(dbusConnection, _s.getSignalSource().getPath(), Adapter1.class);
                        if (adapter1 != null) {
                            BluetoothAdapter bluetoothAdapter = new BluetoothAdapter(adapter1, _s.getSignalSource().getPath(), dbusConnection);

                            btAdapters.addAdapter(bluetoothAdapter);
                            registerCallbacks(bluetoothAdapter.getAddress());
                            logger.debug("New bluetooth adapter: {}, MAC: {} successfully added to adapter list", _s.getSignalSource().getPath(), address.getValue());
                        }
                    }
                });                
            }
        };
        
        AbstractInterfacesRemovedHandler adapterRemoved = new AbstractInterfacesRemovedHandler() {
            
            @Override
            public void handle(InterfacesRemoved _s) {
                if (_s != null) {
                    if (_s.getInterfaces().contains(Adapter1.class.getName())) {     
                        logger.debug("Bluetooth adapter {} removed", _s.getSignalSource().getPath());
                        
                        if (btAdapters.removeAdapterByDbusPath(_s.getSignalSource().getPath())) {
                            logger.debug("Bluetooth adapter {} removed from internal list", _s.getSignalSource().getPath());
                        }
                    }
                }                
            }
        };
        
        try {
            dbusConnection.addSigHandler(adapterRemoved.getImplementationClass(), adapterRemoved);
            dbusConnection.addSigHandler(adapterAdded.getImplementationClass(), adapterAdded);
            adapterSignals = new CallbackSignalContainer(adapterAdded, adapterRemoved);
        } catch (DBusException _ex) {
            logger.error("Unable to register signal handlers.", _ex);
        }
    }
    

    private void unregisterCallbacks() {
        for (CallbackSignalContainer csc : internalCallbacks.values()) {
            try {
                dbusConnection.removeSigHandler(csc.getAddedHandler().getImplementationClass(), csc.getAddedHandler());
                dbusConnection.removeSigHandler(csc.getRemovedHandler().getImplementationClass(), csc.getRemovedHandler());
            } catch (DBusException _ex) {
                logger.error("Unable to remove signal handler.", _ex);
            }
        }
        
        internalCallbacks.clear();
        
        if (adapterSignals != null) {
            try {
                dbusConnection.removeSigHandler(adapterSignals.getAddedHandler().getImplementationClass(), adapterSignals.getAddedHandler());
                dbusConnection.removeSigHandler(adapterSignals.getRemovedHandler().getImplementationClass(), adapterSignals.getRemovedHandler());
                adapterSignals = null;
            } catch (DBusException _ex) {
                logger.error("Unable to remove signal handler.", _ex);
            }
        }
    }
    
    /**
     * Close connection, removes all cached device and adapter instances.
     */
    @Override
    public void close() throws IOException {
        unregisterCallbacks();
        
        bluetoothDeviceByAdapterMac.clear();
        btAdapters.clear();
        defaultAdapterMac = null;
        
        dbusConnection.disconnect();
        dbusConnection = null;
    }


    /**
     * Get the created instance.
     * @return {@link DeviceManager}, never null
     */
    public static DeviceManager getInstance() {
        if (INSTANCE == null) {
            throw new IllegalStateException("Instance not created yet. Please use " + DeviceManager.class.getSimpleName() + ".createInstance() first");
        }

        return INSTANCE;
    }

    /**
     * Search for all bluetooth adapters connected to this machine.
     * Will set the defaultAdapter to the first adapter found if no defaultAdapter was specified before.
     *
     * @return List of adapters, maybe empty, never null
     */
    public List<BluetoothAdapter> findBluetoothAdapters() {
        Set<String> scanObjectManager = DbusHelper.findNodes(dbusConnection, "/org/bluez");
        for (String hci : scanObjectManager) {
            Adapter1 adapter = DbusHelper.getRemoteObject(dbusConnection, "/org/bluez/" + hci, Adapter1.class);
            if (adapter != null) {
                BluetoothAdapter bt2 = new BluetoothAdapter(adapter, "/org/bluez/" + hci, dbusConnection);
                btAdapters.addAdapter(bt2);
                registerCallbacks(bt2.getAddress());
                
                bluetoothDeviceByAdapterMac.put(bt2.getAddress(), new ArrayList<>());                
            }
        }

        List<BluetoothAdapter> allAdapters = btAdapters.getAllAdapters();
        
        if (defaultAdapterMac == null && !allAdapters.isEmpty()) {
            defaultAdapterMac = allAdapters.get(0).getAddress();
        }

        return allAdapters;
    }

    /**
     * Scan for bluetooth devices using the default adapter.
     * @param _timeout timout to use for scanning
     * @return list of found {@link BluetoothDevice}
     */
    public List<BluetoothDevice> scanForBluetoothDevices(int _timeout) {
        return scanForBluetoothDevices(defaultAdapterMac, _timeout);
    }

    /**
     * Scan for Bluetooth devices for on the given adapter.
     * If adapter is null or could not be found, the default adapter is used.
     *
     * @param _adapter adapter to use (either MAC or Dbus-Devicename (e.g. hci0))
     * @param _timeoutMs timeout in milliseconds to scan for devices
     * @return list of found {@link BluetoothDevice}
     */
    public List<BluetoothDevice> scanForBluetoothDevices(String _adapter, int _timeoutMs) {
        BluetoothAdapter adapter = getAdapter(_adapter);
        if (adapter == null) {
            return new ArrayList<>();
        }

        if (adapter.startDiscovery()) {
            try {
                Thread.sleep(_timeoutMs);
            } catch (InterruptedException _ex) {
            }
            adapter.stopDiscovery();

            Set<String> scanObjectManager = DbusHelper.findNodes(dbusConnection, adapter.getDbusPath());

            String adapterMac = adapter.getAddress();

            for (String path : scanObjectManager) {
                String devicePath = "/org/bluez/" + adapter.getDeviceName() + "/" + path;
                Device1 device = DbusHelper.getRemoteObject(dbusConnection, devicePath, Device1.class);
                if (device != null) {
                    BluetoothDevice btDev = new BluetoothDevice(device, adapter, devicePath, dbusConnection);
                    logger.debug("Found bluetooth device {} on adapter {}", btDev.getAddress(), adapterMac);
                    if (bluetoothDeviceByAdapterMac.containsKey(adapterMac)) {
                        bluetoothDeviceByAdapterMac.get(adapterMac).add(btDev);
                    } else {
                        List<BluetoothDevice> list = new ArrayList<>();
                        list.add(btDev);
                        bluetoothDeviceByAdapterMac.put(adapterMac, list);
                    }
                }
            }
        }

        List<BluetoothDevice> devicelist = bluetoothDeviceByAdapterMac.get(_adapter);
        if (devicelist != null) {
            return new ArrayList<>(devicelist);
        }
        return new ArrayList<>();
    }

    /**
     * Setup bluetooth scan/discovery filter.
     *
     * @param _filter
     * @throws BluezInvalidArgumentsException
     * @throws BluezNotReadyException
     * @throws BluezNotSupportedException
     * @throws BluezFailedException
     */
    public void setScanFilter(Map<DiscoveryFilter, Variant<?>> _filter) throws BluezInvalidArgumentsException, BluezNotReadyException, BluezNotSupportedException, BluezFailedException {
        Map<String, Variant<?>> filters = new LinkedHashMap<>();
        for (Entry<DiscoveryFilter, Variant<?>> entry : _filter.entrySet()) {
            if (!entry.getKey().getValueClass().isInstance(entry.getValue())) {
                throw new BluezInvalidArgumentsException("Filter value not of required type " + entry.getKey().getValueClass());
            }
            filters.put(entry.getKey().name(), entry.getValue());

        }

        getAdapter().setDiscoveryFilter(filters);
    }

    /**
     * Get the current adapter in use.
     * @return the adapter currently in use, maybe null
     */
    public BluetoothAdapter getAdapter() {
        if (defaultAdapterMac != null && btAdapters.getAdapterByMac(defaultAdapterMac) != null) {
            return btAdapters.getAdapterByMac(defaultAdapterMac);
        } else {
            return findBluetoothAdapters().get(0);
        }
    }

    /**
     * Find an adapter by the given identifier (either MAC or device name).
     * Will scan for devices if no default device is given and given ident is null.
     *
     * @param _ident mac address or device name
     * @return device, maybe null if no device could be found with the given ident
     */
    private BluetoothAdapter getAdapter(String _ident) {
        if (_ident == null && defaultAdapterMac == null) {
            findBluetoothAdapters();
        }

        if (_ident == null) {
            _ident = defaultAdapterMac;
        }

        
        BluetoothAdapter adapter = btAdapters.getAdapterByMac(_ident);
        if (adapter == null) {
            adapter = btAdapters.getAdapterByDeviceName(_ident);
        }
        if (adapter == null) {
            adapter = btAdapters.getAdapterByDbusPath(_ident);
        }

        return adapter;
    }

    /**
     * Returns all found bluetooth adapters.
     * Will query for adapters if {@link #findBluetoothAdapters()} was not called before.
     * @return list, maybe empty
     */
    public List<BluetoothAdapter> getAdapters() {
        if (!btAdapters.hasAdapters()) {
            findBluetoothAdapters();
        }
        return btAdapters.getAllAdapters();
    }

    /**
     * Get all bluetooth devices connected to the defaultAdapter.
     * @return list - maybe empty
     */
    public List<BluetoothDevice> getDevices() {
        return getDevices(defaultAdapterMac);
    }

    /**
     * Get all bluetooth devices connected to the adapter with the given MAC address.
     * @param _adapterMac adapters MAC address
     * @return list - maybe empty
     */
    public List<BluetoothDevice> getDevices(String _adapterMac) {
        if (bluetoothDeviceByAdapterMac.isEmpty()) {
            scanForBluetoothDevices(_adapterMac, 5000);
        }
        List<BluetoothDevice> list = bluetoothDeviceByAdapterMac.get(_adapterMac);
        if (list == null) {
            return new ArrayList<>();
        }
        return list;
    }

    /**
     * Setup the default bluetooth adapter to use by giving the adapters MAC address.
     *
     * @param _adapterMac MAC address of the bluetooth adapter
     * @throws BluezDoesNotExistException if there is no bluetooth adapter with the given MAC
     */
    public void setDefaultAdapter(String _adapterIdent) throws BluezDoesNotExistException {
        if (!btAdapters.hasAdapters()) {
            findBluetoothAdapters();
        }
        BluetoothAdapter adapter = getAdapter(_adapterIdent);
        if (adapter != null) {
            defaultAdapterMac = adapter.getAddress();
        } else {
            throw new BluezDoesNotExistException("Could not find bluetooth adapter with identifier: " + _adapterIdent);
        }
    }

    /**
     * Setup the default bluetooth adapter to use by giving an adapter object.
     *
     * @param _adapter bluetooth adapter object
     * @throws BluezDoesNotExistException if there is no bluetooth adapter with the given MAC or adapter object was null
     */
    public void setDefaultAdapter(BluetoothAdapter _adapter) throws BluezDoesNotExistException {
        if (_adapter != null) {
            setDefaultAdapter(_adapter.getAddress());
        } else {
            throw new BluezDoesNotExistException("Null is not a valid bluetooth adapter");
        }
    }

    /**
     * Register a callback signal handler.
     * This can either a {@link InterfacesAdded}, {@link InterfacesRemoved} or {@link PropertiesChanged} signal handler.
     * 
     * @param _handler
     * @throws DBusException
     */
    public <T extends DBusSignal> void registerSignalHandler(AbstractSignalHandlerBase<T> _handler) throws DBusException {
        dbusConnection.addSigHandler(_handler.getImplementationClass(), _handler);
    }
    
    /**
     * Remove the given signal handler from the DBus session so signals are no longer received by that handler.
     * 
     * @param _handler
     * @throws DBusException
     */
    public <T extends DBusSignal> void unregisterSignalHandler(AbstractSignalHandlerBase<T> _handler) throws DBusException {
        dbusConnection.removeSigHandler(_handler.getImplementationClass(), _handler);
    }
    
}
