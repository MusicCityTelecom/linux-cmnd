package be.tpvision.smartcontrol.messages.services.input_sources;

import be.tpvision.smartcontrol.messages.Messages;

public class SetInputSourceMessages {
   public static final String DEVICE_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Device");
   public static final String INPUT_SOURCE_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Input source");

   private SetInputSourceMessages() {
   }
}
