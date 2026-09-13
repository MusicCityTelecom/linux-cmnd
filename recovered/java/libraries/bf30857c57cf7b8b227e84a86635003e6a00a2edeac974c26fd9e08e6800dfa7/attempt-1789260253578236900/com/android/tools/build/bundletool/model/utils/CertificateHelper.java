/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model.utils;

import com.google.common.io.BaseEncoding;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;

public final class CertificateHelper {
    private CertificateHelper() {
    }

    public static String sha256AsHexString(X509Certificate certificate) throws CertificateEncodingException {
        try {
            return CertificateHelper.toHexString(CertificateHelper.getSha256Bytes(certificate.getEncoded()));
        }
        catch (NoSuchAlgorithmException e2) {
            throw new IllegalStateException("SHA-256 is not found.", e2);
        }
    }

    private static byte[] getSha256Bytes(byte[] input) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(input);
        return messageDigest.digest();
    }

    private static String toHexString(byte[] rawBytes) {
        return BaseEncoding.base16().upperCase().encode(rawBytes);
    }
}

