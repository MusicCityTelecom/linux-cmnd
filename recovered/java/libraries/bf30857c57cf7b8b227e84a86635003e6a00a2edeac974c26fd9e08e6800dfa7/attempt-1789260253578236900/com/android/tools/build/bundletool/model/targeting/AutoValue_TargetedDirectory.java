/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model.targeting;

import com.android.tools.build.bundletool.model.ZipPath;
import com.android.tools.build.bundletool.model.targeting.TargetedDirectory;
import com.android.tools.build.bundletool.model.targeting.TargetedDirectorySegment;
import com.google.common.collect.ImmutableList;

final class AutoValue_TargetedDirectory
extends TargetedDirectory {
    private final ImmutableList<TargetedDirectorySegment> getPathSegments;
    private final ZipPath originalPath;

    AutoValue_TargetedDirectory(ImmutableList<TargetedDirectorySegment> getPathSegments, ZipPath originalPath) {
        if (getPathSegments == null) {
            throw new NullPointerException("Null getPathSegments");
        }
        this.getPathSegments = getPathSegments;
        if (originalPath == null) {
            throw new NullPointerException("Null originalPath");
        }
        this.originalPath = originalPath;
    }

    @Override
    public ImmutableList<TargetedDirectorySegment> getPathSegments() {
        return this.getPathSegments;
    }

    @Override
    public ZipPath originalPath() {
        return this.originalPath;
    }

    public String toString() {
        return "TargetedDirectory{getPathSegments=" + this.getPathSegments + ", originalPath=" + this.originalPath + "}";
    }

    public boolean equals(Object o3) {
        if (o3 == this) {
            return true;
        }
        if (o3 instanceof TargetedDirectory) {
            TargetedDirectory that = (TargetedDirectory)o3;
            return this.getPathSegments.equals(that.getPathSegments()) && this.originalPath.equals(that.originalPath());
        }
        return false;
    }

    public int hashCode() {
        int h$ = 1;
        h$ *= 1000003;
        h$ ^= this.getPathSegments.hashCode();
        h$ *= 1000003;
        return h$ ^= this.originalPath.hashCode();
    }
}

