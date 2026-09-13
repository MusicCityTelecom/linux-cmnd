/*
 * Decompiled with CFR 0.152.
 */
package com.android.apksig.internal.apk.v2;

public enum ContentDigestAlgorithm {
    CHUNKED_SHA256("SHA-256", 32),
    CHUNKED_SHA512("SHA-512", 64),
    VERITY_CHUNKED_SHA256("SHA-256", 32);

    private final String mJcaMessageDigestAlgorithm;
    private final int mChunkDigestOutputSizeBytes;

    private ContentDigestAlgorithm(String jcaMessageDigestAlgorithm, int chunkDigestOutputSizeBytes) {
        this.mJcaMessageDigestAlgorithm = jcaMessageDigestAlgorithm;
        this.mChunkDigestOutputSizeBytes = chunkDigestOutputSizeBytes;
    }

    String getJcaMessageDigestAlgorithm() {
        return this.mJcaMessageDigestAlgorithm;
    }

    int getChunkDigestOutputSizeBytes() {
        return this.mChunkDigestOutputSizeBytes;
    }
}

