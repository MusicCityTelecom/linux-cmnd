/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.validation;

import com.android.tools.build.bundletool.model.AppBundle;
import com.android.tools.build.bundletool.model.BundleModule;
import com.android.tools.build.bundletool.model.ModuleEntry;
import com.android.tools.build.bundletool.model.ZipPath;
import com.android.tools.build.bundletool.validation.SubValidator;
import com.google.common.collect.ImmutableList;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ValidatorRunner {
    private final ImmutableList<SubValidator> subValidators;

    public ValidatorRunner(ImmutableList<SubValidator> subValidators) {
        this.subValidators = subValidators;
    }

    public void validateBundleZipFile(ZipFile bundleFile) {
        this.subValidators.forEach(subValidator -> subValidator.validateBundleZipFile(bundleFile));
        Enumeration<? extends ZipEntry> zipEntries = bundleFile.entries();
        while (zipEntries.hasMoreElements()) {
            ZipEntry zipEntry = zipEntries.nextElement();
            this.subValidators.forEach(subValidator -> subValidator.validateBundleZipEntry(bundleFile, zipEntry));
        }
    }

    public void validateModuleZipFile(ZipFile moduleFile) {
        this.subValidators.forEach(subValidator -> subValidator.validateModuleZipFile(moduleFile));
    }

    public void validateBundle(AppBundle bundle) {
        this.subValidators.forEach(subValidator -> ValidatorRunner.validateBundleUsingSubValidator(bundle, subValidator));
    }

    public void validateBundleModules(ImmutableList<BundleModule> modules) {
        this.subValidators.forEach(subValidator -> ValidatorRunner.validateBundleModulesUsingSubValidator(modules, subValidator));
    }

    private static void validateBundleUsingSubValidator(AppBundle bundle, SubValidator subValidator) {
        subValidator.validateBundle(bundle);
        ValidatorRunner.validateBundleModulesUsingSubValidator(ImmutableList.copyOf(bundle.getModules().values()), subValidator);
    }

    private static void validateBundleModulesUsingSubValidator(ImmutableList<BundleModule> modules, SubValidator subValidator) {
        subValidator.validateAllModules(modules);
        for (BundleModule module : modules) {
            subValidator.validateModule(module);
            for (ZipPath moduleFile : ValidatorRunner.getModuleFiles(module)) {
                subValidator.validateModuleFile(moduleFile);
            }
        }
    }

    private static ImmutableList<ZipPath> getModuleFiles(BundleModule module) {
        return module.getEntries().stream().map(ModuleEntry::getPath).collect(ImmutableList.toImmutableList());
    }
}

