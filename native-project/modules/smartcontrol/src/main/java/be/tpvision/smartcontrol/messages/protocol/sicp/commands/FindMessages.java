package be.tpvision.smartcontrol.messages.protocol.sicp.commands;

import be.tpvision.smartcontrol.messages.Messages;

public class FindMessages {
   public static final String TYPE_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Type");
   public static final String SETTING_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Setting");

   private FindMessages() {
   }
}
