/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.lambda.fi.util.function;

import java.util.function.Consumer;
import java.util.function.DoubleToIntFunction;
import org.jooq.lambda.Sneaky;
import org.jooq.lambda.Unchecked;

@FunctionalInterface
public interface CheckedDoubleToIntFunction {
    public int applyAsInt(double var1) throws Throwable;

    public static DoubleToIntFunction sneaky(CheckedDoubleToIntFunction function) {
        return Sneaky.doubleToIntFunction(function);
    }

    public static DoubleToIntFunction unchecked(CheckedDoubleToIntFunction function) {
        return Unchecked.doubleToIntFunction(function);
    }

    public static DoubleToIntFunction unchecked(CheckedDoubleToIntFunction function, Consumer<Throwable> handler) {
        return Unchecked.doubleToIntFunction(function, handler);
    }
}

