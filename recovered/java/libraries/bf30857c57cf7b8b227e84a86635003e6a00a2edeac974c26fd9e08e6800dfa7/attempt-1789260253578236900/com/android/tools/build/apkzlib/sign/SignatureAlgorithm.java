/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.apkzlib.sign;

import com.android.tools.build.apkzlib.sign.DigestAlgorithm;
import java.security.NoSuchAlgorithmException;

public enum SignatureAlgorithm {
    RSA("RSA", 1, "withRSA"),
    ECDSA("EC", 18, "withECDSA"),
    DSA("DSA", 1, "withDSA");

    public final String keyAlgorithm;
    public final int minSdkVersion;
    public final String signatureAlgorithmSuffix;

    private SignatureAlgorithm(String keyAlgorithm, int minSdkVersion, String signatureAlgorithmSuffix) {
        this.keyAlgorithm = keyAlgorithm;
        this.minSdkVersion = minSdkVersion;
        this.signatureAlgorithmSuffix = signatureAlgorithmSuffix;
    }

    public static SignatureAlgorithm fromKeyAlgorithm(String keyAlgorithm, int minSdkVersion) throws NoSuchAlgorithmException {
        for (SignatureAlgorithm alg : SignatureAlgorithm.values()) {
            if (!alg.keyAlgorithm.equalsIgnoreCase(keyAlgorithm)) continue;
            if (alg.minSdkVersion > minSdkVersion) {
                throw new NoSuchAlgorithmException("Signatures with " + keyAlgorithm + " keys are not supported on minSdkVersion " + minSdkVersion + ". They are supported only for minSdkVersion >= " + alg.minSdkVersion);
            }
            return alg;
        }
        throw new NoSuchAlgorithmException("Signing with " + keyAlgorithm + " keys is not supported");
    }

    public String signatureAlgorithmName(DigestAlgorithm digestAlgorithm) {
        return digestAlgorithm.messageDigestName.replace("-", "") + this.signatureAlgorithmSuffix;
    }
}

