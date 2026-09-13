package be.tpvision.smartcontrol.codecs.sicp188.miscellaneous;

import be.tpvision.smartcontrol.codecs.sicp.SingleValueCodec;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.SmartPower;
import java.util.EnumMap;
import java.util.Map;

public class SmartPowerCodec extends SingleValueCodec<SmartPower> {
   static final byte OFF_BYTE = 0;
   static final byte LOW_BYTE = 1;
   static final byte MEDIUM_BYTE = 2;
   static final byte HIGH_BYTE = 3;
   private static SmartPowerCodec smartPowerCodec;

   private SmartPowerCodec() {
      super(SmartPower.class);
   }

   public static synchronized SmartPowerCodec getInstance() {
      if (smartPowerCodec == null) {
         smartPowerCodec = new SmartPowerCodec();
      }

      return smartPowerCodec;
   }

   @Override
   protected void initializeDeviceSettings() {
      Map<SmartPower, Byte> domainSmartPower = new EnumMap<>(SmartPower.class);
      domainSmartPower.put(SmartPower.OFF, (byte)0);
      domainSmartPower.put(SmartPower.LOW, (byte)1);
      domainSmartPower.put(SmartPower.MEDIUM, (byte)2);
      domainSmartPower.put(SmartPower.HIGH, (byte)3);
      super.setDeviceSettings(domainSmartPower);
   }
}
