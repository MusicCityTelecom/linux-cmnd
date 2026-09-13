package be.tpvision.smartcontrol.messages.repositories.jdbc.device;

import be.tpvision.smartcontrol.messages.Messages;

public class GetDeviceMessages {
   public static final String DEVICE_ROW_MAPPER_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Device row mapper");
   public static final String ID_HAS_TO_BE_A_POSITIVE_NUMBER = Messages.getHasToBePositiveNumberMessage("Id");

   private GetDeviceMessages() {
   }
}
