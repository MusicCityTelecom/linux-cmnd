package be.tpvision.smartcontrol.messages.controllers.device;

import be.tpvision.smartcontrol.messages.Messages;

public class UnassignContentMessages {
   public static final String CONTENT_MANAGEMENT_SERVICE_CAN_NOT_BE_NULL = Messages.getServiceCanNotBeNullMessage("Content management");
   public static final String DEVICE_ID_HAS_TO_BE_A_POSITIVE_NUMBER = Messages.getHasToBePositiveNumberMessage("Device id");

   private UnassignContentMessages() {
   }
}
