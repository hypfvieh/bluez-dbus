package org.bluez.exceptions.mesh;

import org.freedesktop.dbus.exceptions.DBusException;

@SuppressWarnings("serial")
public class BluezMeshInProgressException extends DBusException {

    public BluezMeshInProgressException(String _message) {
        super(_message);
    }

}
