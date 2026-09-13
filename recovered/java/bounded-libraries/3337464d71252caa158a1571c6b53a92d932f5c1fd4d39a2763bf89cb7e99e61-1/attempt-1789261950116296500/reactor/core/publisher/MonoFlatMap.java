/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.Function;
import java.util.stream.Stream;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.FluxFlatMap;
import reactor.core.publisher.InnerConsumer;
import reactor.core.publisher.InternalMonoOperator;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

final class MonoFlatMap<T, R>
extends InternalMonoOperator<T, R>
implements Fuseable {
    final Function<? super T, ? extends Mono<? extends R>> mapper;

    MonoFlatMap(Mono<? extends T> source, Function<? super T, ? extends Mono<? extends R>> mapper) {
        super(source);
        this.mapper = Objects.requireNonNull(mapper, "mapper");
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super R> actual) {
        if (FluxFlatMap.trySubscribeScalarMap(this.source, actual, this.mapper, true, false)) {
            return null;
        }
        FlatMapMain<? super T, ? super R> manager = new FlatMapMain<T, R>(actual, this.mapper);
        actual.onSubscribe(manager);
        return manager;
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static final class FlatMapInner<R>
    implements InnerConsumer<R> {
        final FlatMapMain<?, R> parent;
        volatile Subscription s;
        static final AtomicReferenceFieldUpdater<FlatMapInner, Subscription> S = AtomicReferenceFieldUpdater.newUpdater(FlatMapInner.class, Subscription.class, "s");
        boolean done;

        FlatMapInner(FlatMapMain<?, R> parent) {
            this.parent = parent;
        }

        @Override
        public Context currentContext() {
            return this.parent.currentContext();
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.PARENT) {
                return this.s;
            }
            if (key == Scannable.Attr.ACTUAL) {
                return this.parent;
            }
            if (key == Scannable.Attr.TERMINATED) {
                return this.done;
            }
            if (key == Scannable.Attr.CANCELLED) {
                return this.s == Operators.cancelledSubscription();
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return null;
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (Operators.setOnce(S, this, s)) {
                s.request(Long.MAX_VALUE);
            }
        }

        public void onNext(R t) {
            if (this.done) {
                Operators.onNextDropped(t, this.parent.currentContext());
                return;
            }
            this.done = true;
            this.parent.complete(t);
        }

        public void onError(Throwable t) {
            if (this.done) {
                Operators.onErrorDropped(t, this.parent.currentContext());
                return;
            }
            this.done = true;
            this.parent.secondError(t);
        }

        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            this.parent.secondComplete();
        }

        void cancel() {
            Operators.terminate(S, this);
        }
    }

    static final class FlatMapMain<T, R>
    extends Operators.MonoSubscriber<T, R> {
        final Function<? super T, ? extends Mono<? extends R>> mapper;
        final FlatMapInner<R> second;
        boolean done;
        volatile Subscription s;
        static final AtomicReferenceFieldUpdater<FlatMapMain, Subscription> S = AtomicReferenceFieldUpdater.newUpdater(FlatMapMain.class, Subscription.class, "s");

        FlatMapMain(CoreSubscriber<? super R> subscriber, Function<? super T, ? extends Mono<? extends R>> mapper) {
            super(subscriber);
            this.mapper = mapper;
            this.second = new FlatMapInner(this);
        }

        @Override
        public Stream<? extends Scannable> inners() {
            return Stream.of(this.second);
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.PARENT) {
                return this.s;
            }
            if (key == Scannable.Attr.CANCELLED) {
                return this.s == Operators.cancelledSubscription();
            }
            if (key == Scannable.Attr.TERMINATED) {
                return this.done;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return super.scanUnsafe(key);
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (Operators.setOnce(S, this, s)) {
                s.request(Long.MAX_VALUE);
            }
        }

        @Override
        public void onNext(T t) {
            Mono<R> m;
            if (this.done) {
                Operators.onNextDropped(t, this.actual.currentContext());
                return;
            }
            this.done = true;
            try {
                m = Objects.requireNonNull(this.mapper.apply(t), "The mapper returned a null Mono");
            }
            catch (Throwable ex) {
                this.actual.onError(Operators.onOperatorError(this.s, ex, t, this.actual.currentContext()));
                return;
            }
            if (m instanceof Callable) {
                Object v;
                Callable c = (Callable)((Object)m);
                try {
                    v = c.call();
                }
                catch (Throwable ex) {
                    this.actual.onError(Operators.onOperatorError(this.s, ex, t, this.actual.currentContext()));
                    return;
                }
                if (v == null) {
                    this.actual.onComplete();
                } else {
                    this.complete(v);
                }
                return;
            }
            try {
                m.subscribe(this.second);
            }
            catch (Throwable e) {
                this.actual.onError(Operators.onOperatorError(this, e, t, this.actual.currentContext()));
            }
        }

        @Override
        public void onError(Throwable t) {
            if (this.done) {
                Operators.onErrorDropped(t, this.actual.currentContext());
                return;
            }
            this.done = true;
            this.actual.onError(t);
        }

        @Override
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            this.actual.onComplete();
        }

        @Override
        public void cancel() {
            super.cancel();
            Operators.terminate(S, this);
            this.second.cancel();
        }

        void secondError(Throwable ex) {
            this.actual.onError(ex);
        }

        void secondComplete() {
            this.actual.onComplete();
        }
    }
}

