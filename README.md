# bluez-dbus [![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.hypfvieh/bluez-dbus/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.hypfvieh/bluez-dbus)
bluetooth library for linux OSes using [DBus](https://dbus.freedesktop.org/) and [bluez](http://www.bluez.org/).

This project was inspired by [tinyb](https://github.com/intel-iot-devkit/tinyb),
but does not require any wrapper library as it is based on a newer version of dbus-java which uses jnr-unixsocket.

This library has been tested with Ubuntu 16.04.4 (AMD64) and bluez library 5.54.

Starting with version 0.1.0 of this library Java 8 is required (previous version used Java 7).

If you want to use filedescriptor passing in any bluez method, you have to add [Robert Middleton's dbus-java-nativefd](https://github.com/rm5248/dbus-java-nativefd) library to your project:

```
<dependency>
    <groupId>com.rm5248</groupId>
    <artifactId>dbus-java-nativefd</artifactId>
    <version>1.0</version>
</dependency>
```

##### To build a newer bluez-library for Ubuntu (16.04 has an older version than 5.50):
-------------
1. Download new bluez library from http://www.bluez.org/download/
2. Install Ubuntu build essentials:  
  &nbsp;&nbsp;&nbsp;&nbsp;sudo apt-get install build-essential
3. Install required additional dependencies:  
&nbsp;&nbsp;&nbsp;&nbsp;`sudo apt-get install libdbus-1-dev libudev-dev libical-dev libreadline-dev checkinstall libglib2.0-dev`
4. Extract the downloaded bluez-tarball:  
&nbsp;&nbsp;&nbsp;&nbsp;`tar xfvJ bluez-5.50.tar.xz`
5. Run ./configure in the extracted bluez tarball:  
   &nbsp;&nbsp;&nbsp;&nbsp;`./configure --prefix=/usr --libexecdir=/usr/lib --enable-manpages`
6. run checkinstall in bluez tarball directory: `sudo checkinstall`
7. Answer 'y' to question if default docs should be created
8. Enter a description (e.g. `New bluez library`), press enter and then CTRL+D
9. In checkinstall:  
    &nbsp;&nbsp;Go to Menu option 13 (Replaces)  
    &nbsp;&nbsp;Enter: `bluez-obexd, bluez-cups, bluez-hcidump, bluez-btsco, bluez-tools`
10. Press Enter to start the build
11. Install the generated .deb files:  
  &nbsp;&nbsp;&nbsp;&nbsp;`sudo dpkg -i bluez_5.50-1_amd64.deb`

# Changelog:

#### Version 0.1.4 (not yet released):
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
