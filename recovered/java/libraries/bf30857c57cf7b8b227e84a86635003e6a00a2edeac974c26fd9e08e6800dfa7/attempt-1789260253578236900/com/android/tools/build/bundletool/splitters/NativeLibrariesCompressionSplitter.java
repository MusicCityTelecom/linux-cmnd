/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.splitters;

import com.android.bundle.Files;
import com.android.tools.build.bundletool.model.ManifestMutator;
import com.android.tools.build.bundletool.model.ModuleEntry;
import com.android.tools.build.bundletool.model.ModuleSplit;
import com.android.tools.build.bundletool.splitters.ApkGenerationConfiguration;
import com.android.tools.build.bundletool.splitters.ModuleSplitSplitter;
import com.android.tools.build.bundletool.splitters.NativeLibrariesHelper;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import java.util.List;

public final class NativeLibrariesCompressionSplitter
implements ModuleSplitSplitter {
    private final ApkGenerationConfiguration apkGenerationConfiguration;

    @VisibleForTesting
    NativeLibrariesCompressionSplitter() {
        this(ApkGenerationConfiguration.getDefaultInstance());
    }

    public NativeLibrariesCompressionSplitter(ApkGenerationConfiguration apkGenerationConfiguration) {
        this.apkGenerationConfiguration = apkGenerationConfiguration;
    }

    @Override
    public ImmutableCollection<ModuleSplit> split(ModuleSplit moduleSplit) {
        Preconditions.checkState(!moduleSplit.getApkTargeting().hasSdkVersionTargeting(), "Split already targets SDK version.");
        if (!moduleSplit.getNativeConfig().isPresent()) {
            return ImmutableList.of(moduleSplit);
        }
        List<Files.TargetedNativeDirectory> allTargetedDirectories = moduleSplit.getNativeConfig().get().getDirectoryList();
        ImmutableSet<ModuleEntry> libraryEntries = allTargetedDirectories.stream().flatMap(directory -> moduleSplit.findEntriesUnderPath(directory.getPath())).collect(ImmutableSet.toImmutableSet());
        boolean shouldCompress = !this.supportUncompressedNativeLibs(moduleSplit);
        return ImmutableList.of(this.createModuleSplit(moduleSplit, this.mergeAndSetCompression(libraryEntries, moduleSplit, shouldCompress), shouldCompress));
    }

    private boolean supportUncompressedNativeLibs(ModuleSplit moduleSplit) {
        if (this.apkGenerationConfiguration.isForInstantAppVariants()) {
            return true;
        }
        if (NativeLibrariesCompressionSplitter.targetsAtLeast(28, moduleSplit)) {
            return true;
        }
        if (NativeLibrariesCompressionSplitter.targetsAtLeast(24, moduleSplit)) {
            return !this.apkGenerationConfiguration.isInstallableOnExternalStorage();
        }
        if (NativeLibrariesCompressionSplitter.targetsAtLeast(23, moduleSplit)) {
            return !this.apkGenerationConfiguration.isInstallableOnExternalStorage() && !NativeLibrariesHelper.mayHaveNativeActivities(moduleSplit);
        }
        return false;
    }

    private static boolean targetsAtLeast(int version, ModuleSplit moduleSplit) {
        return NativeLibrariesCompressionSplitter.getSdkVersion(moduleSplit) >= version;
    }

    private static int getSdkVersion(ModuleSplit moduleSplit) {
        return Iterables.getOnlyElement(moduleSplit.getVariantTargeting().getSdkVersionTargeting().getValueList()).getMin().getValue();
    }

    private ImmutableList<ModuleEntry> mergeAndSetCompression(ImmutableSet<ModuleEntry> libraryEntries, ModuleSplit moduleSplit, boolean shouldCompress) {
        ImmutableSet nonLibraryEntries = moduleSplit.getEntries().stream().filter(entry -> !libraryEntries.contains(entry)).collect(ImmutableSet.toImmutableSet());
        return ((ImmutableList.Builder)((ImmutableList.Builder)ImmutableList.builder().addAll((Iterable)libraryEntries.stream().map(moduleEntry -> moduleEntry.toBuilder().setShouldCompress(shouldCompress).build()).collect(ImmutableList.toImmutableList()))).addAll((Iterable)nonLibraryEntries)).build();
    }

    private ModuleSplit createModuleSplit(ModuleSplit moduleSplit, ImmutableList<ModuleEntry> moduleEntries, boolean extractNativeLibs) {
        return moduleSplit.toBuilder().addMasterManifestMutator(ManifestMutator.withExtractNativeLibs(extractNativeLibs)).setEntries(moduleEntries).build();
    }
}

