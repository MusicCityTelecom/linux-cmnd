/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model;

import com.android.bundle.Targeting;
import com.android.tools.build.bundletool.model.AndroidManifest;
import com.android.tools.build.bundletool.model.AutoValue_ApkModifier_ApkDescription;
import com.google.auto.value.AutoValue;
import com.google.errorprone.annotations.Immutable;
import javax.annotation.CheckReturnValue;

public abstract class ApkModifier {
    public static final ApkModifier NO_OP = new ApkModifier(){};

    @CheckReturnValue
    public AndroidManifest modifyManifest(AndroidManifest manifest, ApkDescription apkDescription) {
        return manifest;
    }

    @Immutable
    @AutoValue
    @AutoValue.CopyAnnotations
    public static abstract class ApkDescription {
        public static Builder builder() {
            return new AutoValue_ApkModifier_ApkDescription.Builder();
        }

        public abstract boolean isBase();

        public abstract ApkType getApkType();

        public abstract int getVariantNumber();

        public abstract Targeting.VariantTargeting getVariantTargeting();

        public abstract Targeting.ApkTargeting getApkTargeting();

        public static enum ApkType {
            MASTER_SPLIT,
            CONFIG_SPLIT,
            STANDALONE;

        }

        @AutoValue.Builder
        public static abstract class Builder {
            public abstract Builder setVariantNumber(int var1);

            public abstract Builder setBase(boolean var1);

            public abstract Builder setApkType(ApkType var1);

            public abstract Builder setVariantTargeting(Targeting.VariantTargeting var1);

            public abstract Builder setApkTargeting(Targeting.ApkTargeting var1);

            public abstract ApkDescription build();
        }
    }
}

