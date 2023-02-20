package org.bluez;

import org.freedesktop.dbus.interfaces.DBusInterface;

/**
 * File generated - 2023-02-20.<br>
 * Based on bluez Documentation: advertising-api.txt.<br>
 * <br>
 * <b>Service:</b> org.bluez<br>
 * <b>Interface:</b> org.bluez.LEAdvertisement1<br>
 * <br>
 * <b>Object path:</b><br>
 *             freely definable<br>
 * <br>
 * <b>Supported properties:</b> <br>
 * <br>
 * 		string Type<br>
 * <br>
 * 			Determines the type of advertising packet requested.<br>
 * <br>
 * 			Possible values: "broadcast" or "peripheral"<br>
 * <br>
 * 		array{string} ServiceUUIDs<br>
 * <br>
 * 			List of UUIDs to include in the "Service UUID" field of<br>
 * 			the Advertising Data.<br>
 * <br>
 * 		dict ManufacturerData<br>
 * <br>
 * 			Manufactuer Data fields to include in<br>
 * 			the Advertising Data.  Keys are the Manufacturer ID<br>
 * 			to associate with the data.<br>
 * <br>
 * 		array{string} SolicitUUIDs<br>
 * <br>
 * 			Array of UUIDs to include in "Service Solicitation"<br>
 * 			Advertisement Data.<br>
 * <br>
 * 		dict ServiceData<br>
 * <br>
 * 			Service Data elements to include. The keys are the<br>
 * 			UUID to associate with the data.<br>
 * <br>
 * 		dict Data [Experimental]<br>
 * <br>
 * 			Advertising Type to include in the Advertising<br>
 * 			Data. Key is the advertising type and value is the<br>
 * 			data as byte array.<br>
 * <br>
 * 			Note: Types already handled by other properties shall<br>
 * 			not be used.<br>
 * <br>
 * 			Possible values:<br>
 * 				&lt;type&gt; &lt;byte array&gt;<br>
 * 				...<br>
 * <br>
 * 			Example:<br>
 * 				&lt;Transport Discovery&gt; &lt;Organization Flags...&gt;<br>
 * 				0x26                   0x01         0x01...<br>
 * <br>
 * 		bool Discoverable [Experimental]<br>
 * <br>
 * 			Advertise as general discoverable. When present this<br>
 * 			will override adapter Discoverable property.<br>
 * <br>
 * 			Note: This property shall not be set when Type is set<br>
 * 			to broadcast.<br>
 * <br>
 * 		uint16 DiscoverableTimeout [Experimental]<br>
 * <br>
 * 			The discoverable timeout in seconds. A value of zero<br>
 * 			means that the timeout is disabled and it will stay in<br>
 * 			discoverable/limited mode forever.<br>
 * <br>
 * 			Note: This property shall not be set when Type is set<br>
 * 			to broadcast.<br>
 * <br>
 * 		array{string} Includes<br>
 * <br>
 * 			List of features to be included in the advertising<br>
 * 			packet.<br>
 * <br>
 * 			Possible values: as found on<br>
 * 					LEAdvertisingManager.SupportedIncludes<br>
 * <br>
 * 		string LocalName<br>
 * <br>
 * 			Local name to be used in the advertising report. If the<br>
 * 			string is too big to fit into the packet it will be<br>
 * 			truncated.<br>
 * <br>
 * 			If this property is available 'local-name' cannot be<br>
 * 			present in the Includes.<br>
 * <br>
 * 		uint16 Appearance<br>
 * <br>
 * 			Appearance to be used in the advertising report.<br>
 * <br>
 * 			Possible values: as found on GAP Service.<br>
 * <br>
 * 		uint16_t Duration<br>
 * <br>
 * 			Rotation duration of the advertisement in seconds. If<br>
 * 			there are other applications advertising no duration is<br>
 * 			set the default is 2 seconds.<br>
 * <br>
 * 		uint16_t Timeout<br>
 * <br>
 * 			Timeout of the advertisement in seconds. This defines<br>
 * 			the lifetime of the advertisement.<br>
 * <br>
 * 		string SecondaryChannel [Experimental]<br>
 * <br>
 * 			Secondary channel to be used. Primary channel is<br>
 * 			always set to "1M" except when "Coded" is set.<br>
 * <br>
 * 			Possible value: "1M" (default)<br>
 * 					"2M"<br>
 * 					"Coded"<br>
 * <br>
 * 		uint32 MinInterval [Experimental]<br>
 * <br>
 * 			Minimum advertising interval to be used by the<br>
 * 			advertising set, in milliseconds. Acceptable values<br>
 * 			are in the range [20ms, 10,485s]. If the provided<br>
 * 			MinInterval is larger than the provided MaxInterval,<br>
 * 			the registration will return failure.<br>
 * <br>
 * 		uint32 MaxInterval [Experimental]<br>
 * <br>
 * 			Maximum advertising interval to be used by the<br>
 * 			advertising set, in milliseconds. Acceptable values<br>
 * 			are in the range [20ms, 10,485s]. If the provided<br>
 * 			MinInterval is larger than the provided MaxInterval,<br>
 * 			the registration will return failure.<br>
 * <br>
 * 		int16 TxPower [Experimental]<br>
 * <br>
 * 			Requested transmission power of this advertising set.<br>
 * 			The provided value is used only if the "CanSetTxPower"<br>
 * 			feature is enabled on the Advertising Manager. The<br>
 * 			provided value must be in range [-127 to +20], where<br>
 * 			units are in dBm.<br>
 * <br>
 * <br>
 * <br>
 */
public interface LEAdvertisement1 extends DBusInterface {

    /**
     * <b>From bluez documentation:</b><br>
     * <br>
     * This method gets called when the service daemon<br>
     * removes the Advertisement. A client can use it to do<br>
     * cleanup tasks. There is no need to call<br>
     * UnregisterAdvertisement because when this method gets<br>
     * called it has already been unregistered.<br>
     * <br>
     */
    void Release();

}
