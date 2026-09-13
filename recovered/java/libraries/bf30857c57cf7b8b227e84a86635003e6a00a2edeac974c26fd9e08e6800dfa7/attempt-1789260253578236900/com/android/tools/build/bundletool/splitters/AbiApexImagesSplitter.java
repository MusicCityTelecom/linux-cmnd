/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.splitters;

import com.android.bundle.Files;
import com.android.bundle.Targeting;
import com.android.tools.build.bundletool.model.ModuleEntry;
import com.android.tools.build.bundletool.model.ModuleSplit;
import com.android.tools.build.bundletool.splitters.ModuleSplitSplitter;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public class AbiApexImagesSplitter
implements ModuleSplitSplitter {
    @Override
    public ImmutableCollection<ModuleSplit> split(ModuleSplit moduleSplit) {
        if (!moduleSplit.isApex()) {
            return ImmutableList.of(moduleSplit);
        }
        List<Files.TargetedApexImage> allTargetedImages = moduleSplit.getApexConfig().get().getImageList();
        ImmutableSet allTargeting = allTargetedImages.stream().flatMap(image -> image.getTargeting().getMultiAbi().getValueList().stream()).collect(ImmutableSet.toImmutableSet());
        ImmutableMap<String, ModuleEntry> apexPathToEntryMap = AbiApexImagesSplitter.buildApexPathToEntryMap(allTargetedImages, moduleSplit);
        ImmutableList.Builder splits = new ImmutableList.Builder();
        for (Files.TargetedApexImage targetedApexImage : allTargetedImages) {
            ModuleEntry entry = apexPathToEntryMap.get(targetedApexImage.getPath());
            List<Targeting.MultiAbi> targeting = targetedApexImage.getTargeting().getMultiAbi().getValueList();
            ModuleSplit.Builder splitBuilder = moduleSplit.toBuilder().setApkTargeting(moduleSplit.getApkTargeting().toBuilder().setMultiAbiTargeting(Targeting.MultiAbiTargeting.newBuilder().addAllValue(targeting).addAllAlternatives(Sets.difference(allTargeting, ImmutableSet.copyOf(targeting)))).build()).setMasterSplit(false).setEntries(targetedApexImage.getBuildInfoPath().isEmpty() ? ImmutableList.of(entry) : ImmutableList.of(entry, apexPathToEntryMap.get(targetedApexImage.getBuildInfoPath())));
            splits.add(splitBuilder.build());
        }
        return splits.build();
    }

    private static ImmutableMap<String, ModuleEntry> buildApexPathToEntryMap(List<Files.TargetedApexImage> allTargetedImages, ModuleSplit moduleSplit) {
        ImmutableMap pathToEntry = Maps.uniqueIndex(moduleSplit.getEntries(), entry -> entry.getPath().toString());
        return Stream.concat(allTargetedImages.stream().map(Files.TargetedApexImage::getPath), allTargetedImages.stream().map(Files.TargetedApexImage::getBuildInfoPath).filter(p3 -> !p3.isEmpty())).collect(ImmutableMap.toImmutableMap(Function.identity(), pathToEntry::get));
    }
}

