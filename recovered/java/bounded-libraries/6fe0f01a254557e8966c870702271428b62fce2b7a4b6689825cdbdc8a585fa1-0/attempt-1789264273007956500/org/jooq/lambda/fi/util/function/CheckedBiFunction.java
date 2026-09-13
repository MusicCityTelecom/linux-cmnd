/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.lambda.fi.util.function;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import org.jooq.lambda.Sneaky;
import org.jooq.lambda.Unchecked;

@FunctionalInterface
public interface CheckedBiFunction<T, U, R> {
    public R apply(T var1, U var2) throws Throwable;

    public static <T, U, R> BiFunction<T, U, R> sneaky(CheckedBiFunction<T, U, R> function) {
        return Sneaky.biFunction(function);
    }

    public static <T, U, R> BiFunction<T, U, R> unchecked(CheckedBiFunction<T, U, R> function) {
        return Unchecked.biFunction(function);
    }

    public static <T, U, R> BiFunction<T, U, R> unchecked(CheckedBiFunction<T, U, R> function, Consumer<Throwable> handler) {
        return Unchecked.biFunction(function, handler);
    }
}

