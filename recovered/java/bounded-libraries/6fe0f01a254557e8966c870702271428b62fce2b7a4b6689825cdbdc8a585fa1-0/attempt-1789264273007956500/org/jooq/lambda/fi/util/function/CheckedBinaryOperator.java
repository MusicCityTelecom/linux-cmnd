/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.lambda.fi.util.function;

import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import org.jooq.lambda.Sneaky;
import org.jooq.lambda.Unchecked;
import org.jooq.lambda.fi.util.function.CheckedBiFunction;

@FunctionalInterface
public interface CheckedBinaryOperator<T>
extends CheckedBiFunction<T, T, T> {
    public static <T> BinaryOperator<T> sneaky(CheckedBinaryOperator<T> operator) {
        return Sneaky.binaryOperator(operator);
    }

    public static <T> BinaryOperator<T> unchecked(CheckedBinaryOperator<T> operator) {
        return Unchecked.binaryOperator(operator);
    }

    public static <T> BinaryOperator<T> unchecked(CheckedBinaryOperator<T> operator, Consumer<Throwable> handler) {
        return Unchecked.binaryOperator(operator, handler);
    }
}

