/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.lambda;

import java.util.Spliterator;

@FunctionalInterface
interface FunctionalSpliterator<T>
extends Spliterator<T> {
    @Override
    default public Spliterator<T> trySplit() {
        return null;
    }

    @Override
    default public long estimateSize() {
        return Long.MAX_VALUE;
    }

    @Override
    default public int characteristics() {
        return 16;
    }
}

