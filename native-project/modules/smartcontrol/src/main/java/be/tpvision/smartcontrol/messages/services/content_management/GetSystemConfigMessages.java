package be.tpvision.smartcontrol.messages.services.content_management;

import be.tpvision.smartcontrol.messages.Messages;

public class GetSystemConfigMessages {
   public static final String SMART_CONTROL_CONTEXT_PATH_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Smart control context path");
   public static final String HARDWARE_KEY_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Hardware key");
   public static final String SERVER_IP_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Server ip");

   private GetSystemConfigMessages() {
   }
}
