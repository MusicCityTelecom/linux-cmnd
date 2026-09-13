/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.splitters;

import com.android.bundle.Targeting;
import com.android.tools.build.bundletool.model.ModuleSplit;
import com.android.tools.build.bundletool.splitters.ModuleSplitSplitter;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;

public abstract class SplitterForOneTargetingDimension
implements ModuleSplitSplitter {
    @Override
    public ImmutableCollection<ModuleSplit> split(ModuleSplit split) {
        if (!split.getApkTargeting().equals(Targeting.ApkTargeting.getDefaultInstance())) {
            return ImmutableList.of(split);
        }
        return this.splitInternal(split);
    }

    public abstract ImmutableCollection<ModuleSplit> splitInternal(ModuleSplit var1);
}

