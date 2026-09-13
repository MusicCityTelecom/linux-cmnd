/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.apkzlib.utils;

import com.google.common.base.Supplier;

public class CachedSupplier<T> {
    private T cached;
    private boolean valid = false;
    private final Supplier<T> supplier;

    public CachedSupplier(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    public synchronized T get() {
        if (!this.valid) {
            this.cached = this.supplier.get();
            this.valid = true;
        }
        return this.cached;
    }

    public synchronized void reset() {
        this.cached = null;
        this.valid = false;
    }

    public synchronized void precomputed(T t3) {
        this.cached = t3;
        this.valid = true;
    }

    public synchronized boolean isValid() {
        return this.valid;
    }
}

