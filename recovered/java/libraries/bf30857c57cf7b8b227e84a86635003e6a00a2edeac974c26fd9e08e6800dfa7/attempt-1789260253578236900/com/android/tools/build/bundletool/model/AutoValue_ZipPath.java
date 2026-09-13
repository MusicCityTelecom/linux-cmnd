/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model;

import com.android.tools.build.bundletool.model.$AutoValue_ZipPath;
import com.android.tools.build.bundletool.model.ZipPath;
import com.google.common.collect.ImmutableList;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;

final class AutoValue_ZipPath
extends $AutoValue_ZipPath {
    private volatile ZipPath getParent;
    private volatile boolean getParent$Memoized;
    private volatile ZipPath getFileName;

    AutoValue_ZipPath(ImmutableList<String> names$) {
        super(names$);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    @Nullable
    @CheckReturnValue
    public ZipPath getParent() {
        if (!this.getParent$Memoized) {
            AutoValue_ZipPath autoValue_ZipPath = this;
            synchronized (autoValue_ZipPath) {
                if (!this.getParent$Memoized) {
                    this.getParent = super.getParent();
                    this.getParent$Memoized = true;
                }
            }
        }
        return this.getParent;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public ZipPath getFileName() {
        if (this.getFileName == null) {
            AutoValue_ZipPath autoValue_ZipPath = this;
            synchronized (autoValue_ZipPath) {
                if (this.getFileName == null) {
                    this.getFileName = super.getFileName();
                    if (this.getFileName == null) {
                        throw new NullPointerException("getFileName() cannot return null");
                    }
                }
            }
        }
        return this.getFileName;
    }
}

