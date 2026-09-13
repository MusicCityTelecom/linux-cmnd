/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Publisher
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.Function;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxConcatMap;
import reactor.core.publisher.FluxFlatMap;
import reactor.core.publisher.InternalFluxOperator;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

final class FluxConcatMapNoPrefetch<T, R>
extends InternalFluxOperator<T, R> {
    final Function<? super T, ? extends Publisher<? extends R>> mapper;
    final FluxConcatMap.ErrorMode errorMode;

    FluxConcatMapNoPrefetch(Flux<? extends T> source, Function<? super T, ? extends Publisher<? extends R>> mapper, FluxConcatMap.ErrorMode errorMode) {
        super(source);
        this.mapper = Objects.requireNonNull(mapper, "mapper");
        this.errorMode = errorMode;
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super R> actual) {
        if (FluxFlatMap.trySubscribeScalarMap(this.source, actual, this.mapper, false, true)) {
            return null;
        }
        return new FluxConcatMapNoPrefetchSubscriber<T, R>(actual, this.mapper, this.errorMode);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    @Override
    public int getPrefetch() {
        return 0;
    }

    static final class FluxConcatMapNoPrefetchSubscriber<T, R>
    implements FluxConcatMap.FluxConcatMapSupport<T, R> {
        volatile State state;
        static final AtomicReferenceFieldUpdater<FluxConcatMapNoPrefetchSubscriber, State> STATE = AtomicReferenceFieldUpdater.newUpdater(FluxConcatMapNoPrefetchSubscriber.class, State.class, "state");
        volatile Throwable error;
        static final AtomicReferenceFieldUpdater<FluxConcatMapNoPrefetchSubscriber, Throwable> ERROR = AtomicReferenceFieldUpdater.newUpdater(FluxConcatMapNoPrefetchSubscriber.class, Throwable.class, "error");
        final CoreSubscriber<? super R> actual;
        final FluxConcatMap.ConcatMapInner<R> inner;
        final Function<? super T, ? extends Publisher<? extends R>> mapper;
        final FluxConcatMap.ErrorMode errorMode;
        Subscription upstream;

        FluxConcatMapNoPrefetchSubscriber(CoreSubscriber<? super R> actual, Function<? super T, ? extends Publisher<? extends R>> mapper, FluxConcatMap.ErrorMode errorMode) {
            this.actual = actual;
            this.mapper = mapper;
            this.errorMode = errorMode;
            this.inner = new FluxConcatMap.ConcatMapInner(this);
            STATE.lazySet(this, State.INITIAL);
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.PARENT) {
                return this.upstream;
            }
            if (key == Scannable.Attr.TERMINATED) {
                return this.state == State.TERMINATED;
            }
            if (key == Scannable.Attr.CANCELLED) {
                return this.state == State.CANCELLED;
            }
            if (key == Scannable.Attr.DELAY_ERROR) {
                return this.errorMode != FluxConcatMap.ErrorMode.IMMEDIATE;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return FluxConcatMap.FluxConcatMapSupport.super.scanUnsafe(key);
        }

        @Override
        public CoreSubscriber<? super R> actual() {
            return this.actual;
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (Operators.validate(this.upstream, s)) {
                this.upstream = s;
                this.actual.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            block10: {
                if (!STATE.compareAndSet(this, State.REQUESTED, State.ACTIVE)) {
                    switch (this.state) {
                        case CANCELLED: {
                            Operators.onDiscard(t, this.currentContext());
                            break;
                        }
                        case TERMINATED: {
                            Operators.onNextDropped(t, this.currentContext());
                        }
                    }
                    return;
                }
                try {
                    Publisher<? extends R> p = this.mapper.apply(t);
                    Objects.requireNonNull(p, "The mapper returned a null Publisher");
                    if (p instanceof Callable) {
                        Callable callable = (Callable)p;
                        Object result = callable.call();
                        if (result == null) {
                            this.innerComplete();
                            return;
                        }
                        if (this.inner.isUnbounded()) {
                            this.actual.onNext(result);
                            this.innerComplete();
                            return;
                        }
                        this.inner.set(new FluxConcatMap.WeakScalarSubscription(result, this.inner));
                        return;
                    }
                    p.subscribe(this.inner);
                }
                catch (Throwable e) {
                    Context ctx = this.actual.currentContext();
                    Operators.onDiscard(t, ctx);
                    if (this.maybeOnError(Operators.onNextError(t, e, ctx), ctx, this.upstream)) break block10;
                    this.innerComplete();
                }
            }
        }

        public void onError(Throwable t) {
            Context ctx = this.currentContext();
            if (!this.maybeOnError(t, ctx, this.inner)) {
                this.onComplete();
            }
        }

        public void onComplete() {
            State previousState = this.state;
            while (true) {
                switch (previousState) {
                    case INITIAL: 
                    case REQUESTED: {
                        if (!STATE.compareAndSet(this, previousState, State.TERMINATED)) break;
                        Throwable ex = this.error;
                        if (ex != null) {
                            this.actual.onError(ex);
                            return;
                        }
                        this.actual.onComplete();
                        return;
                    }
                    case ACTIVE: {
                        if (!STATE.compareAndSet(this, previousState, State.LAST_ACTIVE)) break;
                        return;
                    }
                    default: {
                        return;
                    }
                }
                previousState = this.state;
            }
        }

        @Override
        public synchronized void innerNext(R value) {
            switch (this.state) {
                case ACTIVE: 
                case LAST_ACTIVE: {
                    this.actual.onNext(value);
                    break;
                }
                default: {
                    Operators.onDiscard(value, this.currentContext());
                }
            }
        }

        @Override
        public void innerComplete() {
            State previousState = this.state;
            while (true) {
                switch (previousState) {
                    case ACTIVE: {
                        if (!STATE.compareAndSet(this, previousState, State.REQUESTED)) break;
                        this.upstream.request(1L);
                        return;
                    }
                    case LAST_ACTIVE: {
                        if (!STATE.compareAndSet(this, previousState, State.TERMINATED)) break;
                        Throwable ex = this.error;
                        if (ex != null) {
                            this.actual.onError(ex);
                            return;
                        }
                        this.actual.onComplete();
                        return;
                    }
                    default: {
                        return;
                    }
                }
                previousState = this.state;
            }
        }

        @Override
        public void innerError(Throwable e) {
            Context ctx = this.currentContext();
            if (!this.maybeOnError(Operators.onNextInnerError(e, ctx, null), ctx, this.upstream)) {
                this.innerComplete();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private boolean maybeOnError(@Nullable Throwable e, Context ctx, Subscription subscriptionToCancel) {
            if (e == null) {
                return false;
            }
            if (!ERROR.compareAndSet(this, null, e)) {
                Operators.onErrorDropped(e, ctx);
            }
            if (this.errorMode == FluxConcatMap.ErrorMode.END) {
                return false;
            }
            State previousState = this.state;
            while (true) {
                switch (previousState) {
                    case CANCELLED: 
                    case TERMINATED: {
                        return true;
                    }
                }
                if (STATE.compareAndSet(this, previousState, State.TERMINATED)) {
                    subscriptionToCancel.cancel();
                    FluxConcatMapNoPrefetchSubscriber fluxConcatMapNoPrefetchSubscriber = this;
                    synchronized (fluxConcatMapNoPrefetchSubscriber) {
                        this.actual.onError(this.error);
                    }
                    return true;
                }
                previousState = this.state;
            }
        }

        public void request(long n) {
            if (STATE.compareAndSet(this, State.INITIAL, State.REQUESTED)) {
                this.upstream.request(1L);
            }
            this.inner.request(n);
        }

        public void cancel() {
            switch (STATE.getAndSet(this, State.CANCELLED)) {
                case CANCELLED: {
                    break;
                }
                case TERMINATED: {
                    this.inner.cancel();
                    break;
                }
                default: {
                    this.inner.cancel();
                    this.upstream.cancel();
                }
            }
        }

        static enum State {
            INITIAL,
            REQUESTED,
            ACTIVE,
            LAST_ACTIVE,
            TERMINATED,
            CANCELLED;

        }
    }
}

