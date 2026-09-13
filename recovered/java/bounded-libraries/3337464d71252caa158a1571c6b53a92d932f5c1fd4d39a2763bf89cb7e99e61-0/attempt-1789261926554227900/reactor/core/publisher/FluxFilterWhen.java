/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Publisher
 *  org.reactivestreams.Subscriber
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.Function;
import java.util.stream.Stream;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.InnerConsumer;
import reactor.core.publisher.InnerOperator;
import reactor.core.publisher.InternalFluxOperator;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;
import reactor.util.concurrent.Queues;
import reactor.util.context.Context;

class FluxFilterWhen<T>
extends InternalFluxOperator<T, T> {
    final Function<? super T, ? extends Publisher<Boolean>> asyncPredicate;
    final int bufferSize;

    FluxFilterWhen(Flux<T> source, Function<? super T, ? extends Publisher<Boolean>> asyncPredicate, int bufferSize) {
        super(source);
        this.asyncPredicate = asyncPredicate;
        this.bufferSize = bufferSize;
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> actual) {
        return new FluxFilterWhenSubscriber<T>(actual, this.asyncPredicate, this.bufferSize);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static final class FilterWhenInner
    implements InnerConsumer<Boolean> {
        final FluxFilterWhenSubscriber<?> parent;
        final boolean cancelOnNext;
        boolean done;
        volatile Subscription sub;
        static final AtomicReferenceFieldUpdater<FilterWhenInner, Subscription> SUB = AtomicReferenceFieldUpdater.newUpdater(FilterWhenInner.class, Subscription.class, "sub");

        FilterWhenInner(FluxFilterWhenSubscriber<?> parent, boolean cancelOnNext) {
            this.parent = parent;
            this.cancelOnNext = cancelOnNext;
        }

        @Override
        public Context currentContext() {
            return this.parent.currentContext();
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (Operators.setOnce(SUB, this, s)) {
                s.request(Long.MAX_VALUE);
            }
        }

        public void onNext(Boolean t) {
            if (!this.done) {
                if (this.cancelOnNext) {
                    this.sub.cancel();
                }
                this.done = true;
                this.parent.innerResult(t);
            }
        }

        public void onError(Throwable t) {
            if (!this.done) {
                this.done = true;
                this.parent.innerError(t);
            } else {
                Operators.onErrorDropped(t, this.parent.currentContext());
            }
        }

        public void onComplete() {
            if (!this.done) {
                this.done = true;
                this.parent.innerComplete();
            }
        }

        void cancel() {
            Operators.terminate(SUB, this);
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.PARENT) {
                return this.parent;
            }
            if (key == Scannable.Attr.ACTUAL) {
                return this.sub;
            }
            if (key == Scannable.Attr.CANCELLED) {
                return this.sub == Operators.cancelledSubscription();
            }
            if (key == Scannable.Attr.TERMINATED) {
                return this.done;
            }
            if (key == Scannable.Attr.PREFETCH) {
                return Integer.MAX_VALUE;
            }
            if (key == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM) {
                return this.done ? 0L : 1L;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return null;
        }
    }

    static final class FluxFilterWhenSubscriber<T>
    implements InnerOperator<T, T> {
        final Function<? super T, ? extends Publisher<Boolean>> asyncPredicate;
        final int bufferSize;
        final AtomicReferenceArray<T> toFilter;
        final CoreSubscriber<? super T> actual;
        final Context ctx;
        int consumed;
        long consumerIndex;
        long emitted;
        Boolean innerResult;
        long producerIndex;
        Subscription upstream;
        volatile boolean cancelled;
        volatile FilterWhenInner current;
        volatile boolean done;
        volatile Throwable error;
        volatile long requested;
        volatile int state;
        volatile int wip;
        static final AtomicReferenceFieldUpdater<FluxFilterWhenSubscriber, Throwable> ERROR = AtomicReferenceFieldUpdater.newUpdater(FluxFilterWhenSubscriber.class, Throwable.class, "error");
        static final AtomicLongFieldUpdater<FluxFilterWhenSubscriber> REQUESTED = AtomicLongFieldUpdater.newUpdater(FluxFilterWhenSubscriber.class, "requested");
        static final AtomicIntegerFieldUpdater<FluxFilterWhenSubscriber> WIP = AtomicIntegerFieldUpdater.newUpdater(FluxFilterWhenSubscriber.class, "wip");
        static final AtomicReferenceFieldUpdater<FluxFilterWhenSubscriber, FilterWhenInner> CURRENT = AtomicReferenceFieldUpdater.newUpdater(FluxFilterWhenSubscriber.class, FilterWhenInner.class, "current");
        static final FilterWhenInner INNER_CANCELLED = new FilterWhenInner(null, false);
        static final int STATE_FRESH = 0;
        static final int STATE_RUNNING = 1;
        static final int STATE_RESULT = 2;

        FluxFilterWhenSubscriber(CoreSubscriber<? super T> actual, Function<? super T, ? extends Publisher<Boolean>> asyncPredicate, int bufferSize) {
            this.actual = actual;
            this.ctx = actual.currentContext();
            this.toFilter = new AtomicReferenceArray(Queues.ceilingNextPowerOfTwo(bufferSize));
            this.asyncPredicate = asyncPredicate;
            this.bufferSize = bufferSize;
        }

        @Override
        public final CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        public void onNext(T t) {
            long pi = this.producerIndex;
            int m = this.toFilter.length() - 1;
            int offset = (int)pi & m;
            this.toFilter.lazySet(offset, t);
            this.producerIndex = pi + 1L;
            this.drain();
        }

        public void onError(Throwable t) {
            ERROR.set(this, t);
            this.done = true;
            this.drain();
        }

        public void onComplete() {
            this.done = true;
            this.drain();
        }

        public void request(long n) {
            if (Operators.validate(n)) {
                Operators.addCap(REQUESTED, this, n);
                this.drain();
            }
        }

        public void cancel() {
            if (!this.cancelled) {
                this.cancelled = true;
                this.upstream.cancel();
                this.cancelInner();
                if (WIP.getAndIncrement(this) == 0) {
                    this.clear();
                }
            }
        }

        void cancelInner() {
            FilterWhenInner a = CURRENT.get(this);
            if (a != INNER_CANCELLED && (a = CURRENT.getAndSet(this, INNER_CANCELLED)) != null && a != INNER_CANCELLED) {
                a.cancel();
            }
        }

        void clear() {
            int n = this.toFilter.length();
            for (int i = 0; i < n; ++i) {
                Object old = this.toFilter.getAndSet(i, null);
                Operators.onDiscard(old, this.ctx);
            }
            this.innerResult = null;
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (Operators.validate(this.upstream, s)) {
                this.upstream = s;
                this.actual.onSubscribe(this);
                s.request((long)this.bufferSize);
            }
        }

        void drain() {
            if (WIP.getAndIncrement(this) != 0) {
                return;
            }
            int missed = 1;
            int limit = Operators.unboundedOrLimit(this.bufferSize);
            long e = this.emitted;
            long ci = this.consumerIndex;
            int f = this.consumed;
            int m = this.toFilter.length() - 1;
            CoreSubscriber<? super T> a = this.actual;
            while (true) {
                int w;
                boolean empty;
                T t;
                int offset;
                boolean d;
                long r = this.requested;
                while (e != r) {
                    if (this.cancelled) {
                        this.clear();
                        return;
                    }
                    d = this.done;
                    offset = (int)ci & m;
                    t = this.toFilter.get(offset);
                    boolean bl = empty = t == null;
                    if (d && empty) {
                        Throwable ex = Exceptions.terminate(ERROR, this);
                        if (ex == null) {
                            a.onComplete();
                        } else {
                            a.onError(ex);
                        }
                        return;
                    }
                    if (empty) break;
                    int s = this.state;
                    if (s == 0) {
                        Publisher<Boolean> p;
                        try {
                            p = Objects.requireNonNull(this.asyncPredicate.apply(t), "The asyncPredicate returned a null value");
                        }
                        catch (Throwable ex) {
                            Exceptions.throwIfFatal(ex);
                            Exceptions.addThrowable(ERROR, this, ex);
                            p = null;
                        }
                        if (p != null) {
                            if (p instanceof Callable) {
                                Boolean u;
                                try {
                                    u = (Boolean)((Callable)p).call();
                                }
                                catch (Throwable ex) {
                                    Exceptions.throwIfFatal(ex);
                                    Exceptions.addThrowable(ERROR, this, ex);
                                    u = null;
                                }
                                if (u != null && u.booleanValue()) {
                                    a.onNext(t);
                                    ++e;
                                } else {
                                    Operators.onDiscard(t, this.ctx);
                                }
                            } else {
                                FilterWhenInner inner = new FilterWhenInner(this, !(p instanceof Mono));
                                if (CURRENT.compareAndSet(this, null, inner)) {
                                    this.state = 1;
                                    p.subscribe((Subscriber)inner);
                                    break;
                                }
                            }
                        }
                        Object old = this.toFilter.getAndSet(offset, null);
                        Operators.onDiscard(old, this.ctx);
                        ++ci;
                        if (++f != limit) continue;
                        f = 0;
                        this.upstream.request((long)limit);
                        continue;
                    }
                    if (s != 2) break;
                    Boolean u = this.innerResult;
                    this.innerResult = null;
                    if (u != null && u.booleanValue()) {
                        a.onNext(t);
                        ++e;
                    } else {
                        Operators.onDiscard(t, this.ctx);
                    }
                    this.toFilter.lazySet(offset, null);
                    ++ci;
                    if (++f == limit) {
                        f = 0;
                        this.upstream.request((long)limit);
                    }
                    this.state = 0;
                }
                if (e == r) {
                    if (this.cancelled) {
                        this.clear();
                        return;
                    }
                    d = this.done;
                    offset = (int)ci & m;
                    t = this.toFilter.get(offset);
                    boolean bl = empty = t == null;
                    if (d && empty) {
                        Throwable ex = Exceptions.terminate(ERROR, this);
                        if (ex == null) {
                            a.onComplete();
                        } else {
                            a.onError(ex);
                        }
                        return;
                    }
                }
                if (missed == (w = this.wip)) {
                    this.consumed = f;
                    this.consumerIndex = ci;
                    this.emitted = e;
                    if ((missed = WIP.addAndGet(this, -missed)) != 0) continue;
                    break;
                }
                missed = w;
            }
        }

        void clearCurrent() {
            FilterWhenInner c = this.current;
            if (c != INNER_CANCELLED) {
                CURRENT.compareAndSet(this, c, null);
            }
        }

        void innerResult(Boolean item) {
            this.innerResult = item;
            this.state = 2;
            this.clearCurrent();
            this.drain();
        }

        void innerError(Throwable ex) {
            Exceptions.addThrowable(ERROR, this, ex);
            this.state = 2;
            this.clearCurrent();
            this.drain();
        }

        void innerComplete() {
            this.state = 2;
            this.clearCurrent();
            this.drain();
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.PARENT) {
                return this.upstream;
            }
            if (key == Scannable.Attr.TERMINATED) {
                return this.done;
            }
            if (key == Scannable.Attr.CANCELLED) {
                return this.cancelled;
            }
            if (key == Scannable.Attr.ERROR) {
                return this.error;
            }
            if (key == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM) {
                return this.requested;
            }
            if (key == Scannable.Attr.CAPACITY) {
                return this.toFilter.length();
            }
            if (key == Scannable.Attr.LARGE_BUFFERED) {
                return this.producerIndex - this.consumerIndex;
            }
            if (key == Scannable.Attr.BUFFERED) {
                long realBuffered = this.producerIndex - this.consumerIndex;
                if (realBuffered <= Integer.MAX_VALUE) {
                    return (int)realBuffered;
                }
                return Integer.MIN_VALUE;
            }
            if (key == Scannable.Attr.PREFETCH) {
                return this.bufferSize;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return InnerOperator.super.scanUnsafe(key);
        }

        @Override
        public Stream<? extends Scannable> inners() {
            FilterWhenInner c = this.current;
            return c == null ? Stream.empty() : Stream.of(c);
        }
    }
}

