package org.bluez.exceptions.mesh;

import org.freedesktop.dbus.exceptions.DBusException;

@SuppressWarnings("serial")
public class BluezMeshAbortException extends DBusException {

    public BluezMeshAbortException(String _message) {
        super(_message);
    }

}
