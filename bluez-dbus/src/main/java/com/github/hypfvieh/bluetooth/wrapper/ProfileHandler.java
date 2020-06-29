package com.github.hypfvieh.bluetooth.wrapper;

import java.util.Map;

import org.bluez.Profile1;
import org.bluez.exceptions.BluezCanceledException;
import org.bluez.exceptions.BluezRejectedException;
import org.freedesktop.dbus.DBusPath;
import org.freedesktop.dbus.FileDescriptor;
import org.freedesktop.dbus.types.Variant;

public final class ProfileHandler implements Profile1 {
    private final String objectPath;
    private final ProfileChangeListener profileChangeListener;

    public ProfileHandler(String _objectPath, ProfileChangeListener _profileChangeListener) {
        this.objectPath = _objectPath;
        this.profileChangeListener = _profileChangeListener;
    }

    @Override
    public void NewConnection(DBusPath _device, FileDescriptor _fd, Map<String, Variant<?>> _fdProperties) throws BluezRejectedException, BluezCanceledException {
        profileChangeListener.onProfileConnection(_device.getPath(), _fd, _fdProperties);
    }

    @Override
    public void Release() {
        profileChangeListener.onProfileRelease();
    }

    @Override
    public void RequestDisconnection(DBusPath _device) throws BluezRejectedException, BluezCanceledException {
        profileChangeListener.onProfileDisconnectRequest(_device.getPath());
    }

    @Override
    public String getObjectPath() {
        return objectPath;
    }

    @Override
    public boolean isRemote() {
        return false;
    }
}
