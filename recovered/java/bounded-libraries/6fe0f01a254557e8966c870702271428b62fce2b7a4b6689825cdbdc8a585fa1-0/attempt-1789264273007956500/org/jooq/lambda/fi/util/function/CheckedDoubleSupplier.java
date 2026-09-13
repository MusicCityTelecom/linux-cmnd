/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.lambda.fi.util.function;

import java.util.function.Consumer;
import java.util.function.DoubleSupplier;
import org.jooq.lambda.Sneaky;
import org.jooq.lambda.Unchecked;

@FunctionalInterface
public interface CheckedDoubleSupplier {
    public double getAsDouble() throws Throwable;

    public static DoubleSupplier sneaky(CheckedDoubleSupplier supplier) {
        return Sneaky.doubleSupplier(supplier);
    }

    public static DoubleSupplier unchecked(CheckedDoubleSupplier supplier) {
        return Unchecked.doubleSupplier(supplier);
    }

    public static DoubleSupplier unchecked(CheckedDoubleSupplier supplier, Consumer<Throwable> handler) {
        return Unchecked.doubleSupplier(supplier, handler);
    }
}

