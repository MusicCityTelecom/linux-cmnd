/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.apkzlib.zfile;

import com.android.tools.build.apkzlib.sign.SigningOptions;
import com.android.tools.build.apkzlib.zfile.ApkCreator;
import com.android.tools.build.apkzlib.zfile.NativeLibrariesPackagingMode;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import java.io.File;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface ApkCreatorFactory {
    public ApkCreator make(CreationData var1);

    public static class CreationData {
        private final File apkPath;
        private final Optional<SigningOptions> signingOptions;
        @Nullable
        private final String builtBy;
        @Nullable
        private final String createdBy;
        private final NativeLibrariesPackagingMode nativeLibrariesPackagingMode;
        private final Predicate<String> noCompressPredicate;

        public CreationData(File apkPath, @Nonnull Optional<SigningOptions> signingOptions, @Nullable String builtBy, @Nullable String createdBy, NativeLibrariesPackagingMode nativeLibrariesPackagingMode, Predicate<String> noCompressPredicate) {
            this.apkPath = apkPath;
            this.signingOptions = signingOptions;
            this.builtBy = builtBy;
            this.createdBy = createdBy;
            this.nativeLibrariesPackagingMode = Preconditions.checkNotNull(nativeLibrariesPackagingMode);
            this.noCompressPredicate = Preconditions.checkNotNull(noCompressPredicate);
        }

        public File getApkPath() {
            return this.apkPath;
        }

        @Nonnull
        public Optional<SigningOptions> getSigningOptions() {
            return this.signingOptions;
        }

        @Nullable
        public String getBuiltBy() {
            return this.builtBy;
        }

        @Nullable
        public String getCreatedBy() {
            return this.createdBy;
        }

        public NativeLibrariesPackagingMode getNativeLibrariesPackagingMode() {
            return this.nativeLibrariesPackagingMode;
        }

        public Predicate<String> getNoCompressPredicate() {
            return this.noCompressPredicate;
        }
    }
}

