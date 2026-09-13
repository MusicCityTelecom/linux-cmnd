/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal.guava;

import java.util.Arrays;

final class Ints {
    public static final int MAX_POWER_OF_TWO = 0x40000000;
    private static final byte[] asciiDigits;

    private Ints() {
    }

    public static int saturatedCast(long value) {
        if (value > Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        }
        if (value < Integer.MIN_VALUE) {
            return Integer.MIN_VALUE;
        }
        return (int)value;
    }

    static {
        int i;
        asciiDigits = new byte[128];
        Arrays.fill(asciiDigits, (byte)-1);
        for (i = 0; i <= 9; ++i) {
            Ints.asciiDigits[48 + i] = (byte)i;
        }
        for (i = 0; i <= 26; ++i) {
            Ints.asciiDigits[65 + i] = (byte)(10 + i);
            Ints.asciiDigits[97 + i] = (byte)(10 + i);
        }
    }
}

