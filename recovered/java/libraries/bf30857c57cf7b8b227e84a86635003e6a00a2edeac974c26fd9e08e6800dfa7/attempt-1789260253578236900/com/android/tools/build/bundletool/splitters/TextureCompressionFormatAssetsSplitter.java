/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.splitters;

import com.android.bundle.Targeting;
import com.android.tools.build.bundletool.model.targeting.TargetingDimension;
import com.android.tools.build.bundletool.splitters.AssetsDimensionSplitterFactory;
import com.android.tools.build.bundletool.splitters.ModuleSplitSplitter;
import java.util.Optional;

public class TextureCompressionFormatAssetsSplitter {
    public static ModuleSplitSplitter create(boolean stripTargetingSuffix) {
        return AssetsDimensionSplitterFactory.createSplitter(Targeting.AssetsDirectoryTargeting::getTextureCompressionFormat, TextureCompressionFormatAssetsSplitter::fromTextureCompressionFormat, Targeting.ApkTargeting::hasTextureCompressionFormatTargeting, stripTargetingSuffix ? Optional.of(TargetingDimension.TEXTURE_COMPRESSION_FORMAT) : Optional.empty());
    }

    private static Targeting.ApkTargeting fromTextureCompressionFormat(Targeting.TextureCompressionFormatTargeting targeting) {
        return Targeting.ApkTargeting.newBuilder().setTextureCompressionFormatTargeting(targeting).build();
    }

    private TextureCompressionFormatAssetsSplitter() {
    }
}

