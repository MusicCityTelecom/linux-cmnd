/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.lambda;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.OptionalLong;
import java.util.Spliterator;
import java.util.TreeSet;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Stream;
import org.jooq.lambda.Partition;
import org.jooq.lambda.Seq;
import org.jooq.lambda.WindowSpecification;
import org.jooq.lambda.tuple.Tuple2;

class SeqUtils {
    SeqUtils() {
    }

    @SafeVarargs
    static <T> Seq<T>[] seqs(Stream<? extends T> ... streams) {
        if (streams == null) {
            return null;
        }
        return (Seq[])Seq.of(streams).map(Seq::seq).toArray(Seq[]::new);
    }

    @SafeVarargs
    static <T> Seq<T>[] seqs(Iterable<? extends T> ... iterables) {
        if (iterables == null) {
            return null;
        }
        return (Seq[])Seq.of(iterables).map(Seq::seq).toArray(Seq[]::new);
    }

    static <T, U> Seq<U> transform(Stream<? extends T> stream, final DelegatingSpliterator<T, U> delegating) {
        final Spliterator delegate = stream.spliterator();
        return Seq.seq(new Spliterator<U>(){

            @Override
            public boolean tryAdvance(Consumer<? super U> action) {
                return delegating.tryAdvance(delegate, action);
            }

            @Override
            public Spliterator<U> trySplit() {
                return null;
            }

            @Override
            public long estimateSize() {
                return Long.MAX_VALUE;
            }

            @Override
            public int characteristics() {
                return delegate.characteristics() & 0x10;
            }

            @Override
            public Comparator<? super U> getComparator() {
                return delegate.getComparator();
            }
        }).onClose(() -> stream.close());
    }

    static <T> Map<?, Partition<T>> partitions(WindowSpecification<T> window, List<Tuple2<T, Long>> input) {
        return Seq.seq(input).groupBy(window.partition().compose(t -> t.v1), Collector.of(() -> window.order().isPresent() ? new TreeSet<Tuple2>(Comparator.comparing(t -> t.v1, window.order().get()).thenComparing(t -> (Long)t.v2)) : new ArrayList(), (s, t) -> s.add(t), (s1, s2) -> {
            s1.addAll(s2);
            return s1;
        }, Partition::new, new Collector.Characteristics[0]));
    }

    static <T> OptionalLong indexOf(Iterator<T> iterator, Predicate<? super T> predicate) {
        long index = 0L;
        while (iterator.hasNext()) {
            if (predicate.test(iterator.next())) {
                return OptionalLong.of(index);
            }
            ++index;
        }
        return OptionalLong.empty();
    }

    static void sneakyThrow(Throwable throwable) {
        SeqUtils.sneakyThrow0(throwable);
    }

    static <E extends Throwable> void sneakyThrow0(Throwable throwable) throws E {
        throw throwable;
    }

    static Runnable closeAll(AutoCloseable ... closeables) {
        return () -> {
            Throwable t = null;
            for (AutoCloseable closeable : closeables) {
                try {
                    closeable.close();
                }
                catch (Throwable t1) {
                    if (t == null) {
                        t = t1;
                        continue;
                    }
                    t.addSuppressed(t1);
                }
            }
            if (t != null) {
                SeqUtils.sneakyThrow(t);
            }
        };
    }

    @FunctionalInterface
    static interface DelegatingSpliterator<T, U> {
        public boolean tryAdvance(Spliterator<? extends T> var1, Consumer<? super U> var2);
    }
}

