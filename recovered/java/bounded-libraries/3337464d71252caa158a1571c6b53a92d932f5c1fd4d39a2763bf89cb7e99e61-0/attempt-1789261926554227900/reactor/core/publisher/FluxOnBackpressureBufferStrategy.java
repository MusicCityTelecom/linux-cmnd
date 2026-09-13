/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Subscriber
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import java.util.ArrayDeque;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.function.Consumer;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Scannable;
import reactor.core.publisher.BufferOverflowStrategy;
import reactor.core.publisher.Flux;
import reactor.core.publisher.InnerOperator;
import reactor.core.publisher.InternalFluxOperator;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

final class FluxOnBackpressureBufferStrategy<O>
extends InternalFluxOperator<O, O> {
    final Consumer<? super O> onBufferOverflow;
    final int bufferSize;
    final boolean delayError;
    final BufferOverflowStrategy bufferOverflowStrategy;

    FluxOnBackpressureBufferStrategy(Flux<? extends O> source, int bufferSize, @Nullable Consumer<? super O> onBufferOverflow, BufferOverflowStrategy bufferOverflowStrategy) {
        super(source);
        if (bufferSize < 1) {
            throw new IllegalArgumentException("Buffer Size must be strictly positive");
        }
        this.bufferSize = bufferSize;
        this.onBufferOverflow = onBufferOverflow;
        this.bufferOverflowStrategy = bufferOverflowStrategy;
        this.delayError = onBufferOverflow != null || bufferOverflowStrategy == BufferOverflowStrategy.ERROR;
    }

    @Override
    public CoreSubscriber<? super O> subscribeOrReturn(CoreSubscriber<? super O> actual) {
        return new BackpressureBufferDropOldestSubscriber<O>(actual, this.bufferSize, this.delayError, this.onBufferOverflow, this.bufferOverflowStrategy);
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

    static final class BackpressureBufferDropOldestSubscriber<T>
    extends ArrayDeque<T>
    implements InnerOperator<T, T> {
        final CoreSubscriber<? super T> actual;
        final Context ctx;
        final int bufferSize;
        final Consumer<? super T> onOverflow;
        final boolean delayError;
        final BufferOverflowStrategy overflowStrategy;
        Subscription s;
        volatile boolean cancelled;
        volatile boolean done;
        Throwable error;
        volatile int wip;
        static final AtomicIntegerFieldUpdater<BackpressureBufferDropOldestSubscriber> WIP = AtomicIntegerFieldUpdater.newUpdater(BackpressureBufferDropOldestSubscriber.class, "wip");
        volatile long requested;
        static final AtomicLongFieldUpdater<BackpressureBufferDropOldestSubscriber> REQUESTED = AtomicLongFieldUpdater.newUpdater(BackpressureBufferDropOldestSubscriber.class, "requested");

        BackpressureBufferDropOldestSubscriber(CoreSubscriber<? super T> actual, int bufferSize, boolean delayError, @Nullable Consumer<? super T> onOverflow, BufferOverflowStrategy overflowStrategy) {
            this.actual = actual;
            this.ctx = actual.currentContext();
            this.delayError = delayError;
            this.onOverflow = onOverflow;
            this.overflowStrategy = overflowStrategy;
            this.bufferSize = bufferSize;
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
                return this.delayError;
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

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public void onNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, this.ctx);
                return;
            }
            boolean callOnOverflow = false;
            boolean callOnError = false;
            Object overflowElement = t;
            BackpressureBufferDropOldestSubscriber backpressureBufferDropOldestSubscriber = this;
            synchronized (backpressureBufferDropOldestSubscriber) {
                if (this.size() == this.bufferSize) {
                    callOnOverflow = true;
                    switch (this.overflowStrategy) {
                        case DROP_OLDEST: {
                            overflowElement = this.pollFirst();
                            this.offer(t);
                            break;
                        }
                        case DROP_LATEST: {
                            break;
                        }
                        default: {
                            callOnError = true;
                            break;
                        }
                    }
                } else {
                    this.offer(t);
                }
            }
            if (callOnOverflow) {
                if (this.onOverflow != null) {
                    try {
                        this.onOverflow.accept(overflowElement);
                    }
                    catch (Throwable e) {
                        Throwable ex = Operators.onOperatorError(this.s, e, overflowElement, this.ctx);
                        this.onError(ex);
                        return;
                    }
                    finally {
                        Operators.onDiscard(overflowElement, this.ctx);
                    }
                } else {
                    Operators.onDiscard(overflowElement, this.ctx);
                }
            }
            if (callOnError) {
                Throwable ex = Operators.onOperatorError(this.s, Exceptions.failWithOverflow(), overflowElement, this.ctx);
                this.onError(ex);
            }
            if (!callOnError && !callOnOverflow) {
                this.drain();
            }
        }

        public void onError(Throwable t) {
            if (this.done) {
                Operators.onErrorDropped(t, this.ctx);
                return;
            }
            this.error = t;
            this.done = true;
            this.drain();
        }

        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            this.drain();
        }

        void drain() {
            if (WIP.getAndIncrement(this) != 0) {
                return;
            }
            int missed = 1;
            do {
                CoreSubscriber<? super T> a;
                if ((a = this.actual) == null) continue;
                this.innerDrain(a);
                return;
            } while ((missed = WIP.addAndGet(this, -missed)) != 0);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        void innerDrain(Subscriber<? super T> a) {
            int missed = 1;
            do {
                long e;
                long r = this.requested;
                for (e = 0L; r != e; ++e) {
                    boolean empty;
                    Object t;
                    boolean d = this.done;
                    BackpressureBufferDropOldestSubscriber backpressureBufferDropOldestSubscriber = this;
                    synchronized (backpressureBufferDropOldestSubscriber) {
                        t = this.poll();
                    }
                    boolean bl = empty = t == null;
                    if (this.checkTerminated(d, empty, a)) {
                        return;
                    }
                    if (empty) break;
                    a.onNext(t);
                }
                if (r == e) {
                    boolean empty;
                    BackpressureBufferDropOldestSubscriber backpressureBufferDropOldestSubscriber = this;
                    synchronized (backpressureBufferDropOldestSubscriber) {
                        empty = this.isEmpty();
                    }
                    if (this.checkTerminated(this.done, empty, a)) {
                        return;
                    }
                }
                if (e == 0L || r == Long.MAX_VALUE) continue;
                Operators.produced(REQUESTED, this, e);
            } while ((missed = WIP.addAndGet(this, -missed)) != 0);
        }

        public void request(long n) {
            if (Operators.validate(n)) {
                Operators.addCap(REQUESTED, this, n);
                this.drain();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public void cancel() {
            if (!this.cancelled) {
                this.cancelled = true;
                this.s.cancel();
                if (WIP.getAndIncrement(this) == 0) {
                    BackpressureBufferDropOldestSubscriber backpressureBufferDropOldestSubscriber = this;
                    synchronized (backpressureBufferDropOldestSubscriber) {
                        this.clear();
                    }
                }
            }
        }

        @Override
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        boolean checkTerminated(boolean d, boolean empty, Subscriber<? super T> a) {
            if (this.cancelled) {
                this.s.cancel();
                BackpressureBufferDropOldestSubscriber backpressureBufferDropOldestSubscriber = this;
                synchronized (backpressureBufferDropOldestSubscriber) {
                    this.clear();
                }
                return true;
            }
            if (d) {
                if (this.delayError) {
                    if (empty) {
                        Throwable e = this.error;
                        if (e != null) {
                            a.onError(e);
                        } else {
                            a.onComplete();
                        }
                        return true;
                    }
                } else {
                    Throwable e = this.error;
                    if (e != null) {
                        BackpressureBufferDropOldestSubscriber backpressureBufferDropOldestSubscriber = this;
                        synchronized (backpressureBufferDropOldestSubscriber) {
                            this.clear();
                        }
                        a.onError(e);
                        return true;
                    }
                    if (empty) {
                        a.onComplete();
                        return true;
                    }
                }
            }
            return false;
        }

        @Override
        public void clear() {
            Operators.onDiscardMultiple(this, this.ctx);
            super.clear();
        }
    }
}

