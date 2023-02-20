package org.bluez;

import org.bluez.exceptions.*;
import org.freedesktop.dbus.DBusPath;
import org.freedesktop.dbus.interfaces.DBusInterface;
import org.freedesktop.dbus.types.Variant;

import java.util.Map;

/**
 * File generated - 2023-02-20.<br>
 * Based on bluez Documentation: advertising-api.txt.<br>
 * <br>
 * <b>Service:</b> org.bluez<br>
 * <b>Interface:</b> org.bluez.LEAdvertisingManager1<br>
 * <br>
 * <b>Object path:</b><br>
 *             /org/bluez/{hci0,hci1,...}<br>
 * <br>
 * <b>Supported properties:</b> <br>
 * <br>
 * 		byte ActiveInstances<br>
 * <br>
 * 			Number of active advertising instances.<br>
 * <br>
 * 		byte SupportedInstances<br>
 * <br>
 * 			Number of available advertising instances.<br>
 * <br>
 * 		array{string} SupportedIncludes<br>
 * <br>
 * 			List of supported system includes.<br>
 * <br>
 * 			Possible values: "tx-power"<br>
 * 					 "appearance"<br>
 * 					 "local-name"<br>
 * <br>
 * 		array{string} SupportedSecondaryChannels [Experimental]<br>
 * <br>
 * 			List of supported Secondary channels. Secondary<br>
 * 			channels can be used to advertise with the<br>
 * 			corresponding PHY.<br>
 * <br>
 * 			Possible values: "1M"<br>
 * 					 "2M"<br>
 * 					 "Coded"<br>
 * <br>
 * 		dict SupportedCapabilities [Experimental]<br>
 * <br>
 * 			Enumerates Advertising-related controller capabilities<br>
 * 			useful to the client.<br>
 * <br>
 * 			Possible Values:<br>
 * <br>
 * 				byte MaxAdvLen<br>
 * <br>
 * 					Max advertising data length<br>
 * <br>
 * 				byte MaxScnRspLen<br>
 * <br>
 * 					Max advertising scan response length<br>
 * <br>
 * 				int16 MinTxPower<br>
 * <br>
 * 					Min advertising tx power (dBm)<br>
 * <br>
 * 				int16 MaxTxPower<br>
 * <br>
 * 					Max advertising tx power (dBm)<br>
 * <br>
 * 		array{string} SupportedFeatures [readonly,optional,Experimental]<br>
 * <br>
 * 			List of supported platform features. If no features<br>
 * 			are available on the platform, the SupportedFeatures<br>
 * 			array will be empty.<br>
 * <br>
 * 			Possible values: "CanSetTxPower"<br>
 * <br>
 * 						Indicates whether platform can<br>
 * 						specify tx power on each<br>
 * 						advertising instance.<br>
 * <br>
 * 					 "HardwareOffload"<br>
 * <br>
 * 						Indicates whether multiple<br>
 * 						advertising will be offloaded<br>
 * 						to the controller.<br>
 * <br>
 */
public interface LEAdvertisingManager1 extends DBusInterface {

    /**
     * <b>From bluez documentation:</b><br>
     * <br>
     * Registers an advertisement object to be sent over the LE<br>
     * Advertising channel.  The service must be exported<br>
     * under interface LEAdvertisement1.<br>
     * <br>
     * InvalidArguments error indicates that the object has<br>
     * invalid or conflicting properties.<br>
     * <br>
     * InvalidLength error indicates that the data<br>
     * provided generates a data packet which is too long.<br>
     * <br>
     * The properties of this object are parsed when it is<br>
     * registered, and any changes are ignored.<br>
     * <br>
     * If the same object is registered twice it will result in<br>
     * an AlreadyExists error.<br>
     * <br>
     * If the maximum number of advertisement instances is<br>
     * reached it will result in NotPermitted error.<br>
     * <br>
     * 
     * @param _advertisement advertisement
     * @param _options options
     * 
     * @throws BluezInvalidArgumentsException when argument is invalid
     * @throws BluezAlreadyExistsException when item already exists
     * @throws BluezInvalidLengthException on BluezInvalidLengthException
     * @throws BluezNotPermittedException on BluezNotPermittedException
     */
    void RegisterAdvertisement(DBusPath _advertisement, Map<String, Variant<?>> _options) throws BluezInvalidArgumentsException, BluezAlreadyExistsException, BluezInvalidLengthException, BluezNotPermittedException;

    /**
     * <b>From bluez documentation:</b><br>
     * <br>
     * This unregisters an advertisement that has been<br>
     * previously registered.  The object path parameter must<br>
     * match the same value that has been used on registration.<br>
     * <br>
     * 
     * @param _advertisement advertisement
     * 
     * @throws BluezInvalidArgumentsException when argument is invalid
     * @throws BluezDoesNotExistException when item does not exist
     */
    void UnregisterAdvertisement(DBusPath _advertisement) throws BluezInvalidArgumentsException, BluezDoesNotExistException;

}
