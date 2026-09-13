/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.Function;
import java.util.function.Supplier;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Scannable;
import reactor.core.publisher.ImmutableSignal;
import reactor.core.publisher.InnerConsumer;
import reactor.core.publisher.InternalMonoOperator;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Operators;
import reactor.core.publisher.Signal;
import reactor.core.publisher.SignalType;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import reactor.util.Logger;
import reactor.util.Loggers;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;
import reactor.util.context.ContextView;

class MonoCacheTime<T>
extends InternalMonoOperator<T, T>
implements Runnable {
    private static final Duration DURATION_INFINITE = Duration.ofMillis(Long.MAX_VALUE);
    private static final Logger LOGGER = Loggers.getLogger(MonoCacheTime.class);
    final Function<? super Signal<T>, Duration> ttlGenerator;
    final Scheduler clock;
    volatile Signal<T> state;
    static final AtomicReferenceFieldUpdater<MonoCacheTime, Signal> STATE = AtomicReferenceFieldUpdater.newUpdater(MonoCacheTime.class, Signal.class, "state");
    static final Signal<?> EMPTY = new ImmutableSignal<Object>(Context.empty(), SignalType.ON_NEXT, null, null, null);

    MonoCacheTime(Mono<? extends T> source, Duration ttl, Scheduler clock) {
        super(source);
        Objects.requireNonNull(ttl, "ttl must not be null");
        Objects.requireNonNull(clock, "clock must not be null");
        this.ttlGenerator = ignoredSignal -> ttl;
        this.clock = clock;
        Signal<?> state = EMPTY;
        this.state = state;
    }

    MonoCacheTime(Mono<? extends T> source) {
        this(source, (? super Signal<T> sig) -> DURATION_INFINITE, Schedulers.immediate());
    }

    MonoCacheTime(Mono<? extends T> source, Function<? super Signal<T>, Duration> ttlGenerator, Scheduler clock) {
        super(source);
        this.ttlGenerator = ttlGenerator;
        this.clock = clock;
        Signal<?> state = EMPTY;
        this.state = state;
    }

    MonoCacheTime(Mono<? extends T> source, Function<? super T, Duration> valueTtlGenerator, Function<Throwable, Duration> errorTtlGenerator, Supplier<Duration> emptyTtlGenerator, Scheduler clock) {
        super(source);
        Objects.requireNonNull(valueTtlGenerator, "valueTtlGenerator must not be null");
        Objects.requireNonNull(errorTtlGenerator, "errorTtlGenerator must not be null");
        Objects.requireNonNull(emptyTtlGenerator, "emptyTtlGenerator must not be null");
        Objects.requireNonNull(clock, "clock must not be null");
        this.ttlGenerator = sig -> {
            if (sig.isOnNext()) {
                return (Duration)valueTtlGenerator.apply((Object)sig.get());
            }
            if (sig.isOnError()) {
                return (Duration)errorTtlGenerator.apply(sig.getThrowable());
            }
            return (Duration)emptyTtlGenerator.get();
        };
        this.clock = clock;
        Signal<?> emptyState = EMPTY;
        this.state = emptyState;
    }

    @Override
    public void run() {
        LOGGER.debug("expired {}", this.state);
        Signal<?> emptyState = EMPTY;
        this.state = emptyState;
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> actual) {
        block9: {
            Signal<T> state;
            CacheMonoSubscriber<T> inner = new CacheMonoSubscriber<T>(actual);
            actual.onSubscribe(inner);
            while ((state = this.state) == EMPTY || state instanceof CoordinatorSubscriber) {
                CoordinatorSubscriber coordinator;
                boolean subscribe = false;
                if (state == EMPTY) {
                    coordinator = new CoordinatorSubscriber(this);
                    if (!STATE.compareAndSet(this, EMPTY, coordinator)) continue;
                    subscribe = true;
                } else {
                    coordinator = (CoordinatorSubscriber)state;
                }
                if (!coordinator.add(inner)) continue;
                if (inner.isCancelled()) {
                    coordinator.remove(inner);
                } else {
                    inner.coordinator = coordinator;
                }
                if (!subscribe) break block9;
                this.source.subscribe(coordinator);
                break block9;
            }
            if (state.isOnNext()) {
                inner.complete(state.get());
            } else if (state.isOnComplete()) {
                inner.onComplete();
            } else {
                inner.onError(state.getThrowable());
            }
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

    static final class CacheMonoSubscriber<T>
    extends Operators.MonoSubscriber<T, T> {
        CoordinatorSubscriber<T> coordinator;

        CacheMonoSubscriber(CoreSubscriber<? super T> actual) {
            super(actual);
        }

        @Override
        public void cancel() {
            super.cancel();
            CoordinatorSubscriber<T> coordinator = this.coordinator;
            if (coordinator != null) {
                coordinator.remove(this);
            }
        }

        @Override
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return super.scanUnsafe(key);
        }
    }

    static final class CoordinatorSubscriber<T>
    implements InnerConsumer<T>,
    Signal<T> {
        final MonoCacheTime<T> main;
        volatile Subscription subscription;
        static final AtomicReferenceFieldUpdater<CoordinatorSubscriber, Subscription> S = AtomicReferenceFieldUpdater.newUpdater(CoordinatorSubscriber.class, Subscription.class, "subscription");
        volatile Operators.MonoSubscriber<T, T>[] subscribers;
        static final AtomicReferenceFieldUpdater<CoordinatorSubscriber, Operators.MonoSubscriber[]> SUBSCRIBERS = AtomicReferenceFieldUpdater.newUpdater(CoordinatorSubscriber.class, Operators.MonoSubscriber[].class, "subscribers");
        private static final Operators.MonoSubscriber[] TERMINATED = new Operators.MonoSubscriber[0];
        private static final Operators.MonoSubscriber[] EMPTY = new Operators.MonoSubscriber[0];

        CoordinatorSubscriber(MonoCacheTime<T> main) {
            this.main = main;
            this.subscribers = EMPTY;
        }

        @Override
        public Throwable getThrowable() {
            throw new UnsupportedOperationException("illegal signal use");
        }

        @Override
        public Subscription getSubscription() {
            throw new UnsupportedOperationException("illegal signal use");
        }

        @Override
        public T get() {
            throw new UnsupportedOperationException("illegal signal use");
        }

        @Override
        public SignalType getType() {
            throw new UnsupportedOperationException("illegal signal use");
        }

        @Override
        public ContextView getContextView() {
            throw new UnsupportedOperationException("illegal signal use: getContextView");
        }

        final boolean add(Operators.MonoSubscriber<T, T> toAdd) {
            Operators.MonoSubscriber[] b;
            Operators.MonoSubscriber<T, T>[] a;
            do {
                if ((a = this.subscribers) == TERMINATED) {
                    return false;
                }
                int n = a.length;
                b = new Operators.MonoSubscriber[n + 1];
                System.arraycopy(a, 0, b, 0, n);
                b[n] = toAdd;
            } while (!SUBSCRIBERS.compareAndSet(this, a, b));
            return true;
        }

        final void remove(Operators.MonoSubscriber<T, T> toRemove) {
            Operators.MonoSubscriber[] b;
            Operators.MonoSubscriber<T, T>[] a;
            do {
                if ((a = this.subscribers) == TERMINATED || a == EMPTY) {
                    return;
                }
                int n = a.length;
                int j = -1;
                for (int i = 0; i < n; ++i) {
                    if (a[i] != toRemove) continue;
                    j = i;
                    break;
                }
                if (j < 0) {
                    return;
                }
                if (n == 1) {
                    b = EMPTY;
                    continue;
                }
                b = new Operators.MonoSubscriber[n - 1];
                System.arraycopy(a, 0, b, 0, j);
                System.arraycopy(a, j + 1, b, j, n - j - 1);
            } while (!SUBSCRIBERS.compareAndSet(this, a, b));
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (Operators.validate(this.subscription, s)) {
                this.subscription = s;
                s.request(Long.MAX_VALUE);
            }
        }

        private void signalCached(Signal<T> signal) {
            Signal<T> signalToPropagate = signal;
            if (STATE.compareAndSet(this.main, this, signal)) {
                Duration ttl;
                block12: {
                    ttl = null;
                    try {
                        ttl = this.main.ttlGenerator.apply(signal);
                    }
                    catch (Throwable generatorError) {
                        signalToPropagate = Signal.error(generatorError);
                        STATE.set(this.main, signalToPropagate);
                        if (!signal.isOnError()) break block12;
                        Exceptions.addSuppressed(generatorError, signal.getThrowable());
                    }
                }
                if (ttl != null) {
                    if (ttl.isZero()) {
                        this.main.run();
                    } else if (!ttl.equals(DURATION_INFINITE)) {
                        this.main.clock.schedule(this.main, ttl.toNanos(), TimeUnit.NANOSECONDS);
                    }
                } else {
                    if (signal.isOnNext()) {
                        Operators.onNextDropped(signal.get(), this.currentContext());
                    }
                    this.main.run();
                }
            }
            for (Operators.MonoSubscriber inner : SUBSCRIBERS.getAndSet(this, TERMINATED)) {
                if (signalToPropagate.isOnNext()) {
                    inner.complete(signalToPropagate.get());
                    continue;
                }
                if (signalToPropagate.isOnError()) {
                    inner.onError(signalToPropagate.getThrowable());
                    continue;
                }
                inner.onComplete();
            }
        }

        public void onNext(T t) {
            if (this.main.state != this) {
                Operators.onNextDroppedMulticast(t, this.subscribers);
                return;
            }
            this.signalCached(Signal.next(t));
        }

        public void onError(Throwable t) {
            if (this.main.state != this) {
                Operators.onErrorDroppedMulticast(t, this.subscribers);
                return;
            }
            this.signalCached(Signal.error(t));
        }

        public void onComplete() {
            this.signalCached(Signal.complete());
        }

        @Override
        public Context currentContext() {
            return Operators.multiSubscribersContext(this.subscribers);
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return null;
        }
    }
}

