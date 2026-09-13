/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.function.Consumer;
import java.util.function.Function;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.InnerOperator;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Operators;
import reactor.core.publisher.SourceProducer;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

final class MonoUsing<T, S>
extends Mono<T>
implements Fuseable,
SourceProducer<T> {
    final Callable<S> resourceSupplier;
    final Function<? super S, ? extends Mono<? extends T>> sourceFactory;
    final Consumer<? super S> resourceCleanup;
    final boolean eager;

    MonoUsing(Callable<S> resourceSupplier, Function<? super S, ? extends Mono<? extends T>> sourceFactory, Consumer<? super S> resourceCleanup, boolean eager) {
        this.resourceSupplier = Objects.requireNonNull(resourceSupplier, "resourceSupplier");
        this.sourceFactory = Objects.requireNonNull(sourceFactory, "sourceFactory");
        this.resourceCleanup = Objects.requireNonNull(resourceCleanup, "resourceCleanup");
        this.eager = eager;
    }

    @Override
    public void subscribe(CoreSubscriber<? super T> actual) {
        Mono<T> p;
        S resource;
        try {
            resource = this.resourceSupplier.call();
        }
        catch (Throwable e) {
            Operators.error(actual, Operators.onOperatorError(e, actual.currentContext()));
            return;
        }
        try {
            p = Objects.requireNonNull(this.sourceFactory.apply(resource), "The sourceFactory returned a null value");
        }
        catch (Throwable e) {
            try {
                this.resourceCleanup.accept(resource);
            }
            catch (Throwable ex) {
                e = Exceptions.addSuppressed(ex, Operators.onOperatorError(e, actual.currentContext()));
            }
            Operators.error(actual, Operators.onOperatorError(e, actual.currentContext()));
            return;
        }
        if (p instanceof Fuseable) {
            p.subscribe(new MonoUsingSubscriber<T, S>(actual, this.resourceCleanup, resource, this.eager, true));
        } else {
            p.subscribe(new MonoUsingSubscriber<T, S>(actual, this.resourceCleanup, resource, this.eager, false));
        }
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return null;
    }

    static final class MonoUsingSubscriber<T, S>
    implements InnerOperator<T, T>,
    Fuseable.QueueSubscription<T> {
        final CoreSubscriber<? super T> actual;
        final Consumer<? super S> resourceCleanup;
        final S resource;
        final boolean eager;
        final boolean allowFusion;
        Subscription s;
        @Nullable
        Fuseable.QueueSubscription<T> qs;
        volatile int wip;
        static final AtomicIntegerFieldUpdater<MonoUsingSubscriber> WIP = AtomicIntegerFieldUpdater.newUpdater(MonoUsingSubscriber.class, "wip");
        int mode;
        boolean valued;

        MonoUsingSubscriber(CoreSubscriber<? super T> actual, Consumer<? super S> resourceCleanup, S resource, boolean eager, boolean allowFusion) {
            this.actual = actual;
            this.resourceCleanup = resourceCleanup;
            this.resource = resource;
            this.eager = eager;
            this.allowFusion = allowFusion;
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.TERMINATED || key == Scannable.Attr.CANCELLED) {
                return this.wip == 1;
            }
            if (key == Scannable.Attr.PARENT) {
                return this.s;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return InnerOperator.super.scanUnsafe(key);
        }

        @Override
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        public void request(long n) {
            this.s.request(n);
        }

        public void cancel() {
            if (WIP.compareAndSet(this, 0, 1)) {
                this.s.cancel();
                this.cleanup();
            }
        }

        void cleanup() {
            try {
                this.resourceCleanup.accept(this.resource);
            }
            catch (Throwable e) {
                Operators.onErrorDropped(e, this.actual.currentContext());
            }
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (Operators.validate(this.s, s)) {
                this.s = s;
                if (s instanceof Fuseable.QueueSubscription) {
                    this.qs = (Fuseable.QueueSubscription)s;
                }
                this.actual.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            if (this.mode == 2) {
                this.actual.onNext(null);
                return;
            }
            this.valued = true;
            if (this.eager && WIP.compareAndSet(this, 0, 1)) {
                try {
                    this.resourceCleanup.accept(this.resource);
                }
                catch (Throwable e) {
                    Context ctx = this.actual.currentContext();
                    this.actual.onError(Operators.onOperatorError(e, ctx));
                    Operators.onDiscard(t, ctx);
                    return;
                }
            }
            this.actual.onNext(t);
            this.actual.onComplete();
            if (!this.eager && WIP.compareAndSet(this, 0, 1)) {
                try {
                    this.resourceCleanup.accept(this.resource);
                }
                catch (Throwable e) {
                    Operators.onErrorDropped(e, this.actual.currentContext());
                }
            }
        }

        public void onError(Throwable t) {
            if (this.valued && this.mode != 2) {
                Operators.onErrorDropped(t, this.actual.currentContext());
                return;
            }
            if (this.eager && WIP.compareAndSet(this, 0, 1)) {
                try {
                    this.resourceCleanup.accept(this.resource);
                }
                catch (Throwable e) {
                    Throwable _e = Operators.onOperatorError(e, this.actual.currentContext());
                    t = Exceptions.addSuppressed(_e, t);
                }
            }
            this.actual.onError(t);
            if (!this.eager && WIP.compareAndSet(this, 0, 1)) {
                this.cleanup();
            }
        }

        public void onComplete() {
            if (this.valued && this.mode != 2) {
                return;
            }
            if (this.eager && WIP.compareAndSet(this, 0, 1)) {
                try {
                    this.resourceCleanup.accept(this.resource);
                }
                catch (Throwable e) {
                    this.actual.onError(Operators.onOperatorError(e, this.actual.currentContext()));
                    return;
                }
            }
            this.actual.onComplete();
            if (!this.eager && WIP.compareAndSet(this, 0, 1)) {
                try {
                    this.resourceCleanup.accept(this.resource);
                }
                catch (Throwable e) {
                    Operators.onErrorDropped(e, this.actual.currentContext());
                }
            }
        }

        @Override
        public void clear() {
            if (this.qs != null) {
                this.qs.clear();
            }
        }

        @Override
        public boolean isEmpty() {
            return this.qs == null || this.qs.isEmpty();
        }

        @Override
        @Nullable
        public T poll() {
            if (this.mode == 0 || this.qs == null) {
                return null;
            }
            Object v = this.qs.poll();
            if (v != null) {
                this.valued = true;
                if (this.eager && WIP.compareAndSet(this, 0, 1)) {
                    try {
                        this.resourceCleanup.accept(this.resource);
                    }
                    catch (Throwable t) {
                        Operators.onDiscard(v, this.actual.currentContext());
                        throw t;
                    }
                }
            } else if (this.mode == 1 && !this.eager && WIP.compareAndSet(this, 0, 1)) {
                try {
                    this.resourceCleanup.accept(this.resource);
                }
                catch (Throwable t) {
                    if (!this.valued) {
                        throw t;
                    }
                    Operators.onErrorDropped(t, this.actual.currentContext());
                }
            }
            return (T)v;
        }

        @Override
        public int requestFusion(int requestedMode) {
            int m;
            if (this.qs == null) {
                this.mode = 0;
                return 0;
            }
            this.mode = m = this.qs.requestFusion(requestedMode);
            return m;
        }

        @Override
        public int size() {
            if (this.qs == null) {
                return 0;
            }
            return this.qs.size();
        }
    }
}

