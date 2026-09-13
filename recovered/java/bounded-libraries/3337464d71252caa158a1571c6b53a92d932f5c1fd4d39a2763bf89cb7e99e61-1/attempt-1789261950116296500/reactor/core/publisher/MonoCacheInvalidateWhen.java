/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.Consumer;
import java.util.function.Function;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.InnerConsumer;
import reactor.core.publisher.InternalMonoOperator;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoCacheInvalidateIf;
import reactor.core.publisher.Operators;
import reactor.util.Logger;
import reactor.util.Loggers;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

final class MonoCacheInvalidateWhen<T>
extends InternalMonoOperator<T, T> {
    private static final Logger LOGGER = Loggers.getLogger(MonoCacheInvalidateWhen.class);
    final Function<? super T, Mono<Void>> invalidationTriggerGenerator;
    @Nullable
    final Consumer<? super T> invalidateHandler;
    volatile MonoCacheInvalidateIf.State<T> state;
    static final AtomicReferenceFieldUpdater<MonoCacheInvalidateWhen, MonoCacheInvalidateIf.State> STATE = AtomicReferenceFieldUpdater.newUpdater(MonoCacheInvalidateWhen.class, MonoCacheInvalidateIf.State.class, "state");

    MonoCacheInvalidateWhen(Mono<T> source, Function<? super T, Mono<Void>> invalidationTriggerGenerator, @Nullable Consumer<? super T> invalidateHandler) {
        super(source);
        this.invalidationTriggerGenerator = Objects.requireNonNull(invalidationTriggerGenerator, "invalidationTriggerGenerator");
        this.invalidateHandler = invalidateHandler;
        MonoCacheInvalidateIf.State<?> state = MonoCacheInvalidateIf.EMPTY_STATE;
        this.state = state;
    }

    boolean compareAndInvalidate(MonoCacheInvalidateIf.State<T> expected) {
        if (STATE.compareAndSet(this, expected, MonoCacheInvalidateIf.EMPTY_STATE)) {
            if (expected instanceof MonoCacheInvalidateIf.ValueState) {
                LOGGER.trace("invalidated {}", expected.get());
                this.safeInvalidateHandler(expected.get());
            }
            return true;
        }
        return false;
    }

    void invalidate() {
        MonoCacheInvalidateIf.State<?> oldState = STATE.getAndSet(this, MonoCacheInvalidateIf.EMPTY_STATE);
        if (oldState instanceof MonoCacheInvalidateIf.ValueState) {
            LOGGER.trace("invalidated {}", oldState.get());
            this.safeInvalidateHandler(oldState.get());
        }
    }

    void safeInvalidateHandler(@Nullable T value) {
        if (value != null && this.invalidateHandler != null) {
            try {
                this.invalidateHandler.accept(value);
            }
            catch (Throwable invalidateHandlerError) {
                LOGGER.warnOrDebug(isVerbose -> isVerbose ? "Failed to apply invalidate handler on value " + value : "Failed to apply invalidate handler", invalidateHandlerError);
            }
        }
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> actual) {
        MonoCacheInvalidateIf.State<T> state;
        CacheMonoSubscriber<T> inner = new CacheMonoSubscriber<T>(actual);
        actual.onSubscribe(inner);
        while ((state = this.state) == MonoCacheInvalidateIf.EMPTY_STATE || state instanceof CoordinatorSubscriber) {
            CoordinatorSubscriber<T> coordinator;
            boolean subscribe = false;
            if (state == MonoCacheInvalidateIf.EMPTY_STATE) {
                coordinator = new CoordinatorSubscriber<T>(this);
                if (!STATE.compareAndSet(this, MonoCacheInvalidateIf.EMPTY_STATE, coordinator)) continue;
                subscribe = true;
            } else {
                coordinator = (CoordinatorSubscriber<T>)state;
            }
            if (!coordinator.add(inner)) continue;
            if (inner.isCancelled()) {
                coordinator.remove(inner);
            } else {
                inner.coordinator = coordinator;
            }
            if (subscribe) {
                this.source.subscribe(coordinator);
            }
            return null;
        }
        inner.complete(state.get());
        return null;
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static final class TriggerSubscriber
    implements InnerConsumer<Void> {
        final MonoCacheInvalidateWhen<?> main;

        TriggerSubscriber(MonoCacheInvalidateWhen<?> main) {
            this.main = main;
        }

        @Override
        public void onSubscribe(Subscription s) {
            s.request(1L);
        }

        public void onNext(Void unused) {
        }

        public void onError(Throwable t) {
            LOGGER.debug("Invalidation triggered by onError(" + t + ")");
            this.main.invalidate();
        }

        public void onComplete() {
            this.main.invalidate();
        }

        @Override
        public Context currentContext() {
            return Context.empty();
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.PARENT) {
                return this.main;
            }
            if (key == Scannable.Attr.TERMINATED) {
                return this.main.state == MonoCacheInvalidateIf.EMPTY_STATE || this.main.state instanceof CoordinatorSubscriber;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            if (key == Scannable.Attr.PREFETCH) {
                return 1;
            }
            return null;
        }
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
            if (key == Scannable.Attr.PARENT) {
                return this.coordinator.main;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return super.scanUnsafe(key);
        }
    }

    static final class CoordinatorSubscriber<T>
    implements InnerConsumer<T>,
    MonoCacheInvalidateIf.State<T> {
        final MonoCacheInvalidateWhen<T> main;
        Subscription subscription;
        volatile CacheMonoSubscriber<T>[] subscribers;
        static final AtomicReferenceFieldUpdater<CoordinatorSubscriber, CacheMonoSubscriber[]> SUBSCRIBERS = AtomicReferenceFieldUpdater.newUpdater(CoordinatorSubscriber.class, CacheMonoSubscriber[].class, "subscribers");
        private static final CacheMonoSubscriber[] COORDINATOR_DONE = new CacheMonoSubscriber[0];
        private static final CacheMonoSubscriber[] COORDINATOR_INIT = new CacheMonoSubscriber[0];

        CoordinatorSubscriber(MonoCacheInvalidateWhen<T> main) {
            this.main = main;
            this.subscribers = COORDINATOR_INIT;
        }

        @Override
        @Nullable
        public T get() {
            throw new UnsupportedOperationException("coordinator State#get shouldn't be used");
        }

        @Override
        public void clear() {
        }

        final boolean add(CacheMonoSubscriber<T> toAdd) {
            CacheMonoSubscriber[] b;
            CacheMonoSubscriber<T>[] a;
            do {
                if ((a = this.subscribers) == COORDINATOR_DONE) {
                    return false;
                }
                int n = a.length;
                b = new CacheMonoSubscriber[n + 1];
                System.arraycopy(a, 0, b, 0, n);
                b[n] = toAdd;
            } while (!SUBSCRIBERS.compareAndSet(this, a, b));
            return true;
        }

        final void remove(CacheMonoSubscriber<T> toRemove) {
            while (true) {
                CacheMonoSubscriber<T>[] a;
                if ((a = this.subscribers) == COORDINATOR_DONE || a == COORDINATOR_INIT) {
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
                    if (!SUBSCRIBERS.compareAndSet(this, a, COORDINATOR_DONE)) continue;
                    if (this.main.compareAndInvalidate(this)) {
                        this.subscription.cancel();
                    }
                    return;
                }
                CacheMonoSubscriber[] b = new CacheMonoSubscriber[n - 1];
                System.arraycopy(a, 0, b, 0, j);
                System.arraycopy(a, j + 1, b, j, n - j - 1);
                if (SUBSCRIBERS.compareAndSet(this, a, b)) break;
            }
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (Operators.validate(this.subscription, s)) {
                this.subscription = s;
                s.request(Long.MAX_VALUE);
            }
        }

        boolean cacheLoadFailure(MonoCacheInvalidateIf.State<T> expected, Throwable failure) {
            if (STATE.compareAndSet(this.main, expected, MonoCacheInvalidateIf.EMPTY_STATE)) {
                for (CacheMonoSubscriber inner : SUBSCRIBERS.getAndSet(this, COORDINATOR_DONE)) {
                    inner.onError(failure);
                }
                return true;
            }
            return false;
        }

        void cacheLoad(T value) {
            MonoCacheInvalidateIf.ValueState<T> valueState = new MonoCacheInvalidateIf.ValueState<T>(value);
            if (STATE.compareAndSet(this.main, this, valueState)) {
                Mono<Void> invalidateTrigger = null;
                try {
                    invalidateTrigger = Objects.requireNonNull(this.main.invalidationTriggerGenerator.apply(value), "invalidationTriggerGenerator produced a null trigger");
                }
                catch (Throwable generatorError) {
                    if (this.cacheLoadFailure(valueState, generatorError)) {
                        this.main.safeInvalidateHandler(value);
                    }
                    return;
                }
                for (CacheMonoSubscriber inner : SUBSCRIBERS.getAndSet(this, COORDINATOR_DONE)) {
                    inner.complete(value);
                }
                invalidateTrigger.subscribe(new TriggerSubscriber(this.main));
            }
        }

        public void onNext(T t) {
            if (this.main.state != this) {
                Operators.onNextDroppedMulticast(t, this.subscribers);
                return;
            }
            this.cacheLoad(t);
        }

        public void onError(Throwable t) {
            if (this.main.state != this) {
                Operators.onErrorDroppedMulticast(t, this.subscribers);
                return;
            }
            this.cacheLoadFailure(this, t);
        }

        public void onComplete() {
            if (this.main.state == this) {
                this.cacheLoadFailure(this, new NoSuchElementException("cacheInvalidateWhen expects a value, source completed empty"));
            }
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

