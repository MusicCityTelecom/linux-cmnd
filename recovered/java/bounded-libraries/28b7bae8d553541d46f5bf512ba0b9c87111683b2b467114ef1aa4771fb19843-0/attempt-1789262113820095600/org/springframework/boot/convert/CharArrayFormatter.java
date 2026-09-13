/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.format.Formatter
 */
package org.springframework.boot.convert;

import java.text.ParseException;
import java.util.Locale;
import org.springframework.format.Formatter;

final class CharArrayFormatter
implements Formatter<char[]> {
    CharArrayFormatter() {
    }

    public String print(char[] object, Locale locale) {
        return new String(object);
    }

    public char[] parse(String text, Locale locale) throws ParseException {
        return text.toCharArray();
    }
}

