package org.bluez.exceptions.mesh;

import org.freedesktop.dbus.exceptions.DBusException;

@SuppressWarnings("serial")
public class BluezMeshNotFoundException extends DBusException {

    public BluezMeshNotFoundException(String _message) {
        super(_message);
    }

}
