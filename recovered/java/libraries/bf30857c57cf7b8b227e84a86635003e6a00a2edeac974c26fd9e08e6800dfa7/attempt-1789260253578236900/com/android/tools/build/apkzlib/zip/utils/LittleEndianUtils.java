/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.apkzlib.zip.utils;

import com.google.common.base.Preconditions;
import com.google.common.base.Verify;
import java.io.EOFException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class LittleEndianUtils {
    private LittleEndianUtils() {
    }

    public static long readUnsigned8Le(ByteBuffer bytes) throws IOException {
        Preconditions.checkNotNull(bytes, "bytes == null");
        if (bytes.remaining() < 8) {
            throw new EOFException("Not enough data: 8 bytes expected, " + bytes.remaining() + " available.");
        }
        ByteOrder order = bytes.order();
        bytes.order(ByteOrder.LITTLE_ENDIAN);
        long r3 = bytes.getLong();
        bytes.order(order);
        return r3;
    }

    public static long readUnsigned4Le(ByteBuffer bytes) throws IOException {
        byte b3;
        byte b2;
        byte b12;
        Preconditions.checkNotNull(bytes, "bytes == null");
        if (bytes.remaining() < 4) {
            throw new EOFException("Not enough data: 4 bytes expected, " + bytes.remaining() + " available.");
        }
        byte b02 = bytes.get();
        long r3 = (long)(b02 & 0xFF | ((b12 = bytes.get()) & 0xFF) << 8 | ((b2 = bytes.get()) & 0xFF) << 16) | ((long)(b3 = bytes.get()) & 0xFFL) << 24;
        Verify.verify(r3 >= 0L);
        Verify.verify(r3 <= 0xFFFFFFFFL);
        return r3;
    }

    public static int readUnsigned2Le(ByteBuffer bytes) throws IOException {
        byte b12;
        Preconditions.checkNotNull(bytes, "bytes == null");
        if (bytes.remaining() < 2) {
            throw new EOFException("Not enough data: 2 bytes expected, " + bytes.remaining() + " available.");
        }
        byte b02 = bytes.get();
        int r3 = b02 & 0xFF | ((b12 = bytes.get()) & 0xFF) << 8;
        Verify.verify(r3 >= 0);
        Verify.verify(r3 <= 65535);
        return r3;
    }

    public static void writeUnsigned8Le(ByteBuffer output, long value) throws IOException {
        Preconditions.checkNotNull(output, "output == null");
        ByteOrder order = output.order();
        output.order(ByteOrder.LITTLE_ENDIAN);
        output.putLong(value);
        output.order(order);
    }

    public static void writeUnsigned4Le(ByteBuffer output, long value) throws IOException {
        Preconditions.checkNotNull(output, "output == null");
        Preconditions.checkArgument(value >= 0L, "value (%s) < 0", value);
        Preconditions.checkArgument(value <= 0xFFFFFFFFL, "value (%s) > 0x00000000ffffffffL", value);
        output.put((byte)(value & 0xFFL));
        output.put((byte)(value >> 8 & 0xFFL));
        output.put((byte)(value >> 16 & 0xFFL));
        output.put((byte)(value >> 24 & 0xFFL));
    }

    public static void writeUnsigned2Le(ByteBuffer output, int value) throws IOException {
        Preconditions.checkNotNull(output, "output == null");
        Preconditions.checkArgument(value >= 0, "value (%s) < 0", value);
        Preconditions.checkArgument(value <= 65535, "value (%s) > 0x0000ffff", value);
        output.put((byte)(value & 0xFF));
        output.put((byte)(value >> 8 & 0xFF));
    }
}

