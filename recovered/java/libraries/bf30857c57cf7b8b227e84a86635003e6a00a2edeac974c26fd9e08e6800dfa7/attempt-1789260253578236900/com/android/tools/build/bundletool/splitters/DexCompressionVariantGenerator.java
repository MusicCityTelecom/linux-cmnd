/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.splitters;

import com.android.bundle.Targeting;
import com.android.tools.build.bundletool.model.BundleModule;
import com.android.tools.build.bundletool.model.utils.TargetingProtoUtils;
import com.android.tools.build.bundletool.splitters.ApkGenerationConfiguration;
import com.android.tools.build.bundletool.splitters.BundleModuleVariantGenerator;
import com.google.common.collect.ImmutableSet;
import java.util.stream.Stream;

public final class DexCompressionVariantGenerator
implements BundleModuleVariantGenerator {
    private final ApkGenerationConfiguration apkGenerationConfiguration;

    public DexCompressionVariantGenerator(ApkGenerationConfiguration apkGenerationConfiguration) {
        this.apkGenerationConfiguration = apkGenerationConfiguration;
    }

    @Override
    public Stream<Targeting.VariantTargeting> generate(BundleModule module) {
        if (!this.apkGenerationConfiguration.getEnableDexCompressionSplitter() || this.apkGenerationConfiguration.isForInstantAppVariants()) {
            return Stream.of(new Targeting.VariantTargeting[0]);
        }
        ImmutableSet dexEntries = module.getEntries().stream().filter(entry -> entry.getPath().startsWith(BundleModule.DEX_DIRECTORY)).collect(ImmutableSet.toImmutableSet());
        if (dexEntries.isEmpty()) {
            return Stream.of(new Targeting.VariantTargeting[0]);
        }
        return Stream.of(TargetingProtoUtils.variantTargeting(TargetingProtoUtils.sdkVersionTargeting(TargetingProtoUtils.sdkVersionFrom(29))));
    }
}

