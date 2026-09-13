/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.messages.protocol.sicp.sicp_command_factory_impl;

import be.tpvision.smartcontrol.messages.Messages;

public class GetSicpCommandMessages {
    public static final String TYPE_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Type");
    public static final String SETTING_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Setting");
    public static final String COMMAND_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Command");
    public static final String SICP_VERSION_STRING_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("SICP version string");
    public static final String DEVICE_SETTING_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Device setting");
    public static final String ENCODER_DEVICE_SETTING_CLASS_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Encoder device setting class");
    public static final String DEVICE_SETTING_CLASS_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Device setting class");
    public static final String DESTINATION_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Destination");

    private GetSicpCommandMessages() {
    }

    public static String getEncoderNotFoundMessage(String sicpVersion, String type, String setting) {
        Messages.assertIsNotNullOrEmpty(type);
        Messages.assertIsNotNullOrEmpty(setting);
        return String.format("No SICP %s encoder found for %s %s.", sicpVersion, type, setting);
    }

    public static String getWrongDeviceSettingMessage(String encoderDeviceSettingClass, String deviceSettingClass) {
        Messages.assertIsNotNullOrEmpty(encoderDeviceSettingClass);
        Messages.assertIsNotNullOrEmpty(deviceSettingClass);
        return String.format("Wrong device setting! Expected: %s but found: %s.", encoderDeviceSettingClass, deviceSettingClass);
    }
}

