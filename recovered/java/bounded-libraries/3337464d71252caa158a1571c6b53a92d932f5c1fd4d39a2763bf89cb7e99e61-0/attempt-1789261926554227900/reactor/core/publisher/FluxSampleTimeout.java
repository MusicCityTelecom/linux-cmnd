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
import reactor.core.publisher.InnerConsumer;
import reactor.core.publisher.InnerOperator;
import reactor.core.publisher.InternalFluxOperator;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

final class FluxSampleTimeout<T, U>
extends InternalFluxOperator<T, T> {
    final Function<? super T, ? extends Publisher<U>> throttler;
    final Supplier<Queue<Object>> queueSupplier;

    FluxSampleTimeout(Flux<? extends T> source, Function<? super T, ? extends Publisher<U>> throttler, Supplier<Queue<Object>> queueSupplier) {
        super(source);
        this.throttler = Objects.requireNonNull(throttler, "throttler");
        this.queueSupplier = Objects.requireNonNull(queueSupplier, "queueSupplier");
    }

    @Override
    public int getPrefetch() {
        return Integer.MAX_VALUE;
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> actual) {
        Queue<Object> q = this.queueSupplier.get();
        SampleTimeoutMain main = new SampleTimeoutMain(actual, this.throttler, q);
        actual.onSubscribe(main);
        return main;
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static final class SampleTimeoutOther<T, U>
    extends Operators.DeferredSubscription
    implements InnerConsumer<U> {
        final SampleTimeoutMain<T, U> main;
        final T value;
        final long index;
        volatile int once;
        static final AtomicIntegerFieldUpdater<SampleTimeoutOther> ONCE = AtomicIntegerFieldUpdater.newUpdater(SampleTimeoutOther.class, "once");

        SampleTimeoutOther(SampleTimeoutMain<T, U> main, T value, long index) {
            this.main = main;
            this.value = value;
            this.index = index;
        }

        @Override
        public Context currentContext() {
            return this.main.currentContext();
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.TERMINATED) {
                return this.once == 1;
            }
            if (key == Scannable.Attr.ACTUAL) {
                return this.main;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return super.scanUnsafe(key);
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (this.set(s)) {
                s.request(Long.MAX_VALUE);
            }
        }

        public void onNext(U t) {
            if (ONCE.compareAndSet(this, 0, 1)) {
                this.cancel();
                this.main.otherNext(this);
            }
        }

        public void onError(Throwable t) {
            if (ONCE.compareAndSet(this, 0, 1)) {
                this.main.otherError(this.index, t);
            } else {
                Operators.onErrorDropped(t, this.main.currentContext());
            }
        }

        public void onComplete() {
            if (ONCE.compareAndSet(this, 0, 1)) {
                this.main.otherNext(this);
            }
        }

        final Stream<T> toStream() {
            return Stream.of(this.value);
        }
    }

    static final class SampleTimeoutMain<T, U>
    implements InnerOperator<T, T> {
        final Function<? super T, ? extends Publisher<U>> throttler;
        final Queue<SampleTimeoutOther<T, U>> queue;
        final CoreSubscriber<? super T> actual;
        final Context ctx;
        volatile Subscription s;
        static final AtomicReferenceFieldUpdater<SampleTimeoutMain, Subscription> S = AtomicReferenceFieldUpdater.newUpdater(SampleTimeoutMain.class, Subscription.class, "s");
        volatile Subscription other;
        static final AtomicReferenceFieldUpdater<SampleTimeoutMain, Subscription> OTHER = AtomicReferenceFieldUpdater.newUpdater(SampleTimeoutMain.class, Subscription.class, "other");
        volatile long requested;
        static final AtomicLongFieldUpdater<SampleTimeoutMain> REQUESTED = AtomicLongFieldUpdater.newUpdater(SampleTimeoutMain.class, "requested");
        volatile int wip;
        static final AtomicIntegerFieldUpdater<SampleTimeoutMain> WIP = AtomicIntegerFieldUpdater.newUpdater(SampleTimeoutMain.class, "wip");
        volatile Throwable error;
        static final AtomicReferenceFieldUpdater<SampleTimeoutMain, Throwable> ERROR = AtomicReferenceFieldUpdater.newUpdater(SampleTimeoutMain.class, Throwable.class, "error");
        volatile boolean done;
        volatile boolean cancelled;
        volatile long index;
        static final AtomicLongFieldUpdater<SampleTimeoutMain> INDEX = AtomicLongFieldUpdater.newUpdater(SampleTimeoutMain.class, "index");

        SampleTimeoutMain(CoreSubscriber<? super T> actual, Function<? super T, ? extends Publisher<U>> throttler, Queue<SampleTimeoutOther<T, U>> queue) {
            this.actual = actual;
            this.ctx = actual.currentContext();
            this.throttler = throttler;
            this.queue = queue;
        }

        @Override
        public Stream<? extends Scannable> inners() {
            return Stream.of(Scannable.from(this.other));
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
            if (key == Scannable.Attr.PARENT) {
                return this.s;
            }
            if (key == Scannable.Attr.ERROR) {
                return this.error;
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
        public final CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        public void request(long n) {
            if (Operators.validate(n)) {
                Operators.addCap(REQUESTED, this, n);
            }
        }

        public void cancel() {
            if (!this.cancelled) {
                this.cancelled = true;
                Operators.terminate(S, this);
                Operators.terminate(OTHER, this);
                Operators.onDiscardQueueWithClear(this.queue, this.ctx, SampleTimeoutOther::toStream);
            }
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (Operators.setOnce(S, this, s)) {
                s.request(Long.MAX_VALUE);
            }
        }

        public void onNext(T t) {
            Publisher<U> p;
            long idx = INDEX.incrementAndGet(this);
            if (!Operators.set(OTHER, this, Operators.emptySubscription())) {
                return;
            }
            try {
                p = Objects.requireNonNull(this.throttler.apply(t), "throttler returned a null publisher");
            }
            catch (Throwable e) {
                this.onError(Operators.onOperatorError(this.s, e, t, this.ctx));
                return;
            }
            SampleTimeoutOther os = new SampleTimeoutOther(this, t, idx);
            if (Operators.replace(OTHER, this, os)) {
                p.subscribe(os);
            }
        }

        void error(Throwable t) {
            if (Exceptions.addThrowable(ERROR, this, t)) {
                this.done = true;
                this.drain();
            } else {
                Operators.onErrorDropped(t, this.ctx);
            }
        }

        public void onError(Throwable t) {
            Operators.terminate(OTHER, this);
            this.error(t);
        }

        public void onComplete() {
            Subscription o = this.other;
            if (o instanceof SampleTimeoutOther) {
                SampleTimeoutOther os = (SampleTimeoutOther)o;
                os.cancel();
                os.onComplete();
            }
            this.done = true;
            this.drain();
        }

        void otherNext(SampleTimeoutOther<T, U> other) {
            this.queue.offer(other);
            this.drain();
        }

        void otherError(long idx, Throwable e) {
            if (idx == this.index) {
                Operators.terminate(S, this);
                this.error(e);
            } else {
                Operators.onErrorDropped(e, this.ctx);
            }
        }

        void drain() {
            if (WIP.getAndIncrement(this) != 0) {
                return;
            }
            CoreSubscriber<? super T> a = this.actual;
            Queue<SampleTimeoutOther<T, U>> q = this.queue;
            int missed = 1;
            while (true) {
                boolean empty;
                boolean d = this.done;
                SampleTimeoutOther<T, U> o = q.poll();
                boolean bl = empty = o == null;
                if (this.checkTerminated(d, empty, a, q)) {
                    return;
                }
                if (!empty) {
                    if (o.index != this.index) continue;
                    long r = this.requested;
                    if (r != 0L) {
                        a.onNext(o.value);
                        if (r == Long.MAX_VALUE) continue;
                        REQUESTED.decrementAndGet(this);
                        continue;
                    }
                    this.cancel();
                    Operators.onDiscardQueueWithClear(q, this.ctx, SampleTimeoutOther::toStream);
                    Throwable e = Exceptions.failWithOverflow("Could not emit value due to lack of requests");
                    Exceptions.addThrowable(ERROR, this, e);
                    e = Exceptions.terminate(ERROR, this);
                    a.onError(e);
                    return;
                }
                if ((missed = WIP.addAndGet(this, -missed)) == 0) break;
            }
        }

        boolean checkTerminated(boolean d, boolean empty, Subscriber<?> a, Queue<SampleTimeoutOther<T, U>> q) {
            if (this.cancelled) {
                Operators.onDiscardQueueWithClear(q, this.ctx, SampleTimeoutOther::toStream);
                return true;
            }
            if (d) {
                Throwable e = Exceptions.terminate(ERROR, this);
                if (e != null && e != Exceptions.TERMINATED) {
                    this.cancel();
                    Operators.onDiscardQueueWithClear(q, this.ctx, SampleTimeoutOther::toStream);
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
    }
}

