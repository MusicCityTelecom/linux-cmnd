/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.messages.controllers.led_device;

import be.tpvision.smartcontrol.messages.Messages;

public class SetCascadeModeMessages {
    public static final String IP_ADDRESS_CAN_NOT_BE_EMPTY = Messages.getCanNotBeEmptyMessage("IP address");
    public static final String TWA_COMMAND_VIEW_MODEL_CAN_NOT_BE_NULL = Messages.getViewModelCanNotBeNullMessage("TWA command");
    public static final String DEVICE_ADDRESS_VIEW_MODEL_CAN_NOT_BE_NULL = Messages.getViewModelCanNotBeNullMessage("Device address");
    public static final String DEVICE_ADDRESS_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Device address");
    public static final String MODULE_ADDRESS_VIEW_MODEL_CAN_NOT_BE_NULL = Messages.getViewModelCanNotBeNullMessage("Module address");
    public static final String MODULE_ADDRESS_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Module address");
    public static final String TWA_DEVICE_SETTING_VIEW_MODEL_CAN_NOT_BE_NULL = Messages.getViewModelCanNotBeNullMessage("TWA device setting");
    public static final String TWA_DEVICE_SETTING_VIEW_MODEL_HAS_TO_BE_AN_INSTANCE_OF_STRING_VIEW_MODEL = "TWA device setting view model has to be an instance of string view model";
    public static final String VIEW_MODEL_CASCADE_MODE_CAN_NOT_BE_EMPTY = Messages.getCanNotBeEmptyMessage("View model cascade mode");
    public static final String DOMAIN_CASCADE_MODE_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Cascade mode");
    public static final String CASCADE_MODE_COMMAND_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Cascade mode command");

    private SetCascadeModeMessages() {
    }
}

