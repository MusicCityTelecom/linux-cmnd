/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.jexl3.parser;

public class StringParser {
    private static final int UCHAR_LEN = 4;
    private static final int SHIFT = 12;
    private static final int BASE10 = 10;
    private static final char LAST_ASCII = '\u007f';
    private static final char FIRST_ASCII = ' ';

    public static String buildString(CharSequence str, boolean eatsep) {
        return StringParser.buildString(str, eatsep, true);
    }

    public static String buildTemplate(CharSequence str, boolean eatsep) {
        return StringParser.buildString(str, eatsep, false);
    }

    private static String buildString(CharSequence str, boolean eatsep, boolean esc) {
        StringBuilder strb = new StringBuilder(str.length());
        char sep = eatsep ? str.charAt(0) : (char)'\u0000';
        int end = str.length() - (eatsep ? 1 : 0);
        int begin = eatsep ? 1 : 0;
        StringParser.read(strb, str, begin, end, sep, esc);
        return strb.toString();
    }

    public static String buildRegex(CharSequence str) {
        return StringParser.buildString(str.subSequence(1, str.length()), true);
    }

    public static int readString(StringBuilder strb, CharSequence str, int index, char sep) {
        return StringParser.read(strb, str, index, str.length(), sep, true);
    }

    private static int read(StringBuilder strb, CharSequence str, int begin, int end, char sep, boolean esc) {
        int index;
        boolean escape = false;
        for (index = begin; index < end; ++index) {
            char c = str.charAt(index);
            if (escape) {
                if (c == 'u' && index + 4 < end && StringParser.readUnicodeChar(strb, str, index + 1) > 0) {
                    index += 4;
                } else {
                    boolean notSeparator;
                    boolean bl = sep == '\u0000' ? c != '\'' && c != '\"' : (notSeparator = c != sep);
                    if (notSeparator && c != '\\') {
                        if (!esc) {
                            strb.append('\\').append(c);
                        } else {
                            switch (c) {
                                case 'b': {
                                    strb.append('\b');
                                    break;
                                }
                                case 't': {
                                    strb.append('\t');
                                    break;
                                }
                                case 'n': {
                                    strb.append('\n');
                                    break;
                                }
                                case 'f': {
                                    strb.append('\f');
                                    break;
                                }
                                case 'r': {
                                    strb.append('\r');
                                    break;
                                }
                                default: {
                                    strb.append('\\').append(c);
                                    break;
                                }
                            }
                        }
                    } else {
                        strb.append(c);
                    }
                }
                escape = false;
                continue;
            }
            if (c == '\\') {
                escape = true;
                continue;
            }
            strb.append(c);
            if (c == sep) break;
        }
        return index;
    }

    private static int readUnicodeChar(StringBuilder strb, CharSequence str, int begin) {
        char xc = '\u0000';
        int bits = 12;
        int value = 0;
        for (int offset = 0; offset < 4; ++offset) {
            char c = str.charAt(begin + offset);
            if (c >= '0' && c <= '9') {
                value = c - 48;
            } else if (c >= 'a' && c <= 'h') {
                value = c - 97 + 10;
            } else if (c >= 'A' && c <= 'H') {
                value = c - 65 + 10;
            } else {
                return 0;
            }
            xc = (char)(xc | value << bits);
            bits -= 4;
        }
        strb.append(xc);
        return 4;
    }

    public static String escapeString(String str, char delim) {
        if (str == null) {
            return null;
        }
        int length = str.length();
        StringBuilder strb = new StringBuilder(length + 2);
        strb.append(delim);
        block11: for (int i = 0; i < length; ++i) {
            char c = str.charAt(i);
            switch (c) {
                case '\u0000': {
                    continue block11;
                }
                case '\b': {
                    strb.append("\\b");
                    continue block11;
                }
                case '\t': {
                    strb.append("\\t");
                    continue block11;
                }
                case '\n': {
                    strb.append("\\n");
                    continue block11;
                }
                case '\f': {
                    strb.append("\\f");
                    continue block11;
                }
                case '\r': {
                    strb.append("\\r");
                    continue block11;
                }
                case '\"': {
                    strb.append("\\\"");
                    continue block11;
                }
                case '\'': {
                    strb.append("\\'");
                    continue block11;
                }
                case '\\': {
                    strb.append("\\\\");
                    continue block11;
                }
                default: {
                    if (c >= ' ' && c <= '\u007f') {
                        strb.append(c);
                        continue block11;
                    }
                    strb.append('\\');
                    strb.append('u');
                    String hex = Integer.toHexString(c);
                    for (int h = hex.length(); h < 4; ++h) {
                        strb.append('0');
                    }
                    strb.append(hex);
                }
            }
        }
        strb.append(delim);
        return strb.toString();
    }

    public static String unescapeIdentifier(String str) {
        StringBuilder strb = null;
        if (str != null) {
            int last = str.length();
            for (int n = 0; n < last; ++n) {
                char c = str.charAt(n);
                if (c == '\\') {
                    if (strb != null) continue;
                    strb = new StringBuilder(last);
                    strb.append(str.substring(0, n));
                    continue;
                }
                if (strb == null) continue;
                strb.append(c);
            }
        }
        return strb == null ? str : strb.toString();
    }

    public static String escapeIdentifier(String str) {
        StringBuilder strb = null;
        if (str != null) {
            int last = str.length();
            block3: for (int n = 0; n < last; ++n) {
                char c = str.charAt(n);
                switch (c) {
                    case ' ': 
                    case '\"': 
                    case '\'': 
                    case '\\': {
                        if (strb == null) {
                            strb = new StringBuilder(last);
                            strb.append(str.substring(0, n));
                        }
                        strb.append('\\');
                        strb.append(c);
                        continue block3;
                    }
                    default: {
                        if (strb == null) continue block3;
                        strb.append(c);
                    }
                }
            }
        }
        return strb == null ? str : strb.toString();
    }
}

