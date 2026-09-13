/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.lambda.fi.util.function;

import java.util.function.Consumer;
import java.util.function.IntUnaryOperator;
import org.jooq.lambda.Sneaky;
import org.jooq.lambda.Unchecked;

@FunctionalInterface
public interface CheckedIntUnaryOperator {
    public int applyAsInt(int var1) throws Throwable;

    public static IntUnaryOperator sneaky(CheckedIntUnaryOperator operator) {
        return Sneaky.intUnaryOperator(operator);
    }

    public static IntUnaryOperator unchecked(CheckedIntUnaryOperator operator) {
        return Unchecked.intUnaryOperator(operator);
    }

    public static IntUnaryOperator unchecked(CheckedIntUnaryOperator operator, Consumer<Throwable> handler) {
        return Unchecked.intUnaryOperator(operator, handler);
    }
}

