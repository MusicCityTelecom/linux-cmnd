/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Publisher
 */
package reactor.core.publisher;

import java.util.Queue;
import java.util.function.Function;
import java.util.function.Supplier;
import org.reactivestreams.Publisher;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.FluxFlatMap;
import reactor.core.publisher.ParallelFlux;
import reactor.util.annotation.Nullable;

final class ParallelFlatMap<T, R>
extends ParallelFlux<R>
implements Scannable {
    final ParallelFlux<T> source;
    final Function<? super T, ? extends Publisher<? extends R>> mapper;
    final boolean delayError;
    final int maxConcurrency;
    final Supplier<? extends Queue<R>> mainQueueSupplier;
    final int prefetch;
    final Supplier<? extends Queue<R>> innerQueueSupplier;

    ParallelFlatMap(ParallelFlux<T> source, Function<? super T, ? extends Publisher<? extends R>> mapper, boolean delayError, int maxConcurrency, Supplier<? extends Queue<R>> mainQueueSupplier, int prefetch, Supplier<? extends Queue<R>> innerQueueSupplier) {
        this.source = source;
        this.mapper = mapper;
        this.delayError = delayError;
        this.maxConcurrency = maxConcurrency;
        this.mainQueueSupplier = mainQueueSupplier;
        this.prefetch = prefetch;
        this.innerQueueSupplier = innerQueueSupplier;
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
        if (key == Scannable.Attr.DELAY_ERROR) {
            return this.delayError;
        }
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return null;
    }

    @Override
    public int getPrefetch() {
        return this.prefetch;
    }

    @Override
    public int parallelism() {
        return this.source.parallelism();
    }

    @Override
    public void subscribe(CoreSubscriber<? super R>[] subscribers) {
        if (!this.validate(subscribers)) {
            return;
        }
        int n = subscribers.length;
        CoreSubscriber[] parents = new CoreSubscriber[n];
        for (int i = 0; i < n; ++i) {
            parents[i] = new FluxFlatMap.FlatMapMain<T, R>(subscribers[i], this.mapper, this.delayError, this.maxConcurrency, this.mainQueueSupplier, this.prefetch, this.innerQueueSupplier);
        }
        this.source.subscribe(parents);
    }
}

