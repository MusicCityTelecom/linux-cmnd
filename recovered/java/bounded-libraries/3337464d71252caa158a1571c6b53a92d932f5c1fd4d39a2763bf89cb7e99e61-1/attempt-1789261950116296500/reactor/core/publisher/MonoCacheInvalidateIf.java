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
import java.util.function.Predicate;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.InnerConsumer;
import reactor.core.publisher.InternalMonoOperator;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

final class MonoCacheInvalidateIf<T>
extends InternalMonoOperator<T, T> {
    static final State<?> EMPTY_STATE = new State<Object>(){

        @Override
        @Nullable
        public Object get() {
            return null;
        }

        @Override
        public void clear() {
        }
    };
    final Predicate<? super T> shouldInvalidatePredicate;
    volatile State<T> state;
    static final AtomicReferenceFieldUpdater<MonoCacheInvalidateIf, State> STATE = AtomicReferenceFieldUpdater.newUpdater(MonoCacheInvalidateIf.class, State.class, "state");

    MonoCacheInvalidateIf(Mono<T> source, Predicate<? super T> invalidationPredicate) {
        super(source);
        this.shouldInvalidatePredicate = Objects.requireNonNull(invalidationPredicate, "invalidationPredicate");
        State<?> state = EMPTY_STATE;
        this.state = state;
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> actual) {
        State<T> state;
        CacheMonoSubscriber<T> inner = new CacheMonoSubscriber<T>(actual);
        while (true) {
            if ((state = this.state) == EMPTY_STATE || state instanceof CoordinatorSubscriber) {
                CoordinatorSubscriber<T> coordinator;
                boolean connectToUpstream = false;
                if (state == EMPTY_STATE) {
                    coordinator = new CoordinatorSubscriber<T>(this, this.source);
                    if (!STATE.compareAndSet(this, EMPTY_STATE, coordinator)) continue;
                    connectToUpstream = true;
                } else {
                    coordinator = (CoordinatorSubscriber<T>)state;
                }
                if (!coordinator.add(inner)) continue;
                if (inner.isCancelled()) {
                    coordinator.remove(inner);
                } else {
                    inner.coordinator = coordinator;
                    actual.onSubscribe(inner);
                }
                if (connectToUpstream) {
                    coordinator.delayedSubscribe();
                }
                return null;
            }
            T cached = state.get();
            try {
                boolean invalidated = this.shouldInvalidatePredicate.test(cached);
                if (!invalidated) break;
                if (!STATE.compareAndSet(this, state, EMPTY_STATE)) continue;
                Operators.onDiscard(cached, actual.currentContext());
            }
            catch (Throwable error) {
                if (!STATE.compareAndSet(this, state, EMPTY_STATE)) break;
                Operators.onDiscard(cached, actual.currentContext());
                Operators.error(actual, error);
                return null;
            }
        }
        actual.onSubscribe(inner);
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
    State<T> {
        final MonoCacheInvalidateIf<T> main;
        final Mono<? extends T> source;
        boolean done = false;
        volatile Subscription upstream;
        static final AtomicReferenceFieldUpdater<CoordinatorSubscriber, Subscription> UPSTREAM = AtomicReferenceFieldUpdater.newUpdater(CoordinatorSubscriber.class, Subscription.class, "upstream");
        volatile CacheMonoSubscriber<T>[] subscribers;
        static final AtomicReferenceFieldUpdater<CoordinatorSubscriber, CacheMonoSubscriber[]> SUBSCRIBERS = AtomicReferenceFieldUpdater.newUpdater(CoordinatorSubscriber.class, CacheMonoSubscriber[].class, "subscribers");
        private static final CacheMonoSubscriber[] COORDINATOR_DONE = new CacheMonoSubscriber[0];
        private static final CacheMonoSubscriber[] COORDINATOR_INIT = new CacheMonoSubscriber[0];

        CoordinatorSubscriber(MonoCacheInvalidateIf<T> main, Mono<? extends T> source) {
            this.main = main;
            this.source = source;
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
                    this.upstream.cancel();
                    STATE.compareAndSet(this.main, this, EMPTY_STATE);
                    return;
                }
                CacheMonoSubscriber[] b = new CacheMonoSubscriber[n - 1];
                System.arraycopy(a, 0, b, 0, j);
                System.arraycopy(a, j + 1, b, j, n - j - 1);
                if (SUBSCRIBERS.compareAndSet(this, a, b)) break;
            }
        }

        void delayedSubscribe() {
            Subscription old = UPSTREAM.getAndSet(this, null);
            if (old != null && old != Operators.cancelledSubscription()) {
                old.cancel();
            }
            this.source.subscribe(this);
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (UPSTREAM.compareAndSet(this, null, s)) {
                s.request(Long.MAX_VALUE);
            }
        }

        public void onNext(T t) {
            if (this.main.state != this || this.done) {
                Operators.onNextDropped(t, this.currentContext());
                return;
            }
            this.done = true;
            ValueState<T> valueState = new ValueState<T>(t);
            if (STATE.compareAndSet(this.main, this, valueState)) {
                for (CacheMonoSubscriber inner : SUBSCRIBERS.getAndSet(this, COORDINATOR_DONE)) {
                    inner.complete(t);
                }
            }
        }

        public void onError(Throwable t) {
            if (this.main.state != this || this.done) {
                Operators.onErrorDropped(t, this.currentContext());
                return;
            }
            if (STATE.compareAndSet(this.main, this, EMPTY_STATE)) {
                for (CacheMonoSubscriber inner : SUBSCRIBERS.getAndSet(this, COORDINATOR_DONE)) {
                    inner.onError(t);
                }
            }
        }

        public void onComplete() {
            if (this.done) {
                this.done = false;
                return;
            }
            if (STATE.compareAndSet(this.main, this, EMPTY_STATE)) {
                for (CacheMonoSubscriber inner : SUBSCRIBERS.getAndSet(this, COORDINATOR_DONE)) {
                    inner.onError(new NoSuchElementException("cacheInvalidateWhen expects a value, source completed empty"));
                }
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

    static final class ValueState<T>
    implements State<T> {
        @Nullable
        T value;

        ValueState(T value) {
            this.value = value;
        }

        @Override
        @Nullable
        public T get() {
            return this.value;
        }

        @Override
        public void clear() {
            this.value = null;
        }
    }

    static interface State<T> {
        @Nullable
        public T get();

        public void clear();
    }
}

