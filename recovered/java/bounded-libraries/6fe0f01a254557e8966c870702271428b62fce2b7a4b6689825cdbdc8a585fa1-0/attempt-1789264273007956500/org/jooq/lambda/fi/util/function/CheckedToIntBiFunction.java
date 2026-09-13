/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.lambda.fi.util.function;

import java.util.function.Consumer;
import java.util.function.ToIntBiFunction;
import org.jooq.lambda.Sneaky;
import org.jooq.lambda.Unchecked;

@FunctionalInterface
public interface CheckedToIntBiFunction<T, U> {
    public int applyAsInt(T var1, U var2) throws Throwable;

    public static <T, U> ToIntBiFunction<T, U> sneaky(CheckedToIntBiFunction<T, U> function) {
        return Sneaky.toIntBiFunction(function);
    }

    public static <T, U> ToIntBiFunction<T, U> unchecked(CheckedToIntBiFunction<T, U> function) {
        return Unchecked.toIntBiFunction(function);
    }

    public static <T, U> ToIntBiFunction<T, U> unchecked(CheckedToIntBiFunction<T, U> function, Consumer<Throwable> handler) {
        return Unchecked.toIntBiFunction(function, handler);
    }
}

