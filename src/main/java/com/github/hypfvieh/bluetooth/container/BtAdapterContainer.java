package com.github.hypfvieh.bluetooth.container;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.github.hypfvieh.bluetooth.wrapper.BluetoothAdapter;

/**
 * Class to manage bluetooth adapters and allow querying them by different identifiers.
 * 
 * @author hypfvieh
 * @since v0.1.0 - 2018-03-13
 */
public class BtAdapterContainer {

    /** MacAddress of BT-adapter <-> adapter object */
    private final Map<String, BluetoothAdapter> bluetoothAdaptersByMac = new LinkedHashMap<>();
    /** BT-adapter name <-> adapter object */
    private final Map<String, BluetoothAdapter> bluetoothAdaptersByAdapterName = new LinkedHashMap<>();
    /** BT-adapter dbuspath <-> adapter object */
    private final Map<String, BluetoothAdapter> bluetoothAdaptersByDbusPath = new LinkedHashMap<>();

    public BtAdapterContainer( ) {
        
    }
    
    /**
     * Add the given adapter to the internal map.
     * If the given adapter is already known, it will be ignored.
     * @param _adapter
     */
    public void addAdapter(BluetoothAdapter _adapter) {
        Objects.requireNonNull(_adapter);
        
        bluetoothAdaptersByMac.putIfAbsent(normalizeMac(_adapter.getAddress()), _adapter);
        bluetoothAdaptersByDbusPath.putIfAbsent(_adapter.getDbusPath(), _adapter);
        bluetoothAdaptersByAdapterName.putIfAbsent(_adapter.getDeviceName(), _adapter);
    }
    
    /**
     * Remove the given adapter.
     * @param _adapter
     */
    public void removeAdapter(BluetoothAdapter _adapter) {
        
        Objects.requireNonNull(_adapter);
        
        String mac = normalizeMac(_adapter.getAddress());
        String hci = _adapter.getDeviceName();
        String dbusPath = _adapter.getDbusPath();
        
        bluetoothAdaptersByAdapterName.remove(hci);
        bluetoothAdaptersByDbusPath.remove(dbusPath);
        bluetoothAdaptersByMac.remove(mac);
    }
    
    /**
     * Remove a bluetooth adapter by the given dbus path.
     * 
     * @param _dbusPath
     * @return true if adapter could be removed, false otherwise
     */
    public boolean removeAdapterByDbusPath(String _dbusPath) {
        if (bluetoothAdaptersByDbusPath.containsKey(_dbusPath)) {
            BluetoothAdapter removedAdapter = bluetoothAdaptersByDbusPath.remove(_dbusPath);
            bluetoothAdaptersByMac.remove(removedAdapter.getAddress());            
            bluetoothAdaptersByDbusPath.remove(removedAdapter.getDbusPath());
            return true;
        }
        return false;
    }
    
    /**
     * Remove an adapter by the given MAC address.
     * 
     * @param _macAddr
     * @return true if adapter could be removed, false otherwise
     */
    public boolean removeAdapterByMac(String _macAddr) {
        String normalizedMac = normalizeMac(_macAddr);
        if (bluetoothAdaptersByMac.containsKey(normalizedMac)) {
            BluetoothAdapter removedAdapter = bluetoothAdaptersByMac.remove(normalizedMac);
            bluetoothAdaptersByDbusPath.remove(removedAdapter.getDbusPath());
            bluetoothAdaptersByAdapterName.remove(removedAdapter.getDeviceName());
            return true;
        }
        return false;
    }
    
    /**
     * Remove an adapter by the given adapter device name (e.g. hci0).
     * 
     * @param _adapterName
     * @return true if adapter could be removed, false otherwise
     */
    public boolean removeAdapterByDeviceName(String _adapterName) {
        if (bluetoothAdaptersByAdapterName.containsKey(_adapterName)) {
            BluetoothAdapter removedAdapter = bluetoothAdaptersByAdapterName.remove(_adapterName);
            bluetoothAdaptersByDbusPath.remove(removedAdapter.getDbusPath());
            bluetoothAdaptersByMac.remove(removedAdapter.getAddress());
            return true;
        }
        return false;
    }
    
    /**
     * Get a bluetooth adapter by the given dbus path.
     * 
     * @param _dbusPath
     * @return null if adapter could not be found
     */
    public BluetoothAdapter getAdapterByDbusPath(String _dbusPath) {
        return bluetoothAdaptersByDbusPath.get(_dbusPath);        
    }
    
    /**
     * Gat a bluetooth adapter by the given MAC address.
     * 
     * @param _macAddr
     * @return null if adapter could not be found
     */
    public BluetoothAdapter getAdapterByMac(String _macAddr) {
        return bluetoothAdaptersByMac.get(normalizeMac(_macAddr));
    }
    
    /**
     * Get a bluetooth adapter by the given adapter device name (e.g. hci0).
     * 
     * @param _adapterName
     * @return null if adapter could not be found
     */
    public BluetoothAdapter getAdapterByDeviceName(String _adapterName) {
        return bluetoothAdaptersByAdapterName.get(_adapterName);
    }
    
    /**
     * Returns a list of all known bluetooth adapters.
     * @return
     */
    public List<BluetoothAdapter> getAllAdapters() {
        return new ArrayList<>(bluetoothAdaptersByMac.values());
    }
    
    /**
     * Returns true if any adapter is known, false otherwise.
     * @return
     */
    public boolean hasAdapters() {
        return bluetoothAdaptersByMac.isEmpty();
    }

    /**
     * Remove all known adapters.
     */
    public void clear() {
        bluetoothAdaptersByAdapterName.clear();
        bluetoothAdaptersByDbusPath.clear();
        bluetoothAdaptersByMac.clear();        
    }

    /**
     * Normalize MAC address by replacing _ and - with : and convert it to lower case.
     * @param _mac
     * @return null if input was null
     */
    private static String normalizeMac(String _mac) {
        if (_mac == null) {
            return null;
        }
        String normalized = _mac.replace("-", ":");
        normalized = normalized.replace("_", ":");
        return normalized.toLowerCase();
    }
}
