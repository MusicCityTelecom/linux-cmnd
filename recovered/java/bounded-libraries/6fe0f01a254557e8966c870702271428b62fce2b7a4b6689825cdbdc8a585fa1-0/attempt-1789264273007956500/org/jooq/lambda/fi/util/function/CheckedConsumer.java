/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.lambda.fi.util.function;

import java.util.function.Consumer;
import org.jooq.lambda.Sneaky;
import org.jooq.lambda.Unchecked;

@FunctionalInterface
public interface CheckedConsumer<T> {
    public void accept(T var1) throws Throwable;

    public static <T> Consumer<T> sneaky(CheckedConsumer<T> consumer) {
        return Sneaky.consumer(consumer);
    }

    public static <T> Consumer<T> unchecked(CheckedConsumer<T> consumer) {
        return Unchecked.consumer(consumer);
    }

    public static <T> Consumer<T> unchecked(CheckedConsumer<T> consumer, Consumer<Throwable> handler) {
        return Unchecked.consumer(consumer, handler);
    }
}

