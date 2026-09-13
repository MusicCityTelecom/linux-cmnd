/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model.utils;

import com.android.bundle.Commands;
import com.android.tools.build.bundletool.model.BundleModuleName;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Streams;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.Collection;

public final class ResultUtils {
    public static Commands.BuildApksResult readTableOfContents(Path apksPath) {
        try {
            if (Files.isDirectory(apksPath, new LinkOption[0])) {
                return ResultUtils.readTableOfContentFromApksDirectory(apksPath);
            }
            return ResultUtils.readTableOfContentFromApksArchive(apksPath);
        }
        catch (IOException e2) {
            throw new UncheckedIOException(String.format("Error while reading the table of contents file from '%s'.", apksPath), e2);
        }
    }

    /*
     * Exception decompiling
     */
    private static Commands.BuildApksResult readTableOfContentFromApksArchive(Path apksArchivePath) throws IOException {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:412)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:487)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    private static Commands.BuildApksResult readTableOfContentFromApksDirectory(Path apksDirectoryPath) throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream(apksDirectoryPath.resolve("toc.pb").toFile());){
            Commands.BuildApksResult buildApksResult = Commands.BuildApksResult.parseFrom(fileInputStream);
            return buildApksResult;
        }
    }

    public static ImmutableList<Commands.Variant> splitApkVariants(Commands.BuildApksResult result) {
        return ResultUtils.splitApkVariants(ImmutableList.copyOf(result.getVariantList()));
    }

    public static ImmutableList<Commands.Variant> splitApkVariants(ImmutableList<Commands.Variant> variants) {
        return variants.stream().filter(ResultUtils::isSplitApkVariant).collect(ImmutableList.toImmutableList());
    }

    public static ImmutableList<Commands.Variant> instantApkVariants(Commands.BuildApksResult result) {
        return ResultUtils.instantApkVariants(ImmutableList.copyOf(result.getVariantList()));
    }

    public static ImmutableList<Commands.Variant> instantApkVariants(ImmutableList<Commands.Variant> variants) {
        return variants.stream().filter(ResultUtils::isInstantApkVariant).collect(ImmutableList.toImmutableList());
    }

    public static ImmutableList<Commands.Variant> standaloneApkVariants(Commands.BuildApksResult result) {
        return ResultUtils.standaloneApkVariants(ImmutableList.copyOf(result.getVariantList()));
    }

    public static ImmutableList<Commands.Variant> standaloneApkVariants(ImmutableList<Commands.Variant> variants) {
        return variants.stream().filter(ResultUtils::isStandaloneApkVariant).collect(ImmutableList.toImmutableList());
    }

    public static ImmutableList<Commands.Variant> apexApkVariants(Commands.BuildApksResult result) {
        return ResultUtils.apexApkVariants(ImmutableList.copyOf(result.getVariantList()));
    }

    public static ImmutableList<Commands.Variant> apexApkVariants(ImmutableList<Commands.Variant> variants) {
        return variants.stream().filter(ResultUtils::isApexApkVariant).collect(ImmutableList.toImmutableList());
    }

    public static ImmutableList<Commands.Variant> systemApkVariants(Commands.BuildApksResult result) {
        return ResultUtils.systemApkVariants(ImmutableList.copyOf(result.getVariantList()));
    }

    public static ImmutableList<Commands.Variant> systemApkVariants(ImmutableList<Commands.Variant> variants) {
        return variants.stream().filter(variant -> ResultUtils.isSystemApkVariant(variant)).collect(ImmutableList.toImmutableList());
    }

    public static boolean isSplitApkVariant(Commands.Variant variant) {
        return variant.getApkSetList().stream().flatMap(apkSet -> apkSet.getApkDescriptionList().stream()).allMatch(Commands.ApkDescription::hasSplitApkMetadata);
    }

    public static boolean isStandaloneApkVariant(Commands.Variant variant) {
        return variant.getApkSetList().stream().flatMap(apkSet -> apkSet.getApkDescriptionList().stream()).allMatch(Commands.ApkDescription::hasStandaloneApkMetadata);
    }

    public static boolean isApexApkVariant(Commands.Variant variant) {
        return variant.getApkSetList().stream().flatMap(apkSet -> apkSet.getApkDescriptionList().stream()).allMatch(Commands.ApkDescription::hasApexApkMetadata);
    }

    public static boolean isInstantApkVariant(Commands.Variant variant) {
        return variant.getApkSetList().stream().flatMap(apkSet -> apkSet.getApkDescriptionList().stream()).allMatch(Commands.ApkDescription::hasInstantApkMetadata);
    }

    public static boolean isSystemApkVariant(Commands.Variant variant) {
        return variant.getApkSetList().stream().flatMap(apkSet -> apkSet.getApkDescriptionList().stream()).anyMatch(Commands.ApkDescription::hasSystemApkMetadata);
    }

    public static ImmutableSet<String> getAllTargetedLanguages(Commands.BuildApksResult result) {
        return Streams.concat(result.getAssetSliceSetList().stream().flatMap(assetSliceSet -> assetSliceSet.getApkDescriptionList().stream()), result.getVariantList().stream().flatMap(variant -> variant.getApkSetList().stream()).flatMap(apkSet -> apkSet.getApkDescriptionList().stream())).flatMap(apkDescription -> apkDescription.getTargeting().getLanguageTargeting().getValueList().stream()).collect(ImmutableSet.toImmutableSet());
    }

    public static ImmutableSet<String> getAllBaseMasterSplitPaths(Commands.BuildApksResult toc) {
        return ResultUtils.splitApkVariants(toc).stream().map(Commands.Variant::getApkSetList).flatMap(Collection::stream).filter(apkSet -> apkSet.getModuleMetadata().getName().equals(BundleModuleName.BASE_MODULE_NAME.getName())).map(Commands.ApkSet::getApkDescriptionList).flatMap(Collection::stream).filter(apkDescription -> apkDescription.getSplitApkMetadata().getIsMasterSplit()).map(Commands.ApkDescription::getPath).collect(ImmutableSet.toImmutableSet());
    }

    private ResultUtils() {
    }
}

