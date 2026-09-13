/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.lambda.fi.util.function;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import org.jooq.lambda.Sneaky;
import org.jooq.lambda.Unchecked;

@FunctionalInterface
public interface CheckedBiConsumer<T, U> {
    public void accept(T var1, U var2) throws Throwable;

    public static <T, U> BiConsumer<T, U> sneaky(CheckedBiConsumer<T, U> consumer) {
        return Sneaky.biConsumer(consumer);
    }

    public static <T, U> BiConsumer<T, U> unchecked(CheckedBiConsumer<T, U> consumer) {
        return Unchecked.biConsumer(consumer);
    }

    public static <T, U> BiConsumer<T, U> unchecked(CheckedBiConsumer<T, U> consumer, Consumer<Throwable> handler) {
        return Unchecked.biConsumer(consumer, handler);
    }
}

