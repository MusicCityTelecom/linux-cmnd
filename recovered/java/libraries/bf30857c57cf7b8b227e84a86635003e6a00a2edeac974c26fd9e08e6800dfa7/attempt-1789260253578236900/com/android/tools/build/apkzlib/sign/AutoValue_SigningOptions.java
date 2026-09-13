/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.apkzlib.sign;

import com.android.tools.build.apkzlib.sign.SigningOptions;
import com.google.common.collect.ImmutableList;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;

final class AutoValue_SigningOptions
extends SigningOptions {
    private final PrivateKey key;
    private final ImmutableList<X509Certificate> certificates;
    private final boolean v1SigningEnabled;
    private final boolean v2SigningEnabled;
    private final int minSdkVersion;
    private final SigningOptions.Validation validation;

    private AutoValue_SigningOptions(PrivateKey key, ImmutableList<X509Certificate> certificates, boolean v1SigningEnabled, boolean v2SigningEnabled, int minSdkVersion, SigningOptions.Validation validation) {
        this.key = key;
        this.certificates = certificates;
        this.v1SigningEnabled = v1SigningEnabled;
        this.v2SigningEnabled = v2SigningEnabled;
        this.minSdkVersion = minSdkVersion;
        this.validation = validation;
    }

    @Override
    public PrivateKey getKey() {
        return this.key;
    }

    @Override
    public ImmutableList<X509Certificate> getCertificates() {
        return this.certificates;
    }

    @Override
    public boolean isV1SigningEnabled() {
        return this.v1SigningEnabled;
    }

    @Override
    public boolean isV2SigningEnabled() {
        return this.v2SigningEnabled;
    }

    @Override
    public int getMinSdkVersion() {
        return this.minSdkVersion;
    }

    @Override
    public SigningOptions.Validation getValidation() {
        return this.validation;
    }

    public String toString() {
        return "SigningOptions{key=" + this.key + ", certificates=" + this.certificates + ", v1SigningEnabled=" + this.v1SigningEnabled + ", v2SigningEnabled=" + this.v2SigningEnabled + ", minSdkVersion=" + this.minSdkVersion + ", validation=" + (Object)((Object)this.validation) + "}";
    }

    public boolean equals(Object o3) {
        if (o3 == this) {
            return true;
        }
        if (o3 instanceof SigningOptions) {
            SigningOptions that = (SigningOptions)o3;
            return this.key.equals(that.getKey()) && this.certificates.equals(that.getCertificates()) && this.v1SigningEnabled == that.isV1SigningEnabled() && this.v2SigningEnabled == that.isV2SigningEnabled() && this.minSdkVersion == that.getMinSdkVersion() && this.validation.equals((Object)that.getValidation());
        }
        return false;
    }

    public int hashCode() {
        int h2 = 1;
        h2 *= 1000003;
        h2 ^= this.key.hashCode();
        h2 *= 1000003;
        h2 ^= this.certificates.hashCode();
        h2 *= 1000003;
        h2 ^= this.v1SigningEnabled ? 1231 : 1237;
        h2 *= 1000003;
        h2 ^= this.v2SigningEnabled ? 1231 : 1237;
        h2 *= 1000003;
        h2 ^= this.minSdkVersion;
        h2 *= 1000003;
        return h2 ^= this.validation.hashCode();
    }

    static final class Builder
    extends SigningOptions.Builder {
        private PrivateKey key;
        private ImmutableList<X509Certificate> certificates;
        private Boolean v1SigningEnabled;
        private Boolean v2SigningEnabled;
        private Integer minSdkVersion;
        private SigningOptions.Validation validation;

        Builder() {
        }

        @Override
        public SigningOptions.Builder setKey(PrivateKey key) {
            if (key == null) {
                throw new NullPointerException("Null key");
            }
            this.key = key;
            return this;
        }

        @Override
        public SigningOptions.Builder setCertificates(ImmutableList<X509Certificate> certificates) {
            if (certificates == null) {
                throw new NullPointerException("Null certificates");
            }
            this.certificates = certificates;
            return this;
        }

        @Override
        public SigningOptions.Builder setCertificates(X509Certificate ... certificates) {
            if (certificates == null) {
                throw new NullPointerException("Null certificates");
            }
            this.certificates = ImmutableList.copyOf(certificates);
            return this;
        }

        @Override
        public SigningOptions.Builder setV1SigningEnabled(boolean v1SigningEnabled) {
            this.v1SigningEnabled = v1SigningEnabled;
            return this;
        }

        @Override
        public SigningOptions.Builder setV2SigningEnabled(boolean v2SigningEnabled) {
            this.v2SigningEnabled = v2SigningEnabled;
            return this;
        }

        @Override
        public SigningOptions.Builder setMinSdkVersion(int minSdkVersion) {
            this.minSdkVersion = minSdkVersion;
            return this;
        }

        @Override
        public SigningOptions.Builder setValidation(SigningOptions.Validation validation) {
            if (validation == null) {
                throw new NullPointerException("Null validation");
            }
            this.validation = validation;
            return this;
        }

        @Override
        SigningOptions autoBuild() {
            String missing = "";
            if (this.key == null) {
                missing = missing + " key";
            }
            if (this.certificates == null) {
                missing = missing + " certificates";
            }
            if (this.v1SigningEnabled == null) {
                missing = missing + " v1SigningEnabled";
            }
            if (this.v2SigningEnabled == null) {
                missing = missing + " v2SigningEnabled";
            }
            if (this.minSdkVersion == null) {
                missing = missing + " minSdkVersion";
            }
            if (this.validation == null) {
                missing = missing + " validation";
            }
            if (!missing.isEmpty()) {
                throw new IllegalStateException("Missing required properties:" + missing);
            }
            return new AutoValue_SigningOptions(this.key, this.certificates, this.v1SigningEnabled, this.v2SigningEnabled, this.minSdkVersion, this.validation);
        }
    }
}

