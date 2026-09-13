/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.validation;

import com.android.tools.build.bundletool.model.AppBundle;
import com.android.tools.build.bundletool.validation.AbiParityValidator;
import com.android.tools.build.bundletool.validation.AndroidManifestValidator;
import com.android.tools.build.bundletool.validation.ApexBundleValidator;
import com.android.tools.build.bundletool.validation.AssetModuleFilesValidator;
import com.android.tools.build.bundletool.validation.AssetsTargetingValidator;
import com.android.tools.build.bundletool.validation.BundleConfigValidator;
import com.android.tools.build.bundletool.validation.BundleFilesValidator;
import com.android.tools.build.bundletool.validation.BundleZipValidator;
import com.android.tools.build.bundletool.validation.DexFilesValidator;
import com.android.tools.build.bundletool.validation.EntryClashValidator;
import com.android.tools.build.bundletool.validation.MandatoryFilesPresenceValidator;
import com.android.tools.build.bundletool.validation.ModuleDependencyValidator;
import com.android.tools.build.bundletool.validation.ModuleNamesValidator;
import com.android.tools.build.bundletool.validation.ModuleTitleValidator;
import com.android.tools.build.bundletool.validation.NativeTargetingValidator;
import com.android.tools.build.bundletool.validation.ResourceTableValidator;
import com.android.tools.build.bundletool.validation.SubValidator;
import com.android.tools.build.bundletool.validation.TextureCompressionFormatParityValidator;
import com.android.tools.build.bundletool.validation.ValidatorRunner;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import java.util.zip.ZipFile;

public class AppBundleValidator {
    @VisibleForTesting
    static final ImmutableList<SubValidator> DEFAULT_BUNDLE_FILE_SUB_VALIDATORS = ImmutableList.of(new BundleZipValidator(), new MandatoryFilesPresenceValidator());
    @VisibleForTesting
    static final ImmutableList<SubValidator> DEFAULT_BUNDLE_SUB_VALIDATORS = ImmutableList.of(new BundleFilesValidator(), new ModuleNamesValidator(), new AndroidManifestValidator(), new BundleConfigValidator(), new EntryClashValidator(), new AbiParityValidator(), new TextureCompressionFormatParityValidator(), new DexFilesValidator(), new ApexBundleValidator(), new AssetsTargetingValidator(), new NativeTargetingValidator(), new ModuleDependencyValidator(), new SubValidator[]{new ModuleTitleValidator(), new ResourceTableValidator(), new AssetModuleFilesValidator()});
    private final ImmutableList<SubValidator> allBundleSubValidators;
    private final ImmutableList<SubValidator> allBundleFileSubValidators;

    private AppBundleValidator(ImmutableList<SubValidator> allBundleSubValidators, ImmutableList<SubValidator> allBundleFileSubValidators) {
        this.allBundleSubValidators = allBundleSubValidators;
        this.allBundleFileSubValidators = allBundleFileSubValidators;
    }

    public static AppBundleValidator create() {
        return AppBundleValidator.create(ImmutableList.of());
    }

    public static AppBundleValidator create(ImmutableList<SubValidator> extraSubValidators) {
        AppBundleValidator validator = new AppBundleValidator((ImmutableList<SubValidator>)((ImmutableList.Builder)((ImmutableList.Builder)ImmutableList.builder().addAll(DEFAULT_BUNDLE_SUB_VALIDATORS)).addAll(extraSubValidators)).build(), (ImmutableList<SubValidator>)((ImmutableList.Builder)((ImmutableList.Builder)ImmutableList.builder().addAll(DEFAULT_BUNDLE_FILE_SUB_VALIDATORS)).addAll(extraSubValidators)).build());
        return validator;
    }

    public void validateFile(ZipFile bundleFile) {
        new ValidatorRunner(this.allBundleFileSubValidators).validateBundleZipFile(bundleFile);
    }

    public void validate(AppBundle bundle) {
        new ValidatorRunner(this.allBundleSubValidators).validateBundle(bundle);
    }
}

