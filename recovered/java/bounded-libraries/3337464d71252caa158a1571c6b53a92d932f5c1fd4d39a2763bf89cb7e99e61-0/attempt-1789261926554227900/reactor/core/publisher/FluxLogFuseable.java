/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxPeekFuseable;
import reactor.core.publisher.InternalFluxOperator;
import reactor.core.publisher.SignalPeek;

final class FluxLogFuseable<T>
extends InternalFluxOperator<T, T>
implements Fuseable {
    final SignalPeek<T> log;

    FluxLogFuseable(Flux<? extends T> source, SignalPeek<T> log) {
        super(source);
        this.log = log;
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> actual) {
        if (actual instanceof Fuseable.ConditionalSubscriber) {
            return new FluxPeekFuseable.PeekFuseableConditionalSubscriber<T>((Fuseable.ConditionalSubscriber)actual, this.log);
        }
        return new FluxPeekFuseable.PeekFuseableSubscriber<T>(actual, this.log);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }
}

