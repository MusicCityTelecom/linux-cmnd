/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.lambda;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.util.function.UnaryOperator;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.jooq.lambda.Collectable;
import org.jooq.lambda.FunctionalSpliterator;
import org.jooq.lambda.Partition;
import org.jooq.lambda.SeqBuffer;
import org.jooq.lambda.SeqImpl;
import org.jooq.lambda.SeqUtils;
import org.jooq.lambda.Unchecked;
import org.jooq.lambda.Window;
import org.jooq.lambda.WindowImpl;
import org.jooq.lambda.WindowSpecification;
import org.jooq.lambda.exception.TooManyElementsException;
import org.jooq.lambda.function.Function10;
import org.jooq.lambda.function.Function11;
import org.jooq.lambda.function.Function12;
import org.jooq.lambda.function.Function13;
import org.jooq.lambda.function.Function14;
import org.jooq.lambda.function.Function15;
import org.jooq.lambda.function.Function16;
import org.jooq.lambda.function.Function3;
import org.jooq.lambda.function.Function4;
import org.jooq.lambda.function.Function5;
import org.jooq.lambda.function.Function6;
import org.jooq.lambda.function.Function7;
import org.jooq.lambda.function.Function8;
import org.jooq.lambda.function.Function9;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple1;
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

public interface Seq<T>
extends Stream<T>,
Iterable<T>,
Collectable<T> {
    public Stream<T> stream();

    default public <U> U transform(Function<? super Seq<T>, ? extends U> transformer) {
        return transformer.apply(this);
    }

    default public <U> Seq<Tuple2<T, U>> crossApply(Function<? super T, ? extends Iterable<? extends U>> function) {
        return Seq.crossApply(this, (? super T1 t) -> Seq.seq((Iterable)function.apply(t)));
    }

    default public <U> Seq<Tuple2<T, U>> outerApply(Function<? super T, ? extends Iterable<? extends U>> function) {
        return Seq.outerApply(this, (? super T1 t) -> Seq.seq((Iterable)function.apply(t)));
    }

    default public <U> Seq<Tuple2<T, U>> crossJoin(Stream<? extends U> other) {
        return Seq.crossJoin(this, other);
    }

    default public <U> Seq<Tuple2<T, U>> crossJoin(Iterable<? extends U> other) {
        return Seq.crossJoin(this, other);
    }

    default public <U> Seq<Tuple2<T, U>> crossJoin(Seq<? extends U> other) {
        return Seq.crossJoin(this, other);
    }

    default public Seq<Tuple2<T, T>> crossSelfJoin() {
        SeqBuffer buffer = SeqBuffer.of(this);
        return Seq.crossJoin(buffer.seq(), buffer.seq());
    }

    default public <U> Seq<Tuple2<T, U>> innerJoin(Stream<? extends U> other, BiPredicate<? super T, ? super U> predicate) {
        return this.innerJoin(Seq.seq(other), predicate);
    }

    default public <U> Seq<Tuple2<T, U>> innerJoin(Iterable<? extends U> other, BiPredicate<? super T, ? super U> predicate) {
        return this.innerJoin(Seq.seq(other), predicate);
    }

    default public <U> Seq<Tuple2<T, U>> innerJoin(Seq<? extends U> other, BiPredicate<? super T, ? super U> predicate) {
        SeqBuffer buffer = SeqBuffer.of(other);
        return this.flatMap((T t) -> buffer.seq().filter(u -> predicate.test(t, u)).map((T u) -> Tuple.tuple(t, u))).onClose(other::close);
    }

    default public Seq<Tuple2<T, T>> innerSelfJoin(BiPredicate<? super T, ? super T> predicate) {
        SeqBuffer buffer = SeqBuffer.of(this);
        return buffer.seq().innerJoin(buffer.seq(), predicate);
    }

    default public <U> Seq<Tuple2<T, U>> leftOuterJoin(Stream<? extends U> other, BiPredicate<? super T, ? super U> predicate) {
        return this.leftOuterJoin(Seq.seq(other), predicate);
    }

    default public <U> Seq<Tuple2<T, U>> leftOuterJoin(Iterable<? extends U> other, BiPredicate<? super T, ? super U> predicate) {
        return this.leftOuterJoin(Seq.seq(other), predicate);
    }

    default public <U> Seq<Tuple2<T, U>> leftOuterJoin(Seq<? extends U> other, BiPredicate<? super T, ? super U> predicate) {
        SeqBuffer buffer = SeqBuffer.of(other);
        return this.flatMap((T t) -> buffer.seq().filter(u -> predicate.test(t, u)).onEmpty(null).map((T u) -> Tuple.tuple(t, u))).onClose(other::close);
    }

    default public Seq<Tuple2<T, T>> leftOuterSelfJoin(BiPredicate<? super T, ? super T> predicate) {
        SeqBuffer buffer = SeqBuffer.of(this);
        return buffer.seq().leftOuterJoin(buffer.seq(), predicate);
    }

    default public <U> Seq<Tuple2<T, U>> rightOuterJoin(Stream<? extends U> other, BiPredicate<? super T, ? super U> predicate) {
        return this.rightOuterJoin(Seq.seq(other), predicate);
    }

    default public <U> Seq<Tuple2<T, U>> rightOuterJoin(Iterable<? extends U> other, BiPredicate<? super T, ? super U> predicate) {
        return this.rightOuterJoin(Seq.seq(other), predicate);
    }

    default public <U> Seq<Tuple2<T, U>> rightOuterJoin(Seq<? extends U> other, BiPredicate<? super T, ? super U> predicate) {
        return other.leftOuterJoin(this, (? super T u, ? super U t) -> predicate.test(t, u)).map((T t) -> Tuple.tuple(t.v2, t.v1)).onClose(other::close);
    }

    default public Seq<Tuple2<T, T>> rightOuterSelfJoin(BiPredicate<? super T, ? super T> predicate) {
        return this.leftOuterSelfJoin((u, t) -> predicate.test(t, u)).map((T t) -> Tuple.tuple(t.v2, t.v1));
    }

    default public Seq<T> onEmpty(T value) {
        return this.onEmptyGet(() -> value);
    }

    default public Seq<T> onEmptyGet(Supplier<? extends T> supplier) {
        boolean[] first = new boolean[]{true};
        return SeqUtils.transform(this, (delegate, action) -> {
            if (first[0]) {
                first[0] = false;
                if (!delegate.tryAdvance(action)) {
                    action.accept(supplier.get());
                }
                return true;
            }
            return delegate.tryAdvance(action);
        });
    }

    default public <X extends Throwable> Seq<T> onEmptyThrow(Supplier<? extends X> supplier) {
        boolean[] first = new boolean[]{true};
        return SeqUtils.transform(this, (delegate, action) -> {
            if (first[0]) {
                first[0] = false;
                if (!delegate.tryAdvance(action)) {
                    SeqUtils.sneakyThrow((Throwable)supplier.get());
                }
                return true;
            }
            return delegate.tryAdvance(action);
        });
    }

    default public Seq<T> concat(Stream<? extends T> other) {
        return this.concat((T)Seq.seq(other));
    }

    default public Seq<T> concat(Iterable<? extends T> other) {
        return this.concat((T)Seq.seq(other));
    }

    default public Seq<T> concat(Seq<? extends T> other) {
        return Seq.concat(this, other);
    }

    default public Seq<T> concat(T other) {
        return this.concat((T)Seq.of(other));
    }

    default public Seq<T> concat(T ... other) {
        return this.concat((T)Seq.of(other));
    }

    default public Seq<T> concat(Optional<? extends T> other) {
        return this.concat((T)Seq.seq(other));
    }

    default public Seq<T> append(Stream<? extends T> other) {
        return this.concat((T)other);
    }

    default public Seq<T> append(Iterable<? extends T> other) {
        return this.concat((T)other);
    }

    default public Seq<T> append(Seq<? extends T> other) {
        return this.concat((T)other);
    }

    default public Seq<T> append(T other) {
        return this.concat(other);
    }

    default public Seq<T> append(T ... other) {
        return this.concat(other);
    }

    default public Seq<T> append(Optional<? extends T> other) {
        return this.concat(other);
    }

    default public Seq<T> prepend(Stream<? extends T> other) {
        return Seq.seq(other).concat(this);
    }

    default public Seq<T> prepend(Iterable<? extends T> other) {
        return Seq.seq(other).concat(this);
    }

    default public Seq<T> prepend(Seq<? extends T> other) {
        return Seq.concat(other, this);
    }

    default public Seq<T> prepend(T other) {
        return Seq.of(other).concat(this);
    }

    default public Seq<T> prepend(T ... other) {
        return Seq.of(other).concat(this);
    }

    default public Seq<T> prepend(Optional<? extends T> other) {
        return Seq.seq(other).concat(this);
    }

    default public boolean contains(T other) {
        return this.anyMatch(Predicate.isEqual(other));
    }

    default public boolean containsAll(T ... other) {
        return this.containsAll(Seq.of(other));
    }

    default public boolean containsAll(Stream<? extends T> other) {
        return this.containsAll(Seq.seq(other));
    }

    default public boolean containsAll(Iterable<? extends T> other) {
        return this.containsAll(Seq.seq(other));
    }

    default public boolean containsAll(Seq<? extends T> other) {
        HashSet set = other.toSet(HashSet::new);
        return set.isEmpty() ? true : this.filter((T t) -> set.remove(t)).anyMatch(t -> set.isEmpty());
    }

    default public boolean containsAny(T ... other) {
        return this.containsAny(Seq.of(other));
    }

    default public boolean containsAny(Stream<? extends T> other) {
        return this.containsAny(Seq.seq(other));
    }

    default public boolean containsAny(Iterable<? extends T> other) {
        return this.containsAny(Seq.seq(other));
    }

    default public boolean containsAny(Seq<? extends T> other) {
        HashSet set = other.toSet(HashSet::new);
        return set.isEmpty() ? false : this.anyMatch(set::contains);
    }

    default public Optional<T> get(long index) {
        if (index < 0L) {
            return Optional.empty();
        }
        if (index == 0L) {
            return this.findFirst();
        }
        return this.skip(index).findFirst();
    }

    default public Optional<T> findSingle() throws TooManyElementsException {
        Iterator it = this.iterator();
        if (!it.hasNext()) {
            return Optional.empty();
        }
        Object result = it.next();
        if (!it.hasNext()) {
            return Optional.of(result);
        }
        throw new TooManyElementsException("Stream contained more than one element.");
    }

    default public Optional<T> findFirst(Predicate<? super T> predicate) {
        return this.filter((Predicate)predicate).findFirst();
    }

    default public Optional<T> findLast() {
        return this.reduce((a, b) -> b);
    }

    default public Optional<T> findLast(Predicate<? super T> predicate) {
        return this.filter((Predicate)predicate).findLast();
    }

    default public OptionalLong indexOf(T element) {
        return this.indexOf(Predicate.isEqual(element));
    }

    default public OptionalLong indexOf(Predicate<? super T> predicate) {
        return SeqUtils.indexOf(this.iterator(), predicate);
    }

    default public Seq<T> remove(T other) {
        boolean[] removed = new boolean[1];
        return this.filter((T t) -> removed[0] || !(removed[0] = Objects.equals(t, other)));
    }

    default public Seq<T> removeAll(T ... other) {
        return this.removeAll(Seq.of(other));
    }

    default public Seq<T> removeAll(Stream<? extends T> other) {
        return this.removeAll(Seq.seq(other));
    }

    default public Seq<T> removeAll(Iterable<? extends T> other) {
        return this.removeAll(Seq.seq(other));
    }

    default public Seq<T> removeAll(Seq<? extends T> other) {
        HashSet set = other.toSet(HashSet::new);
        return set.isEmpty() ? this : this.filter((T t) -> !set.contains(t)).onClose(other::close);
    }

    default public Seq<T> retainAll(T ... other) {
        return this.retainAll(Seq.of(other));
    }

    default public Seq<T> retainAll(Stream<? extends T> other) {
        return this.retainAll(Seq.seq(other));
    }

    default public Seq<T> retainAll(Iterable<? extends T> other) {
        return this.retainAll(Seq.seq(other));
    }

    default public Seq<T> retainAll(Seq<? extends T> other) {
        HashSet set = other.toSet(HashSet::new);
        return set.isEmpty() ? Seq.empty() : this.filter((T t) -> set.contains(t)).onClose(other::close);
    }

    default public Seq<T> cycle() {
        return Seq.cycle(this);
    }

    default public Seq<T> cycle(long times) {
        return Seq.cycle(this, times);
    }

    default public <U> Seq<T> distinct(Function<? super T, ? extends U> keyExtractor) {
        ConcurrentHashMap seen = new ConcurrentHashMap();
        return this.filter((T t) -> seen.put(keyExtractor.apply(t), "") == null);
    }

    default public <U> Seq<Tuple2<T, U>> zip(Stream<? extends U> other) {
        return this.zip(Seq.seq(other));
    }

    default public <U> Seq<Tuple2<T, U>> zip(Iterable<? extends U> other) {
        return this.zip(Seq.seq(other));
    }

    default public <U> Seq<Tuple2<T, U>> zip(Seq<? extends U> other) {
        return Seq.zip(this, other);
    }

    default public <U, R> Seq<R> zip(Stream<? extends U> other, BiFunction<? super T, ? super U, ? extends R> zipper) {
        return this.zip(Seq.seq(other), zipper);
    }

    default public <U, R> Seq<R> zip(Iterable<? extends U> other, BiFunction<? super T, ? super U, ? extends R> zipper) {
        return this.zip(Seq.seq(other), zipper);
    }

    default public <U, R> Seq<R> zip(Seq<? extends U> other, BiFunction<? super T, ? super U, ? extends R> zipper) {
        return Seq.zip(this, other, zipper);
    }

    public static <T1, T2> Seq<Tuple2<T1, T2>> zipAll(Stream<? extends T1> s1, Stream<? extends T2> s2, T1 default1, T2 default2) {
        return Seq.zipAll(s1, s2, default1, default2, Tuple::tuple);
    }

    public static <T1, T2, T3> Seq<Tuple3<T1, T2, T3>> zipAll(Stream<? extends T1> s1, Stream<? extends T2> s2, Stream<? extends T3> s3, T1 default1, T2 default2, T3 default3) {
        return Seq.zipAll(s1, s2, s3, default1, default2, default3, Tuple::tuple);
    }

    public static <T1, T2, T3, T4> Seq<Tuple4<T1, T2, T3, T4>> zipAll(Stream<? extends T1> s1, Stream<? extends T2> s2, Stream<? extends T3> s3, Stream<? extends T4> s4, T1 default1, T2 default2, T3 default3, T4 default4) {
        return Seq.zipAll(s1, s2, s3, s4, default1, default2, default3, default4, Tuple::tuple);
    }

    public static <T1, T2, T3, T4, T5> Seq<Tuple5<T1, T2, T3, T4, T5>> zipAll(Stream<? extends T1> s1, Stream<? extends T2> s2, Stream<? extends T3> s3, Stream<? extends T4> s4, Stream<? extends T5> s5, T1 default1, T2 default2, T3 default3, T4 default4, T5 default5) {
        return Seq.zipAll(s1, s2, s3, s4, s5, default1, default2, default3, default4, default5, Tuple::tuple);
    }

    public static <T1, T2, T3, T4, T5, T6> Seq<Tuple6<T1, T2, T3, T4, T5, T6>> zipAll(Stream<? extends T1> s1, Stream<? extends T2> s2, Stream<? extends T3> s3, Stream<? extends T4> s4, Stream<? extends T5> s5, Stream<? extends T6> s6, T1 default1, T2 default2, T3 default3, T4 default4, T5 default5, T6 default6) {
        return Seq.zipAll(s1, s2, s3, s4, s5, s6, default1, default2, default3, default4, default5, default6, Tuple::tuple);
    }

    public static <T1, T2, T3, T4, T5, T6, T7> Seq<Tuple7<T1, T2, T3, T4, T5, T6, T7>> zipAll(Stream<? extends T1> s1, Stream<? extends T2> s2, Stream<? extends T3> s3, Stream<? extends T4> s4, Stream<? extends T5> s5, Stream<? extends T6> s6, Stream<? extends T7> s7, T1 default1, T2 default2, T3 default3, T4 default4, T5 default5, T6 default6, T7 default7) {
        return Seq.zipAll(s1, s2, s3, s4, s5, s6, s7, default1, default2, default3, default4, default5, default6, default7, Tuple::tuple);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8> Seq<Tuple8<T1, T2, T3, T4, T5, T6, T7, T8>> zipAll(Stream<? extends T1> s1, Stream<? extends T2> s2, Stream<? extends T3> s3, Stream<? extends T4> s4, Stream<? extends T5> s5, Stream<? extends T6> s6, Stream<? extends T7> s7, Stream<? extends T8> s8, T1 default1, T2 default2, T3 default3, T4 default4, T5 default5, T6 default6, T7 default7, T8 default8) {
        return Seq.zipAll(s1, s2, s3, s4, s5, s6, s7, s8, default1, default2, default3, default4, default5, default6, default7, default8, Tuple::tuple);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9> Seq<Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> zipAll(Stream<? extends T1> s1, Stream<? extends T2> s2, Stream<? extends T3> s3, Stream<? extends T4> s4, Stream<? extends T5> s5, Stream<? extends T6> s6, Stream<? extends T7> s7, Stream<? extends T8> s8, Stream<? extends T9> s9, T1 default1, T2 default2, T3 default3, T4 default4, T5 default5, T6 default6, T7 default7, T8 default8, T9 default9) {
        return Seq.zipAll(s1, s2, s3, s4, s5, s6, s7, s8, s9, default1, default2, default3, default4, default5, default6, default7, default8, default9, Tuple::tuple);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> Seq<Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> zipAll(Stream<? extends T1> s1, Stream<? extends T2> s2, Stream<? extends T3> s3, Stream<? extends T4> s4, Stream<? extends T5> s5, Stream<? extends T6> s6, Stream<? extends T7> s7, Stream<? extends T8> s8, Stream<? extends T9> s9, Stream<? extends T10> s10, T1 default1, T2 default2, T3 default3, T4 default4, T5 default5, T6 default6, T7 default7, T8 default8, T9 default9, T10 default10) {
        return Seq.zipAll(s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, default1, default2, default3, default4, default5, default6, default7, default8, default9, default10, Tuple::tuple);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> Seq<Tuple11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> zipAll(Stream<? extends T1> s1, Stream<? extends T2> s2, Stream<? extends T3> s3, Stream<? extends T4> s4, Stream<? extends T5> s5, Stream<? extends T6> s6, Stream<? extends T7> s7, Stream<? extends T8> s8, Stream<? extends T9> s9, Stream<? extends T10> s10, Stream<? extends T11> s11, T1 default1, T2 default2, T3 default3, T4 default4, T5 default5, T6 default6, T7 default7, T8 default8, T9 default9, T10 default10, T11 default11) {
        return Seq.zipAll(s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, default1, default2, default3, default4, default5, default6, default7, default8, default9, default10, default11, Tuple::tuple);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> Seq<Tuple12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>> zipAll(Stream<? extends T1> s1, Stream<? extends T2> s2, Stream<? extends T3> s3, Stream<? extends T4> s4, Stream<? extends T5> s5, Stream<? extends T6> s6, Stream<? extends T7> s7, Stream<? extends T8> s8, Stream<? extends T9> s9, Stream<? extends T10> s10, Stream<? extends T11> s11, Stream<? extends T12> s12, T1 default1, T2 default2, T3 default3, T4 default4, T5 default5, T6 default6, T7 default7, T8 default8, T9 default9, T10 default10, T11 default11, T12 default12) {
        return Seq.zipAll(s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, s12, default1, default2, default3, default4, default5, default6, default7, default8, default9, default10, default11, default12, Tuple::tuple);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> Seq<Tuple13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> zipAll(Stream<? extends T1> s1, Stream<? extends T2> s2, Stream<? extends T3> s3, Stream<? extends T4> s4, Stream<? extends T5> s5, Stream<? extends T6> s6, Stream<? extends T7> s7, Stream<? extends T8> s8, Stream<? extends T9> s9, Stream<? extends T10> s10, Stream<? extends T11> s11, Stream<? extends T12> s12, Stream<? extends T13> s13, T1 default1, T2 default2, T3 default3, T4 default4, T5 default5, T6 default6, T7 default7, T8 default8, T9 default9, T10 default10, T11 default11, T12 default12, T13 default13) {
        return Seq.zipAll(s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, s12, s13, default1, default2, default3, default4, default5, default6, default7, default8, default9, default10, default11, default12, default13, Tuple::tuple);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> Seq<Tuple14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> zipAll(Stream<? extends T1> s1, Stream<? extends T2> s2, Stream<? extends T3> s3, Stream<? extends T4> s4, Stream<? extends T5> s5, Stream<? extends T6> s6, Stream<? extends T7> s7, Stream<? extends T8> s8, Stream<? extends T9> s9, Stream<? extends T10> s10, Stream<? extends T11> s11, Stream<? extends T12> s12, Stream<? extends T13> s13, Stream<? extends T14> s14, T1 default1, T2 default2, T3 default3, T4 default4, T5 default5, T6 default6, T7 default7, T8 default8, T9 default9, T10 default10, T11 default11, T12 default12, T13 default13, T14 default14) {
        return Seq.zipAll(s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, s12, s13, s14, default1, default2, default3, default4, default5, default6, default7, default8, default9, default10, default11, default12, default13, default14, Tuple::tuple);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> Seq<Tuple15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> zipAll(Stream<? extends T1> s1, Stream<? extends T2> s2, Stream<? extends T3> s3, Stream<? extends T4> s4, Stream<? extends T5> s5, Stream<? extends T6> s6, Stream<? extends T7> s7, Stream<? extends T8> s8, Stream<? extends T9> s9, Stream<? extends T10> s10, Stream<? extends T11> s11, Stream<? extends T12> s12, Stream<? extends T13> s13, Stream<? extends T14> s14, Stream<? extends T15> s15, T1 default1, T2 default2, T3 default3, T4 default4, T5 default5, T6 default6, T7 default7, T8 default8, T9 default9, T10 default10, T11 default11, T12 default12, T13 default13, T14 default14, T15 default15) {
        return Seq.zipAll(s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, s12, s13, s14, s15, default1, default2, default3, default4, default5, default6, default7, default8, default9, default10, default11, default12, default13, default14, default15, Tuple::tuple);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> Seq<Tuple16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> zipAll(Stream<? extends T1> s1, Stream<? extends T2> s2, Stream<? extends T3> s3, Stream<? extends T4> s4, Stream<? extends T5> s5, Stream<? extends T6> s6, Stream<? extends T7> s7, Stream<? extends T8> s8, Stream<? extends T9> s9, Stream<? extends T10> s10, Stream<? extends T11> s11, Stream<? extends T12> s12, Stream<? extends T13> s13, Stream<? extends T14> s14, Stream<? extends T15> s15, Stream<? extends T16> s16, T1 default1, T2 default2, T3 default3, T4 default4, T5 default5, T6 default6, T7 default7, T8 default8, T9 default9, T10 default10, T11 default11, T12 default12, T13 default13, T14 default14, T15 default15, T16 default16) {
        return Seq.zipAll(s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, s12, s13, s14, s15, s16, default1, default2, default3, default4, default5, default6, default7, default8, default9, default10, default11, default12, default13, default14, default15, default16, Tuple::tuple);
    }

    public static <T1, T2> Seq<Tuple2<T1, T2>> zipAll(Iterable<? extends T1> s1, Iterable<? extends T2> s2, T1 default1, T2 default2) {
        return Seq.zipAll(s1, s2, default1, default2, Tuple::tuple);
    }

    public static <T1, T2, T3> Seq<Tuple3<T1, T2, T3>> zipAll(Iterable<? extends T1> s1, Iterable<? extends T2> s2, Iterable<? extends T3> s3, T1 default1, T2 default2, T3 default3) {
        return Seq.zipAll(s1, s2, s3, default1, default2, default3, Tuple::tuple);
    }

    public static <T1, T2, T3, T4> Seq<Tuple4<T1, T2, T3, T4>> zipAll(Iterable<? extends T1> s1, Iterable<? extends T2> s2, Iterable<? extends T3> s3, Iterable<? extends T4> s4, T1 default1, T2 default2, T3 default3, T4 default4) {
        return Seq.zipAll(s1, s2, s3, s4, default1, default2, default3, default4, Tuple::tuple);
    }

    public static <T1, T2, T3, T4, T5> Seq<Tuple5<T1, T2, T3, T4, T5>> zipAll(Iterable<? extends T1> s1, Iterable<? extends T2> s2, Iterable<? extends T3> s3, Iterable<? extends T4> s4, Iterable<? extends T5> s5, T1 default1, T2 default2, T3 default3, T4 default4, T5 default5) {
        return Seq.zipAll(s1, s2, s3, s4, s5, default1, default2, default3, default4, default5, Tuple::tuple);
    }

    public static <T1, T2, T3, T4, T5, T6> Seq<Tuple6<T1, T2, T3, T4, T5, T6>> zipAll(Iterable<? extends T1> s1, Iterable<? extends T2> s2, Iterable<? extends T3> s3, Iterable<? extends T4> s4, Iterable<? extends T5> s5, Iterable<? extends T6> s6, T1 default1, T2 default2, T3 default3, T4 default4, T5 default5, T6 default6) {
        return Seq.zipAll(s1, s2, s3, s4, s5, s6, default1, default2, default3, default4, default5, default6, Tuple::tuple);
    }

    public static <T1, T2, T3, T4, T5, T6, T7> Seq<Tuple7<T1, T2, T3, T4, T5, T6, T7>> zipAll(Iterable<? extends T1> s1, Iterable<? extends T2> s2, Iterable<? extends T3> s3, Iterable<? extends T4> s4, Iterable<? extends T5> s5, Iterable<? extends T6> s6, Iterable<? extends T7> s7, T1 default1, T2 default2, T3 default3, T4 default4, T5 default5, T6 default6, T7 default7) {
        return Seq.zipAll(s1, s2, s3, s4, s5, s6, s7, default1, default2, default3, default4, default5, default6, default7, Tuple::tuple);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8> Seq<Tuple8<T1, T2, T3, T4, T5, T6, T7, T8>> zipAll(Iterable<? extends T1> s1, Iterable<? extends T2> s2, Iterable<? extends T3> s3, Iterable<? extends T4> s4, Iterable<? extends T5> s5, Iterable<? extends T6> s6, Iterable<? extends T7> s7, Iterable<? extends T8> s8, T1 default1, T2 default2, T3 default3, T4 default4, T5 default5, T6 default6, T7 default7, T8 default8) {
        return Seq.zipAll(s1, s2, s3, s4, s5, s6, s7, s8, default1, default2, default3, default4, default5, default6, default7, default8, Tuple::tuple);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9> Seq<Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> zipAll(Iterable<? extends T1> s1, Iterable<? extends T2> s2, Iterable<? extends T3> s3, Iterable<? extends T4> s4, Iterable<? extends T5> s5, Iterable<? extends T6> s6, Iterable<? extends T7> s7, Iterable<? extends T8> s8, Iterable<? extends T9> s9, T1 default1, T2 default2, T3 default3, T4 default4, T5 default5, T6 default6, T7 default7, T8 default8, T9 default9) {
        return Seq.zipAll(s1, s2, s3, s4, s5, s6, s7, s8, s9, default1, default2, default3, default4, default5, default6, default7, default8, default9, Tuple::tuple);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> Seq<Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> zipAll(Iterable<? extends T1> s1, Iterable<? extends T2> s2, Iterable<? extends T3> s3, Iterable<? extends T4> s4, Iterable<? extends T5> s5, Iterable<? extends T6> s6, Iterable<? extends T7> s7, Iterable<? extends T8> s8, Iterable<? extends T9> s9, Iterable<? extends T10> s10, T1 default1, T2 default2, T3 default3, T4 default4, T5 default5, T6 default6, T7 default7, T8 default8, T9 default9, T10 default10) {
        return Seq.zipAll(s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, default1, default2, default3, default4, default5, default6, default7, default8, default9, default10, Tuple::tuple);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> Seq<Tuple11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> zipAll(Iterable<? extends T1> s1, Iterable<? extends T2> s2, Iterable<? extends T3> s3, Iterable<? extends T4> s4, Iterable<? extends T5> s5, Iterable<? extends T6> s6, Iterable<? extends T7> s7, Iterable<? extends T8> s8, Iterable<? extends T9> s9, Iterable<? extends T10> s10, Iterable<? extends T11> s11, T1 default1, T2 default2, T3 default3, T4 default4, T5 default5, T6 default6, T7 default7, T8 default8, T9 default9, T10 default10, T11 default11) {
        return Seq.zipAll(s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, default1, default2, default3, default4, default5, default6, default7, default8, default9, default10, default11, Tuple::tuple);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> Seq<Tuple12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>> zipAll(Iterable<? extends T1> s1, Iterable<? extends T2> s2, Iterable<? extends T3> s3, Iterable<? extends T4> s4, Iterable<? extends T5> s5, Iterable<? extends T6> s6, Iterable<? extends T7> s7, Iterable<? extends T8> s8, Iterable<? extends T9> s9, Iterable<? extends T10> s10, Iterable<? extends T11> s11, Iterable<? extends T12> s12, T1 default1, T2 default2, T3 default3, T4 default4, T5 default5, T6 default6, T7 default7, T8 default8, T9 default9, T10 default10, T11 default11, T12 default12) {
        return Seq.zipAll(s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, s12, default1, default2, default3, default4, default5, default6, default7, default8, default9, default10, default11, default12, Tuple::tuple);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> Seq<Tuple13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> zipAll(Iterable<? extends T1> s1, Iterable<? extends T2> s2, Iterable<? extends T3> s3, Iterable<? extends T4> s4, Iterable<? extends T5> s5, Iterable<? extends T6> s6, Iterable<? extends T7> s7, Iterable<? extends T8> s8, Iterable<? extends T9> s9, Iterable<? extends T10> s10, Iterable<? extends T11> s11, Iterable<? extends T12> s12, Iterable<? extends T13> s13, T1 default1, T2 default2, T3 default3, T4 default4, T5 default5, T6 default6, T7 default7, T8 default8, T9 default9, T10 default10, T11 default11, T12 default12, T13 default13) {
        return Seq.zipAll(s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, s12, s13, default1, default2, default3, default4, default5, default6, default7, default8, default9, default10, default11, default12, default13, Tuple::tuple);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> Seq<Tuple14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> zipAll(Iterable<? extends T1> s1, Iterable<? extends T2> s2, Iterable<? extends T3> s3, Iterable<? extends T4> s4, Iterable<? extends T5> s5, Iterable<? extends T6> s6, Iterable<? extends T7> s7, Iterable<? extends T8> s8, Iterable<? extends T9> s9, Iterable<? extends T10> s10, Iterable<? extends T11> s11, Iterable<? extends T12> s12, Iterable<? extends T13> s13, Iterable<? extends T14> s14, T1 default1, T2 default2, T3 default3, T4 default4, T5 default5, T6 default6, T7 default7, T8 default8, T9 default9, T10 default10, T11 default11, T12 default12, T13 default13, T14 default14) {
        return Seq.zipAll(s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, s12, s13, s14, default1, default2, default3, default4, default5, default6, default7, default8, default9, default10, default11, default12, default13, default14, Tuple::tuple);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> Seq<Tuple15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> zipAll(Iterable<? extends T1> s1, Iterable<? extends T2> s2, Iterable<? extends T3> s3, Iterable<? extends T4> s4, Iterable<? extends T5> s5, Iterable<? extends T6> s6, Iterable<? extends T7> s7, Iterable<? extends T8> s8, Iterable<? extends T9> s9, Iterable<? extends T10> s10, Iterable<? extends T11> s11, Iterable<? extends T12> s12, Iterable<? extends T13> s13, Iterable<? extends T14> s14, Iterable<? extends T15> s15, T1 default1, T2 default2, T3 default3, T4 default4, T5 default5, T6 default6, T7 default7, T8 default8, T9 default9, T10 default10, T11 default11, T12 default12, T13 default13, T14 default14, T15 default15) {
        return Seq.zipAll(s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, s12, s13, s14, s15, default1, default2, default3, default4, default5, default6, default7, default8, default9, default10, default11, default12, default13, default14, default15, Tuple::tuple);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> Seq<Tuple16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> zipAll(Iterable<? extends T1> s1, Iterable<? extends T2> s2, Iterable<? extends T3> s3, Iterable<? extends T4> s4, Iterable<? extends T5> s5, Iterable<? extends T6> s6, Iterable<? extends T7> s7, Iterable<? extends T8> s8, Iterable<? extends T9> s9, Iterable<? extends T10> s10, Iterable<? extends T11> s11, Iterable<? extends T12> s12, Iterable<? extends T13> s13, Iterable<? extends T14> s14, Iterable<? extends T15> s15, Iterable<? extends T16> s16, T1 default1, T2 default2, T3 default3, T4 default4, T5 default5, T6 default6, T7 default7, T8 default8, T9 default9, T10 default10, T11 default11, T12 default12, T13 default13, T14 default14, T15 default15, T16 default16) {
        return Seq.zipAll(s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, s12, s13, s14, s15, s16, default1, default2, default3, default4, default5, default6, default7, default8, default9, default10, default11, default12, default13, default14, default15, default16, Tuple::tuple);
    }

    public static <T1, T2> Seq<Tuple2<T1, T2>> zipAll(Seq<? extends T1> s1, Seq<? extends T2> s2, T1 default1, T2 default2) {
        return Seq.zipAll(s1, s2, default1, default2, Tuple::tuple);
    }

    public static <T1, T2, T3> Seq<Tuple3<T1, T2, T3>> zipAll(Seq<? extends T1> s1, Seq<? extends T2> s2, Seq<? extends T3> s3, T1 default1, T2 default2, T3 default3) {
        return Seq.zipAll(s1, s2, s3, default1, default2, default3, Tuple::tuple);
    }

    public static <T1, T2, T3, T4> Seq<Tuple4<T1, T2, T3, T4>> zipAll(Seq<? extends T1> s1, Seq<? extends T2> s2, Seq<? extends T3> s3, Seq<? extends T4> s4, T1 default1, T2 default2, T3 default3, T4 default4) {
        return Seq.zipAll(s1, s2, s3, s4, default1, default2, default3, default4, Tuple::tuple);
    }

    public static <T1, T2, T3, T4, T5> Seq<Tuple5<T1, T2, T3, T4, T5>> zipAll(Seq<? extends T1> s1, Seq<? extends T2> s2, Seq<? extends T3> s3, Seq<? extends T4> s4, Seq<? extends T5> s5, T1 default1, T2 default2, T3 default3, T4 default4, T5 default5) {
        return Seq.zipAll(s1, s2, s3, s4, s5, default1, default2, default3, default4, default5, Tuple::tuple);
    }

    public static <T1, T2, T3, T4, T5, T6> Seq<Tuple6<T1, T2, T3, T4, T5, T6>> zipAll(Seq<? extends T1> s1, Seq<? extends T2> s2, Seq<? extends T3> s3, Seq<? extends T4> s4, Seq<? extends T5> s5, Seq<? extends T6> s6, T1 default1, T2 default2, T3 default3, T4 default4, T5 default5, T6 default6) {
        return Seq.zipAll(s1, s2, s3, s4, s5, s6, default1, default2, default3, default4, default5, default6, Tuple::tuple);
    }

    public static <T1, T2, T3, T4, T5, T6, T7> Seq<Tuple7<T1, T2, T3, T4, T5, T6, T7>> zipAll(Seq<? extends T1> s1, Seq<? extends T2> s2, Seq<? extends T3> s3, Seq<? extends T4> s4, Seq<? extends T5> s5, Seq<? extends T6> s6, Seq<? extends T7> s7, T1 default1, T2 default2, T3 default3, T4 default4, T5 default5, T6 default6, T7 default7) {
        return Seq.zipAll(s1, s2, s3, s4, s5, s6, s7, default1, default2, default3, default4, default5, default6, default7, Tuple::tuple);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8> Seq<Tuple8<T1, T2, T3, T4, T5, T6, T7, T8>> zipAll(Seq<? extends T1> s1, Seq<? extends T2> s2, Seq<? extends T3> s3, Seq<? extends T4> s4, Seq<? extends T5> s5, Seq<? extends T6> s6, Seq<? extends T7> s7, Seq<? extends T8> s8, T1 default1, T2 default2, T3 default3, T4 default4, T5 default5, T6 default6, T7 default7, T8 default8) {
        return Seq.zipAll(s1, s2, s3, s4, s5, s6, s7, s8, default1, default2, default3, default4, default5, default6, default7, default8, Tuple::tuple);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9> Seq<Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> zipAll(Seq<? extends T1> s1, Seq<? extends T2> s2, Seq<? extends T3> s3, Seq<? extends T4> s4, Seq<? extends T5> s5, Seq<? extends T6> s6, Seq<? extends T7> s7, Seq<? extends T8> s8, Seq<? extends T9> s9, T1 default1, T2 default2, T3 default3, T4 default4, T5 default5, T6 default6, T7 default7, T8 default8, T9 default9) {
        return Seq.zipAll(s1, s2, s3, s4, s5, s6, s7, s8, s9, default1, default2, default3, default4, default5, default6, default7, default8, default9, Tuple::tuple);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> Seq<Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> zipAll(Seq<? extends T1> s1, Seq<? extends T2> s2, Seq<? extends T3> s3, Seq<? extends T4> s4, Seq<? extends T5> s5, Seq<? extends T6> s6, Seq<? extends T7> s7, Seq<? extends T8> s8, Seq<? extends T9> s9, Seq<? extends T10> s10, T1 default1, T2 default2, T3 default3, T4 default4, T5 default5, T6 default6, T7 default7, T8 default8, T9 default9, T10 default10) {
        return Seq.zipAll(s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, default1, default2, default3, default4, default5, default6, default7, default8, default9, default10, Tuple::tuple);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> Seq<Tuple11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> zipAll(Seq<? extends T1> s1, Seq<? extends T2> s2, Seq<? extends T3> s3, Seq<? extends T4> s4, Seq<? extends T5> s5, Seq<? extends T6> s6, Seq<? extends T7> s7, Seq<? extends T8> s8, Seq<? extends T9> s9, Seq<? extends T10> s10, Seq<? extends T11> s11, T1 default1, T2 default2, T3 default3, T4 default4, T5 default5, T6 default6, T7 default7, T8 default8, T9 default9, T10 default10, T11 default11) {
        return Seq.zipAll(s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, default1, default2, default3, default4, default5, default6, default7, default8, default9, default10, default11, Tuple::tuple);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> Seq<Tuple12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>> zipAll(Seq<? extends T1> s1, Seq<? extends T2> s2, Seq<? extends T3> s3, Seq<? extends T4> s4, Seq<? extends T5> s5, Seq<? extends T6> s6, Seq<? extends T7> s7, Seq<? extends T8> s8, Seq<? extends T9> s9, Seq<? extends T10> s10, Seq<? extends T11> s11, Seq<? extends T12> s12, T1 default1, T2 default2, T3 default3, T4 default4, T5 default5, T6 default6, T7 default7, T8 default8, T9 default9, T10 default10, T11 default11, T12 default12) {
        return Seq.zipAll(s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, s12, default1, default2, default3, default4, default5, default6, default7, default8, default9, default10, default11, default12, Tuple::tuple);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> Seq<Tuple13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> zipAll(Seq<? extends T1> s1, Seq<? extends T2> s2, Seq<? extends T3> s3, Seq<? extends T4> s4, Seq<? extends T5> s5, Seq<? extends T6> s6, Seq<? extends T7> s7, Seq<? extends T8> s8, Seq<? extends T9> s9, Seq<? extends T10> s10, Seq<? extends T11> s11, Seq<? extends T12> s12, Seq<? extends T13> s13, T1 default1, T2 default2, T3 default3, T4 default4, T5 default5, T6 default6, T7 default7, T8 default8, T9 default9, T10 default10, T11 default11, T12 default12, T13 default13) {
        return Seq.zipAll(s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, s12, s13, default1, default2, default3, default4, default5, default6, default7, default8, default9, default10, default11, default12, default13, Tuple::tuple);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> Seq<Tuple14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> zipAll(Seq<? extends T1> s1, Seq<? extends T2> s2, Seq<? extends T3> s3, Seq<? extends T4> s4, Seq<? extends T5> s5, Seq<? extends T6> s6, Seq<? extends T7> s7, Seq<? extends T8> s8, Seq<? extends T9> s9, Seq<? extends T10> s10, Seq<? extends T11> s11, Seq<? extends T12> s12, Seq<? extends T13> s13, Seq<? extends T14> s14, T1 default1, T2 default2, T3 default3, T4 default4, T5 default5, T6 default6, T7 default7, T8 default8, T9 default9, T10 default10, T11 default11, T12 default12, T13 default13, T14 default14) {
        return Seq.zipAll(s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, s12, s13, s14, default1, default2, default3, default4, default5, default6, default7, default8, default9, default10, default11, default12, default13, default14, Tuple::tuple);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> Seq<Tuple15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> zipAll(Seq<? extends T1> s1, Seq<? extends T2> s2, Seq<? extends T3> s3, Seq<? extends T4> s4, Seq<? extends T5> s5, Seq<? extends T6> s6, Seq<? extends T7> s7, Seq<? extends T8> s8, Seq<? extends T9> s9, Seq<? extends T10> s10, Seq<? extends T11> s11, Seq<? extends T12> s12, Seq<? extends T13> s13, Seq<? extends T14> s14, Seq<? extends T15> s15, T1 default1, T2 default2, T3 default3, T4 default4, T5 default5, T6 default6, T7 default7, T8 default8, T9 default9, T10 default10, T11 default11, T12 default12, T13 default13, T14 default14, T15 default15) {
        return Seq.zipAll(s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, s12, s13, s14, s15, default1, default2, default3, default4, default5, default6, default7, default8, default9, default10, default11, default12, default13, default14, default15, Tuple::tuple);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> Seq<Tuple16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> zipAll(Seq<? extends T1> s1, Seq<? extends T2> s2, Seq<? extends T3> s3, Seq<? extends T4> s4, Seq<? extends T5> s5, Seq<? extends T6> s6, Seq<? extends T7> s7, Seq<? extends T8> s8, Seq<? extends T9> s9, Seq<? extends T10> s10, Seq<? extends T11> s11, Seq<? extends T12> s12, Seq<? extends T13> s13, Seq<? extends T14> s14, Seq<? extends T15> s15, Seq<? extends T16> s16, T1 default1, T2 default2, T3 default3, T4 default4, T5 default5, T6 default6, T7 default7, T8 default8, T9 default9, T10 default10, T11 default11, T12 default12, T13 default13, T14 default14, T15 default15, T16 default16) {
        return Seq.zipAll(s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, s12, s13, s14, s15, s16, default1, default2, default3, default4, default5, default6, default7, default8, default9, default10, default11, default12, default13, default14, default15, default16, Tuple::tuple);
    }

    public static <T1, T2, R> Seq<R> zipAll(Stream<? extends T1> s1, Stream<? extends T2> s2, T1 default1, T2 default2, BiFunction<? super T1, ? super T2, ? extends R> zipper) {
        return Seq.zipAll(Seq.seq(s1), Seq.seq(s2), default1, default2, zipper);
    }

    public static <T1, T2, T3, R> Seq<R> zipAll(Stream<? extends T1> s1, Stream<? extends T2> s2, Stream<? extends T3> s3, T1 default1, T2 default2, T3 default3, Function3<? super T1, ? super T2, ? super T3, ? extends R> zipper) {
        return Seq.zipAll(Seq.seq(s1), Seq.seq(s2), Seq.seq(s3), default1, default2, default3, zipper);
    }

    public static <T1, T2, T3, T4, R> Seq<R> zipAll(Stream<? extends T1> s1, Stream<? extends T2> s2, Stream<? extends T3> s3, Stream<? extends T4> s4, T1 default1, T2 default2, T3 default3, T4 default4, Function4<? super T1, ? super T2, ? super T3, ? super T4, ? extends R> zipper) {
        return Seq.zipAll(Seq.seq(s1), Seq.seq(s2), Seq.seq(s3), Seq.seq(s4), default1, default2, default3, default4, zipper);
    }

    public static <T1, T2, T3, T4, T5, R> Seq<R> zipAll(Stream<? extends T1> s1, Stream<? extends T2> s2, Stream<? extends T3> s3, Stream<? extends T4> s4, Stream<? extends T5> s5, T1 default1, T2 default2, T3 default3, T4 default4, T5 default5, Function5<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? extends R> zipper) {
        return Seq.zipAll(Seq.seq(s1), Seq.seq(s2), Seq.seq(s3), Seq.seq(s4), Seq.seq(s5), default1, default2, default3, default4, default5, zipper);
    }

    public static <T1, T2, T3, T4, T5, T6, R> Seq<R> zipAll(Stream<? extends T1> s1, Stream<? extends T2> s2, Stream<? extends T3> s3, Stream<? extends T4> s4, Stream<? extends T5> s5, Stream<? extends T6> s6, T1 default1, T2 default2, T3 default3, T4 default4, T5 default5, T6 default6, Function6<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? extends R> zipper) {
        return Seq.zipAll(Seq.seq(s1), Seq.seq(s2), Seq.seq(s3), Seq.seq(s4), Seq.seq(s5), Seq.seq(s6), default1, default2, default3, default4, default5, default6, zipper);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, R> Seq<R> zipAll(Stream<? extends T1> s1, Stream<? extends T2> s2, Stream<? extends T3> s3, Stream<? extends T4> s4, Stream<? extends T5> s5, Stream<? extends T6> s6, Stream<? extends T7> s7, T1 default1, T2 default2, T3 default3, T4 default4, T5 default5, T6 default6, T7 default7, Function7<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? extends R> zipper) {
        return Seq.zipAll(Seq.seq(s1), Seq.seq(s2), Seq.seq(s3), Seq.seq(s4), Seq.seq(s5), Seq.seq(s6), Seq.seq(s7), default1, default2, default3, default4, default5, default6, default7, zipper);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, R> Seq<R> zipAll(Stream<? extends T1> s1, Stream<? extends T2> s2, Stream<? extends T3> s3, Stream<? extends T4> s4, Stream<? extends T5> s5, Stream<? extends T6> s6, Stream<? extends T7> s7, Stream<? extends T8> s8, T1 default1, T2 default2, T3 default3, T4 default4, T5 default5, T6 default6, T7 default7, T8 default8, Function8<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? extends R> zipper) {
        return Seq.zipAll(Seq.seq(s1), Seq.seq(s2), Seq.seq(s3), Seq.seq(s4), Seq.seq(s5), Seq.seq(s6), Seq.seq(s7), Seq.seq(s8), default1, default2, default3, default4, default5, default6, default7, default8, zipper);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, R> Seq<R> zipAll(Stream<? extends T1> s1, Stream<? extends T2> s2, Stream<? extends T3> s3, Stream<? extends T4> s4, Stream<? extends T5> s5, Stream<? extends T6> s6, Stream<? extends T7> s7, Stream<? extends T8> s8, Stream<? extends T9> s9, T1 default1, T2 default2, T3 default3, T4 default4, T5 default5, T6 default6, T7 default7, T8 default8, T9 default9, Function9<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? extends R> zipper) {
        return Seq.zipAll(Seq.seq(s1), Seq.seq(s2), Seq.seq(s3), Seq.seq(s4), Seq.seq(s5), Seq.seq(s6), Seq.seq(s7), Seq.seq(s8), Seq.seq(s9), default1, default2, default3, default4, default5, default6, default7, default8, default9, zipper);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, R> Seq<R> zipAll(Stream<? extends T1> s1, Stream<? extends T2> s2, Stream<? extends T3> s3, Stream<? extends T4> s4, Stream<? extends T5> s5, Stream<? extends T6> s6, Stream<? extends T7> s7, Stream<? extends T8> s8, Stream<? extends T9> s9, Stream<? extends T10> s10, T1 default1, T2 default2, T3 default3, T4 default4, T5 default5, T6 default6, T7 default7, T8 default8, T9 default9, T10 default10, Function10<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? super T10, ? extends R> zipper) {
        return Seq.zipAll(Seq.seq(s1), Seq.seq(s2), Seq.seq(s3), Seq.seq(s4), Seq.seq(s5), Seq.seq(s6), Seq.seq(s7), Seq.seq(s8), Seq.seq(s9), Seq.seq(s10), default1, default2, default3, default4, default5, default6, default7, default8, default9, default10, zipper);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, R> Seq<R> zipAll(Stream<? extends T1> s1, Stream<? extends T2> s2, Stream<? extends T3> s3, Stream<? extends T4> s4, Stream<? extends T5> s5, Stream<? extends T6> s6, Stream<? extends T7> s7, Stream<? extends T8> s8, Stream<? extends T9> s9, Stream<? extends T10> s10, Stream<? extends T11> s11, T1 default1, T2 default2, T3 default3, T4 default4, T5 default5, T6 default6, T7 default7, T8 default8, T9 default9, T10 default10, T11 default11, Function11<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? super T10, ? super T11, ? extends R> zipper) {
        return Seq.zipAll(Seq.seq(s1), Seq.seq(s2), Seq.seq(s3), Seq.seq(s4), Seq.seq(s5), Seq.seq(s6), Seq.seq(s7), Seq.seq(s8), Seq.seq(s9), Seq.seq(s10), Seq.seq(s11), default1, default2, default3, default4, default5, default6, default7, default8, default9, default10, default11, zipper);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, R> Seq<R> zipAll(Stream<? extends T1> s1, Stream<? extends T2> s2, Stream<? extends T3> s3, Stream<? extends T4> s4, Stream<? extends T5> s5, Stream<? extends T6> s6, Stream<? extends T7> s7, Stream<? extends T8> s8, Stream<? extends T9> s9, Stream<? extends T10> s10, Stream<? extends T11> s11, Stream<? extends T12> s12, T1 default1, T2 default2, T3 default3, T4 default4, T5 default5, T6 default6, T7 default7, T8 default8, T9 default9, T10 default10, T11 default11, T12 default12, Function12<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? super T10, ? super T11, ? super T12, ? extends R> zipper) {
        return Seq.zipAll(Seq.seq(s1), Seq.seq(s2), Seq.seq(s3), Seq.seq(s4), Seq.seq(s5), Seq.seq(s6), Seq.seq(s7), Seq.seq(s8), Seq.seq(s9), Seq.seq(s10), Seq.seq(s11), Seq.seq(s12), default1, default2, default3, default4, default5, default6, default7, default8, default9, default10, default11, default12, zipper);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, R> Seq<R> zipAll(Stream<? extends T1> s1, Stream<? extends T2> s2, Stream<? extends T3> s3, Stream<? extends T4> s4, Stream<? extends T5> s5, Stream<? extends T6> s6, Stream<? extends T7> s7, Stream<? extends T8> s8, Stream<? extends T9> s9, Stream<? extends T10> s10, Stream<? extends T11> s11, Stream<? extends T12> s12, Stream<? extends T13> s13, T1 default1, T2 default2, T3 default3, T4 default4, T5 default5, T6 default6, T7 default7, T8 default8, T9 default9, T10 default10, T11 default11, T12 default12, T13 default13, Function13<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? super T10, ? super T11, ? super T12, ? super T13, ? extends R> zipper) {
        return Seq.zipAll(Seq.seq(s1), Seq.seq(s2), Seq.seq(s3), Seq.seq(s4), Seq.seq(s5), Seq.seq(s6), Seq.seq(s7), Seq.seq(s8), Seq.seq(s9), Seq.seq(s10), Seq.seq(s11), Seq.seq(s12), Seq.seq(s13), default1, default2, default3, default4, default5, default6, default7, default8, default9, default10, default11, default12, default13, zipper);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, R> Seq<R> zipAll(Stream<? extends T1> s1, Stream<? extends T2> s2, Stream<? extends T3> s3, Stream<? extends T4> s4, Stream<? extends T5> s5, Stream<? extends T6> s6, Stream<? extends T7> s7, Stream<? extends T8> s8, Stream<? extends T9> s9, Stream<? extends T10> s10, Stream<? extends T11> s11, Stream<? extends T12> s12, Stream<? extends T13> s13, Stream<? extends T14> s14, T1 default1, T2 default2, T3 default3, T4 default4, T5 default5, T6 default6, T7 default7, T8 default8, T9 default9, T10 default10, T11 default11, T12 default12, T13 default13, T14 default14, Function14<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? super T10, ? super T11, ? super T12, ? super T13, ? super T14, ? extends R> zipper) {
        return Seq.zipAll(Seq.seq(s1), Seq.seq(s2), Seq.seq(s3), Seq.seq(s4), Seq.seq(s5), Seq.seq(s6), Seq.seq(s7), Seq.seq(s8), Seq.seq(s9), Seq.seq(s10), Seq.seq(s11), Seq.seq(s12), Seq.seq(s13), Seq.seq(s14), default1, default2, default3, default4, default5, default6, default7, default8, default9, default10, default11, default12, default13, default14, zipper);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, R> Seq<R> zipAll(Stream<? extends T1> s1, Stream<? extends T2> s2, Stream<? extends T3> s3, Stream<? extends T4> s4, Stream<? extends T5> s5, Stream<? extends T6> s6, Stream<? extends T7> s7, Stream<? extends T8> s8, Stream<? extends T9> s9, Stream<? extends T10> s10, Stream<? extends T11> s11, Stream<? extends T12> s12, Stream<? extends T13> s13, Stream<? extends T14> s14, Stream<? extends T15> s15, T1 default1, T2 default2, T3 default3, T4 default4, T5 default5, T6 default6, T7 default7, T8 default8, T9 default9, T10 default10, T11 default11, T12 default12, T13 default13, T14 default14, T15 default15, Function15<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? super T10, ? super T11, ? super T12, ? super T13, ? super T14, ? super T15, ? extends R> zipper) {
        return Seq.zipAll(Seq.seq(s1), Seq.seq(s2), Seq.seq(s3), Seq.seq(s4), Seq.seq(s5), Seq.seq(s6), Seq.seq(s7), Seq.seq(s8), Seq.seq(s9), Seq.seq(s10), Seq.seq(s11), Seq.seq(s12), Seq.seq(s13), Seq.seq(s14), Seq.seq(s15), default1, default2, default3, default4, default5, default6, default7, default8, default9, default10, default11, default12, default13, default14, default15, zipper);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, R> Seq<R> zipAll(Stream<? extends T1> s1, Stream<? extends T2> s2, Stream<? extends T3> s3, Stream<? extends T4> s4, Stream<? extends T5> s5, Stream<? extends T6> s6, Stream<? extends T7> s7, Stream<? extends T8> s8, Stream<? extends T9> s9, Stream<? extends T10> s10, Stream<? extends T11> s11, Stream<? extends T12> s12, Stream<? extends T13> s13, Stream<? extends T14> s14, Stream<? extends T15> s15, Stream<? extends T16> s16, T1 default1, T2 default2, T3 default3, T4 default4, T5 default5, T6 default6, T7 default7, T8 default8, T9 default9, T10 default10, T11 default11, T12 default12, T13 default13, T14 default14, T15 default15, T16 default16, Function16<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? super T10, ? super T11, ? super T12, ? super T13, ? super T14, ? super T15, ? super T16, ? extends R> zipper) {
        return Seq.zipAll(Seq.seq(s1), Seq.seq(s2), Seq.seq(s3), Seq.seq(s4), Seq.seq(s5), Seq.seq(s6), Seq.seq(s7), Seq.seq(s8), Seq.seq(s9), Seq.seq(s10), Seq.seq(s11), Seq.seq(s12), Seq.seq(s13), Seq.seq(s14), Seq.seq(s15), Seq.seq(s16), default1, default2, default3, default4, default5, default6, default7, default8, default9, default10, default11, default12, default13, default14, default15, default16, zipper);
    }

    public static <T1, T2, R> Seq<R> zipAll(Iterable<? extends T1> s1, Iterable<? extends T2> s2, T1 default1, T2 default2, BiFunction<? super T1, ? super T2, ? extends R> zipper) {
        return Seq.zipAll(Seq.seq(s1), Seq.seq(s2), default1, default2, zipper);
    }

    public static <T1, T2, T3, R> Seq<R> zipAll(Iterable<? extends T1> s1, Iterable<? extends T2> s2, Iterable<? extends T3> s3, T1 default1, T2 default2, T3 default3, Function3<? super T1, ? super T2, ? super T3, ? extends R> zipper) {
        return Seq.zipAll(Seq.seq(s1), Seq.seq(s2), Seq.seq(s3), default1, default2, default3, zipper);
    }

    public static <T1, T2, T3, T4, R> Seq<R> zipAll(Iterable<? extends T1> s1, Iterable<? extends T2> s2, Iterable<? extends T3> s3, Iterable<? extends T4> s4, T1 default1, T2 default2, T3 default3, T4 default4, Function4<? super T1, ? super T2, ? super T3, ? super T4, ? extends R> zipper) {
        return Seq.zipAll(Seq.seq(s1), Seq.seq(s2), Seq.seq(s3), Seq.seq(s4), default1, default2, default3, default4, zipper);
    }

    public static <T1, T2, T3, T4, T5, R> Seq<R> zipAll(Iterable<? extends T1> s1, Iterable<? extends T2> s2, Iterable<? extends T3> s3, Iterable<? extends T4> s4, Iterable<? extends T5> s5, T1 default1, T2 default2, T3 default3, T4 default4, T5 default5, Function5<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? extends R> zipper) {
        return Seq.zipAll(Seq.seq(s1), Seq.seq(s2), Seq.seq(s3), Seq.seq(s4), Seq.seq(s5), default1, default2, default3, default4, default5, zipper);
    }

    public static <T1, T2, T3, T4, T5, T6, R> Seq<R> zipAll(Iterable<? extends T1> s1, Iterable<? extends T2> s2, Iterable<? extends T3> s3, Iterable<? extends T4> s4, Iterable<? extends T5> s5, Iterable<? extends T6> s6, T1 default1, T2 default2, T3 default3, T4 default4, T5 default5, T6 default6, Function6<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? extends R> zipper) {
        return Seq.zipAll(Seq.seq(s1), Seq.seq(s2), Seq.seq(s3), Seq.seq(s4), Seq.seq(s5), Seq.seq(s6), default1, default2, default3, default4, default5, default6, zipper);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, R> Seq<R> zipAll(Iterable<? extends T1> s1, Iterable<? extends T2> s2, Iterable<? extends T3> s3, Iterable<? extends T4> s4, Iterable<? extends T5> s5, Iterable<? extends T6> s6, Iterable<? extends T7> s7, T1 default1, T2 default2, T3 default3, T4 default4, T5 default5, T6 default6, T7 default7, Function7<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? extends R> zipper) {
        return Seq.zipAll(Seq.seq(s1), Seq.seq(s2), Seq.seq(s3), Seq.seq(s4), Seq.seq(s5), Seq.seq(s6), Seq.seq(s7), default1, default2, default3, default4, default5, default6, default7, zipper);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, R> Seq<R> zipAll(Iterable<? extends T1> s1, Iterable<? extends T2> s2, Iterable<? extends T3> s3, Iterable<? extends T4> s4, Iterable<? extends T5> s5, Iterable<? extends T6> s6, Iterable<? extends T7> s7, Iterable<? extends T8> s8, T1 default1, T2 default2, T3 default3, T4 default4, T5 default5, T6 default6, T7 default7, T8 default8, Function8<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? extends R> zipper) {
        return Seq.zipAll(Seq.seq(s1), Seq.seq(s2), Seq.seq(s3), Seq.seq(s4), Seq.seq(s5), Seq.seq(s6), Seq.seq(s7), Seq.seq(s8), default1, default2, default3, default4, default5, default6, default7, default8, zipper);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, R> Seq<R> zipAll(Iterable<? extends T1> s1, Iterable<? extends T2> s2, Iterable<? extends T3> s3, Iterable<? extends T4> s4, Iterable<? extends T5> s5, Iterable<? extends T6> s6, Iterable<? extends T7> s7, Iterable<? extends T8> s8, Iterable<? extends T9> s9, T1 default1, T2 default2, T3 default3, T4 default4, T5 default5, T6 default6, T7 default7, T8 default8, T9 default9, Function9<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? extends R> zipper) {
        return Seq.zipAll(Seq.seq(s1), Seq.seq(s2), Seq.seq(s3), Seq.seq(s4), Seq.seq(s5), Seq.seq(s6), Seq.seq(s7), Seq.seq(s8), Seq.seq(s9), default1, default2, default3, default4, default5, default6, default7, default8, default9, zipper);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, R> Seq<R> zipAll(Iterable<? extends T1> s1, Iterable<? extends T2> s2, Iterable<? extends T3> s3, Iterable<? extends T4> s4, Iterable<? extends T5> s5, Iterable<? extends T6> s6, Iterable<? extends T7> s7, Iterable<? extends T8> s8, Iterable<? extends T9> s9, Iterable<? extends T10> s10, T1 default1, T2 default2, T3 default3, T4 default4, T5 default5, T6 default6, T7 default7, T8 default8, T9 default9, T10 default10, Function10<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? super T10, ? extends R> zipper) {
        return Seq.zipAll(Seq.seq(s1), Seq.seq(s2), Seq.seq(s3), Seq.seq(s4), Seq.seq(s5), Seq.seq(s6), Seq.seq(s7), Seq.seq(s8), Seq.seq(s9), Seq.seq(s10), default1, default2, default3, default4, default5, default6, default7, default8, default9, default10, zipper);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, R> Seq<R> zipAll(Iterable<? extends T1> s1, Iterable<? extends T2> s2, Iterable<? extends T3> s3, Iterable<? extends T4> s4, Iterable<? extends T5> s5, Iterable<? extends T6> s6, Iterable<? extends T7> s7, Iterable<? extends T8> s8, Iterable<? extends T9> s9, Iterable<? extends T10> s10, Iterable<? extends T11> s11, T1 default1, T2 default2, T3 default3, T4 default4, T5 default5, T6 default6, T7 default7, T8 default8, T9 default9, T10 default10, T11 default11, Function11<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? super T10, ? super T11, ? extends R> zipper) {
        return Seq.zipAll(Seq.seq(s1), Seq.seq(s2), Seq.seq(s3), Seq.seq(s4), Seq.seq(s5), Seq.seq(s6), Seq.seq(s7), Seq.seq(s8), Seq.seq(s9), Seq.seq(s10), Seq.seq(s11), default1, default2, default3, default4, default5, default6, default7, default8, default9, default10, default11, zipper);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, R> Seq<R> zipAll(Iterable<? extends T1> s1, Iterable<? extends T2> s2, Iterable<? extends T3> s3, Iterable<? extends T4> s4, Iterable<? extends T5> s5, Iterable<? extends T6> s6, Iterable<? extends T7> s7, Iterable<? extends T8> s8, Iterable<? extends T9> s9, Iterable<? extends T10> s10, Iterable<? extends T11> s11, Iterable<? extends T12> s12, T1 default1, T2 default2, T3 default3, T4 default4, T5 default5, T6 default6, T7 default7, T8 default8, T9 default9, T10 default10, T11 default11, T12 default12, Function12<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? super T10, ? super T11, ? super T12, ? extends R> zipper) {
        return Seq.zipAll(Seq.seq(s1), Seq.seq(s2), Seq.seq(s3), Seq.seq(s4), Seq.seq(s5), Seq.seq(s6), Seq.seq(s7), Seq.seq(s8), Seq.seq(s9), Seq.seq(s10), Seq.seq(s11), Seq.seq(s12), default1, default2, default3, default4, default5, default6, default7, default8, default9, default10, default11, default12, zipper);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, R> Seq<R> zipAll(Iterable<? extends T1> s1, Iterable<? extends T2> s2, Iterable<? extends T3> s3, Iterable<? extends T4> s4, Iterable<? extends T5> s5, Iterable<? extends T6> s6, Iterable<? extends T7> s7, Iterable<? extends T8> s8, Iterable<? extends T9> s9, Iterable<? extends T10> s10, Iterable<? extends T11> s11, Iterable<? extends T12> s12, Iterable<? extends T13> s13, T1 default1, T2 default2, T3 default3, T4 default4, T5 default5, T6 default6, T7 default7, T8 default8, T9 default9, T10 default10, T11 default11, T12 default12, T13 default13, Function13<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? super T10, ? super T11, ? super T12, ? super T13, ? extends R> zipper) {
        return Seq.zipAll(Seq.seq(s1), Seq.seq(s2), Seq.seq(s3), Seq.seq(s4), Seq.seq(s5), Seq.seq(s6), Seq.seq(s7), Seq.seq(s8), Seq.seq(s9), Seq.seq(s10), Seq.seq(s11), Seq.seq(s12), Seq.seq(s13), default1, default2, default3, default4, default5, default6, default7, default8, default9, default10, default11, default12, default13, zipper);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, R> Seq<R> zipAll(Iterable<? extends T1> s1, Iterable<? extends T2> s2, Iterable<? extends T3> s3, Iterable<? extends T4> s4, Iterable<? extends T5> s5, Iterable<? extends T6> s6, Iterable<? extends T7> s7, Iterable<? extends T8> s8, Iterable<? extends T9> s9, Iterable<? extends T10> s10, Iterable<? extends T11> s11, Iterable<? extends T12> s12, Iterable<? extends T13> s13, Iterable<? extends T14> s14, T1 default1, T2 default2, T3 default3, T4 default4, T5 default5, T6 default6, T7 default7, T8 default8, T9 default9, T10 default10, T11 default11, T12 default12, T13 default13, T14 default14, Function14<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? super T10, ? super T11, ? super T12, ? super T13, ? super T14, ? extends R> zipper) {
        return Seq.zipAll(Seq.seq(s1), Seq.seq(s2), Seq.seq(s3), Seq.seq(s4), Seq.seq(s5), Seq.seq(s6), Seq.seq(s7), Seq.seq(s8), Seq.seq(s9), Seq.seq(s10), Seq.seq(s11), Seq.seq(s12), Seq.seq(s13), Seq.seq(s14), default1, default2, default3, default4, default5, default6, default7, default8, default9, default10, default11, default12, default13, default14, zipper);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, R> Seq<R> zipAll(Iterable<? extends T1> s1, Iterable<? extends T2> s2, Iterable<? extends T3> s3, Iterable<? extends T4> s4, Iterable<? extends T5> s5, Iterable<? extends T6> s6, Iterable<? extends T7> s7, Iterable<? extends T8> s8, Iterable<? extends T9> s9, Iterable<? extends T10> s10, Iterable<? extends T11> s11, Iterable<? extends T12> s12, Iterable<? extends T13> s13, Iterable<? extends T14> s14, Iterable<? extends T15> s15, T1 default1, T2 default2, T3 default3, T4 default4, T5 default5, T6 default6, T7 default7, T8 default8, T9 default9, T10 default10, T11 default11, T12 default12, T13 default13, T14 default14, T15 default15, Function15<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? super T10, ? super T11, ? super T12, ? super T13, ? super T14, ? super T15, ? extends R> zipper) {
        return Seq.zipAll(Seq.seq(s1), Seq.seq(s2), Seq.seq(s3), Seq.seq(s4), Seq.seq(s5), Seq.seq(s6), Seq.seq(s7), Seq.seq(s8), Seq.seq(s9), Seq.seq(s10), Seq.seq(s11), Seq.seq(s12), Seq.seq(s13), Seq.seq(s14), Seq.seq(s15), default1, default2, default3, default4, default5, default6, default7, default8, default9, default10, default11, default12, default13, default14, default15, zipper);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, R> Seq<R> zipAll(Iterable<? extends T1> s1, Iterable<? extends T2> s2, Iterable<? extends T3> s3, Iterable<? extends T4> s4, Iterable<? extends T5> s5, Iterable<? extends T6> s6, Iterable<? extends T7> s7, Iterable<? extends T8> s8, Iterable<? extends T9> s9, Iterable<? extends T10> s10, Iterable<? extends T11> s11, Iterable<? extends T12> s12, Iterable<? extends T13> s13, Iterable<? extends T14> s14, Iterable<? extends T15> s15, Iterable<? extends T16> s16, T1 default1, T2 default2, T3 default3, T4 default4, T5 default5, T6 default6, T7 default7, T8 default8, T9 default9, T10 default10, T11 default11, T12 default12, T13 default13, T14 default14, T15 default15, T16 default16, Function16<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? super T10, ? super T11, ? super T12, ? super T13, ? super T14, ? super T15, ? super T16, ? extends R> zipper) {
        return Seq.zipAll(Seq.seq(s1), Seq.seq(s2), Seq.seq(s3), Seq.seq(s4), Seq.seq(s5), Seq.seq(s6), Seq.seq(s7), Seq.seq(s8), Seq.seq(s9), Seq.seq(s10), Seq.seq(s11), Seq.seq(s12), Seq.seq(s13), Seq.seq(s14), Seq.seq(s15), Seq.seq(s16), default1, default2, default3, default4, default5, default6, default7, default8, default9, default10, default11, default12, default13, default14, default15, default16, zipper);
    }

    public static <T1, T2, R> Seq<R> zipAll(Seq<? extends T1> s1, Seq<? extends T2> s2, final T1 default1, final T2 default2, final BiFunction<? super T1, ? super T2, ? extends R> zipper) {
        final Iterator it1 = s1.iterator();
        final Iterator it2 = s2.iterator();
        class ZipAll
        implements Iterator<R> {
            ZipAll() {
            }

            @Override
            public boolean hasNext() {
                return it1.hasNext() || it2.hasNext();
            }

            @Override
            public R next() {
                boolean b1 = it1.hasNext();
                boolean b2 = it2.hasNext();
                if (!b1 && !b2) {
                    throw new NoSuchElementException("next on empty iterator");
                }
                return zipper.apply(b1 ? it1.next() : default1, b2 ? it2.next() : default2);
            }
        }
        return Seq.seq(new ZipAll()).onClose(SeqUtils.closeAll(s1, s2));
    }

    public static <T1, T2, T3, R> Seq<R> zipAll(Seq<? extends T1> s1, Seq<? extends T2> s2, Seq<? extends T3> s3, final T1 default1, final T2 default2, final T3 default3, final Function3<? super T1, ? super T2, ? super T3, ? extends R> zipper) {
        final Iterator it1 = s1.iterator();
        final Iterator it2 = s2.iterator();
        final Iterator it3 = s3.iterator();
        class ZipAll
        implements Iterator<R> {
            ZipAll() {
            }

            @Override
            public boolean hasNext() {
                return it1.hasNext() || it2.hasNext() || it3.hasNext();
            }

            @Override
            public R next() {
                boolean b1 = it1.hasNext();
                boolean b2 = it2.hasNext();
                boolean b3 = it3.hasNext();
                if (!(b1 || b2 || b3)) {
                    throw new NoSuchElementException("next on empty iterator");
                }
                return zipper.apply(b1 ? it1.next() : default1, b2 ? it2.next() : default2, b3 ? it3.next() : default3);
            }
        }
        return Seq.seq(new ZipAll()).onClose(SeqUtils.closeAll(s1, s2, s3));
    }

    public static <T1, T2, T3, T4, R> Seq<R> zipAll(Seq<? extends T1> s1, Seq<? extends T2> s2, Seq<? extends T3> s3, Seq<? extends T4> s4, final T1 default1, final T2 default2, final T3 default3, final T4 default4, final Function4<? super T1, ? super T2, ? super T3, ? super T4, ? extends R> zipper) {
        final Iterator it1 = s1.iterator();
        final Iterator it2 = s2.iterator();
        final Iterator it3 = s3.iterator();
        final Iterator it4 = s4.iterator();
        class ZipAll
        implements Iterator<R> {
            ZipAll() {
            }

            @Override
            public boolean hasNext() {
                return it1.hasNext() || it2.hasNext() || it3.hasNext() || it4.hasNext();
            }

            @Override
            public R next() {
                boolean b1 = it1.hasNext();
                boolean b2 = it2.hasNext();
                boolean b3 = it3.hasNext();
                boolean b4 = it4.hasNext();
                if (!(b1 || b2 || b3 || b4)) {
                    throw new NoSuchElementException("next on empty iterator");
                }
                return zipper.apply(b1 ? it1.next() : default1, b2 ? it2.next() : default2, b3 ? it3.next() : default3, b4 ? it4.next() : default4);
            }
        }
        return Seq.seq(new ZipAll()).onClose(SeqUtils.closeAll(s1, s2, s3, s4));
    }

    public static <T1, T2, T3, T4, T5, R> Seq<R> zipAll(Seq<? extends T1> s1, Seq<? extends T2> s2, Seq<? extends T3> s3, Seq<? extends T4> s4, Seq<? extends T5> s5, final T1 default1, final T2 default2, final T3 default3, final T4 default4, final T5 default5, final Function5<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? extends R> zipper) {
        final Iterator it1 = s1.iterator();
        final Iterator it2 = s2.iterator();
        final Iterator it3 = s3.iterator();
        final Iterator it4 = s4.iterator();
        final Iterator it5 = s5.iterator();
        class ZipAll
        implements Iterator<R> {
            ZipAll() {
            }

            @Override
            public boolean hasNext() {
                return it1.hasNext() || it2.hasNext() || it3.hasNext() || it4.hasNext() || it5.hasNext();
            }

            @Override
            public R next() {
                boolean b1 = it1.hasNext();
                boolean b2 = it2.hasNext();
                boolean b3 = it3.hasNext();
                boolean b4 = it4.hasNext();
                boolean b5 = it5.hasNext();
                if (!(b1 || b2 || b3 || b4 || b5)) {
                    throw new NoSuchElementException("next on empty iterator");
                }
                return zipper.apply(b1 ? it1.next() : default1, b2 ? it2.next() : default2, b3 ? it3.next() : default3, b4 ? it4.next() : default4, b5 ? it5.next() : default5);
            }
        }
        return Seq.seq(new ZipAll()).onClose(SeqUtils.closeAll(s1, s2, s3, s4, s5));
    }

    public static <T1, T2, T3, T4, T5, T6, R> Seq<R> zipAll(Seq<? extends T1> s1, Seq<? extends T2> s2, Seq<? extends T3> s3, Seq<? extends T4> s4, Seq<? extends T5> s5, Seq<? extends T6> s6, final T1 default1, final T2 default2, final T3 default3, final T4 default4, final T5 default5, final T6 default6, final Function6<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? extends R> zipper) {
        final Iterator it1 = s1.iterator();
        final Iterator it2 = s2.iterator();
        final Iterator it3 = s3.iterator();
        final Iterator it4 = s4.iterator();
        final Iterator it5 = s5.iterator();
        final Iterator it6 = s6.iterator();
        class ZipAll
        implements Iterator<R> {
            ZipAll() {
            }

            @Override
            public boolean hasNext() {
                return it1.hasNext() || it2.hasNext() || it3.hasNext() || it4.hasNext() || it5.hasNext() || it6.hasNext();
            }

            @Override
            public R next() {
                boolean b1 = it1.hasNext();
                boolean b2 = it2.hasNext();
                boolean b3 = it3.hasNext();
                boolean b4 = it4.hasNext();
                boolean b5 = it5.hasNext();
                boolean b6 = it6.hasNext();
                if (!(b1 || b2 || b3 || b4 || b5 || b6)) {
                    throw new NoSuchElementException("next on empty iterator");
                }
                return zipper.apply(b1 ? it1.next() : default1, b2 ? it2.next() : default2, b3 ? it3.next() : default3, b4 ? it4.next() : default4, b5 ? it5.next() : default5, b6 ? it6.next() : default6);
            }
        }
        return Seq.seq(new ZipAll()).onClose(SeqUtils.closeAll(s1, s2, s3, s4, s5, s6));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, R> Seq<R> zipAll(Seq<? extends T1> s1, Seq<? extends T2> s2, Seq<? extends T3> s3, Seq<? extends T4> s4, Seq<? extends T5> s5, Seq<? extends T6> s6, Seq<? extends T7> s7, final T1 default1, final T2 default2, final T3 default3, final T4 default4, final T5 default5, final T6 default6, final T7 default7, final Function7<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? extends R> zipper) {
        final Iterator it1 = s1.iterator();
        final Iterator it2 = s2.iterator();
        final Iterator it3 = s3.iterator();
        final Iterator it4 = s4.iterator();
        final Iterator it5 = s5.iterator();
        final Iterator it6 = s6.iterator();
        final Iterator it7 = s7.iterator();
        class ZipAll
        implements Iterator<R> {
            ZipAll() {
            }

            @Override
            public boolean hasNext() {
                return it1.hasNext() || it2.hasNext() || it3.hasNext() || it4.hasNext() || it5.hasNext() || it6.hasNext() || it7.hasNext();
            }

            @Override
            public R next() {
                boolean b1 = it1.hasNext();
                boolean b2 = it2.hasNext();
                boolean b3 = it3.hasNext();
                boolean b4 = it4.hasNext();
                boolean b5 = it5.hasNext();
                boolean b6 = it6.hasNext();
                boolean b7 = it7.hasNext();
                if (!(b1 || b2 || b3 || b4 || b5 || b6 || b7)) {
                    throw new NoSuchElementException("next on empty iterator");
                }
                return zipper.apply(b1 ? it1.next() : default1, b2 ? it2.next() : default2, b3 ? it3.next() : default3, b4 ? it4.next() : default4, b5 ? it5.next() : default5, b6 ? it6.next() : default6, b7 ? it7.next() : default7);
            }
        }
        return Seq.seq(new ZipAll()).onClose(SeqUtils.closeAll(s1, s2, s3, s4, s5, s6, s7));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, R> Seq<R> zipAll(Seq<? extends T1> s1, Seq<? extends T2> s2, Seq<? extends T3> s3, Seq<? extends T4> s4, Seq<? extends T5> s5, Seq<? extends T6> s6, Seq<? extends T7> s7, Seq<? extends T8> s8, final T1 default1, final T2 default2, final T3 default3, final T4 default4, final T5 default5, final T6 default6, final T7 default7, final T8 default8, final Function8<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? extends R> zipper) {
        final Iterator it1 = s1.iterator();
        final Iterator it2 = s2.iterator();
        final Iterator it3 = s3.iterator();
        final Iterator it4 = s4.iterator();
        final Iterator it5 = s5.iterator();
        final Iterator it6 = s6.iterator();
        final Iterator it7 = s7.iterator();
        final Iterator it8 = s8.iterator();
        class ZipAll
        implements Iterator<R> {
            ZipAll() {
            }

            @Override
            public boolean hasNext() {
                return it1.hasNext() || it2.hasNext() || it3.hasNext() || it4.hasNext() || it5.hasNext() || it6.hasNext() || it7.hasNext() || it8.hasNext();
            }

            @Override
            public R next() {
                boolean b1 = it1.hasNext();
                boolean b2 = it2.hasNext();
                boolean b3 = it3.hasNext();
                boolean b4 = it4.hasNext();
                boolean b5 = it5.hasNext();
                boolean b6 = it6.hasNext();
                boolean b7 = it7.hasNext();
                boolean b8 = it8.hasNext();
                if (!(b1 || b2 || b3 || b4 || b5 || b6 || b7 || b8)) {
                    throw new NoSuchElementException("next on empty iterator");
                }
                return zipper.apply(b1 ? it1.next() : default1, b2 ? it2.next() : default2, b3 ? it3.next() : default3, b4 ? it4.next() : default4, b5 ? it5.next() : default5, b6 ? it6.next() : default6, b7 ? it7.next() : default7, b8 ? it8.next() : default8);
            }
        }
        return Seq.seq(new ZipAll()).onClose(SeqUtils.closeAll(s1, s2, s3, s4, s5, s6, s7, s8));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, R> Seq<R> zipAll(Seq<? extends T1> s1, Seq<? extends T2> s2, Seq<? extends T3> s3, Seq<? extends T4> s4, Seq<? extends T5> s5, Seq<? extends T6> s6, Seq<? extends T7> s7, Seq<? extends T8> s8, Seq<? extends T9> s9, final T1 default1, final T2 default2, final T3 default3, final T4 default4, final T5 default5, final T6 default6, final T7 default7, final T8 default8, final T9 default9, final Function9<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? extends R> zipper) {
        final Iterator it1 = s1.iterator();
        final Iterator it2 = s2.iterator();
        final Iterator it3 = s3.iterator();
        final Iterator it4 = s4.iterator();
        final Iterator it5 = s5.iterator();
        final Iterator it6 = s6.iterator();
        final Iterator it7 = s7.iterator();
        final Iterator it8 = s8.iterator();
        final Iterator it9 = s9.iterator();
        class ZipAll
        implements Iterator<R> {
            ZipAll() {
            }

            @Override
            public boolean hasNext() {
                return it1.hasNext() || it2.hasNext() || it3.hasNext() || it4.hasNext() || it5.hasNext() || it6.hasNext() || it7.hasNext() || it8.hasNext() || it9.hasNext();
            }

            @Override
            public R next() {
                boolean b1 = it1.hasNext();
                boolean b2 = it2.hasNext();
                boolean b3 = it3.hasNext();
                boolean b4 = it4.hasNext();
                boolean b5 = it5.hasNext();
                boolean b6 = it6.hasNext();
                boolean b7 = it7.hasNext();
                boolean b8 = it8.hasNext();
                boolean b9 = it9.hasNext();
                if (!(b1 || b2 || b3 || b4 || b5 || b6 || b7 || b8 || b9)) {
                    throw new NoSuchElementException("next on empty iterator");
                }
                return zipper.apply(b1 ? it1.next() : default1, b2 ? it2.next() : default2, b3 ? it3.next() : default3, b4 ? it4.next() : default4, b5 ? it5.next() : default5, b6 ? it6.next() : default6, b7 ? it7.next() : default7, b8 ? it8.next() : default8, b9 ? it9.next() : default9);
            }
        }
        return Seq.seq(new ZipAll()).onClose(SeqUtils.closeAll(s1, s2, s3, s4, s5, s6, s7, s8, s9));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, R> Seq<R> zipAll(Seq<? extends T1> s1, Seq<? extends T2> s2, Seq<? extends T3> s3, Seq<? extends T4> s4, Seq<? extends T5> s5, Seq<? extends T6> s6, Seq<? extends T7> s7, Seq<? extends T8> s8, Seq<? extends T9> s9, Seq<? extends T10> s10, final T1 default1, final T2 default2, final T3 default3, final T4 default4, final T5 default5, final T6 default6, final T7 default7, final T8 default8, final T9 default9, final T10 default10, final Function10<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? super T10, ? extends R> zipper) {
        final Iterator it1 = s1.iterator();
        final Iterator it2 = s2.iterator();
        final Iterator it3 = s3.iterator();
        final Iterator it4 = s4.iterator();
        final Iterator it5 = s5.iterator();
        final Iterator it6 = s6.iterator();
        final Iterator it7 = s7.iterator();
        final Iterator it8 = s8.iterator();
        final Iterator it9 = s9.iterator();
        final Iterator it10 = s10.iterator();
        class ZipAll
        implements Iterator<R> {
            ZipAll() {
            }

            @Override
            public boolean hasNext() {
                return it1.hasNext() || it2.hasNext() || it3.hasNext() || it4.hasNext() || it5.hasNext() || it6.hasNext() || it7.hasNext() || it8.hasNext() || it9.hasNext() || it10.hasNext();
            }

            @Override
            public R next() {
                boolean b1 = it1.hasNext();
                boolean b2 = it2.hasNext();
                boolean b3 = it3.hasNext();
                boolean b4 = it4.hasNext();
                boolean b5 = it5.hasNext();
                boolean b6 = it6.hasNext();
                boolean b7 = it7.hasNext();
                boolean b8 = it8.hasNext();
                boolean b9 = it9.hasNext();
                boolean b10 = it10.hasNext();
                if (!(b1 || b2 || b3 || b4 || b5 || b6 || b7 || b8 || b9 || b10)) {
                    throw new NoSuchElementException("next on empty iterator");
                }
                return zipper.apply(b1 ? it1.next() : default1, b2 ? it2.next() : default2, b3 ? it3.next() : default3, b4 ? it4.next() : default4, b5 ? it5.next() : default5, b6 ? it6.next() : default6, b7 ? it7.next() : default7, b8 ? it8.next() : default8, b9 ? it9.next() : default9, b10 ? it10.next() : default10);
            }
        }
        return Seq.seq(new ZipAll()).onClose(SeqUtils.closeAll(s1, s2, s3, s4, s5, s6, s7, s8, s9, s10));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, R> Seq<R> zipAll(Seq<? extends T1> s1, Seq<? extends T2> s2, Seq<? extends T3> s3, Seq<? extends T4> s4, Seq<? extends T5> s5, Seq<? extends T6> s6, Seq<? extends T7> s7, Seq<? extends T8> s8, Seq<? extends T9> s9, Seq<? extends T10> s10, Seq<? extends T11> s11, final T1 default1, final T2 default2, final T3 default3, final T4 default4, final T5 default5, final T6 default6, final T7 default7, final T8 default8, final T9 default9, final T10 default10, final T11 default11, final Function11<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? super T10, ? super T11, ? extends R> zipper) {
        final Iterator it1 = s1.iterator();
        final Iterator it2 = s2.iterator();
        final Iterator it3 = s3.iterator();
        final Iterator it4 = s4.iterator();
        final Iterator it5 = s5.iterator();
        final Iterator it6 = s6.iterator();
        final Iterator it7 = s7.iterator();
        final Iterator it8 = s8.iterator();
        final Iterator it9 = s9.iterator();
        final Iterator it10 = s10.iterator();
        final Iterator it11 = s11.iterator();
        class ZipAll
        implements Iterator<R> {
            ZipAll() {
            }

            @Override
            public boolean hasNext() {
                return it1.hasNext() || it2.hasNext() || it3.hasNext() || it4.hasNext() || it5.hasNext() || it6.hasNext() || it7.hasNext() || it8.hasNext() || it9.hasNext() || it10.hasNext() || it11.hasNext();
            }

            @Override
            public R next() {
                boolean b1 = it1.hasNext();
                boolean b2 = it2.hasNext();
                boolean b3 = it3.hasNext();
                boolean b4 = it4.hasNext();
                boolean b5 = it5.hasNext();
                boolean b6 = it6.hasNext();
                boolean b7 = it7.hasNext();
                boolean b8 = it8.hasNext();
                boolean b9 = it9.hasNext();
                boolean b10 = it10.hasNext();
                boolean b11 = it11.hasNext();
                if (!(b1 || b2 || b3 || b4 || b5 || b6 || b7 || b8 || b9 || b10 || b11)) {
                    throw new NoSuchElementException("next on empty iterator");
                }
                return zipper.apply(b1 ? it1.next() : default1, b2 ? it2.next() : default2, b3 ? it3.next() : default3, b4 ? it4.next() : default4, b5 ? it5.next() : default5, b6 ? it6.next() : default6, b7 ? it7.next() : default7, b8 ? it8.next() : default8, b9 ? it9.next() : default9, b10 ? it10.next() : default10, b11 ? it11.next() : default11);
            }
        }
        return Seq.seq(new ZipAll()).onClose(SeqUtils.closeAll(s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, R> Seq<R> zipAll(Seq<? extends T1> s1, Seq<? extends T2> s2, Seq<? extends T3> s3, Seq<? extends T4> s4, Seq<? extends T5> s5, Seq<? extends T6> s6, Seq<? extends T7> s7, Seq<? extends T8> s8, Seq<? extends T9> s9, Seq<? extends T10> s10, Seq<? extends T11> s11, Seq<? extends T12> s12, final T1 default1, final T2 default2, final T3 default3, final T4 default4, final T5 default5, final T6 default6, final T7 default7, final T8 default8, final T9 default9, final T10 default10, final T11 default11, final T12 default12, final Function12<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? super T10, ? super T11, ? super T12, ? extends R> zipper) {
        final Iterator it1 = s1.iterator();
        final Iterator it2 = s2.iterator();
        final Iterator it3 = s3.iterator();
        final Iterator it4 = s4.iterator();
        final Iterator it5 = s5.iterator();
        final Iterator it6 = s6.iterator();
        final Iterator it7 = s7.iterator();
        final Iterator it8 = s8.iterator();
        final Iterator it9 = s9.iterator();
        final Iterator it10 = s10.iterator();
        final Iterator it11 = s11.iterator();
        final Iterator it12 = s12.iterator();
        class ZipAll
        implements Iterator<R> {
            ZipAll() {
            }

            @Override
            public boolean hasNext() {
                return it1.hasNext() || it2.hasNext() || it3.hasNext() || it4.hasNext() || it5.hasNext() || it6.hasNext() || it7.hasNext() || it8.hasNext() || it9.hasNext() || it10.hasNext() || it11.hasNext() || it12.hasNext();
            }

            @Override
            public R next() {
                boolean b1 = it1.hasNext();
                boolean b2 = it2.hasNext();
                boolean b3 = it3.hasNext();
                boolean b4 = it4.hasNext();
                boolean b5 = it5.hasNext();
                boolean b6 = it6.hasNext();
                boolean b7 = it7.hasNext();
                boolean b8 = it8.hasNext();
                boolean b9 = it9.hasNext();
                boolean b10 = it10.hasNext();
                boolean b11 = it11.hasNext();
                boolean b12 = it12.hasNext();
                if (!(b1 || b2 || b3 || b4 || b5 || b6 || b7 || b8 || b9 || b10 || b11 || b12)) {
                    throw new NoSuchElementException("next on empty iterator");
                }
                return zipper.apply(b1 ? it1.next() : default1, b2 ? it2.next() : default2, b3 ? it3.next() : default3, b4 ? it4.next() : default4, b5 ? it5.next() : default5, b6 ? it6.next() : default6, b7 ? it7.next() : default7, b8 ? it8.next() : default8, b9 ? it9.next() : default9, b10 ? it10.next() : default10, b11 ? it11.next() : default11, b12 ? it12.next() : default12);
            }
        }
        return Seq.seq(new ZipAll()).onClose(SeqUtils.closeAll(s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, s12));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, R> Seq<R> zipAll(Seq<? extends T1> s1, Seq<? extends T2> s2, Seq<? extends T3> s3, Seq<? extends T4> s4, Seq<? extends T5> s5, Seq<? extends T6> s6, Seq<? extends T7> s7, Seq<? extends T8> s8, Seq<? extends T9> s9, Seq<? extends T10> s10, Seq<? extends T11> s11, Seq<? extends T12> s12, Seq<? extends T13> s13, final T1 default1, final T2 default2, final T3 default3, final T4 default4, final T5 default5, final T6 default6, final T7 default7, final T8 default8, final T9 default9, final T10 default10, final T11 default11, final T12 default12, final T13 default13, final Function13<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? super T10, ? super T11, ? super T12, ? super T13, ? extends R> zipper) {
        final Iterator it1 = s1.iterator();
        final Iterator it2 = s2.iterator();
        final Iterator it3 = s3.iterator();
        final Iterator it4 = s4.iterator();
        final Iterator it5 = s5.iterator();
        final Iterator it6 = s6.iterator();
        final Iterator it7 = s7.iterator();
        final Iterator it8 = s8.iterator();
        final Iterator it9 = s9.iterator();
        final Iterator it10 = s10.iterator();
        final Iterator it11 = s11.iterator();
        final Iterator it12 = s12.iterator();
        final Iterator it13 = s13.iterator();
        class ZipAll
        implements Iterator<R> {
            ZipAll() {
            }

            @Override
            public boolean hasNext() {
                return it1.hasNext() || it2.hasNext() || it3.hasNext() || it4.hasNext() || it5.hasNext() || it6.hasNext() || it7.hasNext() || it8.hasNext() || it9.hasNext() || it10.hasNext() || it11.hasNext() || it12.hasNext() || it13.hasNext();
            }

            @Override
            public R next() {
                boolean b1 = it1.hasNext();
                boolean b2 = it2.hasNext();
                boolean b3 = it3.hasNext();
                boolean b4 = it4.hasNext();
                boolean b5 = it5.hasNext();
                boolean b6 = it6.hasNext();
                boolean b7 = it7.hasNext();
                boolean b8 = it8.hasNext();
                boolean b9 = it9.hasNext();
                boolean b10 = it10.hasNext();
                boolean b11 = it11.hasNext();
                boolean b12 = it12.hasNext();
                boolean b13 = it13.hasNext();
                if (!(b1 || b2 || b3 || b4 || b5 || b6 || b7 || b8 || b9 || b10 || b11 || b12 || b13)) {
                    throw new NoSuchElementException("next on empty iterator");
                }
                return zipper.apply(b1 ? it1.next() : default1, b2 ? it2.next() : default2, b3 ? it3.next() : default3, b4 ? it4.next() : default4, b5 ? it5.next() : default5, b6 ? it6.next() : default6, b7 ? it7.next() : default7, b8 ? it8.next() : default8, b9 ? it9.next() : default9, b10 ? it10.next() : default10, b11 ? it11.next() : default11, b12 ? it12.next() : default12, b13 ? it13.next() : default13);
            }
        }
        return Seq.seq(new ZipAll()).onClose(SeqUtils.closeAll(s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, s12, s13));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, R> Seq<R> zipAll(Seq<? extends T1> s1, Seq<? extends T2> s2, Seq<? extends T3> s3, Seq<? extends T4> s4, Seq<? extends T5> s5, Seq<? extends T6> s6, Seq<? extends T7> s7, Seq<? extends T8> s8, Seq<? extends T9> s9, Seq<? extends T10> s10, Seq<? extends T11> s11, Seq<? extends T12> s12, Seq<? extends T13> s13, Seq<? extends T14> s14, final T1 default1, final T2 default2, final T3 default3, final T4 default4, final T5 default5, final T6 default6, final T7 default7, final T8 default8, final T9 default9, final T10 default10, final T11 default11, final T12 default12, final T13 default13, final T14 default14, final Function14<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? super T10, ? super T11, ? super T12, ? super T13, ? super T14, ? extends R> zipper) {
        final Iterator it1 = s1.iterator();
        final Iterator it2 = s2.iterator();
        final Iterator it3 = s3.iterator();
        final Iterator it4 = s4.iterator();
        final Iterator it5 = s5.iterator();
        final Iterator it6 = s6.iterator();
        final Iterator it7 = s7.iterator();
        final Iterator it8 = s8.iterator();
        final Iterator it9 = s9.iterator();
        final Iterator it10 = s10.iterator();
        final Iterator it11 = s11.iterator();
        final Iterator it12 = s12.iterator();
        final Iterator it13 = s13.iterator();
        final Iterator it14 = s14.iterator();
        class ZipAll
        implements Iterator<R> {
            ZipAll() {
            }

            @Override
            public boolean hasNext() {
                return it1.hasNext() || it2.hasNext() || it3.hasNext() || it4.hasNext() || it5.hasNext() || it6.hasNext() || it7.hasNext() || it8.hasNext() || it9.hasNext() || it10.hasNext() || it11.hasNext() || it12.hasNext() || it13.hasNext() || it14.hasNext();
            }

            @Override
            public R next() {
                boolean b1 = it1.hasNext();
                boolean b2 = it2.hasNext();
                boolean b3 = it3.hasNext();
                boolean b4 = it4.hasNext();
                boolean b5 = it5.hasNext();
                boolean b6 = it6.hasNext();
                boolean b7 = it7.hasNext();
                boolean b8 = it8.hasNext();
                boolean b9 = it9.hasNext();
                boolean b10 = it10.hasNext();
                boolean b11 = it11.hasNext();
                boolean b12 = it12.hasNext();
                boolean b13 = it13.hasNext();
                boolean b14 = it14.hasNext();
                if (!(b1 || b2 || b3 || b4 || b5 || b6 || b7 || b8 || b9 || b10 || b11 || b12 || b13 || b14)) {
                    throw new NoSuchElementException("next on empty iterator");
                }
                return zipper.apply(b1 ? it1.next() : default1, b2 ? it2.next() : default2, b3 ? it3.next() : default3, b4 ? it4.next() : default4, b5 ? it5.next() : default5, b6 ? it6.next() : default6, b7 ? it7.next() : default7, b8 ? it8.next() : default8, b9 ? it9.next() : default9, b10 ? it10.next() : default10, b11 ? it11.next() : default11, b12 ? it12.next() : default12, b13 ? it13.next() : default13, b14 ? it14.next() : default14);
            }
        }
        return Seq.seq(new ZipAll()).onClose(SeqUtils.closeAll(s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, s12, s13, s14));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, R> Seq<R> zipAll(Seq<? extends T1> s1, Seq<? extends T2> s2, Seq<? extends T3> s3, Seq<? extends T4> s4, Seq<? extends T5> s5, Seq<? extends T6> s6, Seq<? extends T7> s7, Seq<? extends T8> s8, Seq<? extends T9> s9, Seq<? extends T10> s10, Seq<? extends T11> s11, Seq<? extends T12> s12, Seq<? extends T13> s13, Seq<? extends T14> s14, Seq<? extends T15> s15, final T1 default1, final T2 default2, final T3 default3, final T4 default4, final T5 default5, final T6 default6, final T7 default7, final T8 default8, final T9 default9, final T10 default10, final T11 default11, final T12 default12, final T13 default13, final T14 default14, final T15 default15, final Function15<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? super T10, ? super T11, ? super T12, ? super T13, ? super T14, ? super T15, ? extends R> zipper) {
        final Iterator it1 = s1.iterator();
        final Iterator it2 = s2.iterator();
        final Iterator it3 = s3.iterator();
        final Iterator it4 = s4.iterator();
        final Iterator it5 = s5.iterator();
        final Iterator it6 = s6.iterator();
        final Iterator it7 = s7.iterator();
        final Iterator it8 = s8.iterator();
        final Iterator it9 = s9.iterator();
        final Iterator it10 = s10.iterator();
        final Iterator it11 = s11.iterator();
        final Iterator it12 = s12.iterator();
        final Iterator it13 = s13.iterator();
        final Iterator it14 = s14.iterator();
        final Iterator it15 = s15.iterator();
        class ZipAll
        implements Iterator<R> {
            ZipAll() {
            }

            @Override
            public boolean hasNext() {
                return it1.hasNext() || it2.hasNext() || it3.hasNext() || it4.hasNext() || it5.hasNext() || it6.hasNext() || it7.hasNext() || it8.hasNext() || it9.hasNext() || it10.hasNext() || it11.hasNext() || it12.hasNext() || it13.hasNext() || it14.hasNext() || it15.hasNext();
            }

            @Override
            public R next() {
                boolean b1 = it1.hasNext();
                boolean b2 = it2.hasNext();
                boolean b3 = it3.hasNext();
                boolean b4 = it4.hasNext();
                boolean b5 = it5.hasNext();
                boolean b6 = it6.hasNext();
                boolean b7 = it7.hasNext();
                boolean b8 = it8.hasNext();
                boolean b9 = it9.hasNext();
                boolean b10 = it10.hasNext();
                boolean b11 = it11.hasNext();
                boolean b12 = it12.hasNext();
                boolean b13 = it13.hasNext();
                boolean b14 = it14.hasNext();
                boolean b15 = it15.hasNext();
                if (!(b1 || b2 || b3 || b4 || b5 || b6 || b7 || b8 || b9 || b10 || b11 || b12 || b13 || b14 || b15)) {
                    throw new NoSuchElementException("next on empty iterator");
                }
                return zipper.apply(b1 ? it1.next() : default1, b2 ? it2.next() : default2, b3 ? it3.next() : default3, b4 ? it4.next() : default4, b5 ? it5.next() : default5, b6 ? it6.next() : default6, b7 ? it7.next() : default7, b8 ? it8.next() : default8, b9 ? it9.next() : default9, b10 ? it10.next() : default10, b11 ? it11.next() : default11, b12 ? it12.next() : default12, b13 ? it13.next() : default13, b14 ? it14.next() : default14, b15 ? it15.next() : default15);
            }
        }
        return Seq.seq(new ZipAll()).onClose(SeqUtils.closeAll(s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, s12, s13, s14, s15));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, R> Seq<R> zipAll(Seq<? extends T1> s1, Seq<? extends T2> s2, Seq<? extends T3> s3, Seq<? extends T4> s4, Seq<? extends T5> s5, Seq<? extends T6> s6, Seq<? extends T7> s7, Seq<? extends T8> s8, Seq<? extends T9> s9, Seq<? extends T10> s10, Seq<? extends T11> s11, Seq<? extends T12> s12, Seq<? extends T13> s13, Seq<? extends T14> s14, Seq<? extends T15> s15, Seq<? extends T16> s16, final T1 default1, final T2 default2, final T3 default3, final T4 default4, final T5 default5, final T6 default6, final T7 default7, final T8 default8, final T9 default9, final T10 default10, final T11 default11, final T12 default12, final T13 default13, final T14 default14, final T15 default15, final T16 default16, final Function16<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? super T10, ? super T11, ? super T12, ? super T13, ? super T14, ? super T15, ? super T16, ? extends R> zipper) {
        final Iterator it1 = s1.iterator();
        final Iterator it2 = s2.iterator();
        final Iterator it3 = s3.iterator();
        final Iterator it4 = s4.iterator();
        final Iterator it5 = s5.iterator();
        final Iterator it6 = s6.iterator();
        final Iterator it7 = s7.iterator();
        final Iterator it8 = s8.iterator();
        final Iterator it9 = s9.iterator();
        final Iterator it10 = s10.iterator();
        final Iterator it11 = s11.iterator();
        final Iterator it12 = s12.iterator();
        final Iterator it13 = s13.iterator();
        final Iterator it14 = s14.iterator();
        final Iterator it15 = s15.iterator();
        final Iterator it16 = s16.iterator();
        class ZipAll
        implements Iterator<R> {
            ZipAll() {
            }

            @Override
            public boolean hasNext() {
                return it1.hasNext() || it2.hasNext() || it3.hasNext() || it4.hasNext() || it5.hasNext() || it6.hasNext() || it7.hasNext() || it8.hasNext() || it9.hasNext() || it10.hasNext() || it11.hasNext() || it12.hasNext() || it13.hasNext() || it14.hasNext() || it15.hasNext() || it16.hasNext();
            }

            @Override
            public R next() {
                boolean b1 = it1.hasNext();
                boolean b2 = it2.hasNext();
                boolean b3 = it3.hasNext();
                boolean b4 = it4.hasNext();
                boolean b5 = it5.hasNext();
                boolean b6 = it6.hasNext();
                boolean b7 = it7.hasNext();
                boolean b8 = it8.hasNext();
                boolean b9 = it9.hasNext();
                boolean b10 = it10.hasNext();
                boolean b11 = it11.hasNext();
                boolean b12 = it12.hasNext();
                boolean b13 = it13.hasNext();
                boolean b14 = it14.hasNext();
                boolean b15 = it15.hasNext();
                boolean b16 = it16.hasNext();
                if (!(b1 || b2 || b3 || b4 || b5 || b6 || b7 || b8 || b9 || b10 || b11 || b12 || b13 || b14 || b15 || b16)) {
                    throw new NoSuchElementException("next on empty iterator");
                }
                return zipper.apply(b1 ? it1.next() : default1, b2 ? it2.next() : default2, b3 ? it3.next() : default3, b4 ? it4.next() : default4, b5 ? it5.next() : default5, b6 ? it6.next() : default6, b7 ? it7.next() : default7, b8 ? it8.next() : default8, b9 ? it9.next() : default9, b10 ? it10.next() : default10, b11 ? it11.next() : default11, b12 ? it12.next() : default12, b13 ? it13.next() : default13, b14 ? it14.next() : default14, b15 ? it15.next() : default15, b16 ? it16.next() : default16);
            }
        }
        return Seq.seq(new ZipAll()).onClose(SeqUtils.closeAll(s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, s12, s13, s14, s15, s16));
    }

    default public Seq<Tuple2<T, Long>> zipWithIndex() {
        return Seq.zipWithIndex(this);
    }

    default public <R> Seq<R> zipWithIndex(BiFunction<? super T, ? super Long, ? extends R> zipper) {
        return Seq.zipWithIndex(this, zipper);
    }

    default public <U> U foldLeft(U seed, BiFunction<? super U, ? super T, ? extends U> function) {
        return Seq.foldLeft(this, seed, function);
    }

    default public <U> U foldRight(U seed, BiFunction<? super T, ? super U, ? extends U> function) {
        return Seq.foldRight(this, seed, function);
    }

    default public <U> Seq<U> scanLeft(U seed, BiFunction<? super U, ? super T, ? extends U> function) {
        return Seq.scanLeft(this, seed, function);
    }

    default public <U> Seq<U> scanRight(U seed, BiFunction<? super T, ? super U, ? extends U> function) {
        return Seq.scanRight(this, seed, function);
    }

    default public Seq<T> reverse() {
        return Seq.reverse(this);
    }

    default public Seq<T> shuffle() {
        return Seq.shuffle(this);
    }

    default public Seq<T> shuffle(Random random) {
        return Seq.shuffle(this, random);
    }

    default public Seq<T> skipWhile(Predicate<? super T> predicate) {
        return Seq.skipWhile(this, predicate);
    }

    default public Seq<T> skipWhileClosed(Predicate<? super T> predicate) {
        return Seq.skipWhileClosed(this, predicate);
    }

    default public Seq<T> skipUntil(Predicate<? super T> predicate) {
        return Seq.skipUntil(this, predicate);
    }

    default public Seq<T> skipUntilClosed(Predicate<? super T> predicate) {
        return Seq.skipUntilClosed(this, predicate);
    }

    default public Seq<T> limitWhile(Predicate<? super T> predicate) {
        return Seq.limitWhile(this, predicate);
    }

    default public Seq<T> limitWhileClosed(Predicate<? super T> predicate) {
        return Seq.limitWhileClosed(this, predicate);
    }

    default public Seq<T> limitUntil(Predicate<? super T> predicate) {
        return Seq.limitUntil(this, predicate);
    }

    default public Seq<T> limitUntilClosed(Predicate<? super T> predicate) {
        return Seq.limitUntilClosed(this, predicate);
    }

    default public Seq<T> intersperse(T value) {
        return Seq.intersperse(this, value);
    }

    default public Tuple2<Seq<T>, Seq<T>> duplicate() {
        return Seq.duplicate(this);
    }

    default public <K> Seq<Tuple2<K, Seq<T>>> grouped(Function<? super T, ? extends K> classifier) {
        return Seq.grouped(this, classifier);
    }

    default public <K, A, D> Seq<Tuple2<K, D>> grouped(Function<? super T, ? extends K> classifier, Collector<? super T, A, D> downstream) {
        return Seq.grouped(this, classifier, downstream);
    }

    default public Tuple2<Seq<T>, Seq<T>> partition(Predicate<? super T> predicate) {
        return Seq.partition(this, predicate);
    }

    default public Tuple2<Seq<T>, Seq<T>> splitAt(long position) {
        return Seq.splitAt(this, position);
    }

    default public Tuple2<Optional<T>, Seq<T>> splitAtHead() {
        return Seq.splitAtHead(this);
    }

    default public Seq<T> slice(long from, long to) {
        return Seq.slice(this, from, to);
    }

    default public boolean isEmpty() {
        return !this.findAny().isPresent();
    }

    default public boolean isNotEmpty() {
        return !this.isEmpty();
    }

    default public <U extends Comparable<? super U>> Seq<T> sorted(Function<? super T, ? extends U> function) {
        return this.sorted((Comparator)Comparator.comparing(function));
    }

    default public <U> Seq<T> sorted(Function<? super T, ? extends U> function, Comparator<? super U> comparator) {
        return this.sorted((Comparator)Comparator.comparing(function, comparator));
    }

    default public <U> Seq<U> ofType(Class<? extends U> type) {
        return Seq.ofType(this, type);
    }

    default public <U> Seq<U> cast(Class<? extends U> type) {
        return Seq.cast(this, type);
    }

    default public Seq<Seq<T>> sliding(long size) {
        if (size <= 0L) {
            throw new IllegalArgumentException("Size must be >= 1");
        }
        return this.window(0L, size - 1L).filter((T w) -> w.count() == size).map((T w) -> w.window());
    }

    default public Seq<Window<T>> window() {
        return this.window(Window.of()).map((T t) -> (Window)t.v1);
    }

    default public Seq<Window<T>> window(long lower, long upper) {
        return this.window(Window.of(lower, upper)).map((T t) -> (Window)t.v1);
    }

    default public Seq<Window<T>> window(Comparator<? super T> orderBy) {
        return this.window(Window.of(orderBy)).map((T t) -> (Window)t.v1);
    }

    default public Seq<Window<T>> window(Comparator<? super T> orderBy, long lower, long upper) {
        return this.window(Window.of(orderBy, lower, upper)).map((T t) -> (Window)t.v1);
    }

    default public <U> Seq<Window<T>> window(Function<? super T, ? extends U> partitionBy) {
        return this.window(Window.of(partitionBy)).map((T t) -> (Window)t.v1);
    }

    default public <U> Seq<Window<T>> window(Function<? super T, ? extends U> partitionBy, long lower, long upper) {
        return this.window(Window.of(partitionBy, lower, upper)).map((T t) -> (Window)t.v1);
    }

    default public <U> Seq<Window<T>> window(Function<? super T, ? extends U> partitionBy, Comparator<? super T> orderBy) {
        return this.window(Window.of(partitionBy, orderBy)).map((T t) -> (Window)t.v1);
    }

    default public <U> Seq<Window<T>> window(Function<? super T, ? extends U> partitionBy, Comparator<? super T> orderBy, long lower, long upper) {
        return this.window(Window.of(partitionBy, orderBy, lower, upper)).map((T t) -> (Window)t.v1);
    }

    default public Seq<Tuple1<Window<T>>> window(WindowSpecification<T> specification1) {
        List buffer = this.zipWithIndex().toList();
        Map partitions1 = SeqUtils.partitions(specification1, buffer);
        return Seq.seq(buffer).map((T t) -> Tuple.tuple(new WindowImpl(t, (Partition)partitions1.get(specification1.partition().apply(t.v1)), specification1))).onClose(this::close);
    }

    default public Seq<Tuple2<Window<T>, Window<T>>> window(WindowSpecification<T> specification1, WindowSpecification<T> specification2) {
        List buffer = this.zipWithIndex().toList();
        Map partitions1 = SeqUtils.partitions(specification1, buffer);
        Map partitions2 = SeqUtils.partitions(specification2, buffer);
        return Seq.seq(buffer).map((T t) -> Tuple.tuple(new WindowImpl(t, (Partition)partitions1.get(specification1.partition().apply(t.v1)), specification1), new WindowImpl(t, (Partition)partitions2.get(specification2.partition().apply(t.v1)), specification2))).onClose(this::close);
    }

    default public Seq<Tuple3<Window<T>, Window<T>, Window<T>>> window(WindowSpecification<T> specification1, WindowSpecification<T> specification2, WindowSpecification<T> specification3) {
        List buffer = this.zipWithIndex().toList();
        Map partitions1 = SeqUtils.partitions(specification1, buffer);
        Map partitions2 = SeqUtils.partitions(specification2, buffer);
        Map partitions3 = SeqUtils.partitions(specification3, buffer);
        return Seq.seq(buffer).map((T t) -> Tuple.tuple(new WindowImpl(t, (Partition)partitions1.get(specification1.partition().apply(t.v1)), specification1), new WindowImpl(t, (Partition)partitions2.get(specification2.partition().apply(t.v1)), specification2), new WindowImpl(t, (Partition)partitions3.get(specification3.partition().apply(t.v1)), specification3))).onClose(this::close);
    }

    default public Seq<Tuple4<Window<T>, Window<T>, Window<T>, Window<T>>> window(WindowSpecification<T> specification1, WindowSpecification<T> specification2, WindowSpecification<T> specification3, WindowSpecification<T> specification4) {
        List buffer = this.zipWithIndex().toList();
        Map partitions1 = SeqUtils.partitions(specification1, buffer);
        Map partitions2 = SeqUtils.partitions(specification2, buffer);
        Map partitions3 = SeqUtils.partitions(specification3, buffer);
        Map partitions4 = SeqUtils.partitions(specification4, buffer);
        return Seq.seq(buffer).map((T t) -> Tuple.tuple(new WindowImpl(t, (Partition)partitions1.get(specification1.partition().apply(t.v1)), specification1), new WindowImpl(t, (Partition)partitions2.get(specification2.partition().apply(t.v1)), specification2), new WindowImpl(t, (Partition)partitions3.get(specification3.partition().apply(t.v1)), specification3), new WindowImpl(t, (Partition)partitions4.get(specification4.partition().apply(t.v1)), specification4))).onClose(this::close);
    }

    default public Seq<Tuple5<Window<T>, Window<T>, Window<T>, Window<T>, Window<T>>> window(WindowSpecification<T> specification1, WindowSpecification<T> specification2, WindowSpecification<T> specification3, WindowSpecification<T> specification4, WindowSpecification<T> specification5) {
        List buffer = this.zipWithIndex().toList();
        Map partitions1 = SeqUtils.partitions(specification1, buffer);
        Map partitions2 = SeqUtils.partitions(specification2, buffer);
        Map partitions3 = SeqUtils.partitions(specification3, buffer);
        Map partitions4 = SeqUtils.partitions(specification4, buffer);
        Map partitions5 = SeqUtils.partitions(specification5, buffer);
        return Seq.seq(buffer).map((T t) -> Tuple.tuple(new WindowImpl(t, (Partition)partitions1.get(specification1.partition().apply(t.v1)), specification1), new WindowImpl(t, (Partition)partitions2.get(specification2.partition().apply(t.v1)), specification2), new WindowImpl(t, (Partition)partitions3.get(specification3.partition().apply(t.v1)), specification3), new WindowImpl(t, (Partition)partitions4.get(specification4.partition().apply(t.v1)), specification4), new WindowImpl(t, (Partition)partitions5.get(specification5.partition().apply(t.v1)), specification5))).onClose(this::close);
    }

    default public Seq<Tuple6<Window<T>, Window<T>, Window<T>, Window<T>, Window<T>, Window<T>>> window(WindowSpecification<T> specification1, WindowSpecification<T> specification2, WindowSpecification<T> specification3, WindowSpecification<T> specification4, WindowSpecification<T> specification5, WindowSpecification<T> specification6) {
        List buffer = this.zipWithIndex().toList();
        Map partitions1 = SeqUtils.partitions(specification1, buffer);
        Map partitions2 = SeqUtils.partitions(specification2, buffer);
        Map partitions3 = SeqUtils.partitions(specification3, buffer);
        Map partitions4 = SeqUtils.partitions(specification4, buffer);
        Map partitions5 = SeqUtils.partitions(specification5, buffer);
        Map partitions6 = SeqUtils.partitions(specification6, buffer);
        return Seq.seq(buffer).map((T t) -> Tuple.tuple(new WindowImpl(t, (Partition)partitions1.get(specification1.partition().apply(t.v1)), specification1), new WindowImpl(t, (Partition)partitions2.get(specification2.partition().apply(t.v1)), specification2), new WindowImpl(t, (Partition)partitions3.get(specification3.partition().apply(t.v1)), specification3), new WindowImpl(t, (Partition)partitions4.get(specification4.partition().apply(t.v1)), specification4), new WindowImpl(t, (Partition)partitions5.get(specification5.partition().apply(t.v1)), specification5), new WindowImpl(t, (Partition)partitions6.get(specification6.partition().apply(t.v1)), specification6))).onClose(this::close);
    }

    default public Seq<Tuple7<Window<T>, Window<T>, Window<T>, Window<T>, Window<T>, Window<T>, Window<T>>> window(WindowSpecification<T> specification1, WindowSpecification<T> specification2, WindowSpecification<T> specification3, WindowSpecification<T> specification4, WindowSpecification<T> specification5, WindowSpecification<T> specification6, WindowSpecification<T> specification7) {
        List buffer = this.zipWithIndex().toList();
        Map partitions1 = SeqUtils.partitions(specification1, buffer);
        Map partitions2 = SeqUtils.partitions(specification2, buffer);
        Map partitions3 = SeqUtils.partitions(specification3, buffer);
        Map partitions4 = SeqUtils.partitions(specification4, buffer);
        Map partitions5 = SeqUtils.partitions(specification5, buffer);
        Map partitions6 = SeqUtils.partitions(specification6, buffer);
        Map partitions7 = SeqUtils.partitions(specification7, buffer);
        return Seq.seq(buffer).map((T t) -> Tuple.tuple(new WindowImpl(t, (Partition)partitions1.get(specification1.partition().apply(t.v1)), specification1), new WindowImpl(t, (Partition)partitions2.get(specification2.partition().apply(t.v1)), specification2), new WindowImpl(t, (Partition)partitions3.get(specification3.partition().apply(t.v1)), specification3), new WindowImpl(t, (Partition)partitions4.get(specification4.partition().apply(t.v1)), specification4), new WindowImpl(t, (Partition)partitions5.get(specification5.partition().apply(t.v1)), specification5), new WindowImpl(t, (Partition)partitions6.get(specification6.partition().apply(t.v1)), specification6), new WindowImpl(t, (Partition)partitions7.get(specification7.partition().apply(t.v1)), specification7))).onClose(this::close);
    }

    default public Seq<Tuple8<Window<T>, Window<T>, Window<T>, Window<T>, Window<T>, Window<T>, Window<T>, Window<T>>> window(WindowSpecification<T> specification1, WindowSpecification<T> specification2, WindowSpecification<T> specification3, WindowSpecification<T> specification4, WindowSpecification<T> specification5, WindowSpecification<T> specification6, WindowSpecification<T> specification7, WindowSpecification<T> specification8) {
        List buffer = this.zipWithIndex().toList();
        Map partitions1 = SeqUtils.partitions(specification1, buffer);
        Map partitions2 = SeqUtils.partitions(specification2, buffer);
        Map partitions3 = SeqUtils.partitions(specification3, buffer);
        Map partitions4 = SeqUtils.partitions(specification4, buffer);
        Map partitions5 = SeqUtils.partitions(specification5, buffer);
        Map partitions6 = SeqUtils.partitions(specification6, buffer);
        Map partitions7 = SeqUtils.partitions(specification7, buffer);
        Map partitions8 = SeqUtils.partitions(specification8, buffer);
        return Seq.seq(buffer).map((T t) -> Tuple.tuple(new WindowImpl(t, (Partition)partitions1.get(specification1.partition().apply(t.v1)), specification1), new WindowImpl(t, (Partition)partitions2.get(specification2.partition().apply(t.v1)), specification2), new WindowImpl(t, (Partition)partitions3.get(specification3.partition().apply(t.v1)), specification3), new WindowImpl(t, (Partition)partitions4.get(specification4.partition().apply(t.v1)), specification4), new WindowImpl(t, (Partition)partitions5.get(specification5.partition().apply(t.v1)), specification5), new WindowImpl(t, (Partition)partitions6.get(specification6.partition().apply(t.v1)), specification6), new WindowImpl(t, (Partition)partitions7.get(specification7.partition().apply(t.v1)), specification7), new WindowImpl(t, (Partition)partitions8.get(specification8.partition().apply(t.v1)), specification8))).onClose(this::close);
    }

    default public Seq<Tuple9<Window<T>, Window<T>, Window<T>, Window<T>, Window<T>, Window<T>, Window<T>, Window<T>, Window<T>>> window(WindowSpecification<T> specification1, WindowSpecification<T> specification2, WindowSpecification<T> specification3, WindowSpecification<T> specification4, WindowSpecification<T> specification5, WindowSpecification<T> specification6, WindowSpecification<T> specification7, WindowSpecification<T> specification8, WindowSpecification<T> specification9) {
        List buffer = this.zipWithIndex().toList();
        Map partitions1 = SeqUtils.partitions(specification1, buffer);
        Map partitions2 = SeqUtils.partitions(specification2, buffer);
        Map partitions3 = SeqUtils.partitions(specification3, buffer);
        Map partitions4 = SeqUtils.partitions(specification4, buffer);
        Map partitions5 = SeqUtils.partitions(specification5, buffer);
        Map partitions6 = SeqUtils.partitions(specification6, buffer);
        Map partitions7 = SeqUtils.partitions(specification7, buffer);
        Map partitions8 = SeqUtils.partitions(specification8, buffer);
        Map partitions9 = SeqUtils.partitions(specification9, buffer);
        return Seq.seq(buffer).map((T t) -> Tuple.tuple(new WindowImpl(t, (Partition)partitions1.get(specification1.partition().apply(t.v1)), specification1), new WindowImpl(t, (Partition)partitions2.get(specification2.partition().apply(t.v1)), specification2), new WindowImpl(t, (Partition)partitions3.get(specification3.partition().apply(t.v1)), specification3), new WindowImpl(t, (Partition)partitions4.get(specification4.partition().apply(t.v1)), specification4), new WindowImpl(t, (Partition)partitions5.get(specification5.partition().apply(t.v1)), specification5), new WindowImpl(t, (Partition)partitions6.get(specification6.partition().apply(t.v1)), specification6), new WindowImpl(t, (Partition)partitions7.get(specification7.partition().apply(t.v1)), specification7), new WindowImpl(t, (Partition)partitions8.get(specification8.partition().apply(t.v1)), specification8), new WindowImpl(t, (Partition)partitions9.get(specification9.partition().apply(t.v1)), specification9))).onClose(this::close);
    }

    default public Seq<Tuple10<Window<T>, Window<T>, Window<T>, Window<T>, Window<T>, Window<T>, Window<T>, Window<T>, Window<T>, Window<T>>> window(WindowSpecification<T> specification1, WindowSpecification<T> specification2, WindowSpecification<T> specification3, WindowSpecification<T> specification4, WindowSpecification<T> specification5, WindowSpecification<T> specification6, WindowSpecification<T> specification7, WindowSpecification<T> specification8, WindowSpecification<T> specification9, WindowSpecification<T> specification10) {
        List buffer = this.zipWithIndex().toList();
        Map partitions1 = SeqUtils.partitions(specification1, buffer);
        Map partitions2 = SeqUtils.partitions(specification2, buffer);
        Map partitions3 = SeqUtils.partitions(specification3, buffer);
        Map partitions4 = SeqUtils.partitions(specification4, buffer);
        Map partitions5 = SeqUtils.partitions(specification5, buffer);
        Map partitions6 = SeqUtils.partitions(specification6, buffer);
        Map partitions7 = SeqUtils.partitions(specification7, buffer);
        Map partitions8 = SeqUtils.partitions(specification8, buffer);
        Map partitions9 = SeqUtils.partitions(specification9, buffer);
        Map partitions10 = SeqUtils.partitions(specification10, buffer);
        return Seq.seq(buffer).map((T t) -> Tuple.tuple(new WindowImpl(t, (Partition)partitions1.get(specification1.partition().apply(t.v1)), specification1), new WindowImpl(t, (Partition)partitions2.get(specification2.partition().apply(t.v1)), specification2), new WindowImpl(t, (Partition)partitions3.get(specification3.partition().apply(t.v1)), specification3), new WindowImpl(t, (Partition)partitions4.get(specification4.partition().apply(t.v1)), specification4), new WindowImpl(t, (Partition)partitions5.get(specification5.partition().apply(t.v1)), specification5), new WindowImpl(t, (Partition)partitions6.get(specification6.partition().apply(t.v1)), specification6), new WindowImpl(t, (Partition)partitions7.get(specification7.partition().apply(t.v1)), specification7), new WindowImpl(t, (Partition)partitions8.get(specification8.partition().apply(t.v1)), specification8), new WindowImpl(t, (Partition)partitions9.get(specification9.partition().apply(t.v1)), specification9), new WindowImpl(t, (Partition)partitions10.get(specification10.partition().apply(t.v1)), specification10))).onClose(this::close);
    }

    default public Seq<Tuple11<Window<T>, Window<T>, Window<T>, Window<T>, Window<T>, Window<T>, Window<T>, Window<T>, Window<T>, Window<T>, Window<T>>> window(WindowSpecification<T> specification1, WindowSpecification<T> specification2, WindowSpecification<T> specification3, WindowSpecification<T> specification4, WindowSpecification<T> specification5, WindowSpecification<T> specification6, WindowSpecification<T> specification7, WindowSpecification<T> specification8, WindowSpecification<T> specification9, WindowSpecification<T> specification10, WindowSpecification<T> specification11) {
        List buffer = this.zipWithIndex().toList();
        Map partitions1 = SeqUtils.partitions(specification1, buffer);
        Map partitions2 = SeqUtils.partitions(specification2, buffer);
        Map partitions3 = SeqUtils.partitions(specification3, buffer);
        Map partitions4 = SeqUtils.partitions(specification4, buffer);
        Map partitions5 = SeqUtils.partitions(specification5, buffer);
        Map partitions6 = SeqUtils.partitions(specification6, buffer);
        Map partitions7 = SeqUtils.partitions(specification7, buffer);
        Map partitions8 = SeqUtils.partitions(specification8, buffer);
        Map partitions9 = SeqUtils.partitions(specification9, buffer);
        Map partitions10 = SeqUtils.partitions(specification10, buffer);
        Map partitions11 = SeqUtils.partitions(specification11, buffer);
        return Seq.seq(buffer).map((T t) -> Tuple.tuple(new WindowImpl(t, (Partition)partitions1.get(specification1.partition().apply(t.v1)), specification1), new WindowImpl(t, (Partition)partitions2.get(specification2.partition().apply(t.v1)), specification2), new WindowImpl(t, (Partition)partitions3.get(specification3.partition().apply(t.v1)), specification3), new WindowImpl(t, (Partition)partitions4.get(specification4.partition().apply(t.v1)), specification4), new WindowImpl(t, (Partition)partitions5.get(specification5.partition().apply(t.v1)), specification5), new WindowImpl(t, (Partition)partitions6.get(specification6.partition().apply(t.v1)), specification6), new WindowImpl(t, (Partition)partitions7.get(specification7.partition().apply(t.v1)), specification7), new WindowImpl(t, (Partition)partitions8.get(specification8.partition().apply(t.v1)), specification8), new WindowImpl(t, (Partition)partitions9.get(specification9.partition().apply(t.v1)), specification9), new WindowImpl(t, (Partition)partitions10.get(specification10.partition().apply(t.v1)), specification10), new WindowImpl(t, (Partition)partitions11.get(specification11.partition().apply(t.v1)), specification11))).onClose(this::close);
    }

    default public Seq<Tuple12<Window<T>, Window<T>, Window<T>, Window<T>, Window<T>, Window<T>, Window<T>, Window<T>, Window<T>, Window<T>, Window<T>, Window<T>>> window(WindowSpecification<T> specification1, WindowSpecification<T> specification2, WindowSpecification<T> specification3, WindowSpecification<T> specification4, WindowSpecification<T> specification5, WindowSpecification<T> specification6, WindowSpecification<T> specification7, WindowSpecification<T> specification8, WindowSpecification<T> specification9, WindowSpecification<T> specification10, WindowSpecification<T> specification11, WindowSpecification<T> specification12) {
        List buffer = this.zipWithIndex().toList();
        Map partitions1 = SeqUtils.partitions(specification1, buffer);
        Map partitions2 = SeqUtils.partitions(specification2, buffer);
        Map partitions3 = SeqUtils.partitions(specification3, buffer);
        Map partitions4 = SeqUtils.partitions(specification4, buffer);
        Map partitions5 = SeqUtils.partitions(specification5, buffer);
        Map partitions6 = SeqUtils.partitions(specification6, buffer);
        Map partitions7 = SeqUtils.partitions(specification7, buffer);
        Map partitions8 = SeqUtils.partitions(specification8, buffer);
        Map partitions9 = SeqUtils.partitions(specification9, buffer);
        Map partitions10 = SeqUtils.partitions(specification10, buffer);
        Map partitions11 = SeqUtils.partitions(specification11, buffer);
        Map partitions12 = SeqUtils.partitions(specification12, buffer);
        return Seq.seq(buffer).map((T t) -> Tuple.tuple(new WindowImpl(t, (Partition)partitions1.get(specification1.partition().apply(t.v1)), specification1), new WindowImpl(t, (Partition)partitions2.get(specification2.partition().apply(t.v1)), specification2), new WindowImpl(t, (Partition)partitions3.get(specification3.partition().apply(t.v1)), specification3), new WindowImpl(t, (Partition)partitions4.get(specification4.partition().apply(t.v1)), specification4), new WindowImpl(t, (Partition)partitions5.get(specification5.partition().apply(t.v1)), specification5), new WindowImpl(t, (Partition)partitions6.get(specification6.partition().apply(t.v1)), specification6), new WindowImpl(t, (Partition)partitions7.get(specification7.partition().apply(t.v1)), specification7), new WindowImpl(t, (Partition)partitions8.get(specification8.partition().apply(t.v1)), specification8), new WindowImpl(t, (Partition)partitions9.get(specification9.partition().apply(t.v1)), specification9), new WindowImpl(t, (Partition)partitions10.get(specification10.partition().apply(t.v1)), specification10), new WindowImpl(t, (Partition)partitions11.get(specification11.partition().apply(t.v1)), specification11), new WindowImpl(t, (Partition)partitions12.get(specification12.partition().apply(t.v1)), specification12))).onClose(this::close);
    }

    default public Seq<Tuple13<Window<T>, Window<T>, Window<T>, Window<T>, Window<T>, Window<T>, Window<T>, Window<T>, Window<T>, Window<T>, Window<T>, Window<T>, Window<T>>> window(WindowSpecification<T> specification1, WindowSpecification<T> specification2, WindowSpecification<T> specification3, WindowSpecification<T> specification4, WindowSpecification<T> specification5, WindowSpecification<T> specification6, WindowSpecification<T> specification7, WindowSpecification<T> specification8, WindowSpecification<T> specification9, WindowSpecification<T> specification10, WindowSpecification<T> specification11, WindowSpecification<T> specification12, WindowSpecification<T> specification13) {
        List buffer = this.zipWithIndex().toList();
        Map partitions1 = SeqUtils.partitions(specification1, buffer);
        Map partitions2 = SeqUtils.partitions(specification2, buffer);
        Map partitions3 = SeqUtils.partitions(specification3, buffer);
        Map partitions4 = SeqUtils.partitions(specification4, buffer);
        Map partitions5 = SeqUtils.partitions(specification5, buffer);
        Map partitions6 = SeqUtils.partitions(specification6, buffer);
        Map partitions7 = SeqUtils.partitions(specification7, buffer);
        Map partitions8 = SeqUtils.partitions(specification8, buffer);
        Map partitions9 = SeqUtils.partitions(specification9, buffer);
        Map partitions10 = SeqUtils.partitions(specification10, buffer);
        Map partitions11 = SeqUtils.partitions(specification11, buffer);
        Map partitions12 = SeqUtils.partitions(specification12, buffer);
        Map partitions13 = SeqUtils.partitions(specification13, buffer);
        return Seq.seq(buffer).map((T t) -> Tuple.tuple(new WindowImpl(t, (Partition)partitions1.get(specification1.partition().apply(t.v1)), specification1), new WindowImpl(t, (Partition)partitions2.get(specification2.partition().apply(t.v1)), specification2), new WindowImpl(t, (Partition)partitions3.get(specification3.partition().apply(t.v1)), specification3), new WindowImpl(t, (Partition)partitions4.get(specification4.partition().apply(t.v1)), specification4), new WindowImpl(t, (Partition)partitions5.get(specification5.partition().apply(t.v1)), specification5), new WindowImpl(t, (Partition)partitions6.get(specification6.partition().apply(t.v1)), specification6), new WindowImpl(t, (Partition)partitions7.get(specification7.partition().apply(t.v1)), specification7), new WindowImpl(t, (Partition)partitions8.get(specification8.partition().apply(t.v1)), specification8), new WindowImpl(t, (Partition)partitions9.get(specification9.partition().apply(t.v1)), specification9), new WindowImpl(t, (Partition)partitions10.get(specification10.partition().apply(t.v1)), specification10), new WindowImpl(t, (Partition)partitions11.get(specification11.partition().apply(t.v1)), specification11), new WindowImpl(t, (Partition)partitions12.get(specification12.partition().apply(t.v1)), specification12), new WindowImpl(t, (Partition)partitions13.get(specification13.partition().apply(t.v1)), specification13))).onClose(this::close);
    }

    default public Seq<Tuple14<Window<T>, Window<T>, Window<T>, Window<T>, Window<T>, Window<T>, Window<T>, Window<T>, Window<T>, Window<T>, Window<T>, Window<T>, Window<T>, Window<T>>> window(WindowSpecification<T> specification1, WindowSpecification<T> specification2, WindowSpecification<T> specification3, WindowSpecification<T> specification4, WindowSpecification<T> specification5, WindowSpecification<T> specification6, WindowSpecification<T> specification7, WindowSpecification<T> specification8, WindowSpecification<T> specification9, WindowSpecification<T> specification10, WindowSpecification<T> specification11, WindowSpecification<T> specification12, WindowSpecification<T> specification13, WindowSpecification<T> specification14) {
        List buffer = this.zipWithIndex().toList();
        Map partitions1 = SeqUtils.partitions(specification1, buffer);
        Map partitions2 = SeqUtils.partitions(specification2, buffer);
        Map partitions3 = SeqUtils.partitions(specification3, buffer);
        Map partitions4 = SeqUtils.partitions(specification4, buffer);
        Map partitions5 = SeqUtils.partitions(specification5, buffer);
        Map partitions6 = SeqUtils.partitions(specification6, buffer);
        Map partitions7 = SeqUtils.partitions(specification7, buffer);
        Map partitions8 = SeqUtils.partitions(specification8, buffer);
        Map partitions9 = SeqUtils.partitions(specification9, buffer);
        Map partitions10 = SeqUtils.partitions(specification10, buffer);
        Map partitions11 = SeqUtils.partitions(specification11, buffer);
        Map partitions12 = SeqUtils.partitions(specification12, buffer);
        Map partitions13 = SeqUtils.partitions(specification13, buffer);
        Map partitions14 = SeqUtils.partitions(specification14, buffer);
        return Seq.seq(buffer).map((T t) -> Tuple.tuple(new WindowImpl(t, (Partition)partitions1.get(specification1.partition().apply(t.v1)), specification1), new WindowImpl(t, (Partition)partitions2.get(specification2.partition().apply(t.v1)), specification2), new WindowImpl(t, (Partition)partitions3.get(specification3.partition().apply(t.v1)), specification3), new WindowImpl(t, (Partition)partitions4.get(specification4.partition().apply(t.v1)), specification4), new WindowImpl(t, (Partition)partitions5.get(specification5.partition().apply(t.v1)), specification5), new WindowImpl(t, (Partition)partitions6.get(specification6.partition().apply(t.v1)), specification6), new WindowImpl(t, (Partition)partitions7.get(specification7.partition().apply(t.v1)), specification7), new WindowImpl(t, (Partition)partitions8.get(specification8.partition().apply(t.v1)), specification8), new WindowImpl(t, (Partition)partitions9.get(specification9.partition().apply(t.v1)), specification9), new WindowImpl(t, (Partition)partitions10.get(specification10.partition().apply(t.v1)), specification10), new WindowImpl(t, (Partition)partitions11.get(specification11.partition().apply(t.v1)), specification11), new WindowImpl(t, (Partition)partitions12.get(specification12.partition().apply(t.v1)), specification12), new WindowImpl(t, (Partition)partitions13.get(specification13.partition().apply(t.v1)), specification13), new WindowImpl(t, (Partition)partitions14.get(specification14.partition().apply(t.v1)), specification14))).onClose(this::close);
    }

    default public Seq<Tuple15<Window<T>, Window<T>, Window<T>, Window<T>, Window<T>, Window<T>, Window<T>, Window<T>, Window<T>, Window<T>, Window<T>, Window<T>, Window<T>, Window<T>, Window<T>>> window(WindowSpecification<T> specification1, WindowSpecification<T> specification2, WindowSpecification<T> specification3, WindowSpecification<T> specification4, WindowSpecification<T> specification5, WindowSpecification<T> specification6, WindowSpecification<T> specification7, WindowSpecification<T> specification8, WindowSpecification<T> specification9, WindowSpecification<T> specification10, WindowSpecification<T> specification11, WindowSpecification<T> specification12, WindowSpecification<T> specification13, WindowSpecification<T> specification14, WindowSpecification<T> specification15) {
        List buffer = this.zipWithIndex().toList();
        Map partitions1 = SeqUtils.partitions(specification1, buffer);
        Map partitions2 = SeqUtils.partitions(specification2, buffer);
        Map partitions3 = SeqUtils.partitions(specification3, buffer);
        Map partitions4 = SeqUtils.partitions(specification4, buffer);
        Map partitions5 = SeqUtils.partitions(specification5, buffer);
        Map partitions6 = SeqUtils.partitions(specification6, buffer);
        Map partitions7 = SeqUtils.partitions(specification7, buffer);
        Map partitions8 = SeqUtils.partitions(specification8, buffer);
        Map partitions9 = SeqUtils.partitions(specification9, buffer);
        Map partitions10 = SeqUtils.partitions(specification10, buffer);
        Map partitions11 = SeqUtils.partitions(specification11, buffer);
        Map partitions12 = SeqUtils.partitions(specification12, buffer);
        Map partitions13 = SeqUtils.partitions(specification13, buffer);
        Map partitions14 = SeqUtils.partitions(specification14, buffer);
        Map partitions15 = SeqUtils.partitions(specification15, buffer);
        return Seq.seq(buffer).map((T t) -> Tuple.tuple(new WindowImpl(t, (Partition)partitions1.get(specification1.partition().apply(t.v1)), specification1), new WindowImpl(t, (Partition)partitions2.get(specification2.partition().apply(t.v1)), specification2), new WindowImpl(t, (Partition)partitions3.get(specification3.partition().apply(t.v1)), specification3), new WindowImpl(t, (Partition)partitions4.get(specification4.partition().apply(t.v1)), specification4), new WindowImpl(t, (Partition)partitions5.get(specification5.partition().apply(t.v1)), specification5), new WindowImpl(t, (Partition)partitions6.get(specification6.partition().apply(t.v1)), specification6), new WindowImpl(t, (Partition)partitions7.get(specification7.partition().apply(t.v1)), specification7), new WindowImpl(t, (Partition)partitions8.get(specification8.partition().apply(t.v1)), specification8), new WindowImpl(t, (Partition)partitions9.get(specification9.partition().apply(t.v1)), specification9), new WindowImpl(t, (Partition)partitions10.get(specification10.partition().apply(t.v1)), specification10), new WindowImpl(t, (Partition)partitions11.get(specification11.partition().apply(t.v1)), specification11), new WindowImpl(t, (Partition)partitions12.get(specification12.partition().apply(t.v1)), specification12), new WindowImpl(t, (Partition)partitions13.get(specification13.partition().apply(t.v1)), specification13), new WindowImpl(t, (Partition)partitions14.get(specification14.partition().apply(t.v1)), specification14), new WindowImpl(t, (Partition)partitions15.get(specification15.partition().apply(t.v1)), specification15))).onClose(this::close);
    }

    default public Seq<Tuple16<Window<T>, Window<T>, Window<T>, Window<T>, Window<T>, Window<T>, Window<T>, Window<T>, Window<T>, Window<T>, Window<T>, Window<T>, Window<T>, Window<T>, Window<T>, Window<T>>> window(WindowSpecification<T> specification1, WindowSpecification<T> specification2, WindowSpecification<T> specification3, WindowSpecification<T> specification4, WindowSpecification<T> specification5, WindowSpecification<T> specification6, WindowSpecification<T> specification7, WindowSpecification<T> specification8, WindowSpecification<T> specification9, WindowSpecification<T> specification10, WindowSpecification<T> specification11, WindowSpecification<T> specification12, WindowSpecification<T> specification13, WindowSpecification<T> specification14, WindowSpecification<T> specification15, WindowSpecification<T> specification16) {
        List buffer = this.zipWithIndex().toList();
        Map partitions1 = SeqUtils.partitions(specification1, buffer);
        Map partitions2 = SeqUtils.partitions(specification2, buffer);
        Map partitions3 = SeqUtils.partitions(specification3, buffer);
        Map partitions4 = SeqUtils.partitions(specification4, buffer);
        Map partitions5 = SeqUtils.partitions(specification5, buffer);
        Map partitions6 = SeqUtils.partitions(specification6, buffer);
        Map partitions7 = SeqUtils.partitions(specification7, buffer);
        Map partitions8 = SeqUtils.partitions(specification8, buffer);
        Map partitions9 = SeqUtils.partitions(specification9, buffer);
        Map partitions10 = SeqUtils.partitions(specification10, buffer);
        Map partitions11 = SeqUtils.partitions(specification11, buffer);
        Map partitions12 = SeqUtils.partitions(specification12, buffer);
        Map partitions13 = SeqUtils.partitions(specification13, buffer);
        Map partitions14 = SeqUtils.partitions(specification14, buffer);
        Map partitions15 = SeqUtils.partitions(specification15, buffer);
        Map partitions16 = SeqUtils.partitions(specification16, buffer);
        return Seq.seq(buffer).map((T t) -> Tuple.tuple(new WindowImpl(t, (Partition)partitions1.get(specification1.partition().apply(t.v1)), specification1), new WindowImpl(t, (Partition)partitions2.get(specification2.partition().apply(t.v1)), specification2), new WindowImpl(t, (Partition)partitions3.get(specification3.partition().apply(t.v1)), specification3), new WindowImpl(t, (Partition)partitions4.get(specification4.partition().apply(t.v1)), specification4), new WindowImpl(t, (Partition)partitions5.get(specification5.partition().apply(t.v1)), specification5), new WindowImpl(t, (Partition)partitions6.get(specification6.partition().apply(t.v1)), specification6), new WindowImpl(t, (Partition)partitions7.get(specification7.partition().apply(t.v1)), specification7), new WindowImpl(t, (Partition)partitions8.get(specification8.partition().apply(t.v1)), specification8), new WindowImpl(t, (Partition)partitions9.get(specification9.partition().apply(t.v1)), specification9), new WindowImpl(t, (Partition)partitions10.get(specification10.partition().apply(t.v1)), specification10), new WindowImpl(t, (Partition)partitions11.get(specification11.partition().apply(t.v1)), specification11), new WindowImpl(t, (Partition)partitions12.get(specification12.partition().apply(t.v1)), specification12), new WindowImpl(t, (Partition)partitions13.get(specification13.partition().apply(t.v1)), specification13), new WindowImpl(t, (Partition)partitions14.get(specification14.partition().apply(t.v1)), specification14), new WindowImpl(t, (Partition)partitions15.get(specification15.partition().apply(t.v1)), specification15), new WindowImpl(t, (Partition)partitions16.get(specification16.partition().apply(t.v1)), specification16))).onClose(this::close);
    }

    default public <K> Map<K, List<T>> groupBy(Function<? super T, ? extends K> classifier) {
        return this.collect(Collectors.groupingBy(classifier));
    }

    default public <K, A, D> Map<K, D> groupBy(Function<? super T, ? extends K> classifier, Collector<? super T, A, D> downstream) {
        return this.collect(Collectors.groupingBy(classifier, downstream));
    }

    default public <K, D, A, M extends Map<K, D>> M groupBy(Function<? super T, ? extends K> classifier, Supplier<M> mapFactory, Collector<? super T, A, D> downstream) {
        return (M)((Map)this.collect(Collectors.groupingBy(classifier, mapFactory, downstream)));
    }

    @Deprecated
    default public String join() {
        return this.map(Objects::toString).collect(Collectors.joining());
    }

    @Deprecated
    default public String join(CharSequence delimiter) {
        return this.map(Objects::toString).collect(Collectors.joining(delimiter));
    }

    @Deprecated
    default public String join(CharSequence delimiter, CharSequence prefix, CharSequence suffix) {
        return this.map(Objects::toString).collect(Collectors.joining(delimiter, prefix, suffix));
    }

    public static <T> Seq<T> of(T value) {
        return Seq.seq(Stream.of(value));
    }

    @SafeVarargs
    public static <T> Seq<T> of(T ... values) {
        return Seq.seq(Stream.of(values));
    }

    public static Seq<Byte> range(byte fromInclusive, byte toExclusive) {
        return Seq.range(fromInclusive, toExclusive, 1);
    }

    public static Seq<Byte> range(byte fromInclusive, byte toExclusive, int step) {
        return toExclusive <= fromInclusive ? Seq.empty() : Seq.iterate(fromInclusive, t -> (byte)(t + step)).limitWhile(t -> t < toExclusive);
    }

    public static Seq<Short> range(short fromInclusive, short toExclusive) {
        return Seq.range(fromInclusive, toExclusive, 1);
    }

    public static Seq<Short> range(short fromInclusive, short toExclusive, int step) {
        return toExclusive <= fromInclusive ? Seq.empty() : Seq.iterate(fromInclusive, t -> (short)(t + step)).limitWhile(t -> t < toExclusive);
    }

    public static Seq<Character> range(char fromInclusive, char toExclusive) {
        return Seq.range(fromInclusive, toExclusive, 1);
    }

    public static Seq<Character> range(char fromInclusive, char toExclusive, int step) {
        return toExclusive <= fromInclusive ? Seq.empty() : Seq.iterate(Character.valueOf(fromInclusive), t -> Character.valueOf((char)(t.charValue() + step))).limitWhile(t -> t.charValue() < toExclusive);
    }

    public static Seq<Integer> range(int fromInclusive, int toExclusive) {
        return Seq.range(fromInclusive, toExclusive, 1);
    }

    public static Seq<Integer> range(int fromInclusive, int toExclusive, int step) {
        return toExclusive <= fromInclusive ? Seq.empty() : Seq.iterate(fromInclusive, t -> t + step).limitWhile(t -> t < toExclusive);
    }

    public static Seq<Long> range(long fromInclusive, long toExclusive) {
        return Seq.range(fromInclusive, toExclusive, 1L);
    }

    public static Seq<Long> range(long fromInclusive, long toExclusive, long step) {
        return toExclusive <= fromInclusive ? Seq.empty() : Seq.iterate(fromInclusive, t -> t + step).limitWhile(t -> t < toExclusive);
    }

    public static Seq<Instant> range(Instant fromInclusive, Instant toExclusive) {
        return Seq.range(fromInclusive, toExclusive, Duration.ofSeconds(1L));
    }

    public static Seq<Instant> range(Instant fromInclusive, Instant toExclusive, Duration step) {
        return toExclusive.compareTo(fromInclusive) <= 0 ? Seq.empty() : Seq.iterate(fromInclusive, t -> t.plus(step)).limitWhile(t -> t.compareTo(toExclusive) < 0);
    }

    public static Seq<Byte> rangeClosed(byte fromInclusive, byte toInclusive) {
        return Seq.rangeClosed(fromInclusive, toInclusive, 1);
    }

    public static Seq<Byte> rangeClosed(byte fromInclusive, byte toInclusive, int step) {
        return toInclusive < fromInclusive ? Seq.empty() : Seq.iterate(fromInclusive, t -> (byte)(t + step)).limitWhile(t -> t <= toInclusive);
    }

    public static Seq<Short> rangeClosed(short fromInclusive, short toInclusive) {
        return Seq.rangeClosed(fromInclusive, toInclusive, 1);
    }

    public static Seq<Short> rangeClosed(short fromInclusive, short toInclusive, int step) {
        return toInclusive < fromInclusive ? Seq.empty() : Seq.iterate(fromInclusive, t -> (short)(t + step)).limitWhile(t -> t <= toInclusive);
    }

    public static Seq<Character> rangeClosed(char fromInclusive, char toInclusive) {
        return Seq.rangeClosed(fromInclusive, toInclusive, 1);
    }

    public static Seq<Character> rangeClosed(char fromInclusive, char toInclusive, int step) {
        return toInclusive < fromInclusive ? Seq.empty() : Seq.iterate(Character.valueOf(fromInclusive), t -> Character.valueOf((char)(t.charValue() + step))).limitWhile(t -> t.charValue() <= toInclusive);
    }

    public static Seq<Integer> rangeClosed(int fromInclusive, int toInclusive) {
        return Seq.rangeClosed(fromInclusive, toInclusive, 1);
    }

    public static Seq<Integer> rangeClosed(int fromInclusive, int toInclusive, int step) {
        return toInclusive < fromInclusive ? Seq.empty() : Seq.iterate(fromInclusive, t -> t + step).limitWhile(t -> t <= toInclusive);
    }

    public static Seq<Long> rangeClosed(long fromInclusive, long toInclusive) {
        return Seq.rangeClosed(fromInclusive, toInclusive, 1L);
    }

    public static Seq<Long> rangeClosed(long fromInclusive, long toInclusive, long step) {
        return toInclusive < fromInclusive ? Seq.empty() : Seq.iterate(fromInclusive, t -> t + step).limitWhile(t -> t <= toInclusive);
    }

    public static Seq<Instant> rangeClosed(Instant fromInclusive, Instant toInclusive) {
        return Seq.rangeClosed(fromInclusive, toInclusive, Duration.ofSeconds(1L));
    }

    public static Seq<Instant> rangeClosed(Instant fromInclusive, Instant toInclusive, Duration step) {
        return toInclusive.compareTo(fromInclusive) < 0 ? Seq.empty() : Seq.iterate(fromInclusive, t -> t.plus(step)).limitWhile(t -> t.compareTo(toInclusive) <= 0);
    }

    public static <T> Seq<T> empty() {
        return Seq.seq(Stream.empty());
    }

    public static <T> Seq<T> iterate(T seed, UnaryOperator<T> f) {
        return Seq.seq(Stream.iterate(seed, f));
    }

    public static <T> Seq<T> iterateWhilePresent(T seed, Function<? super T, Optional<? extends T>> generator) {
        return Seq.iterate(Objects.requireNonNull(seed), t -> ((Optional)generator.apply(t)).orElse(null)).limitWhile(Objects::nonNull);
    }

    public static Seq<Void> generate() {
        return Seq.generate(() -> null);
    }

    public static <T> Seq<T> generate(T value) {
        return Seq.generate(() -> value);
    }

    public static <T> Seq<T> generate(Supplier<? extends T> s) {
        return Seq.seq(Stream.generate(s));
    }

    public static <T> Seq<T> seq(T[] values, int startIndex, int endIndex) {
        return Seq.seq(Arrays.asList(values).subList(startIndex, endIndex));
    }

    public static <T> Seq<T> seq(Stream<? extends T> stream) {
        if (stream == null) {
            return Seq.empty();
        }
        if (stream instanceof Seq) {
            return (Seq)stream;
        }
        return new SeqImpl<T>(stream);
    }

    public static <T> Seq<T> seq(Seq<? extends T> stream) {
        if (stream == null) {
            return Seq.empty();
        }
        return stream;
    }

    public static Seq<Integer> seq(IntStream stream) {
        if (stream == null) {
            return Seq.empty();
        }
        return new SeqImpl<Integer>(stream.boxed());
    }

    public static Seq<Long> seq(LongStream stream) {
        if (stream == null) {
            return Seq.empty();
        }
        return new SeqImpl<Long>(stream.boxed());
    }

    public static Seq<Double> seq(DoubleStream stream) {
        if (stream == null) {
            return Seq.empty();
        }
        return new SeqImpl<Double>(stream.boxed());
    }

    public static <T> Seq<T> seq(Iterable<? extends T> iterable) {
        if (iterable == null) {
            return Seq.empty();
        }
        return Seq.seq(iterable.iterator());
    }

    public static <T> Seq<T> seq(Iterator<? extends T> iterator) {
        if (iterator == null) {
            return Seq.empty();
        }
        return Seq.seq(Spliterators.spliteratorUnknownSize(iterator, 16));
    }

    public static <T> Seq<T> seq(final Enumeration<T> enumeration) {
        if (enumeration == null) {
            return Seq.empty();
        }
        return Seq.seq(new Iterator<T>(){

            @Override
            public boolean hasNext() {
                return enumeration.hasMoreElements();
            }

            @Override
            public T next() {
                return enumeration.nextElement();
            }
        });
    }

    public static <T> Seq<T> seq(Spliterator<? extends T> spliterator) {
        if (spliterator == null) {
            return Seq.empty();
        }
        return Seq.seq(StreamSupport.stream(spliterator, false));
    }

    public static <K, V> Seq<Tuple2<K, V>> seq(Map<? extends K, ? extends V> map) {
        if (map == null) {
            return Seq.empty();
        }
        return Seq.seq(map.entrySet()).map((T e) -> Tuple.tuple(e.getKey(), e.getValue()));
    }

    public static <T> Seq<T> seq(Optional<? extends T> optional) {
        if (optional == null) {
            return Seq.empty();
        }
        return optional.map(Seq::of).orElseGet(Seq::empty);
    }

    @SafeVarargs
    public static <T> Seq<T> seq(Optional<? extends T> ... optionals) {
        if (optionals == null) {
            return Seq.empty();
        }
        return Seq.of(optionals).filter(Optional::isPresent).map(Optional::get);
    }

    public static <T> Seq<T> seq(Supplier<? extends T> s) {
        return Seq.generate(s).limit(1L);
    }

    public static Seq<Byte> seq(InputStream is) {
        if (is == null) {
            return Seq.empty();
        }
        FunctionalSpliterator spliterator = consumer -> {
            try {
                int value = is.read();
                if (value != -1) {
                    consumer.accept((byte)value);
                }
                return value != -1;
            }
            catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        };
        return Seq.seq(spliterator).onClose(Unchecked.runnable(is::close));
    }

    public static Seq<Character> seq(Reader reader) {
        if (reader == null) {
            return Seq.empty();
        }
        FunctionalSpliterator spliterator = consumer -> {
            try {
                int value = reader.read();
                if (value != -1) {
                    consumer.accept(Character.valueOf((char)value));
                }
                return value != -1;
            }
            catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        };
        return Seq.seq(spliterator).onClose(Unchecked.runnable(reader::close));
    }

    public static <T> Seq<T> cycle(Stream<? extends T> stream) {
        return Seq.cycle(Seq.seq(stream));
    }

    public static <T> Seq<T> cycle(Iterable<? extends T> iterable) {
        return Seq.cycle(Seq.seq(iterable));
    }

    public static <T> Seq<T> cycle(Seq<? extends T> stream) {
        return Seq.cycle(stream, -1L);
    }

    public static <T> Seq<T> cycle(Stream<? extends T> stream, long times) {
        return Seq.cycle(Seq.seq(stream), times);
    }

    public static <T> Seq<T> cycle(Iterable<? extends T> iterable, long times) {
        return Seq.cycle(Seq.seq(iterable), times);
    }

    public static <T> Seq<T> cycle(Seq<? extends T> stream, long times) {
        if (times == 0L) {
            return Seq.empty();
        }
        if (times == 1L) {
            return stream;
        }
        ArrayList list = new ArrayList();
        Spliterator[] sp = new Spliterator[1];
        long[] remaining = new long[]{times};
        return SeqUtils.transform(stream, (delegate, action) -> {
            if (sp[0] == null) {
                if (delegate.tryAdvance(t -> {
                    list.add(t);
                    action.accept(t);
                })) {
                    return true;
                }
                sp[0] = list.spliterator();
            }
            if (!sp[0].tryAdvance(action)) {
                if (times != -1L && (remaining[0] = remaining[0] - 1L) == 1L) {
                    return false;
                }
                sp[0] = list.spliterator();
                if (!sp[0].tryAdvance(action)) {
                    return false;
                }
            }
            return true;
        });
    }

    public static <K, V> Tuple2<Seq<K>, Seq<V>> unzip(Map<? extends K, ? extends V> map) {
        return Seq.unzip(Seq.seq(map));
    }

    public static <T1, T2> Tuple2<Seq<T1>, Seq<T2>> unzip(Stream<Tuple2<T1, T2>> stream) {
        return Seq.unzip(Seq.seq(stream));
    }

    public static <T1, T2, U1, U2> Tuple2<Seq<U1>, Seq<U2>> unzip(Stream<Tuple2<T1, T2>> stream, Function<T1, U1> leftUnzipper, Function<T2, U2> rightUnzipper) {
        return Seq.unzip(Seq.seq(stream), leftUnzipper, rightUnzipper);
    }

    public static <T1, T2, U1, U2> Tuple2<Seq<U1>, Seq<U2>> unzip(Stream<Tuple2<T1, T2>> stream, Function<Tuple2<T1, T2>, Tuple2<U1, U2>> unzipper) {
        return Seq.unzip(Seq.seq(stream), unzipper);
    }

    public static <T1, T2, U1, U2> Tuple2<Seq<U1>, Seq<U2>> unzip(Stream<Tuple2<T1, T2>> stream, BiFunction<T1, T2, Tuple2<U1, U2>> unzipper) {
        return Seq.unzip(Seq.seq(stream), unzipper);
    }

    public static <T1, T2> Tuple2<Seq<T1>, Seq<T2>> unzip(Iterable<Tuple2<T1, T2>> iterable) {
        return Seq.unzip(Seq.seq(iterable));
    }

    public static <T1, T2, U1, U2> Tuple2<Seq<U1>, Seq<U2>> unzip(Iterable<Tuple2<T1, T2>> iterable, Function<T1, U1> leftUnzipper, Function<T2, U2> rightUnzipper) {
        return Seq.unzip(Seq.seq(iterable), leftUnzipper, rightUnzipper);
    }

    public static <T1, T2, U1, U2> Tuple2<Seq<U1>, Seq<U2>> unzip(Iterable<Tuple2<T1, T2>> iterable, Function<Tuple2<T1, T2>, Tuple2<U1, U2>> unzipper) {
        return Seq.unzip(Seq.seq(iterable), unzipper);
    }

    public static <T1, T2, U1, U2> Tuple2<Seq<U1>, Seq<U2>> unzip(Iterable<Tuple2<T1, T2>> iterable, BiFunction<T1, T2, Tuple2<U1, U2>> unzipper) {
        return Seq.unzip(Seq.seq(iterable), unzipper);
    }

    public static <T1, T2> Tuple2<Seq<T1>, Seq<T2>> unzip(Seq<Tuple2<T1, T2>> stream) {
        return Seq.unzip(stream, (Tuple2<T1, T2> t) -> t);
    }

    public static <T1, T2, U1, U2> Tuple2<Seq<U1>, Seq<U2>> unzip(Seq<Tuple2<T1, T2>> stream, Function<T1, U1> leftUnzipper, Function<T2, U2> rightUnzipper) {
        return Seq.unzip(stream, (Tuple2<T1, T2> t) -> Tuple.tuple(leftUnzipper.apply(t.v1), rightUnzipper.apply(t.v2)));
    }

    public static <T1, T2, U1, U2> Tuple2<Seq<U1>, Seq<U2>> unzip(Seq<Tuple2<T1, T2>> stream, Function<Tuple2<T1, T2>, Tuple2<U1, U2>> unzipper) {
        return Seq.unzip(stream, (T1 t1, T2 t2) -> (Tuple2)unzipper.apply(Tuple.tuple(t1, t2)));
    }

    public static <T1, T2, U1, U2> Tuple2<Seq<U1>, Seq<U2>> unzip(Seq<Tuple2<T1, T2>> stream, BiFunction<T1, T2, Tuple2<U1, U2>> unzipper) {
        return stream.map((T t) -> (Tuple2)unzipper.apply(t.v1, t.v2)).duplicate().map1(s -> s.map((T u) -> u.v1)).map2(s -> s.map((T u) -> u.v2));
    }

    public static <T1, T2> Seq<Tuple2<T1, T2>> zip(Stream<? extends T1> s1, Stream<? extends T2> s2) {
        return Seq.zip(Seq.seq(s1), Seq.seq(s2));
    }

    public static <T1, T2, T3> Seq<Tuple3<T1, T2, T3>> zip(Stream<? extends T1> s1, Stream<? extends T2> s2, Stream<? extends T3> s3) {
        return Seq.zip(Seq.seq(s1), Seq.seq(s2), Seq.seq(s3));
    }

    public static <T1, T2, T3, T4> Seq<Tuple4<T1, T2, T3, T4>> zip(Stream<? extends T1> s1, Stream<? extends T2> s2, Stream<? extends T3> s3, Stream<? extends T4> s4) {
        return Seq.zip(Seq.seq(s1), Seq.seq(s2), Seq.seq(s3), Seq.seq(s4));
    }

    public static <T1, T2, T3, T4, T5> Seq<Tuple5<T1, T2, T3, T4, T5>> zip(Stream<? extends T1> s1, Stream<? extends T2> s2, Stream<? extends T3> s3, Stream<? extends T4> s4, Stream<? extends T5> s5) {
        return Seq.zip(Seq.seq(s1), Seq.seq(s2), Seq.seq(s3), Seq.seq(s4), Seq.seq(s5));
    }

    public static <T1, T2, T3, T4, T5, T6> Seq<Tuple6<T1, T2, T3, T4, T5, T6>> zip(Stream<? extends T1> s1, Stream<? extends T2> s2, Stream<? extends T3> s3, Stream<? extends T4> s4, Stream<? extends T5> s5, Stream<? extends T6> s6) {
        return Seq.zip(Seq.seq(s1), Seq.seq(s2), Seq.seq(s3), Seq.seq(s4), Seq.seq(s5), Seq.seq(s6));
    }

    public static <T1, T2, T3, T4, T5, T6, T7> Seq<Tuple7<T1, T2, T3, T4, T5, T6, T7>> zip(Stream<? extends T1> s1, Stream<? extends T2> s2, Stream<? extends T3> s3, Stream<? extends T4> s4, Stream<? extends T5> s5, Stream<? extends T6> s6, Stream<? extends T7> s7) {
        return Seq.zip(Seq.seq(s1), Seq.seq(s2), Seq.seq(s3), Seq.seq(s4), Seq.seq(s5), Seq.seq(s6), Seq.seq(s7));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8> Seq<Tuple8<T1, T2, T3, T4, T5, T6, T7, T8>> zip(Stream<? extends T1> s1, Stream<? extends T2> s2, Stream<? extends T3> s3, Stream<? extends T4> s4, Stream<? extends T5> s5, Stream<? extends T6> s6, Stream<? extends T7> s7, Stream<? extends T8> s8) {
        return Seq.zip(Seq.seq(s1), Seq.seq(s2), Seq.seq(s3), Seq.seq(s4), Seq.seq(s5), Seq.seq(s6), Seq.seq(s7), Seq.seq(s8));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9> Seq<Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> zip(Stream<? extends T1> s1, Stream<? extends T2> s2, Stream<? extends T3> s3, Stream<? extends T4> s4, Stream<? extends T5> s5, Stream<? extends T6> s6, Stream<? extends T7> s7, Stream<? extends T8> s8, Stream<? extends T9> s9) {
        return Seq.zip(Seq.seq(s1), Seq.seq(s2), Seq.seq(s3), Seq.seq(s4), Seq.seq(s5), Seq.seq(s6), Seq.seq(s7), Seq.seq(s8), Seq.seq(s9));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> Seq<Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> zip(Stream<? extends T1> s1, Stream<? extends T2> s2, Stream<? extends T3> s3, Stream<? extends T4> s4, Stream<? extends T5> s5, Stream<? extends T6> s6, Stream<? extends T7> s7, Stream<? extends T8> s8, Stream<? extends T9> s9, Stream<? extends T10> s10) {
        return Seq.zip(Seq.seq(s1), Seq.seq(s2), Seq.seq(s3), Seq.seq(s4), Seq.seq(s5), Seq.seq(s6), Seq.seq(s7), Seq.seq(s8), Seq.seq(s9), Seq.seq(s10));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> Seq<Tuple11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> zip(Stream<? extends T1> s1, Stream<? extends T2> s2, Stream<? extends T3> s3, Stream<? extends T4> s4, Stream<? extends T5> s5, Stream<? extends T6> s6, Stream<? extends T7> s7, Stream<? extends T8> s8, Stream<? extends T9> s9, Stream<? extends T10> s10, Stream<? extends T11> s11) {
        return Seq.zip(Seq.seq(s1), Seq.seq(s2), Seq.seq(s3), Seq.seq(s4), Seq.seq(s5), Seq.seq(s6), Seq.seq(s7), Seq.seq(s8), Seq.seq(s9), Seq.seq(s10), Seq.seq(s11));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> Seq<Tuple12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>> zip(Stream<? extends T1> s1, Stream<? extends T2> s2, Stream<? extends T3> s3, Stream<? extends T4> s4, Stream<? extends T5> s5, Stream<? extends T6> s6, Stream<? extends T7> s7, Stream<? extends T8> s8, Stream<? extends T9> s9, Stream<? extends T10> s10, Stream<? extends T11> s11, Stream<? extends T12> s12) {
        return Seq.zip(Seq.seq(s1), Seq.seq(s2), Seq.seq(s3), Seq.seq(s4), Seq.seq(s5), Seq.seq(s6), Seq.seq(s7), Seq.seq(s8), Seq.seq(s9), Seq.seq(s10), Seq.seq(s11), Seq.seq(s12));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> Seq<Tuple13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> zip(Stream<? extends T1> s1, Stream<? extends T2> s2, Stream<? extends T3> s3, Stream<? extends T4> s4, Stream<? extends T5> s5, Stream<? extends T6> s6, Stream<? extends T7> s7, Stream<? extends T8> s8, Stream<? extends T9> s9, Stream<? extends T10> s10, Stream<? extends T11> s11, Stream<? extends T12> s12, Stream<? extends T13> s13) {
        return Seq.zip(Seq.seq(s1), Seq.seq(s2), Seq.seq(s3), Seq.seq(s4), Seq.seq(s5), Seq.seq(s6), Seq.seq(s7), Seq.seq(s8), Seq.seq(s9), Seq.seq(s10), Seq.seq(s11), Seq.seq(s12), Seq.seq(s13));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> Seq<Tuple14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> zip(Stream<? extends T1> s1, Stream<? extends T2> s2, Stream<? extends T3> s3, Stream<? extends T4> s4, Stream<? extends T5> s5, Stream<? extends T6> s6, Stream<? extends T7> s7, Stream<? extends T8> s8, Stream<? extends T9> s9, Stream<? extends T10> s10, Stream<? extends T11> s11, Stream<? extends T12> s12, Stream<? extends T13> s13, Stream<? extends T14> s14) {
        return Seq.zip(Seq.seq(s1), Seq.seq(s2), Seq.seq(s3), Seq.seq(s4), Seq.seq(s5), Seq.seq(s6), Seq.seq(s7), Seq.seq(s8), Seq.seq(s9), Seq.seq(s10), Seq.seq(s11), Seq.seq(s12), Seq.seq(s13), Seq.seq(s14));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> Seq<Tuple15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> zip(Stream<? extends T1> s1, Stream<? extends T2> s2, Stream<? extends T3> s3, Stream<? extends T4> s4, Stream<? extends T5> s5, Stream<? extends T6> s6, Stream<? extends T7> s7, Stream<? extends T8> s8, Stream<? extends T9> s9, Stream<? extends T10> s10, Stream<? extends T11> s11, Stream<? extends T12> s12, Stream<? extends T13> s13, Stream<? extends T14> s14, Stream<? extends T15> s15) {
        return Seq.zip(Seq.seq(s1), Seq.seq(s2), Seq.seq(s3), Seq.seq(s4), Seq.seq(s5), Seq.seq(s6), Seq.seq(s7), Seq.seq(s8), Seq.seq(s9), Seq.seq(s10), Seq.seq(s11), Seq.seq(s12), Seq.seq(s13), Seq.seq(s14), Seq.seq(s15));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> Seq<Tuple16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> zip(Stream<? extends T1> s1, Stream<? extends T2> s2, Stream<? extends T3> s3, Stream<? extends T4> s4, Stream<? extends T5> s5, Stream<? extends T6> s6, Stream<? extends T7> s7, Stream<? extends T8> s8, Stream<? extends T9> s9, Stream<? extends T10> s10, Stream<? extends T11> s11, Stream<? extends T12> s12, Stream<? extends T13> s13, Stream<? extends T14> s14, Stream<? extends T15> s15, Stream<? extends T16> s16) {
        return Seq.zip(Seq.seq(s1), Seq.seq(s2), Seq.seq(s3), Seq.seq(s4), Seq.seq(s5), Seq.seq(s6), Seq.seq(s7), Seq.seq(s8), Seq.seq(s9), Seq.seq(s10), Seq.seq(s11), Seq.seq(s12), Seq.seq(s13), Seq.seq(s14), Seq.seq(s15), Seq.seq(s16));
    }

    public static <T1, T2> Seq<Tuple2<T1, T2>> zip(Iterable<? extends T1> i1, Iterable<? extends T2> i2) {
        return Seq.zip(Seq.seq(i1), Seq.seq(i2));
    }

    public static <T1, T2, T3> Seq<Tuple3<T1, T2, T3>> zip(Iterable<? extends T1> i1, Iterable<? extends T2> i2, Iterable<? extends T3> i3) {
        return Seq.zip(Seq.seq(i1), Seq.seq(i2), Seq.seq(i3));
    }

    public static <T1, T2, T3, T4> Seq<Tuple4<T1, T2, T3, T4>> zip(Iterable<? extends T1> i1, Iterable<? extends T2> i2, Iterable<? extends T3> i3, Iterable<? extends T4> i4) {
        return Seq.zip(Seq.seq(i1), Seq.seq(i2), Seq.seq(i3), Seq.seq(i4));
    }

    public static <T1, T2, T3, T4, T5> Seq<Tuple5<T1, T2, T3, T4, T5>> zip(Iterable<? extends T1> i1, Iterable<? extends T2> i2, Iterable<? extends T3> i3, Iterable<? extends T4> i4, Iterable<? extends T5> i5) {
        return Seq.zip(Seq.seq(i1), Seq.seq(i2), Seq.seq(i3), Seq.seq(i4), Seq.seq(i5));
    }

    public static <T1, T2, T3, T4, T5, T6> Seq<Tuple6<T1, T2, T3, T4, T5, T6>> zip(Iterable<? extends T1> i1, Iterable<? extends T2> i2, Iterable<? extends T3> i3, Iterable<? extends T4> i4, Iterable<? extends T5> i5, Iterable<? extends T6> i6) {
        return Seq.zip(Seq.seq(i1), Seq.seq(i2), Seq.seq(i3), Seq.seq(i4), Seq.seq(i5), Seq.seq(i6));
    }

    public static <T1, T2, T3, T4, T5, T6, T7> Seq<Tuple7<T1, T2, T3, T4, T5, T6, T7>> zip(Iterable<? extends T1> i1, Iterable<? extends T2> i2, Iterable<? extends T3> i3, Iterable<? extends T4> i4, Iterable<? extends T5> i5, Iterable<? extends T6> i6, Iterable<? extends T7> i7) {
        return Seq.zip(Seq.seq(i1), Seq.seq(i2), Seq.seq(i3), Seq.seq(i4), Seq.seq(i5), Seq.seq(i6), Seq.seq(i7));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8> Seq<Tuple8<T1, T2, T3, T4, T5, T6, T7, T8>> zip(Iterable<? extends T1> i1, Iterable<? extends T2> i2, Iterable<? extends T3> i3, Iterable<? extends T4> i4, Iterable<? extends T5> i5, Iterable<? extends T6> i6, Iterable<? extends T7> i7, Iterable<? extends T8> i8) {
        return Seq.zip(Seq.seq(i1), Seq.seq(i2), Seq.seq(i3), Seq.seq(i4), Seq.seq(i5), Seq.seq(i6), Seq.seq(i7), Seq.seq(i8));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9> Seq<Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> zip(Iterable<? extends T1> i1, Iterable<? extends T2> i2, Iterable<? extends T3> i3, Iterable<? extends T4> i4, Iterable<? extends T5> i5, Iterable<? extends T6> i6, Iterable<? extends T7> i7, Iterable<? extends T8> i8, Iterable<? extends T9> i9) {
        return Seq.zip(Seq.seq(i1), Seq.seq(i2), Seq.seq(i3), Seq.seq(i4), Seq.seq(i5), Seq.seq(i6), Seq.seq(i7), Seq.seq(i8), Seq.seq(i9));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> Seq<Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> zip(Iterable<? extends T1> i1, Iterable<? extends T2> i2, Iterable<? extends T3> i3, Iterable<? extends T4> i4, Iterable<? extends T5> i5, Iterable<? extends T6> i6, Iterable<? extends T7> i7, Iterable<? extends T8> i8, Iterable<? extends T9> i9, Iterable<? extends T10> i10) {
        return Seq.zip(Seq.seq(i1), Seq.seq(i2), Seq.seq(i3), Seq.seq(i4), Seq.seq(i5), Seq.seq(i6), Seq.seq(i7), Seq.seq(i8), Seq.seq(i9), Seq.seq(i10));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> Seq<Tuple11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> zip(Iterable<? extends T1> i1, Iterable<? extends T2> i2, Iterable<? extends T3> i3, Iterable<? extends T4> i4, Iterable<? extends T5> i5, Iterable<? extends T6> i6, Iterable<? extends T7> i7, Iterable<? extends T8> i8, Iterable<? extends T9> i9, Iterable<? extends T10> i10, Iterable<? extends T11> i11) {
        return Seq.zip(Seq.seq(i1), Seq.seq(i2), Seq.seq(i3), Seq.seq(i4), Seq.seq(i5), Seq.seq(i6), Seq.seq(i7), Seq.seq(i8), Seq.seq(i9), Seq.seq(i10), Seq.seq(i11));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> Seq<Tuple12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>> zip(Iterable<? extends T1> i1, Iterable<? extends T2> i2, Iterable<? extends T3> i3, Iterable<? extends T4> i4, Iterable<? extends T5> i5, Iterable<? extends T6> i6, Iterable<? extends T7> i7, Iterable<? extends T8> i8, Iterable<? extends T9> i9, Iterable<? extends T10> i10, Iterable<? extends T11> i11, Iterable<? extends T12> i12) {
        return Seq.zip(Seq.seq(i1), Seq.seq(i2), Seq.seq(i3), Seq.seq(i4), Seq.seq(i5), Seq.seq(i6), Seq.seq(i7), Seq.seq(i8), Seq.seq(i9), Seq.seq(i10), Seq.seq(i11), Seq.seq(i12));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> Seq<Tuple13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> zip(Iterable<? extends T1> i1, Iterable<? extends T2> i2, Iterable<? extends T3> i3, Iterable<? extends T4> i4, Iterable<? extends T5> i5, Iterable<? extends T6> i6, Iterable<? extends T7> i7, Iterable<? extends T8> i8, Iterable<? extends T9> i9, Iterable<? extends T10> i10, Iterable<? extends T11> i11, Iterable<? extends T12> i12, Iterable<? extends T13> i13) {
        return Seq.zip(Seq.seq(i1), Seq.seq(i2), Seq.seq(i3), Seq.seq(i4), Seq.seq(i5), Seq.seq(i6), Seq.seq(i7), Seq.seq(i8), Seq.seq(i9), Seq.seq(i10), Seq.seq(i11), Seq.seq(i12), Seq.seq(i13));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> Seq<Tuple14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> zip(Iterable<? extends T1> i1, Iterable<? extends T2> i2, Iterable<? extends T3> i3, Iterable<? extends T4> i4, Iterable<? extends T5> i5, Iterable<? extends T6> i6, Iterable<? extends T7> i7, Iterable<? extends T8> i8, Iterable<? extends T9> i9, Iterable<? extends T10> i10, Iterable<? extends T11> i11, Iterable<? extends T12> i12, Iterable<? extends T13> i13, Iterable<? extends T14> i14) {
        return Seq.zip(Seq.seq(i1), Seq.seq(i2), Seq.seq(i3), Seq.seq(i4), Seq.seq(i5), Seq.seq(i6), Seq.seq(i7), Seq.seq(i8), Seq.seq(i9), Seq.seq(i10), Seq.seq(i11), Seq.seq(i12), Seq.seq(i13), Seq.seq(i14));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> Seq<Tuple15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> zip(Iterable<? extends T1> i1, Iterable<? extends T2> i2, Iterable<? extends T3> i3, Iterable<? extends T4> i4, Iterable<? extends T5> i5, Iterable<? extends T6> i6, Iterable<? extends T7> i7, Iterable<? extends T8> i8, Iterable<? extends T9> i9, Iterable<? extends T10> i10, Iterable<? extends T11> i11, Iterable<? extends T12> i12, Iterable<? extends T13> i13, Iterable<? extends T14> i14, Iterable<? extends T15> i15) {
        return Seq.zip(Seq.seq(i1), Seq.seq(i2), Seq.seq(i3), Seq.seq(i4), Seq.seq(i5), Seq.seq(i6), Seq.seq(i7), Seq.seq(i8), Seq.seq(i9), Seq.seq(i10), Seq.seq(i11), Seq.seq(i12), Seq.seq(i13), Seq.seq(i14), Seq.seq(i15));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> Seq<Tuple16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> zip(Iterable<? extends T1> i1, Iterable<? extends T2> i2, Iterable<? extends T3> i3, Iterable<? extends T4> i4, Iterable<? extends T5> i5, Iterable<? extends T6> i6, Iterable<? extends T7> i7, Iterable<? extends T8> i8, Iterable<? extends T9> i9, Iterable<? extends T10> i10, Iterable<? extends T11> i11, Iterable<? extends T12> i12, Iterable<? extends T13> i13, Iterable<? extends T14> i14, Iterable<? extends T15> i15, Iterable<? extends T16> i16) {
        return Seq.zip(Seq.seq(i1), Seq.seq(i2), Seq.seq(i3), Seq.seq(i4), Seq.seq(i5), Seq.seq(i6), Seq.seq(i7), Seq.seq(i8), Seq.seq(i9), Seq.seq(i10), Seq.seq(i11), Seq.seq(i12), Seq.seq(i13), Seq.seq(i14), Seq.seq(i15), Seq.seq(i16));
    }

    public static <T1, T2> Seq<Tuple2<T1, T2>> zip(Seq<? extends T1> s1, Seq<? extends T2> s2) {
        return Seq.zip(s1, s2, (? super T1 t1, ? super T2 t2) -> Tuple.tuple(t1, t2)).onClose(SeqUtils.closeAll(s1, s2));
    }

    public static <T1, T2, T3> Seq<Tuple3<T1, T2, T3>> zip(Seq<? extends T1> s1, Seq<? extends T2> s2, Seq<? extends T3> s3) {
        return Seq.zip(s1, s2, s3, (? super T1 t1, ? super T2 t2, ? super T3 t3) -> Tuple.tuple(t1, t2, t3)).onClose(SeqUtils.closeAll(s1, s2, s3));
    }

    public static <T1, T2, T3, T4> Seq<Tuple4<T1, T2, T3, T4>> zip(Seq<? extends T1> s1, Seq<? extends T2> s2, Seq<? extends T3> s3, Seq<? extends T4> s4) {
        return Seq.zip(s1, s2, s3, s4, (? super T1 t1, ? super T2 t2, ? super T3 t3, ? super T4 t4) -> Tuple.tuple(t1, t2, t3, t4)).onClose(SeqUtils.closeAll(s1, s2, s3, s4));
    }

    public static <T1, T2, T3, T4, T5> Seq<Tuple5<T1, T2, T3, T4, T5>> zip(Seq<? extends T1> s1, Seq<? extends T2> s2, Seq<? extends T3> s3, Seq<? extends T4> s4, Seq<? extends T5> s5) {
        return Seq.zip(s1, s2, s3, s4, s5, (? super T1 t1, ? super T2 t2, ? super T3 t3, ? super T4 t4, ? super T5 t5) -> Tuple.tuple(t1, t2, t3, t4, t5)).onClose(SeqUtils.closeAll(s1, s2, s3, s4, s5));
    }

    public static <T1, T2, T3, T4, T5, T6> Seq<Tuple6<T1, T2, T3, T4, T5, T6>> zip(Seq<? extends T1> s1, Seq<? extends T2> s2, Seq<? extends T3> s3, Seq<? extends T4> s4, Seq<? extends T5> s5, Seq<? extends T6> s6) {
        return Seq.zip(s1, s2, s3, s4, s5, s6, (? super T1 t1, ? super T2 t2, ? super T3 t3, ? super T4 t4, ? super T5 t5, ? super T6 t6) -> Tuple.tuple(t1, t2, t3, t4, t5, t6)).onClose(SeqUtils.closeAll(s1, s2, s3, s4, s5, s6));
    }

    public static <T1, T2, T3, T4, T5, T6, T7> Seq<Tuple7<T1, T2, T3, T4, T5, T6, T7>> zip(Seq<? extends T1> s1, Seq<? extends T2> s2, Seq<? extends T3> s3, Seq<? extends T4> s4, Seq<? extends T5> s5, Seq<? extends T6> s6, Seq<? extends T7> s7) {
        return Seq.zip(s1, s2, s3, s4, s5, s6, s7, (? super T1 t1, ? super T2 t2, ? super T3 t3, ? super T4 t4, ? super T5 t5, ? super T6 t6, ? super T7 t7) -> Tuple.tuple(t1, t2, t3, t4, t5, t6, t7)).onClose(SeqUtils.closeAll(s1, s2, s3, s4, s5, s6, s7));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8> Seq<Tuple8<T1, T2, T3, T4, T5, T6, T7, T8>> zip(Seq<? extends T1> s1, Seq<? extends T2> s2, Seq<? extends T3> s3, Seq<? extends T4> s4, Seq<? extends T5> s5, Seq<? extends T6> s6, Seq<? extends T7> s7, Seq<? extends T8> s8) {
        return Seq.zip(s1, s2, s3, s4, s5, s6, s7, s8, (? super T1 t1, ? super T2 t2, ? super T3 t3, ? super T4 t4, ? super T5 t5, ? super T6 t6, ? super T7 t7, ? super T8 t8) -> Tuple.tuple(t1, t2, t3, t4, t5, t6, t7, t8)).onClose(SeqUtils.closeAll(s1, s2, s3, s4, s5, s6, s7, s8));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9> Seq<Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> zip(Seq<? extends T1> s1, Seq<? extends T2> s2, Seq<? extends T3> s3, Seq<? extends T4> s4, Seq<? extends T5> s5, Seq<? extends T6> s6, Seq<? extends T7> s7, Seq<? extends T8> s8, Seq<? extends T9> s9) {
        return Seq.zip(s1, s2, s3, s4, s5, s6, s7, s8, s9, (? super T1 t1, ? super T2 t2, ? super T3 t3, ? super T4 t4, ? super T5 t5, ? super T6 t6, ? super T7 t7, ? super T8 t8, ? super T9 t9) -> Tuple.tuple(t1, t2, t3, t4, t5, t6, t7, t8, t9)).onClose(SeqUtils.closeAll(s1, s2, s3, s4, s5, s6, s7, s8, s9));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> Seq<Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> zip(Seq<? extends T1> s1, Seq<? extends T2> s2, Seq<? extends T3> s3, Seq<? extends T4> s4, Seq<? extends T5> s5, Seq<? extends T6> s6, Seq<? extends T7> s7, Seq<? extends T8> s8, Seq<? extends T9> s9, Seq<? extends T10> s10) {
        return Seq.zip(s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, (? super T1 t1, ? super T2 t2, ? super T3 t3, ? super T4 t4, ? super T5 t5, ? super T6 t6, ? super T7 t7, ? super T8 t8, ? super T9 t9, ? super T10 t10) -> Tuple.tuple(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10)).onClose(SeqUtils.closeAll(s1, s2, s3, s4, s5, s6, s7, s8, s9, s10));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> Seq<Tuple11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> zip(Seq<? extends T1> s1, Seq<? extends T2> s2, Seq<? extends T3> s3, Seq<? extends T4> s4, Seq<? extends T5> s5, Seq<? extends T6> s6, Seq<? extends T7> s7, Seq<? extends T8> s8, Seq<? extends T9> s9, Seq<? extends T10> s10, Seq<? extends T11> s11) {
        return Seq.zip(s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, (? super T1 t1, ? super T2 t2, ? super T3 t3, ? super T4 t4, ? super T5 t5, ? super T6 t6, ? super T7 t7, ? super T8 t8, ? super T9 t9, ? super T10 t10, ? super T11 t11) -> Tuple.tuple(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11)).onClose(SeqUtils.closeAll(s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> Seq<Tuple12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>> zip(Seq<? extends T1> s1, Seq<? extends T2> s2, Seq<? extends T3> s3, Seq<? extends T4> s4, Seq<? extends T5> s5, Seq<? extends T6> s6, Seq<? extends T7> s7, Seq<? extends T8> s8, Seq<? extends T9> s9, Seq<? extends T10> s10, Seq<? extends T11> s11, Seq<? extends T12> s12) {
        return Seq.zip(s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, s12, (? super T1 t1, ? super T2 t2, ? super T3 t3, ? super T4 t4, ? super T5 t5, ? super T6 t6, ? super T7 t7, ? super T8 t8, ? super T9 t9, ? super T10 t10, ? super T11 t11, ? super T12 t12) -> Tuple.tuple(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12)).onClose(SeqUtils.closeAll(s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, s12));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> Seq<Tuple13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> zip(Seq<? extends T1> s1, Seq<? extends T2> s2, Seq<? extends T3> s3, Seq<? extends T4> s4, Seq<? extends T5> s5, Seq<? extends T6> s6, Seq<? extends T7> s7, Seq<? extends T8> s8, Seq<? extends T9> s9, Seq<? extends T10> s10, Seq<? extends T11> s11, Seq<? extends T12> s12, Seq<? extends T13> s13) {
        return Seq.zip(s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, s12, s13, (? super T1 t1, ? super T2 t2, ? super T3 t3, ? super T4 t4, ? super T5 t5, ? super T6 t6, ? super T7 t7, ? super T8 t8, ? super T9 t9, ? super T10 t10, ? super T11 t11, ? super T12 t12, ? super T13 t13) -> Tuple.tuple(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13)).onClose(SeqUtils.closeAll(s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, s12, s13));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> Seq<Tuple14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> zip(Seq<? extends T1> s1, Seq<? extends T2> s2, Seq<? extends T3> s3, Seq<? extends T4> s4, Seq<? extends T5> s5, Seq<? extends T6> s6, Seq<? extends T7> s7, Seq<? extends T8> s8, Seq<? extends T9> s9, Seq<? extends T10> s10, Seq<? extends T11> s11, Seq<? extends T12> s12, Seq<? extends T13> s13, Seq<? extends T14> s14) {
        return Seq.zip(s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, s12, s13, s14, (? super T1 t1, ? super T2 t2, ? super T3 t3, ? super T4 t4, ? super T5 t5, ? super T6 t6, ? super T7 t7, ? super T8 t8, ? super T9 t9, ? super T10 t10, ? super T11 t11, ? super T12 t12, ? super T13 t13, ? super T14 t14) -> Tuple.tuple(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14)).onClose(SeqUtils.closeAll(s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, s12, s13, s14));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> Seq<Tuple15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> zip(Seq<? extends T1> s1, Seq<? extends T2> s2, Seq<? extends T3> s3, Seq<? extends T4> s4, Seq<? extends T5> s5, Seq<? extends T6> s6, Seq<? extends T7> s7, Seq<? extends T8> s8, Seq<? extends T9> s9, Seq<? extends T10> s10, Seq<? extends T11> s11, Seq<? extends T12> s12, Seq<? extends T13> s13, Seq<? extends T14> s14, Seq<? extends T15> s15) {
        return Seq.zip(s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, s12, s13, s14, s15, (? super T1 t1, ? super T2 t2, ? super T3 t3, ? super T4 t4, ? super T5 t5, ? super T6 t6, ? super T7 t7, ? super T8 t8, ? super T9 t9, ? super T10 t10, ? super T11 t11, ? super T12 t12, ? super T13 t13, ? super T14 t14, ? super T15 t15) -> Tuple.tuple(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15)).onClose(SeqUtils.closeAll(s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, s12, s13, s14, s15));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> Seq<Tuple16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> zip(Seq<? extends T1> s1, Seq<? extends T2> s2, Seq<? extends T3> s3, Seq<? extends T4> s4, Seq<? extends T5> s5, Seq<? extends T6> s6, Seq<? extends T7> s7, Seq<? extends T8> s8, Seq<? extends T9> s9, Seq<? extends T10> s10, Seq<? extends T11> s11, Seq<? extends T12> s12, Seq<? extends T13> s13, Seq<? extends T14> s14, Seq<? extends T15> s15, Seq<? extends T16> s16) {
        return Seq.zip(s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, s12, s13, s14, s15, s16, (? super T1 t1, ? super T2 t2, ? super T3 t3, ? super T4 t4, ? super T5 t5, ? super T6 t6, ? super T7 t7, ? super T8 t8, ? super T9 t9, ? super T10 t10, ? super T11 t11, ? super T12 t12, ? super T13 t13, ? super T14 t14, ? super T15 t15, ? super T16 t16) -> Tuple.tuple(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16)).onClose(SeqUtils.closeAll(s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, s12, s13, s14, s15, s16));
    }

    public static <T1, T2, R> Seq<R> zip(Stream<? extends T1> s1, Stream<? extends T2> s2, BiFunction<? super T1, ? super T2, ? extends R> zipper) {
        return Seq.zip(Seq.seq(s1), Seq.seq(s2), zipper);
    }

    public static <T1, T2, T3, R> Seq<R> zip(Stream<? extends T1> s1, Stream<? extends T2> s2, Stream<? extends T3> s3, Function3<? super T1, ? super T2, ? super T3, ? extends R> zipper) {
        return Seq.zip(Seq.seq(s1), Seq.seq(s2), Seq.seq(s3), zipper);
    }

    public static <T1, T2, T3, T4, R> Seq<R> zip(Stream<? extends T1> s1, Stream<? extends T2> s2, Stream<? extends T3> s3, Stream<? extends T4> s4, Function4<? super T1, ? super T2, ? super T3, ? super T4, ? extends R> zipper) {
        return Seq.zip(Seq.seq(s1), Seq.seq(s2), Seq.seq(s3), Seq.seq(s4), zipper);
    }

    public static <T1, T2, T3, T4, T5, R> Seq<R> zip(Stream<? extends T1> s1, Stream<? extends T2> s2, Stream<? extends T3> s3, Stream<? extends T4> s4, Stream<? extends T5> s5, Function5<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? extends R> zipper) {
        return Seq.zip(Seq.seq(s1), Seq.seq(s2), Seq.seq(s3), Seq.seq(s4), Seq.seq(s5), zipper);
    }

    public static <T1, T2, T3, T4, T5, T6, R> Seq<R> zip(Stream<? extends T1> s1, Stream<? extends T2> s2, Stream<? extends T3> s3, Stream<? extends T4> s4, Stream<? extends T5> s5, Stream<? extends T6> s6, Function6<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? extends R> zipper) {
        return Seq.zip(Seq.seq(s1), Seq.seq(s2), Seq.seq(s3), Seq.seq(s4), Seq.seq(s5), Seq.seq(s6), zipper);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, R> Seq<R> zip(Stream<? extends T1> s1, Stream<? extends T2> s2, Stream<? extends T3> s3, Stream<? extends T4> s4, Stream<? extends T5> s5, Stream<? extends T6> s6, Stream<? extends T7> s7, Function7<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? extends R> zipper) {
        return Seq.zip(Seq.seq(s1), Seq.seq(s2), Seq.seq(s3), Seq.seq(s4), Seq.seq(s5), Seq.seq(s6), Seq.seq(s7), zipper);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, R> Seq<R> zip(Stream<? extends T1> s1, Stream<? extends T2> s2, Stream<? extends T3> s3, Stream<? extends T4> s4, Stream<? extends T5> s5, Stream<? extends T6> s6, Stream<? extends T7> s7, Stream<? extends T8> s8, Function8<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? extends R> zipper) {
        return Seq.zip(Seq.seq(s1), Seq.seq(s2), Seq.seq(s3), Seq.seq(s4), Seq.seq(s5), Seq.seq(s6), Seq.seq(s7), Seq.seq(s8), zipper);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, R> Seq<R> zip(Stream<? extends T1> s1, Stream<? extends T2> s2, Stream<? extends T3> s3, Stream<? extends T4> s4, Stream<? extends T5> s5, Stream<? extends T6> s6, Stream<? extends T7> s7, Stream<? extends T8> s8, Stream<? extends T9> s9, Function9<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? extends R> zipper) {
        return Seq.zip(Seq.seq(s1), Seq.seq(s2), Seq.seq(s3), Seq.seq(s4), Seq.seq(s5), Seq.seq(s6), Seq.seq(s7), Seq.seq(s8), Seq.seq(s9), zipper);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, R> Seq<R> zip(Stream<? extends T1> s1, Stream<? extends T2> s2, Stream<? extends T3> s3, Stream<? extends T4> s4, Stream<? extends T5> s5, Stream<? extends T6> s6, Stream<? extends T7> s7, Stream<? extends T8> s8, Stream<? extends T9> s9, Stream<? extends T10> s10, Function10<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? super T10, ? extends R> zipper) {
        return Seq.zip(Seq.seq(s1), Seq.seq(s2), Seq.seq(s3), Seq.seq(s4), Seq.seq(s5), Seq.seq(s6), Seq.seq(s7), Seq.seq(s8), Seq.seq(s9), Seq.seq(s10), zipper);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, R> Seq<R> zip(Stream<? extends T1> s1, Stream<? extends T2> s2, Stream<? extends T3> s3, Stream<? extends T4> s4, Stream<? extends T5> s5, Stream<? extends T6> s6, Stream<? extends T7> s7, Stream<? extends T8> s8, Stream<? extends T9> s9, Stream<? extends T10> s10, Stream<? extends T11> s11, Function11<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? super T10, ? super T11, ? extends R> zipper) {
        return Seq.zip(Seq.seq(s1), Seq.seq(s2), Seq.seq(s3), Seq.seq(s4), Seq.seq(s5), Seq.seq(s6), Seq.seq(s7), Seq.seq(s8), Seq.seq(s9), Seq.seq(s10), Seq.seq(s11), zipper);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, R> Seq<R> zip(Stream<? extends T1> s1, Stream<? extends T2> s2, Stream<? extends T3> s3, Stream<? extends T4> s4, Stream<? extends T5> s5, Stream<? extends T6> s6, Stream<? extends T7> s7, Stream<? extends T8> s8, Stream<? extends T9> s9, Stream<? extends T10> s10, Stream<? extends T11> s11, Stream<? extends T12> s12, Function12<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? super T10, ? super T11, ? super T12, ? extends R> zipper) {
        return Seq.zip(Seq.seq(s1), Seq.seq(s2), Seq.seq(s3), Seq.seq(s4), Seq.seq(s5), Seq.seq(s6), Seq.seq(s7), Seq.seq(s8), Seq.seq(s9), Seq.seq(s10), Seq.seq(s11), Seq.seq(s12), zipper);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, R> Seq<R> zip(Stream<? extends T1> s1, Stream<? extends T2> s2, Stream<? extends T3> s3, Stream<? extends T4> s4, Stream<? extends T5> s5, Stream<? extends T6> s6, Stream<? extends T7> s7, Stream<? extends T8> s8, Stream<? extends T9> s9, Stream<? extends T10> s10, Stream<? extends T11> s11, Stream<? extends T12> s12, Stream<? extends T13> s13, Function13<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? super T10, ? super T11, ? super T12, ? super T13, ? extends R> zipper) {
        return Seq.zip(Seq.seq(s1), Seq.seq(s2), Seq.seq(s3), Seq.seq(s4), Seq.seq(s5), Seq.seq(s6), Seq.seq(s7), Seq.seq(s8), Seq.seq(s9), Seq.seq(s10), Seq.seq(s11), Seq.seq(s12), Seq.seq(s13), zipper);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, R> Seq<R> zip(Stream<? extends T1> s1, Stream<? extends T2> s2, Stream<? extends T3> s3, Stream<? extends T4> s4, Stream<? extends T5> s5, Stream<? extends T6> s6, Stream<? extends T7> s7, Stream<? extends T8> s8, Stream<? extends T9> s9, Stream<? extends T10> s10, Stream<? extends T11> s11, Stream<? extends T12> s12, Stream<? extends T13> s13, Stream<? extends T14> s14, Function14<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? super T10, ? super T11, ? super T12, ? super T13, ? super T14, ? extends R> zipper) {
        return Seq.zip(Seq.seq(s1), Seq.seq(s2), Seq.seq(s3), Seq.seq(s4), Seq.seq(s5), Seq.seq(s6), Seq.seq(s7), Seq.seq(s8), Seq.seq(s9), Seq.seq(s10), Seq.seq(s11), Seq.seq(s12), Seq.seq(s13), Seq.seq(s14), zipper);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, R> Seq<R> zip(Stream<? extends T1> s1, Stream<? extends T2> s2, Stream<? extends T3> s3, Stream<? extends T4> s4, Stream<? extends T5> s5, Stream<? extends T6> s6, Stream<? extends T7> s7, Stream<? extends T8> s8, Stream<? extends T9> s9, Stream<? extends T10> s10, Stream<? extends T11> s11, Stream<? extends T12> s12, Stream<? extends T13> s13, Stream<? extends T14> s14, Stream<? extends T15> s15, Function15<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? super T10, ? super T11, ? super T12, ? super T13, ? super T14, ? super T15, ? extends R> zipper) {
        return Seq.zip(Seq.seq(s1), Seq.seq(s2), Seq.seq(s3), Seq.seq(s4), Seq.seq(s5), Seq.seq(s6), Seq.seq(s7), Seq.seq(s8), Seq.seq(s9), Seq.seq(s10), Seq.seq(s11), Seq.seq(s12), Seq.seq(s13), Seq.seq(s14), Seq.seq(s15), zipper);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, R> Seq<R> zip(Stream<? extends T1> s1, Stream<? extends T2> s2, Stream<? extends T3> s3, Stream<? extends T4> s4, Stream<? extends T5> s5, Stream<? extends T6> s6, Stream<? extends T7> s7, Stream<? extends T8> s8, Stream<? extends T9> s9, Stream<? extends T10> s10, Stream<? extends T11> s11, Stream<? extends T12> s12, Stream<? extends T13> s13, Stream<? extends T14> s14, Stream<? extends T15> s15, Stream<? extends T16> s16, Function16<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? super T10, ? super T11, ? super T12, ? super T13, ? super T14, ? super T15, ? super T16, ? extends R> zipper) {
        return Seq.zip(Seq.seq(s1), Seq.seq(s2), Seq.seq(s3), Seq.seq(s4), Seq.seq(s5), Seq.seq(s6), Seq.seq(s7), Seq.seq(s8), Seq.seq(s9), Seq.seq(s10), Seq.seq(s11), Seq.seq(s12), Seq.seq(s13), Seq.seq(s14), Seq.seq(s15), Seq.seq(s16), zipper);
    }

    public static <T1, T2, R> Seq<R> zip(Iterable<? extends T1> i1, Iterable<? extends T2> i2, BiFunction<? super T1, ? super T2, ? extends R> zipper) {
        return Seq.zip(Seq.seq(i1), Seq.seq(i2), zipper);
    }

    public static <T1, T2, T3, R> Seq<R> zip(Iterable<? extends T1> i1, Iterable<? extends T2> i2, Iterable<? extends T3> i3, Function3<? super T1, ? super T2, ? super T3, ? extends R> zipper) {
        return Seq.zip(Seq.seq(i1), Seq.seq(i2), Seq.seq(i3), zipper);
    }

    public static <T1, T2, T3, T4, R> Seq<R> zip(Iterable<? extends T1> i1, Iterable<? extends T2> i2, Iterable<? extends T3> i3, Iterable<? extends T4> i4, Function4<? super T1, ? super T2, ? super T3, ? super T4, ? extends R> zipper) {
        return Seq.zip(Seq.seq(i1), Seq.seq(i2), Seq.seq(i3), Seq.seq(i4), zipper);
    }

    public static <T1, T2, T3, T4, T5, R> Seq<R> zip(Iterable<? extends T1> i1, Iterable<? extends T2> i2, Iterable<? extends T3> i3, Iterable<? extends T4> i4, Iterable<? extends T5> i5, Function5<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? extends R> zipper) {
        return Seq.zip(Seq.seq(i1), Seq.seq(i2), Seq.seq(i3), Seq.seq(i4), Seq.seq(i5), zipper);
    }

    public static <T1, T2, T3, T4, T5, T6, R> Seq<R> zip(Iterable<? extends T1> i1, Iterable<? extends T2> i2, Iterable<? extends T3> i3, Iterable<? extends T4> i4, Iterable<? extends T5> i5, Iterable<? extends T6> i6, Function6<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? extends R> zipper) {
        return Seq.zip(Seq.seq(i1), Seq.seq(i2), Seq.seq(i3), Seq.seq(i4), Seq.seq(i5), Seq.seq(i6), zipper);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, R> Seq<R> zip(Iterable<? extends T1> i1, Iterable<? extends T2> i2, Iterable<? extends T3> i3, Iterable<? extends T4> i4, Iterable<? extends T5> i5, Iterable<? extends T6> i6, Iterable<? extends T7> i7, Function7<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? extends R> zipper) {
        return Seq.zip(Seq.seq(i1), Seq.seq(i2), Seq.seq(i3), Seq.seq(i4), Seq.seq(i5), Seq.seq(i6), Seq.seq(i7), zipper);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, R> Seq<R> zip(Iterable<? extends T1> i1, Iterable<? extends T2> i2, Iterable<? extends T3> i3, Iterable<? extends T4> i4, Iterable<? extends T5> i5, Iterable<? extends T6> i6, Iterable<? extends T7> i7, Iterable<? extends T8> i8, Function8<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? extends R> zipper) {
        return Seq.zip(Seq.seq(i1), Seq.seq(i2), Seq.seq(i3), Seq.seq(i4), Seq.seq(i5), Seq.seq(i6), Seq.seq(i7), Seq.seq(i8), zipper);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, R> Seq<R> zip(Iterable<? extends T1> i1, Iterable<? extends T2> i2, Iterable<? extends T3> i3, Iterable<? extends T4> i4, Iterable<? extends T5> i5, Iterable<? extends T6> i6, Iterable<? extends T7> i7, Iterable<? extends T8> i8, Iterable<? extends T9> i9, Function9<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? extends R> zipper) {
        return Seq.zip(Seq.seq(i1), Seq.seq(i2), Seq.seq(i3), Seq.seq(i4), Seq.seq(i5), Seq.seq(i6), Seq.seq(i7), Seq.seq(i8), Seq.seq(i9), zipper);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, R> Seq<R> zip(Iterable<? extends T1> i1, Iterable<? extends T2> i2, Iterable<? extends T3> i3, Iterable<? extends T4> i4, Iterable<? extends T5> i5, Iterable<? extends T6> i6, Iterable<? extends T7> i7, Iterable<? extends T8> i8, Iterable<? extends T9> i9, Iterable<? extends T10> i10, Function10<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? super T10, ? extends R> zipper) {
        return Seq.zip(Seq.seq(i1), Seq.seq(i2), Seq.seq(i3), Seq.seq(i4), Seq.seq(i5), Seq.seq(i6), Seq.seq(i7), Seq.seq(i8), Seq.seq(i9), Seq.seq(i10), zipper);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, R> Seq<R> zip(Iterable<? extends T1> i1, Iterable<? extends T2> i2, Iterable<? extends T3> i3, Iterable<? extends T4> i4, Iterable<? extends T5> i5, Iterable<? extends T6> i6, Iterable<? extends T7> i7, Iterable<? extends T8> i8, Iterable<? extends T9> i9, Iterable<? extends T10> i10, Iterable<? extends T11> i11, Function11<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? super T10, ? super T11, ? extends R> zipper) {
        return Seq.zip(Seq.seq(i1), Seq.seq(i2), Seq.seq(i3), Seq.seq(i4), Seq.seq(i5), Seq.seq(i6), Seq.seq(i7), Seq.seq(i8), Seq.seq(i9), Seq.seq(i10), Seq.seq(i11), zipper);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, R> Seq<R> zip(Iterable<? extends T1> i1, Iterable<? extends T2> i2, Iterable<? extends T3> i3, Iterable<? extends T4> i4, Iterable<? extends T5> i5, Iterable<? extends T6> i6, Iterable<? extends T7> i7, Iterable<? extends T8> i8, Iterable<? extends T9> i9, Iterable<? extends T10> i10, Iterable<? extends T11> i11, Iterable<? extends T12> i12, Function12<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? super T10, ? super T11, ? super T12, ? extends R> zipper) {
        return Seq.zip(Seq.seq(i1), Seq.seq(i2), Seq.seq(i3), Seq.seq(i4), Seq.seq(i5), Seq.seq(i6), Seq.seq(i7), Seq.seq(i8), Seq.seq(i9), Seq.seq(i10), Seq.seq(i11), Seq.seq(i12), zipper);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, R> Seq<R> zip(Iterable<? extends T1> i1, Iterable<? extends T2> i2, Iterable<? extends T3> i3, Iterable<? extends T4> i4, Iterable<? extends T5> i5, Iterable<? extends T6> i6, Iterable<? extends T7> i7, Iterable<? extends T8> i8, Iterable<? extends T9> i9, Iterable<? extends T10> i10, Iterable<? extends T11> i11, Iterable<? extends T12> i12, Iterable<? extends T13> i13, Function13<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? super T10, ? super T11, ? super T12, ? super T13, ? extends R> zipper) {
        return Seq.zip(Seq.seq(i1), Seq.seq(i2), Seq.seq(i3), Seq.seq(i4), Seq.seq(i5), Seq.seq(i6), Seq.seq(i7), Seq.seq(i8), Seq.seq(i9), Seq.seq(i10), Seq.seq(i11), Seq.seq(i12), Seq.seq(i13), zipper);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, R> Seq<R> zip(Iterable<? extends T1> i1, Iterable<? extends T2> i2, Iterable<? extends T3> i3, Iterable<? extends T4> i4, Iterable<? extends T5> i5, Iterable<? extends T6> i6, Iterable<? extends T7> i7, Iterable<? extends T8> i8, Iterable<? extends T9> i9, Iterable<? extends T10> i10, Iterable<? extends T11> i11, Iterable<? extends T12> i12, Iterable<? extends T13> i13, Iterable<? extends T14> i14, Function14<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? super T10, ? super T11, ? super T12, ? super T13, ? super T14, ? extends R> zipper) {
        return Seq.zip(Seq.seq(i1), Seq.seq(i2), Seq.seq(i3), Seq.seq(i4), Seq.seq(i5), Seq.seq(i6), Seq.seq(i7), Seq.seq(i8), Seq.seq(i9), Seq.seq(i10), Seq.seq(i11), Seq.seq(i12), Seq.seq(i13), Seq.seq(i14), zipper);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, R> Seq<R> zip(Iterable<? extends T1> i1, Iterable<? extends T2> i2, Iterable<? extends T3> i3, Iterable<? extends T4> i4, Iterable<? extends T5> i5, Iterable<? extends T6> i6, Iterable<? extends T7> i7, Iterable<? extends T8> i8, Iterable<? extends T9> i9, Iterable<? extends T10> i10, Iterable<? extends T11> i11, Iterable<? extends T12> i12, Iterable<? extends T13> i13, Iterable<? extends T14> i14, Iterable<? extends T15> i15, Function15<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? super T10, ? super T11, ? super T12, ? super T13, ? super T14, ? super T15, ? extends R> zipper) {
        return Seq.zip(Seq.seq(i1), Seq.seq(i2), Seq.seq(i3), Seq.seq(i4), Seq.seq(i5), Seq.seq(i6), Seq.seq(i7), Seq.seq(i8), Seq.seq(i9), Seq.seq(i10), Seq.seq(i11), Seq.seq(i12), Seq.seq(i13), Seq.seq(i14), Seq.seq(i15), zipper);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, R> Seq<R> zip(Iterable<? extends T1> i1, Iterable<? extends T2> i2, Iterable<? extends T3> i3, Iterable<? extends T4> i4, Iterable<? extends T5> i5, Iterable<? extends T6> i6, Iterable<? extends T7> i7, Iterable<? extends T8> i8, Iterable<? extends T9> i9, Iterable<? extends T10> i10, Iterable<? extends T11> i11, Iterable<? extends T12> i12, Iterable<? extends T13> i13, Iterable<? extends T14> i14, Iterable<? extends T15> i15, Iterable<? extends T16> i16, Function16<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? super T10, ? super T11, ? super T12, ? super T13, ? super T14, ? super T15, ? super T16, ? extends R> zipper) {
        return Seq.zip(Seq.seq(i1), Seq.seq(i2), Seq.seq(i3), Seq.seq(i4), Seq.seq(i5), Seq.seq(i6), Seq.seq(i7), Seq.seq(i8), Seq.seq(i9), Seq.seq(i10), Seq.seq(i11), Seq.seq(i12), Seq.seq(i13), Seq.seq(i14), Seq.seq(i15), Seq.seq(i16), zipper);
    }

    public static <T1, T2, R> Seq<R> zip(Seq<? extends T1> s1, Seq<? extends T2> s2, final BiFunction<? super T1, ? super T2, ? extends R> zipper) {
        final Iterator it1 = s1.iterator();
        final Iterator it2 = s2.iterator();
        class Zip
        implements Iterator<R> {
            Zip() {
            }

            @Override
            public boolean hasNext() {
                return it1.hasNext() && it2.hasNext();
            }

            @Override
            public R next() {
                return zipper.apply(it1.next(), it2.next());
            }
        }
        return Seq.seq(new Zip());
    }

    public static <T1, T2, T3, R> Seq<R> zip(Seq<? extends T1> s1, Seq<? extends T2> s2, Seq<? extends T3> s3, final Function3<? super T1, ? super T2, ? super T3, ? extends R> zipper) {
        final Iterator it1 = s1.iterator();
        final Iterator it2 = s2.iterator();
        final Iterator it3 = s3.iterator();
        class Zip
        implements Iterator<R> {
            Zip() {
            }

            @Override
            public boolean hasNext() {
                return it1.hasNext() && it2.hasNext() && it3.hasNext();
            }

            @Override
            public R next() {
                return zipper.apply(it1.next(), it2.next(), it3.next());
            }
        }
        return Seq.seq(new Zip());
    }

    public static <T1, T2, T3, T4, R> Seq<R> zip(Seq<? extends T1> s1, Seq<? extends T2> s2, Seq<? extends T3> s3, Seq<? extends T4> s4, final Function4<? super T1, ? super T2, ? super T3, ? super T4, ? extends R> zipper) {
        final Iterator it1 = s1.iterator();
        final Iterator it2 = s2.iterator();
        final Iterator it3 = s3.iterator();
        final Iterator it4 = s4.iterator();
        class Zip
        implements Iterator<R> {
            Zip() {
            }

            @Override
            public boolean hasNext() {
                return it1.hasNext() && it2.hasNext() && it3.hasNext() && it4.hasNext();
            }

            @Override
            public R next() {
                return zipper.apply(it1.next(), it2.next(), it3.next(), it4.next());
            }
        }
        return Seq.seq(new Zip());
    }

    public static <T1, T2, T3, T4, T5, R> Seq<R> zip(Seq<? extends T1> s1, Seq<? extends T2> s2, Seq<? extends T3> s3, Seq<? extends T4> s4, Seq<? extends T5> s5, final Function5<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? extends R> zipper) {
        final Iterator it1 = s1.iterator();
        final Iterator it2 = s2.iterator();
        final Iterator it3 = s3.iterator();
        final Iterator it4 = s4.iterator();
        final Iterator it5 = s5.iterator();
        class Zip
        implements Iterator<R> {
            Zip() {
            }

            @Override
            public boolean hasNext() {
                return it1.hasNext() && it2.hasNext() && it3.hasNext() && it4.hasNext() && it5.hasNext();
            }

            @Override
            public R next() {
                return zipper.apply(it1.next(), it2.next(), it3.next(), it4.next(), it5.next());
            }
        }
        return Seq.seq(new Zip());
    }

    public static <T1, T2, T3, T4, T5, T6, R> Seq<R> zip(Seq<? extends T1> s1, Seq<? extends T2> s2, Seq<? extends T3> s3, Seq<? extends T4> s4, Seq<? extends T5> s5, Seq<? extends T6> s6, final Function6<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? extends R> zipper) {
        final Iterator it1 = s1.iterator();
        final Iterator it2 = s2.iterator();
        final Iterator it3 = s3.iterator();
        final Iterator it4 = s4.iterator();
        final Iterator it5 = s5.iterator();
        final Iterator it6 = s6.iterator();
        class Zip
        implements Iterator<R> {
            Zip() {
            }

            @Override
            public boolean hasNext() {
                return it1.hasNext() && it2.hasNext() && it3.hasNext() && it4.hasNext() && it5.hasNext() && it6.hasNext();
            }

            @Override
            public R next() {
                return zipper.apply(it1.next(), it2.next(), it3.next(), it4.next(), it5.next(), it6.next());
            }
        }
        return Seq.seq(new Zip());
    }

    public static <T1, T2, T3, T4, T5, T6, T7, R> Seq<R> zip(Seq<? extends T1> s1, Seq<? extends T2> s2, Seq<? extends T3> s3, Seq<? extends T4> s4, Seq<? extends T5> s5, Seq<? extends T6> s6, Seq<? extends T7> s7, final Function7<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? extends R> zipper) {
        final Iterator it1 = s1.iterator();
        final Iterator it2 = s2.iterator();
        final Iterator it3 = s3.iterator();
        final Iterator it4 = s4.iterator();
        final Iterator it5 = s5.iterator();
        final Iterator it6 = s6.iterator();
        final Iterator it7 = s7.iterator();
        class Zip
        implements Iterator<R> {
            Zip() {
            }

            @Override
            public boolean hasNext() {
                return it1.hasNext() && it2.hasNext() && it3.hasNext() && it4.hasNext() && it5.hasNext() && it6.hasNext() && it7.hasNext();
            }

            @Override
            public R next() {
                return zipper.apply(it1.next(), it2.next(), it3.next(), it4.next(), it5.next(), it6.next(), it7.next());
            }
        }
        return Seq.seq(new Zip());
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, R> Seq<R> zip(Seq<? extends T1> s1, Seq<? extends T2> s2, Seq<? extends T3> s3, Seq<? extends T4> s4, Seq<? extends T5> s5, Seq<? extends T6> s6, Seq<? extends T7> s7, Seq<? extends T8> s8, final Function8<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? extends R> zipper) {
        final Iterator it1 = s1.iterator();
        final Iterator it2 = s2.iterator();
        final Iterator it3 = s3.iterator();
        final Iterator it4 = s4.iterator();
        final Iterator it5 = s5.iterator();
        final Iterator it6 = s6.iterator();
        final Iterator it7 = s7.iterator();
        final Iterator it8 = s8.iterator();
        class Zip
        implements Iterator<R> {
            Zip() {
            }

            @Override
            public boolean hasNext() {
                return it1.hasNext() && it2.hasNext() && it3.hasNext() && it4.hasNext() && it5.hasNext() && it6.hasNext() && it7.hasNext() && it8.hasNext();
            }

            @Override
            public R next() {
                return zipper.apply(it1.next(), it2.next(), it3.next(), it4.next(), it5.next(), it6.next(), it7.next(), it8.next());
            }
        }
        return Seq.seq(new Zip());
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, R> Seq<R> zip(Seq<? extends T1> s1, Seq<? extends T2> s2, Seq<? extends T3> s3, Seq<? extends T4> s4, Seq<? extends T5> s5, Seq<? extends T6> s6, Seq<? extends T7> s7, Seq<? extends T8> s8, Seq<? extends T9> s9, final Function9<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? extends R> zipper) {
        final Iterator it1 = s1.iterator();
        final Iterator it2 = s2.iterator();
        final Iterator it3 = s3.iterator();
        final Iterator it4 = s4.iterator();
        final Iterator it5 = s5.iterator();
        final Iterator it6 = s6.iterator();
        final Iterator it7 = s7.iterator();
        final Iterator it8 = s8.iterator();
        final Iterator it9 = s9.iterator();
        class Zip
        implements Iterator<R> {
            Zip() {
            }

            @Override
            public boolean hasNext() {
                return it1.hasNext() && it2.hasNext() && it3.hasNext() && it4.hasNext() && it5.hasNext() && it6.hasNext() && it7.hasNext() && it8.hasNext() && it9.hasNext();
            }

            @Override
            public R next() {
                return zipper.apply(it1.next(), it2.next(), it3.next(), it4.next(), it5.next(), it6.next(), it7.next(), it8.next(), it9.next());
            }
        }
        return Seq.seq(new Zip());
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, R> Seq<R> zip(Seq<? extends T1> s1, Seq<? extends T2> s2, Seq<? extends T3> s3, Seq<? extends T4> s4, Seq<? extends T5> s5, Seq<? extends T6> s6, Seq<? extends T7> s7, Seq<? extends T8> s8, Seq<? extends T9> s9, Seq<? extends T10> s10, final Function10<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? super T10, ? extends R> zipper) {
        final Iterator it1 = s1.iterator();
        final Iterator it2 = s2.iterator();
        final Iterator it3 = s3.iterator();
        final Iterator it4 = s4.iterator();
        final Iterator it5 = s5.iterator();
        final Iterator it6 = s6.iterator();
        final Iterator it7 = s7.iterator();
        final Iterator it8 = s8.iterator();
        final Iterator it9 = s9.iterator();
        final Iterator it10 = s10.iterator();
        class Zip
        implements Iterator<R> {
            Zip() {
            }

            @Override
            public boolean hasNext() {
                return it1.hasNext() && it2.hasNext() && it3.hasNext() && it4.hasNext() && it5.hasNext() && it6.hasNext() && it7.hasNext() && it8.hasNext() && it9.hasNext() && it10.hasNext();
            }

            @Override
            public R next() {
                return zipper.apply(it1.next(), it2.next(), it3.next(), it4.next(), it5.next(), it6.next(), it7.next(), it8.next(), it9.next(), it10.next());
            }
        }
        return Seq.seq(new Zip());
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, R> Seq<R> zip(Seq<? extends T1> s1, Seq<? extends T2> s2, Seq<? extends T3> s3, Seq<? extends T4> s4, Seq<? extends T5> s5, Seq<? extends T6> s6, Seq<? extends T7> s7, Seq<? extends T8> s8, Seq<? extends T9> s9, Seq<? extends T10> s10, Seq<? extends T11> s11, final Function11<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? super T10, ? super T11, ? extends R> zipper) {
        final Iterator it1 = s1.iterator();
        final Iterator it2 = s2.iterator();
        final Iterator it3 = s3.iterator();
        final Iterator it4 = s4.iterator();
        final Iterator it5 = s5.iterator();
        final Iterator it6 = s6.iterator();
        final Iterator it7 = s7.iterator();
        final Iterator it8 = s8.iterator();
        final Iterator it9 = s9.iterator();
        final Iterator it10 = s10.iterator();
        final Iterator it11 = s11.iterator();
        class Zip
        implements Iterator<R> {
            Zip() {
            }

            @Override
            public boolean hasNext() {
                return it1.hasNext() && it2.hasNext() && it3.hasNext() && it4.hasNext() && it5.hasNext() && it6.hasNext() && it7.hasNext() && it8.hasNext() && it9.hasNext() && it10.hasNext() && it11.hasNext();
            }

            @Override
            public R next() {
                return zipper.apply(it1.next(), it2.next(), it3.next(), it4.next(), it5.next(), it6.next(), it7.next(), it8.next(), it9.next(), it10.next(), it11.next());
            }
        }
        return Seq.seq(new Zip());
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, R> Seq<R> zip(Seq<? extends T1> s1, Seq<? extends T2> s2, Seq<? extends T3> s3, Seq<? extends T4> s4, Seq<? extends T5> s5, Seq<? extends T6> s6, Seq<? extends T7> s7, Seq<? extends T8> s8, Seq<? extends T9> s9, Seq<? extends T10> s10, Seq<? extends T11> s11, Seq<? extends T12> s12, final Function12<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? super T10, ? super T11, ? super T12, ? extends R> zipper) {
        final Iterator it1 = s1.iterator();
        final Iterator it2 = s2.iterator();
        final Iterator it3 = s3.iterator();
        final Iterator it4 = s4.iterator();
        final Iterator it5 = s5.iterator();
        final Iterator it6 = s6.iterator();
        final Iterator it7 = s7.iterator();
        final Iterator it8 = s8.iterator();
        final Iterator it9 = s9.iterator();
        final Iterator it10 = s10.iterator();
        final Iterator it11 = s11.iterator();
        final Iterator it12 = s12.iterator();
        class Zip
        implements Iterator<R> {
            Zip() {
            }

            @Override
            public boolean hasNext() {
                return it1.hasNext() && it2.hasNext() && it3.hasNext() && it4.hasNext() && it5.hasNext() && it6.hasNext() && it7.hasNext() && it8.hasNext() && it9.hasNext() && it10.hasNext() && it11.hasNext() && it12.hasNext();
            }

            @Override
            public R next() {
                return zipper.apply(it1.next(), it2.next(), it3.next(), it4.next(), it5.next(), it6.next(), it7.next(), it8.next(), it9.next(), it10.next(), it11.next(), it12.next());
            }
        }
        return Seq.seq(new Zip());
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, R> Seq<R> zip(Seq<? extends T1> s1, Seq<? extends T2> s2, Seq<? extends T3> s3, Seq<? extends T4> s4, Seq<? extends T5> s5, Seq<? extends T6> s6, Seq<? extends T7> s7, Seq<? extends T8> s8, Seq<? extends T9> s9, Seq<? extends T10> s10, Seq<? extends T11> s11, Seq<? extends T12> s12, Seq<? extends T13> s13, final Function13<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? super T10, ? super T11, ? super T12, ? super T13, ? extends R> zipper) {
        final Iterator it1 = s1.iterator();
        final Iterator it2 = s2.iterator();
        final Iterator it3 = s3.iterator();
        final Iterator it4 = s4.iterator();
        final Iterator it5 = s5.iterator();
        final Iterator it6 = s6.iterator();
        final Iterator it7 = s7.iterator();
        final Iterator it8 = s8.iterator();
        final Iterator it9 = s9.iterator();
        final Iterator it10 = s10.iterator();
        final Iterator it11 = s11.iterator();
        final Iterator it12 = s12.iterator();
        final Iterator it13 = s13.iterator();
        class Zip
        implements Iterator<R> {
            Zip() {
            }

            @Override
            public boolean hasNext() {
                return it1.hasNext() && it2.hasNext() && it3.hasNext() && it4.hasNext() && it5.hasNext() && it6.hasNext() && it7.hasNext() && it8.hasNext() && it9.hasNext() && it10.hasNext() && it11.hasNext() && it12.hasNext() && it13.hasNext();
            }

            @Override
            public R next() {
                return zipper.apply(it1.next(), it2.next(), it3.next(), it4.next(), it5.next(), it6.next(), it7.next(), it8.next(), it9.next(), it10.next(), it11.next(), it12.next(), it13.next());
            }
        }
        return Seq.seq(new Zip());
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, R> Seq<R> zip(Seq<? extends T1> s1, Seq<? extends T2> s2, Seq<? extends T3> s3, Seq<? extends T4> s4, Seq<? extends T5> s5, Seq<? extends T6> s6, Seq<? extends T7> s7, Seq<? extends T8> s8, Seq<? extends T9> s9, Seq<? extends T10> s10, Seq<? extends T11> s11, Seq<? extends T12> s12, Seq<? extends T13> s13, Seq<? extends T14> s14, final Function14<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? super T10, ? super T11, ? super T12, ? super T13, ? super T14, ? extends R> zipper) {
        final Iterator it1 = s1.iterator();
        final Iterator it2 = s2.iterator();
        final Iterator it3 = s3.iterator();
        final Iterator it4 = s4.iterator();
        final Iterator it5 = s5.iterator();
        final Iterator it6 = s6.iterator();
        final Iterator it7 = s7.iterator();
        final Iterator it8 = s8.iterator();
        final Iterator it9 = s9.iterator();
        final Iterator it10 = s10.iterator();
        final Iterator it11 = s11.iterator();
        final Iterator it12 = s12.iterator();
        final Iterator it13 = s13.iterator();
        final Iterator it14 = s14.iterator();
        class Zip
        implements Iterator<R> {
            Zip() {
            }

            @Override
            public boolean hasNext() {
                return it1.hasNext() && it2.hasNext() && it3.hasNext() && it4.hasNext() && it5.hasNext() && it6.hasNext() && it7.hasNext() && it8.hasNext() && it9.hasNext() && it10.hasNext() && it11.hasNext() && it12.hasNext() && it13.hasNext() && it14.hasNext();
            }

            @Override
            public R next() {
                return zipper.apply(it1.next(), it2.next(), it3.next(), it4.next(), it5.next(), it6.next(), it7.next(), it8.next(), it9.next(), it10.next(), it11.next(), it12.next(), it13.next(), it14.next());
            }
        }
        return Seq.seq(new Zip());
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, R> Seq<R> zip(Seq<? extends T1> s1, Seq<? extends T2> s2, Seq<? extends T3> s3, Seq<? extends T4> s4, Seq<? extends T5> s5, Seq<? extends T6> s6, Seq<? extends T7> s7, Seq<? extends T8> s8, Seq<? extends T9> s9, Seq<? extends T10> s10, Seq<? extends T11> s11, Seq<? extends T12> s12, Seq<? extends T13> s13, Seq<? extends T14> s14, Seq<? extends T15> s15, final Function15<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? super T10, ? super T11, ? super T12, ? super T13, ? super T14, ? super T15, ? extends R> zipper) {
        final Iterator it1 = s1.iterator();
        final Iterator it2 = s2.iterator();
        final Iterator it3 = s3.iterator();
        final Iterator it4 = s4.iterator();
        final Iterator it5 = s5.iterator();
        final Iterator it6 = s6.iterator();
        final Iterator it7 = s7.iterator();
        final Iterator it8 = s8.iterator();
        final Iterator it9 = s9.iterator();
        final Iterator it10 = s10.iterator();
        final Iterator it11 = s11.iterator();
        final Iterator it12 = s12.iterator();
        final Iterator it13 = s13.iterator();
        final Iterator it14 = s14.iterator();
        final Iterator it15 = s15.iterator();
        class Zip
        implements Iterator<R> {
            Zip() {
            }

            @Override
            public boolean hasNext() {
                return it1.hasNext() && it2.hasNext() && it3.hasNext() && it4.hasNext() && it5.hasNext() && it6.hasNext() && it7.hasNext() && it8.hasNext() && it9.hasNext() && it10.hasNext() && it11.hasNext() && it12.hasNext() && it13.hasNext() && it14.hasNext() && it15.hasNext();
            }

            @Override
            public R next() {
                return zipper.apply(it1.next(), it2.next(), it3.next(), it4.next(), it5.next(), it6.next(), it7.next(), it8.next(), it9.next(), it10.next(), it11.next(), it12.next(), it13.next(), it14.next(), it15.next());
            }
        }
        return Seq.seq(new Zip());
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, R> Seq<R> zip(Seq<? extends T1> s1, Seq<? extends T2> s2, Seq<? extends T3> s3, Seq<? extends T4> s4, Seq<? extends T5> s5, Seq<? extends T6> s6, Seq<? extends T7> s7, Seq<? extends T8> s8, Seq<? extends T9> s9, Seq<? extends T10> s10, Seq<? extends T11> s11, Seq<? extends T12> s12, Seq<? extends T13> s13, Seq<? extends T14> s14, Seq<? extends T15> s15, Seq<? extends T16> s16, final Function16<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? super T10, ? super T11, ? super T12, ? super T13, ? super T14, ? super T15, ? super T16, ? extends R> zipper) {
        final Iterator it1 = s1.iterator();
        final Iterator it2 = s2.iterator();
        final Iterator it3 = s3.iterator();
        final Iterator it4 = s4.iterator();
        final Iterator it5 = s5.iterator();
        final Iterator it6 = s6.iterator();
        final Iterator it7 = s7.iterator();
        final Iterator it8 = s8.iterator();
        final Iterator it9 = s9.iterator();
        final Iterator it10 = s10.iterator();
        final Iterator it11 = s11.iterator();
        final Iterator it12 = s12.iterator();
        final Iterator it13 = s13.iterator();
        final Iterator it14 = s14.iterator();
        final Iterator it15 = s15.iterator();
        final Iterator it16 = s16.iterator();
        class Zip
        implements Iterator<R> {
            Zip() {
            }

            @Override
            public boolean hasNext() {
                return it1.hasNext() && it2.hasNext() && it3.hasNext() && it4.hasNext() && it5.hasNext() && it6.hasNext() && it7.hasNext() && it8.hasNext() && it9.hasNext() && it10.hasNext() && it11.hasNext() && it12.hasNext() && it13.hasNext() && it14.hasNext() && it15.hasNext() && it16.hasNext();
            }

            @Override
            public R next() {
                return zipper.apply(it1.next(), it2.next(), it3.next(), it4.next(), it5.next(), it6.next(), it7.next(), it8.next(), it9.next(), it10.next(), it11.next(), it12.next(), it13.next(), it14.next(), it15.next(), it16.next());
            }
        }
        return Seq.seq(new Zip());
    }

    public static <T> Seq<Tuple2<T, Long>> zipWithIndex(Stream<? extends T> stream) {
        return Seq.zipWithIndex(Seq.seq(stream));
    }

    public static <T> Seq<Tuple2<T, Long>> zipWithIndex(Iterable<? extends T> iterable) {
        return Seq.zipWithIndex(Seq.seq(iterable));
    }

    public static <T> Seq<Tuple2<T, Long>> zipWithIndex(Seq<? extends T> stream) {
        long[] index = new long[]{-1L};
        return SeqUtils.transform(stream, (delegate, action) -> delegate.tryAdvance(t -> {
            index[0] = index[0] + 1L;
            action.accept(Tuple.tuple(t, index[0]));
        }));
    }

    public static <T, R> Seq<R> zipWithIndex(Stream<? extends T> stream, BiFunction<? super T, ? super Long, ? extends R> zipper) {
        return Seq.zipWithIndex(Seq.seq(stream), zipper);
    }

    public static <T, R> Seq<R> zipWithIndex(Iterable<? extends T> iterable, BiFunction<? super T, ? super Long, ? extends R> zipper) {
        return Seq.zipWithIndex(Seq.seq(iterable), zipper);
    }

    public static <T, R> Seq<R> zipWithIndex(Seq<? extends T> stream, BiFunction<? super T, ? super Long, ? extends R> zipper) {
        long[] index = new long[]{-1L};
        return SeqUtils.transform(stream, (delegate, action) -> delegate.tryAdvance(t -> {
            index[0] = index[0] + 1L;
            action.accept(zipper.apply(t, index[0]));
        }));
    }

    public static <T, U> U foldLeft(Stream<? extends T> stream, U seed, BiFunction<? super U, ? super T, ? extends U> function) {
        return Seq.foldLeft(Seq.seq(stream), seed, function);
    }

    public static <T, U> U foldLeft(Iterable<? extends T> iterable, U seed, BiFunction<? super U, ? super T, ? extends U> function) {
        return Seq.foldLeft(Seq.seq(iterable), seed, function);
    }

    public static <T, U> U foldLeft(Seq<? extends T> stream, U seed, BiFunction<? super U, ? super T, ? extends U> function) {
        Iterator it = stream.iterator();
        U result = seed;
        while (it.hasNext()) {
            result = function.apply(result, it.next());
        }
        return result;
    }

    public static <T, U> U foldRight(Stream<? extends T> stream, U seed, BiFunction<? super T, ? super U, ? extends U> function) {
        return Seq.foldRight(Seq.seq(stream), seed, function);
    }

    public static <T, U> U foldRight(Iterable<? extends T> iterable, U seed, BiFunction<? super T, ? super U, ? extends U> function) {
        return Seq.foldRight(Seq.seq(iterable), seed, function);
    }

    public static <T, U> U foldRight(Seq<? extends T> stream, U seed, BiFunction<? super T, ? super U, ? extends U> function) {
        return (U)stream.reverse().foldLeft(seed, (u, t) -> function.apply(t, u));
    }

    public static <T, U> Seq<U> scanLeft(Stream<? extends T> stream, U seed, BiFunction<? super U, ? super T, ? extends U> function) {
        return Seq.scanLeft(Seq.seq(stream), seed, function);
    }

    public static <T, U> Seq<U> scanLeft(Iterable<? extends T> iterable, U seed, BiFunction<? super U, ? super T, ? extends U> function) {
        return Seq.scanLeft(Seq.seq(iterable), seed, function);
    }

    public static <T, U> Seq<U> scanLeft(Seq<? extends T> stream, U seed, BiFunction<? super U, ? super T, ? extends U> function) {
        Object[] value = new Object[]{seed};
        return Seq.of(seed).concat((U)SeqUtils.transform(stream, (delegate, action) -> delegate.tryAdvance(t -> {
            value[0] = function.apply(value[0], t);
            action.accept(value[0]);
        })));
    }

    public static <T, U> Seq<U> scanRight(Stream<? extends T> stream, U seed, BiFunction<? super T, ? super U, ? extends U> function) {
        return Seq.scanRight(Seq.seq(stream), seed, function);
    }

    public static <T, U> Seq<U> scanRight(Iterable<? extends T> iterable, U seed, BiFunction<? super T, ? super U, ? extends U> function) {
        return Seq.scanRight(Seq.seq(iterable), seed, function);
    }

    public static <T, U> Seq<U> scanRight(Seq<? extends T> stream, U seed, BiFunction<? super T, ? super U, ? extends U> function) {
        return stream.reverse().scanLeft(seed, (u, t) -> function.apply(t, u));
    }

    public static <T, U> Seq<T> unfold(U seed, Function<? super U, Optional<Tuple2<T, U>>> unfolder) {
        Tuple2[] unfolded = new Tuple2[]{Tuple.tuple(null, seed)};
        return Seq.seq(action -> {
            Optional result = (Optional)unfolder.apply((Object)unfolded[0].v2);
            if (result.isPresent()) {
                unfolded[0] = (Tuple2)result.get();
                action.accept(unfolded[0].v1);
            }
            return result.isPresent();
        });
    }

    public static <T> Seq<T> reverse(Stream<? extends T> stream) {
        return Seq.reverse(Seq.seq(stream));
    }

    public static <T> Seq<T> reverse(Iterable<? extends T> iterable) {
        return Seq.reverse(Seq.seq(iterable));
    }

    public static <T> Seq<T> reverse(Seq<? extends T> stream) {
        List<? extends T> list = Seq.toList(stream);
        Collections.reverse(list);
        return Seq.seq(list).onClose(stream::close);
    }

    public static <T> Seq<T> shuffle(Stream<? extends T> stream) {
        return Seq.shuffle(Seq.seq(stream));
    }

    public static <T> Seq<T> shuffle(Iterable<? extends T> iterable) {
        return Seq.shuffle(Seq.seq(iterable));
    }

    public static <T> Seq<T> shuffle(Seq<? extends T> stream) {
        return Seq.shuffle(stream, null);
    }

    public static <T> Seq<T> shuffle(Stream<? extends T> stream, Random random) {
        return Seq.shuffle(Seq.seq(stream), random);
    }

    public static <T> Seq<T> shuffle(Iterable<? extends T> iterable, Random random) {
        return Seq.shuffle(Seq.seq(iterable), random);
    }

    public static <T> Seq<T> shuffle(Seq<? extends T> stream, Random random) {
        Spliterator[] shuffled = new Spliterator[1];
        return SeqUtils.transform(stream, (delegate, action) -> {
            if (shuffled[0] == null) {
                List list = Seq.seq(delegate).toList();
                if (random == null) {
                    Collections.shuffle(list);
                } else {
                    Collections.shuffle(list, random);
                }
                shuffled[0] = list.spliterator();
            }
            return shuffled[0].tryAdvance(action);
        }).onClose(stream::close);
    }

    public static <T1, T2> Seq<Tuple2<T1, T2>> crossApply(Stream<? extends T1> stream, Function<? super T1, ? extends Stream<? extends T2>> function2) {
        return Seq.crossApply(Seq.seq(stream), (? super T1 t) -> Seq.seq((Stream)function2.apply((Object)t)));
    }

    public static <T1, T2, T3> Seq<Tuple3<T1, T2, T3>> crossApply(Stream<? extends T1> stream, Function<? super T1, ? extends Stream<? extends T2>> function2, Function<? super T2, ? extends Stream<? extends T3>> function3) {
        return Seq.crossApply(Seq.seq(stream), (? super T1 t) -> Seq.seq((Stream)function2.apply((Object)t)), (? super T2 t) -> Seq.seq((Stream)function3.apply((Object)t)));
    }

    public static <T1, T2, T3, T4> Seq<Tuple4<T1, T2, T3, T4>> crossApply(Stream<? extends T1> stream, Function<? super T1, ? extends Stream<? extends T2>> function2, Function<? super T2, ? extends Stream<? extends T3>> function3, Function<? super T3, ? extends Stream<? extends T4>> function4) {
        return Seq.crossApply(Seq.seq(stream), (? super T1 t) -> Seq.seq((Stream)function2.apply((Object)t)), (? super T2 t) -> Seq.seq((Stream)function3.apply((Object)t)), (? super T3 t) -> Seq.seq((Stream)function4.apply((Object)t)));
    }

    public static <T1, T2, T3, T4, T5> Seq<Tuple5<T1, T2, T3, T4, T5>> crossApply(Stream<? extends T1> stream, Function<? super T1, ? extends Stream<? extends T2>> function2, Function<? super T2, ? extends Stream<? extends T3>> function3, Function<? super T3, ? extends Stream<? extends T4>> function4, Function<? super T4, ? extends Stream<? extends T5>> function5) {
        return Seq.crossApply(Seq.seq(stream), (? super T1 t) -> Seq.seq((Stream)function2.apply((Object)t)), (? super T2 t) -> Seq.seq((Stream)function3.apply((Object)t)), (? super T3 t) -> Seq.seq((Stream)function4.apply((Object)t)), (? super T4 t) -> Seq.seq((Stream)function5.apply((Object)t)));
    }

    public static <T1, T2, T3, T4, T5, T6> Seq<Tuple6<T1, T2, T3, T4, T5, T6>> crossApply(Stream<? extends T1> stream, Function<? super T1, ? extends Stream<? extends T2>> function2, Function<? super T2, ? extends Stream<? extends T3>> function3, Function<? super T3, ? extends Stream<? extends T4>> function4, Function<? super T4, ? extends Stream<? extends T5>> function5, Function<? super T5, ? extends Stream<? extends T6>> function6) {
        return Seq.crossApply(Seq.seq(stream), (? super T1 t) -> Seq.seq((Stream)function2.apply((Object)t)), (? super T2 t) -> Seq.seq((Stream)function3.apply((Object)t)), (? super T3 t) -> Seq.seq((Stream)function4.apply((Object)t)), (? super T4 t) -> Seq.seq((Stream)function5.apply((Object)t)), (? super T5 t) -> Seq.seq((Stream)function6.apply((Object)t)));
    }

    public static <T1, T2, T3, T4, T5, T6, T7> Seq<Tuple7<T1, T2, T3, T4, T5, T6, T7>> crossApply(Stream<? extends T1> stream, Function<? super T1, ? extends Stream<? extends T2>> function2, Function<? super T2, ? extends Stream<? extends T3>> function3, Function<? super T3, ? extends Stream<? extends T4>> function4, Function<? super T4, ? extends Stream<? extends T5>> function5, Function<? super T5, ? extends Stream<? extends T6>> function6, Function<? super T6, ? extends Stream<? extends T7>> function7) {
        return Seq.crossApply(Seq.seq(stream), (? super T1 t) -> Seq.seq((Stream)function2.apply((Object)t)), (? super T2 t) -> Seq.seq((Stream)function3.apply((Object)t)), (? super T3 t) -> Seq.seq((Stream)function4.apply((Object)t)), (? super T4 t) -> Seq.seq((Stream)function5.apply((Object)t)), (? super T5 t) -> Seq.seq((Stream)function6.apply((Object)t)), (? super T6 t) -> Seq.seq((Stream)function7.apply((Object)t)));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8> Seq<Tuple8<T1, T2, T3, T4, T5, T6, T7, T8>> crossApply(Stream<? extends T1> stream, Function<? super T1, ? extends Stream<? extends T2>> function2, Function<? super T2, ? extends Stream<? extends T3>> function3, Function<? super T3, ? extends Stream<? extends T4>> function4, Function<? super T4, ? extends Stream<? extends T5>> function5, Function<? super T5, ? extends Stream<? extends T6>> function6, Function<? super T6, ? extends Stream<? extends T7>> function7, Function<? super T7, ? extends Stream<? extends T8>> function8) {
        return Seq.crossApply(Seq.seq(stream), (? super T1 t) -> Seq.seq((Stream)function2.apply((Object)t)), (? super T2 t) -> Seq.seq((Stream)function3.apply((Object)t)), (? super T3 t) -> Seq.seq((Stream)function4.apply((Object)t)), (? super T4 t) -> Seq.seq((Stream)function5.apply((Object)t)), (? super T5 t) -> Seq.seq((Stream)function6.apply((Object)t)), (? super T6 t) -> Seq.seq((Stream)function7.apply((Object)t)), (? super T7 t) -> Seq.seq((Stream)function8.apply((Object)t)));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9> Seq<Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> crossApply(Stream<? extends T1> stream, Function<? super T1, ? extends Stream<? extends T2>> function2, Function<? super T2, ? extends Stream<? extends T3>> function3, Function<? super T3, ? extends Stream<? extends T4>> function4, Function<? super T4, ? extends Stream<? extends T5>> function5, Function<? super T5, ? extends Stream<? extends T6>> function6, Function<? super T6, ? extends Stream<? extends T7>> function7, Function<? super T7, ? extends Stream<? extends T8>> function8, Function<? super T8, ? extends Stream<? extends T9>> function9) {
        return Seq.crossApply(Seq.seq(stream), (? super T1 t) -> Seq.seq((Stream)function2.apply((Object)t)), (? super T2 t) -> Seq.seq((Stream)function3.apply((Object)t)), (? super T3 t) -> Seq.seq((Stream)function4.apply((Object)t)), (? super T4 t) -> Seq.seq((Stream)function5.apply((Object)t)), (? super T5 t) -> Seq.seq((Stream)function6.apply((Object)t)), (? super T6 t) -> Seq.seq((Stream)function7.apply((Object)t)), (? super T7 t) -> Seq.seq((Stream)function8.apply((Object)t)), (? super T8 t) -> Seq.seq((Stream)function9.apply((Object)t)));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> Seq<Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> crossApply(Stream<? extends T1> stream, Function<? super T1, ? extends Stream<? extends T2>> function2, Function<? super T2, ? extends Stream<? extends T3>> function3, Function<? super T3, ? extends Stream<? extends T4>> function4, Function<? super T4, ? extends Stream<? extends T5>> function5, Function<? super T5, ? extends Stream<? extends T6>> function6, Function<? super T6, ? extends Stream<? extends T7>> function7, Function<? super T7, ? extends Stream<? extends T8>> function8, Function<? super T8, ? extends Stream<? extends T9>> function9, Function<? super T9, ? extends Stream<? extends T10>> function10) {
        return Seq.crossApply(Seq.seq(stream), (? super T1 t) -> Seq.seq((Stream)function2.apply((Object)t)), (? super T2 t) -> Seq.seq((Stream)function3.apply((Object)t)), (? super T3 t) -> Seq.seq((Stream)function4.apply((Object)t)), (? super T4 t) -> Seq.seq((Stream)function5.apply((Object)t)), (? super T5 t) -> Seq.seq((Stream)function6.apply((Object)t)), (? super T6 t) -> Seq.seq((Stream)function7.apply((Object)t)), (? super T7 t) -> Seq.seq((Stream)function8.apply((Object)t)), (? super T8 t) -> Seq.seq((Stream)function9.apply((Object)t)), (? super T9 t) -> Seq.seq((Stream)function10.apply((Object)t)));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> Seq<Tuple11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> crossApply(Stream<? extends T1> stream, Function<? super T1, ? extends Stream<? extends T2>> function2, Function<? super T2, ? extends Stream<? extends T3>> function3, Function<? super T3, ? extends Stream<? extends T4>> function4, Function<? super T4, ? extends Stream<? extends T5>> function5, Function<? super T5, ? extends Stream<? extends T6>> function6, Function<? super T6, ? extends Stream<? extends T7>> function7, Function<? super T7, ? extends Stream<? extends T8>> function8, Function<? super T8, ? extends Stream<? extends T9>> function9, Function<? super T9, ? extends Stream<? extends T10>> function10, Function<? super T10, ? extends Stream<? extends T11>> function11) {
        return Seq.crossApply(Seq.seq(stream), (? super T1 t) -> Seq.seq((Stream)function2.apply((Object)t)), (? super T2 t) -> Seq.seq((Stream)function3.apply((Object)t)), (? super T3 t) -> Seq.seq((Stream)function4.apply((Object)t)), (? super T4 t) -> Seq.seq((Stream)function5.apply((Object)t)), (? super T5 t) -> Seq.seq((Stream)function6.apply((Object)t)), (? super T6 t) -> Seq.seq((Stream)function7.apply((Object)t)), (? super T7 t) -> Seq.seq((Stream)function8.apply((Object)t)), (? super T8 t) -> Seq.seq((Stream)function9.apply((Object)t)), (? super T9 t) -> Seq.seq((Stream)function10.apply((Object)t)), (? super T10 t) -> Seq.seq((Stream)function11.apply((Object)t)));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> Seq<Tuple12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>> crossApply(Stream<? extends T1> stream, Function<? super T1, ? extends Stream<? extends T2>> function2, Function<? super T2, ? extends Stream<? extends T3>> function3, Function<? super T3, ? extends Stream<? extends T4>> function4, Function<? super T4, ? extends Stream<? extends T5>> function5, Function<? super T5, ? extends Stream<? extends T6>> function6, Function<? super T6, ? extends Stream<? extends T7>> function7, Function<? super T7, ? extends Stream<? extends T8>> function8, Function<? super T8, ? extends Stream<? extends T9>> function9, Function<? super T9, ? extends Stream<? extends T10>> function10, Function<? super T10, ? extends Stream<? extends T11>> function11, Function<? super T11, ? extends Stream<? extends T12>> function12) {
        return Seq.crossApply(Seq.seq(stream), (? super T1 t) -> Seq.seq((Stream)function2.apply((Object)t)), (? super T2 t) -> Seq.seq((Stream)function3.apply((Object)t)), (? super T3 t) -> Seq.seq((Stream)function4.apply((Object)t)), (? super T4 t) -> Seq.seq((Stream)function5.apply((Object)t)), (? super T5 t) -> Seq.seq((Stream)function6.apply((Object)t)), (? super T6 t) -> Seq.seq((Stream)function7.apply((Object)t)), (? super T7 t) -> Seq.seq((Stream)function8.apply((Object)t)), (? super T8 t) -> Seq.seq((Stream)function9.apply((Object)t)), (? super T9 t) -> Seq.seq((Stream)function10.apply((Object)t)), (? super T10 t) -> Seq.seq((Stream)function11.apply((Object)t)), (? super T11 t) -> Seq.seq((Stream)function12.apply((Object)t)));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> Seq<Tuple13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> crossApply(Stream<? extends T1> stream, Function<? super T1, ? extends Stream<? extends T2>> function2, Function<? super T2, ? extends Stream<? extends T3>> function3, Function<? super T3, ? extends Stream<? extends T4>> function4, Function<? super T4, ? extends Stream<? extends T5>> function5, Function<? super T5, ? extends Stream<? extends T6>> function6, Function<? super T6, ? extends Stream<? extends T7>> function7, Function<? super T7, ? extends Stream<? extends T8>> function8, Function<? super T8, ? extends Stream<? extends T9>> function9, Function<? super T9, ? extends Stream<? extends T10>> function10, Function<? super T10, ? extends Stream<? extends T11>> function11, Function<? super T11, ? extends Stream<? extends T12>> function12, Function<? super T12, ? extends Stream<? extends T13>> function13) {
        return Seq.crossApply(Seq.seq(stream), (? super T1 t) -> Seq.seq((Stream)function2.apply((Object)t)), (? super T2 t) -> Seq.seq((Stream)function3.apply((Object)t)), (? super T3 t) -> Seq.seq((Stream)function4.apply((Object)t)), (? super T4 t) -> Seq.seq((Stream)function5.apply((Object)t)), (? super T5 t) -> Seq.seq((Stream)function6.apply((Object)t)), (? super T6 t) -> Seq.seq((Stream)function7.apply((Object)t)), (? super T7 t) -> Seq.seq((Stream)function8.apply((Object)t)), (? super T8 t) -> Seq.seq((Stream)function9.apply((Object)t)), (? super T9 t) -> Seq.seq((Stream)function10.apply((Object)t)), (? super T10 t) -> Seq.seq((Stream)function11.apply((Object)t)), (? super T11 t) -> Seq.seq((Stream)function12.apply((Object)t)), (? super T12 t) -> Seq.seq((Stream)function13.apply((Object)t)));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> Seq<Tuple14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> crossApply(Stream<? extends T1> stream, Function<? super T1, ? extends Stream<? extends T2>> function2, Function<? super T2, ? extends Stream<? extends T3>> function3, Function<? super T3, ? extends Stream<? extends T4>> function4, Function<? super T4, ? extends Stream<? extends T5>> function5, Function<? super T5, ? extends Stream<? extends T6>> function6, Function<? super T6, ? extends Stream<? extends T7>> function7, Function<? super T7, ? extends Stream<? extends T8>> function8, Function<? super T8, ? extends Stream<? extends T9>> function9, Function<? super T9, ? extends Stream<? extends T10>> function10, Function<? super T10, ? extends Stream<? extends T11>> function11, Function<? super T11, ? extends Stream<? extends T12>> function12, Function<? super T12, ? extends Stream<? extends T13>> function13, Function<? super T13, ? extends Stream<? extends T14>> function14) {
        return Seq.crossApply(Seq.seq(stream), (? super T1 t) -> Seq.seq((Stream)function2.apply((Object)t)), (? super T2 t) -> Seq.seq((Stream)function3.apply((Object)t)), (? super T3 t) -> Seq.seq((Stream)function4.apply((Object)t)), (? super T4 t) -> Seq.seq((Stream)function5.apply((Object)t)), (? super T5 t) -> Seq.seq((Stream)function6.apply((Object)t)), (? super T6 t) -> Seq.seq((Stream)function7.apply((Object)t)), (? super T7 t) -> Seq.seq((Stream)function8.apply((Object)t)), (? super T8 t) -> Seq.seq((Stream)function9.apply((Object)t)), (? super T9 t) -> Seq.seq((Stream)function10.apply((Object)t)), (? super T10 t) -> Seq.seq((Stream)function11.apply((Object)t)), (? super T11 t) -> Seq.seq((Stream)function12.apply((Object)t)), (? super T12 t) -> Seq.seq((Stream)function13.apply((Object)t)), (? super T13 t) -> Seq.seq((Stream)function14.apply((Object)t)));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> Seq<Tuple15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> crossApply(Stream<? extends T1> stream, Function<? super T1, ? extends Stream<? extends T2>> function2, Function<? super T2, ? extends Stream<? extends T3>> function3, Function<? super T3, ? extends Stream<? extends T4>> function4, Function<? super T4, ? extends Stream<? extends T5>> function5, Function<? super T5, ? extends Stream<? extends T6>> function6, Function<? super T6, ? extends Stream<? extends T7>> function7, Function<? super T7, ? extends Stream<? extends T8>> function8, Function<? super T8, ? extends Stream<? extends T9>> function9, Function<? super T9, ? extends Stream<? extends T10>> function10, Function<? super T10, ? extends Stream<? extends T11>> function11, Function<? super T11, ? extends Stream<? extends T12>> function12, Function<? super T12, ? extends Stream<? extends T13>> function13, Function<? super T13, ? extends Stream<? extends T14>> function14, Function<? super T14, ? extends Stream<? extends T15>> function15) {
        return Seq.crossApply(Seq.seq(stream), (? super T1 t) -> Seq.seq((Stream)function2.apply((Object)t)), (? super T2 t) -> Seq.seq((Stream)function3.apply((Object)t)), (? super T3 t) -> Seq.seq((Stream)function4.apply((Object)t)), (? super T4 t) -> Seq.seq((Stream)function5.apply((Object)t)), (? super T5 t) -> Seq.seq((Stream)function6.apply((Object)t)), (? super T6 t) -> Seq.seq((Stream)function7.apply((Object)t)), (? super T7 t) -> Seq.seq((Stream)function8.apply((Object)t)), (? super T8 t) -> Seq.seq((Stream)function9.apply((Object)t)), (? super T9 t) -> Seq.seq((Stream)function10.apply((Object)t)), (? super T10 t) -> Seq.seq((Stream)function11.apply((Object)t)), (? super T11 t) -> Seq.seq((Stream)function12.apply((Object)t)), (? super T12 t) -> Seq.seq((Stream)function13.apply((Object)t)), (? super T13 t) -> Seq.seq((Stream)function14.apply((Object)t)), (? super T14 t) -> Seq.seq((Stream)function15.apply((Object)t)));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> Seq<Tuple16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> crossApply(Stream<? extends T1> stream, Function<? super T1, ? extends Stream<? extends T2>> function2, Function<? super T2, ? extends Stream<? extends T3>> function3, Function<? super T3, ? extends Stream<? extends T4>> function4, Function<? super T4, ? extends Stream<? extends T5>> function5, Function<? super T5, ? extends Stream<? extends T6>> function6, Function<? super T6, ? extends Stream<? extends T7>> function7, Function<? super T7, ? extends Stream<? extends T8>> function8, Function<? super T8, ? extends Stream<? extends T9>> function9, Function<? super T9, ? extends Stream<? extends T10>> function10, Function<? super T10, ? extends Stream<? extends T11>> function11, Function<? super T11, ? extends Stream<? extends T12>> function12, Function<? super T12, ? extends Stream<? extends T13>> function13, Function<? super T13, ? extends Stream<? extends T14>> function14, Function<? super T14, ? extends Stream<? extends T15>> function15, Function<? super T15, ? extends Stream<? extends T16>> function16) {
        return Seq.crossApply(Seq.seq(stream), (? super T1 t) -> Seq.seq((Stream)function2.apply((Object)t)), (? super T2 t) -> Seq.seq((Stream)function3.apply((Object)t)), (? super T3 t) -> Seq.seq((Stream)function4.apply((Object)t)), (? super T4 t) -> Seq.seq((Stream)function5.apply((Object)t)), (? super T5 t) -> Seq.seq((Stream)function6.apply((Object)t)), (? super T6 t) -> Seq.seq((Stream)function7.apply((Object)t)), (? super T7 t) -> Seq.seq((Stream)function8.apply((Object)t)), (? super T8 t) -> Seq.seq((Stream)function9.apply((Object)t)), (? super T9 t) -> Seq.seq((Stream)function10.apply((Object)t)), (? super T10 t) -> Seq.seq((Stream)function11.apply((Object)t)), (? super T11 t) -> Seq.seq((Stream)function12.apply((Object)t)), (? super T12 t) -> Seq.seq((Stream)function13.apply((Object)t)), (? super T13 t) -> Seq.seq((Stream)function14.apply((Object)t)), (? super T14 t) -> Seq.seq((Stream)function15.apply((Object)t)), (? super T15 t) -> Seq.seq((Stream)function16.apply((Object)t)));
    }

    public static <T1, T2> Seq<Tuple2<T1, T2>> crossApply(Iterable<? extends T1> iterable, Function<? super T1, ? extends Iterable<? extends T2>> function2) {
        return Seq.crossApply(Seq.seq(iterable), (? super T1 t) -> Seq.seq((Iterable)function2.apply((Object)t)));
    }

    public static <T1, T2, T3> Seq<Tuple3<T1, T2, T3>> crossApply(Iterable<? extends T1> iterable, Function<? super T1, ? extends Iterable<? extends T2>> function2, Function<? super T2, ? extends Iterable<? extends T3>> function3) {
        return Seq.crossApply(Seq.seq(iterable), (? super T1 t) -> Seq.seq((Iterable)function2.apply((Object)t)), (? super T2 t) -> Seq.seq((Iterable)function3.apply((Object)t)));
    }

    public static <T1, T2, T3, T4> Seq<Tuple4<T1, T2, T3, T4>> crossApply(Iterable<? extends T1> iterable, Function<? super T1, ? extends Iterable<? extends T2>> function2, Function<? super T2, ? extends Iterable<? extends T3>> function3, Function<? super T3, ? extends Iterable<? extends T4>> function4) {
        return Seq.crossApply(Seq.seq(iterable), (? super T1 t) -> Seq.seq((Iterable)function2.apply((Object)t)), (? super T2 t) -> Seq.seq((Iterable)function3.apply((Object)t)), (? super T3 t) -> Seq.seq((Iterable)function4.apply((Object)t)));
    }

    public static <T1, T2, T3, T4, T5> Seq<Tuple5<T1, T2, T3, T4, T5>> crossApply(Iterable<? extends T1> iterable, Function<? super T1, ? extends Iterable<? extends T2>> function2, Function<? super T2, ? extends Iterable<? extends T3>> function3, Function<? super T3, ? extends Iterable<? extends T4>> function4, Function<? super T4, ? extends Iterable<? extends T5>> function5) {
        return Seq.crossApply(Seq.seq(iterable), (? super T1 t) -> Seq.seq((Iterable)function2.apply((Object)t)), (? super T2 t) -> Seq.seq((Iterable)function3.apply((Object)t)), (? super T3 t) -> Seq.seq((Iterable)function4.apply((Object)t)), (? super T4 t) -> Seq.seq((Iterable)function5.apply((Object)t)));
    }

    public static <T1, T2, T3, T4, T5, T6> Seq<Tuple6<T1, T2, T3, T4, T5, T6>> crossApply(Iterable<? extends T1> iterable, Function<? super T1, ? extends Iterable<? extends T2>> function2, Function<? super T2, ? extends Iterable<? extends T3>> function3, Function<? super T3, ? extends Iterable<? extends T4>> function4, Function<? super T4, ? extends Iterable<? extends T5>> function5, Function<? super T5, ? extends Iterable<? extends T6>> function6) {
        return Seq.crossApply(Seq.seq(iterable), (? super T1 t) -> Seq.seq((Iterable)function2.apply((Object)t)), (? super T2 t) -> Seq.seq((Iterable)function3.apply((Object)t)), (? super T3 t) -> Seq.seq((Iterable)function4.apply((Object)t)), (? super T4 t) -> Seq.seq((Iterable)function5.apply((Object)t)), (? super T5 t) -> Seq.seq((Iterable)function6.apply((Object)t)));
    }

    public static <T1, T2, T3, T4, T5, T6, T7> Seq<Tuple7<T1, T2, T3, T4, T5, T6, T7>> crossApply(Iterable<? extends T1> iterable, Function<? super T1, ? extends Iterable<? extends T2>> function2, Function<? super T2, ? extends Iterable<? extends T3>> function3, Function<? super T3, ? extends Iterable<? extends T4>> function4, Function<? super T4, ? extends Iterable<? extends T5>> function5, Function<? super T5, ? extends Iterable<? extends T6>> function6, Function<? super T6, ? extends Iterable<? extends T7>> function7) {
        return Seq.crossApply(Seq.seq(iterable), (? super T1 t) -> Seq.seq((Iterable)function2.apply((Object)t)), (? super T2 t) -> Seq.seq((Iterable)function3.apply((Object)t)), (? super T3 t) -> Seq.seq((Iterable)function4.apply((Object)t)), (? super T4 t) -> Seq.seq((Iterable)function5.apply((Object)t)), (? super T5 t) -> Seq.seq((Iterable)function6.apply((Object)t)), (? super T6 t) -> Seq.seq((Iterable)function7.apply((Object)t)));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8> Seq<Tuple8<T1, T2, T3, T4, T5, T6, T7, T8>> crossApply(Iterable<? extends T1> iterable, Function<? super T1, ? extends Iterable<? extends T2>> function2, Function<? super T2, ? extends Iterable<? extends T3>> function3, Function<? super T3, ? extends Iterable<? extends T4>> function4, Function<? super T4, ? extends Iterable<? extends T5>> function5, Function<? super T5, ? extends Iterable<? extends T6>> function6, Function<? super T6, ? extends Iterable<? extends T7>> function7, Function<? super T7, ? extends Iterable<? extends T8>> function8) {
        return Seq.crossApply(Seq.seq(iterable), (? super T1 t) -> Seq.seq((Iterable)function2.apply((Object)t)), (? super T2 t) -> Seq.seq((Iterable)function3.apply((Object)t)), (? super T3 t) -> Seq.seq((Iterable)function4.apply((Object)t)), (? super T4 t) -> Seq.seq((Iterable)function5.apply((Object)t)), (? super T5 t) -> Seq.seq((Iterable)function6.apply((Object)t)), (? super T6 t) -> Seq.seq((Iterable)function7.apply((Object)t)), (? super T7 t) -> Seq.seq((Iterable)function8.apply((Object)t)));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9> Seq<Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> crossApply(Iterable<? extends T1> iterable, Function<? super T1, ? extends Iterable<? extends T2>> function2, Function<? super T2, ? extends Iterable<? extends T3>> function3, Function<? super T3, ? extends Iterable<? extends T4>> function4, Function<? super T4, ? extends Iterable<? extends T5>> function5, Function<? super T5, ? extends Iterable<? extends T6>> function6, Function<? super T6, ? extends Iterable<? extends T7>> function7, Function<? super T7, ? extends Iterable<? extends T8>> function8, Function<? super T8, ? extends Iterable<? extends T9>> function9) {
        return Seq.crossApply(Seq.seq(iterable), (? super T1 t) -> Seq.seq((Iterable)function2.apply((Object)t)), (? super T2 t) -> Seq.seq((Iterable)function3.apply((Object)t)), (? super T3 t) -> Seq.seq((Iterable)function4.apply((Object)t)), (? super T4 t) -> Seq.seq((Iterable)function5.apply((Object)t)), (? super T5 t) -> Seq.seq((Iterable)function6.apply((Object)t)), (? super T6 t) -> Seq.seq((Iterable)function7.apply((Object)t)), (? super T7 t) -> Seq.seq((Iterable)function8.apply((Object)t)), (? super T8 t) -> Seq.seq((Iterable)function9.apply((Object)t)));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> Seq<Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> crossApply(Iterable<? extends T1> iterable, Function<? super T1, ? extends Iterable<? extends T2>> function2, Function<? super T2, ? extends Iterable<? extends T3>> function3, Function<? super T3, ? extends Iterable<? extends T4>> function4, Function<? super T4, ? extends Iterable<? extends T5>> function5, Function<? super T5, ? extends Iterable<? extends T6>> function6, Function<? super T6, ? extends Iterable<? extends T7>> function7, Function<? super T7, ? extends Iterable<? extends T8>> function8, Function<? super T8, ? extends Iterable<? extends T9>> function9, Function<? super T9, ? extends Iterable<? extends T10>> function10) {
        return Seq.crossApply(Seq.seq(iterable), (? super T1 t) -> Seq.seq((Iterable)function2.apply((Object)t)), (? super T2 t) -> Seq.seq((Iterable)function3.apply((Object)t)), (? super T3 t) -> Seq.seq((Iterable)function4.apply((Object)t)), (? super T4 t) -> Seq.seq((Iterable)function5.apply((Object)t)), (? super T5 t) -> Seq.seq((Iterable)function6.apply((Object)t)), (? super T6 t) -> Seq.seq((Iterable)function7.apply((Object)t)), (? super T7 t) -> Seq.seq((Iterable)function8.apply((Object)t)), (? super T8 t) -> Seq.seq((Iterable)function9.apply((Object)t)), (? super T9 t) -> Seq.seq((Iterable)function10.apply((Object)t)));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> Seq<Tuple11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> crossApply(Iterable<? extends T1> iterable, Function<? super T1, ? extends Iterable<? extends T2>> function2, Function<? super T2, ? extends Iterable<? extends T3>> function3, Function<? super T3, ? extends Iterable<? extends T4>> function4, Function<? super T4, ? extends Iterable<? extends T5>> function5, Function<? super T5, ? extends Iterable<? extends T6>> function6, Function<? super T6, ? extends Iterable<? extends T7>> function7, Function<? super T7, ? extends Iterable<? extends T8>> function8, Function<? super T8, ? extends Iterable<? extends T9>> function9, Function<? super T9, ? extends Iterable<? extends T10>> function10, Function<? super T10, ? extends Iterable<? extends T11>> function11) {
        return Seq.crossApply(Seq.seq(iterable), (? super T1 t) -> Seq.seq((Iterable)function2.apply((Object)t)), (? super T2 t) -> Seq.seq((Iterable)function3.apply((Object)t)), (? super T3 t) -> Seq.seq((Iterable)function4.apply((Object)t)), (? super T4 t) -> Seq.seq((Iterable)function5.apply((Object)t)), (? super T5 t) -> Seq.seq((Iterable)function6.apply((Object)t)), (? super T6 t) -> Seq.seq((Iterable)function7.apply((Object)t)), (? super T7 t) -> Seq.seq((Iterable)function8.apply((Object)t)), (? super T8 t) -> Seq.seq((Iterable)function9.apply((Object)t)), (? super T9 t) -> Seq.seq((Iterable)function10.apply((Object)t)), (? super T10 t) -> Seq.seq((Iterable)function11.apply((Object)t)));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> Seq<Tuple12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>> crossApply(Iterable<? extends T1> iterable, Function<? super T1, ? extends Iterable<? extends T2>> function2, Function<? super T2, ? extends Iterable<? extends T3>> function3, Function<? super T3, ? extends Iterable<? extends T4>> function4, Function<? super T4, ? extends Iterable<? extends T5>> function5, Function<? super T5, ? extends Iterable<? extends T6>> function6, Function<? super T6, ? extends Iterable<? extends T7>> function7, Function<? super T7, ? extends Iterable<? extends T8>> function8, Function<? super T8, ? extends Iterable<? extends T9>> function9, Function<? super T9, ? extends Iterable<? extends T10>> function10, Function<? super T10, ? extends Iterable<? extends T11>> function11, Function<? super T11, ? extends Iterable<? extends T12>> function12) {
        return Seq.crossApply(Seq.seq(iterable), (? super T1 t) -> Seq.seq((Iterable)function2.apply((Object)t)), (? super T2 t) -> Seq.seq((Iterable)function3.apply((Object)t)), (? super T3 t) -> Seq.seq((Iterable)function4.apply((Object)t)), (? super T4 t) -> Seq.seq((Iterable)function5.apply((Object)t)), (? super T5 t) -> Seq.seq((Iterable)function6.apply((Object)t)), (? super T6 t) -> Seq.seq((Iterable)function7.apply((Object)t)), (? super T7 t) -> Seq.seq((Iterable)function8.apply((Object)t)), (? super T8 t) -> Seq.seq((Iterable)function9.apply((Object)t)), (? super T9 t) -> Seq.seq((Iterable)function10.apply((Object)t)), (? super T10 t) -> Seq.seq((Iterable)function11.apply((Object)t)), (? super T11 t) -> Seq.seq((Iterable)function12.apply((Object)t)));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> Seq<Tuple13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> crossApply(Iterable<? extends T1> iterable, Function<? super T1, ? extends Iterable<? extends T2>> function2, Function<? super T2, ? extends Iterable<? extends T3>> function3, Function<? super T3, ? extends Iterable<? extends T4>> function4, Function<? super T4, ? extends Iterable<? extends T5>> function5, Function<? super T5, ? extends Iterable<? extends T6>> function6, Function<? super T6, ? extends Iterable<? extends T7>> function7, Function<? super T7, ? extends Iterable<? extends T8>> function8, Function<? super T8, ? extends Iterable<? extends T9>> function9, Function<? super T9, ? extends Iterable<? extends T10>> function10, Function<? super T10, ? extends Iterable<? extends T11>> function11, Function<? super T11, ? extends Iterable<? extends T12>> function12, Function<? super T12, ? extends Iterable<? extends T13>> function13) {
        return Seq.crossApply(Seq.seq(iterable), (? super T1 t) -> Seq.seq((Iterable)function2.apply((Object)t)), (? super T2 t) -> Seq.seq((Iterable)function3.apply((Object)t)), (? super T3 t) -> Seq.seq((Iterable)function4.apply((Object)t)), (? super T4 t) -> Seq.seq((Iterable)function5.apply((Object)t)), (? super T5 t) -> Seq.seq((Iterable)function6.apply((Object)t)), (? super T6 t) -> Seq.seq((Iterable)function7.apply((Object)t)), (? super T7 t) -> Seq.seq((Iterable)function8.apply((Object)t)), (? super T8 t) -> Seq.seq((Iterable)function9.apply((Object)t)), (? super T9 t) -> Seq.seq((Iterable)function10.apply((Object)t)), (? super T10 t) -> Seq.seq((Iterable)function11.apply((Object)t)), (? super T11 t) -> Seq.seq((Iterable)function12.apply((Object)t)), (? super T12 t) -> Seq.seq((Iterable)function13.apply((Object)t)));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> Seq<Tuple14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> crossApply(Iterable<? extends T1> iterable, Function<? super T1, ? extends Iterable<? extends T2>> function2, Function<? super T2, ? extends Iterable<? extends T3>> function3, Function<? super T3, ? extends Iterable<? extends T4>> function4, Function<? super T4, ? extends Iterable<? extends T5>> function5, Function<? super T5, ? extends Iterable<? extends T6>> function6, Function<? super T6, ? extends Iterable<? extends T7>> function7, Function<? super T7, ? extends Iterable<? extends T8>> function8, Function<? super T8, ? extends Iterable<? extends T9>> function9, Function<? super T9, ? extends Iterable<? extends T10>> function10, Function<? super T10, ? extends Iterable<? extends T11>> function11, Function<? super T11, ? extends Iterable<? extends T12>> function12, Function<? super T12, ? extends Iterable<? extends T13>> function13, Function<? super T13, ? extends Iterable<? extends T14>> function14) {
        return Seq.crossApply(Seq.seq(iterable), (? super T1 t) -> Seq.seq((Iterable)function2.apply((Object)t)), (? super T2 t) -> Seq.seq((Iterable)function3.apply((Object)t)), (? super T3 t) -> Seq.seq((Iterable)function4.apply((Object)t)), (? super T4 t) -> Seq.seq((Iterable)function5.apply((Object)t)), (? super T5 t) -> Seq.seq((Iterable)function6.apply((Object)t)), (? super T6 t) -> Seq.seq((Iterable)function7.apply((Object)t)), (? super T7 t) -> Seq.seq((Iterable)function8.apply((Object)t)), (? super T8 t) -> Seq.seq((Iterable)function9.apply((Object)t)), (? super T9 t) -> Seq.seq((Iterable)function10.apply((Object)t)), (? super T10 t) -> Seq.seq((Iterable)function11.apply((Object)t)), (? super T11 t) -> Seq.seq((Iterable)function12.apply((Object)t)), (? super T12 t) -> Seq.seq((Iterable)function13.apply((Object)t)), (? super T13 t) -> Seq.seq((Iterable)function14.apply((Object)t)));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> Seq<Tuple15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> crossApply(Iterable<? extends T1> iterable, Function<? super T1, ? extends Iterable<? extends T2>> function2, Function<? super T2, ? extends Iterable<? extends T3>> function3, Function<? super T3, ? extends Iterable<? extends T4>> function4, Function<? super T4, ? extends Iterable<? extends T5>> function5, Function<? super T5, ? extends Iterable<? extends T6>> function6, Function<? super T6, ? extends Iterable<? extends T7>> function7, Function<? super T7, ? extends Iterable<? extends T8>> function8, Function<? super T8, ? extends Iterable<? extends T9>> function9, Function<? super T9, ? extends Iterable<? extends T10>> function10, Function<? super T10, ? extends Iterable<? extends T11>> function11, Function<? super T11, ? extends Iterable<? extends T12>> function12, Function<? super T12, ? extends Iterable<? extends T13>> function13, Function<? super T13, ? extends Iterable<? extends T14>> function14, Function<? super T14, ? extends Iterable<? extends T15>> function15) {
        return Seq.crossApply(Seq.seq(iterable), (? super T1 t) -> Seq.seq((Iterable)function2.apply((Object)t)), (? super T2 t) -> Seq.seq((Iterable)function3.apply((Object)t)), (? super T3 t) -> Seq.seq((Iterable)function4.apply((Object)t)), (? super T4 t) -> Seq.seq((Iterable)function5.apply((Object)t)), (? super T5 t) -> Seq.seq((Iterable)function6.apply((Object)t)), (? super T6 t) -> Seq.seq((Iterable)function7.apply((Object)t)), (? super T7 t) -> Seq.seq((Iterable)function8.apply((Object)t)), (? super T8 t) -> Seq.seq((Iterable)function9.apply((Object)t)), (? super T9 t) -> Seq.seq((Iterable)function10.apply((Object)t)), (? super T10 t) -> Seq.seq((Iterable)function11.apply((Object)t)), (? super T11 t) -> Seq.seq((Iterable)function12.apply((Object)t)), (? super T12 t) -> Seq.seq((Iterable)function13.apply((Object)t)), (? super T13 t) -> Seq.seq((Iterable)function14.apply((Object)t)), (? super T14 t) -> Seq.seq((Iterable)function15.apply((Object)t)));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> Seq<Tuple16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> crossApply(Iterable<? extends T1> iterable, Function<? super T1, ? extends Iterable<? extends T2>> function2, Function<? super T2, ? extends Iterable<? extends T3>> function3, Function<? super T3, ? extends Iterable<? extends T4>> function4, Function<? super T4, ? extends Iterable<? extends T5>> function5, Function<? super T5, ? extends Iterable<? extends T6>> function6, Function<? super T6, ? extends Iterable<? extends T7>> function7, Function<? super T7, ? extends Iterable<? extends T8>> function8, Function<? super T8, ? extends Iterable<? extends T9>> function9, Function<? super T9, ? extends Iterable<? extends T10>> function10, Function<? super T10, ? extends Iterable<? extends T11>> function11, Function<? super T11, ? extends Iterable<? extends T12>> function12, Function<? super T12, ? extends Iterable<? extends T13>> function13, Function<? super T13, ? extends Iterable<? extends T14>> function14, Function<? super T14, ? extends Iterable<? extends T15>> function15, Function<? super T15, ? extends Iterable<? extends T16>> function16) {
        return Seq.crossApply(Seq.seq(iterable), (? super T1 t) -> Seq.seq((Iterable)function2.apply((Object)t)), (? super T2 t) -> Seq.seq((Iterable)function3.apply((Object)t)), (? super T3 t) -> Seq.seq((Iterable)function4.apply((Object)t)), (? super T4 t) -> Seq.seq((Iterable)function5.apply((Object)t)), (? super T5 t) -> Seq.seq((Iterable)function6.apply((Object)t)), (? super T6 t) -> Seq.seq((Iterable)function7.apply((Object)t)), (? super T7 t) -> Seq.seq((Iterable)function8.apply((Object)t)), (? super T8 t) -> Seq.seq((Iterable)function9.apply((Object)t)), (? super T9 t) -> Seq.seq((Iterable)function10.apply((Object)t)), (? super T10 t) -> Seq.seq((Iterable)function11.apply((Object)t)), (? super T11 t) -> Seq.seq((Iterable)function12.apply((Object)t)), (? super T12 t) -> Seq.seq((Iterable)function13.apply((Object)t)), (? super T13 t) -> Seq.seq((Iterable)function14.apply((Object)t)), (? super T14 t) -> Seq.seq((Iterable)function15.apply((Object)t)), (? super T15 t) -> Seq.seq((Iterable)function16.apply((Object)t)));
    }

    public static <T1, T2> Seq<Tuple2<T1, T2>> crossApply(Seq<? extends T1> seq, Function<? super T1, ? extends Seq<? extends T2>> function2) {
        return seq.flatMap((T t1) -> ((Seq)function2.apply((Object)t1)).map((T t2) -> Tuple.tuple(t1, t2))).onClose(seq::close);
    }

    public static <T1, T2, T3> Seq<Tuple3<T1, T2, T3>> crossApply(Seq<? extends T1> seq, Function<? super T1, ? extends Seq<? extends T2>> function2, Function<? super T2, ? extends Seq<? extends T3>> function3) {
        return seq.flatMap((T t1) -> ((Seq)function2.apply((Object)t1)).map((T t2) -> Tuple.tuple(t1, t2))).flatMap((T t) -> ((Seq)function3.apply((Object)t.v2)).map((T t3) -> t.concat(t3))).onClose(seq::close);
    }

    public static <T1, T2, T3, T4> Seq<Tuple4<T1, T2, T3, T4>> crossApply(Seq<? extends T1> seq, Function<? super T1, ? extends Seq<? extends T2>> function2, Function<? super T2, ? extends Seq<? extends T3>> function3, Function<? super T3, ? extends Seq<? extends T4>> function4) {
        return seq.flatMap((T t1) -> ((Seq)function2.apply((Object)t1)).map((T t2) -> Tuple.tuple(t1, t2))).flatMap((T t) -> ((Seq)function3.apply((Object)t.v2)).map((T t3) -> t.concat(t3))).flatMap((T t) -> ((Seq)function4.apply((Object)t.v3)).map((T t4) -> t.concat(t4))).onClose(seq::close);
    }

    public static <T1, T2, T3, T4, T5> Seq<Tuple5<T1, T2, T3, T4, T5>> crossApply(Seq<? extends T1> seq, Function<? super T1, ? extends Seq<? extends T2>> function2, Function<? super T2, ? extends Seq<? extends T3>> function3, Function<? super T3, ? extends Seq<? extends T4>> function4, Function<? super T4, ? extends Seq<? extends T5>> function5) {
        return seq.flatMap((T t1) -> ((Seq)function2.apply((Object)t1)).map((T t2) -> Tuple.tuple(t1, t2))).flatMap((T t) -> ((Seq)function3.apply((Object)t.v2)).map((T t3) -> t.concat(t3))).flatMap((T t) -> ((Seq)function4.apply((Object)t.v3)).map((T t4) -> t.concat(t4))).flatMap((T t) -> ((Seq)function5.apply((Object)t.v4)).map((T t5) -> t.concat(t5))).onClose(seq::close);
    }

    public static <T1, T2, T3, T4, T5, T6> Seq<Tuple6<T1, T2, T3, T4, T5, T6>> crossApply(Seq<? extends T1> seq, Function<? super T1, ? extends Seq<? extends T2>> function2, Function<? super T2, ? extends Seq<? extends T3>> function3, Function<? super T3, ? extends Seq<? extends T4>> function4, Function<? super T4, ? extends Seq<? extends T5>> function5, Function<? super T5, ? extends Seq<? extends T6>> function6) {
        return seq.flatMap((T t1) -> ((Seq)function2.apply((Object)t1)).map((T t2) -> Tuple.tuple(t1, t2))).flatMap((T t) -> ((Seq)function3.apply((Object)t.v2)).map((T t3) -> t.concat(t3))).flatMap((T t) -> ((Seq)function4.apply((Object)t.v3)).map((T t4) -> t.concat(t4))).flatMap((T t) -> ((Seq)function5.apply((Object)t.v4)).map((T t5) -> t.concat(t5))).flatMap((T t) -> ((Seq)function6.apply((Object)t.v5)).map((T t6) -> t.concat(t6))).onClose(seq::close);
    }

    public static <T1, T2, T3, T4, T5, T6, T7> Seq<Tuple7<T1, T2, T3, T4, T5, T6, T7>> crossApply(Seq<? extends T1> seq, Function<? super T1, ? extends Seq<? extends T2>> function2, Function<? super T2, ? extends Seq<? extends T3>> function3, Function<? super T3, ? extends Seq<? extends T4>> function4, Function<? super T4, ? extends Seq<? extends T5>> function5, Function<? super T5, ? extends Seq<? extends T6>> function6, Function<? super T6, ? extends Seq<? extends T7>> function7) {
        return seq.flatMap((T t1) -> ((Seq)function2.apply((Object)t1)).map((T t2) -> Tuple.tuple(t1, t2))).flatMap((T t) -> ((Seq)function3.apply((Object)t.v2)).map((T t3) -> t.concat(t3))).flatMap((T t) -> ((Seq)function4.apply((Object)t.v3)).map((T t4) -> t.concat(t4))).flatMap((T t) -> ((Seq)function5.apply((Object)t.v4)).map((T t5) -> t.concat(t5))).flatMap((T t) -> ((Seq)function6.apply((Object)t.v5)).map((T t6) -> t.concat(t6))).flatMap((T t) -> ((Seq)function7.apply((Object)t.v6)).map((T t7) -> t.concat(t7))).onClose(seq::close);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8> Seq<Tuple8<T1, T2, T3, T4, T5, T6, T7, T8>> crossApply(Seq<? extends T1> seq, Function<? super T1, ? extends Seq<? extends T2>> function2, Function<? super T2, ? extends Seq<? extends T3>> function3, Function<? super T3, ? extends Seq<? extends T4>> function4, Function<? super T4, ? extends Seq<? extends T5>> function5, Function<? super T5, ? extends Seq<? extends T6>> function6, Function<? super T6, ? extends Seq<? extends T7>> function7, Function<? super T7, ? extends Seq<? extends T8>> function8) {
        return seq.flatMap((T t1) -> ((Seq)function2.apply((Object)t1)).map((T t2) -> Tuple.tuple(t1, t2))).flatMap((T t) -> ((Seq)function3.apply((Object)t.v2)).map((T t3) -> t.concat(t3))).flatMap((T t) -> ((Seq)function4.apply((Object)t.v3)).map((T t4) -> t.concat(t4))).flatMap((T t) -> ((Seq)function5.apply((Object)t.v4)).map((T t5) -> t.concat(t5))).flatMap((T t) -> ((Seq)function6.apply((Object)t.v5)).map((T t6) -> t.concat(t6))).flatMap((T t) -> ((Seq)function7.apply((Object)t.v6)).map((T t7) -> t.concat(t7))).flatMap((T t) -> ((Seq)function8.apply((Object)t.v7)).map((T t8) -> t.concat(t8))).onClose(seq::close);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9> Seq<Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> crossApply(Seq<? extends T1> seq, Function<? super T1, ? extends Seq<? extends T2>> function2, Function<? super T2, ? extends Seq<? extends T3>> function3, Function<? super T3, ? extends Seq<? extends T4>> function4, Function<? super T4, ? extends Seq<? extends T5>> function5, Function<? super T5, ? extends Seq<? extends T6>> function6, Function<? super T6, ? extends Seq<? extends T7>> function7, Function<? super T7, ? extends Seq<? extends T8>> function8, Function<? super T8, ? extends Seq<? extends T9>> function9) {
        return seq.flatMap((T t1) -> ((Seq)function2.apply((Object)t1)).map((T t2) -> Tuple.tuple(t1, t2))).flatMap((T t) -> ((Seq)function3.apply((Object)t.v2)).map((T t3) -> t.concat(t3))).flatMap((T t) -> ((Seq)function4.apply((Object)t.v3)).map((T t4) -> t.concat(t4))).flatMap((T t) -> ((Seq)function5.apply((Object)t.v4)).map((T t5) -> t.concat(t5))).flatMap((T t) -> ((Seq)function6.apply((Object)t.v5)).map((T t6) -> t.concat(t6))).flatMap((T t) -> ((Seq)function7.apply((Object)t.v6)).map((T t7) -> t.concat(t7))).flatMap((T t) -> ((Seq)function8.apply((Object)t.v7)).map((T t8) -> t.concat(t8))).flatMap((T t) -> ((Seq)function9.apply((Object)t.v8)).map((T t9) -> t.concat(t9))).onClose(seq::close);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> Seq<Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> crossApply(Seq<? extends T1> seq, Function<? super T1, ? extends Seq<? extends T2>> function2, Function<? super T2, ? extends Seq<? extends T3>> function3, Function<? super T3, ? extends Seq<? extends T4>> function4, Function<? super T4, ? extends Seq<? extends T5>> function5, Function<? super T5, ? extends Seq<? extends T6>> function6, Function<? super T6, ? extends Seq<? extends T7>> function7, Function<? super T7, ? extends Seq<? extends T8>> function8, Function<? super T8, ? extends Seq<? extends T9>> function9, Function<? super T9, ? extends Seq<? extends T10>> function10) {
        return seq.flatMap((T t1) -> ((Seq)function2.apply((Object)t1)).map((T t2) -> Tuple.tuple(t1, t2))).flatMap((T t) -> ((Seq)function3.apply((Object)t.v2)).map((T t3) -> t.concat(t3))).flatMap((T t) -> ((Seq)function4.apply((Object)t.v3)).map((T t4) -> t.concat(t4))).flatMap((T t) -> ((Seq)function5.apply((Object)t.v4)).map((T t5) -> t.concat(t5))).flatMap((T t) -> ((Seq)function6.apply((Object)t.v5)).map((T t6) -> t.concat(t6))).flatMap((T t) -> ((Seq)function7.apply((Object)t.v6)).map((T t7) -> t.concat(t7))).flatMap((T t) -> ((Seq)function8.apply((Object)t.v7)).map((T t8) -> t.concat(t8))).flatMap((T t) -> ((Seq)function9.apply((Object)t.v8)).map((T t9) -> t.concat(t9))).flatMap((T t) -> ((Seq)function10.apply((Object)t.v9)).map((T t10) -> t.concat(t10))).onClose(seq::close);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> Seq<Tuple11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> crossApply(Seq<? extends T1> seq, Function<? super T1, ? extends Seq<? extends T2>> function2, Function<? super T2, ? extends Seq<? extends T3>> function3, Function<? super T3, ? extends Seq<? extends T4>> function4, Function<? super T4, ? extends Seq<? extends T5>> function5, Function<? super T5, ? extends Seq<? extends T6>> function6, Function<? super T6, ? extends Seq<? extends T7>> function7, Function<? super T7, ? extends Seq<? extends T8>> function8, Function<? super T8, ? extends Seq<? extends T9>> function9, Function<? super T9, ? extends Seq<? extends T10>> function10, Function<? super T10, ? extends Seq<? extends T11>> function11) {
        return seq.flatMap((T t1) -> ((Seq)function2.apply((Object)t1)).map((T t2) -> Tuple.tuple(t1, t2))).flatMap((T t) -> ((Seq)function3.apply((Object)t.v2)).map((T t3) -> t.concat(t3))).flatMap((T t) -> ((Seq)function4.apply((Object)t.v3)).map((T t4) -> t.concat(t4))).flatMap((T t) -> ((Seq)function5.apply((Object)t.v4)).map((T t5) -> t.concat(t5))).flatMap((T t) -> ((Seq)function6.apply((Object)t.v5)).map((T t6) -> t.concat(t6))).flatMap((T t) -> ((Seq)function7.apply((Object)t.v6)).map((T t7) -> t.concat(t7))).flatMap((T t) -> ((Seq)function8.apply((Object)t.v7)).map((T t8) -> t.concat(t8))).flatMap((T t) -> ((Seq)function9.apply((Object)t.v8)).map((T t9) -> t.concat(t9))).flatMap((T t) -> ((Seq)function10.apply((Object)t.v9)).map((T t10) -> t.concat(t10))).flatMap((T t) -> ((Seq)function11.apply((Object)t.v10)).map((T t11) -> t.concat(t11))).onClose(seq::close);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> Seq<Tuple12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>> crossApply(Seq<? extends T1> seq, Function<? super T1, ? extends Seq<? extends T2>> function2, Function<? super T2, ? extends Seq<? extends T3>> function3, Function<? super T3, ? extends Seq<? extends T4>> function4, Function<? super T4, ? extends Seq<? extends T5>> function5, Function<? super T5, ? extends Seq<? extends T6>> function6, Function<? super T6, ? extends Seq<? extends T7>> function7, Function<? super T7, ? extends Seq<? extends T8>> function8, Function<? super T8, ? extends Seq<? extends T9>> function9, Function<? super T9, ? extends Seq<? extends T10>> function10, Function<? super T10, ? extends Seq<? extends T11>> function11, Function<? super T11, ? extends Seq<? extends T12>> function12) {
        return seq.flatMap((T t1) -> ((Seq)function2.apply((Object)t1)).map((T t2) -> Tuple.tuple(t1, t2))).flatMap((T t) -> ((Seq)function3.apply((Object)t.v2)).map((T t3) -> t.concat(t3))).flatMap((T t) -> ((Seq)function4.apply((Object)t.v3)).map((T t4) -> t.concat(t4))).flatMap((T t) -> ((Seq)function5.apply((Object)t.v4)).map((T t5) -> t.concat(t5))).flatMap((T t) -> ((Seq)function6.apply((Object)t.v5)).map((T t6) -> t.concat(t6))).flatMap((T t) -> ((Seq)function7.apply((Object)t.v6)).map((T t7) -> t.concat(t7))).flatMap((T t) -> ((Seq)function8.apply((Object)t.v7)).map((T t8) -> t.concat(t8))).flatMap((T t) -> ((Seq)function9.apply((Object)t.v8)).map((T t9) -> t.concat(t9))).flatMap((T t) -> ((Seq)function10.apply((Object)t.v9)).map((T t10) -> t.concat(t10))).flatMap((T t) -> ((Seq)function11.apply((Object)t.v10)).map((T t11) -> t.concat(t11))).flatMap((T t) -> ((Seq)function12.apply((Object)t.v11)).map((T t12) -> t.concat(t12))).onClose(seq::close);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> Seq<Tuple13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> crossApply(Seq<? extends T1> seq, Function<? super T1, ? extends Seq<? extends T2>> function2, Function<? super T2, ? extends Seq<? extends T3>> function3, Function<? super T3, ? extends Seq<? extends T4>> function4, Function<? super T4, ? extends Seq<? extends T5>> function5, Function<? super T5, ? extends Seq<? extends T6>> function6, Function<? super T6, ? extends Seq<? extends T7>> function7, Function<? super T7, ? extends Seq<? extends T8>> function8, Function<? super T8, ? extends Seq<? extends T9>> function9, Function<? super T9, ? extends Seq<? extends T10>> function10, Function<? super T10, ? extends Seq<? extends T11>> function11, Function<? super T11, ? extends Seq<? extends T12>> function12, Function<? super T12, ? extends Seq<? extends T13>> function13) {
        return seq.flatMap((T t1) -> ((Seq)function2.apply((Object)t1)).map((T t2) -> Tuple.tuple(t1, t2))).flatMap((T t) -> ((Seq)function3.apply((Object)t.v2)).map((T t3) -> t.concat(t3))).flatMap((T t) -> ((Seq)function4.apply((Object)t.v3)).map((T t4) -> t.concat(t4))).flatMap((T t) -> ((Seq)function5.apply((Object)t.v4)).map((T t5) -> t.concat(t5))).flatMap((T t) -> ((Seq)function6.apply((Object)t.v5)).map((T t6) -> t.concat(t6))).flatMap((T t) -> ((Seq)function7.apply((Object)t.v6)).map((T t7) -> t.concat(t7))).flatMap((T t) -> ((Seq)function8.apply((Object)t.v7)).map((T t8) -> t.concat(t8))).flatMap((T t) -> ((Seq)function9.apply((Object)t.v8)).map((T t9) -> t.concat(t9))).flatMap((T t) -> ((Seq)function10.apply((Object)t.v9)).map((T t10) -> t.concat(t10))).flatMap((T t) -> ((Seq)function11.apply((Object)t.v10)).map((T t11) -> t.concat(t11))).flatMap((T t) -> ((Seq)function12.apply((Object)t.v11)).map((T t12) -> t.concat(t12))).flatMap((T t) -> ((Seq)function13.apply((Object)t.v12)).map((T t13) -> t.concat(t13))).onClose(seq::close);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> Seq<Tuple14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> crossApply(Seq<? extends T1> seq, Function<? super T1, ? extends Seq<? extends T2>> function2, Function<? super T2, ? extends Seq<? extends T3>> function3, Function<? super T3, ? extends Seq<? extends T4>> function4, Function<? super T4, ? extends Seq<? extends T5>> function5, Function<? super T5, ? extends Seq<? extends T6>> function6, Function<? super T6, ? extends Seq<? extends T7>> function7, Function<? super T7, ? extends Seq<? extends T8>> function8, Function<? super T8, ? extends Seq<? extends T9>> function9, Function<? super T9, ? extends Seq<? extends T10>> function10, Function<? super T10, ? extends Seq<? extends T11>> function11, Function<? super T11, ? extends Seq<? extends T12>> function12, Function<? super T12, ? extends Seq<? extends T13>> function13, Function<? super T13, ? extends Seq<? extends T14>> function14) {
        return seq.flatMap((T t1) -> ((Seq)function2.apply((Object)t1)).map((T t2) -> Tuple.tuple(t1, t2))).flatMap((T t) -> ((Seq)function3.apply((Object)t.v2)).map((T t3) -> t.concat(t3))).flatMap((T t) -> ((Seq)function4.apply((Object)t.v3)).map((T t4) -> t.concat(t4))).flatMap((T t) -> ((Seq)function5.apply((Object)t.v4)).map((T t5) -> t.concat(t5))).flatMap((T t) -> ((Seq)function6.apply((Object)t.v5)).map((T t6) -> t.concat(t6))).flatMap((T t) -> ((Seq)function7.apply((Object)t.v6)).map((T t7) -> t.concat(t7))).flatMap((T t) -> ((Seq)function8.apply((Object)t.v7)).map((T t8) -> t.concat(t8))).flatMap((T t) -> ((Seq)function9.apply((Object)t.v8)).map((T t9) -> t.concat(t9))).flatMap((T t) -> ((Seq)function10.apply((Object)t.v9)).map((T t10) -> t.concat(t10))).flatMap((T t) -> ((Seq)function11.apply((Object)t.v10)).map((T t11) -> t.concat(t11))).flatMap((T t) -> ((Seq)function12.apply((Object)t.v11)).map((T t12) -> t.concat(t12))).flatMap((T t) -> ((Seq)function13.apply((Object)t.v12)).map((T t13) -> t.concat(t13))).flatMap((T t) -> ((Seq)function14.apply((Object)t.v13)).map((T t14) -> t.concat(t14))).onClose(seq::close);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> Seq<Tuple15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> crossApply(Seq<? extends T1> seq, Function<? super T1, ? extends Seq<? extends T2>> function2, Function<? super T2, ? extends Seq<? extends T3>> function3, Function<? super T3, ? extends Seq<? extends T4>> function4, Function<? super T4, ? extends Seq<? extends T5>> function5, Function<? super T5, ? extends Seq<? extends T6>> function6, Function<? super T6, ? extends Seq<? extends T7>> function7, Function<? super T7, ? extends Seq<? extends T8>> function8, Function<? super T8, ? extends Seq<? extends T9>> function9, Function<? super T9, ? extends Seq<? extends T10>> function10, Function<? super T10, ? extends Seq<? extends T11>> function11, Function<? super T11, ? extends Seq<? extends T12>> function12, Function<? super T12, ? extends Seq<? extends T13>> function13, Function<? super T13, ? extends Seq<? extends T14>> function14, Function<? super T14, ? extends Seq<? extends T15>> function15) {
        return seq.flatMap((T t1) -> ((Seq)function2.apply((Object)t1)).map((T t2) -> Tuple.tuple(t1, t2))).flatMap((T t) -> ((Seq)function3.apply((Object)t.v2)).map((T t3) -> t.concat(t3))).flatMap((T t) -> ((Seq)function4.apply((Object)t.v3)).map((T t4) -> t.concat(t4))).flatMap((T t) -> ((Seq)function5.apply((Object)t.v4)).map((T t5) -> t.concat(t5))).flatMap((T t) -> ((Seq)function6.apply((Object)t.v5)).map((T t6) -> t.concat(t6))).flatMap((T t) -> ((Seq)function7.apply((Object)t.v6)).map((T t7) -> t.concat(t7))).flatMap((T t) -> ((Seq)function8.apply((Object)t.v7)).map((T t8) -> t.concat(t8))).flatMap((T t) -> ((Seq)function9.apply((Object)t.v8)).map((T t9) -> t.concat(t9))).flatMap((T t) -> ((Seq)function10.apply((Object)t.v9)).map((T t10) -> t.concat(t10))).flatMap((T t) -> ((Seq)function11.apply((Object)t.v10)).map((T t11) -> t.concat(t11))).flatMap((T t) -> ((Seq)function12.apply((Object)t.v11)).map((T t12) -> t.concat(t12))).flatMap((T t) -> ((Seq)function13.apply((Object)t.v12)).map((T t13) -> t.concat(t13))).flatMap((T t) -> ((Seq)function14.apply((Object)t.v13)).map((T t14) -> t.concat(t14))).flatMap((T t) -> ((Seq)function15.apply((Object)t.v14)).map((T t15) -> t.concat(t15))).onClose(seq::close);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> Seq<Tuple16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> crossApply(Seq<? extends T1> seq, Function<? super T1, ? extends Seq<? extends T2>> function2, Function<? super T2, ? extends Seq<? extends T3>> function3, Function<? super T3, ? extends Seq<? extends T4>> function4, Function<? super T4, ? extends Seq<? extends T5>> function5, Function<? super T5, ? extends Seq<? extends T6>> function6, Function<? super T6, ? extends Seq<? extends T7>> function7, Function<? super T7, ? extends Seq<? extends T8>> function8, Function<? super T8, ? extends Seq<? extends T9>> function9, Function<? super T9, ? extends Seq<? extends T10>> function10, Function<? super T10, ? extends Seq<? extends T11>> function11, Function<? super T11, ? extends Seq<? extends T12>> function12, Function<? super T12, ? extends Seq<? extends T13>> function13, Function<? super T13, ? extends Seq<? extends T14>> function14, Function<? super T14, ? extends Seq<? extends T15>> function15, Function<? super T15, ? extends Seq<? extends T16>> function16) {
        return seq.flatMap((T t1) -> ((Seq)function2.apply((Object)t1)).map((T t2) -> Tuple.tuple(t1, t2))).flatMap((T t) -> ((Seq)function3.apply((Object)t.v2)).map((T t3) -> t.concat(t3))).flatMap((T t) -> ((Seq)function4.apply((Object)t.v3)).map((T t4) -> t.concat(t4))).flatMap((T t) -> ((Seq)function5.apply((Object)t.v4)).map((T t5) -> t.concat(t5))).flatMap((T t) -> ((Seq)function6.apply((Object)t.v5)).map((T t6) -> t.concat(t6))).flatMap((T t) -> ((Seq)function7.apply((Object)t.v6)).map((T t7) -> t.concat(t7))).flatMap((T t) -> ((Seq)function8.apply((Object)t.v7)).map((T t8) -> t.concat(t8))).flatMap((T t) -> ((Seq)function9.apply((Object)t.v8)).map((T t9) -> t.concat(t9))).flatMap((T t) -> ((Seq)function10.apply((Object)t.v9)).map((T t10) -> t.concat(t10))).flatMap((T t) -> ((Seq)function11.apply((Object)t.v10)).map((T t11) -> t.concat(t11))).flatMap((T t) -> ((Seq)function12.apply((Object)t.v11)).map((T t12) -> t.concat(t12))).flatMap((T t) -> ((Seq)function13.apply((Object)t.v12)).map((T t13) -> t.concat(t13))).flatMap((T t) -> ((Seq)function14.apply((Object)t.v13)).map((T t14) -> t.concat(t14))).flatMap((T t) -> ((Seq)function15.apply((Object)t.v14)).map((T t15) -> t.concat(t15))).flatMap((T t) -> ((Seq)function16.apply((Object)t.v15)).map((T t16) -> t.concat(t16))).onClose(seq::close);
    }

    public static <T1, T2> Seq<Tuple2<T1, T2>> outerApply(Stream<? extends T1> stream, Function<? super T1, ? extends Stream<? extends T2>> function2) {
        return Seq.outerApply(Seq.seq(stream), (? super T1 t) -> Seq.seq((Stream)function2.apply((Object)t)));
    }

    public static <T1, T2, T3> Seq<Tuple3<T1, T2, T3>> outerApply(Stream<? extends T1> stream, Function<? super T1, ? extends Stream<? extends T2>> function2, Function<? super T2, ? extends Stream<? extends T3>> function3) {
        return Seq.outerApply(Seq.seq(stream), (? super T1 t) -> Seq.seq((Stream)function2.apply((Object)t)), (? super T2 t) -> Seq.seq((Stream)function3.apply((Object)t)));
    }

    public static <T1, T2, T3, T4> Seq<Tuple4<T1, T2, T3, T4>> outerApply(Stream<? extends T1> stream, Function<? super T1, ? extends Stream<? extends T2>> function2, Function<? super T2, ? extends Stream<? extends T3>> function3, Function<? super T3, ? extends Stream<? extends T4>> function4) {
        return Seq.outerApply(Seq.seq(stream), (? super T1 t) -> Seq.seq((Stream)function2.apply((Object)t)), (? super T2 t) -> Seq.seq((Stream)function3.apply((Object)t)), (? super T3 t) -> Seq.seq((Stream)function4.apply((Object)t)));
    }

    public static <T1, T2, T3, T4, T5> Seq<Tuple5<T1, T2, T3, T4, T5>> outerApply(Stream<? extends T1> stream, Function<? super T1, ? extends Stream<? extends T2>> function2, Function<? super T2, ? extends Stream<? extends T3>> function3, Function<? super T3, ? extends Stream<? extends T4>> function4, Function<? super T4, ? extends Stream<? extends T5>> function5) {
        return Seq.outerApply(Seq.seq(stream), (? super T1 t) -> Seq.seq((Stream)function2.apply((Object)t)), (? super T2 t) -> Seq.seq((Stream)function3.apply((Object)t)), (? super T3 t) -> Seq.seq((Stream)function4.apply((Object)t)), (? super T4 t) -> Seq.seq((Stream)function5.apply((Object)t)));
    }

    public static <T1, T2, T3, T4, T5, T6> Seq<Tuple6<T1, T2, T3, T4, T5, T6>> outerApply(Stream<? extends T1> stream, Function<? super T1, ? extends Stream<? extends T2>> function2, Function<? super T2, ? extends Stream<? extends T3>> function3, Function<? super T3, ? extends Stream<? extends T4>> function4, Function<? super T4, ? extends Stream<? extends T5>> function5, Function<? super T5, ? extends Stream<? extends T6>> function6) {
        return Seq.outerApply(Seq.seq(stream), (? super T1 t) -> Seq.seq((Stream)function2.apply((Object)t)), (? super T2 t) -> Seq.seq((Stream)function3.apply((Object)t)), (? super T3 t) -> Seq.seq((Stream)function4.apply((Object)t)), (? super T4 t) -> Seq.seq((Stream)function5.apply((Object)t)), (? super T5 t) -> Seq.seq((Stream)function6.apply((Object)t)));
    }

    public static <T1, T2, T3, T4, T5, T6, T7> Seq<Tuple7<T1, T2, T3, T4, T5, T6, T7>> outerApply(Stream<? extends T1> stream, Function<? super T1, ? extends Stream<? extends T2>> function2, Function<? super T2, ? extends Stream<? extends T3>> function3, Function<? super T3, ? extends Stream<? extends T4>> function4, Function<? super T4, ? extends Stream<? extends T5>> function5, Function<? super T5, ? extends Stream<? extends T6>> function6, Function<? super T6, ? extends Stream<? extends T7>> function7) {
        return Seq.outerApply(Seq.seq(stream), (? super T1 t) -> Seq.seq((Stream)function2.apply((Object)t)), (? super T2 t) -> Seq.seq((Stream)function3.apply((Object)t)), (? super T3 t) -> Seq.seq((Stream)function4.apply((Object)t)), (? super T4 t) -> Seq.seq((Stream)function5.apply((Object)t)), (? super T5 t) -> Seq.seq((Stream)function6.apply((Object)t)), (? super T6 t) -> Seq.seq((Stream)function7.apply((Object)t)));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8> Seq<Tuple8<T1, T2, T3, T4, T5, T6, T7, T8>> outerApply(Stream<? extends T1> stream, Function<? super T1, ? extends Stream<? extends T2>> function2, Function<? super T2, ? extends Stream<? extends T3>> function3, Function<? super T3, ? extends Stream<? extends T4>> function4, Function<? super T4, ? extends Stream<? extends T5>> function5, Function<? super T5, ? extends Stream<? extends T6>> function6, Function<? super T6, ? extends Stream<? extends T7>> function7, Function<? super T7, ? extends Stream<? extends T8>> function8) {
        return Seq.outerApply(Seq.seq(stream), (? super T1 t) -> Seq.seq((Stream)function2.apply((Object)t)), (? super T2 t) -> Seq.seq((Stream)function3.apply((Object)t)), (? super T3 t) -> Seq.seq((Stream)function4.apply((Object)t)), (? super T4 t) -> Seq.seq((Stream)function5.apply((Object)t)), (? super T5 t) -> Seq.seq((Stream)function6.apply((Object)t)), (? super T6 t) -> Seq.seq((Stream)function7.apply((Object)t)), (? super T7 t) -> Seq.seq((Stream)function8.apply((Object)t)));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9> Seq<Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> outerApply(Stream<? extends T1> stream, Function<? super T1, ? extends Stream<? extends T2>> function2, Function<? super T2, ? extends Stream<? extends T3>> function3, Function<? super T3, ? extends Stream<? extends T4>> function4, Function<? super T4, ? extends Stream<? extends T5>> function5, Function<? super T5, ? extends Stream<? extends T6>> function6, Function<? super T6, ? extends Stream<? extends T7>> function7, Function<? super T7, ? extends Stream<? extends T8>> function8, Function<? super T8, ? extends Stream<? extends T9>> function9) {
        return Seq.outerApply(Seq.seq(stream), (? super T1 t) -> Seq.seq((Stream)function2.apply((Object)t)), (? super T2 t) -> Seq.seq((Stream)function3.apply((Object)t)), (? super T3 t) -> Seq.seq((Stream)function4.apply((Object)t)), (? super T4 t) -> Seq.seq((Stream)function5.apply((Object)t)), (? super T5 t) -> Seq.seq((Stream)function6.apply((Object)t)), (? super T6 t) -> Seq.seq((Stream)function7.apply((Object)t)), (? super T7 t) -> Seq.seq((Stream)function8.apply((Object)t)), (? super T8 t) -> Seq.seq((Stream)function9.apply((Object)t)));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> Seq<Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> outerApply(Stream<? extends T1> stream, Function<? super T1, ? extends Stream<? extends T2>> function2, Function<? super T2, ? extends Stream<? extends T3>> function3, Function<? super T3, ? extends Stream<? extends T4>> function4, Function<? super T4, ? extends Stream<? extends T5>> function5, Function<? super T5, ? extends Stream<? extends T6>> function6, Function<? super T6, ? extends Stream<? extends T7>> function7, Function<? super T7, ? extends Stream<? extends T8>> function8, Function<? super T8, ? extends Stream<? extends T9>> function9, Function<? super T9, ? extends Stream<? extends T10>> function10) {
        return Seq.outerApply(Seq.seq(stream), (? super T1 t) -> Seq.seq((Stream)function2.apply((Object)t)), (? super T2 t) -> Seq.seq((Stream)function3.apply((Object)t)), (? super T3 t) -> Seq.seq((Stream)function4.apply((Object)t)), (? super T4 t) -> Seq.seq((Stream)function5.apply((Object)t)), (? super T5 t) -> Seq.seq((Stream)function6.apply((Object)t)), (? super T6 t) -> Seq.seq((Stream)function7.apply((Object)t)), (? super T7 t) -> Seq.seq((Stream)function8.apply((Object)t)), (? super T8 t) -> Seq.seq((Stream)function9.apply((Object)t)), (? super T9 t) -> Seq.seq((Stream)function10.apply((Object)t)));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> Seq<Tuple11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> outerApply(Stream<? extends T1> stream, Function<? super T1, ? extends Stream<? extends T2>> function2, Function<? super T2, ? extends Stream<? extends T3>> function3, Function<? super T3, ? extends Stream<? extends T4>> function4, Function<? super T4, ? extends Stream<? extends T5>> function5, Function<? super T5, ? extends Stream<? extends T6>> function6, Function<? super T6, ? extends Stream<? extends T7>> function7, Function<? super T7, ? extends Stream<? extends T8>> function8, Function<? super T8, ? extends Stream<? extends T9>> function9, Function<? super T9, ? extends Stream<? extends T10>> function10, Function<? super T10, ? extends Stream<? extends T11>> function11) {
        return Seq.outerApply(Seq.seq(stream), (? super T1 t) -> Seq.seq((Stream)function2.apply((Object)t)), (? super T2 t) -> Seq.seq((Stream)function3.apply((Object)t)), (? super T3 t) -> Seq.seq((Stream)function4.apply((Object)t)), (? super T4 t) -> Seq.seq((Stream)function5.apply((Object)t)), (? super T5 t) -> Seq.seq((Stream)function6.apply((Object)t)), (? super T6 t) -> Seq.seq((Stream)function7.apply((Object)t)), (? super T7 t) -> Seq.seq((Stream)function8.apply((Object)t)), (? super T8 t) -> Seq.seq((Stream)function9.apply((Object)t)), (? super T9 t) -> Seq.seq((Stream)function10.apply((Object)t)), (? super T10 t) -> Seq.seq((Stream)function11.apply((Object)t)));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> Seq<Tuple12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>> outerApply(Stream<? extends T1> stream, Function<? super T1, ? extends Stream<? extends T2>> function2, Function<? super T2, ? extends Stream<? extends T3>> function3, Function<? super T3, ? extends Stream<? extends T4>> function4, Function<? super T4, ? extends Stream<? extends T5>> function5, Function<? super T5, ? extends Stream<? extends T6>> function6, Function<? super T6, ? extends Stream<? extends T7>> function7, Function<? super T7, ? extends Stream<? extends T8>> function8, Function<? super T8, ? extends Stream<? extends T9>> function9, Function<? super T9, ? extends Stream<? extends T10>> function10, Function<? super T10, ? extends Stream<? extends T11>> function11, Function<? super T11, ? extends Stream<? extends T12>> function12) {
        return Seq.outerApply(Seq.seq(stream), (? super T1 t) -> Seq.seq((Stream)function2.apply((Object)t)), (? super T2 t) -> Seq.seq((Stream)function3.apply((Object)t)), (? super T3 t) -> Seq.seq((Stream)function4.apply((Object)t)), (? super T4 t) -> Seq.seq((Stream)function5.apply((Object)t)), (? super T5 t) -> Seq.seq((Stream)function6.apply((Object)t)), (? super T6 t) -> Seq.seq((Stream)function7.apply((Object)t)), (? super T7 t) -> Seq.seq((Stream)function8.apply((Object)t)), (? super T8 t) -> Seq.seq((Stream)function9.apply((Object)t)), (? super T9 t) -> Seq.seq((Stream)function10.apply((Object)t)), (? super T10 t) -> Seq.seq((Stream)function11.apply((Object)t)), (? super T11 t) -> Seq.seq((Stream)function12.apply((Object)t)));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> Seq<Tuple13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> outerApply(Stream<? extends T1> stream, Function<? super T1, ? extends Stream<? extends T2>> function2, Function<? super T2, ? extends Stream<? extends T3>> function3, Function<? super T3, ? extends Stream<? extends T4>> function4, Function<? super T4, ? extends Stream<? extends T5>> function5, Function<? super T5, ? extends Stream<? extends T6>> function6, Function<? super T6, ? extends Stream<? extends T7>> function7, Function<? super T7, ? extends Stream<? extends T8>> function8, Function<? super T8, ? extends Stream<? extends T9>> function9, Function<? super T9, ? extends Stream<? extends T10>> function10, Function<? super T10, ? extends Stream<? extends T11>> function11, Function<? super T11, ? extends Stream<? extends T12>> function12, Function<? super T12, ? extends Stream<? extends T13>> function13) {
        return Seq.outerApply(Seq.seq(stream), (? super T1 t) -> Seq.seq((Stream)function2.apply((Object)t)), (? super T2 t) -> Seq.seq((Stream)function3.apply((Object)t)), (? super T3 t) -> Seq.seq((Stream)function4.apply((Object)t)), (? super T4 t) -> Seq.seq((Stream)function5.apply((Object)t)), (? super T5 t) -> Seq.seq((Stream)function6.apply((Object)t)), (? super T6 t) -> Seq.seq((Stream)function7.apply((Object)t)), (? super T7 t) -> Seq.seq((Stream)function8.apply((Object)t)), (? super T8 t) -> Seq.seq((Stream)function9.apply((Object)t)), (? super T9 t) -> Seq.seq((Stream)function10.apply((Object)t)), (? super T10 t) -> Seq.seq((Stream)function11.apply((Object)t)), (? super T11 t) -> Seq.seq((Stream)function12.apply((Object)t)), (? super T12 t) -> Seq.seq((Stream)function13.apply((Object)t)));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> Seq<Tuple14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> outerApply(Stream<? extends T1> stream, Function<? super T1, ? extends Stream<? extends T2>> function2, Function<? super T2, ? extends Stream<? extends T3>> function3, Function<? super T3, ? extends Stream<? extends T4>> function4, Function<? super T4, ? extends Stream<? extends T5>> function5, Function<? super T5, ? extends Stream<? extends T6>> function6, Function<? super T6, ? extends Stream<? extends T7>> function7, Function<? super T7, ? extends Stream<? extends T8>> function8, Function<? super T8, ? extends Stream<? extends T9>> function9, Function<? super T9, ? extends Stream<? extends T10>> function10, Function<? super T10, ? extends Stream<? extends T11>> function11, Function<? super T11, ? extends Stream<? extends T12>> function12, Function<? super T12, ? extends Stream<? extends T13>> function13, Function<? super T13, ? extends Stream<? extends T14>> function14) {
        return Seq.outerApply(Seq.seq(stream), (? super T1 t) -> Seq.seq((Stream)function2.apply((Object)t)), (? super T2 t) -> Seq.seq((Stream)function3.apply((Object)t)), (? super T3 t) -> Seq.seq((Stream)function4.apply((Object)t)), (? super T4 t) -> Seq.seq((Stream)function5.apply((Object)t)), (? super T5 t) -> Seq.seq((Stream)function6.apply((Object)t)), (? super T6 t) -> Seq.seq((Stream)function7.apply((Object)t)), (? super T7 t) -> Seq.seq((Stream)function8.apply((Object)t)), (? super T8 t) -> Seq.seq((Stream)function9.apply((Object)t)), (? super T9 t) -> Seq.seq((Stream)function10.apply((Object)t)), (? super T10 t) -> Seq.seq((Stream)function11.apply((Object)t)), (? super T11 t) -> Seq.seq((Stream)function12.apply((Object)t)), (? super T12 t) -> Seq.seq((Stream)function13.apply((Object)t)), (? super T13 t) -> Seq.seq((Stream)function14.apply((Object)t)));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> Seq<Tuple15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> outerApply(Stream<? extends T1> stream, Function<? super T1, ? extends Stream<? extends T2>> function2, Function<? super T2, ? extends Stream<? extends T3>> function3, Function<? super T3, ? extends Stream<? extends T4>> function4, Function<? super T4, ? extends Stream<? extends T5>> function5, Function<? super T5, ? extends Stream<? extends T6>> function6, Function<? super T6, ? extends Stream<? extends T7>> function7, Function<? super T7, ? extends Stream<? extends T8>> function8, Function<? super T8, ? extends Stream<? extends T9>> function9, Function<? super T9, ? extends Stream<? extends T10>> function10, Function<? super T10, ? extends Stream<? extends T11>> function11, Function<? super T11, ? extends Stream<? extends T12>> function12, Function<? super T12, ? extends Stream<? extends T13>> function13, Function<? super T13, ? extends Stream<? extends T14>> function14, Function<? super T14, ? extends Stream<? extends T15>> function15) {
        return Seq.outerApply(Seq.seq(stream), (? super T1 t) -> Seq.seq((Stream)function2.apply((Object)t)), (? super T2 t) -> Seq.seq((Stream)function3.apply((Object)t)), (? super T3 t) -> Seq.seq((Stream)function4.apply((Object)t)), (? super T4 t) -> Seq.seq((Stream)function5.apply((Object)t)), (? super T5 t) -> Seq.seq((Stream)function6.apply((Object)t)), (? super T6 t) -> Seq.seq((Stream)function7.apply((Object)t)), (? super T7 t) -> Seq.seq((Stream)function8.apply((Object)t)), (? super T8 t) -> Seq.seq((Stream)function9.apply((Object)t)), (? super T9 t) -> Seq.seq((Stream)function10.apply((Object)t)), (? super T10 t) -> Seq.seq((Stream)function11.apply((Object)t)), (? super T11 t) -> Seq.seq((Stream)function12.apply((Object)t)), (? super T12 t) -> Seq.seq((Stream)function13.apply((Object)t)), (? super T13 t) -> Seq.seq((Stream)function14.apply((Object)t)), (? super T14 t) -> Seq.seq((Stream)function15.apply((Object)t)));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> Seq<Tuple16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> outerApply(Stream<? extends T1> stream, Function<? super T1, ? extends Stream<? extends T2>> function2, Function<? super T2, ? extends Stream<? extends T3>> function3, Function<? super T3, ? extends Stream<? extends T4>> function4, Function<? super T4, ? extends Stream<? extends T5>> function5, Function<? super T5, ? extends Stream<? extends T6>> function6, Function<? super T6, ? extends Stream<? extends T7>> function7, Function<? super T7, ? extends Stream<? extends T8>> function8, Function<? super T8, ? extends Stream<? extends T9>> function9, Function<? super T9, ? extends Stream<? extends T10>> function10, Function<? super T10, ? extends Stream<? extends T11>> function11, Function<? super T11, ? extends Stream<? extends T12>> function12, Function<? super T12, ? extends Stream<? extends T13>> function13, Function<? super T13, ? extends Stream<? extends T14>> function14, Function<? super T14, ? extends Stream<? extends T15>> function15, Function<? super T15, ? extends Stream<? extends T16>> function16) {
        return Seq.outerApply(Seq.seq(stream), (? super T1 t) -> Seq.seq((Stream)function2.apply((Object)t)), (? super T2 t) -> Seq.seq((Stream)function3.apply((Object)t)), (? super T3 t) -> Seq.seq((Stream)function4.apply((Object)t)), (? super T4 t) -> Seq.seq((Stream)function5.apply((Object)t)), (? super T5 t) -> Seq.seq((Stream)function6.apply((Object)t)), (? super T6 t) -> Seq.seq((Stream)function7.apply((Object)t)), (? super T7 t) -> Seq.seq((Stream)function8.apply((Object)t)), (? super T8 t) -> Seq.seq((Stream)function9.apply((Object)t)), (? super T9 t) -> Seq.seq((Stream)function10.apply((Object)t)), (? super T10 t) -> Seq.seq((Stream)function11.apply((Object)t)), (? super T11 t) -> Seq.seq((Stream)function12.apply((Object)t)), (? super T12 t) -> Seq.seq((Stream)function13.apply((Object)t)), (? super T13 t) -> Seq.seq((Stream)function14.apply((Object)t)), (? super T14 t) -> Seq.seq((Stream)function15.apply((Object)t)), (? super T15 t) -> Seq.seq((Stream)function16.apply((Object)t)));
    }

    public static <T1, T2> Seq<Tuple2<T1, T2>> outerApply(Iterable<? extends T1> iterable, Function<? super T1, ? extends Iterable<? extends T2>> function2) {
        return Seq.outerApply(Seq.seq(iterable), (? super T1 t) -> Seq.seq((Iterable)function2.apply((Object)t)));
    }

    public static <T1, T2, T3> Seq<Tuple3<T1, T2, T3>> outerApply(Iterable<? extends T1> iterable, Function<? super T1, ? extends Iterable<? extends T2>> function2, Function<? super T2, ? extends Iterable<? extends T3>> function3) {
        return Seq.outerApply(Seq.seq(iterable), (? super T1 t) -> Seq.seq((Iterable)function2.apply((Object)t)), (? super T2 t) -> Seq.seq((Iterable)function3.apply((Object)t)));
    }

    public static <T1, T2, T3, T4> Seq<Tuple4<T1, T2, T3, T4>> outerApply(Iterable<? extends T1> iterable, Function<? super T1, ? extends Iterable<? extends T2>> function2, Function<? super T2, ? extends Iterable<? extends T3>> function3, Function<? super T3, ? extends Iterable<? extends T4>> function4) {
        return Seq.outerApply(Seq.seq(iterable), (? super T1 t) -> Seq.seq((Iterable)function2.apply((Object)t)), (? super T2 t) -> Seq.seq((Iterable)function3.apply((Object)t)), (? super T3 t) -> Seq.seq((Iterable)function4.apply((Object)t)));
    }

    public static <T1, T2, T3, T4, T5> Seq<Tuple5<T1, T2, T3, T4, T5>> outerApply(Iterable<? extends T1> iterable, Function<? super T1, ? extends Iterable<? extends T2>> function2, Function<? super T2, ? extends Iterable<? extends T3>> function3, Function<? super T3, ? extends Iterable<? extends T4>> function4, Function<? super T4, ? extends Iterable<? extends T5>> function5) {
        return Seq.outerApply(Seq.seq(iterable), (? super T1 t) -> Seq.seq((Iterable)function2.apply((Object)t)), (? super T2 t) -> Seq.seq((Iterable)function3.apply((Object)t)), (? super T3 t) -> Seq.seq((Iterable)function4.apply((Object)t)), (? super T4 t) -> Seq.seq((Iterable)function5.apply((Object)t)));
    }

    public static <T1, T2, T3, T4, T5, T6> Seq<Tuple6<T1, T2, T3, T4, T5, T6>> outerApply(Iterable<? extends T1> iterable, Function<? super T1, ? extends Iterable<? extends T2>> function2, Function<? super T2, ? extends Iterable<? extends T3>> function3, Function<? super T3, ? extends Iterable<? extends T4>> function4, Function<? super T4, ? extends Iterable<? extends T5>> function5, Function<? super T5, ? extends Iterable<? extends T6>> function6) {
        return Seq.outerApply(Seq.seq(iterable), (? super T1 t) -> Seq.seq((Iterable)function2.apply((Object)t)), (? super T2 t) -> Seq.seq((Iterable)function3.apply((Object)t)), (? super T3 t) -> Seq.seq((Iterable)function4.apply((Object)t)), (? super T4 t) -> Seq.seq((Iterable)function5.apply((Object)t)), (? super T5 t) -> Seq.seq((Iterable)function6.apply((Object)t)));
    }

    public static <T1, T2, T3, T4, T5, T6, T7> Seq<Tuple7<T1, T2, T3, T4, T5, T6, T7>> outerApply(Iterable<? extends T1> iterable, Function<? super T1, ? extends Iterable<? extends T2>> function2, Function<? super T2, ? extends Iterable<? extends T3>> function3, Function<? super T3, ? extends Iterable<? extends T4>> function4, Function<? super T4, ? extends Iterable<? extends T5>> function5, Function<? super T5, ? extends Iterable<? extends T6>> function6, Function<? super T6, ? extends Iterable<? extends T7>> function7) {
        return Seq.outerApply(Seq.seq(iterable), (? super T1 t) -> Seq.seq((Iterable)function2.apply((Object)t)), (? super T2 t) -> Seq.seq((Iterable)function3.apply((Object)t)), (? super T3 t) -> Seq.seq((Iterable)function4.apply((Object)t)), (? super T4 t) -> Seq.seq((Iterable)function5.apply((Object)t)), (? super T5 t) -> Seq.seq((Iterable)function6.apply((Object)t)), (? super T6 t) -> Seq.seq((Iterable)function7.apply((Object)t)));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8> Seq<Tuple8<T1, T2, T3, T4, T5, T6, T7, T8>> outerApply(Iterable<? extends T1> iterable, Function<? super T1, ? extends Iterable<? extends T2>> function2, Function<? super T2, ? extends Iterable<? extends T3>> function3, Function<? super T3, ? extends Iterable<? extends T4>> function4, Function<? super T4, ? extends Iterable<? extends T5>> function5, Function<? super T5, ? extends Iterable<? extends T6>> function6, Function<? super T6, ? extends Iterable<? extends T7>> function7, Function<? super T7, ? extends Iterable<? extends T8>> function8) {
        return Seq.outerApply(Seq.seq(iterable), (? super T1 t) -> Seq.seq((Iterable)function2.apply((Object)t)), (? super T2 t) -> Seq.seq((Iterable)function3.apply((Object)t)), (? super T3 t) -> Seq.seq((Iterable)function4.apply((Object)t)), (? super T4 t) -> Seq.seq((Iterable)function5.apply((Object)t)), (? super T5 t) -> Seq.seq((Iterable)function6.apply((Object)t)), (? super T6 t) -> Seq.seq((Iterable)function7.apply((Object)t)), (? super T7 t) -> Seq.seq((Iterable)function8.apply((Object)t)));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9> Seq<Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> outerApply(Iterable<? extends T1> iterable, Function<? super T1, ? extends Iterable<? extends T2>> function2, Function<? super T2, ? extends Iterable<? extends T3>> function3, Function<? super T3, ? extends Iterable<? extends T4>> function4, Function<? super T4, ? extends Iterable<? extends T5>> function5, Function<? super T5, ? extends Iterable<? extends T6>> function6, Function<? super T6, ? extends Iterable<? extends T7>> function7, Function<? super T7, ? extends Iterable<? extends T8>> function8, Function<? super T8, ? extends Iterable<? extends T9>> function9) {
        return Seq.outerApply(Seq.seq(iterable), (? super T1 t) -> Seq.seq((Iterable)function2.apply((Object)t)), (? super T2 t) -> Seq.seq((Iterable)function3.apply((Object)t)), (? super T3 t) -> Seq.seq((Iterable)function4.apply((Object)t)), (? super T4 t) -> Seq.seq((Iterable)function5.apply((Object)t)), (? super T5 t) -> Seq.seq((Iterable)function6.apply((Object)t)), (? super T6 t) -> Seq.seq((Iterable)function7.apply((Object)t)), (? super T7 t) -> Seq.seq((Iterable)function8.apply((Object)t)), (? super T8 t) -> Seq.seq((Iterable)function9.apply((Object)t)));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> Seq<Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> outerApply(Iterable<? extends T1> iterable, Function<? super T1, ? extends Iterable<? extends T2>> function2, Function<? super T2, ? extends Iterable<? extends T3>> function3, Function<? super T3, ? extends Iterable<? extends T4>> function4, Function<? super T4, ? extends Iterable<? extends T5>> function5, Function<? super T5, ? extends Iterable<? extends T6>> function6, Function<? super T6, ? extends Iterable<? extends T7>> function7, Function<? super T7, ? extends Iterable<? extends T8>> function8, Function<? super T8, ? extends Iterable<? extends T9>> function9, Function<? super T9, ? extends Iterable<? extends T10>> function10) {
        return Seq.outerApply(Seq.seq(iterable), (? super T1 t) -> Seq.seq((Iterable)function2.apply((Object)t)), (? super T2 t) -> Seq.seq((Iterable)function3.apply((Object)t)), (? super T3 t) -> Seq.seq((Iterable)function4.apply((Object)t)), (? super T4 t) -> Seq.seq((Iterable)function5.apply((Object)t)), (? super T5 t) -> Seq.seq((Iterable)function6.apply((Object)t)), (? super T6 t) -> Seq.seq((Iterable)function7.apply((Object)t)), (? super T7 t) -> Seq.seq((Iterable)function8.apply((Object)t)), (? super T8 t) -> Seq.seq((Iterable)function9.apply((Object)t)), (? super T9 t) -> Seq.seq((Iterable)function10.apply((Object)t)));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> Seq<Tuple11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> outerApply(Iterable<? extends T1> iterable, Function<? super T1, ? extends Iterable<? extends T2>> function2, Function<? super T2, ? extends Iterable<? extends T3>> function3, Function<? super T3, ? extends Iterable<? extends T4>> function4, Function<? super T4, ? extends Iterable<? extends T5>> function5, Function<? super T5, ? extends Iterable<? extends T6>> function6, Function<? super T6, ? extends Iterable<? extends T7>> function7, Function<? super T7, ? extends Iterable<? extends T8>> function8, Function<? super T8, ? extends Iterable<? extends T9>> function9, Function<? super T9, ? extends Iterable<? extends T10>> function10, Function<? super T10, ? extends Iterable<? extends T11>> function11) {
        return Seq.outerApply(Seq.seq(iterable), (? super T1 t) -> Seq.seq((Iterable)function2.apply((Object)t)), (? super T2 t) -> Seq.seq((Iterable)function3.apply((Object)t)), (? super T3 t) -> Seq.seq((Iterable)function4.apply((Object)t)), (? super T4 t) -> Seq.seq((Iterable)function5.apply((Object)t)), (? super T5 t) -> Seq.seq((Iterable)function6.apply((Object)t)), (? super T6 t) -> Seq.seq((Iterable)function7.apply((Object)t)), (? super T7 t) -> Seq.seq((Iterable)function8.apply((Object)t)), (? super T8 t) -> Seq.seq((Iterable)function9.apply((Object)t)), (? super T9 t) -> Seq.seq((Iterable)function10.apply((Object)t)), (? super T10 t) -> Seq.seq((Iterable)function11.apply((Object)t)));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> Seq<Tuple12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>> outerApply(Iterable<? extends T1> iterable, Function<? super T1, ? extends Iterable<? extends T2>> function2, Function<? super T2, ? extends Iterable<? extends T3>> function3, Function<? super T3, ? extends Iterable<? extends T4>> function4, Function<? super T4, ? extends Iterable<? extends T5>> function5, Function<? super T5, ? extends Iterable<? extends T6>> function6, Function<? super T6, ? extends Iterable<? extends T7>> function7, Function<? super T7, ? extends Iterable<? extends T8>> function8, Function<? super T8, ? extends Iterable<? extends T9>> function9, Function<? super T9, ? extends Iterable<? extends T10>> function10, Function<? super T10, ? extends Iterable<? extends T11>> function11, Function<? super T11, ? extends Iterable<? extends T12>> function12) {
        return Seq.outerApply(Seq.seq(iterable), (? super T1 t) -> Seq.seq((Iterable)function2.apply((Object)t)), (? super T2 t) -> Seq.seq((Iterable)function3.apply((Object)t)), (? super T3 t) -> Seq.seq((Iterable)function4.apply((Object)t)), (? super T4 t) -> Seq.seq((Iterable)function5.apply((Object)t)), (? super T5 t) -> Seq.seq((Iterable)function6.apply((Object)t)), (? super T6 t) -> Seq.seq((Iterable)function7.apply((Object)t)), (? super T7 t) -> Seq.seq((Iterable)function8.apply((Object)t)), (? super T8 t) -> Seq.seq((Iterable)function9.apply((Object)t)), (? super T9 t) -> Seq.seq((Iterable)function10.apply((Object)t)), (? super T10 t) -> Seq.seq((Iterable)function11.apply((Object)t)), (? super T11 t) -> Seq.seq((Iterable)function12.apply((Object)t)));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> Seq<Tuple13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> outerApply(Iterable<? extends T1> iterable, Function<? super T1, ? extends Iterable<? extends T2>> function2, Function<? super T2, ? extends Iterable<? extends T3>> function3, Function<? super T3, ? extends Iterable<? extends T4>> function4, Function<? super T4, ? extends Iterable<? extends T5>> function5, Function<? super T5, ? extends Iterable<? extends T6>> function6, Function<? super T6, ? extends Iterable<? extends T7>> function7, Function<? super T7, ? extends Iterable<? extends T8>> function8, Function<? super T8, ? extends Iterable<? extends T9>> function9, Function<? super T9, ? extends Iterable<? extends T10>> function10, Function<? super T10, ? extends Iterable<? extends T11>> function11, Function<? super T11, ? extends Iterable<? extends T12>> function12, Function<? super T12, ? extends Iterable<? extends T13>> function13) {
        return Seq.outerApply(Seq.seq(iterable), (? super T1 t) -> Seq.seq((Iterable)function2.apply((Object)t)), (? super T2 t) -> Seq.seq((Iterable)function3.apply((Object)t)), (? super T3 t) -> Seq.seq((Iterable)function4.apply((Object)t)), (? super T4 t) -> Seq.seq((Iterable)function5.apply((Object)t)), (? super T5 t) -> Seq.seq((Iterable)function6.apply((Object)t)), (? super T6 t) -> Seq.seq((Iterable)function7.apply((Object)t)), (? super T7 t) -> Seq.seq((Iterable)function8.apply((Object)t)), (? super T8 t) -> Seq.seq((Iterable)function9.apply((Object)t)), (? super T9 t) -> Seq.seq((Iterable)function10.apply((Object)t)), (? super T10 t) -> Seq.seq((Iterable)function11.apply((Object)t)), (? super T11 t) -> Seq.seq((Iterable)function12.apply((Object)t)), (? super T12 t) -> Seq.seq((Iterable)function13.apply((Object)t)));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> Seq<Tuple14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> outerApply(Iterable<? extends T1> iterable, Function<? super T1, ? extends Iterable<? extends T2>> function2, Function<? super T2, ? extends Iterable<? extends T3>> function3, Function<? super T3, ? extends Iterable<? extends T4>> function4, Function<? super T4, ? extends Iterable<? extends T5>> function5, Function<? super T5, ? extends Iterable<? extends T6>> function6, Function<? super T6, ? extends Iterable<? extends T7>> function7, Function<? super T7, ? extends Iterable<? extends T8>> function8, Function<? super T8, ? extends Iterable<? extends T9>> function9, Function<? super T9, ? extends Iterable<? extends T10>> function10, Function<? super T10, ? extends Iterable<? extends T11>> function11, Function<? super T11, ? extends Iterable<? extends T12>> function12, Function<? super T12, ? extends Iterable<? extends T13>> function13, Function<? super T13, ? extends Iterable<? extends T14>> function14) {
        return Seq.outerApply(Seq.seq(iterable), (? super T1 t) -> Seq.seq((Iterable)function2.apply((Object)t)), (? super T2 t) -> Seq.seq((Iterable)function3.apply((Object)t)), (? super T3 t) -> Seq.seq((Iterable)function4.apply((Object)t)), (? super T4 t) -> Seq.seq((Iterable)function5.apply((Object)t)), (? super T5 t) -> Seq.seq((Iterable)function6.apply((Object)t)), (? super T6 t) -> Seq.seq((Iterable)function7.apply((Object)t)), (? super T7 t) -> Seq.seq((Iterable)function8.apply((Object)t)), (? super T8 t) -> Seq.seq((Iterable)function9.apply((Object)t)), (? super T9 t) -> Seq.seq((Iterable)function10.apply((Object)t)), (? super T10 t) -> Seq.seq((Iterable)function11.apply((Object)t)), (? super T11 t) -> Seq.seq((Iterable)function12.apply((Object)t)), (? super T12 t) -> Seq.seq((Iterable)function13.apply((Object)t)), (? super T13 t) -> Seq.seq((Iterable)function14.apply((Object)t)));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> Seq<Tuple15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> outerApply(Iterable<? extends T1> iterable, Function<? super T1, ? extends Iterable<? extends T2>> function2, Function<? super T2, ? extends Iterable<? extends T3>> function3, Function<? super T3, ? extends Iterable<? extends T4>> function4, Function<? super T4, ? extends Iterable<? extends T5>> function5, Function<? super T5, ? extends Iterable<? extends T6>> function6, Function<? super T6, ? extends Iterable<? extends T7>> function7, Function<? super T7, ? extends Iterable<? extends T8>> function8, Function<? super T8, ? extends Iterable<? extends T9>> function9, Function<? super T9, ? extends Iterable<? extends T10>> function10, Function<? super T10, ? extends Iterable<? extends T11>> function11, Function<? super T11, ? extends Iterable<? extends T12>> function12, Function<? super T12, ? extends Iterable<? extends T13>> function13, Function<? super T13, ? extends Iterable<? extends T14>> function14, Function<? super T14, ? extends Iterable<? extends T15>> function15) {
        return Seq.outerApply(Seq.seq(iterable), (? super T1 t) -> Seq.seq((Iterable)function2.apply((Object)t)), (? super T2 t) -> Seq.seq((Iterable)function3.apply((Object)t)), (? super T3 t) -> Seq.seq((Iterable)function4.apply((Object)t)), (? super T4 t) -> Seq.seq((Iterable)function5.apply((Object)t)), (? super T5 t) -> Seq.seq((Iterable)function6.apply((Object)t)), (? super T6 t) -> Seq.seq((Iterable)function7.apply((Object)t)), (? super T7 t) -> Seq.seq((Iterable)function8.apply((Object)t)), (? super T8 t) -> Seq.seq((Iterable)function9.apply((Object)t)), (? super T9 t) -> Seq.seq((Iterable)function10.apply((Object)t)), (? super T10 t) -> Seq.seq((Iterable)function11.apply((Object)t)), (? super T11 t) -> Seq.seq((Iterable)function12.apply((Object)t)), (? super T12 t) -> Seq.seq((Iterable)function13.apply((Object)t)), (? super T13 t) -> Seq.seq((Iterable)function14.apply((Object)t)), (? super T14 t) -> Seq.seq((Iterable)function15.apply((Object)t)));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> Seq<Tuple16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> outerApply(Iterable<? extends T1> iterable, Function<? super T1, ? extends Iterable<? extends T2>> function2, Function<? super T2, ? extends Iterable<? extends T3>> function3, Function<? super T3, ? extends Iterable<? extends T4>> function4, Function<? super T4, ? extends Iterable<? extends T5>> function5, Function<? super T5, ? extends Iterable<? extends T6>> function6, Function<? super T6, ? extends Iterable<? extends T7>> function7, Function<? super T7, ? extends Iterable<? extends T8>> function8, Function<? super T8, ? extends Iterable<? extends T9>> function9, Function<? super T9, ? extends Iterable<? extends T10>> function10, Function<? super T10, ? extends Iterable<? extends T11>> function11, Function<? super T11, ? extends Iterable<? extends T12>> function12, Function<? super T12, ? extends Iterable<? extends T13>> function13, Function<? super T13, ? extends Iterable<? extends T14>> function14, Function<? super T14, ? extends Iterable<? extends T15>> function15, Function<? super T15, ? extends Iterable<? extends T16>> function16) {
        return Seq.outerApply(Seq.seq(iterable), (? super T1 t) -> Seq.seq((Iterable)function2.apply((Object)t)), (? super T2 t) -> Seq.seq((Iterable)function3.apply((Object)t)), (? super T3 t) -> Seq.seq((Iterable)function4.apply((Object)t)), (? super T4 t) -> Seq.seq((Iterable)function5.apply((Object)t)), (? super T5 t) -> Seq.seq((Iterable)function6.apply((Object)t)), (? super T6 t) -> Seq.seq((Iterable)function7.apply((Object)t)), (? super T7 t) -> Seq.seq((Iterable)function8.apply((Object)t)), (? super T8 t) -> Seq.seq((Iterable)function9.apply((Object)t)), (? super T9 t) -> Seq.seq((Iterable)function10.apply((Object)t)), (? super T10 t) -> Seq.seq((Iterable)function11.apply((Object)t)), (? super T11 t) -> Seq.seq((Iterable)function12.apply((Object)t)), (? super T12 t) -> Seq.seq((Iterable)function13.apply((Object)t)), (? super T13 t) -> Seq.seq((Iterable)function14.apply((Object)t)), (? super T14 t) -> Seq.seq((Iterable)function15.apply((Object)t)), (? super T15 t) -> Seq.seq((Iterable)function16.apply((Object)t)));
    }

    public static <T1, T2> Seq<Tuple2<T1, T2>> outerApply(Seq<? extends T1> seq, Function<? super T1, ? extends Seq<? extends T2>> function2) {
        return seq.flatMap((T t1) -> ((Seq)function2.apply((Object)t1)).onEmpty(null).map((T t2) -> Tuple.tuple(t1, t2))).onClose(seq::close);
    }

    public static <T1, T2, T3> Seq<Tuple3<T1, T2, T3>> outerApply(Seq<? extends T1> seq, Function<? super T1, ? extends Seq<? extends T2>> function2, Function<? super T2, ? extends Seq<? extends T3>> function3) {
        return seq.flatMap((T t1) -> ((Seq)function2.apply((Object)t1)).onEmpty(null).map((T t2) -> Tuple.tuple(t1, t2))).flatMap((T t) -> ((Seq)function3.apply((Object)t.v2)).onEmpty(null).map((T t3) -> t.concat(t3))).onClose(seq::close);
    }

    public static <T1, T2, T3, T4> Seq<Tuple4<T1, T2, T3, T4>> outerApply(Seq<? extends T1> seq, Function<? super T1, ? extends Seq<? extends T2>> function2, Function<? super T2, ? extends Seq<? extends T3>> function3, Function<? super T3, ? extends Seq<? extends T4>> function4) {
        return seq.flatMap((T t1) -> ((Seq)function2.apply((Object)t1)).onEmpty(null).map((T t2) -> Tuple.tuple(t1, t2))).flatMap((T t) -> ((Seq)function3.apply((Object)t.v2)).onEmpty(null).map((T t3) -> t.concat(t3))).flatMap((T t) -> ((Seq)function4.apply((Object)t.v3)).onEmpty(null).map((T t4) -> t.concat(t4))).onClose(seq::close);
    }

    public static <T1, T2, T3, T4, T5> Seq<Tuple5<T1, T2, T3, T4, T5>> outerApply(Seq<? extends T1> seq, Function<? super T1, ? extends Seq<? extends T2>> function2, Function<? super T2, ? extends Seq<? extends T3>> function3, Function<? super T3, ? extends Seq<? extends T4>> function4, Function<? super T4, ? extends Seq<? extends T5>> function5) {
        return seq.flatMap((T t1) -> ((Seq)function2.apply((Object)t1)).onEmpty(null).map((T t2) -> Tuple.tuple(t1, t2))).flatMap((T t) -> ((Seq)function3.apply((Object)t.v2)).onEmpty(null).map((T t3) -> t.concat(t3))).flatMap((T t) -> ((Seq)function4.apply((Object)t.v3)).onEmpty(null).map((T t4) -> t.concat(t4))).flatMap((T t) -> ((Seq)function5.apply((Object)t.v4)).onEmpty(null).map((T t5) -> t.concat(t5))).onClose(seq::close);
    }

    public static <T1, T2, T3, T4, T5, T6> Seq<Tuple6<T1, T2, T3, T4, T5, T6>> outerApply(Seq<? extends T1> seq, Function<? super T1, ? extends Seq<? extends T2>> function2, Function<? super T2, ? extends Seq<? extends T3>> function3, Function<? super T3, ? extends Seq<? extends T4>> function4, Function<? super T4, ? extends Seq<? extends T5>> function5, Function<? super T5, ? extends Seq<? extends T6>> function6) {
        return seq.flatMap((T t1) -> ((Seq)function2.apply((Object)t1)).onEmpty(null).map((T t2) -> Tuple.tuple(t1, t2))).flatMap((T t) -> ((Seq)function3.apply((Object)t.v2)).onEmpty(null).map((T t3) -> t.concat(t3))).flatMap((T t) -> ((Seq)function4.apply((Object)t.v3)).onEmpty(null).map((T t4) -> t.concat(t4))).flatMap((T t) -> ((Seq)function5.apply((Object)t.v4)).onEmpty(null).map((T t5) -> t.concat(t5))).flatMap((T t) -> ((Seq)function6.apply((Object)t.v5)).onEmpty(null).map((T t6) -> t.concat(t6))).onClose(seq::close);
    }

    public static <T1, T2, T3, T4, T5, T6, T7> Seq<Tuple7<T1, T2, T3, T4, T5, T6, T7>> outerApply(Seq<? extends T1> seq, Function<? super T1, ? extends Seq<? extends T2>> function2, Function<? super T2, ? extends Seq<? extends T3>> function3, Function<? super T3, ? extends Seq<? extends T4>> function4, Function<? super T4, ? extends Seq<? extends T5>> function5, Function<? super T5, ? extends Seq<? extends T6>> function6, Function<? super T6, ? extends Seq<? extends T7>> function7) {
        return seq.flatMap((T t1) -> ((Seq)function2.apply((Object)t1)).onEmpty(null).map((T t2) -> Tuple.tuple(t1, t2))).flatMap((T t) -> ((Seq)function3.apply((Object)t.v2)).onEmpty(null).map((T t3) -> t.concat(t3))).flatMap((T t) -> ((Seq)function4.apply((Object)t.v3)).onEmpty(null).map((T t4) -> t.concat(t4))).flatMap((T t) -> ((Seq)function5.apply((Object)t.v4)).onEmpty(null).map((T t5) -> t.concat(t5))).flatMap((T t) -> ((Seq)function6.apply((Object)t.v5)).onEmpty(null).map((T t6) -> t.concat(t6))).flatMap((T t) -> ((Seq)function7.apply((Object)t.v6)).onEmpty(null).map((T t7) -> t.concat(t7))).onClose(seq::close);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8> Seq<Tuple8<T1, T2, T3, T4, T5, T6, T7, T8>> outerApply(Seq<? extends T1> seq, Function<? super T1, ? extends Seq<? extends T2>> function2, Function<? super T2, ? extends Seq<? extends T3>> function3, Function<? super T3, ? extends Seq<? extends T4>> function4, Function<? super T4, ? extends Seq<? extends T5>> function5, Function<? super T5, ? extends Seq<? extends T6>> function6, Function<? super T6, ? extends Seq<? extends T7>> function7, Function<? super T7, ? extends Seq<? extends T8>> function8) {
        return seq.flatMap((T t1) -> ((Seq)function2.apply((Object)t1)).onEmpty(null).map((T t2) -> Tuple.tuple(t1, t2))).flatMap((T t) -> ((Seq)function3.apply((Object)t.v2)).onEmpty(null).map((T t3) -> t.concat(t3))).flatMap((T t) -> ((Seq)function4.apply((Object)t.v3)).onEmpty(null).map((T t4) -> t.concat(t4))).flatMap((T t) -> ((Seq)function5.apply((Object)t.v4)).onEmpty(null).map((T t5) -> t.concat(t5))).flatMap((T t) -> ((Seq)function6.apply((Object)t.v5)).onEmpty(null).map((T t6) -> t.concat(t6))).flatMap((T t) -> ((Seq)function7.apply((Object)t.v6)).onEmpty(null).map((T t7) -> t.concat(t7))).flatMap((T t) -> ((Seq)function8.apply((Object)t.v7)).onEmpty(null).map((T t8) -> t.concat(t8))).onClose(seq::close);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9> Seq<Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> outerApply(Seq<? extends T1> seq, Function<? super T1, ? extends Seq<? extends T2>> function2, Function<? super T2, ? extends Seq<? extends T3>> function3, Function<? super T3, ? extends Seq<? extends T4>> function4, Function<? super T4, ? extends Seq<? extends T5>> function5, Function<? super T5, ? extends Seq<? extends T6>> function6, Function<? super T6, ? extends Seq<? extends T7>> function7, Function<? super T7, ? extends Seq<? extends T8>> function8, Function<? super T8, ? extends Seq<? extends T9>> function9) {
        return seq.flatMap((T t1) -> ((Seq)function2.apply((Object)t1)).onEmpty(null).map((T t2) -> Tuple.tuple(t1, t2))).flatMap((T t) -> ((Seq)function3.apply((Object)t.v2)).onEmpty(null).map((T t3) -> t.concat(t3))).flatMap((T t) -> ((Seq)function4.apply((Object)t.v3)).onEmpty(null).map((T t4) -> t.concat(t4))).flatMap((T t) -> ((Seq)function5.apply((Object)t.v4)).onEmpty(null).map((T t5) -> t.concat(t5))).flatMap((T t) -> ((Seq)function6.apply((Object)t.v5)).onEmpty(null).map((T t6) -> t.concat(t6))).flatMap((T t) -> ((Seq)function7.apply((Object)t.v6)).onEmpty(null).map((T t7) -> t.concat(t7))).flatMap((T t) -> ((Seq)function8.apply((Object)t.v7)).onEmpty(null).map((T t8) -> t.concat(t8))).flatMap((T t) -> ((Seq)function9.apply((Object)t.v8)).onEmpty(null).map((T t9) -> t.concat(t9))).onClose(seq::close);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> Seq<Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> outerApply(Seq<? extends T1> seq, Function<? super T1, ? extends Seq<? extends T2>> function2, Function<? super T2, ? extends Seq<? extends T3>> function3, Function<? super T3, ? extends Seq<? extends T4>> function4, Function<? super T4, ? extends Seq<? extends T5>> function5, Function<? super T5, ? extends Seq<? extends T6>> function6, Function<? super T6, ? extends Seq<? extends T7>> function7, Function<? super T7, ? extends Seq<? extends T8>> function8, Function<? super T8, ? extends Seq<? extends T9>> function9, Function<? super T9, ? extends Seq<? extends T10>> function10) {
        return seq.flatMap((T t1) -> ((Seq)function2.apply((Object)t1)).onEmpty(null).map((T t2) -> Tuple.tuple(t1, t2))).flatMap((T t) -> ((Seq)function3.apply((Object)t.v2)).onEmpty(null).map((T t3) -> t.concat(t3))).flatMap((T t) -> ((Seq)function4.apply((Object)t.v3)).onEmpty(null).map((T t4) -> t.concat(t4))).flatMap((T t) -> ((Seq)function5.apply((Object)t.v4)).onEmpty(null).map((T t5) -> t.concat(t5))).flatMap((T t) -> ((Seq)function6.apply((Object)t.v5)).onEmpty(null).map((T t6) -> t.concat(t6))).flatMap((T t) -> ((Seq)function7.apply((Object)t.v6)).onEmpty(null).map((T t7) -> t.concat(t7))).flatMap((T t) -> ((Seq)function8.apply((Object)t.v7)).onEmpty(null).map((T t8) -> t.concat(t8))).flatMap((T t) -> ((Seq)function9.apply((Object)t.v8)).onEmpty(null).map((T t9) -> t.concat(t9))).flatMap((T t) -> ((Seq)function10.apply((Object)t.v9)).onEmpty(null).map((T t10) -> t.concat(t10))).onClose(seq::close);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> Seq<Tuple11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> outerApply(Seq<? extends T1> seq, Function<? super T1, ? extends Seq<? extends T2>> function2, Function<? super T2, ? extends Seq<? extends T3>> function3, Function<? super T3, ? extends Seq<? extends T4>> function4, Function<? super T4, ? extends Seq<? extends T5>> function5, Function<? super T5, ? extends Seq<? extends T6>> function6, Function<? super T6, ? extends Seq<? extends T7>> function7, Function<? super T7, ? extends Seq<? extends T8>> function8, Function<? super T8, ? extends Seq<? extends T9>> function9, Function<? super T9, ? extends Seq<? extends T10>> function10, Function<? super T10, ? extends Seq<? extends T11>> function11) {
        return seq.flatMap((T t1) -> ((Seq)function2.apply((Object)t1)).onEmpty(null).map((T t2) -> Tuple.tuple(t1, t2))).flatMap((T t) -> ((Seq)function3.apply((Object)t.v2)).onEmpty(null).map((T t3) -> t.concat(t3))).flatMap((T t) -> ((Seq)function4.apply((Object)t.v3)).onEmpty(null).map((T t4) -> t.concat(t4))).flatMap((T t) -> ((Seq)function5.apply((Object)t.v4)).onEmpty(null).map((T t5) -> t.concat(t5))).flatMap((T t) -> ((Seq)function6.apply((Object)t.v5)).onEmpty(null).map((T t6) -> t.concat(t6))).flatMap((T t) -> ((Seq)function7.apply((Object)t.v6)).onEmpty(null).map((T t7) -> t.concat(t7))).flatMap((T t) -> ((Seq)function8.apply((Object)t.v7)).onEmpty(null).map((T t8) -> t.concat(t8))).flatMap((T t) -> ((Seq)function9.apply((Object)t.v8)).onEmpty(null).map((T t9) -> t.concat(t9))).flatMap((T t) -> ((Seq)function10.apply((Object)t.v9)).onEmpty(null).map((T t10) -> t.concat(t10))).flatMap((T t) -> ((Seq)function11.apply((Object)t.v10)).onEmpty(null).map((T t11) -> t.concat(t11))).onClose(seq::close);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> Seq<Tuple12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>> outerApply(Seq<? extends T1> seq, Function<? super T1, ? extends Seq<? extends T2>> function2, Function<? super T2, ? extends Seq<? extends T3>> function3, Function<? super T3, ? extends Seq<? extends T4>> function4, Function<? super T4, ? extends Seq<? extends T5>> function5, Function<? super T5, ? extends Seq<? extends T6>> function6, Function<? super T6, ? extends Seq<? extends T7>> function7, Function<? super T7, ? extends Seq<? extends T8>> function8, Function<? super T8, ? extends Seq<? extends T9>> function9, Function<? super T9, ? extends Seq<? extends T10>> function10, Function<? super T10, ? extends Seq<? extends T11>> function11, Function<? super T11, ? extends Seq<? extends T12>> function12) {
        return seq.flatMap((T t1) -> ((Seq)function2.apply((Object)t1)).onEmpty(null).map((T t2) -> Tuple.tuple(t1, t2))).flatMap((T t) -> ((Seq)function3.apply((Object)t.v2)).onEmpty(null).map((T t3) -> t.concat(t3))).flatMap((T t) -> ((Seq)function4.apply((Object)t.v3)).onEmpty(null).map((T t4) -> t.concat(t4))).flatMap((T t) -> ((Seq)function5.apply((Object)t.v4)).onEmpty(null).map((T t5) -> t.concat(t5))).flatMap((T t) -> ((Seq)function6.apply((Object)t.v5)).onEmpty(null).map((T t6) -> t.concat(t6))).flatMap((T t) -> ((Seq)function7.apply((Object)t.v6)).onEmpty(null).map((T t7) -> t.concat(t7))).flatMap((T t) -> ((Seq)function8.apply((Object)t.v7)).onEmpty(null).map((T t8) -> t.concat(t8))).flatMap((T t) -> ((Seq)function9.apply((Object)t.v8)).onEmpty(null).map((T t9) -> t.concat(t9))).flatMap((T t) -> ((Seq)function10.apply((Object)t.v9)).onEmpty(null).map((T t10) -> t.concat(t10))).flatMap((T t) -> ((Seq)function11.apply((Object)t.v10)).onEmpty(null).map((T t11) -> t.concat(t11))).flatMap((T t) -> ((Seq)function12.apply((Object)t.v11)).onEmpty(null).map((T t12) -> t.concat(t12))).onClose(seq::close);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> Seq<Tuple13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> outerApply(Seq<? extends T1> seq, Function<? super T1, ? extends Seq<? extends T2>> function2, Function<? super T2, ? extends Seq<? extends T3>> function3, Function<? super T3, ? extends Seq<? extends T4>> function4, Function<? super T4, ? extends Seq<? extends T5>> function5, Function<? super T5, ? extends Seq<? extends T6>> function6, Function<? super T6, ? extends Seq<? extends T7>> function7, Function<? super T7, ? extends Seq<? extends T8>> function8, Function<? super T8, ? extends Seq<? extends T9>> function9, Function<? super T9, ? extends Seq<? extends T10>> function10, Function<? super T10, ? extends Seq<? extends T11>> function11, Function<? super T11, ? extends Seq<? extends T12>> function12, Function<? super T12, ? extends Seq<? extends T13>> function13) {
        return seq.flatMap((T t1) -> ((Seq)function2.apply((Object)t1)).onEmpty(null).map((T t2) -> Tuple.tuple(t1, t2))).flatMap((T t) -> ((Seq)function3.apply((Object)t.v2)).onEmpty(null).map((T t3) -> t.concat(t3))).flatMap((T t) -> ((Seq)function4.apply((Object)t.v3)).onEmpty(null).map((T t4) -> t.concat(t4))).flatMap((T t) -> ((Seq)function5.apply((Object)t.v4)).onEmpty(null).map((T t5) -> t.concat(t5))).flatMap((T t) -> ((Seq)function6.apply((Object)t.v5)).onEmpty(null).map((T t6) -> t.concat(t6))).flatMap((T t) -> ((Seq)function7.apply((Object)t.v6)).onEmpty(null).map((T t7) -> t.concat(t7))).flatMap((T t) -> ((Seq)function8.apply((Object)t.v7)).onEmpty(null).map((T t8) -> t.concat(t8))).flatMap((T t) -> ((Seq)function9.apply((Object)t.v8)).onEmpty(null).map((T t9) -> t.concat(t9))).flatMap((T t) -> ((Seq)function10.apply((Object)t.v9)).onEmpty(null).map((T t10) -> t.concat(t10))).flatMap((T t) -> ((Seq)function11.apply((Object)t.v10)).onEmpty(null).map((T t11) -> t.concat(t11))).flatMap((T t) -> ((Seq)function12.apply((Object)t.v11)).onEmpty(null).map((T t12) -> t.concat(t12))).flatMap((T t) -> ((Seq)function13.apply((Object)t.v12)).onEmpty(null).map((T t13) -> t.concat(t13))).onClose(seq::close);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> Seq<Tuple14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> outerApply(Seq<? extends T1> seq, Function<? super T1, ? extends Seq<? extends T2>> function2, Function<? super T2, ? extends Seq<? extends T3>> function3, Function<? super T3, ? extends Seq<? extends T4>> function4, Function<? super T4, ? extends Seq<? extends T5>> function5, Function<? super T5, ? extends Seq<? extends T6>> function6, Function<? super T6, ? extends Seq<? extends T7>> function7, Function<? super T7, ? extends Seq<? extends T8>> function8, Function<? super T8, ? extends Seq<? extends T9>> function9, Function<? super T9, ? extends Seq<? extends T10>> function10, Function<? super T10, ? extends Seq<? extends T11>> function11, Function<? super T11, ? extends Seq<? extends T12>> function12, Function<? super T12, ? extends Seq<? extends T13>> function13, Function<? super T13, ? extends Seq<? extends T14>> function14) {
        return seq.flatMap((T t1) -> ((Seq)function2.apply((Object)t1)).onEmpty(null).map((T t2) -> Tuple.tuple(t1, t2))).flatMap((T t) -> ((Seq)function3.apply((Object)t.v2)).onEmpty(null).map((T t3) -> t.concat(t3))).flatMap((T t) -> ((Seq)function4.apply((Object)t.v3)).onEmpty(null).map((T t4) -> t.concat(t4))).flatMap((T t) -> ((Seq)function5.apply((Object)t.v4)).onEmpty(null).map((T t5) -> t.concat(t5))).flatMap((T t) -> ((Seq)function6.apply((Object)t.v5)).onEmpty(null).map((T t6) -> t.concat(t6))).flatMap((T t) -> ((Seq)function7.apply((Object)t.v6)).onEmpty(null).map((T t7) -> t.concat(t7))).flatMap((T t) -> ((Seq)function8.apply((Object)t.v7)).onEmpty(null).map((T t8) -> t.concat(t8))).flatMap((T t) -> ((Seq)function9.apply((Object)t.v8)).onEmpty(null).map((T t9) -> t.concat(t9))).flatMap((T t) -> ((Seq)function10.apply((Object)t.v9)).onEmpty(null).map((T t10) -> t.concat(t10))).flatMap((T t) -> ((Seq)function11.apply((Object)t.v10)).onEmpty(null).map((T t11) -> t.concat(t11))).flatMap((T t) -> ((Seq)function12.apply((Object)t.v11)).onEmpty(null).map((T t12) -> t.concat(t12))).flatMap((T t) -> ((Seq)function13.apply((Object)t.v12)).onEmpty(null).map((T t13) -> t.concat(t13))).flatMap((T t) -> ((Seq)function14.apply((Object)t.v13)).onEmpty(null).map((T t14) -> t.concat(t14))).onClose(seq::close);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> Seq<Tuple15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> outerApply(Seq<? extends T1> seq, Function<? super T1, ? extends Seq<? extends T2>> function2, Function<? super T2, ? extends Seq<? extends T3>> function3, Function<? super T3, ? extends Seq<? extends T4>> function4, Function<? super T4, ? extends Seq<? extends T5>> function5, Function<? super T5, ? extends Seq<? extends T6>> function6, Function<? super T6, ? extends Seq<? extends T7>> function7, Function<? super T7, ? extends Seq<? extends T8>> function8, Function<? super T8, ? extends Seq<? extends T9>> function9, Function<? super T9, ? extends Seq<? extends T10>> function10, Function<? super T10, ? extends Seq<? extends T11>> function11, Function<? super T11, ? extends Seq<? extends T12>> function12, Function<? super T12, ? extends Seq<? extends T13>> function13, Function<? super T13, ? extends Seq<? extends T14>> function14, Function<? super T14, ? extends Seq<? extends T15>> function15) {
        return seq.flatMap((T t1) -> ((Seq)function2.apply((Object)t1)).onEmpty(null).map((T t2) -> Tuple.tuple(t1, t2))).flatMap((T t) -> ((Seq)function3.apply((Object)t.v2)).onEmpty(null).map((T t3) -> t.concat(t3))).flatMap((T t) -> ((Seq)function4.apply((Object)t.v3)).onEmpty(null).map((T t4) -> t.concat(t4))).flatMap((T t) -> ((Seq)function5.apply((Object)t.v4)).onEmpty(null).map((T t5) -> t.concat(t5))).flatMap((T t) -> ((Seq)function6.apply((Object)t.v5)).onEmpty(null).map((T t6) -> t.concat(t6))).flatMap((T t) -> ((Seq)function7.apply((Object)t.v6)).onEmpty(null).map((T t7) -> t.concat(t7))).flatMap((T t) -> ((Seq)function8.apply((Object)t.v7)).onEmpty(null).map((T t8) -> t.concat(t8))).flatMap((T t) -> ((Seq)function9.apply((Object)t.v8)).onEmpty(null).map((T t9) -> t.concat(t9))).flatMap((T t) -> ((Seq)function10.apply((Object)t.v9)).onEmpty(null).map((T t10) -> t.concat(t10))).flatMap((T t) -> ((Seq)function11.apply((Object)t.v10)).onEmpty(null).map((T t11) -> t.concat(t11))).flatMap((T t) -> ((Seq)function12.apply((Object)t.v11)).onEmpty(null).map((T t12) -> t.concat(t12))).flatMap((T t) -> ((Seq)function13.apply((Object)t.v12)).onEmpty(null).map((T t13) -> t.concat(t13))).flatMap((T t) -> ((Seq)function14.apply((Object)t.v13)).onEmpty(null).map((T t14) -> t.concat(t14))).flatMap((T t) -> ((Seq)function15.apply((Object)t.v14)).onEmpty(null).map((T t15) -> t.concat(t15))).onClose(seq::close);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> Seq<Tuple16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> outerApply(Seq<? extends T1> seq, Function<? super T1, ? extends Seq<? extends T2>> function2, Function<? super T2, ? extends Seq<? extends T3>> function3, Function<? super T3, ? extends Seq<? extends T4>> function4, Function<? super T4, ? extends Seq<? extends T5>> function5, Function<? super T5, ? extends Seq<? extends T6>> function6, Function<? super T6, ? extends Seq<? extends T7>> function7, Function<? super T7, ? extends Seq<? extends T8>> function8, Function<? super T8, ? extends Seq<? extends T9>> function9, Function<? super T9, ? extends Seq<? extends T10>> function10, Function<? super T10, ? extends Seq<? extends T11>> function11, Function<? super T11, ? extends Seq<? extends T12>> function12, Function<? super T12, ? extends Seq<? extends T13>> function13, Function<? super T13, ? extends Seq<? extends T14>> function14, Function<? super T14, ? extends Seq<? extends T15>> function15, Function<? super T15, ? extends Seq<? extends T16>> function16) {
        return seq.flatMap((T t1) -> ((Seq)function2.apply((Object)t1)).onEmpty(null).map((T t2) -> Tuple.tuple(t1, t2))).flatMap((T t) -> ((Seq)function3.apply((Object)t.v2)).onEmpty(null).map((T t3) -> t.concat(t3))).flatMap((T t) -> ((Seq)function4.apply((Object)t.v3)).onEmpty(null).map((T t4) -> t.concat(t4))).flatMap((T t) -> ((Seq)function5.apply((Object)t.v4)).onEmpty(null).map((T t5) -> t.concat(t5))).flatMap((T t) -> ((Seq)function6.apply((Object)t.v5)).onEmpty(null).map((T t6) -> t.concat(t6))).flatMap((T t) -> ((Seq)function7.apply((Object)t.v6)).onEmpty(null).map((T t7) -> t.concat(t7))).flatMap((T t) -> ((Seq)function8.apply((Object)t.v7)).onEmpty(null).map((T t8) -> t.concat(t8))).flatMap((T t) -> ((Seq)function9.apply((Object)t.v8)).onEmpty(null).map((T t9) -> t.concat(t9))).flatMap((T t) -> ((Seq)function10.apply((Object)t.v9)).onEmpty(null).map((T t10) -> t.concat(t10))).flatMap((T t) -> ((Seq)function11.apply((Object)t.v10)).onEmpty(null).map((T t11) -> t.concat(t11))).flatMap((T t) -> ((Seq)function12.apply((Object)t.v11)).onEmpty(null).map((T t12) -> t.concat(t12))).flatMap((T t) -> ((Seq)function13.apply((Object)t.v12)).onEmpty(null).map((T t13) -> t.concat(t13))).flatMap((T t) -> ((Seq)function14.apply((Object)t.v13)).onEmpty(null).map((T t14) -> t.concat(t14))).flatMap((T t) -> ((Seq)function15.apply((Object)t.v14)).onEmpty(null).map((T t15) -> t.concat(t15))).flatMap((T t) -> ((Seq)function16.apply((Object)t.v15)).onEmpty(null).map((T t16) -> t.concat(t16))).onClose(seq::close);
    }

    public static <T1, T2> Seq<Tuple2<T1, T2>> crossJoin(Stream<? extends T1> s1, Stream<? extends T2> s2) {
        return Seq.crossJoin(Seq.seq(s1), Seq.seq(s2));
    }

    public static <T1, T2, T3> Seq<Tuple3<T1, T2, T3>> crossJoin(Stream<? extends T1> s1, Stream<? extends T2> s2, Stream<? extends T3> s3) {
        return Seq.crossJoin(Seq.seq(s1), Seq.seq(s2), Seq.seq(s3));
    }

    public static <T1, T2, T3, T4> Seq<Tuple4<T1, T2, T3, T4>> crossJoin(Stream<? extends T1> s1, Stream<? extends T2> s2, Stream<? extends T3> s3, Stream<? extends T4> s4) {
        return Seq.crossJoin(Seq.seq(s1), Seq.seq(s2), Seq.seq(s3), Seq.seq(s4));
    }

    public static <T1, T2, T3, T4, T5> Seq<Tuple5<T1, T2, T3, T4, T5>> crossJoin(Stream<? extends T1> s1, Stream<? extends T2> s2, Stream<? extends T3> s3, Stream<? extends T4> s4, Stream<? extends T5> s5) {
        return Seq.crossJoin(Seq.seq(s1), Seq.seq(s2), Seq.seq(s3), Seq.seq(s4), Seq.seq(s5));
    }

    public static <T1, T2, T3, T4, T5, T6> Seq<Tuple6<T1, T2, T3, T4, T5, T6>> crossJoin(Stream<? extends T1> s1, Stream<? extends T2> s2, Stream<? extends T3> s3, Stream<? extends T4> s4, Stream<? extends T5> s5, Stream<? extends T6> s6) {
        return Seq.crossJoin(Seq.seq(s1), Seq.seq(s2), Seq.seq(s3), Seq.seq(s4), Seq.seq(s5), Seq.seq(s6));
    }

    public static <T1, T2, T3, T4, T5, T6, T7> Seq<Tuple7<T1, T2, T3, T4, T5, T6, T7>> crossJoin(Stream<? extends T1> s1, Stream<? extends T2> s2, Stream<? extends T3> s3, Stream<? extends T4> s4, Stream<? extends T5> s5, Stream<? extends T6> s6, Stream<? extends T7> s7) {
        return Seq.crossJoin(Seq.seq(s1), Seq.seq(s2), Seq.seq(s3), Seq.seq(s4), Seq.seq(s5), Seq.seq(s6), Seq.seq(s7));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8> Seq<Tuple8<T1, T2, T3, T4, T5, T6, T7, T8>> crossJoin(Stream<? extends T1> s1, Stream<? extends T2> s2, Stream<? extends T3> s3, Stream<? extends T4> s4, Stream<? extends T5> s5, Stream<? extends T6> s6, Stream<? extends T7> s7, Stream<? extends T8> s8) {
        return Seq.crossJoin(Seq.seq(s1), Seq.seq(s2), Seq.seq(s3), Seq.seq(s4), Seq.seq(s5), Seq.seq(s6), Seq.seq(s7), Seq.seq(s8));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9> Seq<Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> crossJoin(Stream<? extends T1> s1, Stream<? extends T2> s2, Stream<? extends T3> s3, Stream<? extends T4> s4, Stream<? extends T5> s5, Stream<? extends T6> s6, Stream<? extends T7> s7, Stream<? extends T8> s8, Stream<? extends T9> s9) {
        return Seq.crossJoin(Seq.seq(s1), Seq.seq(s2), Seq.seq(s3), Seq.seq(s4), Seq.seq(s5), Seq.seq(s6), Seq.seq(s7), Seq.seq(s8), Seq.seq(s9));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> Seq<Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> crossJoin(Stream<? extends T1> s1, Stream<? extends T2> s2, Stream<? extends T3> s3, Stream<? extends T4> s4, Stream<? extends T5> s5, Stream<? extends T6> s6, Stream<? extends T7> s7, Stream<? extends T8> s8, Stream<? extends T9> s9, Stream<? extends T10> s10) {
        return Seq.crossJoin(Seq.seq(s1), Seq.seq(s2), Seq.seq(s3), Seq.seq(s4), Seq.seq(s5), Seq.seq(s6), Seq.seq(s7), Seq.seq(s8), Seq.seq(s9), Seq.seq(s10));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> Seq<Tuple11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> crossJoin(Stream<? extends T1> s1, Stream<? extends T2> s2, Stream<? extends T3> s3, Stream<? extends T4> s4, Stream<? extends T5> s5, Stream<? extends T6> s6, Stream<? extends T7> s7, Stream<? extends T8> s8, Stream<? extends T9> s9, Stream<? extends T10> s10, Stream<? extends T11> s11) {
        return Seq.crossJoin(Seq.seq(s1), Seq.seq(s2), Seq.seq(s3), Seq.seq(s4), Seq.seq(s5), Seq.seq(s6), Seq.seq(s7), Seq.seq(s8), Seq.seq(s9), Seq.seq(s10), Seq.seq(s11));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> Seq<Tuple12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>> crossJoin(Stream<? extends T1> s1, Stream<? extends T2> s2, Stream<? extends T3> s3, Stream<? extends T4> s4, Stream<? extends T5> s5, Stream<? extends T6> s6, Stream<? extends T7> s7, Stream<? extends T8> s8, Stream<? extends T9> s9, Stream<? extends T10> s10, Stream<? extends T11> s11, Stream<? extends T12> s12) {
        return Seq.crossJoin(Seq.seq(s1), Seq.seq(s2), Seq.seq(s3), Seq.seq(s4), Seq.seq(s5), Seq.seq(s6), Seq.seq(s7), Seq.seq(s8), Seq.seq(s9), Seq.seq(s10), Seq.seq(s11), Seq.seq(s12));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> Seq<Tuple13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> crossJoin(Stream<? extends T1> s1, Stream<? extends T2> s2, Stream<? extends T3> s3, Stream<? extends T4> s4, Stream<? extends T5> s5, Stream<? extends T6> s6, Stream<? extends T7> s7, Stream<? extends T8> s8, Stream<? extends T9> s9, Stream<? extends T10> s10, Stream<? extends T11> s11, Stream<? extends T12> s12, Stream<? extends T13> s13) {
        return Seq.crossJoin(Seq.seq(s1), Seq.seq(s2), Seq.seq(s3), Seq.seq(s4), Seq.seq(s5), Seq.seq(s6), Seq.seq(s7), Seq.seq(s8), Seq.seq(s9), Seq.seq(s10), Seq.seq(s11), Seq.seq(s12), Seq.seq(s13));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> Seq<Tuple14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> crossJoin(Stream<? extends T1> s1, Stream<? extends T2> s2, Stream<? extends T3> s3, Stream<? extends T4> s4, Stream<? extends T5> s5, Stream<? extends T6> s6, Stream<? extends T7> s7, Stream<? extends T8> s8, Stream<? extends T9> s9, Stream<? extends T10> s10, Stream<? extends T11> s11, Stream<? extends T12> s12, Stream<? extends T13> s13, Stream<? extends T14> s14) {
        return Seq.crossJoin(Seq.seq(s1), Seq.seq(s2), Seq.seq(s3), Seq.seq(s4), Seq.seq(s5), Seq.seq(s6), Seq.seq(s7), Seq.seq(s8), Seq.seq(s9), Seq.seq(s10), Seq.seq(s11), Seq.seq(s12), Seq.seq(s13), Seq.seq(s14));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> Seq<Tuple15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> crossJoin(Stream<? extends T1> s1, Stream<? extends T2> s2, Stream<? extends T3> s3, Stream<? extends T4> s4, Stream<? extends T5> s5, Stream<? extends T6> s6, Stream<? extends T7> s7, Stream<? extends T8> s8, Stream<? extends T9> s9, Stream<? extends T10> s10, Stream<? extends T11> s11, Stream<? extends T12> s12, Stream<? extends T13> s13, Stream<? extends T14> s14, Stream<? extends T15> s15) {
        return Seq.crossJoin(Seq.seq(s1), Seq.seq(s2), Seq.seq(s3), Seq.seq(s4), Seq.seq(s5), Seq.seq(s6), Seq.seq(s7), Seq.seq(s8), Seq.seq(s9), Seq.seq(s10), Seq.seq(s11), Seq.seq(s12), Seq.seq(s13), Seq.seq(s14), Seq.seq(s15));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> Seq<Tuple16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> crossJoin(Stream<? extends T1> s1, Stream<? extends T2> s2, Stream<? extends T3> s3, Stream<? extends T4> s4, Stream<? extends T5> s5, Stream<? extends T6> s6, Stream<? extends T7> s7, Stream<? extends T8> s8, Stream<? extends T9> s9, Stream<? extends T10> s10, Stream<? extends T11> s11, Stream<? extends T12> s12, Stream<? extends T13> s13, Stream<? extends T14> s14, Stream<? extends T15> s15, Stream<? extends T16> s16) {
        return Seq.crossJoin(Seq.seq(s1), Seq.seq(s2), Seq.seq(s3), Seq.seq(s4), Seq.seq(s5), Seq.seq(s6), Seq.seq(s7), Seq.seq(s8), Seq.seq(s9), Seq.seq(s10), Seq.seq(s11), Seq.seq(s12), Seq.seq(s13), Seq.seq(s14), Seq.seq(s15), Seq.seq(s16));
    }

    public static <T1, T2> Seq<Tuple2<T1, T2>> crossJoin(Iterable<? extends T1> i1, Iterable<? extends T2> i2) {
        return Seq.crossJoin(Seq.seq(i1), Seq.seq(i2));
    }

    public static <T1, T2, T3> Seq<Tuple3<T1, T2, T3>> crossJoin(Iterable<? extends T1> i1, Iterable<? extends T2> i2, Iterable<? extends T3> i3) {
        return Seq.crossJoin(Seq.seq(i1), Seq.seq(i2), Seq.seq(i3));
    }

    public static <T1, T2, T3, T4> Seq<Tuple4<T1, T2, T3, T4>> crossJoin(Iterable<? extends T1> i1, Iterable<? extends T2> i2, Iterable<? extends T3> i3, Iterable<? extends T4> i4) {
        return Seq.crossJoin(Seq.seq(i1), Seq.seq(i2), Seq.seq(i3), Seq.seq(i4));
    }

    public static <T1, T2, T3, T4, T5> Seq<Tuple5<T1, T2, T3, T4, T5>> crossJoin(Iterable<? extends T1> i1, Iterable<? extends T2> i2, Iterable<? extends T3> i3, Iterable<? extends T4> i4, Iterable<? extends T5> i5) {
        return Seq.crossJoin(Seq.seq(i1), Seq.seq(i2), Seq.seq(i3), Seq.seq(i4), Seq.seq(i5));
    }

    public static <T1, T2, T3, T4, T5, T6> Seq<Tuple6<T1, T2, T3, T4, T5, T6>> crossJoin(Iterable<? extends T1> i1, Iterable<? extends T2> i2, Iterable<? extends T3> i3, Iterable<? extends T4> i4, Iterable<? extends T5> i5, Iterable<? extends T6> i6) {
        return Seq.crossJoin(Seq.seq(i1), Seq.seq(i2), Seq.seq(i3), Seq.seq(i4), Seq.seq(i5), Seq.seq(i6));
    }

    public static <T1, T2, T3, T4, T5, T6, T7> Seq<Tuple7<T1, T2, T3, T4, T5, T6, T7>> crossJoin(Iterable<? extends T1> i1, Iterable<? extends T2> i2, Iterable<? extends T3> i3, Iterable<? extends T4> i4, Iterable<? extends T5> i5, Iterable<? extends T6> i6, Iterable<? extends T7> i7) {
        return Seq.crossJoin(Seq.seq(i1), Seq.seq(i2), Seq.seq(i3), Seq.seq(i4), Seq.seq(i5), Seq.seq(i6), Seq.seq(i7));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8> Seq<Tuple8<T1, T2, T3, T4, T5, T6, T7, T8>> crossJoin(Iterable<? extends T1> i1, Iterable<? extends T2> i2, Iterable<? extends T3> i3, Iterable<? extends T4> i4, Iterable<? extends T5> i5, Iterable<? extends T6> i6, Iterable<? extends T7> i7, Iterable<? extends T8> i8) {
        return Seq.crossJoin(Seq.seq(i1), Seq.seq(i2), Seq.seq(i3), Seq.seq(i4), Seq.seq(i5), Seq.seq(i6), Seq.seq(i7), Seq.seq(i8));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9> Seq<Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> crossJoin(Iterable<? extends T1> i1, Iterable<? extends T2> i2, Iterable<? extends T3> i3, Iterable<? extends T4> i4, Iterable<? extends T5> i5, Iterable<? extends T6> i6, Iterable<? extends T7> i7, Iterable<? extends T8> i8, Iterable<? extends T9> i9) {
        return Seq.crossJoin(Seq.seq(i1), Seq.seq(i2), Seq.seq(i3), Seq.seq(i4), Seq.seq(i5), Seq.seq(i6), Seq.seq(i7), Seq.seq(i8), Seq.seq(i9));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> Seq<Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> crossJoin(Iterable<? extends T1> i1, Iterable<? extends T2> i2, Iterable<? extends T3> i3, Iterable<? extends T4> i4, Iterable<? extends T5> i5, Iterable<? extends T6> i6, Iterable<? extends T7> i7, Iterable<? extends T8> i8, Iterable<? extends T9> i9, Iterable<? extends T10> i10) {
        return Seq.crossJoin(Seq.seq(i1), Seq.seq(i2), Seq.seq(i3), Seq.seq(i4), Seq.seq(i5), Seq.seq(i6), Seq.seq(i7), Seq.seq(i8), Seq.seq(i9), Seq.seq(i10));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> Seq<Tuple11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> crossJoin(Iterable<? extends T1> i1, Iterable<? extends T2> i2, Iterable<? extends T3> i3, Iterable<? extends T4> i4, Iterable<? extends T5> i5, Iterable<? extends T6> i6, Iterable<? extends T7> i7, Iterable<? extends T8> i8, Iterable<? extends T9> i9, Iterable<? extends T10> i10, Iterable<? extends T11> i11) {
        return Seq.crossJoin(Seq.seq(i1), Seq.seq(i2), Seq.seq(i3), Seq.seq(i4), Seq.seq(i5), Seq.seq(i6), Seq.seq(i7), Seq.seq(i8), Seq.seq(i9), Seq.seq(i10), Seq.seq(i11));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> Seq<Tuple12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>> crossJoin(Iterable<? extends T1> i1, Iterable<? extends T2> i2, Iterable<? extends T3> i3, Iterable<? extends T4> i4, Iterable<? extends T5> i5, Iterable<? extends T6> i6, Iterable<? extends T7> i7, Iterable<? extends T8> i8, Iterable<? extends T9> i9, Iterable<? extends T10> i10, Iterable<? extends T11> i11, Iterable<? extends T12> i12) {
        return Seq.crossJoin(Seq.seq(i1), Seq.seq(i2), Seq.seq(i3), Seq.seq(i4), Seq.seq(i5), Seq.seq(i6), Seq.seq(i7), Seq.seq(i8), Seq.seq(i9), Seq.seq(i10), Seq.seq(i11), Seq.seq(i12));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> Seq<Tuple13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> crossJoin(Iterable<? extends T1> i1, Iterable<? extends T2> i2, Iterable<? extends T3> i3, Iterable<? extends T4> i4, Iterable<? extends T5> i5, Iterable<? extends T6> i6, Iterable<? extends T7> i7, Iterable<? extends T8> i8, Iterable<? extends T9> i9, Iterable<? extends T10> i10, Iterable<? extends T11> i11, Iterable<? extends T12> i12, Iterable<? extends T13> i13) {
        return Seq.crossJoin(Seq.seq(i1), Seq.seq(i2), Seq.seq(i3), Seq.seq(i4), Seq.seq(i5), Seq.seq(i6), Seq.seq(i7), Seq.seq(i8), Seq.seq(i9), Seq.seq(i10), Seq.seq(i11), Seq.seq(i12), Seq.seq(i13));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> Seq<Tuple14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> crossJoin(Iterable<? extends T1> i1, Iterable<? extends T2> i2, Iterable<? extends T3> i3, Iterable<? extends T4> i4, Iterable<? extends T5> i5, Iterable<? extends T6> i6, Iterable<? extends T7> i7, Iterable<? extends T8> i8, Iterable<? extends T9> i9, Iterable<? extends T10> i10, Iterable<? extends T11> i11, Iterable<? extends T12> i12, Iterable<? extends T13> i13, Iterable<? extends T14> i14) {
        return Seq.crossJoin(Seq.seq(i1), Seq.seq(i2), Seq.seq(i3), Seq.seq(i4), Seq.seq(i5), Seq.seq(i6), Seq.seq(i7), Seq.seq(i8), Seq.seq(i9), Seq.seq(i10), Seq.seq(i11), Seq.seq(i12), Seq.seq(i13), Seq.seq(i14));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> Seq<Tuple15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> crossJoin(Iterable<? extends T1> i1, Iterable<? extends T2> i2, Iterable<? extends T3> i3, Iterable<? extends T4> i4, Iterable<? extends T5> i5, Iterable<? extends T6> i6, Iterable<? extends T7> i7, Iterable<? extends T8> i8, Iterable<? extends T9> i9, Iterable<? extends T10> i10, Iterable<? extends T11> i11, Iterable<? extends T12> i12, Iterable<? extends T13> i13, Iterable<? extends T14> i14, Iterable<? extends T15> i15) {
        return Seq.crossJoin(Seq.seq(i1), Seq.seq(i2), Seq.seq(i3), Seq.seq(i4), Seq.seq(i5), Seq.seq(i6), Seq.seq(i7), Seq.seq(i8), Seq.seq(i9), Seq.seq(i10), Seq.seq(i11), Seq.seq(i12), Seq.seq(i13), Seq.seq(i14), Seq.seq(i15));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> Seq<Tuple16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> crossJoin(Iterable<? extends T1> i1, Iterable<? extends T2> i2, Iterable<? extends T3> i3, Iterable<? extends T4> i4, Iterable<? extends T5> i5, Iterable<? extends T6> i6, Iterable<? extends T7> i7, Iterable<? extends T8> i8, Iterable<? extends T9> i9, Iterable<? extends T10> i10, Iterable<? extends T11> i11, Iterable<? extends T12> i12, Iterable<? extends T13> i13, Iterable<? extends T14> i14, Iterable<? extends T15> i15, Iterable<? extends T16> i16) {
        return Seq.crossJoin(Seq.seq(i1), Seq.seq(i2), Seq.seq(i3), Seq.seq(i4), Seq.seq(i5), Seq.seq(i6), Seq.seq(i7), Seq.seq(i8), Seq.seq(i9), Seq.seq(i10), Seq.seq(i11), Seq.seq(i12), Seq.seq(i13), Seq.seq(i14), Seq.seq(i15), Seq.seq(i16));
    }

    public static <T1, T2> Seq<Tuple2<T1, T2>> crossJoin(Seq<? extends T1> s1, Seq<? extends T2> s2) {
        List list = s2.toList();
        return Seq.seq(s1).flatMap((T v1) -> Seq.seq(list).map(v2 -> Tuple.tuple(v1, v2))).onClose(SeqUtils.closeAll(s1, s2));
    }

    public static <T1, T2, T3> Seq<Tuple3<T1, T2, T3>> crossJoin(Seq<? extends T1> s1, Seq<? extends T2> s2, Seq<? extends T3> s3) {
        List list = Seq.crossJoin(s2, s3).toList();
        return s1.flatMap((T v1) -> Seq.seq(list).map(t -> Tuple.tuple(v1, t.v1, t.v2))).onClose(SeqUtils.closeAll(s2, s3));
    }

    public static <T1, T2, T3, T4> Seq<Tuple4<T1, T2, T3, T4>> crossJoin(Seq<? extends T1> s1, Seq<? extends T2> s2, Seq<? extends T3> s3, Seq<? extends T4> s4) {
        List list = Seq.crossJoin(s2, s3, s4).toList();
        return s1.flatMap((T v1) -> Seq.seq(list).map(t -> Tuple.tuple(v1, t.v1, t.v2, t.v3))).onClose(SeqUtils.closeAll(s2, s3, s4));
    }

    public static <T1, T2, T3, T4, T5> Seq<Tuple5<T1, T2, T3, T4, T5>> crossJoin(Seq<? extends T1> s1, Seq<? extends T2> s2, Seq<? extends T3> s3, Seq<? extends T4> s4, Seq<? extends T5> s5) {
        List list = Seq.crossJoin(s2, s3, s4, s5).toList();
        return s1.flatMap((T v1) -> Seq.seq(list).map(t -> Tuple.tuple(v1, t.v1, t.v2, t.v3, t.v4))).onClose(SeqUtils.closeAll(s2, s3, s4, s5));
    }

    public static <T1, T2, T3, T4, T5, T6> Seq<Tuple6<T1, T2, T3, T4, T5, T6>> crossJoin(Seq<? extends T1> s1, Seq<? extends T2> s2, Seq<? extends T3> s3, Seq<? extends T4> s4, Seq<? extends T5> s5, Seq<? extends T6> s6) {
        List list = Seq.crossJoin(s2, s3, s4, s5, s6).toList();
        return s1.flatMap((T v1) -> Seq.seq(list).map(t -> Tuple.tuple(v1, t.v1, t.v2, t.v3, t.v4, t.v5))).onClose(SeqUtils.closeAll(s2, s3, s4, s5, s6));
    }

    public static <T1, T2, T3, T4, T5, T6, T7> Seq<Tuple7<T1, T2, T3, T4, T5, T6, T7>> crossJoin(Seq<? extends T1> s1, Seq<? extends T2> s2, Seq<? extends T3> s3, Seq<? extends T4> s4, Seq<? extends T5> s5, Seq<? extends T6> s6, Seq<? extends T7> s7) {
        List list = Seq.crossJoin(s2, s3, s4, s5, s6, s7).toList();
        return s1.flatMap((T v1) -> Seq.seq(list).map(t -> Tuple.tuple(v1, t.v1, t.v2, t.v3, t.v4, t.v5, t.v6))).onClose(SeqUtils.closeAll(s2, s3, s4, s5, s6, s7));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8> Seq<Tuple8<T1, T2, T3, T4, T5, T6, T7, T8>> crossJoin(Seq<? extends T1> s1, Seq<? extends T2> s2, Seq<? extends T3> s3, Seq<? extends T4> s4, Seq<? extends T5> s5, Seq<? extends T6> s6, Seq<? extends T7> s7, Seq<? extends T8> s8) {
        List list = Seq.crossJoin(s2, s3, s4, s5, s6, s7, s8).toList();
        return s1.flatMap((T v1) -> Seq.seq(list).map(t -> Tuple.tuple(v1, t.v1, t.v2, t.v3, t.v4, t.v5, t.v6, t.v7))).onClose(SeqUtils.closeAll(s2, s3, s4, s5, s6, s7, s8));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9> Seq<Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> crossJoin(Seq<? extends T1> s1, Seq<? extends T2> s2, Seq<? extends T3> s3, Seq<? extends T4> s4, Seq<? extends T5> s5, Seq<? extends T6> s6, Seq<? extends T7> s7, Seq<? extends T8> s8, Seq<? extends T9> s9) {
        List list = Seq.crossJoin(s2, s3, s4, s5, s6, s7, s8, s9).toList();
        return s1.flatMap((T v1) -> Seq.seq(list).map(t -> Tuple.tuple(v1, t.v1, t.v2, t.v3, t.v4, t.v5, t.v6, t.v7, t.v8))).onClose(SeqUtils.closeAll(s2, s3, s4, s5, s6, s7, s8, s9));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> Seq<Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> crossJoin(Seq<? extends T1> s1, Seq<? extends T2> s2, Seq<? extends T3> s3, Seq<? extends T4> s4, Seq<? extends T5> s5, Seq<? extends T6> s6, Seq<? extends T7> s7, Seq<? extends T8> s8, Seq<? extends T9> s9, Seq<? extends T10> s10) {
        List list = Seq.crossJoin(s2, s3, s4, s5, s6, s7, s8, s9, s10).toList();
        return s1.flatMap((T v1) -> Seq.seq(list).map(t -> Tuple.tuple(v1, t.v1, t.v2, t.v3, t.v4, t.v5, t.v6, t.v7, t.v8, t.v9))).onClose(SeqUtils.closeAll(s2, s3, s4, s5, s6, s7, s8, s9, s10));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> Seq<Tuple11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> crossJoin(Seq<? extends T1> s1, Seq<? extends T2> s2, Seq<? extends T3> s3, Seq<? extends T4> s4, Seq<? extends T5> s5, Seq<? extends T6> s6, Seq<? extends T7> s7, Seq<? extends T8> s8, Seq<? extends T9> s9, Seq<? extends T10> s10, Seq<? extends T11> s11) {
        List list = Seq.crossJoin(s2, s3, s4, s5, s6, s7, s8, s9, s10, s11).toList();
        return s1.flatMap((T v1) -> Seq.seq(list).map(t -> Tuple.tuple(v1, t.v1, t.v2, t.v3, t.v4, t.v5, t.v6, t.v7, t.v8, t.v9, t.v10))).onClose(SeqUtils.closeAll(s2, s3, s4, s5, s6, s7, s8, s9, s10, s11));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> Seq<Tuple12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>> crossJoin(Seq<? extends T1> s1, Seq<? extends T2> s2, Seq<? extends T3> s3, Seq<? extends T4> s4, Seq<? extends T5> s5, Seq<? extends T6> s6, Seq<? extends T7> s7, Seq<? extends T8> s8, Seq<? extends T9> s9, Seq<? extends T10> s10, Seq<? extends T11> s11, Seq<? extends T12> s12) {
        List list = Seq.crossJoin(s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, s12).toList();
        return s1.flatMap((T v1) -> Seq.seq(list).map(t -> Tuple.tuple(v1, t.v1, t.v2, t.v3, t.v4, t.v5, t.v6, t.v7, t.v8, t.v9, t.v10, t.v11))).onClose(SeqUtils.closeAll(s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, s12));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> Seq<Tuple13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> crossJoin(Seq<? extends T1> s1, Seq<? extends T2> s2, Seq<? extends T3> s3, Seq<? extends T4> s4, Seq<? extends T5> s5, Seq<? extends T6> s6, Seq<? extends T7> s7, Seq<? extends T8> s8, Seq<? extends T9> s9, Seq<? extends T10> s10, Seq<? extends T11> s11, Seq<? extends T12> s12, Seq<? extends T13> s13) {
        List list = Seq.crossJoin(s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, s12, s13).toList();
        return s1.flatMap((T v1) -> Seq.seq(list).map(t -> Tuple.tuple(v1, t.v1, t.v2, t.v3, t.v4, t.v5, t.v6, t.v7, t.v8, t.v9, t.v10, t.v11, t.v12))).onClose(SeqUtils.closeAll(s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, s12, s13));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> Seq<Tuple14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> crossJoin(Seq<? extends T1> s1, Seq<? extends T2> s2, Seq<? extends T3> s3, Seq<? extends T4> s4, Seq<? extends T5> s5, Seq<? extends T6> s6, Seq<? extends T7> s7, Seq<? extends T8> s8, Seq<? extends T9> s9, Seq<? extends T10> s10, Seq<? extends T11> s11, Seq<? extends T12> s12, Seq<? extends T13> s13, Seq<? extends T14> s14) {
        List list = Seq.crossJoin(s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, s12, s13, s14).toList();
        return s1.flatMap((T v1) -> Seq.seq(list).map(t -> Tuple.tuple(v1, t.v1, t.v2, t.v3, t.v4, t.v5, t.v6, t.v7, t.v8, t.v9, t.v10, t.v11, t.v12, t.v13))).onClose(SeqUtils.closeAll(s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, s12, s13, s14));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> Seq<Tuple15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> crossJoin(Seq<? extends T1> s1, Seq<? extends T2> s2, Seq<? extends T3> s3, Seq<? extends T4> s4, Seq<? extends T5> s5, Seq<? extends T6> s6, Seq<? extends T7> s7, Seq<? extends T8> s8, Seq<? extends T9> s9, Seq<? extends T10> s10, Seq<? extends T11> s11, Seq<? extends T12> s12, Seq<? extends T13> s13, Seq<? extends T14> s14, Seq<? extends T15> s15) {
        List list = Seq.crossJoin(s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, s12, s13, s14, s15).toList();
        return s1.flatMap((T v1) -> Seq.seq(list).map(t -> Tuple.tuple(v1, t.v1, t.v2, t.v3, t.v4, t.v5, t.v6, t.v7, t.v8, t.v9, t.v10, t.v11, t.v12, t.v13, t.v14))).onClose(SeqUtils.closeAll(s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, s12, s13, s14, s15));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> Seq<Tuple16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> crossJoin(Seq<? extends T1> s1, Seq<? extends T2> s2, Seq<? extends T3> s3, Seq<? extends T4> s4, Seq<? extends T5> s5, Seq<? extends T6> s6, Seq<? extends T7> s7, Seq<? extends T8> s8, Seq<? extends T9> s9, Seq<? extends T10> s10, Seq<? extends T11> s11, Seq<? extends T12> s12, Seq<? extends T13> s13, Seq<? extends T14> s14, Seq<? extends T15> s15, Seq<? extends T16> s16) {
        List list = Seq.crossJoin(s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, s12, s13, s14, s15, s16).toList();
        return s1.flatMap((T v1) -> Seq.seq(list).map(t -> Tuple.tuple(v1, t.v1, t.v2, t.v3, t.v4, t.v5, t.v6, t.v7, t.v8, t.v9, t.v10, t.v11, t.v12, t.v13, t.v14, t.v15))).onClose(SeqUtils.closeAll(s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, s12, s13, s14, s15, s16));
    }

    @SafeVarargs
    public static <T> Seq<T> concat(Stream<? extends T> ... streams) {
        return Seq.concat(SeqUtils.seqs(streams));
    }

    @SafeVarargs
    public static <T> Seq<T> concat(Iterable<? extends T> ... iterables) {
        return Seq.concat(SeqUtils.seqs(iterables));
    }

    @SafeVarargs
    public static <T> Seq<T> concat(Seq<? extends T> ... streams) {
        if (streams == null || streams.length == 0) {
            return Seq.empty();
        }
        if (streams.length == 1) {
            return Seq.seq(streams[0]);
        }
        Stream<? extends T> result = streams[0];
        for (int i = 1; i < streams.length; ++i) {
            result = Stream.concat(result, streams[i]);
        }
        return Seq.seq(result);
    }

    @SafeVarargs
    public static <T> Seq<T> concat(Optional<? extends T> ... optionals) {
        if (optionals == null) {
            return null;
        }
        return Seq.of(optionals).filter(Optional::isPresent).map(Optional::get);
    }

    public static <T> Tuple2<Seq<T>, Seq<T>> duplicate(Stream<? extends T> stream) {
        SeqBuffer<T> buffer = SeqBuffer.of(stream);
        return Tuple.tuple(buffer.seq(), buffer.seq());
    }

    public static String toString(Stream<?> stream) {
        return Seq.toString(stream, "");
    }

    public static String toString(Stream<?> stream, CharSequence delimiter) {
        return stream.map(Objects::toString).collect(Collectors.joining(delimiter));
    }

    public static <T, C extends Collection<T>> C toCollection(Stream<? extends T> stream, Supplier<? extends C> collectionFactory) {
        return (C)((Collection)stream.collect(Collectors.toCollection(collectionFactory)));
    }

    public static <T> List<T> toList(Stream<? extends T> stream) {
        return stream.collect(Collectors.toList());
    }

    public static <T> Set<T> toSet(Stream<? extends T> stream) {
        return stream.collect(Collectors.toSet());
    }

    public static <T, K, V> Map<K, V> toMap(Stream<Tuple2<K, V>> stream) {
        return stream.collect(Collectors.toMap(Tuple2::v1, Tuple2::v2));
    }

    public static <T, K, V> Map<K, V> toMap(Stream<? extends T> stream, Function<? super T, ? extends K> keyMapper, Function<? super T, ? extends V> valueMapper) {
        return stream.collect(Collectors.toMap(keyMapper, valueMapper));
    }

    public static <T> Seq<T> slice(Stream<? extends T> stream, long from, long to) {
        long f = Math.max(from, 0L);
        long t = Math.max(to - f, 0L);
        return Seq.seq(stream.skip(f).limit(t));
    }

    public static <T> Seq<T> skip(Stream<? extends T> stream, long elements) {
        return Seq.seq(stream.skip(elements));
    }

    public static <T> Seq<T> skipWhile(Stream<? extends T> stream, Predicate<? super T> predicate) {
        return Seq.skipUntil(stream, predicate.negate());
    }

    public static <T> Seq<T> skipWhileClosed(Stream<? extends T> stream, Predicate<? super T> predicate) {
        return Seq.skipUntilClosed(stream, predicate.negate());
    }

    public static <T> Seq<T> skipUntil(Stream<? extends T> stream, Predicate<? super T> predicate) {
        boolean[] test = new boolean[]{false, false};
        return SeqUtils.transform(stream, (delegate, action) -> {
            if (test[0]) {
                return delegate.tryAdvance(action);
            }
            do {
                test[1] = delegate.tryAdvance(t -> {
                    test[0] = predicate.test(t);
                    if (test[0]) {
                        action.accept(t);
                    }
                });
            } while (test[1] && !test[0]);
            return test[0];
        });
    }

    public static <T> Seq<T> skipUntilClosed(Stream<? extends T> stream, Predicate<? super T> predicate) {
        boolean[] test = new boolean[]{false, false};
        return SeqUtils.transform(stream, (delegate, action) -> {
            if (!test[0]) {
                do {
                    test[1] = delegate.tryAdvance(t -> {
                        test[0] = predicate.test(t);
                    });
                } while (test[1] && !test[0]);
            }
            return test[0] && delegate.tryAdvance(action);
        });
    }

    public static <T> Seq<T> limit(Stream<? extends T> stream, long elements) {
        return Seq.seq(stream.limit(elements));
    }

    default public Seq<T> take(long maxSize) {
        return this.limit(maxSize);
    }

    default public Seq<T> drop(long n) {
        return this.skip(n);
    }

    public static <T> Seq<T> limitWhile(Stream<? extends T> stream, Predicate<? super T> predicate) {
        return Seq.limitUntil(stream, predicate.negate());
    }

    public static <T> Seq<T> limitWhileClosed(Stream<? extends T> stream, Predicate<? super T> predicate) {
        return Seq.limitUntilClosed(stream, predicate.negate());
    }

    public static <T> Seq<T> limitUntil(Stream<? extends T> stream, Predicate<? super T> predicate) {
        boolean[] test = new boolean[]{false};
        return SeqUtils.transform(stream, (delegate, action) -> !test[0] && delegate.tryAdvance(t -> {
            test[0] = predicate.test(t);
            if (!test[0]) {
                action.accept(t);
            }
        }));
    }

    public static <T> Seq<T> limitUntilClosed(Stream<? extends T> stream, Predicate<? super T> predicate) {
        boolean[] test = new boolean[]{false};
        return SeqUtils.transform(stream, (delegate, action) -> !test[0] && delegate.tryAdvance(t -> {
            test[0] = predicate.test(t);
            action.accept(t);
        }));
    }

    public static <T> Seq<T> intersperse(Stream<? extends T> stream, T value) {
        return Seq.seq(stream.flatMap((? super T t) -> Stream.of(value, t)).skip(1L));
    }

    public static <K, T> Seq<Tuple2<K, Seq<T>>> grouped(Stream<? extends T> stream, Function<? super T, ? extends K> classifier) {
        return Seq.grouped(Seq.seq(stream), classifier);
    }

    public static <K, T> Seq<Tuple2<K, Seq<T>>> grouped(Iterable<? extends T> iterable, Function<? super T, ? extends K> classifier) {
        return Seq.grouped(Seq.seq(iterable), classifier);
    }

    public static <K, T> Seq<Tuple2<K, Seq<T>>> grouped(Seq<? extends T> seq, final Function<? super T, ? extends K> classifier) {
        final Iterator it = seq.iterator();
        class ClassifyingIterator
        implements Iterator<Tuple2<K, Seq<T>>> {
            final Map<K, Queue<T>> buffers = new LinkedHashMap();
            final Queue<K> keys = new LinkedList();

            ClassifyingIterator() {
            }

            void fetchClassifying() {
                while (it.hasNext() && this.fetchNextNewKey()) {
                }
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            boolean fetchNextNewKey() {
                Object next = it.next();
                Object nextK = classifier.apply(next);
                Queue buffer = this.buffers.get(nextK);
                try {
                    if (buffer == null) {
                        buffer = new ArrayDeque();
                        this.buffers.put(nextK, buffer);
                        this.keys.add(nextK);
                        boolean bl = false;
                        return bl;
                    }
                }
                finally {
                    buffer.offer(next);
                }
                return true;
            }

            @Override
            public boolean hasNext() {
                this.fetchClassifying();
                return !this.keys.isEmpty();
            }

            @Override
            public Tuple2<K, Seq<T>> next() {
                Object nextK = this.keys.poll();
                return Tuple.tuple(nextK, Seq.seq(new 1ClassifyingIterator.Classification(nextK)));
            }

            class 1ClassifyingIterator.Classification
            implements Iterator<T> {
                final K key;
                Queue<T> buffer;

                1ClassifyingIterator.Classification(K key) {
                    this.key = key;
                }

                void fetchClassification() {
                    if (this.buffer == null) {
                        this.buffer = buffers.get(this.key);
                    }
                    while (this.buffer.isEmpty() && it.hasNext()) {
                        this.fetchNextNewKey();
                    }
                }

                @Override
                public boolean hasNext() {
                    this.fetchClassification();
                    return !this.buffer.isEmpty();
                }

                @Override
                public T next() {
                    return this.buffer.poll();
                }
            }
        }
        return Seq.seq(new ClassifyingIterator()).onClose(seq::close);
    }

    public static <K, T, A, D> Seq<Tuple2<K, D>> grouped(Stream<? extends T> stream, Function<? super T, ? extends K> classifier, Collector<? super T, A, D> downstream) {
        return Seq.grouped(Seq.seq(stream), classifier, downstream);
    }

    public static <K, T, A, D> Seq<Tuple2<K, D>> grouped(Iterable<? extends T> iterable, Function<? super T, ? extends K> classifier, Collector<? super T, A, D> downstream) {
        return Seq.grouped(Seq.seq(iterable), classifier, downstream);
    }

    public static <K, T, A, D> Seq<Tuple2<K, D>> grouped(Seq<? extends T> seq, Function<? super T, ? extends K> classifier, Collector<? super T, A, D> downstream) {
        return Seq.grouped(seq, classifier).map((T t) -> Tuple.tuple(t.v1, ((Seq)t.v2).collect(downstream)));
    }

    public static <T> Tuple2<Seq<T>, Seq<T>> partition(Stream<? extends T> stream, final Predicate<? super T> predicate) {
        final Iterator it = stream.iterator();
        final LinkedList buffer1 = new LinkedList();
        final LinkedList buffer2 = new LinkedList();
        class Partition
        implements Iterator<T> {
            final boolean b;

            Partition(boolean b) {
                this.b = b;
            }

            void fetch() {
                while (this.buffer(this.b).isEmpty() && it.hasNext()) {
                    Object next = it.next();
                    this.buffer(predicate.test(next)).offer(next);
                }
            }

            LinkedList<T> buffer(boolean test) {
                return test ? buffer1 : buffer2;
            }

            @Override
            public boolean hasNext() {
                this.fetch();
                return !this.buffer(this.b).isEmpty();
            }

            @Override
            public T next() {
                return this.buffer(this.b).poll();
            }
        }
        return Tuple.tuple(Seq.seq(new Partition(true)), Seq.seq(new Partition(false)));
    }

    public static <T> Tuple2<Seq<T>, Seq<T>> splitAt(Stream<? extends T> stream, long position) {
        SeqBuffer<T> buffer = SeqBuffer.of(stream);
        return Tuple.tuple(buffer.seq().limit(position), buffer.seq().skip(position));
    }

    public static <T> Tuple2<Optional<T>, Seq<T>> splitAtHead(Stream<T> stream) {
        Iterator it = stream.iterator();
        return Tuple.tuple(it.hasNext() ? Optional.of(it.next()) : Optional.empty(), Seq.seq(it));
    }

    public static <T, U> Seq<U> ofType(Stream<? extends T> stream, Class<? extends U> type) {
        return Seq.seq(stream).filter(type::isInstance).map((T t) -> t);
    }

    public static <T, U> Seq<U> cast(Stream<? extends T> stream, Class<? extends U> type) {
        return Seq.seq(stream).map(type::cast);
    }

    public static <T, K> Map<K, List<T>> groupBy(Stream<? extends T> stream, Function<? super T, ? extends K> classifier) {
        return Seq.seq(stream).groupBy(classifier);
    }

    public static <T, K, A, D> Map<K, D> groupBy(Stream<? extends T> stream, Function<? super T, ? extends K> classifier, Collector<? super T, A, D> downstream) {
        return Seq.seq(stream).groupBy(classifier, downstream);
    }

    public static <T, K, D, A, M extends Map<K, D>> M groupBy(Stream<? extends T> stream, Function<? super T, ? extends K> classifier, Supplier<M> mapFactory, Collector<? super T, A, D> downstream) {
        return Seq.seq(stream).groupBy(classifier, mapFactory, downstream);
    }

    @Deprecated
    public static String join(Stream<?> stream) {
        return Seq.seq(stream).join();
    }

    @Deprecated
    public static String join(Stream<?> stream, CharSequence delimiter) {
        return Seq.seq(stream).join(delimiter);
    }

    @Deprecated
    public static String join(Stream<?> stream, CharSequence delimiter, CharSequence prefix, CharSequence suffix) {
        return Seq.seq(stream).join(delimiter, prefix, suffix);
    }

    @Override
    public Seq<T> filter(Predicate<? super T> var1);

    @Override
    public <R> Seq<R> map(Function<? super T, ? extends R> var1);

    @Override
    public IntStream mapToInt(ToIntFunction<? super T> var1);

    @Override
    public LongStream mapToLong(ToLongFunction<? super T> var1);

    @Override
    public DoubleStream mapToDouble(ToDoubleFunction<? super T> var1);

    @Override
    public <R> Seq<R> flatMap(Function<? super T, ? extends Stream<? extends R>> var1);

    @Override
    public IntStream flatMapToInt(Function<? super T, ? extends IntStream> var1);

    @Override
    public LongStream flatMapToLong(Function<? super T, ? extends LongStream> var1);

    @Override
    public DoubleStream flatMapToDouble(Function<? super T, ? extends DoubleStream> var1);

    @Override
    public Seq<T> distinct();

    @Override
    public Seq<T> sorted();

    @Override
    public Seq<T> sorted(Comparator<? super T> var1);

    @Override
    public Seq<T> peek(Consumer<? super T> var1);

    @Override
    public Seq<T> limit(long var1);

    @Override
    public Seq<T> skip(long var1);

    @Override
    public Seq<T> onClose(Runnable var1);

    @Override
    public void close();

    @Override
    public long count();

    @Override
    default public Seq<T> sequential() {
        return this;
    }

    @Override
    default public Seq<T> parallel() {
        return this;
    }

    @Override
    default public Seq<T> unordered() {
        return this;
    }

    @Override
    default public Spliterator<T> spliterator() {
        return Iterable.super.spliterator();
    }

    @Override
    default public void forEach(Consumer<? super T> action) {
        Iterable.super.forEach(action);
    }

    public String format();

    default public void printOut() {
        this.print(System.out);
    }

    default public void printErr() {
        this.print(System.err);
    }

    default public void print(PrintWriter writer) {
        this.forEach(writer::println);
    }

    default public void print(PrintStream stream) {
        this.forEach(stream::println);
    }
}

