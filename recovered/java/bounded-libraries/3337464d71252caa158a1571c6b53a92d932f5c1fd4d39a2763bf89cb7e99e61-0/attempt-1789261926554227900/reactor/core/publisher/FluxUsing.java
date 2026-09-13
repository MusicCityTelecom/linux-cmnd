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
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.function.Consumer;
import java.util.function.Function;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.InnerOperator;
import reactor.core.publisher.Operators;
import reactor.core.publisher.SourceProducer;
import reactor.util.annotation.Nullable;

final class FluxUsing<T, S>
extends Flux<T>
implements Fuseable,
SourceProducer<T> {
    final Callable<S> resourceSupplier;
    final Function<? super S, ? extends Publisher<? extends T>> sourceFactory;
    final Consumer<? super S> resourceCleanup;
    final boolean eager;

    FluxUsing(Callable<S> resourceSupplier, Function<? super S, ? extends Publisher<? extends T>> sourceFactory, Consumer<? super S> resourceCleanup, boolean eager) {
        this.resourceSupplier = Objects.requireNonNull(resourceSupplier, "resourceSupplier");
        this.sourceFactory = Objects.requireNonNull(sourceFactory, "sourceFactory");
        this.resourceCleanup = Objects.requireNonNull(resourceCleanup, "resourceCleanup");
        this.eager = eager;
    }

    @Override
    public void subscribe(CoreSubscriber<? super T> actual) {
        Publisher<? extends T> p;
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
            Throwable _e = Operators.onOperatorError(e, actual.currentContext());
            try {
                this.resourceCleanup.accept(resource);
            }
            catch (Throwable ex) {
                _e = Exceptions.addSuppressed(ex, _e);
            }
            Operators.error(actual, _e);
            return;
        }
        if (p instanceof Fuseable) {
            FluxUsing.from(p).subscribe(new UsingFuseableSubscriber<T, S>(actual, this.resourceCleanup, resource, this.eager));
        } else if (actual instanceof Fuseable.ConditionalSubscriber) {
            FluxUsing.from(p).subscribe(new UsingConditionalSubscriber((Fuseable.ConditionalSubscriber)actual, this.resourceCleanup, resource, this.eager));
        } else {
            FluxUsing.from(p).subscribe(new UsingSubscriber<T, S>(actual, this.resourceCleanup, resource, this.eager));
        }
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return null;
    }

    static final class UsingConditionalSubscriber<T, S>
    implements Fuseable.ConditionalSubscriber<T>,
    InnerOperator<T, T>,
    Fuseable.QueueSubscription<T> {
        final Fuseable.ConditionalSubscriber<? super T> actual;
        final Consumer<? super S> resourceCleanup;
        final S resource;
        final boolean eager;
        Subscription s;
        volatile int wip;
        static final AtomicIntegerFieldUpdater<UsingConditionalSubscriber> WIP = AtomicIntegerFieldUpdater.newUpdater(UsingConditionalSubscriber.class, "wip");

        UsingConditionalSubscriber(Fuseable.ConditionalSubscriber<? super T> actual, Consumer<? super S> resourceCleanup, S resource, boolean eager) {
            this.actual = actual;
            this.resourceCleanup = resourceCleanup;
            this.resource = resource;
            this.eager = eager;
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
                this.actual.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            this.actual.onNext(t);
        }

        @Override
        public boolean tryOnNext(T t) {
            return this.actual.tryOnNext(t);
        }

        public void onError(Throwable t) {
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
                this.cleanup();
            }
        }

        @Override
        public int requestFusion(int requestedMode) {
            return 0;
        }

        @Override
        public void clear() {
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        @Nullable
        public T poll() {
            return null;
        }

        @Override
        public int size() {
            return 0;
        }
    }

    static final class UsingFuseableSubscriber<T, S>
    implements InnerOperator<T, T>,
    Fuseable.QueueSubscription<T> {
        final CoreSubscriber<? super T> actual;
        final Consumer<? super S> resourceCleanup;
        final S resource;
        final boolean eager;
        Fuseable.QueueSubscription<T> s;
        volatile int wip;
        static final AtomicIntegerFieldUpdater<UsingFuseableSubscriber> WIP = AtomicIntegerFieldUpdater.newUpdater(UsingFuseableSubscriber.class, "wip");
        int mode;

        UsingFuseableSubscriber(CoreSubscriber<? super T> actual, Consumer<? super S> resourceCleanup, S resource, boolean eager) {
            this.actual = actual;
            this.resourceCleanup = resourceCleanup;
            this.resource = resource;
            this.eager = eager;
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
                this.s = (Fuseable.QueueSubscription)s;
                this.actual.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            this.actual.onNext(t);
        }

        public void onError(Throwable t) {
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
                this.cleanup();
            }
        }

        @Override
        public void clear() {
            this.s.clear();
        }

        @Override
        public boolean isEmpty() {
            return this.s.isEmpty();
        }

        @Override
        @Nullable
        public T poll() {
            Object v = this.s.poll();
            if (v == null && this.mode == 1 && WIP.compareAndSet(this, 0, 1)) {
                this.resourceCleanup.accept(this.resource);
            }
            return (T)v;
        }

        @Override
        public int requestFusion(int requestedMode) {
            int m;
            this.mode = m = this.s.requestFusion(requestedMode);
            return m;
        }

        @Override
        public int size() {
            return this.s.size();
        }
    }

    static final class UsingSubscriber<T, S>
    implements InnerOperator<T, T>,
    Fuseable.QueueSubscription<T> {
        final CoreSubscriber<? super T> actual;
        final Consumer<? super S> resourceCleanup;
        final S resource;
        final boolean eager;
        Subscription s;
        volatile int wip;
        static final AtomicIntegerFieldUpdater<UsingSubscriber> WIP = AtomicIntegerFieldUpdater.newUpdater(UsingSubscriber.class, "wip");

        UsingSubscriber(CoreSubscriber<? super T> actual, Consumer<? super S> resourceCleanup, S resource, boolean eager) {
            this.actual = actual;
            this.resourceCleanup = resourceCleanup;
            this.resource = resource;
            this.eager = eager;
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
                this.actual.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            this.actual.onNext(t);
        }

        public void onError(Throwable t) {
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
                this.cleanup();
            }
        }

        @Override
        public int requestFusion(int requestedMode) {
            return 0;
        }

        @Override
        public void clear() {
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        @Nullable
        public T poll() {
            return null;
        }

        @Override
        public int size() {
            return 0;
        }
    }
}

