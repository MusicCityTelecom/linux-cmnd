/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.lambda.fi.util.function;

import java.util.function.Consumer;
import java.util.function.LongToDoubleFunction;
import org.jooq.lambda.Sneaky;
import org.jooq.lambda.Unchecked;

@FunctionalInterface
public interface CheckedLongToDoubleFunction {
    public double applyAsDouble(long var1) throws Throwable;

    public static LongToDoubleFunction sneaky(CheckedLongToDoubleFunction function) {
        return Sneaky.longToDoubleFunction(function);
    }

    public static LongToDoubleFunction unchecked(CheckedLongToDoubleFunction function) {
        return Unchecked.longToDoubleFunction(function);
    }

    public static LongToDoubleFunction unchecked(CheckedLongToDoubleFunction function, Consumer<Throwable> handler) {
        return Unchecked.longToDoubleFunction(function, handler);
    }
}

