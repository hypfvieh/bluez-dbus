package com.github.hypfvieh.bluetooth.wrapper;

import java.util.Map;
import java.util.UUID;

import org.bluez.ProfileManager1;
import org.bluez.exceptions.BluezAlreadyExistsException;
import org.bluez.exceptions.BluezDoesNotExistException;
import org.bluez.exceptions.BluezInvalidArgumentsException;
import org.freedesktop.dbus.DBusPath;
import org.freedesktop.dbus.connections.impl.DBusConnection;
import org.freedesktop.dbus.interfaces.DBusInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.hypfvieh.DbusHelper;

public class ProfileManager extends AbstractBluetoothObject {
    private final Logger          logger = LoggerFactory.getLogger(getClass());

    private final ProfileManager1 rawProfileManager;

    public ProfileManager(DBusConnection _dbusConnection) {
        super(BluetoothDeviceType.PROFILE_MANAGER, _dbusConnection, "/org/bluez");
        rawProfileManager = DbusHelper.getRemoteObject(_dbusConnection, getDbusPath(), ProfileManager1.class);

    }

    @Override
    protected Class<? extends DBusInterface> getInterfaceClass() {
        return ProfileManager1.class;
    }

    public boolean registerProfile(String _path, String _uuid, Map<String, Object> _options) {
        try {
            rawProfileManager.RegisterProfile(new DBusPath(_path), _uuid, optionsToVariantMap(_options));
            return true;
        } catch (BluezAlreadyExistsException e) {
            logger.debug("Profile already exists (UUID: {}, Path: {}).", _uuid, _path, e);
            return true;
        } catch (BluezInvalidArgumentsException e) {
            logger.error("Error while registering Profile (UUID: {}, Path: {}).", _uuid, _path, e);
            return false;
        }
    }

    public boolean unregisterProfile(UUID _uuid, String _path) {
        try {
            rawProfileManager.UnregisterProfile(new DBusPath(_path));
            return true;
        } catch (BluezDoesNotExistException e) {
            logger.trace("Profile does not exist (UUID: {}, Path: {}).", _uuid, _path, e);
            return false;
        }
    }

}
