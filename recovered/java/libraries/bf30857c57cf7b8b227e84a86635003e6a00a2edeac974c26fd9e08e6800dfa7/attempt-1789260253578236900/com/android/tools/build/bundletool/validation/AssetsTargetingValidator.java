/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.validation;

import com.android.bundle.Files;
import com.android.bundle.Targeting;
import com.android.tools.build.bundletool.model.BundleModule;
import com.android.tools.build.bundletool.model.ZipPath;
import com.android.tools.build.bundletool.model.exceptions.ValidationException;
import com.android.tools.build.bundletool.validation.BundleValidationUtils;
import com.android.tools.build.bundletool.validation.SubValidator;
import com.google.common.collect.ImmutableSet;

public class AssetsTargetingValidator
extends SubValidator {
    @Override
    public void validateModule(BundleModule module) {
        module.getAssetsConfig().ifPresent(targeting -> this.validateTargeting(module, (Files.Assets)targeting));
    }

    private void validateTargeting(BundleModule module, Files.Assets assets) {
        ImmutableSet<ZipPath> assetDirsWithFiles = AssetsTargetingValidator.getDirectoriesWithFiles(module);
        for (Files.TargetedAssetsDirectory targetedDirectory : assets.getDirectoryList()) {
            ZipPath path = ZipPath.create(targetedDirectory.getPath());
            if (!path.startsWith(BundleModule.ASSETS_DIRECTORY)) {
                throw ValidationException.builder().withMessage("Path of targeted assets directory must start with 'assets/' but found '%s'.", path).build();
            }
            if (!assetDirsWithFiles.contains(path)) {
                throw ValidationException.builder().withMessage("Targeted directory '%s' is empty.", path).build();
            }
            AssetsTargetingValidator.checkNoDimensionWithoutValuesAndAlternatives(targetedDirectory);
        }
        if (module.getModuleType().equals((Object)BundleModule.ModuleType.ASSET_MODULE) && assets.getDirectoryList().stream().anyMatch(dir -> dir.getTargeting().hasLanguage())) {
            throw ValidationException.builder().withMessage("Language targeting for asset packs is not supported, but found in module %s.", module.getName().getName()).build();
        }
    }

    private static ImmutableSet<ZipPath> getDirectoriesWithFiles(BundleModule module) {
        return module.getEntries().stream().map(entry -> entry.getPath().getParent()).collect(ImmutableSet.toImmutableSet());
    }

    private static void checkNoDimensionWithoutValuesAndAlternatives(Files.TargetedAssetsDirectory targetedDirectory) {
        Targeting.AssetsDirectoryTargeting targeting = targetedDirectory.getTargeting();
        if (targeting.hasAbi()) {
            BundleValidationUtils.checkHasValuesOrAlternatives(targeting.getAbi(), targetedDirectory.getPath());
        }
        if (targeting.hasGraphicsApi()) {
            BundleValidationUtils.checkHasValuesOrAlternatives(targeting.getGraphicsApi(), targetedDirectory.getPath());
        }
        if (targeting.hasLanguage()) {
            BundleValidationUtils.checkHasValuesOrAlternatives(targeting.getLanguage(), targetedDirectory.getPath());
        }
        if (targeting.hasTextureCompressionFormat()) {
            BundleValidationUtils.checkHasValuesOrAlternatives(targeting.getTextureCompressionFormat(), targetedDirectory.getPath());
        }
    }
}

