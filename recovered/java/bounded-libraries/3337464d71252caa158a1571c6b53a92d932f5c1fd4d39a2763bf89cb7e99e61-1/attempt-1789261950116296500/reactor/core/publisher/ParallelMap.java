/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.function.Function;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.FluxMap;
import reactor.core.publisher.ParallelFlux;
import reactor.util.annotation.Nullable;

final class ParallelMap<T, R>
extends ParallelFlux<R>
implements Scannable {
    final ParallelFlux<T> source;
    final Function<? super T, ? extends R> mapper;

    ParallelMap(ParallelFlux<T> source, Function<? super T, ? extends R> mapper) {
        this.source = source;
        this.mapper = mapper;
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
    public void subscribe(CoreSubscriber<? super R>[] subscribers) {
        if (!this.validate(subscribers)) {
            return;
        }
        int n = subscribers.length;
        CoreSubscriber[] parents = new CoreSubscriber[n];
        boolean conditional = subscribers[0] instanceof Fuseable.ConditionalSubscriber;
        for (int i = 0; i < n; ++i) {
            parents[i] = conditional ? new FluxMap.MapConditionalSubscriber<T, R>((Fuseable.ConditionalSubscriber)subscribers[i], this.mapper) : new FluxMap.MapSubscriber<T, R>(subscribers[i], this.mapper);
        }
        this.source.subscribe(parents);
    }

    @Override
    public int parallelism() {
        return this.source.parallelism();
    }
}

