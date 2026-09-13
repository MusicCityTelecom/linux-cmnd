/*
 * Decompiled with CFR 0.152.
 */
package com.github.benmanes.caffeine.cache;

import com.github.benmanes.caffeine.cache.DisabledBuffer;
import java.util.function.Consumer;

interface Buffer<E> {
    public static final int FULL = 1;
    public static final int FAILED = -1;
    public static final int SUCCESS = 0;

    public static <E> Buffer<E> disabled() {
        return DisabledBuffer.INSTANCE;
    }

    public int offer(E var1);

    public void drainTo(Consumer<E> var1);

    default public long size() {
        return this.writes() - this.reads();
    }

    public long reads();

    public long writes();
}

