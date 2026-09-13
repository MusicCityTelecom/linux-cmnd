/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model;

import com.android.tools.build.bundletool.model.SigningConfiguration;
import com.android.tools.build.bundletool.model.SourceStamp;

final class AutoValue_SourceStamp
extends SourceStamp {
    private final SigningConfiguration signingConfiguration;
    private final String source;

    private AutoValue_SourceStamp(SigningConfiguration signingConfiguration, String source) {
        this.signingConfiguration = signingConfiguration;
        this.source = source;
    }

    @Override
    public SigningConfiguration getSigningConfiguration() {
        return this.signingConfiguration;
    }

    @Override
    public String getSource() {
        return this.source;
    }

    public String toString() {
        return "SourceStamp{signingConfiguration=" + this.signingConfiguration + ", source=" + this.source + "}";
    }

    public boolean equals(Object o3) {
        if (o3 == this) {
            return true;
        }
        if (o3 instanceof SourceStamp) {
            SourceStamp that = (SourceStamp)o3;
            return this.signingConfiguration.equals(that.getSigningConfiguration()) && this.source.equals(that.getSource());
        }
        return false;
    }

    public int hashCode() {
        int h$ = 1;
        h$ *= 1000003;
        h$ ^= this.signingConfiguration.hashCode();
        h$ *= 1000003;
        return h$ ^= this.source.hashCode();
    }

    static final class Builder
    extends SourceStamp.Builder {
        private SigningConfiguration signingConfiguration;
        private String source;

        Builder() {
        }

        @Override
        public SourceStamp.Builder setSigningConfiguration(SigningConfiguration signingConfiguration) {
            if (signingConfiguration == null) {
                throw new NullPointerException("Null signingConfiguration");
            }
            this.signingConfiguration = signingConfiguration;
            return this;
        }

        @Override
        public SourceStamp.Builder setSource(String source) {
            if (source == null) {
                throw new NullPointerException("Null source");
            }
            this.source = source;
            return this;
        }

        @Override
        public SourceStamp build() {
            String missing = "";
            if (this.signingConfiguration == null) {
                missing = missing + " signingConfiguration";
            }
            if (this.source == null) {
                missing = missing + " source";
            }
            if (!missing.isEmpty()) {
                throw new IllegalStateException("Missing required properties:" + missing);
            }
            return new AutoValue_SourceStamp(this.signingConfiguration, this.source);
        }
    }
}

