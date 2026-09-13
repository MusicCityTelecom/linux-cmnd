/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model;

import com.android.bundle.Targeting;
import com.android.tools.build.bundletool.model.ApkModifier;

final class AutoValue_ApkModifier_ApkDescription
extends ApkModifier.ApkDescription {
    private final boolean base;
    private final ApkModifier.ApkDescription.ApkType apkType;
    private final int variantNumber;
    private final Targeting.VariantTargeting variantTargeting;
    private final Targeting.ApkTargeting apkTargeting;

    private AutoValue_ApkModifier_ApkDescription(boolean base, ApkModifier.ApkDescription.ApkType apkType, int variantNumber, Targeting.VariantTargeting variantTargeting, Targeting.ApkTargeting apkTargeting) {
        this.base = base;
        this.apkType = apkType;
        this.variantNumber = variantNumber;
        this.variantTargeting = variantTargeting;
        this.apkTargeting = apkTargeting;
    }

    @Override
    public boolean isBase() {
        return this.base;
    }

    @Override
    public ApkModifier.ApkDescription.ApkType getApkType() {
        return this.apkType;
    }

    @Override
    public int getVariantNumber() {
        return this.variantNumber;
    }

    @Override
    public Targeting.VariantTargeting getVariantTargeting() {
        return this.variantTargeting;
    }

    @Override
    public Targeting.ApkTargeting getApkTargeting() {
        return this.apkTargeting;
    }

    public String toString() {
        return "ApkDescription{base=" + this.base + ", apkType=" + (Object)((Object)this.apkType) + ", variantNumber=" + this.variantNumber + ", variantTargeting=" + this.variantTargeting + ", apkTargeting=" + this.apkTargeting + "}";
    }

    public boolean equals(Object o3) {
        if (o3 == this) {
            return true;
        }
        if (o3 instanceof ApkModifier.ApkDescription) {
            ApkModifier.ApkDescription that = (ApkModifier.ApkDescription)o3;
            return this.base == that.isBase() && this.apkType.equals((Object)that.getApkType()) && this.variantNumber == that.getVariantNumber() && this.variantTargeting.equals(that.getVariantTargeting()) && this.apkTargeting.equals(that.getApkTargeting());
        }
        return false;
    }

    public int hashCode() {
        int h$ = 1;
        h$ *= 1000003;
        h$ ^= this.base ? 1231 : 1237;
        h$ *= 1000003;
        h$ ^= this.apkType.hashCode();
        h$ *= 1000003;
        h$ ^= this.variantNumber;
        h$ *= 1000003;
        h$ ^= this.variantTargeting.hashCode();
        h$ *= 1000003;
        return h$ ^= this.apkTargeting.hashCode();
    }

    static final class Builder
    extends ApkModifier.ApkDescription.Builder {
        private Boolean base;
        private ApkModifier.ApkDescription.ApkType apkType;
        private Integer variantNumber;
        private Targeting.VariantTargeting variantTargeting;
        private Targeting.ApkTargeting apkTargeting;

        Builder() {
        }

        @Override
        public ApkModifier.ApkDescription.Builder setBase(boolean base) {
            this.base = base;
            return this;
        }

        @Override
        public ApkModifier.ApkDescription.Builder setApkType(ApkModifier.ApkDescription.ApkType apkType) {
            if (apkType == null) {
                throw new NullPointerException("Null apkType");
            }
            this.apkType = apkType;
            return this;
        }

        @Override
        public ApkModifier.ApkDescription.Builder setVariantNumber(int variantNumber) {
            this.variantNumber = variantNumber;
            return this;
        }

        @Override
        public ApkModifier.ApkDescription.Builder setVariantTargeting(Targeting.VariantTargeting variantTargeting) {
            if (variantTargeting == null) {
                throw new NullPointerException("Null variantTargeting");
            }
            this.variantTargeting = variantTargeting;
            return this;
        }

        @Override
        public ApkModifier.ApkDescription.Builder setApkTargeting(Targeting.ApkTargeting apkTargeting) {
            if (apkTargeting == null) {
                throw new NullPointerException("Null apkTargeting");
            }
            this.apkTargeting = apkTargeting;
            return this;
        }

        @Override
        public ApkModifier.ApkDescription build() {
            String missing = "";
            if (this.base == null) {
                missing = missing + " base";
            }
            if (this.apkType == null) {
                missing = missing + " apkType";
            }
            if (this.variantNumber == null) {
                missing = missing + " variantNumber";
            }
            if (this.variantTargeting == null) {
                missing = missing + " variantTargeting";
            }
            if (this.apkTargeting == null) {
                missing = missing + " apkTargeting";
            }
            if (!missing.isEmpty()) {
                throw new IllegalStateException("Missing required properties:" + missing);
            }
            return new AutoValue_ApkModifier_ApkDescription(this.base, this.apkType, this.variantNumber, this.variantTargeting, this.apkTargeting);
        }
    }
}

