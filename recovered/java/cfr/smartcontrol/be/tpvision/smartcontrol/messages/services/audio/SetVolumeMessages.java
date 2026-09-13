/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.messages.services.audio;

import be.tpvision.smartcontrol.messages.Messages;

public class SetVolumeMessages {
    public static final String DEVICE_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Device");
    public static final String VOLUME_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Volume");
    public static final String VOLUME_UP_DOWN_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Volume up / down");

    private SetVolumeMessages() {
    }
}

