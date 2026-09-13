/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apache.commons.lang3.RandomStringUtils
 *  org.apache.commons.lang3.StringUtils
 *  org.apache.commons.lang3.Validate
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;
import java.util.stream.IntStream;
import lombok.Generated;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class RandomUtils {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(RandomUtils.class);
    public static final String SYSTEM_PROPERTY_SECURE_RANDOM_ALG = "CAS_SECURE_RANDOM_ALG";
    private static final int HEX_HIGH_BITS_BITWISE_FLAG = 15;
    private static final int SECURE_ID_CHARS_LENGTH = 40;
    private static final int SECURE_ID_BYTES_LENGTH = 20;
    private static final int SECURE_ID_SHIFT_LENGTH = 4;
    private static final String NATIVE_NON_BLOCKING_ALGORITHM = "NativePRNGNonBlocking";

    public static SecureRandom getNativeInstance() {
        try {
            String alg = (String)StringUtils.defaultIfBlank((CharSequence)System.getProperty(SYSTEM_PROPERTY_SECURE_RANDOM_ALG), (CharSequence)NATIVE_NON_BLOCKING_ALGORITHM);
            return SecureRandom.getInstance(alg);
        }
        catch (NoSuchAlgorithmException e) {
            LOGGER.trace(e.getMessage(), (Throwable)e);
            return new SecureRandom();
        }
    }

    public static long nextLong() {
        return RandomUtils.nextLong(0L, Long.MAX_VALUE);
    }

    public static long nextLong(long startInclusive, long endExclusive) {
        Validate.isTrue((endExclusive >= startInclusive ? 1 : 0) != 0, (String)"Start value must be smaller or equal to end value.", (Object[])new Object[0]);
        Validate.isTrue((startInclusive >= 0L ? 1 : 0) != 0, (String)"Both range values must be non-negative.", (Object[])new Object[0]);
        if (startInclusive == endExclusive) {
            return startInclusive;
        }
        return Double.valueOf(RandomUtils.nextDouble(startInclusive, endExclusive)).longValue();
    }

    public static double nextDouble(double startInclusive, double endInclusive) {
        Validate.isTrue((endInclusive >= startInclusive ? 1 : 0) != 0, (String)"Start value must be smaller or equal to end value.", (Object[])new Object[0]);
        Validate.isTrue((startInclusive >= 0.0 ? 1 : 0) != 0, (String)"Both range values must be non-negative.", (Object[])new Object[0]);
        if (startInclusive == endInclusive) {
            return startInclusive;
        }
        return startInclusive + (endInclusive - startInclusive) * RandomUtils.getNativeInstance().nextDouble();
    }

    public static double nextDouble() {
        return RandomUtils.nextDouble(0.0, Double.MAX_VALUE);
    }

    public static String generateSecureRandomId() {
        SecureRandom generator = RandomUtils.getNativeInstance();
        char[] charMappings = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p'};
        byte[] bytes = new byte[20];
        generator.nextBytes(bytes);
        char[] chars = new char[40];
        IntStream.range(0, bytes.length).forEach(i -> {
            int left = bytes[i] >> 4 & 0xF;
            int right = bytes[i] & 0xF;
            chars[i * 2] = charMappings[left];
            chars[i * 2 + 1] = charMappings[right];
        });
        return String.valueOf(chars);
    }

    public static String randomAlphabetic(int count) {
        return RandomUtils.random(count, true, false);
    }

    public static String randomAlphabetic(int minLengthInclusive, int maxLengthExclusive) {
        return RandomUtils.randomAlphabetic(RandomUtils.nextInt(minLengthInclusive, maxLengthExclusive));
    }

    public static int nextInt(int startInclusive, int endExclusive) {
        Validate.isTrue((endExclusive >= startInclusive ? 1 : 0) != 0, (String)"Start value must be smaller or equal to end value.", (Object[])new Object[0]);
        Validate.isTrue((startInclusive >= 0 ? 1 : 0) != 0, (String)"Both range values must be non-negative.", (Object[])new Object[0]);
        if (startInclusive == endExclusive) {
            return startInclusive;
        }
        return startInclusive + RandomUtils.getNativeInstance().nextInt(endExclusive - startInclusive);
    }

    public static int nextInt() {
        return RandomUtils.nextInt(0, Integer.MAX_VALUE);
    }

    public static String random(int count, boolean letters, boolean numbers) {
        return RandomUtils.random(count, 0, 0, letters, numbers);
    }

    public static String random(int count, int start, int end, boolean letters, boolean numbers) {
        return RandomStringUtils.random((int)count, (int)start, (int)end, (boolean)letters, (boolean)numbers, null, (Random)RandomUtils.getNativeInstance());
    }

    public static String randomAlphanumeric(int count) {
        return RandomUtils.random(count, true, true);
    }

    public static String randomAlphanumeric(int minLengthInclusive, int maxLengthExclusive) {
        return RandomUtils.randomAlphanumeric(RandomUtils.nextInt(minLengthInclusive, maxLengthExclusive));
    }

    public static String randomNumeric(int count) {
        return RandomUtils.random(count, false, true);
    }

    @Generated
    private RandomUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}

