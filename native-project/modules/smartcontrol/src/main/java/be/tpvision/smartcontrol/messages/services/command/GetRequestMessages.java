package be.tpvision.smartcontrol.messages.services.command;

import be.tpvision.smartcontrol.messages.Messages;

public class GetRequestMessages {
   public static final String SICP_FACTORY_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Sicp factory");
   public static final String IP_DESTINATION_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("IP destination");
   public static final String TYPE_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Type");
   public static final String SETTING_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Setting");
   public static final String SICP_COMMAND_FACTORY_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Sicp command factory");

   private GetRequestMessages() {
   }
}
