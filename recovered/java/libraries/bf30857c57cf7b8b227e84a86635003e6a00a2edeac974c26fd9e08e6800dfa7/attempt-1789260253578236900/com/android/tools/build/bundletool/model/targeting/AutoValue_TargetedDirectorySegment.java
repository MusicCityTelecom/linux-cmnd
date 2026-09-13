/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model.targeting;

import com.android.bundle.Targeting;
import com.android.tools.build.bundletool.model.targeting.TargetedDirectorySegment;

final class AutoValue_TargetedDirectorySegment
extends TargetedDirectorySegment {
    private final String name;
    private final Targeting.AssetsDirectoryTargeting targeting;

    AutoValue_TargetedDirectorySegment(String name, Targeting.AssetsDirectoryTargeting targeting) {
        if (name == null) {
            throw new NullPointerException("Null name");
        }
        this.name = name;
        if (targeting == null) {
            throw new NullPointerException("Null targeting");
        }
        this.targeting = targeting;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Targeting.AssetsDirectoryTargeting getTargeting() {
        return this.targeting;
    }

    public String toString() {
        return "TargetedDirectorySegment{name=" + this.name + ", targeting=" + this.targeting + "}";
    }

    public boolean equals(Object o3) {
        if (o3 == this) {
            return true;
        }
        if (o3 instanceof TargetedDirectorySegment) {
            TargetedDirectorySegment that = (TargetedDirectorySegment)o3;
            return this.name.equals(that.getName()) && this.targeting.equals(that.getTargeting());
        }
        return false;
    }

    public int hashCode() {
        int h$ = 1;
        h$ *= 1000003;
        h$ ^= this.name.hashCode();
        h$ *= 1000003;
        return h$ ^= this.targeting.hashCode();
    }
}

