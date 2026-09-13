/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.lambda.function;

import java.util.function.Predicate;
import org.jooq.lambda.Seq;

public final class Functions {
    public static final <T> Predicate<T> not(Predicate<T> predicate) {
        return predicate.negate();
    }

    @SafeVarargs
    public static final <T> Predicate<T> and(Predicate<T> ... predicates) {
        return Seq.of(predicates).reduce(t -> true, (t1, t2) -> t1.and(t2));
    }

    @SafeVarargs
    public static final <T> Predicate<T> or(Predicate<T> ... predicates) {
        return Seq.of(predicates).reduce(t -> false, (t1, t2) -> t1.or(t2));
    }
}

