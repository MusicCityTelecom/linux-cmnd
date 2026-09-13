/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.lambda.fi.util.function;

import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import org.jooq.lambda.Sneaky;
import org.jooq.lambda.Unchecked;

@FunctionalInterface
public interface CheckedBooleanSupplier {
    public boolean getAsBoolean() throws Throwable;

    public static BooleanSupplier sneaky(CheckedBooleanSupplier supplier) {
        return Sneaky.booleanSupplier(supplier);
    }

    public static BooleanSupplier unchecked(CheckedBooleanSupplier supplier) {
        return Unchecked.booleanSupplier(supplier);
    }

    public static BooleanSupplier unchecked(CheckedBooleanSupplier supplier, Consumer<Throwable> handler) {
        return Unchecked.booleanSupplier(supplier, handler);
    }
}

