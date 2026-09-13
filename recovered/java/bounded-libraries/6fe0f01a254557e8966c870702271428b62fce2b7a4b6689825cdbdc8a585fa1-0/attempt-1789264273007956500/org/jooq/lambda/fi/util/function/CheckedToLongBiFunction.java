/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.lambda.fi.util.function;

import java.util.function.Consumer;
import java.util.function.ToLongBiFunction;
import org.jooq.lambda.Sneaky;
import org.jooq.lambda.Unchecked;

@FunctionalInterface
public interface CheckedToLongBiFunction<T, U> {
    public long applyAsLong(T var1, U var2) throws Throwable;

    public static <T, U> ToLongBiFunction<T, U> sneaky(CheckedToLongBiFunction<T, U> function) {
        return Sneaky.toLongBiFunction(function);
    }

    public static <T, U> ToLongBiFunction<T, U> unchecked(CheckedToLongBiFunction<T, U> function) {
        return Unchecked.toLongBiFunction(function);
    }

    public static <T, U> ToLongBiFunction<T, U> unchecked(CheckedToLongBiFunction<T, U> function, Consumer<Throwable> handler) {
        return Unchecked.toLongBiFunction(function, handler);
    }
}

