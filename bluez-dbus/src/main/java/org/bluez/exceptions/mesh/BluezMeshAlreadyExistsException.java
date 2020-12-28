package org.bluez.exceptions.mesh;

import org.freedesktop.dbus.exceptions.DBusException;

@SuppressWarnings("serial")
public class BluezMeshAlreadyExistsException extends DBusException {

    public BluezMeshAlreadyExistsException(String _message) {
        super(_message);
    }

}
