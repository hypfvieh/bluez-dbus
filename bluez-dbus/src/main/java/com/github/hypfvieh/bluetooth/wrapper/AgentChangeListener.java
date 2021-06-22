package com.github.hypfvieh.bluetooth.wrapper;

public interface AgentChangeListener {
    void onAgentAuthorizeService(String path, String uuid);

    void onAgentCancel();

    void onAgentDisplayPassKey(String path, long passkey, int entered);

    void onAgentDisplayPinCode(String path, String pincode);

    void onAgentRelease();

    void onAgentRequestAuthorization(String path);

    void onAgentRequestConfirmation(String path, long passkey);

    long onAgentRequestPassKey(String path);

    String onAgentRequestPinCode(String path);
}
