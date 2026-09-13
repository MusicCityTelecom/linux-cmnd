/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Subscriber
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import org.reactivestreams.Subscriber;
import reactor.core.CoreSubscriber;
import reactor.core.Disposable;
import reactor.core.Disposables;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.InnerProducer;
import reactor.core.publisher.OperatorDisposables;
import reactor.core.publisher.Operators;
import reactor.core.scheduler.Scheduler;
import reactor.util.annotation.Nullable;

final class FluxSubscribeOnValue<T>
extends Flux<T>
implements Fuseable,
Scannable {
    final T value;
    final Scheduler scheduler;

    FluxSubscribeOnValue(@Nullable T value, Scheduler scheduler) {
        this.value = value;
        this.scheduler = Objects.requireNonNull(scheduler, "scheduler");
    }

    @Override
    public void subscribe(CoreSubscriber<? super T> actual) {
        T v = this.value;
        if (v == null) {
            ScheduledEmpty parent = new ScheduledEmpty(actual);
            actual.onSubscribe(parent);
            try {
                parent.setFuture(this.scheduler.schedule(parent));
            }
            catch (RejectedExecutionException ree) {
                if (parent.future != OperatorDisposables.DISPOSED) {
                    actual.onError(Operators.onRejectedExecution(ree, actual.currentContext()));
                }
            }
        } else {
            actual.onSubscribe(new ScheduledScalar<T>(actual, v, this.scheduler));
        }
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_ON) {
            return this.scheduler;
        }
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.ASYNC;
        }
        return null;
    }

    static final class ScheduledEmpty
    implements Fuseable.QueueSubscription<Void>,
    Runnable {
        final Subscriber<?> actual;
        volatile Disposable future;
        static final AtomicReferenceFieldUpdater<ScheduledEmpty, Disposable> FUTURE = AtomicReferenceFieldUpdater.newUpdater(ScheduledEmpty.class, Disposable.class, "future");
        static final Disposable FINISHED = Disposables.disposed();

        ScheduledEmpty(Subscriber<?> actual) {
            this.actual = actual;
        }

        public void request(long n) {
            Operators.validate(n);
        }

        public void cancel() {
            Disposable f = this.future;
            if (f != OperatorDisposables.DISPOSED && f != FINISHED && (f = FUTURE.getAndSet(this, OperatorDisposables.DISPOSED)) != null && f != OperatorDisposables.DISPOSED && f != FINISHED) {
                f.dispose();
            }
        }

        @Override
        public void run() {
            try {
                this.actual.onComplete();
            }
            finally {
                FUTURE.lazySet(this, FINISHED);
            }
        }

        void setFuture(Disposable f) {
            Disposable a;
            if (!FUTURE.compareAndSet(this, null, f) && (a = this.future) != FINISHED && a != OperatorDisposables.DISPOSED) {
                f.dispose();
            }
        }

        @Override
        public int requestFusion(int requestedMode) {
            return requestedMode & 2;
        }

        @Override
        @Nullable
        public Void poll() {
            return null;
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public int size() {
            return 0;
        }

        @Override
        public void clear() {
        }
    }

    static final class ScheduledScalar<T>
    implements Fuseable.QueueSubscription<T>,
    InnerProducer<T>,
    Runnable {
        final CoreSubscriber<? super T> actual;
        final T value;
        final Scheduler scheduler;
        volatile int once;
        static final AtomicIntegerFieldUpdater<ScheduledScalar> ONCE = AtomicIntegerFieldUpdater.newUpdater(ScheduledScalar.class, "once");
        volatile Disposable future;
        static final AtomicReferenceFieldUpdater<ScheduledScalar, Disposable> FUTURE = AtomicReferenceFieldUpdater.newUpdater(ScheduledScalar.class, Disposable.class, "future");
        static final Disposable FINISHED = Disposables.disposed();
        int fusionState;
        static final int NO_VALUE = 1;
        static final int HAS_VALUE = 2;
        static final int COMPLETE = 3;

        ScheduledScalar(CoreSubscriber<? super T> actual, T value, Scheduler scheduler) {
            this.actual = actual;
            this.value = value;
            this.scheduler = scheduler;
        }

        @Override
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.CANCELLED) {
                return this.future == OperatorDisposables.DISPOSED;
            }
            if (key == Scannable.Attr.TERMINATED) {
                return this.future == FINISHED;
            }
            if (key == Scannable.Attr.BUFFERED) {
                return 1;
            }
            if (key == Scannable.Attr.RUN_ON) {
                return this.scheduler;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.ASYNC;
            }
            return InnerProducer.super.scanUnsafe(key);
        }

        public void request(long n) {
            block4: {
                if (Operators.validate(n) && ONCE.compareAndSet(this, 0, 1)) {
                    try {
                        Disposable f = this.scheduler.schedule(this);
                        if (!FUTURE.compareAndSet(this, null, f) && this.future != FINISHED && this.future != OperatorDisposables.DISPOSED) {
                            f.dispose();
                        }
                    }
                    catch (RejectedExecutionException ree) {
                        if (this.future == FINISHED || this.future == OperatorDisposables.DISPOSED) break block4;
                        this.actual.onError(Operators.onRejectedExecution(ree, this, null, this.value, this.actual.currentContext()));
                    }
                }
            }
        }

        public void cancel() {
            ONCE.lazySet(this, 1);
            Disposable f = this.future;
            if (f != OperatorDisposables.DISPOSED && this.future != FINISHED && (f = FUTURE.getAndSet(this, OperatorDisposables.DISPOSED)) != null && f != OperatorDisposables.DISPOSED && f != FINISHED) {
                f.dispose();
            }
        }

        @Override
        public void run() {
            try {
                if (this.fusionState == 1) {
                    this.fusionState = 2;
                }
                this.actual.onNext(this.value);
                this.actual.onComplete();
            }
            finally {
                FUTURE.lazySet(this, FINISHED);
            }
        }

        @Override
        public int requestFusion(int requestedMode) {
            if ((requestedMode & 2) != 0) {
                this.fusionState = 1;
                return 2;
            }
            return 0;
        }

        @Override
        @Nullable
        public T poll() {
            if (this.fusionState == 2) {
                this.fusionState = 3;
                return this.value;
            }
            return null;
        }

        @Override
        public boolean isEmpty() {
            return this.fusionState != 2;
        }

        @Override
        public int size() {
            return this.isEmpty() ? 0 : 1;
        }

        @Override
        public void clear() {
            this.fusionState = 3;
        }
    }
}

