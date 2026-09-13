/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.messages.protocol.twa.twa_command_factory;

import be.tpvision.smartcontrol.messages.Messages;

public class GetDeviceAddressCommandMessages {
    public static final String DEVICE_ADDRESS_WRAPPER_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Device address wrapper");
    public static final String DEVICE_ADDRESS_WRAPPER_CODEC_CAN_NOT_BE_NULL = Messages.getCodecCanNotBeNullMessage("Device address wrapper");
    public static final String PROTOCOL_DEVICE_ADDRESS_WRAPPER_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Protocol device address wrapper");

    private GetDeviceAddressCommandMessages() {
    }
}

