/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.mergers;

import com.google.common.collect.ImmutableList;
import java.nio.file.Path;
import java.util.Optional;

public interface DexMerger {
    public ImmutableList<Path> merge(ImmutableList<Path> var1, Path var2, Optional<Path> var3, boolean var4, int var5);
}

