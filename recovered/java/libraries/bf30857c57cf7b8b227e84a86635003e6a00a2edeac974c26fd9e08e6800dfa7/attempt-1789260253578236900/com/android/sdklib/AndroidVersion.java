/*
 * Decompiled with CFR 0.152.
 */
package com.android.sdklib;

import com.google.common.base.Objects;
import java.util.regex.Pattern;

public final class AndroidVersion
implements Comparable<AndroidVersion> {
    private final int mApiLevel;
    private final String mCodename;
    public static final AndroidVersion DEFAULT = new AndroidVersion(1, null);
    public static final AndroidVersion ART_RUNTIME = new AndroidVersion(21, null);
    public static final AndroidVersion BINDER_CMD_AVAILABLE = new AndroidVersion(24, null);
    public static final AndroidVersion ALLOW_SPLIT_APK_INSTALLATION = new AndroidVersion(21, null);
    public static final AndroidVersion SUPPORTS_MULTI_USER = new AndroidVersion(17, null);
    public static final int MIN_RECOMMENDED_API = 22;
    public static final int MIN_RECOMMENDED_WEAR_API = 25;

    public AndroidVersion(int apiLevel, String codename) {
        this.mApiLevel = apiLevel;
        this.mCodename = AndroidVersion.sanitizeCodename(codename);
    }

    public AndroidVersion(int apiLevel) {
        this(apiLevel, null);
    }

    public AndroidVersion(String apiOrCodename) throws AndroidVersionException {
        String codename;
        int apiLevel;
        block3: {
            apiLevel = 0;
            codename = null;
            try {
                apiLevel = Integer.parseInt(apiOrCodename);
            }
            catch (NumberFormatException ignore) {
                if ("REL".equals(apiOrCodename) || !Pattern.matches("[A-Z_]+", apiOrCodename)) break block3;
                codename = apiOrCodename;
            }
        }
        this.mApiLevel = apiLevel;
        this.mCodename = AndroidVersion.sanitizeCodename(codename);
        if (this.mApiLevel <= 0 && codename == null) {
            throw new AndroidVersionException("Invalid android API or codename " + apiOrCodename, null);
        }
    }

    public int getApiLevel() {
        return this.mApiLevel;
    }

    public int getFeatureLevel() {
        return this.mCodename != null ? this.mApiLevel + 1 : this.mApiLevel;
    }

    public String getCodename() {
        return this.mCodename;
    }

    public String getApiString() {
        if (this.mCodename != null) {
            return this.mCodename;
        }
        return Integer.toString(this.mApiLevel);
    }

    public boolean isPreview() {
        return this.mCodename != null;
    }

    public boolean isLegacyMultidex() {
        return this.getFeatureLevel() < 21;
    }

    public boolean canRun(AndroidVersion appVersion) {
        if (appVersion.mCodename != null) {
            return appVersion.mCodename.equals(this.mCodename);
        }
        return this.mApiLevel >= appVersion.mApiLevel;
    }

    public boolean equals(int apiLevel) {
        return this.mCodename == null && apiLevel == this.mApiLevel;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof AndroidVersion)) {
            return false;
        }
        AndroidVersion other = (AndroidVersion)obj;
        return this.mApiLevel == other.mApiLevel && Objects.equal(this.mCodename, other.mCodename);
    }

    public int hashCode() {
        if (this.mCodename != null) {
            return this.mCodename.hashCode();
        }
        return this.mApiLevel;
    }

    public String toString() {
        String s3 = String.format("API %1$d", this.mApiLevel);
        if (this.isPreview()) {
            s3 = s3 + String.format(", %1$s preview", this.mCodename);
        }
        return s3;
    }

    @Override
    public int compareTo(AndroidVersion o3) {
        return this.compareTo(o3.mApiLevel, o3.mCodename);
    }

    public int compareTo(int apiLevel, String codename) {
        if (this.mCodename == null) {
            if (codename != null && this.mApiLevel == apiLevel) {
                return -1;
            }
            return this.mApiLevel - apiLevel;
        }
        if (this.mApiLevel == apiLevel) {
            if (codename == null) {
                return 1;
            }
            return this.mCodename.compareTo(codename);
        }
        return this.mApiLevel - apiLevel;
    }

    public boolean isGreaterOrEqualThan(int api) {
        return this.compareTo(api, null) >= 0;
    }

    private static String sanitizeCodename(String codename) {
        if (codename != null && ((codename = codename.trim()).isEmpty() || "REL".equals(codename))) {
            codename = null;
        }
        return codename;
    }

    public static final class AndroidVersionException
    extends Exception {
        private static final long serialVersionUID = 1L;

        public AndroidVersionException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static class VersionCodes {
        public static final int BASE = 1;
        public static final int BASE_1_1 = 2;
        public static final int CUPCAKE = 3;
        public static final int DONUT = 4;
        public static final int ECLAIR = 5;
        public static final int ECLAIR_0_1 = 6;
        public static final int ECLAIR_MR1 = 7;
        public static final int FROYO = 8;
        public static final int GINGERBREAD = 9;
        public static final int GINGERBREAD_MR1 = 10;
        public static final int HONEYCOMB = 11;
        public static final int HONEYCOMB_MR1 = 12;
        public static final int HONEYCOMB_MR2 = 13;
        public static final int ICE_CREAM_SANDWICH = 14;
        public static final int ICE_CREAM_SANDWICH_MR1 = 15;
        public static final int JELLY_BEAN = 16;
        public static final int JELLY_BEAN_MR1 = 17;
        public static final int JELLY_BEAN_MR2 = 18;
        public static final int KITKAT = 19;
        public static final int KITKAT_WATCH = 20;
        public static final int LOLLIPOP = 21;
        public static final int LOLLIPOP_MR1 = 22;
        public static final int M = 23;
        public static final int N = 24;
        public static final int N_MR1 = 25;
        public static final int O = 26;
        public static final int O_MR1 = 27;
        public static final int P = 28;
    }
}

