/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.FluxTimed;
import reactor.core.publisher.InternalMonoOperator;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Timed;
import reactor.core.scheduler.Scheduler;
import reactor.util.annotation.Nullable;

final class MonoTimed<T>
extends InternalMonoOperator<T, Timed<T>> {
    final Scheduler clock;

    MonoTimed(Mono<? extends T> source, Scheduler clock) {
        super(source);
        this.clock = clock;
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super Timed<T>> actual) {
        return new FluxTimed.TimedSubscriber(actual, this.clock);
    }

    @Override
    @Nullable
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.PREFETCH) {
            return 0;
        }
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }
}

