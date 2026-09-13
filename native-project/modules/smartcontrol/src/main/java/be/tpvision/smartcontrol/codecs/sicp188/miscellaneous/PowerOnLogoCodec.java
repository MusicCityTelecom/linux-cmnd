package be.tpvision.smartcontrol.codecs.sicp188.miscellaneous;

import be.tpvision.smartcontrol.codecs.sicp.SingleValueCodec;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.PowerOnLogo;
import java.util.EnumMap;
import java.util.Map;

public class PowerOnLogoCodec extends SingleValueCodec<PowerOnLogo> {
   static final byte OFF_BYTE = 0;
   static final byte ON_BYTE = 1;
   static final byte USER_BYTE = 2;
   private static PowerOnLogoCodec powerOnLogoCodec;

   private PowerOnLogoCodec() {
      super(PowerOnLogo.class);
   }

   public static synchronized PowerOnLogoCodec getInstance() {
      if (powerOnLogoCodec == null) {
         powerOnLogoCodec = new PowerOnLogoCodec();
      }

      return powerOnLogoCodec;
   }

   @Override
   protected void initializeDeviceSettings() {
      Map<PowerOnLogo, Byte> domainPowerOnLogo = new EnumMap<>(PowerOnLogo.class);
      domainPowerOnLogo.put(PowerOnLogo.OFF, (byte)0);
      domainPowerOnLogo.put(PowerOnLogo.ON, (byte)1);
      domainPowerOnLogo.put(PowerOnLogo.USER, (byte)2);
      super.setDeviceSettings(domainPowerOnLogo);
   }
}
