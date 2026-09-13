package be.tpvision.smartcontrol.messages.services.content_management;

import be.tpvision.smartcontrol.messages.Messages;

public class SetContentMessages {
   public static final String DEVICE_SERVICE_JDBC_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Device service jdbc");
   public static final String HARDWARE_SERVICE_JDBC_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Hardware service jdbc");
   public static final String DEVICE_ID_HAS_TO_BE_A_POSITIVE_NUMBER = Messages.getHasToBePositiveNumberMessage("Device id");
   public static final String HARDWARE_KEY_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Hardware key");
   public static final String HARDWARE_IS_BUSY = "Hardware is busy.";

   private SetContentMessages() {
   }
}
