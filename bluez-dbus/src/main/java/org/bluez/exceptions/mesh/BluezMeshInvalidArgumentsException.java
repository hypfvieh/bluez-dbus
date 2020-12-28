package org.bluez.exceptions.mesh;

import org.freedesktop.dbus.exceptions.DBusException;

@SuppressWarnings("serial")
public class BluezMeshInvalidArgumentsException extends DBusException {

    public BluezMeshInvalidArgumentsException(String _message) {
        super(_message);
    }

}
