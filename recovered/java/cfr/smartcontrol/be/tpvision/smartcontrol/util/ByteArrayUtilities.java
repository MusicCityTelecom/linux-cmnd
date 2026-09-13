/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.util;

public class ByteArrayUtilities {
    private static final char[] hexArray = "0123456789ABCDEF".toCharArray();

    private ByteArrayUtilities() {
    }

    public static String bytesToHex(byte[] bytes) {
        if (bytes == null) {
            return "";
        }
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; ++j) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0xF];
        }
        return new String(hexChars);
    }

    public static String newBytesToHex(byte[] bytes) {
        if (bytes == null) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < bytes.length; ++i) {
            boolean isLastByte;
            byte currentByte = bytes[i];
            String hex = String.format("0x%02X", currentByte);
            stringBuilder.append(hex);
            int lastByteIndex = bytes.length - 1;
            boolean bl = isLastByte = i == lastByteIndex;
            if (isLastByte) continue;
            stringBuilder.append(" ");
        }
        return stringBuilder.toString();
    }
}

