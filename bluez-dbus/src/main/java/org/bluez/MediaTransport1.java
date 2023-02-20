package org.bluez;

import org.bluez.datatypes.ThreeTuple;
import org.bluez.exceptions.*;
import org.freedesktop.dbus.FileDescriptor;
import org.freedesktop.dbus.interfaces.DBusInterface;
import org.freedesktop.dbus.types.UInt16;

/**
 * File generated - 2023-02-20.<br>
 * Based on bluez Documentation: media-api.txt.<br>
 * <br>
 * <b>Service:</b> org.bluez<br>
 * <b>Interface:</b> org.bluez.MediaTransport1<br>
 * <br>
 * <b>Object path:</b><br>
 *             [variable prefix]/{hci0,hci1,...}/dev_XX_XX_XX_XX_XX_XX/fdX<br>
 * <br>
 * <b>Supported properties:</b> <br>
 * <br>
 * 		object Device [readonly]<br>
 * <br>
 * 			Device object which the transport is connected to.<br>
 * <br>
 * 		string UUID [readonly]<br>
 * <br>
 * 			UUID of the profile which the transport is for.<br>
 * <br>
 * 		byte Codec [readonly]<br>
 * <br>
 * 			Assigned number of codec that the transport support.<br>
 * 			The values should match the profile specification which<br>
 * 			is indicated by the UUID.<br>
 * <br>
 * 		array{byte} Configuration [readonly]<br>
 * <br>
 * 			Configuration blob, it is used as it is so the size and<br>
 * 			byte order must match.<br>
 * <br>
 * 		string State [readonly]<br>
 * <br>
 * 			Indicates the state of the transport. Possible<br>
 * 			values are:<br>
 * 				"idle": not streaming<br>
 * 				"pending": streaming but not acquired<br>
 * 				"active": streaming and acquired<br>
 * <br>
 * 		uint16 Delay [readwrite]<br>
 * <br>
 * 			Optional. Transport delay in 1/10 of millisecond, this<br>
 * 			property is only writeable when the transport was<br>
 * 			acquired by the sender.<br>
 * <br>
 * 		uint16 Volume [readwrite]<br>
 * <br>
 * 			Optional. Indicates volume level of the transport,<br>
 * 			this property is only writeable when the transport was<br>
 * 			acquired by the sender.<br>
 * <br>
 * 			Possible Values: 0-127<br>
 * <br>
 * 		object Endpoint [readonly, optional, experimental]<br>
 * <br>
 * 			Endpoint object which the transport is associated<br>
 * 			with.<br>
 * <br>
 * 		uint32 Location [readonly, ISO only, experimental]<br>
 * <br>
 * 			Indicates transport Audio Location.<br>
 * <br>
 * 		array{byte} Metadata [ISO Only, experimental]<br>
 * <br>
 * 			Indicates transport Metadata.<br>
 * <br>
 * 		array{object} Links [readonly, optional, ISO only, experimental]<br>
 * <br>
 * 			Linked transport objects which the transport is<br>
 * 			associated with.<br>
 * <br>
 */
public interface MediaTransport1 extends DBusInterface {

    /**
     * <b>From bluez documentation:</b><br>
     * <br>
     * Acquire transport file descriptor and the MTU for read<br>
     * and write respectively.<br>
     * <br>
     * 
     * @return ThreeTuple&lt;FileDescriptor, UInt16, UInt16&gt; - maybe null
     * 
     * @throws BluezNotAuthorizedException when not authorized
     * @throws BluezFailedException on failure
     */
    ThreeTuple<FileDescriptor, UInt16, UInt16> Acquire() throws BluezNotAuthorizedException, BluezFailedException;

    /**
     * <b>From bluez documentation:</b><br>
     * <br>
     * Acquire transport file descriptor only if the transport<br>
     * is in "pending" state at the time the message is<br>
     * received by BlueZ. Otherwise no request will be sent<br>
     * to the remote device and the function will just fail<br>
     * with org.bluez.Error.NotAvailable.<br>
     * <br>
     * 
     * @return ThreeTuple&lt;FileDescriptor, UInt16, UInt16&gt; - maybe null
     * 
     * @throws BluezNotAuthorizedException when not authorized
     * @throws BluezFailedException on failure
     * @throws BluezNotAvailableException when not available
     */
    ThreeTuple<FileDescriptor, UInt16, UInt16> TryAcquire() throws BluezNotAuthorizedException, BluezFailedException, BluezNotAvailableException;

    /**
     * <b>From bluez documentation:</b><br>
     * <br>
     * Releases file descriptor.<br>
     * <br>
     */
    void Release();

}
