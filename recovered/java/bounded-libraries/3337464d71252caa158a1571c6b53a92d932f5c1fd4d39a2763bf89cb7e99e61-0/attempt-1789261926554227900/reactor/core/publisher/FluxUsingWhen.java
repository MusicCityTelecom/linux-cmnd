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
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.InnerConsumer;
import reactor.core.publisher.InnerOperator;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Operators;
import reactor.core.publisher.SourceProducer;
import reactor.util.Loggers;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

final class FluxUsingWhen<T, S>
extends Flux<T>
implements SourceProducer<T> {
    final Publisher<S> resourceSupplier;
    final Function<? super S, ? extends Publisher<? extends T>> resourceClosure;
    final Function<? super S, ? extends Publisher<?>> asyncComplete;
    final BiFunction<? super S, ? super Throwable, ? extends Publisher<?>> asyncError;
    @Nullable
    final Function<? super S, ? extends Publisher<?>> asyncCancel;

    FluxUsingWhen(Publisher<S> resourceSupplier, Function<? super S, ? extends Publisher<? extends T>> resourceClosure, Function<? super S, ? extends Publisher<?>> asyncComplete, BiFunction<? super S, ? super Throwable, ? extends Publisher<?>> asyncError, @Nullable Function<? super S, ? extends Publisher<?>> asyncCancel) {
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
                    Publisher<T> p = FluxUsingWhen.deriveFluxFromResource(resource, this.resourceClosure);
                    UsingWhenSubscriber<? super T, ? super S> subscriber = FluxUsingWhen.prepareSubscriberForResource(resource, actual, this.asyncComplete, this.asyncError, this.asyncCancel, null);
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

    private static <RESOURCE, T> Publisher<? extends T> deriveFluxFromResource(RESOURCE resource, Function<? super RESOURCE, ? extends Publisher<? extends T>> resourceClosure) {
        Flux p;
        try {
            p = Objects.requireNonNull(resourceClosure.apply(resource), "The resourceClosure function returned a null value");
        }
        catch (Throwable e) {
            p = Flux.error(e);
        }
        return p;
    }

    private static <RESOURCE, T> UsingWhenSubscriber<? super T, RESOURCE> prepareSubscriberForResource(RESOURCE resource, CoreSubscriber<? super T> actual, Function<? super RESOURCE, ? extends Publisher<?>> asyncComplete, BiFunction<? super RESOURCE, ? super Throwable, ? extends Publisher<?>> asyncError, @Nullable Function<? super RESOURCE, ? extends Publisher<?>> asyncCancel, @Nullable Operators.DeferredSubscription arbiter) {
        if (actual instanceof Fuseable.ConditionalSubscriber) {
            Fuseable.ConditionalSubscriber conditionalActual = (Fuseable.ConditionalSubscriber)actual;
            return new UsingWhenConditionalSubscriber(conditionalActual, resource, asyncComplete, asyncError, asyncCancel, arbiter);
        }
        return new UsingWhenSubscriber<T, RESOURCE>(actual, resource, asyncComplete, asyncError, asyncCancel, arbiter);
    }

    private static interface UsingWhenParent<T>
    extends InnerOperator<T, T> {
        public void deferredComplete();

        public void deferredError(Throwable var1);
    }

    static final class CancelInner
    implements InnerConsumer<Object> {
        final UsingWhenParent parent;

        CancelInner(UsingWhenParent ts) {
            this.parent = ts;
        }

        @Override
        public Context currentContext() {
            return this.parent.currentContext();
        }

        @Override
        public void onSubscribe(Subscription s) {
            Objects.requireNonNull(s, "Subscription cannot be null").request(Long.MAX_VALUE);
        }

        public void onNext(Object o) {
        }

        public void onError(Throwable e) {
            Loggers.getLogger(FluxUsingWhen.class).warn("Async resource cleanup failed after cancel", e);
        }

        public void onComplete() {
        }

        @Override
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.PARENT) {
                return this.parent;
            }
            if (key == Scannable.Attr.ACTUAL) {
                return this.parent.actual();
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return null;
        }
    }

    static final class CommitInner
    implements InnerConsumer<Object> {
        final UsingWhenParent parent;
        boolean done;

        CommitInner(UsingWhenParent ts) {
            this.parent = ts;
        }

        @Override
        public Context currentContext() {
            return this.parent.currentContext();
        }

        @Override
        public void onSubscribe(Subscription s) {
            Objects.requireNonNull(s, "Subscription cannot be null").request(Long.MAX_VALUE);
        }

        public void onNext(Object o) {
        }

        public void onError(Throwable e) {
            this.done = true;
            Throwable e_ = Operators.onOperatorError(e, this.parent.currentContext());
            RuntimeException commitError = new RuntimeException("Async resource cleanup failed after onComplete", e_);
            this.parent.deferredError(commitError);
        }

        public void onComplete() {
            this.done = true;
            this.parent.deferredComplete();
        }

        @Override
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.PARENT) {
                return this.parent;
            }
            if (key == Scannable.Attr.ACTUAL) {
                return this.parent.actual();
            }
            if (key == Scannable.Attr.TERMINATED) {
                return this.done;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return null;
        }
    }

    static final class RollbackInner
    implements InnerConsumer<Object> {
        final UsingWhenParent parent;
        final Throwable rollbackCause;
        boolean done;

        RollbackInner(UsingWhenParent ts, Throwable rollbackCause) {
            this.parent = ts;
            this.rollbackCause = rollbackCause;
        }

        @Override
        public Context currentContext() {
            return this.parent.currentContext();
        }

        @Override
        public void onSubscribe(Subscription s) {
            Objects.requireNonNull(s, "Subscription cannot be null").request(Long.MAX_VALUE);
        }

        public void onNext(Object o) {
        }

        public void onError(Throwable e) {
            this.done = true;
            RuntimeException rollbackError = new RuntimeException("Async resource cleanup failed after onError", e);
            this.parent.deferredError(Exceptions.addSuppressed(rollbackError, this.rollbackCause));
        }

        public void onComplete() {
            this.done = true;
            this.parent.deferredError(this.rollbackCause);
        }

        @Override
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.PARENT) {
                return this.parent;
            }
            if (key == Scannable.Attr.ACTUAL) {
                return this.parent.actual();
            }
            if (key == Scannable.Attr.ERROR) {
                return this.rollbackCause;
            }
            if (key == Scannable.Attr.TERMINATED) {
                return this.done;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return null;
        }
    }

    static final class UsingWhenConditionalSubscriber<T, S>
    extends UsingWhenSubscriber<T, S>
    implements Fuseable.ConditionalSubscriber<T> {
        final Fuseable.ConditionalSubscriber<? super T> actual;

        UsingWhenConditionalSubscriber(Fuseable.ConditionalSubscriber<? super T> actual, S resource, Function<? super S, ? extends Publisher<?>> asyncComplete, BiFunction<? super S, ? super Throwable, ? extends Publisher<?>> asyncError, @Nullable Function<? super S, ? extends Publisher<?>> asyncCancel, @Nullable Operators.DeferredSubscription arbiter) {
            super(actual, resource, asyncComplete, asyncError, asyncCancel, arbiter);
            this.actual = actual;
        }

        @Override
        public boolean tryOnNext(T t) {
            return this.actual.tryOnNext(t);
        }
    }

    static class UsingWhenSubscriber<T, S>
    implements UsingWhenParent<T> {
        final CoreSubscriber<? super T> actual;
        final S resource;
        final Function<? super S, ? extends Publisher<?>> asyncComplete;
        final BiFunction<? super S, ? super Throwable, ? extends Publisher<?>> asyncError;
        @Nullable
        final Function<? super S, ? extends Publisher<?>> asyncCancel;
        @Nullable
        final Operators.DeferredSubscription arbiter;
        volatile int callbackApplied;
        static final AtomicIntegerFieldUpdater<UsingWhenSubscriber> CALLBACK_APPLIED = AtomicIntegerFieldUpdater.newUpdater(UsingWhenSubscriber.class, "callbackApplied");
        Throwable error;
        Subscription s;

        UsingWhenSubscriber(CoreSubscriber<? super T> actual, S resource, Function<? super S, ? extends Publisher<?>> asyncComplete, BiFunction<? super S, ? super Throwable, ? extends Publisher<?>> asyncError, @Nullable Function<? super S, ? extends Publisher<?>> asyncCancel, @Nullable Operators.DeferredSubscription arbiter) {
            this.actual = actual;
            this.resource = resource;
            this.asyncComplete = asyncComplete;
            this.asyncError = asyncError;
            this.asyncCancel = asyncCancel;
            this.arbiter = arbiter;
        }

        @Override
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.TERMINATED) {
                return this.error != null;
            }
            if (key == Scannable.Attr.ERROR) {
                return this.error == Exceptions.TERMINATED ? null : this.error;
            }
            if (key == Scannable.Attr.CANCELLED) {
                return this.callbackApplied == 3;
            }
            if (key == Scannable.Attr.PARENT) {
                return this.s;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return UsingWhenParent.super.scanUnsafe(key);
        }

        public void request(long l) {
            if (Operators.validate(l)) {
                this.s.request(l);
            }
        }

        public void cancel() {
            if (CALLBACK_APPLIED.compareAndSet(this, 0, 3)) {
                this.s.cancel();
                try {
                    if (this.asyncCancel != null) {
                        Flux.from(this.asyncCancel.apply(this.resource)).subscribe(new CancelInner(this));
                    } else {
                        Flux.from(this.asyncComplete.apply(this.resource)).subscribe(new CancelInner(this));
                    }
                }
                catch (Throwable error) {
                    Loggers.getLogger(FluxUsingWhen.class).warn("Error generating async resource cleanup during onCancel", error);
                }
            }
        }

        public void onNext(T t) {
            this.actual.onNext(t);
        }

        public void onError(Throwable t) {
            if (CALLBACK_APPLIED.compareAndSet(this, 0, 2)) {
                Publisher<?> p;
                try {
                    p = Objects.requireNonNull(this.asyncError.apply(this.resource, t), "The asyncError returned a null Publisher");
                }
                catch (Throwable e) {
                    Throwable _e = Operators.onOperatorError(e, this.actual.currentContext());
                    _e = Exceptions.addSuppressed(_e, t);
                    this.actual.onError(_e);
                    return;
                }
                p.subscribe((Subscriber)new RollbackInner(this, t));
            }
        }

        public void onComplete() {
            if (CALLBACK_APPLIED.compareAndSet(this, 0, 1)) {
                Publisher<?> p;
                try {
                    p = Objects.requireNonNull(this.asyncComplete.apply(this.resource), "The asyncComplete returned a null Publisher");
                }
                catch (Throwable e) {
                    Throwable _e = Operators.onOperatorError(e, this.actual.currentContext());
                    this.deferredError(_e);
                    return;
                }
                p.subscribe((Subscriber)new CommitInner(this));
            }
        }

        @Override
        public void deferredComplete() {
            this.error = Exceptions.TERMINATED;
            this.actual.onComplete();
        }

        @Override
        public void deferredError(Throwable error) {
            this.error = error;
            this.actual.onError(error);
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (Operators.validate(this.s, s)) {
                this.s = s;
                if (this.arbiter == null) {
                    this.actual.onSubscribe(this);
                } else {
                    this.arbiter.set(this);
                }
            }
        }
    }

    static class ResourceSubscriber<S, T>
    extends Operators.DeferredSubscription
    implements InnerConsumer<S> {
        final CoreSubscriber<? super T> actual;
        final Function<? super S, ? extends Publisher<? extends T>> resourceClosure;
        final Function<? super S, ? extends Publisher<?>> asyncComplete;
        final BiFunction<? super S, ? super Throwable, ? extends Publisher<?>> asyncError;
        @Nullable
        final Function<? super S, ? extends Publisher<?>> asyncCancel;
        final boolean isMonoSource;
        Subscription resourceSubscription;
        boolean resourceProvided;

        ResourceSubscriber(CoreSubscriber<? super T> actual, Function<? super S, ? extends Publisher<? extends T>> resourceClosure, Function<? super S, ? extends Publisher<?>> asyncComplete, BiFunction<? super S, ? super Throwable, ? extends Publisher<?>> asyncError, @Nullable Function<? super S, ? extends Publisher<?>> asyncCancel, boolean isMonoSource) {
            this.actual = Objects.requireNonNull(actual, "actual");
            this.resourceClosure = Objects.requireNonNull(resourceClosure, "resourceClosure");
            this.asyncComplete = Objects.requireNonNull(asyncComplete, "asyncComplete");
            this.asyncError = Objects.requireNonNull(asyncError, "asyncError");
            this.asyncCancel = asyncCancel;
            this.isMonoSource = isMonoSource;
        }

        public void onNext(S resource) {
            if (this.resourceProvided) {
                Operators.onNextDropped(resource, this.actual.currentContext());
                return;
            }
            this.resourceProvided = true;
            Publisher p = FluxUsingWhen.deriveFluxFromResource(resource, this.resourceClosure);
            p.subscribe((Subscriber)FluxUsingWhen.prepareSubscriberForResource(resource, this.actual, this.asyncComplete, this.asyncError, this.asyncCancel, this));
            if (!this.isMonoSource) {
                this.resourceSubscription.cancel();
            }
        }

        @Override
        public Context currentContext() {
            return this.actual.currentContext();
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

