/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.lambda;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.util.stream.Collector;
import org.jooq.lambda.Seq;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple10;
import org.jooq.lambda.tuple.Tuple11;
import org.jooq.lambda.tuple.Tuple12;
import org.jooq.lambda.tuple.Tuple13;
import org.jooq.lambda.tuple.Tuple14;
import org.jooq.lambda.tuple.Tuple15;
import org.jooq.lambda.tuple.Tuple16;
import org.jooq.lambda.tuple.Tuple2;
import org.jooq.lambda.tuple.Tuple3;
import org.jooq.lambda.tuple.Tuple4;
import org.jooq.lambda.tuple.Tuple5;
import org.jooq.lambda.tuple.Tuple6;
import org.jooq.lambda.tuple.Tuple7;
import org.jooq.lambda.tuple.Tuple8;
import org.jooq.lambda.tuple.Tuple9;

public interface Collectable<T> {
    public <R, A> R collect(Collector<? super T, A, R> var1);

    default public <R1, R2, A1, A2> Tuple2<R1, R2> collect(Collector<? super T, A1, R1> collector1, Collector<? super T, A2, R2> collector2) {
        return this.collect(Tuple.collectors(collector1, collector2));
    }

    default public <R1, R2, R3, A1, A2, A3> Tuple3<R1, R2, R3> collect(Collector<? super T, A1, R1> collector1, Collector<? super T, A2, R2> collector2, Collector<? super T, A3, R3> collector3) {
        return this.collect(Tuple.collectors(collector1, collector2, collector3));
    }

    default public <R1, R2, R3, R4, A1, A2, A3, A4> Tuple4<R1, R2, R3, R4> collect(Collector<? super T, A1, R1> collector1, Collector<? super T, A2, R2> collector2, Collector<? super T, A3, R3> collector3, Collector<? super T, A4, R4> collector4) {
        return this.collect(Tuple.collectors(collector1, collector2, collector3, collector4));
    }

    default public <R1, R2, R3, R4, R5, A1, A2, A3, A4, A5> Tuple5<R1, R2, R3, R4, R5> collect(Collector<? super T, A1, R1> collector1, Collector<? super T, A2, R2> collector2, Collector<? super T, A3, R3> collector3, Collector<? super T, A4, R4> collector4, Collector<? super T, A5, R5> collector5) {
        return this.collect(Tuple.collectors(collector1, collector2, collector3, collector4, collector5));
    }

    default public <R1, R2, R3, R4, R5, R6, A1, A2, A3, A4, A5, A6> Tuple6<R1, R2, R3, R4, R5, R6> collect(Collector<? super T, A1, R1> collector1, Collector<? super T, A2, R2> collector2, Collector<? super T, A3, R3> collector3, Collector<? super T, A4, R4> collector4, Collector<? super T, A5, R5> collector5, Collector<? super T, A6, R6> collector6) {
        return this.collect(Tuple.collectors(collector1, collector2, collector3, collector4, collector5, collector6));
    }

    default public <R1, R2, R3, R4, R5, R6, R7, A1, A2, A3, A4, A5, A6, A7> Tuple7<R1, R2, R3, R4, R5, R6, R7> collect(Collector<? super T, A1, R1> collector1, Collector<? super T, A2, R2> collector2, Collector<? super T, A3, R3> collector3, Collector<? super T, A4, R4> collector4, Collector<? super T, A5, R5> collector5, Collector<? super T, A6, R6> collector6, Collector<? super T, A7, R7> collector7) {
        return this.collect(Tuple.collectors(collector1, collector2, collector3, collector4, collector5, collector6, collector7));
    }

    default public <R1, R2, R3, R4, R5, R6, R7, R8, A1, A2, A3, A4, A5, A6, A7, A8> Tuple8<R1, R2, R3, R4, R5, R6, R7, R8> collect(Collector<? super T, A1, R1> collector1, Collector<? super T, A2, R2> collector2, Collector<? super T, A3, R3> collector3, Collector<? super T, A4, R4> collector4, Collector<? super T, A5, R5> collector5, Collector<? super T, A6, R6> collector6, Collector<? super T, A7, R7> collector7, Collector<? super T, A8, R8> collector8) {
        return this.collect(Tuple.collectors(collector1, collector2, collector3, collector4, collector5, collector6, collector7, collector8));
    }

    default public <R1, R2, R3, R4, R5, R6, R7, R8, R9, A1, A2, A3, A4, A5, A6, A7, A8, A9> Tuple9<R1, R2, R3, R4, R5, R6, R7, R8, R9> collect(Collector<? super T, A1, R1> collector1, Collector<? super T, A2, R2> collector2, Collector<? super T, A3, R3> collector3, Collector<? super T, A4, R4> collector4, Collector<? super T, A5, R5> collector5, Collector<? super T, A6, R6> collector6, Collector<? super T, A7, R7> collector7, Collector<? super T, A8, R8> collector8, Collector<? super T, A9, R9> collector9) {
        return this.collect(Tuple.collectors(collector1, collector2, collector3, collector4, collector5, collector6, collector7, collector8, collector9));
    }

    default public <R1, R2, R3, R4, R5, R6, R7, R8, R9, R10, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> Tuple10<R1, R2, R3, R4, R5, R6, R7, R8, R9, R10> collect(Collector<? super T, A1, R1> collector1, Collector<? super T, A2, R2> collector2, Collector<? super T, A3, R3> collector3, Collector<? super T, A4, R4> collector4, Collector<? super T, A5, R5> collector5, Collector<? super T, A6, R6> collector6, Collector<? super T, A7, R7> collector7, Collector<? super T, A8, R8> collector8, Collector<? super T, A9, R9> collector9, Collector<? super T, A10, R10> collector10) {
        return this.collect(Tuple.collectors(collector1, collector2, collector3, collector4, collector5, collector6, collector7, collector8, collector9, collector10));
    }

    default public <R1, R2, R3, R4, R5, R6, R7, R8, R9, R10, R11, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> Tuple11<R1, R2, R3, R4, R5, R6, R7, R8, R9, R10, R11> collect(Collector<? super T, A1, R1> collector1, Collector<? super T, A2, R2> collector2, Collector<? super T, A3, R3> collector3, Collector<? super T, A4, R4> collector4, Collector<? super T, A5, R5> collector5, Collector<? super T, A6, R6> collector6, Collector<? super T, A7, R7> collector7, Collector<? super T, A8, R8> collector8, Collector<? super T, A9, R9> collector9, Collector<? super T, A10, R10> collector10, Collector<? super T, A11, R11> collector11) {
        return this.collect(Tuple.collectors(collector1, collector2, collector3, collector4, collector5, collector6, collector7, collector8, collector9, collector10, collector11));
    }

    default public <R1, R2, R3, R4, R5, R6, R7, R8, R9, R10, R11, R12, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> Tuple12<R1, R2, R3, R4, R5, R6, R7, R8, R9, R10, R11, R12> collect(Collector<? super T, A1, R1> collector1, Collector<? super T, A2, R2> collector2, Collector<? super T, A3, R3> collector3, Collector<? super T, A4, R4> collector4, Collector<? super T, A5, R5> collector5, Collector<? super T, A6, R6> collector6, Collector<? super T, A7, R7> collector7, Collector<? super T, A8, R8> collector8, Collector<? super T, A9, R9> collector9, Collector<? super T, A10, R10> collector10, Collector<? super T, A11, R11> collector11, Collector<? super T, A12, R12> collector12) {
        return this.collect(Tuple.collectors(collector1, collector2, collector3, collector4, collector5, collector6, collector7, collector8, collector9, collector10, collector11, collector12));
    }

    default public <R1, R2, R3, R4, R5, R6, R7, R8, R9, R10, R11, R12, R13, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13> Tuple13<R1, R2, R3, R4, R5, R6, R7, R8, R9, R10, R11, R12, R13> collect(Collector<? super T, A1, R1> collector1, Collector<? super T, A2, R2> collector2, Collector<? super T, A3, R3> collector3, Collector<? super T, A4, R4> collector4, Collector<? super T, A5, R5> collector5, Collector<? super T, A6, R6> collector6, Collector<? super T, A7, R7> collector7, Collector<? super T, A8, R8> collector8, Collector<? super T, A9, R9> collector9, Collector<? super T, A10, R10> collector10, Collector<? super T, A11, R11> collector11, Collector<? super T, A12, R12> collector12, Collector<? super T, A13, R13> collector13) {
        return this.collect(Tuple.collectors(collector1, collector2, collector3, collector4, collector5, collector6, collector7, collector8, collector9, collector10, collector11, collector12, collector13));
    }

    default public <R1, R2, R3, R4, R5, R6, R7, R8, R9, R10, R11, R12, R13, R14, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14> Tuple14<R1, R2, R3, R4, R5, R6, R7, R8, R9, R10, R11, R12, R13, R14> collect(Collector<? super T, A1, R1> collector1, Collector<? super T, A2, R2> collector2, Collector<? super T, A3, R3> collector3, Collector<? super T, A4, R4> collector4, Collector<? super T, A5, R5> collector5, Collector<? super T, A6, R6> collector6, Collector<? super T, A7, R7> collector7, Collector<? super T, A8, R8> collector8, Collector<? super T, A9, R9> collector9, Collector<? super T, A10, R10> collector10, Collector<? super T, A11, R11> collector11, Collector<? super T, A12, R12> collector12, Collector<? super T, A13, R13> collector13, Collector<? super T, A14, R14> collector14) {
        return this.collect(Tuple.collectors(collector1, collector2, collector3, collector4, collector5, collector6, collector7, collector8, collector9, collector10, collector11, collector12, collector13, collector14));
    }

    default public <R1, R2, R3, R4, R5, R6, R7, R8, R9, R10, R11, R12, R13, R14, R15, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> Tuple15<R1, R2, R3, R4, R5, R6, R7, R8, R9, R10, R11, R12, R13, R14, R15> collect(Collector<? super T, A1, R1> collector1, Collector<? super T, A2, R2> collector2, Collector<? super T, A3, R3> collector3, Collector<? super T, A4, R4> collector4, Collector<? super T, A5, R5> collector5, Collector<? super T, A6, R6> collector6, Collector<? super T, A7, R7> collector7, Collector<? super T, A8, R8> collector8, Collector<? super T, A9, R9> collector9, Collector<? super T, A10, R10> collector10, Collector<? super T, A11, R11> collector11, Collector<? super T, A12, R12> collector12, Collector<? super T, A13, R13> collector13, Collector<? super T, A14, R14> collector14, Collector<? super T, A15, R15> collector15) {
        return this.collect(Tuple.collectors(collector1, collector2, collector3, collector4, collector5, collector6, collector7, collector8, collector9, collector10, collector11, collector12, collector13, collector14, collector15));
    }

    default public <R1, R2, R3, R4, R5, R6, R7, R8, R9, R10, R11, R12, R13, R14, R15, R16, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16> Tuple16<R1, R2, R3, R4, R5, R6, R7, R8, R9, R10, R11, R12, R13, R14, R15, R16> collect(Collector<? super T, A1, R1> collector1, Collector<? super T, A2, R2> collector2, Collector<? super T, A3, R3> collector3, Collector<? super T, A4, R4> collector4, Collector<? super T, A5, R5> collector5, Collector<? super T, A6, R6> collector6, Collector<? super T, A7, R7> collector7, Collector<? super T, A8, R8> collector8, Collector<? super T, A9, R9> collector9, Collector<? super T, A10, R10> collector10, Collector<? super T, A11, R11> collector11, Collector<? super T, A12, R12> collector12, Collector<? super T, A13, R13> collector13, Collector<? super T, A14, R14> collector14, Collector<? super T, A15, R15> collector15, Collector<? super T, A16, R16> collector16) {
        return this.collect(Tuple.collectors(collector1, collector2, collector3, collector4, collector5, collector6, collector7, collector8, collector9, collector10, collector11, collector12, collector13, collector14, collector15, collector16));
    }

    public long count();

    public long count(Predicate<? super T> var1);

    public long countDistinct();

    public long countDistinct(Predicate<? super T> var1);

    public <U> long countDistinctBy(Function<? super T, ? extends U> var1);

    public <U> long countDistinctBy(Function<? super T, ? extends U> var1, Predicate<? super U> var2);

    public Optional<T> mode();

    public <U> Optional<T> modeBy(Function<? super T, ? extends U> var1);

    public Seq<T> modeAll();

    public <U> Seq<T> modeAllBy(Function<? super T, ? extends U> var1);

    public Optional<T> sum();

    public <U> Optional<U> sum(Function<? super T, ? extends U> var1);

    public int sumInt(ToIntFunction<? super T> var1);

    public long sumLong(ToLongFunction<? super T> var1);

    public double sumDouble(ToDoubleFunction<? super T> var1);

    public Optional<T> avg();

    public <U> Optional<U> avg(Function<? super T, ? extends U> var1);

    public double avgInt(ToIntFunction<? super T> var1);

    public double avgLong(ToLongFunction<? super T> var1);

    public double avgDouble(ToDoubleFunction<? super T> var1);

    public Optional<T> min();

    public Optional<T> min(Comparator<? super T> var1);

    public <U extends Comparable<? super U>> Optional<U> min(Function<? super T, ? extends U> var1);

    public <U> Optional<U> min(Function<? super T, ? extends U> var1, Comparator<? super U> var2);

    public <U extends Comparable<? super U>> Optional<T> minBy(Function<? super T, ? extends U> var1);

    public <U> Optional<T> minBy(Function<? super T, ? extends U> var1, Comparator<? super U> var2);

    public Seq<T> minAll();

    public Seq<T> minAll(Comparator<? super T> var1);

    public <U extends Comparable<? super U>> Seq<U> minAll(Function<? super T, ? extends U> var1);

    public <U> Seq<U> minAll(Function<? super T, ? extends U> var1, Comparator<? super U> var2);

    public <U extends Comparable<? super U>> Seq<T> minAllBy(Function<? super T, ? extends U> var1);

    public <U> Seq<T> minAllBy(Function<? super T, ? extends U> var1, Comparator<? super U> var2);

    public Optional<T> max();

    public Optional<T> max(Comparator<? super T> var1);

    public <U extends Comparable<? super U>> Optional<U> max(Function<? super T, ? extends U> var1);

    public <U> Optional<U> max(Function<? super T, ? extends U> var1, Comparator<? super U> var2);

    public <U extends Comparable<? super U>> Optional<T> maxBy(Function<? super T, ? extends U> var1);

    public <U> Optional<T> maxBy(Function<? super T, ? extends U> var1, Comparator<? super U> var2);

    public Seq<T> maxAll();

    public Seq<T> maxAll(Comparator<? super T> var1);

    public <U extends Comparable<? super U>> Seq<U> maxAll(Function<? super T, ? extends U> var1);

    public <U> Seq<U> maxAll(Function<? super T, ? extends U> var1, Comparator<? super U> var2);

    public <U extends Comparable<? super U>> Seq<T> maxAllBy(Function<? super T, ? extends U> var1);

    public <U> Seq<T> maxAllBy(Function<? super T, ? extends U> var1, Comparator<? super U> var2);

    public Optional<T> median();

    public Optional<T> median(Comparator<? super T> var1);

    public <U extends Comparable<? super U>> Optional<T> medianBy(Function<? super T, ? extends U> var1);

    public <U> Optional<T> medianBy(Function<? super T, ? extends U> var1, Comparator<? super U> var2);

    public Optional<T> percentile(double var1);

    public Optional<T> percentile(double var1, Comparator<? super T> var3);

    public <U extends Comparable<? super U>> Optional<T> percentileBy(double var1, Function<? super T, ? extends U> var3);

    public <U> Optional<T> percentileBy(double var1, Function<? super T, ? extends U> var3, Comparator<? super U> var4);

    public boolean allMatch(Predicate<? super T> var1);

    public boolean anyMatch(Predicate<? super T> var1);

    public boolean noneMatch(Predicate<? super T> var1);

    public Optional<T> bitAnd();

    public <U> Optional<U> bitAnd(Function<? super T, ? extends U> var1);

    public int bitAndInt(ToIntFunction<? super T> var1);

    public long bitAndLong(ToLongFunction<? super T> var1);

    public Optional<T> bitOr();

    public <U> Optional<U> bitOr(Function<? super T, ? extends U> var1);

    public int bitOrInt(ToIntFunction<? super T> var1);

    public long bitOrLong(ToLongFunction<? super T> var1);

    public List<T> toList();

    public <L extends List<T>> L toList(Supplier<L> var1);

    public List<T> toUnmodifiableList();

    public Set<T> toSet();

    public <S extends Set<T>> S toSet(Supplier<S> var1);

    public Set<T> toUnmodifiableSet();

    public <C extends Collection<T>> C toCollection(Supplier<C> var1);

    public <K, V> Map<K, V> toMap(Function<? super T, ? extends K> var1, Function<? super T, ? extends V> var2);

    public <K> Map<K, T> toMap(Function<? super T, ? extends K> var1);

    public String toString(CharSequence var1);

    public String toString(CharSequence var1, CharSequence var2, CharSequence var3);

    public String commonPrefix();

    public String commonSuffix();
}

