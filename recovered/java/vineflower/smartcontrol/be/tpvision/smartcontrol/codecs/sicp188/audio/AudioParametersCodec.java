package be.tpvision.smartcontrol.codecs.sicp188.audio;

import be.tpvision.smartcontrol.codecs.sicp.Codec;
import be.tpvision.smartcontrol.domain.device_settings.audio.AudioParameters;
import be.tpvision.smartcontrol.messages.codecs.sicp188.audio.audio_parameters_codec.ToProtocolMessages;
import be.tpvision.smartcontrol.util.ValueUtilities;
import org.springframework.util.Assert;

public class AudioParametersCodec extends Codec<AudioParameters> {
   private static AudioParametersCodec audioParametersCodec;

   private AudioParametersCodec() {
      super(AudioParameters.class);
   }

   public static synchronized AudioParametersCodec getInstance() {
      if (audioParametersCodec == null) {
         audioParametersCodec = new AudioParametersCodec();
      }

      return audioParametersCodec;
   }

   public byte[] toProtocol(AudioParameters audioParameters) {
      Assert.notNull(audioParameters, ToProtocolMessages.AUDIO_PARAMETERS_CAN_NOT_BE_NULL);
      int domainTreble = audioParameters.getTreble();
      byte protocolTreble = ValueUtilities.getByteValue(domainTreble);
      int domainBass = audioParameters.getBass();
      byte protocolBass = ValueUtilities.getByteValue(domainBass);
      return new byte[]{protocolTreble, protocolBass};
   }

   public AudioParameters toDomain(byte[] bytes) {
      if (bytes != null && bytes.length >= 2) {
         byte treble = bytes[0];
         byte bass = bytes[1];
         return new AudioParameters(treble, bass);
      } else {
         return null;
      }
   }
}
