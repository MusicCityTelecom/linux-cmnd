/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.apkzlib.sign;

import com.android.tools.build.apkzlib.sign.AutoValue_SigningOptions;
import com.google.auto.value.AutoValue;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import javax.annotation.Nonnull;

@AutoValue
public abstract class SigningOptions {
    public static Builder builder() {
        return new AutoValue_SigningOptions.Builder().setV1SigningEnabled(false).setV2SigningEnabled(false).setValidation(Validation.ALWAYS_VALIDATE);
    }

    public abstract PrivateKey getKey();

    public abstract ImmutableList<X509Certificate> getCertificates();

    public abstract boolean isV1SigningEnabled();

    public abstract boolean isV2SigningEnabled();

    public abstract int getMinSdkVersion();

    public abstract Validation getValidation();

    public static enum Validation {
        ALWAYS_VALIDATE,
        ASSUME_VALID,
        ASSUME_INVALID;

    }

    public static abstract class Builder {
        public abstract Builder setKey(@Nonnull PrivateKey var1);

        public abstract Builder setCertificates(@Nonnull ImmutableList<X509Certificate> var1);

        public abstract Builder setCertificates(X509Certificate ... var1);

        public abstract Builder setV1SigningEnabled(boolean var1);

        public abstract Builder setV2SigningEnabled(boolean var1);

        public abstract Builder setMinSdkVersion(int var1);

        public abstract Builder setValidation(@Nonnull Validation var1);

        abstract SigningOptions autoBuild();

        public SigningOptions build() {
            SigningOptions options = this.autoBuild();
            Preconditions.checkArgument(options.getMinSdkVersion() >= 0, "minSdkVersion < 0");
            Preconditions.checkArgument(!options.getCertificates().isEmpty(), "There should be at least one certificate in SigningOptions");
            return options;
        }
    }
}

