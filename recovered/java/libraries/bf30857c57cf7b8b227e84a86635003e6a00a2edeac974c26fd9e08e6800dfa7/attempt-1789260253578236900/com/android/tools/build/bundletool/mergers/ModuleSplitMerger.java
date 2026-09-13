/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.mergers;

import com.android.tools.build.bundletool.model.ModuleSplit;
import com.google.common.collect.ImmutableCollection;

public interface ModuleSplitMerger {
    public ImmutableCollection<ModuleSplit> merge(ImmutableCollection<ModuleSplit> var1);
}

