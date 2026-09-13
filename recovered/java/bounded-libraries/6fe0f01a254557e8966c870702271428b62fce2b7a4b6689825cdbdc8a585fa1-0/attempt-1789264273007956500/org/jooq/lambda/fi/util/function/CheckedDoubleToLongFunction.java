/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.lambda.fi.util.function;

import java.util.function.Consumer;
import java.util.function.DoubleToLongFunction;
import org.jooq.lambda.Sneaky;
import org.jooq.lambda.Unchecked;

@FunctionalInterface
public interface CheckedDoubleToLongFunction {
    public long applyAsLong(double var1) throws Throwable;

    public static DoubleToLongFunction sneaky(CheckedDoubleToLongFunction function) {
        return Sneaky.doubleToLongFunction(function);
    }

    public static DoubleToLongFunction unchecked(CheckedDoubleToLongFunction function) {
        return Unchecked.doubleToLongFunction(function);
    }

    public static DoubleToLongFunction unchecked(CheckedDoubleToLongFunction function, Consumer<Throwable> handler) {
        return Unchecked.doubleToLongFunction(function, handler);
    }
}

