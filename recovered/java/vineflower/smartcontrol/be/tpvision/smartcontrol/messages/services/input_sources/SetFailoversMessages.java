package be.tpvision.smartcontrol.messages.services.input_sources;

import be.tpvision.smartcontrol.messages.Messages;

public class SetFailoversMessages {
   public static final String DEVICE_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Device");
   public static final String FAILOVERS_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Failovers");

   private SetFailoversMessages() {
   }
}
