/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.lambda;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import org.jooq.lambda.Agg;
import org.jooq.lambda.Partition;
import org.jooq.lambda.Seq;
import org.jooq.lambda.Window;
import org.jooq.lambda.WindowSpecification;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

class WindowImpl<T>
implements Window<T> {
    final Tuple2<T, Long> value;
    final int index;
    final Partition<T> partition;
    final Comparator<? super T> order;
    final long lower;
    final long upper;

    WindowImpl(Tuple2<T, Long> value, Partition<T> partition, WindowSpecification<T> specification) {
        this.value = value;
        this.partition = partition;
        this.order = specification.order().orElse(Comparator.naturalOrder());
        this.lower = specification.lower();
        this.upper = specification.upper();
        int i = specification.order().isPresent() ? Collections.binarySearch(partition.list, value, Comparator.comparing(t -> t.v1, specification.order().get()).thenComparing(t -> (Long)t.v2)) : Collections.binarySearch(partition.list, value, Comparator.comparing(t -> (Long)t.v2));
        this.index = i >= 0 ? i : -i - 1;
    }

    @Override
    public T value() {
        return (T)this.value.v1;
    }

    @Override
    public Seq<T> window() {
        return Seq.seq(this.partition.list.subList(this.lower(), this.upper() + 1)).map(t -> t.v1);
    }

    private int lower() {
        return this.lower == Long.MIN_VALUE ? 0 : (int)Math.max(0L, (long)this.index + this.lower);
    }

    private boolean lowerInPartition() {
        return this.lower == Long.MIN_VALUE || (long)this.index + this.lower >= 0L && (long)this.index + this.lower < (long)this.partition.list.size();
    }

    private int upper() {
        return this.upper == Long.MAX_VALUE ? this.partition.list.size() - 1 : (int)Math.min((long)(this.partition.list.size() - 1), (long)this.index + this.upper);
    }

    private boolean upperInPartition() {
        return this.upper == Long.MAX_VALUE || (long)this.index + this.upper >= 0L && (long)this.index + this.upper < (long)this.partition.list.size();
    }

    private boolean completePartition() {
        return this.count() == (long)this.partition.list.size();
    }

    @Override
    public long rowNumber() {
        return this.index;
    }

    @Override
    public long rank() {
        return Seq.seq(this.partition.list).map(t -> t.v1).collect(Agg.rank(this.value.v1, this.order)).get();
    }

    @Override
    public long denseRank() {
        return Seq.seq(this.partition.list).map(t -> t.v1).collect(Agg.denseRank(this.value.v1, this.order)).get();
    }

    @Override
    public double percentRank() {
        return (double)this.rank() / (double)(this.partition.list.size() - 1);
    }

    @Override
    public long ntile(long bucket) {
        return bucket * this.rowNumber() / (long)this.partition.list.size();
    }

    @Override
    public Optional<T> lead() {
        return this.lead(1L);
    }

    @Override
    public Optional<T> lead(long lead) {
        return this.lead0(lead);
    }

    @Override
    public Optional<T> lag() {
        return this.lag(1L);
    }

    @Override
    public Optional<T> lag(long lag) {
        return this.lead0(-lag);
    }

    private Optional<T> lead0(long lead) {
        if (lead == 0L) {
            return Optional.of(this.value.v1);
        }
        if ((long)this.index + lead >= 0L && (long)this.index + lead < (long)this.partition.list.size()) {
            return Optional.of(this.partition.list.get((int)(this.index + (int)lead)).v1);
        }
        return Optional.empty();
    }

    @Override
    public Optional<T> firstValue() {
        return this.firstValue(t -> t);
    }

    @Override
    public <U> Optional<U> firstValue(Function<? super T, ? extends U> function) {
        return this.lowerInPartition() ? Optional.of(function.apply(this.partition.list.get((int)this.lower()).v1)) : (this.upperInPartition() ? Optional.of(function.apply(this.partition.list.get((int)0).v1)) : Optional.empty());
    }

    @Override
    public Optional<T> lastValue() {
        return this.lastValue(t -> t);
    }

    @Override
    public <U> Optional<U> lastValue(Function<? super T, ? extends U> function) {
        return this.upperInPartition() ? Optional.of(function.apply(this.partition.list.get((int)this.upper()).v1)) : (this.lowerInPartition() ? Optional.of(function.apply(this.partition.list.get((int)(this.partition.list.size() - 1)).v1)) : Optional.empty());
    }

    @Override
    public Optional<T> nthValue(long n) {
        return this.nthValue(n, t -> t);
    }

    @Override
    public <U> Optional<U> nthValue(long n, Function<? super T, ? extends U> function) {
        return (long)this.lower() + n <= (long)this.upper() ? Optional.of(function.apply(this.partition.list.get((int)(this.lower() + (int)n)).v1)) : Optional.empty();
    }

    @Override
    public long count() {
        return 1 + this.upper() - this.lower();
    }

    @Override
    public long count(Predicate<? super T> predicate) {
        return this.partition.cacheIf(this.completePartition(), Tuple.tuple("count", predicate), () -> this.window().count(predicate));
    }

    @Override
    public long countDistinct() {
        return this.partition.cacheIf(this.completePartition(), (Object)"countDistinct", () -> this.window().countDistinct());
    }

    @Override
    public long countDistinct(Predicate<? super T> predicate) {
        return this.partition.cacheIf(this.completePartition(), Tuple.tuple("countDistinct", predicate), () -> this.window().countDistinct(predicate));
    }

    @Override
    public <U> long countDistinctBy(Function<? super T, ? extends U> function) {
        return this.partition.cacheIf(this.completePartition(), () -> Tuple.tuple("countDistinctBy", function), () -> this.window().countDistinctBy(function));
    }

    @Override
    public <U> long countDistinctBy(Function<? super T, ? extends U> function, Predicate<? super U> predicate) {
        return this.partition.cacheIf(this.completePartition(), Tuple.tuple("countDistinctBy", function, predicate), () -> this.window().countDistinctBy(function, predicate));
    }

    @Override
    public Optional<T> sum() {
        return this.partition.cacheIf(this.completePartition(), (Object)"sum", () -> this.window().sum());
    }

    @Override
    public <U> Optional<U> sum(Function<? super T, ? extends U> function) {
        return this.partition.cacheIf(this.completePartition(), () -> Tuple.tuple("sum", function), () -> this.window().sum(function));
    }

    @Override
    public int sumInt(ToIntFunction<? super T> function) {
        return this.partition.cacheIf(this.completePartition(), () -> Tuple.tuple("sumInt", function), () -> this.window().sumInt(function));
    }

    @Override
    public long sumLong(ToLongFunction<? super T> function) {
        return this.partition.cacheIf(this.completePartition(), () -> Tuple.tuple("sumLong", function), () -> this.window().sumLong(function));
    }

    @Override
    public double sumDouble(ToDoubleFunction<? super T> function) {
        return this.partition.cacheIf(this.completePartition(), () -> Tuple.tuple("sumDouble", function), () -> this.window().sumDouble(function));
    }

    @Override
    public Optional<T> avg() {
        return this.partition.cacheIf(this.completePartition(), (Object)"avg", () -> this.window().avg());
    }

    @Override
    public <U> Optional<U> avg(Function<? super T, ? extends U> function) {
        return this.partition.cacheIf(this.completePartition(), () -> Tuple.tuple("avg", function), () -> this.window().avg(function));
    }

    @Override
    public double avgInt(ToIntFunction<? super T> function) {
        return this.partition.cacheIf(this.completePartition(), () -> Tuple.tuple("avgInt", function), () -> this.window().avgInt(function));
    }

    @Override
    public double avgLong(ToLongFunction<? super T> function) {
        return this.partition.cacheIf(this.completePartition(), () -> Tuple.tuple("avgLong", function), () -> this.window().avgLong(function));
    }

    @Override
    public double avgDouble(ToDoubleFunction<? super T> function) {
        return this.partition.cacheIf(this.completePartition(), () -> Tuple.tuple("avgDouble", function), () -> this.window().avgDouble(function));
    }

    @Override
    public Optional<T> min() {
        return this.partition.cacheIf(this.completePartition(), (Object)"min", () -> this.window().min(Comparator.naturalOrder()));
    }

    @Override
    public Optional<T> min(Comparator<? super T> comparator) {
        return this.partition.cacheIf(this.completePartition(), () -> Tuple.tuple("min", comparator), () -> this.window().min(comparator));
    }

    @Override
    public <U extends Comparable<? super U>> Optional<U> min(Function<? super T, ? extends U> function) {
        return this.partition.cacheIf(this.completePartition(), () -> Tuple.tuple("min", function), () -> this.window().min(function));
    }

    @Override
    public <U> Optional<U> min(Function<? super T, ? extends U> function, Comparator<? super U> comparator) {
        return this.partition.cacheIf(this.completePartition(), () -> Tuple.tuple("min", function, comparator), () -> this.window().min(function, comparator));
    }

    @Override
    public <U extends Comparable<? super U>> Optional<T> minBy(Function<? super T, ? extends U> function) {
        return this.partition.cacheIf(this.completePartition(), () -> Tuple.tuple("minBy", function), () -> this.window().minBy(function));
    }

    @Override
    public <U> Optional<T> minBy(Function<? super T, ? extends U> function, Comparator<? super U> comparator) {
        return this.partition.cacheIf(this.completePartition(), () -> Tuple.tuple("minBy", function, comparator), () -> this.window().minBy(function, comparator));
    }

    @Override
    public Seq<T> minAll() {
        return this.partition.cacheIf(this.completePartition(), (Object)"minAll", () -> this.window().minAll(Comparator.naturalOrder()));
    }

    @Override
    public Seq<T> minAll(Comparator<? super T> comparator) {
        return this.partition.cacheIf(this.completePartition(), () -> Tuple.tuple("minAll", comparator), () -> this.window().minAll(comparator));
    }

    @Override
    public <U extends Comparable<? super U>> Seq<U> minAll(Function<? super T, ? extends U> function) {
        return this.partition.cacheIf(this.completePartition(), () -> Tuple.tuple("minAll", function), () -> this.window().minAll(function));
    }

    @Override
    public <U> Seq<U> minAll(Function<? super T, ? extends U> function, Comparator<? super U> comparator) {
        return this.partition.cacheIf(this.completePartition(), () -> Tuple.tuple("minAll", function, comparator), () -> this.window().minAll(function, comparator));
    }

    @Override
    public <U extends Comparable<? super U>> Seq<T> minAllBy(Function<? super T, ? extends U> function) {
        return this.partition.cacheIf(this.completePartition(), () -> Tuple.tuple("minAllBy", function), () -> this.window().minAllBy(function));
    }

    @Override
    public <U> Seq<T> minAllBy(Function<? super T, ? extends U> function, Comparator<? super U> comparator) {
        return this.partition.cacheIf(this.completePartition(), () -> Tuple.tuple("minAllBy", function, comparator), () -> this.window().minAllBy(function, comparator));
    }

    @Override
    public Optional<T> max() {
        return this.partition.cacheIf(this.completePartition(), (Object)"max", () -> this.window().max(Comparator.naturalOrder()));
    }

    @Override
    public Optional<T> max(Comparator<? super T> comparator) {
        return this.partition.cacheIf(this.completePartition(), () -> Tuple.tuple("max", comparator), () -> this.window().max(comparator));
    }

    @Override
    public <U extends Comparable<? super U>> Optional<U> max(Function<? super T, ? extends U> function) {
        return this.partition.cacheIf(this.completePartition(), () -> Tuple.tuple("max", function), () -> this.window().max(function));
    }

    @Override
    public <U> Optional<U> max(Function<? super T, ? extends U> function, Comparator<? super U> comparator) {
        return this.partition.cacheIf(this.completePartition(), () -> Tuple.tuple("max", function, comparator), () -> this.window().max(function, comparator));
    }

    @Override
    public <U extends Comparable<? super U>> Optional<T> maxBy(Function<? super T, ? extends U> function) {
        return this.partition.cacheIf(this.completePartition(), () -> Tuple.tuple("maxBy", function), () -> this.window().maxBy(function));
    }

    @Override
    public <U> Optional<T> maxBy(Function<? super T, ? extends U> function, Comparator<? super U> comparator) {
        return this.partition.cacheIf(this.completePartition(), () -> Tuple.tuple("maxBy", function, comparator), () -> this.window().maxBy(function, comparator));
    }

    @Override
    public Seq<T> maxAll() {
        return this.partition.cacheIf(this.completePartition(), (Object)"maxAll", () -> this.window().maxAll(Comparator.naturalOrder()));
    }

    @Override
    public Seq<T> maxAll(Comparator<? super T> comparator) {
        return this.partition.cacheIf(this.completePartition(), () -> Tuple.tuple("maxAll", comparator), () -> this.window().maxAll(comparator));
    }

    @Override
    public <U extends Comparable<? super U>> Seq<U> maxAll(Function<? super T, ? extends U> function) {
        return this.partition.cacheIf(this.completePartition(), () -> Tuple.tuple("maxAll", function), () -> this.window().maxAll(function));
    }

    @Override
    public <U> Seq<U> maxAll(Function<? super T, ? extends U> function, Comparator<? super U> comparator) {
        return this.partition.cacheIf(this.completePartition(), () -> Tuple.tuple("maxAll", function, comparator), () -> this.window().maxAll(function, comparator));
    }

    @Override
    public <U extends Comparable<? super U>> Seq<T> maxAllBy(Function<? super T, ? extends U> function) {
        return this.partition.cacheIf(this.completePartition(), () -> Tuple.tuple("maxAllBy", function), () -> this.window().maxAllBy(function));
    }

    @Override
    public <U> Seq<T> maxAllBy(Function<? super T, ? extends U> function, Comparator<? super U> comparator) {
        return this.partition.cacheIf(this.completePartition(), () -> Tuple.tuple("maxAllBy", function, comparator), () -> this.window().maxAllBy(function, comparator));
    }

    @Override
    public Optional<T> median() {
        return this.partition.cacheIf(this.completePartition(), (Object)"median", () -> this.window().median(Comparator.naturalOrder()));
    }

    @Override
    public Optional<T> median(Comparator<? super T> comparator) {
        return this.partition.cacheIf(this.completePartition(), () -> Tuple.tuple("median", comparator), () -> this.window().median(comparator));
    }

    @Override
    public <U extends Comparable<? super U>> Optional<T> medianBy(Function<? super T, ? extends U> function) {
        return this.partition.cacheIf(this.completePartition(), () -> Tuple.tuple("medianBy", function), () -> this.window().medianBy(function));
    }

    @Override
    public <U> Optional<T> medianBy(Function<? super T, ? extends U> function, Comparator<? super U> comparator) {
        return this.partition.cacheIf(this.completePartition(), () -> Tuple.tuple("medianBy", function, comparator), () -> this.window().medianBy(function, comparator));
    }

    @Override
    public Optional<T> percentile(double percentile) {
        return this.partition.cacheIf(this.completePartition(), () -> Tuple.tuple("percentile", percentile), () -> this.window().percentile(percentile, Comparator.naturalOrder()));
    }

    @Override
    public Optional<T> percentile(double percentile, Comparator<? super T> comparator) {
        return this.partition.cacheIf(this.completePartition(), () -> Tuple.tuple("percentile", percentile, comparator), () -> this.window().percentile(percentile, comparator));
    }

    @Override
    public <U extends Comparable<? super U>> Optional<T> percentileBy(double percentile, Function<? super T, ? extends U> function) {
        return this.partition.cacheIf(this.completePartition(), () -> Tuple.tuple("percentileBy", percentile, function), () -> this.window().percentileBy(percentile, function));
    }

    @Override
    public <U> Optional<T> percentileBy(double percentile, Function<? super T, ? extends U> function, Comparator<? super U> comparator) {
        return this.partition.cacheIf(this.completePartition(), () -> Tuple.tuple("percentileBy", percentile, function, comparator), () -> this.window().percentileBy(percentile, function, comparator));
    }

    @Override
    public Optional<T> mode() {
        return this.partition.cacheIf(this.completePartition(), (Object)"mode", () -> this.window().mode());
    }

    @Override
    public <U> Optional<T> modeBy(Function<? super T, ? extends U> function) {
        return this.partition.cacheIf(this.completePartition(), Tuple.tuple("modeBy", function), () -> this.window().modeBy(function));
    }

    @Override
    public Seq<T> modeAll() {
        return this.partition.cacheIf(this.completePartition(), (Object)"modeAll", () -> this.window().modeAll());
    }

    @Override
    public <U> Seq<T> modeAllBy(Function<? super T, ? extends U> function) {
        return this.partition.cacheIf(this.completePartition(), Tuple.tuple("modeAllBy", function), () -> this.window().modeAllBy(function));
    }

    @Override
    public boolean allMatch(Predicate<? super T> predicate) {
        return this.partition.cacheIf(this.completePartition(), () -> Tuple.tuple("allMatch", predicate), () -> this.window().allMatch(predicate));
    }

    @Override
    public boolean anyMatch(Predicate<? super T> predicate) {
        return this.partition.cacheIf(this.completePartition(), () -> Tuple.tuple("anyMatch", predicate), () -> this.window().anyMatch(predicate));
    }

    @Override
    public boolean noneMatch(Predicate<? super T> predicate) {
        return this.partition.cacheIf(this.completePartition(), () -> Tuple.tuple("noneMatch", predicate), () -> this.window().noneMatch(predicate));
    }

    @Override
    public Optional<T> bitAnd() {
        return this.partition.cacheIf(this.completePartition(), () -> Tuple.tuple("bitAnd"), () -> this.window().bitAnd());
    }

    @Override
    public <U> Optional<U> bitAnd(Function<? super T, ? extends U> function) {
        return this.partition.cacheIf(this.completePartition(), () -> Tuple.tuple("bitAnd", function), () -> this.window().bitAnd(function));
    }

    @Override
    public int bitAndInt(ToIntFunction<? super T> function) {
        return this.partition.cacheIf(this.completePartition(), () -> Tuple.tuple("bitAndInt", function), () -> this.window().bitAndInt(function));
    }

    @Override
    public long bitAndLong(ToLongFunction<? super T> function) {
        return this.partition.cacheIf(this.completePartition(), () -> Tuple.tuple("bitAndLong", function), () -> this.window().bitAndLong(function));
    }

    @Override
    public Optional<T> bitOr() {
        return this.partition.cacheIf(this.completePartition(), () -> Tuple.tuple("bitOr"), () -> this.window().bitOr());
    }

    @Override
    public <U> Optional<U> bitOr(Function<? super T, ? extends U> function) {
        return this.partition.cacheIf(this.completePartition(), () -> Tuple.tuple("bitOr", function), () -> this.window().bitOr(function));
    }

    @Override
    public int bitOrInt(ToIntFunction<? super T> function) {
        return this.partition.cacheIf(this.completePartition(), () -> Tuple.tuple("bitOrInt", function), () -> this.window().bitOrInt(function));
    }

    @Override
    public long bitOrLong(ToLongFunction<? super T> function) {
        return this.partition.cacheIf(this.completePartition(), () -> Tuple.tuple("bitOrLong", function), () -> this.window().bitOrLong(function));
    }

    @Override
    public <R, A> R collect(Collector<? super T, A, R> collector) {
        return (R)this.partition.cacheIf(this.completePartition(), () -> Tuple.tuple("collect", collector), () -> this.window().collect(collector));
    }

    @Override
    public List<T> toList() {
        return this.partition.cacheIf(this.completePartition(), (Object)"toList", () -> this.window().toList());
    }

    @Override
    public <L extends List<T>> L toList(Supplier<L> factory) {
        return (L)this.partition.cacheIf(this.completePartition(), () -> Tuple.tuple("toList", factory), () -> this.window().toList(factory));
    }

    @Override
    public List<T> toUnmodifiableList() {
        return Collections.unmodifiableList(this.toList());
    }

    @Override
    public Set<T> toSet() {
        return this.partition.cacheIf(this.completePartition(), (Object)"toSet", () -> this.window().toSet());
    }

    @Override
    public <S extends Set<T>> S toSet(Supplier<S> factory) {
        return (S)this.partition.cacheIf(this.completePartition(), () -> Tuple.tuple("toSet", factory), () -> this.window().toSet(factory));
    }

    @Override
    public Set<T> toUnmodifiableSet() {
        return Collections.unmodifiableSet(this.toSet());
    }

    @Override
    public <C extends Collection<T>> C toCollection(Supplier<C> factory) {
        return (C)this.partition.cacheIf(this.completePartition(), () -> Tuple.tuple("toCollection", factory), () -> this.window().toCollection(factory));
    }

    @Override
    public <K, V> Map<K, V> toMap(Function<? super T, ? extends K> keyMapper, Function<? super T, ? extends V> valueMapper) {
        return this.partition.cacheIf(this.completePartition(), () -> Tuple.tuple("toMap", keyMapper, valueMapper), () -> this.window().toMap(keyMapper, valueMapper));
    }

    @Override
    public <K> Map<K, T> toMap(Function<? super T, ? extends K> keyMapper) {
        return this.toMap(keyMapper, Function.identity());
    }

    public String toString() {
        return this.partition.cacheIf(this.completePartition(), (Object)"toString", () -> this.window().toString());
    }

    @Override
    public String toString(CharSequence delimiter) {
        return this.partition.cacheIf(this.completePartition(), () -> Tuple.tuple("toString", delimiter), () -> Seq.toString(this.window(), delimiter));
    }

    @Override
    public String toString(CharSequence delimiter, CharSequence prefix, CharSequence suffix) {
        return this.partition.cacheIf(this.completePartition(), () -> Tuple.tuple("toString", delimiter, prefix, suffix), () -> this.window().map(Objects::toString).collect(Collectors.joining(delimiter, prefix, suffix)));
    }

    @Override
    public String commonPrefix() {
        return this.partition.cacheIf(this.completePartition(), () -> "commonPrefix", () -> this.window().commonPrefix());
    }

    @Override
    public String commonSuffix() {
        return this.partition.cacheIf(this.completePartition(), () -> "commonSuffix", () -> this.window().commonSuffix());
    }
}

