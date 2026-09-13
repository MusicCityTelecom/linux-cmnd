package be.tpvision.smartcontrol.codecs.sicp207.general;

import be.tpvision.smartcontrol.codecs.sicp.SingleValueCodec;
import be.tpvision.smartcontrol.domain.device_settings.general.TimeZone;
import java.util.EnumMap;
import java.util.Map;

public class TimeZoneCodec extends SingleValueCodec<TimeZone> {
   private static TimeZoneCodec timeZoneCodec;

   private TimeZoneCodec() {
      super(TimeZone.class);
   }

   public static synchronized TimeZoneCodec getInstance() {
      if (timeZoneCodec == null) {
         timeZoneCodec = new TimeZoneCodec();
      }

      return timeZoneCodec;
   }

   @Override
   protected void initializeDeviceSettings() {
      Map<TimeZone, Byte> domainTouch = new EnumMap<>(TimeZone.class);

      for (TimeZone timeZone : TimeZone.values()) {
         domainTouch.put(timeZone, (byte)timeZone.getIndex());
      }

      super.setDeviceSettings(domainTouch);
   }
}
