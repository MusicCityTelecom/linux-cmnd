/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.lambda.fi.util.function;

import java.util.function.Consumer;
import java.util.function.DoubleUnaryOperator;
import org.jooq.lambda.Sneaky;
import org.jooq.lambda.Unchecked;

@FunctionalInterface
public interface CheckedDoubleUnaryOperator {
    public double applyAsDouble(double var1) throws Throwable;

    public static DoubleUnaryOperator sneaky(CheckedDoubleUnaryOperator operator) {
        return Sneaky.doubleUnaryOperator(operator);
    }

    public static DoubleUnaryOperator unchecked(CheckedDoubleUnaryOperator operator) {
        return Unchecked.doubleUnaryOperator(operator);
    }

    public static DoubleUnaryOperator unchecked(CheckedDoubleUnaryOperator operator, Consumer<Throwable> handler) {
        return Unchecked.doubleUnaryOperator(operator, handler);
    }
}

