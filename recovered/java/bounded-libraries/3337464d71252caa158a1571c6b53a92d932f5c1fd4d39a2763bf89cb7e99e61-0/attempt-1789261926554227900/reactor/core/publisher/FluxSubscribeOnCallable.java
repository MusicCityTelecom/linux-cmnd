/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import reactor.core.CoreSubscriber;
import reactor.core.Disposable;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.InnerProducer;
import reactor.core.publisher.OperatorDisposables;
import reactor.core.publisher.Operators;
import reactor.core.scheduler.Scheduler;
import reactor.util.annotation.Nullable;

final class FluxSubscribeOnCallable<T>
extends Flux<T>
implements Fuseable,
Scannable {
    final Callable<? extends T> callable;
    final Scheduler scheduler;

    FluxSubscribeOnCallable(Callable<? extends T> callable, Scheduler scheduler) {
        this.callable = Objects.requireNonNull(callable, "callable");
        this.scheduler = Objects.requireNonNull(scheduler, "scheduler");
    }

    @Override
    public void subscribe(CoreSubscriber<? super T> actual) {
        block2: {
            CallableSubscribeOnSubscription<T> parent = new CallableSubscribeOnSubscription<T>(actual, this.callable, this.scheduler);
            actual.onSubscribe(parent);
            try {
                Disposable f = this.scheduler.schedule(parent);
                parent.setMainFuture(f);
            }
            catch (RejectedExecutionException ree) {
                if (parent.state == 4) break block2;
                actual.onError(Operators.onRejectedExecution(ree, actual.currentContext()));
            }
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

    static final class CallableSubscribeOnSubscription<T>
    implements Fuseable.QueueSubscription<T>,
    InnerProducer<T>,
    Runnable {
        final CoreSubscriber<? super T> actual;
        final Callable<? extends T> callable;
        final Scheduler scheduler;
        volatile int state;
        static final AtomicIntegerFieldUpdater<CallableSubscribeOnSubscription> STATE = AtomicIntegerFieldUpdater.newUpdater(CallableSubscribeOnSubscription.class, "state");
        T value;
        static final int NO_REQUEST_HAS_VALUE = 1;
        static final int HAS_REQUEST_NO_VALUE = 2;
        static final int HAS_REQUEST_HAS_VALUE = 3;
        static final int HAS_CANCELLED = 4;
        int fusionState;
        static final int NO_VALUE = 1;
        static final int HAS_VALUE = 2;
        static final int COMPLETE = 3;
        volatile Disposable mainFuture;
        static final AtomicReferenceFieldUpdater<CallableSubscribeOnSubscription, Disposable> MAIN_FUTURE = AtomicReferenceFieldUpdater.newUpdater(CallableSubscribeOnSubscription.class, Disposable.class, "mainFuture");
        volatile Disposable requestFuture;
        static final AtomicReferenceFieldUpdater<CallableSubscribeOnSubscription, Disposable> REQUEST_FUTURE = AtomicReferenceFieldUpdater.newUpdater(CallableSubscribeOnSubscription.class, Disposable.class, "requestFuture");

        CallableSubscribeOnSubscription(CoreSubscriber<? super T> actual, Callable<? extends T> callable, Scheduler scheduler) {
            this.actual = actual;
            this.callable = callable;
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
                return this.state == 4;
            }
            if (key == Scannable.Attr.BUFFERED) {
                return this.value != null ? 1 : 0;
            }
            if (key == Scannable.Attr.RUN_ON) {
                return this.scheduler;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.ASYNC;
            }
            return InnerProducer.super.scanUnsafe(key);
        }

        public void cancel() {
            this.state = 4;
            this.fusionState = 3;
            Disposable a = this.mainFuture;
            if (a != OperatorDisposables.DISPOSED && (a = MAIN_FUTURE.getAndSet(this, OperatorDisposables.DISPOSED)) != null && a != OperatorDisposables.DISPOSED) {
                a.dispose();
            }
            if ((a = this.requestFuture) != OperatorDisposables.DISPOSED && (a = REQUEST_FUTURE.getAndSet(this, OperatorDisposables.DISPOSED)) != null && a != OperatorDisposables.DISPOSED) {
                a.dispose();
            }
        }

        @Override
        public void clear() {
            this.value = null;
            this.fusionState = 3;
        }

        @Override
        public boolean isEmpty() {
            return this.fusionState == 3;
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
        public int requestFusion(int requestedMode) {
            if ((requestedMode & 2) != 0 && (requestedMode & 4) == 0) {
                this.fusionState = 1;
                return 2;
            }
            return 0;
        }

        @Override
        public int size() {
            return this.isEmpty() ? 0 : 1;
        }

        void setMainFuture(Disposable c) {
            Disposable a;
            do {
                if ((a = this.mainFuture) != OperatorDisposables.DISPOSED) continue;
                c.dispose();
                return;
            } while (!MAIN_FUTURE.compareAndSet(this, a, c));
        }

        void setRequestFuture(Disposable c) {
            Disposable a;
            do {
                if ((a = this.requestFuture) != OperatorDisposables.DISPOSED) continue;
                c.dispose();
                return;
            } while (!REQUEST_FUTURE.compareAndSet(this, a, c));
        }

        @Override
        public void run() {
            int s;
            T v;
            try {
                v = this.callable.call();
            }
            catch (Throwable ex) {
                this.actual.onError(Operators.onOperatorError(this, ex, this.actual.currentContext()));
                return;
            }
            if (v == null) {
                this.fusionState = 3;
                this.actual.onComplete();
                return;
            }
            do {
                if ((s = this.state) == 4 || s == 3 || s == 1) {
                    return;
                }
                if (s == 2) {
                    if (this.fusionState == 1) {
                        this.value = v;
                        this.fusionState = 2;
                    }
                    this.actual.onNext(v);
                    if (this.state != 4) {
                        this.actual.onComplete();
                    }
                    return;
                }
                this.value = v;
            } while (!STATE.compareAndSet(this, s, 1));
        }

        public void request(long n) {
            if (Operators.validate(n)) {
                int s;
                do {
                    if ((s = this.state) == 4 || s == 2 || s == 3) {
                        return;
                    }
                    if (s != 1) continue;
                    if (STATE.compareAndSet(this, s, 3)) {
                        try {
                            Disposable f = this.scheduler.schedule(this::emitValue);
                            this.setRequestFuture(f);
                        }
                        catch (RejectedExecutionException ree) {
                            this.actual.onError(Operators.onRejectedExecution(ree, this.actual.currentContext()));
                        }
                    }
                    return;
                } while (!STATE.compareAndSet(this, s, 2));
                return;
            }
        }

        void emitValue() {
            if (this.fusionState == 1) {
                this.fusionState = 2;
            }
            T v = this.value;
            this.clear();
            if (v != null) {
                this.actual.onNext(v);
            }
            if (this.state != 4) {
                this.actual.onComplete();
            }
        }
    }
}

