package be.tpvision.smartcontrol.codecs.sicp188.audio;

import be.tpvision.smartcontrol.codecs.sicp.Codec;
import be.tpvision.smartcontrol.domain.device_settings.audio.VolumeLimits;
import be.tpvision.smartcontrol.messages.codecs.sicp188.audio.volume_limits.ToProtocolMessages;
import be.tpvision.smartcontrol.util.ValueUtilities;
import org.springframework.util.Assert;

public class VolumeLimitsCodec extends Codec<VolumeLimits> {
   private static VolumeLimitsCodec volumeLimitsCodec;

   private VolumeLimitsCodec() {
      super(VolumeLimits.class);
   }

   public static synchronized VolumeLimitsCodec getInstance() {
      if (volumeLimitsCodec == null) {
         volumeLimitsCodec = new VolumeLimitsCodec();
      }

      return volumeLimitsCodec;
   }

   public byte[] toProtocol(VolumeLimits volumeLimits) {
      Assert.notNull(volumeLimits, ToProtocolMessages.VOLUME_LIMITS_CAN_NOT_BE_NULL);
      int domainMinimum = volumeLimits.getMinimum();
      byte protocolMinimum = ValueUtilities.getByteValueFromUnsigned(domainMinimum);
      int domainMaximum = volumeLimits.getMaximum();
      byte protocolMaximum = ValueUtilities.getByteValueFromUnsigned(domainMaximum);
      int domainSwitchOn = volumeLimits.getSwitchOn();
      byte protocolSwitchOn = ValueUtilities.getByteValueFromUnsigned(domainSwitchOn);
      return new byte[]{protocolMinimum, protocolMaximum, protocolSwitchOn};
   }

   public VolumeLimits toDomain(byte[] bytes) {
      if (bytes != null && bytes.length >= 3) {
         byte protocolMinimum = bytes[0];
         int domainMinimum = Byte.toUnsignedInt(protocolMinimum);
         byte protocolMaximum = bytes[1];
         int domainMaximum = Byte.toUnsignedInt(protocolMaximum);
         byte protocolSwitchOn = bytes[2];
         int domainSwitchOn = Byte.toUnsignedInt(protocolSwitchOn);
         return new VolumeLimits(domainMinimum, domainMaximum, domainSwitchOn);
      } else {
         return null;
      }
   }
}
