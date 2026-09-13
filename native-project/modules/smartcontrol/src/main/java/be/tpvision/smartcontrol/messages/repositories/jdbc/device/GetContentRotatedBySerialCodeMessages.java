package be.tpvision.smartcontrol.messages.repositories.jdbc.device;

import be.tpvision.smartcontrol.messages.Messages;

public class GetContentRotatedBySerialCodeMessages {
   public static final String SERIAL_CODE_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Serial code");
   public static final String SERIAL_CODE_CAN_NOT_BE_EMPTY = Messages.getCanNotBeEmptyMessage("Serial code");

   private GetContentRotatedBySerialCodeMessages() {
   }

   public static String noDeviceFoundWithSerialCodeMessage(final String serialCode) {
      Messages.assertIsNotNullOrEmpty(serialCode);
      return String.format("No device found with serial code: %s.", serialCode);
   }
}
