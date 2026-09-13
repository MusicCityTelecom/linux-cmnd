package be.tpvision.smartcontrol.codecs.sicp188.miscellaneous;

import be.tpvision.smartcontrol.codecs.sicp.SingleValueCodec;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.ScanMode;
import java.util.EnumMap;
import java.util.Map;

public class ScanModeCodec extends SingleValueCodec<ScanMode> {
   static final byte OVERSCAN_BYTE = 0;
   static final byte UNDERSCAN_BYTE = 1;
   static final byte OFF_BYTE = 2;
   private static ScanModeCodec scanModeCodec;

   private ScanModeCodec() {
      super(ScanMode.class);
   }

   public static synchronized ScanModeCodec getInstance() {
      if (scanModeCodec == null) {
         scanModeCodec = new ScanModeCodec();
      }

      return scanModeCodec;
   }

   @Override
   protected void initializeDeviceSettings() {
      Map<ScanMode, Byte> domainScanMode = new EnumMap<>(ScanMode.class);
      domainScanMode.put(ScanMode.OVERSCAN, (byte)0);
      domainScanMode.put(ScanMode.UNDERSCAN, (byte)1);
      domainScanMode.put(ScanMode.OFF, (byte)2);
      super.setDeviceSettings(domainScanMode);
   }
}
