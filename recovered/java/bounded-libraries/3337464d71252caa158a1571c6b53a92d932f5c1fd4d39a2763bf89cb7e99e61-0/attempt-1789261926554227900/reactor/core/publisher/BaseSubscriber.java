/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Disposable;
import reactor.core.Exceptions;
import reactor.core.publisher.Operators;
import reactor.core.publisher.SignalType;

public abstract class BaseSubscriber<T>
implements CoreSubscriber<T>,
Subscription,
Disposable {
    volatile Subscription subscription;
    static AtomicReferenceFieldUpdater<BaseSubscriber, Subscription> S = AtomicReferenceFieldUpdater.newUpdater(BaseSubscriber.class, Subscription.class, "subscription");

    protected Subscription upstream() {
        return this.subscription;
    }

    @Override
    public boolean isDisposed() {
        return this.subscription == Operators.cancelledSubscription();
    }

    @Override
    public void dispose() {
        this.cancel();
    }

    protected void hookOnSubscribe(Subscription subscription) {
        subscription.request(Long.MAX_VALUE);
    }

    protected void hookOnNext(T value) {
    }

    protected void hookOnComplete() {
    }

    protected void hookOnError(Throwable throwable) {
        throw Exceptions.errorCallbackNotImplemented(throwable);
    }

    protected void hookOnCancel() {
    }

    protected void hookFinally(SignalType type) {
    }

    @Override
    public final void onSubscribe(Subscription s) {
        if (Operators.setOnce(S, this, s)) {
            try {
                this.hookOnSubscribe(s);
            }
            catch (Throwable throwable) {
                this.onError(Operators.onOperatorError(s, throwable, this.currentContext()));
            }
        }
    }

    public final void onNext(T value) {
        Objects.requireNonNull(value, "onNext");
        try {
            this.hookOnNext(value);
        }
        catch (Throwable throwable) {
            this.onError(Operators.onOperatorError(this.subscription, throwable, value, this.currentContext()));
        }
    }

    public final void onError(Throwable t) {
        Objects.requireNonNull(t, "onError");
        if (S.getAndSet(this, Operators.cancelledSubscription()) == Operators.cancelledSubscription()) {
            Operators.onErrorDropped(t, this.currentContext());
            return;
        }
        try {
            this.hookOnError(t);
        }
        catch (Throwable e) {
            e = Exceptions.addSuppressed(e, t);
            Operators.onErrorDropped(e, this.currentContext());
        }
        finally {
            this.safeHookFinally(SignalType.ON_ERROR);
        }
    }

    public final void onComplete() {
        if (S.getAndSet(this, Operators.cancelledSubscription()) != Operators.cancelledSubscription()) {
            try {
                this.hookOnComplete();
            }
            catch (Throwable throwable) {
                this.hookOnError(Operators.onOperatorError(throwable, this.currentContext()));
            }
            finally {
                this.safeHookFinally(SignalType.ON_COMPLETE);
            }
        }
    }

    public final void request(long n) {
        Subscription s;
        if (Operators.validate(n) && (s = this.subscription) != null) {
            s.request(n);
        }
    }

    public final void requestUnbounded() {
        this.request(Long.MAX_VALUE);
    }

    public final void cancel() {
        if (Operators.terminate(S, this)) {
            try {
                this.hookOnCancel();
            }
            catch (Throwable throwable) {
                this.hookOnError(Operators.onOperatorError(this.subscription, throwable, this.currentContext()));
            }
            finally {
                this.safeHookFinally(SignalType.CANCEL);
            }
        }
    }

    void safeHookFinally(SignalType type) {
        try {
            this.hookFinally(type);
        }
        catch (Throwable finallyFailure) {
            Operators.onErrorDropped(finallyFailure, this.currentContext());
        }
    }

    public String toString() {
        return this.getClass().getSimpleName();
    }
}

