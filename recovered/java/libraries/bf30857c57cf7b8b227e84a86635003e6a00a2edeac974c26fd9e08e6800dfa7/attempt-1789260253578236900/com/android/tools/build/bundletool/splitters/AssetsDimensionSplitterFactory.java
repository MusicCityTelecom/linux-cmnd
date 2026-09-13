/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.splitters;

import com.android.bundle.Files;
import com.android.bundle.Targeting;
import com.android.tools.build.bundletool.model.ManifestMutator;
import com.android.tools.build.bundletool.model.ModuleEntry;
import com.android.tools.build.bundletool.model.ModuleSplit;
import com.android.tools.build.bundletool.model.ZipPath;
import com.android.tools.build.bundletool.model.targeting.TargetingDimension;
import com.android.tools.build.bundletool.model.targeting.TargetingUtils;
import com.android.tools.build.bundletool.splitters.ModuleSplitSplitter;
import com.android.utils.ImmutableCollectors;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimaps;
import com.google.protobuf.Message;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Predicate;

public class AssetsDimensionSplitterFactory {
    public static <T extends Message> ModuleSplitSplitter createSplitter(Function<Targeting.AssetsDirectoryTargeting, T> dimensionGetter, Function<T, Targeting.ApkTargeting> targetingSetter, Predicate<Targeting.ApkTargeting> hasTargeting) {
        return AssetsDimensionSplitterFactory.createSplitter(dimensionGetter, targetingSetter, hasTargeting, Optional.empty());
    }

    public static <T extends Message> ModuleSplitSplitter createSplitter(final Function<Targeting.AssetsDirectoryTargeting, T> dimensionGetter, final Function<T, Targeting.ApkTargeting> targetingSetter, final Predicate<Targeting.ApkTargeting> hasTargeting, final Optional<TargetingDimension> targetingDimensionToRemove) {
        return new ModuleSplitSplitter(){

            @Override
            public ImmutableCollection<ModuleSplit> split(ModuleSplit split) {
                Preconditions.checkArgument(!hasTargeting.test(split.getApkTargeting()), "Split is already targeting the splitting dimension.");
                return split.getAssetsConfig().map(assetsConfig -> this.splitAssetsDirectories((Files.Assets)assetsConfig, split)).orElse(ImmutableList.of(split)).stream().map(moduleSplit -> moduleSplit.isMasterSplit() ? moduleSplit : this.removeAssetsTargeting((ModuleSplit)moduleSplit)).collect(ImmutableList.toImmutableList());
            }

            private ModuleSplit removeAssetsTargeting(ModuleSplit split) {
                return targetingDimensionToRemove.isPresent() ? TargetingUtils.removeAssetsTargeting(split, (TargetingDimension)((Object)targetingDimensionToRemove.get())) : split;
            }

            private ImmutableList<ModuleSplit> splitAssetsDirectories(Files.Assets assets, ModuleSplit split) {
                ListMultimap directoriesMap = Multimaps.filterKeys(Multimaps.index(assets.getDirectoryList(), targetedDirectory -> (Message)dimensionGetter.apply(targetedDirectory.getTargeting())), Predicates.not(this::isDefaultTargeting));
                ImmutableList.Builder splitsBuilder = new ImmutableList.Builder();
                directoriesMap.asMap().entrySet().forEach(entry -> {
                    ImmutableList<ModuleEntry> entries = this.listEntriesFromDirectories((Collection)entry.getValue(), split);
                    if (entries.isEmpty()) {
                        return;
                    }
                    ModuleSplit.Builder modifiedSplit = split.toBuilder();
                    modifiedSplit.setEntries(entries).setApkTargeting(this.generateTargeting(split.getApkTargeting(), (Message)entry.getKey())).setMasterSplit(false).addMasterManifestMutator(ManifestMutator.withSplitsRequired(true));
                    splitsBuilder.add(modifiedSplit.build());
                });
                ModuleSplit defaultSplit = this.getDefaultAssetsSplit(split, (ImmutableList<ModuleSplit>)splitsBuilder.build());
                if (defaultSplit.isMasterSplit() || !defaultSplit.getEntries().isEmpty()) {
                    splitsBuilder.add(defaultSplit);
                }
                return splitsBuilder.build();
            }

            private ModuleSplit getDefaultAssetsSplit(ModuleSplit inputSplit, ImmutableList<ModuleSplit> configSplits) {
                ImmutableSet claimedEntries = configSplits.stream().map(ModuleSplit::getEntries).flatMap(Collection::stream).collect(ImmutableCollectors.toImmutableSet());
                return inputSplit.toBuilder().setEntries(inputSplit.getEntries().stream().filter(Predicates.not(claimedEntries::contains)).collect(ImmutableList.toImmutableList())).build();
            }

            private boolean isDefaultTargeting(T splittingDimensionTargeting) {
                return splittingDimensionTargeting.equals(splittingDimensionTargeting.getDefaultInstanceForType());
            }

            private Targeting.ApkTargeting generateTargeting(Targeting.ApkTargeting splitTargeting, T extraTargeting) {
                if (this.isDefaultTargeting(extraTargeting)) {
                    return splitTargeting;
                }
                return splitTargeting.toBuilder().mergeFrom((Targeting.ApkTargeting)targetingSetter.apply(extraTargeting)).build();
            }

            private ImmutableList<ModuleEntry> listEntriesFromDirectories(Collection<Files.TargetedAssetsDirectory> directories, ModuleSplit moduleSplit) {
                return directories.stream().map(targetedAssetsDirectory -> ZipPath.create(targetedAssetsDirectory.getPath())).flatMap(moduleSplit::getEntriesInDirectory).collect(ImmutableList.toImmutableList());
            }
        };
    }

    private AssetsDimensionSplitterFactory() {
    }
}

