/*
 * Decompiled with CFR 0.152.
 */
package org.apache.groovy.parser.antlr4.util;

import groovy.lang.Closure;
import java.util.Map;
import java.util.regex.Pattern;
import org.apache.groovy.util.Maps;
import org.codehaus.groovy.runtime.StringGroovyMethods;

public class StringUtils {
    private static final String BACKSLASH = "\\";
    private static final Pattern HEX_ESCAPES_PATTERN = Pattern.compile("(\\\\*)\\\\u([0-9abcdefABCDEF]{4})");
    private static final Pattern OCTAL_ESCAPES_PATTERN = Pattern.compile("(\\\\*)\\\\([0-3]?[0-7]?[0-7])");
    private static final Pattern STANDARD_ESCAPES_PATTERN = Pattern.compile("(\\\\*)\\\\([btnfrs\"'])");
    private static final Pattern LINE_ESCAPE_PATTERN = Pattern.compile("(\\\\*)\\\\\r?\n");
    private static final Map<Character, Character> STANDARD_ESCAPES = Maps.of(Character.valueOf('b'), Character.valueOf('\b'), Character.valueOf('t'), Character.valueOf('\t'), Character.valueOf('n'), Character.valueOf('\n'), Character.valueOf('f'), Character.valueOf('\f'), Character.valueOf('r'), Character.valueOf('\r'), Character.valueOf('s'), Character.valueOf(' '));
    public static final int NONE_SLASHY = 0;
    public static final int SLASHY = 1;
    public static final int DOLLAR_SLASHY = 2;
    private static final int INDEX_NOT_FOUND = -1;

    public static String replaceHexEscapes(String text) {
        if (!text.contains(BACKSLASH)) {
            return text;
        }
        return StringGroovyMethods.replaceAll((CharSequence)text, HEX_ESCAPES_PATTERN, (Closure)new Closure<Void>(null, null){

            Object doCall(String _0, String _1, String _2) {
                if (StringUtils.isLengthOdd(_1)) {
                    return _0;
                }
                return _1 + new String(Character.toChars(Integer.parseInt(_2, 16)));
            }
        });
    }

    public static String replaceOctalEscapes(String text) {
        if (!text.contains(BACKSLASH)) {
            return text;
        }
        return StringGroovyMethods.replaceAll((CharSequence)text, OCTAL_ESCAPES_PATTERN, (Closure)new Closure<Void>(null, null){

            Object doCall(String _0, String _1, String _2) {
                if (StringUtils.isLengthOdd(_1)) {
                    return _0;
                }
                return _1 + new String(Character.toChars(Integer.parseInt(_2, 8)));
            }
        });
    }

    public static String replaceStandardEscapes(String text) {
        if (!text.contains(BACKSLASH)) {
            return text;
        }
        String result = StringGroovyMethods.replaceAll((CharSequence)text, STANDARD_ESCAPES_PATTERN, (Closure)new Closure<Void>(null, null){

            Object doCall(String _0, String _1, String _2) {
                if (StringUtils.isLengthOdd(_1)) {
                    return _0;
                }
                Character character = (Character)STANDARD_ESCAPES.get(Character.valueOf(_2.charAt(0)));
                return _1 + (character != null ? character : _2);
            }
        });
        return StringUtils.replace(result, "\\\\", BACKSLASH);
    }

    public static String replaceEscapes(String text, int slashyType) {
        if (slashyType == 1 || slashyType == 2) {
            text = StringUtils.replaceHexEscapes(text);
            text = StringUtils.replaceLineEscape(text);
            if (slashyType == 1) {
                text = StringUtils.replace(text, "\\/", "/");
            }
            if (slashyType == 2) {
                text = StringUtils.replace(text, "$/", "/");
                text = StringUtils.replace(text, "$$", "$");
            }
        } else if (slashyType == 0) {
            text = StringUtils.replaceEscapes(text);
        } else {
            throw new IllegalArgumentException("Invalid slashyType: " + slashyType);
        }
        return text;
    }

    private static String replaceEscapes(String text) {
        if (!text.contains(BACKSLASH)) {
            return text;
        }
        text = StringUtils.replace(text, "\\$", "$");
        text = StringUtils.replaceLineEscape(text);
        return StringUtils.replaceStandardEscapes(StringUtils.replaceHexEscapes(StringUtils.replaceOctalEscapes(text)));
    }

    private static String replaceLineEscape(String text) {
        if (!text.contains(BACKSLASH)) {
            return text;
        }
        text = StringGroovyMethods.replaceAll((CharSequence)text, LINE_ESCAPE_PATTERN, (Closure)new Closure<Void>(null, null){

            Object doCall(String _0, String _1) {
                if (StringUtils.isLengthOdd(_1)) {
                    return _0;
                }
                return _1;
            }
        });
        return text;
    }

    private static boolean isLengthOdd(String str) {
        return null != str && str.length() % 2 == 1;
    }

    public static String removeCR(String text) {
        return StringUtils.replace(text, "\r\n", "\n");
    }

    public static long countChar(String text, char c) {
        return text.chars().filter(e -> c == e).count();
    }

    public static String trimQuotations(String text, int quotationLength) {
        int length = text.length();
        return length == quotationLength << 1 ? "" : text.substring(quotationLength, length - quotationLength);
    }

    public static boolean matches(String text, Pattern pattern) {
        return pattern.matcher(text).matches();
    }

    public static String replace(String text, String searchString, String replacement) {
        if (StringUtils.isEmpty(text) || StringUtils.isEmpty(searchString) || replacement == null) {
            return text;
        }
        int start = 0;
        int end = text.indexOf(searchString, start);
        if (end == -1) {
            return text;
        }
        int replLength = searchString.length();
        int increase = replacement.length() - replLength;
        increase = (increase < 0 ? 0 : increase) * 16;
        StringBuilder buf = new StringBuilder(text.length() + increase);
        while (end != -1) {
            buf.append(text, start, end).append(replacement);
            start = end + replLength;
            end = text.indexOf(searchString, start);
        }
        buf.append(text, start, text.length());
        return buf.toString();
    }

    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }
}

