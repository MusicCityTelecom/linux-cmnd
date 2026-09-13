/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.lambda.fi.util.function;

import java.util.function.Consumer;
import java.util.function.ObjIntConsumer;
import org.jooq.lambda.Sneaky;
import org.jooq.lambda.Unchecked;

@FunctionalInterface
public interface CheckedObjIntConsumer<T> {
    public void accept(T var1, int var2) throws Throwable;

    public static <T> ObjIntConsumer<T> sneaky(CheckedObjIntConsumer<T> consumer) {
        return Sneaky.objIntConsumer(consumer);
    }

    public static <T> ObjIntConsumer<T> unchecked(CheckedObjIntConsumer<T> consumer) {
        return Unchecked.objIntConsumer(consumer);
    }

    public static <T> ObjIntConsumer<T> unchecked(CheckedObjIntConsumer<T> consumer, Consumer<Throwable> handler) {
        return Unchecked.objIntConsumer(consumer, handler);
    }
}

