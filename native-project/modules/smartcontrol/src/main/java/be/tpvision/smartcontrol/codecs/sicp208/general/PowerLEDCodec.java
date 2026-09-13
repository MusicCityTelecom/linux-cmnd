package be.tpvision.smartcontrol.codecs.sicp208.general;

import be.tpvision.smartcontrol.codecs.sicp.SingleValueCodec;
import be.tpvision.smartcontrol.domain.device_settings.general.PowerLED;
import java.util.EnumMap;
import java.util.Map;

public class PowerLEDCodec extends SingleValueCodec<PowerLED> {
   private static PowerLEDCodec powerLEDCodec;

   private PowerLEDCodec() {
      super(PowerLED.class);
   }

   public static synchronized PowerLEDCodec getInstance() {
      if (powerLEDCodec == null) {
         powerLEDCodec = new PowerLEDCodec();
      }

      return powerLEDCodec;
   }

   @Override
   protected void initializeDeviceSettings() {
      Map<PowerLED, Byte> domainTouch = new EnumMap<>(PowerLED.class);

      for (PowerLED powerLED : PowerLED.values()) {
         domainTouch.put(powerLED, (byte)powerLED.ordinal());
      }

      super.setDeviceSettings(domainTouch);
   }
}
