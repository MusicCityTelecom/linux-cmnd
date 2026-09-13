package be.tpvision.smartcontrol.messages.io.ip.twa.twa_handler;

import be.tpvision.smartcontrol.messages.Messages;

public class SendMessages {
   public static final String REQUEST_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Request");
   public static final String DESTINATION_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Destination");
   public static final String CHANNEL_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Channel");

   private SendMessages() {
   }
}
