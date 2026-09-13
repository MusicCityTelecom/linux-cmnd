/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.lambda.fi.util.function;

import java.util.function.Consumer;
import java.util.function.LongUnaryOperator;
import org.jooq.lambda.Sneaky;
import org.jooq.lambda.Unchecked;

@FunctionalInterface
public interface CheckedLongUnaryOperator {
    public long applyAsLong(long var1) throws Throwable;

    public static LongUnaryOperator sneaky(CheckedLongUnaryOperator operator) {
        return Sneaky.longUnaryOperator(operator);
    }

    public static LongUnaryOperator unchecked(CheckedLongUnaryOperator operator) {
        return Unchecked.longUnaryOperator(operator);
    }

    public static LongUnaryOperator unchecked(CheckedLongUnaryOperator operator, Consumer<Throwable> handler) {
        return Unchecked.longUnaryOperator(operator, handler);
    }
}

