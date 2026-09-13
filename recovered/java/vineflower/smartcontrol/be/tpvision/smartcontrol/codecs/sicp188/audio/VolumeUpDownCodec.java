package be.tpvision.smartcontrol.codecs.sicp188.audio;

import be.tpvision.smartcontrol.codecs.sicp.Codec;
import be.tpvision.smartcontrol.domain.device_settings.audio.VolumeUpDown;
import be.tpvision.smartcontrol.messages.codecs.sicp188.audio.volume_up_down_codec.ToDomainMessages;
import be.tpvision.smartcontrol.messages.codecs.sicp188.audio.volume_up_down_codec.ToProtocolMessages;
import java.util.EnumMap;
import java.util.Map;
import org.springframework.util.Assert;

public class VolumeUpDownCodec extends Codec<VolumeUpDown> {
   static final byte VOLUME_DOWN_BYTE = 0;
   static final byte VOLUME_UP_BYTE = 1;
   static final byte VOLUME_UNCHANGED_BYTE = -1;
   private static final Map<VolumeUpDown.Volume, Byte> domainVolume = new EnumMap<>(VolumeUpDown.Volume.class);
   private static VolumeUpDownCodec volumeUpDownCodec;

   private VolumeUpDownCodec() {
      super(VolumeUpDown.class);
   }

   public static synchronized VolumeUpDownCodec getInstance() {
      if (volumeUpDownCodec == null) {
         volumeUpDownCodec = new VolumeUpDownCodec();
      }

      return volumeUpDownCodec;
   }

   public byte[] toProtocol(VolumeUpDown volumeUpDown) {
      Assert.notNull(volumeUpDown, ToProtocolMessages.VOLUME_UP_DOWN_CAN_NOT_BE_NULL);
      VolumeUpDown.Volume domainSpeakerOut = volumeUpDown.getSpeakerOut();
      Byte protocolSpeakerOut = domainVolume.get(domainSpeakerOut);
      Assert.state(protocolSpeakerOut != null, ToProtocolMessages.SPEAKER_OUT_CAN_NOT_BE_NULL);
      VolumeUpDown.Volume domainAudioOut = volumeUpDown.getAudioOut();
      Byte protocolAudioOut = domainVolume.get(domainAudioOut);
      Assert.state(protocolAudioOut != null, ToProtocolMessages.AUDIO_OUT_CAN_NOT_BE_NULL);
      return new byte[]{protocolSpeakerOut, protocolAudioOut};
   }

   public VolumeUpDown toDomain(byte[] bytes) {
      throw new UnsupportedOperationException(ToDomainMessages.TO_DOMAIN_IS_NOT_SUPPORTED_FOR_THIS_SICP_VERSION);
   }

   static {
      domainVolume.put(VolumeUpDown.Volume.DOWN, (byte)0);
      domainVolume.put(VolumeUpDown.Volume.UP, (byte)1);
      domainVolume.put(VolumeUpDown.Volume.UNCHANGED, (byte)-1);
   }
}
