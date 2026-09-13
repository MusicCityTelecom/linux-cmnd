/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.io;

import com.android.bundle.Commands;
import com.android.bundle.Config;
import com.android.bundle.Devices;
import com.android.bundle.Targeting;
import com.android.tools.build.bundletool.commands.BuildApksCommand;
import com.android.tools.build.bundletool.device.ApkMatcher;
import com.android.tools.build.bundletool.io.ApkSetBuilderFactory;
import com.android.tools.build.bundletool.io.ConcurrencyUtils;
import com.android.tools.build.bundletool.model.AndroidManifest;
import com.android.tools.build.bundletool.model.ApkListener;
import com.android.tools.build.bundletool.model.ApkModifier;
import com.android.tools.build.bundletool.model.AppBundle;
import com.android.tools.build.bundletool.model.BundleModule;
import com.android.tools.build.bundletool.model.BundleModuleName;
import com.android.tools.build.bundletool.model.GeneratedApks;
import com.android.tools.build.bundletool.model.GeneratedAssetSlices;
import com.android.tools.build.bundletool.model.ManifestDeliveryElement;
import com.android.tools.build.bundletool.model.ModuleSplit;
import com.android.tools.build.bundletool.model.VariantKey;
import com.android.tools.build.bundletool.model.utils.CollectorUtils;
import com.android.tools.build.bundletool.model.version.BundleToolVersion;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Multimap;
import com.google.common.util.concurrent.ListeningExecutorService;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ApkSerializerManager {
    private final ListeningExecutorService executorService;
    private final ApkListener apkListener;
    private final ApkModifier apkModifier;
    private final int firstVariantNumber;
    private final AppBundle appBundle;
    private final ApkSetBuilderFactory.ApkSetBuilder apkSetBuilder;

    public ApkSerializerManager(AppBundle appBundle, ApkSetBuilderFactory.ApkSetBuilder apkSetBuilder, ListeningExecutorService executorService, ApkListener apkListener, ApkModifier apkModifier, int firstVariantNumber) {
        this.appBundle = appBundle;
        this.apkSetBuilder = apkSetBuilder;
        this.executorService = executorService;
        this.apkListener = apkListener;
        this.apkModifier = apkModifier;
        this.firstVariantNumber = firstVariantNumber;
    }

    public void populateApkSetBuilder(GeneratedApks generatedApks, GeneratedAssetSlices generatedAssetSlices, BuildApksCommand.ApkBuildMode apkBuildMode, Optional<Devices.DeviceSpec> deviceSpec, Commands.LocalTestingInfo localTestingInfo) {
        ImmutableList<Commands.Variant> allVariantsWithTargeting = this.serializeApks(generatedApks, apkBuildMode, deviceSpec);
        ImmutableList<Commands.AssetSliceSet> allAssetSliceSets = this.serializeAssetSlices(generatedAssetSlices, apkBuildMode, deviceSpec);
        this.apkSetBuilder.setTableOfContentsFile(Commands.BuildApksResult.newBuilder().setPackageName(this.appBundle.getBaseModule().getAndroidManifest().getPackageName()).addAllVariant(allVariantsWithTargeting).setBundletool(Config.Bundletool.newBuilder().setVersion(BundleToolVersion.getCurrentVersion().toString())).addAllAssetSliceSet(allAssetSliceSets).setLocalTestingInfo(localTestingInfo).build());
    }

    @VisibleForTesting
    ImmutableList<Commands.Variant> serializeApksForDevice(GeneratedApks generatedApks, Devices.DeviceSpec deviceSpec, BuildApksCommand.ApkBuildMode apkBuildMode) {
        return this.serializeApks(generatedApks, apkBuildMode, Optional.of(deviceSpec));
    }

    @VisibleForTesting
    ImmutableList<Commands.Variant> serializeApks(GeneratedApks generatedApks) {
        return this.serializeApks(generatedApks, BuildApksCommand.ApkBuildMode.DEFAULT);
    }

    @VisibleForTesting
    ImmutableList<Commands.Variant> serializeApks(GeneratedApks generatedApks, BuildApksCommand.ApkBuildMode apkBuildMode) {
        return this.serializeApks(generatedApks, apkBuildMode, Optional.empty());
    }

    private ImmutableList<Commands.Variant> serializeApks(GeneratedApks generatedApks, BuildApksCommand.ApkBuildMode apkBuildMode, Optional<Devices.DeviceSpec> deviceSpec) {
        ApkSerializerManager.validateInput(generatedApks, apkBuildMode);
        Predicate deviceFilter = deviceSpec.isPresent() && !apkBuildMode.isAnySystemMode() ? new ApkMatcher(deviceSpec.get())::matchesModuleSplitByTargeting : Predicates.alwaysTrue();
        ImmutableListMultimap<VariantKey, ModuleSplit> splitsByVariant = generatedApks.getAllApksGroupedByOrderedVariants();
        AtomicInteger variantNumberCounter = new AtomicInteger(this.firstVariantNumber);
        ImmutableMap variantNumberByVariantKey = splitsByVariant.keySet().stream().collect(ImmutableMap.toImmutableMap(Function.identity(), unused -> variantNumberCounter.getAndIncrement()));
        ApkSerializer apkSerializer = new ApkSerializer(this.apkListener, apkBuildMode);
        ImmutableListMultimap<VariantKey, ModuleSplit> finalSplitsByVariant = splitsByVariant.entries().stream().filter(keyModuleSplitEntry -> deviceFilter.test(keyModuleSplitEntry.getValue())).collect(CollectorUtils.groupingBySortedKeys(Map.Entry::getKey, entry -> ApkSerializerManager.clearVariantTargeting(this.modifyApk((ModuleSplit)entry.getValue(), (Integer)variantNumberByVariantKey.get(entry.getKey())))));
        Preconditions.checkState(!finalSplitsByVariant.isEmpty(), "Internal error: No variants were generated.");
        ImmutableMap apkDescriptionBySplit = finalSplitsByVariant.values().stream().distinct().collect(Collectors.collectingAndThen(ImmutableMap.toImmutableMap(Function.identity(), split -> this.executorService.submit(() -> apkSerializer.serialize((ModuleSplit)split))), ConcurrencyUtils::waitForAll));
        ImmutableList.Builder variants = ImmutableList.builder();
        for (VariantKey variantKey : finalSplitsByVariant.keySet()) {
            Commands.Variant.Builder variant = Commands.Variant.newBuilder().setVariantNumber(variantNumberByVariantKey.get(variantKey)).setTargeting(variantKey.getVariantTargeting());
            Multimap splitsByModuleName = finalSplitsByVariant.get((Object)variantKey).stream().collect(CollectorUtils.groupingBySortedKeys(ModuleSplit::getModuleName));
            for (BundleModuleName moduleName : splitsByModuleName.keySet()) {
                variant.addApkSet(Commands.ApkSet.newBuilder().setModuleMetadata(this.appBundle.getModule(moduleName).getModuleMetadata()).addAllApkDescription(splitsByModuleName.get(moduleName).stream().flatMap(split -> ((ImmutableList)apkDescriptionBySplit.get(split)).stream()).collect(ImmutableList.toImmutableList())));
            }
            variants.add(variant.build());
        }
        return variants.build();
    }

    @VisibleForTesting
    ImmutableList<Commands.AssetSliceSet> serializeAssetSlices(GeneratedAssetSlices generatedAssetSlices, BuildApksCommand.ApkBuildMode apkBuildMode, Optional<Devices.DeviceSpec> deviceSpec) {
        Predicate deviceFilter = deviceSpec.isPresent() ? new ApkMatcher(deviceSpec.get())::matchesModuleSplitByTargeting : Predicates.alwaysTrue();
        ApkSerializer apkSerializer = new ApkSerializer(this.apkListener, apkBuildMode);
        ImmutableListMultimap generatedSlicesByModule = generatedAssetSlices.getAssetSlices().stream().filter(deviceFilter).collect(Collectors.groupingBy(ModuleSplit::getModuleName, Collectors.mapping(assetSlice -> this.executorService.submit(() -> apkSerializer.serialize((ModuleSplit)assetSlice)), ImmutableList.toImmutableList()))).entrySet().stream().collect(ImmutableListMultimap.flatteningToImmutableListMultimap(Map.Entry::getKey, entry -> ConcurrencyUtils.waitForAll((Iterable)entry.getValue()).stream().flatMap(Collection::stream)));
        return ((ImmutableMap)generatedSlicesByModule.asMap()).entrySet().stream().map(entry -> Commands.AssetSliceSet.newBuilder().setAssetModuleMetadata(this.getAssetModuleMetadata(this.appBundle.getModule((BundleModuleName)entry.getKey()))).addAllApkDescription((Iterable)entry.getValue()).build()).collect(ImmutableList.toImmutableList());
    }

    private Commands.AssetModuleMetadata getAssetModuleMetadata(BundleModule module) {
        AndroidManifest manifest = module.getAndroidManifest();
        Commands.AssetModuleMetadata.Builder metadataBuilder = Commands.AssetModuleMetadata.newBuilder().setName(module.getName().getName());
        Optional<ManifestDeliveryElement> persistentDelivery = manifest.getManifestDeliveryElement();
        metadataBuilder.setDeliveryType(persistentDelivery.map(delivery -> ApkSerializerManager.getDeliveryType(delivery)).orElse(Commands.DeliveryType.INSTALL_TIME));
        boolean isInstantModule = module.isInstantModule();
        Commands.InstantMetadata.Builder instantMetadataBuilder = Commands.InstantMetadata.newBuilder().setIsInstant(isInstantModule);
        Optional<ManifestDeliveryElement> instantDelivery = manifest.getInstantManifestDeliveryElement();
        if (isInstantModule) {
            instantMetadataBuilder.setDeliveryType(instantDelivery.map(delivery -> ApkSerializerManager.getDeliveryType(delivery)).orElse(Commands.DeliveryType.ON_DEMAND));
        }
        metadataBuilder.setInstantMetadata(instantMetadataBuilder.build());
        return metadataBuilder.build();
    }

    private static void validateInput(GeneratedApks generatedApks, BuildApksCommand.ApkBuildMode apkBuildMode) {
        switch (apkBuildMode) {
            case DEFAULT: {
                Preconditions.checkArgument(generatedApks.getSystemApks().isEmpty(), "Internal error: System APKs can only be set in system mode.");
                break;
            }
            case UNIVERSAL: {
                Preconditions.checkArgument(generatedApks.getSplitApks().isEmpty() && generatedApks.getInstantApks().isEmpty() && generatedApks.getSystemApks().isEmpty(), "Internal error: For universal APK expecting only standalone APKs.");
                break;
            }
            case SYSTEM_COMPRESSED: 
            case SYSTEM: {
                Preconditions.checkArgument(generatedApks.getSplitApks().isEmpty() && generatedApks.getInstantApks().isEmpty() && generatedApks.getStandaloneApks().isEmpty(), "Internal error: For system mode expecting only system APKs.");
                break;
            }
            case PERSISTENT: {
                Preconditions.checkArgument(generatedApks.getSystemApks().isEmpty(), "Internal error: System APKs not expected with persistent mode.");
                Preconditions.checkArgument(generatedApks.getInstantApks().isEmpty(), "Internal error: Instant APKs not expected with persistent mode.");
                break;
            }
            case INSTANT: {
                Preconditions.checkArgument(generatedApks.getSystemApks().isEmpty(), "Internal error: System APKs not expected with instant mode.");
                Preconditions.checkArgument(generatedApks.getSplitApks().isEmpty() && generatedApks.getStandaloneApks().isEmpty(), "Internal error: Persistent APKs not expected with instant mode.");
            }
        }
    }

    private ModuleSplit modifyApk(ModuleSplit moduleSplit, int variantNumber) {
        ApkModifier.ApkDescription apkDescription = ApkModifier.ApkDescription.builder().setBase(moduleSplit.isBaseModuleSplit()).setApkType(moduleSplit.getSplitType().equals((Object)ModuleSplit.SplitType.STANDALONE) ? ApkModifier.ApkDescription.ApkType.STANDALONE : (moduleSplit.isMasterSplit() ? ApkModifier.ApkDescription.ApkType.MASTER_SPLIT : ApkModifier.ApkDescription.ApkType.CONFIG_SPLIT)).setVariantNumber(variantNumber).setVariantTargeting(moduleSplit.getVariantTargeting()).setApkTargeting(moduleSplit.getApkTargeting()).build();
        return moduleSplit.toBuilder().setAndroidManifest(this.apkModifier.modifyManifest(moduleSplit.getAndroidManifest(), apkDescription)).build();
    }

    private static ModuleSplit clearVariantTargeting(ModuleSplit moduleSplit) {
        return moduleSplit.toBuilder().setVariantTargeting(Targeting.VariantTargeting.getDefaultInstance()).build();
    }

    private static Commands.DeliveryType getDeliveryType(ManifestDeliveryElement deliveryElement) {
        if (deliveryElement.hasOnDemandElement()) {
            return Commands.DeliveryType.ON_DEMAND;
        }
        if (deliveryElement.hasFastFollowElement()) {
            return Commands.DeliveryType.FAST_FOLLOW;
        }
        return Commands.DeliveryType.INSTALL_TIME;
    }

    private final class ApkSerializer {
        private final ApkListener apkListener;
        private final BuildApksCommand.ApkBuildMode apkBuildMode;

        public ApkSerializer(ApkListener apkListener, BuildApksCommand.ApkBuildMode apkBuildMode) {
            this.apkListener = apkListener;
            this.apkBuildMode = apkBuildMode;
        }

        public ImmutableList<Commands.ApkDescription> serialize(ModuleSplit split) {
            ImmutableList<Commands.ApkDescription> apkDescriptions;
            switch (split.getSplitType()) {
                case INSTANT: {
                    apkDescriptions = ImmutableList.of(ApkSerializerManager.this.apkSetBuilder.addInstantApk(split));
                    break;
                }
                case SPLIT: {
                    apkDescriptions = ImmutableList.of(ApkSerializerManager.this.apkSetBuilder.addSplitApk(split));
                    break;
                }
                case SYSTEM: {
                    if (split.isBaseModuleSplit() && split.isMasterSplit()) {
                        apkDescriptions = this.apkBuildMode.equals((Object)BuildApksCommand.ApkBuildMode.SYSTEM_COMPRESSED) ? ApkSerializerManager.this.apkSetBuilder.addCompressedSystemApks(split) : ImmutableList.of(ApkSerializerManager.this.apkSetBuilder.addSystemApk(split));
                        break;
                    }
                    apkDescriptions = ImmutableList.of(ApkSerializerManager.this.apkSetBuilder.addSplitApk(split));
                    break;
                }
                case STANDALONE: {
                    apkDescriptions = this.apkBuildMode.equals((Object)BuildApksCommand.ApkBuildMode.UNIVERSAL) ? ImmutableList.of(ApkSerializerManager.this.apkSetBuilder.addStandaloneUniversalApk(split)) : ImmutableList.of(ApkSerializerManager.this.apkSetBuilder.addStandaloneApk(split));
                    break;
                }
                case ASSET_SLICE: {
                    apkDescriptions = ImmutableList.of(ApkSerializerManager.this.apkSetBuilder.addAssetSliceApk(split));
                    break;
                }
                default: {
                    throw new IllegalStateException("Unexpected splitType: " + (Object)((Object)split.getSplitType()));
                }
            }
            apkDescriptions.forEach(this.apkListener::onApkFinalized);
            return apkDescriptions;
        }
    }
}

