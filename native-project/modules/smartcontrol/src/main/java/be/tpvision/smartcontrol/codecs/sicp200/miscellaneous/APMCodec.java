package be.tpvision.smartcontrol.codecs.sicp200.miscellaneous;

import be.tpvision.smartcontrol.codecs.sicp.SingleValueCodec;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.APM;
import java.util.EnumMap;
import java.util.Map;

public class APMCodec extends SingleValueCodec<APM> {
   static final byte OFF_BYTE = 0;
   static final byte ON_BYTE = 1;
   static final byte MODE_1_BYTE = 2;
   static final byte MODE_2_BYTE = 3;
   private static APMCodec apmCodec;

   private APMCodec() {
      super(APM.class);
   }

   public static synchronized APMCodec getInstance() {
      if (apmCodec == null) {
         apmCodec = new APMCodec();
      }

      return apmCodec;
   }

   @Override
   protected void initializeDeviceSettings() {
      Map<APM, Byte> domainAPM = new EnumMap<>(APM.class);
      domainAPM.put(APM.OFF, (byte)0);
      domainAPM.put(APM.ON, (byte)1);
      domainAPM.put(APM.MODE_1, (byte)2);
      domainAPM.put(APM.MODE_2, (byte)3);
      super.setDeviceSettings(domainAPM);
   }
}
