package be.tpvision.smartcontrol.codecs.sicp208.miscellaneous;

import be.tpvision.smartcontrol.codecs.sicp.SingleValueCodec;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.OtaImageType;
import java.util.EnumMap;
import java.util.Map;

public class OtaImageTypeCodec extends SingleValueCodec<OtaImageType> {
   private static OtaImageTypeCodec otaImageTypeCodec;

   private OtaImageTypeCodec() {
      super(OtaImageType.class);
   }

   public static synchronized OtaImageTypeCodec getInstance() {
      if (otaImageTypeCodec == null) {
         otaImageTypeCodec = new OtaImageTypeCodec();
      }

      return otaImageTypeCodec;
   }

   @Override
   protected void initializeDeviceSettings() {
      Map<OtaImageType, Byte> domainInfo = new EnumMap<>(OtaImageType.class);

      for (OtaImageType otaImageType : OtaImageType.values()) {
         domainInfo.put(otaImageType, (byte)otaImageType.getIndex());
      }

      super.setDeviceSettings(domainInfo);
   }
}
