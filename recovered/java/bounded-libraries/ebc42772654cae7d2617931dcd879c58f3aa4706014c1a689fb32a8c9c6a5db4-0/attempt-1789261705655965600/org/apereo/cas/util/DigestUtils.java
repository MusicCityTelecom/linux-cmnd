/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 */
package org.apereo.cas.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Optional;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.util.EncodingUtils;

public final class DigestUtils {
    private static final int ABBREVIATE_MAX_WIDTH = 125;

    public static String sha512(String data) {
        return DigestUtils.digest("SHA-512", data.getBytes(StandardCharsets.UTF_8));
    }

    public static String sha256(String data) {
        return DigestUtils.digest("SHA-256", data.getBytes(StandardCharsets.UTF_8));
    }

    public static String sha(String data) {
        return DigestUtils.digest("SHA-1", data);
    }

    public static byte[] sha(byte[] data) {
        return DigestUtils.rawDigest("SHA-1", data);
    }

    public static String shaBase64(String salt, String data) {
        return DigestUtils.shaBase64(salt, data, null);
    }

    public static String shaBase64(String salt, String data, String separator, boolean chunked) {
        byte[] result = DigestUtils.rawDigest("SHA-1", salt, Optional.ofNullable(separator).map(s -> data + s).orElse(data));
        return EncodingUtils.encodeBase64(result, chunked);
    }

    public static String shaBase64(String salt, String data, String separator) {
        byte[] result = DigestUtils.rawDigest("SHA-1", salt, Optional.ofNullable(separator).map(s -> data + s).orElse(data));
        return EncodingUtils.encodeBase64(result);
    }

    public static String shaBase32(String salt, String data, String separator, boolean chunked) {
        byte[] result = DigestUtils.rawDigest("SHA-1", salt, Optional.ofNullable(separator).map(s -> data + s).orElse(data));
        return EncodingUtils.encodeBase32(result, chunked);
    }

    public static String digest(String alg, String data) {
        return DigestUtils.digest(alg, data.getBytes(StandardCharsets.UTF_8));
    }

    public static String digest(String alg, byte[] data) {
        return EncodingUtils.hexEncode(DigestUtils.rawDigest(alg, data));
    }

    public static byte[] rawDigestSha256(String data) {
        try {
            return DigestUtils.rawDigest("SHA-256", data.getBytes(StandardCharsets.UTF_8));
        }
        catch (Exception cause) {
            throw new SecurityException(cause);
        }
    }

    public static byte[] rawDigest(String alg, byte[] data) {
        try {
            MessageDigest digest = DigestUtils.getMessageDigestInstance(alg);
            return digest.digest(data);
        }
        catch (Exception cause) {
            throw new SecurityException(cause);
        }
    }

    public static byte[] rawDigest(String alg, String salt, String ... data) {
        try {
            MessageDigest digest = DigestUtils.getMessageDigestInstance(alg);
            Arrays.stream(data).forEach(d -> digest.update(d.getBytes(StandardCharsets.UTF_8)));
            return digest.digest(salt.getBytes(StandardCharsets.UTF_8));
        }
        catch (Exception cause) {
            throw new SecurityException(cause);
        }
    }

    public static String abbreviate(String str) {
        return DigestUtils.abbreviate(str, 125);
    }

    public static String abbreviate(String str, int maxWidth) {
        return maxWidth <= 0 ? str : StringUtils.abbreviate((String)str, (int)maxWidth);
    }

    private static MessageDigest getMessageDigestInstance(String alg) throws Exception {
        MessageDigest digest = MessageDigest.getInstance(alg);
        digest.reset();
        return digest;
    }

    @Generated
    private DigestUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}

