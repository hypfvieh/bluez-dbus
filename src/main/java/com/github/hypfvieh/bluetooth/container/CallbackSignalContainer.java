package com.github.hypfvieh.bluetooth.container;

import org.freedesktop.dbus.handlers.AbstractInterfacesAddedHandler;
import org.freedesktop.dbus.handlers.AbstractInterfacesRemovedHandler;

public class CallbackSignalContainer {

    private final AbstractInterfacesAddedHandler addedHandler;
    private final AbstractInterfacesRemovedHandler removedHandler;
    
    public CallbackSignalContainer(AbstractInterfacesAddedHandler _addedHandler,
            AbstractInterfacesRemovedHandler _removedHandler) {
    
        addedHandler = _addedHandler;
        removedHandler = _removedHandler;
    }

    public AbstractInterfacesAddedHandler getAddedHandler() {
        return addedHandler;
    }

    public AbstractInterfacesRemovedHandler getRemovedHandler() {
        return removedHandler;
    }
    
}
