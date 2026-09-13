/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal.util;

import java.util.Collection;
import java.util.LinkedList;
import java.util.regex.Pattern;

public final class Tokenizer {
    public static final String COMMON_DELIMITERS = " ,;\n";

    private Tokenizer() {
    }

    public static String[] tokenize(String[] entries) {
        return Tokenizer.tokenize(entries, COMMON_DELIMITERS);
    }

    public static String[] tokenize(String[] entries, String delimiters) {
        LinkedList<String> tokens = new LinkedList<String>();
        for (String entry : entries) {
            if (entry == null || entry.isEmpty() || (entry = entry.trim()).isEmpty()) continue;
            Tokenizer.tokenize(entry, delimiters, tokens);
        }
        return tokens.toArray(new String[tokens.size()]);
    }

    public static String[] tokenize(String entry) {
        return Tokenizer.tokenize(entry, COMMON_DELIMITERS);
    }

    public static String[] tokenize(String entry, String delimiters) {
        Collection<String> tokens = Tokenizer.tokenize(entry, delimiters, new LinkedList<String>());
        return tokens.toArray(new String[tokens.size()]);
    }

    private static Collection<String> tokenize(String entry, String delimiters, Collection<String> tokens) {
        String[] tokenArray;
        StringBuilder regexpBuilder = new StringBuilder(delimiters.length() * 3);
        regexpBuilder.append('[');
        for (char c : delimiters.toCharArray()) {
            regexpBuilder.append(Pattern.quote(String.valueOf(c)));
        }
        regexpBuilder.append(']');
        for (String token : tokenArray = entry.split(regexpBuilder.toString())) {
            if (token == null || token.isEmpty() || (token = token.trim()).isEmpty()) continue;
            tokens.add(token);
        }
        return tokens;
    }
}

