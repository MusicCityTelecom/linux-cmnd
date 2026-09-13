/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model;

import com.android.tools.build.bundletool.model.BundleModuleName;

final class AutoValue_BundleModuleName
extends BundleModuleName {
    private final String name;

    AutoValue_BundleModuleName(String name) {
        if (name == null) {
            throw new NullPointerException("Null name");
        }
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public boolean equals(Object o3) {
        if (o3 == this) {
            return true;
        }
        if (o3 instanceof BundleModuleName) {
            BundleModuleName that = (BundleModuleName)o3;
            return this.name.equals(that.getName());
        }
        return false;
    }

    public int hashCode() {
        int h$ = 1;
        h$ *= 1000003;
        return h$ ^= this.name.hashCode();
    }
}

