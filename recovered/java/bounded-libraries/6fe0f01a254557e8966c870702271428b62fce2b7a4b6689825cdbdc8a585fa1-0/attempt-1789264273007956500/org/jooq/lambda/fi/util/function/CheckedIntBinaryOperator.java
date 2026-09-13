/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.lambda.fi.util.function;

import java.util.function.Consumer;
import java.util.function.IntBinaryOperator;
import org.jooq.lambda.Sneaky;
import org.jooq.lambda.Unchecked;

@FunctionalInterface
public interface CheckedIntBinaryOperator {
    public int applyAsInt(int var1, int var2) throws Throwable;

    public static IntBinaryOperator sneaky(CheckedIntBinaryOperator operator) {
        return Sneaky.intBinaryOperator(operator);
    }

    public static IntBinaryOperator unchecked(CheckedIntBinaryOperator operator) {
        return Unchecked.intBinaryOperator(operator);
    }

    public static IntBinaryOperator unchecked(CheckedIntBinaryOperator operator, Consumer<Throwable> handler) {
        return Unchecked.intBinaryOperator(operator, handler);
    }
}

