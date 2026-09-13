/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.lambda;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import org.jooq.lambda.Seq;
import org.jooq.lambda.Sum;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

public class Agg {
    public static <T, A, R> Collector<T, ?, R> filter(Predicate<? super T> predicate, Collector<T, A, R> downstream) {
        return Collector.of(downstream.supplier(), (c, t) -> {
            if (predicate.test(t)) {
                downstream.accumulator().accept(c, t);
            }
        }, downstream.combiner(), downstream.finisher(), new Collector.Characteristics[0]);
    }

    public static <T> Collector<T, ?, Optional<T>> first() {
        return Collectors.reducing((v1, v2) -> v1);
    }

    public static <T> Collector<T, ?, Optional<T>> last() {
        return Collectors.reducing((v1, v2) -> v2);
    }

    public static <T> Collector<T, ?, Long> count() {
        return Collectors.counting();
    }

    public static <T> Collector<T, ?, Long> countDistinct() {
        return Agg.countDistinctBy(t -> t);
    }

    public static <T, U> Collector<T, ?, Long> countDistinctBy(Function<? super T, ? extends U> function) {
        return Collector.of(() -> new HashSet(), (s, v) -> s.add(function.apply(v)), (s1, s2) -> {
            s1.addAll(s2);
            return s1;
        }, s -> s.size(), new Collector.Characteristics[0]);
    }

    public static <T> Collector<T, ?, Optional<T>> sum() {
        return Agg.sum(t -> t);
    }

    public static <T, U> Collector<T, ?, Optional<U>> sum(Function<? super T, ? extends U> function) {
        return Collector.of(() -> new Sum[1], (s, v) -> {
            if (s[0] == null) {
                s[0] = Sum.create(function.apply(v));
            } else {
                s[0].add(function.apply(v));
            }
        }, (s1, s2) -> {
            s1[0].add(s2[0]);
            return s1;
        }, s -> s[0] == null ? Optional.empty() : Optional.of(s[0].result()), new Collector.Characteristics[0]);
    }

    public static <T> Collector<T, ?, Optional<T>> avg() {
        return Agg.avg(t -> t);
    }

    public static <T, U> Collector<T, ?, Optional<U>> avg(Function<? super T, ? extends U> function) {
        return Collector.of(() -> new Sum[1], (s, v) -> {
            if (s[0] == null) {
                s[0] = Sum.create(function.apply(v));
            } else {
                s[0].add(function.apply(v));
            }
        }, (s1, s2) -> {
            s1[0].add(s2[0]);
            return s1;
        }, s -> s[0] == null ? Optional.empty() : Optional.of(s[0].avg()), new Collector.Characteristics[0]);
    }

    public static <T extends Comparable<? super T>> Collector<T, ?, Optional<T>> min() {
        return Agg.minBy(t -> t, Comparator.naturalOrder());
    }

    public static <T> Collector<T, ?, Optional<T>> min(Comparator<? super T> comparator) {
        return Agg.minBy(t -> t, comparator);
    }

    public static <T, U extends Comparable<? super U>> Collector<T, ?, Optional<U>> min(Function<? super T, ? extends U> function) {
        return Agg.min(function, Comparator.naturalOrder());
    }

    public static <T, U> Collector<T, ?, Optional<U>> min(Function<? super T, ? extends U> function, Comparator<? super U> comparator) {
        return Collectors.collectingAndThen(Agg.minBy(function, comparator), t -> t.map(function));
    }

    public static <T, U extends Comparable<? super U>> Collector<T, ?, Optional<T>> minBy(Function<? super T, ? extends U> function) {
        return Agg.minBy(function, Comparator.naturalOrder());
    }

    public static <T, U> Collector<T, ?, Optional<T>> minBy(Function<? super T, ? extends U> function, Comparator<? super U> comparator) {
        return Agg.maxBy(function, comparator.reversed());
    }

    public static <T extends Comparable<? super T>> Collector<T, ?, Seq<T>> minAll() {
        return Agg.minAllBy(t -> t, Comparator.naturalOrder());
    }

    public static <T> Collector<T, ?, Seq<T>> minAll(Comparator<? super T> comparator) {
        return Agg.minAllBy(t -> t, comparator);
    }

    public static <T, U extends Comparable<? super U>> Collector<T, ?, Seq<U>> minAll(Function<? super T, ? extends U> function) {
        return Agg.minAll(function, Comparator.naturalOrder());
    }

    public static <T, U> Collector<T, ?, Seq<U>> minAll(Function<? super T, ? extends U> function, Comparator<? super U> comparator) {
        return Collectors.collectingAndThen(Agg.minAllBy(function, comparator), t -> t.map(function));
    }

    public static <T, U extends Comparable<? super U>> Collector<T, ?, Seq<T>> minAllBy(Function<? super T, ? extends U> function) {
        return Agg.minAllBy(function, Comparator.naturalOrder());
    }

    public static <T, U> Collector<T, ?, Seq<T>> minAllBy(Function<? super T, ? extends U> function, Comparator<? super U> comparator) {
        return Agg.maxAllBy(function, comparator.reversed());
    }

    public static <T extends Comparable<? super T>> Collector<T, ?, Optional<T>> max() {
        return Agg.maxBy(t -> t, Comparator.naturalOrder());
    }

    public static <T> Collector<T, ?, Optional<T>> max(Comparator<? super T> comparator) {
        return Agg.maxBy(t -> t, comparator);
    }

    public static <T, U extends Comparable<? super U>> Collector<T, ?, Optional<U>> max(Function<? super T, ? extends U> function) {
        return Agg.max(function, Comparator.naturalOrder());
    }

    public static <T, U> Collector<T, ?, Optional<U>> max(Function<? super T, ? extends U> function, Comparator<? super U> comparator) {
        return Collectors.collectingAndThen(Agg.maxBy(function, comparator), t -> t.map(function));
    }

    public static <T, U extends Comparable<? super U>> Collector<T, ?, Optional<T>> maxBy(Function<? super T, ? extends U> function) {
        return Agg.maxBy(function, Comparator.naturalOrder());
    }

    public static <T, U> Collector<T, ?, Optional<T>> maxBy(Function<? super T, ? extends U> function, Comparator<? super U> comparator) {
        class Accumulator {
            T t;
            U u;

            Accumulator() {
            }
        }
        return Collector.of(() -> new Accumulator(), (a, t) -> {
            Object u = function.apply(t);
            if (a.u == null || comparator.compare((Object)a.u, (Object)u) < 0) {
                a.t = t;
                a.u = u;
            }
        }, (a1, a2) -> a1.u == null ? a2 : (a2.u == null ? a1 : (comparator.compare((Object)a1.u, (Object)a2.u) < 0 ? a2 : a1)), a -> Optional.ofNullable(a.t), new Collector.Characteristics[0]);
    }

    public static <T extends Comparable<? super T>> Collector<T, ?, Seq<T>> maxAll() {
        return Agg.maxAllBy(t -> t, Comparator.naturalOrder());
    }

    public static <T> Collector<T, ?, Seq<T>> maxAll(Comparator<? super T> comparator) {
        return Agg.maxAllBy(t -> t, comparator);
    }

    public static <T, U extends Comparable<? super U>> Collector<T, ?, Seq<U>> maxAll(Function<? super T, ? extends U> function) {
        return Agg.maxAll(function, Comparator.naturalOrder());
    }

    public static <T, U> Collector<T, ?, Seq<U>> maxAll(Function<? super T, ? extends U> function, Comparator<? super U> comparator) {
        return Collectors.collectingAndThen(Agg.maxAllBy(function, comparator), t -> t.map(function));
    }

    public static <T, U extends Comparable<? super U>> Collector<T, ?, Seq<T>> maxAllBy(Function<? super T, ? extends U> function) {
        return Agg.maxAllBy(function, Comparator.naturalOrder());
    }

    public static <T, U> Collector<T, ?, Seq<T>> maxAllBy(Function<? super T, ? extends U> function, Comparator<? super U> comparator) {
        class Accumulator {
            List<T> t = new ArrayList();
            U u;

            Accumulator() {
            }

            void set(T t, U u) {
                this.t.clear();
                this.t.add(t);
                this.u = u;
            }
        }
        return Collector.of(() -> new Accumulator(), (a, t) -> {
            Object u = function.apply(t);
            if (a.u == null) {
                a.set(t, u);
            } else {
                int compare = comparator.compare((Object)a.u, (Object)u);
                if (compare < 0) {
                    a.set(t, u);
                } else if (compare == 0) {
                    a.t.add(t);
                }
            }
        }, (a1, a2) -> {
            if (a1.u == null) {
                return a2;
            }
            if (a2.u == null) {
                return a1;
            }
            int compare = comparator.compare((Object)a1.u, (Object)a2.u);
            if (compare < 0) {
                return a1;
            }
            if (compare > 0) {
                return a2;
            }
            a1.t.addAll(a2.t);
            return a1;
        }, a -> Seq.seq(a.t), new Collector.Characteristics[0]);
    }

    public static Collector<Boolean, ?, Boolean> allMatch() {
        return Agg.allMatch(t -> t);
    }

    public static <T> Collector<T, ?, Boolean> allMatch(Predicate<? super T> predicate) {
        return Collector.of(() -> new Boolean[1], (a, t) -> {
            a[0] = a[0] == null ? Boolean.valueOf(predicate.test(t)) : Boolean.valueOf(a[0] != false && predicate.test(t));
        }, (a1, a2) -> {
            a1[0] = a1[0] != false && a2[0] != false;
            return a1;
        }, a -> a[0] == null || a[0] != false, new Collector.Characteristics[0]);
    }

    public static Collector<Boolean, ?, Boolean> anyMatch() {
        return Agg.anyMatch(t -> t);
    }

    public static <T> Collector<T, ?, Boolean> anyMatch(Predicate<? super T> predicate) {
        return Collectors.collectingAndThen(Agg.noneMatch(predicate), t -> t == false);
    }

    public static Collector<Boolean, ?, Boolean> noneMatch() {
        return Agg.noneMatch(t -> t);
    }

    public static <T> Collector<T, ?, Boolean> noneMatch(Predicate<? super T> predicate) {
        return Agg.allMatch(predicate.negate());
    }

    public static <T> Collector<T, ?, Optional<T>> bitAnd() {
        return Agg.bitAnd(t -> t);
    }

    public static <T, U> Collector<T, ?, Optional<U>> bitAnd(Function<? super T, ? extends U> function) {
        return Collector.of(() -> new Sum[1], (s, v) -> {
            if (s[0] == null) {
                s[0] = Sum.create(function.apply(v));
            } else {
                s[0].and(function.apply(v));
            }
        }, (s1, s2) -> {
            s1[0].and(s2[0]);
            return s1;
        }, s -> s[0] == null ? Optional.empty() : Optional.of(s[0].result()), new Collector.Characteristics[0]);
    }

    public static <T, U> Collector<T, ?, Integer> bitAndInt(ToIntFunction<? super T> function) {
        return Collector.of(() -> new int[]{Integer.MAX_VALUE}, (s, v) -> {
            s[0] = s[0] & function.applyAsInt(v);
        }, (s1, s2) -> {
            s1[0] = s1[0] & s2[0];
            return s1;
        }, s -> s[0], new Collector.Characteristics[0]);
    }

    public static <T, U> Collector<T, ?, Long> bitAndLong(ToLongFunction<? super T> function) {
        return Collector.of(() -> new long[]{Long.MAX_VALUE}, (s, v) -> {
            s[0] = s[0] & function.applyAsLong(v);
        }, (s1, s2) -> {
            s1[0] = s1[0] & s2[0];
            return s1;
        }, s -> s[0], new Collector.Characteristics[0]);
    }

    public static <T> Collector<T, ?, Optional<T>> bitOr() {
        return Agg.bitOr(t -> t);
    }

    public static <T, U> Collector<T, ?, Optional<U>> bitOr(Function<? super T, ? extends U> function) {
        return Collector.of(() -> new Sum[1], (s, v) -> {
            if (s[0] == null) {
                s[0] = Sum.create(function.apply(v));
            } else {
                s[0].or(function.apply(v));
            }
        }, (s1, s2) -> {
            s1[0].or(s2[0]);
            return s1;
        }, s -> s[0] == null ? Optional.empty() : Optional.of(s[0].result()), new Collector.Characteristics[0]);
    }

    public static <T, U> Collector<T, ?, Integer> bitOrInt(ToIntFunction<? super T> function) {
        return Collector.of(() -> new int[1], (s, v) -> {
            s[0] = s[0] | function.applyAsInt(v);
        }, (s1, s2) -> {
            s1[0] = s1[0] | s2[0];
            return s1;
        }, s -> s[0], new Collector.Characteristics[0]);
    }

    public static <T, U> Collector<T, ?, Long> bitOrLong(ToLongFunction<? super T> function) {
        return Collector.of(() -> new long[1], (s, v) -> {
            s[0] = s[0] | function.applyAsLong(v);
        }, (s1, s2) -> {
            s1[0] = s1[0] | s2[0];
            return s1;
        }, s -> s[0], new Collector.Characteristics[0]);
    }

    public static <T> Collector<T, ?, Optional<T>> mode() {
        return Agg.mode0(seq -> seq.maxBy(t -> (Long)t.v2).map(t -> t.v1));
    }

    public static <T> Collector<T, ?, Seq<T>> modeAll() {
        return Agg.mode0(seq -> seq.maxAllBy(t -> (Long)t.v2).map(t -> t.v1));
    }

    private static <T, X> Collector<T, ?, X> mode0(Function<? super Seq<Tuple2<T, Long>>, ? extends X> transformer) {
        return Collector.of(() -> new LinkedHashMap(), (m, v) -> m.compute(v, (k1, v1) -> v1 == null ? 1L : v1 + 1L), (m1, m2) -> {
            m1.putAll(m2);
            return m1;
        }, m -> Seq.seq(m).transform(transformer), new Collector.Characteristics[0]);
    }

    public static <T, U> Collector<T, ?, Optional<T>> modeBy(Function<? super T, ? extends U> function) {
        return Collectors.collectingAndThen(Agg.modeAllBy(function), s -> s.findFirst());
    }

    public static <T, U> Collector<T, ?, Seq<T>> modeAllBy(Function<? super T, ? extends U> function) {
        return Collector.of(() -> new LinkedHashMap(), (m, t) -> m.compute(function.apply(t), (k, l) -> {
            List result = l != null ? l : new ArrayList();
            result.add(t);
            return result;
        }), (m1, m2) -> {
            for (Map.Entry e : m2.entrySet()) {
                List l = (List)m1.get(e.getKey());
                if (l == null) {
                    m1.put(e.getKey(), (List)e.getValue());
                    continue;
                }
                l.addAll((Collection)e.getValue());
            }
            return m1;
        }, m -> Seq.seq(m).maxAllBy(t -> ((List)t.v2).size()).flatMap(t -> Seq.seq((Iterable)t.v2)), new Collector.Characteristics[0]);
    }

    public static <T extends Comparable<? super T>> Collector<T, ?, Optional<Long>> rank(T value) {
        return Agg.rankBy(value, t -> t, Comparator.naturalOrder());
    }

    public static <T> Collector<T, ?, Optional<Long>> rank(T value, Comparator<? super T> comparator) {
        return Agg.rankBy(value, t -> t, comparator);
    }

    public static <T, U extends Comparable<? super U>> Collector<T, ?, Optional<Long>> rankBy(U value, Function<? super T, ? extends U> function) {
        return Agg.rankBy(value, function, Comparator.naturalOrder());
    }

    public static <T, U> Collector<T, ?, Optional<Long>> rankBy(U value, Function<? super T, ? extends U> function, Comparator<? super U> comparator) {
        return Collector.of(() -> new long[]{-1L}, (l, v) -> {
            if (l[0] == -1L) {
                l[0] = 0L;
            }
            if (comparator.compare((Object)value, (Object)function.apply(v)) > 0) {
                l[0] = l[0] + 1L;
            }
        }, (l1, l2) -> {
            l1[0] = (l1[0] == -1L ? 0L : l1[0]) + (l2[0] == -1L ? 0L : l2[0]);
            return l1;
        }, l -> l[0] == -1L ? Optional.empty() : Optional.of(l[0]), new Collector.Characteristics[0]);
    }

    public static <T extends Comparable<? super T>> Collector<T, ?, Optional<Long>> denseRank(T value) {
        return Agg.denseRankBy(value, t -> t, Comparator.naturalOrder());
    }

    public static <T> Collector<T, ?, Optional<Long>> denseRank(T value, Comparator<? super T> comparator) {
        return Agg.denseRankBy(value, t -> t, comparator);
    }

    public static <T, U extends Comparable<? super U>> Collector<T, ?, Optional<Long>> denseRankBy(U value, Function<? super T, ? extends U> function) {
        return Agg.denseRankBy(value, function, Comparator.naturalOrder());
    }

    public static <T, U> Collector<T, ?, Optional<Long>> denseRankBy(U value, Function<? super T, ? extends U> function, Comparator<? super U> comparator) {
        return Collector.of(() -> new TreeSet[1], (l, v) -> {
            Object u;
            if (l[0] == null) {
                l[0] = new TreeSet(comparator);
            }
            if (comparator.compare((Object)value, (Object)(u = function.apply(v))) > 0) {
                l[0].add(u);
            }
        }, (l1, l2) -> {
            if (l2[0] == null) {
                return l1;
            }
            if (l1[0] == null) {
                return l2;
            }
            l1[0].addAll(l2[0]);
            return l1;
        }, l -> l[0] == null ? Optional.empty() : Optional.of(Long.valueOf(l[0].size())), new Collector.Characteristics[0]);
    }

    public static <T extends Comparable<? super T>> Collector<T, ?, Optional<Double>> percentRank(T value) {
        return Agg.percentRankBy(value, t -> t, Comparator.naturalOrder());
    }

    public static <T> Collector<T, ?, Optional<Double>> percentRank(T value, Comparator<? super T> comparator) {
        return Agg.percentRankBy(value, t -> t, comparator);
    }

    public static <T, U extends Comparable<? super U>> Collector<T, ?, Optional<Double>> percentRankBy(U value, Function<? super T, ? extends U> function) {
        return Agg.percentRankBy(value, function, Comparator.naturalOrder());
    }

    public static <T, U> Collector<T, ?, Optional<Double>> percentRankBy(U value, Function<? super T, ? extends U> function, Comparator<? super U> comparator) {
        return Collectors.collectingAndThen(Tuple.collectors(Agg.rankBy(value, function, comparator), Agg.count()), t -> t.map((rank, count) -> rank.map(r -> (double)r.longValue() / (double)count.longValue())));
    }

    public static <T extends Comparable<? super T>> Collector<T, ?, Optional<T>> median() {
        return Agg.percentile(0.5);
    }

    public static <T> Collector<T, ?, Optional<T>> median(Comparator<? super T> comparator) {
        return Agg.percentile(0.5, comparator);
    }

    public static <T, U extends Comparable<? super U>> Collector<T, ?, Optional<U>> median(Function<? super T, ? extends U> function) {
        return Agg.percentile(0.5, function);
    }

    public static <T, U> Collector<T, ?, Optional<U>> median(Function<? super T, ? extends U> function, Comparator<? super U> comparator) {
        return Agg.percentile(0.5, function, comparator);
    }

    public static <T, U extends Comparable<? super U>> Collector<T, ?, Optional<T>> medianBy(Function<? super T, ? extends U> function) {
        return Agg.percentileBy(0.5, function, Comparator.naturalOrder());
    }

    public static <T, U> Collector<T, ?, Optional<T>> medianBy(Function<? super T, ? extends U> function, Comparator<? super U> comparator) {
        return Agg.percentileBy(0.5, function, comparator);
    }

    public static <T extends Comparable<? super T>> Collector<T, ?, Optional<T>> percentile(double percentile) {
        return Agg.percentile(percentile, t -> t, Comparator.naturalOrder());
    }

    public static <T> Collector<T, ?, Optional<T>> percentile(double percentile, Comparator<? super T> comparator) {
        return Agg.percentile(percentile, t -> t, comparator);
    }

    public static <T, U extends Comparable<? super U>> Collector<T, ?, Optional<U>> percentile(double percentile, Function<? super T, ? extends U> function) {
        return Agg.percentile(percentile, function, Comparator.naturalOrder());
    }

    public static <T, U> Collector<T, ?, Optional<U>> percentile(double percentile, Function<? super T, ? extends U> function, Comparator<? super U> comparator) {
        return Collectors.collectingAndThen(Agg.percentileBy(percentile, function, comparator), t -> t.map(function));
    }

    public static <T, U extends Comparable<? super U>> Collector<T, ?, Optional<T>> percentileBy(double percentile, Function<? super T, ? extends U> function) {
        return Agg.percentileBy(percentile, function, Comparator.naturalOrder());
    }

    public static <T, U> Collector<T, ?, Optional<T>> percentileBy(double percentile, Function<? super T, ? extends U> function, Comparator<? super U> comparator) {
        if (percentile < 0.0 || percentile > 1.0) {
            throw new IllegalArgumentException("Percentile must be between 0.0 and 1.0");
        }
        return Collector.of(() -> new ArrayList(), (l, v) -> l.add(Tuple.tuple(v, function.apply(v))), (l1, l2) -> {
            l1.addAll(l2);
            return l1;
        }, l -> {
            int size = l.size();
            if (size == 0) {
                return Optional.empty();
            }
            if (size == 1) {
                return Optional.of(((Tuple2)l.get((int)0)).v1);
            }
            l.sort(Comparator.comparing(t -> t.v2, comparator));
            if (percentile == 0.0) {
                return Optional.of(((Tuple2)l.get((int)0)).v1);
            }
            if (percentile == 1.0) {
                return Optional.of(((Tuple2)l.get((int)(size - 1))).v1);
            }
            return Optional.of(((Tuple2)l.get((int)((int)(-Math.round((double)(-((double)size * percentile + 0.5)))) - 1))).v1);
        }, new Collector.Characteristics[0]);
    }

    public static Collector<CharSequence, ?, String> commonPrefix() {
        return Collectors.collectingAndThen(Collectors.reducing((s1, s2) -> {
            int i;
            if (s1 == null || s2 == null) {
                return "";
            }
            int l = Math.min(s1.length(), s2.length());
            for (i = 0; i < l && s1.charAt(i) == s2.charAt(i); ++i) {
            }
            return s1.subSequence(0, i);
        }), s -> s.map(Objects::toString).orElse(""));
    }

    public static Collector<CharSequence, ?, String> commonSuffix() {
        return Collectors.collectingAndThen(Collectors.reducing((s1, s2) -> {
            int i;
            if (s1 == null || s2 == null) {
                return "";
            }
            int l1 = s1.length();
            int l2 = s2.length();
            int l = Math.min(l1, l2);
            for (i = 0; i < l && s1.charAt(l1 - i - 1) == s2.charAt(l2 - i - 1); ++i) {
            }
            return s1.subSequence(l1 - i, l1);
        }), s -> s.map(Objects::toString).orElse(""));
    }
}

