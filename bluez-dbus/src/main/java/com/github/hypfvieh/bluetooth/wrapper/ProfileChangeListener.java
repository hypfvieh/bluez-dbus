package com.github.hypfvieh.bluetooth.wrapper;

import java.util.Map;

import org.freedesktop.dbus.FileDescriptor;
import org.freedesktop.dbus.types.Variant;

public interface ProfileChangeListener {
    void onProfileConnection(String _dbusPath, FileDescriptor _fd, Map<String, Variant<?>> _fdProperties);

    void onProfileDisconnectRequest(String _path);

    void onProfileRelease();
}
