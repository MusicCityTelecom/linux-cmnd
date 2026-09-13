/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Publisher
 *  org.reactivestreams.Subscriber
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.Function;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.CorePublisher;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.InnerConsumer;
import reactor.core.publisher.InnerProducer;
import reactor.core.publisher.InternalFluxOperator;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;
import reactor.util.concurrent.Queues;
import reactor.util.context.Context;

final class FluxExpand<T>
extends InternalFluxOperator<T, T> {
    final boolean breadthFirst;
    final Function<? super T, ? extends Publisher<? extends T>> expander;
    final int capacityHint;

    FluxExpand(Flux<T> source, Function<? super T, ? extends Publisher<? extends T>> expander, boolean breadthFirst, int capacityHint) {
        super(source);
        this.expander = expander;
        this.breadthFirst = breadthFirst;
        this.capacityHint = capacityHint;
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> s) {
        if (this.breadthFirst) {
            ExpandBreathSubscriber<T> parent = new ExpandBreathSubscriber<T>(s, this.expander, this.capacityHint);
            parent.queue.offer(this.source);
            s.onSubscribe(parent);
            parent.drainQueue();
        } else {
            ExpandDepthSubscription<? super T> parent = new ExpandDepthSubscription<T>(s, this.expander, this.capacityHint);
            parent.source = this.source;
            s.onSubscribe(parent);
        }
        return null;
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static final class ExpandDepthSubscriber<T>
    implements InnerConsumer<T> {
        ExpandDepthSubscription<T> parent;
        volatile boolean done;
        volatile T value;
        volatile Subscription s;
        static final AtomicReferenceFieldUpdater<ExpandDepthSubscriber, Subscription> S = AtomicReferenceFieldUpdater.newUpdater(ExpandDepthSubscriber.class, Subscription.class, "s");

        ExpandDepthSubscriber(ExpandDepthSubscription<T> parent) {
            this.parent = parent;
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (Operators.setOnce(S, this, s)) {
                s.request(1L);
            }
        }

        public void onNext(T t) {
            if (this.s != Operators.cancelledSubscription()) {
                this.value = t;
                this.parent.innerNext();
            }
        }

        public void onError(Throwable t) {
            if (this.s != Operators.cancelledSubscription()) {
                this.parent.innerError(this, t);
            }
        }

        public void onComplete() {
            if (this.s != Operators.cancelledSubscription()) {
                this.parent.innerComplete(this);
            }
        }

        void requestOne() {
            this.s.request(1L);
        }

        void dispose() {
            Operators.terminate(S, this);
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.PARENT) {
                return this.s;
            }
            if (key == Scannable.Attr.ACTUAL) {
                return this.parent.actual;
            }
            if (key == Scannable.Attr.TERMINATED) {
                return this.done;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return null;
        }

        @Override
        public Context currentContext() {
            return this.parent.actual().currentContext();
        }
    }

    static final class ExpandDepthSubscription<T>
    implements InnerProducer<T> {
        final CoreSubscriber<? super T> actual;
        final Function<? super T, ? extends Publisher<? extends T>> expander;
        volatile Throwable error;
        static final AtomicReferenceFieldUpdater<ExpandDepthSubscription, Throwable> ERROR = AtomicReferenceFieldUpdater.newUpdater(ExpandDepthSubscription.class, Throwable.class, "error");
        volatile int active;
        static final AtomicIntegerFieldUpdater<ExpandDepthSubscription> ACTIVE = AtomicIntegerFieldUpdater.newUpdater(ExpandDepthSubscription.class, "active");
        volatile long requested;
        static final AtomicLongFieldUpdater<ExpandDepthSubscription> REQUESTED = AtomicLongFieldUpdater.newUpdater(ExpandDepthSubscription.class, "requested");
        volatile Object current;
        static final AtomicReferenceFieldUpdater<ExpandDepthSubscription, Object> CURRENT = AtomicReferenceFieldUpdater.newUpdater(ExpandDepthSubscription.class, Object.class, "current");
        volatile int wip;
        static final AtomicIntegerFieldUpdater<ExpandDepthSubscription> WIP = AtomicIntegerFieldUpdater.newUpdater(ExpandDepthSubscription.class, "wip");
        Deque<ExpandDepthSubscriber<T>> subscriptionStack;
        volatile boolean cancelled;
        CorePublisher<? extends T> source;
        long consumed;

        ExpandDepthSubscription(CoreSubscriber<? super T> actual, Function<? super T, ? extends Publisher<? extends T>> expander, int capacityHint) {
            this.actual = actual;
            this.expander = expander;
            this.subscriptionStack = new ArrayDeque<ExpandDepthSubscriber<T>>(capacityHint);
        }

        @Override
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        public void request(long n) {
            if (Operators.validate(n)) {
                Operators.addCap(REQUESTED, this, n);
                this.drainQueue();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public void cancel() {
            if (!this.cancelled) {
                Object o;
                Deque<ExpandDepthSubscriber<T>> q;
                this.cancelled = true;
                ExpandDepthSubscription expandDepthSubscription = this;
                synchronized (expandDepthSubscription) {
                    q = this.subscriptionStack;
                    this.subscriptionStack = null;
                }
                if (q != null) {
                    while (!q.isEmpty()) {
                        q.poll().dispose();
                    }
                }
                if ((o = CURRENT.getAndSet(this, this)) != this && o != null) {
                    ((ExpandDepthSubscriber)o).dispose();
                }
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Nullable
        ExpandDepthSubscriber<T> pop() {
            ExpandDepthSubscription expandDepthSubscription = this;
            synchronized (expandDepthSubscription) {
                Deque<ExpandDepthSubscriber<T>> q = this.subscriptionStack;
                return q != null ? q.pollFirst() : null;
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        boolean push(ExpandDepthSubscriber<T> subscriber) {
            ExpandDepthSubscription expandDepthSubscription = this;
            synchronized (expandDepthSubscription) {
                Deque<ExpandDepthSubscriber<T>> q = this.subscriptionStack;
                if (q != null) {
                    q.offerFirst(subscriber);
                    return true;
                }
                return false;
            }
        }

        boolean setCurrent(ExpandDepthSubscriber<T> inner) {
            Object o;
            do {
                if ((o = CURRENT.get(this)) != this) continue;
                inner.dispose();
                return false;
            } while (!CURRENT.compareAndSet(this, o, inner));
            return true;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        void drainQueue() {
            if (WIP.getAndIncrement(this) != 0) {
                return;
            }
            int missed = 1;
            CoreSubscriber<? super T> a = this.actual;
            long e = this.consumed;
            while (true) {
                int w;
                Object o = this.current;
                if (this.cancelled || o == this) {
                    this.source = null;
                    return;
                }
                ExpandDepthSubscriber<T> curr = (ExpandDepthSubscriber<T>)o;
                CorePublisher<? extends T> p = this.source;
                if (curr == null && p != null) {
                    this.source = null;
                    ACTIVE.getAndIncrement(this);
                    ExpandDepthSubscriber eds = new ExpandDepthSubscriber(this);
                    if (!this.setCurrent(eds)) return;
                    p.subscribe(eds);
                } else {
                    if (curr == null) {
                        return;
                    }
                    boolean currentDone = curr.done;
                    Object v = curr.value;
                    boolean newSource = false;
                    if (v != null && e != this.requested) {
                        curr.value = null;
                        a.onNext(v);
                        ++e;
                        try {
                            p = Objects.requireNonNull(this.expander.apply(v), "The expander returned a null Publisher");
                        }
                        catch (Throwable ex) {
                            Exceptions.throwIfFatal(ex);
                            p = null;
                            curr.dispose();
                            curr.done = true;
                            currentDone = true;
                            v = null;
                            Exceptions.addThrowable(ERROR, this, ex);
                        }
                        if (p != null && this.push(curr)) {
                            ACTIVE.getAndIncrement(this);
                            curr = new ExpandDepthSubscriber(this);
                            if (!this.setCurrent(curr)) return;
                            p.subscribe(curr);
                            newSource = true;
                        }
                    }
                    if (!newSource && currentDone && v == null) {
                        if (ACTIVE.decrementAndGet(this) == 0) {
                            Throwable ex = Exceptions.terminate(ERROR, this);
                            if (ex != null) {
                                a.onError(ex);
                                return;
                            } else {
                                a.onComplete();
                            }
                            return;
                        }
                        curr = this.pop();
                        if (curr == null || !this.setCurrent(curr)) return;
                        curr.requestOne();
                        continue;
                    }
                }
                if (missed == (w = this.wip)) {
                    this.consumed = e;
                    if ((missed = WIP.addAndGet(this, -missed)) != 0) continue;
                    return;
                }
                missed = w;
            }
        }

        void innerNext() {
            this.drainQueue();
        }

        void innerError(ExpandDepthSubscriber inner, Throwable t) {
            Exceptions.addThrowable(ERROR, this, t);
            inner.done = true;
            this.drainQueue();
        }

        void innerComplete(ExpandDepthSubscriber inner) {
            inner.done = true;
            this.drainQueue();
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.CANCELLED) {
                return this.cancelled;
            }
            if (key == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM) {
                return this.requested;
            }
            if (key == Scannable.Attr.ERROR) {
                return this.error;
            }
            return InnerProducer.super.scanUnsafe(key);
        }
    }

    static final class ExpandBreathSubscriber<T>
    extends Operators.MultiSubscriptionSubscriber<T, T> {
        final Function<? super T, ? extends Publisher<? extends T>> expander;
        final Queue<Publisher<? extends T>> queue;
        volatile boolean active;
        volatile int wip;
        static final AtomicIntegerFieldUpdater<ExpandBreathSubscriber> WIP = AtomicIntegerFieldUpdater.newUpdater(ExpandBreathSubscriber.class, "wip");
        long produced;

        ExpandBreathSubscriber(CoreSubscriber<? super T> actual, Function<? super T, ? extends Publisher<? extends T>> expander, int capacityHint) {
            super(actual);
            this.expander = expander;
            this.queue = Queues.unbounded(capacityHint).get();
        }

        @Override
        public void onSubscribe(Subscription s) {
            this.set(s);
        }

        public void onNext(T t) {
            Publisher<? extends T> p;
            ++this.produced;
            this.actual.onNext(t);
            try {
                p = Objects.requireNonNull(this.expander.apply(t), "The expander returned a null Publisher");
            }
            catch (Throwable ex) {
                Exceptions.throwIfFatal(ex);
                super.cancel();
                this.actual.onError(ex);
                this.drainQueue();
                return;
            }
            this.queue.offer(p);
        }

        @Override
        public void onError(Throwable t) {
            this.set(Operators.cancelledSubscription());
            super.cancel();
            this.actual.onError(t);
            this.drainQueue();
        }

        @Override
        public void onComplete() {
            this.active = false;
            this.drainQueue();
        }

        @Override
        public void cancel() {
            super.cancel();
            this.drainQueue();
        }

        void drainQueue() {
            if (WIP.getAndIncrement(this) == 0) {
                do {
                    Queue<Publisher<T>> q = this.queue;
                    if (this.isCancelled()) {
                        q.clear();
                        continue;
                    }
                    if (this.active) continue;
                    if (q.isEmpty()) {
                        this.set(Operators.cancelledSubscription());
                        super.cancel();
                        this.actual.onComplete();
                        continue;
                    }
                    Publisher<? extends T> p = q.poll();
                    long c = this.produced;
                    if (c != 0L) {
                        this.produced = 0L;
                        this.produced(c);
                    }
                    this.active = true;
                    p.subscribe((Subscriber)this);
                } while (WIP.decrementAndGet(this) != 0);
            }
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.BUFFERED) {
                return this.queue != null ? this.queue.size() : 0;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return super.scanUnsafe(key);
        }
    }
}

