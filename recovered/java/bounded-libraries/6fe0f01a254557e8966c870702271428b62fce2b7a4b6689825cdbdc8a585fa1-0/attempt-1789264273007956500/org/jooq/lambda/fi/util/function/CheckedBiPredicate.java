/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.lambda.fi.util.function;

import java.util.function.BiPredicate;
import java.util.function.Consumer;
import org.jooq.lambda.Sneaky;
import org.jooq.lambda.Unchecked;

@FunctionalInterface
public interface CheckedBiPredicate<T, U> {
    public boolean test(T var1, U var2) throws Throwable;

    public static <T, U> BiPredicate<T, U> sneaky(CheckedBiPredicate<T, U> predicate) {
        return Sneaky.biPredicate(predicate);
    }

    public static <T, U> BiPredicate<T, U> unchecked(CheckedBiPredicate<T, U> predicate) {
        return Unchecked.biPredicate(predicate);
    }

    public static <T, U> BiPredicate<T, U> unchecked(CheckedBiPredicate<T, U> predicate, Consumer<Throwable> handler) {
        return Unchecked.biPredicate(predicate, handler);
    }
}

