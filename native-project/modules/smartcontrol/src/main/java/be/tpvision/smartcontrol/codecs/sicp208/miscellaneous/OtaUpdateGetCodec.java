package be.tpvision.smartcontrol.codecs.sicp208.miscellaneous;

import be.tpvision.smartcontrol.codecs.sicp.Codec;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.OtaImageType;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.OtaUpdateGet;

public class OtaUpdateGetCodec extends Codec<OtaUpdateGet> {
   private static OtaUpdateGetCodec otaUpdateGetCodec;

   private OtaUpdateGetCodec() {
      super(OtaUpdateGet.class);
   }

   public static synchronized OtaUpdateGetCodec getInstance() {
      if (otaUpdateGetCodec == null) {
         otaUpdateGetCodec = new OtaUpdateGetCodec();
      }

      return otaUpdateGetCodec;
   }

   public byte[] toProtocol(final OtaUpdateGet otaUpdateGet) {
      return new byte[0];
   }

   public OtaUpdateGet toDomain(final byte[] bytes) {
      OtaImageType imageType = OtaImageType.ofIndex(bytes[0]);
      OtaUpdateGet.UpdateStatus status = OtaUpdateGet.UpdateStatus.values()[bytes[1]];
      return new OtaUpdateGet(imageType, status);
   }
}
