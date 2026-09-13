/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.splitters;

import com.android.bundle.Files;
import com.android.bundle.Targeting;
import com.android.tools.build.bundletool.model.ManifestMutator;
import com.android.tools.build.bundletool.model.ModuleEntry;
import com.android.tools.build.bundletool.model.ModuleSplit;
import com.android.tools.build.bundletool.splitters.ModuleSplitSplitter;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimaps;
import com.google.common.collect.Sets;
import java.util.HashSet;
import java.util.List;

public class AbiNativeLibrariesSplitter
implements ModuleSplitSplitter {
    @Override
    public ImmutableCollection<ModuleSplit> split(ModuleSplit moduleSplit) {
        if (!moduleSplit.getNativeConfig().isPresent()) {
            return ImmutableList.of(moduleSplit);
        }
        ImmutableList.Builder splits = new ImmutableList.Builder();
        List<Files.TargetedNativeDirectory> allTargetedDirectories = moduleSplit.getNativeConfig().get().getDirectoryList();
        ImmutableListMultimap targetingMap = Multimaps.index(allTargetedDirectories, Files.TargetedNativeDirectory::getTargeting);
        ImmutableSet abisToGenerate = targetingMap.keySet().stream().map(Targeting.NativeDirectoryTargeting::getAbi).collect(ImmutableSet.toImmutableSet());
        HashSet<ModuleEntry> leftOverEntries = new HashSet<ModuleEntry>(moduleSplit.getEntries());
        for (Targeting.NativeDirectoryTargeting targeting : targetingMap.keySet()) {
            ImmutableList<ModuleEntry> entriesList = ((ImmutableMultimap)targetingMap).get(targeting).stream().flatMap(directory -> moduleSplit.findEntriesUnderPath(directory.getPath())).collect(ImmutableList.toImmutableList());
            ModuleSplit.Builder splitBuilder = moduleSplit.toBuilder().setApkTargeting(moduleSplit.getApkTargeting().toBuilder().setAbiTargeting(Targeting.AbiTargeting.newBuilder().addValue(targeting.getAbi()).addAllAlternatives(Sets.difference(abisToGenerate, ImmutableSet.of(targeting.getAbi())))).build()).setMasterSplit(false).addMasterManifestMutator(ManifestMutator.withSplitsRequired(true)).setEntries(entriesList);
            splits.add(splitBuilder.build());
            leftOverEntries.removeAll(entriesList);
        }
        if (!leftOverEntries.isEmpty()) {
            splits.add(moduleSplit.toBuilder().setEntries(ImmutableList.copyOf(leftOverEntries)).build());
        }
        return splits.build();
    }
}

