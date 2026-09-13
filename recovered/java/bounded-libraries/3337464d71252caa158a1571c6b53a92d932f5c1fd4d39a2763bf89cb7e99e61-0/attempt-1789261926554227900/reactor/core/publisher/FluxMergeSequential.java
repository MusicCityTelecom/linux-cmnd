/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Publisher
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxConcatMap;
import reactor.core.publisher.FluxFlatMap;
import reactor.core.publisher.InnerConsumer;
import reactor.core.publisher.InnerOperator;
import reactor.core.publisher.InternalFluxOperator;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;
import reactor.util.concurrent.Queues;
import reactor.util.context.Context;

final class FluxMergeSequential<T, R>
extends InternalFluxOperator<T, R> {
    final FluxConcatMap.ErrorMode errorMode;
    final Function<? super T, ? extends Publisher<? extends R>> mapper;
    final int maxConcurrency;
    final int prefetch;
    final Supplier<Queue<MergeSequentialInner<R>>> queueSupplier;

    FluxMergeSequential(Flux<? extends T> source, Function<? super T, ? extends Publisher<? extends R>> mapper, int maxConcurrency, int prefetch, FluxConcatMap.ErrorMode errorMode) {
        this(source, mapper, maxConcurrency, prefetch, errorMode, Queues.get(Math.max(prefetch, maxConcurrency)));
    }

    FluxMergeSequential(Flux<? extends T> source, Function<? super T, ? extends Publisher<? extends R>> mapper, int maxConcurrency, int prefetch, FluxConcatMap.ErrorMode errorMode, Supplier<Queue<MergeSequentialInner<R>>> queueSupplier) {
        super(source);
        if (prefetch <= 0) {
            throw new IllegalArgumentException("prefetch > 0 required but it was " + prefetch);
        }
        if (maxConcurrency <= 0) {
            throw new IllegalArgumentException("maxConcurrency > 0 required but it was " + maxConcurrency);
        }
        this.mapper = Objects.requireNonNull(mapper, "mapper");
        this.maxConcurrency = maxConcurrency;
        this.prefetch = prefetch;
        this.errorMode = errorMode;
        this.queueSupplier = queueSupplier;
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super R> actual) {
        if (FluxFlatMap.trySubscribeScalarMap(this.source, actual, this.mapper, false, false)) {
            return null;
        }
        return new MergeSequentialMain<T, R>(actual, this.mapper, this.maxConcurrency, this.prefetch, this.errorMode, this.queueSupplier);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static final class MergeSequentialInner<R>
    implements InnerConsumer<R> {
        final MergeSequentialMain<?, R> parent;
        final int prefetch;
        final int limit;
        volatile Queue<R> queue;
        volatile Subscription subscription;
        static final AtomicReferenceFieldUpdater<MergeSequentialInner, Subscription> SUBSCRIPTION = AtomicReferenceFieldUpdater.newUpdater(MergeSequentialInner.class, Subscription.class, "subscription");
        volatile boolean done;
        long produced;
        int fusionMode;

        MergeSequentialInner(MergeSequentialMain<?, R> parent, int prefetch) {
            this.parent = parent;
            this.prefetch = prefetch;
            this.limit = Operators.unboundedOrLimit(prefetch);
        }

        @Override
        public Context currentContext() {
            return this.parent.currentContext();
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.PARENT) {
                return this.subscription;
            }
            if (key == Scannable.Attr.ACTUAL) {
                return this.parent;
            }
            if (key == Scannable.Attr.TERMINATED) {
                return this.done && (this.queue == null || this.queue.isEmpty());
            }
            if (key == Scannable.Attr.CANCELLED) {
                return this.subscription == Operators.cancelledSubscription();
            }
            if (key == Scannable.Attr.BUFFERED) {
                return this.queue == null ? 0 : this.queue.size();
            }
            if (key == Scannable.Attr.PREFETCH) {
                return this.prefetch;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return null;
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (Operators.setOnce(SUBSCRIPTION, this, s)) {
                if (s instanceof Fuseable.QueueSubscription) {
                    Fuseable.QueueSubscription qs = (Fuseable.QueueSubscription)s;
                    int m = qs.requestFusion(7);
                    if (m == 1) {
                        this.fusionMode = m;
                        this.queue = qs;
                        this.done = true;
                        this.parent.innerComplete(this);
                        return;
                    }
                    if (m == 2) {
                        this.fusionMode = m;
                        this.queue = qs;
                        s.request(Operators.unboundedOrPrefetch(this.prefetch));
                        return;
                    }
                }
                this.queue = Queues.get(this.prefetch).get();
                s.request(Operators.unboundedOrPrefetch(this.prefetch));
            }
        }

        public void onNext(R t) {
            if (this.fusionMode == 0) {
                this.parent.innerNext(this, t);
            } else {
                this.parent.drain();
            }
        }

        public void onError(Throwable t) {
            this.parent.innerError(this, t);
        }

        public void onComplete() {
            this.parent.innerComplete(this);
        }

        void requestOne() {
            if (this.fusionMode != 1) {
                long p = this.produced + 1L;
                if (p == (long)this.limit) {
                    this.produced = 0L;
                    this.subscription.request(p);
                } else {
                    this.produced = p;
                }
            }
        }

        void cancel() {
            Operators.set(SUBSCRIPTION, this, Operators.cancelledSubscription());
        }

        boolean isDone() {
            return this.done;
        }

        void setDone() {
            this.done = true;
        }

        Queue<R> queue() {
            return this.queue;
        }
    }

    static final class MergeSequentialMain<T, R>
    implements InnerOperator<T, R> {
        final Function<? super T, ? extends Publisher<? extends R>> mapper;
        final int maxConcurrency;
        final int prefetch;
        final Queue<MergeSequentialInner<R>> subscribers;
        final FluxConcatMap.ErrorMode errorMode;
        final CoreSubscriber<? super R> actual;
        Subscription s;
        volatile boolean done;
        volatile boolean cancelled;
        volatile Throwable error;
        static final AtomicReferenceFieldUpdater<MergeSequentialMain, Throwable> ERROR = AtomicReferenceFieldUpdater.newUpdater(MergeSequentialMain.class, Throwable.class, "error");
        MergeSequentialInner<R> current;
        volatile int wip;
        static final AtomicIntegerFieldUpdater<MergeSequentialMain> WIP = AtomicIntegerFieldUpdater.newUpdater(MergeSequentialMain.class, "wip");
        volatile long requested;
        static final AtomicLongFieldUpdater<MergeSequentialMain> REQUESTED = AtomicLongFieldUpdater.newUpdater(MergeSequentialMain.class, "requested");

        MergeSequentialMain(CoreSubscriber<? super R> actual, Function<? super T, ? extends Publisher<? extends R>> mapper, int maxConcurrency, int prefetch, FluxConcatMap.ErrorMode errorMode, Supplier<Queue<MergeSequentialInner<R>>> queueSupplier) {
            this.actual = actual;
            this.mapper = mapper;
            this.maxConcurrency = maxConcurrency;
            this.prefetch = prefetch;
            this.errorMode = errorMode;
            this.subscribers = queueSupplier.get();
        }

        @Override
        public final CoreSubscriber<? super R> actual() {
            return this.actual;
        }

        @Override
        public Stream<? extends Scannable> inners() {
            return Stream.of(this.subscribers.peek());
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.PARENT) {
                return this.s;
            }
            if (key == Scannable.Attr.ERROR) {
                return this.error;
            }
            if (key == Scannable.Attr.TERMINATED) {
                return this.done && this.subscribers.isEmpty();
            }
            if (key == Scannable.Attr.DELAY_ERROR) {
                return this.errorMode != FluxConcatMap.ErrorMode.IMMEDIATE;
            }
            if (key == Scannable.Attr.PREFETCH) {
                return this.maxConcurrency;
            }
            if (key == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM) {
                return this.requested;
            }
            if (key == Scannable.Attr.BUFFERED) {
                return this.subscribers.size();
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return InnerOperator.super.scanUnsafe(key);
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (Operators.validate(this.s, s)) {
                this.s = s;
                this.actual.onSubscribe(this);
                s.request(this.maxConcurrency == Integer.MAX_VALUE ? Long.MAX_VALUE : (long)this.maxConcurrency);
            }
        }

        public void onNext(T t) {
            Publisher<? extends R> publisher;
            try {
                publisher = Objects.requireNonNull(this.mapper.apply(t), "publisher");
            }
            catch (Throwable ex) {
                this.onError(Operators.onOperatorError(this.s, ex, t, this.actual.currentContext()));
                return;
            }
            MergeSequentialInner inner = new MergeSequentialInner(this, this.prefetch);
            if (this.cancelled) {
                return;
            }
            if (!this.subscribers.offer(inner)) {
                int badSize = this.subscribers.size();
                inner.cancel();
                this.drainAndCancel();
                this.onError(Operators.onOperatorError(this.s, new IllegalStateException("Too many subscribers for fluxMergeSequential on item: " + t + "; subscribers: " + badSize), t, this.actual.currentContext()));
                return;
            }
            if (this.cancelled) {
                return;
            }
            publisher.subscribe(inner);
            if (this.cancelled) {
                inner.cancel();
                this.drainAndCancel();
            }
        }

        public void onError(Throwable t) {
            if (Exceptions.addThrowable(ERROR, this, t)) {
                this.done = true;
                this.drain();
            } else {
                Operators.onErrorDropped(t, this.actual.currentContext());
            }
        }

        public void onComplete() {
            this.done = true;
            this.drain();
        }

        public void cancel() {
            if (this.cancelled) {
                return;
            }
            this.cancelled = true;
            this.s.cancel();
            this.drainAndCancel();
        }

        void drainAndCancel() {
            if (WIP.getAndIncrement(this) == 0) {
                do {
                    this.cancelAll();
                } while (WIP.decrementAndGet(this) != 0);
            }
        }

        void cancelAll() {
            MergeSequentialInner<R> inner;
            MergeSequentialInner<R> c = this.current;
            if (c != null) {
                c.cancel();
            }
            while ((inner = this.subscribers.poll()) != null) {
                inner.cancel();
            }
        }

        public void request(long n) {
            if (Operators.validate(n)) {
                Operators.addCap(REQUESTED, this, n);
                this.drain();
            }
        }

        void innerNext(MergeSequentialInner<R> inner, R value) {
            if (inner.queue().offer(value)) {
                this.drain();
            } else {
                inner.cancel();
                this.onError(Operators.onOperatorError(null, Exceptions.failWithOverflow("Queue is full: Reactive Streams source doesn't respect backpressure"), value, this.actual.currentContext()));
            }
        }

        void innerError(MergeSequentialInner<R> inner, Throwable e) {
            if (Exceptions.addThrowable(ERROR, this, e)) {
                inner.setDone();
                if (this.errorMode != FluxConcatMap.ErrorMode.END) {
                    this.s.cancel();
                }
                this.drain();
            } else {
                Operators.onErrorDropped(e, this.actual.currentContext());
            }
        }

        void innerComplete(MergeSequentialInner<R> inner) {
            inner.setDone();
            this.drain();
        }

        void drain() {
            boolean continueNextSource;
            if (WIP.getAndIncrement(this) != 0) {
                return;
            }
            int missed = 1;
            MergeSequentialInner<R> inner = this.current;
            CoreSubscriber<? super R> a = this.actual;
            FluxConcatMap.ErrorMode em = this.errorMode;
            do {
                Queue<R> q;
                long r = this.requested;
                long e = 0L;
                if (inner == null) {
                    Throwable ex;
                    if (em != FluxConcatMap.ErrorMode.END && (ex = this.error) != null) {
                        this.cancelAll();
                        a.onError(ex);
                        return;
                    }
                    boolean outerDone = this.done;
                    inner = this.subscribers.poll();
                    if (outerDone && inner == null) {
                        Throwable ex2 = this.error;
                        if (ex2 != null) {
                            a.onError(ex2);
                        } else {
                            a.onComplete();
                        }
                        return;
                    }
                    if (inner != null) {
                        this.current = inner;
                    }
                }
                continueNextSource = false;
                if (inner != null && (q = inner.queue()) != null) {
                    boolean d;
                    while (e != r) {
                        boolean empty;
                        R v;
                        Throwable ex;
                        if (this.cancelled) {
                            this.cancelAll();
                            return;
                        }
                        if (em == FluxConcatMap.ErrorMode.IMMEDIATE && (ex = this.error) != null) {
                            this.current = null;
                            inner.cancel();
                            this.cancelAll();
                            a.onError(ex);
                            return;
                        }
                        d = inner.isDone();
                        try {
                            v = q.poll();
                        }
                        catch (Throwable ex3) {
                            this.current = null;
                            inner.cancel();
                            ex3 = Operators.onOperatorError(ex3, this.actual.currentContext());
                            this.cancelAll();
                            a.onError(ex3);
                            return;
                        }
                        boolean bl = empty = v == null;
                        if (d && empty) {
                            inner = null;
                            this.current = null;
                            this.s.request(1L);
                            continueNextSource = true;
                            break;
                        }
                        if (empty) break;
                        a.onNext(v);
                        ++e;
                        inner.requestOne();
                    }
                    if (e == r) {
                        Throwable ex;
                        if (this.cancelled) {
                            this.cancelAll();
                            return;
                        }
                        if (em == FluxConcatMap.ErrorMode.IMMEDIATE && (ex = this.error) != null) {
                            this.current = null;
                            inner.cancel();
                            this.cancelAll();
                            a.onError(ex);
                            return;
                        }
                        d = inner.isDone();
                        boolean empty = q.isEmpty();
                        if (d && empty) {
                            inner = null;
                            this.current = null;
                            this.s.request(1L);
                            continueNextSource = true;
                        }
                    }
                }
                if (e == 0L || r == Long.MAX_VALUE) continue;
                REQUESTED.addAndGet(this, -e);
            } while (continueNextSource || (missed = WIP.addAndGet(this, -missed)) != 0);
        }
    }
}

