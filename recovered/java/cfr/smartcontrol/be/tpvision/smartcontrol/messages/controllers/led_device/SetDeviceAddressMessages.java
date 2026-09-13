/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.messages.controllers.led_device;

import be.tpvision.smartcontrol.messages.Messages;

public class SetDeviceAddressMessages {
    public static final String IP_ADDRESS_CAN_NOT_BE_EMPTY = Messages.getCanNotBeEmptyMessage("IP address");
    public static final String DEVICE_ADDRESS_WRAPPER_VIEW_MODEL_CAN_NOT_BE_NULL = Messages.getViewModelCanNotBeNullMessage("Device address wrapper");
    public static final String DEVICE_ADDRESS_WRAPPER_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Device address wrapper");
    public static final String DEVICE_ADDRESS_COMMAND_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Device address command");

    private SetDeviceAddressMessages() {
    }
}

