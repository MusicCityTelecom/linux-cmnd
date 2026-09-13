/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model.exceptions.manifest;

import com.android.aapt.Resources;
import com.android.bundle.Errors;
import com.android.tools.build.bundletool.model.exceptions.manifest.ManifestValidationException;
import com.google.errorprone.annotations.FormatMethod;
import com.google.errorprone.annotations.FormatString;

public abstract class ManifestSdkTargetingException
extends ManifestValidationException {
    @FormatMethod
    private ManifestSdkTargetingException(@FormatString String message, Object ... args) {
        super(message, args);
    }

    public static class MaxSdkLessThanMinInstantSdk
    extends ManifestSdkTargetingException {
        public static final int MIN_INSTANT_SDK_VERSION = 21;
        private final int maxSdk;

        public MaxSdkLessThanMinInstantSdk(int maxSdk) {
            super("maxSdkVersion (%d) is less than minimum sdk allowed for instant apps (%d).", new Object[]{maxSdk, 21});
            this.maxSdk = maxSdk;
        }

        @Override
        protected void customizeProto(Errors.BundleToolError.Builder builder) {
            builder.setManifestMaxSdkLessThanMinInstantSdk(Errors.ManifestMaxSdkLessThanMinInstantSdkError.newBuilder().setMaxSdk(this.maxSdk));
        }
    }

    public static class MinSdkGreaterThanMaxSdkException
    extends ManifestSdkTargetingException {
        private final int minSdk;
        private final int maxSdk;

        public MinSdkGreaterThanMaxSdkException(int minSdk, int maxSdk) {
            super("minSdkVersion (%d) is greater than maxSdkVersion (%d).", new Object[]{minSdk, maxSdk});
            this.minSdk = minSdk;
            this.maxSdk = maxSdk;
        }

        @Override
        protected void customizeProto(Errors.BundleToolError.Builder builder) {
            builder.setManifestMinSdkGreaterThanMax(Errors.ManifestMinSdkGreaterThanMaxSdkError.newBuilder().setMinSdk(this.minSdk).setMaxSdk(this.maxSdk));
        }
    }

    public static class MinSdkInvalidException
    extends ManifestSdkTargetingException {
        private final String minSdk;

        public MinSdkInvalidException(Resources.XmlAttribute attribute) {
            super("Type of minSdkVersion is expected to be decimal integer, found: '%s'.", new Object[]{attribute.getValue()});
            this.minSdk = attribute.getValue();
        }

        public MinSdkInvalidException(int value) {
            super("minSdkVersion must be nonnegative, found: (%d).", new Object[]{value});
            this.minSdk = Integer.toString(value);
        }

        @Override
        protected void customizeProto(Errors.BundleToolError.Builder builder) {
            builder.setManifestMinSdkInvalid(Errors.ManifestMinSdkInvalidError.newBuilder().setMinSdk(this.minSdk));
        }
    }

    public static class MaxSdkInvalidException
    extends ManifestSdkTargetingException {
        private final String maxSdk;

        public MaxSdkInvalidException(Resources.XmlAttribute attribute) {
            super("Type of maxSdkVersion is expected to be decimal integer, found: '%s'.", new Object[]{attribute.getValue()});
            this.maxSdk = attribute.getValue();
        }

        public MaxSdkInvalidException(int value) {
            super("maxSdkVersion must be nonnegative, found: (%d).", new Object[]{value});
            this.maxSdk = Integer.toString(value);
        }

        @Override
        protected void customizeProto(Errors.BundleToolError.Builder builder) {
            builder.setManifestMaxSdkInvalid(Errors.ManifestMaxSdkInvalidError.newBuilder().setMaxSdk(this.maxSdk));
        }
    }
}

