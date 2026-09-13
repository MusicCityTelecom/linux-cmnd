/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.validation;

import com.android.aapt.Resources;
import com.android.tools.build.bundletool.model.BundleModule;
import com.android.tools.build.bundletool.model.exceptions.ValidationException;
import com.android.tools.build.bundletool.model.utils.ResourcesUtils;
import com.android.tools.build.bundletool.model.version.BundleToolVersion;
import com.android.tools.build.bundletool.model.version.Version;
import com.android.tools.build.bundletool.model.version.VersionGuardedFeature;
import com.android.tools.build.bundletool.validation.SubValidator;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import java.util.Optional;

public class ModuleTitleValidator
extends SubValidator {
    @Override
    public void validateAllModules(ImmutableList<BundleModule> modules) {
        ModuleTitleValidator.checkModuleTitles(modules);
    }

    private static void checkModuleTitles(ImmutableList<BundleModule> modules) {
        BundleModule baseModule = modules.stream().filter(BundleModule::isBaseModule).findFirst().get();
        Version bundleVersion = BundleToolVersion.getVersionFromBundleConfig(baseModule.getBundleConfig());
        if (!VersionGuardedFeature.MODULE_TITLE_VALIDATION_ENFORCED.enabledForVersion(bundleVersion)) {
            return;
        }
        Resources.ResourceTable table = baseModule.getResourceTable().orElse(Resources.ResourceTable.getDefaultInstance());
        ImmutableSet stringResourceIds = ResourcesUtils.entries(table).filter(entry -> entry.getType().getName().equals("string")).map(entry -> entry.getResourceId().getFullResourceId()).collect(ImmutableSet.toImmutableSet());
        for (BundleModule module : modules) {
            if (module.getModuleType().equals((Object)BundleModule.ModuleType.ASSET_MODULE)) {
                if (!module.getAndroidManifest().getTitleRefId().isPresent()) continue;
                throw ValidationException.builder().withMessage("Module titles not supported in asset packs, but found in '%s'.", module.getName()).build();
            }
            if (module.getDeliveryType().equals((Object)BundleModule.ModuleDeliveryType.ALWAYS_INITIAL_INSTALL)) continue;
            Optional<Integer> titleRefId = module.getAndroidManifest().getTitleRefId();
            if (!titleRefId.isPresent()) {
                throw ValidationException.builder().withMessage("Mandatory title is missing in manifest for module '%s'.", module.getName()).build();
            }
            if (stringResourceIds.contains(titleRefId.get())) continue;
            throw ValidationException.builder().withMessage("Title for module '%s' is missing in the base resource table.", module.getName()).build();
        }
    }
}

