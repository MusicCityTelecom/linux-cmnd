/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.misc;

import groovyjarjarantlr4.v4.runtime.misc.Interval;
import groovyjarjarantlr4.v4.runtime.misc.IntervalSet;
import java.util.Iterator;

public class CharSupport {
    public static int[] ANTLRLiteralEscapedCharValue = new int[255];
    public static String[] ANTLRLiteralCharValueEscape = new String[255];

    public static String getANTLRCharLiteralForChar(int c) {
        String result;
        if (c < 0) {
            result = "<INVALID>";
        } else {
            String charValueEscape;
            String string = charValueEscape = c < ANTLRLiteralCharValueEscape.length ? ANTLRLiteralCharValueEscape[c] : null;
            result = charValueEscape != null ? charValueEscape : (Character.UnicodeBlock.of((char)c) == Character.UnicodeBlock.BASIC_LATIN && !Character.isISOControl((char)c) ? (c == 92 ? "\\\\" : (c == 39 ? "\\'" : Character.toString((char)c))) : (c <= 65535 ? String.format("\\u%04X", c) : String.format("\\u{%06X}", c)));
        }
        return '\'' + result + '\'';
    }

    public static int getCharValueFromGrammarCharLiteral(String literal) {
        if (literal == null || literal.length() < 3) {
            return -1;
        }
        return CharSupport.getCharValueFromCharInGrammarLiteral(literal.substring(1, literal.length() - 1));
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public static String getStringFromGrammarStringLiteral(String literal) {
        StringBuilder buf = new StringBuilder();
        int i = 1;
        int n = literal.length() - 1;
        while (i < n) {
            int end;
            block11: {
                end = i + 1;
                if (literal.charAt(i) == '\\') {
                    end = i + 2;
                    if (i + 1 < n && literal.charAt(i + 1) == 'u') {
                        if (i + 2 < n && literal.charAt(i + 2) == '{') {
                            end = i + 3;
                            while (true) {
                                char charAt;
                                if (end + 1 > n) {
                                    return null;
                                }
                                if ((charAt = literal.charAt(end++)) != '}') {
                                    if (Character.isDigit(charAt) || charAt >= 'a' && charAt <= 'f') continue;
                                    if (charAt < 'A') return null;
                                    if (charAt > 'F') return null;
                                    continue;
                                }
                                break block11;
                                break;
                            }
                        }
                        for (end = i + 2; end < i + 6; ++end) {
                            if (end > n) {
                                return null;
                            }
                            char charAt = literal.charAt(end);
                            if (Character.isDigit(charAt) || charAt >= 'a' && charAt <= 'f') continue;
                            if (charAt < 'A') return null;
                            if (charAt <= 'F') continue;
                            return null;
                        }
                    }
                }
            }
            if (end > n) {
                return null;
            }
            String esc = literal.substring(i, end);
            int c = CharSupport.getCharValueFromCharInGrammarLiteral(esc);
            if (c == -1) {
                return null;
            }
            buf.appendCodePoint(c);
            i = end;
        }
        return buf.toString();
    }

    public static int getCharValueFromCharInGrammarLiteral(String cstr) {
        switch (cstr.length()) {
            case 1: {
                return cstr.charAt(0);
            }
            case 2: {
                if (cstr.charAt(0) != '\\') {
                    return -1;
                }
                char escChar = cstr.charAt(1);
                if (escChar == '\'') {
                    return escChar;
                }
                int charVal = ANTLRLiteralEscapedCharValue[escChar];
                if (charVal == 0) {
                    return -1;
                }
                return charVal;
            }
            case 6: {
                int endOff;
                int startOff;
                if (!cstr.startsWith("\\u")) {
                    return -1;
                }
                if (cstr.charAt(2) == '{') {
                    startOff = 3;
                    endOff = cstr.indexOf(125);
                } else {
                    startOff = 2;
                    endOff = cstr.length();
                }
                return CharSupport.parseHexValue(cstr, startOff, endOff);
            }
        }
        if (cstr.startsWith("\\u{")) {
            return CharSupport.parseHexValue(cstr, 3, cstr.indexOf(125));
        }
        return -1;
    }

    public static int parseHexValue(String cstr, int startOff, int endOff) {
        if (startOff < 0 || endOff < 0) {
            return -1;
        }
        String unicodeChars = cstr.substring(startOff, endOff);
        int result = -1;
        try {
            result = Integer.parseInt(unicodeChars, 16);
        }
        catch (NumberFormatException e) {
            // empty catch block
        }
        return result;
    }

    public static String capitalize(String s) {
        return Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }

    public static String getIntervalSetEscapedString(IntervalSet intervalSet) {
        StringBuilder buf = new StringBuilder();
        Iterator<Interval> iter = intervalSet.getIntervals().iterator();
        while (iter.hasNext()) {
            Interval interval = iter.next();
            buf.append(CharSupport.getRangeEscapedString(interval.a, interval.b));
            if (!iter.hasNext()) continue;
            buf.append(" | ");
        }
        return buf.toString();
    }

    public static String getRangeEscapedString(int codePointStart, int codePointEnd) {
        return codePointStart != codePointEnd ? CharSupport.getANTLRCharLiteralForChar(codePointStart) + ".." + CharSupport.getANTLRCharLiteralForChar(codePointEnd) : CharSupport.getANTLRCharLiteralForChar(codePointStart);
    }

    static {
        CharSupport.ANTLRLiteralEscapedCharValue[110] = 10;
        CharSupport.ANTLRLiteralEscapedCharValue[114] = 13;
        CharSupport.ANTLRLiteralEscapedCharValue[116] = 9;
        CharSupport.ANTLRLiteralEscapedCharValue[98] = 8;
        CharSupport.ANTLRLiteralEscapedCharValue[102] = 12;
        CharSupport.ANTLRLiteralEscapedCharValue[92] = 92;
        CharSupport.ANTLRLiteralCharValueEscape[10] = "\\n";
        CharSupport.ANTLRLiteralCharValueEscape[13] = "\\r";
        CharSupport.ANTLRLiteralCharValueEscape[9] = "\\t";
        CharSupport.ANTLRLiteralCharValueEscape[8] = "\\b";
        CharSupport.ANTLRLiteralCharValueEscape[12] = "\\f";
        CharSupport.ANTLRLiteralCharValueEscape[92] = "\\\\";
    }
}

