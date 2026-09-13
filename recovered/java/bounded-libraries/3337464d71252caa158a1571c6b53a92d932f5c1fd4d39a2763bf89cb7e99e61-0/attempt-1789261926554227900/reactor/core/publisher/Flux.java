/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Publisher
 *  org.reactivestreams.Subscriber
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.LongConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.stream.Collector;
import java.util.stream.Stream;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.CorePublisher;
import reactor.core.CoreSubscriber;
import reactor.core.Disposable;
import reactor.core.Exceptions;
import reactor.core.Fuseable;
import reactor.core.publisher.BlockingFirstSubscriber;
import reactor.core.publisher.BlockingIterable;
import reactor.core.publisher.BlockingLastSubscriber;
import reactor.core.publisher.BufferOverflowStrategy;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.ContextTrackingFunctionWrapper;
import reactor.core.publisher.FluxArray;
import reactor.core.publisher.FluxBuffer;
import reactor.core.publisher.FluxBufferBoundary;
import reactor.core.publisher.FluxBufferPredicate;
import reactor.core.publisher.FluxBufferTimeout;
import reactor.core.publisher.FluxBufferWhen;
import reactor.core.publisher.FluxCancelOn;
import reactor.core.publisher.FluxCombineLatest;
import reactor.core.publisher.FluxConcatArray;
import reactor.core.publisher.FluxConcatIterable;
import reactor.core.publisher.FluxConcatMap;
import reactor.core.publisher.FluxConcatMapNoPrefetch;
import reactor.core.publisher.FluxContextWrite;
import reactor.core.publisher.FluxCreate;
import reactor.core.publisher.FluxDefaultIfEmpty;
import reactor.core.publisher.FluxDefer;
import reactor.core.publisher.FluxDeferContextual;
import reactor.core.publisher.FluxDelaySequence;
import reactor.core.publisher.FluxDelaySubscription;
import reactor.core.publisher.FluxDematerialize;
import reactor.core.publisher.FluxDetach;
import reactor.core.publisher.FluxDistinct;
import reactor.core.publisher.FluxDistinctFuseable;
import reactor.core.publisher.FluxDistinctUntilChanged;
import reactor.core.publisher.FluxDoFinally;
import reactor.core.publisher.FluxDoFirst;
import reactor.core.publisher.FluxDoFirstFuseable;
import reactor.core.publisher.FluxDoOnEach;
import reactor.core.publisher.FluxDoOnEachFuseable;
import reactor.core.publisher.FluxElapsed;
import reactor.core.publisher.FluxEmpty;
import reactor.core.publisher.FluxError;
import reactor.core.publisher.FluxErrorOnRequest;
import reactor.core.publisher.FluxErrorSupplied;
import reactor.core.publisher.FluxExpand;
import reactor.core.publisher.FluxFilter;
import reactor.core.publisher.FluxFilterFuseable;
import reactor.core.publisher.FluxFilterWhen;
import reactor.core.publisher.FluxFirstWithSignal;
import reactor.core.publisher.FluxFirstWithValue;
import reactor.core.publisher.FluxFlatMap;
import reactor.core.publisher.FluxFlattenIterable;
import reactor.core.publisher.FluxGenerate;
import reactor.core.publisher.FluxGroupBy;
import reactor.core.publisher.FluxGroupJoin;
import reactor.core.publisher.FluxHandle;
import reactor.core.publisher.FluxHandleFuseable;
import reactor.core.publisher.FluxHide;
import reactor.core.publisher.FluxIndex;
import reactor.core.publisher.FluxIndexFuseable;
import reactor.core.publisher.FluxInterval;
import reactor.core.publisher.FluxIterable;
import reactor.core.publisher.FluxJoin;
import reactor.core.publisher.FluxJust;
import reactor.core.publisher.FluxLimitRequest;
import reactor.core.publisher.FluxLog;
import reactor.core.publisher.FluxLogFuseable;
import reactor.core.publisher.FluxMap;
import reactor.core.publisher.FluxMapFuseable;
import reactor.core.publisher.FluxMapSignal;
import reactor.core.publisher.FluxMaterialize;
import reactor.core.publisher.FluxMerge;
import reactor.core.publisher.FluxMergeComparing;
import reactor.core.publisher.FluxMergeSequential;
import reactor.core.publisher.FluxMetrics;
import reactor.core.publisher.FluxMetricsFuseable;
import reactor.core.publisher.FluxName;
import reactor.core.publisher.FluxNever;
import reactor.core.publisher.FluxOnAssembly;
import reactor.core.publisher.FluxOnBackpressureBuffer;
import reactor.core.publisher.FluxOnBackpressureBufferStrategy;
import reactor.core.publisher.FluxOnBackpressureBufferTimeout;
import reactor.core.publisher.FluxOnBackpressureDrop;
import reactor.core.publisher.FluxOnBackpressureLatest;
import reactor.core.publisher.FluxOnErrorResume;
import reactor.core.publisher.FluxPeek;
import reactor.core.publisher.FluxPeekFuseable;
import reactor.core.publisher.FluxPublish;
import reactor.core.publisher.FluxPublishMulticast;
import reactor.core.publisher.FluxPublishOn;
import reactor.core.publisher.FluxRange;
import reactor.core.publisher.FluxRefCount;
import reactor.core.publisher.FluxRepeat;
import reactor.core.publisher.FluxRepeatPredicate;
import reactor.core.publisher.FluxRepeatWhen;
import reactor.core.publisher.FluxReplay;
import reactor.core.publisher.FluxRetry;
import reactor.core.publisher.FluxRetryWhen;
import reactor.core.publisher.FluxSample;
import reactor.core.publisher.FluxSampleFirst;
import reactor.core.publisher.FluxSampleTimeout;
import reactor.core.publisher.FluxScan;
import reactor.core.publisher.FluxScanSeed;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.FluxSkip;
import reactor.core.publisher.FluxSkipLast;
import reactor.core.publisher.FluxSkipUntil;
import reactor.core.publisher.FluxSkipUntilOther;
import reactor.core.publisher.FluxSkipWhile;
import reactor.core.publisher.FluxSource;
import reactor.core.publisher.FluxSourceFuseable;
import reactor.core.publisher.FluxSourceMono;
import reactor.core.publisher.FluxSourceMonoFuseable;
import reactor.core.publisher.FluxStream;
import reactor.core.publisher.FluxSubscribeOn;
import reactor.core.publisher.FluxSubscribeOnCallable;
import reactor.core.publisher.FluxSubscribeOnValue;
import reactor.core.publisher.FluxSwitchIfEmpty;
import reactor.core.publisher.FluxSwitchMap;
import reactor.core.publisher.FluxSwitchMapNoPrefetch;
import reactor.core.publisher.FluxSwitchOnFirst;
import reactor.core.publisher.FluxTake;
import reactor.core.publisher.FluxTakeFuseable;
import reactor.core.publisher.FluxTakeLast;
import reactor.core.publisher.FluxTakeLastOne;
import reactor.core.publisher.FluxTakeUntil;
import reactor.core.publisher.FluxTakeUntilOther;
import reactor.core.publisher.FluxTakeWhile;
import reactor.core.publisher.FluxTimed;
import reactor.core.publisher.FluxTimeout;
import reactor.core.publisher.FluxUsing;
import reactor.core.publisher.FluxUsingWhen;
import reactor.core.publisher.FluxWindow;
import reactor.core.publisher.FluxWindowBoundary;
import reactor.core.publisher.FluxWindowPredicate;
import reactor.core.publisher.FluxWindowTimeout;
import reactor.core.publisher.FluxWindowWhen;
import reactor.core.publisher.FluxWithLatestFrom;
import reactor.core.publisher.FluxZip;
import reactor.core.publisher.FluxZipIterable;
import reactor.core.publisher.GroupedFlux;
import reactor.core.publisher.Hooks;
import reactor.core.publisher.LambdaSubscriber;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoAll;
import reactor.core.publisher.MonoAny;
import reactor.core.publisher.MonoCallable;
import reactor.core.publisher.MonoCollect;
import reactor.core.publisher.MonoCollectList;
import reactor.core.publisher.MonoCount;
import reactor.core.publisher.MonoElementAt;
import reactor.core.publisher.MonoEmpty;
import reactor.core.publisher.MonoError;
import reactor.core.publisher.MonoHasElements;
import reactor.core.publisher.MonoIgnoreElements;
import reactor.core.publisher.MonoIgnoreThen;
import reactor.core.publisher.MonoJust;
import reactor.core.publisher.MonoNext;
import reactor.core.publisher.MonoReduce;
import reactor.core.publisher.MonoReduceSeed;
import reactor.core.publisher.MonoSingle;
import reactor.core.publisher.MonoSingleCallable;
import reactor.core.publisher.MonoStreamCollector;
import reactor.core.publisher.MonoTakeLastOne;
import reactor.core.publisher.NextProcessor;
import reactor.core.publisher.OnNextFailureStrategy;
import reactor.core.publisher.Operators;
import reactor.core.publisher.OptimizableOperator;
import reactor.core.publisher.ParallelFlux;
import reactor.core.publisher.Signal;
import reactor.core.publisher.SignalLogger;
import reactor.core.publisher.SignalType;
import reactor.core.publisher.SynchronousSink;
import reactor.core.publisher.Timed;
import reactor.core.publisher.Traces;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import reactor.util.Logger;
import reactor.util.Metrics;
import reactor.util.annotation.Nullable;
import reactor.util.concurrent.Queues;
import reactor.util.context.Context;
import reactor.util.context.ContextView;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuple3;
import reactor.util.function.Tuple4;
import reactor.util.function.Tuple5;
import reactor.util.function.Tuple6;
import reactor.util.function.Tuple7;
import reactor.util.function.Tuple8;
import reactor.util.function.Tuples;
import reactor.util.retry.Retry;

public abstract class Flux<T>
implements CorePublisher<T> {
    static final BiFunction TUPLE2_BIFUNCTION = Tuples::of;
    static final Supplier LIST_SUPPLIER = ArrayList::new;
    static final Supplier SET_SUPPLIER = HashSet::new;
    static final BooleanSupplier ALWAYS_BOOLEAN_SUPPLIER = () -> true;
    static final BiPredicate OBJECT_EQUAL = Object::equals;
    static final Function IDENTITY_FUNCTION = Function.identity();

    @SafeVarargs
    public static <T, V> Flux<V> combineLatest(Function<Object[], V> combinator, Publisher<? extends T> ... sources) {
        return Flux.combineLatest(combinator, Queues.XS_BUFFER_SIZE, sources);
    }

    @SafeVarargs
    public static <T, V> Flux<V> combineLatest(Function<Object[], V> combinator, int prefetch, Publisher<? extends T> ... sources) {
        if (sources.length == 0) {
            return Flux.empty();
        }
        if (sources.length == 1) {
            Publisher<? extends T> source = sources[0];
            if (source instanceof Fuseable) {
                return Flux.onAssembly(new FluxMapFuseable<Object, Object>(Flux.from(source), v -> combinator.apply(new Object[]{v})));
            }
            return Flux.onAssembly(new FluxMap<Object, Object>(Flux.from(source), v -> combinator.apply(new Object[]{v})));
        }
        return Flux.onAssembly(new FluxCombineLatest<T, V>(sources, combinator, Queues.get(prefetch), prefetch));
    }

    public static <T1, T2, V> Flux<V> combineLatest(Publisher<? extends T1> source1, Publisher<? extends T2> source2, BiFunction<? super T1, ? super T2, ? extends V> combinator) {
        return Flux.combineLatest((Object[] tuple) -> combinator.apply((Object)tuple[0], (Object)tuple[1]), source1, source2);
    }

    public static <T1, T2, T3, V> Flux<V> combineLatest(Publisher<? extends T1> source1, Publisher<? extends T2> source2, Publisher<? extends T3> source3, Function<Object[], V> combinator) {
        return Flux.combineLatest(combinator, source1, source2, source3);
    }

    public static <T1, T2, T3, T4, V> Flux<V> combineLatest(Publisher<? extends T1> source1, Publisher<? extends T2> source2, Publisher<? extends T3> source3, Publisher<? extends T4> source4, Function<Object[], V> combinator) {
        return Flux.combineLatest(combinator, source1, source2, source3, source4);
    }

    public static <T1, T2, T3, T4, T5, V> Flux<V> combineLatest(Publisher<? extends T1> source1, Publisher<? extends T2> source2, Publisher<? extends T3> source3, Publisher<? extends T4> source4, Publisher<? extends T5> source5, Function<Object[], V> combinator) {
        return Flux.combineLatest(combinator, source1, source2, source3, source4, source5);
    }

    public static <T1, T2, T3, T4, T5, T6, V> Flux<V> combineLatest(Publisher<? extends T1> source1, Publisher<? extends T2> source2, Publisher<? extends T3> source3, Publisher<? extends T4> source4, Publisher<? extends T5> source5, Publisher<? extends T6> source6, Function<Object[], V> combinator) {
        return Flux.combineLatest(combinator, source1, source2, source3, source4, source5, source6);
    }

    public static <T, V> Flux<V> combineLatest(Iterable<? extends Publisher<? extends T>> sources, Function<Object[], V> combinator) {
        return Flux.combineLatest(sources, Queues.XS_BUFFER_SIZE, combinator);
    }

    public static <T, V> Flux<V> combineLatest(Iterable<? extends Publisher<? extends T>> sources, int prefetch, Function<Object[], V> combinator) {
        return Flux.onAssembly(new FluxCombineLatest(sources, combinator, Queues.get(prefetch), prefetch));
    }

    public static <T> Flux<T> concat(Iterable<? extends Publisher<? extends T>> sources) {
        return Flux.onAssembly(new FluxConcatIterable(sources));
    }

    @SafeVarargs
    public final Flux<T> concatWithValues(T ... values) {
        return this.concatWith(Flux.fromArray(values));
    }

    public static <T> Flux<T> concat(Publisher<? extends Publisher<? extends T>> sources) {
        return Flux.concat(sources, Queues.XS_BUFFER_SIZE);
    }

    public static <T> Flux<T> concat(Publisher<? extends Publisher<? extends T>> sources, int prefetch) {
        return Flux.from(sources).concatMap(Flux.identityFunction(), prefetch);
    }

    @SafeVarargs
    public static <T> Flux<T> concat(Publisher<? extends T> ... sources) {
        return Flux.onAssembly(new FluxConcatArray<T>(false, sources));
    }

    public static <T> Flux<T> concatDelayError(Publisher<? extends Publisher<? extends T>> sources) {
        return Flux.concatDelayError(sources, Queues.XS_BUFFER_SIZE);
    }

    public static <T> Flux<T> concatDelayError(Publisher<? extends Publisher<? extends T>> sources, int prefetch) {
        return Flux.from(sources).concatMapDelayError(Flux.identityFunction(), prefetch);
    }

    public static <T> Flux<T> concatDelayError(Publisher<? extends Publisher<? extends T>> sources, boolean delayUntilEnd, int prefetch) {
        return Flux.from(sources).concatMapDelayError(Flux.identityFunction(), delayUntilEnd, prefetch);
    }

    @SafeVarargs
    public static <T> Flux<T> concatDelayError(Publisher<? extends T> ... sources) {
        return Flux.onAssembly(new FluxConcatArray<T>(true, sources));
    }

    public static <T> Flux<T> create(Consumer<? super FluxSink<T>> emitter) {
        return Flux.create(emitter, FluxSink.OverflowStrategy.BUFFER);
    }

    public static <T> Flux<T> create(Consumer<? super FluxSink<T>> emitter, FluxSink.OverflowStrategy backpressure) {
        return Flux.onAssembly(new FluxCreate(emitter, backpressure, FluxCreate.CreateMode.PUSH_PULL));
    }

    public static <T> Flux<T> push(Consumer<? super FluxSink<T>> emitter) {
        return Flux.push(emitter, FluxSink.OverflowStrategy.BUFFER);
    }

    public static <T> Flux<T> push(Consumer<? super FluxSink<T>> emitter, FluxSink.OverflowStrategy backpressure) {
        return Flux.onAssembly(new FluxCreate(emitter, backpressure, FluxCreate.CreateMode.PUSH_ONLY));
    }

    public static <T> Flux<T> defer(Supplier<? extends Publisher<T>> supplier) {
        return Flux.onAssembly(new FluxDefer(supplier));
    }

    @Deprecated
    public static <T> Flux<T> deferWithContext(Function<Context, ? extends Publisher<T>> contextualPublisherFactory) {
        return Flux.deferContextual(view -> (Publisher)contextualPublisherFactory.apply(Context.of(view)));
    }

    public static <T> Flux<T> deferContextual(Function<ContextView, ? extends Publisher<T>> contextualPublisherFactory) {
        return Flux.onAssembly(new FluxDeferContextual(contextualPublisherFactory));
    }

    public static <T> Flux<T> empty() {
        return FluxEmpty.instance();
    }

    public static <T> Flux<T> error(Throwable error) {
        return Flux.error(error, false);
    }

    public static <T> Flux<T> error(Supplier<? extends Throwable> errorSupplier) {
        return Flux.onAssembly(new FluxErrorSupplied(errorSupplier));
    }

    public static <O> Flux<O> error(Throwable throwable, boolean whenRequested) {
        if (whenRequested) {
            return Flux.onAssembly(new FluxErrorOnRequest(throwable));
        }
        return Flux.onAssembly(new FluxError(throwable));
    }

    @SafeVarargs
    @Deprecated
    public static <I> Flux<I> first(Publisher<? extends I> ... sources) {
        return Flux.firstWithSignal(sources);
    }

    @Deprecated
    public static <I> Flux<I> first(Iterable<? extends Publisher<? extends I>> sources) {
        return Flux.firstWithSignal(sources);
    }

    @SafeVarargs
    public static <I> Flux<I> firstWithSignal(Publisher<? extends I> ... sources) {
        return Flux.onAssembly(new FluxFirstWithSignal<I>(sources));
    }

    public static <I> Flux<I> firstWithSignal(Iterable<? extends Publisher<? extends I>> sources) {
        return Flux.onAssembly(new FluxFirstWithSignal(sources));
    }

    public static <I> Flux<I> firstWithValue(Iterable<? extends Publisher<? extends I>> sources) {
        return Flux.onAssembly(new FluxFirstWithValue(sources));
    }

    @SafeVarargs
    public static <I> Flux<I> firstWithValue(Publisher<? extends I> first, Publisher<? extends I> ... others) {
        FluxFirstWithValue orPublisher;
        FluxFirstWithValue<? extends I> result;
        if (first instanceof FluxFirstWithValue && (result = (orPublisher = (FluxFirstWithValue)first).firstValuedAdditionalSources(others)) != null) {
            return result;
        }
        return Flux.onAssembly(new FluxFirstWithValue<I>(first, others));
    }

    public static <T> Flux<T> from(Publisher<? extends T> source) {
        if (source instanceof Flux) {
            Flux casted = (Flux)source;
            return casted;
        }
        return Flux.onAssembly(Flux.wrap(source));
    }

    public static <T> Flux<T> fromArray(T[] array) {
        if (array.length == 0) {
            return Flux.empty();
        }
        if (array.length == 1) {
            return Flux.just(array[0]);
        }
        return Flux.onAssembly(new FluxArray<T>(array));
    }

    public static <T> Flux<T> fromIterable(Iterable<? extends T> it) {
        return Flux.onAssembly(new FluxIterable<T>(it));
    }

    public static <T> Flux<T> fromStream(Stream<? extends T> s) {
        Objects.requireNonNull(s, "Stream s must be provided");
        return Flux.onAssembly(new FluxStream(() -> s));
    }

    public static <T> Flux<T> fromStream(Supplier<Stream<? extends T>> streamSupplier) {
        return Flux.onAssembly(new FluxStream<T>(streamSupplier));
    }

    public static <T> Flux<T> generate(Consumer<SynchronousSink<T>> generator) {
        Objects.requireNonNull(generator, "generator");
        return Flux.onAssembly(new FluxGenerate(generator));
    }

    public static <T, S> Flux<T> generate(Callable<S> stateSupplier, BiFunction<S, SynchronousSink<T>, S> generator) {
        return Flux.onAssembly(new FluxGenerate<T, S>(stateSupplier, generator));
    }

    public static <T, S> Flux<T> generate(Callable<S> stateSupplier, BiFunction<S, SynchronousSink<T>, S> generator, Consumer<? super S> stateConsumer) {
        return Flux.onAssembly(new FluxGenerate<T, S>(stateSupplier, generator, stateConsumer));
    }

    public static Flux<Long> interval(Duration period) {
        return Flux.interval(period, Schedulers.parallel());
    }

    public static Flux<Long> interval(Duration delay, Duration period) {
        return Flux.interval(delay, period, Schedulers.parallel());
    }

    public static Flux<Long> interval(Duration period, Scheduler timer) {
        return Flux.interval(period, period, timer);
    }

    public static Flux<Long> interval(Duration delay, Duration period, Scheduler timer) {
        return Flux.onAssembly(new FluxInterval(delay.toNanos(), period.toNanos(), TimeUnit.NANOSECONDS, timer));
    }

    @SafeVarargs
    public static <T> Flux<T> just(T ... data) {
        return Flux.fromArray(data);
    }

    public static <T> Flux<T> just(T data) {
        return Flux.onAssembly(new FluxJust<T>(data));
    }

    public static <T> Flux<T> merge(Publisher<? extends Publisher<? extends T>> source) {
        return Flux.merge(source, Queues.SMALL_BUFFER_SIZE, Queues.XS_BUFFER_SIZE);
    }

    public static <T> Flux<T> merge(Publisher<? extends Publisher<? extends T>> source, int concurrency) {
        return Flux.merge(source, concurrency, Queues.XS_BUFFER_SIZE);
    }

    public static <T> Flux<T> merge(Publisher<? extends Publisher<? extends T>> source, int concurrency, int prefetch) {
        return Flux.onAssembly(new FluxFlatMap(Flux.from(source), Flux.identityFunction(), false, concurrency, Queues.get(concurrency), prefetch, Queues.get(prefetch)));
    }

    public static <I> Flux<I> merge(Iterable<? extends Publisher<? extends I>> sources) {
        return Flux.merge(Flux.fromIterable(sources));
    }

    @SafeVarargs
    public static <I> Flux<I> merge(Publisher<? extends I> ... sources) {
        return Flux.merge(Queues.XS_BUFFER_SIZE, sources);
    }

    @SafeVarargs
    public static <I> Flux<I> merge(int prefetch, Publisher<? extends I> ... sources) {
        return Flux.merge(prefetch, false, sources);
    }

    @SafeVarargs
    public static <I> Flux<I> mergeDelayError(int prefetch, Publisher<? extends I> ... sources) {
        return Flux.merge(prefetch, true, sources);
    }

    @SafeVarargs
    public static <I extends Comparable<? super I>> Flux<I> mergeComparing(Publisher<? extends I> ... sources) {
        return Flux.mergeComparing(Queues.SMALL_BUFFER_SIZE, Comparator.naturalOrder(), sources);
    }

    @SafeVarargs
    public static <T> Flux<T> mergeComparing(Comparator<? super T> comparator, Publisher<? extends T> ... sources) {
        return Flux.mergeComparing(Queues.SMALL_BUFFER_SIZE, comparator, sources);
    }

    @SafeVarargs
    public static <T> Flux<T> mergeComparing(int prefetch, Comparator<? super T> comparator, Publisher<? extends T> ... sources) {
        if (sources.length == 0) {
            return Flux.empty();
        }
        if (sources.length == 1) {
            return Flux.from(sources[0]);
        }
        return Flux.onAssembly(new FluxMergeComparing<T>(prefetch, comparator, false, sources));
    }

    @SafeVarargs
    public static <T> Flux<T> mergeComparingDelayError(int prefetch, Comparator<? super T> comparator, Publisher<? extends T> ... sources) {
        if (sources.length == 0) {
            return Flux.empty();
        }
        if (sources.length == 1) {
            return Flux.from(sources[0]);
        }
        return Flux.onAssembly(new FluxMergeComparing<T>(prefetch, comparator, true, sources));
    }

    @SafeVarargs
    @Deprecated
    public static <I extends Comparable<? super I>> Flux<I> mergeOrdered(Publisher<? extends I> ... sources) {
        return Flux.mergeOrdered(Queues.SMALL_BUFFER_SIZE, Comparator.naturalOrder(), sources);
    }

    @SafeVarargs
    @Deprecated
    public static <T> Flux<T> mergeOrdered(Comparator<? super T> comparator, Publisher<? extends T> ... sources) {
        return Flux.mergeOrdered(Queues.SMALL_BUFFER_SIZE, comparator, sources);
    }

    @SafeVarargs
    @Deprecated
    public static <T> Flux<T> mergeOrdered(int prefetch, Comparator<? super T> comparator, Publisher<? extends T> ... sources) {
        if (sources.length == 0) {
            return Flux.empty();
        }
        if (sources.length == 1) {
            return Flux.from(sources[0]);
        }
        return Flux.onAssembly(new FluxMergeComparing<T>(prefetch, comparator, true, sources));
    }

    public static <T> Flux<T> mergeSequential(Publisher<? extends Publisher<? extends T>> sources) {
        return Flux.mergeSequential(sources, false, Queues.SMALL_BUFFER_SIZE, Queues.XS_BUFFER_SIZE);
    }

    public static <T> Flux<T> mergeSequential(Publisher<? extends Publisher<? extends T>> sources, int maxConcurrency, int prefetch) {
        return Flux.mergeSequential(sources, false, maxConcurrency, prefetch);
    }

    public static <T> Flux<T> mergeSequentialDelayError(Publisher<? extends Publisher<? extends T>> sources, int maxConcurrency, int prefetch) {
        return Flux.mergeSequential(sources, true, maxConcurrency, prefetch);
    }

    @SafeVarargs
    public static <I> Flux<I> mergeSequential(Publisher<? extends I> ... sources) {
        return Flux.mergeSequential(Queues.XS_BUFFER_SIZE, false, sources);
    }

    @SafeVarargs
    public static <I> Flux<I> mergeSequential(int prefetch, Publisher<? extends I> ... sources) {
        return Flux.mergeSequential(prefetch, false, sources);
    }

    @SafeVarargs
    public static <I> Flux<I> mergeSequentialDelayError(int prefetch, Publisher<? extends I> ... sources) {
        return Flux.mergeSequential(prefetch, true, sources);
    }

    public static <I> Flux<I> mergeSequential(Iterable<? extends Publisher<? extends I>> sources) {
        return Flux.mergeSequential(sources, false, Queues.SMALL_BUFFER_SIZE, Queues.XS_BUFFER_SIZE);
    }

    public static <I> Flux<I> mergeSequential(Iterable<? extends Publisher<? extends I>> sources, int maxConcurrency, int prefetch) {
        return Flux.mergeSequential(sources, false, maxConcurrency, prefetch);
    }

    public static <I> Flux<I> mergeSequentialDelayError(Iterable<? extends Publisher<? extends I>> sources, int maxConcurrency, int prefetch) {
        return Flux.mergeSequential(sources, true, maxConcurrency, prefetch);
    }

    public static <T> Flux<T> never() {
        return FluxNever.instance();
    }

    public static Flux<Integer> range(int start, int count) {
        if (count == 1) {
            return Flux.just(Integer.valueOf(start));
        }
        if (count == 0) {
            return Flux.empty();
        }
        return Flux.onAssembly(new FluxRange(start, count));
    }

    public static <T> Flux<T> switchOnNext(Publisher<? extends Publisher<? extends T>> mergedPublishers) {
        return Flux.switchOnNext(mergedPublishers, Queues.XS_BUFFER_SIZE);
    }

    @Deprecated
    public static <T> Flux<T> switchOnNext(Publisher<? extends Publisher<? extends T>> mergedPublishers, int prefetch) {
        if (prefetch == 0) {
            return Flux.onAssembly(new FluxSwitchMapNoPrefetch(Flux.from(mergedPublishers), Flux.identityFunction()));
        }
        return Flux.onAssembly(new FluxSwitchMap(Flux.from(mergedPublishers), Flux.identityFunction(), Queues.unbounded(prefetch), prefetch));
    }

    public static <T, D> Flux<T> using(Callable<? extends D> resourceSupplier, Function<? super D, ? extends Publisher<? extends T>> sourceSupplier, Consumer<? super D> resourceCleanup) {
        return Flux.using(resourceSupplier, sourceSupplier, resourceCleanup, true);
    }

    public static <T, D> Flux<T> using(Callable<? extends D> resourceSupplier, Function<? super D, ? extends Publisher<? extends T>> sourceSupplier, Consumer<? super D> resourceCleanup, boolean eager) {
        return Flux.onAssembly(new FluxUsing(resourceSupplier, sourceSupplier, resourceCleanup, eager));
    }

    public static <T, D> Flux<T> usingWhen(Publisher<D> resourceSupplier, Function<? super D, ? extends Publisher<? extends T>> resourceClosure, Function<? super D, ? extends Publisher<?>> asyncCleanup) {
        return Flux.usingWhen(resourceSupplier, resourceClosure, asyncCleanup, (resource, error) -> (Publisher)asyncCleanup.apply((Object)resource), asyncCleanup);
    }

    public static <T, D> Flux<T> usingWhen(Publisher<D> resourceSupplier, Function<? super D, ? extends Publisher<? extends T>> resourceClosure, Function<? super D, ? extends Publisher<?>> asyncComplete, BiFunction<? super D, ? super Throwable, ? extends Publisher<?>> asyncError, Function<? super D, ? extends Publisher<?>> asyncCancel) {
        return Flux.onAssembly(new FluxUsingWhen(resourceSupplier, resourceClosure, asyncComplete, asyncError, asyncCancel));
    }

    public static <T1, T2, O> Flux<O> zip(Publisher<? extends T1> source1, Publisher<? extends T2> source2, BiFunction<? super T1, ? super T2, ? extends O> combinator) {
        return Flux.onAssembly(new FluxZip<T1, O>(source1, source2, combinator, Queues.xs(), Queues.XS_BUFFER_SIZE));
    }

    public static <T1, T2> Flux<Tuple2<T1, T2>> zip(Publisher<? extends T1> source1, Publisher<? extends T2> source2) {
        return Flux.zip(source1, source2, Flux.tuple2Function());
    }

    public static <T1, T2, T3> Flux<Tuple3<T1, T2, T3>> zip(Publisher<? extends T1> source1, Publisher<? extends T2> source2, Publisher<? extends T3> source3) {
        return Flux.zip(Tuples.fn3(), source1, source2, source3);
    }

    public static <T1, T2, T3, T4> Flux<Tuple4<T1, T2, T3, T4>> zip(Publisher<? extends T1> source1, Publisher<? extends T2> source2, Publisher<? extends T3> source3, Publisher<? extends T4> source4) {
        return Flux.zip(Tuples.fn4(), source1, source2, source3, source4);
    }

    public static <T1, T2, T3, T4, T5> Flux<Tuple5<T1, T2, T3, T4, T5>> zip(Publisher<? extends T1> source1, Publisher<? extends T2> source2, Publisher<? extends T3> source3, Publisher<? extends T4> source4, Publisher<? extends T5> source5) {
        return Flux.zip(Tuples.fn5(), source1, source2, source3, source4, source5);
    }

    public static <T1, T2, T3, T4, T5, T6> Flux<Tuple6<T1, T2, T3, T4, T5, T6>> zip(Publisher<? extends T1> source1, Publisher<? extends T2> source2, Publisher<? extends T3> source3, Publisher<? extends T4> source4, Publisher<? extends T5> source5, Publisher<? extends T6> source6) {
        return Flux.zip(Tuples.fn6(), source1, source2, source3, source4, source5, source6);
    }

    public static <T1, T2, T3, T4, T5, T6, T7> Flux<Tuple7<T1, T2, T3, T4, T5, T6, T7>> zip(Publisher<? extends T1> source1, Publisher<? extends T2> source2, Publisher<? extends T3> source3, Publisher<? extends T4> source4, Publisher<? extends T5> source5, Publisher<? extends T6> source6, Publisher<? extends T7> source7) {
        return Flux.zip(Tuples.fn7(), source1, source2, source3, source4, source5, source6, source7);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8> Flux<Tuple8<T1, T2, T3, T4, T5, T6, T7, T8>> zip(Publisher<? extends T1> source1, Publisher<? extends T2> source2, Publisher<? extends T3> source3, Publisher<? extends T4> source4, Publisher<? extends T5> source5, Publisher<? extends T6> source6, Publisher<? extends T7> source7, Publisher<? extends T8> source8) {
        return Flux.zip(Tuples.fn8(), source1, source2, source3, source4, source5, source6, source7, source8);
    }

    public static <O> Flux<O> zip(Iterable<? extends Publisher<?>> sources, Function<? super Object[], ? extends O> combinator) {
        return Flux.zip(sources, Queues.XS_BUFFER_SIZE, combinator);
    }

    public static <O> Flux<O> zip(Iterable<? extends Publisher<?>> sources, int prefetch, Function<? super Object[], ? extends O> combinator) {
        return Flux.onAssembly(new FluxZip(sources, combinator, Queues.get(prefetch), prefetch));
    }

    @SafeVarargs
    public static <I, O> Flux<O> zip(Function<? super Object[], ? extends O> combinator, Publisher<? extends I> ... sources) {
        return Flux.zip(combinator, Queues.XS_BUFFER_SIZE, sources);
    }

    @SafeVarargs
    public static <I, O> Flux<O> zip(Function<? super Object[], ? extends O> combinator, int prefetch, Publisher<? extends I> ... sources) {
        if (sources.length == 0) {
            return Flux.empty();
        }
        if (sources.length == 1) {
            Publisher<? extends I> source = sources[0];
            if (source instanceof Fuseable) {
                return Flux.onAssembly(new FluxMapFuseable<Object, Object>(Flux.from(source), v -> combinator.apply(new Object[]{v})));
            }
            return Flux.onAssembly(new FluxMap<Object, Object>(Flux.from(source), v -> combinator.apply(new Object[]{v})));
        }
        return Flux.onAssembly(new FluxZip<I, O>(sources, combinator, Queues.get(prefetch), prefetch));
    }

    public static <TUPLE extends Tuple2, V> Flux<V> zip(Publisher<? extends Publisher<?>> sources, final Function<? super TUPLE, ? extends V> combinator) {
        return Flux.onAssembly(new FluxBuffer(Flux.from(sources), Integer.MAX_VALUE, Flux.listSupplier()).flatMap(new Function<List<? extends Publisher<?>>, Publisher<V>>(){

            @Override
            public Publisher<V> apply(List<? extends Publisher<?>> publishers) {
                return Flux.zip(Tuples.fnAny(combinator), publishers.toArray(new Publisher[publishers.size()]));
            }
        }));
    }

    public final Mono<Boolean> all(Predicate<? super T> predicate) {
        return Mono.onAssembly(new MonoAll<T>(this, predicate));
    }

    public final Mono<Boolean> any(Predicate<? super T> predicate) {
        return Mono.onAssembly(new MonoAny<T>(this, predicate));
    }

    public final <P> P as(Function<? super Flux<T>, P> transformer) {
        return transformer.apply(this);
    }

    @Nullable
    public final T blockFirst() {
        BlockingFirstSubscriber subscriber = new BlockingFirstSubscriber();
        this.subscribe(subscriber);
        return subscriber.blockingGet();
    }

    @Nullable
    public final T blockFirst(Duration timeout) {
        BlockingFirstSubscriber subscriber = new BlockingFirstSubscriber();
        this.subscribe(subscriber);
        return subscriber.blockingGet(timeout.toNanos(), TimeUnit.NANOSECONDS);
    }

    @Nullable
    public final T blockLast() {
        BlockingLastSubscriber subscriber = new BlockingLastSubscriber();
        this.subscribe(subscriber);
        return subscriber.blockingGet();
    }

    @Nullable
    public final T blockLast(Duration timeout) {
        BlockingLastSubscriber subscriber = new BlockingLastSubscriber();
        this.subscribe(subscriber);
        return subscriber.blockingGet(timeout.toNanos(), TimeUnit.NANOSECONDS);
    }

    public final Flux<List<T>> buffer() {
        return this.buffer(Integer.MAX_VALUE);
    }

    public final Flux<List<T>> buffer(int maxSize) {
        return this.buffer(maxSize, Flux.listSupplier());
    }

    public final <C extends Collection<? super T>> Flux<C> buffer(int maxSize, Supplier<C> bufferSupplier) {
        return Flux.onAssembly(new FluxBuffer(this, maxSize, bufferSupplier));
    }

    public final Flux<List<T>> buffer(int maxSize, int skip) {
        return this.buffer(maxSize, skip, Flux.listSupplier());
    }

    public final <C extends Collection<? super T>> Flux<C> buffer(int maxSize, int skip, Supplier<C> bufferSupplier) {
        return Flux.onAssembly(new FluxBuffer(this, maxSize, skip, bufferSupplier));
    }

    public final Flux<List<T>> buffer(Publisher<?> other) {
        return this.buffer(other, Flux.listSupplier());
    }

    public final <C extends Collection<? super T>> Flux<C> buffer(Publisher<?> other, Supplier<C> bufferSupplier) {
        return Flux.onAssembly(new FluxBufferBoundary(this, other, bufferSupplier));
    }

    public final Flux<List<T>> buffer(Duration bufferingTimespan) {
        return this.buffer(bufferingTimespan, Schedulers.parallel());
    }

    public final Flux<List<T>> buffer(Duration bufferingTimespan, Duration openBufferEvery) {
        return this.buffer(bufferingTimespan, openBufferEvery, Schedulers.parallel());
    }

    public final Flux<List<T>> buffer(Duration bufferingTimespan, Scheduler timer) {
        return this.buffer(Flux.interval(bufferingTimespan, timer));
    }

    public final Flux<List<T>> buffer(Duration bufferingTimespan, Duration openBufferEvery, Scheduler timer) {
        if (bufferingTimespan.equals(openBufferEvery)) {
            return this.buffer(bufferingTimespan, timer);
        }
        return this.bufferWhen(Flux.interval(Duration.ZERO, openBufferEvery, timer), aLong -> Mono.delay(bufferingTimespan, timer));
    }

    public final Flux<List<T>> bufferTimeout(int maxSize, Duration maxTime) {
        return this.bufferTimeout(maxSize, maxTime, Flux.listSupplier());
    }

    public final <C extends Collection<? super T>> Flux<C> bufferTimeout(int maxSize, Duration maxTime, Supplier<C> bufferSupplier) {
        return this.bufferTimeout(maxSize, maxTime, Schedulers.parallel(), bufferSupplier);
    }

    public final Flux<List<T>> bufferTimeout(int maxSize, Duration maxTime, Scheduler timer) {
        return this.bufferTimeout(maxSize, maxTime, timer, Flux.listSupplier());
    }

    public final <C extends Collection<? super T>> Flux<C> bufferTimeout(int maxSize, Duration maxTime, Scheduler timer, Supplier<C> bufferSupplier) {
        return Flux.onAssembly(new FluxBufferTimeout(this, maxSize, maxTime.toNanos(), TimeUnit.NANOSECONDS, timer, bufferSupplier));
    }

    public final Flux<List<T>> bufferUntil(Predicate<? super T> predicate) {
        return Flux.onAssembly(new FluxBufferPredicate(this, predicate, Flux.listSupplier(), FluxBufferPredicate.Mode.UNTIL));
    }

    public final Flux<List<T>> bufferUntil(Predicate<? super T> predicate, boolean cutBefore) {
        return Flux.onAssembly(new FluxBufferPredicate(this, predicate, Flux.listSupplier(), cutBefore ? FluxBufferPredicate.Mode.UNTIL_CUT_BEFORE : FluxBufferPredicate.Mode.UNTIL));
    }

    public final <V> Flux<List<T>> bufferUntilChanged() {
        return this.bufferUntilChanged(Flux.identityFunction());
    }

    public final <V> Flux<List<T>> bufferUntilChanged(Function<? super T, ? extends V> keySelector) {
        return this.bufferUntilChanged(keySelector, Flux.equalPredicate());
    }

    public final <V> Flux<List<T>> bufferUntilChanged(Function<? super T, ? extends V> keySelector, BiPredicate<? super V, ? super V> keyComparator) {
        return Flux.defer(() -> this.bufferUntil(new FluxBufferPredicate.ChangedPredicate(keySelector, keyComparator), true));
    }

    public final Flux<List<T>> bufferWhile(Predicate<? super T> predicate) {
        return Flux.onAssembly(new FluxBufferPredicate(this, predicate, Flux.listSupplier(), FluxBufferPredicate.Mode.WHILE));
    }

    public final <U, V> Flux<List<T>> bufferWhen(Publisher<U> bucketOpening, Function<? super U, ? extends Publisher<V>> closeSelector) {
        return this.bufferWhen(bucketOpening, closeSelector, Flux.listSupplier());
    }

    public final <U, V, C extends Collection<? super T>> Flux<C> bufferWhen(Publisher<U> bucketOpening, Function<? super U, ? extends Publisher<V>> closeSelector, Supplier<C> bufferSupplier) {
        return Flux.onAssembly(new FluxBufferWhen(this, bucketOpening, closeSelector, bufferSupplier, Queues.unbounded(Queues.XS_BUFFER_SIZE)));
    }

    public final Flux<T> cache() {
        return this.cache(Integer.MAX_VALUE);
    }

    public final Flux<T> cache(int history) {
        return this.replay(history).autoConnect();
    }

    public final Flux<T> cache(Duration ttl) {
        return this.cache(ttl, Schedulers.parallel());
    }

    public final Flux<T> cache(Duration ttl, Scheduler timer) {
        return this.cache(Integer.MAX_VALUE, ttl, timer);
    }

    public final Flux<T> cache(int history, Duration ttl) {
        return this.cache(history, ttl, Schedulers.parallel());
    }

    public final Flux<T> cache(int history, Duration ttl, Scheduler timer) {
        return this.replay(history, ttl, timer).autoConnect();
    }

    public final <E> Flux<E> cast(Class<E> clazz) {
        Objects.requireNonNull(clazz, "clazz");
        return this.map(clazz::cast);
    }

    public final Flux<T> cancelOn(Scheduler scheduler) {
        return Flux.onAssembly(new FluxCancelOn(this, scheduler));
    }

    public final Flux<T> checkpoint() {
        return this.checkpoint(null, true);
    }

    public final Flux<T> checkpoint(String description) {
        return this.checkpoint(Objects.requireNonNull(description), false);
    }

    public final Flux<T> checkpoint(@Nullable String description, boolean forceStackTrace) {
        FluxOnAssembly.AssemblySnapshot stacktrace = !forceStackTrace ? new FluxOnAssembly.CheckpointLightSnapshot(description) : new FluxOnAssembly.CheckpointHeavySnapshot(description, Traces.callSiteSupplierFactory.get());
        return new FluxOnAssembly(this, stacktrace);
    }

    public final <E> Mono<E> collect(Supplier<E> containerSupplier, BiConsumer<E, ? super T> collector) {
        return Mono.onAssembly(new MonoCollect<T, E>(this, containerSupplier, collector));
    }

    public final <R, A> Mono<R> collect(Collector<? super T, A, ? extends R> collector) {
        return Mono.onAssembly(new MonoStreamCollector<T, A, R>(this, collector));
    }

    public final Mono<List<T>> collectList() {
        if (this instanceof Callable) {
            if (this instanceof Fuseable.ScalarCallable) {
                Object v;
                Fuseable.ScalarCallable scalarCallable = (Fuseable.ScalarCallable)((Object)this);
                try {
                    v = scalarCallable.call();
                }
                catch (Exception e) {
                    return Mono.error(Exceptions.unwrap(e));
                }
                return Mono.onAssembly(new MonoCallable<List>(() -> {
                    List list = Flux.listSupplier().get();
                    if (v != null) {
                        list.add(v);
                    }
                    return list;
                }));
            }
            Callable thiz = (Callable)((Object)this);
            return Mono.onAssembly(new MonoCallable<List>(() -> {
                List list = Flux.listSupplier().get();
                Object u = thiz.call();
                if (u != null) {
                    list.add(u);
                }
                return list;
            }));
        }
        return Mono.onAssembly(new MonoCollectList(this));
    }

    public final <K> Mono<Map<K, T>> collectMap(Function<? super T, ? extends K> keyExtractor) {
        return this.collectMap(keyExtractor, Flux.identityFunction());
    }

    public final <K, V> Mono<Map<K, V>> collectMap(Function<? super T, ? extends K> keyExtractor, Function<? super T, ? extends V> valueExtractor) {
        return this.collectMap(keyExtractor, valueExtractor, () -> new HashMap());
    }

    public final <K, V> Mono<Map<K, V>> collectMap(Function<? super T, ? extends K> keyExtractor, Function<? super T, ? extends V> valueExtractor, Supplier<Map<K, V>> mapSupplier) {
        Objects.requireNonNull(keyExtractor, "Key extractor is null");
        Objects.requireNonNull(valueExtractor, "Value extractor is null");
        Objects.requireNonNull(mapSupplier, "Map supplier is null");
        return this.collect(mapSupplier, (m, d) -> m.put(keyExtractor.apply(d), valueExtractor.apply(d)));
    }

    public final <K> Mono<Map<K, Collection<T>>> collectMultimap(Function<? super T, ? extends K> keyExtractor) {
        return this.collectMultimap(keyExtractor, Flux.identityFunction());
    }

    public final <K, V> Mono<Map<K, Collection<V>>> collectMultimap(Function<? super T, ? extends K> keyExtractor, Function<? super T, ? extends V> valueExtractor) {
        return this.collectMultimap(keyExtractor, valueExtractor, () -> new HashMap());
    }

    public final <K, V> Mono<Map<K, Collection<V>>> collectMultimap(Function<? super T, ? extends K> keyExtractor, Function<? super T, ? extends V> valueExtractor, Supplier<Map<K, Collection<V>>> mapSupplier) {
        Objects.requireNonNull(keyExtractor, "Key extractor is null");
        Objects.requireNonNull(valueExtractor, "Value extractor is null");
        Objects.requireNonNull(mapSupplier, "Map supplier is null");
        return this.collect(mapSupplier, (m, d) -> {
            Object key = keyExtractor.apply(d);
            Collection values = m.computeIfAbsent(key, k -> new ArrayList());
            values.add(valueExtractor.apply(d));
        });
    }

    public final Mono<List<T>> collectSortedList() {
        return this.collectSortedList(null);
    }

    public final Mono<List<T>> collectSortedList(@Nullable Comparator<? super T> comparator) {
        return this.collectList().doOnNext(list -> list.sort(comparator));
    }

    public final <V> Flux<V> concatMap(Function<? super T, ? extends Publisher<? extends V>> mapper) {
        return this.concatMap(mapper, Queues.XS_BUFFER_SIZE);
    }

    public final <V> Flux<V> concatMap(Function<? super T, ? extends Publisher<? extends V>> mapper, int prefetch) {
        if (prefetch == 0) {
            return Flux.onAssembly(new FluxConcatMapNoPrefetch(this, mapper, FluxConcatMap.ErrorMode.IMMEDIATE));
        }
        return Flux.onAssembly(new FluxConcatMap(this, mapper, Queues.get(prefetch), prefetch, FluxConcatMap.ErrorMode.IMMEDIATE));
    }

    public final <V> Flux<V> concatMapDelayError(Function<? super T, ? extends Publisher<? extends V>> mapper) {
        return this.concatMapDelayError(mapper, Queues.XS_BUFFER_SIZE);
    }

    public final <V> Flux<V> concatMapDelayError(Function<? super T, ? extends Publisher<? extends V>> mapper, int prefetch) {
        return this.concatMapDelayError(mapper, true, prefetch);
    }

    public final <V> Flux<V> concatMapDelayError(Function<? super T, ? extends Publisher<? extends V>> mapper, boolean delayUntilEnd, int prefetch) {
        FluxConcatMap.ErrorMode errorMode;
        FluxConcatMap.ErrorMode errorMode2 = errorMode = delayUntilEnd ? FluxConcatMap.ErrorMode.END : FluxConcatMap.ErrorMode.BOUNDARY;
        if (prefetch == 0) {
            return Flux.onAssembly(new FluxConcatMapNoPrefetch(this, mapper, errorMode));
        }
        return Flux.onAssembly(new FluxConcatMap(this, mapper, Queues.get(prefetch), prefetch, errorMode));
    }

    public final <R> Flux<R> concatMapIterable(Function<? super T, ? extends Iterable<? extends R>> mapper) {
        return this.concatMapIterable(mapper, Queues.XS_BUFFER_SIZE);
    }

    public final <R> Flux<R> concatMapIterable(Function<? super T, ? extends Iterable<? extends R>> mapper, int prefetch) {
        return Flux.onAssembly(new FluxFlattenIterable(this, mapper, prefetch, Queues.get(prefetch)));
    }

    public final Flux<T> concatWith(Publisher<? extends T> other) {
        if (this instanceof FluxConcatArray) {
            FluxConcatArray fluxConcatArray = (FluxConcatArray)this;
            return fluxConcatArray.concatAdditionalSourceLast(other);
        }
        return Flux.concat(this, other);
    }

    public final Flux<T> contextWrite(ContextView contextToAppend) {
        return this.contextWrite((Context c) -> c.putAll(contextToAppend));
    }

    public final Flux<T> contextWrite(Function<Context, Context> contextModifier) {
        return Flux.onAssembly(new FluxContextWrite(this, contextModifier));
    }

    public final Mono<Long> count() {
        return Mono.onAssembly(new MonoCount(this));
    }

    public final Flux<T> defaultIfEmpty(T defaultV) {
        return Flux.onAssembly(new FluxDefaultIfEmpty<T>(this, defaultV));
    }

    public final Flux<T> delayElements(Duration delay) {
        return this.delayElements(delay, Schedulers.parallel());
    }

    public final Flux<T> delayElements(Duration delay, Scheduler timer) {
        return this.delayUntil(d -> Mono.delay(delay, timer));
    }

    public final Flux<T> delaySequence(Duration delay) {
        return this.delaySequence(delay, Schedulers.parallel());
    }

    public final Flux<T> delaySequence(Duration delay, Scheduler timer) {
        return Flux.onAssembly(new FluxDelaySequence(this, delay, timer));
    }

    public final Flux<T> delayUntil(Function<? super T, ? extends Publisher<?>> triggerProvider) {
        return this.concatMap(v -> Mono.just(v).delayUntil(triggerProvider));
    }

    public final Flux<T> delaySubscription(Duration delay) {
        return this.delaySubscription(delay, Schedulers.parallel());
    }

    public final Flux<T> delaySubscription(Duration delay, Scheduler timer) {
        return this.delaySubscription(Mono.delay(delay, timer));
    }

    public final <U> Flux<T> delaySubscription(Publisher<U> subscriptionDelay) {
        return Flux.onAssembly(new FluxDelaySubscription(this, subscriptionDelay));
    }

    public final <X> Flux<X> dematerialize() {
        Flux thiz = this;
        return Flux.onAssembly(new FluxDematerialize(thiz));
    }

    public final Flux<T> distinct() {
        return this.distinct(Flux.identityFunction());
    }

    public final <V> Flux<T> distinct(Function<? super T, ? extends V> keySelector) {
        return this.distinct(keySelector, Flux.hashSetSupplier());
    }

    public final <V, C extends Collection<? super V>> Flux<T> distinct(Function<? super T, ? extends V> keySelector, Supplier<C> distinctCollectionSupplier) {
        return this.distinct(keySelector, distinctCollectionSupplier, Collection::add, Collection::clear);
    }

    public final <V, C> Flux<T> distinct(Function<? super T, ? extends V> keySelector, Supplier<C> distinctStoreSupplier, BiPredicate<C, V> distinctPredicate, Consumer<C> cleanup) {
        if (this instanceof Fuseable) {
            return Flux.onAssembly(new FluxDistinctFuseable<T, V, C>(this, keySelector, distinctStoreSupplier, distinctPredicate, cleanup));
        }
        return Flux.onAssembly(new FluxDistinct<T, V, C>(this, keySelector, distinctStoreSupplier, distinctPredicate, cleanup));
    }

    public final Flux<T> distinctUntilChanged() {
        return this.distinctUntilChanged(Flux.identityFunction());
    }

    public final <V> Flux<T> distinctUntilChanged(Function<? super T, ? extends V> keySelector) {
        return this.distinctUntilChanged(keySelector, Flux.equalPredicate());
    }

    public final <V> Flux<T> distinctUntilChanged(Function<? super T, ? extends V> keySelector, BiPredicate<? super V, ? super V> keyComparator) {
        return Flux.onAssembly(new FluxDistinctUntilChanged<T, V>(this, keySelector, keyComparator));
    }

    public final Flux<T> doAfterTerminate(Runnable afterTerminate) {
        Objects.requireNonNull(afterTerminate, "afterTerminate");
        return Flux.doOnSignal(this, null, null, null, null, afterTerminate, null, null);
    }

    public final Flux<T> doOnCancel(Runnable onCancel) {
        Objects.requireNonNull(onCancel, "onCancel");
        return Flux.doOnSignal(this, null, null, null, null, null, null, onCancel);
    }

    public final Flux<T> doOnComplete(Runnable onComplete) {
        Objects.requireNonNull(onComplete, "onComplete");
        return Flux.doOnSignal(this, null, null, null, onComplete, null, null, null);
    }

    public final <R> Flux<T> doOnDiscard(Class<R> type, Consumer<? super R> discardHook) {
        return this.subscriberContext(Operators.discardLocalAdapter(type, discardHook));
    }

    public final Flux<T> doOnEach(Consumer<? super Signal<T>> signalConsumer) {
        if (this instanceof Fuseable) {
            return Flux.onAssembly(new FluxDoOnEachFuseable(this, signalConsumer));
        }
        return Flux.onAssembly(new FluxDoOnEach(this, signalConsumer));
    }

    public final Flux<T> doOnError(Consumer<? super Throwable> onError) {
        Objects.requireNonNull(onError, "onError");
        return Flux.doOnSignal(this, null, null, onError, null, null, null, null);
    }

    public final <E extends Throwable> Flux<T> doOnError(Class<E> exceptionType, Consumer<? super E> onError) {
        Objects.requireNonNull(exceptionType, "type");
        Consumer<? super E> handler = onError;
        return this.doOnError(exceptionType::isInstance, handler);
    }

    public final Flux<T> doOnError(Predicate<? super Throwable> predicate, Consumer<? super Throwable> onError) {
        Objects.requireNonNull(predicate, "predicate");
        return this.doOnError(t -> {
            if (predicate.test((Throwable)t)) {
                onError.accept((Throwable)t);
            }
        });
    }

    public final Flux<T> doOnNext(Consumer<? super T> onNext) {
        Objects.requireNonNull(onNext, "onNext");
        return Flux.doOnSignal(this, null, onNext, null, null, null, null, null);
    }

    public final Flux<T> doOnRequest(LongConsumer consumer) {
        Objects.requireNonNull(consumer, "consumer");
        return Flux.doOnSignal(this, null, null, null, null, null, consumer, null);
    }

    public final Flux<T> doOnSubscribe(Consumer<? super Subscription> onSubscribe) {
        Objects.requireNonNull(onSubscribe, "onSubscribe");
        return Flux.doOnSignal(this, onSubscribe, null, null, null, null, null, null);
    }

    public final Flux<T> doOnTerminate(Runnable onTerminate) {
        Objects.requireNonNull(onTerminate, "onTerminate");
        return Flux.doOnSignal(this, null, null, e -> onTerminate.run(), onTerminate, null, null, null);
    }

    public final Flux<T> doFirst(Runnable onFirst) {
        Objects.requireNonNull(onFirst, "onFirst");
        if (this instanceof Fuseable) {
            return Flux.onAssembly(new FluxDoFirstFuseable(this, onFirst));
        }
        return Flux.onAssembly(new FluxDoFirst(this, onFirst));
    }

    public final Flux<T> doFinally(Consumer<SignalType> onFinally) {
        Objects.requireNonNull(onFinally, "onFinally");
        return Flux.onAssembly(new FluxDoFinally(this, onFinally));
    }

    public final Flux<Tuple2<Long, T>> elapsed() {
        return this.elapsed(Schedulers.parallel());
    }

    public final Flux<Tuple2<Long, T>> elapsed(Scheduler scheduler) {
        Objects.requireNonNull(scheduler, "scheduler");
        return Flux.onAssembly(new FluxElapsed(this, scheduler));
    }

    public final Mono<T> elementAt(int index) {
        return Mono.onAssembly(new MonoElementAt(this, index));
    }

    public final Mono<T> elementAt(int index, T defaultValue) {
        return Mono.onAssembly(new MonoElementAt<T>(this, index, defaultValue));
    }

    public final Flux<T> expandDeep(Function<? super T, ? extends Publisher<? extends T>> expander, int capacityHint) {
        return Flux.onAssembly(new FluxExpand<T>(this, expander, false, capacityHint));
    }

    public final Flux<T> expandDeep(Function<? super T, ? extends Publisher<? extends T>> expander) {
        return this.expandDeep(expander, Queues.SMALL_BUFFER_SIZE);
    }

    public final Flux<T> expand(Function<? super T, ? extends Publisher<? extends T>> expander, int capacityHint) {
        return Flux.onAssembly(new FluxExpand<T>(this, expander, true, capacityHint));
    }

    public final Flux<T> expand(Function<? super T, ? extends Publisher<? extends T>> expander) {
        return this.expand(expander, Queues.SMALL_BUFFER_SIZE);
    }

    public final Flux<T> filter(Predicate<? super T> p) {
        if (this instanceof Fuseable) {
            return Flux.onAssembly(new FluxFilterFuseable<T>(this, p));
        }
        return Flux.onAssembly(new FluxFilter<T>(this, p));
    }

    public final Flux<T> filterWhen(Function<? super T, ? extends Publisher<Boolean>> asyncPredicate) {
        return this.filterWhen(asyncPredicate, Queues.SMALL_BUFFER_SIZE);
    }

    public final Flux<T> filterWhen(Function<? super T, ? extends Publisher<Boolean>> asyncPredicate, int bufferSize) {
        return Flux.onAssembly(new FluxFilterWhen<T>(this, asyncPredicate, bufferSize));
    }

    public final <R> Flux<R> flatMap(Function<? super T, ? extends Publisher<? extends R>> mapper) {
        return this.flatMap(mapper, Queues.SMALL_BUFFER_SIZE, Queues.XS_BUFFER_SIZE);
    }

    public final <V> Flux<V> flatMap(Function<? super T, ? extends Publisher<? extends V>> mapper, int concurrency) {
        return this.flatMap(mapper, concurrency, Queues.XS_BUFFER_SIZE);
    }

    public final <V> Flux<V> flatMap(Function<? super T, ? extends Publisher<? extends V>> mapper, int concurrency, int prefetch) {
        return this.flatMap(mapper, false, concurrency, prefetch);
    }

    public final <V> Flux<V> flatMapDelayError(Function<? super T, ? extends Publisher<? extends V>> mapper, int concurrency, int prefetch) {
        return this.flatMap(mapper, true, concurrency, prefetch);
    }

    public final <R> Flux<R> flatMap(@Nullable Function<? super T, ? extends Publisher<? extends R>> mapperOnNext, @Nullable Function<? super Throwable, ? extends Publisher<? extends R>> mapperOnError, @Nullable Supplier<? extends Publisher<? extends R>> mapperOnComplete) {
        return Flux.onAssembly(new FluxFlatMap(new FluxMapSignal<T, Publisher<? extends R>>(this, mapperOnNext, mapperOnError, mapperOnComplete), Flux.identityFunction(), false, Queues.XS_BUFFER_SIZE, Queues.xs(), Queues.XS_BUFFER_SIZE, Queues.xs()));
    }

    public final <R> Flux<R> flatMapIterable(Function<? super T, ? extends Iterable<? extends R>> mapper) {
        return this.flatMapIterable(mapper, Queues.SMALL_BUFFER_SIZE);
    }

    public final <R> Flux<R> flatMapIterable(Function<? super T, ? extends Iterable<? extends R>> mapper, int prefetch) {
        return Flux.onAssembly(new FluxFlattenIterable(this, mapper, prefetch, Queues.get(prefetch)));
    }

    public final <R> Flux<R> flatMapSequential(Function<? super T, ? extends Publisher<? extends R>> mapper) {
        return this.flatMapSequential(mapper, Queues.SMALL_BUFFER_SIZE);
    }

    public final <R> Flux<R> flatMapSequential(Function<? super T, ? extends Publisher<? extends R>> mapper, int maxConcurrency) {
        return this.flatMapSequential(mapper, maxConcurrency, Queues.XS_BUFFER_SIZE);
    }

    public final <R> Flux<R> flatMapSequential(Function<? super T, ? extends Publisher<? extends R>> mapper, int maxConcurrency, int prefetch) {
        return this.flatMapSequential(mapper, false, maxConcurrency, prefetch);
    }

    public final <R> Flux<R> flatMapSequentialDelayError(Function<? super T, ? extends Publisher<? extends R>> mapper, int maxConcurrency, int prefetch) {
        return this.flatMapSequential(mapper, true, maxConcurrency, prefetch);
    }

    public int getPrefetch() {
        return -1;
    }

    public final <K> Flux<GroupedFlux<K, T>> groupBy(Function<? super T, ? extends K> keyMapper) {
        return this.groupBy(keyMapper, Flux.identityFunction());
    }

    public final <K> Flux<GroupedFlux<K, T>> groupBy(Function<? super T, ? extends K> keyMapper, int prefetch) {
        return this.groupBy(keyMapper, Flux.identityFunction(), prefetch);
    }

    public final <K, V> Flux<GroupedFlux<K, V>> groupBy(Function<? super T, ? extends K> keyMapper, Function<? super T, ? extends V> valueMapper) {
        return this.groupBy(keyMapper, valueMapper, Queues.SMALL_BUFFER_SIZE);
    }

    public final <K, V> Flux<GroupedFlux<K, V>> groupBy(Function<? super T, ? extends K> keyMapper, Function<? super T, ? extends V> valueMapper, int prefetch) {
        return Flux.onAssembly(new FluxGroupBy<T, K, V>(this, keyMapper, valueMapper, Queues.unbounded(prefetch), Queues.unbounded(prefetch), prefetch));
    }

    public final <TRight, TLeftEnd, TRightEnd, R> Flux<R> groupJoin(Publisher<? extends TRight> other, Function<? super T, ? extends Publisher<TLeftEnd>> leftEnd, Function<? super TRight, ? extends Publisher<TRightEnd>> rightEnd, BiFunction<? super T, ? super Flux<TRight>, ? extends R> resultSelector) {
        return Flux.onAssembly(new FluxGroupJoin(this, other, leftEnd, rightEnd, resultSelector, Queues.unbounded(Queues.XS_BUFFER_SIZE), Queues.unbounded(Queues.XS_BUFFER_SIZE)));
    }

    public final <R> Flux<R> handle(BiConsumer<? super T, SynchronousSink<R>> handler) {
        if (this instanceof Fuseable) {
            return Flux.onAssembly(new FluxHandleFuseable<T, R>(this, handler));
        }
        return Flux.onAssembly(new FluxHandle<T, R>(this, handler));
    }

    public final Mono<Boolean> hasElement(T value) {
        Objects.requireNonNull(value, "value");
        return this.any(t -> Objects.equals(value, t));
    }

    public final Mono<Boolean> hasElements() {
        return Mono.onAssembly(new MonoHasElements(this));
    }

    public Flux<T> hide() {
        return new FluxHide(this);
    }

    public final Flux<Tuple2<Long, T>> index() {
        return this.index(Flux.tuple2Function());
    }

    public final <I> Flux<I> index(BiFunction<? super Long, ? super T, ? extends I> indexMapper) {
        if (this instanceof Fuseable) {
            return Flux.onAssembly(new FluxIndexFuseable<T, I>(this, indexMapper));
        }
        return Flux.onAssembly(new FluxIndex<T, I>(this, indexMapper));
    }

    public final Mono<T> ignoreElements() {
        return Mono.onAssembly(new MonoIgnoreElements(this));
    }

    public final <TRight, TLeftEnd, TRightEnd, R> Flux<R> join(Publisher<? extends TRight> other, Function<? super T, ? extends Publisher<TLeftEnd>> leftEnd, Function<? super TRight, ? extends Publisher<TRightEnd>> rightEnd, BiFunction<? super T, ? super TRight, ? extends R> resultSelector) {
        return Flux.onAssembly(new FluxJoin(this, other, leftEnd, rightEnd, resultSelector));
    }

    public final Mono<T> last() {
        if (this instanceof Callable) {
            Callable thiz = (Callable)((Object)this);
            Mono<T> callableMono = Flux.wrapToMono(thiz);
            if (callableMono == Mono.empty()) {
                return Mono.onAssembly(new MonoError(new NoSuchElementException("Flux#last() didn't observe any onNext signal from Callable flux")));
            }
            return Mono.onAssembly(callableMono);
        }
        return Mono.onAssembly(new MonoTakeLastOne(this));
    }

    public final Mono<T> last(T defaultValue) {
        if (this instanceof Callable) {
            Callable thiz = (Callable)((Object)this);
            if (thiz instanceof Fuseable.ScalarCallable) {
                Object v;
                Fuseable.ScalarCallable c = (Fuseable.ScalarCallable)thiz;
                try {
                    v = c.call();
                }
                catch (Exception e) {
                    return Mono.error(Exceptions.unwrap(e));
                }
                if (v == null) {
                    return Mono.just(defaultValue);
                }
                return Mono.just(v);
            }
            Mono.onAssembly(new MonoCallable(thiz));
        }
        return Mono.onAssembly(new MonoTakeLastOne<T>(this, defaultValue));
    }

    public final Flux<T> limitRate(int prefetchRate) {
        return Flux.onAssembly(this.publishOn(Schedulers.immediate(), prefetchRate));
    }

    public final Flux<T> limitRate(int highTide, int lowTide) {
        return Flux.onAssembly(this.publishOn(Schedulers.immediate(), true, highTide, lowTide));
    }

    @Deprecated
    public final Flux<T> limitRequest(long n) {
        return this.take(n, true);
    }

    public final Flux<T> log() {
        return this.log(null, Level.INFO, new SignalType[0]);
    }

    public final Flux<T> log(String category) {
        return this.log(category, Level.INFO, new SignalType[0]);
    }

    public final Flux<T> log(@Nullable String category, Level level, SignalType ... options) {
        return this.log(category, level, false, options);
    }

    public final Flux<T> log(@Nullable String category, Level level, boolean showOperatorLine, SignalType ... options) {
        SignalLogger log = new SignalLogger(this, category, level, showOperatorLine, options);
        if (this instanceof Fuseable) {
            return Flux.onAssembly(new FluxLogFuseable(this, log));
        }
        return Flux.onAssembly(new FluxLog(this, log));
    }

    public final Flux<T> log(Logger logger) {
        return this.log(logger, Level.INFO, false, new SignalType[0]);
    }

    public final Flux<T> log(Logger logger, Level level, boolean showOperatorLine, SignalType ... options) {
        SignalLogger log = new SignalLogger(this, "IGNORED", level, showOperatorLine, s -> logger, options);
        if (this instanceof Fuseable) {
            return Flux.onAssembly(new FluxLogFuseable(this, log));
        }
        return Flux.onAssembly(new FluxLog(this, log));
    }

    public final <V> Flux<V> map(Function<? super T, ? extends V> mapper) {
        if (this instanceof Fuseable) {
            return Flux.onAssembly(new FluxMapFuseable<T, V>(this, mapper));
        }
        return Flux.onAssembly(new FluxMap<T, V>(this, mapper));
    }

    public final <V> Flux<V> mapNotNull(Function<? super T, ? extends V> mapper) {
        return this.handle((t, sink) -> {
            Object v = mapper.apply(t);
            if (v != null) {
                sink.next(v);
            }
        });
    }

    public final Flux<Signal<T>> materialize() {
        return Flux.onAssembly(new FluxMaterialize(this));
    }

    @Deprecated
    public final Flux<T> mergeOrderedWith(Publisher<? extends T> other, Comparator<? super T> otherComparator) {
        if (this instanceof FluxMergeComparing) {
            FluxMergeComparing fluxMerge = (FluxMergeComparing)this;
            return fluxMerge.mergeAdditionalSource(other, otherComparator);
        }
        return Flux.mergeOrdered(otherComparator, this, other);
    }

    public final Flux<T> mergeComparingWith(Publisher<? extends T> other, Comparator<? super T> otherComparator) {
        if (this instanceof FluxMergeComparing) {
            FluxMergeComparing fluxMerge = (FluxMergeComparing)this;
            return fluxMerge.mergeAdditionalSource(other, otherComparator);
        }
        return Flux.mergeComparing(otherComparator, this, other);
    }

    public final Flux<T> mergeWith(Publisher<? extends T> other) {
        if (this instanceof FluxMerge) {
            FluxMerge fluxMerge = (FluxMerge)this;
            return fluxMerge.mergeAdditionalSource(other, Queues::get);
        }
        return Flux.merge(this, other);
    }

    public final Flux<T> metrics() {
        if (!Metrics.isInstrumentationAvailable()) {
            return this;
        }
        if (this instanceof Fuseable) {
            return Flux.onAssembly(new FluxMetricsFuseable(this));
        }
        return Flux.onAssembly(new FluxMetrics(this));
    }

    public final Flux<T> name(String name) {
        return FluxName.createOrAppend(this, name);
    }

    public final Mono<T> next() {
        if (this instanceof Callable) {
            Callable m = (Callable)((Object)this);
            return Mono.onAssembly(Flux.wrapToMono(m));
        }
        return Mono.onAssembly(new MonoNext(this));
    }

    public final <U> Flux<U> ofType(Class<U> clazz) {
        Objects.requireNonNull(clazz, "clazz");
        return this.filter(o -> clazz.isAssignableFrom(o.getClass())).cast(clazz);
    }

    public final Flux<T> onBackpressureBuffer() {
        return Flux.onAssembly(new FluxOnBackpressureBuffer(this, Queues.SMALL_BUFFER_SIZE, true, null));
    }

    public final Flux<T> onBackpressureBuffer(int maxSize) {
        return Flux.onAssembly(new FluxOnBackpressureBuffer(this, maxSize, false, null));
    }

    public final Flux<T> onBackpressureBuffer(int maxSize, Consumer<? super T> onOverflow) {
        Objects.requireNonNull(onOverflow, "onOverflow");
        return Flux.onAssembly(new FluxOnBackpressureBuffer<T>(this, maxSize, false, onOverflow));
    }

    public final Flux<T> onBackpressureBuffer(int maxSize, BufferOverflowStrategy bufferOverflowStrategy) {
        Objects.requireNonNull(bufferOverflowStrategy, "bufferOverflowStrategy");
        return Flux.onAssembly(new FluxOnBackpressureBufferStrategy(this, maxSize, null, bufferOverflowStrategy));
    }

    public final Flux<T> onBackpressureBuffer(int maxSize, Consumer<? super T> onBufferOverflow, BufferOverflowStrategy bufferOverflowStrategy) {
        Objects.requireNonNull(onBufferOverflow, "onBufferOverflow");
        Objects.requireNonNull(bufferOverflowStrategy, "bufferOverflowStrategy");
        return Flux.onAssembly(new FluxOnBackpressureBufferStrategy<T>(this, maxSize, onBufferOverflow, bufferOverflowStrategy));
    }

    public final Flux<T> onBackpressureBuffer(Duration ttl, int maxSize, Consumer<? super T> onBufferEviction) {
        return this.onBackpressureBuffer(ttl, maxSize, onBufferEviction, Schedulers.parallel());
    }

    public final Flux<T> onBackpressureBuffer(Duration ttl, int maxSize, Consumer<? super T> onBufferEviction, Scheduler scheduler) {
        Objects.requireNonNull(ttl, "ttl");
        Objects.requireNonNull(onBufferEviction, "onBufferEviction");
        return Flux.onAssembly(new FluxOnBackpressureBufferTimeout<T>(this, ttl, scheduler, maxSize, onBufferEviction));
    }

    public final Flux<T> onBackpressureDrop() {
        return Flux.onAssembly(new FluxOnBackpressureDrop(this));
    }

    public final Flux<T> onBackpressureDrop(Consumer<? super T> onDropped) {
        return Flux.onAssembly(new FluxOnBackpressureDrop<T>(this, onDropped));
    }

    public final Flux<T> onBackpressureError() {
        return this.onBackpressureDrop(t -> {
            throw Exceptions.failWithOverflow();
        });
    }

    public final Flux<T> onBackpressureLatest() {
        return Flux.onAssembly(new FluxOnBackpressureLatest(this));
    }

    public final Flux<T> onErrorContinue(BiConsumer<Throwable, Object> errorConsumer) {
        BiConsumer<Throwable, Object> genericConsumer = errorConsumer;
        return this.subscriberContext(Context.of("reactor.onNextError.localStrategy", OnNextFailureStrategy.resume(genericConsumer)));
    }

    public final <E extends Throwable> Flux<T> onErrorContinue(Class<E> type, BiConsumer<Throwable, Object> errorConsumer) {
        return this.onErrorContinue(type::isInstance, errorConsumer);
    }

    public final <E extends Throwable> Flux<T> onErrorContinue(Predicate<E> errorPredicate, BiConsumer<Throwable, Object> errorConsumer) {
        Predicate<Throwable> genericPredicate = errorPredicate;
        BiConsumer<Throwable, Object> genericErrorConsumer = errorConsumer;
        return this.subscriberContext(Context.of("reactor.onNextError.localStrategy", OnNextFailureStrategy.resumeIf(genericPredicate, genericErrorConsumer)));
    }

    public final Flux<T> onErrorStop() {
        return this.subscriberContext(Context.of("reactor.onNextError.localStrategy", OnNextFailureStrategy.stop()));
    }

    public final Flux<T> onErrorMap(Function<? super Throwable, ? extends Throwable> mapper) {
        return this.onErrorResume(e -> Mono.error((Throwable)mapper.apply((Throwable)e)));
    }

    public final <E extends Throwable> Flux<T> onErrorMap(Class<E> type, Function<? super E, ? extends Throwable> mapper) {
        Function<? super E, ? extends Throwable> handler = mapper;
        return this.onErrorMap(type::isInstance, handler);
    }

    public final Flux<T> onErrorMap(Predicate<? super Throwable> predicate, Function<? super Throwable, ? extends Throwable> mapper) {
        return this.onErrorResume(predicate, (? super Throwable e) -> Mono.error((Throwable)mapper.apply((Throwable)e)));
    }

    public final Flux<T> onErrorResume(Function<? super Throwable, ? extends Publisher<? extends T>> fallback) {
        return Flux.onAssembly(new FluxOnErrorResume(this, fallback));
    }

    public final <E extends Throwable> Flux<T> onErrorResume(Class<E> type, Function<? super E, ? extends Publisher<? extends T>> fallback) {
        Objects.requireNonNull(type, "type");
        Function<? super E, ? extends Publisher<? extends T>> handler = fallback;
        return this.onErrorResume(type::isInstance, handler);
    }

    public final Flux<T> onErrorResume(Predicate<? super Throwable> predicate, Function<? super Throwable, ? extends Publisher<? extends T>> fallback) {
        Objects.requireNonNull(predicate, "predicate");
        return this.onErrorResume(e -> predicate.test((Throwable)e) ? (Publisher)fallback.apply((Throwable)e) : Flux.error(e));
    }

    public final Flux<T> onErrorReturn(T fallbackValue) {
        return this.onErrorResume(t -> Flux.just(fallbackValue));
    }

    public final <E extends Throwable> Flux<T> onErrorReturn(Class<E> type, T fallbackValue) {
        return this.onErrorResume(type, (? super E t) -> Flux.just(fallbackValue));
    }

    public final Flux<T> onErrorReturn(Predicate<? super Throwable> predicate, T fallbackValue) {
        return this.onErrorResume(predicate, (? super Throwable t) -> Flux.just(fallbackValue));
    }

    public final Flux<T> onTerminateDetach() {
        return new FluxDetach(this);
    }

    public final Flux<T> or(Publisher<? extends T> other) {
        FluxFirstWithSignal orPublisher;
        FluxFirstWithSignal<? extends T> result;
        if (this instanceof FluxFirstWithSignal && (result = (orPublisher = (FluxFirstWithSignal)this).orAdditionalSource(other)) != null) {
            return result;
        }
        return Flux.firstWithSignal(this, other);
    }

    public final ParallelFlux<T> parallel() {
        return this.parallel(Schedulers.DEFAULT_POOL_SIZE);
    }

    public final ParallelFlux<T> parallel(int parallelism) {
        return this.parallel(parallelism, Queues.SMALL_BUFFER_SIZE);
    }

    public final ParallelFlux<T> parallel(int parallelism, int prefetch) {
        return ParallelFlux.from(this, parallelism, prefetch, Queues.get(prefetch));
    }

    public final ConnectableFlux<T> publish() {
        return this.publish(Queues.SMALL_BUFFER_SIZE);
    }

    public final ConnectableFlux<T> publish(int prefetch) {
        return Flux.onAssembly(new FluxPublish(this, prefetch, Queues.get(prefetch)));
    }

    public final <R> Flux<R> publish(Function<? super Flux<T>, ? extends Publisher<? extends R>> transform) {
        return this.publish(transform, Queues.SMALL_BUFFER_SIZE);
    }

    public final <R> Flux<R> publish(Function<? super Flux<T>, ? extends Publisher<? extends R>> transform, int prefetch) {
        return Flux.onAssembly(new FluxPublishMulticast(this, transform, prefetch, Queues.get(prefetch)));
    }

    @Deprecated
    public final Mono<T> publishNext() {
        return this.shareNext();
    }

    public final Flux<T> publishOn(Scheduler scheduler) {
        return this.publishOn(scheduler, Queues.SMALL_BUFFER_SIZE);
    }

    public final Flux<T> publishOn(Scheduler scheduler, int prefetch) {
        return this.publishOn(scheduler, true, prefetch);
    }

    public final Flux<T> publishOn(Scheduler scheduler, boolean delayError, int prefetch) {
        return this.publishOn(scheduler, delayError, prefetch, prefetch);
    }

    final Flux<T> publishOn(Scheduler scheduler, boolean delayError, int prefetch, int lowTide) {
        if (this instanceof Callable) {
            if (this instanceof Fuseable.ScalarCallable) {
                Fuseable.ScalarCallable s = (Fuseable.ScalarCallable)((Object)this);
                try {
                    return Flux.onAssembly(new FluxSubscribeOnValue(s.call(), scheduler));
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
            Callable c = (Callable)((Object)this);
            return Flux.onAssembly(new FluxSubscribeOnCallable(c, scheduler));
        }
        return Flux.onAssembly(new FluxPublishOn(this, scheduler, delayError, prefetch, lowTide, Queues.get(prefetch)));
    }

    public final Mono<T> reduce(BiFunction<T, T, T> aggregator) {
        if (this instanceof Callable) {
            Callable thiz = (Callable)((Object)this);
            return Mono.onAssembly(Flux.wrapToMono(thiz));
        }
        return Mono.onAssembly(new MonoReduce<T>(this, aggregator));
    }

    public final <A> Mono<A> reduce(A initial, BiFunction<A, ? super T, A> accumulator) {
        return this.reduceWith(() -> initial, accumulator);
    }

    public final <A> Mono<A> reduceWith(Supplier<A> initial, BiFunction<A, ? super T, A> accumulator) {
        return Mono.onAssembly(new MonoReduceSeed<T, A>(this, initial, accumulator));
    }

    public final Flux<T> repeat() {
        return this.repeat(ALWAYS_BOOLEAN_SUPPLIER);
    }

    public final Flux<T> repeat(BooleanSupplier predicate) {
        return Flux.onAssembly(new FluxRepeatPredicate(this, predicate));
    }

    public final Flux<T> repeat(long numRepeat) {
        if (numRepeat == 0L) {
            return this;
        }
        return Flux.onAssembly(new FluxRepeat(this, numRepeat));
    }

    public final Flux<T> repeat(long numRepeat, BooleanSupplier predicate) {
        if (numRepeat < 0L) {
            throw new IllegalArgumentException("numRepeat >= 0 required");
        }
        if (numRepeat == 0L) {
            return this;
        }
        return Flux.defer(() -> this.repeat(Flux.countingBooleanSupplier(predicate, numRepeat)));
    }

    public final Flux<T> repeatWhen(Function<Flux<Long>, ? extends Publisher<?>> repeatFactory) {
        return Flux.onAssembly(new FluxRepeatWhen(this, repeatFactory));
    }

    public final ConnectableFlux<T> replay() {
        return this.replay(Integer.MAX_VALUE);
    }

    public final ConnectableFlux<T> replay(int history) {
        if (history == 0) {
            return Flux.onAssembly(new FluxPublish(this, Queues.SMALL_BUFFER_SIZE, Queues.get(Queues.SMALL_BUFFER_SIZE)));
        }
        return Flux.onAssembly(new FluxReplay(this, history, 0L, null));
    }

    public final ConnectableFlux<T> replay(Duration ttl) {
        return this.replay(Integer.MAX_VALUE, ttl);
    }

    public final ConnectableFlux<T> replay(int history, Duration ttl) {
        return this.replay(history, ttl, Schedulers.parallel());
    }

    public final ConnectableFlux<T> replay(Duration ttl, Scheduler timer) {
        return this.replay(Integer.MAX_VALUE, ttl, timer);
    }

    public final ConnectableFlux<T> replay(int history, Duration ttl, Scheduler timer) {
        Objects.requireNonNull(timer, "timer");
        if (history == 0) {
            return Flux.onAssembly(new FluxPublish(this, Queues.SMALL_BUFFER_SIZE, Queues.get(Queues.SMALL_BUFFER_SIZE)));
        }
        return Flux.onAssembly(new FluxReplay(this, history, ttl.toNanos(), timer));
    }

    public final Flux<T> retry() {
        return this.retry(Long.MAX_VALUE);
    }

    public final Flux<T> retry(long numRetries) {
        return Flux.onAssembly(new FluxRetry(this, numRetries));
    }

    public final Flux<T> retryWhen(Retry retrySpec) {
        return Flux.onAssembly(new FluxRetryWhen(this, retrySpec));
    }

    public final Flux<T> sample(Duration timespan) {
        return this.sample(Flux.interval(timespan));
    }

    public final <U> Flux<T> sample(Publisher<U> sampler) {
        return Flux.onAssembly(new FluxSample(this, sampler));
    }

    public final Flux<T> sampleFirst(Duration timespan) {
        return this.sampleFirst((? super T t) -> Mono.delay(timespan));
    }

    public final <U> Flux<T> sampleFirst(Function<? super T, ? extends Publisher<U>> samplerFactory) {
        return Flux.onAssembly(new FluxSampleFirst(this, samplerFactory));
    }

    public final <U> Flux<T> sampleTimeout(Function<? super T, ? extends Publisher<U>> throttlerFactory) {
        return this.sampleTimeout(throttlerFactory, Queues.XS_BUFFER_SIZE);
    }

    public final <U> Flux<T> sampleTimeout(Function<? super T, ? extends Publisher<U>> throttlerFactory, int maxConcurrency) {
        return Flux.onAssembly(new FluxSampleTimeout(this, throttlerFactory, Queues.get(maxConcurrency)));
    }

    public final Flux<T> scan(BiFunction<T, T, T> accumulator) {
        return Flux.onAssembly(new FluxScan<T>(this, accumulator));
    }

    public final <A> Flux<A> scan(A initial, BiFunction<A, ? super T, A> accumulator) {
        Objects.requireNonNull(initial, "seed");
        return this.scanWith(() -> initial, accumulator);
    }

    public final <A> Flux<A> scanWith(Supplier<A> initial, BiFunction<A, ? super T, A> accumulator) {
        return Flux.onAssembly(new FluxScanSeed<T, A>(this, initial, accumulator));
    }

    public final Flux<T> share() {
        return Flux.onAssembly(new FluxRefCount(new FluxPublish(this, Queues.SMALL_BUFFER_SIZE, Queues.small()), 1));
    }

    public final Mono<T> shareNext() {
        NextProcessor nextProcessor = new NextProcessor(this);
        return Mono.onAssembly(nextProcessor);
    }

    public final Mono<T> single() {
        if (this instanceof Callable) {
            if (this instanceof Fuseable.ScalarCallable) {
                Object v;
                Fuseable.ScalarCallable scalarCallable = (Fuseable.ScalarCallable)((Object)this);
                try {
                    v = scalarCallable.call();
                }
                catch (Exception e) {
                    return Mono.error(Exceptions.unwrap(e));
                }
                if (v == null) {
                    return Mono.error(new NoSuchElementException("Source was a (constant) empty"));
                }
                return Mono.just(v);
            }
            Callable thiz = (Callable)((Object)this);
            return Mono.onAssembly(new MonoSingleCallable(thiz));
        }
        return Mono.onAssembly(new MonoSingle(this));
    }

    public final Mono<T> single(T defaultValue) {
        if (this instanceof Callable) {
            if (this instanceof Fuseable.ScalarCallable) {
                Object v;
                Fuseable.ScalarCallable scalarCallable = (Fuseable.ScalarCallable)((Object)this);
                try {
                    v = scalarCallable.call();
                }
                catch (Exception e) {
                    return Mono.error(Exceptions.unwrap(e));
                }
                if (v == null) {
                    return Mono.just(defaultValue);
                }
                return Mono.just(v);
            }
            Callable thiz = (Callable)((Object)this);
            return Mono.onAssembly(new MonoSingleCallable<T>(thiz, defaultValue));
        }
        return Mono.onAssembly(new MonoSingle<T>(this, defaultValue, false));
    }

    public final Mono<T> singleOrEmpty() {
        if (this instanceof Callable) {
            Callable thiz = (Callable)((Object)this);
            return Mono.onAssembly(Flux.wrapToMono(thiz));
        }
        return Mono.onAssembly(new MonoSingle<Object>(this, null, true));
    }

    public final Flux<T> skip(long skipped) {
        if (skipped == 0L) {
            return this;
        }
        return Flux.onAssembly(new FluxSkip(this, skipped));
    }

    public final Flux<T> skip(Duration timespan) {
        return this.skip(timespan, Schedulers.parallel());
    }

    public final Flux<T> skip(Duration timespan, Scheduler timer) {
        if (!timespan.isZero()) {
            return this.skipUntilOther(Mono.delay(timespan, timer));
        }
        return this;
    }

    public final Flux<T> skipLast(int n) {
        if (n == 0) {
            return this;
        }
        return Flux.onAssembly(new FluxSkipLast(this, n));
    }

    public final Flux<T> skipUntil(Predicate<? super T> untilPredicate) {
        return Flux.onAssembly(new FluxSkipUntil<T>(this, untilPredicate));
    }

    public final Flux<T> skipUntilOther(Publisher<?> other) {
        return Flux.onAssembly(new FluxSkipUntilOther(this, other));
    }

    public final Flux<T> skipWhile(Predicate<? super T> skipPredicate) {
        return Flux.onAssembly(new FluxSkipWhile<T>(this, skipPredicate));
    }

    public final Flux<T> sort() {
        return this.collectSortedList().flatMapIterable(Flux.identityFunction());
    }

    public final Flux<T> sort(Comparator<? super T> sortFunction) {
        return this.collectSortedList(sortFunction).flatMapIterable(Flux.identityFunction());
    }

    public final Flux<T> startWith(Iterable<? extends T> iterable) {
        return this.startWith((Publisher<? extends T>)Flux.fromIterable(iterable));
    }

    @SafeVarargs
    public final Flux<T> startWith(T ... values) {
        return this.startWith((Publisher<? extends T>)Flux.just(values));
    }

    public final Flux<T> startWith(Publisher<? extends T> publisher) {
        if (this instanceof FluxConcatArray) {
            FluxConcatArray fluxConcatArray = (FluxConcatArray)this;
            return fluxConcatArray.concatAdditionalSourceFirst(publisher);
        }
        return Flux.concat(publisher, this);
    }

    public final Disposable subscribe() {
        return this.subscribe(null, null, null);
    }

    public final Disposable subscribe(Consumer<? super T> consumer) {
        Objects.requireNonNull(consumer, "consumer");
        return this.subscribe(consumer, null, null);
    }

    public final Disposable subscribe(@Nullable Consumer<? super T> consumer, Consumer<? super Throwable> errorConsumer) {
        Objects.requireNonNull(errorConsumer, "errorConsumer");
        return this.subscribe(consumer, errorConsumer, null);
    }

    public final Disposable subscribe(@Nullable Consumer<? super T> consumer, @Nullable Consumer<? super Throwable> errorConsumer, @Nullable Runnable completeConsumer) {
        return this.subscribe(consumer, errorConsumer, completeConsumer, (Context)null);
    }

    @Deprecated
    public final Disposable subscribe(@Nullable Consumer<? super T> consumer, @Nullable Consumer<? super Throwable> errorConsumer, @Nullable Runnable completeConsumer, @Nullable Consumer<? super Subscription> subscriptionConsumer) {
        return this.subscribeWith(new LambdaSubscriber<T>(consumer, errorConsumer, completeConsumer, subscriptionConsumer, null));
    }

    public final Disposable subscribe(@Nullable Consumer<? super T> consumer, @Nullable Consumer<? super Throwable> errorConsumer, @Nullable Runnable completeConsumer, @Nullable Context initialContext) {
        return this.subscribeWith(new LambdaSubscriber<T>(consumer, errorConsumer, completeConsumer, null, initialContext));
    }

    @Override
    public final void subscribe(Subscriber<? super T> actual) {
        CorePublisher<Object> publisher = Operators.onLastAssembly(this);
        CoreSubscriber<Object> subscriber = Operators.toCoreSubscriber(actual);
        try {
            if (publisher instanceof OptimizableOperator) {
                OptimizableOperator operator = (OptimizableOperator)publisher;
                while (true) {
                    if ((subscriber = operator.subscribeOrReturn(subscriber)) == null) {
                        return;
                    }
                    OptimizableOperator newSource = operator.nextOptimizableSource();
                    if (newSource == null) {
                        publisher = operator.source();
                        break;
                    }
                    operator = newSource;
                }
            }
            publisher.subscribe(subscriber);
        }
        catch (Throwable e) {
            Operators.reportThrowInSubscribe(subscriber, e);
            return;
        }
    }

    @Override
    public abstract void subscribe(CoreSubscriber<? super T> var1);

    @Deprecated
    public final Flux<T> subscriberContext(Context mergeContext) {
        return this.subscriberContext((Context c) -> c.putAll(mergeContext.readOnly()));
    }

    @Deprecated
    public final Flux<T> subscriberContext(Function<Context, Context> doOnContext) {
        return this.contextWrite(doOnContext);
    }

    public final Flux<T> subscribeOn(Scheduler scheduler) {
        return this.subscribeOn(scheduler, true);
    }

    public final Flux<T> subscribeOn(Scheduler scheduler, boolean requestOnSeparateThread) {
        if (this instanceof Callable) {
            if (this instanceof Fuseable.ScalarCallable) {
                try {
                    Object value = ((Fuseable.ScalarCallable)((Object)this)).call();
                    return Flux.onAssembly(new FluxSubscribeOnValue(value, scheduler));
                }
                catch (Exception value) {
                    // empty catch block
                }
            }
            Callable c = (Callable)((Object)this);
            return Flux.onAssembly(new FluxSubscribeOnCallable(c, scheduler));
        }
        return Flux.onAssembly(new FluxSubscribeOn(this, scheduler, requestOnSeparateThread));
    }

    public final <E extends Subscriber<? super T>> E subscribeWith(E subscriber) {
        this.subscribe(subscriber);
        return subscriber;
    }

    public final <V> Flux<V> switchOnFirst(BiFunction<Signal<? extends T>, Flux<T>, Publisher<? extends V>> transformer) {
        return this.switchOnFirst(transformer, true);
    }

    public final <V> Flux<V> switchOnFirst(BiFunction<Signal<? extends T>, Flux<T>, Publisher<? extends V>> transformer, boolean cancelSourceOnComplete) {
        return Flux.onAssembly(new FluxSwitchOnFirst(this, transformer, cancelSourceOnComplete));
    }

    public final Flux<T> switchIfEmpty(Publisher<? extends T> alternate) {
        return Flux.onAssembly(new FluxSwitchIfEmpty<T>(this, alternate));
    }

    public final <V> Flux<V> switchMap(Function<? super T, Publisher<? extends V>> fn) {
        return this.switchMap(fn, Queues.XS_BUFFER_SIZE);
    }

    @Deprecated
    public final <V> Flux<V> switchMap(Function<? super T, Publisher<? extends V>> fn, int prefetch) {
        if (prefetch == 0) {
            return Flux.onAssembly(new FluxSwitchMapNoPrefetch<T, V>(this, fn));
        }
        return Flux.onAssembly(new FluxSwitchMap<T, V>(this, fn, Queues.unbounded(prefetch), prefetch));
    }

    public final Flux<T> tag(String key, String value) {
        return FluxName.createOrAppend(this, key, value);
    }

    public final Flux<T> take(long n) {
        return this.take(n, false);
    }

    public final Flux<T> take(long n, boolean limitRequest) {
        if (limitRequest) {
            return Flux.onAssembly(new FluxLimitRequest(this, n));
        }
        if (this instanceof Fuseable) {
            return Flux.onAssembly(new FluxTakeFuseable(this, n));
        }
        return Flux.onAssembly(new FluxTake(this, n));
    }

    public final Flux<T> take(Duration timespan) {
        return this.take(timespan, Schedulers.parallel());
    }

    public final Flux<T> take(Duration timespan, Scheduler timer) {
        if (!timespan.isZero()) {
            return this.takeUntilOther(Mono.delay(timespan, timer));
        }
        return this.take(0L);
    }

    public final Flux<T> takeLast(int n) {
        if (n == 1) {
            return Flux.onAssembly(new FluxTakeLastOne(this));
        }
        return Flux.onAssembly(new FluxTakeLast(this, n));
    }

    public final Flux<T> takeUntil(Predicate<? super T> predicate) {
        return Flux.onAssembly(new FluxTakeUntil<T>(this, predicate));
    }

    public final Flux<T> takeUntilOther(Publisher<?> other) {
        return Flux.onAssembly(new FluxTakeUntilOther(this, other));
    }

    public final Flux<T> takeWhile(Predicate<? super T> continuePredicate) {
        return Flux.onAssembly(new FluxTakeWhile<T>(this, continuePredicate));
    }

    public final Mono<Void> then() {
        MonoIgnoreElements then = new MonoIgnoreElements(this);
        return Mono.onAssembly(then);
    }

    public final <V> Mono<V> then(Mono<V> other) {
        return Mono.onAssembly(new MonoIgnoreThen<V>(new Publisher[]{this}, other));
    }

    public final Mono<Void> thenEmpty(Publisher<Void> other) {
        return this.then(Mono.fromDirect(other));
    }

    public final <V> Flux<V> thenMany(Publisher<V> other) {
        if (this instanceof FluxConcatArray) {
            FluxConcatArray fluxConcatArray = (FluxConcatArray)this;
            return fluxConcatArray.concatAdditionalIgnoredLast(other);
        }
        Flux<T> concat = Flux.concat(this.ignoreElements(), other);
        return concat;
    }

    public final Flux<Timed<T>> timed() {
        return this.timed(Schedulers.parallel());
    }

    public final Flux<Timed<T>> timed(Scheduler clock) {
        return Flux.onAssembly(new FluxTimed(this, clock));
    }

    public final Flux<T> timeout(Duration timeout) {
        return this.timeout(timeout, null, Schedulers.parallel());
    }

    public final Flux<T> timeout(Duration timeout, @Nullable Publisher<? extends T> fallback) {
        return this.timeout(timeout, fallback, Schedulers.parallel());
    }

    public final Flux<T> timeout(Duration timeout, Scheduler timer) {
        return this.timeout(timeout, null, timer);
    }

    public final Flux<T> timeout(Duration timeout, @Nullable Publisher<? extends T> fallback, Scheduler timer) {
        Mono<Long> _timer = Mono.delay(timeout, timer).onErrorReturn(0L);
        Function<Object, Publisher> rest = o -> _timer;
        if (fallback == null) {
            return this.timeout(_timer, rest, timeout.toMillis() + "ms");
        }
        return this.timeout(_timer, rest, fallback);
    }

    public final <U> Flux<T> timeout(Publisher<U> firstTimeout) {
        return this.timeout(firstTimeout, (? super T t) -> Flux.never());
    }

    public final <U, V> Flux<T> timeout(Publisher<U> firstTimeout, Function<? super T, ? extends Publisher<V>> nextTimeoutFactory) {
        return this.timeout(firstTimeout, nextTimeoutFactory, "first signal from a Publisher");
    }

    private final <U, V> Flux<T> timeout(Publisher<U> firstTimeout, Function<? super T, ? extends Publisher<V>> nextTimeoutFactory, String timeoutDescription) {
        return Flux.onAssembly(new FluxTimeout(this, firstTimeout, nextTimeoutFactory, timeoutDescription));
    }

    public final <U, V> Flux<T> timeout(Publisher<U> firstTimeout, Function<? super T, ? extends Publisher<V>> nextTimeoutFactory, Publisher<? extends T> fallback) {
        return Flux.onAssembly(new FluxTimeout(this, firstTimeout, nextTimeoutFactory, fallback));
    }

    public final Flux<Tuple2<Long, T>> timestamp() {
        return this.timestamp(Schedulers.parallel());
    }

    public final Flux<Tuple2<Long, T>> timestamp(Scheduler scheduler) {
        Objects.requireNonNull(scheduler, "scheduler");
        return this.map(d -> Tuples.of(scheduler.now(TimeUnit.MILLISECONDS), d));
    }

    public final Iterable<T> toIterable() {
        return this.toIterable(Queues.SMALL_BUFFER_SIZE);
    }

    public final Iterable<T> toIterable(int batchSize) {
        return this.toIterable(batchSize, null);
    }

    public final Iterable<T> toIterable(int batchSize, @Nullable Supplier<Queue<T>> queueProvider) {
        Supplier<Queue<Object>> provider = queueProvider == null ? Queues.get(batchSize) : () -> Hooks.wrapQueue((Queue)queueProvider.get());
        return new BlockingIterable(this, batchSize, provider);
    }

    public final Stream<T> toStream() {
        return this.toStream(Queues.SMALL_BUFFER_SIZE);
    }

    public final Stream<T> toStream(int batchSize) {
        Supplier provider = Queues.get(batchSize);
        return new BlockingIterable(this, batchSize, provider).stream();
    }

    public final <V> Flux<V> transform(Function<? super Flux<T>, ? extends Publisher<V>> transformer) {
        if (Hooks.DETECT_CONTEXT_LOSS) {
            transformer = new ContextTrackingFunctionWrapper(transformer);
        }
        return Flux.onAssembly(Flux.from(transformer.apply(this)));
    }

    public final <V> Flux<V> transformDeferred(Function<? super Flux<T>, ? extends Publisher<V>> transformer) {
        return Flux.defer(() -> {
            if (Hooks.DETECT_CONTEXT_LOSS) {
                ContextTrackingFunctionWrapper wrapper = new ContextTrackingFunctionWrapper(transformer);
                return wrapper.apply(this);
            }
            return (Publisher)transformer.apply(this);
        });
    }

    public final <V> Flux<V> transformDeferredContextual(BiFunction<? super Flux<T>, ? super ContextView, ? extends Publisher<V>> transformer) {
        return Flux.deferContextual(ctxView -> {
            if (Hooks.DETECT_CONTEXT_LOSS) {
                ContextTrackingFunctionWrapper wrapper = new ContextTrackingFunctionWrapper(publisher -> (Publisher)transformer.apply((Object)Flux.wrap(publisher), (ContextView)ctxView), transformer.toString());
                return wrapper.apply(this);
            }
            return (Publisher)transformer.apply((Flux<T>)this, (ContextView)ctxView);
        });
    }

    public final Flux<Flux<T>> window(int maxSize) {
        return Flux.onAssembly(new FluxWindow(this, maxSize, Queues.get(maxSize)));
    }

    public final Flux<Flux<T>> window(int maxSize, int skip) {
        return Flux.onAssembly(new FluxWindow(this, maxSize, skip, Queues.unbounded(Queues.XS_BUFFER_SIZE), Queues.unbounded(Queues.XS_BUFFER_SIZE)));
    }

    public final Flux<Flux<T>> window(Publisher<?> boundary) {
        return Flux.onAssembly(new FluxWindowBoundary(this, boundary, Queues.unbounded(Queues.XS_BUFFER_SIZE)));
    }

    public final Flux<Flux<T>> window(Duration windowingTimespan) {
        return this.window(windowingTimespan, Schedulers.parallel());
    }

    public final Flux<Flux<T>> window(Duration windowingTimespan, Duration openWindowEvery) {
        return this.window(windowingTimespan, openWindowEvery, Schedulers.parallel());
    }

    public final Flux<Flux<T>> window(Duration windowingTimespan, Scheduler timer) {
        return this.window(Flux.interval(windowingTimespan, timer));
    }

    public final Flux<Flux<T>> window(Duration windowingTimespan, Duration openWindowEvery, Scheduler timer) {
        if (openWindowEvery.equals(windowingTimespan)) {
            return this.window(windowingTimespan);
        }
        return this.windowWhen(Flux.interval(Duration.ZERO, openWindowEvery, timer), aLong -> Mono.delay(windowingTimespan, timer));
    }

    public final Flux<Flux<T>> windowTimeout(int maxSize, Duration maxTime) {
        return this.windowTimeout(maxSize, maxTime, Schedulers.parallel());
    }

    public final Flux<Flux<T>> windowTimeout(int maxSize, Duration maxTime, boolean fairBackpressure) {
        return this.windowTimeout(maxSize, maxTime, Schedulers.parallel(), fairBackpressure);
    }

    public final Flux<Flux<T>> windowTimeout(int maxSize, Duration maxTime, Scheduler timer) {
        return this.windowTimeout(maxSize, maxTime, timer, false);
    }

    public final Flux<Flux<T>> windowTimeout(int maxSize, Duration maxTime, Scheduler timer, boolean fairBackpressure) {
        return Flux.onAssembly(new FluxWindowTimeout(this, maxSize, maxTime.toNanos(), TimeUnit.NANOSECONDS, timer, fairBackpressure));
    }

    public final Flux<Flux<T>> windowUntil(Predicate<T> boundaryTrigger) {
        return this.windowUntil(boundaryTrigger, false);
    }

    public final Flux<Flux<T>> windowUntil(Predicate<T> boundaryTrigger, boolean cutBefore) {
        return this.windowUntil(boundaryTrigger, cutBefore, Queues.SMALL_BUFFER_SIZE);
    }

    public final Flux<Flux<T>> windowUntil(Predicate<T> boundaryTrigger, boolean cutBefore, int prefetch) {
        return Flux.onAssembly(new FluxWindowPredicate(this, Queues.unbounded(prefetch), Queues.unbounded(prefetch), prefetch, boundaryTrigger, cutBefore ? FluxBufferPredicate.Mode.UNTIL_CUT_BEFORE : FluxBufferPredicate.Mode.UNTIL));
    }

    public final <V> Flux<Flux<T>> windowUntilChanged() {
        return this.windowUntilChanged(Flux.identityFunction());
    }

    public final <V> Flux<Flux<T>> windowUntilChanged(Function<? super T, ? super V> keySelector) {
        return this.windowUntilChanged(keySelector, Flux.equalPredicate());
    }

    public final <V> Flux<Flux<T>> windowUntilChanged(Function<? super T, ? extends V> keySelector, BiPredicate<? super V, ? super V> keyComparator) {
        return Flux.defer(() -> this.windowUntil(new FluxBufferPredicate.ChangedPredicate(keySelector, keyComparator), true));
    }

    public final Flux<Flux<T>> windowWhile(Predicate<T> inclusionPredicate) {
        return this.windowWhile(inclusionPredicate, Queues.SMALL_BUFFER_SIZE);
    }

    public final Flux<Flux<T>> windowWhile(Predicate<T> inclusionPredicate, int prefetch) {
        return Flux.onAssembly(new FluxWindowPredicate(this, Queues.unbounded(prefetch), Queues.unbounded(prefetch), prefetch, inclusionPredicate, FluxBufferPredicate.Mode.WHILE));
    }

    public final <U, V> Flux<Flux<T>> windowWhen(Publisher<U> bucketOpening, Function<? super U, ? extends Publisher<V>> closeSelector) {
        return Flux.onAssembly(new FluxWindowWhen(this, bucketOpening, closeSelector, Queues.unbounded(Queues.XS_BUFFER_SIZE)));
    }

    public final <U, R> Flux<R> withLatestFrom(Publisher<? extends U> other, BiFunction<? super T, ? super U, ? extends R> resultSelector) {
        return Flux.onAssembly(new FluxWithLatestFrom<T, U, R>(this, other, resultSelector));
    }

    public final <T2> Flux<Tuple2<T, T2>> zipWith(Publisher<? extends T2> source2) {
        return this.zipWith(source2, Flux.tuple2Function());
    }

    public final <T2, V> Flux<V> zipWith(Publisher<? extends T2> source2, BiFunction<? super T, ? super T2, ? extends V> combinator) {
        FluxZip o;
        FluxZip result;
        if (this instanceof FluxZip && (result = (o = (FluxZip)this).zipAdditionalSource(source2, combinator)) != null) {
            return result;
        }
        return Flux.zip(this, source2, combinator);
    }

    public final <T2, V> Flux<V> zipWith(Publisher<? extends T2> source2, int prefetch, BiFunction<? super T, ? super T2, ? extends V> combinator) {
        return Flux.zip((? super Object[] objects) -> combinator.apply((Object)objects[0], (Object)objects[1]), prefetch, this, source2);
    }

    public final <T2> Flux<Tuple2<T, T2>> zipWith(Publisher<? extends T2> source2, int prefetch) {
        return this.zipWith(source2, prefetch, Flux.tuple2Function());
    }

    public final <T2> Flux<Tuple2<T, T2>> zipWithIterable(Iterable<? extends T2> iterable) {
        return this.zipWithIterable(iterable, Flux.tuple2Function());
    }

    public final <T2, V> Flux<V> zipWithIterable(Iterable<? extends T2> iterable, BiFunction<? super T, ? super T2, ? extends V> zipper) {
        return Flux.onAssembly(new FluxZipIterable<T, T2, V>(this, iterable, zipper));
    }

    protected static <T> Flux<T> onAssembly(Flux<T> source) {
        Function<Publisher, Publisher> hook = Hooks.onEachOperatorHook;
        if (hook != null) {
            source = (Flux)hook.apply(source);
        }
        if (Hooks.GLOBAL_TRACE) {
            FluxOnAssembly.AssemblySnapshot stacktrace = new FluxOnAssembly.AssemblySnapshot(null, Traces.callSiteSupplierFactory.get());
            source = (Flux)Hooks.addAssemblyInfo(source, stacktrace);
        }
        return source;
    }

    protected static <T> ConnectableFlux<T> onAssembly(ConnectableFlux<T> source) {
        Function<Publisher, Publisher> hook = Hooks.onEachOperatorHook;
        if (hook != null) {
            source = (ConnectableFlux)hook.apply(source);
        }
        if (Hooks.GLOBAL_TRACE) {
            FluxOnAssembly.AssemblySnapshot stacktrace = new FluxOnAssembly.AssemblySnapshot(null, Traces.callSiteSupplierFactory.get());
            source = (ConnectableFlux)Hooks.addAssemblyInfo(source, stacktrace);
        }
        return source;
    }

    public String toString() {
        return this.getClass().getSimpleName();
    }

    final <V> Flux<V> flatMap(Function<? super T, ? extends Publisher<? extends V>> mapper, boolean delayError, int concurrency, int prefetch) {
        return Flux.onAssembly(new FluxFlatMap(this, mapper, delayError, concurrency, Queues.get(concurrency), prefetch, Queues.get(prefetch)));
    }

    final <R> Flux<R> flatMapSequential(Function<? super T, ? extends Publisher<? extends R>> mapper, boolean delayError, int maxConcurrency, int prefetch) {
        return Flux.onAssembly(new FluxMergeSequential(this, mapper, maxConcurrency, prefetch, delayError ? FluxConcatMap.ErrorMode.END : FluxConcatMap.ErrorMode.IMMEDIATE));
    }

    static <T> Flux<T> doOnSignal(Flux<T> source, @Nullable Consumer<? super Subscription> onSubscribe, @Nullable Consumer<? super T> onNext, @Nullable Consumer<? super Throwable> onError, @Nullable Runnable onComplete, @Nullable Runnable onAfterTerminate, @Nullable LongConsumer onRequest, @Nullable Runnable onCancel) {
        if (source instanceof Fuseable) {
            return Flux.onAssembly(new FluxPeekFuseable<T>(source, onSubscribe, onNext, onError, onComplete, onAfterTerminate, onRequest, onCancel));
        }
        return Flux.onAssembly(new FluxPeek<T>(source, onSubscribe, onNext, onError, onComplete, onAfterTerminate, onRequest, onCancel));
    }

    static <T> Mono<T> wrapToMono(Callable<T> supplier) {
        if (supplier instanceof Fuseable.ScalarCallable) {
            Object v;
            Fuseable.ScalarCallable scalarCallable = (Fuseable.ScalarCallable)supplier;
            try {
                v = scalarCallable.call();
            }
            catch (Exception e) {
                return new MonoError(Exceptions.unwrap(e));
            }
            if (v == null) {
                return MonoEmpty.instance();
            }
            return new MonoJust(v);
        }
        return new MonoCallable<T>(supplier);
    }

    @SafeVarargs
    static <I> Flux<I> merge(int prefetch, boolean delayError, Publisher<? extends I> ... sources) {
        if (sources.length == 0) {
            return Flux.empty();
        }
        if (sources.length == 1) {
            return Flux.from(sources[0]);
        }
        return Flux.onAssembly(new FluxMerge<I>(sources, delayError, sources.length, Queues.get(sources.length), prefetch, Queues.get(prefetch)));
    }

    @SafeVarargs
    static <I> Flux<I> mergeSequential(int prefetch, boolean delayError, Publisher<? extends I> ... sources) {
        if (sources.length == 0) {
            return Flux.empty();
        }
        if (sources.length == 1) {
            return Flux.from(sources[0]);
        }
        return Flux.onAssembly(new FluxMergeSequential(new FluxArray<Publisher<? extends I>>(sources), Flux.identityFunction(), sources.length, prefetch, delayError ? FluxConcatMap.ErrorMode.END : FluxConcatMap.ErrorMode.IMMEDIATE));
    }

    static <T> Flux<T> mergeSequential(Publisher<? extends Publisher<? extends T>> sources, boolean delayError, int maxConcurrency, int prefetch) {
        return Flux.onAssembly(new FluxMergeSequential(Flux.from(sources), Flux.identityFunction(), maxConcurrency, prefetch, delayError ? FluxConcatMap.ErrorMode.END : FluxConcatMap.ErrorMode.IMMEDIATE));
    }

    static <I> Flux<I> mergeSequential(Iterable<? extends Publisher<? extends I>> sources, boolean delayError, int maxConcurrency, int prefetch) {
        return Flux.onAssembly(new FluxMergeSequential(new FluxIterable<Publisher<? extends I>>(sources), Flux.identityFunction(), maxConcurrency, prefetch, delayError ? FluxConcatMap.ErrorMode.END : FluxConcatMap.ErrorMode.IMMEDIATE));
    }

    static BooleanSupplier countingBooleanSupplier(final BooleanSupplier predicate, final long max) {
        if (max <= 0L) {
            return predicate;
        }
        return new BooleanSupplier(){
            long n;

            @Override
            public boolean getAsBoolean() {
                return this.n++ < max && predicate.getAsBoolean();
            }
        };
    }

    static <O> Predicate<O> countingPredicate(final Predicate<O> predicate, final long max) {
        if (max == 0L) {
            return predicate;
        }
        return new Predicate<O>(){
            long n;

            @Override
            public boolean test(O o) {
                return this.n++ < max && predicate.test(o);
            }
        };
    }

    static <O> Supplier<Set<O>> hashSetSupplier() {
        return SET_SUPPLIER;
    }

    static <O> Supplier<List<O>> listSupplier() {
        return LIST_SUPPLIER;
    }

    static <U, V> BiPredicate<U, V> equalPredicate() {
        return OBJECT_EQUAL;
    }

    static <T> Function<T, T> identityFunction() {
        return IDENTITY_FUNCTION;
    }

    static <A, B> BiFunction<A, B, Tuple2<A, B>> tuple2Function() {
        return TUPLE2_BIFUNCTION;
    }

    static <I> Flux<I> wrap(Publisher<? extends I> source) {
        if (source instanceof Flux) {
            return (Flux)source;
        }
        if (source instanceof Fuseable.ScalarCallable) {
            try {
                Object t = ((Fuseable.ScalarCallable)source).call();
                if (t != null) {
                    return new FluxJust(t);
                }
                return FluxEmpty.instance();
            }
            catch (Exception e) {
                return new FluxError(Exceptions.unwrap(e));
            }
        }
        if (source instanceof Mono) {
            if (source instanceof Fuseable) {
                return new FluxSourceMonoFuseable((Mono)source);
            }
            return new FluxSourceMono((Mono)source);
        }
        if (source instanceof Fuseable) {
            return new FluxSourceFuseable<I>(source);
        }
        return new FluxSource<I>(source);
    }
}

