/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Publisher
 *  org.reactivestreams.Subscriber
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.function.Function;
import java.util.stream.Stream;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.CorePublisher;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.InnerConsumer;
import reactor.core.publisher.InternalFluxOperator;
import reactor.core.publisher.Operators;
import reactor.core.publisher.OptimizableOperator;
import reactor.core.publisher.Sinks;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;
import reactor.util.context.ContextView;

final class FluxRepeatWhen<T>
extends InternalFluxOperator<T, T> {
    final Function<? super Flux<Long>, ? extends Publisher<?>> whenSourceFactory;

    FluxRepeatWhen(Flux<? extends T> source, Function<? super Flux<Long>, ? extends Publisher<?>> whenSourceFactory) {
        super(source);
        this.whenSourceFactory = Objects.requireNonNull(whenSourceFactory, "whenSourceFactory");
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> actual) {
        Publisher<?> p;
        RepeatWhenOtherSubscriber other = new RepeatWhenOtherSubscriber();
        CoreSubscriber<T> serial = Operators.serialize(actual);
        RepeatWhenMainSubscriber<? super T> main = new RepeatWhenMainSubscriber<T>(serial, other.completionSignal, this.source);
        other.main = main;
        serial.onSubscribe(main);
        try {
            p = Objects.requireNonNull(this.whenSourceFactory.apply(other), "The whenSourceFactory returned a null Publisher");
        }
        catch (Throwable e) {
            actual.onError(Operators.onOperatorError(e, actual.currentContext()));
            return null;
        }
        p.subscribe((Subscriber)other);
        if (!main.cancelled) {
            return main;
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

    static final class RepeatWhenOtherSubscriber
    extends Flux<Long>
    implements InnerConsumer<Object>,
    OptimizableOperator<Long, Long> {
        RepeatWhenMainSubscriber<?> main;
        final Sinks.Many<Long> completionSignal = Sinks.many().multicast().onBackpressureBuffer();

        RepeatWhenOtherSubscriber() {
        }

        @Override
        public Context currentContext() {
            return this.main.currentContext();
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.PARENT) {
                return this.main.otherArbiter;
            }
            if (key == Scannable.Attr.ACTUAL) {
                return this.main;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return null;
        }

        @Override
        public void onSubscribe(Subscription s) {
            this.main.setWhen(s);
        }

        public void onNext(Object t) {
            this.main.resubscribe(t);
        }

        public void onError(Throwable t) {
            this.main.whenError(t);
        }

        public void onComplete() {
            this.main.whenComplete();
        }

        @Override
        public void subscribe(CoreSubscriber<? super Long> actual) {
            this.completionSignal.asFlux().subscribe(actual);
        }

        @Override
        public CoreSubscriber<? super Long> subscribeOrReturn(CoreSubscriber<? super Long> actual) {
            return actual;
        }

        @Override
        public Flux<Long> source() {
            return this.completionSignal.asFlux();
        }

        @Override
        public OptimizableOperator<?, ? extends Long> nextOptimizableSource() {
            return null;
        }
    }

    static final class RepeatWhenMainSubscriber<T>
    extends Operators.MultiSubscriptionSubscriber<T, T> {
        final Operators.DeferredSubscription otherArbiter;
        final Sinks.Many<Long> signaller;
        final CorePublisher<? extends T> source;
        volatile int wip;
        static final AtomicIntegerFieldUpdater<RepeatWhenMainSubscriber> WIP = AtomicIntegerFieldUpdater.newUpdater(RepeatWhenMainSubscriber.class, "wip");
        Context context;
        long produced;

        RepeatWhenMainSubscriber(CoreSubscriber<? super T> actual, Sinks.Many<Long> signaller, CorePublisher<? extends T> source) {
            super(actual);
            this.signaller = signaller;
            this.source = source;
            this.otherArbiter = new Operators.DeferredSubscription();
            this.context = actual.currentContext();
        }

        @Override
        public Context currentContext() {
            return this.context;
        }

        @Override
        public Stream<? extends Scannable> inners() {
            return Stream.of(Scannable.from(this.signaller), this.otherArbiter);
        }

        @Override
        public void cancel() {
            if (!this.cancelled) {
                this.otherArbiter.cancel();
                super.cancel();
            }
        }

        public void onNext(T t) {
            this.actual.onNext(t);
            ++this.produced;
        }

        @Override
        public void onError(Throwable t) {
            this.otherArbiter.cancel();
            this.actual.onError(t);
        }

        @Override
        public void onComplete() {
            long p = this.produced;
            if (p != 0L) {
                this.produced = 0L;
                this.produced(p);
            }
            this.signaller.emitNext(p, Sinks.EmitFailureHandler.FAIL_FAST);
            this.otherArbiter.request(1L);
        }

        void setWhen(Subscription w) {
            this.otherArbiter.set(w);
        }

        void resubscribe(Object trigger) {
            if (WIP.getAndIncrement(this) == 0) {
                do {
                    if (this.cancelled) {
                        return;
                    }
                    if (trigger instanceof ContextView) {
                        this.context = this.context.putAll((ContextView)trigger);
                    }
                    this.source.subscribe(this);
                } while (WIP.decrementAndGet(this) != 0);
            }
        }

        void whenError(Throwable e) {
            super.cancel();
            this.actual.onError(e);
        }

        void whenComplete() {
            super.cancel();
            this.actual.onComplete();
        }

        @Override
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return super.scanUnsafe(key);
        }
    }
}

