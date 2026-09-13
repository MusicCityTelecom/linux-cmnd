/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.lambda.fi.util.function;

import java.util.function.Consumer;
import java.util.function.DoubleBinaryOperator;
import org.jooq.lambda.Sneaky;
import org.jooq.lambda.Unchecked;

@FunctionalInterface
public interface CheckedDoubleBinaryOperator {
    public double applyAsDouble(double var1, double var3) throws Throwable;

    public static DoubleBinaryOperator sneaky(CheckedDoubleBinaryOperator operator) {
        return Sneaky.doubleBinaryOperator(operator);
    }

    public static DoubleBinaryOperator unchecked(CheckedDoubleBinaryOperator operator) {
        return Unchecked.doubleBinaryOperator(operator);
    }

    public static DoubleBinaryOperator unchecked(CheckedDoubleBinaryOperator operator, Consumer<Throwable> handler) {
        return Unchecked.doubleBinaryOperator(operator, handler);
    }
}

