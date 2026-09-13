/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.splitters;

import com.android.tools.build.bundletool.model.ModuleSplit;
import com.android.tools.build.bundletool.splitters.ModuleSplitSplitter;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import java.util.Collection;

public final class SplittingPipeline {
    private final ImmutableList<ModuleSplitSplitter> splitters;

    SplittingPipeline(ImmutableList<ModuleSplitSplitter> splitters) {
        this.splitters = splitters;
    }

    public ImmutableList<ModuleSplitSplitter> getSplitters() {
        return this.splitters;
    }

    public ImmutableCollection<ModuleSplit> split(ModuleSplit split) {
        ImmutableList<ModuleSplit> splits = ImmutableList.of(split);
        for (ModuleSplitSplitter splitter : this.splitters) {
            splits = splits.stream().map(splitter::split).flatMap(Collection::stream).collect(ImmutableList.toImmutableList());
        }
        return splits;
    }
}

