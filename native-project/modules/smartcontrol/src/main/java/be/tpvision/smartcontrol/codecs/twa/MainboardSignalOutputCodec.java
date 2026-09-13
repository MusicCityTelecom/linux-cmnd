package be.tpvision.smartcontrol.codecs.twa;

import be.tpvision.smartcontrol.domain.twa.MainboardSignalOutput;
import java.util.Map;

public class MainboardSignalOutputCodec extends SingleValueCodec<MainboardSignalOutput> {
   static final byte ON_BYTE = 0;
   static final byte OFF_BYTE = 1;
   private static MainboardSignalOutputCodec mainboardSignalOutputCodec;

   private MainboardSignalOutputCodec() {
      super(MainboardSignalOutput.class);
      Map<MainboardSignalOutput, Byte> domainMainboardSignalOutput = super.getDeviceSettings();
      domainMainboardSignalOutput.put(MainboardSignalOutput.ON, (byte)0);
      domainMainboardSignalOutput.put(MainboardSignalOutput.OFF, (byte)1);
      super.setDeviceSettings(domainMainboardSignalOutput);
   }

   public static synchronized MainboardSignalOutputCodec getInstance() {
      if (mainboardSignalOutputCodec == null) {
         mainboardSignalOutputCodec = new MainboardSignalOutputCodec();
      }

      return mainboardSignalOutputCodec;
   }

   public MainboardSignalOutput toDomain(final byte[] bytes) {
      throw new UnsupportedOperationException("Mainboard signal output codec toDomain() is not supported.");
   }
}
