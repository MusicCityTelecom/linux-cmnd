/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.Consumer;
import org.reactivestreams.Subscription;
import reactor.core.Disposable;
import reactor.core.Exceptions;
import reactor.core.Scannable;
import reactor.core.publisher.InnerConsumer;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

final class LambdaSubscriber<T>
implements InnerConsumer<T>,
Disposable {
    final Consumer<? super T> consumer;
    final Consumer<? super Throwable> errorConsumer;
    final Runnable completeConsumer;
    final Consumer<? super Subscription> subscriptionConsumer;
    final Context initialContext;
    volatile Subscription subscription;
    static final AtomicReferenceFieldUpdater<LambdaSubscriber, Subscription> S = AtomicReferenceFieldUpdater.newUpdater(LambdaSubscriber.class, Subscription.class, "subscription");

    LambdaSubscriber(@Nullable Consumer<? super T> consumer, @Nullable Consumer<? super Throwable> errorConsumer, @Nullable Runnable completeConsumer, @Nullable Consumer<? super Subscription> subscriptionConsumer, @Nullable Context initialContext) {
        this.consumer = consumer;
        this.errorConsumer = errorConsumer;
        this.completeConsumer = completeConsumer;
        this.subscriptionConsumer = subscriptionConsumer;
        this.initialContext = initialContext == null ? Context.empty() : initialContext;
    }

    LambdaSubscriber(@Nullable Consumer<? super T> consumer, @Nullable Consumer<? super Throwable> errorConsumer, @Nullable Runnable completeConsumer, @Nullable Consumer<? super Subscription> subscriptionConsumer) {
        this(consumer, errorConsumer, completeConsumer, subscriptionConsumer, null);
    }

    @Override
    public Context currentContext() {
        return this.initialContext;
    }

    @Override
    public final void onSubscribe(Subscription s) {
        if (Operators.validate(this.subscription, s)) {
            this.subscription = s;
            if (this.subscriptionConsumer != null) {
                try {
                    this.subscriptionConsumer.accept((Subscription)s);
                }
                catch (Throwable t) {
                    Exceptions.throwIfFatal(t);
                    s.cancel();
                    this.onError(t);
                }
            } else {
                s.request(Long.MAX_VALUE);
            }
        }
    }

    public final void onComplete() {
        Subscription s = S.getAndSet(this, Operators.cancelledSubscription());
        if (s == Operators.cancelledSubscription()) {
            return;
        }
        if (this.completeConsumer != null) {
            try {
                this.completeConsumer.run();
            }
            catch (Throwable t) {
                Exceptions.throwIfFatal(t);
                this.onError(t);
            }
        }
    }

    public final void onError(Throwable t) {
        Subscription s = S.getAndSet(this, Operators.cancelledSubscription());
        if (s == Operators.cancelledSubscription()) {
            Operators.onErrorDropped(t, this.initialContext);
            return;
        }
        if (this.errorConsumer != null) {
            this.errorConsumer.accept(t);
        } else {
            Operators.onErrorDropped(Exceptions.errorCallbackNotImplemented(t), this.initialContext);
        }
    }

    public final void onNext(T x) {
        try {
            if (this.consumer != null) {
                this.consumer.accept(x);
            }
        }
        catch (Throwable t) {
            Exceptions.throwIfFatal(t);
            this.subscription.cancel();
            this.onError(t);
        }
    }

    @Override
    @Nullable
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.PARENT) {
            return this.subscription;
        }
        if (key == Scannable.Attr.PREFETCH) {
            return Integer.MAX_VALUE;
        }
        if (key == Scannable.Attr.TERMINATED || key == Scannable.Attr.CANCELLED) {
            return this.isDisposed();
        }
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return null;
    }

    @Override
    public boolean isDisposed() {
        return this.subscription == Operators.cancelledSubscription();
    }

    @Override
    public void dispose() {
        Subscription s = S.getAndSet(this, Operators.cancelledSubscription());
        if (s != null && s != Operators.cancelledSubscription()) {
            s.cancel();
        }
    }
}

