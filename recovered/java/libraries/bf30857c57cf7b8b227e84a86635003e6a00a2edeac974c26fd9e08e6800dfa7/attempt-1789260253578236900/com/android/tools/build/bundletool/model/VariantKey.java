/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model;

import com.android.bundle.Targeting;
import com.android.tools.build.bundletool.model.AutoValue_VariantKey;
import com.android.tools.build.bundletool.model.ModuleSplit;
import com.android.tools.build.bundletool.model.targeting.TargetingComparators;
import com.google.auto.value.AutoValue;
import com.google.common.collect.Ordering;
import com.google.errorprone.annotations.Immutable;
import java.util.Comparator;

@Immutable
@AutoValue
@AutoValue.CopyAnnotations
public abstract class VariantKey
implements Comparable<VariantKey> {
    public static VariantKey create(ModuleSplit moduleSplit) {
        return new AutoValue_VariantKey(moduleSplit.getSplitType(), moduleSplit.getVariantTargeting());
    }

    public abstract ModuleSplit.SplitType getSplitType();

    public abstract Targeting.VariantTargeting getVariantTargeting();

    @Override
    public int compareTo(VariantKey o3) {
        return Comparator.comparing(VariantKey::getSplitType, Ordering.explicit(ModuleSplit.SplitType.INSTANT, ModuleSplit.SplitType.STANDALONE, ModuleSplit.SplitType.SPLIT, ModuleSplit.SplitType.SYSTEM)).thenComparing(VariantKey::getVariantTargeting, TargetingComparators.VARIANT_TARGETING_COMPARATOR).compare(this, o3);
    }
}

