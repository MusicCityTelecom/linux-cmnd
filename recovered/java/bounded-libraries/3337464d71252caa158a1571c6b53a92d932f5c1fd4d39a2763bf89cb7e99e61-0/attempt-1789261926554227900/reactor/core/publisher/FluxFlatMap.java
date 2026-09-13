/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Publisher
 *  org.reactivestreams.Subscriber
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.FlatMapTracker;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxHide;
import reactor.core.publisher.InnerConsumer;
import reactor.core.publisher.InnerOperator;
import reactor.core.publisher.InternalFluxOperator;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

final class FluxFlatMap<T, R>
extends InternalFluxOperator<T, R> {
    final Function<? super T, ? extends Publisher<? extends R>> mapper;
    final boolean delayError;
    final int maxConcurrency;
    final Supplier<? extends Queue<R>> mainQueueSupplier;
    final int prefetch;
    final Supplier<? extends Queue<R>> innerQueueSupplier;

    FluxFlatMap(Flux<? extends T> source, Function<? super T, ? extends Publisher<? extends R>> mapper, boolean delayError, int maxConcurrency, Supplier<? extends Queue<R>> mainQueueSupplier, int prefetch, Supplier<? extends Queue<R>> innerQueueSupplier) {
        super(source);
        if (prefetch <= 0) {
            throw new IllegalArgumentException("prefetch > 0 required but it was " + prefetch);
        }
        if (maxConcurrency <= 0) {
            throw new IllegalArgumentException("maxConcurrency > 0 required but it was " + maxConcurrency);
        }
        this.mapper = Objects.requireNonNull(mapper, "mapper");
        this.delayError = delayError;
        this.prefetch = prefetch;
        this.maxConcurrency = maxConcurrency;
        this.mainQueueSupplier = Objects.requireNonNull(mainQueueSupplier, "mainQueueSupplier");
        this.innerQueueSupplier = Objects.requireNonNull(innerQueueSupplier, "innerQueueSupplier");
    }

    @Override
    public int getPrefetch() {
        return this.prefetch;
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super R> actual) {
        if (FluxFlatMap.trySubscribeScalarMap(this.source, actual, this.mapper, false, true)) {
            return null;
        }
        return new FlatMapMain<T, R>(actual, this.mapper, this.delayError, this.maxConcurrency, this.mainQueueSupplier, this.prefetch, this.innerQueueSupplier);
    }

    static <T, R> boolean trySubscribeScalarMap(Publisher<? extends T> source, CoreSubscriber<? super R> s, Function<? super T, ? extends Publisher<? extends R>> mapper, boolean fuseableExpected, boolean errorContinueExpected) {
        if (source instanceof Callable) {
            Publisher<? extends R> p;
            Object t;
            try {
                t = ((Callable)source).call();
            }
            catch (Throwable e) {
                Throwable e_;
                Context ctx = s.currentContext();
                Throwable throwable = e_ = errorContinueExpected ? Operators.onNextError(null, e, ctx) : Operators.onOperatorError(e, ctx);
                if (e_ != null) {
                    Operators.error(s, e_);
                } else {
                    Operators.complete(s);
                }
                return true;
            }
            if (t == null) {
                Operators.complete(s);
                return true;
            }
            try {
                p = Objects.requireNonNull(mapper.apply(t), "The mapper returned a null Publisher");
            }
            catch (Throwable e) {
                Throwable e_;
                Context ctx = s.currentContext();
                Throwable throwable = e_ = errorContinueExpected ? Operators.onNextError(t, e, ctx) : Operators.onOperatorError(null, e, t, ctx);
                if (e_ != null) {
                    Operators.error(s, e_);
                } else {
                    Operators.complete(s);
                }
                return true;
            }
            if (p instanceof Callable) {
                Object v;
                try {
                    v = ((Callable)p).call();
                }
                catch (Throwable e) {
                    Throwable e_;
                    Context ctx = s.currentContext();
                    Throwable throwable = e_ = errorContinueExpected ? Operators.onNextError(t, e, ctx) : Operators.onOperatorError(null, e, t, ctx);
                    if (e_ != null) {
                        Operators.error(s, e_);
                    } else {
                        Operators.complete(s);
                    }
                    return true;
                }
                if (v != null) {
                    s.onSubscribe(Operators.scalarSubscription(s, v));
                } else {
                    Operators.complete(s);
                }
            } else if (!fuseableExpected || p instanceof Fuseable) {
                p.subscribe(s);
            } else {
                p.subscribe(new FluxHide.SuppressFuseableSubscriber<R>(s));
            }
            return true;
        }
        return false;
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static final class FlatMapInner<R>
    implements InnerConsumer<R>,
    Subscription {
        final FlatMapMain<?, R> parent;
        final int prefetch;
        final int limit;
        volatile Subscription s;
        static final AtomicReferenceFieldUpdater<FlatMapInner, Subscription> S = AtomicReferenceFieldUpdater.newUpdater(FlatMapInner.class, Subscription.class, "s");
        long produced;
        volatile Queue<R> queue;
        volatile boolean done;
        int sourceMode;
        int index;

        FlatMapInner(FlatMapMain<?, R> parent, int prefetch) {
            this.parent = parent;
            this.prefetch = prefetch;
            this.limit = Operators.unboundedOrLimit(prefetch);
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (Operators.setOnce(S, this, s)) {
                if (s instanceof Fuseable.QueueSubscription) {
                    Fuseable.QueueSubscription f = (Fuseable.QueueSubscription)s;
                    int m = f.requestFusion(7);
                    if (m == 1) {
                        this.sourceMode = 1;
                        this.queue = f;
                        this.done = true;
                        this.parent.drain(null);
                        return;
                    }
                    if (m == 2) {
                        this.sourceMode = 2;
                        this.queue = f;
                    }
                }
                s.request(Operators.unboundedOrPrefetch(this.prefetch));
            }
        }

        public void onNext(R t) {
            if (this.sourceMode == 2) {
                this.parent.drain(t);
            } else {
                if (this.done) {
                    Operators.onNextDropped(t, this.parent.currentContext());
                    return;
                }
                if (this.s == Operators.cancelledSubscription()) {
                    Operators.onDiscard(t, this.parent.currentContext());
                    return;
                }
                this.parent.tryEmit(this, t);
            }
        }

        public void onError(Throwable t) {
            this.parent.innerError(this, t);
        }

        public void onComplete() {
            this.done = true;
            this.parent.innerComplete(this);
        }

        public void request(long n) {
            if (this.sourceMode == 1) {
                return;
            }
            long p = this.produced + n;
            if (p >= (long)this.limit) {
                this.produced = 0L;
                this.s.request(p);
            } else {
                this.produced = p;
            }
        }

        @Override
        public Context currentContext() {
            return this.parent.currentContext();
        }

        public void cancel() {
            Operators.terminate(S, this);
            Operators.onDiscardQueueWithClear(this.queue, this.parent.currentContext(), null);
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.PARENT) {
                return this.s;
            }
            if (key == Scannable.Attr.ACTUAL) {
                return this.parent;
            }
            if (key == Scannable.Attr.TERMINATED) {
                return this.done && (this.queue == null || this.queue.isEmpty());
            }
            if (key == Scannable.Attr.CANCELLED) {
                return this.s == Operators.cancelledSubscription();
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
    }

    static final class FlatMapMain<T, R>
    extends FlatMapTracker<FlatMapInner<R>>
    implements InnerOperator<T, R> {
        final boolean delayError;
        final int maxConcurrency;
        final int prefetch;
        final int limit;
        final Function<? super T, ? extends Publisher<? extends R>> mapper;
        final Supplier<? extends Queue<R>> mainQueueSupplier;
        final Supplier<? extends Queue<R>> innerQueueSupplier;
        final CoreSubscriber<? super R> actual;
        volatile Queue<R> scalarQueue;
        volatile Throwable error;
        static final AtomicReferenceFieldUpdater<FlatMapMain, Throwable> ERROR = AtomicReferenceFieldUpdater.newUpdater(FlatMapMain.class, Throwable.class, "error");
        volatile boolean done;
        volatile boolean cancelled;
        Subscription s;
        volatile long requested;
        static final AtomicLongFieldUpdater<FlatMapMain> REQUESTED = AtomicLongFieldUpdater.newUpdater(FlatMapMain.class, "requested");
        volatile int wip;
        static final AtomicIntegerFieldUpdater<FlatMapMain> WIP = AtomicIntegerFieldUpdater.newUpdater(FlatMapMain.class, "wip");
        static final FlatMapInner[] EMPTY = new FlatMapInner[0];
        static final FlatMapInner[] TERMINATED = new FlatMapInner[0];
        int lastIndex;
        int produced;

        FlatMapMain(CoreSubscriber<? super R> actual, Function<? super T, ? extends Publisher<? extends R>> mapper, boolean delayError, int maxConcurrency, Supplier<? extends Queue<R>> mainQueueSupplier, int prefetch, Supplier<? extends Queue<R>> innerQueueSupplier) {
            this.actual = actual;
            this.mapper = mapper;
            this.delayError = delayError;
            this.maxConcurrency = maxConcurrency;
            this.mainQueueSupplier = mainQueueSupplier;
            this.prefetch = prefetch;
            this.innerQueueSupplier = innerQueueSupplier;
            this.limit = Operators.unboundedOrLimit(maxConcurrency);
        }

        @Override
        public final CoreSubscriber<? super R> actual() {
            return this.actual;
        }

        @Override
        public Stream<? extends Scannable> inners() {
            return Stream.of(this.array).filter(Objects::nonNull);
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.PARENT) {
                return this.s;
            }
            if (key == Scannable.Attr.CANCELLED) {
                return this.cancelled;
            }
            if (key == Scannable.Attr.ERROR) {
                return this.error;
            }
            if (key == Scannable.Attr.TERMINATED) {
                return this.done && (this.scalarQueue == null || this.scalarQueue.isEmpty());
            }
            if (key == Scannable.Attr.DELAY_ERROR) {
                return this.delayError;
            }
            if (key == Scannable.Attr.PREFETCH) {
                return this.maxConcurrency;
            }
            if (key == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM) {
                return this.requested;
            }
            if (key == Scannable.Attr.LARGE_BUFFERED) {
                return (this.scalarQueue != null ? (long)this.scalarQueue.size() : 0L) + (long)this.size;
            }
            if (key == Scannable.Attr.BUFFERED) {
                long realBuffered = (this.scalarQueue != null ? (long)this.scalarQueue.size() : 0L) + (long)this.size;
                if (realBuffered <= Integer.MAX_VALUE) {
                    return (int)realBuffered;
                }
                return Integer.MIN_VALUE;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return InnerOperator.super.scanUnsafe(key);
        }

        FlatMapInner<R>[] empty() {
            return EMPTY;
        }

        FlatMapInner<R>[] terminated() {
            return TERMINATED;
        }

        FlatMapInner<R>[] newArray(int size) {
            return new FlatMapInner[size];
        }

        @Override
        void setIndex(FlatMapInner<R> entry, int index) {
            entry.index = index;
        }

        @Override
        void unsubscribeEntry(FlatMapInner<R> entry) {
            entry.cancel();
        }

        public void request(long n) {
            if (Operators.validate(n)) {
                Operators.addCap(REQUESTED, this, n);
                this.drain(null);
            }
        }

        public void cancel() {
            if (!this.cancelled) {
                this.cancelled = true;
                if (WIP.getAndIncrement(this) == 0) {
                    Operators.onDiscardQueueWithClear(this.scalarQueue, this.actual.currentContext(), null);
                    this.scalarQueue = null;
                    this.s.cancel();
                    this.unsubscribe();
                }
            }
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (Operators.validate(this.s, s)) {
                this.s = s;
                this.actual.onSubscribe(this);
                s.request(Operators.unboundedOrPrefetch(this.maxConcurrency));
            }
        }

        public void onNext(T t) {
            Publisher<? extends R> p;
            if (this.done) {
                Operators.onNextDropped(t, this.actual.currentContext());
                return;
            }
            try {
                p = Objects.requireNonNull(this.mapper.apply(t), "The mapper returned a null Publisher");
            }
            catch (Throwable e) {
                Context ctx = this.actual.currentContext();
                Throwable e_ = Operators.onNextError(t, e, ctx, this.s);
                Operators.onDiscard(t, ctx);
                if (e_ != null) {
                    this.onError(e_);
                } else {
                    this.tryEmitScalar(null);
                }
                return;
            }
            if (p instanceof Callable) {
                Object v;
                try {
                    v = ((Callable)p).call();
                }
                catch (Throwable e) {
                    Context ctx = this.actual.currentContext();
                    Throwable e_ = Operators.onNextError(t, e, ctx);
                    if (e_ == null) {
                        this.tryEmitScalar(null);
                    } else if (!this.delayError || !Exceptions.addThrowable(ERROR, this, e_)) {
                        this.onError(Operators.onOperatorError(this.s, e_, t, ctx));
                    }
                    Operators.onDiscard(t, ctx);
                    return;
                }
                this.tryEmitScalar(v);
            } else {
                FlatMapInner inner = new FlatMapInner(this, this.prefetch);
                if (this.add(inner)) {
                    p.subscribe(inner);
                } else {
                    Operators.onDiscard(t, this.actual.currentContext());
                }
            }
        }

        Queue<R> getOrCreateScalarQueue() {
            Queue<R> q = this.scalarQueue;
            if (q == null) {
                this.scalarQueue = q = this.mainQueueSupplier.get();
            }
            return q;
        }

        public void onError(Throwable t) {
            if (this.done) {
                Operators.onErrorDropped(t, this.actual.currentContext());
                return;
            }
            if (Exceptions.addThrowable(ERROR, this, t)) {
                this.done = true;
                this.drain(null);
            } else {
                Operators.onErrorDropped(t, this.actual.currentContext());
            }
        }

        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            this.drain(null);
        }

        void tryEmitScalar(@Nullable R v) {
            if (v == null) {
                if (this.maxConcurrency != Integer.MAX_VALUE) {
                    int p = this.produced + 1;
                    if (p == this.limit) {
                        this.produced = 0;
                        this.s.request((long)p);
                    } else {
                        this.produced = p;
                    }
                }
                return;
            }
            if (this.wip == 0 && WIP.compareAndSet(this, 0, 1)) {
                long r = this.requested;
                Queue<R> q = this.scalarQueue;
                if (r != 0L && (q == null || q.isEmpty())) {
                    this.actual.onNext(v);
                    if (r != Long.MAX_VALUE) {
                        REQUESTED.decrementAndGet(this);
                    }
                    if (this.maxConcurrency != Integer.MAX_VALUE) {
                        int p = this.produced + 1;
                        if (p == this.limit) {
                            this.produced = 0;
                            this.s.request((long)p);
                        } else {
                            this.produced = p;
                        }
                    }
                } else {
                    if (q == null) {
                        q = this.getOrCreateScalarQueue();
                    }
                    if (!q.offer(v) && this.failOverflow(v, this.s)) {
                        this.done = true;
                        this.drainLoop();
                        return;
                    }
                }
                if (WIP.decrementAndGet(this) == 0) {
                    if (this.cancelled) {
                        Operators.onDiscard(v, this.actual.currentContext());
                    }
                    return;
                }
                this.drainLoop();
            } else {
                Queue<R> q = this.getOrCreateScalarQueue();
                if (!q.offer(v) && this.failOverflow(v, this.s)) {
                    this.done = true;
                }
                this.drain(v);
            }
        }

        void tryEmit(FlatMapInner<R> inner, R v) {
            if (this.wip == 0 && WIP.compareAndSet(this, 0, 1)) {
                long r = this.requested;
                Queue q = inner.queue;
                if (r != 0L && (q == null || q.isEmpty())) {
                    this.actual.onNext(v);
                    if (r != Long.MAX_VALUE) {
                        REQUESTED.decrementAndGet(this);
                    }
                    inner.request(1L);
                } else {
                    if (q == null) {
                        q = this.getOrCreateInnerQueue(inner);
                    }
                    if (!q.offer(v) && this.failOverflow(v, inner)) {
                        inner.done = true;
                        this.drainLoop();
                        return;
                    }
                }
                if (WIP.decrementAndGet(this) == 0) {
                    if (this.cancelled) {
                        Operators.onDiscard(v, this.actual.currentContext());
                    }
                    return;
                }
                this.drainLoop();
            } else {
                Queue<R> q = this.getOrCreateInnerQueue(inner);
                if (!q.offer(v) && this.failOverflow(v, inner)) {
                    inner.done = true;
                }
                this.drain(v);
            }
        }

        void drain(@Nullable R dataSignal) {
            if (WIP.getAndIncrement(this) != 0) {
                if (dataSignal != null && this.cancelled) {
                    Operators.onDiscard(dataSignal, this.actual.currentContext());
                }
                return;
            }
            this.drainLoop();
        }

        void drainLoop() {
            boolean again;
            int missed = 1;
            CoreSubscriber<? super R> a = this.actual;
            do {
                long e;
                boolean d = this.done;
                FlatMapInner[] as = (FlatMapInner[])this.get();
                int n = as.length;
                Queue<R> sq = this.scalarQueue;
                boolean noSources = this.isEmpty();
                if (this.checkTerminated(d, noSources && (sq == null || sq.isEmpty()), a, null)) {
                    return;
                }
                again = false;
                long r = this.requested;
                long replenishMain = 0L;
                if (r != 0L && sq != null) {
                    for (e = 0L; e != r; ++e) {
                        boolean empty;
                        d = this.done;
                        R v = sq.poll();
                        boolean bl = empty = v == null;
                        if (this.checkTerminated(d, false, a, v)) {
                            return;
                        }
                        if (empty) break;
                        a.onNext(v);
                    }
                    if (e != 0L) {
                        replenishMain += e;
                        if (r != Long.MAX_VALUE) {
                            r = REQUESTED.addAndGet(this, -e);
                        }
                        e = 0L;
                        again = true;
                    }
                }
                if (r != 0L && !noSources) {
                    int j = this.lastIndex;
                    for (int i = 0; i < n; ++i) {
                        if (this.cancelled) {
                            Operators.onDiscardQueueWithClear(this.scalarQueue, this.actual.currentContext(), null);
                            this.scalarQueue = null;
                            this.s.cancel();
                            this.unsubscribe();
                            return;
                        }
                        FlatMapInner inner = as[j];
                        if (inner != null) {
                            d = inner.done;
                            Queue q = inner.queue;
                            if (d && q == null) {
                                this.remove(inner.index);
                                again = true;
                                ++replenishMain;
                            } else if (q != null) {
                                while (e != r) {
                                    boolean empty;
                                    Object v;
                                    d = inner.done;
                                    try {
                                        v = q.poll();
                                    }
                                    catch (Throwable ex) {
                                        ex = Operators.onOperatorError(inner, ex, this.actual.currentContext());
                                        if (!Exceptions.addThrowable(ERROR, this, ex)) {
                                            Operators.onErrorDropped(ex, this.actual.currentContext());
                                        }
                                        v = null;
                                        d = true;
                                    }
                                    boolean bl = empty = v == null;
                                    if (this.checkTerminated(d, false, a, v)) {
                                        return;
                                    }
                                    if (d && empty) {
                                        this.remove(inner.index);
                                        again = true;
                                        ++replenishMain;
                                        break;
                                    }
                                    if (empty) break;
                                    a.onNext(v);
                                    ++e;
                                }
                                if (e == r) {
                                    d = inner.done;
                                    boolean empty = q.isEmpty();
                                    if (d && empty) {
                                        this.remove(inner.index);
                                        again = true;
                                        ++replenishMain;
                                    }
                                }
                                if (e != 0L) {
                                    if (!inner.done) {
                                        inner.request(e);
                                    }
                                    if (r != Long.MAX_VALUE && (r = REQUESTED.addAndGet(this, -e)) == 0L) break;
                                    e = 0L;
                                }
                            }
                        }
                        if (r == 0L) break;
                        if (++j != n) continue;
                        j = 0;
                    }
                    this.lastIndex = j;
                }
                if (r == 0L && !noSources) {
                    as = (FlatMapInner[])this.get();
                    n = as.length;
                    for (int i = 0; i < n; ++i) {
                        boolean empty;
                        if (this.cancelled) {
                            Operators.onDiscardQueueWithClear(this.scalarQueue, this.actual.currentContext(), null);
                            this.scalarQueue = null;
                            this.s.cancel();
                            this.unsubscribe();
                            return;
                        }
                        FlatMapInner inner = as[i];
                        if (inner == null) continue;
                        d = inner.done;
                        Queue q = inner.queue;
                        boolean bl = empty = q == null || q.isEmpty();
                        if (!empty) break;
                        if (!d || !empty) continue;
                        this.remove(inner.index);
                        again = true;
                        ++replenishMain;
                    }
                }
                if (replenishMain == 0L || this.done || this.cancelled) continue;
                this.s.request(replenishMain);
            } while (again || (missed = WIP.addAndGet(this, -missed)) != 0);
        }

        boolean checkTerminated(boolean d, boolean empty, Subscriber<?> a, @Nullable R value) {
            if (this.cancelled) {
                Context ctx = this.actual.currentContext();
                Operators.onDiscard(value, ctx);
                Operators.onDiscardQueueWithClear(this.scalarQueue, ctx, null);
                this.scalarQueue = null;
                this.s.cancel();
                this.unsubscribe();
                return true;
            }
            if (this.delayError) {
                if (d && empty) {
                    Throwable e = this.error;
                    if (e != null && e != Exceptions.TERMINATED) {
                        e = Exceptions.terminate(ERROR, this);
                        a.onError(e);
                    } else {
                        a.onComplete();
                    }
                    return true;
                }
            } else if (d) {
                Throwable e = this.error;
                if (e != null && e != Exceptions.TERMINATED) {
                    e = Exceptions.terminate(ERROR, this);
                    Context ctx = this.actual.currentContext();
                    Operators.onDiscard(value, ctx);
                    Operators.onDiscardQueueWithClear(this.scalarQueue, ctx, null);
                    this.scalarQueue = null;
                    this.s.cancel();
                    this.unsubscribe();
                    a.onError(e);
                    return true;
                }
                if (empty) {
                    a.onComplete();
                    return true;
                }
            }
            return false;
        }

        void innerError(FlatMapInner<R> inner, Throwable e) {
            if ((e = Operators.onNextInnerError(e, this.currentContext(), this.s)) != null) {
                if (Exceptions.addThrowable(ERROR, this, e)) {
                    if (!this.delayError) {
                        this.done = true;
                    }
                    inner.done = true;
                    this.drain(null);
                } else {
                    inner.done = true;
                    Operators.onErrorDropped(e, this.actual.currentContext());
                }
            } else {
                inner.done = true;
                this.drain(null);
            }
        }

        boolean failOverflow(R v, Subscription toCancel) {
            Throwable e = Operators.onOperatorError(toCancel, Exceptions.failWithOverflow("Queue is full: Reactive Streams source doesn't respect backpressure"), v, this.actual.currentContext());
            Operators.onDiscard(v, this.actual.currentContext());
            if (!Exceptions.addThrowable(ERROR, this, e)) {
                Operators.onErrorDropped(e, this.actual.currentContext());
                return false;
            }
            return true;
        }

        void innerComplete(FlatMapInner<R> inner) {
            if (WIP.getAndIncrement(this) != 0) {
                return;
            }
            this.drainLoop();
        }

        Queue<R> getOrCreateInnerQueue(FlatMapInner<R> inner) {
            Queue q = inner.queue;
            if (q == null) {
                inner.queue = q = this.innerQueueSupplier.get();
            }
            return q;
        }
    }
}

