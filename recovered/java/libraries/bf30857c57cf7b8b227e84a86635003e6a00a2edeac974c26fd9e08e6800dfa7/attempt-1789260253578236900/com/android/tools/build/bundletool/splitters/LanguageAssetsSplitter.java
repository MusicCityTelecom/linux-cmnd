/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.splitters;

import com.android.bundle.Targeting;
import com.android.tools.build.bundletool.splitters.AssetsDimensionSplitterFactory;
import com.android.tools.build.bundletool.splitters.ModuleSplitSplitter;

public final class LanguageAssetsSplitter {
    public static ModuleSplitSplitter create() {
        return AssetsDimensionSplitterFactory.createSplitter(Targeting.AssetsDirectoryTargeting::getLanguage, LanguageAssetsSplitter::fromLanguage, Targeting.ApkTargeting::hasLanguageTargeting);
    }

    private static Targeting.ApkTargeting fromLanguage(Targeting.LanguageTargeting targeting) {
        return Targeting.ApkTargeting.newBuilder().setLanguageTargeting(targeting).build();
    }

    private LanguageAssetsSplitter() {
    }
}

