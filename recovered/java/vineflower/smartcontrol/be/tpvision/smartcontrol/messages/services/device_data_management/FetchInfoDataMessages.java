package be.tpvision.smartcontrol.messages.services.device_data_management;

import be.tpvision.smartcontrol.messages.Messages;

public class FetchInfoDataMessages {
   public static final String DEVICE_SERVICE_CAN_NOT_BE_NULL = Messages.getServiceCanNotBeNullMessage("Device");
   public static final String SYSTEM_SERVICE_CAN_NOT_BE_NULL = Messages.getServiceCanNotBeNullMessage("System");
   public static final String MISCELLANEOUS_SERVICE_CAN_NOT_BE_NULL = Messages.getServiceCanNotBeNullMessage("Miscellaneous");

   private FetchInfoDataMessages() {
   }
}
