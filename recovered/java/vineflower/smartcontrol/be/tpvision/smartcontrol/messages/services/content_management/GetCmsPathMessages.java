package be.tpvision.smartcontrol.messages.services.content_management;

import be.tpvision.smartcontrol.messages.Messages;

public class GetCmsPathMessages {
   public static final String CONTENT_PATH_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Content path");
   public static final String CONTENT_FILE_EXTENSION_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Content file extension");
   public static final String CONTENT_ID_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Content id");
   public static final String CONTENT_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Content");
   public static final String ORIENTATION_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Orientation");

   private GetCmsPathMessages() {
   }
}
