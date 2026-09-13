package be.tpvision.smartcontrol.codecs.sicp188.miscellaneous;

import be.tpvision.smartcontrol.codecs.sicp.SingleValueCodec;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.PowerSavingMode;
import java.util.EnumMap;
import java.util.Map;

public class PowerSavingModeCodec extends SingleValueCodec<PowerSavingMode> {
   static final byte RGB_OFF_VIDEO_OFF_BYTE = 0;
   static final byte RGB_OFF_VIDEO_ON_BYTE = 1;
   static final byte RGB_ON_VIDEO_OFF_BYTE = 2;
   static final byte RGB_ON_VIDEO_ON_BYTE = 3;
   private static PowerSavingModeCodec powerSavingModeCodec;

   private PowerSavingModeCodec() {
      super(PowerSavingMode.class);
   }

   public static synchronized PowerSavingModeCodec getInstance() {
      if (powerSavingModeCodec == null) {
         powerSavingModeCodec = new PowerSavingModeCodec();
      }

      return powerSavingModeCodec;
   }

   @Override
   protected void initializeDeviceSettings() {
      Map<PowerSavingMode, Byte> domainPowerSavingMode = new EnumMap<>(PowerSavingMode.class);
      domainPowerSavingMode.put(PowerSavingMode.RGB_OFF_VIDEO_OFF, (byte)0);
      domainPowerSavingMode.put(PowerSavingMode.RGB_OFF_VIDEO_ON, (byte)1);
      domainPowerSavingMode.put(PowerSavingMode.RGB_ON_VIDEO_OFF, (byte)2);
      domainPowerSavingMode.put(PowerSavingMode.RGB_ON_VIDEO_ON, (byte)3);
      super.setDeviceSettings(domainPowerSavingMode);
   }
}
