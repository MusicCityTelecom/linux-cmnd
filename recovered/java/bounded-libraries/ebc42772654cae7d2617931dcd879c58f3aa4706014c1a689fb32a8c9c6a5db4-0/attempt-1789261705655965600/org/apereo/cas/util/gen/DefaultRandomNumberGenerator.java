/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package org.apereo.cas.util.gen;

import lombok.Generated;
import org.apereo.cas.util.gen.DefaultRandomStringGenerator;

public class DefaultRandomNumberGenerator
extends DefaultRandomStringGenerator {
    private static final char[] PRINTABLE_CHARACTERS = "012345679".toCharArray();

    public DefaultRandomNumberGenerator(int defaultLength) {
        super(defaultLength);
    }

    @Override
    protected char[] getPrintableCharacters() {
        return PRINTABLE_CHARACTERS;
    }

    @Generated
    public DefaultRandomNumberGenerator() {
    }
}

