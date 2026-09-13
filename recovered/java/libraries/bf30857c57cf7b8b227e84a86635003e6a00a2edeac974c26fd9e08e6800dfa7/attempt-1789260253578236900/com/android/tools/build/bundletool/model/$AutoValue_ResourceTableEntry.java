/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model;

import com.android.aapt.Resources;
import com.android.tools.build.bundletool.model.ResourceTableEntry;

abstract class $AutoValue_ResourceTableEntry
extends ResourceTableEntry {
    private final Resources.Package package0;
    private final Resources.Type type;
    private final Resources.Entry entry;

    $AutoValue_ResourceTableEntry(Resources.Package package0, Resources.Type type, Resources.Entry entry) {
        if (package0 == null) {
            throw new NullPointerException("Null package");
        }
        this.package0 = package0;
        if (type == null) {
            throw new NullPointerException("Null type");
        }
        this.type = type;
        if (entry == null) {
            throw new NullPointerException("Null entry");
        }
        this.entry = entry;
    }

    @Override
    public Resources.Package getPackage() {
        return this.package0;
    }

    @Override
    public Resources.Type getType() {
        return this.type;
    }

    @Override
    public Resources.Entry getEntry() {
        return this.entry;
    }

    public String toString() {
        return "ResourceTableEntry{package=" + this.package0 + ", type=" + this.type + ", entry=" + this.entry + "}";
    }

    public boolean equals(Object o3) {
        if (o3 == this) {
            return true;
        }
        if (o3 instanceof ResourceTableEntry) {
            ResourceTableEntry that = (ResourceTableEntry)o3;
            return this.package0.equals(that.getPackage()) && this.type.equals(that.getType()) && this.entry.equals(that.getEntry());
        }
        return false;
    }

    public int hashCode() {
        int h$ = 1;
        h$ *= 1000003;
        h$ ^= this.package0.hashCode();
        h$ *= 1000003;
        h$ ^= this.type.hashCode();
        h$ *= 1000003;
        return h$ ^= this.entry.hashCode();
    }
}

