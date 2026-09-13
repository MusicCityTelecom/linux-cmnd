/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.util;

import java.nio.ByteBuffer;

public class ChecksumUtilities {
    private ChecksumUtilities() {
    }

    public static byte xorChecksum(byte[] data) {
        byte result = data[0];
        for (int i = 1; i < data.length; ++i) {
            result = (byte)(result ^ data[i]);
        }
        return result;
    }

    public static byte xorChecksum(ByteBuffer byteBuffer) {
        byte[] byteArray = byteBuffer.array();
        return ChecksumUtilities.xorChecksum(byteArray);
    }

    public static byte modChecksum(byte[] data) {
        int result = data[0];
        for (int i = 1; i < data.length; ++i) {
            result += data[i];
        }
        return (byte)(result %= 256);
    }

    public static byte modChecksum(ByteBuffer byteBuffer) {
        byte[] byteArray = byteBuffer.array();
        return ChecksumUtilities.modChecksum(byteArray);
    }
}

