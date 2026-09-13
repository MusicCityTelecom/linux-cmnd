/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.validation;

import com.android.bundle.Config;
import com.android.tools.build.bundletool.model.BundleModule;
import com.android.tools.build.bundletool.model.BundleModuleName;
import com.android.tools.build.bundletool.model.InputStreamSuppliers;
import com.android.tools.build.bundletool.model.ModuleEntry;
import com.android.tools.build.bundletool.model.ZipPath;
import com.android.tools.build.bundletool.model.version.BundleToolVersion;
import com.android.tools.build.bundletool.validation.AbiParityValidator;
import com.android.tools.build.bundletool.validation.AndroidManifestValidator;
import com.android.tools.build.bundletool.validation.ApexBundleValidator;
import com.android.tools.build.bundletool.validation.AssetModuleFilesValidator;
import com.android.tools.build.bundletool.validation.BundleFilesValidator;
import com.android.tools.build.bundletool.validation.DexFilesValidator;
import com.android.tools.build.bundletool.validation.EntryClashValidator;
import com.android.tools.build.bundletool.validation.MandatoryFilesPresenceValidator;
import com.android.tools.build.bundletool.validation.ModuleDependencyValidator;
import com.android.tools.build.bundletool.validation.ModuleNamesValidator;
import com.android.tools.build.bundletool.validation.ModuleTitleValidator;
import com.android.tools.build.bundletool.validation.ResourceTableValidator;
import com.android.tools.build.bundletool.validation.SubValidator;
import com.android.tools.build.bundletool.validation.TextureCompressionFormatParityValidator;
import com.android.tools.build.bundletool.validation.ValidatorRunner;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class BundleModulesValidator {
    @VisibleForTesting
    static final ImmutableList<SubValidator> MODULE_FILE_SUB_VALIDATORS = ImmutableList.of(new MandatoryFilesPresenceValidator());
    @VisibleForTesting
    static final ImmutableList<SubValidator> MODULES_SUB_VALIDATORS = ImmutableList.of(new BundleFilesValidator(), new ModuleNamesValidator(), new AndroidManifestValidator(), new EntryClashValidator(), new AbiParityValidator(), new TextureCompressionFormatParityValidator(), new DexFilesValidator(), new ApexBundleValidator(), new ModuleDependencyValidator(), new ModuleTitleValidator(), new ResourceTableValidator(), new AssetModuleFilesValidator(), new SubValidator[0]);
    private static final Config.BundleConfig EMPTY_CONFIG_WITH_CURRENT_VERSION = Config.BundleConfig.newBuilder().setBundletool(Config.Bundletool.newBuilder().setVersion(BundleToolVersion.getCurrentVersion().toString())).build();

    public ImmutableList<BundleModule> validate(ImmutableList<ZipFile> moduleZips) {
        for (ZipFile moduleZip : moduleZips) {
            new ValidatorRunner(MODULE_FILE_SUB_VALIDATORS).validateModuleZipFile(moduleZip);
        }
        ImmutableList<BundleModule> modules = moduleZips.stream().map(this::toBundleModule).collect(ImmutableList.toImmutableList());
        new ValidatorRunner(MODULES_SUB_VALIDATORS).validateBundleModules(modules);
        return modules;
    }

    private BundleModule toBundleModule(ZipFile moduleZipFile) {
        BundleModule bundleModule;
        try {
            bundleModule = BundleModule.builder().setName(BundleModuleName.create("TEMPORARY_MODULE_NAME")).setBundleConfig(EMPTY_CONFIG_WITH_CURRENT_VERSION).addEntries(moduleZipFile.stream().filter(Predicates.not(ZipEntry::isDirectory)).map(zipEntry -> ModuleEntry.builder().setPath(ZipPath.create(zipEntry.getName())).setContentSupplier(InputStreamSuppliers.fromZipEntry(zipEntry, moduleZipFile)).build()).collect(ImmutableList.toImmutableList())).build();
        }
        catch (IOException e2) {
            throw new UncheckedIOException(String.format("Error reading module zip file '%s'.", moduleZipFile.getName()), e2);
        }
        BundleModuleName actualModuleName = bundleModule.getAndroidManifest().getSplitId().map(BundleModuleName::create).orElse(BundleModuleName.BASE_MODULE_NAME);
        return bundleModule.toBuilder().setName(actualModuleName).build();
    }
}

