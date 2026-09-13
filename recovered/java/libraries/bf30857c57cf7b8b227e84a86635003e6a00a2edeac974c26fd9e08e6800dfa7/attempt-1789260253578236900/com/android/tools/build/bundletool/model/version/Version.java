/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model.version;

import com.android.tools.build.bundletool.model.exceptions.ValidationException;
import com.android.tools.build.bundletool.model.version.AutoValue_Version;
import com.google.auto.value.AutoValue;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;
import com.google.errorprone.annotations.Immutable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nullable;

@Immutable
@AutoValue
@AutoValue.CopyAnnotations
public abstract class Version
implements Comparable<Version> {
    private static final Pattern VERSION_REGEXP = Pattern.compile("^(?<major>\\d+?)\\.(?<minor>\\d+?)\\.(?<revision>\\d+?)(-(?<qualifier>.+))?$");

    abstract String getFullVersion();

    abstract int getMajorVersion();

    abstract int getMinorVersion();

    abstract int getRevisionVersion();

    @Nullable
    abstract String getQualifier();

    public static Version of(String version) {
        Matcher matcher = VERSION_REGEXP.matcher(version);
        if (!matcher.matches()) {
            throw ValidationException.builder().withMessage("Version must match the format '<major>.<minor>.<revision>[-<qualifier>]', but found '%s'.", version).build();
        }
        return Version.builder().setFullVersion(version).setMajorVersion(Integer.parseInt(matcher.group("major"))).setMinorVersion(Integer.parseInt(matcher.group("minor"))).setRevisionVersion(Integer.parseInt(matcher.group("revision"))).setQualifier(matcher.group("qualifier")).build();
    }

    @Override
    public int compareTo(Version otherVersion) {
        return ComparisonChain.start().compare(this.getMajorVersion(), otherVersion.getMajorVersion()).compare(this.getMinorVersion(), otherVersion.getMinorVersion()).compare(this.getRevisionVersion(), otherVersion.getRevisionVersion()).compare(this.getQualifier(), otherVersion.getQualifier(), Ordering.natural().onResultOf(q3 -> q3.replaceAll("^dev$", "")).nullsLast()).result();
    }

    public boolean isOlderThan(Version version) {
        return this.compareTo(version) < 0;
    }

    public boolean isNewerThan(Version version) {
        return this.compareTo(version) > 0;
    }

    public final String toString() {
        return this.getFullVersion();
    }

    static Builder builder() {
        return new AutoValue_Version.Builder();
    }

    @AutoValue.Builder
    static abstract class Builder {
        Builder() {
        }

        abstract Builder setFullVersion(String var1);

        abstract Builder setMajorVersion(int var1);

        abstract Builder setMinorVersion(int var1);

        abstract Builder setRevisionVersion(int var1);

        abstract Builder setQualifier(String var1);

        abstract Version build();
    }
}

