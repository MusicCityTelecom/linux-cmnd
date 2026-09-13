/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.lambda.tuple;

import java.util.Optional;
import org.jooq.lambda.tuple.Tuple2;

public class Range<T extends Comparable<T>>
extends Tuple2<T, T> {
    private static final long serialVersionUID = 1L;

    public Range(T v1, T v2) {
        super(Range.r(v1, v2));
    }

    public Range(Tuple2<T, T> tuple) {
        this((Comparable)tuple.v1, (Comparable)tuple.v2);
    }

    private static <T extends Comparable<T>> Tuple2<T, T> r(T t1, T t2) {
        return t1.compareTo(t2) <= 0 ? new Tuple2<T, T>(t1, t2) : new Tuple2<T, T>(t2, t1);
    }

    public boolean overlaps(Tuple2<T, T> other) {
        return Tuple2.overlaps(this, other);
    }

    public boolean overlaps(T t1, T t2) {
        return this.overlaps(new Range<T>(t1, t2));
    }

    public Optional<Range<T>> intersect(Tuple2<T, T> other) {
        return Tuple2.intersect(this, other).map(Range::new);
    }

    public Optional<Range<T>> intersect(T t1, T t2) {
        return this.intersect(new Range<T>(t1, t2));
    }
}

