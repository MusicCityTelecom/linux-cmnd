/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.lambda.fi.util.function;

import java.util.function.Consumer;
import java.util.function.LongSupplier;
import org.jooq.lambda.Sneaky;
import org.jooq.lambda.Unchecked;

@FunctionalInterface
public interface CheckedLongSupplier {
    public long getAsLong() throws Throwable;

    public static LongSupplier sneaky(CheckedLongSupplier supplier) {
        return Sneaky.longSupplier(supplier);
    }

    public static LongSupplier unchecked(CheckedLongSupplier supplier) {
        return Unchecked.longSupplier(supplier);
    }

    public static LongSupplier unchecked(CheckedLongSupplier supplier, Consumer<Throwable> handler) {
        return Unchecked.longSupplier(supplier, handler);
    }
}

