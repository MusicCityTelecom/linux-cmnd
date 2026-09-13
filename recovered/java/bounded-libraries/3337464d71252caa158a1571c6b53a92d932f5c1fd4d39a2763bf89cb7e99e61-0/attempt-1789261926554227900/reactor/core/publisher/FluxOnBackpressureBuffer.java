/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Subscriber
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import java.util.Queue;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.function.Consumer;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.InnerOperator;
import reactor.core.publisher.InternalFluxOperator;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;
import reactor.util.concurrent.Queues;
import reactor.util.context.Context;

final class FluxOnBackpressureBuffer<O>
extends InternalFluxOperator<O, O>
implements Fuseable {
    final Consumer<? super O> onOverflow;
    final int bufferSize;
    final boolean unbounded;

    FluxOnBackpressureBuffer(Flux<? extends O> source, int bufferSize, boolean unbounded, @Nullable Consumer<? super O> onOverflow) {
        super(source);
        if (bufferSize < 1) {
            throw new IllegalArgumentException("Buffer Size must be strictly positive");
        }
        this.bufferSize = bufferSize;
        this.unbounded = unbounded;
        this.onOverflow = onOverflow;
    }

    @Override
    public CoreSubscriber<? super O> subscribeOrReturn(CoreSubscriber<? super O> actual) {
        return new BackpressureBufferSubscriber<O>(actual, this.bufferSize, this.unbounded, this.onOverflow);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    @Override
    public int getPrefetch() {
        return Integer.MAX_VALUE;
    }

    static final class BackpressureBufferSubscriber<T>
    implements Fuseable.QueueSubscription<T>,
    InnerOperator<T, T> {
        final CoreSubscriber<? super T> actual;
        final Context ctx;
        final Queue<T> queue;
        final int capacityOrSkip;
        final Consumer<? super T> onOverflow;
        Subscription s;
        volatile boolean cancelled;
        volatile boolean enabledFusion;
        volatile boolean done;
        Throwable error;
        volatile int wip;
        static final AtomicIntegerFieldUpdater<BackpressureBufferSubscriber> WIP = AtomicIntegerFieldUpdater.newUpdater(BackpressureBufferSubscriber.class, "wip");
        volatile int discardGuard;
        static final AtomicIntegerFieldUpdater<BackpressureBufferSubscriber> DISCARD_GUARD = AtomicIntegerFieldUpdater.newUpdater(BackpressureBufferSubscriber.class, "discardGuard");
        volatile long requested;
        static final AtomicLongFieldUpdater<BackpressureBufferSubscriber> REQUESTED = AtomicLongFieldUpdater.newUpdater(BackpressureBufferSubscriber.class, "requested");

        BackpressureBufferSubscriber(CoreSubscriber<? super T> actual, int bufferSize, boolean unbounded, @Nullable Consumer<? super T> onOverflow) {
            this.actual = actual;
            this.ctx = actual.currentContext();
            this.onOverflow = onOverflow;
            Queue q = unbounded ? Queues.unbounded(bufferSize).get() : Queues.get(bufferSize).get();
            this.capacityOrSkip = !unbounded && Queues.capacity(q) > bufferSize ? bufferSize : Integer.MAX_VALUE;
            this.queue = q;
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
                return this.done && this.queue.isEmpty();
            }
            if (key == Scannable.Attr.CANCELLED) {
                return this.cancelled;
            }
            if (key == Scannable.Attr.BUFFERED) {
                return this.queue.size();
            }
            if (key == Scannable.Attr.ERROR) {
                return this.error;
            }
            if (key == Scannable.Attr.PREFETCH) {
                return Integer.MAX_VALUE;
            }
            if (key == Scannable.Attr.DELAY_ERROR) {
                return true;
            }
            if (key == Scannable.Attr.CAPACITY) {
                return this.capacityOrSkip == Integer.MAX_VALUE ? Queues.capacity(this.queue) : this.capacityOrSkip;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return InnerOperator.super.scanUnsafe(key);
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (Operators.validate(this.s, s)) {
                this.s = s;
                this.actual.onSubscribe(this);
                s.request(Long.MAX_VALUE);
            }
        }

        public void onNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, this.ctx);
                return;
            }
            if (this.cancelled) {
                Operators.onDiscard(t, this.ctx);
            }
            if (this.capacityOrSkip != Integer.MAX_VALUE && this.queue.size() >= this.capacityOrSkip || !this.queue.offer(t)) {
                Throwable ex = Operators.onOperatorError(this.s, Exceptions.failWithOverflow(), t, this.ctx);
                if (this.onOverflow != null) {
                    try {
                        this.onOverflow.accept(t);
                    }
                    catch (Throwable e) {
                        Exceptions.throwIfFatal(e);
                        ex.initCause(e);
                    }
                }
                Operators.onDiscard(t, this.ctx);
                this.onError(ex);
                return;
            }
            this.drain(t);
        }

        public void onError(Throwable t) {
            if (this.done) {
                Operators.onErrorDropped(t, this.ctx);
                return;
            }
            this.error = t;
            this.done = true;
            this.drain(null);
        }

        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            this.drain(null);
        }

        void drain(@Nullable T dataSignal) {
            if (WIP.getAndIncrement(this) != 0) {
                if (dataSignal != null && this.cancelled) {
                    Operators.onDiscard(dataSignal, this.actual.currentContext());
                }
                return;
            }
            int missed = 1;
            do {
                CoreSubscriber<? super T> a;
                if ((a = this.actual) == null) continue;
                if (this.enabledFusion) {
                    this.drainFused(a);
                } else {
                    this.drainRegular(a);
                }
                return;
            } while ((missed = WIP.addAndGet(this, -missed)) != 0);
        }

        void drainRegular(Subscriber<? super T> a) {
            int missed = 1;
            Queue<T> q = this.queue;
            do {
                long e;
                long r = this.requested;
                for (e = 0L; r != e; ++e) {
                    boolean empty;
                    boolean d = this.done;
                    T t = q.poll();
                    boolean bl = empty = t == null;
                    if (this.checkTerminated(d, empty, a, t)) {
                        return;
                    }
                    if (empty) break;
                    a.onNext(t);
                }
                if (r == e && this.checkTerminated(this.done, q.isEmpty(), a, null)) {
                    return;
                }
                if (e == 0L || r == Long.MAX_VALUE) continue;
                REQUESTED.addAndGet(this, -e);
            } while ((missed = WIP.addAndGet(this, -missed)) != 0);
        }

        void drainFused(Subscriber<? super T> a) {
            int missed = 1;
            Queue<T> q = this.queue;
            do {
                if (this.cancelled) {
                    this.clear();
                    return;
                }
                boolean d = this.done;
                a.onNext(null);
                if (!d) continue;
                Throwable ex = this.error;
                if (ex != null) {
                    a.onError(ex);
                } else {
                    a.onComplete();
                }
                return;
            } while ((missed = WIP.addAndGet(this, -missed)) != 0);
        }

        public void request(long n) {
            if (Operators.validate(n)) {
                Operators.addCap(REQUESTED, this, n);
                this.drain(null);
            }
        }

        public void cancel() {
            if (!this.cancelled) {
                this.cancelled = true;
                this.s.cancel();
                if (WIP.getAndIncrement(this) == 0 && !this.enabledFusion) {
                    Operators.onDiscardQueueWithClear(this.queue, this.ctx, null);
                }
            }
        }

        @Override
        @Nullable
        public T poll() {
            return this.queue.poll();
        }

        @Override
        public int size() {
            return this.queue.size();
        }

        @Override
        public boolean isEmpty() {
            return this.queue.isEmpty();
        }

        @Override
        public void clear() {
            if (DISCARD_GUARD.getAndIncrement(this) != 0) {
                return;
            }
            int missed = 1;
            while (true) {
                Operators.onDiscardQueueWithClear(this.queue, this.ctx, null);
                int dg = this.discardGuard;
                if (missed == dg) {
                    if ((missed = DISCARD_GUARD.addAndGet(this, -missed)) != 0) continue;
                    break;
                }
                missed = dg;
            }
        }

        @Override
        public int requestFusion(int requestedMode) {
            if ((requestedMode & 2) != 0) {
                this.enabledFusion = true;
                return 2;
            }
            return 0;
        }

        @Override
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        boolean checkTerminated(boolean d, boolean empty, Subscriber<? super T> a, @Nullable T v) {
            if (this.cancelled) {
                this.s.cancel();
                Operators.onDiscard(v, this.ctx);
                Operators.onDiscardQueueWithClear(this.queue, this.ctx, null);
                return true;
            }
            if (d && empty) {
                Throwable e = this.error;
                if (e != null) {
                    a.onError(e);
                } else {
                    a.onComplete();
                }
                return true;
            }
            return false;
        }
    }
}

