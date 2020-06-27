package com.github.hypfvieh.bluetooth.wrapper;

import com.github.hypfvieh.DbusHelper;
import org.bluez.ProfileManager1;
import org.bluez.exceptions.BluezAlreadyExistsException;
import org.bluez.exceptions.BluezDoesNotExistException;
import org.bluez.exceptions.BluezInvalidArgumentsException;
import org.freedesktop.dbus.DBusPath;
import org.freedesktop.dbus.connections.impl.DBusConnection;
import org.freedesktop.dbus.interfaces.DBusInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.UUID;

public class ProfileManager extends AbstractBluetoothObject {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final ProfileManager1 rawProfileManager;

    public ProfileManager(DBusConnection _dbusConnection) {
        super(BluetoothDeviceType.PROFILE_MANAGER, _dbusConnection, "/org/bluez");
        rawProfileManager = DbusHelper.getRemoteObject(_dbusConnection, getDbusPath(), ProfileManager1.class);

    }

    @Override
    protected Class<? extends DBusInterface> getInterfaceClass() {
        return ProfileManager1.class;
    }

    public boolean registerProfile(String path, String uuid, Map<String, Object> options) {
        try {
            rawProfileManager.RegisterProfile(new DBusPath(path), uuid, optionsToVariantMap(options));
            return true;
        } catch (BluezAlreadyExistsException e) {
            logger.debug("Profile already exists (UUID: {}, Path: {}).", uuid, path, e);
            return true;
        } catch (BluezInvalidArgumentsException e) {
            logger.error("Error while registering Profile (UUID: {}, Path: {}).", uuid, path, e);
            return false;
        }
    }

    public boolean unregisterProfile(UUID uuid, String path) {
        try {
            rawProfileManager.UnregisterProfile(new DBusPath(path));
            return true;
        } catch (BluezDoesNotExistException e) {
            logger.trace("Profile does not exist (UUID: {}, Path: {}).", uuid, path, e);
            return false;
        }
    }


}
