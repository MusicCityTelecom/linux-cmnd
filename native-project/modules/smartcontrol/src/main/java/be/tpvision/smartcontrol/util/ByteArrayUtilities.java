package be.tpvision.smartcontrol.util;

public class ByteArrayUtilities {
   private static final char[] hexArray = "0123456789ABCDEF".toCharArray();

   private ByteArrayUtilities() {
   }

   public static String bytesToHex(final byte[] bytes) {
      if (bytes == null) {
         return "";
      }

      char[] hexChars = new char[bytes.length * 2];

      for (int j = 0; j < bytes.length; j++) {
         int v = bytes[j] & 255;
         hexChars[j * 2] = hexArray[v >>> 4];
         hexChars[j * 2 + 1] = hexArray[v & 15];
      }

      return new String(hexChars);
   }

   public static String newBytesToHex(final byte[] bytes) {
      if (bytes == null) {
         return "";
      }

      StringBuilder stringBuilder = new StringBuilder();

      for (int i = 0; i < bytes.length; i++) {
         byte currentByte = bytes[i];
         String hex = String.format("0x%02X", currentByte);
         stringBuilder.append(hex);
         int lastByteIndex = bytes.length - 1;
         boolean isLastByte = i == lastByteIndex;
         if (!isLastByte) {
            stringBuilder.append(" ");
         }
      }

      return stringBuilder.toString();
   }
}
