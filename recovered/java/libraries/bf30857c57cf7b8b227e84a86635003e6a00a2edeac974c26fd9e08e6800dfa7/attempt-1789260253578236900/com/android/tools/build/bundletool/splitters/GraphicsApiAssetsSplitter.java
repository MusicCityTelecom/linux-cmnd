/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.splitters;

import com.android.bundle.Targeting;
import com.android.tools.build.bundletool.splitters.AssetsDimensionSplitterFactory;
import com.android.tools.build.bundletool.splitters.ModuleSplitSplitter;

public class GraphicsApiAssetsSplitter {
    public static ModuleSplitSplitter create() {
        return AssetsDimensionSplitterFactory.createSplitter(Targeting.AssetsDirectoryTargeting::getGraphicsApi, GraphicsApiAssetsSplitter::fromGraphicsApi, Targeting.ApkTargeting::hasGraphicsApiTargeting);
    }

    private static Targeting.ApkTargeting fromGraphicsApi(Targeting.GraphicsApiTargeting targeting) {
        return Targeting.ApkTargeting.newBuilder().setGraphicsApiTargeting(targeting).build();
    }

    private GraphicsApiAssetsSplitter() {
    }
}

