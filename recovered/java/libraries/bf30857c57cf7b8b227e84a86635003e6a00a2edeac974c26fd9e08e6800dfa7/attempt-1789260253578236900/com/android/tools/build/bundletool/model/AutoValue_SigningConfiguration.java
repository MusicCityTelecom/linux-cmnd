/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model;

import com.android.tools.build.bundletool.model.SigningConfiguration;
import com.google.common.collect.ImmutableList;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;

final class AutoValue_SigningConfiguration
extends SigningConfiguration {
    private final PrivateKey privateKey;
    private final ImmutableList<X509Certificate> certificates;

    private AutoValue_SigningConfiguration(PrivateKey privateKey, ImmutableList<X509Certificate> certificates) {
        this.privateKey = privateKey;
        this.certificates = certificates;
    }

    @Override
    public PrivateKey getPrivateKey() {
        return this.privateKey;
    }

    @Override
    public ImmutableList<X509Certificate> getCertificates() {
        return this.certificates;
    }

    public String toString() {
        return "SigningConfiguration{privateKey=" + this.privateKey + ", certificates=" + this.certificates + "}";
    }

    public boolean equals(Object o3) {
        if (o3 == this) {
            return true;
        }
        if (o3 instanceof SigningConfiguration) {
            SigningConfiguration that = (SigningConfiguration)o3;
            return this.privateKey.equals(that.getPrivateKey()) && this.certificates.equals(that.getCertificates());
        }
        return false;
    }

    public int hashCode() {
        int h$ = 1;
        h$ *= 1000003;
        h$ ^= this.privateKey.hashCode();
        h$ *= 1000003;
        return h$ ^= this.certificates.hashCode();
    }

    static final class Builder
    extends SigningConfiguration.Builder {
        private PrivateKey privateKey;
        private ImmutableList<X509Certificate> certificates;

        Builder() {
        }

        @Override
        public SigningConfiguration.Builder setPrivateKey(PrivateKey privateKey) {
            if (privateKey == null) {
                throw new NullPointerException("Null privateKey");
            }
            this.privateKey = privateKey;
            return this;
        }

        @Override
        public SigningConfiguration.Builder setCertificates(ImmutableList<X509Certificate> certificates) {
            if (certificates == null) {
                throw new NullPointerException("Null certificates");
            }
            this.certificates = certificates;
            return this;
        }

        @Override
        public SigningConfiguration build() {
            String missing = "";
            if (this.privateKey == null) {
                missing = missing + " privateKey";
            }
            if (this.certificates == null) {
                missing = missing + " certificates";
            }
            if (!missing.isEmpty()) {
                throw new IllegalStateException("Missing required properties:" + missing);
            }
            return new AutoValue_SigningConfiguration(this.privateKey, this.certificates);
        }
    }
}

