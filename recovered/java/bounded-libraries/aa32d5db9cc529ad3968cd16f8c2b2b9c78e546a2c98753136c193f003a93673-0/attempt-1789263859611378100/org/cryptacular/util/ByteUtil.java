/*
 * Decompiled with CFR 0.152.
 */
package org.cryptacular.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import org.cryptacular.StreamException;

public final class ByteUtil {
    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
    public static final Charset ASCII_CHARSET = Charset.forName("ASCII");

    private ByteUtil() {
    }

    public static int toInt(byte[] data) {
        return data[0] << 24 | (data[1] & 0xFF) << 16 | (data[2] & 0xFF) << 8 | data[3] & 0xFF;
    }

    public static int toInt(byte unsigned) {
        return 0xFF & unsigned;
    }

    public static int readInt(InputStream in) throws StreamException {
        try {
            return in.read() << 24 | (in.read() & 0xFF) << 16 | (in.read() & 0xFF) << 8 | in.read() & 0xFF;
        }
        catch (IOException e) {
            throw new StreamException(e);
        }
    }

    public static long toLong(byte[] data) {
        return (long)data[0] << 56 | ((long)data[1] & 0xFFL) << 48 | ((long)data[2] & 0xFFL) << 40 | ((long)data[3] & 0xFFL) << 32 | ((long)data[4] & 0xFFL) << 24 | ((long)data[5] & 0xFFL) << 16 | ((long)data[6] & 0xFFL) << 8 | (long)data[7] & 0xFFL;
    }

    public static long readLong(InputStream in) throws StreamException {
        try {
            return (long)in.read() << 56 | ((long)in.read() & 0xFFL) << 48 | ((long)in.read() & 0xFFL) << 40 | ((long)in.read() & 0xFFL) << 32 | ((long)in.read() & 0xFFL) << 24 | ((long)in.read() & 0xFFL) << 16 | ((long)in.read() & 0xFFL) << 8 | (long)in.read() & 0xFFL;
        }
        catch (IOException e) {
            throw new StreamException(e);
        }
    }

    public static byte[] toBytes(int value) {
        byte[] bytes = new byte[4];
        ByteUtil.toBytes(value, bytes, 0);
        return bytes;
    }

    public static void toBytes(int value, byte[] output, int offset) {
        int shift = 24;
        for (int i = 0; i < 4; ++i) {
            output[offset + i] = (byte)(value >> shift);
            shift -= 8;
        }
    }

    public static byte[] toBytes(long value) {
        byte[] bytes = new byte[8];
        ByteUtil.toBytes(value, bytes, 0);
        return bytes;
    }

    public static void toBytes(long value, byte[] output, int offset) {
        int shift = 56;
        for (int i = 0; i < 8; ++i) {
            output[offset + i] = (byte)(value >> shift);
            shift -= 8;
        }
    }

    public static String toString(byte[] bytes) {
        return new String(bytes, DEFAULT_CHARSET);
    }

    public static String toString(byte[] bytes, int offset, int length) {
        return new String(bytes, offset, length, DEFAULT_CHARSET);
    }

    public static String toString(ByteBuffer buffer) {
        return ByteUtil.toCharBuffer(buffer).toString();
    }

    public static CharBuffer toCharBuffer(ByteBuffer buffer) {
        return DEFAULT_CHARSET.decode(buffer);
    }

    public static ByteBuffer toByteBuffer(String s) {
        return DEFAULT_CHARSET.encode(CharBuffer.wrap(s));
    }

    public static byte[] toBytes(String s) {
        return s.getBytes(DEFAULT_CHARSET);
    }

    public static byte toUnsignedByte(int b) {
        return (byte)(0xFF & b);
    }

    public static byte[] toArray(ByteBuffer buffer) {
        int size = buffer.limit() - buffer.position();
        if (buffer.hasArray() && size == buffer.capacity()) {
            return buffer.array();
        }
        byte[] array = new byte[size];
        buffer.get(array);
        return array;
    }
}

