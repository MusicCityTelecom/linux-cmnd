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
import com.google.common.collect.Sets;

public class NativeTargetingValidator
extends SubValidator {
    @Override
    public void validateModule(BundleModule module) {
        module.getNativeConfig().ifPresent(targeting -> NativeTargetingValidator.validateTargeting(module, targeting));
    }

    private static void validateTargeting(BundleModule module, Files.NativeLibraries nativeLibraries) {
        for (Files.TargetedNativeDirectory targetedDirectory : nativeLibraries.getDirectoryList()) {
            ZipPath path = ZipPath.create(targetedDirectory.getPath());
            Targeting.NativeDirectoryTargeting targeting = targetedDirectory.getTargeting();
            if (!targeting.hasAbi()) {
                throw ValidationException.builder().withMessage("Targeted native directory '%s' does not have the ABI dimension set.", targetedDirectory.getPath()).build();
            }
            if (!path.startsWith(BundleModule.LIB_DIRECTORY) || path.getNameCount() != 2) {
                throw ValidationException.builder().withMessage("Path of targeted native directory must be in format 'lib/<directory>' but found '%s'.", path).build();
            }
            if (!BundleValidationUtils.directoryContainsNoFiles(module, path)) continue;
            throw ValidationException.builder().withMessage("Targeted directory '%s' is empty.", path).build();
        }
        Sets.SetView libDirsWithoutTargeting = Sets.difference(module.findEntriesUnderPath(BundleModule.LIB_DIRECTORY).map(libFile -> libFile.getPath().subpath(0, 2).toString()).collect(ImmutableSet.toImmutableSet()), nativeLibraries.getDirectoryList().stream().map(Files.TargetedNativeDirectory::getPath).collect(ImmutableSet.toImmutableSet()));
        if (!libDirsWithoutTargeting.isEmpty()) {
            throw ValidationException.builder().withMessage("Following native directories are not targeted: %s", libDirsWithoutTargeting).build();
        }
    }
}

