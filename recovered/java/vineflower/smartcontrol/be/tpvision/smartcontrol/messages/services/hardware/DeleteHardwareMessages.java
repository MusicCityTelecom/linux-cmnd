package be.tpvision.smartcontrol.messages.services.hardware;

import be.tpvision.smartcontrol.messages.Messages;

public class DeleteHardwareMessages {
   public static final String HARDWARE_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Hardware");
   public static final String HARDWARE_KEY_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Hardware key");

   private DeleteHardwareMessages() {
   }
}
