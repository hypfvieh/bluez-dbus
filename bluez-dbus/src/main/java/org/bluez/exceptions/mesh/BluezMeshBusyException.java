package org.bluez.exceptions.mesh;

import org.freedesktop.dbus.exceptions.DBusException;

@SuppressWarnings("serial")
public class BluezMeshBusyException extends DBusException {

    public BluezMeshBusyException(String _message) {
        super(_message);
    }

}
