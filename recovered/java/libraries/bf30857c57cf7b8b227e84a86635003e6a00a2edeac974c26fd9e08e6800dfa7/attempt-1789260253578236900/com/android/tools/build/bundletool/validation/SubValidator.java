/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.validation;

import com.android.tools.build.bundletool.model.AppBundle;
import com.android.tools.build.bundletool.model.BundleModule;
import com.android.tools.build.bundletool.model.ZipPath;
import com.google.common.collect.ImmutableList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public abstract class SubValidator {
    public void validateModuleZipFile(ZipFile moduleFile) {
    }

    public void validateBundleZipFile(ZipFile bundleFile) {
    }

    public void validateBundleZipEntry(ZipFile bundleFile, ZipEntry zipEntry) {
    }

    public void validateBundle(AppBundle bundle) {
    }

    public void validateAllModules(ImmutableList<BundleModule> modules) {
    }

    public void validateModule(BundleModule module) {
    }

    public void validateModuleFile(ZipPath file) {
    }
}

