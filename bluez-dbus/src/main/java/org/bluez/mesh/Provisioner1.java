package org.bluez.mesh;

import java.util.Map;

import org.bluez.datatypes.TwoTuple;
import org.bluez.exceptions.mesh.BluezMeshAbortException;
import org.freedesktop.dbus.interfaces.DBusInterface;
import org.freedesktop.dbus.types.UInt16;
import org.freedesktop.dbus.types.Variant;

/**
 * File generated - 2020-12-28.<br>
 * Based on bluez Documentation: mesh-api.txt.<br>
 * <br>
 * <b>Service:</b> unique name<br>
 * <b>Interface:</b> org.bluez.mesh.Provisioner1<br>
 * <br>
 * <b>Object path:</b><br>
 *             freely definable<br>
 * <br>
 * <b>Supported properties:</b> <br>
 * <br>
 * 		An array of strings with the following allowed values:<br>
 * 			"blink"<br>
 * 			"beep"<br>
 * 			"vibrate"<br>
 * 			"out-numeric"<br>
 * 			"out-alpha"<br>
 * 			"push"<br>
 * 			"twist"<br>
 * 			"in-numeric"<br>
 * 			"in-alpha"<br>
 * 			"static-oob"<br>
 * 			"public-oob"<br>
 * <br>
 * <br>
 * 		Indicates availability of OOB data. An array of strings with the<br>
 * 		following allowed values:<br>
 * 			"other"<br>
 * 			"uri"<br>
 * 			"machine-code-2d"<br>
 * 			"bar-code"<br>
 * 			"nfc"<br>
 * 			"number"<br>
 * 			"string"<br>
 * 			"on-box"<br>
 * 			"in-box"<br>
 * 			"on-paper",<br>
 * 			"in-manual"<br>
 * 			"on-device"<br>
 * <br>
 * <br>
 * 		Uniform Resource Identifier points to out-of-band (OOB)<br>
 * 		information (e.g., a public key)<br>
 * <br>
 * <br>
 */
public interface Provisioner1 extends DBusInterface {

    /**
     * <b>From bluez documentation:</b><br>
     * <br>
     * The method is called from the bluetooth-meshd daemon when a<br>
     * unique UUID has been seen during UnprovisionedScan() for<br>
     * unprovsioned devices.<br>
     * <br>
     * The rssi parameter is a signed, normalized measurement of the<br>
     * signal strength of the recieved unprovisioned beacon.<br>
     * <br>
     * The data parameter is a variable length byte array, that may<br>
     * have 1, 2 or 3 distinct fields contained in it including the 16<br>
     * byte remote device UUID (always), a 16 bit mask of OOB<br>
     * authentication flags (optional), and a 32 bit URI hash (if URI<br>
     * bit set in OOB mask). Whether these fields exist or not is a<br>
     * decision of the remote device.<br>
     * <br>
     * The options parameter is a dictionary that may contain<br>
     * additional scan result info (currently an empty placeholder for<br>
     * forward compatibility).<br>
     * <br>
     * If a beacon with a UUID that has already been reported is<br>
     * recieved by the daemon, it will be silently discarded unless it<br>
     * was recieved at a higher rssi power level.
     *
     * @param _rssi rssi
     * @param _data data
     * @param _options options
     */
    void ScanResult(int _rssi, byte[] _data, Map<String, Variant<?>> _options);


    /**
     * <b>From bluez documentation:</b><br>
     * <br>
     * This method is implemented by a Provisioner capable application<br>
     * and is called when the remote device has been fully<br>
     * authenticated and confirmed.<br>
     * <br>
     * The count parameter is the number of consecutive unicast<br>
     * addresses the remote device is requesting.<br>
     * <br>
     * Return Parameters are from the Mesh Profile Spec:<br>
     * net_index - Subnet index of the net_key<br>
     * unicast - Primary Unicast address of the new node
     *
     * @param _count count
     *
     * @return Tuple of net_index and unicast, maybe null
     *
     * @throws BluezMeshAbortException when operation is aborted
     */
    TwoTuple<UInt16, UInt16> RequestProvData(byte _count) throws BluezMeshAbortException;

    /**
     * <b>From bluez documentation:</b><br>
     * <br>
     * This method is called when the node provisioning initiated<br>
     * by an AddNode() method call successfully completed.<br>
     * <br>
     * The unicast parameter is the primary address that has been<br>
     * assigned to the new node, and the address of it's config server.<br>
     * <br>
     * The count parameter is the number of unicast addresses assigned<br>
     * to the new node.<br>
     * <br>
     * The new node may now be sent messages using the credentials<br>
     * supplied by the RequestProvData method.
     *
     * @param _uuid uuid
     * @param _unicast unicast
     * @param _count count
     */
    void AddNodeComplete(byte[] _uuid, UInt16 _unicast, byte _count);

    /**
     * <b>From bluez documentation:</b><br>
     * <br>
     * This method is called when the node provisioning initiated by<br>
     * AddNode() has failed. Depending on how far Provisioning<br>
     * proceeded before failing, some cleanup of cached data may be<br>
     * required.<br>
     * <br>
     * The reason parameter identifies the reason for provisioning<br>
     * failure. The defined values are: "aborted", "timeout",<br>
     * "bad-pdu", "confirmation-failed", "out-of-resources",<br>
     * "decryption-error", "unexpected-error",<br>
     * "cannot-assign-addresses".
     *
     * @param _uuid uuid
     * @param _reason reason
     */
    void AddNodeFailed(byte[] _uuid, String _reason);

}
