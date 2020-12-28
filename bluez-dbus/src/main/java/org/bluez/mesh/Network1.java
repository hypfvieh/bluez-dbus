package org.bluez.mesh;

import java.util.List;
import java.util.Map;

import org.bluez.datatypes.TwoTuple;
import org.bluez.exceptions.mesh.BluezMeshAlreadyExistsException;
import org.bluez.exceptions.mesh.BluezMeshBusyException;
import org.bluez.exceptions.mesh.BluezMeshFailedException;
import org.bluez.exceptions.mesh.BluezMeshInvalidArgumentsException;
import org.bluez.exceptions.mesh.BluezMeshNotFoundException;
import org.bluez.exceptions.mesh.BluezMeshNotSupportedException;
import org.freedesktop.dbus.DBusPath;
import org.freedesktop.dbus.interfaces.DBusInterface;
import org.freedesktop.dbus.types.UInt16;
import org.freedesktop.dbus.types.UInt32;
import org.freedesktop.dbus.types.UInt64;
import org.freedesktop.dbus.types.Variant;

/**
 * File generated - 2020-12-28.<br>
 * Based on bluez Documentation: mesh-api.txt.<br>
 * <br>
 * <b>Service:</b> org.bluez.mesh<br>
 * <b>Interface:</b> org.bluez.mesh.Network1<br>
 * <br>
 * <b>Object path:</b><br>
 *             /org/bluez/mesh<br>
 * <br>
 */
public interface Network1 extends DBusInterface {

    /**
     * <b>From bluez documentation:</b><br>
     * <br>
     *  This is the first method that an application has to call to
     *  become a provisioned node on a mesh network. The call will
     *  initiate broadcasting of Unprovisioned Device Beacon.
     *
     *  The app_root parameter is a D-Bus object root path of
     *  the application that implements org.bluez.mesh.Application1
     *  interface. The application represents a node where child mesh
     *  elements have their own objects that implement
     *  org.bluez.mesh.Element1 interface. The application hierarchy
     *  also contains a provision agent object that implements
     *  org.bluez.mesh.ProvisionAgent1 interface. The standard
     *  DBus.ObjectManager interface must be available on the
     *  app_root path.
     *
     *  The uuid parameter is a 16-byte array that contains Device UUID.
     *  This UUID must be unique (at least from the daemon perspective),
     *  therefore attempting to call this function using already
     *  registered UUID results in an error.
     *
     *  When provisioning finishes, the daemon will call either
     *  JoinComplete or JoinFailed method on object implementing
     *  org.bluez.mesh.Application1 interface.
     *
     * @param _appRoot app_root
     * @param _uuid uuid
     *
     * @throws BluezMeshInvalidArgumentsException when invalid argument given
     * @throws BluezMeshAlreadyExistsException when mesh already exists
     */
    void Join(DBusPath _appRoot, byte[] _uuid) throws BluezMeshInvalidArgumentsException, BluezMeshAlreadyExistsException;

    /**
     * <b>From bluez documentation:</b><br>
     * <br>
     * Cancels an outstanding provisioning request initiated by Join()
     * method.
     */
    void Cancel();

    /**
     * <b>From bluez documentation:</b><br>
     * <br>
     *
     *  This is the first method that an application must call to get<br>
     *  access to mesh node functionalities.<br>
     *  <br>
     *  The app_root parameter is a D-Bus object root path of<br>
     *  the application that implements org.bluez.mesh.Application1<br>
     *  interface. The application represents a node where child mesh<br>
     *  elements have their own objects that implement<br>
     *  org.bluez.mesh.Element1 interface. The standard<br>
     *  DBus.ObjectManager interface must be available on the<br>
     *  app_root path.<br>
     *  <br>
     *  The token parameter is a 64-bit number that has been assigned to<br>
     *  the application when it first got provisioned/joined mesh<br>
     *  network, i.e. upon receiving JoinComplete() method. The daemon<br>
     *  uses the token to verify whether the application is authorized<br>
     *  to assume the mesh node identity.<br>
     *  <br>
     *  In case of success, the method call returns mesh node object<br>
     *  (see Mesh Node Hierarchy section) and current configuration<br>
     *  settings. The return value of configuration parameter is an<br>
     *  array, where each entry is a structure that contains element<br>
     *  configuration. The element configuration structure is organized<br>
     *  as follows:
     *  <pre>
     *  byte
     *
     *      Element index, identifies the element to which this
     *      configuration entry pertains.
     *
     *  array{struct}
     *
     *      Models array where each entry is a structure with the
     *      following members:
     *
     *      uint16
     *
     *          Either a SIG Model Identifier or, if Vendor key
     *          is present in model configuration dictionary, a
     *          16-bit vendor-assigned Model Identifier
     *
     *      dict
     *
     *          A dictionary that contains model configuration
     *          with the following keys defined:
     *
     *          array{uint16} Bindings
     *
     *              Indices of application keys bound to the
     *              model
     *
     *          uint32 PublicationPeriod
     *
     *              Model publication period in milliseconds
     *
     *          uint16 Vendor
     *
     *              A 16-bit Company ID as defined by the
     *              Bluetooth SIG
     *
     *          array{variant} Subscriptions
     *
     *              Addresses the model is subscribed to.
     *
     *              Each address is provided either as
     *              uint16 for group addresses, or
     *              as array{byte} for virtual labels.
     *  </pre>
     *
     * @param _appRoot app_root
     * @param _token token
     *
     * @return TwoTuple or null
     *
     * @throws BluezMeshInvalidArgumentsException when invalid argument provided
     * @throws BluezMeshAlreadyExistsException when mesh already exists
     * @throws BluezMeshNotFoundExceptionwhen mesh not found
     * @throws BluezMeshBusyException when still busy
     * @throws BluezMeshFailedException when operation failed
     */
    TwoTuple<DBusPath, List<TwoTuple<Byte,List<TwoTuple<UInt16, Map<String, Variant<?>>>>>>>Attach(DBusPath _appRoot, UInt64 _token) throws BluezMeshInvalidArgumentsException, BluezMeshNotFoundException, BluezMeshAlreadyExistsException, BluezMeshBusyException, BluezMeshFailedException;

    /**
     * <b>From bluez documentation:</b><br>
     * <br>
     * This removes the configuration information about the mesh node
     * identified by the 64-bit token parameter. The token parameter
     * has been obtained as a result of successful Join() method call.
     *
     *
     * @throws BluezMeshInvalidArgumentsException when invalid argument provided
     * @throws BluezMeshNotFoundExceptionwhen mesh not found
     * @throws BluezMeshBusyException when still busy
     */
    void Leave(UInt64 _token) throws BluezMeshBusyException, BluezMeshNotFoundException, BluezMeshInvalidArgumentsException;


    /**
     * <b>From bluez documentation:</b><br>
     * <br>
     *  This is the first method that an application calls to become
     *  a Provisioner node, and a Configuration Client on a newly
     *  created Mesh Network.
     *
     *  The app_root parameter is a D-Bus object root path of the
     *  application that implements org.bluez.mesh.Application1
     *  interface, and a org.bluez.mesh.Provisioner1 interface. The
     *  application represents a node where child mesh elements have
     *  their own objects that implement org.bluez.mesh.Element1
     *  interface. The application hierarchy also contains a provision
     *  agent object that implements org.bluez.mesh.ProvisionAgent1
     *  interface. The standard DBus.ObjectManager interface must be
     *  available on the app_root path.
     *
     *  The uuid parameter is a 16-byte array that contains Device UUID.
     *  This UUID must be unique (at least from the daemon perspective),
     *  therefore attempting to call this function using already
     *  registered UUID results in an error.
     *
     *  The other information the bluetooth-meshd daemon will preserve
     *  about the initial node, is to give it the initial primary
     *  unicast address (0x0001), and create and assign a net_key as the
     *  primary network net_index (0x000).
     *
     *  Upon successful processing of Create() method, the daemon
     *  will call JoinComplete method on object implementing
     *  org.bluez.mesh.Application1.
     *
     *  @param _app_root app_root
     *  @param _uuid uuid
     *
     *  @throws BluezMeshInvalidArgumentsException on invalid arguments
     *  @throws BluezMeshAlreadyExistsException when mesh already exists
     */
    void CreateNetwork(DBusPath _app_root, byte[] _uuid) throws BluezMeshInvalidArgumentsException, BluezMeshAlreadyExistsException;


    /**
     * <b>From bluez documentation:</b><br>
     * <br>
     * This method creates a local mesh node based on node
     *  configuration that has been generated outside bluetooth-meshd.
     *
     *  The app_root parameter is a D-Bus object root path of the
     *  application that implements org.bluez.mesh.Application1
     *  interface.
     *
     *  The uuid parameter is a 16-byte array that contains Device UUID.
     *  This UUID must be unique (at least from the daemon perspective),
     *  therefore attempting to call this function using already
     *  registered UUID results in an error.
     *
     *  The dev_key parameter is the 16-byte value of the dev key of
     *  the imported mesh node.
     *
     *  Remaining parameters correspond to provisioning data:
     *
     *  The net_key and net_index parameters describe the network (or a
     *  subnet, if net_index is not 0) the imported mesh node belongs
     *  to.
     *
     *  The flags parameter is a dictionary containing provisioning
     *  flags. Supported values are:
     *
     *      boolean IvUpdate
     *
     *          When true, indicates that the network is in the
     *          middle of IV Index Update procedure.
     *
     *      boolean KeyRefresh
     *
     *          When true, indicates that the specified net key
     *          is in the middle of a key refresh procedure.
     *
     *  The iv_index parameter is the current IV Index value used by
     *  the network. This value is known by the provisioner.
     *
     *  The unicast parameter is the primary unicast address of the
     *  imported node.
     *
     *  Upon successful processing of Import() method, the daemon will
     *  call JoinComplete method on object implementing
     *  org.bluez.mesh.Application1 interface.
     *
     *  @param _appRoot
     *  @param _uuid
     *  @param _devKey
     *  @param _netKey
     *  @param _netIndex
     *  @param _flags
     *  @param _ivIndex
     *  @param _unicast
     *
     *  @throws BluezMeshAlreadyExistsException when mesh already exists
     *  @throws BluezMeshInvalidArgumentsException when invalid argument provided
     *  @throws BluezMeshNotFoundException when mesh could not be found
     *  @throws BluezMeshNotSupportedException when operation is not supported
     */
    void Import(DBusPath _appRoot, byte[] _uuid, byte[] _devKey, byte[] _netKey, UInt16 _netIndex, Map<String, Variant<?>> _flags, UInt32 _ivIndex, UInt16 _unicast) throws BluezMeshInvalidArgumentsException, BluezMeshAlreadyExistsException, BluezMeshNotSupportedException, BluezMeshNotFoundException;

}
