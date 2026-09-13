/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.lambda.fi.util.function;

import java.util.function.Consumer;
import java.util.function.DoublePredicate;
import org.jooq.lambda.Sneaky;
import org.jooq.lambda.Unchecked;

@FunctionalInterface
public interface CheckedDoublePredicate {
    public boolean test(double var1) throws Throwable;

    public static DoublePredicate sneaky(CheckedDoublePredicate predicate) {
        return Sneaky.doublePredicate(predicate);
    }

    public static DoublePredicate unchecked(CheckedDoublePredicate predicate) {
        return Unchecked.doublePredicate(predicate);
    }

    public static DoublePredicate unchecked(CheckedDoublePredicate function, Consumer<Throwable> handler) {
        return Unchecked.doublePredicate(function, handler);
    }
}

