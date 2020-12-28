package org.bluez.mesh;

import org.bluez.exceptions.mesh.BluezMeshNotSupportedException;
import org.freedesktop.dbus.interfaces.DBusInterface;
import org.freedesktop.dbus.types.UInt16;

/**
 * File generated - 2020-12-28.<br>
 * Based on bluez Documentation: mesh-api.txt.<br>
 * <br>
 * <b>Service:</b> unique name<br>
 * <b>Interface:</b> org.bluez.mesh.Attention1<br>
 * <br>
 * <b>Object path:</b><br>
 *             freely definable<br>
 * <br>
 */
public interface Attention1 extends DBusInterface {

    /**
     * <b>From bluez documentation:</b><br>
     * <br>
     * The element_index parameter is the element's index within the<br>
     * node where the health server model is hosted.<br>
     * <br>
     * The time parameter indicates how many seconds the attention<br>
     * state shall be on.
     *
     * @param _elementIndex element_index
     * @param _time time
     *
     * @throws BluezMeshNotSupportedException when operation is not supported
     */
    void SetTimer(byte _elementIndex, UInt16 _time) throws BluezMeshNotSupportedException;

    /**
     * <b>From bluez documentation:</b><br>
     * <br>
     *  The element parameter is the unicast address within the node<br>
     *  where the health server model is hosted.<br>
     *  <br>
     *  Returns the number of seconds for how long the attention action<br>
     *  remains staying on.
     *
     * @param _element element
     *
     * @return UInt16 - maybe null
     *
     * @throws BluezMeshNotSupportedException when operation is not supported
     */
    UInt16 GetTimer(UInt16 _element) throws BluezMeshNotSupportedException;

}
