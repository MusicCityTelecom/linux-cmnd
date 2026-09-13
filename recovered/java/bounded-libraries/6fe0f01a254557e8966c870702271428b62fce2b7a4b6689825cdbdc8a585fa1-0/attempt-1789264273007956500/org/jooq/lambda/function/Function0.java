/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.lambda.function;

import java.util.function.Supplier;
import org.jooq.lambda.tuple.Tuple0;

@FunctionalInterface
public interface Function0<R>
extends Supplier<R> {
    default public R apply() {
        return this.get();
    }

    default public R apply(Tuple0 args) {
        return this.get();
    }

    @Override
    public R get();

    default public Supplier<R> toSupplier() {
        return this::apply;
    }

    public static <R> Function0<R> from(Supplier<R> supplier) {
        return supplier::get;
    }
}

