package be.tpvision.smartcontrol.codecs.sicp188.miscellaneous;

import be.tpvision.smartcontrol.codecs.sicp.Codec;
import be.tpvision.smartcontrol.codecs.sicp.SingleValueCodec;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.Miscellaneous;
import be.tpvision.smartcontrol.messages.codecs.sicp188.miscellaneous.miscellaneous.ToProtocolMessages;
import java.util.EnumMap;
import java.util.Map;

public class MiscellaneousCodec extends Codec<Miscellaneous> {
   private static MiscellaneousCodec miscellaneousCodec;

   private MiscellaneousCodec() {
      super(Miscellaneous.class);
   }

   public static synchronized MiscellaneousCodec getInstance() {
      if (miscellaneousCodec == null) {
         miscellaneousCodec = new MiscellaneousCodec();
      }

      return miscellaneousCodec;
   }

   public byte[] toProtocol(Miscellaneous miscellaneous) {
      throw new UnsupportedOperationException(ToProtocolMessages.TO_PROTOCOL_IS_NOT_SUPPORTED_FOR_THIS_SICP_VERSION);
   }

   public Miscellaneous toDomain(byte[] bytes) {
      if (bytes != null && bytes.length >= 2) {
         byte protocolValue1 = bytes[0];
         int domainValue1 = Byte.toUnsignedInt(protocolValue1);
         byte protocolValue2 = bytes[1];
         int domainValue2 = Byte.toUnsignedInt(protocolValue2);
         int domainOperatingHours = domainValue1 + domainValue2;
         return new Miscellaneous(domainOperatingHours);
      } else {
         return null;
      }
   }

   public static class InfoCodec extends SingleValueCodec<Miscellaneous.Info> {
      static final byte OPERATING_HOURS_BYTE = 2;
      private static MiscellaneousCodec.InfoCodec infoCodec;

      private InfoCodec() {
         super(Miscellaneous.Info.class);
      }

      public static synchronized MiscellaneousCodec.InfoCodec getInstance() {
         if (infoCodec == null) {
            infoCodec = new MiscellaneousCodec.InfoCodec();
         }

         return infoCodec;
      }

      @Override
      protected void initializeDeviceSettings() {
         Map<Miscellaneous.Info, Byte> domainInfo = new EnumMap<>(Miscellaneous.Info.class);
         domainInfo.put(Miscellaneous.Info.OPERATING_HOURS, (byte)2);
         super.setDeviceSettings(domainInfo);
      }
   }
}
