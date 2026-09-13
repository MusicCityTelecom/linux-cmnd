/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.lambda.fi.util.function;

import java.util.function.Consumer;
import java.util.function.ToDoubleBiFunction;
import org.jooq.lambda.Sneaky;
import org.jooq.lambda.Unchecked;

@FunctionalInterface
public interface CheckedToDoubleBiFunction<T, U> {
    public double applyAsDouble(T var1, U var2) throws Throwable;

    public static <T, U> ToDoubleBiFunction<T, U> sneaky(CheckedToDoubleBiFunction<T, U> function) {
        return Sneaky.toDoubleBiFunction(function);
    }

    public static <T, U> ToDoubleBiFunction<T, U> unchecked(CheckedToDoubleBiFunction<T, U> function) {
        return Unchecked.toDoubleBiFunction(function);
    }

    public static <T, U> ToDoubleBiFunction<T, U> unchecked(CheckedToDoubleBiFunction<T, U> function, Consumer<Throwable> handler) {
        return Unchecked.toDoubleBiFunction(function, handler);
    }
}

