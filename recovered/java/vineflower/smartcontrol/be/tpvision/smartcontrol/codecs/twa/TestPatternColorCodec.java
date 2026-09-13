package be.tpvision.smartcontrol.codecs.twa;

import be.tpvision.smartcontrol.domain.twa.TestPatternColor;
import java.util.Map;

public class TestPatternColorCodec extends SingleValueCodec<TestPatternColor> {
   static final byte RED_BYTE = 1;
   static final byte GREEN_BYTE = 2;
   static final byte BLUE_BYTE = 3;
   static final byte WHITE_BYTE = 4;
   static final byte BLACK_BYTE = 5;
   private static TestPatternColorCodec testPatternColorCodec;

   private TestPatternColorCodec() {
      super(TestPatternColor.class);
      Map<TestPatternColor, Byte> domainTestPatternColor = super.getDeviceSettings();
      domainTestPatternColor.put(TestPatternColor.RED, (byte)1);
      domainTestPatternColor.put(TestPatternColor.GREEN, (byte)2);
      domainTestPatternColor.put(TestPatternColor.BLUE, (byte)3);
      domainTestPatternColor.put(TestPatternColor.WHITE, (byte)4);
      domainTestPatternColor.put(TestPatternColor.BLACK, (byte)5);
      super.setDeviceSettings(domainTestPatternColor);
   }

   public static synchronized TestPatternColorCodec getInstance() {
      if (testPatternColorCodec == null) {
         testPatternColorCodec = new TestPatternColorCodec();
      }

      return testPatternColorCodec;
   }

   public TestPatternColor toDomain(final byte[] bytes) {
      throw new UnsupportedOperationException("Test pattern color codec toDomain() is not supported.");
   }
}
