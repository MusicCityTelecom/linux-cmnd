package be.tpvision.smartcontrol.messages.services.command;

import be.tpvision.smartcontrol.messages.Messages;

public class SendMessages {
   public static final String IP_DESTINATION_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("IP destination");
   public static final String SICP_FACTORY_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Sicp factory");
   public static final String RESPONSE_CLASS_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Response class");

   private SendMessages() {
   }
}
