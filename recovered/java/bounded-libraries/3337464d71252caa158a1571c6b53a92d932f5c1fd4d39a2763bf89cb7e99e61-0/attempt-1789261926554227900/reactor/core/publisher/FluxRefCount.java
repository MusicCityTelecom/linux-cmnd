/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import java.util.Objects;
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
import reactor.util.annotation.Nullable;

final class FluxRefCount<T>
extends Flux<T>
implements Scannable,
Fuseable {
    final ConnectableFlux<? extends T> source;
    final int n;
    @Nullable
    RefCountMonitor<T> connection;

    FluxRefCount(ConnectableFlux<? extends T> source, int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n > 0 required but it was " + n);
        }
        this.source = Objects.requireNonNull(source, "source");
        this.n = n;
    }

    @Override
    public int getPrefetch() {
        return this.source.getPrefetch();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void subscribe(CoreSubscriber<? super T> actual) {
        RefCountMonitor<T> conn;
        boolean connect = false;
        FluxRefCount fluxRefCount = this;
        synchronized (fluxRefCount) {
            conn = this.connection;
            if (conn == null || conn.terminated) {
                this.connection = conn = new RefCountMonitor(this);
            }
            long c = conn.subscribers;
            conn.subscribers = c + 1L;
            if (!conn.connected && c + 1L == (long)this.n) {
                connect = true;
                conn.connected = true;
            }
        }
        this.source.subscribe((CoreSubscriber<? super T>)new RefCountInner<T>(actual, conn));
        if (connect) {
            this.source.connect(conn);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void cancel(RefCountMonitor rc) {
        Disposable dispose = null;
        FluxRefCount fluxRefCount = this;
        synchronized (fluxRefCount) {
            long c;
            if (rc.terminated) {
                return;
            }
            rc.subscribers = c = rc.subscribers - 1L;
            if (c != 0L || !rc.connected) {
                return;
            }
            if (rc == this.connection) {
                dispose = RefCountMonitor.DISCONNECT.getAndSet(rc, Disposables.disposed());
                this.connection = null;
            }
        }
        if (dispose != null) {
            dispose.dispose();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void terminated(RefCountMonitor rc) {
        FluxRefCount fluxRefCount = this;
        synchronized (fluxRefCount) {
            if (!rc.terminated) {
                rc.terminated = true;
                this.connection = null;
            }
        }
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

    static final class RefCountInner<T>
    implements Fuseable.QueueSubscription<T>,
    InnerOperator<T, T> {
        final CoreSubscriber<? super T> actual;
        final RefCountMonitor<T> connection;
        Subscription s;
        Fuseable.QueueSubscription<T> qs;
        volatile int parentDone;
        static final AtomicIntegerFieldUpdater<RefCountInner> PARENT_DONE = AtomicIntegerFieldUpdater.newUpdater(RefCountInner.class, "parentDone");

        RefCountInner(CoreSubscriber<? super T> actual, RefCountMonitor<T> connection) {
            this.actual = actual;
            this.connection = connection;
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.PARENT) {
                return this.s;
            }
            if (key == Scannable.Attr.TERMINATED) {
                return this.parentDone == 1;
            }
            if (key == Scannable.Attr.CANCELLED) {
                return this.parentDone == 2;
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
            }
        }

        public void onNext(T t) {
            this.actual.onNext(t);
        }

        public void onError(Throwable t) {
            if (PARENT_DONE.compareAndSet(this, 0, 1)) {
                this.connection.upstreamFinished();
                this.actual.onError(t);
            } else {
                Operators.onErrorDropped(t, this.actual.currentContext());
            }
        }

        public void onComplete() {
            if (PARENT_DONE.compareAndSet(this, 0, 1)) {
                this.connection.upstreamFinished();
                this.actual.onComplete();
            }
        }

        public void request(long n) {
            this.s.request(n);
        }

        public void cancel() {
            this.s.cancel();
            if (PARENT_DONE.compareAndSet(this, 0, 2)) {
                this.connection.innerCancelled();
            }
        }

        @Override
        public CoreSubscriber<? super T> actual() {
            return this.actual;
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
    }

    static final class RefCountMonitor<T>
    implements Consumer<Disposable> {
        final FluxRefCount<? extends T> parent;
        long subscribers;
        boolean terminated;
        boolean connected;
        volatile Disposable disconnect;
        static final AtomicReferenceFieldUpdater<RefCountMonitor, Disposable> DISCONNECT = AtomicReferenceFieldUpdater.newUpdater(RefCountMonitor.class, Disposable.class, "disconnect");

        RefCountMonitor(FluxRefCount<? extends T> parent) {
            this.parent = parent;
        }

        @Override
        public void accept(Disposable r) {
            OperatorDisposables.replace(DISCONNECT, this, r);
        }

        void innerCancelled() {
            this.parent.cancel(this);
        }

        void upstreamFinished() {
            this.parent.terminated(this);
        }
    }
}

