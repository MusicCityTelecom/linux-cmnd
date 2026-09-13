/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model.version;

import com.android.tools.build.bundletool.model.version.Version;
import javax.annotation.Nullable;

final class AutoValue_Version
extends Version {
    private final String fullVersion;
    private final int majorVersion;
    private final int minorVersion;
    private final int revisionVersion;
    private final String qualifier;

    private AutoValue_Version(String fullVersion, int majorVersion, int minorVersion, int revisionVersion, @Nullable String qualifier) {
        this.fullVersion = fullVersion;
        this.majorVersion = majorVersion;
        this.minorVersion = minorVersion;
        this.revisionVersion = revisionVersion;
        this.qualifier = qualifier;
    }

    @Override
    String getFullVersion() {
        return this.fullVersion;
    }

    @Override
    int getMajorVersion() {
        return this.majorVersion;
    }

    @Override
    int getMinorVersion() {
        return this.minorVersion;
    }

    @Override
    int getRevisionVersion() {
        return this.revisionVersion;
    }

    @Override
    @Nullable
    String getQualifier() {
        return this.qualifier;
    }

    public boolean equals(Object o3) {
        if (o3 == this) {
            return true;
        }
        if (o3 instanceof Version) {
            Version that = (Version)o3;
            return this.fullVersion.equals(that.getFullVersion()) && this.majorVersion == that.getMajorVersion() && this.minorVersion == that.getMinorVersion() && this.revisionVersion == that.getRevisionVersion() && (this.qualifier == null ? that.getQualifier() == null : this.qualifier.equals(that.getQualifier()));
        }
        return false;
    }

    public int hashCode() {
        int h$ = 1;
        h$ *= 1000003;
        h$ ^= this.fullVersion.hashCode();
        h$ *= 1000003;
        h$ ^= this.majorVersion;
        h$ *= 1000003;
        h$ ^= this.minorVersion;
        h$ *= 1000003;
        h$ ^= this.revisionVersion;
        h$ *= 1000003;
        return h$ ^= this.qualifier == null ? 0 : this.qualifier.hashCode();
    }

    static final class Builder
    extends Version.Builder {
        private String fullVersion;
        private Integer majorVersion;
        private Integer minorVersion;
        private Integer revisionVersion;
        private String qualifier;

        Builder() {
        }

        @Override
        Version.Builder setFullVersion(String fullVersion) {
            if (fullVersion == null) {
                throw new NullPointerException("Null fullVersion");
            }
            this.fullVersion = fullVersion;
            return this;
        }

        @Override
        Version.Builder setMajorVersion(int majorVersion) {
            this.majorVersion = majorVersion;
            return this;
        }

        @Override
        Version.Builder setMinorVersion(int minorVersion) {
            this.minorVersion = minorVersion;
            return this;
        }

        @Override
        Version.Builder setRevisionVersion(int revisionVersion) {
            this.revisionVersion = revisionVersion;
            return this;
        }

        @Override
        Version.Builder setQualifier(String qualifier) {
            this.qualifier = qualifier;
            return this;
        }

        @Override
        Version build() {
            String missing = "";
            if (this.fullVersion == null) {
                missing = missing + " fullVersion";
            }
            if (this.majorVersion == null) {
                missing = missing + " majorVersion";
            }
            if (this.minorVersion == null) {
                missing = missing + " minorVersion";
            }
            if (this.revisionVersion == null) {
                missing = missing + " revisionVersion";
            }
            if (!missing.isEmpty()) {
                throw new IllegalStateException("Missing required properties:" + missing);
            }
            return new AutoValue_Version(this.fullVersion, this.majorVersion, this.minorVersion, this.revisionVersion, this.qualifier);
        }
    }
}

