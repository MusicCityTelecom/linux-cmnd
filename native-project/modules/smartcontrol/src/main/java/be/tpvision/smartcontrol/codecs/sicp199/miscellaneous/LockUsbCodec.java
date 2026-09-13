package be.tpvision.smartcontrol.codecs.sicp199.miscellaneous;

import be.tpvision.smartcontrol.codecs.sicp.SingleValueCodec;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.LockUsb;
import java.util.EnumMap;
import java.util.Map;

public class LockUsbCodec extends SingleValueCodec<LockUsb> {
   static final byte OFF_BYTE = 0;
   static final byte ON_BYTE = 1;
   private static LockUsbCodec lockUsbCodec;

   private LockUsbCodec() {
      super(LockUsb.class);
   }

   public static synchronized LockUsbCodec getInstance() {
      if (lockUsbCodec == null) {
         lockUsbCodec = new LockUsbCodec();
      }

      return lockUsbCodec;
   }

   @Override
   protected void initializeDeviceSettings() {
      Map<LockUsb, Byte> domainTouch = new EnumMap<>(LockUsb.class);
      domainTouch.put(LockUsb.OFF, (byte)0);
      domainTouch.put(LockUsb.ON, (byte)1);
      super.setDeviceSettings(domainTouch);
   }
}
