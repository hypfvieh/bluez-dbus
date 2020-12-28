package org.bluez.mesh;

import org.freedesktop.dbus.interfaces.DBusInterface;
import org.freedesktop.dbus.types.UInt64;

/**
 * File generated - 2020-12-28.<br>
 * Based on bluez Documentation: mesh-api.txt.<br>
 * <br>
 * <b>Service:</b> unique name<br>
 * <b>Interface:</b> org.bluez.mesh.Application1<br>
 * <br>
 * <b>Object path:</b><br>
 *             &lt;app_root&gt;<br>
 * <br>
 * <b>Supported properties:</b> <br>
 * <br>
 *  uint16 CompanyID [read-only]<br>
 *  <br>
 *      A 16-bit Bluetooth-assigned Company Identifier of the vendor as<br>
 *      defined by Bluetooth SIG<br>
 *  <br>
 *  uint16 ProductID [read-only]<br>
 *  <br>
 *      A 16-bit vendor-assigned product identifier<br>
 *  <br>
 *  uint16 VersionID [read-only]<br>
 *  <br>
 *      A 16-bit vendor-assigned product version identifier<br>
 *  <br>
 *  uint16 CRPL [read-only, optional]<br>
 *  <br>
 *      A 16-bit minimum number of replay protection list entries
 * <br>
 * <br>
 */
public interface Application1 extends DBusInterface {

    /**
     * <b>From bluez documentation:</b><br>
     * <br>
     * This method is called when the node provisioning initiated<br>
     * by a Join() method call successfully completed.<br>
     * <br>
     * The token parameter serves as a unique identifier of the<br>
     * particular node. The token must be preserved by the application<br>
     * in order to authenticate itself to the mesh daemon and attach to<br>
     * the network as a mesh node by calling Attach() method or<br>
     * permanently remove the identity of the mesh node by calling<br>
     * Leave() method.<br>
     * <br>
     * If this method returns an error, the daemon will assume that the<br>
     * application failed to preserve the token, and will remove the<br>
     * freshly created node.
     *
     * @param _token token
     */
    void JoinComplete(UInt64 _token);


    /**
     * <b>From bluez documentation:</b><br>
     * <br>
     * This method is called when the node provisioning initiated by<br>
     * Join() has failed.<br>
     * <br>
     * The reason parameter identifies the reason for provisioning<br>
     * failure. The defined values are: "timeout", "bad-pdu",<br>
     * "confirmation-failed", "out-of-resources", "decryption-error",<br>
     * "unexpected-error", "cannot-assign-addresses".
     *
     * @param _reason reason
     */
    void JoinFailed(String _reason);

}
