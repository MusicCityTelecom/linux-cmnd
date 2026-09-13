/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.lambda.fi.util.function;

import java.util.function.Consumer;
import java.util.function.ToDoubleFunction;
import org.jooq.lambda.Sneaky;
import org.jooq.lambda.Unchecked;

@FunctionalInterface
public interface CheckedToDoubleFunction<T> {
    public double applyAsDouble(T var1) throws Throwable;

    public static <T> ToDoubleFunction<T> sneaky(CheckedToDoubleFunction<T> function) {
        return Sneaky.toDoubleFunction(function);
    }

    public static <T> ToDoubleFunction<T> unchecked(CheckedToDoubleFunction<T> function) {
        return Unchecked.toDoubleFunction(function);
    }

    public static <T> ToDoubleFunction<T> unchecked(CheckedToDoubleFunction<T> function, Consumer<Throwable> handler) {
        return Unchecked.toDoubleFunction(function, handler);
    }
}

