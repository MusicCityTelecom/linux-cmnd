/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.lambda.fi.util;

import java.util.Comparator;
import java.util.function.Consumer;
import org.jooq.lambda.Sneaky;
import org.jooq.lambda.Unchecked;

@FunctionalInterface
public interface CheckedComparator<T> {
    public int compare(T var1, T var2) throws Throwable;

    public static <T> Comparator<T> sneaky(CheckedComparator<T> comparator) {
        return Sneaky.comparator(comparator);
    }

    public static <T> Comparator<T> unchecked(CheckedComparator<T> comparator) {
        return Unchecked.comparator(comparator);
    }

    public static <T> Comparator<T> unchecked(CheckedComparator<T> comparator, Consumer<Throwable> handler) {
        return Unchecked.comparator(comparator, handler);
    }
}

