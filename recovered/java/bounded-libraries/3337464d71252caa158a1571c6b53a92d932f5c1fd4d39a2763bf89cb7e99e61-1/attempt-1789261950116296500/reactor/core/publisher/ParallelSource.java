/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Publisher
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import java.util.Queue;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongArray;
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.InnerConsumer;
import reactor.core.publisher.InnerProducer;
import reactor.core.publisher.Operators;
import reactor.core.publisher.ParallelFlux;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

final class ParallelSource<T>
extends ParallelFlux<T>
implements Scannable {
    final Publisher<? extends T> source;
    final int parallelism;
    final int prefetch;
    final Supplier<Queue<T>> queueSupplier;

    ParallelSource(Publisher<? extends T> source, int parallelism, int prefetch, Supplier<Queue<T>> queueSupplier) {
        if (parallelism <= 0) {
            throw new IllegalArgumentException("parallelism > 0 required but it was " + parallelism);
        }
        if (prefetch <= 0) {
            throw new IllegalArgumentException("prefetch > 0 required but it was " + prefetch);
        }
        this.source = source;
        this.parallelism = parallelism;
        this.prefetch = prefetch;
        this.queueSupplier = queueSupplier;
    }

    @Override
    public int getPrefetch() {
        return this.prefetch;
    }

    @Override
    public int parallelism() {
        return this.parallelism;
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
    public void subscribe(CoreSubscriber<? super T>[] subscribers) {
        if (!this.validate(subscribers)) {
            return;
        }
        this.source.subscribe(new ParallelSourceMain<T>(subscribers, this.prefetch, this.queueSupplier));
    }

    static final class ParallelSourceMain<T>
    implements InnerConsumer<T> {
        final CoreSubscriber<? super T>[] subscribers;
        final AtomicLongArray requests;
        final long[] emissions;
        final int prefetch;
        final int limit;
        final Supplier<Queue<T>> queueSupplier;
        Subscription s;
        Queue<T> queue;
        Throwable error;
        volatile boolean done;
        int index;
        volatile boolean cancelled;
        volatile int wip;
        static final AtomicIntegerFieldUpdater<ParallelSourceMain> WIP = AtomicIntegerFieldUpdater.newUpdater(ParallelSourceMain.class, "wip");
        volatile int subscriberCount;
        static final AtomicIntegerFieldUpdater<ParallelSourceMain> SUBSCRIBER_COUNT = AtomicIntegerFieldUpdater.newUpdater(ParallelSourceMain.class, "subscriberCount");
        int produced;
        int sourceMode;

        ParallelSourceMain(CoreSubscriber<? super T>[] subscribers, int prefetch, Supplier<Queue<T>> queueSupplier) {
            this.subscribers = subscribers;
            this.prefetch = prefetch;
            this.queueSupplier = queueSupplier;
            this.limit = Operators.unboundedOrLimit(prefetch);
            this.requests = new AtomicLongArray(subscribers.length);
            this.emissions = new long[subscribers.length];
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.PARENT) {
                return this.s;
            }
            if (key == Scannable.Attr.PREFETCH) {
                return this.prefetch;
            }
            if (key == Scannable.Attr.TERMINATED) {
                return this.done;
            }
            if (key == Scannable.Attr.CANCELLED) {
                return this.cancelled;
            }
            if (key == Scannable.Attr.ERROR) {
                return this.error;
            }
            if (key == Scannable.Attr.BUFFERED) {
                return this.queue != null ? this.queue.size() : 0;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return null;
        }

        @Override
        public Stream<? extends Scannable> inners() {
            return Stream.of(this.subscribers).map(Scannable::from);
        }

        @Override
        public Context currentContext() {
            return this.subscribers[0].currentContext();
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (Operators.validate(this.s, s)) {
                this.s = s;
                if (s instanceof Fuseable.QueueSubscription) {
                    Fuseable.QueueSubscription qs = (Fuseable.QueueSubscription)s;
                    int m = qs.requestFusion(7);
                    if (m == 1) {
                        this.sourceMode = m;
                        this.queue = qs;
                        this.done = true;
                        this.setupSubscribers();
                        this.drain();
                        return;
                    }
                    if (m == 2) {
                        this.sourceMode = m;
                        this.queue = qs;
                        this.setupSubscribers();
                        s.request(Operators.unboundedOrPrefetch(this.prefetch));
                        return;
                    }
                }
                this.queue = this.queueSupplier.get();
                this.setupSubscribers();
                s.request(Operators.unboundedOrPrefetch(this.prefetch));
            }
        }

        void setupSubscribers() {
            int m = this.subscribers.length;
            for (int i = 0; i < m; ++i) {
                if (this.cancelled) {
                    return;
                }
                int j = i;
                SUBSCRIBER_COUNT.lazySet(this, i + 1);
                this.subscribers[i].onSubscribe(new ParallelSourceInner(this, j, m));
            }
        }

        public void onNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, this.currentContext());
                return;
            }
            if (this.sourceMode == 0 && !this.queue.offer(t)) {
                this.onError(Operators.onOperatorError(this.s, Exceptions.failWithOverflow("Queue is full: Reactive Streams source doesn't respect backpressure"), t, this.currentContext()));
                return;
            }
            this.drain();
        }

        public void onError(Throwable t) {
            if (this.done) {
                Operators.onErrorDropped(t, this.currentContext());
                return;
            }
            this.error = t;
            this.done = true;
            this.drain();
        }

        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            this.drain();
        }

        void cancel() {
            if (!this.cancelled) {
                this.cancelled = true;
                this.s.cancel();
                if (WIP.getAndIncrement(this) == 0) {
                    this.queue.clear();
                }
            }
        }

        void drainAsync() {
            int missed = 1;
            Queue<T> q = this.queue;
            CoreSubscriber<? super T>[] a = this.subscribers;
            AtomicLongArray r = this.requests;
            long[] e = this.emissions;
            int n = e.length;
            int idx = this.index;
            int consumed = this.produced;
            while (true) {
                int w;
                int notReady = 0;
                do {
                    long eidx;
                    Throwable ex;
                    if (this.cancelled) {
                        q.clear();
                        return;
                    }
                    boolean d = this.done;
                    if (d && (ex = this.error) != null) {
                        q.clear();
                        for (CoreSubscriber<? super T> s : a) {
                            s.onError(ex);
                        }
                        return;
                    }
                    boolean empty = q.isEmpty();
                    if (d && empty) {
                        for (CoreSubscriber<? super T> s : a) {
                            s.onComplete();
                        }
                        return;
                    }
                    if (empty) break;
                    long ridx = r.get(idx);
                    if (ridx != (eidx = e[idx])) {
                        T v;
                        try {
                            v = q.poll();
                        }
                        catch (Throwable ex2) {
                            ex2 = Operators.onOperatorError(this.s, ex2, a[idx].currentContext());
                            for (CoreSubscriber<? super T> s : a) {
                                s.onError(ex2);
                            }
                            return;
                        }
                        if (v == null) break;
                        a[idx].onNext(v);
                        e[idx] = eidx + 1L;
                        int c = ++consumed;
                        if (c == this.limit) {
                            consumed = 0;
                            this.s.request((long)c);
                        }
                        notReady = 0;
                    } else {
                        ++notReady;
                    }
                    if (++idx != n) continue;
                    idx = 0;
                } while (notReady != n);
                if ((w = this.wip) == missed) {
                    this.index = idx;
                    this.produced = consumed;
                    if ((missed = WIP.addAndGet(this, -missed)) != 0) continue;
                    break;
                }
                missed = w;
            }
        }

        void drainSync() {
            int missed = 1;
            Queue<T> q = this.queue;
            CoreSubscriber<? super T>[] a = this.subscribers;
            AtomicLongArray r = this.requests;
            long[] e = this.emissions;
            int n = e.length;
            int idx = this.index;
            while (true) {
                int notReady = 0;
                do {
                    long eidx;
                    if (this.cancelled) {
                        q.clear();
                        return;
                    }
                    if (q.isEmpty()) {
                        for (CoreSubscriber<? super T> s : a) {
                            s.onComplete();
                        }
                        return;
                    }
                    long ridx = r.get(idx);
                    if (ridx != (eidx = e[idx])) {
                        T v;
                        try {
                            v = q.poll();
                        }
                        catch (Throwable ex) {
                            ex = Operators.onOperatorError(this.s, ex, a[idx].currentContext());
                            for (CoreSubscriber<? super T> s : a) {
                                s.onError(ex);
                            }
                            return;
                        }
                        if (v == null) {
                            for (CoreSubscriber<? super T> s : a) {
                                s.onComplete();
                            }
                            return;
                        }
                        a[idx].onNext(v);
                        e[idx] = eidx + 1L;
                        notReady = 0;
                    } else {
                        ++notReady;
                    }
                    if (++idx != n) continue;
                    idx = 0;
                } while (notReady != n);
                int w = this.wip;
                if (w == missed) {
                    this.index = idx;
                    if ((missed = WIP.addAndGet(this, -missed)) != 0) continue;
                    break;
                }
                missed = w;
            }
        }

        void drain() {
            if (WIP.getAndIncrement(this) != 0) {
                return;
            }
            if (this.sourceMode == 1) {
                this.drainSync();
            } else {
                this.drainAsync();
            }
        }

        static final class ParallelSourceInner<T>
        implements InnerProducer<T> {
            final ParallelSourceMain<T> parent;
            final int index;
            final int length;

            ParallelSourceInner(ParallelSourceMain<T> parent, int index, int length) {
                this.index = index;
                this.length = length;
                this.parent = parent;
            }

            @Override
            public CoreSubscriber<? super T> actual() {
                return this.parent.subscribers[this.index];
            }

            @Override
            @Nullable
            public Object scanUnsafe(Scannable.Attr key) {
                if (key == Scannable.Attr.PARENT) {
                    return this.parent;
                }
                if (key == Scannable.Attr.RUN_STYLE) {
                    return Scannable.Attr.RunStyle.SYNC;
                }
                return InnerProducer.super.scanUnsafe(key);
            }

            public void request(long n) {
                if (Operators.validate(n)) {
                    long u;
                    long r;
                    AtomicLongArray ra = this.parent.requests;
                    do {
                        if ((r = ra.get(this.index)) != Long.MAX_VALUE) continue;
                        return;
                    } while (!ra.compareAndSet(this.index, r, u = Operators.addCap(r, n)));
                    if (this.parent.subscriberCount == this.length) {
                        this.parent.drain();
                    }
                }
            }

            public void cancel() {
                this.parent.cancel();
            }
        }
    }
}

