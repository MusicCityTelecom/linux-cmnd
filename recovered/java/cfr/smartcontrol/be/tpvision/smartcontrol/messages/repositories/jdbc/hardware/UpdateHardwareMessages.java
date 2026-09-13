/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.messages.repositories.jdbc.hardware;

import be.tpvision.smartcontrol.messages.Messages;

public class UpdateHardwareMessages {
    public static final String HARDWARE_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Hardware");
    public static final String HARDWARE_KEY_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Hardware key");
    public static final String HARDWARE_KEY_CAN_NOT_BE_EMPTY = Messages.getCanNotBeEmptyMessage("Hardware key");

    private UpdateHardwareMessages() {
    }
}

