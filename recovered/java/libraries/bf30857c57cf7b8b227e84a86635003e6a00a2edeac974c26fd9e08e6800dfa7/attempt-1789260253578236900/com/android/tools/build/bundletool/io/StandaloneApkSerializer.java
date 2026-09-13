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
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import java.nio.file.Path;
import java.util.Optional;

public class StandaloneApkSerializer {
    private final ApkPathManager apkPathManager;
    private final ApkSerializerHelper apkSerializerHelper;

    public StandaloneApkSerializer(ApkPathManager apkPathManager, Aapt2Command aapt2Command, Optional<SigningConfiguration> signingConfig, Optional<SigningConfiguration> stampSigningConfig, Version bundleVersion, Config.Compression compression) {
        this.apkPathManager = apkPathManager;
        this.apkSerializerHelper = new ApkSerializerHelper(aapt2Command, signingConfig, stampSigningConfig, bundleVersion, compression);
    }

    public Commands.ApkDescription writeToDisk(ModuleSplit standaloneSplit, Path outputDirectory) {
        ZipPath apkPath = this.apkPathManager.getApkPath(standaloneSplit);
        return this.writeToDiskInternal(standaloneSplit, outputDirectory, apkPath);
    }

    public Commands.ApkDescription writeToDiskAsUniversal(ModuleSplit standaloneSplit, Path outputDirectory) {
        return this.writeToDiskInternal(standaloneSplit, outputDirectory, ZipPath.create("universal.apk"));
    }

    public Commands.ApkDescription writeSystemApkToDisk(ModuleSplit systemSplit, Path outputDirectory) {
        return this.writeSystemApkToDiskInternal(systemSplit, outputDirectory, Commands.SystemApkMetadata.SystemApkType.SYSTEM);
    }

    public ImmutableList<Commands.ApkDescription> writeCompressedSystemApksToDisk(ModuleSplit systemSplit, Path outputDirectory) {
        Commands.ApkDescription stubApkDescription = this.writeSystemApkToDiskInternal(StandaloneApkSerializer.splitWithOnlyManifest(systemSplit), outputDirectory, Commands.SystemApkMetadata.SystemApkType.SYSTEM_STUB);
        ZipPath compressedApkPath = ZipPath.create(StandaloneApkSerializer.getCompressedApkPathFromStubApkPath(stubApkDescription.getPath()));
        this.apkSerializerHelper.writeCompressedApkToZipFile(systemSplit, outputDirectory.resolve(compressedApkPath.toString()));
        return ImmutableList.of(stubApkDescription, StandaloneApkSerializer.createSystemApkDescription(systemSplit, compressedApkPath, Commands.SystemApkMetadata.SystemApkType.SYSTEM_COMPRESSED));
    }

    @VisibleForTesting
    Commands.ApkDescription writeToDiskInternal(ModuleSplit standaloneSplit, Path outputDirectory, ZipPath apkPath) {
        this.apkSerializerHelper.writeToZipFile(standaloneSplit, outputDirectory.resolve(apkPath.toString()));
        Commands.ApkDescription.Builder apkDescription = Commands.ApkDescription.newBuilder().setPath(apkPath.toString()).setTargeting(standaloneSplit.getApkTargeting());
        if (standaloneSplit.isApex()) {
            apkDescription.setApexApkMetadata(Commands.ApexApkMetadata.newBuilder().addAllApexEmbeddedApkConfig(standaloneSplit.getApexConfig().get().getApexEmbeddedApkConfigList()).build());
        } else {
            apkDescription.setStandaloneApkMetadata(Commands.StandaloneApkMetadata.newBuilder().addAllFusedModuleName(standaloneSplit.getAndroidManifest().getFusedModuleNames()));
        }
        return apkDescription.build();
    }

    private Commands.ApkDescription writeSystemApkToDiskInternal(ModuleSplit systemSplit, Path outputDirectory, Commands.SystemApkMetadata.SystemApkType apkType) {
        ZipPath apkPath = this.apkPathManager.getApkPath(systemSplit);
        this.apkSerializerHelper.writeToZipFile(systemSplit, outputDirectory.resolve(apkPath.toString()));
        return StandaloneApkSerializer.createSystemApkDescription(systemSplit, apkPath, apkType);
    }

    private static String getCompressedApkPathFromStubApkPath(String stubApkPath) {
        Preconditions.checkArgument(stubApkPath.endsWith(".apk"));
        return stubApkPath + ".gz";
    }

    private static Commands.ApkDescription createSystemApkDescription(ModuleSplit systemSplit, ZipPath apkPath, Commands.SystemApkMetadata.SystemApkType apkType) {
        Commands.ApkDescription.Builder apkDescription = Commands.ApkDescription.newBuilder().setPath(apkPath.toString()).setTargeting(systemSplit.getApkTargeting());
        if (systemSplit.isBaseModuleSplit() && systemSplit.isMasterSplit()) {
            apkDescription.setSystemApkMetadata(Commands.SystemApkMetadata.newBuilder().addAllFusedModuleName(systemSplit.getAndroidManifest().getFusedModuleNames()).setSystemApkType(apkType));
        } else {
            apkDescription.setSplitApkMetadata(Commands.SplitApkMetadata.newBuilder().setSplitId(systemSplit.getAndroidManifest().getSplitId().get()).setIsMasterSplit(systemSplit.isMasterSplit()));
        }
        return apkDescription.build();
    }

    private static ModuleSplit splitWithOnlyManifest(ModuleSplit split) {
        return ModuleSplit.builder().setModuleName(split.getModuleName()).setSplitType(split.getSplitType()).setVariantTargeting(split.getVariantTargeting()).setApkTargeting(split.getApkTargeting()).setAndroidManifest(split.getAndroidManifest()).setMasterSplit(split.isMasterSplit()).build();
    }
}

