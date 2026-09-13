/*
 * Decompiled with CFR 0.152.
 */
package com.android.ddmlib.utils;

public final class ArrayHelper {
    public static void swap32bitsToArray(int value, byte[] dest, int offset) {
        dest[offset] = (byte)(value & 0xFF);
        dest[offset + 1] = (byte)((value & 0xFF00) >> 8);
        dest[offset + 2] = (byte)((value & 0xFF0000) >> 16);
        dest[offset + 3] = (byte)((value & 0xFF000000) >> 24);
    }

    public static int swap32bitFromArray(byte[] value, int offset) {
        int v3 = 0;
        v3 |= value[offset] & 0xFF;
        v3 |= (value[offset + 1] & 0xFF) << 8;
        v3 |= (value[offset + 2] & 0xFF) << 16;
        return v3 |= (value[offset + 3] & 0xFF) << 24;
    }

    public static int swapU16bitFromArray(byte[] value, int offset) {
        int v3 = 0;
        v3 |= value[offset] & 0xFF;
        return v3 |= (value[offset + 1] & 0xFF) << 8;
    }

    public static long swap64bitFromArray(byte[] value, int offset) {
        long v3 = 0L;
        v3 |= (long)value[offset] & 0xFFL;
        v3 |= ((long)value[offset + 1] & 0xFFL) << 8;
        v3 |= ((long)value[offset + 2] & 0xFFL) << 16;
        v3 |= ((long)value[offset + 3] & 0xFFL) << 24;
        v3 |= ((long)value[offset + 4] & 0xFFL) << 32;
        v3 |= ((long)value[offset + 5] & 0xFFL) << 40;
        v3 |= ((long)value[offset + 6] & 0xFFL) << 48;
        return v3 |= ((long)value[offset + 7] & 0xFFL) << 56;
    }
}

