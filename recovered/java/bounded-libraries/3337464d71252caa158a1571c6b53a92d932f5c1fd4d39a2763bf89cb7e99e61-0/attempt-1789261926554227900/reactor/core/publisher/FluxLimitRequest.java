/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.InnerOperator;
import reactor.core.publisher.InternalFluxOperator;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;

final class FluxLimitRequest<T>
extends InternalFluxOperator<T, T> {
    final long cap;

    FluxLimitRequest(Flux<T> flux, long cap) {
        super(flux);
        if (cap < 0L) {
            throw new IllegalArgumentException("cap >= 0 required but it was " + cap);
        }
        this.cap = cap;
    }

    @Override
    @Nullable
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> actual) {
        if (this.cap == 0L) {
            Operators.complete(actual);
            return null;
        }
        return new FluxLimitRequestSubscriber<T>(actual, this.cap);
    }

    @Override
    public int getPrefetch() {
        return 0;
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM) {
            return this.cap;
        }
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static class FluxLimitRequestSubscriber<T>
    implements InnerOperator<T, T> {
        final CoreSubscriber<? super T> actual;
        Subscription parent;
        long toProduce;
        boolean done;
        volatile long requestRemaining;
        static final AtomicLongFieldUpdater<FluxLimitRequestSubscriber> REQUEST_REMAINING = AtomicLongFieldUpdater.newUpdater(FluxLimitRequestSubscriber.class, "requestRemaining");

        FluxLimitRequestSubscriber(CoreSubscriber<? super T> actual, long cap) {
            this.actual = actual;
            this.toProduce = cap;
            this.requestRemaining = cap;
        }

        @Override
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        public void onNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, this.actual.currentContext());
                return;
            }
            long r = this.toProduce;
            if (r > 0L) {
                this.toProduce = --r;
                this.actual.onNext(t);
                if (r == 0L) {
                    this.done = true;
                    this.parent.cancel();
                    this.actual.onComplete();
                }
            }
        }

        public void onError(Throwable throwable) {
            if (this.done) {
                Operators.onErrorDropped(throwable, this.currentContext());
                return;
            }
            this.done = true;
            this.actual.onError(throwable);
        }

        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            this.actual.onComplete();
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (Operators.validate(this.parent, s)) {
                this.parent = s;
                this.actual.onSubscribe(this);
            }
        }

        public void request(long l) {
            block1: {
                long newRequest;
                long u;
                long r;
                while (!REQUEST_REMAINING.compareAndSet(this, r, u = r - (newRequest = (r = this.requestRemaining) <= l ? r : l))) {
                }
                if (newRequest == 0L) break block1;
                this.parent.request(newRequest);
            }
        }

        public void cancel() {
            this.parent.cancel();
        }

        @Override
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.PARENT) {
                return this.parent;
            }
            if (key == Scannable.Attr.TERMINATED) {
                return this.done;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return InnerOperator.super.scanUnsafe(key);
        }
    }
}

