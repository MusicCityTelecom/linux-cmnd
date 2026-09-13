package be.tpvision.smartcontrol.messages.codecs.sicp188.audio.volume_up_down_codec;

import be.tpvision.smartcontrol.messages.Messages;

public class ToProtocolMessages {
   public static final String VOLUME_UP_DOWN_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Volume up / down");
   public static final String SPEAKER_OUT_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Speaker out");
   public static final String AUDIO_OUT_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Audio out");

   private ToProtocolMessages() {
   }
}
