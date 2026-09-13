package be.tpvision.smartcontrol.codecs.sicp188.miscellaneous;

import be.tpvision.smartcontrol.codecs.sicp.SingleValueCodec;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.LightSensor;
import java.util.EnumMap;
import java.util.Map;

public class LightSensorCodec extends SingleValueCodec<LightSensor> {
   static final byte OFF_BYTE = 0;
   static final byte ON_BYTE = 1;
   private static LightSensorCodec lightSensorCodec;

   private LightSensorCodec() {
      super(LightSensor.class);
   }

   public static synchronized LightSensorCodec getInstance() {
      if (lightSensorCodec == null) {
         lightSensorCodec = new LightSensorCodec();
      }

      return lightSensorCodec;
   }

   @Override
   protected void initializeDeviceSettings() {
      Map<LightSensor, Byte> domainLightSensor = new EnumMap<>(LightSensor.class);
      domainLightSensor.put(LightSensor.OFF, (byte)0);
      domainLightSensor.put(LightSensor.ON, (byte)1);
      super.setDeviceSettings(domainLightSensor);
   }
}
