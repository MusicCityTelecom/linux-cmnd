/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.validation;

import com.android.tools.build.bundletool.model.BundleModule;
import com.android.tools.build.bundletool.model.ModuleEntry;
import com.android.tools.build.bundletool.model.ZipPath;
import com.android.tools.build.bundletool.model.exceptions.ValidationException;
import com.android.tools.build.bundletool.validation.SubValidator;
import com.google.common.collect.ImmutableList;
import com.google.errorprone.annotations.FormatMethod;
import com.google.errorprone.annotations.FormatString;
import java.util.function.Predicate;

public class AssetModuleFilesValidator
extends SubValidator {
    private static final Predicate<ZipPath> IS_INVALID_ASSET_MODULE_ENTRY = path -> !path.startsWith(BundleModule.ASSETS_DIRECTORY) && !path.startsWith(BundleModule.MANIFEST_DIRECTORY);

    @Override
    public void validateModule(BundleModule module) {
        if (!module.getModuleType().equals((Object)BundleModule.ModuleType.ASSET_MODULE)) {
            return;
        }
        this.validateNoResourceTable(module);
        this.validateOnlyAssetsAndManifest(module);
        this.validateNoNativeOrApexConfig(module);
    }

    private void validateNoResourceTable(BundleModule module) {
        this.validate(!module.getResourceTable().isPresent(), "Unexpected resource table found in asset pack '%s'.", module.getName());
    }

    private void validateOnlyAssetsAndManifest(BundleModule module) {
        ImmutableList illegalPaths = module.findEntries(IS_INVALID_ASSET_MODULE_ENTRY).map(ModuleEntry::getPath).map(ZipPath::toString).collect(ImmutableList.toImmutableList());
        this.validate(illegalPaths.isEmpty(), "Invalid %s found in asset pack '%s': '%s'.", illegalPaths.size() == 1 ? "entry" : "entries", module.getName(), String.join((CharSequence)"', '", illegalPaths));
    }

    private void validateNoNativeOrApexConfig(BundleModule module) {
        this.validate(!module.getNativeConfig().isPresent(), "Native libraries config not allowed in asset packs, but found in '%s'.", module.getName());
        this.validate(!module.getApexConfig().isPresent(), "Apex config not allowed in asset packs, but found in '%s'.", module.getName());
    }

    @FormatMethod
    private void validate(boolean condition, @FormatString String message, Object ... args) {
        if (!condition) {
            throw ValidationException.builder().withMessage(message, args).build();
        }
    }
}

