/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.lambda.fi.util.function;

import java.util.function.Consumer;
import java.util.function.Function;
import org.jooq.lambda.Sneaky;
import org.jooq.lambda.Unchecked;

@FunctionalInterface
public interface CheckedFunction<T, R> {
    public R apply(T var1) throws Throwable;

    public static <T, R> Function<T, R> sneaky(CheckedFunction<T, R> function) {
        return Sneaky.function(function);
    }

    public static <T, R> Function<T, R> unchecked(CheckedFunction<T, R> function) {
        return Unchecked.function(function);
    }

    public static <T, R> Function<T, R> unchecked(CheckedFunction<T, R> function, Consumer<Throwable> handler) {
        return Unchecked.function(function, handler);
    }
}

