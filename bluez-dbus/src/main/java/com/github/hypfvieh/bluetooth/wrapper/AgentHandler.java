package com.github.hypfvieh.bluetooth.wrapper;

import org.bluez.Agent1;
import org.bluez.exceptions.BluezCanceledException;
import org.bluez.exceptions.BluezRejectedException;
import org.freedesktop.dbus.DBusPath;
import org.freedesktop.dbus.connections.impl.DBusConnection;
import org.freedesktop.dbus.interfaces.DBusInterface;
import org.freedesktop.dbus.types.UInt16;
import org.freedesktop.dbus.types.UInt32;

public final class AgentHandler extends AbstractBluetoothObject implements Agent1 {

    private final AgentChangeListener agentChangeListener;

    public AgentHandler(String _dbusPath, DBusConnection _dbusConnection, AgentChangeListener agentChangeListener) {
        super(BluetoothDeviceType.AGENT, _dbusConnection, _dbusPath);
        this.agentChangeListener = agentChangeListener;
    }

    @Override
    public void AuthorizeService(DBusPath _device, String _uuid) throws BluezRejectedException, BluezCanceledException {
        agentChangeListener.onAgentAuthorizeService(_device.getPath(), _uuid);
    }

    @Override
    public void Cancel() {
        agentChangeListener.onAgentCancel();
    }

    @Override
    public void DisplayPasskey(DBusPath _device, UInt32 _passkey, UInt16 _entered) {
        agentChangeListener.onAgentDisplayPassKey(_device.getPath(), _passkey.longValue(), _entered.intValue());
    }

    @Override
    public void DisplayPinCode(DBusPath _device, String _pincode) throws BluezRejectedException, BluezCanceledException {
        agentChangeListener.onAgentDisplayPinCode(_device.getPath(), _pincode);
    }

    @Override
    public void Release() {
        agentChangeListener.onAgentRelease();
    }

    @Override
    public void RequestAuthorization(DBusPath _device) throws BluezRejectedException, BluezCanceledException {
        agentChangeListener.onAgentRequestAuthorization(_device.getPath());
    }

    @Override
    public void RequestConfirmation(DBusPath _device, UInt32 _passkey) throws BluezRejectedException, BluezCanceledException {
        agentChangeListener.onAgentRequestConfirmation(_device.getPath(), _passkey.longValue());
    }

    @Override
    public UInt32 RequestPasskey(DBusPath _device) throws BluezRejectedException, BluezCanceledException {
        return new UInt32(agentChangeListener.onAgentRequestPassKey(_device.getPath()));
    }

    @Override
    public String RequestPinCode(DBusPath _device) throws BluezRejectedException, BluezCanceledException {
        return agentChangeListener.onAgentRequestPinCode(_device.getPath());
    }

    @Override
    protected Class<? extends DBusInterface> getInterfaceClass() {
        return Agent1.class;
    }

    public String getObjectPath() {
        return getDbusPath();
    }

    @Override
    public boolean isRemote() {
        return false;
    }
}
