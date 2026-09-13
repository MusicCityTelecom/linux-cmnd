package be.tpvision.smartcontrol.messages.protocol.twa.twa_command;

import be.tpvision.smartcontrol.messages.Messages;

public class ToStringMessages {
   public static final String COMMAND_CODE_HEX_CAN_NOT_BE_EMPTY = Messages.getCanNotBeEmptyMessage("Command code hex");
   public static final String BYTES_HEX_CAN_NOT_BE_EMPTY = Messages.getCanNotBeEmptyMessage("Bytes hex");

   private ToStringMessages() {
   }
}
