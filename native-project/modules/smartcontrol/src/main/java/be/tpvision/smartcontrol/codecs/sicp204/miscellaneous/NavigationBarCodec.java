package be.tpvision.smartcontrol.codecs.sicp204.miscellaneous;

import be.tpvision.smartcontrol.codecs.sicp.SingleValueCodec;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.NavigationBar;
import java.util.EnumMap;
import java.util.Map;

public class NavigationBarCodec extends SingleValueCodec<NavigationBar> {
   static final byte DISABLED_BYTE = 0;
   static final byte ENABLED_BYTE = 1;
   private static NavigationBarCodec navigationBarCodec;

   private NavigationBarCodec() {
      super(NavigationBar.class);
   }

   public static synchronized NavigationBarCodec getInstance() {
      if (navigationBarCodec == null) {
         navigationBarCodec = new NavigationBarCodec();
      }

      return navigationBarCodec;
   }

   @Override
   protected void initializeDeviceSettings() {
      Map<NavigationBar, Byte> domainNavigationBar = new EnumMap<>(NavigationBar.class);
      domainNavigationBar.put(NavigationBar.DISABLED, (byte)0);
      domainNavigationBar.put(NavigationBar.ENABLED, (byte)1);
      super.setDeviceSettings(domainNavigationBar);
   }
}
