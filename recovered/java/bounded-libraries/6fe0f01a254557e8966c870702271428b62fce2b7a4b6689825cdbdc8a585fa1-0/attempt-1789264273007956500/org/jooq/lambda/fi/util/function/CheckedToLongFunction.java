/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.lambda.fi.util.function;

import java.util.function.Consumer;
import java.util.function.ToLongFunction;
import org.jooq.lambda.Sneaky;
import org.jooq.lambda.Unchecked;

@FunctionalInterface
public interface CheckedToLongFunction<T> {
    public long applyAsLong(T var1) throws Throwable;

    public static <T> ToLongFunction<T> sneaky(CheckedToLongFunction<T> function) {
        return Sneaky.toLongFunction(function);
    }

    public static <T> ToLongFunction<T> unchecked(CheckedToLongFunction<T> function) {
        return Unchecked.toLongFunction(function);
    }

    public static <T> ToLongFunction<T> unchecked(CheckedToLongFunction<T> function, Consumer<Throwable> handler) {
        return Unchecked.toLongFunction(function, handler);
    }
}

