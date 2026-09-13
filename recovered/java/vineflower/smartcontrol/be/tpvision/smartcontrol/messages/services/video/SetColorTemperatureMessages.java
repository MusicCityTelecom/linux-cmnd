package be.tpvision.smartcontrol.messages.services.video;

import be.tpvision.smartcontrol.messages.Messages;

public class SetColorTemperatureMessages {
   public static final String DEVICE_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Device");
   public static final String COLOR_TEMPERATURE_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Color temperature");

   private SetColorTemperatureMessages() {
   }
}
