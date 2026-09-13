package be.tpvision.smartcontrol.codecs.sicp208.general;

import be.tpvision.smartcontrol.codecs.sicp.SingleValueCodec;
import be.tpvision.smartcontrol.domain.device_settings.general.OpsSdmSettings;
import java.util.EnumMap;
import java.util.Map;

public class OpsSdmSettingsCodec extends SingleValueCodec<OpsSdmSettings> {
   private static OpsSdmSettingsCodec opsSdmSettingsCodec;

   private OpsSdmSettingsCodec() {
      super(OpsSdmSettings.class);
   }

   public static synchronized OpsSdmSettingsCodec getInstance() {
      if (opsSdmSettingsCodec == null) {
         opsSdmSettingsCodec = new OpsSdmSettingsCodec();
      }

      return opsSdmSettingsCodec;
   }

   @Override
   protected void initializeDeviceSettings() {
      Map<OpsSdmSettings, Byte> domainTouch = new EnumMap<>(OpsSdmSettings.class);

      for (OpsSdmSettings opsSdmSettings : OpsSdmSettings.values()) {
         domainTouch.put(opsSdmSettings, (byte)opsSdmSettings.ordinal());
      }

      super.setDeviceSettings(domainTouch);
   }
}
