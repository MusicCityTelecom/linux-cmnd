/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.lambda.fi.util.function;

import java.util.function.Consumer;
import java.util.function.LongFunction;
import org.jooq.lambda.Sneaky;
import org.jooq.lambda.Unchecked;

@FunctionalInterface
public interface CheckedLongFunction<R> {
    public R apply(long var1) throws Throwable;

    public static <R> LongFunction<R> sneaky(CheckedLongFunction<R> function) {
        return Sneaky.longFunction(function);
    }

    public static <R> LongFunction<R> unchecked(CheckedLongFunction<R> function) {
        return Unchecked.longFunction(function);
    }

    public static <R> LongFunction<R> unchecked(CheckedLongFunction<R> function, Consumer<Throwable> handler) {
        return Unchecked.longFunction(function, handler);
    }
}

