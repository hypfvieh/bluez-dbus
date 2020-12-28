package org.bluez.mesh;

import org.freedesktop.dbus.interfaces.DBusInterface;
import org.freedesktop.dbus.types.UInt32;

/**
 * File generated - 2020-12-28.<br>
 * Based on bluez Documentation: mesh-api.txt.<br>
 * <br>
 * <b>Service:</b> unique name<br>
 * <b>Interface:</b> org.bluez.mesh.ProvisionAgent1<br>
 * <br>
 * <b>Object path:</b><br>
 *             freely definable<br>
 * <br>
 * <b>Supported properties:</b> <br>
 * array{string} Capabilities [read-only]<br>
 * An array of strings with the following allowed values:<br>
 * <pre>
 *      "blink"
 *      "beep"
 *      "vibrate"
 *      "out-numeric"
 *      "out-alpha"
 *      "push"
 *      "twist"
 *      "in-numeric"
 *      "in-alpha"
 *      "static-oob"
 *      "public-oob"
 * </pre>
 * array{string} OutOfBandInfo [read-only, optional]<br>
 * Indicates availability of OOB data. An array of strings with the<br>
 *       following allowed values:<br>
 * <pre>
 *       "other"
 *       "uri"
 *       "machine-code-2d"
 *       "bar-code"
 *       "nfc"
 *       "number"
 *       "string"
 *       "on-box"
 *       "in-box"
 *       "on-paper",
 *       "in-manual"
 *       "on-device"
 * </pre>
 *  string URI [read-only, optional]<br>
 *      Uniform Resource Identifier points to out-of-band (OOB)<br>
 *      information (e.g., a public key)<br>
 *
 */
public interface ProvisionAgent1 extends DBusInterface {

    /**
     * <b>From bluez documentation:</b><br>
     * <br>
     * This method is called during provisioning if the Provisioner<br>
     * has requested Out-Of-Band ECC key exchange. The Private key is<br>
     * returned to the Daemon, and the Public Key is delivered to the<br>
     * remote Provisioner using a method that does not involve the<br>
     * Bluetooth Mesh system. The Private Key returned must be 32<br>
     * octets in size, or the Provisioning procedure will fail and be<br>
     * canceled.<br>
     * <br>
     * This function will only be called if the Provisioner has<br>
     * requested pre-determined keys to be exchanged Out-of-Band, and<br>
     * the local role is Unprovisioned device.
     *
     * @return byte[] - maybe null
     */
    byte[] PrivateKey();

    /**
     * <b>From bluez documentation:</b><br>
     * <br>
     * This method is called during provisioning if the local device is<br>
     * the Provisioner, and is requestng Out-Of-Band ECC key exchange.<br>
     * The Public key is returned to the Daemon that is the matched<br>
     * pair of the Private key of the remote device. The Public Key<br>
     * returned must be 64 octets in size, or the Provisioning<br>
     * procedure will fail and be canceled.<br>
     * <br>
     * This function will only be called if the Provisioner has<br>
     * requested pre-determined keys to be exchanged Out-of-Band, and<br>
     * the local role is Provisioner.
     *
     * @param _value value
     */
    byte[] PublicKey();

    /**
    /**
     * <b>From bluez documentation:</b><br>
     * <br>
     * This method is called when the Daemon has something important <br>
     * for the Agent to Display, but does not require any additional<br>
     * input locally. For instance: "Enter "ABCDE" on remote device".<br>
     *
     * @param _value
     */
    void DisplayString(String _value);

    /**
     * <b>From bluez documentation:</b><br>
     * <br>
     * This method is called when the Daemon has something important<br>
     * for the Agent to Display, but does not require any additional<br>
     * input locally. For instance: "Enter 14939264 on remote device".<br>
     * <br>
     * The type parameter indicates the display method. Allowed values<br>
     * are:
     * <pre>
     *     "blink" - Locally blink LED
     *     "beep" - Locally make a noise
     *     "vibrate" - Locally vibrate
     *     "out-numeric" - Display value to enter remotely
     *     "push" - Request pushes on remote button
     *     "twist" - Request twists on remote knob
     * </pre>
     * The number parameter is the specific value represented by the<br>
     * Prompt.
     *
     * @param _type type
     * @param _number number
     */
    void DisplayNumeric(String _type, UInt32 _number);

    /**
     * <b>From bluez documentation:</b><br>
     * <br>
     * This method is called when the Daemon requests the user to<br>
     * enter a decimal value between 1-99999999.<br>
     * <br>
     * The type parameter indicates the input method. Allowed values<br>
     * are:
     * <pre>
     *     "blink" - Enter times remote LED blinked
     *     "beep" - Enter times remote device beeped
     *     "vibrate" - Enter times remote device vibrated
     *     "in-numeric" - Enter remotely displayed value
     *     "push" - Push local button remotely requested times
     *     "twist" - Twist local knob remotely requested times
     * </pre>
     * This agent should prompt the user for specific input. For<br>
     * instance: "Enter value being displayed by remote device".
     *
     * @param _type
     * @return UInt32 - maybe null
     */
    UInt32 PromptNumeric(String _type);

    /**
     * <b>From bluez documentation:</b><br>
     * <br>
     * This method is called when the Daemon requires a 16 octet byte<br>
     * array, as an Out-of-Band authentication.<br>
     * <br>
     * The type parameter indicates the input method. Allowed values<br>
     * are:
     * <pre>
     *     "static-oob" - return 16 octet array
     *     "in-alpha" - return 16 octet alpha array
     * </pre>
     * The Static data returned must be 16 octets in size, or the<br>
     * Provisioning procedure will fail and be canceled. If input type<br>
     * is "in-alpha", the printable characters should be<br>
     * left-justified, with trailing 0x00 octets filling the remaining<br>
     * bytes.
     *
     * @param _type
     * @return
     */
    byte[] PromptStatic(String _type);

    /**
     * <b>From bluez documentation:</b><br>
     * <br>
     * This method gets called by the daemon to cancel any existing<br>
     * Agent Requests. When called, any pending user input should be<br>
     * canceled, and any display requests removed.
     */
    void Cancel();
}
