/*
 * Decompiled with CFR 0.152.
 */
package org.apache.groovy.json.internal;

import java.math.BigDecimal;
import org.apache.groovy.json.internal.ArrayUtils;
import org.apache.groovy.json.internal.CharBuf;
import org.apache.groovy.json.internal.Exceptions;

public class CharScanner {
    protected static final int COMMA = 44;
    protected static final int CLOSED_CURLY = 125;
    protected static final int CLOSED_BRACKET = 93;
    protected static final int LETTER_E = 101;
    protected static final int LETTER_BIG_E = 69;
    protected static final int DECIMAL_POINT = 46;
    protected static final int ALPHA_0 = 48;
    protected static final int ALPHA_9 = 57;
    protected static final int MINUS = 45;
    protected static final int PLUS = 43;
    static final String MIN_LONG_STR_NO_SIGN = String.valueOf(Long.MIN_VALUE);
    static final String MAX_LONG_STR = String.valueOf(Long.MAX_VALUE);
    static final String MIN_INT_STR_NO_SIGN = String.valueOf(Integer.MIN_VALUE);
    static final String MAX_INT_STR = String.valueOf(Integer.MAX_VALUE);
    private static double[] powersOf10 = new double[]{1.0, 10.0, 100.0, 1000.0, 10000.0, 100000.0, 1000000.0, 1.0E7, 1.0E8, 1.0E9, 1.0E10, 1.0E11, 1.0E12, 1.0E13, 1.0E14, 1.0E15, 1.0E16, 1.0E17, 1.0E18};

    public static boolean isDigit(int c) {
        return c >= 48 && c <= 57;
    }

    public static boolean isDecimalDigit(int c) {
        return CharScanner.isDigit(c) || CharScanner.isDecimalChar(c);
    }

    public static boolean isDecimalChar(int currentChar) {
        switch (currentChar) {
            case 43: 
            case 45: 
            case 46: 
            case 69: 
            case 101: {
                return true;
            }
        }
        return false;
    }

    public static boolean hasDecimalChar(char[] chars, boolean negative) {
        int index = 0;
        if (negative) {
            ++index;
        }
        while (index < chars.length) {
            switch (chars[index]) {
                case '+': 
                case '-': 
                case '.': 
                case 'E': 
                case 'e': {
                    return true;
                }
            }
            ++index;
        }
        return false;
    }

    public static boolean isLong(char[] digitChars) {
        return CharScanner.isLong(digitChars, 0, digitChars.length);
    }

    public static boolean isLong(char[] digitChars, int offset, int len) {
        String cmpStr = digitChars[offset] == '-' ? MIN_LONG_STR_NO_SIGN : MAX_LONG_STR;
        int cmpLen = cmpStr.length();
        if (len < cmpLen) {
            return true;
        }
        if (len > cmpLen) {
            return false;
        }
        for (int i = 0; i < cmpLen; ++i) {
            int diff = digitChars[offset + i] - cmpStr.charAt(i);
            if (diff == 0) continue;
            return diff < 0;
        }
        return true;
    }

    public static boolean isInteger(char[] digitChars) {
        return CharScanner.isInteger(digitChars, 0, digitChars.length);
    }

    public static boolean isInteger(char[] digitChars, int offset, int len) {
        String cmpStr = digitChars[offset] == '-' ? MIN_INT_STR_NO_SIGN : MAX_INT_STR;
        int cmpLen = cmpStr.length();
        if (len < cmpLen) {
            return true;
        }
        if (len > cmpLen) {
            return false;
        }
        for (int i = 0; i < cmpLen; ++i) {
            int diff = digitChars[offset + i] - cmpStr.charAt(i);
            if (diff == 0) continue;
            return diff < 0;
        }
        return true;
    }

    public static int parseInt(char[] digitChars) {
        return CharScanner.parseIntFromTo(digitChars, 0, digitChars.length);
    }

    public static int parseIntFromTo(char[] digitChars, int offset, int to) {
        try {
            boolean negative = false;
            char c = digitChars[offset];
            if (c == '-') {
                ++offset;
                negative = true;
            }
            if (offset >= to) {
                Exceptions.die();
            }
            int num = digitChars[offset] - 48;
            if (++offset < to) {
                num = num * 10 + (digitChars[offset] - 48);
                if (++offset < to) {
                    num = num * 10 + (digitChars[offset] - 48);
                    if (++offset < to) {
                        num = num * 10 + (digitChars[offset] - 48);
                        if (++offset < to) {
                            num = num * 10 + (digitChars[offset] - 48);
                            if (++offset < to) {
                                num = num * 10 + (digitChars[offset] - 48);
                                if (++offset < to) {
                                    num = num * 10 + (digitChars[offset] - 48);
                                    if (++offset < to) {
                                        num = num * 10 + (digitChars[offset] - 48);
                                        if (++offset < to) {
                                            num = num * 10 + (digitChars[offset] - 48);
                                            if (++offset < to) {
                                                num = num * 10 + (digitChars[offset] - 48);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return negative ? num * -1 : num;
        }
        catch (Exception ex) {
            return Exceptions.handle(Integer.TYPE, ex);
        }
    }

    public static int parseIntFromToIgnoreDot(char[] digitChars, int offset, int to) {
        boolean negative = false;
        char c = digitChars[offset];
        if (c == '-') {
            ++offset;
            negative = true;
        }
        if (offset >= to) {
            Exceptions.die();
        }
        c = digitChars[offset];
        int num = c - 48;
        ++offset;
        while (offset < to) {
            c = digitChars[offset];
            if (c != '.') {
                num = num * 10 + (c - 48);
            }
            ++offset;
        }
        return negative ? num * -1 : num;
    }

    public static long parseLongFromToIgnoreDot(char[] digitChars, int offset, int to) {
        boolean negative = false;
        char c = digitChars[offset];
        if (c == '-') {
            ++offset;
            negative = true;
        }
        if (offset >= to) {
            Exceptions.die();
        }
        c = digitChars[offset];
        long num = c - 48;
        ++offset;
        while (offset < to) {
            c = digitChars[offset];
            if (c != '.') {
                num = num * 10L + (long)(c - 48);
            }
            ++offset;
        }
        return negative ? num * -1L : num;
    }

    public static long parseLongFromTo(char[] digitChars, int offset, int to) {
        boolean negative = false;
        char c = digitChars[offset];
        if (c == '-') {
            ++offset;
            negative = true;
        }
        if (offset >= to) {
            Exceptions.die();
        }
        c = digitChars[offset];
        long num = c - 48;
        ++offset;
        while (offset < to) {
            c = digitChars[offset];
            long digit = c - 48;
            num = num * 10L + digit;
            ++offset;
        }
        return negative ? num * -1L : num;
    }

    public static long parseLong(char[] digitChars) {
        return CharScanner.parseLongFromTo(digitChars, 0, digitChars.length);
    }

    public static Number parseJsonNumber(char[] buffer) {
        return CharScanner.parseJsonNumber(buffer, 0, buffer.length);
    }

    public static Number parseJsonNumber(char[] buffer, int from, int to) {
        return CharScanner.parseJsonNumber(buffer, from, to, null);
    }

    public static boolean isNumberDigit(int c) {
        return c >= 48 && c <= 57;
    }

    protected static boolean isDelimiter(int c) {
        return c == 44 || c == 125 || c == 93;
    }

    public static Number parseJsonNumber(char[] buffer, int from, int max, int[] size) {
        Number value = null;
        boolean simple = true;
        int digitsPastPoint = 0;
        int index = from;
        if (buffer[index] == '-') {
            ++index;
        }
        if (index >= max) {
            Exceptions.die();
        }
        boolean foundDot = false;
        while (index < max) {
            char ch = buffer[index];
            if (CharScanner.isNumberDigit(ch)) {
                if (foundDot) {
                    ++digitsPastPoint;
                }
            } else {
                if (ch <= ' ' || CharScanner.isDelimiter(ch)) break;
                if (ch == '.') {
                    if (foundDot) {
                        Exceptions.die("unexpected character " + ch);
                    }
                    foundDot = true;
                } else if (ch == 'E' || ch == 'e' || ch == '-' || ch == '+') {
                    simple = false;
                } else {
                    Exceptions.die("unexpected character " + ch);
                }
            }
            ++index;
        }
        if (digitsPastPoint >= powersOf10.length - 1) {
            simple = false;
        }
        int length = index - from;
        value = !foundDot && simple ? (CharScanner.isInteger(buffer, from, length) ? (Number)CharScanner.parseIntFromTo(buffer, from, index) : (Number)CharScanner.parseLongFromTo(buffer, from, index)) : new BigDecimal(buffer, from, length);
        if (size != null) {
            size[0] = index;
        }
        return value;
    }

    public static BigDecimal parseBigDecimal(char[] buffer) {
        return new BigDecimal(buffer);
    }

    public static float parseFloat(char[] buffer, int from, int to) {
        return (float)CharScanner.parseDouble(buffer, from, to);
    }

    public static double parseDouble(char[] buffer, int from, int to) {
        double value;
        boolean simple = true;
        int digitsPastPoint = 0;
        int index = from;
        if (buffer[index] == '-') {
            ++index;
        }
        boolean foundDot = false;
        while (index < to) {
            char ch = buffer[index];
            if (CharScanner.isNumberDigit(ch)) {
                if (foundDot) {
                    ++digitsPastPoint;
                }
            } else if (ch == '.') {
                if (foundDot) {
                    Exceptions.die("unexpected character " + ch);
                }
                foundDot = true;
            } else if (ch == 'E' || ch == 'e' || ch == '-' || ch == '+') {
                simple = false;
            } else {
                Exceptions.die("unexpected character " + ch);
            }
            ++index;
        }
        if (digitsPastPoint >= powersOf10.length - 1) {
            simple = false;
        }
        int length = index - from;
        if (!foundDot && simple) {
            value = CharScanner.isInteger(buffer, from, length) ? (double)CharScanner.parseIntFromTo(buffer, from, index) : (double)CharScanner.parseLongFromTo(buffer, from, index);
        } else if (foundDot && simple) {
            if (length < powersOf10.length) {
                long lvalue = CharScanner.isInteger(buffer, from, length) ? (long)CharScanner.parseIntFromToIgnoreDot(buffer, from, index) : CharScanner.parseLongFromToIgnoreDot(buffer, from, index);
                double power = powersOf10[digitsPastPoint];
                value = (double)lvalue / power;
            } else {
                value = Double.parseDouble(new String(buffer, from, length));
            }
        } else {
            value = Double.parseDouble(new String(buffer, from, index - from));
        }
        return value;
    }

    public static int skipWhiteSpace(char[] array, int index, int length) {
        while (index < length) {
            char c = array[index];
            if (c > ' ') {
                return index;
            }
            ++index;
        }
        return index;
    }

    public static char[] readNumber(char[] array, int idx, int len) {
        int startIndex = idx;
        while (CharScanner.isDecimalDigit(array[idx]) && ++idx < len) {
        }
        return ArrayUtils.copyRange(array, startIndex, idx);
    }

    public static String errorDetails(String message, char[] array, int index, int ch) {
        CharBuf buf = CharBuf.create(255);
        buf.addLine(message);
        buf.addLine("");
        buf.addLine("The current character read is " + CharScanner.debugCharDescription(ch));
        buf.addLine(message);
        int line = 0;
        int lastLineIndex = 0;
        for (int i = 0; i < index && i < array.length; ++i) {
            if (array[i] != '\n') continue;
            ++line;
            lastLineIndex = i + 1;
        }
        int count = 0;
        int i = lastLineIndex;
        while (i < array.length && array[i] != '\n') {
            ++i;
            ++count;
        }
        buf.addLine("line number " + (line + 1));
        buf.addLine("index number " + index);
        try {
            buf.addLine(new String(array, lastLineIndex, count));
        }
        catch (Exception ex) {
            try {
                int start = index = Math.max(index - 10, 0);
                buf.addLine(new String(array, start, index));
            }
            catch (Exception ex2) {
                buf.addLine(new String(array, 0, array.length));
            }
        }
        for (i = 0; i < index - lastLineIndex; ++i) {
            buf.add('.');
        }
        buf.add('^');
        return buf.toString();
    }

    public static String debugCharDescription(int c) {
        String charString = c == 32 ? "[SPACE]" : (c == 9 ? "[TAB]" : (c == 10 ? "[NEWLINE]" : "'" + (char)c + "'"));
        charString = charString + " with an int value of " + c;
        return charString;
    }
}

