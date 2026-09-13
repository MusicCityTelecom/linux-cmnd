package be.tpvision.smartcontrol.codecs.sicp188.video;

import be.tpvision.smartcontrol.codecs.sicp.SingleValueCodec;
import be.tpvision.smartcontrol.domain.device_settings.video.ColorTemperature;
import java.util.EnumMap;
import java.util.Map;

public class ColorTemperatureCodec extends SingleValueCodec<ColorTemperature> {
   static final byte USER_1_BYTE = 0;
   static final byte NATURE_BYTE = 1;
   static final byte _11000K_BYTE = 2;
   static final byte _10000K_BYTE = 3;
   static final byte _9300K_BYTE = 4;
   static final byte _7500K_BYTE = 5;
   static final byte _6500K_BYTE = 6;
   static final byte _5770K_BYTE = 7;
   static final byte _5500K_BYTE = 8;
   static final byte _5000K_BYTE = 9;
   static final byte _4000K_BYTE = 10;
   static final byte _3400K_BYTE = 11;
   static final byte _3350K_BYTE = 12;
   static final byte _3000K_BYTE = 13;
   static final byte _2800K_BYTE = 14;
   static final byte _2600K_BYTE = 15;
   static final byte _1850K_BYTE = 16;
   static final byte USER_2_BYTE = 18;
   private static ColorTemperatureCodec colorTemperatureCodec;

   private ColorTemperatureCodec() {
      super(ColorTemperature.class);
   }

   public static synchronized ColorTemperatureCodec getInstance() {
      if (colorTemperatureCodec == null) {
         colorTemperatureCodec = new ColorTemperatureCodec();
      }

      return colorTemperatureCodec;
   }

   @Override
   protected void initializeDeviceSettings() {
      Map<ColorTemperature, Byte> domainColorTemperatures = new EnumMap<>(ColorTemperature.class);
      domainColorTemperatures.put(ColorTemperature.USER_1, (byte)0);
      domainColorTemperatures.put(ColorTemperature.NATURE, (byte)1);
      domainColorTemperatures.put(ColorTemperature._11000K, (byte)2);
      domainColorTemperatures.put(ColorTemperature._10000K, (byte)3);
      domainColorTemperatures.put(ColorTemperature._9300K, (byte)4);
      domainColorTemperatures.put(ColorTemperature._7500K, (byte)5);
      domainColorTemperatures.put(ColorTemperature._6500K, (byte)6);
      domainColorTemperatures.put(ColorTemperature._5770K, (byte)7);
      domainColorTemperatures.put(ColorTemperature._5500K, (byte)8);
      domainColorTemperatures.put(ColorTemperature._5000K, (byte)9);
      domainColorTemperatures.put(ColorTemperature._4000K, (byte)10);
      domainColorTemperatures.put(ColorTemperature._3400K, (byte)11);
      domainColorTemperatures.put(ColorTemperature._3350K, (byte)12);
      domainColorTemperatures.put(ColorTemperature._3000K, (byte)13);
      domainColorTemperatures.put(ColorTemperature._2800K, (byte)14);
      domainColorTemperatures.put(ColorTemperature._2600K, (byte)15);
      domainColorTemperatures.put(ColorTemperature._1850K, (byte)16);
      domainColorTemperatures.put(ColorTemperature.USER_2, (byte)18);
      super.setDeviceSettings(domainColorTemperatures);
   }
}
