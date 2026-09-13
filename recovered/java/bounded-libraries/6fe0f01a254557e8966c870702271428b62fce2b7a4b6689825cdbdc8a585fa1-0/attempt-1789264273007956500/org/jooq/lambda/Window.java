/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.lambda;

import java.util.Comparator;
import java.util.Optional;
import java.util.function.Function;
import org.jooq.lambda.Collectable;
import org.jooq.lambda.Seq;
import org.jooq.lambda.SeqImpl;
import org.jooq.lambda.WindowSpecification;
import org.jooq.lambda.WindowSpecificationImpl;

public interface Window<T>
extends Collectable<T> {
    public static <T> WindowSpecification<T> of() {
        return new WindowSpecificationImpl<Object>(t -> SeqImpl.NULL, null, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    public static <T> WindowSpecification<T> of(long lower, long upper) {
        return new WindowSpecificationImpl<Object>(t -> SeqImpl.NULL, null, lower, upper);
    }

    public static <T> WindowSpecification<T> of(Comparator<? super T> orderBy) {
        return new WindowSpecificationImpl<T>(t -> SeqImpl.NULL, orderBy, Long.MIN_VALUE, 0L);
    }

    public static <T> WindowSpecification<T> of(Comparator<? super T> orderBy, long lower, long upper) {
        return new WindowSpecificationImpl<T>(t -> SeqImpl.NULL, orderBy, lower, upper);
    }

    public static <T, U> WindowSpecification<T> of(Function<? super T, ? extends U> partitionBy) {
        return new WindowSpecificationImpl<T>(partitionBy, null, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    public static <T, U> WindowSpecification<T> of(Function<? super T, ? extends U> partitionBy, long lower, long upper) {
        return new WindowSpecificationImpl<T>(partitionBy, null, lower, upper);
    }

    public static <T, U> WindowSpecification<T> of(Function<? super T, ? extends U> partitionBy, Comparator<? super T> orderBy) {
        return new WindowSpecificationImpl<T>(partitionBy, orderBy, Long.MIN_VALUE, 0L);
    }

    public static <T, U> WindowSpecification<T> of(Function<? super T, ? extends U> partitionBy, Comparator<? super T> orderBy, long lower, long upper) {
        return new WindowSpecificationImpl<T>(partitionBy, orderBy, lower, upper);
    }

    public T value();

    public Seq<T> window();

    public long rowNumber();

    public long rank();

    public long denseRank();

    public double percentRank();

    public long ntile(long var1);

    public Optional<T> lead();

    public Optional<T> lead(long var1);

    public Optional<T> lag();

    public Optional<T> lag(long var1);

    public Optional<T> firstValue();

    public <U> Optional<U> firstValue(Function<? super T, ? extends U> var1);

    public Optional<T> lastValue();

    public <U> Optional<U> lastValue(Function<? super T, ? extends U> var1);

    public Optional<T> nthValue(long var1);

    public <U> Optional<U> nthValue(long var1, Function<? super T, ? extends U> var3);
}

