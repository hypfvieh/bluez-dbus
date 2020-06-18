package org.bluez;

import java.util.Map;

import org.bluez.exceptions.BluezAlreadyExistsException;
import org.bluez.exceptions.BluezDoesNotExistException;
import org.bluez.exceptions.BluezInvalidArgumentsException;
import org.freedesktop.dbus.DBusPath;
import org.freedesktop.dbus.interfaces.DBusInterface;
import org.freedesktop.dbus.types.Variant;

/**
 * File generated - 2020-06-18.<br>
 * Based on bluez Documentation: profile-api.txt.<br>
 * <br>
 * <b>Service:</b> org.bluez<br>
 * <b>Interface:</b> org.bluez.ProfileManager1<br>
 * <br>
 * <b>Object path:</b><br>
 *             /org/bluez<br>
 * <br>
 */
public interface ProfileManager1 extends DBusInterface {

    /**
     * <b>From bluez documentation:</b><br>
     * <br>
     * This registers a profile implementation.<br>
     * <br>
     * If an application disconnects from the bus all<br>
     * its registered profiles will be removed.<br>
     * <br>
     * Some predefined services:<br>
     * <br>
     * HFP AG UUID: 0000111f-0000-1000-8000-00805f9b34fb<br>
     * <br>
     * 	Default profile Version is 1.7, profile Features<br>
     * 	is 0b001001 and RFCOMM channel is 13.<br>
     * 	Authentication is required.<br>
     * <br>
     * HFP HS UUID: 0000111e-0000-1000-8000-00805f9b34fb<br>
     * <br>
     * 	Default profile Version is 1.7, profile Features<br>
     * 	is 0b000000 and RFCOMM channel is 7.<br>
     * 	Authentication is required.<br>
     * <br>
     * HSP AG UUID: 00001112-0000-1000-8000-00805f9b34fb<br>
     * <br>
     * 	Default profile Version is 1.2, RFCOMM channel<br>
     * 	is 12 and Authentication is required. Does not<br>
     * 	support any Features, option is ignored.<br>
     * <br>
     * HSP HS UUID: 00001108-0000-1000-8000-00805f9b34fb<br>
     * <br>
     * 	Default profile Version is 1.2, profile Features<br>
     * 	is 0b0 and RFCOMM channel is 6. Authentication<br>
     * 	is required. Features is one bit value, specify<br>
     * 	capability of Remote Audio Volume Control<br>
     * 	(by default turned off).<br>
     * <br>
     * Available options:<br>
     * <br>
     * 	string Name<br>
     * <br>
     * 		Human readable name for the profile<br>
     * <br>
     * 	string Service<br>
     * <br>
     * 		The primary service class UUID<br>
     * 		(if different from the actual<br>
     * 		 profile UUID)<br>
     * <br>
     * 	string Role<br>
     * <br>
     * 		For asymmetric profiles that do not<br>
     * 		have UUIDs available to uniquely<br>
     * 		identify each side this<br>
     * 		parameter allows specifying the<br>
     * 		precise local role.<br>
     * <br>
     * 		Possible values: "client", "server"<br>
     * <br>
     * 	uint16 Channel<br>
     * <br>
     * 		RFCOMM channel number that is used<br>
     * 		for client and server UUIDs.<br>
     * <br>
     * 		If applicable it will be used in the<br>
     * 		SDP record as well.<br>
     * <br>
     * 	uint16 PSM<br>
     * <br>
     * 		PSM number that is used for client<br>
     * 		and server UUIDs.<br>
     * <br>
     * 		If applicable it will be used in the<br>
     * 		SDP record as well.<br>
     * <br>
     * 	boolean RequireAuthentication<br>
     * <br>
     * 		Pairing is required before connections<br>
     * 		will be established. No devices will<br>
     * 		be connected if not paired.<br>
     * <br>
     * 	boolean RequireAuthorization<br>
     * <br>
     * 		Request authorization before any<br>
     * 		connection will be established.<br>
     * <br>
     * 	boolean AutoConnect<br>
     * <br>
     * 		In case of a client UUID this will<br>
     * 		force connection of the RFCOMM or<br>
     * 		L2CAP channels when a remote device<br>
     * 		is connected.<br>
     * <br>
     * 	string ServiceRecord<br>
     * <br>
     * 		Provide a manual SDP record.<br>
     * <br>
     * 	uint16 Version<br>
     * <br>
     * 		Profile version (for SDP record)<br>
     * <br>
     * 	uint16 Features<br>
     * <br>
     * 		Profile features (for SDP record)<br>
     * <br>
     * 
     * @param _profile profile
     * @param _uuid uuid
     * @param _options options
     * 
     * @throws BluezInvalidArgumentsException when argument is invalid
     * @throws BluezAlreadyExistsException when item already exists
     */
    void RegisterProfile(DBusPath _profile, String _uuid, Map<String, Variant<?>> _options) throws BluezInvalidArgumentsException, BluezAlreadyExistsException;

    /**
     * <b>From bluez documentation:</b><br>
     * <br>
     * This unregisters the profile that has been previously<br>
     * registered. The object path parameter must match the<br>
     * same value that has been used on registration.<br>
     * <br>
     * 
     * @param _profile profile
     * 
     * @throws BluezDoesNotExistException when item does not exist
     */
    void UnregisterProfile(DBusPath _profile) throws BluezDoesNotExistException;

}
