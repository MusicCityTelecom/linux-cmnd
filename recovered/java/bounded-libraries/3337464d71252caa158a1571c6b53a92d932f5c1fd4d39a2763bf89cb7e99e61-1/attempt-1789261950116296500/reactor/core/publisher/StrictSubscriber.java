/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Subscriber
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Scannable;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

final class StrictSubscriber<T>
implements Scannable,
CoreSubscriber<T>,
Subscription {
    final Subscriber<? super T> actual;
    volatile Subscription s;
    static final AtomicReferenceFieldUpdater<StrictSubscriber, Subscription> S = AtomicReferenceFieldUpdater.newUpdater(StrictSubscriber.class, Subscription.class, "s");
    volatile long requested;
    static final AtomicLongFieldUpdater<StrictSubscriber> REQUESTED = AtomicLongFieldUpdater.newUpdater(StrictSubscriber.class, "requested");
    volatile int wip;
    static final AtomicIntegerFieldUpdater<StrictSubscriber> WIP = AtomicIntegerFieldUpdater.newUpdater(StrictSubscriber.class, "wip");
    volatile Throwable error;
    static final AtomicReferenceFieldUpdater<StrictSubscriber, Throwable> ERROR = AtomicReferenceFieldUpdater.newUpdater(StrictSubscriber.class, Throwable.class, "error");
    volatile boolean done;

    StrictSubscriber(Subscriber<? super T> actual) {
        this.actual = actual;
    }

    @Override
    public void onSubscribe(Subscription s) {
        if (Operators.validate(this.s, s)) {
            long r;
            this.actual.onSubscribe((Subscription)this);
            if (Operators.setOnce(S, this, s) && (r = REQUESTED.getAndSet(this, 0L)) != 0L) {
                s.request(r);
            }
        } else {
            this.onError(new IllegalStateException("\u00a72.12 violated: onSubscribe must be called at most once"));
        }
    }

    public void onNext(T t) {
        if (WIP.get(this) == 0 && WIP.compareAndSet(this, 0, 1)) {
            this.actual.onNext(t);
            if (WIP.decrementAndGet(this) != 0) {
                Throwable ex = Exceptions.terminate(ERROR, this);
                if (ex != null) {
                    this.actual.onError(ex);
                } else {
                    this.actual.onComplete();
                }
            }
        }
    }

    public void onError(Throwable t) {
        this.done = true;
        if (Exceptions.addThrowable(ERROR, this, t)) {
            if (WIP.getAndIncrement(this) == 0) {
                this.actual.onError(Exceptions.terminate(ERROR, this));
            }
        } else {
            Operators.onErrorDropped(t, Context.empty());
        }
    }

    public void onComplete() {
        this.done = true;
        if (WIP.getAndIncrement(this) == 0) {
            Throwable ex = Exceptions.terminate(ERROR, this);
            if (ex != null) {
                this.actual.onError(ex);
            } else {
                this.actual.onComplete();
            }
        }
    }

    public void request(long n) {
        if (n <= 0L) {
            this.cancel();
            this.onError(new IllegalArgumentException("\u00a73.9 violated: positive request amount required but it was " + n));
            return;
        }
        Subscription a = this.s;
        if (a != null) {
            a.request(n);
        } else {
            long r;
            Operators.addCap(REQUESTED, this, n);
            a = this.s;
            if (a != null && (r = REQUESTED.getAndSet(this, 0L)) != 0L) {
                a.request(n);
            }
        }
    }

    public void cancel() {
        if (!this.done) {
            Operators.terminate(S, this);
        }
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
        if (key == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM) {
            return this.requested;
        }
        if (key == Scannable.Attr.ACTUAL) {
            return this.actual;
        }
        return null;
    }

    @Override
    public Context currentContext() {
        return Context.empty();
    }
}

