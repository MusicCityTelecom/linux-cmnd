/*
 * Decompiled with CFR 0.152.
 */
package org.cryptacular.generator;

import java.security.SecureRandom;
import org.cryptacular.generator.IdGenerator;

public class RandomIdGenerator
implements IdGenerator {
    public static final String DEFAULT_CHARSET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private final int length;
    private final String charset;
    private final SecureRandom secureRandom;

    public RandomIdGenerator(int length) {
        this(length, DEFAULT_CHARSET);
    }

    public RandomIdGenerator(int length, String charset) {
        if (length < 1) {
            throw new IllegalArgumentException("Length must be positive");
        }
        this.length = length;
        if (charset == null || charset.length() < 2 || charset.length() > 128) {
            throw new IllegalArgumentException("Charset length must be in the range 2 - 128");
        }
        this.charset = charset;
        this.secureRandom = new SecureRandom();
        this.secureRandom.nextBytes(new byte[1]);
    }

    @Override
    public String generate() {
        StringBuilder id = new StringBuilder(this.length);
        byte[] output = new byte[this.length];
        this.secureRandom.nextBytes(output);
        for (int i = 0; i < output.length && id.length() < this.length; ++i) {
            int index = 0x7F & output[i];
            id.append(this.charset.charAt(index % this.charset.length()));
        }
        return id.toString();
    }
}

