package com.github.hypfvieh.bluetooth.wrapper;

import org.bluez.Profile1;
import org.bluez.exceptions.BluezCanceledException;
import org.bluez.exceptions.BluezRejectedException;
import org.freedesktop.dbus.DBusPath;
import org.freedesktop.dbus.FileDescriptor;
import org.freedesktop.dbus.types.Variant;

import java.util.Map;

public final class ProfileHandler implements Profile1 {
    private final String objectPath;
    private final ProfileChangeListener profileChangeListener;

    public ProfileHandler(String objectPath, ProfileChangeListener profileChangeListener) {
        this.objectPath = objectPath;
        this.profileChangeListener = profileChangeListener;
    }

    @Override
    public void NewConnection(DBusPath _device, FileDescriptor fd, Map<String, Variant<?>> _fd_properties) throws BluezRejectedException, BluezCanceledException {
        profileChangeListener.onProfileConnection(_device.getPath(), fd, _fd_properties);
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
