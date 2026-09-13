package be.tpvision.smartcontrol.messages.services.audio;

import be.tpvision.smartcontrol.messages.Messages;

public class SetMuteMessages {
   public static final String DEVICE_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Device");
   public static final String MUTE_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Mute");

   private SetMuteMessages() {
   }
}
