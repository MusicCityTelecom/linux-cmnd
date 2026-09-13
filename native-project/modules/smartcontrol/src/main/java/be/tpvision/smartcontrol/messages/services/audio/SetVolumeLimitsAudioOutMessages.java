package be.tpvision.smartcontrol.messages.services.audio;

import be.tpvision.smartcontrol.messages.Messages;

public class SetVolumeLimitsAudioOutMessages {
   public static final String DEVICE_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Device");
   public static final String VOLUME_LIMITS_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Volume limits");

   private SetVolumeLimitsAudioOutMessages() {
   }
}
