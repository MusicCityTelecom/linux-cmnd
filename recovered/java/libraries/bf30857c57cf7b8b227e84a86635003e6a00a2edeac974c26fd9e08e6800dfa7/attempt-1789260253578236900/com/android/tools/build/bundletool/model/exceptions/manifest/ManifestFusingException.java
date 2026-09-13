/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model.exceptions.manifest;

import com.android.bundle.Errors;
import com.android.tools.build.bundletool.model.exceptions.manifest.ManifestValidationException;
import com.google.errorprone.annotations.FormatMethod;
import com.google.errorprone.annotations.FormatString;
import java.util.Optional;

public abstract class ManifestFusingException
extends ManifestValidationException {
    @FormatMethod
    private ManifestFusingException(@FormatString String message, Object ... args) {
        super(message, args);
    }

    public static class ModuleFusingConfigurationMissingException
    extends ManifestFusingException {
        private final String moduleName;

        public ModuleFusingConfigurationMissingException(String moduleName) {
            super("Module '%s' must specify its fusing configuration in AndroidManifest.xml.", new Object[]{moduleName});
            this.moduleName = moduleName;
        }

        @Override
        protected void customizeProto(Errors.BundleToolError.Builder builder) {
            builder.setManifestFusingConfigurationMissing(Errors.ManifestModuleFusingConfigurationMissingError.newBuilder().setModuleName(this.moduleName));
        }
    }

    public static class BaseModuleExcludedFromFusingException
    extends ManifestFusingException {
        public BaseModuleExcludedFromFusingException() {
            super("The base module cannot be excluded from fusing.", new Object[0]);
        }

        @Override
        protected void customizeProto(Errors.BundleToolError.Builder builder) {
            builder.setManifestFusingBaseModuleExcluded(Errors.ManifestBaseModuleExcludedFromFusingError.getDefaultInstance());
        }
    }

    public static class FusingMissingIncludeAttribute
    extends ManifestFusingException {
        private final Optional<String> splitId;

        public FusingMissingIncludeAttribute(Optional<String> splitId) {
            super("<fusing> element is missing the 'include' attribute%s.", new Object[]{splitId.map(id -> " (split: '" + id + "')").orElse("base")});
            this.splitId = splitId;
        }

        public Optional<String> getSplitId() {
            return this.splitId;
        }

        @Override
        protected void customizeProto(Errors.BundleToolError.Builder builder) {
            builder.setManifestFusingMissingIncludeAttribute(Errors.ManifestFusingMissingIncludeAttributeError.newBuilder().setModuleName(this.getSplitId().orElse("base")));
        }
    }
}

