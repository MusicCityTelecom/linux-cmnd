/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package org.apereo.cas.util.gen;

import java.util.stream.IntStream;
import lombok.Generated;
import org.apereo.cas.util.gen.AbstractRandomStringGenerator;

public class DefaultRandomStringGenerator
extends AbstractRandomStringGenerator {
    private static final char[] PRINTABLE_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ012345679".toCharArray();

    public DefaultRandomStringGenerator(long defaultLength) {
        super(defaultLength);
    }

    @Override
    protected String convertBytesToString(byte[] random) {
        char[] output = new char[random.length];
        IntStream.range(0, random.length).forEach(i -> {
            char[] printableCharacters = this.getPrintableCharacters();
            int index = Math.abs(random[i] % printableCharacters.length);
            output[i] = printableCharacters[index];
        });
        return new String(output);
    }

    protected char[] getPrintableCharacters() {
        return PRINTABLE_CHARACTERS;
    }

    @Generated
    public DefaultRandomStringGenerator() {
    }
}

