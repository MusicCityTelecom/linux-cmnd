/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.messages.controllers.led_device;

import be.tpvision.smartcontrol.messages.Messages;

public class SetMainboardOriginPositionMessages {
    public static final String IP_ADDRESS_CAN_NOT_BE_EMPTY = Messages.getCanNotBeEmptyMessage("IP address");
    public static final String TWA_COMMAND_VIEW_MODEL_CAN_NOT_BE_NULL = Messages.getViewModelCanNotBeNullMessage("TWA command");
    public static final String DEVICE_ADDRESS_VIEW_MODEL_CAN_NOT_BE_NULL = Messages.getViewModelCanNotBeNullMessage("Device address");
    public static final String DEVICE_ADDRESS_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Device address");
    public static final String MODULE_ADDRESS_VIEW_MODEL_CAN_NOT_BE_NULL = Messages.getViewModelCanNotBeNullMessage("Module address");
    public static final String MODULE_ADDRESS_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Module address");
    public static final String TWA_DEVICE_SETTING_VIEW_MODEL_CAN_NOT_BE_NULL = Messages.getViewModelCanNotBeNullMessage("TWA device setting");
    public static final String TWA_DEVICE_SETTING_VIEW_MODEL_HAS_TO_BE_AN_INSTANCE_OF_MAINBOARD_ORIGIN_POSITION_VIEW_MODEL = "TWA device setting view model has to be an instance of mainboard origin position view model";
    public static final String MAINBOARD_ORIGIN_POSITION_VIEW_MODEL_CAN_NOT_BE_NULL = Messages.getViewModelCanNotBeNullMessage("Mainboard origin position");
    public static final String MAINBOARD_ORIGIN_POSITION_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Mainboard origin position");
    public static final String MAINBOARD_ORIGIN_POSITION_COMMAND_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Mainboard origin position command");

    private SetMainboardOriginPositionMessages() {
    }
}

