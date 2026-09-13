/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.mergers;

import com.android.aapt.Resources;
import com.android.bundle.Files;
import com.android.bundle.Targeting;
import com.android.tools.build.bundletool.mergers.MergingUtils;
import com.android.tools.build.bundletool.mergers.ModuleSplitMerger;
import com.android.tools.build.bundletool.model.AndroidManifest;
import com.android.tools.build.bundletool.model.BundleModuleName;
import com.android.tools.build.bundletool.model.ModuleEntry;
import com.android.tools.build.bundletool.model.ModuleSplit;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Multimaps;
import java.util.HashMap;
import java.util.List;

public class SameTargetingMerger
implements ModuleSplitMerger {
    public ImmutableList<ModuleSplit> merge(ImmutableCollection<ModuleSplit> moduleSplits) {
        Preconditions.checkArgument(moduleSplits.stream().map(ModuleSplit::getVariantTargeting).distinct().count() == 1L, "SameTargetingMerger doesn't support merging splits from different variants.");
        ImmutableList.Builder result = ImmutableList.builder();
        ImmutableListMultimap splitsByTargeting = Multimaps.index(moduleSplits, ModuleSplit::getApkTargeting);
        for (Targeting.ApkTargeting targeting : splitsByTargeting.keySet()) {
            result.add(this.mergeSplits(splitsByTargeting.get(targeting)));
        }
        return result.build();
    }

    private ModuleSplit mergeSplits(ImmutableCollection<ModuleSplit> splits) {
        ModuleSplit.Builder builder = ModuleSplit.builder();
        ImmutableList.Builder entries = ImmutableList.builder();
        AndroidManifest mergedManifest = null;
        Resources.ResourceTable mergedResourceTable = null;
        Files.NativeLibraries mergedNativeConfig = null;
        HashMap mergedAssetsConfig = new HashMap();
        Files.ApexImages mergedApexConfig = null;
        BundleModuleName mergedModuleName = null;
        Boolean mergedIsMasterSplit = null;
        Targeting.VariantTargeting mergedVariantTargeting = null;
        for (ModuleSplit split : splits) {
            mergedManifest = MergingUtils.getSameValueOrNonNull(mergedManifest, split.getAndroidManifest()).orElseThrow(() -> new IllegalStateException("Encountered two distinct manifests while merging."));
            if (split.getResourceTable().isPresent()) {
                mergedResourceTable = MergingUtils.getSameValueOrNonNull(mergedResourceTable, split.getResourceTable().get()).orElseThrow(() -> new IllegalStateException("Unsupported case: encountered two distinct resource tables while merging."));
            }
            if (split.getNativeConfig().isPresent()) {
                mergedNativeConfig = MergingUtils.getSameValueOrNonNull(mergedNativeConfig, split.getNativeConfig().get()).orElseThrow(() -> new IllegalStateException("Encountered two distinct native configs while merging."));
            }
            if (split.getApexConfig().isPresent()) {
                mergedApexConfig = MergingUtils.getSameValueOrNonNull(mergedApexConfig, split.getApexConfig().get()).orElseThrow(() -> new IllegalStateException("Encountered two distinct apex configs while merging."));
            }
            mergedModuleName = MergingUtils.getSameValueOrNonNull(mergedModuleName, split.getModuleName()).orElseThrow(() -> new IllegalStateException("Encountered two distinct module names while merging."));
            mergedIsMasterSplit = MergingUtils.getSameValueOrNonNull(mergedIsMasterSplit, split.isMasterSplit()).orElseThrow(() -> new IllegalStateException("Encountered conflicting isMasterSplit flag values while merging."));
            mergedVariantTargeting = MergingUtils.getSameValueOrNonNull(mergedVariantTargeting, split.getVariantTargeting()).orElseThrow(() -> new IllegalStateException("Encountered conflicting variant targeting values while merging."));
            entries.addAll(split.getEntries());
            builder.setApkTargeting(split.getApkTargeting());
            split.getAssetsConfig().ifPresent(assetsConfig -> MergingUtils.mergeTargetedAssetsDirectories(mergedAssetsConfig, assetsConfig.getDirectoryList()));
        }
        if (mergedManifest != null) {
            builder.setAndroidManifest(mergedManifest);
        }
        if (mergedResourceTable != null) {
            builder.setResourceTable(mergedResourceTable);
        }
        if (mergedNativeConfig != null) {
            builder.setNativeConfig(mergedNativeConfig);
        }
        if (!mergedAssetsConfig.isEmpty()) {
            builder.setAssetsConfig(Files.Assets.newBuilder().addAllDirectory(mergedAssetsConfig.values()).build());
        }
        if (mergedApexConfig != null) {
            builder.setApexConfig(mergedApexConfig);
        }
        if (mergedModuleName != null) {
            builder.setModuleName(mergedModuleName);
        }
        if (mergedIsMasterSplit != null) {
            builder.setMasterSplit(mergedIsMasterSplit);
        }
        builder.setVariantTargeting(mergedVariantTargeting);
        builder.setEntries((List<ModuleEntry>)((Object)entries.build()));
        return builder.build();
    }
}

