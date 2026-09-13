/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.splitters;

import com.android.aapt.ConfigurationOuterClass;
import com.android.aapt.Resources;
import com.android.bundle.Targeting;
import com.android.tools.build.bundletool.model.ManifestMutator;
import com.android.tools.build.bundletool.model.ModuleSplit;
import com.android.tools.build.bundletool.model.ResourceId;
import com.android.tools.build.bundletool.model.ResourceTableEntry;
import com.android.tools.build.bundletool.model.targeting.ScreenDensitySelector;
import com.android.tools.build.bundletool.model.utils.ResourcesUtils;
import com.android.tools.build.bundletool.model.version.Version;
import com.android.tools.build.bundletool.model.version.VersionGuardedFeature;
import com.android.tools.build.bundletool.splitters.SplitterForOneTargetingDimension;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ScreenDensityResourcesSplitter
extends SplitterForOneTargetingDimension {
    static final ImmutableSet<Targeting.ScreenDensity.DensityAlias> DEFAULT_DENSITY_BUCKETS = ImmutableSet.of(Targeting.ScreenDensity.DensityAlias.LDPI, Targeting.ScreenDensity.DensityAlias.MDPI, Targeting.ScreenDensity.DensityAlias.HDPI, Targeting.ScreenDensity.DensityAlias.XHDPI, Targeting.ScreenDensity.DensityAlias.XXHDPI, Targeting.ScreenDensity.DensityAlias.XXXHDPI, new Targeting.ScreenDensity.DensityAlias[]{Targeting.ScreenDensity.DensityAlias.TVDPI});
    private final ImmutableSet<Targeting.ScreenDensity.DensityAlias> densityBuckets;
    private final Version bundleVersion;
    private final Predicate<ResourceId> pinWholeResourceToMaster;
    private final Predicate<ResourceId> pinLowestBucketOfResourceToMaster;

    public ScreenDensityResourcesSplitter(Version bundleVersion, Predicate<ResourceId> pinWholeResourceToMaster, Predicate<ResourceId> pinLowestBucketOfResourceToMaster) {
        this(DEFAULT_DENSITY_BUCKETS, bundleVersion, pinWholeResourceToMaster, pinLowestBucketOfResourceToMaster);
    }

    public ScreenDensityResourcesSplitter(ImmutableSet<Targeting.ScreenDensity.DensityAlias> densityBuckets, Version bundleVersion, Predicate<ResourceId> pinWholeResourceToMaster, Predicate<ResourceId> pinLowestBucketOfResourceToMaster) {
        this.densityBuckets = densityBuckets;
        this.bundleVersion = bundleVersion;
        this.pinWholeResourceToMaster = pinWholeResourceToMaster;
        this.pinLowestBucketOfResourceToMaster = pinLowestBucketOfResourceToMaster;
    }

    @Override
    public ImmutableCollection<ModuleSplit> splitInternal(ModuleSplit split) {
        Optional<Resources.ResourceTable> resourceTable = split.getResourceTable();
        if (!resourceTable.isPresent() || resourceTable.get().equals(Resources.ResourceTable.getDefaultInstance())) {
            return ImmutableList.of(split);
        }
        ImmutableList.Builder splitsBuilder = new ImmutableList.Builder();
        for (Targeting.ScreenDensity.DensityAlias density : this.densityBuckets) {
            Resources.ResourceTable optimizedTable = this.filterResourceTableForDensity(resourceTable.get(), density);
            if (optimizedTable.equals(Resources.ResourceTable.getDefaultInstance())) continue;
            ModuleSplit.Builder moduleSplitBuilder = split.toBuilder().setApkTargeting(split.getApkTargeting().toBuilder().setScreenDensityTargeting(Targeting.ScreenDensityTargeting.newBuilder().addValue(ScreenDensityResourcesSplitter.toScreenDensity(density)).addAllAlternatives(ScreenDensityResourcesSplitter.allBut(this.densityBuckets, density).stream().map(ScreenDensityResourcesSplitter::toScreenDensity).collect(ImmutableList.toImmutableList()))).build()).setMasterSplit(false).addMasterManifestMutator(ManifestMutator.withSplitsRequired(true)).setEntries(ModuleSplit.filterResourceEntries(split.getEntries(), optimizedTable)).setResourceTable(optimizedTable);
            splitsBuilder.add(moduleSplitBuilder.build());
        }
        ModuleSplit defaultResourcesSplit = this.getDefaultResourcesSplit(split, splitsBuilder.build());
        return ((ImmutableList.Builder)splitsBuilder.add(defaultResourcesSplit)).build();
    }

    private static Targeting.ScreenDensity toScreenDensity(Targeting.ScreenDensity.DensityAlias alias) {
        return Targeting.ScreenDensity.newBuilder().setDensityAlias(alias).build();
    }

    private ModuleSplit getDefaultResourcesSplit(ModuleSplit inputSplit, ImmutableCollection<ModuleSplit> densitySplits) {
        Resources.ResourceTable defaultSplitTable = this.getResourceTableForDefaultSplit(inputSplit, this.getClaimedConfigs(densitySplits));
        return inputSplit.toBuilder().setEntries(ModuleSplit.filterResourceEntries(inputSplit.getEntries(), defaultSplitTable)).setResourceTable(defaultSplitTable).build();
    }

    private ImmutableMultimap<ResourceId, Resources.ConfigValue> getClaimedConfigs(Iterable<ModuleSplit> moduleSplits) {
        ImmutableMultimap.Builder<ResourceId, Resources.ConfigValue> result = new ImmutableMultimap.Builder<ResourceId, Resources.ConfigValue>();
        for (ModuleSplit moduleSplit : moduleSplits) {
            Preconditions.checkState(moduleSplit.getResourceTable().isPresent(), "Resource table not found in the density split.");
            for (Resources.Package pkg : moduleSplit.getResourceTable().get().getPackageList()) {
                for (Resources.Type type : pkg.getTypeList()) {
                    for (Resources.Entry entry : type.getEntryList()) {
                        for (Resources.ConfigValue configValue : entry.getConfigValueList()) {
                            result.put(ResourceId.create(pkg, type, entry), configValue);
                        }
                    }
                }
            }
        }
        return result.build();
    }

    private Resources.ResourceTable getResourceTableForDefaultSplit(ModuleSplit split, ImmutableMultimap<ResourceId, Resources.ConfigValue> claimedConfigs) {
        Preconditions.checkArgument(split.getResourceTable().isPresent(), "Expected the split to contain Resource Table.");
        Resources.ResourceTable.Builder prunedTable = split.getResourceTable().get().toBuilder();
        for (Resources.Package.Builder packageBuilder : prunedTable.getPackageBuilderList()) {
            for (Resources.Type.Builder typeBuilder : packageBuilder.getTypeBuilderList()) {
                ArrayList<Resources.Entry> newEntries = new ArrayList<Resources.Entry>();
                for (Resources.Entry entry : typeBuilder.getEntryList()) {
                    ResourceId resourceId = ResourceId.create(packageBuilder, typeBuilder, entry);
                    ImmutableList allConfigsExceptClaimed = entry.getConfigValueList().stream().filter(configValue -> !claimedConfigs.containsEntry(resourceId, configValue)).collect(ImmutableList.toImmutableList());
                    Resources.Entry.Builder newEntry = entry.toBuilder().clearConfigValue().addAllConfigValue(allConfigsExceptClaimed);
                    if (newEntry.getConfigValueCount() <= 0) continue;
                    newEntries.add(newEntry.build());
                }
                typeBuilder.clearEntry().addAllEntry(newEntries);
            }
        }
        return prunedTable.build();
    }

    private Resources.ResourceTable filterResourceTableForDensity(Resources.ResourceTable input, Targeting.ScreenDensity.DensityAlias density) {
        return ResourcesUtils.filterResourceTable(input, entry -> entry.getType().getName().equals("mipmap"), entry -> this.filterEntryForDensity((ResourceTableEntry)entry, density));
    }

    private Resources.Entry filterEntryForDensity(ResourceTableEntry tableEntry, Targeting.ScreenDensity.DensityAlias targetDensity) {
        Predicate<Resources.ConfigValue> pinConfigToMaster;
        Resources.Entry initialEntry = tableEntry.getEntry();
        Map<ConfigurationOuterClass.Configuration, List<Resources.ConfigValue>> configValuesByConfiguration = initialEntry.getConfigValueList().stream().filter(configValue -> VersionGuardedFeature.RESOURCES_WITH_NO_ALTERNATIVES_IN_MASTER_SPLIT.enabledForVersion(this.bundleVersion) || configValue.getConfig().getDensity() != 0).collect(Collectors.groupingBy(configValue -> ScreenDensityResourcesSplitter.clearDensity(configValue.getConfig())));
        if (VersionGuardedFeature.RESOURCES_WITH_NO_ALTERNATIVES_IN_MASTER_SPLIT.enabledForVersion(this.bundleVersion)) {
            configValuesByConfiguration = Maps.filterValues(configValuesByConfiguration, configValues -> configValues.size() > 1);
        }
        ImmutableList<List<Resources.ConfigValue>> densityGroups = ImmutableList.copyOf(configValuesByConfiguration.values());
        if (this.pinWholeResourceToMaster.test(tableEntry.getResourceId())) {
            pinConfigToMaster = anyConfig -> true;
        } else if (this.pinLowestBucketOfResourceToMaster.test(tableEntry.getResourceId())) {
            ImmutableSet lowDensityConfigsPinnedToMaster = this.pickBestDensityForEachGroup(densityGroups, ResourcesUtils.getLowestDensity(this.densityBuckets)).collect(ImmutableSet.toImmutableSet());
            pinConfigToMaster = lowDensityConfigsPinnedToMaster::contains;
        } else {
            pinConfigToMaster = anyConfig -> false;
        }
        ImmutableList valuesToKeep = this.pickBestDensityForEachGroup(densityGroups, targetDensity).filter(config -> !pinConfigToMaster.test((Resources.ConfigValue)config)).collect(ImmutableList.toImmutableList());
        return initialEntry.toBuilder().clearConfigValue().addAllConfigValue(valuesToKeep).build();
    }

    private Stream<Resources.ConfigValue> pickBestDensityForEachGroup(ImmutableList<List<Resources.ConfigValue>> densityGroups, Targeting.ScreenDensity.DensityAlias densityAlias) {
        return densityGroups.stream().flatMap(group -> new ScreenDensitySelector().selectAllMatchingConfigValues(ImmutableList.copyOf(group), densityAlias, ScreenDensityResourcesSplitter.allBut(this.densityBuckets, densityAlias), this.bundleVersion).stream());
    }

    private static Set<Targeting.ScreenDensity.DensityAlias> allBut(ImmutableSet<Targeting.ScreenDensity.DensityAlias> splitByDensities, Targeting.ScreenDensity.DensityAlias densityAlias) {
        return Sets.difference(splitByDensities, ImmutableSet.of(densityAlias));
    }

    private static ConfigurationOuterClass.Configuration clearDensity(ConfigurationOuterClass.Configuration source) {
        return source.toBuilder().clearDensity().build();
    }
}

