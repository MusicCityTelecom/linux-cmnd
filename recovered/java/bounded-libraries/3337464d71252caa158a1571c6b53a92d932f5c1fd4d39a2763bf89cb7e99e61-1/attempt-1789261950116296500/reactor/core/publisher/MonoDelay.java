/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import reactor.core.CoreSubscriber;
import reactor.core.Disposable;
import reactor.core.Exceptions;
import reactor.core.Scannable;
import reactor.core.publisher.InnerProducer;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Operators;
import reactor.core.publisher.SourceProducer;
import reactor.core.scheduler.Scheduler;
import reactor.util.annotation.Nullable;

final class MonoDelay
extends Mono<Long>
implements Scannable,
SourceProducer<Long> {
    final Scheduler timedScheduler;
    final long delay;
    final TimeUnit unit;
    static final String CONTEXT_OPT_OUT_NOBACKPRESSURE = "reactor.core.publisher.MonoDelay.failOnBackpressure";

    MonoDelay(long delay, TimeUnit unit, Scheduler timedScheduler) {
        this.delay = delay;
        this.unit = Objects.requireNonNull(unit, "unit");
        this.timedScheduler = Objects.requireNonNull(timedScheduler, "timedScheduler");
    }

    @Override
    public void subscribe(CoreSubscriber<? super Long> actual) {
        block2: {
            boolean failOnBackpressure = actual.currentContext().getOrDefault(CONTEXT_OPT_OUT_NOBACKPRESSURE, false) == Boolean.TRUE;
            MonoDelayRunnable r = new MonoDelayRunnable(actual, failOnBackpressure);
            actual.onSubscribe(r);
            try {
                r.setCancel(this.timedScheduler.schedule(r, this.delay, this.unit));
            }
            catch (RejectedExecutionException ree) {
                if (MonoDelayRunnable.wasCancelled(r.state)) break block2;
                actual.onError(Operators.onRejectedExecution(ree, r, null, null, actual.currentContext()));
            }
        }
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_ON) {
            return this.timedScheduler;
        }
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.ASYNC;
        }
        return null;
    }

    static final class MonoDelayRunnable
    implements Runnable,
    InnerProducer<Long> {
        final CoreSubscriber<? super Long> actual;
        final boolean failOnBackpressure;
        Disposable cancel;
        volatile int state;
        static final AtomicIntegerFieldUpdater<MonoDelayRunnable> STATE = AtomicIntegerFieldUpdater.newUpdater(MonoDelayRunnable.class, "state");
        static final byte FLAG_CANCELLED = 64;
        static final byte FLAG_REQUESTED = 32;
        static final byte FLAG_REQUESTED_EARLY = 16;
        static final byte FLAG_CANCEL_SET = 1;
        static final byte FLAG_DELAY_DONE = 2;
        static final byte FLAG_PROPAGATED = 4;

        MonoDelayRunnable(CoreSubscriber<? super Long> actual, boolean failOnBackpressure) {
            this.actual = actual;
            this.failOnBackpressure = failOnBackpressure;
        }

        static int markCancelFutureSet(MonoDelayRunnable instance) {
            int state;
            do {
                if (!MonoDelayRunnable.wasCancelled(state = instance.state) && !MonoDelayRunnable.wasCancelFutureSet(state)) continue;
                return state;
            } while (!STATE.compareAndSet(instance, state, state | 1));
            return state;
        }

        static boolean wasCancelFutureSet(int state) {
            return (state & 1) == 1;
        }

        static int markCancelled(MonoDelayRunnable instance) {
            int state;
            do {
                if (!MonoDelayRunnable.wasCancelled(state = instance.state) && !MonoDelayRunnable.wasPropagated(state)) continue;
                return state;
            } while (!STATE.compareAndSet(instance, state, state | 0x40));
            return state;
        }

        static boolean wasCancelled(int state) {
            return (state & 0x40) == 64;
        }

        static int markDelayDone(MonoDelayRunnable instance) {
            int state;
            do {
                if (!MonoDelayRunnable.wasCancelled(state = instance.state) && !MonoDelayRunnable.wasDelayDone(state)) continue;
                return state;
            } while (!STATE.compareAndSet(instance, state, state | 2));
            return state;
        }

        static boolean wasDelayDone(int state) {
            return (state & 2) == 2;
        }

        static int markRequested(MonoDelayRunnable instance) {
            int newFlag;
            int state;
            do {
                if (MonoDelayRunnable.wasCancelled(state = instance.state) || MonoDelayRunnable.wasRequested(state)) {
                    return state;
                }
                newFlag = 32;
                if (MonoDelayRunnable.wasDelayDone(state)) continue;
                newFlag |= 0x10;
            } while (!STATE.compareAndSet(instance, state, state | newFlag));
            return state;
        }

        static boolean wasRequested(int state) {
            return (state & 0x20) == 32;
        }

        static int markPropagated(MonoDelayRunnable instance) {
            int state;
            do {
                if (!MonoDelayRunnable.wasCancelled(state = instance.state)) continue;
                return state;
            } while (!STATE.compareAndSet(instance, state, state | 4));
            return state;
        }

        static boolean wasPropagated(int state) {
            return (state & 4) == 4;
        }

        @Override
        public CoreSubscriber<? super Long> actual() {
            return this.actual;
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.TERMINATED) {
                return MonoDelayRunnable.wasDelayDone(this.state) && MonoDelayRunnable.wasRequested(this.state);
            }
            if (key == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM) {
                return MonoDelayRunnable.wasRequested(this.state) ? 1L : 0L;
            }
            if (key == Scannable.Attr.CANCELLED) {
                return MonoDelayRunnable.wasCancelled(this.state);
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.ASYNC;
            }
            return InnerProducer.super.scanUnsafe(key);
        }

        void setCancel(Disposable cancel) {
            Disposable c = this.cancel;
            this.cancel = cancel;
            int previousState = MonoDelayRunnable.markCancelFutureSet(this);
            if (MonoDelayRunnable.wasCancelFutureSet(previousState)) {
                if (c != null) {
                    c.dispose();
                }
                return;
            }
            if (MonoDelayRunnable.wasCancelled(previousState)) {
                cancel.dispose();
            }
        }

        private void propagateDelay() {
            int previousState = MonoDelayRunnable.markPropagated(this);
            if (MonoDelayRunnable.wasCancelled(previousState)) {
                return;
            }
            try {
                this.actual.onNext(0L);
                this.actual.onComplete();
            }
            catch (Throwable t) {
                this.actual.onError(Operators.onOperatorError(t, this.actual.currentContext()));
            }
        }

        @Override
        public void run() {
            int previousState = MonoDelayRunnable.markDelayDone(this);
            if (MonoDelayRunnable.wasCancelled(previousState) || MonoDelayRunnable.wasDelayDone(previousState)) {
                return;
            }
            if (MonoDelayRunnable.wasRequested(previousState)) {
                this.propagateDelay();
            } else if (this.failOnBackpressure) {
                this.actual.onError(Exceptions.failWithOverflow("Could not emit value due to lack of requests"));
            }
        }

        public void cancel() {
            int previousState = MonoDelayRunnable.markCancelled(this);
            if (MonoDelayRunnable.wasCancelled(previousState) || MonoDelayRunnable.wasPropagated(previousState)) {
                return;
            }
            if (MonoDelayRunnable.wasCancelFutureSet(previousState)) {
                this.cancel.dispose();
            }
        }

        public void request(long n) {
            if (Operators.validate(n)) {
                int previousState = MonoDelayRunnable.markRequested(this);
                if (MonoDelayRunnable.wasCancelled(previousState) || MonoDelayRunnable.wasRequested(previousState)) {
                    return;
                }
                if (MonoDelayRunnable.wasDelayDone(previousState) && !this.failOnBackpressure) {
                    this.propagateDelay();
                }
            }
        }
    }
}

