/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.lambda.fi.util.function;

import java.util.function.Consumer;
import java.util.function.IntToDoubleFunction;
import org.jooq.lambda.Sneaky;
import org.jooq.lambda.Unchecked;

@FunctionalInterface
public interface CheckedIntToDoubleFunction {
    public double applyAsDouble(int var1) throws Throwable;

    public static IntToDoubleFunction sneaky(CheckedIntToDoubleFunction function) {
        return Sneaky.intToDoubleFunction(function);
    }

    public static IntToDoubleFunction unchecked(CheckedIntToDoubleFunction function) {
        return Unchecked.intToDoubleFunction(function);
    }

    public static IntToDoubleFunction unchecked(CheckedIntToDoubleFunction function, Consumer<Throwable> handler) {
        return Unchecked.intToDoubleFunction(function, handler);
    }
}

