package be.tpvision.smartcontrol.codecs.sicp197.miscellaneous;

import be.tpvision.smartcontrol.codecs.sicp.Codec;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.LedStrips;
import be.tpvision.smartcontrol.messages.codecs.sicp197.miscellaneous.led_strips.ToDomainMessages;
import be.tpvision.smartcontrol.messages.codecs.sicp197.miscellaneous.led_strips.ToProtocolMessages;
import be.tpvision.smartcontrol.util.MapUtilities;
import be.tpvision.smartcontrol.util.ValueUtilities;
import java.util.EnumMap;
import java.util.Map;
import org.springframework.util.Assert;

public class LedStripsCodec extends Codec<LedStrips> {
   static final byte STATUS_OFF_BYTE = 0;
   static final byte STATUS_ON_BYTE = 1;
   private static final Map<LedStrips.Status, Byte> domainStatusses = new EnumMap<>(LedStrips.Status.class);
   private static final Map<Byte, LedStrips.Status> protocolStatusses = MapUtilities.inverse(domainStatusses);
   private static LedStripsCodec ledStripsCodec;

   private LedStripsCodec() {
      super(LedStrips.class);
   }

   public static Map<LedStrips.Status, Byte> getDomainStatusses() {
      return domainStatusses;
   }

   public static Map<Byte, LedStrips.Status> getProtocolStatusses() {
      return protocolStatusses;
   }

   public static synchronized LedStripsCodec getInstance() {
      if (ledStripsCodec == null) {
         ledStripsCodec = new LedStripsCodec();
      }

      return ledStripsCodec;
   }

   public byte[] toProtocol(final LedStrips ledStrips) {
      Assert.notNull(ledStrips, ToProtocolMessages.LED_STRIPS_CAN_NOT_BE_NULL_MESSAGE);
      LedStrips.Status domainStatus = ledStrips.getStatus();
      Assert.state(domainStatus != null, ToProtocolMessages.DOMAIN_STATUS_CAN_NOT_BE_NULL_MESSAGE);
      Byte protocolStatus = domainStatusses.get(domainStatus);
      Assert.state(protocolStatus != null, ToProtocolMessages.PROTOCOL_STATUS_CAN_NOT_BE_NULL_MESSAGE);
      int domainRedValue = ledStrips.getRedValue();
      byte protocolRedValue = ValueUtilities.getByteValueFromUnsigned(domainRedValue);
      int domainGreenValue = ledStrips.getGreenValue();
      byte protocolGreenValue = ValueUtilities.getByteValueFromUnsigned(domainGreenValue);
      int domainBlueValue = ledStrips.getBlueValue();
      byte protocolBlueValue = ValueUtilities.getByteValueFromUnsigned(domainBlueValue);
      return new byte[]{protocolStatus, protocolRedValue, protocolGreenValue, protocolBlueValue};
   }

   public LedStrips toDomain(final byte[] bytes) {
      if (bytes != null && bytes.length >= 4) {
         byte protocolStatus = bytes[0];
         LedStrips.Status domainStatus = protocolStatusses.get(protocolStatus);
         Assert.notNull(domainStatus, ToDomainMessages.STATUS_CAN_NOT_BE_NULL_MESSAGE);
         byte protocolRedValue = bytes[1];
         int domainRedValue = Byte.toUnsignedInt(protocolRedValue);
         byte protocolGreenValue = bytes[2];
         int domainGreenValue = Byte.toUnsignedInt(protocolGreenValue);
         byte protocolBlueValue = bytes[3];
         int domainBlueValue = Byte.toUnsignedInt(protocolBlueValue);
         return new LedStrips(domainStatus, domainRedValue, domainGreenValue, domainBlueValue);
      } else {
         return null;
      }
   }

   static {
      domainStatusses.put(LedStrips.Status.OFF, (byte)0);
      domainStatusses.put(LedStrips.Status.ON, (byte)1);
   }
}
