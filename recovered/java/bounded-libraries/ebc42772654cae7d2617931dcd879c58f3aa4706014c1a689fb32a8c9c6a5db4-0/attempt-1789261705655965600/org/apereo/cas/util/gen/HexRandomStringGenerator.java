/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package org.apereo.cas.util.gen;

import lombok.Generated;
import org.apereo.cas.util.EncodingUtils;
import org.apereo.cas.util.gen.AbstractRandomStringGenerator;

public class HexRandomStringGenerator
extends AbstractRandomStringGenerator {
    public HexRandomStringGenerator(int defaultLength) {
        super(defaultLength);
    }

    @Override
    protected String convertBytesToString(byte[] random) {
        return EncodingUtils.hexEncode(random);
    }

    @Generated
    public HexRandomStringGenerator() {
    }
}

