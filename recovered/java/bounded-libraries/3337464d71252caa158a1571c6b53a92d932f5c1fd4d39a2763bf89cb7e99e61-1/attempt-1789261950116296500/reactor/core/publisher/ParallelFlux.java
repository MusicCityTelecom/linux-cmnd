/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Publisher
 *  org.reactivestreams.Subscriber
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
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
import reactor.core.Disposables;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxConcatMap;
import reactor.core.publisher.FluxHide;
import reactor.core.publisher.FluxOnAssembly;
import reactor.core.publisher.GroupedFlux;
import reactor.core.publisher.Hooks;
import reactor.core.publisher.LambdaSubscriber;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Operators;
import reactor.core.publisher.ParallelArraySource;
import reactor.core.publisher.ParallelCollect;
import reactor.core.publisher.ParallelConcatMap;
import reactor.core.publisher.ParallelDoOnEach;
import reactor.core.publisher.ParallelFilter;
import reactor.core.publisher.ParallelFlatMap;
import reactor.core.publisher.ParallelFluxHide;
import reactor.core.publisher.ParallelFluxName;
import reactor.core.publisher.ParallelFluxOnAssembly;
import reactor.core.publisher.ParallelGroup;
import reactor.core.publisher.ParallelLog;
import reactor.core.publisher.ParallelMap;
import reactor.core.publisher.ParallelMergeOrdered;
import reactor.core.publisher.ParallelMergeReduce;
import reactor.core.publisher.ParallelMergeSequential;
import reactor.core.publisher.ParallelMergeSort;
import reactor.core.publisher.ParallelPeek;
import reactor.core.publisher.ParallelReduceSeed;
import reactor.core.publisher.ParallelRunOn;
import reactor.core.publisher.ParallelSource;
import reactor.core.publisher.ParallelThen;
import reactor.core.publisher.Signal;
import reactor.core.publisher.SignalLogger;
import reactor.core.publisher.SignalType;
import reactor.core.publisher.Traces;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import reactor.util.annotation.Nullable;
import reactor.util.concurrent.Queues;
import reactor.util.context.Context;

public abstract class ParallelFlux<T>
implements CorePublisher<T> {
    public static <T> ParallelFlux<T> from(Publisher<? extends T> source) {
        return ParallelFlux.from(source, Schedulers.DEFAULT_POOL_SIZE, Queues.SMALL_BUFFER_SIZE, Queues.small());
    }

    public static <T> ParallelFlux<T> from(Publisher<? extends T> source, int parallelism) {
        return ParallelFlux.from(source, parallelism, Queues.SMALL_BUFFER_SIZE, Queues.small());
    }

    public static <T> ParallelFlux<T> from(Publisher<? extends T> source, int parallelism, int prefetch, Supplier<Queue<T>> queueSupplier) {
        Objects.requireNonNull(queueSupplier, "queueSupplier");
        Objects.requireNonNull(source, "source");
        return ParallelFlux.onAssembly(new ParallelSource<T>(source, parallelism, prefetch, queueSupplier));
    }

    @SafeVarargs
    public static <T> ParallelFlux<T> from(Publisher<T> ... publishers) {
        return ParallelFlux.onAssembly(new ParallelArraySource<T>(publishers));
    }

    public final <U> U as(Function<? super ParallelFlux<T>, U> converter) {
        return converter.apply(this);
    }

    public final ParallelFlux<T> checkpoint() {
        FluxOnAssembly.CheckpointHeavySnapshot stacktrace = new FluxOnAssembly.CheckpointHeavySnapshot(null, Traces.callSiteSupplierFactory.get());
        return new ParallelFluxOnAssembly(this, stacktrace);
    }

    public final ParallelFlux<T> checkpoint(String description) {
        return new ParallelFluxOnAssembly(this, new FluxOnAssembly.CheckpointLightSnapshot(description));
    }

    public final ParallelFlux<T> checkpoint(String description, boolean forceStackTrace) {
        FluxOnAssembly.AssemblySnapshot stacktrace = !forceStackTrace ? new FluxOnAssembly.CheckpointLightSnapshot(description) : new FluxOnAssembly.CheckpointHeavySnapshot(description, Traces.callSiteSupplierFactory.get());
        return new ParallelFluxOnAssembly(this, stacktrace);
    }

    public final <C> ParallelFlux<C> collect(Supplier<? extends C> collectionSupplier, BiConsumer<? super C, ? super T> collector) {
        return ParallelFlux.onAssembly(new ParallelCollect<T, C>(this, collectionSupplier, collector));
    }

    public final Mono<List<T>> collectSortedList(Comparator<? super T> comparator) {
        return this.collectSortedList(comparator, 16);
    }

    public final Mono<List<T>> collectSortedList(Comparator<? super T> comparator, int capacityHint) {
        int ch = capacityHint / this.parallelism() + 1;
        ParallelFlux<List> railReduced = this.reduce(() -> new ArrayList(ch), (a, b) -> {
            a.add(b);
            return a;
        });
        ParallelFlux<List> railSorted = railReduced.map(list -> {
            list.sort(comparator);
            return list;
        });
        Mono<List<T>> merged = railSorted.reduce((a, b) -> ParallelFlux.sortedMerger(a, b, comparator));
        return merged;
    }

    public final <R> ParallelFlux<R> concatMap(Function<? super T, ? extends Publisher<? extends R>> mapper) {
        return this.concatMap(mapper, 2, FluxConcatMap.ErrorMode.IMMEDIATE);
    }

    public final <R> ParallelFlux<R> concatMap(Function<? super T, ? extends Publisher<? extends R>> mapper, int prefetch) {
        return this.concatMap(mapper, prefetch, FluxConcatMap.ErrorMode.IMMEDIATE);
    }

    public final <R> ParallelFlux<R> concatMapDelayError(Function<? super T, ? extends Publisher<? extends R>> mapper) {
        return this.concatMap(mapper, 2, FluxConcatMap.ErrorMode.END);
    }

    public final ParallelFlux<T> doAfterTerminate(Runnable afterTerminate) {
        Objects.requireNonNull(afterTerminate, "afterTerminate");
        return ParallelFlux.doOnSignal(this, null, null, null, null, afterTerminate, null, null, null);
    }

    public final ParallelFlux<T> doOnCancel(Runnable onCancel) {
        Objects.requireNonNull(onCancel, "onCancel");
        return ParallelFlux.doOnSignal(this, null, null, null, null, null, null, null, onCancel);
    }

    public final ParallelFlux<T> doOnComplete(Runnable onComplete) {
        Objects.requireNonNull(onComplete, "onComplete");
        return ParallelFlux.doOnSignal(this, null, null, null, onComplete, null, null, null, null);
    }

    public final ParallelFlux<T> doOnEach(Consumer<? super Signal<T>> signalConsumer) {
        Objects.requireNonNull(signalConsumer, "signalConsumer");
        return ParallelFlux.onAssembly(new ParallelDoOnEach<Object>(this, (ctx, v) -> signalConsumer.accept(Signal.next(v, ctx)), (ctx, e) -> signalConsumer.accept(Signal.error(e, ctx)), ctx -> signalConsumer.accept(Signal.complete(ctx))));
    }

    public final ParallelFlux<T> doOnError(Consumer<? super Throwable> onError) {
        Objects.requireNonNull(onError, "onError");
        return ParallelFlux.doOnSignal(this, null, null, onError, null, null, null, null, null);
    }

    public final ParallelFlux<T> doOnSubscribe(Consumer<? super Subscription> onSubscribe) {
        Objects.requireNonNull(onSubscribe, "onSubscribe");
        return ParallelFlux.doOnSignal(this, null, null, null, null, null, onSubscribe, null, null);
    }

    public final ParallelFlux<T> doOnNext(Consumer<? super T> onNext) {
        Objects.requireNonNull(onNext, "onNext");
        return ParallelFlux.doOnSignal(this, onNext, null, null, null, null, null, null, null);
    }

    public final ParallelFlux<T> doOnRequest(LongConsumer onRequest) {
        Objects.requireNonNull(onRequest, "onRequest");
        return ParallelFlux.doOnSignal(this, null, null, null, null, null, null, onRequest, null);
    }

    public final ParallelFlux<T> doOnTerminate(Runnable onTerminate) {
        Objects.requireNonNull(onTerminate, "onTerminate");
        return ParallelFlux.doOnSignal(this, null, null, e -> onTerminate.run(), onTerminate, null, null, null, null);
    }

    public final ParallelFlux<T> filter(Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate, "predicate");
        return ParallelFlux.onAssembly(new ParallelFilter<T>(this, predicate));
    }

    public final <R> ParallelFlux<R> flatMap(Function<? super T, ? extends Publisher<? extends R>> mapper) {
        return this.flatMap(mapper, false, Integer.MAX_VALUE, Queues.SMALL_BUFFER_SIZE);
    }

    public final <R> ParallelFlux<R> flatMap(Function<? super T, ? extends Publisher<? extends R>> mapper, boolean delayError) {
        return this.flatMap(mapper, delayError, Integer.MAX_VALUE, Queues.SMALL_BUFFER_SIZE);
    }

    public final <R> ParallelFlux<R> flatMap(Function<? super T, ? extends Publisher<? extends R>> mapper, boolean delayError, int maxConcurrency) {
        return this.flatMap(mapper, delayError, maxConcurrency, Queues.SMALL_BUFFER_SIZE);
    }

    public final <R> ParallelFlux<R> flatMap(Function<? super T, ? extends Publisher<? extends R>> mapper, boolean delayError, int maxConcurrency, int prefetch) {
        return ParallelFlux.onAssembly(new ParallelFlatMap(this, mapper, delayError, maxConcurrency, Queues.get(maxConcurrency), prefetch, Queues.get(prefetch)));
    }

    public final Flux<GroupedFlux<Integer, T>> groups() {
        return Flux.onAssembly(new ParallelGroup(this));
    }

    public final ParallelFlux<T> hide() {
        return new ParallelFluxHide(this);
    }

    public final ParallelFlux<T> log() {
        return this.log(null, Level.INFO, new SignalType[0]);
    }

    public final ParallelFlux<T> log(@Nullable String category) {
        return this.log(category, Level.INFO, new SignalType[0]);
    }

    public final ParallelFlux<T> log(@Nullable String category, Level level, SignalType ... options) {
        return this.log(category, level, false, options);
    }

    public final ParallelFlux<T> log(@Nullable String category, Level level, boolean showOperatorLine, SignalType ... options) {
        return ParallelFlux.onAssembly(new ParallelLog(this, new SignalLogger(this, category, level, showOperatorLine, options)));
    }

    public final <U> ParallelFlux<U> map(Function<? super T, ? extends U> mapper) {
        Objects.requireNonNull(mapper, "mapper");
        return ParallelFlux.onAssembly(new ParallelMap<T, U>(this, mapper));
    }

    public final ParallelFlux<T> name(String name) {
        return ParallelFluxName.createOrAppend(this, name);
    }

    public final Flux<T> ordered(Comparator<? super T> comparator) {
        return this.ordered(comparator, Queues.SMALL_BUFFER_SIZE);
    }

    public final Flux<T> ordered(Comparator<? super T> comparator, int prefetch) {
        return new ParallelMergeOrdered<T>(this, prefetch, comparator);
    }

    public abstract int parallelism();

    public final Mono<T> reduce(BiFunction<T, T, T> reducer) {
        Objects.requireNonNull(reducer, "reducer");
        return Mono.onAssembly(new ParallelMergeReduce<T>(this, reducer));
    }

    public final <R> ParallelFlux<R> reduce(Supplier<R> initialSupplier, BiFunction<R, ? super T, R> reducer) {
        Objects.requireNonNull(initialSupplier, "initialSupplier");
        Objects.requireNonNull(reducer, "reducer");
        return ParallelFlux.onAssembly(new ParallelReduceSeed<T, R>(this, initialSupplier, reducer));
    }

    public final ParallelFlux<T> runOn(Scheduler scheduler) {
        return this.runOn(scheduler, Queues.SMALL_BUFFER_SIZE);
    }

    public final ParallelFlux<T> runOn(Scheduler scheduler, int prefetch) {
        Objects.requireNonNull(scheduler, "scheduler");
        return ParallelFlux.onAssembly(new ParallelRunOn(this, scheduler, prefetch, Queues.get(prefetch)));
    }

    public final Flux<T> sequential() {
        return this.sequential(Queues.SMALL_BUFFER_SIZE);
    }

    public final Flux<T> sequential(int prefetch) {
        return Flux.onAssembly(new ParallelMergeSequential(this, prefetch, Queues.get(prefetch)));
    }

    public final Flux<T> sorted(Comparator<? super T> comparator) {
        return this.sorted(comparator, 16);
    }

    public final Flux<T> sorted(Comparator<? super T> comparator, int capacityHint) {
        int ch = capacityHint / this.parallelism() + 1;
        ParallelFlux<List> railReduced = this.reduce(() -> new ArrayList(ch), (a, b) -> {
            a.add(b);
            return a;
        });
        ParallelFlux railSorted = railReduced.map(list -> {
            list.sort(comparator);
            return list;
        });
        return Flux.onAssembly(new ParallelMergeSort<T>(railSorted, comparator));
    }

    public abstract void subscribe(CoreSubscriber<? super T>[] var1);

    public final Disposable subscribe() {
        return this.subscribe(null, null, null);
    }

    public final Disposable subscribe(Consumer<? super T> onNext) {
        return this.subscribe(onNext, null, null);
    }

    public final Disposable subscribe(@Nullable Consumer<? super T> onNext, Consumer<? super Throwable> onError) {
        return this.subscribe(onNext, onError, null);
    }

    public final Disposable subscribe(@Nullable Consumer<? super T> onNext, @Nullable Consumer<? super Throwable> onError, @Nullable Runnable onComplete) {
        return this.subscribe(onNext, onError, onComplete, null, null);
    }

    @Override
    public final void subscribe(CoreSubscriber<? super T> s) {
        FluxHide.SuppressFuseableSubscriber<? super T> subscriber = new FluxHide.SuppressFuseableSubscriber<T>(Operators.toCoreSubscriber(s));
        this.sequential().subscribe(Operators.toCoreSubscriber(subscriber));
    }

    public final Disposable subscribe(@Nullable Consumer<? super T> onNext, @Nullable Consumer<? super Throwable> onError, @Nullable Runnable onComplete, @Nullable Consumer<? super Subscription> onSubscribe) {
        return this.subscribe(onNext, onError, onComplete, onSubscribe, null);
    }

    public final Disposable subscribe(@Nullable Consumer<? super T> onNext, @Nullable Consumer<? super Throwable> onError, @Nullable Runnable onComplete, @Nullable Context initialContext) {
        return this.subscribe(onNext, onError, onComplete, null, initialContext);
    }

    final Disposable subscribe(@Nullable Consumer<? super T> onNext, @Nullable Consumer<? super Throwable> onError, @Nullable Runnable onComplete, @Nullable Consumer<? super Subscription> onSubscribe, @Nullable Context initialContext) {
        CorePublisher<? super T> publisher = Operators.onLastAssembly(this);
        if (publisher instanceof ParallelFlux) {
            Disposable[] subscribers = new LambdaSubscriber[this.parallelism()];
            int i = 0;
            while (i < subscribers.length) {
                subscribers[i++] = new LambdaSubscriber<T>(onNext, onError, onComplete, onSubscribe, initialContext);
            }
            ((ParallelFlux)publisher).subscribe((CoreSubscriber<? super T>[])subscribers);
            return Disposables.composite(subscribers);
        }
        LambdaSubscriber<? super T> subscriber = new LambdaSubscriber<T>(onNext, onError, onComplete, onSubscribe, initialContext);
        publisher.subscribe(Operators.toCoreSubscriber(new FluxHide.SuppressFuseableSubscriber<T>(subscriber)));
        return subscriber;
    }

    @Override
    public final void subscribe(Subscriber<? super T> s) {
        FluxHide.SuppressFuseableSubscriber<? super T> subscriber = new FluxHide.SuppressFuseableSubscriber<T>(Operators.toCoreSubscriber(s));
        Operators.onLastAssembly(this.sequential()).subscribe(Operators.toCoreSubscriber(subscriber));
    }

    public final ParallelFlux<T> tag(String key, String value) {
        return ParallelFluxName.createOrAppend(this, key, value);
    }

    public final Mono<Void> then() {
        return Mono.onAssembly(new ParallelThen(this));
    }

    public final <U> ParallelFlux<U> transform(Function<? super ParallelFlux<T>, ParallelFlux<U>> composer) {
        return ParallelFlux.onAssembly(this.as(composer));
    }

    public final <U> ParallelFlux<U> transformGroups(Function<? super GroupedFlux<Integer, T>, ? extends Publisher<? extends U>> composer) {
        if (this.getPrefetch() > -1) {
            return ParallelFlux.from(this.groups().flatMap(composer::apply), this.parallelism(), this.getPrefetch(), Queues.small());
        }
        return ParallelFlux.from(this.groups().flatMap(composer::apply), this.parallelism());
    }

    public String toString() {
        return this.getClass().getSimpleName();
    }

    protected final boolean validate(Subscriber<?>[] subscribers) {
        int p = this.parallelism();
        if (subscribers.length != p) {
            IllegalArgumentException iae = new IllegalArgumentException("parallelism = " + p + ", subscribers = " + subscribers.length);
            for (Subscriber<?> s : subscribers) {
                Operators.error(s, iae);
            }
            return false;
        }
        return true;
    }

    final <R> ParallelFlux<R> concatMap(Function<? super T, ? extends Publisher<? extends R>> mapper, int prefetch, FluxConcatMap.ErrorMode errorMode) {
        return ParallelFlux.onAssembly(new ParallelConcatMap(this, mapper, Queues.get(prefetch), prefetch, errorMode));
    }

    final <R> ParallelFlux<R> concatMapDelayError(Function<? super T, ? extends Publisher<? extends R>> mapper, boolean delayUntilEnd, int prefetch) {
        return this.concatMap(mapper, prefetch, delayUntilEnd ? FluxConcatMap.ErrorMode.END : FluxConcatMap.ErrorMode.BOUNDARY);
    }

    final <R> ParallelFlux<R> concatMapDelayError(Function<? super T, ? extends Publisher<? extends R>> mapper, int prefetch) {
        return this.concatMap(mapper, prefetch, FluxConcatMap.ErrorMode.END);
    }

    public int getPrefetch() {
        return -1;
    }

    protected static <T> ParallelFlux<T> onAssembly(ParallelFlux<T> source) {
        Function<Publisher, Publisher> hook = Hooks.onEachOperatorHook;
        if (hook != null) {
            source = (ParallelFlux)hook.apply(source);
        }
        if (Hooks.GLOBAL_TRACE) {
            FluxOnAssembly.AssemblySnapshot stacktrace = new FluxOnAssembly.AssemblySnapshot(null, Traces.callSiteSupplierFactory.get());
            source = (ParallelFlux)Hooks.addAssemblyInfo(source, stacktrace);
        }
        return source;
    }

    static <T> ParallelFlux<T> doOnSignal(ParallelFlux<T> source, @Nullable Consumer<? super T> onNext, @Nullable Consumer<? super T> onAfterNext, @Nullable Consumer<? super Throwable> onError, @Nullable Runnable onComplete, @Nullable Runnable onAfterTerminate, @Nullable Consumer<? super Subscription> onSubscribe, @Nullable LongConsumer onRequest, @Nullable Runnable onCancel) {
        return ParallelFlux.onAssembly(new ParallelPeek<T>(source, onNext, onAfterNext, onError, onComplete, onAfterTerminate, onSubscribe, onRequest, onCancel));
    }

    static final <T> List<T> sortedMerger(List<T> a, List<T> b, Comparator<? super T> comparator) {
        ArrayList<T> both;
        block6: {
            Object s2;
            Iterator<T> bt;
            block5: {
                int n = a.size() + b.size();
                if (n == 0) {
                    return new ArrayList();
                }
                both = new ArrayList<T>(n);
                Iterator<T> at = a.iterator();
                bt = b.iterator();
                Object s1 = at.hasNext() ? at.next() : null;
                Object v0 = s2 = bt.hasNext() ? bt.next() : null;
                while (s1 != null && s2 != null) {
                    if (comparator.compare(s1, s2) < 0) {
                        both.add(s1);
                        s1 = at.hasNext() ? at.next() : null;
                        continue;
                    }
                    both.add(s2);
                    s2 = bt.hasNext() ? bt.next() : null;
                }
                if (s1 == null) break block5;
                both.add(s1);
                while (at.hasNext()) {
                    both.add(at.next());
                }
                break block6;
            }
            if (s2 == null) break block6;
            both.add(s2);
            while (bt.hasNext()) {
                both.add(bt.next());
            }
        }
        return both;
    }
}

