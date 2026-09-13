/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import java.time.Duration;
import java.util.ArrayDeque;
import java.util.Objects;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.function.Consumer;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.InnerOperator;
import reactor.core.publisher.InternalFluxOperator;
import reactor.core.publisher.Operators;
import reactor.core.scheduler.Scheduler;
import reactor.util.Logger;
import reactor.util.Loggers;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

final class FluxOnBackpressureBufferTimeout<O>
extends InternalFluxOperator<O, O> {
    private static final Logger LOGGER = Loggers.getLogger(FluxOnBackpressureBufferTimeout.class);
    final Duration ttl;
    final Scheduler ttlScheduler;
    final int bufferSize;
    final Consumer<? super O> onBufferEviction;

    FluxOnBackpressureBufferTimeout(Flux<? extends O> source, Duration ttl, Scheduler ttlScheduler, int bufferSize, Consumer<? super O> onBufferEviction) {
        super(source);
        this.ttl = ttl;
        this.ttlScheduler = ttlScheduler;
        this.bufferSize = bufferSize;
        this.onBufferEviction = onBufferEviction;
    }

    @Override
    public CoreSubscriber<? super O> subscribeOrReturn(CoreSubscriber<? super O> actual) {
        return new BackpressureBufferTimeoutSubscriber<O>(actual, this.ttl, this.ttlScheduler, this.bufferSize, this.onBufferEviction);
    }

    @Override
    public int getPrefetch() {
        return Integer.MAX_VALUE;
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_ON) {
            return this.ttlScheduler;
        }
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.ASYNC;
        }
        return super.scanUnsafe(key);
    }

    static final class BackpressureBufferTimeoutSubscriber<T>
    extends ArrayDeque<Object>
    implements InnerOperator<T, T>,
    Runnable {
        final CoreSubscriber<? super T> actual;
        final Context ctx;
        final Duration ttl;
        final Scheduler ttlScheduler;
        final Scheduler.Worker worker;
        final int bufferSizeDouble;
        final Consumer<? super T> onBufferEviction;
        Subscription s;
        volatile boolean cancelled;
        volatile boolean done;
        Throwable error;
        volatile int wip;
        static final AtomicIntegerFieldUpdater<BackpressureBufferTimeoutSubscriber> WIP = AtomicIntegerFieldUpdater.newUpdater(BackpressureBufferTimeoutSubscriber.class, "wip");
        volatile long requested;
        static final AtomicLongFieldUpdater<BackpressureBufferTimeoutSubscriber> REQUESTED = AtomicLongFieldUpdater.newUpdater(BackpressureBufferTimeoutSubscriber.class, "requested");

        BackpressureBufferTimeoutSubscriber(CoreSubscriber<? super T> actual, Duration ttl, Scheduler ttlScheduler, int bufferSize, Consumer<? super T> onBufferEviction) {
            this.actual = actual;
            this.ctx = actual.currentContext();
            this.onBufferEviction = Objects.requireNonNull(onBufferEviction, "buffer eviction callback must not be null");
            this.bufferSizeDouble = bufferSize << 1;
            this.ttl = ttl;
            this.ttlScheduler = Objects.requireNonNull(ttlScheduler, "ttl Scheduler must not be null");
            this.worker = ttlScheduler.createWorker();
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.PARENT) {
                return this.s;
            }
            if (key == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM) {
                return this.requested;
            }
            if (key == Scannable.Attr.TERMINATED) {
                return this.done && this.isEmpty();
            }
            if (key == Scannable.Attr.CANCELLED) {
                return this.cancelled;
            }
            if (key == Scannable.Attr.BUFFERED) {
                return this.size();
            }
            if (key == Scannable.Attr.ERROR) {
                return this.error;
            }
            if (key == Scannable.Attr.PREFETCH) {
                return Integer.MAX_VALUE;
            }
            if (key == Scannable.Attr.DELAY_ERROR) {
                return false;
            }
            if (key == Scannable.Attr.RUN_ON) {
                return this.ttlScheduler;
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

        public void request(long n) {
            if (Operators.validate(n)) {
                Operators.addCap(REQUESTED, this, n);
                this.drain();
            }
        }

        public void cancel() {
            this.cancelled = true;
            this.s.cancel();
            this.worker.dispose();
            if (WIP.getAndIncrement(this) == 0) {
                this.clearQueue();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        void clearQueue() {
            while (true) {
                Object evicted;
                BackpressureBufferTimeoutSubscriber backpressureBufferTimeoutSubscriber = this;
                synchronized (backpressureBufferTimeoutSubscriber) {
                    if (this.isEmpty()) {
                        break;
                    }
                    this.poll();
                    evicted = this.poll();
                }
                this.evict(evicted);
            }
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (Operators.validate(this.s, s)) {
                this.s = s;
                this.actual.onSubscribe(this);
                s.request(Long.MAX_VALUE);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public void onNext(T t) {
            T evicted = null;
            BackpressureBufferTimeoutSubscriber backpressureBufferTimeoutSubscriber = this;
            synchronized (backpressureBufferTimeoutSubscriber) {
                if (this.size() == this.bufferSizeDouble) {
                    this.poll();
                    evicted = (T)this.poll();
                }
                this.offer(this.ttlScheduler.now(TimeUnit.NANOSECONDS));
                this.offer(t);
            }
            this.evict(evicted);
            try {
                this.worker.schedule(this, this.ttl.toNanos(), TimeUnit.NANOSECONDS);
            }
            catch (RejectedExecutionException re) {
                this.done = true;
                this.error = Operators.onRejectedExecution(re, this, null, t, this.actual.currentContext());
            }
            this.drain();
        }

        public void onError(Throwable t) {
            this.error = t;
            this.done = true;
            this.drain();
        }

        public void onComplete() {
            this.done = true;
            this.drain();
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void run() {
            while (!this.cancelled) {
                boolean empty;
                boolean d = this.done;
                T evicted = null;
                BackpressureBufferTimeoutSubscriber backpressureBufferTimeoutSubscriber = this;
                synchronized (backpressureBufferTimeoutSubscriber) {
                    Long ts = (Long)this.peek();
                    boolean bl = empty = ts == null;
                    if (!empty) {
                        if (ts <= this.ttlScheduler.now(TimeUnit.NANOSECONDS) - this.ttl.toNanos()) {
                            this.poll();
                            evicted = (T)this.poll();
                        } else {
                            break;
                        }
                    }
                }
                this.evict(evicted);
                if (!empty) continue;
                if (!d) break;
                this.drain();
                break;
            }
        }

        void evict(@Nullable T evicted) {
            if (evicted != null) {
                try {
                    this.onBufferEviction.accept(evicted);
                }
                catch (Throwable ex) {
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("value [{}] couldn't be evicted due to a callback error. This error will be dropped: {}", evicted, ex);
                    }
                    Operators.onErrorDropped(ex, this.actual.currentContext());
                }
                Operators.onDiscard(evicted, this.actual.currentContext());
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        void drain() {
            if (WIP.getAndIncrement(this) != 0) {
                return;
            }
            int missed = 1;
            do {
                boolean d;
                long e;
                long r = this.requested;
                for (e = 0L; e != r; ++e) {
                    boolean empty2;
                    Object v;
                    if (this.cancelled) {
                        this.clearQueue();
                        return;
                    }
                    d = this.done;
                    BackpressureBufferTimeoutSubscriber backpressureBufferTimeoutSubscriber = this;
                    synchronized (backpressureBufferTimeoutSubscriber) {
                        v = this.poll() != null ? this.poll() : null;
                    }
                    boolean bl = empty2 = v == null;
                    if (d && empty2) {
                        Throwable ex = this.error;
                        if (ex != null) {
                            this.actual.onError(ex);
                        } else {
                            this.actual.onComplete();
                        }
                        this.worker.dispose();
                        return;
                    }
                    if (empty2) break;
                    this.actual.onNext(v);
                }
                if (e == r) {
                    boolean empty;
                    if (this.cancelled) {
                        this.clearQueue();
                        return;
                    }
                    d = this.done;
                    BackpressureBufferTimeoutSubscriber empty2 = this;
                    synchronized (empty2) {
                        empty = this.isEmpty();
                    }
                    if (d && empty) {
                        Throwable ex = this.error;
                        if (ex != null) {
                            this.actual.onError(ex);
                        } else {
                            this.actual.onComplete();
                        }
                        this.worker.dispose();
                        return;
                    }
                }
                if (e == 0L || r == Long.MAX_VALUE) continue;
                REQUESTED.addAndGet(this, -e);
            } while ((missed = WIP.addAndGet(this, -missed)) != 0);
        }
    }
}

