/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.lambda.fi.util.function;

import java.util.function.Consumer;
import java.util.function.ToIntFunction;
import org.jooq.lambda.Sneaky;
import org.jooq.lambda.Unchecked;

@FunctionalInterface
public interface CheckedToIntFunction<T> {
    public int applyAsInt(T var1) throws Throwable;

    public static <T> ToIntFunction<T> sneaky(CheckedToIntFunction<T> function) {
        return Sneaky.toIntFunction(function);
    }

    public static <T> ToIntFunction<T> unchecked(CheckedToIntFunction<T> function) {
        return Unchecked.toIntFunction(function);
    }

    public static <T> ToIntFunction<T> unchecked(CheckedToIntFunction<T> function, Consumer<Throwable> handler) {
        return Unchecked.toIntFunction(function, handler);
    }
}

