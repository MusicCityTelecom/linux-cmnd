/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model;

import com.android.tools.build.bundletool.model.AutoValue_GeneratedApks;
import com.android.tools.build.bundletool.model.ModuleSplit;
import com.android.tools.build.bundletool.model.VariantKey;
import com.android.tools.build.bundletool.model.utils.CollectorUtils;
import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.errorprone.annotations.Immutable;
import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Immutable
@AutoValue
@AutoValue.CopyAnnotations
public abstract class GeneratedApks {
    public abstract ImmutableList<ModuleSplit> getInstantApks();

    public abstract ImmutableList<ModuleSplit> getSplitApks();

    public abstract ImmutableList<ModuleSplit> getStandaloneApks();

    public abstract ImmutableList<ModuleSplit> getSystemApks();

    public int size() {
        return this.getInstantApks().size() + this.getSplitApks().size() + this.getStandaloneApks().size() + this.getSystemApks().size();
    }

    public Stream<ModuleSplit> getAllApksStream() {
        return Stream.of(this.getStandaloneApks(), this.getInstantApks(), this.getSplitApks(), this.getSystemApks()).flatMap(Collection::stream);
    }

    public ImmutableListMultimap<VariantKey, ModuleSplit> getAllApksGroupedByOrderedVariants() {
        return this.getAllApksStream().collect(CollectorUtils.groupingBySortedKeys(VariantKey::create, Function.identity()));
    }

    public static Builder builder() {
        return new AutoValue_GeneratedApks.Builder().setInstantApks(ImmutableList.of()).setSplitApks(ImmutableList.of()).setStandaloneApks(ImmutableList.of()).setSystemApks(ImmutableList.of());
    }

    public static GeneratedApks fromModuleSplits(ImmutableList<ModuleSplit> moduleSplits) {
        Map groups2 = moduleSplits.stream().collect(Collectors.groupingBy(ModuleSplit::getSplitType, ImmutableList.toImmutableList()));
        return GeneratedApks.builder().setInstantApks(groups2.getOrDefault((Object)ModuleSplit.SplitType.INSTANT, ImmutableList.of())).setSplitApks(groups2.getOrDefault((Object)ModuleSplit.SplitType.SPLIT, ImmutableList.of())).setStandaloneApks(groups2.getOrDefault((Object)ModuleSplit.SplitType.STANDALONE, ImmutableList.of())).setSystemApks(groups2.getOrDefault((Object)ModuleSplit.SplitType.SYSTEM, ImmutableList.of())).build();
    }

    @AutoValue.Builder
    public static abstract class Builder {
        public abstract Builder setInstantApks(ImmutableList<ModuleSplit> var1);

        public abstract Builder setSplitApks(ImmutableList<ModuleSplit> var1);

        public abstract Builder setStandaloneApks(ImmutableList<ModuleSplit> var1);

        public abstract Builder setSystemApks(ImmutableList<ModuleSplit> var1);

        public abstract GeneratedApks build();
    }
}

