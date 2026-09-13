package be.tpvision.smartcontrol.messages.controllers.led_device;

import be.tpvision.smartcontrol.messages.Messages;

public class SetBrightnessMessages {
   public static final String IP_ADDRESS_CAN_NOT_BE_EMPTY = Messages.getCanNotBeEmptyMessage("IP address");
   public static final String TWA_COMMAND_VIEW_MODEL_CAN_NOT_BE_NULL = Messages.getViewModelCanNotBeNullMessage("TWA command");
   public static final String DEVICE_ADDRESS_VIEW_MODEL_CAN_NOT_BE_NULL = Messages.getViewModelCanNotBeNullMessage("Device address");
   public static final String DEVICE_ADDRESS_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Device address");
   public static final String MODULE_ADDRESS_VIEW_MODEL_CAN_NOT_BE_NULL = Messages.getViewModelCanNotBeNullMessage("Module address");
   public static final String MODULE_ADDRESS_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Module address");
   public static final String TWA_DEVICE_SETTING_VIEW_MODEL_CAN_NOT_BE_NULL = Messages.getViewModelCanNotBeNullMessage("TWA device setting");
   public static final String TWA_DEVICE_SETTING_VIEW_MODEL_HAS_TO_BE_AN_INSTANCE_OF_INTEGER_VIEW_MODEL = "TWA device setting view model has to be an instance of integer view model";
   public static final String BRIGHTNESS_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Brightness");
   public static final String BRIGHTNESS_COMMAND_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Brightness command");

   private SetBrightnessMessages() {
   }
}
