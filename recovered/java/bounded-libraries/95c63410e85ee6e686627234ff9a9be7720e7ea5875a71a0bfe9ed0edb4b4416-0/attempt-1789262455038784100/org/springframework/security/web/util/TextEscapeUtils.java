/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.security.web.util;

public abstract class TextEscapeUtils {
    public static String escapeEntities(String s) {
        if (s == null || s.length() == 0) {
            return s;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); ++i) {
            char ch = s.charAt(i);
            if (ch >= 'a' && ch <= 'z' || ch >= 'A' && ch <= 'Z' || ch >= '0' && ch <= '9') {
                sb.append(ch);
                continue;
            }
            if (ch == '<') {
                sb.append("&lt;");
                continue;
            }
            if (ch == '>') {
                sb.append("&gt;");
                continue;
            }
            if (ch == '&') {
                sb.append("&amp;");
                continue;
            }
            if (Character.isWhitespace(ch)) {
                sb.append("&#").append((int)ch).append(";");
                continue;
            }
            if (Character.isISOControl(ch)) continue;
            if (Character.isHighSurrogate(ch)) {
                if (i + 1 >= s.length()) {
                    throw new IllegalArgumentException("Missing low surrogate character at end of string");
                }
                char low = s.charAt(i + 1);
                if (!Character.isLowSurrogate(low)) {
                    throw new IllegalArgumentException("Expected low surrogate character but found value = " + low);
                }
                int codePoint = Character.toCodePoint(ch, low);
                if (Character.isDefined(codePoint)) {
                    sb.append("&#").append(codePoint).append(";");
                }
                ++i;
                continue;
            }
            if (Character.isLowSurrogate(ch)) {
                throw new IllegalArgumentException("Unexpected low surrogate character, value = " + ch);
            }
            if (!Character.isDefined(ch)) continue;
            sb.append("&#").append((int)ch).append(";");
        }
        return sb.toString();
    }
}

