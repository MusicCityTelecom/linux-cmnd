/*
 * Decompiled with CFR 0.152.
 */
package com.android.apksig.internal.apk.v2;

import com.android.apksig.internal.apk.v2.ContentDigestAlgorithm;
import com.android.apksig.internal.util.Pair;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.MGF1ParameterSpec;
import java.security.spec.PSSParameterSpec;

public enum SignatureAlgorithm {
    RSA_PSS_WITH_SHA256(257, ContentDigestAlgorithm.CHUNKED_SHA256, "RSA", Pair.of("SHA256withRSA/PSS", new PSSParameterSpec("SHA-256", "MGF1", MGF1ParameterSpec.SHA256, 32, 1)), 24),
    RSA_PSS_WITH_SHA512(258, ContentDigestAlgorithm.CHUNKED_SHA512, "RSA", Pair.of("SHA512withRSA/PSS", new PSSParameterSpec("SHA-512", "MGF1", MGF1ParameterSpec.SHA512, 64, 1)), 24),
    RSA_PKCS1_V1_5_WITH_SHA256(259, ContentDigestAlgorithm.CHUNKED_SHA256, "RSA", Pair.of("SHA256withRSA", null), 24),
    RSA_PKCS1_V1_5_WITH_SHA512(260, ContentDigestAlgorithm.CHUNKED_SHA512, "RSA", Pair.of("SHA512withRSA", null), 24),
    ECDSA_WITH_SHA256(513, ContentDigestAlgorithm.CHUNKED_SHA256, "EC", Pair.of("SHA256withECDSA", null), 24),
    ECDSA_WITH_SHA512(514, ContentDigestAlgorithm.CHUNKED_SHA512, "EC", Pair.of("SHA512withECDSA", null), 24),
    DSA_WITH_SHA256(769, ContentDigestAlgorithm.CHUNKED_SHA256, "DSA", Pair.of("SHA256withDSA", null), 24),
    VERITY_RSA_PKCS1_V1_5_WITH_SHA256(39169, ContentDigestAlgorithm.VERITY_CHUNKED_SHA256, "RSA", Pair.of("SHA256withRSA", null), 28),
    VERITY_ECDSA_WITH_SHA256(39171, ContentDigestAlgorithm.VERITY_CHUNKED_SHA256, "EC", Pair.of("SHA256withECDSA", null), 28),
    VERITY_DSA_WITH_SHA256(39173, ContentDigestAlgorithm.VERITY_CHUNKED_SHA256, "DSA", Pair.of("SHA256withDSA", null), 28);

    private final int mId;
    private final String mJcaKeyAlgorithm;
    private final ContentDigestAlgorithm mContentDigestAlgorithm;
    private final Pair<String, ? extends AlgorithmParameterSpec> mJcaSignatureAlgAndParams;
    private final int mMinSdkVersion;

    private SignatureAlgorithm(int id, ContentDigestAlgorithm contentDigestAlgorithm, String jcaKeyAlgorithm, Pair<String, ? extends AlgorithmParameterSpec> jcaSignatureAlgAndParams, int minSdkVersion) {
        this.mId = id;
        this.mContentDigestAlgorithm = contentDigestAlgorithm;
        this.mJcaKeyAlgorithm = jcaKeyAlgorithm;
        this.mJcaSignatureAlgAndParams = jcaSignatureAlgAndParams;
        this.mMinSdkVersion = minSdkVersion;
    }

    int getId() {
        return this.mId;
    }

    ContentDigestAlgorithm getContentDigestAlgorithm() {
        return this.mContentDigestAlgorithm;
    }

    String getJcaKeyAlgorithm() {
        return this.mJcaKeyAlgorithm;
    }

    Pair<String, ? extends AlgorithmParameterSpec> getJcaSignatureAlgorithmAndParams() {
        return this.mJcaSignatureAlgAndParams;
    }

    int getMinSdkVersion() {
        return this.mMinSdkVersion;
    }

    static SignatureAlgorithm findById(int id) {
        for (SignatureAlgorithm alg : SignatureAlgorithm.values()) {
            if (alg.getId() != id) continue;
            return alg;
        }
        return null;
    }
}

