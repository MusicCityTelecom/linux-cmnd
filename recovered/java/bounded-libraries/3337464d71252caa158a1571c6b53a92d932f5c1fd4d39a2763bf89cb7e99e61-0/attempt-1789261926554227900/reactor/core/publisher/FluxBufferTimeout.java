/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.function.Supplier;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Disposable;
import reactor.core.Exceptions;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.InnerOperator;
import reactor.core.publisher.InternalFluxOperator;
import reactor.core.publisher.Operators;
import reactor.core.scheduler.Scheduler;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

final class FluxBufferTimeout<T, C extends Collection<? super T>>
extends InternalFluxOperator<T, C> {
    final int batchSize;
    final Supplier<C> bufferSupplier;
    final Scheduler timer;
    final long timespan;
    final TimeUnit unit;

    FluxBufferTimeout(Flux<T> source, int maxSize, long timespan, TimeUnit unit, Scheduler timer, Supplier<C> bufferSupplier) {
        super(source);
        if (timespan <= 0L) {
            throw new IllegalArgumentException("Timeout period must be strictly positive");
        }
        if (maxSize <= 0) {
            throw new IllegalArgumentException("maxSize must be strictly positive");
        }
        this.timer = Objects.requireNonNull(timer, "Timer");
        this.timespan = timespan;
        this.unit = Objects.requireNonNull(unit, "unit");
        this.batchSize = maxSize;
        this.bufferSupplier = Objects.requireNonNull(bufferSupplier, "bufferSupplier");
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super C> actual) {
        return new BufferTimeoutSubscriber(Operators.serialize(actual), this.batchSize, this.timespan, this.unit, this.timer.createWorker(), this.bufferSupplier);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_ON) {
            return this.timer;
        }
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.ASYNC;
        }
        return super.scanUnsafe(key);
    }

    static final class BufferTimeoutSubscriber<T, C extends Collection<? super T>>
    implements InnerOperator<T, C> {
        final CoreSubscriber<? super C> actual;
        static final int NOT_TERMINATED = 0;
        static final int TERMINATED_WITH_SUCCESS = 1;
        static final int TERMINATED_WITH_ERROR = 2;
        static final int TERMINATED_WITH_CANCEL = 3;
        final int batchSize;
        final long timespan;
        final TimeUnit unit;
        final Scheduler.Worker timer;
        final Runnable flushTask;
        protected Subscription subscription;
        volatile int terminated = 0;
        static final AtomicIntegerFieldUpdater<BufferTimeoutSubscriber> TERMINATED = AtomicIntegerFieldUpdater.newUpdater(BufferTimeoutSubscriber.class, "terminated");
        volatile long requested;
        static final AtomicLongFieldUpdater<BufferTimeoutSubscriber> REQUESTED = AtomicLongFieldUpdater.newUpdater(BufferTimeoutSubscriber.class, "requested");
        volatile long outstanding;
        static final AtomicLongFieldUpdater<BufferTimeoutSubscriber> OUTSTANDING = AtomicLongFieldUpdater.newUpdater(BufferTimeoutSubscriber.class, "outstanding");
        volatile int index = 0;
        static final AtomicIntegerFieldUpdater<BufferTimeoutSubscriber> INDEX = AtomicIntegerFieldUpdater.newUpdater(BufferTimeoutSubscriber.class, "index");
        volatile Disposable timespanRegistration;
        final Supplier<C> bufferSupplier;
        volatile C values;

        BufferTimeoutSubscriber(CoreSubscriber<? super C> actual, int maxSize, long timespan, TimeUnit unit, Scheduler.Worker timer, Supplier<C> bufferSupplier) {
            this.actual = actual;
            this.timespan = timespan;
            this.unit = unit;
            this.timer = timer;
            this.flushTask = () -> {
                if (this.terminated == 0) {
                    int index;
                    do {
                        if ((index = this.index) != 0) continue;
                        return;
                    } while (!INDEX.compareAndSet(this, index, 0));
                    this.flushCallback(null);
                }
            };
            this.batchSize = maxSize;
            this.bufferSupplier = bufferSupplier;
        }

        protected void doOnSubscribe() {
            this.values = (Collection)this.bufferSupplier.get();
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        void nextCallback(T value) {
            BufferTimeoutSubscriber bufferTimeoutSubscriber = this;
            synchronized (bufferTimeoutSubscriber) {
                if (OUTSTANDING.decrementAndGet(this) < 0L) {
                    this.actual.onError(Exceptions.failWithOverflow("Unrequested element received"));
                    Context ctx = this.actual.currentContext();
                    Operators.onDiscard(value, ctx);
                    Operators.onDiscardMultiple(this.values, ctx);
                    return;
                }
                Object v = this.values;
                if (v == null) {
                    this.values = v = (Collection)Objects.requireNonNull(this.bufferSupplier.get(), "The bufferSupplier returned a null buffer");
                }
                v.add(value);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        void flushCallback(@Nullable T ev) {
            C v;
            boolean flush = false;
            BufferTimeoutSubscriber bufferTimeoutSubscriber = this;
            synchronized (bufferTimeoutSubscriber) {
                v = this.values;
                if (v != null && !v.isEmpty()) {
                    this.values = (Collection)this.bufferSupplier.get();
                    flush = true;
                }
            }
            if (flush) {
                long r = this.requested;
                if (r != 0L) {
                    if (r != Long.MAX_VALUE) {
                        do {
                            long next;
                            if (!REQUESTED.compareAndSet(this, r, next = r - 1L)) continue;
                            this.actual.onNext(v);
                            return;
                        } while ((r = this.requested) > 0L);
                    } else {
                        this.actual.onNext(v);
                        return;
                    }
                }
                this.cancel();
                this.actual.onError(Exceptions.failWithOverflow("Could not emit buffer due to lack of requests"));
                Operators.onDiscardMultiple(v, this.actual.currentContext());
            }
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.PARENT) {
                return this.subscription;
            }
            if (key == Scannable.Attr.CANCELLED) {
                return this.terminated == 3;
            }
            if (key == Scannable.Attr.TERMINATED) {
                return this.terminated == 2 || this.terminated == 1;
            }
            if (key == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM) {
                return this.requested;
            }
            if (key == Scannable.Attr.CAPACITY) {
                return this.batchSize;
            }
            if (key == Scannable.Attr.BUFFERED) {
                return this.batchSize - this.index;
            }
            if (key == Scannable.Attr.RUN_ON) {
                return this.timer;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.ASYNC;
            }
            return InnerOperator.super.scanUnsafe(key);
        }

        public void onNext(T value) {
            int index;
            while (!INDEX.compareAndSet(this, (index = this.index + 1) - 1, index)) {
            }
            if (index == 1) {
                try {
                    this.timespanRegistration = this.timer.schedule(this.flushTask, this.timespan, this.unit);
                }
                catch (RejectedExecutionException ree) {
                    Context ctx = this.actual.currentContext();
                    this.onError(Operators.onRejectedExecution(ree, this.subscription, null, value, ctx));
                    Operators.onDiscard(value, ctx);
                    return;
                }
            }
            this.nextCallback(value);
            if (this.index % this.batchSize == 0) {
                this.index = 0;
                if (this.timespanRegistration != null) {
                    this.timespanRegistration.dispose();
                    this.timespanRegistration = null;
                }
                this.flushCallback(value);
            }
        }

        void checkedComplete() {
            try {
                this.flushCallback(null);
            }
            finally {
                this.actual.onComplete();
            }
        }

        final boolean isCompleted() {
            return this.terminated == 1;
        }

        final boolean isFailed() {
            return this.terminated == 2;
        }

        public void request(long n) {
            if (Operators.validate(n)) {
                Operators.addCap(REQUESTED, this, n);
                if (this.terminated != 0) {
                    return;
                }
                if (this.batchSize == Integer.MAX_VALUE || n == Long.MAX_VALUE) {
                    this.requestMore(Long.MAX_VALUE);
                } else {
                    long requestLimit = Operators.multiplyCap(this.requested, this.batchSize);
                    if (requestLimit > this.outstanding) {
                        this.requestMore(requestLimit - this.outstanding);
                    }
                }
            }
        }

        final void requestMore(long n) {
            Subscription s = this.subscription;
            if (s != null) {
                Operators.addCap(OUTSTANDING, this, n);
                s.request(n);
            }
        }

        @Override
        public CoreSubscriber<? super C> actual() {
            return this.actual;
        }

        public void onComplete() {
            if (TERMINATED.compareAndSet(this, 0, 1)) {
                this.timer.dispose();
                this.checkedComplete();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public void onError(Throwable throwable) {
            if (TERMINATED.compareAndSet(this, 0, 2)) {
                this.timer.dispose();
                Context ctx = this.actual.currentContext();
                BufferTimeoutSubscriber bufferTimeoutSubscriber = this;
                synchronized (bufferTimeoutSubscriber) {
                    C v = this.values;
                    if (v != null) {
                        Operators.onDiscardMultiple(v, ctx);
                        v.clear();
                        this.values = null;
                    }
                }
                this.actual.onError(throwable);
            }
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (Operators.validate(this.subscription, s)) {
                this.subscription = s;
                this.doOnSubscribe();
                this.actual.onSubscribe(this);
            }
        }

        public void cancel() {
            if (TERMINATED.compareAndSet(this, 0, 3)) {
                C v;
                this.timer.dispose();
                Subscription s = this.subscription;
                if (s != null) {
                    this.subscription = null;
                    s.cancel();
                }
                if ((v = this.values) != null) {
                    Operators.onDiscardMultiple(v, this.actual.currentContext());
                    v.clear();
                }
            }
        }
    }
}

