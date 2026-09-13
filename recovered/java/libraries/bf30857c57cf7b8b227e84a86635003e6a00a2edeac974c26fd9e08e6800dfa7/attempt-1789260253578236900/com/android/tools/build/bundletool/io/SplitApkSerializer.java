/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.io;

import com.android.bundle.Commands;
import com.android.bundle.Config;
import com.android.tools.build.bundletool.io.ApkPathManager;
import com.android.tools.build.bundletool.io.ApkSerializerHelper;
import com.android.tools.build.bundletool.model.Aapt2Command;
import com.android.tools.build.bundletool.model.ModuleSplit;
import com.android.tools.build.bundletool.model.SigningConfiguration;
import com.android.tools.build.bundletool.model.ZipPath;
import com.android.tools.build.bundletool.model.version.Version;
import com.google.common.base.Preconditions;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.Optional;
import java.util.function.BiFunction;

public class SplitApkSerializer {
    private final ApkPathManager apkPathManager;
    private final ApkSerializerHelper apkSerializerHelper;

    public SplitApkSerializer(ApkPathManager apkPathManager, Aapt2Command aapt2Command, Optional<SigningConfiguration> signingConfig, Optional<SigningConfiguration> stampSigningConfig, Version bundleVersion, Config.Compression compression) {
        this.apkPathManager = apkPathManager;
        this.apkSerializerHelper = new ApkSerializerHelper(aapt2Command, signingConfig, stampSigningConfig, bundleVersion, compression);
    }

    public Commands.ApkDescription writeSplitToDisk(ModuleSplit split, Path outputDirectory) {
        return this.writeToDisk(split, outputDirectory, Commands.ApkDescription.Builder::setSplitApkMetadata);
    }

    public Commands.ApkDescription writeInstantSplitToDisk(ModuleSplit split, Path outputDirectory) {
        return this.writeToDisk(split, outputDirectory, Commands.ApkDescription.Builder::setInstantApkMetadata);
    }

    public Commands.ApkDescription writeAssetSliceToDisk(ModuleSplit split, Path outputDirectory) {
        return this.writeToDisk(split, outputDirectory, Commands.ApkDescription.Builder::setAssetSliceMetadata);
    }

    private Commands.ApkDescription writeToDisk(ModuleSplit split, Path outputDirectory, BiFunction<Commands.ApkDescription.Builder, Commands.SplitApkMetadata, Commands.ApkDescription.Builder> setApkMetadata) {
        Preconditions.checkState(Files.isDirectory(outputDirectory, new LinkOption[0]), "Output directory does not exist.");
        ZipPath apkPath = this.apkPathManager.getApkPath(split);
        this.apkSerializerHelper.writeToZipFile(split, outputDirectory.resolve(apkPath.toString()));
        Commands.ApkDescription.Builder builder = Commands.ApkDescription.newBuilder().setPath(apkPath.toString()).setTargeting(split.getApkTargeting());
        return setApkMetadata.apply(builder, Commands.SplitApkMetadata.newBuilder().setSplitId(split.getAndroidManifest().getSplitId().orElse("")).setIsMasterSplit(split.isMasterSplit()).build()).build();
    }
}

