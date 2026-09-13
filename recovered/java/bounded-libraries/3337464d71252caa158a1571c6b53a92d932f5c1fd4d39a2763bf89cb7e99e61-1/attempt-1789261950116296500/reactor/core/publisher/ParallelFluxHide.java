/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.FluxHide;
import reactor.core.publisher.ParallelFlux;
import reactor.util.annotation.Nullable;

final class ParallelFluxHide<T>
extends ParallelFlux<T>
implements Scannable {
    final ParallelFlux<T> source;

    ParallelFluxHide(ParallelFlux<T> source) {
        this.source = source;
    }

    @Override
    public int getPrefetch() {
        return this.source.getPrefetch();
    }

    @Override
    public int parallelism() {
        return this.source.parallelism();
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

    @Override
    public void subscribe(CoreSubscriber<? super T>[] subscribers) {
        if (!this.validate(subscribers)) {
            return;
        }
        int n = subscribers.length;
        CoreSubscriber[] parents = new CoreSubscriber[n];
        for (int i = 0; i < n; ++i) {
            parents[i] = new FluxHide.HideSubscriber<T>(subscribers[i]);
        }
        this.source.subscribe(parents);
    }
}

