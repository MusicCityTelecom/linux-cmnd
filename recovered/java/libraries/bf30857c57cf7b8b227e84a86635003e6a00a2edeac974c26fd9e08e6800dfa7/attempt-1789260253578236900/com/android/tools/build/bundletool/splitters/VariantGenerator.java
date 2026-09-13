/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.splitters;

import com.android.bundle.Targeting;
import com.android.tools.build.bundletool.model.BundleModule;
import com.android.tools.build.bundletool.model.exceptions.CommandExecutionException;
import com.android.tools.build.bundletool.model.targeting.TargetingUtils;
import com.android.tools.build.bundletool.model.utils.TargetingProtoUtils;
import com.android.tools.build.bundletool.splitters.ApkGenerationConfiguration;
import com.android.tools.build.bundletool.splitters.BundleModuleVariantGenerator;
import com.android.tools.build.bundletool.splitters.DexCompressionVariantGenerator;
import com.android.tools.build.bundletool.splitters.NativeLibsCompressionVariantGenerator;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import java.util.Optional;
import java.util.stream.Stream;

public final class VariantGenerator {
    private final BundleModule module;
    private final ApkGenerationConfiguration apkGenerationConfiguration;

    @VisibleForTesting
    VariantGenerator(BundleModule module) {
        this(module, ApkGenerationConfiguration.getDefaultInstance());
    }

    public VariantGenerator(BundleModule module, ApkGenerationConfiguration apkGenerationConfiguration) {
        this.module = module;
        this.apkGenerationConfiguration = apkGenerationConfiguration;
    }

    public ImmutableSet<Targeting.VariantTargeting> generateVariants() {
        if (VariantGenerator.targetsOnlyPreL(this.module)) {
            throw CommandExecutionException.builder().withMessage("Cannot generate variants '%s' because it does not target devices on Android L or above.", this.module.getName()).build();
        }
        ImmutableSet<Targeting.VariantTargeting> splitVariants = this.getVariantGenerators().stream().flatMap(generator -> generator.generate(this.module)).collect(ImmutableSet.toImmutableSet());
        return TargetingUtils.cropVariantsWithAppSdkRange(splitVariants, this.module.getAndroidManifest().getSdkRange());
    }

    private ImmutableList<BundleModuleVariantGenerator> getVariantGenerators() {
        return ImmutableList.of(apkGenerationConfiguration -> Stream.of(TargetingProtoUtils.lPlusVariantTargeting()), new NativeLibsCompressionVariantGenerator(this.apkGenerationConfiguration), new DexCompressionVariantGenerator(this.apkGenerationConfiguration));
    }

    private static boolean targetsOnlyPreL(BundleModule module) {
        Optional<Integer> maxSdkVersion = module.getAndroidManifest().getMaxSdkVersion();
        return maxSdkVersion.isPresent() && maxSdkVersion.get() < 21;
    }
}

