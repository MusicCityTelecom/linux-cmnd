package be.tpvision.smartcontrol.messages.controllers.schedule;

import be.tpvision.smartcontrol.messages.Messages;

public class HeadCmsSourceFileMessages {
   public static final String CONTENT_MANAGEMENT_SERVICE_CAN_NOT_BE_NULL = Messages.getServiceCanNotBeNullMessage("Content management");
   public static final String HARDWARE_KEY_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Hardware key");
   public static final String HARDWARE_KEY_CAN_NOT_BE_EMPTY = Messages.getCanNotBeEmptyMessage("Hardware key");
   public static final String FILE_NAME_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("File name");
   public static final String FILE_NAME_CAN_NOT_BE_EMPTY = Messages.getCanNotBeEmptyMessage("File name");
   public static final String HTTP_HEADERS_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Http headers");
   public static final String CMS_PATH_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Cms path");
   public static final String CONTENT_LENGTH_HAS_TO_BE_HIGHER_THAN_ZERO = "Content length has to be higher than 0.";

   private HeadCmsSourceFileMessages() {
   }
}
