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
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Disposable;
import reactor.core.Exceptions;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.InnerConsumer;
import reactor.core.publisher.InnerOperator;
import reactor.core.publisher.InternalFluxOperator;
import reactor.core.publisher.Operators;
import reactor.core.publisher.Sinks;
import reactor.util.annotation.Nullable;
import reactor.util.concurrent.Queues;
import reactor.util.context.Context;

final class FluxWindowBoundary<T, U>
extends InternalFluxOperator<T, Flux<T>> {
    final Publisher<U> other;
    final Supplier<? extends Queue<T>> processorQueueSupplier;

    FluxWindowBoundary(Flux<? extends T> source, Publisher<U> other, Supplier<? extends Queue<T>> processorQueueSupplier) {
        super(source);
        this.other = Objects.requireNonNull(other, "other");
        this.processorQueueSupplier = Objects.requireNonNull(processorQueueSupplier, "processorQueueSupplier");
    }

    @Override
    public int getPrefetch() {
        return Integer.MAX_VALUE;
    }

    @Override
    @Nullable
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super Flux<T>> actual) {
        WindowBoundaryMain main = new WindowBoundaryMain(actual, this.processorQueueSupplier, this.processorQueueSupplier.get());
        actual.onSubscribe(main);
        if (main.emit(main.window)) {
            this.other.subscribe(main.boundary);
            return main;
        }
        return null;
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static final class WindowBoundaryOther<U>
    extends Operators.DeferredSubscription
    implements InnerConsumer<U> {
        final WindowBoundaryMain<?, U> main;

        WindowBoundaryOther(WindowBoundaryMain<?, U> main) {
            this.main = main;
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (this.set(s)) {
                s.request(Long.MAX_VALUE);
            }
        }

        @Override
        public Context currentContext() {
            return this.main.currentContext();
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.ACTUAL) {
                return this.main;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return super.scanUnsafe(key);
        }

        public void onNext(U t) {
            this.main.boundaryNext();
        }

        public void onError(Throwable t) {
            this.main.boundaryError(t);
        }

        public void onComplete() {
            this.main.boundaryComplete();
        }
    }

    static final class WindowBoundaryMain<T, U>
    implements InnerOperator<T, Flux<T>>,
    Disposable {
        final Supplier<? extends Queue<T>> processorQueueSupplier;
        final WindowBoundaryOther<U> boundary;
        final Queue<Object> queue;
        final CoreSubscriber<? super Flux<T>> actual;
        Sinks.Many<T> window;
        volatile Subscription s;
        static final AtomicReferenceFieldUpdater<WindowBoundaryMain, Subscription> S = AtomicReferenceFieldUpdater.newUpdater(WindowBoundaryMain.class, Subscription.class, "s");
        volatile long requested;
        static final AtomicLongFieldUpdater<WindowBoundaryMain> REQUESTED = AtomicLongFieldUpdater.newUpdater(WindowBoundaryMain.class, "requested");
        volatile Throwable error;
        static final AtomicReferenceFieldUpdater<WindowBoundaryMain, Throwable> ERROR = AtomicReferenceFieldUpdater.newUpdater(WindowBoundaryMain.class, Throwable.class, "error");
        volatile int cancelled;
        static final AtomicIntegerFieldUpdater<WindowBoundaryMain> CANCELLED = AtomicIntegerFieldUpdater.newUpdater(WindowBoundaryMain.class, "cancelled");
        volatile int windowCount;
        static final AtomicIntegerFieldUpdater<WindowBoundaryMain> WINDOW_COUNT = AtomicIntegerFieldUpdater.newUpdater(WindowBoundaryMain.class, "windowCount");
        volatile int wip;
        static final AtomicIntegerFieldUpdater<WindowBoundaryMain> WIP = AtomicIntegerFieldUpdater.newUpdater(WindowBoundaryMain.class, "wip");
        boolean done;
        static final Object BOUNDARY_MARKER = new Object();
        static final Object DONE = new Object();

        WindowBoundaryMain(CoreSubscriber<? super Flux<T>> actual, Supplier<? extends Queue<T>> processorQueueSupplier, Queue<T> processorQueue) {
            this.actual = actual;
            this.processorQueueSupplier = processorQueueSupplier;
            this.window = Sinks.unsafe().many().unicast().onBackpressureBuffer(processorQueue, this);
            WINDOW_COUNT.lazySet(this, 2);
            this.boundary = new WindowBoundaryOther(this);
            this.queue = Queues.unboundedMultiproducer().get();
        }

        @Override
        public final CoreSubscriber<? super Flux<T>> actual() {
            return this.actual;
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
                return this.cancelled == 1;
            }
            if (key == Scannable.Attr.TERMINATED) {
                return this.done;
            }
            if (key == Scannable.Attr.PREFETCH) {
                return Integer.MAX_VALUE;
            }
            if (key == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM) {
                return this.requested;
            }
            if (key == Scannable.Attr.BUFFERED) {
                return this.queue.size();
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return InnerOperator.super.scanUnsafe(key);
        }

        @Override
        public Stream<? extends Scannable> inners() {
            return Stream.of(this.boundary, Scannable.from(this.window));
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (Operators.setOnce(S, this, s)) {
                s.request(Long.MAX_VALUE);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public void onNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, this.actual.currentContext());
                return;
            }
            WindowBoundaryMain windowBoundaryMain = this;
            synchronized (windowBoundaryMain) {
                this.queue.offer(t);
            }
            this.drain();
        }

        public void onError(Throwable t) {
            if (this.done) {
                Operators.onErrorDropped(t, this.actual.currentContext());
                return;
            }
            this.done = true;
            this.boundary.cancel();
            if (Exceptions.addThrowable(ERROR, this, t)) {
                this.drain();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            this.boundary.cancel();
            WindowBoundaryMain windowBoundaryMain = this;
            synchronized (windowBoundaryMain) {
                this.queue.offer(DONE);
            }
            this.drain();
        }

        @Override
        public void dispose() {
            if (WINDOW_COUNT.decrementAndGet(this) == 0) {
                this.cancelMain();
                this.boundary.cancel();
            }
        }

        @Override
        public boolean isDisposed() {
            return this.cancelled == 1 || this.done;
        }

        public void request(long n) {
            if (Operators.validate(n)) {
                Operators.addCap(REQUESTED, this, n);
            }
        }

        void cancelMain() {
            Operators.terminate(S, this);
        }

        public void cancel() {
            if (CANCELLED.compareAndSet(this, 0, 1)) {
                this.dispose();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        void boundaryNext() {
            WindowBoundaryMain windowBoundaryMain = this;
            synchronized (windowBoundaryMain) {
                this.queue.offer(BOUNDARY_MARKER);
            }
            if (this.cancelled != 0) {
                this.boundary.cancel();
            }
            this.drain();
        }

        void boundaryError(Throwable e) {
            this.cancelMain();
            if (Exceptions.addThrowable(ERROR, this, e)) {
                this.drain();
            } else {
                Operators.onErrorDropped(e, this.actual.currentContext());
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        void boundaryComplete() {
            this.cancelMain();
            WindowBoundaryMain windowBoundaryMain = this;
            synchronized (windowBoundaryMain) {
                this.queue.offer(DONE);
            }
            this.drain();
        }

        void drain() {
            if (WIP.getAndIncrement(this) != 0) {
                return;
            }
            CoreSubscriber<? super Flux<T>> a = this.actual;
            Queue<Object> q = this.queue;
            Sinks.Many<Object> w = this.window;
            int missed = 1;
            while (true) {
                if (this.error != null) {
                    q.clear();
                    Throwable e = Exceptions.terminate(ERROR, this);
                    if (e != Exceptions.TERMINATED) {
                        w.emitError(e, Sinks.EmitFailureHandler.FAIL_FAST);
                        a.onError(e);
                    }
                    return;
                }
                Object o = q.poll();
                if (o != null) {
                    if (o == DONE) {
                        q.clear();
                        w.emitComplete(Sinks.EmitFailureHandler.FAIL_FAST);
                        a.onComplete();
                        return;
                    }
                    if (o != BOUNDARY_MARKER) {
                        Object v = o;
                        w.emitNext(v, Sinks.EmitFailureHandler.FAIL_FAST);
                    }
                    if (o != BOUNDARY_MARKER) continue;
                    w.emitComplete(Sinks.EmitFailureHandler.FAIL_FAST);
                    if (this.cancelled != 0) continue;
                    if (this.requested != 0L) {
                        Queue<T> pq = this.processorQueueSupplier.get();
                        WINDOW_COUNT.getAndIncrement(this);
                        w = Sinks.unsafe().many().unicast().onBackpressureBuffer(pq, this);
                        this.window = w;
                        a.onNext(w.asFlux());
                        if (this.requested == Long.MAX_VALUE) continue;
                        REQUESTED.decrementAndGet(this);
                        continue;
                    }
                    q.clear();
                    this.cancelMain();
                    this.boundary.cancel();
                    a.onError(Exceptions.failWithOverflow("Could not create new window due to lack of requests"));
                    return;
                }
                if ((missed = WIP.addAndGet(this, -missed)) == 0) break;
            }
        }

        boolean emit(Sinks.Many<T> w) {
            long r = this.requested;
            if (r != 0L) {
                this.actual.onNext(w.asFlux());
                if (r != Long.MAX_VALUE) {
                    REQUESTED.decrementAndGet(this);
                }
                return true;
            }
            this.cancel();
            this.actual.onError(Exceptions.failWithOverflow("Could not emit buffer due to lack of requests"));
            return false;
        }
    }
}

