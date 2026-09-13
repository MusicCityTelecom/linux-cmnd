/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.message.internal;

final class GrammarUtil {
    public static final int TOKEN = 0;
    public static final int QUOTED_STRING = 1;
    public static final int COMMENT = 2;
    public static final int SEPARATOR = 3;
    public static final int CONTROL = 4;
    private static final char[] WHITE_SPACE = new char[]{'\t', '\r', '\n', ' '};
    private static final char[] SEPARATORS = new char[]{'(', ')', '<', '>', '@', ',', ';', ':', '\\', '\"', '/', '[', ']', '?', '=', '{', '}', ' ', '\t'};
    private static final int[] TYPE_TABLE = GrammarUtil.createEventTable();
    private static final boolean[] IS_WHITE_SPACE = GrammarUtil.createWhiteSpaceTable();
    private static final boolean[] IS_TOKEN = GrammarUtil.createTokenTable();

    private static int[] createEventTable() {
        int i;
        int[] table = new int[128];
        int control_char_bound = 32;
        for (i = 0; i < 32; ++i) {
            table[i] = 4;
        }
        table[127] = 4;
        for (i = 32; i < 127; ++i) {
            table[i] = 0;
        }
        for (char c : SEPARATORS) {
            table[c] = 3;
        }
        table[40] = 2;
        table[34] = 1;
        for (char c : WHITE_SPACE) {
            table[c] = -1;
        }
        return table;
    }

    private static boolean[] createWhiteSpaceTable() {
        boolean[] table = new boolean[128];
        for (char c : WHITE_SPACE) {
            table[c] = true;
        }
        return table;
    }

    private static boolean[] createTokenTable() {
        boolean[] table = new boolean[128];
        for (int i = 0; i <= 127; ++i) {
            table[i] = TYPE_TABLE[i] == 0;
        }
        return table;
    }

    public static boolean isWhiteSpace(char c) {
        return c <= '\u007f' && IS_WHITE_SPACE[c];
    }

    public static boolean isToken(char c) {
        return c <= '\u007f' && IS_TOKEN[c];
    }

    public static int getType(char c) {
        if (c > '\u007f') {
            throw new IllegalArgumentException("Unsupported character - ordinal value too high: " + c);
        }
        return TYPE_TABLE[c];
    }

    public static boolean isSeparator(char c) {
        return c <= '\u007f' && TYPE_TABLE[c] == 3;
    }

    public static boolean isTokenString(String s) {
        for (char c : s.toCharArray()) {
            if (GrammarUtil.isToken(c)) continue;
            return false;
        }
        return true;
    }

    public static boolean containsWhiteSpace(String s) {
        for (char c : s.toCharArray()) {
            if (!GrammarUtil.isWhiteSpace(c)) continue;
            return true;
        }
        return false;
    }

    public static String filterToken(CharSequence s, int start, int end) {
        return GrammarUtil.filterToken(s, start, end, false);
    }

    public static String filterToken(CharSequence s, int start, int end, boolean preserveBackslash) {
        StringBuilder sb = new StringBuilder();
        boolean gotEscape = false;
        boolean gotCR = false;
        for (int i = start; i < end; ++i) {
            char c = s.charAt(i);
            if (c == '\n' && gotCR) {
                gotCR = false;
                continue;
            }
            gotCR = false;
            if (!gotEscape) {
                if (!preserveBackslash && c == '\\') {
                    gotEscape = true;
                    continue;
                }
                if (c == '\r') {
                    gotCR = true;
                    continue;
                }
                sb.append(c);
                continue;
            }
            sb.append(c);
            gotEscape = false;
        }
        return sb.toString();
    }

    private GrammarUtil() {
    }
}

