/*
 * Decompiled with CFR 0.152.
 */
package com.fasterxml.jackson.dataformat.javaprop.io;

import java.util.Arrays;

public class JPropEscapes {
    private static final char[] HEX = "0123456789ABCDEF".toCharArray();
    private static final int UNICODE_ESCAPE = -1;
    private static final int[] sValueEscapes;
    private static final int[] sKeyEscapes;

    public static void appendKey(StringBuilder sb, String key) {
        char c;
        int end = key.length();
        if (end == 0) {
            return;
        }
        int[] esc = sKeyEscapes;
        int i = 0;
        while ((c = key.charAt(i)) <= '\u00ff' && esc[c] == 0) {
            sb.append(c);
            if (++i != end) continue;
            return;
        }
        JPropEscapes._appendWithEscapes(sb, key, esc, i);
    }

    public static StringBuilder appendValue(String value) {
        char c;
        int end = value.length();
        if (end == 0) {
            return null;
        }
        int[] esc = sValueEscapes;
        int i = 0;
        while ((c = value.charAt(i)) <= '\u00ff' && esc[c] == 0) {
            if (++i != end) continue;
            return null;
        }
        StringBuilder sb = new StringBuilder(end + 5 + (end >> 3));
        for (int j = 0; j < i; ++j) {
            sb.append(value.charAt(j));
        }
        JPropEscapes._appendWithEscapes(sb, value, esc, i);
        return sb;
    }

    private static void _appendWithEscapes(StringBuilder sb, String key, int[] esc, int i) {
        int end = key.length();
        do {
            char c;
            int type;
            int n = type = (c = key.charAt(i)) > '\u00ff' ? -1 : esc[c];
            if (type == 0) {
                sb.append(c);
                continue;
            }
            if (type == -1) {
                sb.append('\\');
                sb.append('u');
                sb.append(HEX[c >>> 12]);
                sb.append(HEX[c >> 8 & 0xF]);
                sb.append(HEX[c >> 4 & 0xF]);
                sb.append(HEX[c & 0xF]);
                continue;
            }
            sb.append('\\');
            sb.append((char)type);
        } while (++i < end);
    }

    static {
        int[] table = new int[256];
        for (int i = 0; i < 32; ++i) {
            table[i] = -1;
            table[128 + i] = -1;
        }
        table[127] = -1;
        table[9] = 116;
        table[13] = 114;
        table[10] = 110;
        table[92] = 92;
        sValueEscapes = table;
        table = Arrays.copyOf(sValueEscapes, 256);
        table[35] = 35;
        table[33] = 33;
        table[61] = 61;
        table[58] = 58;
        table[32] = 32;
        sKeyEscapes = table;
    }
}

