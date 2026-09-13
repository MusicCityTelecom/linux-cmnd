/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.validation;

import com.android.bundle.Targeting;
import com.android.tools.build.bundletool.model.AndroidManifest;
import com.android.tools.build.bundletool.model.BundleModule;
import com.android.tools.build.bundletool.model.ManifestDeliveryElement;
import com.android.tools.build.bundletool.model.ModuleConditions;
import com.android.tools.build.bundletool.model.exceptions.ValidationException;
import com.android.tools.build.bundletool.model.exceptions.manifest.ManifestDuplicateAttributeException;
import com.android.tools.build.bundletool.model.exceptions.manifest.ManifestFusingException;
import com.android.tools.build.bundletool.model.exceptions.manifest.ManifestSdkTargetingException;
import com.android.tools.build.bundletool.model.exceptions.manifest.ManifestVersionCodeConflictException;
import com.android.tools.build.bundletool.model.exceptions.manifest.ManifestVersionException;
import com.android.tools.build.bundletool.model.targeting.TargetingUtils;
import com.android.tools.build.bundletool.model.utils.xmlproto.XmlProtoAttribute;
import com.android.tools.build.bundletool.model.utils.xmlproto.XmlProtoElement;
import com.android.tools.build.bundletool.validation.SubValidator;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import java.util.Optional;

public class AndroidManifestValidator
extends SubValidator {
    private static final Joiner COMMA_JOINER = Joiner.on(',');

    @Override
    public void validateAllModules(ImmutableList<BundleModule> modules) {
        this.validateSameVersionCode(modules);
        AndroidManifestValidator.validateInstant(modules);
        AndroidManifestValidator.validateNoVersionCodeInAssetModules(modules);
        this.validateTargetSandboxVersion(modules);
        AndroidManifestValidator.validateMinSdk(modules);
        AndroidManifestValidator.validateTcfTargetingNotMixedWithSupportsGlTexture(modules);
    }

    public void validateSameVersionCode(ImmutableList<BundleModule> modules) {
        ImmutableList versionCodes = modules.stream().map(BundleModule::getAndroidManifest).filter(manifest -> !manifest.getModuleType().equals((Object)BundleModule.ModuleType.ASSET_MODULE)).map(AndroidManifest::getVersionCode).map(optVersionCode -> (Integer)optVersionCode.orElseThrow(ManifestVersionException.VersionCodeMissingException::new)).distinct().sorted().collect(ImmutableList.toImmutableList());
        if (versionCodes.size() > 1) {
            throw new ManifestVersionCodeConflictException(versionCodes.toArray(new Integer[0]));
        }
    }

    private static void validateNoVersionCodeInAssetModules(ImmutableList<BundleModule> modules) {
        Optional<BundleModule> assetModuleWithVersionCode = modules.stream().filter(module -> module.getModuleType().equals((Object)BundleModule.ModuleType.ASSET_MODULE) && ((XmlProtoElement)module.getAndroidManifest().getManifestRoot().getElement()).getAndroidAttribute(16843291).isPresent()).findFirst();
        if (assetModuleWithVersionCode.isPresent()) {
            throw ValidationException.builder().withMessage("Asset packs cannot specify a version code, but '%s' does.", assetModuleWithVersionCode.get().getName()).build();
        }
    }

    void validateTargetSandboxVersion(ImmutableList<BundleModule> modules) {
        ImmutableList targetSandboxVersion = modules.stream().map(BundleModule::getAndroidManifest).filter(manifest -> !manifest.getModuleType().equals((Object)BundleModule.ModuleType.ASSET_MODULE)).map(AndroidManifest::getTargetSandboxVersion).filter(Optional::isPresent).map(Optional::get).distinct().sorted().collect(ImmutableList.toImmutableList());
        if (targetSandboxVersion.size() > 1) {
            throw ValidationException.builder().withMessage("The attribute 'targetSandboxVersion' should have the same value across modules, but found [%s]", COMMA_JOINER.join(targetSandboxVersion)).build();
        }
        if (targetSandboxVersion.size() == 1 && (Integer)Iterables.getOnlyElement(targetSandboxVersion) > 2) {
            throw ValidationException.builder().withMessage("The attribute 'targetSandboxVersion' cannot have a value greater than 2, but found %d", Iterables.getOnlyElement(targetSandboxVersion)).build();
        }
    }

    private static void validateMinSdk(ImmutableList<BundleModule> modules) {
        int baseMinSdk = modules.stream().filter(BundleModule::isBaseModule).map(BundleModule::getAndroidManifest).mapToInt(AndroidManifest::getEffectiveMinSdkVersion).findFirst().orElseThrow(() -> new ValidationException("No base module found."));
        if (modules.stream().filter(m3 -> m3.getAndroidManifest().getMinSdkVersion().isPresent()).anyMatch(m3 -> m3.getAndroidManifest().getEffectiveMinSdkVersion() < baseMinSdk)) {
            throw ValidationException.builder().withMessage("Modules cannot have a minSdkVersion attribute with a value lower than the one from the base module.").build();
        }
    }

    private static void validateTcfTargetingNotMixedWithSupportsGlTexture(ImmutableList<BundleModule> modules) {
        ImmutableSet supportsGlTextureStrings = modules.stream().map(BundleModule::getAndroidManifest).flatMap(manifest -> manifest.getSupportsGlTextures().stream()).collect(ImmutableSet.toImmutableSet());
        ImmutableSet allModuleTextureFormats = modules.stream().flatMap(module -> TargetingUtils.extractTextureCompressionFormats(TargetingUtils.extractAssetsTargetedDirectories(module)).stream()).collect(ImmutableSet.toImmutableSet());
        if (!supportsGlTextureStrings.isEmpty() && !allModuleTextureFormats.isEmpty()) {
            throw ValidationException.builder().withMessage("Modules cannot have supports-gl-texture in their manifest (found: %s) and texture targeted directories in modules (found: %s).", supportsGlTextureStrings, allModuleTextureFormats).build();
        }
    }

    @Override
    public void validateModule(BundleModule module) {
        AndroidManifestValidator.validateInstant(module);
        AndroidManifestValidator.validateDeliverySettings(module);
        AndroidManifestValidator.validateInstantDeliverySettings(module);
        AndroidManifestValidator.validateFusingConfig(module);
        AndroidManifestValidator.validateMinMaxSdk(module);
        AndroidManifestValidator.validateNumberOfDistinctSplitIds(module);
        AndroidManifestValidator.validateAssetModuleManifest(module);
        AndroidManifestValidator.validateMinSdkCondition(module);
        AndroidManifestValidator.validateNoConditionalTargetingInAssetModules(module);
        AndroidManifestValidator.validateInstantAndPersistentDeliveryCombinationsForAssetModules(module);
    }

    private static void validateInstant(ImmutableList<BundleModule> modules) {
        BundleModule baseModule = modules.stream().filter(BundleModule::isBaseModule).findFirst().orElseThrow(() -> new ValidationException("App Bundle does not contain a mandatory 'base' module."));
        if (modules.stream().anyMatch(BundleModule::isInstantModule) && !baseModule.isInstantModule()) {
            throw ValidationException.builder().withMessage("App Bundle contains instant modules but the 'base' module is not marked 'instant'.").build();
        }
    }

    private static void validateInstant(BundleModule module) {
        Optional<Integer> maxSdk;
        AndroidManifest manifest = module.getAndroidManifest();
        Optional<Boolean> isInstantModule = manifest.isInstantModule();
        if (isInstantModule.orElse(false).booleanValue() && (maxSdk = manifest.getMaxSdkVersion()).isPresent() && maxSdk.get() < 21) {
            throw new ManifestSdkTargetingException.MaxSdkLessThanMinInstantSdk(maxSdk.get());
        }
    }

    private static void validateDeliverySettings(BundleModule module) {
        boolean deliveryTypeDeclared = module.getAndroidManifest().isDeliveryTypeDeclared();
        BundleModule.ModuleDeliveryType deliveryType = module.getDeliveryType();
        if (module.getAndroidManifest().getOnDemandAttribute().isPresent() && module.getAndroidManifest().getManifestDeliveryElement().isPresent()) {
            throw ValidationException.builder().withMessage("Module '%s' cannot use <dist:delivery> settings and legacy dist:onDemand attribute at the same time", module.getName()).build();
        }
        if (module.isBaseModule()) {
            if (deliveryType.equals((Object)BundleModule.ModuleDeliveryType.NO_INITIAL_INSTALL)) {
                throw new ValidationException("The base module cannot be marked on-demand since it will always be served.");
            }
            if (deliveryType.equals((Object)BundleModule.ModuleDeliveryType.CONDITIONAL_INITIAL_INSTALL)) {
                throw new ValidationException("The base module cannot have conditions since it will always be served.");
            }
        } else if (!deliveryTypeDeclared) {
            throw ValidationException.builder().withMessage("The module must explicitly set its delivery options using the <dist:delivery> element (module: '%s').", module.getName()).build();
        }
    }

    private static void validateInstantDeliverySettings(BundleModule module) {
        if (module.getAndroidManifest().getInstantManifestDeliveryElement().isPresent() && module.getAndroidManifest().getInstantAttribute().isPresent()) {
            throw ValidationException.builder().withMessage("The <dist:instant-delivery> element and dist:instant attribute cannot be used together (module: '%s').", module.getName()).build();
        }
    }

    private static void validateFusingConfig(BundleModule module) {
        Optional<Boolean> isInstant = module.getAndroidManifest().isInstantModule();
        if (isInstant.isPresent() && isInstant.get().booleanValue()) {
            return;
        }
        Optional<Boolean> includedInFusingByManifest = module.getAndroidManifest().getIsModuleIncludedInFusing();
        if (module.isBaseModule()) {
            if (includedInFusingByManifest.isPresent() && !includedInFusingByManifest.get().booleanValue()) {
                throw new ManifestFusingException.BaseModuleExcludedFromFusingException();
            }
        } else if (!includedInFusingByManifest.isPresent()) {
            throw new ManifestFusingException.ModuleFusingConfigurationMissingException(module.getName().getName());
        }
    }

    private static void validateMinMaxSdk(BundleModule module) {
        AndroidManifest manifest = module.getAndroidManifest();
        Optional<Integer> maxSdk = manifest.getMaxSdkVersion();
        Optional<Integer> minSdk = manifest.getMinSdkVersion();
        maxSdk.filter(sdk -> sdk < 0).ifPresent(sdk -> {
            throw new ManifestSdkTargetingException.MaxSdkInvalidException((int)sdk);
        });
        minSdk.filter(sdk -> sdk < 0).ifPresent(sdk -> {
            throw new ManifestSdkTargetingException.MinSdkInvalidException((int)sdk);
        });
        if (maxSdk.isPresent() && minSdk.isPresent() && maxSdk.get() < minSdk.get()) {
            throw new ManifestSdkTargetingException.MinSdkGreaterThanMaxSdkException(minSdk.get(), maxSdk.get());
        }
    }

    private static void validateNumberOfDistinctSplitIds(BundleModule module) {
        ImmutableSet<XmlProtoAttribute> splitIds = ((XmlProtoElement)module.getAndroidManifest().getManifestRoot().getElement()).getAttributes().filter(attr -> attr.getName().equals("split") && attr.getNamespaceUri().equals("")).collect(ImmutableSet.toImmutableSet());
        if (splitIds.size() > 1) {
            throw new ManifestDuplicateAttributeException("split", splitIds, module.getName().toString());
        }
    }

    private static void validateAssetModuleManifest(BundleModule module) {
        ImmutableMultimap<String, String> allowedManifestElementChildren = ImmutableMultimap.of("http://schemas.android.com/apk/distribution", "module", "", "uses-split");
        AndroidManifest manifest = module.getAndroidManifest();
        if (!manifest.getModuleType().equals((Object)BundleModule.ModuleType.ASSET_MODULE)) {
            return;
        }
        if (((XmlProtoElement)manifest.getManifestRoot().getElement()).getChildrenElements().anyMatch(child -> !allowedManifestElementChildren.containsEntry(child.getNamespaceUri(), child.getName()))) {
            throw ValidationException.builder().withMessage("Unexpected element declaration in manifest of asset pack '%s'.", module.getName()).build();
        }
    }

    private static void validateMinSdkCondition(BundleModule module) {
        int effectiveMinSdkVersion = module.getAndroidManifest().getEffectiveMinSdkVersion();
        Optional minSdkCondition = module.getAndroidManifest().getManifestDeliveryElement().map(ManifestDeliveryElement::getModuleConditions).flatMap(ModuleConditions::getMinSdkVersion);
        if (minSdkCondition.isPresent() && (Integer)minSdkCondition.get() < effectiveMinSdkVersion) {
            throw ValidationException.builder().withMessage("Module '%s' has <dist:min-sdk> condition (%d) lower than the minSdkVersion(%d) of the module.", module.getName(), minSdkCondition.get(), effectiveMinSdkVersion).build();
        }
    }

    private static void validateNoConditionalTargetingInAssetModules(BundleModule module) {
        if (module.getModuleType().equals((Object)BundleModule.ModuleType.ASSET_MODULE) && !module.getModuleMetadata().getTargeting().equals(Targeting.ModuleTargeting.getDefaultInstance())) {
            throw ValidationException.builder().withMessage("Conditional targeting is not allowed in asset packs, but found in '%s'.", module.getName()).build();
        }
    }

    private static void validateInstantAndPersistentDeliveryCombinationsForAssetModules(BundleModule module) {
        if (!module.getAndroidManifest().getModuleType().equals((Object)BundleModule.ModuleType.ASSET_MODULE) || !module.isInstantModule()) {
            return;
        }
        if (!module.getDeliveryType().equals((Object)BundleModule.ModuleDeliveryType.NO_INITIAL_INSTALL)) {
            throw ValidationException.builder().withMessage("Instant asset packs cannot have install-time delivery (module '%s').", module.getName()).build();
        }
        BundleModule.ModuleDeliveryType instantDelivery = module.getInstantDeliveryType().get();
        if (instantDelivery.equals((Object)BundleModule.ModuleDeliveryType.ALWAYS_INITIAL_INSTALL) || instantDelivery.equals((Object)BundleModule.ModuleDeliveryType.CONDITIONAL_INITIAL_INSTALL)) {
            throw ValidationException.builder().withMessage("Instant delivery cannot be install-time (module '%s').", module.getName()).build();
        }
    }
}

