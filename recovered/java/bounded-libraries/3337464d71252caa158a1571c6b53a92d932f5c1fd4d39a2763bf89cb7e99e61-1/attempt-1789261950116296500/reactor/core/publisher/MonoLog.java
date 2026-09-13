/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.FluxPeek;
import reactor.core.publisher.FluxPeekFuseable;
import reactor.core.publisher.InternalMonoOperator;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SignalPeek;

final class MonoLog<T>
extends InternalMonoOperator<T, T> {
    final SignalPeek<T> log;

    MonoLog(Mono<? extends T> source, SignalPeek<T> log) {
        super(source);
        this.log = log;
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> actual) {
        if (actual instanceof Fuseable.ConditionalSubscriber) {
            Fuseable.ConditionalSubscriber s2 = (Fuseable.ConditionalSubscriber)actual;
            return new FluxPeekFuseable.PeekConditionalSubscriber<T>(s2, this.log);
        }
        return new FluxPeek.PeekSubscriber<T>(actual, this.log);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }
}

