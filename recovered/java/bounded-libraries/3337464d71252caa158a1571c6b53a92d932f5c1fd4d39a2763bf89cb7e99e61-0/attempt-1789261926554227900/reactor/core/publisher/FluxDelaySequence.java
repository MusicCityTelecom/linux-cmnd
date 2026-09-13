/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.InnerOperator;
import reactor.core.publisher.InternalFluxOperator;
import reactor.core.publisher.Operators;
import reactor.core.scheduler.Scheduler;

final class FluxDelaySequence<T>
extends InternalFluxOperator<T, T> {
    final Duration delay;
    final Scheduler scheduler;

    FluxDelaySequence(Flux<T> source, Duration delay, Scheduler scheduler) {
        super(source);
        this.delay = delay;
        this.scheduler = scheduler;
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> actual) {
        Scheduler.Worker w = this.scheduler.createWorker();
        return new DelaySubscriber<T>(actual, this.delay, w);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_ON) {
            return this.scheduler;
        }
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.ASYNC;
        }
        return super.scanUnsafe(key);
    }

    static final class DelaySubscriber<T>
    implements InnerOperator<T, T> {
        final CoreSubscriber<? super T> actual;
        final long delay;
        final TimeUnit timeUnit;
        final Scheduler.Worker w;
        Subscription s;
        volatile boolean done;
        volatile long delayed;
        static final AtomicLongFieldUpdater<DelaySubscriber> DELAYED = AtomicLongFieldUpdater.newUpdater(DelaySubscriber.class, "delayed");

        DelaySubscriber(CoreSubscriber<? super T> actual, Duration delay, Scheduler.Worker w) {
            this.actual = Operators.serialize(actual);
            this.w = w;
            this.delay = delay.toNanos();
            this.timeUnit = TimeUnit.NANOSECONDS;
        }

        @Override
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (Operators.validate(this.s, s)) {
                this.s = s;
                this.actual.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            if (this.done || this.delayed < 0L) {
                Operators.onNextDropped(t, this.currentContext());
                return;
            }
            DELAYED.incrementAndGet(this);
            this.w.schedule(() -> this.delayedNext(t), this.delay, this.timeUnit);
        }

        private void delayedNext(T t) {
            DELAYED.decrementAndGet(this);
            this.actual.onNext(t);
        }

        public void onError(Throwable t) {
            if (this.done) {
                Operators.onErrorDropped(t, this.currentContext());
                return;
            }
            this.done = true;
            if (DELAYED.compareAndSet(this, 0L, -1L)) {
                this.actual.onError(t);
            } else {
                this.w.schedule(new OnError(t), this.delay, this.timeUnit);
            }
        }

        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            if (DELAYED.compareAndSet(this, 0L, -1L)) {
                this.actual.onComplete();
            } else {
                this.w.schedule(new OnComplete(), this.delay, this.timeUnit);
            }
        }

        public void request(long n) {
            this.s.request(n);
        }

        public void cancel() {
            this.s.cancel();
            this.w.dispose();
        }

        @Override
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.PARENT) {
                return this.s;
            }
            if (key == Scannable.Attr.RUN_ON) {
                return this.w;
            }
            if (key == Scannable.Attr.TERMINATED) {
                return this.done;
            }
            if (key == Scannable.Attr.CANCELLED) {
                return this.w.isDisposed() && !this.done;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.ASYNC;
            }
            return InnerOperator.super.scanUnsafe(key);
        }

        final class OnComplete
        implements Runnable {
            OnComplete() {
            }

            @Override
            public void run() {
                try {
                    DelaySubscriber.this.actual.onComplete();
                }
                finally {
                    DelaySubscriber.this.w.dispose();
                }
            }
        }

        final class OnError
        implements Runnable {
            private final Throwable t;

            OnError(Throwable t) {
                this.t = t;
            }

            @Override
            public void run() {
                try {
                    DelaySubscriber.this.actual.onError(this.t);
                }
                finally {
                    DelaySubscriber.this.w.dispose();
                }
            }
        }
    }
}

