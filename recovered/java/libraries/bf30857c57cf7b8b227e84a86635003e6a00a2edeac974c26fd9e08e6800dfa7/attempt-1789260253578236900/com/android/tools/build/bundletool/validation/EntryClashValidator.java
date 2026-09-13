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
import java.util.HashMap;
import java.util.function.Predicate;

public class EntryClashValidator
extends SubValidator {
    private static final Predicate<ZipPath> IS_EXPECTED_TO_BE_DIFFERENT = path -> path.startsWith(BundleModule.DEX_DIRECTORY);

    @Override
    public void validateAllModules(ImmutableList<BundleModule> modules) {
        HashMap<ZipPath, BundleModule> usedPaths = new HashMap<ZipPath, BundleModule>();
        for (BundleModule module : modules) {
            for (ModuleEntry entry : module.getEntries()) {
                BundleModule otherModuleWithPath;
                if (IS_EXPECTED_TO_BE_DIFFERENT.test(entry.getPath()) || (otherModuleWithPath = usedPaths.putIfAbsent(entry.getPath(), module)) == null) continue;
                EntryClashValidator.checkEqualEntries(entry.getPath(), otherModuleWithPath, module);
            }
        }
    }

    private static void checkEqualEntries(ZipPath path, BundleModule module1, BundleModule module2) {
        ModuleEntry entry2;
        ModuleEntry entry1 = module1.getEntry(path).get();
        if (!entry1.equals(entry2 = module2.getEntry(path).get())) {
            throw ValidationException.builder().withMessage("Modules '%s' and '%s' contain entry '%s' with different content.", module1.getName(), module2.getName(), path).build();
        }
    }
}

