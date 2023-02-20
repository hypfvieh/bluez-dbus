package com.github.hypfvieh.bluetooth;

import com.github.hypfvieh.DbusHelper;
import com.github.hypfvieh.bluetooth.wrapper.BluetoothAdapter;
import com.github.hypfvieh.bluetooth.wrapper.BluetoothDevice;
import org.bluez.Adapter1;
import org.bluez.Device1;
import org.bluez.exceptions.*;
import org.freedesktop.dbus.connections.impl.DBusConnection;
import org.freedesktop.dbus.connections.impl.DBusConnection.DBusBusType;
import org.freedesktop.dbus.connections.impl.DBusConnectionBuilder;
import org.freedesktop.dbus.exceptions.DBusException;
import org.freedesktop.dbus.handlers.AbstractPropertiesChangedHandler;
import org.freedesktop.dbus.handlers.AbstractSignalHandlerBase;
import org.freedesktop.dbus.messages.DBusSignal;
import org.freedesktop.dbus.types.Variant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.Map.Entry;

/**
 * The 'main' class to get access to all DBus/bluez related objects.
 *
 * @author hypfvieh
 *
 */
public class DeviceManager {

    private static DeviceManager INSTANCE;
    private DBusConnection dbusConnection;

    /** MacAddress of BT-adapter <-> adapter object */
    private final Map<String, BluetoothAdapter> bluetoothAdaptersByMac = new LinkedHashMap<>();
    /** BT-adapter name <-> adapter object */
    private final Map<String, BluetoothAdapter> bluetoothAdaptersByAdapterName = new LinkedHashMap<>();

    /** MacAddress of BT-adapter <-> List of connected bluetooth device objects */
    private final Map<String, List<BluetoothDevice>> bluetoothDeviceByAdapterMac = new LinkedHashMap<>();

    private String defaultAdapterMac;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Private constructor for singleton pattern.
     *
     * @param _connection
     */
    private DeviceManager(DBusConnection _connection) {
        dbusConnection = Objects.requireNonNull(_connection);
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
        INSTANCE = new DeviceManager(DBusConnectionBuilder.forType(_sessionConnection ? DBusBusType.SESSION : DBusBusType.SYSTEM).build());
        return INSTANCE;
    }

    /**
     * Close current connection.
     */
    public void closeConnection() {
        dbusConnection.disconnect();
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
        INSTANCE = new DeviceManager(DBusConnectionBuilder.forAddress(_address).build());
        return INSTANCE;
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
    public List<BluetoothAdapter> scanForBluetoothAdapters() {
        bluetoothAdaptersByAdapterName.clear();
        bluetoothAdaptersByMac.clear();

        Set<String> scanObjectManager = DbusHelper.findNodes(dbusConnection, "/org/bluez");
        for (String hci : scanObjectManager) {
            Adapter1 adapter = DbusHelper.getRemoteObject(dbusConnection, "/org/bluez/" + hci, Adapter1.class);
            if (adapter != null) {
                BluetoothAdapter bt2 = new BluetoothAdapter(adapter, "/org/bluez/" + hci, dbusConnection);
                bluetoothAdaptersByMac.put(bt2.getAddress(), bt2);
                bluetoothAdaptersByAdapterName.put(hci, bt2);
            }
        }

        ArrayList<BluetoothAdapter> adapterList = new ArrayList<>(bluetoothAdaptersByAdapterName.values());

        if (defaultAdapterMac == null && !bluetoothAdaptersByMac.isEmpty()) {
            defaultAdapterMac = new ArrayList<>(bluetoothAdaptersByMac.keySet()).get(0);
        }

        return adapterList;
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

            findBtDevicesByIntrospection(adapter);
        }

        List<BluetoothDevice> devicelist = bluetoothDeviceByAdapterMac.get(adapter.getAddress());
        if (devicelist != null) {
            return new ArrayList<>(devicelist);
        }
        return new ArrayList<>();
    }

    /**
     * Gets all devices found by the given adapter and published by bluez using DBus Introspection API.
     * @param adapter bluetooth adapter
     */
    public void findBtDevicesByIntrospection(BluetoothAdapter adapter) {
        Set<String> scanObjectManager = DbusHelper.findNodes(dbusConnection, adapter.getDbusPath());

        String adapterMac = adapter.getAddress();
        // remove all devices from previous calls so unavailable devices will be removed
        // and only devices found in the current introspection result will be used
        if (bluetoothDeviceByAdapterMac.containsKey(adapterMac)) {
            bluetoothDeviceByAdapterMac.get(adapterMac).clear();
        }

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

    /**
     * Setup bluetooth scan/discovery filter.
     *
     * @param _filter filter to apply
     * @throws BluezInvalidArgumentsException when argument is invalid
     * @throws BluezNotReadyException when bluez not ready
     * @throws BluezNotSupportedException when operation not supported
     * @throws BluezFailedException on failure
     */
    public void setScanFilter(Map<DiscoveryFilter, Object> _filter) throws BluezInvalidArgumentsException, BluezNotReadyException, BluezNotSupportedException, BluezFailedException {
        Map<String, Variant<?>> filters = new LinkedHashMap<>();
        for (Entry<DiscoveryFilter, Object> entry : _filter.entrySet()) {
            if (!entry.getKey().getValueClass().isInstance(entry.getValue())) {
                throw new BluezInvalidArgumentsException("Filter value not of required type " + entry.getKey().getValueClass());
            }
            if (entry.getValue() instanceof Enum<?>) {
                filters.put(entry.getKey().name(), new Variant<>(entry.getValue().toString()));
            } else {
                filters.put(entry.getKey().name(), new Variant<>(entry.getValue()));
            }

        }



        getAdapter().setDiscoveryFilter(filters);
    }

    /**
     * Get the current adapter in use.
     * @return the adapter currently in use, maybe null
     */
    public BluetoothAdapter getAdapter() {
        if (defaultAdapterMac != null && bluetoothAdaptersByMac.containsKey(defaultAdapterMac)) {
            return bluetoothAdaptersByMac.get(defaultAdapterMac);
        } else {
            return scanForBluetoothAdapters().get(0);
        }
    }

    /**
     * Find an adapter by the given identifier (either MAC or device name).
     * Will scan for devices if no default device is given and given ident is also null.
     * Will also scan for devices if the requested device could not be found in device map.
     *
     * @param _ident mac address or device name
     * @return device, maybe null if no device could be found with the given ident
     */
    public BluetoothAdapter getAdapter(String _ident) {
        if (_ident == null && defaultAdapterMac == null) {
            scanForBluetoothAdapters();
        }

        if (_ident == null) {
            _ident = defaultAdapterMac;
        }

        if (bluetoothAdaptersByMac.containsKey(_ident)) {
            return bluetoothAdaptersByMac.get(_ident);
        }
        if (bluetoothAdaptersByAdapterName.containsKey(_ident)) {
            return bluetoothAdaptersByAdapterName.get(_ident);
        }
        // adapter not found by any identification, search for new adapters
        List<BluetoothAdapter> scanForBluetoothAdapters = scanForBluetoothAdapters();
        if (!scanForBluetoothAdapters.isEmpty()) { // there are new candidates, try once more
            if (bluetoothAdaptersByMac.containsKey(_ident)) {
                return bluetoothAdaptersByMac.get(_ident);
            }
            if (bluetoothAdaptersByAdapterName.containsKey(_ident)) {
                return bluetoothAdaptersByAdapterName.get(_ident);
            }

        }
        // no luck, no adapters found which are matching the given identification
        return null;
    }

    /**
     * Returns all found bluetooth adapters.
     * Will query for adapters if {@link #scanForBluetoothAdapters()} was not called before.
     * @return list, maybe empty
     */
    public List<BluetoothAdapter> getAdapters() {
        if (bluetoothAdaptersByMac.isEmpty()) {
            scanForBluetoothAdapters();
        }
        return new ArrayList<>(bluetoothAdaptersByMac.values());
    }

    /**
     * Get all bluetooth devices connected to the defaultAdapter.
     * @return list - maybe empty
     */
    public List<BluetoothDevice> getDevices() {
        return getDevices(defaultAdapterMac);
    }

    /**
     * Get all bluetooth devices connected to the defaultAdapter.
     * @param _doNotScan true to disable new device recovery, just return all devices already known by bluez, false to scan before returning devices
     * @return list - maybe empty
     */
    public List<BluetoothDevice> getDevices(boolean _doNotScan) {
        return getDevices(defaultAdapterMac, _doNotScan);
    }

    /**
     * Get all bluetooth devices connected to the adapter with the given MAC address.
     * @param _adapterMac adapters MAC address
     * @return list - maybe empty
     */
    public List<BluetoothDevice> getDevices(String _adapterMac) {
        return getDevices(_adapterMac, false);
    }

    /**
     * Get all bluetooth devices connected to the adapter with the given MAC address.
     * @param _adapterMac adapters MAC address
     * @param _doNotScan true to disable new device recovery, just return all devices already known by bluez, false to scan before returning devices
     * @return list - maybe empty
     */
    public List<BluetoothDevice> getDevices(String _adapterMac, boolean _doNotScan) {
        if (_doNotScan) {
            findBtDevicesByIntrospection(getAdapter(_adapterMac));
        } else {
            if (bluetoothDeviceByAdapterMac.isEmpty()) {
                scanForBluetoothDevices(_adapterMac, 5000);
            }
        }

        return bluetoothDeviceByAdapterMac.getOrDefault(_adapterMac, new ArrayList<>());
    }

    /**
     * Setup the default bluetooth adapter to use by giving the adapters MAC address.
     *
     * @param _adapterMac MAC address of the bluetooth adapter
     * @throws BluezDoesNotExistException when item does not exist if there is no bluetooth adapter with the given MAC
     */
    public void setDefaultAdapter(String _adapterMac) throws BluezDoesNotExistException {
        if (bluetoothAdaptersByMac.isEmpty()) {
            scanForBluetoothAdapters();
        }
        if (bluetoothAdaptersByMac.containsKey(_adapterMac)) {
            defaultAdapterMac = _adapterMac;
        } else {
            throw new BluezDoesNotExistException("Could not find bluetooth adapter with MAC address: " + _adapterMac);
        }
    }

    /**
     * Setup the default bluetooth adapter to use by giving an adapter object.
     *
     * @param _adapter bluetooth adapter object
     * @throws BluezDoesNotExistException when item does not exist if there is no bluetooth adapter with the given MAC or adapter object was null
     */
    public void setDefaultAdapter(BluetoothAdapter _adapter) throws BluezDoesNotExistException {
        if (_adapter != null) {
            setDefaultAdapter(_adapter.getAddress());
        } else {
            throw new BluezDoesNotExistException("Null is not a valid bluetooth adapter");
        }
    }

    /**
     * Register a PropertiesChanged callback handler on the DBusConnection.
     *
     * @param _handler callback class instance
     * @throws DBusException on error
     */
    public void registerPropertyHandler(AbstractPropertiesChangedHandler _handler) throws DBusException {
        dbusConnection.addSigHandler(_handler.getImplementationClass(), _handler);
    }

    /**
     * Unregister a PropertiesChanged callback handler on the DBusConnection.
     *
     * @param _handler callback class instance
     * @throws DBusException on error
     */
    public void unRegisterPropertyHandler(AbstractPropertiesChangedHandler _handler) throws DBusException {
        dbusConnection.removeSigHandler(_handler.getImplementationClass(), _handler);
    }

    /**
     * Register a signal handler callback on the connection.
     * @param _handler callback class extending {@link AbstractSignalHandlerBase}
     * @throws DBusException on DBus error
     *
     * @param <T> a {@link DBusSignal} or a subclass of it
     */
    public <T extends DBusSignal> void registerSignalHandler(AbstractSignalHandlerBase<T> _handler) throws DBusException {
        dbusConnection.addSigHandler(_handler.getImplementationClass(), _handler);
    }

    /**
     * Unregister a signal handler callback on the connection.
     * @param _handler callback class extending {@link AbstractSignalHandlerBase}
     * @throws DBusException on DBus error
     *
     * @param <T> a {@link DBusSignal} or a subclass of it
     */
    public <T extends DBusSignal> void unRegisterSignalHandler(AbstractSignalHandlerBase<T> _handler) throws DBusException {
        dbusConnection.removeSigHandler(_handler.getImplementationClass(), _handler);
    }

    /**
     * Get the DBusConnection provided in constructor.
     * @return {@link DBusConnection}
     */
    public DBusConnection getDbusConnection() {
        return dbusConnection;
    }


}
