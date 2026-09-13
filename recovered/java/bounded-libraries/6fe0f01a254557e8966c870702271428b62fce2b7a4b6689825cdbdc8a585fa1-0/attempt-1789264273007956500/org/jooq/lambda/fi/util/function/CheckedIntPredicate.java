/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.lambda.fi.util.function;

import java.util.function.Consumer;
import java.util.function.IntPredicate;
import org.jooq.lambda.Sneaky;
import org.jooq.lambda.Unchecked;

@FunctionalInterface
public interface CheckedIntPredicate {
    public boolean test(int var1) throws Throwable;

    public static IntPredicate sneaky(CheckedIntPredicate predicate) {
        return Sneaky.intPredicate(predicate);
    }

    public static IntPredicate unchecked(CheckedIntPredicate predicate) {
        return Unchecked.intPredicate(predicate);
    }

    public static IntPredicate unchecked(CheckedIntPredicate function, Consumer<Throwable> handler) {
        return Unchecked.intPredicate(function, handler);
    }
}

