package be.tpvision.smartcontrol.codecs.sicp208.general;

import be.tpvision.smartcontrol.codecs.sicp.SingleValueCodec;
import be.tpvision.smartcontrol.domain.device_settings.general.ForceRestartCustomApp;
import java.util.EnumMap;
import java.util.Map;

public class ForceRestartCustomAppCodec extends SingleValueCodec<ForceRestartCustomApp> {
   private static ForceRestartCustomAppCodec forceRestartCustomAppCodec;

   private ForceRestartCustomAppCodec() {
      super(ForceRestartCustomApp.class);
   }

   public static synchronized ForceRestartCustomAppCodec getInstance() {
      if (forceRestartCustomAppCodec == null) {
         forceRestartCustomAppCodec = new ForceRestartCustomAppCodec();
      }

      return forceRestartCustomAppCodec;
   }

   @Override
   protected void initializeDeviceSettings() {
      Map<ForceRestartCustomApp, Byte> domainTouch = new EnumMap<>(ForceRestartCustomApp.class);

      for (ForceRestartCustomApp forceRestartCustomApp : ForceRestartCustomApp.values()) {
         domainTouch.put(forceRestartCustomApp, (byte)forceRestartCustomApp.ordinal());
      }

      super.setDeviceSettings(domainTouch);
   }
}
