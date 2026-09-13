/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model;

import com.android.tools.build.bundletool.model.AutoValue_SourceStamp;
import com.android.tools.build.bundletool.model.SigningConfiguration;
import com.google.auto.value.AutoValue;
import com.google.errorprone.annotations.Immutable;

@Immutable
@AutoValue
public abstract class SourceStamp {
    public static final String LOCAL_SOURCE = "local";
    public static final String STAMP_SOURCE_METADATA_KEY = "com.android.stamp.source";
    public static final String STAMP_TYPE_METADATA_KEY = "com.android.stamp.type";

    public abstract SigningConfiguration getSigningConfiguration();

    public abstract String getSource();

    public static Builder builder() {
        return new AutoValue_SourceStamp.Builder().setSource(LOCAL_SOURCE);
    }

    public static enum StampType {
        STAMP_TYPE_DISTRIBUTION_APK,
        STAMP_TYPE_STANDALONE_APK;

    }

    @AutoValue.Builder
    public static abstract class Builder {
        public abstract Builder setSigningConfiguration(SigningConfiguration var1);

        public abstract Builder setSource(String var1);

        public abstract SourceStamp build();
    }
}

