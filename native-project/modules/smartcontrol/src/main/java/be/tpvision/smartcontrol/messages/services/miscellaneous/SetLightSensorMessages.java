package be.tpvision.smartcontrol.messages.services.miscellaneous;

import be.tpvision.smartcontrol.messages.Messages;

public class SetLightSensorMessages {
   public static final String DEVICE_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Device");
   public static final String LIGHT_SENSOR_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Light sensor");

   private SetLightSensorMessages() {
   }
}
