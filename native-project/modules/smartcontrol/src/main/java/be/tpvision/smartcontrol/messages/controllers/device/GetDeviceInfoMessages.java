package be.tpvision.smartcontrol.messages.controllers.device;

import be.tpvision.smartcontrol.messages.Messages;

public class GetDeviceInfoMessages {
   public static final String SERIAL_CODE_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Serial code");
   public static final String HARDWARE_SERVICE_CAN_NOT_BE_NULL = Messages.getServiceCanNotBeNullMessage("Hardware");
   public static final String CONTENT_SERVICE_CAN_NOT_BE_NULL = Messages.getServiceCanNotBeNullMessage("Content");

   private GetDeviceInfoMessages() {
   }
}
