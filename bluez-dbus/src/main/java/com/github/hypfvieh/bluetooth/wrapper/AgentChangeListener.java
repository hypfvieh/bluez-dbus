package com.github.hypfvieh.bluetooth.wrapper;

/*-
 * #%L
 * devicebridge-module-bt
 * %%
 * Copyright (C) 2021 Tincore
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

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
