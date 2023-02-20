package org.bluez.mesh;

import org.bluez.datatypes.TwoTuple;
import org.bluez.exceptions.mesh.*;
import org.freedesktop.dbus.DBusPath;
import org.freedesktop.dbus.interfaces.DBusInterface;
import org.freedesktop.dbus.types.*;

import java.util.List;
import java.util.Map;

/**
 * File generated - 2023-02-20.<br>
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
     * This is the first method that an application has to call to<br>
     * become a provisioned node on a mesh network. The call will<br>
     * initiate broadcasting of Unprovisioned Device Beacon.<br>
     * <br>
     * The app_root parameter is a D-Bus object root path of<br>
     * the application that implements org.bluez.mesh.Application1<br>
     * interface. The application represents a node where child mesh<br>
     * elements have their own objects that implement<br>
     * org.bluez.mesh.Element1 interface. The application hierarchy<br>
     * also contains a provision agent object that implements<br>
     * org.bluez.mesh.ProvisionAgent1 interface. The standard<br>
     * DBus.ObjectManager interface must be available on the<br>
     * app_root path.<br>
     * <br>
     * The uuid parameter is a 16-byte array that contains Device UUID.<br>
     * This UUID must be unique (at least from the daemon perspective),<br>
     * therefore attempting to call this function using already<br>
     * registered UUID results in an error. The composition of the UUID<br>
     * octets must be in compliance with RFC 4122.<br>
     * <br>
     * When provisioning finishes, the daemon will call either<br>
     * JoinComplete or JoinFailed method on object implementing<br>
     * org.bluez.mesh.Application1 interface.<br>
     * <br>
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
     * Cancels an outstanding provisioning request initiated by Join()<br>
     * method.<br>
     * <br>
     */
    void Cancel();

    /**
     * <b>From bluez documentation:</b><br>
     * <br>
     * This is the first method that an application must call to get<br>
     * access to mesh node functionalities.<br>
     * <br>
     * The app_root parameter is a D-Bus object root path of<br>
     * the application that implements org.bluez.mesh.Application1<br>
     * interface. The application represents a node where child mesh<br>
     * elements have their own objects that implement<br>
     * org.bluez.mesh.Element1 interface. The standard<br>
     * DBus.ObjectManager interface must be available on the<br>
     * app_root path.<br>
     * <br>
     * The token parameter is a 64-bit number that has been assigned to<br>
     * the application when it first got provisioned/joined mesh<br>
     * network, i.e. upon receiving JoinComplete() method. The daemon<br>
     * uses the token to verify whether the application is authorized<br>
     * to assume the mesh node identity.<br>
     * <br>
     * In case of success, the method call returns mesh node object<br>
     * (see Mesh Node Hierarchy section) and current configuration<br>
     * settings. The return value of configuration parameter is an<br>
     * array, where each entry is a structure that contains element<br>
     * configuration. The element configuration structure is organized<br>
     * as follows:<br>
     * <br>
     * byte<br>
     * <br>
     * 	Element index, identifies the element to which this<br>
     * 	configuration entry pertains.<br>
     * <br>
     * array{struct}<br>
     * <br>
     * 	Models array where each entry is a structure with the<br>
     * 	following members:<br>
     * <br>
     * 	uint16<br>
     * <br>
     * Either a SIG Model Identifier or, if Vendor key<br>
     * is present in model configuration dictionary, a<br>
     * 16-bit vendor-assigned Model Identifier<br>
     * <br>
     * 	dict<br>
     * <br>
     * A dictionary that contains model configuration<br>
     * with the following keys defined:<br>
     * <br>
     * array{uint16} Bindings<br>
     * <br>
     * 	Indices of application keys bound to the<br>
     * 	model<br>
     * <br>
     * uint32 PublicationPeriod<br>
     * <br>
     * 	Model publication period in milliseconds<br>
     * <br>
     * uint16 Vendor<br>
     * <br>
     * 	A 16-bit Company ID as defined by the<br>
     * 	Bluetooth SIG<br>
     * <br>
     * array{variant} Subscriptions<br>
     * <br>
     * 	Addresses the model is subscribed to.<br>
     * <br>
     * 	Each address is provided either as<br>
     * 	uint16 for group addresses, or<br>
     * 	as array{byte} for virtual labels.<br>
     * <br>
     * 
     * @param uint16 uint16
     * @param dict dict
     * 
     * @throws  on 
     * @throws BluezInvalidArgumentsException when argument is invalid
     * @throws BluezFailedException on failure
     */
    TwoTuple<DBusPath, List<TwoTuple<Byte,List<TwoTuple<UInt16, Map<String, Variant<?>>>>>>>Attach(DBusPath _appRoot, UInt64 _token) throws BluezMeshInvalidArgumentsException, BluezMeshNotFoundException, BluezMeshAlreadyExistsException, BluezMeshBusyException, BluezMeshFailedException;

    /**
     * <b>From bluez documentation:</b><br>
     * <br>
     * This removes the configuration information about the mesh node<br>
     * identified by the 64-bit token parameter. The token parameter<br>
     * has been obtained as a result of successful Join() method call.<br>
     * <br>
     * 
     * @param _token token
     *
     * @throws BluezMeshInvalidArgumentsException when invalid argument provided
     * @throws BluezMeshNotFoundException when mesh not found
     * @throws BluezMeshBusyException when still busy
     */
    void Leave(UInt64 _token) throws BluezMeshBusyException, BluezMeshNotFoundException, BluezMeshInvalidArgumentsException;


    /**
     * <b>From bluez documentation:</b><br>
     * <br>
     * This is the first method that an application calls to become<br>
     * a Provisioner node, and a Configuration Client on a newly<br>
     * created Mesh Network.<br>
     * <br>
     * The app_root parameter is a D-Bus object root path of the<br>
     * application that implements org.bluez.mesh.Application1<br>
     * interface, and a org.bluez.mesh.Provisioner1 interface. The<br>
     * application represents a node where child mesh elements have<br>
     * their own objects that implement org.bluez.mesh.Element1<br>
     * interface. The application hierarchy also contains a provision<br>
     * agent object that implements org.bluez.mesh.ProvisionAgent1<br>
     * interface. The standard DBus.ObjectManager interface must be<br>
     * available on the app_root path.<br>
     * <br>
     * The uuid parameter is a 16-byte array that contains Device UUID.<br>
     * This UUID must be unique (at least from the daemon perspective),<br>
     * therefore attempting to call this function using already<br>
     * registered UUID results in an error. The composition of the UUID<br>
     * octets must be in compliance with RFC 4122.<br>
     * <br>
     * The other information the bluetooth-meshd daemon will preserve<br>
     * about the initial node, is to give it the initial primary<br>
     * unicast address (0x0001), and create and assign a net_key as the<br>
     * primary network net_index (0x000).<br>
     * <br>
     * Upon successful processing of Create() method, the daemon<br>
     * will call JoinComplete method on object implementing<br>
     * org.bluez.mesh.Application1.<br>
     * <br>
     * 
     * @param _app_root app_root
     * @param _uuid uuid
     * 
     * @throws  on 
     * @throws BluezInvalidArgumentsException when argument is invalid
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
     *  @param _appRoot root
     *  @param _uuid uuid
     *  @param _devKey devkey
     *  @param _netKey netkey
     *  @param _netIndex netidx
     *  @param _flags flags
     *  @param _ivIndex ividx
     *  @param _unicast unicast
     *
     *  @throws BluezMeshAlreadyExistsException when mesh already exists
     *  @throws BluezMeshInvalidArgumentsException when invalid argument provided
     *  @throws BluezMeshNotFoundException when mesh could not be found
     *  @throws BluezMeshNotSupportedException when operation is not supported
     */
    void Import(DBusPath _appRoot, byte[] _uuid, byte[] _devKey, byte[] _netKey, UInt16 _netIndex, Map<String, Variant<?>> _flags, UInt32 _ivIndex, UInt16 _unicast) throws BluezMeshInvalidArgumentsException, BluezMeshAlreadyExistsException, BluezMeshNotSupportedException, BluezMeshNotFoundException;

}
