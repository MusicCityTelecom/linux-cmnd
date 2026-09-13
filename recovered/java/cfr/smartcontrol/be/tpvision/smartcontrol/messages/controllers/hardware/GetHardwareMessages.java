/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.messages.controllers.hardware;

import be.tpvision.smartcontrol.messages.Messages;

public class GetHardwareMessages {
    public static final String HARDWARE_KEY_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Hardware key");

    private GetHardwareMessages() {
    }

    public static String getHardwareNotFoundMessage(String hardwareKey) {
        return String.format("Hardware with hardwareKey %s not found.", hardwareKey);
    }
}

