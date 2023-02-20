package org.bluez;

import org.bluez.exceptions.*;
import org.freedesktop.dbus.FileDescriptor;
import org.freedesktop.dbus.interfaces.DBusInterface;

/**
 * File generated - 2023-02-20.<br>
 * Based on bluez Documentation: health-api.txt.<br>
 * <br>
 * <b>Service:</b> org.bluez<br>
 * <b>Interface:</b> org.bluez.HealthChannel1<br>
 * <br>
 * <b>Object path:</b><br>
 *             [variable prefix]/{hci0,hci1,...}/dev_XX_XX_XX_XX_XX_XX/chanZZZ<br>
 * <br>
 * <b>Supported properties:</b> <br>
 * <br>
 * 		string Type [readonly]<br>
 * <br>
 * 			The quality of service of the data channel. ("reliable"<br>
 * 			or "streaming")<br>
 * <br>
 * 		object Device [readonly]<br>
 * <br>
 * 			Identifies the Remote Device that is connected with.<br>
 * 			Maps with a HealthDevice object.<br>
 * <br>
 * 		object Application [readonly]<br>
 * <br>
 * 			Identifies the HealthApplication to which this channel<br>
 * 			is related to (which indirectly defines its role and<br>
 * 			data type).<br>
 * <br>
 */
public interface HealthChannel1 extends DBusInterface {

    /**
     * <b>From bluez documentation:</b><br>
     * <br>
     * Returns the file descriptor for this data channel. If<br>
     * the data channel is not connected it will also<br>
     * reconnect.<br>
     * <br>
     * 
     * @return FileDescriptor - maybe null
     * 
     * @throws BluezNotConnectedException when bluez not connected
     * @throws BluezNotAllowedException when operation not allowed
     */
    FileDescriptor Acquire() throws BluezNotConnectedException, BluezNotAllowedException;

    /**
     * <b>From bluez documentation:</b><br>
     * <br>
     * Releases the fd. Application should also need to<br>
     * close() it.<br>
     * <br>
     * 
     * @throws BluezNotAcquiredException when item is not acquired
     * @throws BluezNotAllowedException when operation not allowed
     */
    void Release() throws BluezNotAcquiredException, BluezNotAllowedException;

}
