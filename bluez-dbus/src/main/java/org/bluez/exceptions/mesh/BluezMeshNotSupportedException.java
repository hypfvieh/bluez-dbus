package org.bluez.exceptions.mesh;

import org.freedesktop.dbus.exceptions.DBusException;

@SuppressWarnings("serial")
public class BluezMeshNotSupportedException extends DBusException {

    public BluezMeshNotSupportedException(String _message) {
        super(_message);
    }

}
