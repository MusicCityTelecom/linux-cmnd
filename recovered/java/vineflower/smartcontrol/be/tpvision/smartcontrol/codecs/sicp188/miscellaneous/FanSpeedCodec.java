package be.tpvision.smartcontrol.codecs.sicp188.miscellaneous;

import be.tpvision.smartcontrol.codecs.sicp.SingleValueCodec;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.FanSpeed;
import java.util.EnumMap;
import java.util.Map;

public class FanSpeedCodec extends SingleValueCodec<FanSpeed> {
   static final byte OFF_BYTE = 0;
   static final byte AUTO_BYTE = 1;
   static final byte LOW_BYTE = 2;
   static final byte MIDDLE_BYTE = 3;
   static final byte HIGH_BYTE = 4;
   private static FanSpeedCodec fanSpeedCodec;

   private FanSpeedCodec() {
      super(FanSpeed.class);
   }

   public static synchronized FanSpeedCodec getInstance() {
      if (fanSpeedCodec == null) {
         fanSpeedCodec = new FanSpeedCodec();
      }

      return fanSpeedCodec;
   }

   @Override
   protected void initializeDeviceSettings() {
      Map<FanSpeed, Byte> domainFanSpeed = new EnumMap<>(FanSpeed.class);
      domainFanSpeed.put(FanSpeed.OFF, (byte)0);
      domainFanSpeed.put(FanSpeed.AUTO, (byte)1);
      domainFanSpeed.put(FanSpeed.LOW, (byte)2);
      domainFanSpeed.put(FanSpeed.MIDDLE, (byte)3);
      domainFanSpeed.put(FanSpeed.HIGH, (byte)4);
      super.setDeviceSettings(domainFanSpeed);
   }
}
