/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.lambda.fi.util.function;

import java.util.function.Consumer;
import java.util.function.LongPredicate;
import org.jooq.lambda.Sneaky;
import org.jooq.lambda.Unchecked;

@FunctionalInterface
public interface CheckedLongPredicate {
    public boolean test(long var1) throws Throwable;

    public static LongPredicate sneaky(CheckedLongPredicate predicate) {
        return Sneaky.longPredicate(predicate);
    }

    public static LongPredicate unchecked(CheckedLongPredicate predicate) {
        return Unchecked.longPredicate(predicate);
    }

    public static LongPredicate unchecked(CheckedLongPredicate function, Consumer<Throwable> handler) {
        return Unchecked.longPredicate(function, handler);
    }
}

