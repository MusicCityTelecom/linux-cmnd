package be.tpvision.smartcontrol.messages.controllers.schedule;

import be.tpvision.smartcontrol.messages.Messages;

public class HeadRevisionFileMessages {
   public static final String CONTENT_MANAGEMENT_SERVICE_CAN_NOT_BE_NULL = Messages.getServiceCanNotBeNullMessage("Content management");
   public static final String HARDWARE_KEY_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Hardware key");
   public static final String HARDWARE_KEY_CAN_NOT_BE_EMPTY = Messages.getCanNotBeEmptyMessage("Hardware key");
   public static final String REVISION_PATH_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Revision path");
   public static final String HTTP_HEADERS_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Http headers");

   private HeadRevisionFileMessages() {
   }
}
