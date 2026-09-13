/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.validation;

import com.android.bundle.Config;
import com.android.tools.build.bundletool.model.AppBundle;
import com.android.tools.build.bundletool.model.BundleModule;
import com.android.tools.build.bundletool.model.ResourceId;
import com.android.tools.build.bundletool.model.ResourceTableEntry;
import com.android.tools.build.bundletool.model.exceptions.ValidationException;
import com.android.tools.build.bundletool.model.utils.PathMatcher;
import com.android.tools.build.bundletool.model.utils.ResourcesUtils;
import com.android.tools.build.bundletool.model.utils.TextureCompressionUtils;
import com.android.tools.build.bundletool.model.version.BundleToolVersion;
import com.android.tools.build.bundletool.validation.SubValidator;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public final class BundleConfigValidator
extends SubValidator {
    private static final ImmutableSet<String> FORBIDDEN_CHARS_IN_GLOB = ImmutableSet.of("\n", "\\\\");

    @Override
    public void validateBundle(AppBundle bundle) {
        Config.BundleConfig bundleConfig = bundle.getBundleConfig();
        this.validateVersion(bundleConfig);
        this.validateCompression(bundleConfig.getCompression());
        this.validateOptimizations(bundleConfig.getOptimizations());
        this.validateMasterResources(bundleConfig, bundle);
    }

    private void validateCompression(Config.Compression compression) {
        for (String pattern : compression.getUncompressedGlobList()) {
            if (FORBIDDEN_CHARS_IN_GLOB.stream().anyMatch(pattern::contains)) {
                throw ValidationException.builder().withMessage("Invalid uncompressed glob: '%s'.", pattern).build();
            }
            try {
                PathMatcher.createFromGlob(pattern);
            }
            catch (PathMatcher.GlobPatternSyntaxException e2) {
                throw ValidationException.builder().withCause(e2).withMessage("Invalid uncompressed glob: '%s'.", pattern).build();
            }
        }
    }

    private void validateOptimizations(Config.Optimizations optimizations) {
        List<Config.SplitDimension> splitDimensions = optimizations.getSplitsConfig().getSplitDimensionList();
        if (splitDimensions.stream().anyMatch(dimension -> dimension.getValue().equals(Config.SplitDimension.Value.UNRECOGNIZED) && !dimension.getNegate())) {
            throw ValidationException.builder().withMessage("BundleConfig.pb contains an unrecognized split dimension. Update bundletool?").build();
        }
        if (splitDimensions.stream().map(Config.SplitDimension::getValueValue).distinct().count() != (long)splitDimensions.size()) {
            throw ValidationException.builder().withMessage("BundleConfig.pb contains duplicate split dimensions: %s", splitDimensions).build();
        }
        if (splitDimensions.stream().anyMatch(dimension -> dimension.hasSuffixStripping() && dimension.getSuffixStripping().getEnabled() && !dimension.getValue().equals(Config.SplitDimension.Value.TEXTURE_COMPRESSION_FORMAT))) {
            throw ValidationException.builder().withMessage("Suffix stripping was enabled for an unsupported dimension. Only TEXTURE_COMPRESSION_FORMAT is supported.").build();
        }
        splitDimensions.stream().filter(dimension -> dimension.getValue().equals(Config.SplitDimension.Value.TEXTURE_COMPRESSION_FORMAT)).filter(dimension -> !dimension.getSuffixStripping().getDefaultSuffix().isEmpty()).filter(dimension -> !TextureCompressionUtils.TEXTURE_TO_TARGETING.containsKey(dimension.getSuffixStripping().getDefaultSuffix())).findFirst().ifPresent(dimension -> {
            Set supportedTextures = TextureCompressionUtils.TEXTURE_TO_TARGETING.keySet();
            throw ValidationException.builder().withMessage("The default texture compression format chosen for suffix stripping (\"%s\") is not valid. Supported formats are: %s.", dimension.getSuffixStripping().getDefaultSuffix(), supportedTextures.stream().collect(Collectors.joining(", "))).build();
        });
    }

    private void validateVersion(Config.BundleConfig bundleConfig) {
        try {
            BundleToolVersion.getVersionFromBundleConfig(bundleConfig);
        }
        catch (ValidationException e2) {
            throw ValidationException.builder().withCause(e2).withMessage("Invalid version in the BundleConfig.pb file.").build();
        }
    }

    private void validateMasterResources(Config.BundleConfig bundleConfig, AppBundle bundle) {
        ImmutableSet<Integer> resourcesToBePinned = ImmutableSet.copyOf(bundleConfig.getMasterResources().getResourceIdsList());
        if (resourcesToBePinned.isEmpty()) {
            return;
        }
        ImmutableSet allResourceIds = bundle.getFeatureModules().values().stream().map(BundleModule::getResourceTable).filter(Optional::isPresent).map(Optional::get).flatMap(resourceTable -> ResourcesUtils.entries(resourceTable)).map(ResourceTableEntry::getResourceId).map(ResourceId::getFullResourceId).collect(ImmutableSet.toImmutableSet());
        Sets.SetView<Integer> undefinedResources = Sets.difference(resourcesToBePinned, allResourceIds);
        if (!undefinedResources.isEmpty()) {
            throw ValidationException.builder().withMessage("Error in BundleConfig. The Master Resources list contains resource IDs not defined in any module. For example: 0x%08x", undefinedResources.iterator().next()).build();
        }
    }
}

