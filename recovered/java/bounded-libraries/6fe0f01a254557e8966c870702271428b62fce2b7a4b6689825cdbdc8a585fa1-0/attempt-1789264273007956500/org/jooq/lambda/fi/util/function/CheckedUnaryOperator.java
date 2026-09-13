/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.lambda.fi.util.function;

import java.util.function.Consumer;
import java.util.function.UnaryOperator;
import org.jooq.lambda.Sneaky;
import org.jooq.lambda.Unchecked;
import org.jooq.lambda.fi.util.function.CheckedFunction;

@FunctionalInterface
public interface CheckedUnaryOperator<T>
extends CheckedFunction<T, T> {
    public static <T> UnaryOperator<T> sneaky(CheckedUnaryOperator<T> operator) {
        return Sneaky.unaryOperator(operator);
    }

    public static <T> UnaryOperator<T> unchecked(CheckedUnaryOperator<T> operator) {
        return Unchecked.unaryOperator(operator);
    }

    public static <T> UnaryOperator<T> unchecked(CheckedUnaryOperator<T> operator, Consumer<Throwable> handler) {
        return Unchecked.unaryOperator(operator, handler);
    }
}

