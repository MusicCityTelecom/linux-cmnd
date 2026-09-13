/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.lambda;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;
import org.jooq.lambda.tuple.Tuple2;

class Partition<T> {
    final List<Tuple2<T, Long>> list;
    final Map<Object, Object> cache;

    Partition(Collection<Tuple2<T, Long>> list) {
        this.list = list instanceof ArrayList ? (List<Object>)list : new ArrayList<Tuple2<T, Long>>(list);
        this.cache = new HashMap<Object, Object>();
    }

    <R> R cacheIf(boolean condition, Object key, Supplier<? extends R> value) {
        return this.cacheIf(() -> condition, () -> key, value);
    }

    <R> R cacheIf(boolean condition, Supplier<?> key, Supplier<? extends R> value) {
        if (condition) {
            return this.cache(key, value);
        }
        return value.get();
    }

    <R> R cacheIf(BooleanSupplier condition, Object key, Supplier<? extends R> value) {
        return this.cacheIf(condition, () -> key, value);
    }

    <R> R cacheIf(BooleanSupplier condition, Supplier<?> key, Supplier<? extends R> value) {
        if (condition.getAsBoolean()) {
            return this.cache(key, value);
        }
        return value.get();
    }

    <R> R cache(Object key, Supplier<? extends R> value) {
        return this.cache(() -> key, value);
    }

    <R> R cache(Supplier<?> key, Supplier<? extends R> value) {
        return (R)this.cache.computeIfAbsent(key.get(), k -> value.get());
    }
}

