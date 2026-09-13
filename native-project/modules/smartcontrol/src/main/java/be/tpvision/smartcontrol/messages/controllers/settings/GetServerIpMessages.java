package be.tpvision.smartcontrol.messages.controllers.settings;

import be.tpvision.smartcontrol.messages.Messages;

public class GetServerIpMessages {
   public static final String SETTINGS_SERVICE_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Settings service");
   public static final String SERVER_IP_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Server ip");
   public static final String SERVER_IP_CAN_NOT_BE_EMPTY = Messages.getCanNotBeEmptyMessage("Server ip");
   public static final String SETTINGS_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Settings");

   private GetServerIpMessages() {
   }
}
