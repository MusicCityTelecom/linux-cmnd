package be.tpvision.smartcontrol.codecs.sicp208.miscellaneous;

import be.tpvision.smartcontrol.codecs.sicp.Codec;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.OtaUpdateSet;

public class OtaUpdateSetCodec extends Codec<OtaUpdateSet> {
   private static OtaUpdateSetCodec otaUpdateSetCodec;

   private OtaUpdateSetCodec() {
      super(OtaUpdateSet.class);
   }

   public static synchronized OtaUpdateSetCodec getInstance() {
      if (otaUpdateSetCodec == null) {
         otaUpdateSetCodec = new OtaUpdateSetCodec();
      }

      return otaUpdateSetCodec;
   }

   public byte[] toProtocol(final OtaUpdateSet otaUpdateSet) {
      return new byte[]{(byte)otaUpdateSet.getImageType().getIndex(), (byte)otaUpdateSet.getStatus().getIndex()};
   }

   public OtaUpdateSet toDomain(final byte[] bytes) {
      return null;
   }
}
