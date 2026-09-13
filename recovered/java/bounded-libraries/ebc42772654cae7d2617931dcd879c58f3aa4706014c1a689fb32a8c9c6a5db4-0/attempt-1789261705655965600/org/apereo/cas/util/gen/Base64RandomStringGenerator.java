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

public class Base64RandomStringGenerator
extends AbstractRandomStringGenerator {
    public Base64RandomStringGenerator(long defaultLength) {
        super(defaultLength);
    }

    @Override
    protected String convertBytesToString(byte[] random) {
        return EncodingUtils.encodeUrlSafeBase64(random);
    }

    @Generated
    public Base64RandomStringGenerator() {
    }
}

