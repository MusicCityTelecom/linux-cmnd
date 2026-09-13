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
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
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
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.CorePublisher;
import reactor.core.CoreSubscriber;
import reactor.core.Disposable;
import reactor.core.Exceptions;
import reactor.core.Fuseable;
import reactor.core.publisher.BlockingMonoSubscriber;
import reactor.core.publisher.BlockingOptionalMonoSubscriber;
import reactor.core.publisher.ContextTrackingFunctionWrapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxCallable;
import reactor.core.publisher.FluxFromMonoOperator;
import reactor.core.publisher.FluxOnAssembly;
import reactor.core.publisher.FluxSourceMono;
import reactor.core.publisher.FluxSourceMonoFuseable;
import reactor.core.publisher.Hooks;
import reactor.core.publisher.LambdaMonoSubscriber;
import reactor.core.publisher.MonoCacheInvalidateIf;
import reactor.core.publisher.MonoCacheInvalidateWhen;
import reactor.core.publisher.MonoCacheTime;
import reactor.core.publisher.MonoCallable;
import reactor.core.publisher.MonoCancelOn;
import reactor.core.publisher.MonoCompletionStage;
import reactor.core.publisher.MonoContextWrite;
import reactor.core.publisher.MonoCreate;
import reactor.core.publisher.MonoCurrentContext;
import reactor.core.publisher.MonoDefaultIfEmpty;
import reactor.core.publisher.MonoDefer;
import reactor.core.publisher.MonoDeferContextual;
import reactor.core.publisher.MonoDelay;
import reactor.core.publisher.MonoDelayElement;
import reactor.core.publisher.MonoDelaySubscription;
import reactor.core.publisher.MonoDelayUntil;
import reactor.core.publisher.MonoDematerialize;
import reactor.core.publisher.MonoDetach;
import reactor.core.publisher.MonoDoFinally;
import reactor.core.publisher.MonoDoFirst;
import reactor.core.publisher.MonoDoFirstFuseable;
import reactor.core.publisher.MonoDoOnEach;
import reactor.core.publisher.MonoDoOnEachFuseable;
import reactor.core.publisher.MonoElapsed;
import reactor.core.publisher.MonoEmpty;
import reactor.core.publisher.MonoError;
import reactor.core.publisher.MonoErrorSupplied;
import reactor.core.publisher.MonoExpand;
import reactor.core.publisher.MonoFilter;
import reactor.core.publisher.MonoFilterFuseable;
import reactor.core.publisher.MonoFilterWhen;
import reactor.core.publisher.MonoFirstWithSignal;
import reactor.core.publisher.MonoFirstWithValue;
import reactor.core.publisher.MonoFlatMap;
import reactor.core.publisher.MonoFlatMapMany;
import reactor.core.publisher.MonoFlattenIterable;
import reactor.core.publisher.MonoFromPublisher;
import reactor.core.publisher.MonoHandle;
import reactor.core.publisher.MonoHandleFuseable;
import reactor.core.publisher.MonoHasElement;
import reactor.core.publisher.MonoHide;
import reactor.core.publisher.MonoIgnoreElement;
import reactor.core.publisher.MonoIgnorePublisher;
import reactor.core.publisher.MonoIgnoreThen;
import reactor.core.publisher.MonoJust;
import reactor.core.publisher.MonoLog;
import reactor.core.publisher.MonoLogFuseable;
import reactor.core.publisher.MonoMap;
import reactor.core.publisher.MonoMapFuseable;
import reactor.core.publisher.MonoMaterialize;
import reactor.core.publisher.MonoMetrics;
import reactor.core.publisher.MonoMetricsFuseable;
import reactor.core.publisher.MonoName;
import reactor.core.publisher.MonoNever;
import reactor.core.publisher.MonoNext;
import reactor.core.publisher.MonoOnAssembly;
import reactor.core.publisher.MonoOnErrorResume;
import reactor.core.publisher.MonoPeek;
import reactor.core.publisher.MonoPeekFuseable;
import reactor.core.publisher.MonoPeekTerminal;
import reactor.core.publisher.MonoProcessor;
import reactor.core.publisher.MonoPublishMulticast;
import reactor.core.publisher.MonoPublishOn;
import reactor.core.publisher.MonoRepeat;
import reactor.core.publisher.MonoRepeatPredicate;
import reactor.core.publisher.MonoRepeatWhen;
import reactor.core.publisher.MonoRetry;
import reactor.core.publisher.MonoRetryWhen;
import reactor.core.publisher.MonoRunnable;
import reactor.core.publisher.MonoSequenceEqual;
import reactor.core.publisher.MonoSingleCallable;
import reactor.core.publisher.MonoSingleMono;
import reactor.core.publisher.MonoSink;
import reactor.core.publisher.MonoSource;
import reactor.core.publisher.MonoSourceFlux;
import reactor.core.publisher.MonoSourceFluxFuseable;
import reactor.core.publisher.MonoSourceFuseable;
import reactor.core.publisher.MonoSubscribeOn;
import reactor.core.publisher.MonoSubscribeOnCallable;
import reactor.core.publisher.MonoSubscribeOnValue;
import reactor.core.publisher.MonoSupplier;
import reactor.core.publisher.MonoSwitchIfEmpty;
import reactor.core.publisher.MonoTakeUntilOther;
import reactor.core.publisher.MonoTimed;
import reactor.core.publisher.MonoTimeout;
import reactor.core.publisher.MonoToCompletableFuture;
import reactor.core.publisher.MonoUsing;
import reactor.core.publisher.MonoUsingWhen;
import reactor.core.publisher.MonoWhen;
import reactor.core.publisher.MonoZip;
import reactor.core.publisher.NextProcessor;
import reactor.core.publisher.OnNextFailureStrategy;
import reactor.core.publisher.Operators;
import reactor.core.publisher.OptimizableOperator;
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

public abstract class Mono<T>
implements CorePublisher<T> {
    static final BiPredicate EQUALS_BIPREDICATE = Object::equals;

    public static <T> Mono<T> create(Consumer<MonoSink<T>> callback) {
        return Mono.onAssembly(new MonoCreate<T>(callback));
    }

    public static <T> Mono<T> defer(Supplier<? extends Mono<? extends T>> supplier) {
        return Mono.onAssembly(new MonoDefer(supplier));
    }

    @Deprecated
    public static <T> Mono<T> deferWithContext(Function<Context, ? extends Mono<? extends T>> contextualMonoFactory) {
        return Mono.deferContextual(view -> (Mono)contextualMonoFactory.apply(Context.of(view)));
    }

    public static <T> Mono<T> deferContextual(Function<ContextView, ? extends Mono<? extends T>> contextualMonoFactory) {
        return Mono.onAssembly(new MonoDeferContextual(contextualMonoFactory));
    }

    public static Mono<Long> delay(Duration duration) {
        return Mono.delay(duration, Schedulers.parallel());
    }

    public static Mono<Long> delay(Duration duration, Scheduler timer) {
        return Mono.onAssembly(new MonoDelay(duration.toNanos(), TimeUnit.NANOSECONDS, timer));
    }

    public static <T> Mono<T> empty() {
        return MonoEmpty.instance();
    }

    public static <T> Mono<T> error(Throwable error) {
        return Mono.onAssembly(new MonoError(error));
    }

    public static <T> Mono<T> error(Supplier<? extends Throwable> errorSupplier) {
        return Mono.onAssembly(new MonoErrorSupplied(errorSupplier));
    }

    @SafeVarargs
    @Deprecated
    public static <T> Mono<T> first(Mono<? extends T> ... monos) {
        return Mono.firstWithSignal(monos);
    }

    @Deprecated
    public static <T> Mono<T> first(Iterable<? extends Mono<? extends T>> monos) {
        return Mono.firstWithSignal(monos);
    }

    @SafeVarargs
    public static <T> Mono<T> firstWithSignal(Mono<? extends T> ... monos) {
        return Mono.onAssembly(new MonoFirstWithSignal<T>(monos));
    }

    public static <T> Mono<T> firstWithSignal(Iterable<? extends Mono<? extends T>> monos) {
        return Mono.onAssembly(new MonoFirstWithSignal(monos));
    }

    public static <T> Mono<T> firstWithValue(Iterable<? extends Mono<? extends T>> monos) {
        return Mono.onAssembly(new MonoFirstWithValue(monos));
    }

    @SafeVarargs
    public static <T> Mono<T> firstWithValue(Mono<? extends T> first, Mono<? extends T> ... others) {
        MonoFirstWithValue a;
        MonoFirstWithValue<? extends T> result;
        if (first instanceof MonoFirstWithValue && (result = (a = (MonoFirstWithValue)first).firstValuedAdditionalSources(others)) != null) {
            return result;
        }
        return Mono.onAssembly(new MonoFirstWithValue<T>(first, others));
    }

    public static <T> Mono<T> from(Publisher<? extends T> source) {
        if (source instanceof Mono) {
            Mono casted = (Mono)source;
            return casted;
        }
        if (source instanceof FluxSourceMono || source instanceof FluxSourceMonoFuseable) {
            FluxFromMonoOperator wrapper = (FluxFromMonoOperator)source;
            Mono extracted = wrapper.source;
            return extracted;
        }
        Publisher<? extends T> downcasted = source;
        return Mono.onAssembly(Mono.wrap(downcasted, true));
    }

    public static <T> Mono<T> fromCallable(Callable<? extends T> supplier) {
        return Mono.onAssembly(new MonoCallable<T>(supplier));
    }

    public static <T> Mono<T> fromCompletionStage(CompletionStage<? extends T> completionStage) {
        return Mono.onAssembly(new MonoCompletionStage<T>(completionStage));
    }

    public static <T> Mono<T> fromCompletionStage(Supplier<? extends CompletionStage<? extends T>> stageSupplier) {
        return Mono.defer(() -> Mono.onAssembly(new MonoCompletionStage((CompletionStage)stageSupplier.get())));
    }

    public static <I> Mono<I> fromDirect(Publisher<? extends I> source) {
        if (source instanceof Mono) {
            Mono m = (Mono)source;
            return m;
        }
        if (source instanceof FluxSourceMono || source instanceof FluxSourceMonoFuseable) {
            FluxFromMonoOperator wrapper = (FluxFromMonoOperator)source;
            Mono extracted = wrapper.source;
            return extracted;
        }
        Publisher<? extends I> downcasted = source;
        return Mono.onAssembly(Mono.wrap(downcasted, false));
    }

    public static <T> Mono<T> fromFuture(CompletableFuture<? extends T> future) {
        return Mono.onAssembly(new MonoCompletionStage<T>(future));
    }

    public static <T> Mono<T> fromFuture(Supplier<? extends CompletableFuture<? extends T>> futureSupplier) {
        return Mono.defer(() -> Mono.onAssembly(new MonoCompletionStage((CompletionStage)futureSupplier.get())));
    }

    public static <T> Mono<T> fromRunnable(Runnable runnable) {
        return Mono.onAssembly(new MonoRunnable(runnable));
    }

    public static <T> Mono<T> fromSupplier(Supplier<? extends T> supplier) {
        return Mono.onAssembly(new MonoSupplier<T>(supplier));
    }

    public static <T> Mono<T> ignoreElements(Publisher<T> source) {
        return Mono.onAssembly(new MonoIgnorePublisher<T>(source));
    }

    public static <T> Mono<T> just(T data) {
        return Mono.onAssembly(new MonoJust<T>(data));
    }

    public static <T> Mono<T> justOrEmpty(@Nullable Optional<? extends T> data) {
        return data != null && data.isPresent() ? Mono.just(data.get()) : Mono.empty();
    }

    public static <T> Mono<T> justOrEmpty(@Nullable T data) {
        return data != null ? Mono.just(data) : Mono.empty();
    }

    public static <T> Mono<T> never() {
        return MonoNever.instance();
    }

    public static <T> Mono<Boolean> sequenceEqual(Publisher<? extends T> source1, Publisher<? extends T> source2) {
        return Mono.sequenceEqual(source1, source2, Mono.equalsBiPredicate(), Queues.SMALL_BUFFER_SIZE);
    }

    public static <T> Mono<Boolean> sequenceEqual(Publisher<? extends T> source1, Publisher<? extends T> source2, BiPredicate<? super T, ? super T> isEqual) {
        return Mono.sequenceEqual(source1, source2, isEqual, Queues.SMALL_BUFFER_SIZE);
    }

    public static <T> Mono<Boolean> sequenceEqual(Publisher<? extends T> source1, Publisher<? extends T> source2, BiPredicate<? super T, ? super T> isEqual, int prefetch) {
        return Mono.onAssembly(new MonoSequenceEqual<T>(source1, source2, isEqual, prefetch));
    }

    @Deprecated
    public static Mono<Context> subscriberContext() {
        return Mono.onAssembly(MonoCurrentContext.INSTANCE);
    }

    public static <T, D> Mono<T> using(Callable<? extends D> resourceSupplier, Function<? super D, ? extends Mono<? extends T>> sourceSupplier, Consumer<? super D> resourceCleanup, boolean eager) {
        return Mono.onAssembly(new MonoUsing(resourceSupplier, sourceSupplier, resourceCleanup, eager));
    }

    public static <T, D> Mono<T> using(Callable<? extends D> resourceSupplier, Function<? super D, ? extends Mono<? extends T>> sourceSupplier, Consumer<? super D> resourceCleanup) {
        return Mono.using(resourceSupplier, sourceSupplier, resourceCleanup, true);
    }

    public static <T, D> Mono<T> usingWhen(Publisher<D> resourceSupplier, Function<? super D, ? extends Mono<? extends T>> resourceClosure, Function<? super D, ? extends Publisher<?>> asyncCleanup) {
        return Mono.usingWhen(resourceSupplier, resourceClosure, asyncCleanup, (res, error) -> (Publisher)asyncCleanup.apply((Object)res), asyncCleanup);
    }

    public static <T, D> Mono<T> usingWhen(Publisher<D> resourceSupplier, Function<? super D, ? extends Mono<? extends T>> resourceClosure, Function<? super D, ? extends Publisher<?>> asyncComplete, BiFunction<? super D, ? super Throwable, ? extends Publisher<?>> asyncError, Function<? super D, ? extends Publisher<?>> asyncCancel) {
        return Mono.onAssembly(new MonoUsingWhen(resourceSupplier, resourceClosure, asyncComplete, asyncError, asyncCancel));
    }

    public static Mono<Void> when(Publisher<?> ... sources) {
        if (sources.length == 0) {
            return Mono.empty();
        }
        if (sources.length == 1) {
            return Mono.empty(sources[0]);
        }
        return Mono.onAssembly(new MonoWhen(false, sources));
    }

    public static Mono<Void> when(Iterable<? extends Publisher<?>> sources) {
        return Mono.onAssembly(new MonoWhen(false, sources));
    }

    public static Mono<Void> whenDelayError(Iterable<? extends Publisher<?>> sources) {
        return Mono.onAssembly(new MonoWhen(true, sources));
    }

    public static Mono<Void> whenDelayError(Publisher<?> ... sources) {
        if (sources.length == 0) {
            return Mono.empty();
        }
        if (sources.length == 1) {
            return Mono.empty(sources[0]);
        }
        return Mono.onAssembly(new MonoWhen(true, sources));
    }

    public static <T1, T2> Mono<Tuple2<T1, T2>> zip(Mono<? extends T1> p1, Mono<? extends T2> p2) {
        return Mono.zip(p1, p2, Flux.tuple2Function());
    }

    public static <T1, T2, O> Mono<O> zip(Mono<? extends T1> p1, Mono<? extends T2> p2, BiFunction<? super T1, ? super T2, ? extends O> combinator) {
        return Mono.onAssembly(new MonoZip<T1, O>(false, p1, p2, combinator));
    }

    public static <T1, T2, T3> Mono<Tuple3<T1, T2, T3>> zip(Mono<? extends T1> p1, Mono<? extends T2> p2, Mono<? extends T3> p3) {
        return Mono.onAssembly(new MonoZip(false, a -> Tuples.fromArray((Object[])a), p1, p2, p3));
    }

    public static <T1, T2, T3, T4> Mono<Tuple4<T1, T2, T3, T4>> zip(Mono<? extends T1> p1, Mono<? extends T2> p2, Mono<? extends T3> p3, Mono<? extends T4> p4) {
        return Mono.onAssembly(new MonoZip(false, a -> Tuples.fromArray((Object[])a), p1, p2, p3, p4));
    }

    public static <T1, T2, T3, T4, T5> Mono<Tuple5<T1, T2, T3, T4, T5>> zip(Mono<? extends T1> p1, Mono<? extends T2> p2, Mono<? extends T3> p3, Mono<? extends T4> p4, Mono<? extends T5> p5) {
        return Mono.onAssembly(new MonoZip(false, a -> Tuples.fromArray((Object[])a), p1, p2, p3, p4, p5));
    }

    public static <T1, T2, T3, T4, T5, T6> Mono<Tuple6<T1, T2, T3, T4, T5, T6>> zip(Mono<? extends T1> p1, Mono<? extends T2> p2, Mono<? extends T3> p3, Mono<? extends T4> p4, Mono<? extends T5> p5, Mono<? extends T6> p6) {
        return Mono.onAssembly(new MonoZip(false, a -> Tuples.fromArray((Object[])a), p1, p2, p3, p4, p5, p6));
    }

    public static <T1, T2, T3, T4, T5, T6, T7> Mono<Tuple7<T1, T2, T3, T4, T5, T6, T7>> zip(Mono<? extends T1> p1, Mono<? extends T2> p2, Mono<? extends T3> p3, Mono<? extends T4> p4, Mono<? extends T5> p5, Mono<? extends T6> p6, Mono<? extends T7> p7) {
        return Mono.onAssembly(new MonoZip(false, a -> Tuples.fromArray((Object[])a), p1, p2, p3, p4, p5, p6, p7));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8> Mono<Tuple8<T1, T2, T3, T4, T5, T6, T7, T8>> zip(Mono<? extends T1> p1, Mono<? extends T2> p2, Mono<? extends T3> p3, Mono<? extends T4> p4, Mono<? extends T5> p5, Mono<? extends T6> p6, Mono<? extends T7> p7, Mono<? extends T8> p8) {
        return Mono.onAssembly(new MonoZip(false, a -> Tuples.fromArray((Object[])a), p1, p2, p3, p4, p5, p6, p7, p8));
    }

    public static <R> Mono<R> zip(Iterable<? extends Mono<?>> monos, Function<? super Object[], ? extends R> combinator) {
        return Mono.onAssembly(new MonoZip(false, combinator, monos));
    }

    public static <R> Mono<R> zip(Function<? super Object[], ? extends R> combinator, Mono<?> ... monos) {
        if (monos.length == 0) {
            return Mono.empty();
        }
        if (monos.length == 1) {
            return monos[0].map(d -> combinator.apply(new Object[]{d}));
        }
        return Mono.onAssembly(new MonoZip(false, combinator, monos));
    }

    public static <T1, T2> Mono<Tuple2<T1, T2>> zipDelayError(Mono<? extends T1> p1, Mono<? extends T2> p2) {
        return Mono.onAssembly(new MonoZip(true, a -> Tuples.fromArray((Object[])a), p1, p2));
    }

    public static <T1, T2, T3> Mono<Tuple3<T1, T2, T3>> zipDelayError(Mono<? extends T1> p1, Mono<? extends T2> p2, Mono<? extends T3> p3) {
        return Mono.onAssembly(new MonoZip(true, a -> Tuples.fromArray((Object[])a), p1, p2, p3));
    }

    public static <T1, T2, T3, T4> Mono<Tuple4<T1, T2, T3, T4>> zipDelayError(Mono<? extends T1> p1, Mono<? extends T2> p2, Mono<? extends T3> p3, Mono<? extends T4> p4) {
        return Mono.onAssembly(new MonoZip(true, a -> Tuples.fromArray((Object[])a), p1, p2, p3, p4));
    }

    public static <T1, T2, T3, T4, T5> Mono<Tuple5<T1, T2, T3, T4, T5>> zipDelayError(Mono<? extends T1> p1, Mono<? extends T2> p2, Mono<? extends T3> p3, Mono<? extends T4> p4, Mono<? extends T5> p5) {
        return Mono.onAssembly(new MonoZip(true, a -> Tuples.fromArray((Object[])a), p1, p2, p3, p4, p5));
    }

    public static <T1, T2, T3, T4, T5, T6> Mono<Tuple6<T1, T2, T3, T4, T5, T6>> zipDelayError(Mono<? extends T1> p1, Mono<? extends T2> p2, Mono<? extends T3> p3, Mono<? extends T4> p4, Mono<? extends T5> p5, Mono<? extends T6> p6) {
        return Mono.onAssembly(new MonoZip(true, a -> Tuples.fromArray((Object[])a), p1, p2, p3, p4, p5, p6));
    }

    public static <T1, T2, T3, T4, T5, T6, T7> Mono<Tuple7<T1, T2, T3, T4, T5, T6, T7>> zipDelayError(Mono<? extends T1> p1, Mono<? extends T2> p2, Mono<? extends T3> p3, Mono<? extends T4> p4, Mono<? extends T5> p5, Mono<? extends T6> p6, Mono<? extends T7> p7) {
        return Mono.onAssembly(new MonoZip(true, a -> Tuples.fromArray((Object[])a), p1, p2, p3, p4, p5, p6, p7));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8> Mono<Tuple8<T1, T2, T3, T4, T5, T6, T7, T8>> zipDelayError(Mono<? extends T1> p1, Mono<? extends T2> p2, Mono<? extends T3> p3, Mono<? extends T4> p4, Mono<? extends T5> p5, Mono<? extends T6> p6, Mono<? extends T7> p7, Mono<? extends T8> p8) {
        return Mono.onAssembly(new MonoZip(true, a -> Tuples.fromArray((Object[])a), p1, p2, p3, p4, p5, p6, p7, p8));
    }

    public static <R> Mono<R> zipDelayError(Iterable<? extends Mono<?>> monos, Function<? super Object[], ? extends R> combinator) {
        return Mono.onAssembly(new MonoZip(true, combinator, monos));
    }

    public static <R> Mono<R> zipDelayError(Function<? super Object[], ? extends R> combinator, Mono<?> ... monos) {
        if (monos.length == 0) {
            return Mono.empty();
        }
        if (monos.length == 1) {
            return monos[0].map(d -> combinator.apply(new Object[]{d}));
        }
        return Mono.onAssembly(new MonoZip(true, combinator, monos));
    }

    public final <P> P as(Function<? super Mono<T>, P> transformer) {
        return transformer.apply(this);
    }

    public final Mono<Void> and(Publisher<?> other) {
        MonoWhen o;
        Mono<Void> result;
        if (this instanceof MonoWhen && (result = (o = (MonoWhen)this).whenAdditionalSource(other)) != null) {
            return result;
        }
        return Mono.when(this, other);
    }

    @Nullable
    public T block() {
        BlockingMonoSubscriber subscriber = new BlockingMonoSubscriber();
        this.subscribe(subscriber);
        return subscriber.blockingGet();
    }

    @Nullable
    public T block(Duration timeout) {
        BlockingMonoSubscriber subscriber = new BlockingMonoSubscriber();
        this.subscribe(subscriber);
        return subscriber.blockingGet(timeout.toNanos(), TimeUnit.NANOSECONDS);
    }

    public Optional<T> blockOptional() {
        BlockingOptionalMonoSubscriber subscriber = new BlockingOptionalMonoSubscriber();
        this.subscribe(subscriber);
        return subscriber.blockingGet();
    }

    public Optional<T> blockOptional(Duration timeout) {
        BlockingOptionalMonoSubscriber subscriber = new BlockingOptionalMonoSubscriber();
        this.subscribe(subscriber);
        return subscriber.blockingGet(timeout.toNanos(), TimeUnit.NANOSECONDS);
    }

    public final <E> Mono<E> cast(Class<E> clazz) {
        Objects.requireNonNull(clazz, "clazz");
        return this.map(clazz::cast);
    }

    public final Mono<T> cache() {
        return Mono.onAssembly(new MonoCacheTime(this));
    }

    public final Mono<T> cache(Duration ttl) {
        return this.cache(ttl, Schedulers.parallel());
    }

    public final Mono<T> cache(Duration ttl, Scheduler timer) {
        return Mono.onAssembly(new MonoCacheTime(this, ttl, timer));
    }

    public final Mono<T> cache(Function<? super T, Duration> ttlForValue, Function<Throwable, Duration> ttlForError, Supplier<Duration> ttlForEmpty) {
        return this.cache(ttlForValue, ttlForError, ttlForEmpty, Schedulers.parallel());
    }

    public final Mono<T> cache(Function<? super T, Duration> ttlForValue, Function<Throwable, Duration> ttlForError, Supplier<Duration> ttlForEmpty, Scheduler timer) {
        return Mono.onAssembly(new MonoCacheTime<T>(this, ttlForValue, ttlForError, ttlForEmpty, timer));
    }

    public final Mono<T> cacheInvalidateIf(Predicate<? super T> invalidationPredicate) {
        return Mono.onAssembly(new MonoCacheInvalidateIf<T>(this, invalidationPredicate));
    }

    public final Mono<T> cacheInvalidateWhen(Function<? super T, Mono<Void>> invalidationTriggerGenerator) {
        return Mono.onAssembly(new MonoCacheInvalidateWhen<T>(this, invalidationTriggerGenerator, null));
    }

    public final Mono<T> cacheInvalidateWhen(Function<? super T, Mono<Void>> invalidationTriggerGenerator, Consumer<? super T> onInvalidate) {
        return Mono.onAssembly(new MonoCacheInvalidateWhen<T>(this, invalidationTriggerGenerator, onInvalidate));
    }

    public final Mono<T> cancelOn(Scheduler scheduler) {
        return Mono.onAssembly(new MonoCancelOn(this, scheduler));
    }

    public final Mono<T> checkpoint() {
        return this.checkpoint(null, true);
    }

    public final Mono<T> checkpoint(String description) {
        return this.checkpoint(Objects.requireNonNull(description), false);
    }

    public final Mono<T> checkpoint(@Nullable String description, boolean forceStackTrace) {
        FluxOnAssembly.AssemblySnapshot stacktrace = !forceStackTrace ? new FluxOnAssembly.CheckpointLightSnapshot(description) : new FluxOnAssembly.CheckpointHeavySnapshot(description, Traces.callSiteSupplierFactory.get());
        return new MonoOnAssembly(this, stacktrace);
    }

    public final Flux<T> concatWith(Publisher<? extends T> other) {
        return Flux.concat(this, other);
    }

    public final Mono<T> contextWrite(ContextView contextToAppend) {
        return this.contextWrite((Context c) -> c.putAll(contextToAppend));
    }

    public final Mono<T> contextWrite(Function<Context, Context> contextModifier) {
        return Mono.onAssembly(new MonoContextWrite(this, contextModifier));
    }

    public final Mono<T> defaultIfEmpty(T defaultV) {
        if (this instanceof Fuseable.ScalarCallable) {
            try {
                T v = this.block();
                if (v == null) {
                    return Mono.just(defaultV);
                }
            }
            catch (Throwable throwable) {
                // empty catch block
            }
            return this;
        }
        return Mono.onAssembly(new MonoDefaultIfEmpty<T>(this, defaultV));
    }

    public final Mono<T> delayElement(Duration delay) {
        return this.delayElement(delay, Schedulers.parallel());
    }

    public final Mono<T> delayElement(Duration delay, Scheduler timer) {
        return Mono.onAssembly(new MonoDelayElement(this, delay.toNanos(), TimeUnit.NANOSECONDS, timer));
    }

    public final Mono<T> delayUntil(Function<? super T, ? extends Publisher<?>> triggerProvider) {
        Objects.requireNonNull(triggerProvider, "triggerProvider required");
        if (this instanceof MonoDelayUntil) {
            return ((MonoDelayUntil)this).copyWithNewTriggerGenerator(false, triggerProvider);
        }
        return Mono.onAssembly(new MonoDelayUntil<T>(this, triggerProvider));
    }

    public final Mono<T> delaySubscription(Duration delay) {
        return this.delaySubscription(delay, Schedulers.parallel());
    }

    public final Mono<T> delaySubscription(Duration delay, Scheduler timer) {
        return this.delaySubscription(Mono.delay(delay, timer));
    }

    public final <U> Mono<T> delaySubscription(Publisher<U> subscriptionDelay) {
        return Mono.onAssembly(new MonoDelaySubscription(this, subscriptionDelay));
    }

    public final <X> Mono<X> dematerialize() {
        Mono thiz = this;
        return Mono.onAssembly(new MonoDematerialize(thiz));
    }

    @Deprecated
    public final Mono<T> doAfterSuccessOrError(BiConsumer<? super T, Throwable> afterSuccessOrError) {
        return Mono.doOnTerminalSignal(this, null, null, afterSuccessOrError);
    }

    public final Mono<T> doAfterTerminate(Runnable afterTerminate) {
        Objects.requireNonNull(afterTerminate, "afterTerminate");
        return Mono.onAssembly(new MonoPeekTerminal<Object>(this, null, null, (s, e) -> afterTerminate.run()));
    }

    public final Mono<T> doFirst(Runnable onFirst) {
        Objects.requireNonNull(onFirst, "onFirst");
        if (this instanceof Fuseable) {
            return Mono.onAssembly(new MonoDoFirstFuseable(this, onFirst));
        }
        return Mono.onAssembly(new MonoDoFirst(this, onFirst));
    }

    public final Mono<T> doFinally(Consumer<SignalType> onFinally) {
        Objects.requireNonNull(onFinally, "onFinally");
        return Mono.onAssembly(new MonoDoFinally(this, onFinally));
    }

    public final Mono<T> doOnCancel(Runnable onCancel) {
        Objects.requireNonNull(onCancel, "onCancel");
        return Mono.doOnSignal(this, null, null, null, onCancel);
    }

    public final <R> Mono<T> doOnDiscard(Class<R> type, Consumer<? super R> discardHook) {
        return this.subscriberContext(Operators.discardLocalAdapter(type, discardHook));
    }

    public final Mono<T> doOnNext(Consumer<? super T> onNext) {
        Objects.requireNonNull(onNext, "onNext");
        return Mono.doOnSignal(this, null, onNext, null, null);
    }

    public final Mono<T> doOnSuccess(Consumer<? super T> onSuccess) {
        Objects.requireNonNull(onSuccess, "onSuccess");
        return Mono.doOnTerminalSignal(this, onSuccess, null, null);
    }

    public final Mono<T> doOnEach(Consumer<? super Signal<T>> signalConsumer) {
        Objects.requireNonNull(signalConsumer, "signalConsumer");
        if (this instanceof Fuseable) {
            return Mono.onAssembly(new MonoDoOnEachFuseable(this, signalConsumer));
        }
        return Mono.onAssembly(new MonoDoOnEach(this, signalConsumer));
    }

    public final Mono<T> doOnError(Consumer<? super Throwable> onError) {
        Objects.requireNonNull(onError, "onError");
        return Mono.doOnTerminalSignal(this, null, onError, null);
    }

    public final <E extends Throwable> Mono<T> doOnError(Class<E> exceptionType, Consumer<? super E> onError) {
        Objects.requireNonNull(exceptionType, "type");
        Objects.requireNonNull(onError, "onError");
        return Mono.doOnTerminalSignal(this, null, error -> {
            if (exceptionType.isInstance(error)) {
                onError.accept((Object)exceptionType.cast(error));
            }
        }, null);
    }

    public final Mono<T> doOnError(Predicate<? super Throwable> predicate, Consumer<? super Throwable> onError) {
        Objects.requireNonNull(predicate, "predicate");
        Objects.requireNonNull(onError, "onError");
        return Mono.doOnTerminalSignal(this, null, error -> {
            if (predicate.test((Throwable)error)) {
                onError.accept((Throwable)error);
            }
        }, null);
    }

    public final Mono<T> doOnRequest(LongConsumer consumer) {
        Objects.requireNonNull(consumer, "consumer");
        return Mono.doOnSignal(this, null, null, consumer, null);
    }

    public final Mono<T> doOnSubscribe(Consumer<? super Subscription> onSubscribe) {
        Objects.requireNonNull(onSubscribe, "onSubscribe");
        return Mono.doOnSignal(this, onSubscribe, null, null, null);
    }

    @Deprecated
    public final Mono<T> doOnSuccessOrError(BiConsumer<? super T, Throwable> onSuccessOrError) {
        Objects.requireNonNull(onSuccessOrError, "onSuccessOrError");
        return Mono.doOnTerminalSignal(this, v -> onSuccessOrError.accept(v, null), e -> onSuccessOrError.accept((Object)null, (Throwable)e), null);
    }

    public final Mono<T> doOnTerminate(Runnable onTerminate) {
        Objects.requireNonNull(onTerminate, "onTerminate");
        return Mono.doOnTerminalSignal(this, ignoreValue -> onTerminate.run(), ignoreError -> onTerminate.run(), null);
    }

    public final Mono<Tuple2<Long, T>> elapsed() {
        return this.elapsed(Schedulers.parallel());
    }

    public final Mono<Tuple2<Long, T>> elapsed(Scheduler scheduler) {
        Objects.requireNonNull(scheduler, "scheduler");
        return Mono.onAssembly(new MonoElapsed(this, scheduler));
    }

    public final Flux<T> expandDeep(Function<? super T, ? extends Publisher<? extends T>> expander, int capacityHint) {
        return Flux.onAssembly(new MonoExpand<T>(this, expander, false, capacityHint));
    }

    public final Flux<T> expandDeep(Function<? super T, ? extends Publisher<? extends T>> expander) {
        return this.expandDeep(expander, Queues.SMALL_BUFFER_SIZE);
    }

    public final Flux<T> expand(Function<? super T, ? extends Publisher<? extends T>> expander, int capacityHint) {
        return Flux.onAssembly(new MonoExpand<T>(this, expander, true, capacityHint));
    }

    public final Flux<T> expand(Function<? super T, ? extends Publisher<? extends T>> expander) {
        return this.expand(expander, Queues.SMALL_BUFFER_SIZE);
    }

    public final Mono<T> filter(Predicate<? super T> tester) {
        if (this instanceof Fuseable) {
            return Mono.onAssembly(new MonoFilterFuseable<T>(this, tester));
        }
        return Mono.onAssembly(new MonoFilter<T>(this, tester));
    }

    public final Mono<T> filterWhen(Function<? super T, ? extends Publisher<Boolean>> asyncPredicate) {
        return Mono.onAssembly(new MonoFilterWhen<T>(this, asyncPredicate));
    }

    public final <R> Mono<R> flatMap(Function<? super T, ? extends Mono<? extends R>> transformer) {
        return Mono.onAssembly(new MonoFlatMap(this, transformer));
    }

    public final <R> Flux<R> flatMapMany(Function<? super T, ? extends Publisher<? extends R>> mapper) {
        return Flux.onAssembly(new MonoFlatMapMany(this, mapper));
    }

    public final <R> Flux<R> flatMapMany(Function<? super T, ? extends Publisher<? extends R>> mapperOnNext, Function<? super Throwable, ? extends Publisher<? extends R>> mapperOnError, Supplier<? extends Publisher<? extends R>> mapperOnComplete) {
        return this.flux().flatMap(mapperOnNext, mapperOnError, mapperOnComplete);
    }

    public final <R> Flux<R> flatMapIterable(Function<? super T, ? extends Iterable<? extends R>> mapper) {
        return Flux.onAssembly(new MonoFlattenIterable(this, mapper, Integer.MAX_VALUE, Queues.one()));
    }

    public final Flux<T> flux() {
        if (this instanceof Callable && !(this instanceof Fuseable.ScalarCallable)) {
            Callable thiz = (Callable)((Object)this);
            return Flux.onAssembly(new FluxCallable(thiz));
        }
        return Flux.from(this);
    }

    public final Mono<Boolean> hasElement() {
        return Mono.onAssembly(new MonoHasElement(this));
    }

    public final <R> Mono<R> handle(BiConsumer<? super T, SynchronousSink<R>> handler) {
        if (this instanceof Fuseable) {
            return Mono.onAssembly(new MonoHandleFuseable<T, R>(this, handler));
        }
        return Mono.onAssembly(new MonoHandle<T, R>(this, handler));
    }

    public final Mono<T> hide() {
        return Mono.onAssembly(new MonoHide(this));
    }

    public final Mono<T> ignoreElement() {
        return Mono.onAssembly(new MonoIgnoreElement(this));
    }

    public final Mono<T> log() {
        return this.log(null, Level.INFO, new SignalType[0]);
    }

    public final Mono<T> log(@Nullable String category) {
        return this.log(category, Level.INFO, new SignalType[0]);
    }

    public final Mono<T> log(@Nullable String category, Level level, SignalType ... options) {
        return this.log(category, level, false, options);
    }

    public final Mono<T> log(@Nullable String category, Level level, boolean showOperatorLine, SignalType ... options) {
        SignalLogger log = new SignalLogger(this, category, level, showOperatorLine, options);
        if (this instanceof Fuseable) {
            return Mono.onAssembly(new MonoLogFuseable(this, log));
        }
        return Mono.onAssembly(new MonoLog(this, log));
    }

    public final Mono<T> log(Logger logger) {
        return this.log(logger, Level.INFO, false, new SignalType[0]);
    }

    public final Mono<T> log(Logger logger, Level level, boolean showOperatorLine, SignalType ... options) {
        SignalLogger log = new SignalLogger(this, "IGNORED", level, showOperatorLine, s -> logger, options);
        if (this instanceof Fuseable) {
            return Mono.onAssembly(new MonoLogFuseable(this, log));
        }
        return Mono.onAssembly(new MonoLog(this, log));
    }

    public final <R> Mono<R> map(Function<? super T, ? extends R> mapper) {
        if (this instanceof Fuseable) {
            return Mono.onAssembly(new MonoMapFuseable<T, R>(this, mapper));
        }
        return Mono.onAssembly(new MonoMap<T, R>(this, mapper));
    }

    public final <R> Mono<R> mapNotNull(Function<? super T, ? extends R> mapper) {
        return this.handle((t, sink) -> {
            Object r = mapper.apply(t);
            if (r != null) {
                sink.next(r);
            }
        });
    }

    public final Mono<Signal<T>> materialize() {
        return Mono.onAssembly(new MonoMaterialize(this));
    }

    public final Flux<T> mergeWith(Publisher<? extends T> other) {
        return Flux.merge(this, other);
    }

    public final Mono<T> metrics() {
        if (!Metrics.isInstrumentationAvailable()) {
            return this;
        }
        if (this instanceof Fuseable) {
            return Mono.onAssembly(new MonoMetricsFuseable(this));
        }
        return Mono.onAssembly(new MonoMetrics(this));
    }

    public final Mono<T> name(String name) {
        return MonoName.createOrAppend(this, name);
    }

    public final Mono<T> or(Mono<? extends T> other) {
        MonoFirstWithSignal a;
        Mono<? extends T> result;
        if (this instanceof MonoFirstWithSignal && (result = (a = (MonoFirstWithSignal)this).orAdditionalSource(other)) != null) {
            return result;
        }
        return Mono.firstWithSignal(this, other);
    }

    public final <U> Mono<U> ofType(Class<U> clazz) {
        Objects.requireNonNull(clazz, "clazz");
        return this.filter(o -> clazz.isAssignableFrom(o.getClass())).cast(clazz);
    }

    public final Mono<T> onErrorContinue(BiConsumer<Throwable, Object> errorConsumer) {
        BiConsumer<Throwable, Object> genericConsumer = errorConsumer;
        return this.subscriberContext(Context.of("reactor.onNextError.localStrategy", OnNextFailureStrategy.resume(genericConsumer)));
    }

    public final <E extends Throwable> Mono<T> onErrorContinue(Class<E> type, BiConsumer<Throwable, Object> errorConsumer) {
        return this.onErrorContinue(type::isInstance, errorConsumer);
    }

    public final <E extends Throwable> Mono<T> onErrorContinue(Predicate<E> errorPredicate, BiConsumer<Throwable, Object> errorConsumer) {
        Predicate<Throwable> genericPredicate = errorPredicate;
        BiConsumer<Throwable, Object> genericErrorConsumer = errorConsumer;
        return this.subscriberContext(Context.of("reactor.onNextError.localStrategy", OnNextFailureStrategy.resumeIf(genericPredicate, genericErrorConsumer)));
    }

    public final Mono<T> onErrorStop() {
        return this.subscriberContext(Context.of("reactor.onNextError.localStrategy", OnNextFailureStrategy.stop()));
    }

    public final Mono<T> onErrorMap(Predicate<? super Throwable> predicate, Function<? super Throwable, ? extends Throwable> mapper) {
        return this.onErrorResume(predicate, (? super Throwable e) -> Mono.error((Throwable)mapper.apply((Throwable)e)));
    }

    public final Mono<T> onErrorMap(Function<? super Throwable, ? extends Throwable> mapper) {
        return this.onErrorResume(e -> Mono.error((Throwable)mapper.apply((Throwable)e)));
    }

    public final <E extends Throwable> Mono<T> onErrorMap(Class<E> type, Function<? super E, ? extends Throwable> mapper) {
        Function<? super E, ? extends Throwable> handler = mapper;
        return this.onErrorMap(type::isInstance, handler);
    }

    public final Mono<T> onErrorResume(Function<? super Throwable, ? extends Mono<? extends T>> fallback) {
        return Mono.onAssembly(new MonoOnErrorResume(this, fallback));
    }

    public final <E extends Throwable> Mono<T> onErrorResume(Class<E> type, Function<? super E, ? extends Mono<? extends T>> fallback) {
        Objects.requireNonNull(type, "type");
        Function<? super E, ? extends Mono<? extends T>> handler = fallback;
        return this.onErrorResume(type::isInstance, handler);
    }

    public final Mono<T> onErrorResume(Predicate<? super Throwable> predicate, Function<? super Throwable, ? extends Mono<? extends T>> fallback) {
        Objects.requireNonNull(predicate, "predicate");
        return this.onErrorResume(e -> predicate.test((Throwable)e) ? (Mono)fallback.apply((Throwable)e) : Mono.error(e));
    }

    public final Mono<T> onErrorReturn(T fallback) {
        return this.onErrorResume(throwable -> Mono.just(fallback));
    }

    public final <E extends Throwable> Mono<T> onErrorReturn(Class<E> type, T fallbackValue) {
        return this.onErrorResume(type, (? super E throwable) -> Mono.just(fallbackValue));
    }

    public final Mono<T> onErrorReturn(Predicate<? super Throwable> predicate, T fallbackValue) {
        return this.onErrorResume(predicate, (? super Throwable throwable) -> Mono.just(fallbackValue));
    }

    public final Mono<T> onTerminateDetach() {
        return new MonoDetach(this);
    }

    public final <R> Mono<R> publish(Function<? super Mono<T>, ? extends Mono<? extends R>> transform) {
        return Mono.onAssembly(new MonoPublishMulticast(this, transform));
    }

    public final Mono<T> publishOn(Scheduler scheduler) {
        if (this instanceof Callable) {
            if (this instanceof Fuseable.ScalarCallable) {
                try {
                    T value = this.block();
                    return Mono.onAssembly(new MonoSubscribeOnValue<T>(value, scheduler));
                }
                catch (Throwable value) {
                    // empty catch block
                }
            }
            Callable c = (Callable)((Object)this);
            return Mono.onAssembly(new MonoSubscribeOnCallable(c, scheduler));
        }
        return Mono.onAssembly(new MonoPublishOn(this, scheduler));
    }

    public final Flux<T> repeat() {
        return this.repeat(Flux.ALWAYS_BOOLEAN_SUPPLIER);
    }

    public final Flux<T> repeat(BooleanSupplier predicate) {
        return Flux.onAssembly(new MonoRepeatPredicate(this, predicate));
    }

    public final Flux<T> repeat(long numRepeat) {
        if (numRepeat == 0L) {
            return this.flux();
        }
        return Flux.onAssembly(new MonoRepeat(this, numRepeat));
    }

    public final Flux<T> repeat(long numRepeat, BooleanSupplier predicate) {
        if (numRepeat < 0L) {
            throw new IllegalArgumentException("numRepeat >= 0 required");
        }
        if (numRepeat == 0L) {
            return this.flux();
        }
        return Flux.defer(() -> this.repeat(Flux.countingBooleanSupplier(predicate, numRepeat)));
    }

    public final Flux<T> repeatWhen(Function<Flux<Long>, ? extends Publisher<?>> repeatFactory) {
        return Flux.onAssembly(new MonoRepeatWhen(this, repeatFactory));
    }

    public final Mono<T> repeatWhenEmpty(Function<Flux<Long>, ? extends Publisher<?>> repeatFactory) {
        return this.repeatWhenEmpty(Integer.MAX_VALUE, repeatFactory);
    }

    public final Mono<T> repeatWhenEmpty(int maxRepeat, Function<Flux<Long>, ? extends Publisher<?>> repeatFactory) {
        return Mono.defer(() -> this.repeatWhen(o -> {
            if (maxRepeat == Integer.MAX_VALUE) {
                return (Publisher)repeatFactory.apply(o.index().map(Tuple2::getT1));
            }
            return (Publisher)repeatFactory.apply(o.index().map(Tuple2::getT1).take(maxRepeat).concatWith(Flux.error(() -> new IllegalStateException("Exceeded maximum number of repeats"))));
        }).next());
    }

    public final Mono<T> retry() {
        return this.retry(Long.MAX_VALUE);
    }

    public final Mono<T> retry(long numRetries) {
        return Mono.onAssembly(new MonoRetry(this, numRetries));
    }

    public final Mono<T> retryWhen(Retry retrySpec) {
        return Mono.onAssembly(new MonoRetryWhen(this, retrySpec));
    }

    public final Mono<T> share() {
        if (this instanceof Fuseable.ScalarCallable) {
            return this;
        }
        if (this instanceof NextProcessor && ((NextProcessor)this).isRefCounted) {
            return this;
        }
        return new NextProcessor(this, true);
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
        return Mono.onAssembly(new MonoSingleMono(this));
    }

    public final Disposable subscribe() {
        if (this instanceof NextProcessor) {
            NextProcessor s = (NextProcessor)this;
            if (s.source != null && !s.isRefCounted) {
                s.subscribe(new LambdaMonoSubscriber(null, null, null, null, null));
                s.connect();
                return s;
            }
        }
        return this.subscribeWith(new LambdaMonoSubscriber(null, null, null, null, null));
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

    public final Disposable subscribe(@Nullable Consumer<? super T> consumer, @Nullable Consumer<? super Throwable> errorConsumer, @Nullable Runnable completeConsumer, @Nullable Consumer<? super Subscription> subscriptionConsumer) {
        return this.subscribeWith(new LambdaMonoSubscriber<T>(consumer, errorConsumer, completeConsumer, subscriptionConsumer, null));
    }

    public final Disposable subscribe(@Nullable Consumer<? super T> consumer, @Nullable Consumer<? super Throwable> errorConsumer, @Nullable Runnable completeConsumer, @Nullable Context initialContext) {
        return this.subscribeWith(new LambdaMonoSubscriber<T>(consumer, errorConsumer, completeConsumer, null, initialContext));
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
    public final Mono<T> subscriberContext(Context mergeContext) {
        return this.subscriberContext((Context c) -> c.putAll(mergeContext.readOnly()));
    }

    @Deprecated
    public final Mono<T> subscriberContext(Function<Context, Context> doOnContext) {
        return new MonoContextWrite(this, doOnContext);
    }

    public final Mono<T> subscribeOn(Scheduler scheduler) {
        if (this instanceof Callable) {
            if (this instanceof Fuseable.ScalarCallable) {
                try {
                    T value = this.block();
                    return Mono.onAssembly(new MonoSubscribeOnValue<T>(value, scheduler));
                }
                catch (Throwable value) {
                    // empty catch block
                }
            }
            Callable c = (Callable)((Object)this);
            return Mono.onAssembly(new MonoSubscribeOnCallable(c, scheduler));
        }
        return Mono.onAssembly(new MonoSubscribeOn(this, scheduler));
    }

    public final <E extends Subscriber<? super T>> E subscribeWith(E subscriber) {
        this.subscribe(subscriber);
        return subscriber;
    }

    public final Mono<T> switchIfEmpty(Mono<? extends T> alternate) {
        return Mono.onAssembly(new MonoSwitchIfEmpty<T>(this, alternate));
    }

    public final Mono<T> tag(String key, String value) {
        return MonoName.createOrAppend(this, key, value);
    }

    public final Mono<T> take(Duration duration) {
        return this.take(duration, Schedulers.parallel());
    }

    public final Mono<T> take(Duration duration, Scheduler timer) {
        return this.takeUntilOther(Mono.delay(duration, timer));
    }

    public final Mono<T> takeUntilOther(Publisher<?> other) {
        return Mono.onAssembly(new MonoTakeUntilOther(this, other));
    }

    public final Mono<Void> then() {
        return Mono.empty(this);
    }

    public final <V> Mono<V> then(Mono<V> other) {
        if (this instanceof MonoIgnoreThen) {
            MonoIgnoreThen a = (MonoIgnoreThen)this;
            return a.shift(other);
        }
        return Mono.onAssembly(new MonoIgnoreThen<V>(new Publisher[]{this}, other));
    }

    public final <V> Mono<V> thenReturn(V value) {
        return this.then(Mono.just(value));
    }

    public final Mono<Void> thenEmpty(Publisher<Void> other) {
        return this.then(Mono.fromDirect(other));
    }

    public final <V> Flux<V> thenMany(Publisher<V> other) {
        Flux concat = Flux.concat(this.ignoreElement(), other);
        return Flux.onAssembly(concat);
    }

    public final Mono<Timed<T>> timed() {
        return this.timed(Schedulers.parallel());
    }

    public final Mono<Timed<T>> timed(Scheduler clock) {
        return Mono.onAssembly(new MonoTimed(this, clock));
    }

    public final Mono<T> timeout(Duration timeout) {
        return this.timeout(timeout, Schedulers.parallel());
    }

    public final Mono<T> timeout(Duration timeout, Mono<? extends T> fallback) {
        return this.timeout(timeout, fallback, Schedulers.parallel());
    }

    public final Mono<T> timeout(Duration timeout, Scheduler timer) {
        return this.timeout(timeout, null, timer);
    }

    public final Mono<T> timeout(Duration timeout, @Nullable Mono<? extends T> fallback, Scheduler timer) {
        Mono<Long> _timer = Mono.delay(timeout, timer).onErrorReturn(0L);
        if (fallback == null) {
            return Mono.onAssembly(new MonoTimeout(this, _timer, timeout.toMillis() + "ms"));
        }
        return Mono.onAssembly(new MonoTimeout(this, _timer, fallback));
    }

    public final <U> Mono<T> timeout(Publisher<U> firstTimeout) {
        return Mono.onAssembly(new MonoTimeout(this, firstTimeout, "first signal from a Publisher"));
    }

    public final <U> Mono<T> timeout(Publisher<U> firstTimeout, Mono<? extends T> fallback) {
        return Mono.onAssembly(new MonoTimeout(this, firstTimeout, fallback));
    }

    public final Mono<Tuple2<Long, T>> timestamp() {
        return this.timestamp(Schedulers.parallel());
    }

    public final Mono<Tuple2<Long, T>> timestamp(Scheduler scheduler) {
        Objects.requireNonNull(scheduler, "scheduler");
        return this.map(d -> Tuples.of(scheduler.now(TimeUnit.MILLISECONDS), d));
    }

    public final CompletableFuture<T> toFuture() {
        return this.subscribeWith(new MonoToCompletableFuture(false));
    }

    @Deprecated
    public final MonoProcessor<T> toProcessor() {
        if (this instanceof MonoProcessor) {
            return (MonoProcessor)this;
        }
        NextProcessor result = new NextProcessor(this);
        result.connect();
        return result;
    }

    public final <V> Mono<V> transform(Function<? super Mono<T>, ? extends Publisher<V>> transformer) {
        if (Hooks.DETECT_CONTEXT_LOSS) {
            transformer = new ContextTrackingFunctionWrapper(transformer);
        }
        return Mono.onAssembly(Mono.from(transformer.apply(this)));
    }

    public final <V> Mono<V> transformDeferred(Function<? super Mono<T>, ? extends Publisher<V>> transformer) {
        return Mono.defer(() -> {
            if (Hooks.DETECT_CONTEXT_LOSS) {
                Mono result = Mono.from(new ContextTrackingFunctionWrapper(transformer).apply(this));
                return result;
            }
            return Mono.from((Publisher)transformer.apply(this));
        });
    }

    public final <V> Mono<V> transformDeferredContextual(BiFunction<? super Mono<T>, ? super ContextView, ? extends Publisher<V>> transformer) {
        return Mono.deferContextual(ctxView -> {
            if (Hooks.DETECT_CONTEXT_LOSS) {
                ContextTrackingFunctionWrapper wrapper = new ContextTrackingFunctionWrapper(publisher -> (Publisher)transformer.apply((Object)Mono.wrap(publisher, false), (ContextView)ctxView), transformer.toString());
                return Mono.wrap(wrapper.apply(this), true);
            }
            return Mono.from((Publisher)transformer.apply((Mono<T>)this, (ContextView)ctxView));
        });
    }

    public final <T2> Mono<Tuple2<T, T2>> zipWhen(Function<T, Mono<? extends T2>> rightGenerator) {
        return this.zipWhen(rightGenerator, Tuples::of);
    }

    public final <T2, O> Mono<O> zipWhen(Function<T, Mono<? extends T2>> rightGenerator, BiFunction<T, T2, O> combinator) {
        Objects.requireNonNull(rightGenerator, "rightGenerator function is mandatory to get the right-hand side Mono");
        Objects.requireNonNull(combinator, "combinator function is mandatory to combine results from both Monos");
        return this.flatMap(t -> ((Mono)rightGenerator.apply(t)).map(t2 -> combinator.apply(t, t2)));
    }

    public final <T2> Mono<Tuple2<T, T2>> zipWith(Mono<? extends T2> other) {
        return this.zipWith(other, Flux.tuple2Function());
    }

    public final <T2, O> Mono<O> zipWith(Mono<? extends T2> other, BiFunction<? super T, ? super T2, ? extends O> combinator) {
        MonoZip o;
        Mono result;
        if (this instanceof MonoZip && (result = (o = (MonoZip)this).zipAdditionalSource(other, combinator)) != null) {
            return result;
        }
        return Mono.zip(this, other, combinator);
    }

    protected static <T> Mono<T> onAssembly(Mono<T> source) {
        Function<Publisher, Publisher> hook = Hooks.onEachOperatorHook;
        if (hook != null) {
            source = (Mono)hook.apply(source);
        }
        if (Hooks.GLOBAL_TRACE) {
            FluxOnAssembly.AssemblySnapshot stacktrace = new FluxOnAssembly.AssemblySnapshot(null, Traces.callSiteSupplierFactory.get());
            source = (Mono)Hooks.addAssemblyInfo(source, stacktrace);
        }
        return source;
    }

    public String toString() {
        return this.getClass().getSimpleName();
    }

    static <T> Mono<Void> empty(Publisher<T> source) {
        Mono<Void> then = Mono.ignoreElements(source);
        return then;
    }

    static <T> Mono<T> doOnSignal(Mono<T> source, @Nullable Consumer<? super Subscription> onSubscribe, @Nullable Consumer<? super T> onNext, @Nullable LongConsumer onRequest, @Nullable Runnable onCancel) {
        if (source instanceof Fuseable) {
            return Mono.onAssembly(new MonoPeekFuseable<T>(source, onSubscribe, onNext, onRequest, onCancel));
        }
        return Mono.onAssembly(new MonoPeek<T>(source, onSubscribe, onNext, onRequest, onCancel));
    }

    static <T> Mono<T> doOnTerminalSignal(Mono<T> source, @Nullable Consumer<? super T> onSuccess, @Nullable Consumer<? super Throwable> onError, @Nullable BiConsumer<? super T, Throwable> onAfterTerminate) {
        return Mono.onAssembly(new MonoPeekTerminal<T>(source, onSuccess, onError, onAfterTerminate));
    }

    static <T> Mono<T> wrap(Publisher<T> source, boolean enforceMonoContract) {
        if (source instanceof Mono) {
            return (Mono)source;
        }
        if (source instanceof FluxSourceMono || source instanceof FluxSourceMonoFuseable) {
            Mono extracted = ((FluxFromMonoOperator)source).source;
            return extracted;
        }
        if (enforceMonoContract) {
            if (source instanceof Flux && source instanceof Callable) {
                Callable m = (Callable)source;
                return Flux.wrapToMono(m);
            }
            if (source instanceof Flux) {
                return new MonoNext((Flux)source);
            }
            return new MonoFromPublisher<T>(source);
        }
        if (source instanceof Flux && source instanceof Fuseable) {
            return new MonoSourceFluxFuseable((Flux)source);
        }
        if (source instanceof Flux) {
            return new MonoSourceFlux((Flux)source);
        }
        if (source instanceof Fuseable) {
            return new MonoSourceFuseable<T>(source);
        }
        return new MonoSource<T>(source);
    }

    static <T> BiPredicate<? super T, ? super T> equalsBiPredicate() {
        return EQUALS_BIPREDICATE;
    }
}

