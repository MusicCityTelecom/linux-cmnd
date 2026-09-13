/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.lambda.fi.util.function;

import java.util.function.Consumer;
import java.util.function.ObjLongConsumer;
import org.jooq.lambda.Sneaky;
import org.jooq.lambda.Unchecked;

@FunctionalInterface
public interface CheckedObjLongConsumer<T> {
    public void accept(T var1, long var2) throws Throwable;

    public static <T> ObjLongConsumer<T> sneaky(CheckedObjLongConsumer<T> consumer) {
        return Sneaky.objLongConsumer(consumer);
    }

    public static <T> ObjLongConsumer<T> unchecked(CheckedObjLongConsumer<T> consumer) {
        return Unchecked.objLongConsumer(consumer);
    }

    public static <T> ObjLongConsumer<T> unchecked(CheckedObjLongConsumer<T> consumer, Consumer<Throwable> handler) {
        return Unchecked.objLongConsumer(consumer, handler);
    }
}

