/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.validation;

import com.android.tools.build.bundletool.model.BundleModule;
import com.android.tools.build.bundletool.model.BundleModuleName;
import com.android.tools.build.bundletool.model.exceptions.ValidationException;
import com.android.tools.build.bundletool.validation.SubValidator;
import com.google.common.collect.ImmutableList;
import java.util.HashSet;
import java.util.Optional;

final class ModuleNamesValidator
extends SubValidator {
    ModuleNamesValidator() {
    }

    @Override
    public void validateAllModules(ImmutableList<BundleModule> modules) {
        HashSet<BundleModuleName> moduleNames = new HashSet<BundleModuleName>();
        for (BundleModule module : modules) {
            boolean alreadyPresent;
            Optional<String> splitId = module.getAndroidManifest().getSplitId();
            BundleModuleName moduleName = module.getName();
            boolean isFeatureModule = module.getAndroidManifest().getModuleType().equals((Object)BundleModule.ModuleType.FEATURE_MODULE);
            if (moduleName.equals(BundleModuleName.BASE_MODULE_NAME)) {
                if (splitId.isPresent()) {
                    throw new ValidationException("The base module should not have the 'split' attribute set in the AndroidManifest.xml");
                }
            } else if (splitId.isPresent()) {
                if (!splitId.get().equals(moduleName.getName())) {
                    throw ValidationException.builder().withMessage("The 'split' attribute in the AndroidManifest.xml of modules must be the name of the module, but has the value '%s' in module '%s'", splitId.get(), moduleName).build();
                }
            } else {
                throw ValidationException.builder().withMessage("No 'split' attribute found in the AndroidManifest.xml of module '%s'.", moduleName).build();
            }
            if (!(alreadyPresent = !moduleNames.add(moduleName))) continue;
            if (splitId.isPresent()) {
                throw ValidationException.builder().withMessage("More than one module have the 'split' attribute set to '%s' in the AndroidManifest.xml.", splitId.get()).build();
            }
            if (!isFeatureModule) continue;
            throw new ValidationException("More than one module was found without the 'split' attribute set in the AndroidManifest.xml. Ensure that all the dynamic features have the 'split' attribute correctly set in the AndroidManifest.xml.");
        }
        if (!moduleNames.contains(BundleModuleName.BASE_MODULE_NAME)) {
            throw new ValidationException("No base module found. At least one module must not have a 'split' attribute set in the AndroidManifest.xml.");
        }
    }
}

