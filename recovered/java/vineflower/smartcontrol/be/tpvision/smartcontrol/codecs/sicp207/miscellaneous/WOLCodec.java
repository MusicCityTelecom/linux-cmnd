package be.tpvision.smartcontrol.codecs.sicp207.miscellaneous;

import be.tpvision.smartcontrol.codecs.sicp.SingleValueCodec;
import be.tpvision.smartcontrol.domain.device_settings.general.WOL;
import java.util.EnumMap;
import java.util.Map;

public class WOLCodec extends SingleValueCodec<WOL> {
   static final byte OFF_BYTE = 0;
   static final byte ON_BYTE = 1;
   private static WOLCodec wolCodec;

   private WOLCodec() {
      super(WOL.class);
   }

   public static synchronized WOLCodec getInstance() {
      if (wolCodec == null) {
         wolCodec = new WOLCodec();
      }

      return wolCodec;
   }

   @Override
   protected void initializeDeviceSettings() {
      Map<WOL, Byte> domainTouch = new EnumMap<>(WOL.class);
      domainTouch.put(WOL.OFF, (byte)0);
      domainTouch.put(WOL.ON, (byte)1);
      super.setDeviceSettings(domainTouch);
   }
}
