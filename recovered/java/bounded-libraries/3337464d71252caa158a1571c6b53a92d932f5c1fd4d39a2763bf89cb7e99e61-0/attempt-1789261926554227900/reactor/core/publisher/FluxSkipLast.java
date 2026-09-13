/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import java.util.ArrayDeque;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.InnerOperator;
import reactor.core.publisher.InternalFluxOperator;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;

final class FluxSkipLast<T>
extends InternalFluxOperator<T, T> {
    final int n;

    FluxSkipLast(Flux<? extends T> source, int n) {
        super(source);
        if (n < 0) {
            throw new IllegalArgumentException("n >= 0 required but it was " + n);
        }
        this.n = n;
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> actual) {
        return new SkipLastSubscriber<T>(actual, this.n);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static final class SkipLastSubscriber<T>
    extends ArrayDeque<T>
    implements InnerOperator<T, T> {
        final CoreSubscriber<? super T> actual;
        final int n;
        Subscription s;

        SkipLastSubscriber(CoreSubscriber<? super T> actual, int n) {
            this.actual = actual;
            this.n = n;
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (Operators.validate(this.s, s)) {
                this.s = s;
                this.actual.onSubscribe(this);
                s.request((long)this.n);
            }
        }

        public void onNext(T t) {
            if (this.size() == this.n) {
                this.actual.onNext(this.pollFirst());
            }
            this.offerLast(t);
        }

        public void onError(Throwable t) {
            this.actual.onError(t);
            Operators.onDiscardQueueWithClear(this, this.actual.currentContext(), null);
        }

        public void onComplete() {
            this.actual.onComplete();
            Operators.onDiscardQueueWithClear(this, this.actual.currentContext(), null);
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.PARENT) {
                return this.s;
            }
            if (key == Scannable.Attr.PREFETCH) {
                return this.n;
            }
            if (key == Scannable.Attr.BUFFERED) {
                return this.size();
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
            this.s.cancel();
            Operators.onDiscardQueueWithClear(this, this.actual.currentContext(), null);
        }
    }
}

