/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model;

import com.android.tools.build.bundletool.model.ZipPath;
import com.google.common.collect.ImmutableList;

abstract class $AutoValue_ZipPath
extends ZipPath {
    private final ImmutableList<String> names;

    $AutoValue_ZipPath(ImmutableList<String> names) {
        if (names == null) {
            throw new NullPointerException("Null names");
        }
        this.names = names;
    }

    @Override
    public ImmutableList<String> getNames() {
        return this.names;
    }

    public boolean equals(Object o3) {
        if (o3 == this) {
            return true;
        }
        if (o3 instanceof ZipPath) {
            ZipPath that = (ZipPath)o3;
            return this.names.equals(that.getNames());
        }
        return false;
    }

    public int hashCode() {
        int h$ = 1;
        h$ *= 1000003;
        return h$ ^= this.names.hashCode();
    }
}

