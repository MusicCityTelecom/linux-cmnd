/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.lambda.fi.util.function;

import java.util.function.Consumer;
import java.util.function.LongConsumer;
import org.jooq.lambda.Sneaky;
import org.jooq.lambda.Unchecked;

@FunctionalInterface
public interface CheckedLongConsumer {
    public void accept(long var1) throws Throwable;

    public static LongConsumer sneaky(CheckedLongConsumer consumer) {
        return Sneaky.longConsumer(consumer);
    }

    public static LongConsumer unchecked(CheckedLongConsumer consumer) {
        return Unchecked.longConsumer(consumer);
    }

    public static LongConsumer unchecked(CheckedLongConsumer consumer, Consumer<Throwable> handler) {
        return Unchecked.longConsumer(consumer, handler);
    }
}

