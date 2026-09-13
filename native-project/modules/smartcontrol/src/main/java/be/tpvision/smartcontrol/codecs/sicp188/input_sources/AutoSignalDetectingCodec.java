package be.tpvision.smartcontrol.codecs.sicp188.input_sources;

import be.tpvision.smartcontrol.codecs.sicp.SingleValueCodec;
import be.tpvision.smartcontrol.domain.device_settings.input_sources.AutoSignalDetecting;
import java.util.EnumMap;
import java.util.Map;

public class AutoSignalDetectingCodec extends SingleValueCodec<AutoSignalDetecting> {
   static final byte OFF_BYTE = 0;
   static final byte ALL_BYTE = 1;
   static final byte RESERVED_BYTE = 2;
   static final byte PC_ONLY_BYTE = 3;
   static final byte VIDEO_ONLY_BYTE = 4;
   static final byte FAILOVER_BYTE = 5;
   private static AutoSignalDetectingCodec autoSignalDetectingCodec;

   private AutoSignalDetectingCodec() {
      super(AutoSignalDetecting.class);
   }

   public static synchronized AutoSignalDetectingCodec getInstance() {
      if (autoSignalDetectingCodec == null) {
         autoSignalDetectingCodec = new AutoSignalDetectingCodec();
      }

      return autoSignalDetectingCodec;
   }

   @Override
   protected void initializeDeviceSettings() {
      Map<AutoSignalDetecting, Byte> domainAutoSignalDetecting = new EnumMap<>(AutoSignalDetecting.class);
      domainAutoSignalDetecting.put(AutoSignalDetecting.OFF, (byte)0);
      domainAutoSignalDetecting.put(AutoSignalDetecting.ALL, (byte)1);
      domainAutoSignalDetecting.put(AutoSignalDetecting.RESERVED, (byte)2);
      domainAutoSignalDetecting.put(AutoSignalDetecting.PC_ONLY, (byte)3);
      domainAutoSignalDetecting.put(AutoSignalDetecting.VIDEO_ONLY, (byte)4);
      domainAutoSignalDetecting.put(AutoSignalDetecting.FAILOVER, (byte)5);
      super.setDeviceSettings(domainAutoSignalDetecting);
   }
}
