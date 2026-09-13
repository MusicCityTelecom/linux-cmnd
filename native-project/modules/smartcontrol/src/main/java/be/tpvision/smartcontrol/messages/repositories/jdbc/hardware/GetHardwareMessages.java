package be.tpvision.smartcontrol.messages.repositories.jdbc.hardware;

import be.tpvision.smartcontrol.messages.Messages;

public class GetHardwareMessages {
   public static final String HARDWARE_ROW_MAPPER_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Hardware row mapper");
   public static final String HARDWARE_KEY_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Hardware key");
   public static final String HARDWARE_KEY_CAN_NOT_BE_EMPTY = Messages.getCanNotBeEmptyMessage("Hardware key");

   private GetHardwareMessages() {
   }
}
