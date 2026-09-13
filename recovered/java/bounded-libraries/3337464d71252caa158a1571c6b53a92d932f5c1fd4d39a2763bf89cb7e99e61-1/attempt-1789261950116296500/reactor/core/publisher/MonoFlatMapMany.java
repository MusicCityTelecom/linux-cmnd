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
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.Function;
import java.util.stream.Stream;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.FluxFlatMap;
import reactor.core.publisher.FluxFromMonoOperator;
import reactor.core.publisher.InnerConsumer;
import reactor.core.publisher.InnerOperator;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

final class MonoFlatMapMany<T, R>
extends FluxFromMonoOperator<T, R> {
    final Function<? super T, ? extends Publisher<? extends R>> mapper;

    MonoFlatMapMany(Mono<? extends T> source, Function<? super T, ? extends Publisher<? extends R>> mapper) {
        super(source);
        this.mapper = mapper;
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super R> actual) {
        if (FluxFlatMap.trySubscribeScalarMap(this.source, actual, this.mapper, false, false)) {
            return null;
        }
        return new FlatMapManyMain<T, R>(actual, this.mapper);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static final class FlatMapManyInner<R>
    implements InnerConsumer<R> {
        final FlatMapManyMain<?, R> parent;
        final CoreSubscriber<? super R> actual;

        FlatMapManyInner(FlatMapManyMain<?, R> parent, CoreSubscriber<? super R> actual) {
            this.parent = parent;
            this.actual = actual;
        }

        @Override
        public Context currentContext() {
            return this.actual.currentContext();
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.PARENT) {
                return this.parent.inner;
            }
            if (key == Scannable.Attr.ACTUAL) {
                return this.parent;
            }
            if (key == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM) {
                return this.parent.requested;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return null;
        }

        @Override
        public void onSubscribe(Subscription s) {
            this.parent.onSubscribeInner(s);
        }

        public void onNext(R t) {
            this.actual.onNext(t);
        }

        public void onError(Throwable t) {
            this.actual.onError(t);
        }

        public void onComplete() {
            this.actual.onComplete();
        }
    }

    static final class FlatMapManyMain<T, R>
    implements InnerOperator<T, R> {
        final CoreSubscriber<? super R> actual;
        final Function<? super T, ? extends Publisher<? extends R>> mapper;
        Subscription main;
        volatile Subscription inner;
        static final AtomicReferenceFieldUpdater<FlatMapManyMain, Subscription> INNER = AtomicReferenceFieldUpdater.newUpdater(FlatMapManyMain.class, Subscription.class, "inner");
        volatile long requested;
        static final AtomicLongFieldUpdater<FlatMapManyMain> REQUESTED = AtomicLongFieldUpdater.newUpdater(FlatMapManyMain.class, "requested");
        boolean hasValue;

        FlatMapManyMain(CoreSubscriber<? super R> actual, Function<? super T, ? extends Publisher<? extends R>> mapper) {
            this.actual = actual;
            this.mapper = mapper;
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.PARENT) {
                return this.main;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return InnerOperator.super.scanUnsafe(key);
        }

        @Override
        public Stream<? extends Scannable> inners() {
            return Stream.of(Scannable.from(this.inner));
        }

        @Override
        public CoreSubscriber<? super R> actual() {
            return this.actual;
        }

        public void request(long n) {
            Subscription a = this.inner;
            if (a != null) {
                a.request(n);
            } else if (Operators.validate(n)) {
                Operators.addCap(REQUESTED, this, n);
                a = this.inner;
                if (a != null && (n = REQUESTED.getAndSet(this, 0L)) != 0L) {
                    a.request(n);
                }
            }
        }

        public void cancel() {
            this.main.cancel();
            Operators.terminate(INNER, this);
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (Operators.validate(this.main, s)) {
                this.main = s;
                this.actual.onSubscribe(this);
                s.request(Long.MAX_VALUE);
            }
        }

        void onSubscribeInner(Subscription s) {
            long r;
            if (Operators.setOnce(INNER, this, s) && (r = REQUESTED.getAndSet(this, 0L)) != 0L) {
                s.request(r);
            }
        }

        public void onNext(T t) {
            Publisher<? extends R> p;
            this.hasValue = true;
            try {
                p = Objects.requireNonNull(this.mapper.apply(t), "The mapper returned a null Publisher.");
            }
            catch (Throwable ex) {
                this.actual.onError(Operators.onOperatorError(this, ex, t, this.actual.currentContext()));
                return;
            }
            if (p instanceof Callable) {
                Object v;
                try {
                    v = ((Callable)p).call();
                }
                catch (Throwable ex) {
                    this.actual.onError(Operators.onOperatorError(this, ex, t, this.actual.currentContext()));
                    return;
                }
                if (v == null) {
                    this.actual.onComplete();
                } else {
                    this.onSubscribeInner(Operators.scalarSubscription(this.actual, v));
                }
                return;
            }
            p.subscribe(new FlatMapManyInner<R>(this, this.actual));
        }

        public void onError(Throwable t) {
            if (this.hasValue) {
                Operators.onErrorDropped(t, this.actual.currentContext());
                return;
            }
            this.actual.onError(t);
        }

        public void onComplete() {
            if (!this.hasValue) {
                this.actual.onComplete();
            }
        }
    }
}

