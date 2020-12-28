package org.bluez.exceptions.mesh;

import org.freedesktop.dbus.exceptions.DBusException;

@SuppressWarnings("serial")
public class BluezMeshNotAuthorizedException extends DBusException {

    public BluezMeshNotAuthorizedException(String _message) {
        super(_message);
    }

}
