/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.validation;

import com.android.bundle.Targeting;
import com.android.tools.build.bundletool.model.AbiName;
import com.android.tools.build.bundletool.model.BundleModule;
import com.android.tools.build.bundletool.model.exceptions.ValidationException;
import com.android.tools.build.bundletool.model.targeting.TargetedDirectorySegment;
import com.android.tools.build.bundletool.validation.SubValidator;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

public class AbiParityValidator
extends SubValidator {
    @Override
    public void validateAllModules(ImmutableList<BundleModule> modules) {
        BundleModule referentialModule = null;
        ImmutableSet<Targeting.Abi.AbiAlias> referentialAbis = null;
        for (BundleModule module : modules) {
            ImmutableSet<Targeting.Abi.AbiAlias> moduleAbis = AbiParityValidator.getSupportedAbis(module);
            if (moduleAbis.isEmpty()) continue;
            if (referentialAbis == null) {
                referentialModule = module;
                referentialAbis = moduleAbis;
                continue;
            }
            if (referentialAbis.equals(moduleAbis)) continue;
            throw ValidationException.builder().withMessage("All modules with native libraries must support the same set of ABIs, but module '%s' supports '%s' and module '%s' supports '%s'.", referentialModule.getName(), referentialAbis, module.getName(), moduleAbis).build();
        }
    }

    private static ImmutableSet<Targeting.Abi.AbiAlias> getSupportedAbis(BundleModule module) {
        return module.findEntriesUnderPath(BundleModule.LIB_DIRECTORY).map(entry -> entry.getPath().getName(1).toString()).map(TargetedDirectorySegment::parse).map(TargetedDirectorySegment::getName).map(subDir -> AbiName.fromLibSubDirName(subDir).get().toProto()).collect(ImmutableSet.toImmutableSet());
    }
}

