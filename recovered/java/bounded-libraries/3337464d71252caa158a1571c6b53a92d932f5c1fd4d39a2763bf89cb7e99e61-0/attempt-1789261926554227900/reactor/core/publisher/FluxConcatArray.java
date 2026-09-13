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
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.InnerOperator;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Operators;
import reactor.core.publisher.SourceProducer;
import reactor.util.annotation.Nullable;

final class FluxConcatArray<T>
extends Flux<T>
implements SourceProducer<T> {
    static final Object WORKING = new Object();
    static final Object DONE = new Object();
    final Publisher<? extends T>[] array;
    final boolean delayError;

    @SafeVarargs
    FluxConcatArray(boolean delayError, Publisher<? extends T> ... array) {
        this.array = Objects.requireNonNull(array, "array");
        this.delayError = delayError;
    }

    @Override
    public void subscribe(CoreSubscriber<? super T> actual) {
        Publisher<? extends T>[] a = this.array;
        if (a.length == 0) {
            Operators.complete(actual);
            return;
        }
        if (a.length == 1) {
            Publisher<? extends T> p = a[0];
            if (p == null) {
                Operators.error(actual, new NullPointerException("The single source Publisher is null"));
            } else {
                p.subscribe(actual);
            }
            return;
        }
        if (this.delayError) {
            ConcatArrayDelayErrorSubscriber<T> parent = new ConcatArrayDelayErrorSubscriber<T>(actual, a);
            parent.onComplete();
            return;
        }
        ConcatArraySubscriber<T> parent = new ConcatArraySubscriber<T>(actual, a);
        parent.onComplete();
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.DELAY_ERROR) {
            return this.delayError;
        }
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return null;
    }

    FluxConcatArray<T> concatAdditionalSourceLast(Publisher<? extends T> source) {
        int n = this.array.length;
        Publisher[] newArray = new Publisher[n + 1];
        System.arraycopy(this.array, 0, newArray, 0, n);
        newArray[n] = source;
        return new FluxConcatArray<T>(this.delayError, newArray);
    }

    <V> FluxConcatArray<V> concatAdditionalIgnoredLast(Publisher<? extends V> source) {
        int n = this.array.length;
        Publisher[] newArray = new Publisher[n + 1];
        System.arraycopy(this.array, 0, newArray, 0, n);
        newArray[n - 1] = Mono.ignoreElements(newArray[n - 1]);
        newArray[n] = source;
        return new FluxConcatArray<T>(this.delayError, newArray);
    }

    FluxConcatArray<T> concatAdditionalSourceFirst(Publisher<? extends T> source) {
        int n = this.array.length;
        Publisher[] newArray = new Publisher[n + 1];
        System.arraycopy(this.array, 0, newArray, 1, n);
        newArray[0] = source;
        return new FluxConcatArray<T>(this.delayError, newArray);
    }

    static <T> long activateAndGetRequested(AtomicLongFieldUpdater<T> updater, T instance) {
        long actualRequested;
        long deactivatedRequested;
        while (!updater.compareAndSet(instance, deactivatedRequested = updater.get(instance), actualRequested = deactivatedRequested & Long.MAX_VALUE)) {
        }
        return actualRequested;
    }

    static <T> void deactivateAndProduce(long produced, AtomicLongFieldUpdater<T> updater, T instance) {
        long deactivatedRequested;
        long actualRequested;
        while (!updater.compareAndSet(instance, actualRequested, deactivatedRequested = (actualRequested = updater.get(instance)) == Long.MAX_VALUE ? -1L : actualRequested - produced | Long.MIN_VALUE)) {
        }
    }

    @Nullable
    static <T extends SubscriptionAware> Subscription addCapAndGetSubscription(long n, AtomicLongFieldUpdater<T> updater, T instance) {
        Subscription s;
        long status;
        long actualRequested;
        long state;
        do {
            state = updater.get(instance);
            s = instance.upstream();
            actualRequested = state & Long.MAX_VALUE;
            status = state & Long.MIN_VALUE;
            if (actualRequested != Long.MAX_VALUE) continue;
            return status == Long.MIN_VALUE ? null : s;
        } while (!updater.compareAndSet(instance, state, Operators.addCap(actualRequested, n) | status));
        return status == Long.MIN_VALUE ? null : s;
    }

    static final class ConcatArrayDelayErrorSubscriber<T>
    extends ThreadLocal<Object>
    implements InnerOperator<T, T>,
    SubscriptionAware {
        final CoreSubscriber<? super T> actual;
        final Publisher<? extends T>[] sources;
        int index;
        long produced;
        Subscription s;
        volatile long requested;
        static final AtomicLongFieldUpdater<ConcatArrayDelayErrorSubscriber> REQUESTED = AtomicLongFieldUpdater.newUpdater(ConcatArrayDelayErrorSubscriber.class, "requested");
        volatile Throwable error;
        static final AtomicReferenceFieldUpdater<ConcatArrayDelayErrorSubscriber, Throwable> ERROR = AtomicReferenceFieldUpdater.newUpdater(ConcatArrayDelayErrorSubscriber.class, Throwable.class, "error");
        volatile boolean cancelled;

        ConcatArrayDelayErrorSubscriber(CoreSubscriber<? super T> actual, Publisher<? extends T>[] sources) {
            this.actual = actual;
            this.sources = sources;
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (this.cancelled) {
                this.remove();
                s.cancel();
                return;
            }
            Subscription previousSubscription = this.s;
            this.s = s;
            if (previousSubscription == null) {
                this.actual.onSubscribe(this);
                return;
            }
            long actualRequested = FluxConcatArray.activateAndGetRequested(REQUESTED, this);
            if (actualRequested > 0L) {
                s.request(actualRequested);
            }
        }

        public void onNext(T t) {
            ++this.produced;
            this.actual.onNext(t);
        }

        public void onError(Throwable t) {
            if (!Exceptions.addThrowable(ERROR, this, t)) {
                this.remove();
                Operators.onErrorDropped(t, this.actual.currentContext());
                return;
            }
            this.onComplete();
        }

        public void onComplete() {
            Object state;
            if (this.get() == WORKING) {
                this.set(DONE);
                return;
            }
            Publisher<? extends T>[] a = this.sources;
            do {
                this.set(WORKING);
                int i = this.index;
                if (i == a.length) {
                    this.remove();
                    Throwable e = Exceptions.terminate(ERROR, this);
                    if (e == Exceptions.TERMINATED) {
                        return;
                    }
                    if (e != null) {
                        this.actual.onError(e);
                    } else {
                        this.actual.onComplete();
                    }
                    return;
                }
                Publisher<? extends T> p = a[i];
                if (p == null) {
                    this.remove();
                    if (this.cancelled) {
                        return;
                    }
                    NullPointerException npe = new NullPointerException("Source Publisher at index " + i + " is null");
                    if (!Exceptions.addThrowable(ERROR, this, npe)) {
                        Operators.onErrorDropped(npe, this.actual.currentContext());
                        return;
                    }
                    Throwable throwable = Exceptions.terminate(ERROR, this);
                    if (throwable == Exceptions.TERMINATED) {
                        return;
                    }
                    this.actual.onError(throwable);
                    return;
                }
                long c = this.produced;
                if (c != 0L) {
                    this.produced = 0L;
                    FluxConcatArray.deactivateAndProduce(c, REQUESTED, this);
                }
                this.index = ++i;
                if (this.cancelled) {
                    return;
                }
                p.subscribe((Subscriber)this);
            } while ((state = this.get()) == DONE);
            this.remove();
        }

        public void request(long n) {
            Subscription subscription = FluxConcatArray.addCapAndGetSubscription(n, REQUESTED, this);
            if (subscription == null) {
                return;
            }
            subscription.request(n);
        }

        public void cancel() {
            Throwable throwable;
            this.remove();
            this.cancelled = true;
            if ((this.requested & Long.MIN_VALUE) != Long.MIN_VALUE) {
                this.s.cancel();
            }
            if ((throwable = Exceptions.terminate(ERROR, this)) != null) {
                Operators.onErrorDropped(throwable, this.actual.currentContext());
            }
        }

        @Override
        public Subscription upstream() {
            return this.s;
        }

        @Override
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.DELAY_ERROR) {
                return true;
            }
            if (key == Scannable.Attr.TERMINATED) {
                return this.error == Exceptions.TERMINATED;
            }
            if (key == Scannable.Attr.ERROR) {
                return this.error != Exceptions.TERMINATED ? this.error : null;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            if (key == Scannable.Attr.PARENT) {
                return this.s;
            }
            if (key == Scannable.Attr.CANCELLED) {
                return this.cancelled;
            }
            if (key == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM) {
                return this.requested;
            }
            return InnerOperator.super.scanUnsafe(key);
        }
    }

    static final class ConcatArraySubscriber<T>
    extends ThreadLocal<Object>
    implements InnerOperator<T, T>,
    SubscriptionAware {
        final CoreSubscriber<? super T> actual;
        final Publisher<? extends T>[] sources;
        int index;
        long produced;
        Subscription s;
        volatile long requested;
        static final AtomicLongFieldUpdater<ConcatArraySubscriber> REQUESTED = AtomicLongFieldUpdater.newUpdater(ConcatArraySubscriber.class, "requested");
        volatile boolean cancelled;

        ConcatArraySubscriber(CoreSubscriber<? super T> actual, Publisher<? extends T>[] sources) {
            this.actual = actual;
            this.sources = sources;
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (this.cancelled) {
                this.remove();
                s.cancel();
                return;
            }
            Subscription previousSubscription = this.s;
            this.s = s;
            if (previousSubscription == null) {
                this.actual.onSubscribe(this);
                return;
            }
            long actualRequested = FluxConcatArray.activateAndGetRequested(REQUESTED, this);
            if (actualRequested > 0L) {
                s.request(actualRequested);
            }
        }

        public void onNext(T t) {
            ++this.produced;
            this.actual.onNext(t);
        }

        public void onError(Throwable t) {
            this.remove();
            this.actual.onError(t);
        }

        public void onComplete() {
            Object state;
            if (this.get() == WORKING) {
                this.set(DONE);
                return;
            }
            Publisher<? extends T>[] a = this.sources;
            do {
                this.set(WORKING);
                int i = this.index;
                if (i == a.length) {
                    this.remove();
                    if (this.cancelled) {
                        return;
                    }
                    this.actual.onComplete();
                    return;
                }
                Publisher<? extends T> p = a[i];
                if (p == null) {
                    this.remove();
                    if (this.cancelled) {
                        return;
                    }
                    this.actual.onError(new NullPointerException("Source Publisher at index " + i + " is null"));
                    return;
                }
                long c = this.produced;
                if (c != 0L) {
                    this.produced = 0L;
                    FluxConcatArray.deactivateAndProduce(c, REQUESTED, this);
                }
                this.index = ++i;
                if (this.cancelled) {
                    return;
                }
                p.subscribe((Subscriber)this);
            } while ((state = this.get()) == DONE);
            this.remove();
        }

        public void request(long n) {
            Subscription subscription = FluxConcatArray.addCapAndGetSubscription(n, REQUESTED, this);
            if (subscription == null) {
                return;
            }
            subscription.request(n);
        }

        public void cancel() {
            this.remove();
            this.cancelled = true;
            if ((this.requested & Long.MIN_VALUE) != Long.MIN_VALUE) {
                this.s.cancel();
            }
        }

        @Override
        public Subscription upstream() {
            return this.s;
        }

        @Override
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            if (key == Scannable.Attr.PARENT) {
                return this.s;
            }
            if (key == Scannable.Attr.CANCELLED) {
                return this.cancelled;
            }
            if (key == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM) {
                return this.requested;
            }
            return InnerOperator.super.scanUnsafe(key);
        }
    }

    static interface SubscriptionAware {
        public Subscription upstream();
    }
}

