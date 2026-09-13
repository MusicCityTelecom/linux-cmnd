/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.FluxPeek;
import reactor.core.publisher.FluxPeekFuseable;
import reactor.core.publisher.ParallelFlux;
import reactor.core.publisher.SignalPeek;
import reactor.util.annotation.Nullable;

final class ParallelLog<T>
extends ParallelFlux<T>
implements Scannable {
    final ParallelFlux<T> source;
    final SignalPeek<T> log;

    ParallelLog(ParallelFlux<T> source, SignalPeek<T> log) {
        this.source = source;
        this.log = log;
    }

    @Override
    public void subscribe(CoreSubscriber<? super T>[] subscribers) {
        if (!this.validate(subscribers)) {
            return;
        }
        int n = subscribers.length;
        CoreSubscriber[] parents = new CoreSubscriber[n];
        boolean conditional = subscribers[0] instanceof Fuseable.ConditionalSubscriber;
        for (int i = 0; i < n; ++i) {
            parents[i] = conditional ? new FluxPeekFuseable.PeekConditionalSubscriber<T>((Fuseable.ConditionalSubscriber)subscribers[i], this.log) : new FluxPeek.PeekSubscriber<T>(subscribers[i], this.log);
        }
        this.source.subscribe(parents);
    }

    @Override
    public int parallelism() {
        return this.source.parallelism();
    }

    @Override
    public int getPrefetch() {
        return this.source.getPrefetch();
    }

    @Override
    @Nullable
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.PARENT) {
            return this.source;
        }
        if (key == Scannable.Attr.PREFETCH) {
            return this.getPrefetch();
        }
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return null;
    }
}

