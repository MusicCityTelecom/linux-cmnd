/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package org.apereo.cas.util.gen;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import lombok.Generated;
import org.apereo.cas.util.RandomUtils;
import org.apereo.cas.util.gen.RandomStringGenerator;

public abstract class AbstractRandomStringGenerator
implements RandomStringGenerator {
    protected final SecureRandom randomizer = RandomUtils.getNativeInstance();
    protected final long defaultLength;

    protected AbstractRandomStringGenerator() {
        this(36L);
    }

    @Override
    public String getAlgorithm() {
        return this.randomizer.getAlgorithm();
    }

    protected String convertBytesToString(byte[] random) {
        return new String(random, StandardCharsets.UTF_8);
    }

    @Override
    public String getNewString(int size) {
        byte[] random = this.getNewStringAsBytes(size);
        return this.convertBytesToString(random);
    }

    @Override
    public String getNewString() {
        return this.getNewString(Long.valueOf(this.getDefaultLength()).intValue());
    }

    @Override
    public byte[] getNewStringAsBytes(int size) {
        byte[] random = new byte[size];
        this.randomizer.nextBytes(random);
        return random;
    }

    @Generated
    public SecureRandom getRandomizer() {
        return this.randomizer;
    }

    @Override
    @Generated
    public long getDefaultLength() {
        return this.defaultLength;
    }

    @Generated
    protected AbstractRandomStringGenerator(long defaultLength) {
        this.defaultLength = defaultLength;
    }
}

