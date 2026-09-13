/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.lambda.fi.util.function;

import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import org.jooq.lambda.Sneaky;
import org.jooq.lambda.Unchecked;

@FunctionalInterface
public interface CheckedDoubleConsumer {
    public void accept(double var1) throws Throwable;

    public static DoubleConsumer sneaky(CheckedDoubleConsumer consumer) {
        return Sneaky.doubleConsumer(consumer);
    }

    public static DoubleConsumer unchecked(CheckedDoubleConsumer consumer) {
        return Unchecked.doubleConsumer(consumer);
    }

    public static DoubleConsumer unchecked(CheckedDoubleConsumer consumer, Consumer<Throwable> handler) {
        return Unchecked.doubleConsumer(consumer, handler);
    }
}

