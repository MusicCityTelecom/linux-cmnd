package be.tpvision.smartcontrol.messages.services.content_management;

import be.tpvision.smartcontrol.messages.Messages;

public class GetHardwarePath {
   public static final String CONTENT_PATH_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Content path");
   public static final String HARDWARE_KEY_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Hardware key");

   private GetHardwarePath() {
   }
}
