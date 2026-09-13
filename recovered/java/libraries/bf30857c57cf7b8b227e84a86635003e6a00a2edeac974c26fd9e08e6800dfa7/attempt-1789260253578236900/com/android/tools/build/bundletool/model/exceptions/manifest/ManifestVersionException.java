/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model.exceptions.manifest;

import com.android.aapt.Resources;
import com.android.bundle.Errors;
import com.android.tools.build.bundletool.model.exceptions.manifest.ManifestValidationException;
import com.google.errorprone.annotations.FormatMethod;
import com.google.errorprone.annotations.FormatString;

public abstract class ManifestVersionException
extends ManifestValidationException {
    @FormatMethod
    private ManifestVersionException(@FormatString String message, Object ... args) {
        super(message, args);
    }

    public static class VersionCodeInvalidException
    extends ManifestVersionException {
        public VersionCodeInvalidException(Resources.XmlAttribute attribute) {
            this(attribute.getValue());
        }

        public VersionCodeInvalidException(String value) {
            super("Type of versionCode is expected to be decimal integer, found: '%s'.", new Object[]{value});
        }

        @Override
        protected void customizeProto(Errors.BundleToolError.Builder builder) {
            builder.setManifestInvalidVersionCode(Errors.ManifestInvalidVersionCodeError.getDefaultInstance());
        }
    }

    public static class VersionCodeMissingException
    extends ManifestVersionException {
        public VersionCodeMissingException() {
            super("Version code not found in manifest.", new Object[0]);
        }

        @Override
        protected void customizeProto(Errors.BundleToolError.Builder builder) {
            builder.setManifestMissingVersionCode(Errors.ManifestMissingVersionCodeError.getDefaultInstance());
        }
    }
}

