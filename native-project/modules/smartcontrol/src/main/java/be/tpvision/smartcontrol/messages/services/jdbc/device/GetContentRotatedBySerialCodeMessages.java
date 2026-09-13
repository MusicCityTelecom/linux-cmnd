package be.tpvision.smartcontrol.messages.services.jdbc.device;

import be.tpvision.smartcontrol.messages.Messages;

public class GetContentRotatedBySerialCodeMessages {
   public static final String SERIAL_CODE_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Serial code");
   public static final String SERIAL_CODE_CAN_NOT_BE_EMPTY = Messages.getCanNotBeEmptyMessage("Serial code");

   private GetContentRotatedBySerialCodeMessages() {
   }
}
