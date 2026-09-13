/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.lambda.fi.util.concurrent;

import java.util.concurrent.Callable;
import java.util.function.Consumer;
import org.jooq.lambda.Sneaky;
import org.jooq.lambda.Unchecked;

@FunctionalInterface
public interface CheckedCallable<T> {
    public T call() throws Throwable;

    public static <T> Callable<T> sneaky(CheckedCallable<T> callable) {
        return Sneaky.callable(callable);
    }

    public static <T> Callable<T> unchecked(CheckedCallable<T> callable) {
        return Unchecked.callable(callable);
    }

    public static <T> Callable<T> unchecked(CheckedCallable<T> callable, Consumer<Throwable> handler) {
        return Unchecked.callable(callable, handler);
    }
}

