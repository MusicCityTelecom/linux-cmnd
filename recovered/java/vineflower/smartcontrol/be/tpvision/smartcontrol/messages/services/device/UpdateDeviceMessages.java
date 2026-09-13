package be.tpvision.smartcontrol.messages.services.device;

import be.tpvision.smartcontrol.messages.Messages;

public class UpdateDeviceMessages {
   public static final String DEVICE_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Device");
   public static final String DEVICE_REPOSITORY_CAN_NOT_BE_NULL = Messages.getRepositoryCanNotBeNullMessage("Device");

   private UpdateDeviceMessages() {
   }
}
