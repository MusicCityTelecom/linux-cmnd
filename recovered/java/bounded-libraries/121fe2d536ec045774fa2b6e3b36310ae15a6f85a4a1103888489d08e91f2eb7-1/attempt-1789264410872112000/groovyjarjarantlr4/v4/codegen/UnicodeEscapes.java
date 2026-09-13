/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.codegen;

public enum UnicodeEscapes {


    public static void appendJavaStyleEscapedCodePoint(int codePoint, StringBuilder sb) {
        if (Character.isSupplementaryCodePoint(codePoint)) {
            sb.append(String.format("\\u%04X", UnicodeEscapes.highSurrogate(codePoint)));
            sb.append(String.format("\\u%04X", UnicodeEscapes.lowSurrogate(codePoint)));
        } else {
            sb.append(String.format("\\u%04X", codePoint));
        }
    }

    private static char highSurrogate(int codePoint) {
        return (char)((codePoint >>> 10) + 55232);
    }

    public static char lowSurrogate(int codePoint) {
        return (char)((codePoint & 0x3FF) + 56320);
    }
}

