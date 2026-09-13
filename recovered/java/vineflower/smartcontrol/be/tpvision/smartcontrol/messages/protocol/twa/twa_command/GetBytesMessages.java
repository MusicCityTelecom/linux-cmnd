package be.tpvision.smartcontrol.messages.protocol.twa.twa_command;

import be.tpvision.smartcontrol.messages.Messages;

public class GetBytesMessages {
   public static final String BYTES_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Bytes");
   public static final String BYTES_HAS_TO_HAVE_A_LENGTH_OF_EXACTLY_TWENTY = "Bytes has to have a length of exactly 20";

   private GetBytesMessages() {
   }
}
