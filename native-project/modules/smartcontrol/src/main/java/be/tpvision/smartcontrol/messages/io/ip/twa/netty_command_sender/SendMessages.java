package be.tpvision.smartcontrol.messages.io.ip.twa.netty_command_sender;

import be.tpvision.smartcontrol.messages.Messages;

public class SendMessages {
   public static final String COMMAND_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Command");
   public static final String DESTINATION_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Destination");
   public static final String TWA_HANDLER_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("TWA handler");

   private SendMessages() {
   }
}
