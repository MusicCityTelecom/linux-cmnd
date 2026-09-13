package be.tpvision.smartcontrol.messages.controllers.group;

import be.tpvision.smartcontrol.messages.Messages;

public class SetContentMessages {
   public static final String CONTENT_MANAGEMENT_SERVICE_CAN_NOT_BE_NULL = Messages.getServiceCanNotBeNullMessage("Content management");
   public static final String GROUP_ID_HAS_TO_BE_A_POSITIVE_NUMBER = Messages.getHasToBePositiveNumberMessage("Group id");
   public static final String CONTENT_ID_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Content id");
   public static final String CONTENT_ID_CAN_NOT_BE_EMPTY = Messages.getCanNotBeEmptyMessage("Content id");
   public static final String DEVICE_ID_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Device id");
   public static final String DEVICE_ID_HAS_TO_BE_A_POSITIVE_NUMBER = Messages.getHasToBePositiveNumberMessage("Device id");

   private SetContentMessages() {
   }
}
