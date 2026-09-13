/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.splitters;

import com.android.tools.build.bundletool.model.BundleModule;
import com.android.tools.build.bundletool.model.ModuleEntry;
import com.android.tools.build.bundletool.model.ModuleSplit;
import com.android.tools.build.bundletool.splitters.ModuleSplitSplitter;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;

public class DexCompressionSplitter
implements ModuleSplitSplitter {
    @Override
    public ImmutableCollection<ModuleSplit> split(ModuleSplit moduleSplit) {
        ImmutableSet<ModuleEntry> dexEntries = moduleSplit.getEntries().stream().filter(entry -> entry.getPath().startsWith(BundleModule.DEX_DIRECTORY)).collect(ImmutableSet.toImmutableSet());
        if (dexEntries.isEmpty()) {
            return ImmutableList.of(moduleSplit);
        }
        boolean shouldCompress = DexCompressionSplitter.targetsPreQ(moduleSplit);
        return ImmutableList.of(DexCompressionSplitter.createModuleSplit(moduleSplit, DexCompressionSplitter.mergeAndSetCompression(dexEntries, moduleSplit, shouldCompress)));
    }

    private static ImmutableList<ModuleEntry> mergeAndSetCompression(ImmutableSet<ModuleEntry> dexEntries, ModuleSplit moduleSplit, boolean shouldCompress) {
        ImmutableSet nonDexEntries = moduleSplit.getEntries().stream().filter(entry -> !dexEntries.contains(entry)).collect(ImmutableSet.toImmutableSet());
        return ((ImmutableList.Builder)((ImmutableList.Builder)ImmutableList.builder().addAll((Iterable)dexEntries.stream().map(moduleEntry -> moduleEntry.toBuilder().setShouldCompress(shouldCompress).build()).collect(ImmutableList.toImmutableList()))).addAll((Iterable)nonDexEntries)).build();
    }

    private static boolean targetsPreQ(ModuleSplit moduleSplit) {
        int sdkVersion = Iterables.getOnlyElement(moduleSplit.getVariantTargeting().getSdkVersionTargeting().getValueList()).getMin().getValue();
        return sdkVersion < 29;
    }

    private static ModuleSplit createModuleSplit(ModuleSplit moduleSplit, ImmutableList<ModuleEntry> moduleEntries) {
        return moduleSplit.toBuilder().setEntries(moduleEntries).build();
    }
}

