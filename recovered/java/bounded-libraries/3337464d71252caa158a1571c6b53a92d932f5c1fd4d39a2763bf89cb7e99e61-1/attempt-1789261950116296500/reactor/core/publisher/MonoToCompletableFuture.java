/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.Operators;
import reactor.util.context.Context;

final class MonoToCompletableFuture<T>
extends CompletableFuture<T>
implements CoreSubscriber<T> {
    final AtomicReference<Subscription> ref = new AtomicReference();
    final boolean cancelSourceOnNext;

    MonoToCompletableFuture(boolean sourceCanEmitMoreThanOnce) {
        this.cancelSourceOnNext = sourceCanEmitMoreThanOnce;
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        Subscription s;
        boolean cancelled = super.cancel(mayInterruptIfRunning);
        if (cancelled && (s = (Subscription)this.ref.getAndSet(null)) != null) {
            s.cancel();
        }
        return cancelled;
    }

    @Override
    public void onSubscribe(Subscription s) {
        if (Operators.validate(this.ref.getAndSet(s), s)) {
            s.request(Long.MAX_VALUE);
        } else {
            s.cancel();
        }
    }

    public void onNext(T t) {
        Subscription s = this.ref.getAndSet(null);
        if (s != null) {
            this.complete(t);
            if (this.cancelSourceOnNext) {
                s.cancel();
            }
        } else {
            Operators.onNextDropped(t, this.currentContext());
        }
    }

    public void onError(Throwable t) {
        if (this.ref.getAndSet(null) != null) {
            this.completeExceptionally(t);
        }
    }

    public void onComplete() {
        if (this.ref.getAndSet(null) != null) {
            this.complete(null);
        }
    }

    @Override
    public Context currentContext() {
        return Context.empty();
    }
}

