/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.message.internal;

import org.glassfish.jersey.message.internal.GrammarUtil;

public class StringBuilderUtils {
    public static void appendQuotedIfNonToken(StringBuilder b, String value) {
        boolean quote;
        if (value == null) {
            return;
        }
        boolean bl = quote = !GrammarUtil.isTokenString(value);
        if (quote) {
            b.append('\"');
        }
        StringBuilderUtils.appendEscapingQuotes(b, value);
        if (quote) {
            b.append('\"');
        }
    }

    public static void appendQuotedIfWhitespace(StringBuilder b, String value) {
        if (value == null) {
            return;
        }
        boolean quote = GrammarUtil.containsWhiteSpace(value);
        if (quote) {
            b.append('\"');
        }
        StringBuilderUtils.appendEscapingQuotes(b, value);
        if (quote) {
            b.append('\"');
        }
    }

    public static void appendQuoted(StringBuilder b, String value) {
        b.append('\"');
        StringBuilderUtils.appendEscapingQuotes(b, value);
        b.append('\"');
    }

    public static void appendEscapingQuotes(StringBuilder b, String value) {
        for (int i = 0; i < value.length(); ++i) {
            char c = value.charAt(i);
            if (c == '\"') {
                b.append('\\');
            }
            b.append(c);
        }
    }

    private StringBuilderUtils() {
    }
}

