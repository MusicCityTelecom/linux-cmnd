/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.Consumer;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Disposable;
import reactor.core.Disposables;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.InnerOperator;
import reactor.core.publisher.OperatorDisposables;
import reactor.core.publisher.Operators;
import reactor.core.scheduler.Scheduler;
import reactor.util.annotation.Nullable;

final class FluxRefCountGrace<T>
extends Flux<T>
implements Scannable,
Fuseable {
    final ConnectableFlux<T> source;
    final int n;
    final Duration gracePeriod;
    final Scheduler scheduler;
    RefConnection connection;

    FluxRefCountGrace(ConnectableFlux<T> source, int n, Duration gracePeriod, Scheduler scheduler) {
        this.source = source;
        this.n = n;
        this.gracePeriod = gracePeriod;
        this.scheduler = scheduler;
    }

    @Override
    public int getPrefetch() {
        return this.source.getPrefetch();
    }

    @Override
    @Nullable
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.PREFETCH) {
            return this.getPrefetch();
        }
        if (key == Scannable.Attr.PARENT) {
            return this.source;
        }
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void subscribe(CoreSubscriber<? super T> actual) {
        RefConnection conn;
        boolean connect = false;
        FluxRefCountGrace fluxRefCountGrace = this;
        synchronized (fluxRefCountGrace) {
            long c;
            conn = this.connection;
            if (conn == null || conn.isTerminated()) {
                this.connection = conn = new RefConnection(this);
            }
            if ((c = conn.subscriberCount) == 0L && conn.timer != null) {
                conn.timer.dispose();
            }
            conn.subscriberCount = c + 1L;
            if (!conn.connected && c + 1L == (long)this.n) {
                connect = true;
                conn.connected = true;
            }
        }
        this.source.subscribe((CoreSubscriber<? super T>)new RefCountInner<T>(actual, this, conn));
        if (connect) {
            this.source.connect(conn);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void cancel(RefConnection rc) {
        boolean replaceTimer = false;
        Disposable dispose = null;
        Disposable.Swap sd = null;
        FluxRefCountGrace fluxRefCountGrace = this;
        synchronized (fluxRefCountGrace) {
            long c;
            if (rc.terminated) {
                return;
            }
            rc.subscriberCount = c = rc.subscriberCount - 1L;
            if (c != 0L || !rc.connected) {
                return;
            }
            if (!this.gracePeriod.isZero()) {
                sd = Disposables.swap();
                rc.timer = sd;
                replaceTimer = true;
            } else if (rc == this.connection) {
                this.connection = null;
                dispose = RefConnection.SOURCE_DISCONNECTOR.getAndSet(rc, Disposables.disposed());
            }
        }
        if (replaceTimer) {
            sd.replace(this.scheduler.schedule(rc, this.gracePeriod.toNanos(), TimeUnit.NANOSECONDS));
        } else if (dispose != null) {
            dispose.dispose();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void terminated(RefConnection rc) {
        FluxRefCountGrace fluxRefCountGrace = this;
        synchronized (fluxRefCountGrace) {
            if (!rc.terminated) {
                rc.terminated = true;
                this.connection = null;
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void timeout(RefConnection rc) {
        Disposable dispose = null;
        FluxRefCountGrace fluxRefCountGrace = this;
        synchronized (fluxRefCountGrace) {
            if (rc.subscriberCount == 0L && rc == this.connection) {
                this.connection = null;
                dispose = RefConnection.SOURCE_DISCONNECTOR.getAndSet(rc, Disposables.disposed());
            }
        }
        if (dispose != null) {
            dispose.dispose();
        }
    }

    static final class RefCountInner<T>
    implements Fuseable.QueueSubscription<T>,
    InnerOperator<T, T> {
        final CoreSubscriber<? super T> actual;
        final FluxRefCountGrace<T> parent;
        final RefConnection connection;
        Subscription s;
        Fuseable.QueueSubscription<T> qs;
        volatile int parentDone;
        static final AtomicIntegerFieldUpdater<RefCountInner> PARENT_DONE = AtomicIntegerFieldUpdater.newUpdater(RefCountInner.class, "parentDone");

        RefCountInner(CoreSubscriber<? super T> actual, FluxRefCountGrace<T> parent, RefConnection connection) {
            this.actual = actual;
            this.parent = parent;
            this.connection = connection;
        }

        public void onNext(T t) {
            this.actual.onNext(t);
        }

        public void onError(Throwable t) {
            if (PARENT_DONE.compareAndSet(this, 0, 1)) {
                this.parent.terminated(this.connection);
            }
            this.actual.onError(t);
        }

        public void onComplete() {
            if (PARENT_DONE.compareAndSet(this, 0, 1)) {
                this.parent.terminated(this.connection);
            }
            this.actual.onComplete();
        }

        public void request(long n) {
            this.s.request(n);
        }

        public void cancel() {
            this.s.cancel();
            if (PARENT_DONE.compareAndSet(this, 0, 1)) {
                this.parent.cancel(this.connection);
            }
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (Operators.validate(this.s, s)) {
                this.s = s;
                this.actual.onSubscribe(this);
            }
        }

        @Override
        public int requestFusion(int requestedMode) {
            if (this.s instanceof Fuseable.QueueSubscription) {
                this.qs = (Fuseable.QueueSubscription)this.s;
                return this.qs.requestFusion(requestedMode);
            }
            return 0;
        }

        @Override
        @Nullable
        public T poll() {
            return (T)this.qs.poll();
        }

        @Override
        public int size() {
            return this.qs.size();
        }

        @Override
        public boolean isEmpty() {
            return this.qs.isEmpty();
        }

        @Override
        public void clear() {
            this.qs.clear();
        }

        @Override
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return InnerOperator.super.scanUnsafe(key);
        }
    }

    static final class RefConnection
    implements Runnable,
    Consumer<Disposable> {
        final FluxRefCountGrace<?> parent;
        Disposable timer;
        long subscriberCount;
        boolean connected;
        boolean terminated;
        volatile Disposable sourceDisconnector;
        static final AtomicReferenceFieldUpdater<RefConnection, Disposable> SOURCE_DISCONNECTOR = AtomicReferenceFieldUpdater.newUpdater(RefConnection.class, Disposable.class, "sourceDisconnector");

        RefConnection(FluxRefCountGrace<?> parent) {
            this.parent = parent;
        }

        boolean isTerminated() {
            Disposable sd = this.sourceDisconnector;
            return this.terminated || sd != null && sd.isDisposed();
        }

        @Override
        public void run() {
            this.parent.timeout(this);
        }

        @Override
        public void accept(Disposable t) {
            OperatorDisposables.replace(SOURCE_DISCONNECTOR, this, t);
        }
    }
}

