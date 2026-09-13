/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.FluxRetry;
import reactor.core.publisher.InternalMonoOperator;
import reactor.core.publisher.Mono;

final class MonoRetry<T>
extends InternalMonoOperator<T, T> {
    final long times;

    MonoRetry(Mono<? extends T> source, long times) {
        super(source);
        if (times < 0L) {
            throw new IllegalArgumentException("times >= 0 required");
        }
        this.times = times;
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> actual) {
        FluxRetry.RetrySubscriber<T> parent = new FluxRetry.RetrySubscriber<T>(this.source, actual, this.times);
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
}

