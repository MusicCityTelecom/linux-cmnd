/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Publisher
 *  org.reactivestreams.Subscriber
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.Function;
import java.util.function.Supplier;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Disposable;
import reactor.core.Disposables;
import reactor.core.Exceptions;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.InternalFluxOperator;
import reactor.core.publisher.OperatorDisposables;
import reactor.core.publisher.Operators;
import reactor.core.publisher.QueueDrainSubscriber;
import reactor.core.publisher.Sinks;
import reactor.util.annotation.Nullable;
import reactor.util.concurrent.Queues;

final class FluxWindowWhen<T, U, V>
extends InternalFluxOperator<T, Flux<T>> {
    final Publisher<U> start;
    final Function<? super U, ? extends Publisher<V>> end;
    final Supplier<? extends Queue<T>> processorQueueSupplier;

    FluxWindowWhen(Flux<? extends T> source, Publisher<U> start, Function<? super U, ? extends Publisher<V>> end, Supplier<? extends Queue<T>> processorQueueSupplier) {
        super(source);
        this.start = Objects.requireNonNull(start, "start");
        this.end = Objects.requireNonNull(end, "end");
        this.processorQueueSupplier = Objects.requireNonNull(processorQueueSupplier, "processorQueueSupplier");
    }

    @Override
    public int getPrefetch() {
        return Integer.MAX_VALUE;
    }

    @Override
    @Nullable
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super Flux<T>> actual) {
        WindowWhenMainSubscriber main = new WindowWhenMainSubscriber(actual, this.start, this.end, this.processorQueueSupplier);
        actual.onSubscribe(main);
        if (main.cancelled) {
            return null;
        }
        WindowWhenOpenSubscriber os = new WindowWhenOpenSubscriber(main);
        if (WindowWhenMainSubscriber.BOUNDARY.compareAndSet(main, null, os)) {
            WindowWhenMainSubscriber.OPEN_WINDOW_COUNT.incrementAndGet(main);
            this.start.subscribe(os);
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

    static final class WindowWhenCloseSubscriber<T, V>
    implements Disposable,
    Subscriber<V> {
        volatile Subscription subscription;
        static final AtomicReferenceFieldUpdater<WindowWhenCloseSubscriber, Subscription> SUBSCRIPTION = AtomicReferenceFieldUpdater.newUpdater(WindowWhenCloseSubscriber.class, Subscription.class, "subscription");
        final WindowWhenMainSubscriber<T, ?, V> parent;
        final Sinks.Many<T> w;
        boolean done;

        WindowWhenCloseSubscriber(WindowWhenMainSubscriber<T, ?, V> parent, Sinks.Many<T> w) {
            this.parent = parent;
            this.w = w;
        }

        public void onSubscribe(Subscription s) {
            if (Operators.setOnce(SUBSCRIPTION, this, s)) {
                this.subscription.request(Long.MAX_VALUE);
            }
        }

        @Override
        public void dispose() {
            Operators.terminate(SUBSCRIPTION, this);
        }

        @Override
        public boolean isDisposed() {
            return this.subscription == Operators.cancelledSubscription();
        }

        public void onNext(V t) {
            if (this.done) {
                return;
            }
            this.done = true;
            this.dispose();
            this.parent.close(this);
        }

        public void onError(Throwable t) {
            if (this.done) {
                Operators.onErrorDropped(t, this.parent.actual.currentContext());
                return;
            }
            this.done = true;
            this.parent.error(t);
        }

        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            this.parent.close(this);
        }
    }

    static final class WindowWhenOpenSubscriber<T, U>
    implements Disposable,
    Subscriber<U> {
        volatile Subscription subscription;
        static final AtomicReferenceFieldUpdater<WindowWhenOpenSubscriber, Subscription> SUBSCRIPTION = AtomicReferenceFieldUpdater.newUpdater(WindowWhenOpenSubscriber.class, Subscription.class, "subscription");
        final WindowWhenMainSubscriber<T, U, ?> parent;
        boolean done;

        WindowWhenOpenSubscriber(WindowWhenMainSubscriber<T, U, ?> parent) {
            this.parent = parent;
        }

        public void onSubscribe(Subscription s) {
            if (Operators.setOnce(SUBSCRIPTION, this, s)) {
                this.subscription.request(Long.MAX_VALUE);
            }
        }

        @Override
        public void dispose() {
            Operators.terminate(SUBSCRIPTION, this);
        }

        @Override
        public boolean isDisposed() {
            return this.subscription == Operators.cancelledSubscription();
        }

        public void onNext(U t) {
            if (this.done) {
                return;
            }
            this.parent.open(t);
        }

        public void onError(Throwable t) {
            if (this.done) {
                Operators.onErrorDropped(t, this.parent.actual.currentContext());
                return;
            }
            this.done = true;
            this.parent.error(t);
        }

        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            this.parent.onComplete();
        }
    }

    static final class WindowOperation<T, U> {
        final Sinks.Many<T> w;
        final U open;

        WindowOperation(@Nullable Sinks.Many<T> w, @Nullable U open) {
            this.w = w;
            this.open = open;
        }
    }

    static final class WindowWhenMainSubscriber<T, U, V>
    extends QueueDrainSubscriber<T, Object, Flux<T>> {
        final Publisher<U> open;
        final Function<? super U, ? extends Publisher<V>> close;
        final Supplier<? extends Queue<T>> processorQueueSupplier;
        final Disposable.Composite resources;
        Subscription s;
        volatile Disposable boundary;
        static final AtomicReferenceFieldUpdater<WindowWhenMainSubscriber, Disposable> BOUNDARY = AtomicReferenceFieldUpdater.newUpdater(WindowWhenMainSubscriber.class, Disposable.class, "boundary");
        final List<Sinks.Many<T>> windows;
        volatile long openWindowCount;
        static final AtomicLongFieldUpdater<WindowWhenMainSubscriber> OPEN_WINDOW_COUNT = AtomicLongFieldUpdater.newUpdater(WindowWhenMainSubscriber.class, "openWindowCount");

        WindowWhenMainSubscriber(CoreSubscriber<? super Flux<T>> actual, Publisher<U> open, Function<? super U, ? extends Publisher<V>> close, Supplier<? extends Queue<T>> processorQueueSupplier) {
            super(actual, Queues.unboundedMultiproducer().get());
            this.open = open;
            this.close = close;
            this.processorQueueSupplier = processorQueueSupplier;
            this.resources = Disposables.composite();
            this.windows = new ArrayList<Sinks.Many<T>>();
            OPEN_WINDOW_COUNT.lazySet(this, 1L);
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (Operators.validate(this.s, s)) {
                this.s = s;
                s.request(Long.MAX_VALUE);
            }
        }

        public void onNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, this.actual.currentContext());
                return;
            }
            if (this.fastEnter()) {
                for (Sinks.Many<T> w : this.windows) {
                    w.emitNext(t, Sinks.EmitFailureHandler.FAIL_FAST);
                }
                if (this.leave(-1) == 0) {
                    return;
                }
            } else {
                this.queue.offer(t);
                if (!this.enter()) {
                    return;
                }
            }
            this.drainLoop();
        }

        public void onError(Throwable t) {
            if (this.done) {
                Operators.onErrorDropped(t, this.actual.currentContext());
                return;
            }
            this.error = t;
            this.done = true;
            if (this.enter()) {
                this.drainLoop();
            }
            if (OPEN_WINDOW_COUNT.decrementAndGet(this) == 0L) {
                this.resources.dispose();
            }
        }

        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            if (this.enter()) {
                this.drainLoop();
            }
            if (OPEN_WINDOW_COUNT.decrementAndGet(this) == 0L) {
                this.resources.dispose();
            }
        }

        void error(Throwable t) {
            this.s.cancel();
            this.resources.dispose();
            OperatorDisposables.dispose(BOUNDARY, this);
            this.actual.onError(t);
        }

        public void request(long n) {
            this.requested(n);
        }

        public void cancel() {
            this.cancelled = true;
        }

        void dispose() {
            this.resources.dispose();
            OperatorDisposables.dispose(BOUNDARY, this);
        }

        void drainLoop() {
            Queue q = this.queue;
            CoreSubscriber a = this.actual;
            List<Sinks.Many<T>> ws = this.windows;
            int missed = 1;
            block2: while (true) {
                boolean empty;
                boolean d = this.done;
                Object o = q.poll();
                boolean bl = empty = o == null;
                if (d && empty) {
                    this.dispose();
                    Throwable e = this.error;
                    if (e != null) {
                        this.actual.onError(e);
                        for (Sinks.Many<T> w : ws) {
                            w.emitError(e, Sinks.EmitFailureHandler.FAIL_FAST);
                        }
                    } else {
                        this.actual.onComplete();
                        for (Sinks.Many<T> w : ws) {
                            w.emitComplete(Sinks.EmitFailureHandler.FAIL_FAST);
                        }
                    }
                    ws.clear();
                    return;
                }
                if (!empty) {
                    Sinks.Many w;
                    if (o instanceof WindowOperation) {
                        Publisher<V> p;
                        WindowOperation wo = (WindowOperation)o;
                        w = wo.w;
                        if (w != null) {
                            if (!ws.remove(wo.w)) continue;
                            wo.w.emitComplete(Sinks.EmitFailureHandler.FAIL_FAST);
                            if (OPEN_WINDOW_COUNT.decrementAndGet(this) != 0L) continue;
                            this.dispose();
                            return;
                        }
                        if (this.cancelled) continue;
                        w = Sinks.unsafe().many().unicast().onBackpressureBuffer(this.processorQueueSupplier.get());
                        long r = this.requested();
                        if (r != 0L) {
                            ws.add(w);
                            a.onNext(w.asFlux());
                            if (r != Long.MAX_VALUE) {
                                this.produced(1L);
                            }
                        } else {
                            this.cancelled = true;
                            a.onError(Exceptions.failWithOverflow("Could not deliver new window due to lack of requests"));
                            continue;
                        }
                        try {
                            p = Objects.requireNonNull(this.close.apply(wo.open), "The publisher supplied is null");
                        }
                        catch (Throwable e) {
                            this.cancelled = true;
                            a.onError(e);
                            continue;
                        }
                        WindowWhenCloseSubscriber cl = new WindowWhenCloseSubscriber(this, w);
                        if (!this.resources.add(cl)) continue;
                        OPEN_WINDOW_COUNT.getAndIncrement(this);
                        p.subscribe(cl);
                        continue;
                    }
                    Iterator<Sinks.Many<T>> iterator = ws.iterator();
                    while (true) {
                        if (!iterator.hasNext()) continue block2;
                        w = iterator.next();
                        Object t = o;
                        w.emitNext(t, Sinks.EmitFailureHandler.FAIL_FAST);
                    }
                }
                if ((missed = this.leave(-missed)) == 0) break;
            }
        }

        void open(U b) {
            this.queue.offer(new WindowOperation(null, b));
            if (this.enter()) {
                this.drainLoop();
            }
        }

        void close(WindowWhenCloseSubscriber<T, V> w) {
            this.resources.remove(w);
            this.queue.offer(new WindowOperation(w.w, null));
            if (this.enter()) {
                this.drainLoop();
            }
        }

        @Override
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return super.scanUnsafe(key);
        }
    }
}

