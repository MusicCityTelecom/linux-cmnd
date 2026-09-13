/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.lambda.fi.util.function;

import java.util.function.Consumer;
import java.util.function.Predicate;
import org.jooq.lambda.Sneaky;
import org.jooq.lambda.Unchecked;

@FunctionalInterface
public interface CheckedPredicate<T> {
    public boolean test(T var1) throws Throwable;

    public static <T> Predicate<T> sneaky(CheckedPredicate<T> predicate) {
        return Sneaky.predicate(predicate);
    }

    public static <T> Predicate<T> unchecked(CheckedPredicate<T> predicate) {
        return Unchecked.predicate(predicate);
    }

    public static <T> Predicate<T> unchecked(CheckedPredicate<T> function, Consumer<Throwable> handler) {
        return Unchecked.predicate(function, handler);
    }
}

