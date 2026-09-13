/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.splitters;

import com.android.bundle.Targeting;
import com.android.tools.build.bundletool.model.BundleModule;
import com.android.tools.build.bundletool.model.ModuleSplit;
import com.android.tools.build.bundletool.model.SourceStamp;
import com.android.tools.build.bundletool.model.targeting.TargetingUtils;
import com.android.tools.build.bundletool.model.version.Version;
import com.android.tools.build.bundletool.splitters.ApkGenerationConfiguration;
import com.android.tools.build.bundletool.splitters.ModuleSplitter;
import com.android.tools.build.bundletool.splitters.VariantGenerator;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import java.util.Optional;

public final class SplitApksGenerator {
    private final ImmutableList<BundleModule> modules;
    private final ApkGenerationConfiguration apkGenerationConfiguration;
    private final Version bundleVersion;
    private final Optional<String> stampSource;

    public SplitApksGenerator(ImmutableList<BundleModule> modules, Version bundleVersion, ApkGenerationConfiguration apkGenerationConfiguration) {
        this(modules, bundleVersion, apkGenerationConfiguration, Optional.empty());
    }

    public SplitApksGenerator(ImmutableList<BundleModule> modules, Version bundleVersion, ApkGenerationConfiguration apkGenerationConfiguration, Optional<String> stampSource) {
        this.modules = Preconditions.checkNotNull(modules);
        this.bundleVersion = Preconditions.checkNotNull(bundleVersion);
        this.apkGenerationConfiguration = Preconditions.checkNotNull(apkGenerationConfiguration);
        this.stampSource = stampSource;
    }

    public ImmutableList<ModuleSplit> generateSplits() {
        ImmutableSet<Targeting.VariantTargeting> variantTargetings = this.generateVariants();
        return variantTargetings.stream().flatMap(variantTargeting -> this.generateSplitApks((Targeting.VariantTargeting)variantTargeting).stream()).collect(ImmutableList.toImmutableList());
    }

    private ImmutableSet<Targeting.VariantTargeting> generateVariants() {
        ImmutableSet.Builder builder = ImmutableSet.builder();
        for (BundleModule module : this.modules) {
            VariantGenerator variantGenerator = new VariantGenerator(module, this.apkGenerationConfiguration);
            ImmutableSet<Targeting.VariantTargeting> splitApks = variantGenerator.generateVariants();
            builder.addAll(splitApks);
        }
        return TargetingUtils.generateAllVariantTargetings((ImmutableSet<Targeting.VariantTargeting>)builder.build());
    }

    private ImmutableList<ModuleSplit> generateSplitApks(Targeting.VariantTargeting variantTargeting) {
        ImmutableSet<String> allModuleNames = this.modules.stream().map(module -> module.getName().getName()).collect(ImmutableSet.toImmutableSet());
        ImmutableList.Builder splits = ImmutableList.builder();
        for (BundleModule module2 : this.modules) {
            ModuleSplitter moduleSplitter = ModuleSplitter.create(module2, this.bundleVersion, this.apkGenerationConfiguration, variantTargeting, allModuleNames, this.stampSource, SourceStamp.StampType.STAMP_TYPE_DISTRIBUTION_APK);
            splits.addAll(moduleSplitter.splitModule());
        }
        return splits.build();
    }
}

