/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Publisher
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.BiPredicate;
import java.util.stream.Stream;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Scannable;
import reactor.core.publisher.InnerConsumer;
import reactor.core.publisher.InnerProducer;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Operators;
import reactor.core.publisher.SourceProducer;
import reactor.util.annotation.Nullable;
import reactor.util.concurrent.Queues;
import reactor.util.context.Context;

final class MonoSequenceEqual<T>
extends Mono<Boolean>
implements SourceProducer<Boolean> {
    final Publisher<? extends T> first;
    final Publisher<? extends T> second;
    final BiPredicate<? super T, ? super T> comparer;
    final int prefetch;

    MonoSequenceEqual(Publisher<? extends T> first, Publisher<? extends T> second, BiPredicate<? super T, ? super T> comparer, int prefetch) {
        this.first = Objects.requireNonNull(first, "first");
        this.second = Objects.requireNonNull(second, "second");
        this.comparer = Objects.requireNonNull(comparer, "comparer");
        if (prefetch < 1) {
            throw new IllegalArgumentException("Buffer size must be strictly positive: " + prefetch);
        }
        this.prefetch = prefetch;
    }

    @Override
    public void subscribe(CoreSubscriber<? super Boolean> actual) {
        EqualCoordinator<T> ec = new EqualCoordinator<T>(actual, this.prefetch, this.first, this.second, this.comparer);
        actual.onSubscribe(ec);
        ec.subscribe();
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.PREFETCH) {
            return this.prefetch;
        }
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return null;
    }

    static final class EqualSubscriber<T>
    implements InnerConsumer<T> {
        final EqualCoordinator<T> parent;
        final Queue<T> queue;
        final int prefetch;
        volatile boolean done;
        Throwable error;
        Subscription cachedSubscription;
        volatile Subscription subscription;
        static final AtomicReferenceFieldUpdater<EqualSubscriber, Subscription> S = AtomicReferenceFieldUpdater.newUpdater(EqualSubscriber.class, Subscription.class, "subscription");

        EqualSubscriber(EqualCoordinator<T> parent, int prefetch) {
            this.parent = parent;
            this.prefetch = prefetch;
            this.queue = Queues.get(prefetch).get();
        }

        @Override
        public Context currentContext() {
            return this.parent.actual.currentContext();
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.TERMINATED) {
                return this.done;
            }
            if (key == Scannable.Attr.ACTUAL) {
                return this.parent;
            }
            if (key == Scannable.Attr.ERROR) {
                return this.error;
            }
            if (key == Scannable.Attr.CANCELLED) {
                return this.subscription == Operators.cancelledSubscription();
            }
            if (key == Scannable.Attr.PARENT) {
                return this.subscription;
            }
            if (key == Scannable.Attr.PREFETCH) {
                return this.prefetch;
            }
            if (key == Scannable.Attr.BUFFERED) {
                return this.queue.size();
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return null;
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (Operators.setOnce(S, this, s)) {
                this.cachedSubscription = s;
                s.request(Operators.unboundedOrPrefetch(this.prefetch));
            }
        }

        public void onNext(T t) {
            if (!this.queue.offer(t)) {
                this.onError(Operators.onOperatorError(this.cachedSubscription, Exceptions.failWithOverflow("Queue is full: Reactive Streams source doesn't respect backpressure"), t, this.currentContext()));
                return;
            }
            this.parent.drain();
        }

        public void onError(Throwable t) {
            this.error = t;
            this.done = true;
            this.parent.drain();
        }

        public void onComplete() {
            this.done = true;
            this.parent.drain();
        }
    }

    static final class EqualCoordinator<T>
    implements InnerProducer<Boolean> {
        final CoreSubscriber<? super Boolean> actual;
        final BiPredicate<? super T, ? super T> comparer;
        final Publisher<? extends T> first;
        final Publisher<? extends T> second;
        final EqualSubscriber<T> firstSubscriber;
        final EqualSubscriber<T> secondSubscriber;
        volatile boolean cancelled;
        volatile int once;
        static final AtomicIntegerFieldUpdater<EqualCoordinator> ONCE = AtomicIntegerFieldUpdater.newUpdater(EqualCoordinator.class, "once");
        T v1;
        T v2;
        volatile int wip;
        static final AtomicIntegerFieldUpdater<EqualCoordinator> WIP = AtomicIntegerFieldUpdater.newUpdater(EqualCoordinator.class, "wip");

        EqualCoordinator(CoreSubscriber<? super Boolean> actual, int prefetch, Publisher<? extends T> first, Publisher<? extends T> second, BiPredicate<? super T, ? super T> comparer) {
            this.actual = actual;
            this.first = first;
            this.second = second;
            this.comparer = comparer;
            this.firstSubscriber = new EqualSubscriber(this, prefetch);
            this.secondSubscriber = new EqualSubscriber(this, prefetch);
        }

        @Override
        public CoreSubscriber<? super Boolean> actual() {
            return this.actual;
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.CANCELLED) {
                return this.cancelled;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return InnerProducer.super.scanUnsafe(key);
        }

        @Override
        public Stream<? extends Scannable> inners() {
            return Stream.of(this.firstSubscriber, this.secondSubscriber);
        }

        void subscribe() {
            if (ONCE.compareAndSet(this, 0, 1)) {
                this.first.subscribe(this.firstSubscriber);
                this.second.subscribe(this.secondSubscriber);
            }
        }

        public void request(long n) {
            if (!Operators.validate(n)) {
                return;
            }
            if (ONCE.compareAndSet(this, 0, 1)) {
                this.first.subscribe(this.firstSubscriber);
                this.second.subscribe(this.secondSubscriber);
            }
        }

        public void cancel() {
            if (!this.cancelled) {
                this.cancelled = true;
                this.cancelInner(this.firstSubscriber);
                this.cancelInner(this.secondSubscriber);
                if (WIP.getAndIncrement(this) == 0) {
                    this.firstSubscriber.queue.clear();
                    this.secondSubscriber.queue.clear();
                }
            }
        }

        void cancel(EqualSubscriber<T> s1, Queue<T> q1, EqualSubscriber<T> s2, Queue<T> q2) {
            this.cancelled = true;
            this.cancelInner(s1);
            q1.clear();
            this.cancelInner(s2);
            q2.clear();
        }

        void cancelInner(EqualSubscriber<T> innerSubscriber) {
            Subscription s = innerSubscriber.subscription;
            if (s != Operators.cancelledSubscription() && (s = EqualSubscriber.S.getAndSet(innerSubscriber, Operators.cancelledSubscription())) != null && s != Operators.cancelledSubscription()) {
                s.cancel();
            }
        }

        void drain() {
            if (WIP.getAndIncrement(this) != 0) {
                return;
            }
            int missed = 1;
            EqualSubscriber<T> s1 = this.firstSubscriber;
            Queue q1 = s1.queue;
            EqualSubscriber<T> s2 = this.secondSubscriber;
            Queue q2 = s2.queue;
            do {
                boolean e2;
                boolean e1;
                long r = 0L;
                do {
                    boolean c;
                    Throwable e;
                    Throwable e3;
                    if (this.cancelled) {
                        q1.clear();
                        q2.clear();
                        return;
                    }
                    boolean d1 = s1.done;
                    if (d1 && (e3 = s1.error) != null) {
                        this.cancel(s1, q1, s2, q2);
                        this.actual.onError(e3);
                        return;
                    }
                    boolean d2 = s2.done;
                    if (d2 && (e = s2.error) != null) {
                        this.cancel(s1, q1, s2, q2);
                        this.actual.onError(e);
                        return;
                    }
                    if (this.v1 == null) {
                        this.v1 = q1.poll();
                    }
                    boolean bl = e1 = this.v1 == null;
                    if (this.v2 == null) {
                        this.v2 = q2.poll();
                    }
                    boolean bl2 = e2 = this.v2 == null;
                    if (d1 && d2 && e1 && e2) {
                        this.actual.onNext(true);
                        this.actual.onComplete();
                        return;
                    }
                    if (d1 && d2 && e1 != e2) {
                        this.cancel(s1, q1, s2, q2);
                        this.actual.onNext(false);
                        this.actual.onComplete();
                        return;
                    }
                    if (e1 || e2) continue;
                    try {
                        c = this.comparer.test(this.v1, this.v2);
                    }
                    catch (Throwable ex) {
                        Exceptions.throwIfFatal(ex);
                        this.cancel(s1, q1, s2, q2);
                        this.actual.onError(Operators.onOperatorError(ex, this.actual.currentContext()));
                        return;
                    }
                    if (!c) {
                        this.cancel(s1, q1, s2, q2);
                        this.actual.onNext(false);
                        this.actual.onComplete();
                        return;
                    }
                    ++r;
                    this.v1 = null;
                    this.v2 = null;
                } while (!e1 && !e2);
                if (r == 0L) continue;
                s1.cachedSubscription.request(r);
                s2.cachedSubscription.request(r);
            } while ((missed = WIP.addAndGet(this, -missed)) != 0);
        }
    }
}

