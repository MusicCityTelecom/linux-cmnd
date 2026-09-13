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
import reactor.util.retry.Retry;

final class FluxRetryWhen<T>
extends InternalFluxOperator<T, T> {
    final Retry whenSourceFactory;

    FluxRetryWhen(Flux<? extends T> source, Retry whenSourceFactory) {
        super(source);
        this.whenSourceFactory = Objects.requireNonNull(whenSourceFactory, "whenSourceFactory");
    }

    static <T> void subscribe(CoreSubscriber<? super T> s, Retry whenSourceFactory, CorePublisher<? extends T> source) {
        Publisher<?> p;
        RetryWhenOtherSubscriber other = new RetryWhenOtherSubscriber();
        CoreSubscriber<T> serial = Operators.serialize(s);
        RetryWhenMainSubscriber<? extends T> main = new RetryWhenMainSubscriber<T>(serial, other.completionSignal, source, whenSourceFactory.retryContext());
        other.main = main;
        serial.onSubscribe(main);
        try {
            p = Objects.requireNonNull(whenSourceFactory.generateCompanion(other), "The whenSourceFactory returned a null Publisher");
        }
        catch (Throwable e) {
            s.onError(Operators.onOperatorError(e, s.currentContext()));
            return;
        }
        p.subscribe((Subscriber)other);
        if (!main.cancelled) {
            source.subscribe(main);
        }
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> actual) {
        FluxRetryWhen.subscribe(actual, this.whenSourceFactory, this.source);
        return null;
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static final class RetryWhenOtherSubscriber
    extends Flux<Retry.RetrySignal>
    implements InnerConsumer<Object>,
    OptimizableOperator<Retry.RetrySignal, Retry.RetrySignal> {
        RetryWhenMainSubscriber<?> main;
        final Sinks.Many<Retry.RetrySignal> completionSignal = Sinks.many().multicast().onBackpressureBuffer();

        RetryWhenOtherSubscriber() {
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
            this.main.swap(s);
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
        public void subscribe(CoreSubscriber<? super Retry.RetrySignal> actual) {
            this.completionSignal.asFlux().subscribe(actual);
        }

        @Override
        public CoreSubscriber<? super Retry.RetrySignal> subscribeOrReturn(CoreSubscriber<? super Retry.RetrySignal> actual) {
            return actual;
        }

        @Override
        public CorePublisher<Retry.RetrySignal> source() {
            return this.completionSignal.asFlux();
        }

        @Override
        public OptimizableOperator<?, ? extends Retry.RetrySignal> nextOptimizableSource() {
            return null;
        }
    }

    static final class RetryWhenMainSubscriber<T>
    extends Operators.MultiSubscriptionSubscriber<T, T>
    implements Retry.RetrySignal {
        final Operators.DeferredSubscription otherArbiter;
        final Sinks.Many<Retry.RetrySignal> signaller;
        final CorePublisher<? extends T> source;
        long totalFailureIndex = 0L;
        long subsequentFailureIndex = 0L;
        @Nullable
        Throwable lastFailure = null;
        final ContextView retryContext;
        Context context;
        volatile int wip;
        static final AtomicIntegerFieldUpdater<RetryWhenMainSubscriber> WIP = AtomicIntegerFieldUpdater.newUpdater(RetryWhenMainSubscriber.class, "wip");
        long produced;

        RetryWhenMainSubscriber(CoreSubscriber<? super T> actual, Sinks.Many<Retry.RetrySignal> signaller, CorePublisher<? extends T> source, ContextView retryContext) {
            super(actual);
            this.signaller = signaller;
            this.source = source;
            this.otherArbiter = new Operators.DeferredSubscription();
            this.context = actual.currentContext();
            this.retryContext = retryContext;
        }

        @Override
        public long totalRetries() {
            return this.totalFailureIndex - 1L;
        }

        @Override
        public long totalRetriesInARow() {
            return this.subsequentFailureIndex - 1L;
        }

        @Override
        public Throwable failure() {
            assert (this.lastFailure != null);
            return this.lastFailure;
        }

        @Override
        public ContextView retryContextView() {
            return this.retryContext;
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

        void swap(Subscription w) {
            this.otherArbiter.set(w);
        }

        public void onNext(T t) {
            this.subsequentFailureIndex = 0L;
            this.actual.onNext(t);
            ++this.produced;
        }

        @Override
        public void onError(Throwable t) {
            ++this.totalFailureIndex;
            ++this.subsequentFailureIndex;
            this.lastFailure = t;
            long p = this.produced;
            if (p != 0L) {
                this.produced = 0L;
                this.produced(p);
            }
            this.signaller.emitNext(this, Sinks.EmitFailureHandler.FAIL_FAST);
            this.otherArbiter.request(1L);
        }

        @Override
        public void onComplete() {
            this.lastFailure = null;
            this.otherArbiter.cancel();
            this.actual.onComplete();
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

