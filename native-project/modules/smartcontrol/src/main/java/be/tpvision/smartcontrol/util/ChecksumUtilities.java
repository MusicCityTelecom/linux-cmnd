package be.tpvision.smartcontrol.util;

import java.nio.ByteBuffer;

public class ChecksumUtilities {
   private ChecksumUtilities() {
   }

   public static byte xorChecksum(final byte[] data) {
      byte result = data[0];

      for (int i = 1; i < data.length; i++) {
         result ^= data[i];
      }

      return result;
   }

   public static byte xorChecksum(final ByteBuffer byteBuffer) {
      byte[] byteArray = byteBuffer.array();
      return xorChecksum(byteArray);
   }

   public static byte modChecksum(final byte[] data) {
      int result = data[0];

      for (int i = 1; i < data.length; i++) {
         result += data[i];
      }

      result %= 256;
      return (byte)result;
   }

   public static byte modChecksum(final ByteBuffer byteBuffer) {
      byte[] byteArray = byteBuffer.array();
      return modChecksum(byteArray);
   }
}
