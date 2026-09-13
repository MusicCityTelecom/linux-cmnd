/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import reactor.core.CorePublisher;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.InternalFluxOperator;
import reactor.core.publisher.Operators;

final class FluxRetry<T>
extends InternalFluxOperator<T, T> {
    final long times;

    FluxRetry(Flux<? extends T> source, long times) {
        super(source);
        if (times < 0L) {
            throw new IllegalArgumentException("times >= 0 required but it was " + times);
        }
        this.times = times;
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> actual) {
        RetrySubscriber<T> parent = new RetrySubscriber<T>(this.source, actual, this.times);
        actual.onSubscribe(parent);
        if (!parent.isCancelled()) {
            parent.resubscribe();
        }
        return null;
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static final class RetrySubscriber<T>
    extends Operators.MultiSubscriptionSubscriber<T, T> {
        final CorePublisher<? extends T> source;
        long remaining;
        volatile int wip;
        static final AtomicIntegerFieldUpdater<RetrySubscriber> WIP = AtomicIntegerFieldUpdater.newUpdater(RetrySubscriber.class, "wip");
        long produced;

        RetrySubscriber(CorePublisher<? extends T> source, CoreSubscriber<? super T> actual, long remaining) {
            super(actual);
            this.source = source;
            this.remaining = remaining;
        }

        public void onNext(T t) {
            ++this.produced;
            this.actual.onNext(t);
        }

        @Override
        public void onError(Throwable t) {
            long r = this.remaining;
            if (r != Long.MAX_VALUE) {
                if (r == 0L) {
                    this.actual.onError(t);
                    return;
                }
                this.remaining = r - 1L;
            }
            this.resubscribe();
        }

        void resubscribe() {
            if (WIP.getAndIncrement(this) == 0) {
                do {
                    if (this.isCancelled()) {
                        return;
                    }
                    long c = this.produced;
                    if (c != 0L) {
                        this.produced = 0L;
                        this.produced(c);
                    }
                    this.source.subscribe(this);
                } while (WIP.decrementAndGet(this) != 0);
            }
        }

        @Override
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return super.scanUnsafe(key);
        }
    }
}

