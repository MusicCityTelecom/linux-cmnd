/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.lambda.fi.util.function;

import java.util.function.Consumer;
import java.util.function.IntToLongFunction;
import org.jooq.lambda.Sneaky;
import org.jooq.lambda.Unchecked;

@FunctionalInterface
public interface CheckedIntToLongFunction {
    public long applyAsLong(int var1) throws Throwable;

    public static IntToLongFunction sneaky(CheckedIntToLongFunction function) {
        return Sneaky.intToLongFunction(function);
    }

    public static IntToLongFunction unchecked(CheckedIntToLongFunction function) {
        return Unchecked.intToLongFunction(function);
    }

    public static IntToLongFunction unchecked(CheckedIntToLongFunction function, Consumer<Throwable> handler) {
        return Unchecked.intToLongFunction(function, handler);
    }
}

