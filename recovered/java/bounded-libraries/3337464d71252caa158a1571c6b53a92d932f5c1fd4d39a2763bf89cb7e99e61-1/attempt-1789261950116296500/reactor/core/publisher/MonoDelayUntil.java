/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Publisher
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.Function;
import java.util.stream.Stream;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;
import reactor.core.CorePublisher;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Scannable;
import reactor.core.publisher.InnerConsumer;
import reactor.core.publisher.InnerOperator;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Operators;
import reactor.core.publisher.OptimizableOperator;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

final class MonoDelayUntil<T>
extends Mono<T>
implements Scannable,
OptimizableOperator<T, T> {
    final Mono<T> source;
    Function<? super T, ? extends Publisher<?>>[] otherGenerators;
    @Nullable
    final OptimizableOperator<?, T> optimizableOperator;

    MonoDelayUntil(Mono<T> monoSource, Function<? super T, ? extends Publisher<?>> triggerGenerator) {
        this.source = Objects.requireNonNull(monoSource, "monoSource");
        this.otherGenerators = new Function[]{Objects.requireNonNull(triggerGenerator, "triggerGenerator")};
        this.optimizableOperator = this.source instanceof OptimizableOperator ? (OptimizableOperator)((Object)this.source) : null;
    }

    MonoDelayUntil(Mono<T> monoSource, Function<? super T, ? extends Publisher<?>>[] triggerGenerators) {
        OptimizableOperator optimSource;
        this.source = Objects.requireNonNull(monoSource, "monoSource");
        this.otherGenerators = triggerGenerators;
        this.optimizableOperator = this.source instanceof OptimizableOperator ? (optimSource = (OptimizableOperator)((Object)this.source)) : null;
    }

    MonoDelayUntil<T> copyWithNewTriggerGenerator(boolean delayError, Function<? super T, ? extends Publisher<?>> triggerGenerator) {
        Objects.requireNonNull(triggerGenerator, "triggerGenerator");
        Function<? super T, ? extends Publisher<?>>[] oldTriggers = this.otherGenerators;
        Function[] newTriggers = new Function[oldTriggers.length + 1];
        System.arraycopy(oldTriggers, 0, newTriggers, 0, oldTriggers.length);
        newTriggers[oldTriggers.length] = triggerGenerator;
        return new MonoDelayUntil<T>(this.source, newTriggers);
    }

    @Override
    public void subscribe(CoreSubscriber<? super T> actual) {
        try {
            this.source.subscribe(this.subscribeOrReturn((CoreSubscriber<? super T>)actual));
        }
        catch (Throwable e) {
            Operators.error(actual, Operators.onOperatorError(e, actual.currentContext()));
            return;
        }
    }

    @Override
    public final CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> actual) throws Throwable {
        DelayUntilCoordinator<? super T> parent = new DelayUntilCoordinator<T>(actual, this.otherGenerators);
        actual.onSubscribe(parent);
        return parent;
    }

    @Override
    public final CorePublisher<? extends T> source() {
        return this.source;
    }

    @Override
    public final OptimizableOperator<?, ? extends T> nextOptimizableSource() {
        return this.optimizableOperator;
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return null;
    }

    static boolean isTerminated(int state) {
        return state == Integer.MIN_VALUE;
    }

    static boolean hasValue(int state) {
        return (state & 8) == 8;
    }

    static boolean hasInner(int state) {
        return (state & 2) == 2;
    }

    static boolean hasRequest(int state) {
        return (state & 4) == 4;
    }

    static boolean hasSubscription(int state) {
        return (state & 1) == 1;
    }

    static final class DelayUntilTrigger<T>
    implements InnerConsumer<T> {
        final DelayUntilCoordinator<?> parent;
        Subscription s;
        boolean done;
        Throwable error;

        DelayUntilTrigger(DelayUntilCoordinator<?> parent) {
            this.parent = parent;
        }

        @Override
        public Context currentContext() {
            return this.parent.currentContext();
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.CANCELLED) {
                return MonoDelayUntil.isTerminated(this.parent.state) && !this.done;
            }
            if (key == Scannable.Attr.PARENT) {
                return this.s;
            }
            if (key == Scannable.Attr.ACTUAL) {
                return this.parent;
            }
            if (key == Scannable.Attr.ERROR) {
                return this.error;
            }
            if (key == Scannable.Attr.PREFETCH) {
                return Integer.MAX_VALUE;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return null;
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (Operators.validate(this.s, s)) {
                this.s = s;
                int previousState = this.markInnerActive();
                if (MonoDelayUntil.isTerminated(previousState)) {
                    s.cancel();
                    DelayUntilCoordinator<?> parent = this.parent;
                    Operators.onDiscard(parent.value, parent.currentContext());
                    return;
                }
                s.request(Long.MAX_VALUE);
            }
        }

        public void onNext(T t) {
            Operators.onDiscard(t, this.parent.currentContext());
        }

        public void onError(Throwable t) {
            if (this.done) {
                Operators.onErrorDropped(t, this.parent.currentContext());
                return;
            }
            DelayUntilCoordinator<?> parent = this.parent;
            this.done = true;
            parent.done = true;
            if (!Exceptions.addThrowable(DelayUntilCoordinator.ERROR, parent, t)) {
                Operators.onErrorDropped(t, parent.currentContext());
                return;
            }
            int previousState = parent.markTerminated();
            if (MonoDelayUntil.isTerminated(previousState)) {
                return;
            }
            Operators.onDiscard(parent.value, parent.currentContext());
            parent.s.cancel();
            Throwable e = Exceptions.terminate(DelayUntilCoordinator.ERROR, parent);
            parent.actual.onError(e);
        }

        public void onComplete() {
            int nextIndex;
            if (this.done) {
                return;
            }
            this.done = true;
            DelayUntilCoordinator<?> parent = this.parent;
            parent.index = nextIndex = parent.index + 1;
            if (nextIndex == parent.otherGenerators.length) {
                parent.complete();
                return;
            }
            int previousState = this.markInnerInactive();
            if (MonoDelayUntil.isTerminated(previousState)) {
                return;
            }
            this.done = false;
            this.s = null;
            parent.subscribeNextTrigger();
        }

        void cancel() {
            this.s.cancel();
        }

        int markInnerActive() {
            int state;
            DelayUntilCoordinator<?> parent = this.parent;
            do {
                if (MonoDelayUntil.isTerminated(state = parent.state)) {
                    return Integer.MIN_VALUE;
                }
                if (!MonoDelayUntil.hasInner(state)) continue;
                return state;
            } while (!DelayUntilCoordinator.STATE.compareAndSet(parent, state, state | 2));
            return state;
        }

        int markInnerInactive() {
            int state;
            DelayUntilCoordinator<?> parent = this.parent;
            do {
                if (MonoDelayUntil.isTerminated(state = parent.state)) {
                    return Integer.MIN_VALUE;
                }
                if (MonoDelayUntil.hasInner(state)) continue;
                return state;
            } while (!DelayUntilCoordinator.STATE.compareAndSet(parent, state, state & 0xFFFFFFFD));
            return state;
        }
    }

    static final class DelayUntilCoordinator<T>
    implements InnerOperator<T, T> {
        final Function<? super T, ? extends Publisher<?>>[] otherGenerators;
        final CoreSubscriber<? super T> actual;
        int index;
        T value;
        boolean done;
        Subscription s;
        DelayUntilTrigger<?> triggerSubscriber;
        volatile Throwable error;
        static final AtomicReferenceFieldUpdater<DelayUntilCoordinator, Throwable> ERROR = AtomicReferenceFieldUpdater.newUpdater(DelayUntilCoordinator.class, Throwable.class, "error");
        volatile int state;
        static final AtomicIntegerFieldUpdater<DelayUntilCoordinator> STATE = AtomicIntegerFieldUpdater.newUpdater(DelayUntilCoordinator.class, "state");
        static final int HAS_SUBSCRIPTION = 1;
        static final int HAS_INNER = 2;
        static final int HAS_REQUEST = 4;
        static final int HAS_VALUE = 8;
        static final int TERMINATED = Integer.MIN_VALUE;

        DelayUntilCoordinator(CoreSubscriber<? super T> subscriber, Function<? super T, ? extends Publisher<?>>[] otherGenerators) {
            this.actual = subscriber;
            this.otherGenerators = otherGenerators;
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (Operators.validate(this.s, s)) {
                this.s = s;
                int previousState = this.markHasSubscription();
                if (MonoDelayUntil.isTerminated(previousState)) {
                    s.cancel();
                    return;
                }
                s.request(Long.MAX_VALUE);
            }
        }

        public void onNext(T t) {
            if (this.done) {
                Operators.onDiscard(t, this.actual.currentContext());
                return;
            }
            this.value = t;
            this.subscribeNextTrigger();
        }

        public void onError(Throwable t) {
            if (this.done) {
                Operators.onErrorDropped(t, this.actual.currentContext());
                return;
            }
            this.done = true;
            if (this.value == null) {
                this.actual.onError(t);
                return;
            }
            if (!Exceptions.addThrowable(ERROR, this, t)) {
                Operators.onErrorDropped(t, this.actual.currentContext());
                return;
            }
            int previousState = this.markTerminated();
            if (MonoDelayUntil.isTerminated(previousState)) {
                return;
            }
            if (MonoDelayUntil.hasInner(previousState)) {
                Operators.onDiscard(this.value, this.actual.currentContext());
                this.triggerSubscriber.cancel();
            }
            Throwable e = Exceptions.terminate(ERROR, this);
            this.actual.onError(e);
        }

        public void onComplete() {
            if (this.done) {
                return;
            }
            if (this.value == null) {
                this.done = true;
                this.actual.onComplete();
            }
        }

        public void request(long n) {
            if (Operators.validate(n)) {
                int previousState = this.markHasRequest();
                if (MonoDelayUntil.isTerminated(previousState)) {
                    return;
                }
                if (MonoDelayUntil.hasRequest(previousState)) {
                    return;
                }
                if (MonoDelayUntil.hasValue(previousState)) {
                    this.done = true;
                    CoreSubscriber<? super T> actual = this.actual;
                    T v = this.value;
                    actual.onNext(v);
                    actual.onComplete();
                }
            }
        }

        public void cancel() {
            int previousState = this.markTerminated();
            if (MonoDelayUntil.isTerminated(previousState)) {
                return;
            }
            Throwable t = Exceptions.terminate(ERROR, this);
            if (t != null) {
                Operators.onErrorDropped(t, this.actual.currentContext());
            }
            if (MonoDelayUntil.hasSubscription(previousState)) {
                this.s.cancel();
            }
            if (MonoDelayUntil.hasInner(previousState)) {
                Operators.onDiscard(this.value, this.actual.currentContext());
                this.triggerSubscriber.cancel();
            }
        }

        void subscribeNextTrigger() {
            Publisher<?> p;
            Function<T, Publisher<?>> generator = this.otherGenerators[this.index];
            try {
                p = generator.apply(this.value);
                Objects.requireNonNull(p, "mapper returned null value");
            }
            catch (Throwable t) {
                this.onError(t);
                return;
            }
            DelayUntilTrigger<Object> triggerSubscriber = this.triggerSubscriber;
            if (triggerSubscriber == null) {
                this.triggerSubscriber = triggerSubscriber = new DelayUntilTrigger(this);
            }
            p.subscribe(triggerSubscriber);
        }

        @Override
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.CANCELLED) {
                return MonoDelayUntil.isTerminated(this.state) && !this.done;
            }
            if (key == Scannable.Attr.TERMINATED) {
                return MonoDelayUntil.isTerminated(this.state) && this.done;
            }
            if (key == Scannable.Attr.PREFETCH) {
                return Integer.MAX_VALUE;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return InnerOperator.super.scanUnsafe(key);
        }

        @Override
        public Stream<? extends Scannable> inners() {
            DelayUntilTrigger<?> subscriber = this.triggerSubscriber;
            return subscriber == null ? Stream.empty() : Stream.of(subscriber);
        }

        int markHasSubscription() {
            int state;
            do {
                if (!MonoDelayUntil.isTerminated(state = this.state)) continue;
                return Integer.MIN_VALUE;
            } while (!STATE.compareAndSet(this, state, state | 1));
            return state;
        }

        int markHasRequest() {
            int nextState;
            int state;
            do {
                if (MonoDelayUntil.isTerminated(state = this.state)) {
                    return Integer.MIN_VALUE;
                }
                if (!MonoDelayUntil.hasRequest(state)) continue;
                return state;
            } while (!STATE.compareAndSet(this, state, nextState = MonoDelayUntil.hasValue(state) ? Integer.MIN_VALUE : state | 4));
            return state;
        }

        int markTerminated() {
            int state;
            do {
                if (!MonoDelayUntil.isTerminated(state = this.state)) continue;
                return Integer.MIN_VALUE;
            } while (!STATE.compareAndSet(this, state, Integer.MIN_VALUE));
            return state;
        }

        void complete() {
            int s;
            do {
                if (MonoDelayUntil.isTerminated(s = this.state)) {
                    return;
                }
                if (!MonoDelayUntil.hasRequest(s) || !STATE.compareAndSet(this, s, Integer.MIN_VALUE)) continue;
                CoreSubscriber<? super T> actual = this.actual;
                T v = this.value;
                actual.onNext(v);
                actual.onComplete();
                return;
            } while (!STATE.compareAndSet(this, s, s | 8));
        }
    }
}

