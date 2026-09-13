/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Disposable;
import reactor.core.Scannable;
import reactor.core.publisher.InnerOperator;
import reactor.core.publisher.InternalMonoOperator;
import reactor.core.publisher.Mono;
import reactor.core.publisher.OperatorDisposables;
import reactor.core.publisher.Operators;
import reactor.core.scheduler.Scheduler;
import reactor.util.annotation.Nullable;

final class MonoPublishOn<T>
extends InternalMonoOperator<T, T> {
    final Scheduler scheduler;

    MonoPublishOn(Mono<? extends T> source, Scheduler scheduler) {
        super(source);
        this.scheduler = scheduler;
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> actual) {
        return new PublishOnSubscriber<T>(actual, this.scheduler);
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

    static final class PublishOnSubscriber<T>
    implements InnerOperator<T, T>,
    Runnable {
        final CoreSubscriber<? super T> actual;
        final Scheduler scheduler;
        Subscription s;
        volatile Disposable future;
        static final AtomicReferenceFieldUpdater<PublishOnSubscriber, Disposable> FUTURE = AtomicReferenceFieldUpdater.newUpdater(PublishOnSubscriber.class, Disposable.class, "future");
        volatile T value;
        static final AtomicReferenceFieldUpdater<PublishOnSubscriber, Object> VALUE = AtomicReferenceFieldUpdater.newUpdater(PublishOnSubscriber.class, Object.class, "value");
        volatile Throwable error;

        PublishOnSubscriber(CoreSubscriber<? super T> actual, Scheduler scheduler) {
            this.actual = actual;
            this.scheduler = scheduler;
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.CANCELLED) {
                return this.future == OperatorDisposables.DISPOSED;
            }
            if (key == Scannable.Attr.PARENT) {
                return this.s;
            }
            if (key == Scannable.Attr.ERROR) {
                return this.error;
            }
            if (key == Scannable.Attr.RUN_ON) {
                return this.scheduler;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.ASYNC;
            }
            return InnerOperator.super.scanUnsafe(key);
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
            this.value = t;
            this.trySchedule(this, null, t);
        }

        public void onError(Throwable t) {
            this.error = t;
            this.trySchedule(null, t, null);
        }

        public void onComplete() {
            if (this.value == null) {
                this.trySchedule(null, null, null);
            }
        }

        void trySchedule(@Nullable Subscription subscription, @Nullable Throwable suppressed, @Nullable Object dataSignal) {
            if (this.future != null) {
                return;
            }
            try {
                this.future = this.scheduler.schedule(this);
            }
            catch (RejectedExecutionException ree) {
                this.actual.onError(Operators.onRejectedExecution(ree, subscription, suppressed, dataSignal, this.actual.currentContext()));
            }
        }

        public void request(long n) {
            this.s.request(n);
        }

        public void cancel() {
            Disposable c = this.future;
            if (c != OperatorDisposables.DISPOSED) {
                c = FUTURE.getAndSet(this, OperatorDisposables.DISPOSED);
                if (c != null && !OperatorDisposables.isDisposed(c)) {
                    c.dispose();
                }
                this.value = null;
            }
            this.s.cancel();
        }

        @Override
        public void run() {
            if (OperatorDisposables.isDisposed(this.future)) {
                return;
            }
            Object v = VALUE.getAndSet(this, null);
            if (v != null) {
                this.actual.onNext(v);
                this.actual.onComplete();
            } else {
                Throwable e = this.error;
                if (e != null) {
                    this.actual.onError(e);
                } else {
                    this.actual.onComplete();
                }
            }
        }
    }
}

