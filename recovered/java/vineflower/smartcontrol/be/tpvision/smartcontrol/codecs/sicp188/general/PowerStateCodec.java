package be.tpvision.smartcontrol.codecs.sicp188.general;

import be.tpvision.smartcontrol.codecs.sicp.SingleValueCodec;
import be.tpvision.smartcontrol.domain.device_settings.general.PowerState;
import java.util.EnumMap;
import java.util.Map;

public class PowerStateCodec extends SingleValueCodec<PowerState> {
   static final byte OFF_BYTE = 1;
   static final byte ON_BYTE = 2;
   private static PowerStateCodec powerStateCodec;

   private PowerStateCodec() {
      super(PowerState.class);
   }

   public static synchronized PowerStateCodec getInstance() {
      if (powerStateCodec == null) {
         powerStateCodec = new PowerStateCodec();
      }

      return powerStateCodec;
   }

   @Override
   protected void initializeDeviceSettings() {
      Map<PowerState, Byte> domainPowerState = new EnumMap<>(PowerState.class);
      domainPowerState.put(PowerState.OFF, (byte)1);
      domainPowerState.put(PowerState.ON, (byte)2);
      super.setDeviceSettings(domainPowerState);
   }
}
