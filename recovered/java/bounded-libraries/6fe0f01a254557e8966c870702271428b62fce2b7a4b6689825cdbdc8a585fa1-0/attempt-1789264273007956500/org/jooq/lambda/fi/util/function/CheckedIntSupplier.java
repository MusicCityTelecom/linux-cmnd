/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.lambda.fi.util.function;

import java.util.function.Consumer;
import java.util.function.IntSupplier;
import org.jooq.lambda.Sneaky;
import org.jooq.lambda.Unchecked;

@FunctionalInterface
public interface CheckedIntSupplier {
    public int getAsInt() throws Throwable;

    public static IntSupplier sneaky(CheckedIntSupplier supplier) {
        return Sneaky.intSupplier(supplier);
    }

    public static IntSupplier unchecked(CheckedIntSupplier supplier) {
        return Unchecked.intSupplier(supplier);
    }

    public static IntSupplier unchecked(CheckedIntSupplier supplier, Consumer<Throwable> handler) {
        return Unchecked.intSupplier(supplier, handler);
    }
}

