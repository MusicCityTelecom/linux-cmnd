/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Publisher
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.stream.Stream;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.InnerOperator;
import reactor.core.publisher.Operators;
import reactor.core.publisher.SourceProducer;
import reactor.util.annotation.Nullable;

final class FluxFirstWithSignal<T>
extends Flux<T>
implements SourceProducer<T> {
    final Publisher<? extends T>[] array;
    final Iterable<? extends Publisher<? extends T>> iterable;

    @SafeVarargs
    FluxFirstWithSignal(Publisher<? extends T> ... array) {
        this.array = Objects.requireNonNull(array, "array");
        this.iterable = null;
    }

    FluxFirstWithSignal(Iterable<? extends Publisher<? extends T>> iterable) {
        this.array = null;
        this.iterable = Objects.requireNonNull(iterable);
    }

    @Override
    public void subscribe(CoreSubscriber<? super T> actual) {
        int n;
        Publisher<? extends T>[] a;
        block13: {
            a = this.array;
            if (a == null) {
                Iterator<Publisher<T>> it;
                n = 0;
                a = new Publisher[8];
                try {
                    it = Objects.requireNonNull(this.iterable.iterator(), "The iterator returned is null");
                }
                catch (Throwable e) {
                    Operators.error(actual, Operators.onOperatorError(e, actual.currentContext()));
                    return;
                }
                while (true) {
                    Publisher<? extends T> p;
                    boolean b;
                    try {
                        b = it.hasNext();
                    }
                    catch (Throwable e) {
                        Operators.error(actual, Operators.onOperatorError(e, actual.currentContext()));
                        return;
                    }
                    if (!b) break block13;
                    try {
                        p = Objects.requireNonNull(it.next(), "The Publisher returned by the iterator is null");
                    }
                    catch (Throwable e) {
                        Operators.error(actual, Operators.onOperatorError(e, actual.currentContext()));
                        return;
                    }
                    if (n == a.length) {
                        Publisher[] c = new Publisher[n + (n >> 2)];
                        System.arraycopy(a, 0, c, 0, n);
                        a = c;
                    }
                    a[n++] = p;
                }
            }
            n = a.length;
        }
        if (n == 0) {
            Operators.complete(actual);
            return;
        }
        if (n == 1) {
            Publisher<? extends T> p = a[0];
            if (p == null) {
                Operators.error(actual, new NullPointerException("The single source Publisher is null"));
            } else {
                p.subscribe(actual);
            }
            return;
        }
        RaceCoordinator<? extends T> coordinator = new RaceCoordinator<T>(n);
        coordinator.subscribe(a, n, actual);
    }

    @Nullable
    FluxFirstWithSignal<T> orAdditionalSource(Publisher<? extends T> source) {
        if (this.array != null) {
            int n = this.array.length;
            Publisher[] newArray = new Publisher[n + 1];
            System.arraycopy(this.array, 0, newArray, 0, n);
            newArray[n] = source;
            return new FluxFirstWithSignal<T>(newArray);
        }
        return null;
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return null;
    }

    static final class FirstEmittingSubscriber<T>
    extends Operators.DeferredSubscription
    implements InnerOperator<T, T> {
        final RaceCoordinator<T> parent;
        final CoreSubscriber<? super T> actual;
        final int index;
        boolean won;

        FirstEmittingSubscriber(CoreSubscriber<? super T> actual, RaceCoordinator<T> parent, int index) {
            this.actual = actual;
            this.parent = parent;
            this.index = index;
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.PARENT) {
                return this.s;
            }
            if (key == Scannable.Attr.CANCELLED) {
                return this.parent.cancelled;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return InnerOperator.super.scanUnsafe(key);
        }

        @Override
        public void onSubscribe(Subscription s) {
            this.set(s);
        }

        @Override
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        public void onNext(T t) {
            if (this.won) {
                this.actual.onNext(t);
            } else if (this.parent.tryWin(this.index)) {
                this.won = true;
                this.actual.onNext(t);
            }
        }

        public void onError(Throwable t) {
            if (this.won) {
                this.actual.onError(t);
            } else if (this.parent.tryWin(this.index)) {
                this.won = true;
                this.actual.onError(t);
            }
        }

        public void onComplete() {
            if (this.won) {
                this.actual.onComplete();
            } else if (this.parent.tryWin(this.index)) {
                this.won = true;
                this.actual.onComplete();
            }
        }
    }

    static final class RaceCoordinator<T>
    implements Subscription,
    Scannable {
        final FirstEmittingSubscriber<T>[] subscribers;
        volatile boolean cancelled;
        volatile int winner;
        static final AtomicIntegerFieldUpdater<RaceCoordinator> WINNER = AtomicIntegerFieldUpdater.newUpdater(RaceCoordinator.class, "winner");

        RaceCoordinator(int n) {
            this.subscribers = new FirstEmittingSubscriber[n];
            this.winner = Integer.MIN_VALUE;
        }

        @Override
        public Stream<? extends Scannable> inners() {
            return Stream.of(this.subscribers);
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.CANCELLED) {
                return this.cancelled;
            }
            return null;
        }

        void subscribe(Publisher<? extends T>[] sources, int n, CoreSubscriber<? super T> actual) {
            int i;
            FirstEmittingSubscriber<T>[] a = this.subscribers;
            for (i = 0; i < n; ++i) {
                a[i] = new FirstEmittingSubscriber<T>(actual, this, i);
            }
            actual.onSubscribe(this);
            for (i = 0; i < n; ++i) {
                if (this.cancelled || this.winner != Integer.MIN_VALUE) {
                    return;
                }
                Publisher<? extends T> p = sources[i];
                if (p == null) {
                    if (WINNER.compareAndSet(this, Integer.MIN_VALUE, -1)) {
                        actual.onError(new NullPointerException("The " + i + " th Publisher source is null"));
                    }
                    return;
                }
                p.subscribe(a[i]);
            }
        }

        public void request(long n) {
            if (Operators.validate(n)) {
                int w = this.winner;
                if (w >= 0) {
                    this.subscribers[w].request(n);
                } else {
                    for (FirstEmittingSubscriber<T> s : this.subscribers) {
                        s.request(n);
                    }
                }
            }
        }

        public void cancel() {
            if (this.cancelled) {
                return;
            }
            this.cancelled = true;
            int w = this.winner;
            if (w >= 0) {
                this.subscribers[w].cancel();
            } else {
                for (FirstEmittingSubscriber<T> s : this.subscribers) {
                    s.cancel();
                }
            }
        }

        boolean tryWin(int index) {
            if (this.winner == Integer.MIN_VALUE && WINNER.compareAndSet(this, Integer.MIN_VALUE, index)) {
                FirstEmittingSubscriber<T>[] a = this.subscribers;
                int n = a.length;
                for (int i = 0; i < n; ++i) {
                    if (i == index) continue;
                    a[i].cancel();
                }
                return true;
            }
            return false;
        }
    }
}

