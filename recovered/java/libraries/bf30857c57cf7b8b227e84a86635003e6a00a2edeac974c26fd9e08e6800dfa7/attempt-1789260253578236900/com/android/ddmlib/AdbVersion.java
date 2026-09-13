/*
 * Decompiled with CFR 0.152.
 */
package com.android.ddmlib;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdbVersion
implements Comparable<AdbVersion> {
    public static final AdbVersion UNKNOWN = new AdbVersion(-1, -1, -1);
    private static final Pattern ADB_VERSION_PATTERN = Pattern.compile("^.*(\\d+)\\.(\\d+)\\.(\\d+).*");
    public final int major;
    public final int minor;
    public final int micro;

    private AdbVersion(int major, int minor, int micro) {
        this.major = major;
        this.minor = minor;
        this.micro = micro;
    }

    public String toString() {
        return String.format(Locale.US, "%1$d.%2$d.%3$d", this.major, this.minor, this.micro);
    }

    @Override
    public int compareTo(AdbVersion o3) {
        if (this.major != o3.major) {
            return this.major - o3.major;
        }
        if (this.minor != o3.minor) {
            return this.minor - o3.minor;
        }
        return this.micro - o3.micro;
    }

    public static AdbVersion parseFrom(String input) {
        Matcher matcher = ADB_VERSION_PATTERN.matcher(input);
        if (matcher.matches()) {
            int major = Integer.parseInt(matcher.group(1));
            int minor = Integer.parseInt(matcher.group(2));
            int micro = Integer.parseInt(matcher.group(3));
            return new AdbVersion(major, minor, micro);
        }
        return UNKNOWN;
    }

    public boolean equals(Object o3) {
        if (this == o3) {
            return true;
        }
        if (o3 == null || this.getClass() != o3.getClass()) {
            return false;
        }
        AdbVersion version = (AdbVersion)o3;
        if (this.major != version.major) {
            return false;
        }
        if (this.minor != version.minor) {
            return false;
        }
        return this.micro == version.micro;
    }

    public int hashCode() {
        int result = this.major;
        result = 31 * result + this.minor;
        result = 31 * result + this.micro;
        return result;
    }
}

