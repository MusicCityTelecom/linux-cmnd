/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.lambda;

import java.util.Comparator;
import java.util.Optional;
import java.util.function.Function;
import org.jooq.lambda.WindowSpecification;

class WindowSpecificationImpl<T>
implements WindowSpecification<T> {
    private final Function<? super T, ?> partition;
    private final Comparator<? super T> order;
    private final long lower;
    private final long upper;

    WindowSpecificationImpl(Function<? super T, ?> partition, Comparator<? super T> order, long lower, long upper) {
        this.partition = partition;
        this.order = order;
        this.lower = lower;
        this.upper = upper;
    }

    @Override
    public Function<? super T, ?> partition() {
        return this.partition;
    }

    @Override
    public Optional<Comparator<? super T>> order() {
        return Optional.ofNullable(this.order);
    }

    @Override
    public long lower() {
        return this.lower;
    }

    @Override
    public long upper() {
        return this.upper;
    }
}

