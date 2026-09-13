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
import reactor.core.publisher.InnerConsumer;
import reactor.core.publisher.InnerOperator;
import reactor.core.publisher.InnerProducer;
import reactor.core.publisher.InternalFluxOperator;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

final class FluxPublishMulticast<T, R>
extends InternalFluxOperator<T, R>
implements Fuseable {
    final Function<? super Flux<T>, ? extends Publisher<? extends R>> transform;
    final Supplier<? extends Queue<T>> queueSupplier;
    final int prefetch;

    FluxPublishMulticast(Flux<? extends T> source, Function<? super Flux<T>, ? extends Publisher<? extends R>> transform, int prefetch, Supplier<? extends Queue<T>> queueSupplier) {
        super(source);
        if (prefetch < 1) {
            throw new IllegalArgumentException("prefetch > 0 required but it was " + prefetch);
        }
        this.prefetch = prefetch;
        this.transform = Objects.requireNonNull(transform, "transform");
        this.queueSupplier = Objects.requireNonNull(queueSupplier, "queueSupplier");
    }

    @Override
    public int getPrefetch() {
        return this.prefetch;
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super R> actual) {
        FluxPublishMulticaster multicast = new FluxPublishMulticaster(this.prefetch, this.queueSupplier, actual.currentContext());
        Publisher<? extends R> out = Objects.requireNonNull(this.transform.apply(multicast), "The transform returned a null Publisher");
        if (out instanceof Fuseable) {
            out.subscribe(new CancelFuseableMulticaster<R>(actual, multicast));
        } else {
            out.subscribe(new CancelMulticaster<R>(actual, multicast));
        }
        return multicast;
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static final class CancelFuseableMulticaster<T>
    implements InnerOperator<T, T>,
    Fuseable.QueueSubscription<T> {
        final CoreSubscriber<? super T> actual;
        final PublishMulticasterParent parent;
        Fuseable.QueueSubscription<T> s;

        CancelFuseableMulticaster(CoreSubscriber<? super T> actual, PublishMulticasterParent parent) {
            this.actual = actual;
            this.parent = parent;
        }

        @Override
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.PARENT) {
                return this.s;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return InnerOperator.super.scanUnsafe(key);
        }

        public void request(long n) {
            this.s.request(n);
        }

        public void cancel() {
            this.s.cancel();
            this.parent.terminate();
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (Operators.validate(this.s, s)) {
                this.s = Operators.as(s);
                this.actual.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            this.actual.onNext(t);
        }

        public void onError(Throwable t) {
            this.actual.onError(t);
            this.parent.terminate();
        }

        public void onComplete() {
            this.actual.onComplete();
            this.parent.terminate();
        }

        @Override
        public int requestFusion(int requestedMode) {
            return this.s.requestFusion(requestedMode);
        }

        @Override
        @Nullable
        public T poll() {
            return (T)this.s.poll();
        }

        @Override
        public boolean isEmpty() {
            return this.s.isEmpty();
        }

        @Override
        public int size() {
            return this.s.size();
        }

        @Override
        public void clear() {
            this.s.clear();
        }
    }

    static final class CancelMulticaster<T>
    implements InnerOperator<T, T>,
    Fuseable.QueueSubscription<T> {
        final CoreSubscriber<? super T> actual;
        final PublishMulticasterParent parent;
        Subscription s;

        CancelMulticaster(CoreSubscriber<? super T> actual, PublishMulticasterParent parent) {
            this.actual = actual;
            this.parent = parent;
        }

        @Override
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.PARENT) {
                return this.s;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return InnerOperator.super.scanUnsafe(key);
        }

        public void request(long n) {
            this.s.request(n);
        }

        public void cancel() {
            this.s.cancel();
            this.parent.terminate();
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (Operators.validate(this.s, s)) {
                this.s = s;
                this.actual.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            this.actual.onNext(t);
        }

        public void onError(Throwable t) {
            this.actual.onError(t);
            this.parent.terminate();
        }

        public void onComplete() {
            this.actual.onComplete();
            this.parent.terminate();
        }

        @Override
        public int requestFusion(int requestedMode) {
            return 0;
        }

        @Override
        public void clear() {
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public int size() {
            return 0;
        }

        @Override
        @Nullable
        public T poll() {
            return null;
        }
    }

    static interface PublishMulticasterParent {
        public void terminate();
    }

    static final class PublishMulticastInner<T>
    implements InnerProducer<T> {
        final FluxPublishMulticaster<T> parent;
        final CoreSubscriber<? super T> actual;
        volatile long requested;
        static final AtomicLongFieldUpdater<PublishMulticastInner> REQUESTED = AtomicLongFieldUpdater.newUpdater(PublishMulticastInner.class, "requested");

        PublishMulticastInner(FluxPublishMulticaster<T> parent, CoreSubscriber<? super T> actual) {
            this.parent = parent;
            this.actual = actual;
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM) {
                return Math.max(0L, this.requested);
            }
            if (key == Scannable.Attr.PARENT) {
                return this.parent;
            }
            if (key == Scannable.Attr.CANCELLED) {
                return Long.MIN_VALUE == this.requested;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return InnerProducer.super.scanUnsafe(key);
        }

        @Override
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        public void request(long n) {
            if (Operators.validate(n)) {
                Operators.addCapCancellable(REQUESTED, this, n);
                this.parent.drain();
            }
        }

        public void cancel() {
            if (REQUESTED.getAndSet(this, Long.MIN_VALUE) != Long.MIN_VALUE) {
                this.parent.remove(this);
                this.parent.drain();
            }
        }

        void produced(long n) {
            Operators.producedCancellable(REQUESTED, this, n);
        }
    }

    static final class FluxPublishMulticaster<T>
    extends Flux<T>
    implements InnerConsumer<T>,
    PublishMulticasterParent {
        final int limit;
        final int prefetch;
        final Supplier<? extends Queue<T>> queueSupplier;
        Queue<T> queue;
        volatile Subscription s;
        static final AtomicReferenceFieldUpdater<FluxPublishMulticaster, Subscription> S = AtomicReferenceFieldUpdater.newUpdater(FluxPublishMulticaster.class, Subscription.class, "s");
        volatile int wip;
        static final AtomicIntegerFieldUpdater<FluxPublishMulticaster> WIP = AtomicIntegerFieldUpdater.newUpdater(FluxPublishMulticaster.class, "wip");
        volatile PublishMulticastInner<T>[] subscribers;
        static final AtomicReferenceFieldUpdater<FluxPublishMulticaster, PublishMulticastInner[]> SUBSCRIBERS = AtomicReferenceFieldUpdater.newUpdater(FluxPublishMulticaster.class, PublishMulticastInner[].class, "subscribers");
        static final PublishMulticastInner[] EMPTY = new PublishMulticastInner[0];
        static final PublishMulticastInner[] TERMINATED = new PublishMulticastInner[0];
        volatile boolean done;
        volatile boolean connected;
        Throwable error;
        final Context context;
        int produced;
        int sourceMode;

        FluxPublishMulticaster(int prefetch, Supplier<? extends Queue<T>> queueSupplier, Context ctx) {
            this.prefetch = prefetch;
            this.limit = Operators.unboundedOrLimit(prefetch);
            this.queueSupplier = queueSupplier;
            SUBSCRIBERS.lazySet(this, EMPTY);
            this.context = ctx;
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
            if (key == Scannable.Attr.CANCELLED) {
                return this.s == Operators.cancelledSubscription();
            }
            if (key == Scannable.Attr.TERMINATED) {
                return this.done;
            }
            if (key == Scannable.Attr.PREFETCH) {
                return this.prefetch;
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
            return Stream.of(this.subscribers);
        }

        @Override
        public Context currentContext() {
            return this.context;
        }

        @Override
        public void subscribe(CoreSubscriber<? super T> actual) {
            PublishMulticastInner<? super T> pcs = new PublishMulticastInner<T>(this, actual);
            actual.onSubscribe(pcs);
            if (this.add(pcs)) {
                if (pcs.requested == Long.MIN_VALUE) {
                    this.remove(pcs);
                    return;
                }
                this.drain();
            } else {
                Throwable ex = this.error;
                if (ex != null) {
                    actual.onError(ex);
                } else {
                    actual.onComplete();
                }
            }
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (Operators.setOnce(S, this, s)) {
                if (s instanceof Fuseable.QueueSubscription) {
                    Fuseable.QueueSubscription qs = (Fuseable.QueueSubscription)s;
                    int m = qs.requestFusion(3);
                    if (m == 1) {
                        this.sourceMode = m;
                        this.queue = qs;
                        this.done = true;
                        this.connected = true;
                        this.drain();
                        return;
                    }
                    if (m == 2) {
                        this.sourceMode = m;
                        this.queue = qs;
                        this.connected = true;
                        s.request(Operators.unboundedOrPrefetch(this.prefetch));
                        return;
                    }
                }
                this.queue = this.queueSupplier.get();
                this.connected = true;
                s.request(Operators.unboundedOrPrefetch(this.prefetch));
            }
        }

        public void onNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, this.context);
                return;
            }
            if (this.sourceMode != 2 && !this.queue.offer(t)) {
                this.onError(Operators.onOperatorError(this.s, Exceptions.failWithOverflow("Queue is full: Reactive Streams source doesn't respect backpressure"), t, this.context));
                return;
            }
            this.drain();
        }

        public void onError(Throwable t) {
            if (this.done) {
                Operators.onErrorDropped(t, this.context);
                return;
            }
            this.error = t;
            this.done = true;
            this.drain();
        }

        public void onComplete() {
            this.done = true;
            this.drain();
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

        void drainSync() {
            int missed = 1;
            do {
                long e;
                if (!this.connected) continue;
                if (this.s == Operators.cancelledSubscription()) {
                    this.queue.clear();
                    return;
                }
                Queue<T> queue = this.queue;
                PublishMulticastInner<T>[] a = this.subscribers;
                int n = a.length;
                if (n == 0) continue;
                long r = Long.MAX_VALUE;
                for (int i = 0; i < n; ++i) {
                    long u = a[i].requested;
                    if (u == Long.MIN_VALUE) continue;
                    r = Math.min(r, u);
                }
                for (e = 0L; e != r; ++e) {
                    int i;
                    T v;
                    if (this.s == Operators.cancelledSubscription()) {
                        queue.clear();
                        return;
                    }
                    try {
                        v = queue.poll();
                    }
                    catch (Throwable ex) {
                        this.error = Operators.onOperatorError(this.s, ex, this.context);
                        queue.clear();
                        a = SUBSCRIBERS.getAndSet(this, TERMINATED);
                        n = a.length;
                        for (int i2 = 0; i2 < n; ++i2) {
                            a[i2].actual.onError(ex);
                        }
                        return;
                    }
                    if (v == null) {
                        a = SUBSCRIBERS.getAndSet(this, TERMINATED);
                        n = a.length;
                        for (i = 0; i < n; ++i) {
                            a[i].actual.onComplete();
                        }
                        return;
                    }
                    for (i = 0; i < n; ++i) {
                        a[i].actual.onNext(v);
                    }
                }
                if (this.s == Operators.cancelledSubscription()) {
                    queue.clear();
                    return;
                }
                if (queue.isEmpty()) {
                    a = SUBSCRIBERS.getAndSet(this, TERMINATED);
                    n = a.length;
                    for (int i = 0; i < n; ++i) {
                        a[i].actual.onComplete();
                    }
                    return;
                }
                if (e == 0L) continue;
                for (int i = 0; i < n; ++i) {
                    a[i].produced(e);
                }
            } while ((missed = WIP.addAndGet(this, -missed)) != 0);
        }

        void drainAsync() {
            int missed = 1;
            int p = this.produced;
            do {
                if (this.connected) {
                    if (this.s == Operators.cancelledSubscription()) {
                        this.queue.clear();
                        return;
                    }
                    Queue<T> queue = this.queue;
                    PublishMulticastInner<T>[] a = this.subscribers;
                    int n = a.length;
                    if (n != 0) {
                        boolean d;
                        long e;
                        long r = Long.MAX_VALUE;
                        for (int i = 0; i < n; ++i) {
                            long u = a[i].requested;
                            if (u == Long.MIN_VALUE) continue;
                            r = Math.min(r, u);
                        }
                        for (e = 0L; e != r; ++e) {
                            boolean empty;
                            T v;
                            if (this.s == Operators.cancelledSubscription()) {
                                queue.clear();
                                return;
                            }
                            d = this.done;
                            try {
                                v = queue.poll();
                            }
                            catch (Throwable ex) {
                                queue.clear();
                                this.error = Operators.onOperatorError(this.s, ex, this.context);
                                a = SUBSCRIBERS.getAndSet(this, TERMINATED);
                                n = a.length;
                                for (int i = 0; i < n; ++i) {
                                    a[i].actual.onError(ex);
                                }
                                return;
                            }
                            boolean bl = empty = v == null;
                            if (d) {
                                Throwable ex = this.error;
                                if (ex != null) {
                                    queue.clear();
                                    a = SUBSCRIBERS.getAndSet(this, TERMINATED);
                                    n = a.length;
                                    for (int i = 0; i < n; ++i) {
                                        a[i].actual.onError(ex);
                                    }
                                    return;
                                }
                                if (empty) {
                                    a = SUBSCRIBERS.getAndSet(this, TERMINATED);
                                    n = a.length;
                                    for (int i = 0; i < n; ++i) {
                                        a[i].actual.onComplete();
                                    }
                                    return;
                                }
                            }
                            if (empty) break;
                            for (int i = 0; i < n; ++i) {
                                a[i].actual.onNext(v);
                            }
                            if (++p != this.limit) continue;
                            this.s.request((long)p);
                            p = 0;
                        }
                        if (e == r) {
                            if (this.s == Operators.cancelledSubscription()) {
                                queue.clear();
                                return;
                            }
                            d = this.done;
                            if (d) {
                                int i;
                                Throwable ex = this.error;
                                if (ex != null) {
                                    queue.clear();
                                    a = SUBSCRIBERS.getAndSet(this, TERMINATED);
                                    n = a.length;
                                    for (i = 0; i < n; ++i) {
                                        a[i].actual.onError(ex);
                                    }
                                    return;
                                }
                                if (queue.isEmpty()) {
                                    a = SUBSCRIBERS.getAndSet(this, TERMINATED);
                                    n = a.length;
                                    for (i = 0; i < n; ++i) {
                                        a[i].actual.onComplete();
                                    }
                                    return;
                                }
                            }
                        }
                        if (e != 0L) {
                            for (int i = 0; i < n; ++i) {
                                a[i].produced(e);
                            }
                        }
                    }
                }
                this.produced = p;
            } while ((missed = WIP.addAndGet(this, -missed)) != 0);
        }

        boolean add(PublishMulticastInner<T> s) {
            PublishMulticastInner[] b;
            PublishMulticastInner<T>[] a;
            do {
                if ((a = this.subscribers) == TERMINATED) {
                    return false;
                }
                int n = a.length;
                b = new PublishMulticastInner[n + 1];
                System.arraycopy(a, 0, b, 0, n);
                b[n] = s;
            } while (!SUBSCRIBERS.compareAndSet(this, a, b));
            return true;
        }

        void remove(PublishMulticastInner<T> s) {
            PublishMulticastInner[] b;
            PublishMulticastInner<T>[] a;
            do {
                if ((a = this.subscribers) == TERMINATED || a == EMPTY) {
                    return;
                }
                int n = a.length;
                int j = -1;
                for (int i = 0; i < n; ++i) {
                    if (a[i] != s) continue;
                    j = i;
                    break;
                }
                if (j < 0) {
                    return;
                }
                if (n == 1) {
                    b = EMPTY;
                    continue;
                }
                b = new PublishMulticastInner[n - 1];
                System.arraycopy(a, 0, b, 0, j);
                System.arraycopy(a, j + 1, b, j, n - j - 1);
            } while (!SUBSCRIBERS.compareAndSet(this, a, b));
        }

        @Override
        public void terminate() {
            Operators.terminate(S, this);
            if (WIP.getAndIncrement(this) == 0 && this.connected) {
                this.queue.clear();
            }
        }
    }
}

