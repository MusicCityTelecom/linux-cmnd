/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.messages.protocol.twa.twa_command_factory;

import be.tpvision.smartcontrol.domain.twa.TwaDeviceSetting;
import be.tpvision.smartcontrol.messages.Messages;

public class GetTwaCommandMessages {
    public static final String DEVICE_ADDRESS_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Device address");
    public static final String MODULE_ADDRESS_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Module address");
    public static final String TWA_DEVICE_SETTING_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("TWA device setting");
    public static final String TWA_DEVICE_SETTING_CLASS_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("TWA device setting class");
    public static final String COMMAND_CODE_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Command code");
    public static final String CODEC_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Codec");
    public static final String PROTOCOL_TWA_DEVICE_SETTING_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Protocol TWA device setting");

    private GetTwaCommandMessages() {
    }

    public static String getNoCommandFoundForTwaDeviceSettingMessage(Class<? extends TwaDeviceSetting> clazz) {
        return String.format("No command found for TWA device setting with class %s.", clazz);
    }
}

