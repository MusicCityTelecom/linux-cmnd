/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.splitters;

import com.android.tools.build.bundletool.model.ModuleSplit;
import com.google.common.collect.ImmutableCollection;

public interface ModuleSplitSplitter {
    public ImmutableCollection<ModuleSplit> split(ModuleSplit var1);
}

