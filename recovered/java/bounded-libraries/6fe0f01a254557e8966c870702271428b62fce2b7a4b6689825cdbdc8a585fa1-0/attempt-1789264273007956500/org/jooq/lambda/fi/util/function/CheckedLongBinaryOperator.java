/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.lambda.fi.util.function;

import java.util.function.Consumer;
import java.util.function.LongBinaryOperator;
import org.jooq.lambda.Sneaky;
import org.jooq.lambda.Unchecked;

@FunctionalInterface
public interface CheckedLongBinaryOperator {
    public long applyAsLong(long var1, long var3) throws Throwable;

    public static LongBinaryOperator sneaky(CheckedLongBinaryOperator operator) {
        return Sneaky.longBinaryOperator(operator);
    }

    public static LongBinaryOperator unchecked(CheckedLongBinaryOperator operator) {
        return Unchecked.longBinaryOperator(operator);
    }

    public static LongBinaryOperator unchecked(CheckedLongBinaryOperator operator, Consumer<Throwable> handler) {
        return Unchecked.longBinaryOperator(operator, handler);
    }
}

