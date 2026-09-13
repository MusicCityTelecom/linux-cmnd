/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.lambda.fi.util.function;

import java.util.function.Consumer;
import java.util.function.ObjDoubleConsumer;
import org.jooq.lambda.Sneaky;
import org.jooq.lambda.Unchecked;

@FunctionalInterface
public interface CheckedObjDoubleConsumer<T> {
    public void accept(T var1, double var2) throws Throwable;

    public static <T> ObjDoubleConsumer<T> sneaky(CheckedObjDoubleConsumer<T> consumer) {
        return Sneaky.objDoubleConsumer(consumer);
    }

    public static <T> ObjDoubleConsumer<T> unchecked(CheckedObjDoubleConsumer<T> consumer) {
        return Unchecked.objDoubleConsumer(consumer);
    }

    public static <T> ObjDoubleConsumer<T> unchecked(CheckedObjDoubleConsumer<T> consumer, Consumer<Throwable> handler) {
        return Unchecked.objDoubleConsumer(consumer, handler);
    }
}

