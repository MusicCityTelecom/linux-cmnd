/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.lambda.fi.util.function;

import java.util.function.Consumer;
import java.util.function.IntConsumer;
import org.jooq.lambda.Sneaky;
import org.jooq.lambda.Unchecked;

@FunctionalInterface
public interface CheckedIntConsumer {
    public void accept(int var1) throws Throwable;

    public static IntConsumer sneaky(CheckedIntConsumer consumer) {
        return Sneaky.intConsumer(consumer);
    }

    public static IntConsumer unchecked(CheckedIntConsumer consumer) {
        return Unchecked.intConsumer(consumer);
    }

    public static IntConsumer unchecked(CheckedIntConsumer consumer, Consumer<Throwable> handler) {
        return Unchecked.intConsumer(consumer, handler);
    }
}

