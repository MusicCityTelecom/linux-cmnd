package be.tpvision.smartcontrol.codecs.sicp188.miscellaneous;

import be.tpvision.smartcontrol.codecs.sicp.SingleValueCodec;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.OSDRotating;
import java.util.EnumMap;
import java.util.Map;

public class OSDRotatingCodec extends SingleValueCodec<OSDRotating> {
   static final byte OFF_BYTE = 0;
   static final byte ON_BYTE = 1;
   private static OSDRotatingCodec osdRotatingCodec;

   private OSDRotatingCodec() {
      super(OSDRotating.class);
   }

   public static synchronized OSDRotatingCodec getInstance() {
      if (osdRotatingCodec == null) {
         osdRotatingCodec = new OSDRotatingCodec();
      }

      return osdRotatingCodec;
   }

   @Override
   protected void initializeDeviceSettings() {
      Map<OSDRotating, Byte> domainOSDRotating = new EnumMap<>(OSDRotating.class);
      domainOSDRotating.put(OSDRotating.OFF, (byte)0);
      domainOSDRotating.put(OSDRotating.ON, (byte)1);
      super.setDeviceSettings(domainOSDRotating);
   }
}
