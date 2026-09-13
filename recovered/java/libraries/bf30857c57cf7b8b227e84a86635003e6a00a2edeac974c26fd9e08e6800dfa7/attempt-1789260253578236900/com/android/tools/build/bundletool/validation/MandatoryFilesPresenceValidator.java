/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.validation;

import com.android.tools.build.bundletool.model.AppBundle;
import com.android.tools.build.bundletool.model.BundleModule;
import com.android.tools.build.bundletool.model.ZipPath;
import com.android.tools.build.bundletool.model.exceptions.BundleFileTypesException;
import com.android.tools.build.bundletool.validation.SubValidator;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableSet;
import com.google.common.io.Files;
import java.util.Collections;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class MandatoryFilesPresenceValidator
extends SubValidator {
    private static final ImmutableSet<ZipPath> NON_MODULE_DIRECTORIES = ImmutableSet.of(AppBundle.METADATA_DIRECTORY, ZipPath.create("META-INF"));

    @Override
    public void validateModuleZipFile(ZipFile moduleFile) {
        MandatoryFilesPresenceValidator.checkModuleHasAndroidManifest(moduleFile, ZipPath.create(""), Files.getNameWithoutExtension(moduleFile.getName()));
    }

    @Override
    public void validateBundleZipFile(ZipFile bundleFile) {
        ImmutableSet moduleDirectories = Collections.list(bundleFile.entries()).stream().map(ZipEntry::getName).map(ZipPath::create).filter(entryPath -> entryPath.getNameCount() > 1).map(entryPath -> entryPath.getName(0)).filter(Predicates.not(NON_MODULE_DIRECTORIES::contains)).collect(ImmutableSet.toImmutableSet());
        MandatoryFilesPresenceValidator.checkBundleHasBundleConfig(bundleFile);
        for (ZipPath moduleDir : moduleDirectories) {
            MandatoryFilesPresenceValidator.checkModuleHasAndroidManifest(bundleFile, moduleDir, moduleDir.toString());
        }
    }

    private static void checkBundleHasBundleConfig(ZipFile bundleFile) {
        if (bundleFile.getEntry("BundleConfig.pb") == null) {
            throw new BundleFileTypesException.MandatoryBundleFileMissingException(ZipPath.create("BundleConfig.pb"));
        }
    }

    private static void checkModuleHasAndroidManifest(ZipFile zipFile, ZipPath moduleBaseDir, String moduleName) {
        ZipPath moduleManifestPath = moduleBaseDir.resolve(BundleModule.SpecialModuleEntry.ANDROID_MANIFEST.getPath());
        if (zipFile.getEntry(moduleManifestPath.toString()) == null) {
            throw new BundleFileTypesException.MandatoryModuleFileMissingException(moduleName, BundleModule.SpecialModuleEntry.ANDROID_MANIFEST.getPath());
        }
    }
}

