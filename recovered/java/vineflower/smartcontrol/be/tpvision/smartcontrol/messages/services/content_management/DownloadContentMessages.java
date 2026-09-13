package be.tpvision.smartcontrol.messages.services.content_management;

import be.tpvision.smartcontrol.messages.Messages;

public class DownloadContentMessages {
   public static final String CONTENT_PATH_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Content path");
   public static final String CONTENT_URL_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Content url");
   public static final String CONTENT_SERVICE_JDBC_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Content service jdbc");
   public static final String CONTENT_ID_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Content id");
   public static final String CONTENT_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Content");
   public static final String ORIENTATION_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Orientation");

   private DownloadContentMessages() {
   }
}
