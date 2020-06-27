package com.github.hypfvieh.bluetooth.wrapper;

import org.freedesktop.dbus.FileDescriptor;
import org.freedesktop.dbus.types.Variant;

import java.util.Map;

public interface ProfileChangeListener {
    void onProfileConnection(String dbusPath, FileDescriptor fd, Map<String, Variant<?>> fd_properties);

    void onProfileDisconnectRequest(String path);

    void onProfileRelease();
}
