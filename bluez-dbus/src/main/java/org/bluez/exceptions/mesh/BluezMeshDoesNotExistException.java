package org.bluez.exceptions.mesh;

import org.freedesktop.dbus.exceptions.DBusException;

@SuppressWarnings("serial")
public class BluezMeshDoesNotExistException extends DBusException {

    public BluezMeshDoesNotExistException(String _message) {
        super(_message);
    }

}
