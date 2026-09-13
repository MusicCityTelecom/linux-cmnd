/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Publisher
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import java.util.Iterator;
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
import reactor.core.publisher.FluxMap;
import reactor.core.publisher.FluxMapFuseable;
import reactor.core.publisher.InnerConsumer;
import reactor.core.publisher.InnerProducer;
import reactor.core.publisher.Operators;
import reactor.core.publisher.SourceProducer;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

final class FluxCombineLatest<T, R>
extends Flux<R>
implements Fuseable,
SourceProducer<R> {
    final Publisher<? extends T>[] array;
    final Iterable<? extends Publisher<? extends T>> iterable;
    final Function<Object[], R> combiner;
    final Supplier<? extends Queue<SourceAndArray>> queueSupplier;
    final int prefetch;

    FluxCombineLatest(Publisher<? extends T>[] array, Function<Object[], R> combiner, Supplier<? extends Queue<SourceAndArray>> queueSupplier, int prefetch) {
        if (prefetch <= 0) {
            throw new IllegalArgumentException("prefetch > 0 required but it was " + prefetch);
        }
        this.array = Objects.requireNonNull(array, "array");
        this.iterable = null;
        this.combiner = Objects.requireNonNull(combiner, "combiner");
        this.queueSupplier = Objects.requireNonNull(queueSupplier, "queueSupplier");
        this.prefetch = prefetch;
    }

    FluxCombineLatest(Iterable<? extends Publisher<? extends T>> iterable, Function<Object[], R> combiner, Supplier<? extends Queue<SourceAndArray>> queueSupplier, int prefetch) {
        if (prefetch < 0) {
            throw new IllegalArgumentException("prefetch > 0 required but it was " + prefetch);
        }
        this.array = null;
        this.iterable = Objects.requireNonNull(iterable, "iterable");
        this.combiner = Objects.requireNonNull(combiner, "combiner");
        this.queueSupplier = Objects.requireNonNull(queueSupplier, "queueSupplier");
        this.prefetch = prefetch;
    }

    @Override
    public int getPrefetch() {
        return this.prefetch;
    }

    @Override
    public void subscribe(CoreSubscriber<? super R> actual) {
        int n;
        Publisher<? extends T>[] a;
        block13: {
            a = this.array;
            if (a == null) {
                Iterator<Publisher<T>> it;
                n = 0;
                a = new Publisher[8];
                try {
                    it = Objects.requireNonNull(this.iterable.iterator(), "The iterator returned is null");
                }
                catch (Throwable e) {
                    Operators.error(actual, Operators.onOperatorError(e, actual.currentContext()));
                    return;
                }
                while (true) {
                    Publisher<? extends T> p;
                    boolean b;
                    try {
                        b = it.hasNext();
                    }
                    catch (Throwable e) {
                        Operators.error(actual, Operators.onOperatorError(e, actual.currentContext()));
                        return;
                    }
                    if (!b) break block13;
                    try {
                        p = Objects.requireNonNull(it.next(), "The Publisher returned by the iterator is null");
                    }
                    catch (Throwable e) {
                        Operators.error(actual, Operators.onOperatorError(e, actual.currentContext()));
                        return;
                    }
                    if (n == a.length) {
                        Publisher[] c = new Publisher[n + (n >> 2)];
                        System.arraycopy(a, 0, c, 0, n);
                        a = c;
                    }
                    a[n++] = p;
                }
            }
            n = a.length;
        }
        if (n == 0) {
            Operators.complete(actual);
            return;
        }
        if (n == 1) {
            Function<Object, Object> f = t -> this.combiner.apply(new Object[]{t});
            if (a[0] instanceof Fuseable) {
                new FluxMapFuseable<Object, Object>(FluxCombineLatest.from(a[0]), f).subscribe(actual);
                return;
            }
            if (!(actual instanceof Fuseable.QueueSubscription)) {
                new FluxMap<Object, Object>(FluxCombineLatest.from(a[0]), f).subscribe(actual);
                return;
            }
        }
        Queue<SourceAndArray> queue = this.queueSupplier.get();
        CombineLatestCoordinator<? extends T, R> coordinator = new CombineLatestCoordinator<T, R>(actual, this.combiner, n, queue, this.prefetch);
        actual.onSubscribe(coordinator);
        coordinator.subscribe(a, n);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.PREFETCH) {
            return this.prefetch;
        }
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return null;
    }

    static final class SourceAndArray {
        final CombineLatestInner<?> source;
        final Object[] array;

        SourceAndArray(CombineLatestInner<?> source, Object[] array) {
            this.source = source;
            this.array = array;
        }

        final Stream<?> toStream() {
            return Stream.of(this.array);
        }
    }

    static final class CombineLatestInner<T>
    implements InnerConsumer<T> {
        final CombineLatestCoordinator<T, ?> parent;
        final int index;
        final int prefetch;
        final int limit;
        volatile Subscription s;
        static final AtomicReferenceFieldUpdater<CombineLatestInner, Subscription> S = AtomicReferenceFieldUpdater.newUpdater(CombineLatestInner.class, Subscription.class, "s");
        int produced;

        CombineLatestInner(CombineLatestCoordinator<T, ?> parent, int index, int prefetch) {
            this.parent = parent;
            this.index = index;
            this.prefetch = prefetch;
            this.limit = Operators.unboundedOrLimit(prefetch);
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
            this.parent.innerValue(this.index, t);
        }

        public void onError(Throwable t) {
            this.parent.innerError(t);
        }

        public void onComplete() {
            this.parent.innerComplete(this.index);
        }

        public void cancel() {
            Operators.terminate(S, this);
        }

        void requestOne() {
            int p = this.produced + 1;
            if (p == this.limit) {
                this.produced = 0;
                this.s.request((long)p);
            } else {
                this.produced = p;
            }
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
            if (key == Scannable.Attr.CANCELLED) {
                return this.s == Operators.cancelledSubscription();
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

    static final class CombineLatestCoordinator<T, R>
    implements Fuseable.QueueSubscription<R>,
    InnerProducer<R> {
        final Function<Object[], R> combiner;
        final CombineLatestInner<T>[] subscribers;
        final Queue<SourceAndArray> queue;
        final Object[] latest;
        final CoreSubscriber<? super R> actual;
        boolean outputFused;
        int nonEmptySources;
        int completedSources;
        volatile boolean cancelled;
        volatile long requested;
        static final AtomicLongFieldUpdater<CombineLatestCoordinator> REQUESTED = AtomicLongFieldUpdater.newUpdater(CombineLatestCoordinator.class, "requested");
        volatile int wip;
        static final AtomicIntegerFieldUpdater<CombineLatestCoordinator> WIP = AtomicIntegerFieldUpdater.newUpdater(CombineLatestCoordinator.class, "wip");
        volatile boolean done;
        volatile Throwable error;
        static final AtomicReferenceFieldUpdater<CombineLatestCoordinator, Throwable> ERROR = AtomicReferenceFieldUpdater.newUpdater(CombineLatestCoordinator.class, Throwable.class, "error");

        CombineLatestCoordinator(CoreSubscriber<? super R> actual, Function<Object[], R> combiner, int n, Queue<SourceAndArray> queue, int prefetch) {
            this.actual = actual;
            this.combiner = combiner;
            CombineLatestInner[] a = new CombineLatestInner[n];
            for (int i = 0; i < n; ++i) {
                a[i] = new CombineLatestInner(this, i, prefetch);
            }
            this.subscribers = a;
            this.latest = new Object[n];
            this.queue = queue;
        }

        @Override
        public final CoreSubscriber<? super R> actual() {
            return this.actual;
        }

        public void request(long n) {
            if (Operators.validate(n)) {
                Operators.addCap(REQUESTED, this, n);
                this.drain();
            }
        }

        public void cancel() {
            if (this.cancelled) {
                return;
            }
            this.cancelled = true;
            this.cancelAll();
            if (WIP.getAndIncrement(this) == 0) {
                this.clear();
            }
        }

        @Override
        public Stream<? extends Scannable> inners() {
            return Stream.of(this.subscribers);
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.TERMINATED) {
                return this.done;
            }
            if (key == Scannable.Attr.CANCELLED) {
                return this.cancelled;
            }
            if (key == Scannable.Attr.ERROR) {
                return this.error;
            }
            if (key == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM) {
                return this.requested;
            }
            return InnerProducer.super.scanUnsafe(key);
        }

        void subscribe(Publisher<? extends T>[] sources, int n) {
            CombineLatestInner<T>[] a = this.subscribers;
            for (int i = 0; i < n; ++i) {
                if (this.done || this.cancelled) {
                    return;
                }
                sources[i].subscribe(a[i]);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        void innerValue(int index, T value) {
            boolean replenishInsteadOfDrain;
            CombineLatestCoordinator combineLatestCoordinator = this;
            synchronized (combineLatestCoordinator) {
                Object[] os = this.latest;
                int localNonEmptySources = this.nonEmptySources;
                if (os[index] == null) {
                    this.nonEmptySources = ++localNonEmptySources;
                }
                os[index] = value;
                if (os.length == localNonEmptySources) {
                    SourceAndArray sa = new SourceAndArray(this.subscribers[index], (Object[])os.clone());
                    if (!this.queue.offer(sa)) {
                        this.innerError(Operators.onOperatorError(this, Exceptions.failWithOverflow("Queue is full: Reactive Streams source doesn't respect backpressure"), this.actual.currentContext()));
                        return;
                    }
                    replenishInsteadOfDrain = false;
                } else {
                    replenishInsteadOfDrain = true;
                }
            }
            if (replenishInsteadOfDrain) {
                this.subscribers[index].requestOne();
            } else {
                this.drain();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        void innerComplete(int index) {
            CombineLatestCoordinator combineLatestCoordinator = this;
            synchronized (combineLatestCoordinator) {
                Object[] os = this.latest;
                if (os[index] != null) {
                    int localCompletedSources = this.completedSources + 1;
                    if (localCompletedSources != os.length) {
                        this.completedSources = localCompletedSources;
                        return;
                    }
                    this.done = true;
                } else {
                    this.done = true;
                }
            }
            this.drain();
        }

        void innerError(Throwable e) {
            if (Exceptions.addThrowable(ERROR, this, e)) {
                this.done = true;
                this.drain();
            } else {
                this.discardQueue(this.queue);
                Operators.onErrorDropped(e, this.actual.currentContext());
            }
        }

        void drainOutput() {
            CoreSubscriber<? super R> a = this.actual;
            Queue<SourceAndArray> q = this.queue;
            int missed = 1;
            do {
                if (this.cancelled) {
                    this.discardQueue(q);
                    return;
                }
                Throwable ex = this.error;
                if (ex != null) {
                    this.discardQueue(q);
                    a.onError(ex);
                    return;
                }
                boolean d = this.done;
                boolean empty = q.isEmpty();
                if (!empty) {
                    a.onNext(null);
                }
                if (!d || !empty) continue;
                a.onComplete();
                return;
            } while ((missed = WIP.addAndGet(this, -missed)) != 0);
        }

        void drainAsync() {
            Queue<SourceAndArray> q = this.queue;
            int missed = 1;
            do {
                long e;
                long r = this.requested;
                for (e = 0L; e != r; ++e) {
                    R w;
                    boolean empty;
                    boolean d = this.done;
                    SourceAndArray v = q.poll();
                    boolean bl = empty = v == null;
                    if (this.checkTerminated(d, empty, q)) {
                        return;
                    }
                    if (empty) break;
                    try {
                        w = Objects.requireNonNull(this.combiner.apply(v.array), "Combiner returned null");
                    }
                    catch (Throwable ex) {
                        Context ctx = this.actual.currentContext();
                        Operators.onDiscardMultiple(Stream.of(v.array), ctx);
                        ex = Operators.onOperatorError(this, ex, v.array, ctx);
                        Exceptions.addThrowable(ERROR, this, ex);
                        ex = Exceptions.terminate(ERROR, this);
                        this.actual.onError(ex);
                        return;
                    }
                    this.actual.onNext(w);
                    v.source.requestOne();
                }
                if (e == r && this.checkTerminated(this.done, q.isEmpty(), q)) {
                    return;
                }
                if (e == 0L || r == Long.MAX_VALUE) continue;
                REQUESTED.addAndGet(this, -e);
            } while ((missed = WIP.addAndGet(this, -missed)) != 0);
        }

        void drain() {
            if (WIP.getAndIncrement(this) != 0) {
                return;
            }
            if (this.outputFused) {
                this.drainOutput();
            } else {
                this.drainAsync();
            }
        }

        boolean checkTerminated(boolean d, boolean empty, Queue<SourceAndArray> q) {
            if (this.cancelled) {
                this.cancelAll();
                this.discardQueue(q);
                return true;
            }
            if (d) {
                Throwable e = Exceptions.terminate(ERROR, this);
                if (e != null && e != Exceptions.TERMINATED) {
                    this.cancelAll();
                    this.discardQueue(q);
                    this.actual.onError(e);
                    return true;
                }
                if (empty) {
                    this.cancelAll();
                    this.actual.onComplete();
                    return true;
                }
            }
            return false;
        }

        void cancelAll() {
            for (CombineLatestInner<T> inner : this.subscribers) {
                inner.cancel();
            }
        }

        @Override
        public int requestFusion(int requestedMode) {
            if ((requestedMode & 4) != 0) {
                return 0;
            }
            int m = requestedMode & 2;
            this.outputFused = m != 0;
            return m;
        }

        @Override
        @Nullable
        public R poll() {
            SourceAndArray e = this.queue.poll();
            if (e == null) {
                return null;
            }
            R r = this.combiner.apply(e.array);
            e.source.requestOne();
            return r;
        }

        private void discardQueue(Queue<SourceAndArray> q) {
            Operators.onDiscardQueueWithClear(q, this.actual.currentContext(), SourceAndArray::toStream);
        }

        @Override
        public void clear() {
            this.discardQueue(this.queue);
        }

        @Override
        public boolean isEmpty() {
            return this.queue.isEmpty();
        }

        @Override
        public int size() {
            return this.queue.size();
        }
    }
}

