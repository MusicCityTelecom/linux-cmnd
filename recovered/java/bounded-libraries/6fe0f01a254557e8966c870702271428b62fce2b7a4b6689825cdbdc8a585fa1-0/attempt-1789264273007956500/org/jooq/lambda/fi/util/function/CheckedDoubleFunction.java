/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.lambda.fi.util.function;

import java.util.function.Consumer;
import java.util.function.DoubleFunction;
import org.jooq.lambda.Sneaky;
import org.jooq.lambda.Unchecked;

@FunctionalInterface
public interface CheckedDoubleFunction<R> {
    public R apply(double var1) throws Throwable;

    public static <R> DoubleFunction<R> sneaky(CheckedDoubleFunction<R> function) {
        return Sneaky.doubleFunction(function);
    }

    public static <R> DoubleFunction<R> unchecked(CheckedDoubleFunction<R> function) {
        return Unchecked.doubleFunction(function);
    }

    public static <R> DoubleFunction<R> unchecked(CheckedDoubleFunction<R> function, Consumer<Throwable> handler) {
        return Unchecked.doubleFunction(function, handler);
    }
}

