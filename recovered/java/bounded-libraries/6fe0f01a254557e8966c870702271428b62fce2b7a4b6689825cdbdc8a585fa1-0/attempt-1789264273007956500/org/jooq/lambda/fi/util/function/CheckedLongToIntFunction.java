/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.lambda.fi.util.function;

import java.util.function.Consumer;
import java.util.function.LongToIntFunction;
import org.jooq.lambda.Sneaky;
import org.jooq.lambda.Unchecked;

@FunctionalInterface
public interface CheckedLongToIntFunction {
    public int applyAsInt(long var1) throws Throwable;

    public static LongToIntFunction sneaky(CheckedLongToIntFunction function) {
        return Sneaky.longToIntFunction(function);
    }

    public static LongToIntFunction unchecked(CheckedLongToIntFunction function) {
        return Unchecked.longToIntFunction(function);
    }

    public static LongToIntFunction unchecked(CheckedLongToIntFunction function, Consumer<Throwable> handler) {
        return Unchecked.longToIntFunction(function, handler);
    }
}

