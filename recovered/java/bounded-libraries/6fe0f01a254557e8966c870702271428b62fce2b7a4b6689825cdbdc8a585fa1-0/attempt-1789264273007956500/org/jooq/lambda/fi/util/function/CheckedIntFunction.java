/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.lambda.fi.util.function;

import java.util.function.Consumer;
import java.util.function.IntFunction;
import org.jooq.lambda.Sneaky;
import org.jooq.lambda.Unchecked;

@FunctionalInterface
public interface CheckedIntFunction<R> {
    public R apply(int var1) throws Throwable;

    public static <R> IntFunction<R> sneaky(CheckedIntFunction<R> function) {
        return Sneaky.intFunction(function);
    }

    public static <R> IntFunction<R> unchecked(CheckedIntFunction<R> function) {
        return Unchecked.intFunction(function);
    }

    public static <R> IntFunction<R> unchecked(CheckedIntFunction<R> function, Consumer<Throwable> handler) {
        return Unchecked.intFunction(function, handler);
    }
}

