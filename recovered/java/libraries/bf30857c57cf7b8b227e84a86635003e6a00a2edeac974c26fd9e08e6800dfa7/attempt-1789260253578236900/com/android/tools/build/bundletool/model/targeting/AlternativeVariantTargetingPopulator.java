/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model.targeting;

import com.android.bundle.Targeting;
import com.android.tools.build.bundletool.model.GeneratedApks;
import com.android.tools.build.bundletool.model.ModuleSplit;
import com.android.tools.build.bundletool.model.utils.TargetingProtoUtils;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import com.google.protobuf.Message;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import javax.annotation.CheckReturnValue;

public abstract class AlternativeVariantTargetingPopulator<T extends Message> {
    public static GeneratedApks populateAlternativeVariantTargeting(GeneratedApks generatedApks, int maxSdkVersion) {
        return AlternativeVariantTargetingPopulator.populateAlternativeVariantTargeting(generatedApks, Optional.of(maxSdkVersion));
    }

    public static GeneratedApks populateAlternativeVariantTargeting(GeneratedApks generatedApks) {
        return AlternativeVariantTargetingPopulator.populateAlternativeVariantTargeting(generatedApks, Optional.empty());
    }

    public static GeneratedApks populateAlternativeVariantTargeting(GeneratedApks generatedApks, Optional<Integer> maxSdkVersion) {
        ImmutableList<ModuleSplit> standaloneApks = new AbiAlternativesPopulator().addAlternativeVariantTargeting(generatedApks.getStandaloneApks());
        standaloneApks = new ScreenDensityAlternativesPopulator().addAlternativeVariantTargeting(standaloneApks);
        ImmutableCollection moduleSplits = ((ImmutableList.Builder)((ImmutableList.Builder)((ImmutableList.Builder)ImmutableList.builder().addAll(new SdkVersionAlternativesPopulator(maxSdkVersion).addAlternativeVariantTargeting(generatedApks.getSplitApks(), standaloneApks))).addAll(generatedApks.getInstantApks())).addAll(generatedApks.getSystemApks())).build();
        return GeneratedApks.fromModuleSplits((ImmutableList<ModuleSplit>)moduleSplits);
    }

    @CheckReturnValue
    ImmutableList<ModuleSplit> addAlternativeVariantTargeting(ImmutableList<ModuleSplit> ... splits) {
        return this.addAlternativeVariantTargeting(Arrays.stream(splits).flatMap(Collection::stream).collect(ImmutableList.toImmutableList()));
    }

    @CheckReturnValue
    ImmutableList<ModuleSplit> addAlternativeVariantTargeting(ImmutableList<ModuleSplit> apks) {
        ImmutableList<Targeting.VariantTargeting> variantTargeting = apks.stream().map(ModuleSplit::getVariantTargeting).collect(ImmutableList.toImmutableList());
        Preconditions.checkState((variantTargeting = this.addAlternativeVariantTargetingInternal(variantTargeting)).size() == apks.size());
        ImmutableList.Builder result = ImmutableList.builder();
        for (int i2 = 0; i2 < apks.size(); ++i2) {
            result.add(((ModuleSplit)apks.get(i2)).toBuilder().setVariantTargeting((Targeting.VariantTargeting)variantTargeting.get(i2)).build());
        }
        return result.build();
    }

    @CheckReturnValue
    ImmutableList<Targeting.VariantTargeting> addAlternativeVariantTargetingInternal(ImmutableList<Targeting.VariantTargeting> variantTargetings) {
        ImmutableSet dimensionIsTargeted = variantTargetings.stream().map(variantTargeting -> !this.getValues((Targeting.VariantTargeting)variantTargeting).isEmpty()).collect(ImmutableSet.toImmutableSet());
        Preconditions.checkArgument(dimensionIsTargeted.size() <= 1, "Some variants are agnostic to the dimension, and some are not.");
        if (variantTargetings.isEmpty() || !((Boolean)Iterables.getOnlyElement(dimensionIsTargeted)).booleanValue()) {
            return variantTargetings;
        }
        ImmutableSet allValues = variantTargetings.stream().flatMap(variantTargeting -> this.getValues((Targeting.VariantTargeting)variantTargeting).stream()).collect(ImmutableSet.toImmutableSet());
        return variantTargetings.stream().map(variantTargeting -> {
            Targeting.VariantTargeting.Builder result = variantTargeting.toBuilder();
            this.setDimensionAlternatives(result, ImmutableSet.copyOf(Sets.difference(allValues, ImmutableSet.copyOf(this.getValues((Targeting.VariantTargeting)variantTargeting)))));
            return result.build();
        }).collect(ImmutableList.toImmutableList());
    }

    protected abstract ImmutableList<T> getValues(Targeting.VariantTargeting var1);

    protected abstract void setDimensionAlternatives(Targeting.VariantTargeting.Builder var1, ImmutableCollection<T> var2);

    @VisibleForTesting
    static class SdkVersionAlternativesPopulator
    extends AlternativeVariantTargetingPopulator<Targeting.SdkVersion> {
        private final Optional<Integer> maxSdkVersion;

        public SdkVersionAlternativesPopulator() {
            this(Optional.empty());
        }

        public SdkVersionAlternativesPopulator(Optional<Integer> maxSdkVersion) {
            this.maxSdkVersion = maxSdkVersion;
        }

        @Override
        protected ImmutableList<Targeting.SdkVersion> getValues(Targeting.VariantTargeting targeting) {
            return ImmutableList.copyOf(targeting.getSdkVersionTargeting().getValueList());
        }

        @Override
        protected void setDimensionAlternatives(Targeting.VariantTargeting.Builder targetingBuilder, ImmutableCollection<Targeting.SdkVersion> alternatives) {
            targetingBuilder.getSdkVersionTargetingBuilder().clearAlternatives().addAllAlternatives(alternatives);
        }

        @Override
        @CheckReturnValue
        ImmutableList<Targeting.VariantTargeting> addAlternativeVariantTargetingInternal(ImmutableList<Targeting.VariantTargeting> variantTargetings) {
            ImmutableList<Targeting.VariantTargeting> variantsWithoutSentinel = super.addAlternativeVariantTargetingInternal(variantTargetings);
            if (!this.maxSdkVersion.isPresent()) {
                return variantsWithoutSentinel;
            }
            return variantsWithoutSentinel.stream().map(targeting -> SdkVersionAlternativesPopulator.addSentinelVariantTargeting(targeting, this.maxSdkVersion.get())).collect(ImmutableList.toImmutableList());
        }

        private static Targeting.VariantTargeting addSentinelVariantTargeting(Targeting.VariantTargeting targeting, int maxSdkVersion) {
            Targeting.SdkVersionTargeting sdkTargeting = targeting.getSdkVersionTargeting();
            return targeting.toBuilder().setSdkVersionTargeting(sdkTargeting.toBuilder().addAlternatives(TargetingProtoUtils.sdkVersionFrom(maxSdkVersion + 1))).build();
        }
    }

    @VisibleForTesting
    static class ScreenDensityAlternativesPopulator
    extends AlternativeVariantTargetingPopulator<Targeting.ScreenDensity> {
        ScreenDensityAlternativesPopulator() {
        }

        @Override
        protected ImmutableList<Targeting.ScreenDensity> getValues(Targeting.VariantTargeting targeting) {
            return ImmutableList.copyOf(targeting.getScreenDensityTargeting().getValueList());
        }

        @Override
        protected void setDimensionAlternatives(Targeting.VariantTargeting.Builder targetingBuilder, ImmutableCollection<Targeting.ScreenDensity> alternatives) {
            targetingBuilder.getScreenDensityTargetingBuilder().clearAlternatives().addAllAlternatives(alternatives);
        }
    }

    @VisibleForTesting
    static class AbiAlternativesPopulator
    extends AlternativeVariantTargetingPopulator<Targeting.Abi> {
        AbiAlternativesPopulator() {
        }

        @Override
        protected ImmutableList<Targeting.Abi> getValues(Targeting.VariantTargeting targeting) {
            return ImmutableList.copyOf(targeting.getAbiTargeting().getValueList());
        }

        @Override
        protected void setDimensionAlternatives(Targeting.VariantTargeting.Builder targetingBuilder, ImmutableCollection<Targeting.Abi> alternatives) {
            targetingBuilder.getAbiTargetingBuilder().clearAlternatives().addAllAlternatives(alternatives);
        }
    }
}

