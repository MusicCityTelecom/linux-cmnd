/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.configurationmetadata;

import java.text.BreakIterator;
import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Collectors;

class SentenceExtractor {
    SentenceExtractor() {
    }

    String getFirstSentence(String text) {
        if (text == null) {
            return null;
        }
        int dot = text.indexOf(46);
        if (dot != -1) {
            BreakIterator breakIterator = BreakIterator.getSentenceInstance(Locale.US);
            breakIterator.setText(text);
            String sentence = text.substring(breakIterator.first(), breakIterator.next());
            return this.removeSpaceBetweenLine(sentence.trim());
        }
        String[] lines = text.split(System.lineSeparator());
        return lines[0].trim();
    }

    private String removeSpaceBetweenLine(String text) {
        String[] lines = text.split(System.lineSeparator());
        return Arrays.stream(lines).map(String::trim).collect(Collectors.joining(" "));
    }
}

