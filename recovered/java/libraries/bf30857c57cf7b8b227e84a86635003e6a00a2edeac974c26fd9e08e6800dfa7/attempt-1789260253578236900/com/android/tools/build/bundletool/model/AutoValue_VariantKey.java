/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model;

import com.android.bundle.Targeting;
import com.android.tools.build.bundletool.model.ModuleSplit;
import com.android.tools.build.bundletool.model.VariantKey;

final class AutoValue_VariantKey
extends VariantKey {
    private final ModuleSplit.SplitType splitType;
    private final Targeting.VariantTargeting variantTargeting;

    AutoValue_VariantKey(ModuleSplit.SplitType splitType, Targeting.VariantTargeting variantTargeting) {
        if (splitType == null) {
            throw new NullPointerException("Null splitType");
        }
        this.splitType = splitType;
        if (variantTargeting == null) {
            throw new NullPointerException("Null variantTargeting");
        }
        this.variantTargeting = variantTargeting;
    }

    @Override
    public ModuleSplit.SplitType getSplitType() {
        return this.splitType;
    }

    @Override
    public Targeting.VariantTargeting getVariantTargeting() {
        return this.variantTargeting;
    }

    public String toString() {
        return "VariantKey{splitType=" + (Object)((Object)this.splitType) + ", variantTargeting=" + this.variantTargeting + "}";
    }

    public boolean equals(Object o3) {
        if (o3 == this) {
            return true;
        }
        if (o3 instanceof VariantKey) {
            VariantKey that = (VariantKey)o3;
            return this.splitType.equals((Object)that.getSplitType()) && this.variantTargeting.equals(that.getVariantTargeting());
        }
        return false;
    }

    public int hashCode() {
        int h$ = 1;
        h$ *= 1000003;
        h$ ^= this.splitType.hashCode();
        h$ *= 1000003;
        return h$ ^= this.variantTargeting.hashCode();
    }
}

