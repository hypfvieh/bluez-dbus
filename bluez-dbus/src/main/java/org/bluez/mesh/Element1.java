package org.bluez.mesh;

import java.util.Map;

import org.freedesktop.dbus.interfaces.DBusInterface;
import org.freedesktop.dbus.types.UInt16;
import org.freedesktop.dbus.types.Variant;

/**
* File generated - 2020-12-28.<br>
* Based on bluez Documentation: mesh-api.txt.<br>
* <br>
* <b>Service:</b> org.bluez.mesh<br>
* <b>Interface:</b> org.bluez.mesh.Element1<br>
* <br>
* <b>Object path: &lt;app_defined_element_path&gt;</b><br>
* <br>
* <b>Supported properties:</b> <br>
*   uint8 Index [read-only]<br>
*   <br>
*       Element index. It is required that the application follows<br>
*       sequential numbering scheme for the elements, starting with 0.<br>
*   <br>
*   array{(uint16 id, dict caps)} Models [read-only]<br>
*   <br>
*       An array of SIG Models:<br>
*   <br>
*           id - SIG Model Identifier<br>
*   <br>
*           options - a dictionary that may contain additional model<br>
*           info. The following keys are defined:<br>
*   <br>
*               boolean Publish - indicates whether the model<br>
*                   supports publication mechanism. If not<br>
*                   present, publication is enabled.<br>
*   <br>
*               boolean Subscribe - indicates whether the model<br>
*                   supports subscription mechanism. If not<br>
*                   present, subscriptons are enabled.<br>
*   <br>
*       The array may be empty.<br>
*   <br>
*   <br>
*   array{(uint16 vendor, uint16 id, dict options)} VendorModels [read-only]<br>
*   <br>
*       An array of Vendor Models:<br>
*   <br>
*           vendor - a 16-bit Bluetooth-assigned Company ID as<br>
*           defined by Bluetooth SIG.<br>
*   <br>
*           id - a 16-bit vendor-assigned Model Identifier<br>
*   <br>
*           options - a dictionary that may contain additional model<br>
*           info. The following keys are defined:<br>
*   <br>
*               boolean Publish - indicates whether the model<br>
*                   supports publication mechanism<br>
*   <br>
*               boolean Subscribe - indicates whether the model<br>
*                   supports subscription mechanism<br>
*   <br>
*       The array may be empty.<br>
*   <br>
*   uint16 Location [read-only, optional]<br>
*   <br>
*       Location descriptor as defined in the GATT Bluetooth Namespace<br>
*       Descriptors section of the Bluetooth SIG Assigned Numbers<br>
*
*/
public interface Element1 extends DBusInterface {

    /**
     * <b>From bluez documentation:</b><br>
     * <br>
     * This method is called by bluetooth-meshd daemon when a message<br>
     * arrives addressed to the application.<br>
     * <br>
     * The source parameter is unicast address of the remote<br>
     * node-element that sent the message.<br>
     * <br>
     * The key_index parameter indicates which application key has been<br>
     * used to decode the incoming message. The same key_index should<br>
     * be used by the application when sending a response to this<br>
     * message (in case a response is expected).<br>
     * <br>
     * The destination parameter contains the destination address of<br>
     * received message. Underlying variant types are:
     * <pre>
     *      uint16
     *
     *      Destination is an unicast address, or a well known
     *      group address
     *
     *      array{byte}
     *
     *      Destination is a virtual address label
     * </pre>
     * The data parameter is the incoming message.
     *
     * @param _source source
     * @param _keyIndex key_index
     * @param _destination destination
     * @param _data data
     */
    void MessageReceived(UInt16 _source, UInt16 _keyIndex, Variant<?> _destination, byte[] _data);

    /**
     * <b>From bluez documentation:</b><br>
     * <br>
     *  This method is called by meshd daemon when a message arrives<br>
     *  addressed to the application, which was sent with the remote<br>
     *  node's device key.<br>
     *  <br>
     *  The source parameter is unicast address of the remote<br>
     *  node-element that sent the message.<br>
     *  <br>
     *  The remote parameter if true indicates that the device key<br>
     *  used to decrypt the message was from the sender. False<br>
     *  indicates that the local nodes device key was used, and the<br>
     *  message has permissions to modify local states.<br>
     *  <br>
     *  The net_index parameter indicates what subnet the message was<br>
     *  received on, and if a response is required, the same subnet<br>
     *  must be used to send the response.<br>
     *  <br>
     *  The data parameter is the incoming message.
     *
     * @param _source source
     * @param _remote remote
     * @param _net_index net_index
     * @param _data data
     */
    void DevKeyMessageReceived(UInt16 _source, boolean _remote, UInt16 _net_index, byte[] _data);

    /**
     * <b>From bluez documentation:</b><br>
     * <br>
     * This method is called by bluetooth-meshd daemon when a model's<br>
     * configuration is updated.<br>
     * <br>
     * The model_id parameter contains BT SIG Model Identifier or, if<br>
     * Vendor key is present in config dictionary, a 16-bit<br>
     * vendor-assigned Model Identifier.<br>
     * <br>
     * The config parameter is a dictionary with the following keys<br>
     * defined:
     * <pre>
     *  array{uint16} Bindings
     *
     *      Indices of application keys bound to the model
     *
     *  uint32 PublicationPeriod
     *
     *      Model publication period in milliseconds
     *
     *  uint16 Vendor
     *
     *      A 16-bit Bluetooth-assigned Company Identifier of the
     *      vendor as defined by Bluetooth SIG
     *
     *  array{variant} Subscriptions
     *
     *      Addresses the model is subscribed to.
     *
     *      Each address is provided either as uint16 for group
     *      addresses, or as array{byte} for virtual labels.
     * </pre>
     *
     * @param _modelId model_id
     * @param _config config
     */
    void UpdateModelConfiguration(UInt16 _modelId, Map<String, Variant<?>> _config);
}
