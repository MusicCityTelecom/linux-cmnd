package be.tpvision.smartcontrol.messages.services.device_data_management;

import be.tpvision.smartcontrol.messages.Messages;

public class FetchOverviewDataMessages {
   public static final String DEVICE_SERVICE_CAN_NOT_BE_NULL = Messages.getServiceCanNotBeNullMessage("Device");
   public static final String GENERAL_SERVICE_CAN_NOT_BE_NULL = Messages.getServiceCanNotBeNullMessage("General");
   public static final String MISCELLANEOUS_SERVICE_CAN_NOT_BE_NULL = Messages.getServiceCanNotBeNullMessage("Miscellaneous");
   public static final String INPUT_SOURCES_SERVICE_CAN_NOT_BE_NULL = Messages.getServiceCanNotBeNullMessage("Input sources");

   private FetchOverviewDataMessages() {
   }
}
