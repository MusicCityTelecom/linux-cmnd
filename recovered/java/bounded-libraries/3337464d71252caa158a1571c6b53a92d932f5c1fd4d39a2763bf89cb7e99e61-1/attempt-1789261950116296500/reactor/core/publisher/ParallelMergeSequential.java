/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import java.util.Queue;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.InnerConsumer;
import reactor.core.publisher.InnerProducer;
import reactor.core.publisher.Operators;
import reactor.core.publisher.ParallelFlux;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

final class ParallelMergeSequential<T>
extends Flux<T>
implements Scannable {
    final ParallelFlux<? extends T> source;
    final int prefetch;
    final Supplier<Queue<T>> queueSupplier;

    ParallelMergeSequential(ParallelFlux<? extends T> source, int prefetch, Supplier<Queue<T>> queueSupplier) {
        if (prefetch <= 0) {
            throw new IllegalArgumentException("prefetch > 0 required but it was " + prefetch);
        }
        this.source = source;
        this.prefetch = prefetch;
        this.queueSupplier = queueSupplier;
    }

    @Override
    @Nullable
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.PARENT) {
            return this.source;
        }
        if (key == Scannable.Attr.PREFETCH) {
            return this.getPrefetch();
        }
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return null;
    }

    @Override
    public int getPrefetch() {
        return this.prefetch;
    }

    @Override
    public void subscribe(CoreSubscriber<? super T> actual) {
        MergeSequentialMain<? super T> parent = new MergeSequentialMain<T>(actual, this.source.parallelism(), this.prefetch, this.queueSupplier);
        actual.onSubscribe(parent);
        this.source.subscribe(parent.subscribers);
    }

    static final class MergeSequentialInner<T>
    implements InnerConsumer<T> {
        final MergeSequentialMain<T> parent;
        final int prefetch;
        final int limit;
        long produced;
        volatile Subscription s;
        static final AtomicReferenceFieldUpdater<MergeSequentialInner, Subscription> S = AtomicReferenceFieldUpdater.newUpdater(MergeSequentialInner.class, Subscription.class, "s");
        volatile Queue<T> queue;
        volatile boolean done;

        MergeSequentialInner(MergeSequentialMain<T> parent, int prefetch) {
            this.parent = parent;
            this.prefetch = prefetch;
            this.limit = Operators.unboundedOrLimit(prefetch);
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.CANCELLED) {
                return this.s == Operators.cancelledSubscription();
            }
            if (key == Scannable.Attr.PARENT) {
                return this.s;
            }
            if (key == Scannable.Attr.ACTUAL) {
                return this.parent;
            }
            if (key == Scannable.Attr.PREFETCH) {
                return this.prefetch;
            }
            if (key == Scannable.Attr.BUFFERED) {
                return this.queue != null ? this.queue.size() : 0;
            }
            if (key == Scannable.Attr.TERMINATED) {
                return this.done;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return null;
        }

        @Override
        public Context currentContext() {
            return this.parent.actual.currentContext();
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (Operators.setOnce(S, this, s)) {
                s.request(Operators.unboundedOrPrefetch(this.prefetch));
            }
        }

        public void onNext(T t) {
            this.parent.onNext(this, t);
        }

        public void onError(Throwable t) {
            this.parent.onError(t);
        }

        public void onComplete() {
            this.parent.onComplete();
        }

        void requestOne() {
            long p = this.produced + 1L;
            if (p == (long)this.limit) {
                this.produced = 0L;
                this.s.request(p);
            } else {
                this.produced = p;
            }
        }

        public void cancel() {
            Operators.terminate(S, this);
        }

        Queue<T> getQueue(Supplier<Queue<T>> queueSupplier) {
            Queue<T> q = this.queue;
            if (q == null) {
                this.queue = q = queueSupplier.get();
            }
            return q;
        }
    }

    static final class MergeSequentialMain<T>
    implements InnerProducer<T> {
        final MergeSequentialInner<T>[] subscribers;
        final Supplier<Queue<T>> queueSupplier;
        final CoreSubscriber<? super T> actual;
        static final AtomicReferenceFieldUpdater<MergeSequentialMain, Throwable> ERROR = AtomicReferenceFieldUpdater.newUpdater(MergeSequentialMain.class, Throwable.class, "error");
        volatile int wip;
        static final AtomicIntegerFieldUpdater<MergeSequentialMain> WIP = AtomicIntegerFieldUpdater.newUpdater(MergeSequentialMain.class, "wip");
        volatile long requested;
        static final AtomicLongFieldUpdater<MergeSequentialMain> REQUESTED = AtomicLongFieldUpdater.newUpdater(MergeSequentialMain.class, "requested");
        volatile boolean cancelled;
        volatile int done;
        static final AtomicIntegerFieldUpdater<MergeSequentialMain> DONE = AtomicIntegerFieldUpdater.newUpdater(MergeSequentialMain.class, "done");
        volatile Throwable error;

        MergeSequentialMain(CoreSubscriber<? super T> actual, int n, int prefetch, Supplier<Queue<T>> queueSupplier) {
            this.actual = actual;
            this.queueSupplier = queueSupplier;
            MergeSequentialInner[] a = new MergeSequentialInner[n];
            for (int i = 0; i < n; ++i) {
                a[i] = new MergeSequentialInner(this, prefetch);
            }
            this.subscribers = a;
            DONE.lazySet(this, n);
        }

        @Override
        public final CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.CANCELLED) {
                return this.cancelled;
            }
            if (key == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM) {
                return this.requested;
            }
            if (key == Scannable.Attr.TERMINATED) {
                return this.done == 0;
            }
            if (key == Scannable.Attr.ERROR) {
                return this.error;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return InnerProducer.super.scanUnsafe(key);
        }

        @Override
        public Stream<? extends Scannable> inners() {
            return Stream.of(this.subscribers);
        }

        public void request(long n) {
            if (Operators.validate(n)) {
                Operators.addCap(REQUESTED, this, n);
                this.drain();
            }
        }

        public void cancel() {
            if (!this.cancelled) {
                this.cancelled = true;
                this.cancelAll();
                if (WIP.getAndIncrement(this) == 0) {
                    this.cleanup();
                }
            }
        }

        void cancelAll() {
            for (MergeSequentialInner<T> s : this.subscribers) {
                s.cancel();
            }
        }

        void cleanup() {
            for (MergeSequentialInner<T> s : this.subscribers) {
                s.queue = null;
            }
        }

        void onNext(MergeSequentialInner<T> inner, T value) {
            if (this.wip == 0 && WIP.compareAndSet(this, 0, 1)) {
                if (this.requested != 0L) {
                    this.actual.onNext(value);
                    if (this.requested != Long.MAX_VALUE) {
                        REQUESTED.decrementAndGet(this);
                    }
                    inner.requestOne();
                } else {
                    Queue<T> q = inner.getQueue(this.queueSupplier);
                    if (!q.offer(value)) {
                        this.onError(Operators.onOperatorError(this, Exceptions.failWithOverflow("Queue is full: Reactive Streams source doesn't respect backpressure"), value, this.actual.currentContext()));
                        return;
                    }
                }
                if (WIP.decrementAndGet(this) == 0) {
                    return;
                }
            } else {
                Queue<T> q = inner.getQueue(this.queueSupplier);
                if (!q.offer(value)) {
                    this.onError(Operators.onOperatorError(this, Exceptions.failWithOverflow("Queue is full: Reactive Streams source doesn't respect backpressure"), value, this.actual.currentContext()));
                    return;
                }
                if (WIP.getAndIncrement(this) != 0) {
                    return;
                }
            }
            this.drainLoop();
        }

        void onError(Throwable ex) {
            if (ERROR.compareAndSet(this, null, ex)) {
                this.cancelAll();
                this.drain();
            } else if (this.error != ex) {
                Operators.onErrorDropped(ex, this.actual.currentContext());
            }
        }

        void onComplete() {
            if (DONE.decrementAndGet(this) < 0) {
                return;
            }
            this.drain();
        }

        void drain() {
            if (WIP.getAndIncrement(this) != 0) {
                return;
            }
            this.drainLoop();
        }

        void drainLoop() {
            int missed = 1;
            MergeSequentialInner<T>[] s = this.subscribers;
            int n = s.length;
            CoreSubscriber<? super T> a = this.actual;
            while (true) {
                int w;
                Queue q;
                MergeSequentialInner<T> inner;
                int i;
                boolean empty;
                boolean d;
                Throwable ex;
                long r = this.requested;
                long e = 0L;
                block1: while (e != r) {
                    if (this.cancelled) {
                        this.cleanup();
                        return;
                    }
                    ex = this.error;
                    if (ex != null) {
                        this.cleanup();
                        a.onError(ex);
                        return;
                    }
                    d = this.done == 0;
                    empty = true;
                    for (i = 0; i < n; ++i) {
                        Object v;
                        inner = s[i];
                        q = inner.queue;
                        if (q == null || (v = q.poll()) == null) continue;
                        empty = false;
                        a.onNext(v);
                        inner.requestOne();
                        if (++e == r) break block1;
                    }
                    if (d && empty) {
                        a.onComplete();
                        return;
                    }
                    if (!empty) continue;
                    break;
                }
                if (e == r) {
                    if (this.cancelled) {
                        this.cleanup();
                        return;
                    }
                    ex = this.error;
                    if (ex != null) {
                        this.cleanup();
                        a.onError(ex);
                        return;
                    }
                    d = this.done == 0;
                    empty = true;
                    for (i = 0; i < n; ++i) {
                        inner = s[i];
                        q = inner.queue;
                        if (q == null || q.isEmpty()) continue;
                        empty = false;
                        break;
                    }
                    if (d && empty) {
                        a.onComplete();
                        return;
                    }
                }
                if (e != 0L && r != Long.MAX_VALUE) {
                    REQUESTED.addAndGet(this, -e);
                }
                if ((w = this.wip) == missed) {
                    if ((missed = WIP.addAndGet(this, -missed)) != 0) continue;
                    break;
                }
                missed = w;
            }
        }
    }
}

