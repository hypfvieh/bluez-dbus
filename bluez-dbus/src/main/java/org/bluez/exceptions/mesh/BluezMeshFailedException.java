package org.bluez.exceptions.mesh;

import org.freedesktop.dbus.exceptions.DBusException;

@SuppressWarnings("serial")
public class BluezMeshFailedException extends DBusException {

    public BluezMeshFailedException(String _message) {
        super(_message);
    }

}
