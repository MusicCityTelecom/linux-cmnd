/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Publisher
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.StringJoiner;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.Function;
import java.util.stream.Stream;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxFlatMap;
import reactor.core.publisher.InnerConsumer;
import reactor.core.publisher.InnerOperator;
import reactor.core.publisher.InternalFluxOperator;
import reactor.core.publisher.Operators;
import reactor.core.publisher.StateLogger;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

final class FluxSwitchMapNoPrefetch<T, R>
extends InternalFluxOperator<T, R> {
    final Function<? super T, ? extends Publisher<? extends R>> mapper;
    static int INDEX_OFFSET = 32;
    static int HAS_REQUEST_OFFSET = 4;
    static long TERMINATED = -1L;
    static long INNER_WIP_MASK = 1L;
    static long INNER_SUBSCRIBED_MASK = 2L;
    static long INNER_COMPLETED_MASK = 4L;
    static long COMPLETED_MASK = 8L;
    static long HAS_REQUEST_MASK = 0xFFFFFFF0L;
    static int MAX_HAS_REQUEST = 0xFFFFFFF;

    FluxSwitchMapNoPrefetch(Flux<? extends T> source, Function<? super T, ? extends Publisher<? extends R>> mapper) {
        super(source);
        this.mapper = Objects.requireNonNull(mapper, "mapper");
    }

    @Override
    public int getPrefetch() {
        return Integer.MAX_VALUE;
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super R> actual) {
        if (FluxFlatMap.trySubscribeScalarMap(this.source, actual, this.mapper, false, false)) {
            return null;
        }
        return new SwitchMapMain<T, R>(actual, this.mapper);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static long setTerminated(SwitchMapMain<?, ?> instance) {
        long state;
        do {
            if ((state = instance.state) != TERMINATED) continue;
            return TERMINATED;
        } while (!SwitchMapMain.STATE.compareAndSet(instance, state, TERMINATED));
        if (instance.logger != null) {
            instance.logger.log(instance.toString(), "std", state, TERMINATED);
        }
        return state;
    }

    static long setMainCompleted(SwitchMapMain<?, ?> instance) {
        long nextState;
        long state;
        do {
            if ((state = instance.state) == TERMINATED) {
                return TERMINATED;
            }
            if ((state & COMPLETED_MASK) != COMPLETED_MASK) continue;
            return state;
        } while (!SwitchMapMain.STATE.compareAndSet(instance, state, nextState = state | COMPLETED_MASK));
        if (instance.logger != null) {
            instance.logger.log(instance.toString(), "smc", state, nextState);
        }
        return state;
    }

    static long addRequest(SwitchMapMain<?, ?> instance, long previousRequested) {
        int hasRequest;
        long nextState;
        long state;
        do {
            if ((state = instance.state) == TERMINATED) {
                return TERMINATED;
            }
            hasRequest = FluxSwitchMapNoPrefetch.hasRequest(state);
            if (hasRequest != 0 || previousRequested <= 0L) continue;
            return state;
        } while (!SwitchMapMain.STATE.compareAndSet(instance, state, nextState = FluxSwitchMapNoPrefetch.state(FluxSwitchMapNoPrefetch.index(state), FluxSwitchMapNoPrefetch.isWip(state), hasRequest + 1, FluxSwitchMapNoPrefetch.isInnerSubscribed(state), FluxSwitchMapNoPrefetch.hasMainCompleted(state), FluxSwitchMapNoPrefetch.hasInnerCompleted(state))));
        if (instance.logger != null) {
            instance.logger.log(instance.toString(), "adr", state, nextState);
        }
        return nextState;
    }

    static long incrementIndex(SwitchMapMain<?, ?> instance) {
        long state = instance.state;
        if (state == TERMINATED) {
            return TERMINATED;
        }
        int nextIndex = FluxSwitchMapNoPrefetch.nextIndex(state);
        do {
            long nextState;
            if (!SwitchMapMain.STATE.compareAndSet(instance, state, nextState = FluxSwitchMapNoPrefetch.state(nextIndex, FluxSwitchMapNoPrefetch.isWip(state), FluxSwitchMapNoPrefetch.hasRequest(state), false, false, false))) continue;
            if (instance.logger != null) {
                instance.logger.log(instance.toString(), "ini", state, nextState);
            }
            return state;
        } while ((state = instance.state) != TERMINATED);
        return TERMINATED;
    }

    static long setInnerSubscribed(SwitchMapMain<?, ?> instance, int expectedIndex) {
        long nextState;
        long state;
        do {
            if ((state = instance.state) == TERMINATED) {
                return TERMINATED;
            }
            int actualIndex = FluxSwitchMapNoPrefetch.index(state);
            if (expectedIndex == actualIndex) continue;
            return state;
        } while (!SwitchMapMain.STATE.compareAndSet(instance, state, nextState = FluxSwitchMapNoPrefetch.state(expectedIndex, false, FluxSwitchMapNoPrefetch.hasRequest(state), true, FluxSwitchMapNoPrefetch.hasMainCompleted(state), false)));
        if (instance.logger != null) {
            instance.logger.log(instance.toString(), "sns", state, nextState);
        }
        return state;
    }

    static long setWip(SwitchMapMain<?, ?> instance, int expectedIndex) {
        long nextState;
        long state;
        do {
            if ((state = instance.state) == TERMINATED) {
                return TERMINATED;
            }
            int actualIndex = FluxSwitchMapNoPrefetch.index(state);
            if (expectedIndex == actualIndex) continue;
            return state;
        } while (!SwitchMapMain.STATE.compareAndSet(instance, state, nextState = FluxSwitchMapNoPrefetch.state(expectedIndex, true, FluxSwitchMapNoPrefetch.hasRequest(state), true, FluxSwitchMapNoPrefetch.hasMainCompleted(state), false)));
        if (instance.logger != null) {
            instance.logger.log(instance.toString(), "swp", state, nextState);
        }
        return state;
    }

    static long unsetWip(SwitchMapMain<?, ?> instance, int expectedIndex, boolean isDemandFulfilled, int expectedRequest) {
        int actualRequest;
        int actualIndex;
        long nextState;
        long state;
        do {
            boolean sameIndex;
            if ((state = instance.state) == TERMINATED) {
                return TERMINATED;
            }
            actualIndex = FluxSwitchMapNoPrefetch.index(state);
            actualRequest = FluxSwitchMapNoPrefetch.hasRequest(state);
            boolean bl = sameIndex = expectedIndex == actualIndex;
            if (!isDemandFulfilled || expectedRequest >= actualRequest || !sameIndex) continue;
            return state;
        } while (!SwitchMapMain.STATE.compareAndSet(instance, state, nextState = FluxSwitchMapNoPrefetch.state(actualIndex, false, isDemandFulfilled && expectedRequest == actualRequest ? 0 : actualRequest, FluxSwitchMapNoPrefetch.isInnerSubscribed(state), FluxSwitchMapNoPrefetch.hasMainCompleted(state), false)));
        if (instance.logger != null) {
            instance.logger.log(instance.toString(), "uwp", state, nextState);
        }
        return state;
    }

    static long setInnerCompleted(SwitchMapMain<?, ?> instance) {
        boolean isInnerSubscribed;
        long nextState;
        long state;
        do {
            if ((state = instance.state) == TERMINATED) {
                return TERMINATED;
            }
            isInnerSubscribed = FluxSwitchMapNoPrefetch.isInnerSubscribed(state);
        } while (!SwitchMapMain.STATE.compareAndSet(instance, state, nextState = FluxSwitchMapNoPrefetch.state(FluxSwitchMapNoPrefetch.index(state), false, FluxSwitchMapNoPrefetch.hasRequest(state), isInnerSubscribed, FluxSwitchMapNoPrefetch.hasMainCompleted(state), isInnerSubscribed)));
        if (instance.logger != null) {
            instance.logger.log(instance.toString(), "sic", state, nextState);
        }
        return state;
    }

    static long state(int index, boolean wip, int hasRequest, boolean innerSubscribed, boolean mainCompleted, boolean innerCompleted) {
        return (long)index << INDEX_OFFSET | (wip ? INNER_WIP_MASK : 0L) | (long)Math.max(Math.min(hasRequest, MAX_HAS_REQUEST), 0) << HAS_REQUEST_OFFSET | (innerSubscribed ? INNER_SUBSCRIBED_MASK : 0L) | (mainCompleted ? COMPLETED_MASK : 0L) | (innerCompleted ? INNER_COMPLETED_MASK : 0L);
    }

    static boolean isInnerSubscribed(long state) {
        return (state & INNER_SUBSCRIBED_MASK) == INNER_SUBSCRIBED_MASK;
    }

    static boolean hasMainCompleted(long state) {
        return (state & COMPLETED_MASK) == COMPLETED_MASK;
    }

    static boolean hasInnerCompleted(long state) {
        return (state & INNER_COMPLETED_MASK) == INNER_COMPLETED_MASK;
    }

    static int hasRequest(long state) {
        return (int)(state & HAS_REQUEST_MASK) >> HAS_REQUEST_OFFSET;
    }

    static int index(long state) {
        return (int)(state >>> INDEX_OFFSET);
    }

    static int nextIndex(long state) {
        return (int)(state >>> INDEX_OFFSET) + 1;
    }

    static boolean isWip(long state) {
        return (state & INNER_WIP_MASK) == INNER_WIP_MASK;
    }

    static final class SwitchMapInner<T, R>
    implements InnerConsumer<R> {
        @Nullable
        final StateLogger logger;
        final SwitchMapMain<T, R> parent;
        final CoreSubscriber<? super R> actual;
        final int index;
        Subscription s;
        long produced;
        long requested;
        boolean done;
        T nextElement;
        SwitchMapInner<T, R> nextInner;

        SwitchMapInner(SwitchMapMain<T, R> parent, CoreSubscriber<? super R> actual, int index, @Nullable StateLogger logger) {
            this.parent = parent;
            this.actual = actual;
            this.index = index;
            this.logger = logger;
        }

        @Override
        public Context currentContext() {
            return this.parent.currentContext();
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.CANCELLED) {
                return this.isCancelledByParent();
            }
            if (key == Scannable.Attr.PARENT) {
                return this.parent;
            }
            if (key == Scannable.Attr.ACTUAL) {
                return this.actual;
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
                SwitchMapMain<T, R> parent = this.parent;
                int expectedIndex = this.index;
                long state = FluxSwitchMapNoPrefetch.setInnerSubscribed(parent, expectedIndex);
                if (state == TERMINATED) {
                    s.cancel();
                    return;
                }
                int actualIndex = FluxSwitchMapNoPrefetch.index(state);
                if (expectedIndex != actualIndex) {
                    s.cancel();
                    parent.subscribeInner(this.nextElement, this.nextInner, actualIndex);
                    return;
                }
                if (FluxSwitchMapNoPrefetch.hasRequest(state) > 0) {
                    long requested;
                    this.requested = requested = parent.requested;
                    s.request(requested);
                }
            }
        }

        public void onNext(R t) {
            if (this.done) {
                Operators.onNextDropped(t, this.actual.currentContext());
                return;
            }
            SwitchMapMain<T, R> parent = this.parent;
            Subscription s = this.s;
            int expectedIndex = this.index;
            long requested = this.requested;
            long state = FluxSwitchMapNoPrefetch.setWip(parent, expectedIndex);
            if (state == TERMINATED) {
                Operators.onDiscard(t, this.actual.currentContext());
                return;
            }
            int actualIndex = FluxSwitchMapNoPrefetch.index(state);
            if (actualIndex != expectedIndex) {
                Operators.onDiscard(t, this.actual.currentContext());
                return;
            }
            this.actual.onNext(t);
            long produced = 0L;
            boolean isDemandFulfilled = false;
            int expectedHasRequest = FluxSwitchMapNoPrefetch.hasRequest(state);
            if (requested != Long.MAX_VALUE) {
                long actualRequested;
                long toRequestInAddition;
                this.produced = produced = this.produced + 1L;
                if (expectedHasRequest > 1 && (toRequestInAddition = (actualRequested = parent.requested) - requested) > 0L) {
                    this.requested = requested = actualRequested;
                    if (requested == Long.MAX_VALUE) {
                        produced = 0L;
                        this.produced = 0L;
                        s.request(Long.MAX_VALUE);
                    } else {
                        s.request(toRequestInAddition);
                    }
                }
                boolean bl = isDemandFulfilled = produced == requested;
                if (isDemandFulfilled) {
                    this.produced = 0L;
                    this.requested = requested = SwitchMapMain.REQUESTED.addAndGet(parent, -produced);
                    produced = 0L;
                    boolean bl2 = isDemandFulfilled = requested == 0L;
                    if (!isDemandFulfilled) {
                        s.request(requested);
                    }
                }
            }
            while (true) {
                if ((state = FluxSwitchMapNoPrefetch.unsetWip(parent, expectedIndex, isDemandFulfilled, expectedHasRequest)) == TERMINATED) {
                    return;
                }
                actualIndex = FluxSwitchMapNoPrefetch.index(state);
                if (expectedIndex != actualIndex) {
                    if (produced > 0L) {
                        this.produced = 0L;
                        this.requested = 0L;
                        SwitchMapMain.REQUESTED.addAndGet(parent, -produced);
                    }
                    parent.subscribeInner(this.nextElement, this.nextInner, actualIndex);
                    return;
                }
                int actualHasRequest = FluxSwitchMapNoPrefetch.hasRequest(state);
                if (!isDemandFulfilled || expectedHasRequest >= actualHasRequest) break;
                expectedHasRequest = actualHasRequest;
                long currentRequest = parent.requested;
                long toRequestInAddition = currentRequest - requested;
                if (toRequestInAddition <= 0L) continue;
                this.requested = requested = currentRequest;
                isDemandFulfilled = false;
                s.request(requested == Long.MAX_VALUE ? Long.MAX_VALUE : toRequestInAddition);
            }
        }

        public void onError(Throwable t) {
            if (this.done) {
                Operators.onErrorDropped(t, this.actual.currentContext());
                return;
            }
            this.done = true;
            SwitchMapMain<T, R> parent = this.parent;
            if (!Exceptions.addThrowable(SwitchMapMain.THROWABLE, parent, t)) {
                Operators.onErrorDropped(t, this.actual.currentContext());
                return;
            }
            long state = FluxSwitchMapNoPrefetch.setTerminated(parent);
            if (state == TERMINATED) {
                return;
            }
            if (!FluxSwitchMapNoPrefetch.hasMainCompleted(state)) {
                parent.s.cancel();
            }
            this.actual.onError(Exceptions.terminate(SwitchMapMain.THROWABLE, parent));
        }

        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            SwitchMapMain<T, R> parent = this.parent;
            int expectedIndex = this.index;
            long state = FluxSwitchMapNoPrefetch.setWip(parent, expectedIndex);
            if (state == TERMINATED) {
                return;
            }
            int actualIndex = FluxSwitchMapNoPrefetch.index(state);
            if (actualIndex != expectedIndex) {
                return;
            }
            long produced = this.produced;
            if (produced > 0L) {
                this.produced = 0L;
                this.requested = 0L;
                SwitchMapMain.REQUESTED.addAndGet(parent, -produced);
            }
            if (FluxSwitchMapNoPrefetch.hasMainCompleted(state)) {
                this.actual.onComplete();
                return;
            }
            state = FluxSwitchMapNoPrefetch.setInnerCompleted(parent);
            if (state == TERMINATED) {
                return;
            }
            actualIndex = FluxSwitchMapNoPrefetch.index(state);
            if (expectedIndex != actualIndex) {
                parent.subscribeInner(this.nextElement, this.nextInner, actualIndex);
            } else if (FluxSwitchMapNoPrefetch.hasMainCompleted(state)) {
                this.actual.onComplete();
            }
        }

        void request(long n) {
            long requested = this.requested;
            this.requested = Operators.addCap(requested, n);
            this.s.request(n);
        }

        boolean isCancelledByParent() {
            long state = this.parent.state;
            return this.index != FluxSwitchMapNoPrefetch.index(state) && !this.done || !this.parent.done && state == TERMINATED;
        }

        void cancelFromParent() {
            this.s.cancel();
        }

        public String toString() {
            return new StringJoiner(", ", SwitchMapInner.class.getSimpleName() + "[", "]").add("index=" + this.index).toString();
        }
    }

    static final class SwitchMapMain<T, R>
    implements InnerOperator<T, R> {
        @Nullable
        final StateLogger logger;
        final Function<? super T, ? extends Publisher<? extends R>> mapper;
        final CoreSubscriber<? super R> actual;
        Subscription s;
        boolean done;
        SwitchMapInner<T, R> inner;
        volatile Throwable throwable;
        static final AtomicReferenceFieldUpdater<SwitchMapMain, Throwable> THROWABLE = AtomicReferenceFieldUpdater.newUpdater(SwitchMapMain.class, Throwable.class, "throwable");
        volatile long requested;
        static final AtomicLongFieldUpdater<SwitchMapMain> REQUESTED = AtomicLongFieldUpdater.newUpdater(SwitchMapMain.class, "requested");
        volatile long state;
        static final AtomicLongFieldUpdater<SwitchMapMain> STATE = AtomicLongFieldUpdater.newUpdater(SwitchMapMain.class, "state");

        SwitchMapMain(CoreSubscriber<? super R> actual, Function<? super T, ? extends Publisher<? extends R>> mapper) {
            this(actual, mapper, null);
        }

        SwitchMapMain(CoreSubscriber<? super R> actual, Function<? super T, ? extends Publisher<? extends R>> mapper, @Nullable StateLogger logger) {
            this.actual = actual;
            this.mapper = mapper;
            this.logger = logger;
        }

        @Override
        public final CoreSubscriber<? super R> actual() {
            return this.actual;
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            long state = this.state;
            if (key == Scannable.Attr.CANCELLED) {
                return !this.done && state == TERMINATED;
            }
            if (key == Scannable.Attr.PARENT) {
                return this.s;
            }
            if (key == Scannable.Attr.TERMINATED) {
                return this.done;
            }
            if (key == Scannable.Attr.ERROR) {
                return this.throwable;
            }
            if (key == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM) {
                return this.requested;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return InnerOperator.super.scanUnsafe(key);
        }

        @Override
        public Stream<? extends Scannable> inners() {
            return Stream.of(this.inner);
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (Operators.validate(this.s, s)) {
                this.s = s;
                this.actual.onSubscribe(this);
                s.request(Long.MAX_VALUE);
            }
        }

        public void onNext(T t) {
            boolean hasInner;
            if (this.done) {
                Operators.onNextDropped(t, this.actual.currentContext());
                return;
            }
            SwitchMapInner<T, R> si = this.inner;
            boolean bl = hasInner = si != null;
            if (!hasInner) {
                SwitchMapInner nsi = new SwitchMapInner(this, this.actual, 0, this.logger);
                this.inner = nsi;
                this.subscribeInner(t, nsi, 0);
                return;
            }
            int nextIndex = si.index + 1;
            SwitchMapInner nsi = new SwitchMapInner(this, this.actual, nextIndex, this.logger);
            this.inner = nsi;
            si.nextInner = nsi;
            si.nextElement = t;
            long state = FluxSwitchMapNoPrefetch.incrementIndex(this);
            if (state == TERMINATED) {
                Operators.onDiscard(t, this.actual.currentContext());
                return;
            }
            if (FluxSwitchMapNoPrefetch.isInnerSubscribed(state)) {
                si.cancelFromParent();
                if (!FluxSwitchMapNoPrefetch.isWip(state)) {
                    long produced = si.produced;
                    if (produced > 0L) {
                        si.produced = 0L;
                        if (this.requested != Long.MAX_VALUE) {
                            si.requested = 0L;
                            REQUESTED.addAndGet(this, -produced);
                        }
                    }
                    this.subscribeInner(t, nsi, nextIndex);
                }
            }
        }

        void subscribeInner(T nextElement, SwitchMapInner<T, R> nextInner, int nextIndex) {
            Publisher<? extends R> p;
            CoreSubscriber<R> actual = this.actual;
            Context context = actual.currentContext();
            while (nextInner.index != nextIndex) {
                Operators.onDiscard(nextElement, context);
                nextElement = nextInner.nextElement;
                nextInner = nextInner.nextInner;
            }
            try {
                p = Objects.requireNonNull(this.mapper.apply(nextElement), "The mapper returned a null publisher");
            }
            catch (Throwable e) {
                this.onError(Operators.onOperatorError(this.s, e, nextElement, context));
                return;
            }
            p.subscribe(nextInner);
        }

        public void onError(Throwable t) {
            if (this.done) {
                Operators.onErrorDropped(t, this.actual.currentContext());
                return;
            }
            this.done = true;
            if (!Exceptions.addThrowable(THROWABLE, this, t)) {
                Operators.onErrorDropped(t, this.actual.currentContext());
                return;
            }
            long state = FluxSwitchMapNoPrefetch.setTerminated(this);
            if (state == TERMINATED) {
                return;
            }
            SwitchMapInner<T, R> inner = this.inner;
            if (inner != null && FluxSwitchMapNoPrefetch.isInnerSubscribed(state)) {
                inner.cancelFromParent();
            }
            this.actual.onError(Exceptions.terminate(THROWABLE, this));
        }

        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            long state = FluxSwitchMapNoPrefetch.setMainCompleted(this);
            if (state == TERMINATED) {
                return;
            }
            SwitchMapInner<T, R> inner = this.inner;
            if (inner == null || FluxSwitchMapNoPrefetch.hasInnerCompleted(state)) {
                this.actual.onComplete();
            }
        }

        public void request(long n) {
            if (Operators.validate(n)) {
                long previousRequested = Operators.addCap(REQUESTED, this, n);
                long state = FluxSwitchMapNoPrefetch.addRequest(this, previousRequested);
                if (state == TERMINATED) {
                    return;
                }
                if (FluxSwitchMapNoPrefetch.hasRequest(state) == 1 && FluxSwitchMapNoPrefetch.isInnerSubscribed(state) && !FluxSwitchMapNoPrefetch.hasInnerCompleted(state)) {
                    SwitchMapInner<T, R> inner = this.inner;
                    if (inner.index == FluxSwitchMapNoPrefetch.index(state)) {
                        inner.request(n);
                    }
                }
            }
        }

        public void cancel() {
            long state = FluxSwitchMapNoPrefetch.setTerminated(this);
            if (state == TERMINATED) {
                return;
            }
            SwitchMapInner<T, R> inner = this.inner;
            if (inner != null && FluxSwitchMapNoPrefetch.isInnerSubscribed(state) && !FluxSwitchMapNoPrefetch.hasInnerCompleted(state) && inner.index == FluxSwitchMapNoPrefetch.index(state)) {
                inner.cancelFromParent();
            }
            if (!FluxSwitchMapNoPrefetch.hasMainCompleted(state)) {
                this.s.cancel();
                Throwable e = Exceptions.terminate(THROWABLE, this);
                if (e != null) {
                    Operators.onErrorDropped(e, this.actual.currentContext());
                }
            }
        }
    }
}

