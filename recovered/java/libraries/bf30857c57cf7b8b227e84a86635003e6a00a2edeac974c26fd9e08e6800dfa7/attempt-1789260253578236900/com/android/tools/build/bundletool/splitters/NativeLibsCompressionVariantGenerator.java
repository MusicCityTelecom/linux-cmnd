/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.splitters;

import com.android.bundle.Targeting;
import com.android.tools.build.bundletool.model.BundleModule;
import com.android.tools.build.bundletool.model.utils.TargetingProtoUtils;
import com.android.tools.build.bundletool.splitters.ApkGenerationConfiguration;
import com.android.tools.build.bundletool.splitters.BundleModuleVariantGenerator;
import com.android.tools.build.bundletool.splitters.NativeLibrariesHelper;
import java.util.stream.Stream;

public class NativeLibsCompressionVariantGenerator
implements BundleModuleVariantGenerator {
    private final ApkGenerationConfiguration apkGenerationConfiguration;

    public NativeLibsCompressionVariantGenerator(ApkGenerationConfiguration apkGenerationConfiguration) {
        this.apkGenerationConfiguration = apkGenerationConfiguration;
    }

    @Override
    public Stream<Targeting.VariantTargeting> generate(BundleModule module) {
        if (!this.apkGenerationConfiguration.getEnableNativeLibraryCompressionSplitter() || this.apkGenerationConfiguration.isForInstantAppVariants() || !module.getNativeConfig().isPresent()) {
            return Stream.of(new Targeting.VariantTargeting[0]);
        }
        if (this.apkGenerationConfiguration.isInstallableOnExternalStorage()) {
            return Stream.of(TargetingProtoUtils.variantTargeting(TargetingProtoUtils.sdkVersionTargeting(TargetingProtoUtils.sdkVersionFrom(28))));
        }
        if (NativeLibrariesHelper.mayHaveNativeActivities(module)) {
            return Stream.of(TargetingProtoUtils.variantTargeting(TargetingProtoUtils.sdkVersionTargeting(TargetingProtoUtils.sdkVersionFrom(24))));
        }
        return Stream.of(TargetingProtoUtils.variantTargeting(TargetingProtoUtils.sdkVersionTargeting(TargetingProtoUtils.sdkVersionFrom(23))));
    }
}

