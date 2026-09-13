/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.mergers;

import com.android.aapt.Resources;
import com.android.bundle.Devices;
import com.android.bundle.Files;
import com.android.bundle.Targeting;
import com.android.tools.build.bundletool.device.ApkMatcher;
import com.android.tools.build.bundletool.mergers.AndroidManifestMerger;
import com.android.tools.build.bundletool.mergers.DexMerger;
import com.android.tools.build.bundletool.mergers.MergingUtils;
import com.android.tools.build.bundletool.mergers.ResourceTableMerger;
import com.android.tools.build.bundletool.model.AndroidManifest;
import com.android.tools.build.bundletool.model.BundleMetadata;
import com.android.tools.build.bundletool.model.BundleModule;
import com.android.tools.build.bundletool.model.BundleModuleName;
import com.android.tools.build.bundletool.model.InputStreamSupplier;
import com.android.tools.build.bundletool.model.InputStreamSuppliers;
import com.android.tools.build.bundletool.model.ModuleEntry;
import com.android.tools.build.bundletool.model.ModuleSplit;
import com.android.tools.build.bundletool.model.ShardedSystemSplits;
import com.android.tools.build.bundletool.model.SuffixManager;
import com.android.tools.build.bundletool.model.ZipPath;
import com.android.tools.build.bundletool.model.exceptions.CommandExecutionException;
import com.android.tools.build.bundletool.model.utils.files.BufferedIo;
import com.android.tools.build.bundletool.model.version.Version;
import com.android.tools.build.bundletool.model.version.VersionGuardedFeature;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.MoreCollectors;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.google.common.collect.Streams;
import com.google.common.io.ByteStreams;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileAttribute;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ModuleSplitsToShardMerger {
    private final DexMerger dexMerger;
    private final Version bundleVersion;
    private final Path globalTempDir;

    public ModuleSplitsToShardMerger(DexMerger dexMerger, Version bundleVersion, Path globalTempDir) {
        this.dexMerger = dexMerger;
        this.bundleVersion = bundleVersion;
        this.globalTempDir = globalTempDir;
    }

    public ImmutableList<ModuleSplit> merge(ImmutableList<ImmutableList<ModuleSplit>> unfusedShards, BundleMetadata bundleMetadata) {
        HashMap<ImmutableSet<ModuleEntry>, ImmutableList<Path>> mergedDexCache = new HashMap<ImmutableSet<ModuleEntry>, ImmutableList<Path>>();
        ImmutableList.Builder shards = ImmutableList.builder();
        for (ImmutableList immutableList : unfusedShards) {
            shards.add(this.mergeSingleShard(immutableList, bundleMetadata, mergedDexCache));
        }
        return shards.build();
    }

    public ShardedSystemSplits mergeSystemShard(ImmutableCollection<ModuleSplit> splits, ImmutableSet<BundleModuleName> modulesToFuse, BundleMetadata bundleMetadata, Devices.DeviceSpec deviceSpec) {
        ApkMatcher deviceSpecMatcher = new ApkMatcher(deviceSpec);
        AndroidManifest baseMasterAndroidManifest = ModuleSplitsToShardMerger.getBaseMasterAndroidManifest(splits);
        ImmutableSet splitsOfFusedModules = splits.stream().filter(split -> modulesToFuse.contains(split.getModuleName())).collect(ImmutableSet.toImmutableSet());
        ImmutableSet<ModuleSplit> splitsOfNonFusedModules = Sets.difference(ImmutableSet.copyOf(splits), splitsOfFusedModules).immutableCopy();
        ImmutableSet<ModuleSplit> splitsForTheSystemApk = splitsOfFusedModules.stream().filter(split -> !split.getApkTargeting().hasLanguageTargeting() || deviceSpecMatcher.matchesModuleSplitByTargeting((ModuleSplit)split)).collect(ImmutableSet.toImmutableSet());
        ModuleSplit fusedSplit = this.mergeSingleShard(splitsForTheSystemApk, bundleMetadata, new HashMap<ImmutableSet<ModuleEntry>, ImmutableList<Path>>());
        ImmutableSet nonMatchedLanguageSplitsForFusedModules = Sets.difference(splitsOfFusedModules, splitsForTheSystemApk).stream().collect(Collectors.groupingBy(ModuleSplit::getApkTargeting)).values().stream().map(languageSplits -> this.mergeNonMatchedLanguageSplitsForSystemApks(ImmutableList.copyOf(languageSplits), bundleMetadata, baseMasterAndroidManifest)).collect(ImmutableSet.toImmutableSet());
        ImmutableList<ModuleSplit> otherSplits = Sets.union(nonMatchedLanguageSplitsForFusedModules, splitsOfNonFusedModules).stream().collect(Collectors.groupingBy(ModuleSplit::getModuleName)).values().stream().flatMap(ModuleSplitsToShardMerger::writeSplitIdInManifestHavingSameModule).collect(ImmutableList.toImmutableList());
        return ShardedSystemSplits.builder().setSystemImageSplit(fusedSplit).setAdditionalSplits(otherSplits).build();
    }

    @VisibleForTesting
    ModuleSplit mergeSingleShard(ImmutableCollection<ModuleSplit> splitsOfShard, BundleMetadata bundleMetadata, Map<ImmutableSet<ModuleEntry>, ImmutableList<Path>> mergedDexCache) {
        return this.mergeSingleShard(splitsOfShard, bundleMetadata, mergedDexCache, ModuleSplit.SplitType.STANDALONE, VersionGuardedFeature.FUSE_ACTIVITIES_FROM_FEATURE_MANIFESTS.enabledForVersion(this.bundleVersion) ? AndroidManifestMerger.fusingMerger() : AndroidManifestMerger.useBaseModuleManifestMerger());
    }

    private ModuleSplit mergeSingleShard(ImmutableCollection<ModuleSplit> splitsOfShard, BundleMetadata bundleMetadata, Map<ImmutableSet<ModuleEntry>, ImmutableList<Path>> mergedDexCache, ModuleSplit.SplitType mergedSplitType, AndroidManifestMerger manifestMerger) {
        ArrayListMultimap<BundleModuleName, ModuleEntry> dexFilesToMergeByModule = ArrayListMultimap.create();
        HashMultimap<BundleModuleName, AndroidManifest> androidManifestsToMergeByModule = HashMultimap.create();
        HashMap<ZipPath, ModuleEntry> mergedEntriesByPath = new HashMap<ZipPath, ModuleEntry>();
        Optional<Resources.ResourceTable> mergedResourceTable = Optional.empty();
        HashMap<String, Files.TargetedAssetsDirectory> mergedAssetsConfig = new HashMap<String, Files.TargetedAssetsDirectory>();
        Targeting.ApkTargeting mergedSplitTargeting = Targeting.ApkTargeting.getDefaultInstance();
        for (ModuleSplit split : splitsOfShard) {
            mergedResourceTable = this.mergeResourceTables(mergedResourceTable, split);
            mergedSplitTargeting = this.mergeSplitTargetings(mergedSplitTargeting, split);
            androidManifestsToMergeByModule.put(split.getModuleName(), split.getAndroidManifest());
            for (ModuleEntry entry : split.getEntries()) {
                if (entry.getPath().startsWith(BundleModule.DEX_DIRECTORY)) {
                    dexFilesToMergeByModule.put(split.getModuleName(), entry);
                    continue;
                }
                ModuleSplitsToShardMerger.mergeEntries(mergedEntriesByPath, split, entry);
            }
            split.getAssetsConfig().ifPresent(assetsConfig -> MergingUtils.mergeTargetedAssetsDirectories(mergedAssetsConfig, assetsConfig.getDirectoryList()));
        }
        AndroidManifest mergedAndroidManifest = manifestMerger.merge(androidManifestsToMergeByModule);
        Collection<ModuleEntry> mergedDexFiles = this.mergeDexFilesAndCache(dexFilesToMergeByModule, bundleMetadata, mergedAndroidManifest, mergedDexCache);
        ImmutableList<String> fusedModuleNames = ModuleSplitsToShardMerger.getUniqueModuleNames(splitsOfShard);
        if (mergedSplitType.equals((Object)ModuleSplit.SplitType.STANDALONE)) {
            mergedAndroidManifest = mergedAndroidManifest.toEditor().setFusedModuleNames(fusedModuleNames).save();
        }
        return this.buildShard(mergedEntriesByPath.values(), mergedDexFiles, mergedSplitTargeting, mergedAndroidManifest, mergedResourceTable, mergedAssetsConfig, mergedSplitType);
    }

    public ImmutableList<ModuleSplit> mergeApex(ImmutableList<ImmutableList<ModuleSplit>> unfusedShards) {
        return unfusedShards.stream().map(this::mergeSingleApexShard).collect(ImmutableList.toImmutableList());
    }

    @VisibleForTesting
    ModuleSplit mergeSingleApexShard(ImmutableList<ModuleSplit> splitsOfShard) {
        Preconditions.checkState(!splitsOfShard.isEmpty(), "A shard is made of at least one split.");
        HashMap<ZipPath, ModuleEntry> mergedEntriesByPath = new HashMap<ZipPath, ModuleEntry>();
        Targeting.ApkTargeting splitTargeting = Targeting.ApkTargeting.getDefaultInstance();
        for (ModuleSplit split : splitsOfShard) {
            splitTargeting = splitTargeting.hasMultiAbiTargeting() ? splitTargeting : split.getApkTargeting();
            for (ModuleEntry entry : split.getEntries()) {
                ModuleSplitsToShardMerger.mergeEntries(mergedEntriesByPath, split, entry);
            }
        }
        ModuleSplit shard = this.buildShard(mergedEntriesByPath.values(), ImmutableList.of(), splitTargeting, ((ModuleSplit)splitsOfShard.get(0)).getAndroidManifest(), Optional.empty(), new HashMap<String, Files.TargetedAssetsDirectory>(), ModuleSplit.SplitType.STANDALONE);
        return shard.toBuilder().setApexConfig(((ModuleSplit)splitsOfShard.get(0)).getApexConfig().get()).build();
    }

    private ModuleSplit buildShard(Collection<ModuleEntry> entriesByPath, Collection<ModuleEntry> mergedDexFiles, Targeting.ApkTargeting splitTargeting, AndroidManifest androidManifest, Optional<Resources.ResourceTable> mergedResourceTable, Map<String, Files.TargetedAssetsDirectory> mergedAssetsConfig, ModuleSplit.SplitType mergedSplitType) {
        ImmutableCollection entries = ((ImmutableList.Builder)((ImmutableList.Builder)ImmutableList.builder().addAll(entriesByPath)).addAll(mergedDexFiles)).build();
        ModuleSplit.Builder shard = ModuleSplit.builder().setAndroidManifest(androidManifest).setEntries((List<ModuleEntry>)((Object)entries)).setApkTargeting(splitTargeting).setSplitType(mergedSplitType).setMasterSplit(false).setModuleName(BundleModuleName.BASE_MODULE_NAME).setVariantTargeting(Targeting.VariantTargeting.getDefaultInstance());
        mergedResourceTable.ifPresent(shard::setResourceTable);
        if (!mergedAssetsConfig.isEmpty()) {
            shard.setAssetsConfig(Files.Assets.newBuilder().addAllDirectory(mergedAssetsConfig.values()).build());
        }
        return shard.build();
    }

    private Collection<ModuleEntry> mergeDexFilesAndCache(ListMultimap<BundleModuleName, ModuleEntry> dexFilesToMergeByModule, BundleMetadata bundleMetadata, AndroidManifest androidManifest, Map<ImmutableSet<ModuleEntry>, ImmutableList<Path>> mergedDexCache) {
        if (dexFilesToMergeByModule.keySet().size() <= 1) {
            return dexFilesToMergeByModule.values();
        }
        if (androidManifest.getEffectiveMinSdkVersion() >= 21) {
            return ModuleSplitsToShardMerger.renameDexFromAllModulesToSingleShard(dexFilesToMergeByModule);
        }
        ImmutableList dexEntries = ImmutableList.copyOf(dexFilesToMergeByModule.values());
        ImmutableList mergedDexFiles = mergedDexCache.computeIfAbsent(ImmutableSet.copyOf(dexEntries), key -> this.mergeDexFiles(dexEntries, bundleMetadata, androidManifest));
        return mergedDexFiles.stream().map(filePath -> ModuleEntry.builder().setPath(BundleModule.DEX_DIRECTORY.resolve(filePath.getFileName().toString())).setContentSupplier(InputStreamSuppliers.fromFile(filePath)).build()).collect(ImmutableList.toImmutableList());
    }

    private static ImmutableList<ModuleEntry> renameDexFromAllModulesToSingleShard(Multimap<BundleModuleName, ModuleEntry> dexFilesToMergeByModule) {
        Stream<ModuleEntry> dexFilesFromBase = dexFilesToMergeByModule.get(BundleModuleName.BASE_MODULE_NAME).stream();
        int dexFilesCountInBase = dexFilesToMergeByModule.get(BundleModuleName.BASE_MODULE_NAME).size();
        Stream dexFilesFromNotBase = dexFilesToMergeByModule.keys().stream().distinct().filter(moduleName -> !BundleModuleName.BASE_MODULE_NAME.equals(moduleName)).flatMap(moduleName -> dexFilesToMergeByModule.get((BundleModuleName)moduleName).stream());
        Stream<ModuleEntry> renamedDexFiles = Streams.mapWithIndex(dexFilesFromNotBase, (entry, index) -> entry.toBuilder().setPath(BundleModule.DEX_DIRECTORY.resolve((long)dexFilesCountInBase + index == 0L ? "classes.dex" : String.format("classes%d.dex", (long)dexFilesCountInBase + index + 1L))).build());
        return Stream.concat(dexFilesFromBase, renamedDexFiles).collect(ImmutableList.toImmutableList());
    }

    private ImmutableList<Path> mergeDexFiles(List<ModuleEntry> dexEntries, BundleMetadata bundleMetadata, AndroidManifest androidManifest) {
        try {
            Path dexOriginalDir = Files.createTempDirectory(this.globalTempDir, "dex-merging-in", new FileAttribute[0]);
            Path dexMergedDir = Files.createTempDirectory(this.globalTempDir, "dex-merging-out", new FileAttribute[0]);
            Optional<Path> mainDexListFile = this.writeMainDexListFileIfPresent(bundleMetadata);
            ImmutableList<Path> dexFiles = ModuleSplitsToShardMerger.writeModuleEntriesToIndexedFiles(dexEntries, dexOriginalDir, ".dex");
            ImmutableList<Path> mergedDexFiles = this.dexMerger.merge(dexFiles, dexMergedDir, mainDexListFile, androidManifest.getEffectiveApplicationDebuggable(), androidManifest.getEffectiveMinSdkVersion());
            return mergedDexFiles;
        }
        catch (IOException e2) {
            throw CommandExecutionException.builder().withCause(e2).withMessage("I/O error while merging dex files.").build();
        }
    }

    private static void mergeEntries(Map<ZipPath, ModuleEntry> mergedEntriesByPath, ModuleSplit split, ModuleEntry entry) {
        ModuleEntry existingEntry = mergedEntriesByPath.putIfAbsent(entry.getPath(), entry);
        Preconditions.checkState(existingEntry == null || existingEntry.equals(entry), "Module '%s' and some other module(s) contain entry '%s' with different contents.", (Object)split.getModuleName(), (Object)entry.getPath());
    }

    private Optional<Resources.ResourceTable> mergeResourceTables(Optional<Resources.ResourceTable> merged, ModuleSplit split) {
        if (!merged.isPresent()) {
            return split.getResourceTable();
        }
        if (split.getResourceTable().isPresent()) {
            try {
                return Optional.of(new ResourceTableMerger().merge(merged.get(), split.getResourceTable().get()));
            }
            catch (CommandExecutionException | IllegalStateException e2) {
                throw CommandExecutionException.builder().withCause(e2).withMessage("Failed to merge with resource table of module '%s'.", split.getModuleName()).build();
            }
        }
        return merged;
    }

    private Targeting.ApkTargeting mergeSplitTargetings(Targeting.ApkTargeting merged, ModuleSplit split) {
        try {
            return MergingUtils.mergeShardTargetings(merged, split.getApkTargeting());
        }
        catch (CommandExecutionException | IllegalStateException e2) {
            throw CommandExecutionException.builder().withCause(e2).withMessage("Failed to merge with targeting of module '%s'.", split.getModuleName()).build();
        }
    }

    private Optional<Path> writeMainDexListFileIfPresent(BundleMetadata bundleMetadata) throws IOException {
        Optional<InputStreamSupplier> mainDexListFileData = bundleMetadata.getFileData("com.android.tools.build.bundletool", "mainDexList.txt");
        if (!mainDexListFileData.isPresent()) {
            return Optional.empty();
        }
        Path mainDexListFile = Files.createTempFile(this.globalTempDir, "mainDexList", ".txt", new FileAttribute[0]);
        try (InputStream inputStream = mainDexListFileData.get().get();){
            Files.copy(inputStream, mainDexListFile, StandardCopyOption.REPLACE_EXISTING);
        }
        return Optional.of(mainDexListFile);
    }

    private static ImmutableList<String> getUniqueModuleNames(ImmutableCollection<ModuleSplit> splits) {
        return splits.stream().map(ModuleSplit::getModuleName).map(BundleModuleName::getName).distinct().collect(ImmutableList.toImmutableList());
    }

    private static ImmutableList<Path> writeModuleEntriesToIndexedFiles(List<ModuleEntry> moduleEntries, Path toDirectory, String fileSuffix) throws IOException {
        ImmutableList.Builder files = ImmutableList.builder();
        for (int i2 = 0; i2 < moduleEntries.size(); ++i2) {
            Path fileName = toDirectory.resolve(i2 + fileSuffix);
            ModuleSplitsToShardMerger.writeModuleEntryToFile(moduleEntries.get(i2), fileName);
            files.add(fileName);
        }
        return files.build();
    }

    private static void writeModuleEntryToFile(ModuleEntry entry, Path toFile) throws IOException {
        try (InputStream is = entry.getContent();
             OutputStream os = BufferedIo.outputStream(toFile);){
            ByteStreams.copy(is, os);
        }
    }

    private static Stream<ModuleSplit> writeSplitIdInManifestHavingSameModule(List<ModuleSplit> splits) {
        Preconditions.checkState(splits.stream().map(ModuleSplit::getModuleName).distinct().count() == 1L);
        SuffixManager suffixManager = new SuffixManager();
        return splits.stream().map(split -> split.writeSplitIdInManifest(suffixManager.createSuffix((ModuleSplit)split)));
    }

    private ModuleSplit mergeNonMatchedLanguageSplitsForSystemApks(ImmutableCollection<ModuleSplit> splits, BundleMetadata bundleMetadata, AndroidManifest baseMasterAndroidManifest) {
        return this.mergeSingleShard(splits, bundleMetadata, new HashMap<ImmutableSet<ModuleEntry>, ImmutableList<Path>>(), ModuleSplit.SplitType.SPLIT, AndroidManifestMerger.manifestOverride(baseMasterAndroidManifest));
    }

    private static AndroidManifest getBaseMasterAndroidManifest(ImmutableCollection<ModuleSplit> splits) {
        return ((ModuleSplit)splits.stream().filter(split -> split.isMasterSplit() && split.isBaseModuleSplit()).collect(MoreCollectors.onlyElement())).getAndroidManifest();
    }
}

