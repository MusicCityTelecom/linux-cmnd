package be.tpvision.smartcontrol.messages.controllers.device;

import be.tpvision.smartcontrol.messages.Messages;

public class DownloadContentMessages {
   public static final String DEVICE_SERVICE_JDBC_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Device service jdbc");
   public static final String HARDWARE_SERVICE_JDBC_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Hardware service jdbc");
   public static final String CONTENT_MANAGEMENT_SERVICE_CAN_NOT_BE_NULL = Messages.getServiceCanNotBeNullMessage("Content management");
   public static final String DEVICE_ID_HAS_TO_BE_A_POSITIVE_NUMBER = Messages.getHasToBePositiveNumberMessage("Device id");
   public static final String SERIAL_CODE_IS_UNKNOWN = "Serial code is unknown.";
   public static final String NO_CONTENT_ASSIGNED_TO_DEVICE = "No content assigned to device.";
   public static final String HARDWARE_IS_BUSY = "Hardware is busy.";
   public static final String CMS_PATH_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("CMS path");

   private DownloadContentMessages() {
   }
}
