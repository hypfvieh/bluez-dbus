package org.bluez;

import org.freedesktop.dbus.DBusPath;
import org.freedesktop.dbus.interfaces.DBusInterface;
import org.freedesktop.dbus.types.Variant;

import java.util.Map;

/**
 * File generated - 2023-02-20.<br>
 * Based on bluez Documentation: media-api.txt.<br>
 * <br>
 * <b>Service:</b> unique name (Server role)<br>
 * <b>Interface:</b> org.bluez.MediaEndpoint1<br>
 * <br>
 * <b>Object path:</b><br>
 *             freely definable (Server role)<br>
 *             [variable prefix]/{hci0,hci1,...}/dev_XX_XX_XX_XX_XX_XX/sepX<br>
 *             (Client role) <br>
 * <br>
 * <b>Supported properties:</b> <br>
 * <br>
 * 		string UUID [readonly, optional]:<br>
 * <br>
 * 			UUID of the profile which the endpoint is for.<br>
 * <br>
 * 		byte Codec [readonly, optional]:<br>
 * <br>
 * 			Assigned number of codec that the endpoint implements.<br>
 * 			The values should match the profile specification which<br>
 * 			is indicated by the UUID.<br>
 * <br>
 * 		array{byte} Capabilities [readonly, optional]:<br>
 * <br>
 * 			Capabilities blob, it is used as it is so the size and<br>
 * 			byte order must match.<br>
 * <br>
 * 		object Device [readonly, optional]:<br>
 * <br>
 * 			Device object which the endpoint is belongs to.<br>
 * <br>
 * 		bool DelayReporting [readonly, optional]:<br>
 * <br>
 * 			Indicates if endpoint supports Delay Reporting.<br>
 * <br>
 * 		byte Framing [ISO only]<br>
 * <br>
 * 			Indicates endpoint support framing.<br>
 * <br>
 * 		byte PHY [ISO only]<br>
 * <br>
 * 			Indicates endpoint supported PHY.<br>
 * <br>
 * 		uint16_t MaximumLatency [ISO only]<br>
 * <br>
 * 			Indicates endpoint maximum latency.<br>
 * <br>
 * 		uint32_t MinimumDelay [ISO only]<br>
 * <br>
 * 			Indicates endpoint minimum presentation delay.<br>
 * <br>
 * 		uint32_t MaximumDelay [ISO only]<br>
 * <br>
 * 			Indicates endpoint maximum presentation delay.<br>
 * <br>
 * 		uint32_t PreferredMinimumDelay [ISO only]<br>
 * <br>
 * 			Indicates endpoint preferred minimum presentation delay.<br>
 * <br>
 * 		uint32_t PreferredMinimumDelay [ISO only]<br>
 * <br>
 * 			Indicates endpoint preferred minimum presentation delay.<br>
 * <br>
 * 		uint32 Location [ISO only]<br>
 * <br>
 * 			Indicates endpoint supported locations.<br>
 * <br>
 * 		uint16 SupportedContext [ISO only]<br>
 * <br>
 * 			Indicates endpoint supported audio context.<br>
 * <br>
 * 		uint16 Context [ISO only]<br>
 * <br>
 * 			Indicates endpoint available audio context.<br>
 * <br>
 * <br>
 */
public interface MediaEndpoint1 extends DBusInterface {

    /**
     * <b>From bluez documentation:</b><br>
     * <br>
     * Set configuration for the transport.<br>
     * <br>
     * For client role transport must be set with a server<br>
     * endpoint oject which will be configured and the<br>
     * properties must contain the following properties:<br>
     * <br>
     * 	array{byte} Capabilities [Mandatory]<br>
     * 	array{byte} Metadata [ISO only]<br>
     * 	byte CIG [ISO only]<br>
     * 	byte CIS [ISO only]<br>
     * 	uint32 Interval [ISO only]<br>
     * 	bool Framing [ISO only]<br>
     * 	string PHY [ISO only]<br>
     * 	uint16 SDU [ISO only]<br>
     * 	byte Retransmissions [ISO only]<br>
     * 	uint16 Latency [ISO only]<br>
     * 	uint32 Delay [ISO only]<br>
     * 	uint8 TargetLatency [ISO Latency]<br>
     * <br>
     * 
     * @param _transport transport
     * @param _properties properties
     */
    void SetConfiguration(DBusPath _transport, Map<String, Variant<?>> _properties);

    /**
     * <b>From bluez documentation:</b><br>
     * <br>
     * Select preferable configuration from the supported<br>
     * capabilities.<br>
     * <br>
     * Returns a configuration which can be used to setup<br>
     * a transport.<br>
     * <br>
     * Note: There is no need to cache the selected<br>
     * configuration since on success the configuration is<br>
     * send back as parameter of SetConfiguration.<br>
     * <br>
     * 
     * @param _capabilities capabilities
     * 
     * @return byte[] - maybe null
     */
    byte[] SelectConfiguration(byte[] _capabilities);

    /**
     * <b>From bluez documentation:</b><br>
     * <br>
     * Select preferable properties from the supported<br>
     * properties:<br>
     * 	object Endpoint [ISO only]<br>
     * 	Refer to SetConfiguration for the list of<br>
     * 		other possible properties.<br>
     * <br>
     * Returns propeties which can be used to setup<br>
     * a transport.<br>
     * <br>
     * Note: There is no need to cache the selected<br>
     * properties since on success the configuration is<br>
     * send back as parameter of SetConfiguration.<br>
     * <br>
     * 
     * @param _properties properties
     * 
     * @return Map&lt;String, Variant&lt;?&gt;&gt; - maybe null
     */
    Map<String, Variant<?>> SelectProperties(Map<String, Variant<?>> _properties);

    /**
     * <b>From bluez documentation:</b><br>
     * <br>
     * Clear transport configuration.<br>
     * <br>
     * 
     * @param _transport transport
     */
    void ClearConfiguration(DBusPath _transport);

    /**
     * <b>From bluez documentation:</b><br>
     * <br>
     * This method gets called when the service daemon<br>
     * unregisters the endpoint. An endpoint can use it to do<br>
     * cleanup tasks. There is no need to unregister the<br>
     * endpoint, because when this method gets called it has<br>
     * already been unregistered.<br>
     * <br>
     */
    void Release();

}
