package org.bluez.mesh;

import java.util.Map;

import org.bluez.exceptions.mesh.BluezMeshDoesNotExistException;
import org.bluez.exceptions.mesh.BluezMeshInvalidArgumentsException;
import org.bluez.exceptions.mesh.BluezMeshNotAuthorizedException;
import org.bluez.exceptions.mesh.BluezMeshNotFoundException;
import org.freedesktop.dbus.DBusPath;
import org.freedesktop.dbus.interfaces.DBusInterface;
import org.freedesktop.dbus.types.UInt16;
import org.freedesktop.dbus.types.Variant;

/**
 * File generated - 2020-12-28.<br>
 * Based on bluez Documentation: mesh-api.txt.<br>
 * <br>
 * <b>Service:</b> org.bluez.mesh<br>
 * <b>Interface:</b> org.bluez.mesh.Node1<br>
 * <br>
 * <b>Object path:</b><br>
 *             /org/bluez/mesh/node&lt;uuid&gt;<br>
 *             where <uuid> is the Device UUID passed to Join(),<br>
 *             CreateNetwork() or Import()<br>
 * <br>
 * <b>Supported properties:</b> <br>
 *  dict Features [read-only]<br>
 *  <br>
 *      The dictionary that contains information about feature support.<br>
 *      The following keys are defined:<br>
 *  <br>
 *      boolean Friend<br>
 *  <br>
 *          Indicates the ability to establish a friendship with a<br>
 *          Low Power node<br>
 *  <br>
 *      boolean LowPower<br>
 *  <br>
 *          Indicates support for operating in Low Power node mode<br>
 *  <br>
 *      boolean Proxy<br>
 *  <br>
 *          Indicates support for GATT proxy<br>
 *  <br>
 *      boolean Relay<br>
 *          Indicates support for relaying messages<br>
 *  <br>
 *  If a key is absent from the dictionary, the feature is not supported.<br>
 *  Otherwise, true means that the feature is enabled and false means that<br>
 *  the feature is disabled.<br>
 *  <br>
 *  boolean Beacon [read-only]<br>
 *  <br>
 *      This property indicates whether the periodic beaconing is<br>
 *      enabled (true) or disabled (false).<br>
 *  <br>
 *  boolean IvUpdate [read-only]<br>
 *  <br>
 *      When true, indicates that the network is in the middle of IV<br>
 *      Index Update procedure. This information is only useful for<br>
 *      provisioning.<br>
 *  <br>
 *  uint32 IvIndex [read-only]<br>
 *  <br>
 *      This property may be read at any time to determine the IV_Index<br>
 *      that the current network is on. This information is only useful<br>
 *      for provisioning.<br>
 *  <br>
 *  uint32 SecondsSinceLastHeard [read-only]<br>
 *  <br>
 *      This property may be read at any time to determine the number of<br>
 *      seconds since mesh network layer traffic was last detected on<br>
 *      this node's network.<br>
 *  <br>
 *  array{uint16} Addresses [read-only]<br>
 *  <br>
 *      This property contains unicast addresses of node's elements.<br>
 *  <br>
 *  uint32 SequenceNumber [read-only]<br>
 *  <br>
 *      This property may be read at any time to determine the<br>
 *      sequence number.
 */
public interface Node1 extends DBusInterface {

    /**
     * <b>From bluez documentation:</b><br>
     * <br>
     *  This method is used to send a message originated by a local<br>
     *  model.<br>
     *  <br>
     *  The element_path parameter is the object path of an element from<br>
     *  a collection of the application elements (see Mesh Application<br>
     *  Hierarchy section).<br>
     *  <br>
     *  The destination parameter contains the destination address. This<br>
     *  destination must be a uint16 to a unicast address, or a well<br>
     *  known group address.<br>
     *  <br>
     *  The key_index parameter determines which application key to use<br>
     *  for encrypting the message. The key_index must be valid for that<br>
     *  element, i.e., the application key must be bound to a model on<br>
     *  this element. Otherwise, org.bluez.mesh.Error.NotAuthorized will<br>
     *  be returned.<br>
     *  <br>
     *  The options parameter is a dictionary with the following keys<br>
     *  defined:
     *  <pre>
     *      bool ForceSegmented
     *          Specifies whether to force sending of a short
     *          message as one-segment payload. If not present,
     *          the default setting is "false".
     *  </pre>
     *  The data parameter is an outgoing message to be encypted by the<br>
     *  bluetooth-meshd daemon and sent on.
     *
     * @param _elementPath element_path
     * @param _destination destination
     * @param _keyIndex key_index
     * @param _options options
     * @param _data data
     *
     * @throws BluezMeshInvalidArgumentsException when invalid argument given
     * @throws BluezMeshNotAuthorizedException when not authorized
     * @throws BluezMeshNotFoundException when mesh not found
     */
    void Send(DBusPath _elementPath, UInt16 _destination, UInt16 _keyIndex, Map<String, Variant<?>> _options, byte[] _data) throws BluezMeshNotFoundException, BluezMeshInvalidArgumentsException, BluezMeshNotAuthorizedException;

    /**
     * <b>From bluez documentation:</b><br>
     * <br>
     *  This method is used to send a message originated by a local<br>
     *  model encoded with the device key of the remote node.<br>
     * <br>
     *  The element_path parameter is the object path of an element from<br>
     *  a collection of the application elements (see Mesh Application<br>
     *  Hierarchy section).<br>
     * <br>
     *  The destination parameter contains the destination address. This<br>
     *  destination must be a uint16 to a unicast address, or a well<br>
     *  known group address.<br>
     * <br>
     *  The remote parameter, if true, looks up the device key by the<br>
     *  destination address in the key database to encrypt the message.<br>
     *  If remote is true, but requested key does not exist, a NotFound<br>
     *  error will be returned. If set to false, the local node's<br>
     *  device key is used.<br>
     * <br>
     *  The net_index parameter is the subnet index of the network on<br>
     *  which the message is to be sent.<br>
     * <br>
     *  The options parameter is a dictionary with the following keys<br>
     *  defined:<br>
     * <br>
     * <pre>
     *      bool ForceSegmented
     *          Specifies whether to force sending of a short
     *          message as one-segment payload. If not present,
     *          the default setting is "false".
     *  </pre>
     *  <br>
     *  The data parameter is an outgoing message to be encypted by the<br>
     *  meshd daemon and sent on.<br>
     *
     * @param _elementPath element_path
     * @param _destination destination
     * @param _remote remote
     * @param _netIndex net_index
     * @param _options options
     * @param _data data
     *
     * @throws BluezMeshInvalidArgumentsException when invalid argument provided
     * @throws BluezMeshNotFoundException when mesh not found
     */
    void DevKeySend(DBusPath _elementPath, UInt16 _destination, boolean _remote, UInt16 _netIndex, Map<String, Variant<?>> _options, byte[] _data) throws BluezMeshInvalidArgumentsException, BluezMeshNotFoundException;

    /**
     * <b>From bluez documentation:</b><br>
     * <br>
     *  This method is used to send add or update network key originated<br>
     *  by the local configuration client to a remote configuration<br>
     *  server.<br>
     *  <br>
     *  The element_path parameter is the object path of an element from<br>
     *  a collection of the application elements (see Mesh Application<br>
     *  Hierarchy section).<br>
     *  <br>
     *  The destination parameter contains the destination address. This<br>
     *  destination must be a uint16 to a nodes primary unicast address.<br>
     *  <br>
     *  The subnet_index parameter refers to the subnet index of the<br>
     *  network that is being added or updated. This key must exist in<br>
     *  the local key database.<br>
     *  <br>
     *  The net_index parameter is the subnet index of the network on<br>
     *  which the message is to be sent.<br>
     *  <br>
     *  The update parameter indicates if this is an addition or an<br>
     *  update. If true, the subnet key must be in the phase 1 state of<br>
     *  the key update procedure.
     *
     * @param _elementPath element_path
     * @param _destination destination
     * @param _subnetIndex subnet_index
     * @param _netIndex net_index
     * @param _update update
     *
     * @throws BluezMeshInvalidArgumentsException when invalid argument provided
     * @throws BluezMeshNotFoundException when mesh not found
     */
    void AddNetKey(DBusPath _elementPath, UInt16 _destination, UInt16 _subnetIndex, UInt16 _netIndex, boolean _update) throws BluezMeshInvalidArgumentsException, BluezMeshNotFoundException;

    /**
     * <b>From bluez documentation:</b><br>
     * <br>
     * This method is used to send add or update network key originated<br>
     * by the local configuration client to a remote configuration<br>
     * server.<br>
     * <br>
     * The element_path parameter is the object path of an element from<br>
     * a collection of the application elements (see Mesh Application<br>
     * Hierarchy section).<br>
     * <br>
     * The destination parameter contains the destination address. This<br>
     * destination must be a uint16 to a nodes primary unicast address.<br>
     * <br>
     * The app_index parameter refers to the application key which is<br>
     * being added or updated. This key must exist in the local key<br>
     * database.<br>
     * <br>
     * The net_index parameter is the subnet index of the network on<br>
     * which the message is to be sent.<br>
     * <br>
     * The update parameter indicates if this is an addition or an<br>
     * update. If true, the subnet key must be in the phase 1 state of<br>
     * the key update procedure.
     *
     * @param _elementPath element_path
     * @param _destination destination
     * @param _appIndex app_index
     * @param _netIndex net_index
     * @param _update update
     *
     * @throws BluezMeshInvalidArgumentsException when invalid argument provided
     * @throws BluezMeshNotFoundException when mesh not found
     */
    void AddAppKey(DBusPath _elementPath, UInt16 _destination, UInt16 _appIndex, UInt16 _netIndex, boolean _update) throws BluezMeshInvalidArgumentsException, BluezMeshNotFoundException;

    /**
     * <b>From bluez documentation:</b><br>
     * <br>
     *  This method is used to send a publication originated by a local<br>
     *  model. If the model does not exist, or it has no publication<br>
     *  record, the method returns org.bluez.mesh.Error.DoesNotExist<br>
     *  error.<br>
     *  <br>
     *  The element_path parameter is the object path of an element from<br>
     *  a collection of the application elements (see Mesh Application<br>
     *  Hierarchy section).<br>
     *  <br>
     *  The model parameter contains a model ID, as defined by the<br>
     *  Bluetooth SIG. If the options dictionary contains a "Vendor"<br>
     *  key, then this ID is defined by the specified vendor.<br>
     *  <br>
     *  The options parameter is a dictionary with the following keys<br>
     *  defined:
     *  <pre>
     *      bool ForceSegmented
     *          Specifies whether to force sending of a short
     *          message as one-segment payload. If not present,
     *          the default setting is "false".
     *
     *      uint16 Vendor
     *          A 16-bit Company ID as defined by the
     *          Bluetooth SIG. This key should only exist when
     *          publishing on a Vendor defined model.
     *  </pre>
     *
     *  The data parameter is an outgoing message to be encrypted by the<br>
     *  meshd daemon and sent on.<br>
     *  <br>
     *  Since only one Publish record may exist per element-model, the<br>
     *  destination and key_index are obtained from the Publication<br>
     *  record cached by the daemon.
     *
     * @param _elementPath element_path
     * @param _model model
     * @param _options options
     * @param _data data
     *
     * @throws BluezMeshInvalidArgumentsException when invalid argument provided
     * @throws BluezMeshDoesNotExistException when mesh does not exist
     */
    void Publish(DBusPath _elementPath, UInt16 _model, Map<String, Variant<?>> _options, byte[] _data) throws BluezMeshInvalidArgumentsException, BluezMeshDoesNotExistException;

}
