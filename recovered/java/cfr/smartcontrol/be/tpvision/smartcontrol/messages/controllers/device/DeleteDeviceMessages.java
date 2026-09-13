/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.messages.controllers.device;

import be.tpvision.smartcontrol.messages.Messages;

public class DeleteDeviceMessages {
    public static final String DEVICE_SERVICE_CAN_NOT_BE_NULL = Messages.getServiceCanNotBeNullMessage("Device");
    public static final String CONTENT_MANAGEMENT_SERVICE_CAN_NOT_BE_NULL = Messages.getServiceCanNotBeNullMessage("Content management");
    public static final String HARDWARE_SERVICE_CAN_NOT_BE_NULL = Messages.getServiceCanNotBeNullMessage("Hardware");
    public static final String HARDWARE_KEY_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Hardware key");

    private DeleteDeviceMessages() {
    }
}

