/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.lambda;

import java.util.Comparator;
import java.util.Optional;
import java.util.function.Function;

public interface WindowSpecification<T> {
    public Function<? super T, ?> partition();

    public Optional<Comparator<? super T>> order();

    public long lower();

    public long upper();
}

