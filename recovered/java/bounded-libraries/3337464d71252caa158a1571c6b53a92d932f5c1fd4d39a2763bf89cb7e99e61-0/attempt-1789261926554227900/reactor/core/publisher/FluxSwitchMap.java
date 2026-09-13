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
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxFlatMap;
import reactor.core.publisher.InnerConsumer;
import reactor.core.publisher.InnerOperator;
import reactor.core.publisher.InternalFluxOperator;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

final class FluxSwitchMap<T, R>
extends InternalFluxOperator<T, R> {
    final Function<? super T, ? extends Publisher<? extends R>> mapper;
    final Supplier<? extends Queue<Object>> queueSupplier;
    final int prefetch;
    static final SwitchMapInner<Object> CANCELLED_INNER = new SwitchMapInner(null, 0, Long.MAX_VALUE);

    FluxSwitchMap(Flux<? extends T> source, Function<? super T, ? extends Publisher<? extends R>> mapper, Supplier<? extends Queue<Object>> queueSupplier, int prefetch) {
        super(source);
        if (prefetch <= 0) {
            throw new IllegalArgumentException("prefetch > 0 required but it was " + prefetch);
        }
        this.mapper = Objects.requireNonNull(mapper, "mapper");
        this.queueSupplier = Objects.requireNonNull(queueSupplier, "queueSupplier");
        this.prefetch = prefetch;
    }

    @Override
    public int getPrefetch() {
        return Integer.MAX_VALUE;
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super R> actual) {
        if (FluxFlatMap.trySubscribeScalarMap(this.source, actual, this.mapper, false, false)) {
            return null;
        }
        return new SwitchMapMain<T, R>(actual, this.mapper, this.queueSupplier.get(), this.prefetch);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static final class SwitchMapInner<R>
    implements InnerConsumer<R>,
    Subscription {
        final SwitchMapMain<?, R> parent;
        final int prefetch;
        final int limit;
        final long index;
        volatile int once;
        static final AtomicIntegerFieldUpdater<SwitchMapInner> ONCE = AtomicIntegerFieldUpdater.newUpdater(SwitchMapInner.class, "once");
        volatile Subscription s;
        static final AtomicReferenceFieldUpdater<SwitchMapInner, Subscription> S = AtomicReferenceFieldUpdater.newUpdater(SwitchMapInner.class, Subscription.class, "s");
        int produced;

        SwitchMapInner(SwitchMapMain<?, R> parent, int prefetch, long index) {
            this.parent = parent;
            this.prefetch = prefetch;
            this.limit = Operators.unboundedOrLimit(prefetch);
            this.index = index;
        }

        @Override
        public Context currentContext() {
            return this.parent.currentContext();
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
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return null;
        }

        @Override
        public void onSubscribe(Subscription s) {
            Subscription a = this.s;
            if (a == Operators.cancelledSubscription()) {
                s.cancel();
            }
            if (a != null) {
                s.cancel();
                Operators.reportSubscriptionSet();
                return;
            }
            if (S.compareAndSet(this, null, s)) {
                s.request(Operators.unboundedOrPrefetch(this.prefetch));
                return;
            }
            a = this.s;
            if (a != Operators.cancelledSubscription()) {
                s.cancel();
                Operators.reportSubscriptionSet();
            }
        }

        public void onNext(R t) {
            this.parent.innerNext(this, t);
        }

        public void onError(Throwable t) {
            this.parent.innerError(this, t);
        }

        public void onComplete() {
            this.parent.innerComplete(this);
        }

        void deactivate() {
            if (ONCE.compareAndSet(this, 0, 1)) {
                this.parent.deactivate();
            }
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

        public void request(long n) {
            long p = (long)this.produced + n;
            if (p >= (long)this.limit) {
                this.produced = 0;
                this.s.request(p);
            } else {
                this.produced = (int)p;
            }
        }

        public void cancel() {
            Subscription a = this.s;
            if (a != Operators.cancelledSubscription() && (a = S.getAndSet(this, Operators.cancelledSubscription())) != null && a != Operators.cancelledSubscription()) {
                a.cancel();
            }
        }
    }

    static final class SwitchMapMain<T, R>
    implements InnerOperator<T, R> {
        final Function<? super T, ? extends Publisher<? extends R>> mapper;
        final Queue<Object> queue;
        final BiPredicate<Object, Object> queueBiAtomic;
        final int prefetch;
        final CoreSubscriber<? super R> actual;
        Subscription s;
        volatile boolean done;
        volatile Throwable error;
        static final AtomicReferenceFieldUpdater<SwitchMapMain, Throwable> ERROR = AtomicReferenceFieldUpdater.newUpdater(SwitchMapMain.class, Throwable.class, "error");
        volatile boolean cancelled;
        volatile int once;
        static final AtomicIntegerFieldUpdater<SwitchMapMain> ONCE = AtomicIntegerFieldUpdater.newUpdater(SwitchMapMain.class, "once");
        volatile long requested;
        static final AtomicLongFieldUpdater<SwitchMapMain> REQUESTED = AtomicLongFieldUpdater.newUpdater(SwitchMapMain.class, "requested");
        volatile int wip;
        static final AtomicIntegerFieldUpdater<SwitchMapMain> WIP = AtomicIntegerFieldUpdater.newUpdater(SwitchMapMain.class, "wip");
        volatile SwitchMapInner<R> inner;
        static final AtomicReferenceFieldUpdater<SwitchMapMain, SwitchMapInner> INNER = AtomicReferenceFieldUpdater.newUpdater(SwitchMapMain.class, SwitchMapInner.class, "inner");
        volatile long index;
        static final AtomicLongFieldUpdater<SwitchMapMain> INDEX = AtomicLongFieldUpdater.newUpdater(SwitchMapMain.class, "index");
        volatile int active;
        static final AtomicIntegerFieldUpdater<SwitchMapMain> ACTIVE = AtomicIntegerFieldUpdater.newUpdater(SwitchMapMain.class, "active");

        SwitchMapMain(CoreSubscriber<? super R> actual, Function<? super T, ? extends Publisher<? extends R>> mapper, Queue<Object> queue, int prefetch) {
            this.actual = actual;
            this.mapper = mapper;
            this.queue = queue;
            this.prefetch = prefetch;
            this.active = 1;
            this.queueBiAtomic = queue instanceof BiPredicate ? (BiPredicate)((Object)queue) : null;
        }

        @Override
        public final CoreSubscriber<? super R> actual() {
            return this.actual;
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.CANCELLED) {
                return this.cancelled;
            }
            if (key == Scannable.Attr.PARENT) {
                return this.s;
            }
            if (key == Scannable.Attr.TERMINATED) {
                return this.done;
            }
            if (key == Scannable.Attr.ERROR) {
                return this.error;
            }
            if (key == Scannable.Attr.PREFETCH) {
                return this.prefetch;
            }
            if (key == Scannable.Attr.BUFFERED) {
                return this.queue.size();
            }
            if (key == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM) {
                return this.requested;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return InnerOperator.super.scanUnsafe(key);
        }

        @Override
        public Stream<? extends Scannable> inners() {
            return Stream.of(this.inner);
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (Operators.validate(this.s, s)) {
                this.s = s;
                this.actual.onSubscribe(this);
                s.request(Long.MAX_VALUE);
            }
        }

        public void onNext(T t) {
            Publisher<? extends R> p;
            if (this.done) {
                Operators.onNextDropped(t, this.actual.currentContext());
                return;
            }
            long idx = INDEX.incrementAndGet(this);
            SwitchMapInner<R> si = this.inner;
            if (si != null) {
                si.deactivate();
                si.cancel();
            }
            try {
                p = Objects.requireNonNull(this.mapper.apply(t), "The mapper returned a null publisher");
            }
            catch (Throwable e) {
                this.onError(Operators.onOperatorError(this.s, e, t, this.actual.currentContext()));
                return;
            }
            SwitchMapInner innerSubscriber = new SwitchMapInner(this, this.prefetch, idx);
            if (INNER.compareAndSet(this, si, innerSubscriber)) {
                ACTIVE.getAndIncrement(this);
                p.subscribe(innerSubscriber);
            }
        }

        public void onError(Throwable t) {
            if (this.done) {
                Operators.onErrorDropped(t, this.actual.currentContext());
                return;
            }
            if (Exceptions.addThrowable(ERROR, this, t)) {
                if (ONCE.compareAndSet(this, 0, 1)) {
                    this.deactivate();
                }
                this.cancelInner();
                this.done = true;
                this.drain();
            } else {
                Operators.onErrorDropped(t, this.actual.currentContext());
            }
        }

        public void onComplete() {
            if (this.done) {
                return;
            }
            if (ONCE.compareAndSet(this, 0, 1)) {
                this.deactivate();
            }
            this.done = true;
            this.drain();
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
                if (WIP.getAndIncrement(this) == 0) {
                    this.cancelAndCleanup(this.queue);
                }
            }
        }

        void deactivate() {
            ACTIVE.decrementAndGet(this);
        }

        void cancelInner() {
            SwitchMapInner<Object> si = INNER.getAndSet(this, CANCELLED_INNER);
            if (si != null && si != CANCELLED_INNER) {
                si.cancel();
                si.deactivate();
            }
        }

        void cancelAndCleanup(Queue<?> q) {
            this.s.cancel();
            this.cancelInner();
            q.clear();
        }

        void drain() {
            if (WIP.getAndIncrement(this) != 0) {
                return;
            }
            CoreSubscriber<? super R> a = this.actual;
            Queue<Object> q = this.queue;
            int missed = 1;
            do {
                long r = this.requested;
                long e = 0L;
                while (r != e) {
                    Object second;
                    boolean empty;
                    boolean d = this.active == 0;
                    SwitchMapInner si = (SwitchMapInner)q.poll();
                    boolean bl = empty = si == null;
                    if (this.checkTerminated(d, empty, a, q)) {
                        return;
                    }
                    if (empty) break;
                    while ((second = q.poll()) == null) {
                    }
                    if (this.index != si.index) continue;
                    Object v = second;
                    a.onNext(v);
                    si.requestOne();
                    ++e;
                }
                if (r == e && this.checkTerminated(this.active == 0, q.isEmpty(), a, q)) {
                    return;
                }
                if (e == 0L || r == Long.MAX_VALUE) continue;
                REQUESTED.addAndGet(this, -e);
            } while ((missed = WIP.addAndGet(this, -missed)) != 0);
        }

        boolean checkTerminated(boolean d, boolean empty, Subscriber<?> a, Queue<?> q) {
            if (this.cancelled) {
                this.cancelAndCleanup(q);
                return true;
            }
            if (d) {
                Throwable e = Exceptions.terminate(ERROR, this);
                if (e != null && e != Exceptions.TERMINATED) {
                    this.cancelAndCleanup(q);
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

        void innerNext(SwitchMapInner<R> inner, R value) {
            if (this.queueBiAtomic != null) {
                this.queueBiAtomic.test(inner, value);
            } else {
                this.queue.offer(inner);
                this.queue.offer(value);
            }
            this.drain();
        }

        void innerError(SwitchMapInner<R> inner, Throwable e) {
            if (Exceptions.addThrowable(ERROR, this, e)) {
                this.s.cancel();
                if (ONCE.compareAndSet(this, 0, 1)) {
                    this.deactivate();
                }
                inner.deactivate();
                this.drain();
            } else {
                Operators.onErrorDropped(e, this.actual.currentContext());
            }
        }

        void innerComplete(SwitchMapInner<R> inner) {
            inner.deactivate();
            this.drain();
        }
    }
}

