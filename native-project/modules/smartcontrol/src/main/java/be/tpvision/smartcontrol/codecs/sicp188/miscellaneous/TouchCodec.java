package be.tpvision.smartcontrol.codecs.sicp188.miscellaneous;

import be.tpvision.smartcontrol.codecs.sicp.SingleValueCodec;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.Touch;
import java.util.EnumMap;
import java.util.Map;

public class TouchCodec extends SingleValueCodec<Touch> {
   static final byte OFF_BYTE = 0;
   static final byte ON_BYTE = 1;
   private static TouchCodec touchCodec;

   private TouchCodec() {
      super(Touch.class);
   }

   public static synchronized TouchCodec getInstance() {
      if (touchCodec == null) {
         touchCodec = new TouchCodec();
      }

      return touchCodec;
   }

   @Override
   protected void initializeDeviceSettings() {
      Map<Touch, Byte> domainTouch = new EnumMap<>(Touch.class);
      domainTouch.put(Touch.OFF, (byte)0);
      domainTouch.put(Touch.ON, (byte)1);
      super.setDeviceSettings(domainTouch);
   }
}
