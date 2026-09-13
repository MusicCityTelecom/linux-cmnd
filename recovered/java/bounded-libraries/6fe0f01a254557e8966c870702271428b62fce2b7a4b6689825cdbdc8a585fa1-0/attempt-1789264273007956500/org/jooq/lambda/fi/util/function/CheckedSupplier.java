/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.lambda.fi.util.function;

import java.util.function.Consumer;
import java.util.function.Supplier;
import org.jooq.lambda.Sneaky;
import org.jooq.lambda.Unchecked;

@FunctionalInterface
public interface CheckedSupplier<T> {
    public T get() throws Throwable;

    public static <T> Supplier<T> sneaky(CheckedSupplier<T> supplier) {
        return Sneaky.supplier(supplier);
    }

    public static <T> Supplier<T> unchecked(CheckedSupplier<T> supplier) {
        return Unchecked.supplier(supplier);
    }

    public static <T> Supplier<T> unchecked(CheckedSupplier<T> supplier, Consumer<Throwable> handler) {
        return Unchecked.supplier(supplier, handler);
    }
}

