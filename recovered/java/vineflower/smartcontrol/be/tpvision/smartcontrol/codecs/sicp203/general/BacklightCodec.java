package be.tpvision.smartcontrol.codecs.sicp203.general;

import be.tpvision.smartcontrol.codecs.sicp.SingleValueCodec;
import be.tpvision.smartcontrol.domain.device_settings.general.Backlight;
import java.util.EnumMap;
import java.util.Map;

public class BacklightCodec extends SingleValueCodec<Backlight> {
   static final byte ON_BYTE = 0;
   static final byte OFF_BYTE = 1;
   private static BacklightCodec backlightCodec;

   private BacklightCodec() {
      super(Backlight.class);
   }

   public static synchronized BacklightCodec getInstance() {
      if (backlightCodec == null) {
         backlightCodec = new BacklightCodec();
      }

      return backlightCodec;
   }

   @Override
   protected void initializeDeviceSettings() {
      Map<Backlight, Byte> domainBacklight = new EnumMap<>(Backlight.class);
      domainBacklight.put(Backlight.ON, (byte)0);
      domainBacklight.put(Backlight.OFF, (byte)1);
      super.setDeviceSettings(domainBacklight);
   }
}
