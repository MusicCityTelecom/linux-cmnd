/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.lambda;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import org.jooq.lambda.Agg;
import org.jooq.lambda.Seq;
import org.jooq.lambda.tuple.Tuple;

class SeqImpl<T>
implements Seq<T> {
    static final Object NULL = new Object();
    private final Stream<? extends T> stream;
    private Object[] buffered;

    SeqImpl(Stream<? extends T> stream) {
        this.stream = (Stream)stream.sequential();
    }

    @Override
    public Stream<T> stream() {
        return this.buffered == null ? this.stream : Stream.of(this.buffered);
    }

    @Override
    public Seq<T> filter(Predicate<? super T> predicate) {
        return Seq.seq(this.stream().filter(predicate));
    }

    @Override
    public <R> Seq<R> map(Function<? super T, ? extends R> mapper) {
        return Seq.seq(this.stream().map(mapper));
    }

    @Override
    public IntStream mapToInt(ToIntFunction<? super T> mapper) {
        return this.stream().mapToInt(mapper);
    }

    @Override
    public LongStream mapToLong(ToLongFunction<? super T> mapper) {
        return this.stream().mapToLong(mapper);
    }

    @Override
    public DoubleStream mapToDouble(ToDoubleFunction<? super T> mapper) {
        return this.stream().mapToDouble(mapper);
    }

    @Override
    public <R> Seq<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper) {
        return Seq.seq(this.stream().flatMap(mapper));
    }

    @Override
    public IntStream flatMapToInt(Function<? super T, ? extends IntStream> mapper) {
        return this.stream().flatMapToInt(mapper);
    }

    @Override
    public LongStream flatMapToLong(Function<? super T, ? extends LongStream> mapper) {
        return this.stream().flatMapToLong(mapper);
    }

    @Override
    public DoubleStream flatMapToDouble(Function<? super T, ? extends DoubleStream> mapper) {
        return this.stream().flatMapToDouble(mapper);
    }

    @Override
    public Seq<T> distinct() {
        return Seq.seq(this.stream().distinct());
    }

    @Override
    public Seq<T> sorted() {
        return Seq.seq(this.stream().sorted());
    }

    @Override
    public Seq<T> sorted(Comparator<? super T> comparator) {
        return Seq.seq(this.stream().sorted(comparator));
    }

    @Override
    public Seq<T> peek(Consumer<? super T> action) {
        return Seq.seq(this.stream().peek(action));
    }

    @Override
    public Seq<T> limit(long maxSize) {
        return Seq.seq(this.stream().limit(maxSize));
    }

    @Override
    public Seq<T> skip(long n) {
        return Seq.seq(this.stream().skip(n));
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        this.stream().forEach(action);
    }

    @Override
    public void forEachOrdered(Consumer<? super T> action) {
        this.stream().forEachOrdered(action);
    }

    @Override
    public Object[] toArray() {
        return this.stream().toArray();
    }

    @Override
    public <A> A[] toArray(IntFunction<A[]> generator) {
        return this.stream().toArray(generator);
    }

    @Override
    public T reduce(T identity, BinaryOperator<T> accumulator) {
        return this.stream().reduce(identity, accumulator);
    }

    @Override
    public Optional<T> reduce(BinaryOperator<T> accumulator) {
        return this.stream().reduce(accumulator);
    }

    @Override
    public <U> U reduce(U identity, BiFunction<U, ? super T, U> accumulator, BinaryOperator<U> combiner) {
        return this.stream().reduce(identity, accumulator, combiner);
    }

    @Override
    public <R> R collect(Supplier<R> supplier, BiConsumer<R, ? super T> accumulator, BiConsumer<R, R> combiner) {
        return this.stream().collect(supplier, accumulator, combiner);
    }

    @Override
    public <R, A> R collect(Collector<? super T, A, R> collector) {
        return this.stream().collect(collector);
    }

    @Override
    public long count() {
        return this.stream().count();
    }

    @Override
    public long count(Predicate<? super T> predicate) {
        return this.filter((Predicate)predicate).count();
    }

    @Override
    public long countDistinct() {
        return this.collect(Agg.countDistinct());
    }

    @Override
    public long countDistinct(Predicate<? super T> predicate) {
        return this.filter((Predicate)predicate).countDistinct();
    }

    @Override
    public <U> long countDistinctBy(Function<? super T, ? extends U> function) {
        return this.collect(Agg.countDistinctBy(function));
    }

    @Override
    public <U> long countDistinctBy(Function<? super T, ? extends U> function, Predicate<? super U> predicate) {
        return this.map(function).filter(predicate).countDistinct();
    }

    @Override
    public Optional<T> sum() {
        return this.collect(Agg.sum());
    }

    @Override
    public <U> Optional<U> sum(Function<? super T, ? extends U> function) {
        return this.collect(Agg.sum(function));
    }

    @Override
    public int sumInt(ToIntFunction<? super T> function) {
        return this.collect(Collectors.summingInt(function));
    }

    @Override
    public long sumLong(ToLongFunction<? super T> function) {
        return this.collect(Collectors.summingLong(function));
    }

    @Override
    public double sumDouble(ToDoubleFunction<? super T> function) {
        return this.collect(Collectors.summingDouble(function));
    }

    @Override
    public Optional<T> avg() {
        return this.collect(Agg.avg());
    }

    @Override
    public <U> Optional<U> avg(Function<? super T, ? extends U> function) {
        return this.collect(Agg.avg(function));
    }

    @Override
    public double avgInt(ToIntFunction<? super T> function) {
        return this.collect(Collectors.averagingInt(function));
    }

    @Override
    public double avgLong(ToLongFunction<? super T> function) {
        return this.collect(Collectors.averagingLong(function));
    }

    @Override
    public double avgDouble(ToDoubleFunction<? super T> function) {
        return this.collect(Collectors.averagingDouble(function));
    }

    @Override
    public Optional<T> min() {
        return this.min(Comparator.naturalOrder());
    }

    @Override
    public Optional<T> min(Comparator<? super T> comparator) {
        return this.stream().min(comparator);
    }

    @Override
    public <U extends Comparable<? super U>> Optional<U> min(Function<? super T, ? extends U> function) {
        return this.collect(Agg.min(function));
    }

    @Override
    public <U> Optional<U> min(Function<? super T, ? extends U> function, Comparator<? super U> comparator) {
        return this.collect(Agg.min(function, comparator));
    }

    @Override
    public <U extends Comparable<? super U>> Optional<T> minBy(Function<? super T, ? extends U> function) {
        return this.collect(Agg.minBy(function));
    }

    @Override
    public <U> Optional<T> minBy(Function<? super T, ? extends U> function, Comparator<? super U> comparator) {
        return this.collect(Agg.minBy(function, comparator));
    }

    @Override
    public Seq<T> minAll() {
        return this.minAll(Comparator.naturalOrder());
    }

    @Override
    public Seq<T> minAll(Comparator<? super T> comparator) {
        return this.collect(Agg.minAll(comparator));
    }

    @Override
    public <U extends Comparable<? super U>> Seq<U> minAll(Function<? super T, ? extends U> function) {
        return this.collect(Agg.minAll(function));
    }

    @Override
    public <U> Seq<U> minAll(Function<? super T, ? extends U> function, Comparator<? super U> comparator) {
        return this.collect(Agg.minAll(function, comparator));
    }

    @Override
    public <U extends Comparable<? super U>> Seq<T> minAllBy(Function<? super T, ? extends U> function) {
        return this.collect(Agg.minAllBy(function));
    }

    @Override
    public <U> Seq<T> minAllBy(Function<? super T, ? extends U> function, Comparator<? super U> comparator) {
        return this.collect(Agg.minAllBy(function, comparator));
    }

    @Override
    public Optional<T> max() {
        return this.max(Comparator.naturalOrder());
    }

    @Override
    public Optional<T> max(Comparator<? super T> comparator) {
        return this.stream().max(comparator);
    }

    @Override
    public <U extends Comparable<? super U>> Optional<U> max(Function<? super T, ? extends U> function) {
        return this.collect(Agg.max(function));
    }

    @Override
    public <U> Optional<U> max(Function<? super T, ? extends U> function, Comparator<? super U> comparator) {
        return this.collect(Agg.max(function, comparator));
    }

    @Override
    public <U extends Comparable<? super U>> Optional<T> maxBy(Function<? super T, ? extends U> function) {
        return this.collect(Agg.maxBy(function));
    }

    @Override
    public <U> Optional<T> maxBy(Function<? super T, ? extends U> function, Comparator<? super U> comparator) {
        return this.collect(Agg.maxBy(function, comparator));
    }

    @Override
    public Seq<T> maxAll() {
        return this.maxAll(Comparator.naturalOrder());
    }

    @Override
    public Seq<T> maxAll(Comparator<? super T> comparator) {
        return this.collect(Agg.maxAll(comparator));
    }

    @Override
    public <U extends Comparable<? super U>> Seq<U> maxAll(Function<? super T, ? extends U> function) {
        return this.collect(Agg.maxAll(function));
    }

    @Override
    public <U> Seq<U> maxAll(Function<? super T, ? extends U> function, Comparator<? super U> comparator) {
        return this.collect(Agg.maxAll(function, comparator));
    }

    @Override
    public <U extends Comparable<? super U>> Seq<T> maxAllBy(Function<? super T, ? extends U> function) {
        return this.collect(Agg.maxAllBy(function));
    }

    @Override
    public <U> Seq<T> maxAllBy(Function<? super T, ? extends U> function, Comparator<? super U> comparator) {
        return this.collect(Agg.maxAllBy(function, comparator));
    }

    @Override
    public Optional<T> median() {
        return this.median(Comparator.naturalOrder());
    }

    @Override
    public Optional<T> median(Comparator<? super T> comparator) {
        return this.collect(Agg.median(comparator));
    }

    @Override
    public <U extends Comparable<? super U>> Optional<T> medianBy(Function<? super T, ? extends U> function) {
        return this.collect(Agg.medianBy(function));
    }

    @Override
    public <U> Optional<T> medianBy(Function<? super T, ? extends U> function, Comparator<? super U> comparator) {
        return this.collect(Agg.medianBy(function, comparator));
    }

    @Override
    public Optional<T> percentile(double percentile) {
        return this.percentile(percentile, Comparator.naturalOrder());
    }

    @Override
    public Optional<T> percentile(double percentile, Comparator<? super T> comparator) {
        return this.collect(Agg.percentile(percentile, comparator));
    }

    @Override
    public <U extends Comparable<? super U>> Optional<T> percentileBy(double percentile, Function<? super T, ? extends U> function) {
        return this.collect(Agg.percentileBy(percentile, function));
    }

    @Override
    public <U> Optional<T> percentileBy(double percentile, Function<? super T, ? extends U> function, Comparator<? super U> comparator) {
        return this.collect(Agg.percentileBy(percentile, function, comparator));
    }

    @Override
    public Optional<T> mode() {
        return this.collect(Agg.mode());
    }

    @Override
    public <U> Optional<T> modeBy(Function<? super T, ? extends U> function) {
        return this.collect(Agg.modeBy(function));
    }

    @Override
    public Seq<T> modeAll() {
        return this.collect(Agg.modeAll());
    }

    @Override
    public <U> Seq<T> modeAllBy(Function<? super T, ? extends U> function) {
        return this.collect(Agg.modeAllBy(function));
    }

    @Override
    public boolean anyMatch(Predicate<? super T> predicate) {
        return this.stream().anyMatch(predicate);
    }

    @Override
    public boolean allMatch(Predicate<? super T> predicate) {
        return this.stream().allMatch(predicate);
    }

    @Override
    public boolean noneMatch(Predicate<? super T> predicate) {
        return this.stream().noneMatch(predicate);
    }

    @Override
    public Optional<T> bitAnd() {
        return this.collect(Agg.bitAnd());
    }

    @Override
    public <U> Optional<U> bitAnd(Function<? super T, ? extends U> function) {
        return this.collect(Agg.bitAnd(function));
    }

    @Override
    public int bitAndInt(ToIntFunction<? super T> function) {
        return this.collect(Agg.bitAndInt(function));
    }

    @Override
    public long bitAndLong(ToLongFunction<? super T> function) {
        return this.collect(Agg.bitAndLong(function));
    }

    @Override
    public Optional<T> bitOr() {
        return this.collect(Agg.bitOr());
    }

    @Override
    public <U> Optional<U> bitOr(Function<? super T, ? extends U> function) {
        return this.collect(Agg.bitOr(function));
    }

    @Override
    public int bitOrInt(ToIntFunction<? super T> function) {
        return this.collect(Agg.bitOrInt(function));
    }

    @Override
    public long bitOrLong(ToLongFunction<? super T> function) {
        return this.collect(Agg.bitOrLong(function));
    }

    @Override
    public Optional<T> findFirst() {
        return this.stream().findFirst();
    }

    @Override
    public Optional<T> findAny() {
        return this.stream().findAny();
    }

    @Override
    public Iterator<T> iterator() {
        return this.stream().iterator();
    }

    @Override
    public Spliterator<T> spliterator() {
        return this.stream().spliterator();
    }

    @Override
    public boolean isParallel() {
        return false;
    }

    @Override
    public Seq<T> onClose(Runnable closeHandler) {
        return Seq.seq((Stream)this.stream.onClose(closeHandler));
    }

    @Override
    public void close() {
        this.stream.close();
    }

    @Override
    public List<T> toList() {
        return Seq.toList(this);
    }

    @Override
    public <L extends List<T>> L toList(Supplier<L> factory) {
        return (L)((List)Seq.toCollection(this, factory));
    }

    @Override
    public List<T> toUnmodifiableList() {
        return Collections.unmodifiableList(this.toList());
    }

    @Override
    public Set<T> toSet() {
        return Seq.toSet(this);
    }

    @Override
    public <S extends Set<T>> S toSet(Supplier<S> factory) {
        return (S)((Set)Seq.toCollection(this, factory));
    }

    @Override
    public Set<T> toUnmodifiableSet() {
        return Collections.unmodifiableSet(this.toSet());
    }

    @Override
    public <C extends Collection<T>> C toCollection(Supplier<C> factory) {
        return Seq.toCollection(this, factory);
    }

    @Override
    public <K, V> Map<K, V> toMap(Function<? super T, ? extends K> keyMapper, Function<? super T, ? extends V> valueMapper) {
        return Seq.toMap(this, keyMapper, valueMapper);
    }

    @Override
    public <K> Map<K, T> toMap(Function<? super T, ? extends K> keyMapper) {
        return this.toMap(keyMapper, Function.identity());
    }

    public String toString() {
        this.buffered = this.toArray();
        return Seq.toString(this.stream());
    }

    @Override
    public String toString(CharSequence delimiter) {
        return Seq.toString(this, delimiter);
    }

    @Override
    public String toString(CharSequence delimiter, CharSequence prefix, CharSequence suffix) {
        return this.map(Objects::toString).collect(Collectors.joining(delimiter, prefix, suffix));
    }

    @Override
    public String commonPrefix() {
        return this.map(Objects::toString).collect(Agg.commonPrefix());
    }

    @Override
    public String commonSuffix() {
        return this.map(Objects::toString).collect(Agg.commonSuffix());
    }

    @Override
    public String format() {
        ArrayList<String[]> strings = new ArrayList<String[]>();
        Class[] types0 = null;
        for (T t : this) {
            Object[] objectArray;
            if (t instanceof Tuple) {
                objectArray = ((Tuple)t).toArray();
            } else {
                Object[] objectArray2 = new Object[1];
                objectArray = objectArray2;
                objectArray2[0] = t;
            }
            Object[] array = objectArray;
            if (types0 == null) {
                types0 = new Class[array.length];
            }
            for (int i2 = 0; i2 < array.length; ++i2) {
                if (types0[i2] != null || array[i2] == null) continue;
                types0[i2] = array[i2].getClass();
            }
            strings.add((String[])Seq.of(array).map((T o) -> o instanceof Optional ? ((Optional)o).map(Objects::toString).orElse("{empty}") : Objects.toString(o)).toArray(String[]::new));
        }
        if (strings.isEmpty()) {
            return "(empty seq)";
        }
        Class[] types = types0;
        int length = types.length;
        int[] maxLengths = new int[length];
        for (int s = 0; s < strings.size(); ++s) {
            for (int l = 0; l < length; ++l) {
                maxLengths[l] = Math.max(2, Math.max(maxLengths[l], ((String[])strings.get(s))[l].length()));
            }
        }
        Function[] pad = (Function[])IntStream.range(0, length).mapToObj(i -> string -> {
            boolean number = Number.class.isAssignableFrom(types[i]);
            return Seq.seq(Collections.nCopies(maxLengths[i] - string.length(), " ")).toString("", number ? "" : string, number ? string : "");
        }).toArray(Function[]::new);
        StringBuilder separator = new StringBuilder("+-");
        for (int l = 0; l < length; ++l) {
            if (l > 0) {
                separator.append("-+-");
            }
            for (int p = 0; p < maxLengths[l]; ++p) {
                separator.append('-');
            }
        }
        separator.append("-+\n");
        StringBuilder result = new StringBuilder(separator).append("| ");
        for (int l = 0; l < length; ++l) {
            String n = "v" + (l + 1);
            if (l > 0) {
                result.append(" | ");
            }
            result.append((String)pad[l].apply(n));
        }
        result.append(" |\n").append((CharSequence)separator);
        for (int s = 0; s < strings.size(); ++s) {
            result.append("| ");
            for (int l = 0; l < length; ++l) {
                String string = ((String[])strings.get(s))[l];
                if (l > 0) {
                    result.append(" | ");
                }
                result.append((String)pad[l].apply(string));
            }
            result.append(" |\n");
        }
        return result.append((CharSequence)separator).toString();
    }
}

