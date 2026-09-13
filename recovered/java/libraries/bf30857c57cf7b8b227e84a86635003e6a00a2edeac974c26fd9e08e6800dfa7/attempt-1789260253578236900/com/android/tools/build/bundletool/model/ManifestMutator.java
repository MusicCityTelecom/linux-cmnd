/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model;

import com.android.tools.build.bundletool.model.ManifestEditor;
import com.google.errorprone.annotations.Immutable;
import java.util.function.Consumer;

@Immutable
public interface ManifestMutator
extends Consumer<ManifestEditor> {
    public static ManifestMutator withExtractNativeLibs(boolean value) {
        return manifestEditor -> manifestEditor.setExtractNativeLibsValue(value);
    }

    public static ManifestMutator withSplitsRequired(boolean value) {
        return manifestEditor -> manifestEditor.setSplitsRequired(value);
    }
}

