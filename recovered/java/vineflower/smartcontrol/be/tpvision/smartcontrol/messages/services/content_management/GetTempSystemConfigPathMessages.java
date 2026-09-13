package be.tpvision.smartcontrol.messages.services.content_management;

import be.tpvision.smartcontrol.messages.Messages;

public class GetTempSystemConfigPathMessages {
   public static final String HARDWARE_KEY_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Hardware key");
   public static final String TEMP_SYSTEM_CONFIG_PATH_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Temp system config path");

   private GetTempSystemConfigPathMessages() {
   }
}
