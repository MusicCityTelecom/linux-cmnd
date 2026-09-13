package be.tpvision.smartcontrol.messages.services.device;

import be.tpvision.smartcontrol.messages.Messages;

public class GetDeviceBySerialCodeMessages {
   public static final String DEVICE_REPOSITORY_CAN_NOT_BE_NULL = Messages.getRepositoryCanNotBeNullMessage("Device");
   public static final String SERIAL_CODE_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Serial code");

   private GetDeviceBySerialCodeMessages() {
   }
}
