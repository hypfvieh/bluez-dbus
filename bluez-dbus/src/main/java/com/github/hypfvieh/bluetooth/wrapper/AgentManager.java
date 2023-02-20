package com.github.hypfvieh.bluetooth.wrapper;

import com.github.hypfvieh.DbusHelper;
import org.bluez.AgentManager1;
import org.bluez.exceptions.*;
import org.freedesktop.dbus.DBusPath;
import org.freedesktop.dbus.connections.impl.DBusConnection;
import org.freedesktop.dbus.interfaces.DBusInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AgentManager extends AbstractBluetoothObject {
    private static final Logger logger = LoggerFactory.getLogger(AgentManager.class);

    private final AgentManager1 rawAgentManager;

    public AgentManager(DBusConnection _dbusConnection) { // NOPMD names
        super(BluetoothDeviceType.AGENT_MANAGER, _dbusConnection, "/org/bluez");
        rawAgentManager = DbusHelper.getRemoteObject(_dbusConnection, getDbusPath(), AgentManager1.class);
    }

    @Override
    protected Class<? extends DBusInterface> getInterfaceClass() {
        return AgentManager1.class;
    }

    public boolean registerAgent(String path, String capability) {
        try {
            rawAgentManager.RegisterAgent(new DBusPath(path), capability);
            return true;
        } catch (BluezAlreadyExistsException e) {
            logger.debug("Agent already exists (Path: {}).", path, e);
            return true;
        } catch (BluezInvalidArgumentsException e) {
            logger.error("Error while registering Agent (Path: {}).", path, e);
            return false;
        }
    }

    public boolean requestDefaultAgent(String path) {
        try {
            rawAgentManager.RequestDefaultAgent(new DBusPath(path));
            return true;
        } catch (BluezDoesNotExistException e) {
            logger.trace("Profile does not exist (Path: {}).", path, e);
            return false;
        }
    }

    public boolean unregisterAgent(String path) {
        try {
            rawAgentManager.UnregisterAgent(new DBusPath(path));
            return true;
        } catch (BluezDoesNotExistException e) {
            logger.trace("Profile does not exist (Path: {}).", path, e);
            return false;
        }
    }
}
