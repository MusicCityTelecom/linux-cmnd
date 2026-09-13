/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal.inject;

import java.util.function.Supplier;
import org.glassfish.jersey.internal.inject.Binding;

public class SupplierInstanceBinding<T>
extends Binding<Supplier<T>, SupplierInstanceBinding<T>> {
    private final Supplier<T> supplier;

    SupplierInstanceBinding(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    public Supplier<T> getSupplier() {
        return this.supplier;
    }
}

