/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.apkzlib.sign;

public enum DigestAlgorithm {
    SHA1("SHA1", "SHA-1"),
    SHA256("SHA-256", "SHA-256");

    public static final int API_SHA_256_RSA_AND_ECDSA = 18;
    public static final int API_SHA_256_ALL_ALGORITHMS = 21;
    public final String messageDigestName;
    public final String manifestAttributeName;
    public final String entryAttributeName;

    private DigestAlgorithm(String attributeName, String messageDigestName) {
        this.messageDigestName = messageDigestName;
        this.entryAttributeName = attributeName + "-Digest";
        this.manifestAttributeName = attributeName + "-Digest-Manifest";
    }
}

