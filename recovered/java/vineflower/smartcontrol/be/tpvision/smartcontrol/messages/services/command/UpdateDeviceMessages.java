package be.tpvision.smartcontrol.messages.services.command;

import be.tpvision.smartcontrol.messages.Messages;

public class UpdateDeviceMessages {
   public static final String DEVICE_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Device");
   public static final String SETTING_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Setting");

   private UpdateDeviceMessages() {
   }
}
