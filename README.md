# bluez-dbus [![Maven Central](https://img.shields.io/maven-central/v/com.github.hypfvieh/bluez-dbus?color=%2300AA00)](https://maven-badges.herokuapp.com/maven-central/com.github.hypfvieh/bluez-dbus)

This project was inspired by [tinyb](https://github.com/intel-iot-devkit/tinyb),
but does not require any wrapper library as it is based on a newer version of dbus-java which supports multiple unix socket implementations such as
the Java 16 native unixsocket implementation, jnr-unixsocket and junixsocket.

This library has been tested on Ubuntu 24.04 (AMD64) and bluez library 5.72.

## Compatibility
The different versions of bluez-dbus are using different versions of dbus-java which requires modern Java versions.
Here is a list of which bluez-dbus versions uses which dbus-java and the required Java version to use.

| bluez-java Version | dbus-java Version | minimum Java Version |
| ------------------ | ----------------- | -------------------- |
| 0.0.x              | 2.7.x             | Java 1.7             |
| 0.1.x              | 3.x               | Java 1.8             |
| 0.2.x              | 4.3.x             | Java 11              |
| 0.3.x              | 5.x               | Java 17              |

## Usage
When using the library directly, you have to add at least one `dbus-java` transport to your project as well.
There are different transports available. For usage with DBus you usually need a UnixSocket providing transport.

| Transport                             | Usage                                                                                      |
| ------------------------------------- | ------------------------------------------------------------------------------------------ | 
| dbus-java-transport-jnr-unixsocket    | Use this if you need filedescriptor support as well (see below)                            |
| dbus-java-transport-junixsocket       | Alternative if you don't want to use jnr (beta support in dbus-java 4.x), supports<br>filedescriptor without additional libraries. |
| dbus-java-transport-native-unixsocket | Use Java native implementation for UnixSockets. Needs at least Java 16, does not support<br>filedescriptor at all. |
| dbus-java-transport-tcp               | TCP implementation, only needed when your DBusDaemon is configured to use TCP as well<br>as/instead of UnixSockets. |

If you use the OSGi bundle, you don't have to add anything.
The OSGi bundle will use jnr-unixsocket transport as default in bluez-dbus version 0.2.x.
It will be changed to junixsocket-transport in bluez-dbus 0.3.x.

### filedescriptor support

If you want to use filedescriptor passing in any bluez method, you have to add [Robert Middleton's dbus-java-nativefd](https://github.com/rm5248/dbus-java-nativefd) library to your project if you are using jnr-unixsocket-transport.
The library can be found here:

```
<dependency>
    <groupId>com.rm5248</groupId>
    <artifactId>dbus-java-nativefd</artifactId>
    <version>2.0</version>
</dependency>
```

If you use the native-transport, you cannot use filedescriptor's because Java native implementation of UnixSockets only has a limited functionality.
For most cases this implementation should be fine. In some special cases like filedescriptors it isn't good enough.

Starting from bluez-java 0.3.x, dbus-java 5.x is used which has proper support for junixsocket. JUnixsocket is another implementation for UnixSockets like
jnr-unixsockets but with a richer feature set out of the box. It supports filedescriptors without the need of additional libraries.

# Changelog:

#### Version 0.3.3 (not released yet):
- Added `InterfaceRemoved` signal listener to clean up old `BluetoothDevice` proxy objects when a bluetooth device disconnects [#PR73](https://github.com/hypfvieh/bluez-dbus/pull/73), thanks to [Vlad Kolotov](https://github.com/vkolotov)

#### Version 0.3.2 (2025-04-20):
- Improved scanning/refreshing behavior of `DeviceManager` [#PR67](https://github.com/hypfvieh/bluez-dbus/pull/67), thanks to [joerg1985](https://github.com/joerg1985)
- Added new method `setLazyScan` to enable/disable re-querying of GATT services from already known devices when scanning

#### Version 0.3.1 (2025-01-20):
- Fixed issues with OSGi Bundle, [#64](https://github.com/hypfvieh/bluez-dbus/issues/64), [#PR65](https://github.com/hypfvieh/bluez-dbus/pull/65), thanks to [joerg1985](https://github.com/joerg1985)

#### Version 0.3.0 (2024-08-02):
- Requires Java 17
- Uses dbus-java 5.x

#### Version 0.2.0 (2023-07-21):
- Requires Java 11
- Uses dbus-java 4.x
- Updated interfaces to comply with bluez 5.64
- Issue [#48](https://github.com/hypfvieh/bluez-dbus/issues/48) allow unregistering of signal handlers
- Issue [#51](https://github.com/hypfvieh/bluez-dbus/issues/51) added mesh interface classes 

#### Version 0.1.3:
- Various bugfixes: [#35](https://github.com/hypfvieh/bluez-dbus/issues/35), [#36](https://github.com/hypfvieh/bluez-dbus/issues/36), [#38](https://github.com/hypfvieh/bluez-dbus/issues/38)
- Updated dependency versions

#### Version 0.1.2:
- Multi module maven project
- Provide a new artifact for usage with OSGi (bluez-java-osgi)
- Changed visibility of some methods to public
- Smaller bugfixes
- Updated interface classes to match with bluez 5.54 - Please note: mesh classes are still missing, PRs welcome
