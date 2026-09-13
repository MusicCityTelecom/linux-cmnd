/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.InnerOperator;
import reactor.core.publisher.InternalFluxOperator;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;

final class FluxTake<T>
extends InternalFluxOperator<T, T> {
    final long n;

    FluxTake(Flux<? extends T> source, long n) {
        super(source);
        if (n < 0L) {
            throw new IllegalArgumentException("n >= 0 required but it was " + n);
        }
        this.n = n;
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> actual) {
        if (actual instanceof Fuseable.ConditionalSubscriber) {
            return new TakeConditionalSubscriber((Fuseable.ConditionalSubscriber)actual, this.n);
        }
        return new TakeSubscriber<T>(actual, this.n);
    }

    @Override
    public int getPrefetch() {
        return Integer.MAX_VALUE;
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static final class TakeFuseableSubscriber<T>
    implements Fuseable.QueueSubscription<T>,
    InnerOperator<T, T> {
        final CoreSubscriber<? super T> actual;
        final long n;
        long remaining;
        Fuseable.QueueSubscription<T> qs;
        boolean done;
        volatile int wip;
        static final AtomicIntegerFieldUpdater<TakeFuseableSubscriber> WIP = AtomicIntegerFieldUpdater.newUpdater(TakeFuseableSubscriber.class, "wip");
        int inputMode;

        TakeFuseableSubscriber(CoreSubscriber<? super T> actual, long n) {
            this.actual = actual;
            this.n = n;
            this.remaining = n;
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (Operators.validate(this.qs, s)) {
                if (this.n == 0L) {
                    s.cancel();
                    this.done = true;
                    Operators.complete(this.actual);
                } else {
                    this.qs = (Fuseable.QueueSubscription)s;
                    this.actual.onSubscribe(this);
                }
            }
        }

        public void onNext(T t) {
            if (this.inputMode == 2) {
                this.actual.onNext(null);
                return;
            }
            if (this.done) {
                Operators.onNextDropped(t, this.actual.currentContext());
                return;
            }
            long r = this.remaining;
            if (r == 0L) {
                this.qs.cancel();
                this.onComplete();
                return;
            }
            this.remaining = --r;
            boolean stop = r == 0L;
            this.actual.onNext(t);
            if (stop) {
                this.qs.cancel();
                this.onComplete();
            }
        }

        public void onError(Throwable t) {
            if (this.done) {
                Operators.onErrorDropped(t, this.actual.currentContext());
                return;
            }
            this.done = true;
            this.actual.onError(t);
        }

        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            this.actual.onComplete();
        }

        public void request(long n) {
            if (this.wip == 0 && WIP.compareAndSet(this, 0, 1)) {
                if (n >= this.n) {
                    this.qs.request(Long.MAX_VALUE);
                } else {
                    this.qs.request(n);
                }
                return;
            }
            this.qs.request(n);
        }

        public void cancel() {
            this.qs.cancel();
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.TERMINATED) {
                return this.done;
            }
            if (key == Scannable.Attr.PARENT) {
                return this.qs;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return InnerOperator.super.scanUnsafe(key);
        }

        @Override
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override
        public int requestFusion(int requestedMode) {
            int m;
            this.inputMode = m = this.qs.requestFusion(requestedMode);
            return m;
        }

        @Override
        @Nullable
        public T poll() {
            if (this.done) {
                return null;
            }
            long r = this.remaining;
            Object v = this.qs.poll();
            if (r == 0L) {
                this.done = true;
                if (this.inputMode == 2) {
                    this.qs.cancel();
                    this.actual.onComplete();
                }
                return null;
            }
            if (v != null) {
                this.remaining = --r;
                if (r == 0L && !this.done) {
                    this.done = true;
                    if (this.inputMode == 2) {
                        this.qs.cancel();
                        this.actual.onComplete();
                    }
                }
            }
            return (T)v;
        }

        @Override
        public boolean isEmpty() {
            return this.remaining == 0L || this.qs.isEmpty();
        }

        @Override
        public void clear() {
            this.qs.clear();
        }

        @Override
        public int size() {
            return this.qs.size();
        }
    }

    static final class TakeConditionalSubscriber<T>
    implements Fuseable.ConditionalSubscriber<T>,
    InnerOperator<T, T> {
        final Fuseable.ConditionalSubscriber<? super T> actual;
        final long n;
        long remaining;
        Subscription s;
        boolean done;
        volatile int wip;
        static final AtomicIntegerFieldUpdater<TakeConditionalSubscriber> WIP = AtomicIntegerFieldUpdater.newUpdater(TakeConditionalSubscriber.class, "wip");

        TakeConditionalSubscriber(Fuseable.ConditionalSubscriber<? super T> actual, long n) {
            this.actual = actual;
            this.n = n;
            this.remaining = n;
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (Operators.validate(this.s, s)) {
                if (this.n == 0L) {
                    s.cancel();
                    this.done = true;
                    Operators.complete(this.actual);
                } else {
                    this.s = s;
                    this.actual.onSubscribe(this);
                }
            }
        }

        public void onNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, this.actual.currentContext());
                return;
            }
            long r = this.remaining;
            if (r == 0L) {
                this.s.cancel();
                this.onComplete();
                return;
            }
            this.remaining = --r;
            boolean stop = r == 0L;
            this.actual.onNext(t);
            if (stop) {
                this.s.cancel();
                this.onComplete();
            }
        }

        @Override
        public boolean tryOnNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, this.actual.currentContext());
                return true;
            }
            long r = this.remaining;
            if (r == 0L) {
                this.s.cancel();
                this.onComplete();
                return true;
            }
            this.remaining = --r;
            boolean stop = r == 0L;
            boolean b = this.actual.tryOnNext(t);
            if (stop) {
                this.s.cancel();
                this.onComplete();
            }
            return b;
        }

        public void onError(Throwable t) {
            if (this.done) {
                Operators.onErrorDropped(t, this.actual.currentContext());
                return;
            }
            this.done = true;
            this.actual.onError(t);
        }

        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            this.actual.onComplete();
        }

        public void request(long n) {
            if (this.wip == 0 && WIP.compareAndSet(this, 0, 1)) {
                if (n >= this.n) {
                    this.s.request(Long.MAX_VALUE);
                } else {
                    this.s.request(n);
                }
                return;
            }
            this.s.request(n);
        }

        public void cancel() {
            this.s.cancel();
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.TERMINATED) {
                return this.done;
            }
            if (key == Scannable.Attr.PARENT) {
                return this.s;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return InnerOperator.super.scanUnsafe(key);
        }

        @Override
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }
    }

    static final class TakeSubscriber<T>
    implements InnerOperator<T, T> {
        final CoreSubscriber<? super T> actual;
        final long n;
        long remaining;
        Subscription s;
        boolean done;
        volatile int wip;
        static final AtomicIntegerFieldUpdater<TakeSubscriber> WIP = AtomicIntegerFieldUpdater.newUpdater(TakeSubscriber.class, "wip");

        public TakeSubscriber(CoreSubscriber<? super T> actual, long n) {
            this.actual = actual;
            this.n = n;
            this.remaining = n;
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (Operators.validate(this.s, s)) {
                if (this.n == 0L) {
                    s.cancel();
                    this.done = true;
                    Operators.complete(this.actual);
                } else {
                    this.s = s;
                    this.actual.onSubscribe(this);
                }
            }
        }

        public void onNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, this.actual.currentContext());
                return;
            }
            long r = this.remaining;
            if (r == 0L) {
                this.s.cancel();
                this.onComplete();
                return;
            }
            this.remaining = --r;
            boolean stop = r == 0L;
            this.actual.onNext(t);
            if (stop) {
                this.s.cancel();
                this.onComplete();
            }
        }

        public void onError(Throwable t) {
            if (this.done) {
                Operators.onErrorDropped(t, this.actual.currentContext());
                return;
            }
            this.done = true;
            this.actual.onError(t);
        }

        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            this.actual.onComplete();
        }

        public void request(long n) {
            if (this.wip == 0 && WIP.compareAndSet(this, 0, 1)) {
                if (n >= this.n) {
                    this.s.request(Long.MAX_VALUE);
                } else {
                    this.s.request(n);
                }
                return;
            }
            this.s.request(n);
        }

        public void cancel() {
            this.s.cancel();
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.TERMINATED) {
                return this.done;
            }
            if (key == Scannable.Attr.PARENT) {
                return this.s;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return InnerOperator.super.scanUnsafe(key);
        }

        @Override
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }
    }
}

