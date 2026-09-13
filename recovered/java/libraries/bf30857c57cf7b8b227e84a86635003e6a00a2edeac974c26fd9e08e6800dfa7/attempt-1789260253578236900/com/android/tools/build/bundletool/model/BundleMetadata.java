/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model;

import com.android.tools.build.bundletool.model.AutoValue_BundleMetadata;
import com.android.tools.build.bundletool.model.InputStreamSupplier;
import com.android.tools.build.bundletool.model.ZipPath;
import com.google.auto.value.AutoValue;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.errorprone.annotations.Immutable;
import java.util.Optional;

@Immutable
@AutoValue
@AutoValue.CopyAnnotations
public abstract class BundleMetadata {
    public static final String BUNDLETOOL_NAMESPACE = "com.android.tools.build.bundletool";
    public static final String MAIN_DEX_LIST_FILE_NAME = "mainDexList.txt";

    public abstract ImmutableMap<ZipPath, InputStreamSupplier> getFileDataMap();

    public static Builder builder() {
        return new AutoValue_BundleMetadata.Builder();
    }

    public Optional<InputStreamSupplier> getFileData(String namespacedDir, String fileName) {
        return Optional.ofNullable(this.getFileDataMap().get(BundleMetadata.toMetadataPath(namespacedDir, fileName)));
    }

    private static ZipPath toMetadataPath(String namespacedDir, String fileName) {
        return BundleMetadata.checkMetadataPath(ZipPath.create(namespacedDir).resolve(fileName));
    }

    private static ZipPath checkMetadataPath(ZipPath path) {
        Preconditions.checkArgument(path.getNameCount() >= 2, "The metadata file path '%s' is too shallow.", (Object)path);
        Preconditions.checkArgument(path.getName(0).toString().contains("."), "Top-level directories for metadata files must be namespaced (eg. 'com.package'), got %s'.", (Object)path.getName(0));
        return path;
    }

    @AutoValue.Builder
    public static abstract class Builder {
        abstract ImmutableMap.Builder<ZipPath, InputStreamSupplier> fileDataMapBuilder();

        public Builder addFile(String namespacedDir, String fileName, InputStreamSupplier dataSupplier) {
            return this.addFile(BundleMetadata.toMetadataPath(namespacedDir, fileName), dataSupplier);
        }

        public Builder addFile(ZipPath path, InputStreamSupplier dataSupplier) {
            this.fileDataMapBuilder().put(BundleMetadata.checkMetadataPath(path), dataSupplier);
            return this;
        }

        public abstract BundleMetadata build();
    }
}

