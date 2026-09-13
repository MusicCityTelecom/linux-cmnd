package be.tpvision.smartcontrol.messages.services.general;

import be.tpvision.smartcontrol.messages.Messages;

public class SetPowerStateAtColdStartMessages {
   public static final String DEVICE_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Device");
   public static final String POWER_STATE_AT_COLD_START_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Power state at cold start");

   private SetPowerStateAtColdStartMessages() {
   }
}
