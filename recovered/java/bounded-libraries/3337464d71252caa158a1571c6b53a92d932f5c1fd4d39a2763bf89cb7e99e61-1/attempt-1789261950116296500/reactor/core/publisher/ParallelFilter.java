/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.function.Predicate;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.FluxFilter;
import reactor.core.publisher.ParallelFlux;
import reactor.util.annotation.Nullable;

final class ParallelFilter<T>
extends ParallelFlux<T>
implements Scannable {
    final ParallelFlux<T> source;
    final Predicate<? super T> predicate;

    ParallelFilter(ParallelFlux<T> source, Predicate<? super T> predicate) {
        this.source = source;
        this.predicate = predicate;
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
        boolean conditional = subscribers[0] instanceof Fuseable.ConditionalSubscriber;
        for (int i = 0; i < n; ++i) {
            parents[i] = conditional ? new FluxFilter.FilterConditionalSubscriber<T>((Fuseable.ConditionalSubscriber)subscribers[i], this.predicate) : new FluxFilter.FilterSubscriber<T>(subscribers[i], this.predicate);
        }
        this.source.subscribe(parents);
    }

    @Override
    public int parallelism() {
        return this.source.parallelism();
    }
}

