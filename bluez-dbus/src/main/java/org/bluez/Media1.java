package org.bluez;

import java.util.Map;

import org.bluez.exceptions.BluezAlreadyExistsException;
import org.bluez.exceptions.BluezDoesNotExistException;
import org.bluez.exceptions.BluezInvalidArgumentsException;
import org.bluez.exceptions.BluezNotSupportedException;
import org.freedesktop.dbus.DBusPath;
import org.freedesktop.dbus.interfaces.DBusInterface;
import org.freedesktop.dbus.types.Variant;

/**
 * File generated - 2020-06-18.<br>
 * Based on bluez Documentation: media-api.txt.<br>
 * <br>
 * <b>Service:</b> org.bluez<br>
 * <b>Interface:</b> org.bluez.Media1<br>
 * <br>
 * <b>Object path:</b><br>
 *             [variable prefix]/{hci0,hci1,...}<br>
 * <br>
 */
public interface Media1 extends DBusInterface {

    /**
     * <b>From bluez documentation:</b><br>
     * <br>
     * Register a local end point to sender, the sender can<br>
     * register as many end points as it likes.<br>
     * <br>
     * Note: If the sender disconnects the end points are<br>
     * automatically unregistered.<br>
     * <br>
     * possible properties:<br>
     * <br>
     * 	string UUID:<br>
     * <br>
     * 		UUID of the profile which the endpoint<br>
     * 		is for.<br>
     * <br>
     * 	byte Codec:<br>
     * <br>
     * 		Assigned number of codec that the<br>
     * 		endpoint implements. The values should<br>
     * 		match the profile specification which<br>
     * 		is indicated by the UUID.<br>
     * <br>
     * 	array{byte} Capabilities:<br>
     * <br>
     * 		Capabilities blob, it is used as it is<br>
     * 		so the size and byte order must match.<br>
     * <br>
     * 
     * @param _endpoint endpoint
     * @param _properties properties
     * 
     * @throws BluezInvalidArgumentsException when argument is invalid
     */
    void RegisterEndpoint(DBusPath _endpoint, Map<String, Variant<?>> _properties) throws BluezInvalidArgumentsException;

    /**
     * <b>From bluez documentation:</b><br>
     * <br>
     * Unregister sender end point.<br>
     * <br>
     * 
     * @param _endpoint endpoint
     */
    void UnregisterEndpoint(DBusPath _endpoint);

    /**
     * <b>From bluez documentation:</b><br>
     * <br>
     * Register a media player object to sender, the sender<br>
     * can register as many objects as it likes.<br>
     * <br>
     * Object must implement at least<br>
     * org.mpris.MediaPlayer2.Player as defined in MPRIS 2.2<br>
     * spec:<br>
     * <br>
     * http://specifications.freedesktop.org/mpris-spec/latest/<br>
     * <br>
     * Note: If the sender disconnects its objects are<br>
     * automatically unregistered.<br>
     * <br>
     * 
     * @param _player player
     * @param _properties properties
     * 
     * @throws BluezInvalidArgumentsException when argument is invalid
     * @throws BluezNotSupportedException when operation not supported
     */
    void RegisterPlayer(DBusPath _player, Map<String, Variant<?>> _properties) throws BluezInvalidArgumentsException, BluezNotSupportedException;

    /**
     * <b>From bluez documentation:</b><br>
     * <br>
     * Unregister sender media player.<br>
     * <br>
     * 
     * @param _player player
     */
    void UnregisterPlayer(DBusPath _player);

    /**
     * <b>From bluez documentation:</b><br>
     * <br>
     * Register endpoints an player objects within root<br>
     * object which must implement ObjectManager.<br>
     * <br>
     * The application object path together with the D-Bus<br>
     * system bus connection ID define the identification of<br>
     * the application.<br>
     * <br>
     * 
     * @param _root root
     * @param _options options
     * 
     * @throws BluezInvalidArgumentsException when argument is invalid
     * @throws BluezAlreadyExistsException when item already exists
     */
    void RegisterApplication(DBusPath _root, Map<String, Variant<?>> _options) throws BluezInvalidArgumentsException, BluezAlreadyExistsException;

    /**
     * <b>From bluez documentation:</b><br>
     * <br>
     * This unregisters the services that has been<br>
     * previously registered. The object path parameter<br>
     * must match the same value that has been used<br>
     * on registration.<br>
     * <br>
     * 
     * @param _application application
     * 
     * @throws BluezInvalidArgumentsException when argument is invalid
     * @throws BluezDoesNotExistException when item does not exist
     */
    void UnregisterApplication(DBusPath _application) throws BluezInvalidArgumentsException, BluezDoesNotExistException;

}
