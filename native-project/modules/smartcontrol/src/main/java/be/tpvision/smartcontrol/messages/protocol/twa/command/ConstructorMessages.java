package be.tpvision.smartcontrol.messages.protocol.twa.command;

import be.tpvision.smartcontrol.messages.Messages;

public class ConstructorMessages {
   public static final String TWA_DEVICE_SETTING_CLASS_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("TWA device setting class");
   public static final String COMMAND_CODE_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Command code");
   public static final String CODEC_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Codec");

   private ConstructorMessages() {
   }
}
