/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Publisher
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.stream.Stream;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.InnerOperator;
import reactor.core.publisher.Operators;
import reactor.core.publisher.SourceProducer;
import reactor.util.annotation.Nullable;

final class FluxFirstWithValue<T>
extends Flux<T>
implements SourceProducer<T> {
    final Publisher<? extends T>[] array;
    final Iterable<? extends Publisher<? extends T>> iterable;

    private FluxFirstWithValue(Publisher<? extends T>[] array) {
        this.array = Objects.requireNonNull(array, "array");
        this.iterable = null;
    }

    @SafeVarargs
    FluxFirstWithValue(Publisher<? extends T> first, Publisher<? extends T> ... others) {
        Objects.requireNonNull(first, "first");
        Objects.requireNonNull(others, "others");
        Publisher[] newArray = new Publisher[others.length + 1];
        newArray[0] = first;
        System.arraycopy(others, 0, newArray, 1, others.length);
        this.array = newArray;
        this.iterable = null;
    }

    FluxFirstWithValue(Iterable<? extends Publisher<? extends T>> iterable) {
        this.array = null;
        this.iterable = Objects.requireNonNull(iterable);
    }

    @SafeVarargs
    @Nullable
    final FluxFirstWithValue<T> firstValuedAdditionalSources(Publisher<? extends T> ... others) {
        Objects.requireNonNull(others, "others");
        if (others.length == 0) {
            return this;
        }
        if (this.array == null) {
            return null;
        }
        int currentSize = this.array.length;
        int otherSize = others.length;
        Publisher[] newArray = new Publisher[currentSize + otherSize];
        System.arraycopy(this.array, 0, newArray, 0, currentSize);
        System.arraycopy(others, 0, newArray, currentSize, otherSize);
        return new FluxFirstWithValue<T>(newArray);
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
        RaceValuesCoordinator<? extends T> coordinator = new RaceValuesCoordinator<T>(n);
        coordinator.subscribe(a, n, actual);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return null;
    }

    static final class FirstValuesEmittingSubscriber<T>
    extends Operators.DeferredSubscription
    implements InnerOperator<T, T> {
        final RaceValuesCoordinator<T> parent;
        final CoreSubscriber<? super T> actual;
        final int index;
        boolean won;

        FirstValuesEmittingSubscriber(CoreSubscriber<? super T> actual, RaceValuesCoordinator<T> parent, int index) {
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
            } else {
                this.recordTerminalSignals(t);
            }
        }

        public void onComplete() {
            if (this.won) {
                this.actual.onComplete();
            } else {
                this.recordTerminalSignals(new NoSuchElementException("source at index " + this.index + " completed empty"));
            }
        }

        void recordTerminalSignals(Throwable t) {
            this.parent.errorsOrCompleteEmpty[this.index] = t;
            int nb = RaceValuesCoordinator.ERRORS_OR_COMPLETED_EMPTY.incrementAndGet(this.parent);
            if (nb == this.parent.subscribers.length) {
                NoSuchElementException e = new NoSuchElementException("All sources completed with error or without values");
                e.initCause(Exceptions.multiple(this.parent.errorsOrCompleteEmpty));
                this.actual.onError(e);
            }
        }
    }

    static final class RaceValuesCoordinator<T>
    implements Subscription,
    Scannable {
        final FirstValuesEmittingSubscriber<T>[] subscribers;
        final Throwable[] errorsOrCompleteEmpty;
        volatile boolean cancelled;
        volatile int winner;
        static final AtomicIntegerFieldUpdater<RaceValuesCoordinator> WINNER = AtomicIntegerFieldUpdater.newUpdater(RaceValuesCoordinator.class, "winner");
        volatile int nbErrorsOrCompletedEmpty;
        static final AtomicIntegerFieldUpdater<RaceValuesCoordinator> ERRORS_OR_COMPLETED_EMPTY = AtomicIntegerFieldUpdater.newUpdater(RaceValuesCoordinator.class, "nbErrorsOrCompletedEmpty");

        public RaceValuesCoordinator(int n) {
            this.subscribers = new FirstValuesEmittingSubscriber[n];
            this.errorsOrCompleteEmpty = new Throwable[n];
            this.winner = Integer.MIN_VALUE;
        }

        @Override
        public Stream<? extends Scannable> inners() {
            return Stream.of(this.subscribers);
        }

        @Override
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.CANCELLED) {
                return this.cancelled;
            }
            return null;
        }

        void subscribe(Publisher<? extends T>[] sources, int n, CoreSubscriber<? super T> actual) {
            int i;
            for (i = 0; i < n; ++i) {
                this.subscribers[i] = new FirstValuesEmittingSubscriber<T>(actual, this, i);
            }
            actual.onSubscribe(this);
            for (i = 0; i < n; ++i) {
                if (this.cancelled || this.winner != Integer.MIN_VALUE) {
                    return;
                }
                if (sources[i] == null) {
                    actual.onError(new NullPointerException("The " + i + " th Publisher source is null"));
                    return;
                }
                sources[i].subscribe(this.subscribers[i]);
            }
        }

        public void request(long n) {
            if (Operators.validate(n)) {
                int w = this.winner;
                if (w >= 0) {
                    this.subscribers[w].request(n);
                } else {
                    for (FirstValuesEmittingSubscriber<T> s : this.subscribers) {
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
                for (FirstValuesEmittingSubscriber<T> s : this.subscribers) {
                    s.cancel();
                }
            }
        }

        boolean tryWin(int index) {
            if (this.winner == Integer.MIN_VALUE && WINNER.compareAndSet(this, Integer.MIN_VALUE, index)) {
                for (int i = 0; i < this.subscribers.length; ++i) {
                    if (i == index) continue;
                    this.subscribers[i].cancel();
                    this.errorsOrCompleteEmpty[i] = null;
                }
                return true;
            }
            return false;
        }
    }
}

