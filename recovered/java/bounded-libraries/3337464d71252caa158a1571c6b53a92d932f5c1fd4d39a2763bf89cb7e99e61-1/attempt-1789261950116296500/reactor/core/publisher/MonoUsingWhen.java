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
import java.util.function.BiFunction;
import java.util.function.Function;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Scannable;
import reactor.core.publisher.FluxUsingWhen;
import reactor.core.publisher.InnerConsumer;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Operators;
import reactor.core.publisher.SourceProducer;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

final class MonoUsingWhen<T, S>
extends Mono<T>
implements SourceProducer<T> {
    final Publisher<S> resourceSupplier;
    final Function<? super S, ? extends Mono<? extends T>> resourceClosure;
    final Function<? super S, ? extends Publisher<?>> asyncComplete;
    final BiFunction<? super S, ? super Throwable, ? extends Publisher<?>> asyncError;
    @Nullable
    final Function<? super S, ? extends Publisher<?>> asyncCancel;

    MonoUsingWhen(Publisher<S> resourceSupplier, Function<? super S, ? extends Mono<? extends T>> resourceClosure, Function<? super S, ? extends Publisher<?>> asyncComplete, BiFunction<? super S, ? super Throwable, ? extends Publisher<?>> asyncError, @Nullable Function<? super S, ? extends Publisher<?>> asyncCancel) {
        this.resourceSupplier = Objects.requireNonNull(resourceSupplier, "resourceSupplier");
        this.resourceClosure = Objects.requireNonNull(resourceClosure, "resourceClosure");
        this.asyncComplete = Objects.requireNonNull(asyncComplete, "asyncComplete");
        this.asyncError = Objects.requireNonNull(asyncError, "asyncError");
        this.asyncCancel = asyncCancel;
    }

    @Override
    public void subscribe(CoreSubscriber<? super T> actual) {
        if (this.resourceSupplier instanceof Callable) {
            try {
                Callable resourceCallable = (Callable)this.resourceSupplier;
                Object resource = resourceCallable.call();
                if (resource == null) {
                    Operators.complete(actual);
                } else {
                    Mono<T> p = MonoUsingWhen.deriveMonoFromResource(resource, this.resourceClosure);
                    MonoUsingWhenSubscriber<? super T, ? super S> subscriber = MonoUsingWhen.prepareSubscriberForResource(resource, actual, this.asyncComplete, this.asyncError, this.asyncCancel, null);
                    p.subscribe(subscriber);
                }
            }
            catch (Throwable e) {
                Operators.error(actual, e);
            }
            return;
        }
        this.resourceSupplier.subscribe(new ResourceSubscriber<S, T>(actual, this.resourceClosure, this.asyncComplete, this.asyncError, this.asyncCancel, this.resourceSupplier instanceof Mono));
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return null;
    }

    private static <RESOURCE, T> Mono<? extends T> deriveMonoFromResource(RESOURCE resource, Function<? super RESOURCE, ? extends Mono<? extends T>> resourceClosure) {
        Mono<Object> p;
        try {
            p = Objects.requireNonNull(resourceClosure.apply(resource), "The resourceClosure function returned a null value");
        }
        catch (Throwable e) {
            p = Mono.error(e);
        }
        return p;
    }

    private static <RESOURCE, T> MonoUsingWhenSubscriber<? super T, RESOURCE> prepareSubscriberForResource(RESOURCE resource, CoreSubscriber<? super T> actual, Function<? super RESOURCE, ? extends Publisher<?>> asyncComplete, BiFunction<? super RESOURCE, ? super Throwable, ? extends Publisher<?>> asyncError, @Nullable Function<? super RESOURCE, ? extends Publisher<?>> asyncCancel, @Nullable Operators.DeferredSubscription arbiter) {
        return new MonoUsingWhenSubscriber<T, RESOURCE>(actual, resource, asyncComplete, asyncError, asyncCancel, arbiter);
    }

    static class MonoUsingWhenSubscriber<T, S>
    extends FluxUsingWhen.UsingWhenSubscriber<T, S> {
        T value;

        MonoUsingWhenSubscriber(CoreSubscriber<? super T> actual, S resource, Function<? super S, ? extends Publisher<?>> asyncComplete, BiFunction<? super S, ? super Throwable, ? extends Publisher<?>> asyncError, @Nullable Function<? super S, ? extends Publisher<?>> asyncCancel, @Nullable Operators.DeferredSubscription arbiter) {
            super(actual, resource, asyncComplete, asyncError, asyncCancel, arbiter);
        }

        @Override
        public void onNext(T value) {
            this.value = value;
        }

        @Override
        public void deferredComplete() {
            this.error = Exceptions.TERMINATED;
            if (this.value != null) {
                this.actual.onNext(this.value);
            }
            this.actual.onComplete();
        }

        @Override
        public void deferredError(Throwable error) {
            Operators.onDiscard(this.value, this.actual.currentContext());
            this.error = error;
            this.actual.onError(error);
        }
    }

    static class ResourceSubscriber<S, T>
    extends Operators.DeferredSubscription
    implements InnerConsumer<S> {
        final CoreSubscriber<? super T> actual;
        final Function<? super S, ? extends Mono<? extends T>> resourceClosure;
        final Function<? super S, ? extends Publisher<?>> asyncComplete;
        final BiFunction<? super S, ? super Throwable, ? extends Publisher<?>> asyncError;
        @Nullable
        final Function<? super S, ? extends Publisher<?>> asyncCancel;
        final boolean isMonoSource;
        Subscription resourceSubscription;
        boolean resourceProvided;

        ResourceSubscriber(CoreSubscriber<? super T> actual, Function<? super S, ? extends Mono<? extends T>> resourceClosure, Function<? super S, ? extends Publisher<?>> asyncComplete, BiFunction<? super S, ? super Throwable, ? extends Publisher<?>> asyncError, @Nullable Function<? super S, ? extends Publisher<?>> asyncCancel, boolean isMonoSource) {
            this.actual = Objects.requireNonNull(actual, "actual");
            this.resourceClosure = Objects.requireNonNull(resourceClosure, "resourceClosure");
            this.asyncComplete = Objects.requireNonNull(asyncComplete, "asyncComplete");
            this.asyncError = Objects.requireNonNull(asyncError, "asyncError");
            this.asyncCancel = asyncCancel;
            this.isMonoSource = isMonoSource;
        }

        @Override
        public Context currentContext() {
            return this.actual.currentContext();
        }

        public void onNext(S resource) {
            if (this.resourceProvided) {
                Operators.onNextDropped(resource, this.actual.currentContext());
                return;
            }
            this.resourceProvided = true;
            Mono p = MonoUsingWhen.deriveMonoFromResource(resource, this.resourceClosure);
            p.subscribe(MonoUsingWhen.prepareSubscriberForResource(resource, this.actual, this.asyncComplete, this.asyncError, this.asyncCancel, this));
            if (!this.isMonoSource) {
                this.resourceSubscription.cancel();
            }
        }

        public void onError(Throwable throwable) {
            if (this.resourceProvided) {
                Operators.onErrorDropped(throwable, this.actual.currentContext());
                return;
            }
            this.actual.onError(throwable);
        }

        public void onComplete() {
            if (this.resourceProvided) {
                return;
            }
            this.actual.onComplete();
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (Operators.validate(this.resourceSubscription, s)) {
                this.resourceSubscription = s;
                this.actual.onSubscribe(this);
                s.request(Long.MAX_VALUE);
            }
        }

        @Override
        public void cancel() {
            if (!this.resourceProvided) {
                this.resourceSubscription.cancel();
            }
            super.cancel();
        }

        @Override
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.PARENT) {
                return this.resourceSubscription;
            }
            if (key == Scannable.Attr.ACTUAL) {
                return this.actual;
            }
            if (key == Scannable.Attr.PREFETCH) {
                return Integer.MAX_VALUE;
            }
            if (key == Scannable.Attr.TERMINATED) {
                return this.resourceProvided;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return null;
        }
    }
}

