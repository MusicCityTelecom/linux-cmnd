/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Publisher
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.Queue;
import java.util.function.IntFunction;
import java.util.function.Supplier;
import org.reactivestreams.Publisher;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxArray;
import reactor.core.publisher.FluxFlatMap;
import reactor.core.publisher.SourceProducer;

final class FluxMerge<T>
extends Flux<T>
implements SourceProducer<T> {
    final Publisher<? extends T>[] sources;
    final boolean delayError;
    final int maxConcurrency;
    final Supplier<? extends Queue<T>> mainQueueSupplier;
    final int prefetch;
    final Supplier<? extends Queue<T>> innerQueueSupplier;

    FluxMerge(Publisher<? extends T>[] sources, boolean delayError, int maxConcurrency, Supplier<? extends Queue<T>> mainQueueSupplier, int prefetch, Supplier<? extends Queue<T>> innerQueueSupplier) {
        if (prefetch <= 0) {
            throw new IllegalArgumentException("prefetch > 0 required but it was " + prefetch);
        }
        if (maxConcurrency <= 0) {
            throw new IllegalArgumentException("maxConcurrency > 0 required but it was " + maxConcurrency);
        }
        this.sources = Objects.requireNonNull(sources, "sources");
        this.delayError = delayError;
        this.maxConcurrency = maxConcurrency;
        this.prefetch = prefetch;
        this.mainQueueSupplier = Objects.requireNonNull(mainQueueSupplier, "mainQueueSupplier");
        this.innerQueueSupplier = Objects.requireNonNull(innerQueueSupplier, "innerQueueSupplier");
    }

    @Override
    public void subscribe(CoreSubscriber<? super T> actual) {
        FluxFlatMap.FlatMapMain merger = new FluxFlatMap.FlatMapMain(actual, FluxMerge.identityFunction(), this.delayError, this.maxConcurrency, this.mainQueueSupplier, this.prefetch, this.innerQueueSupplier);
        merger.onSubscribe(new FluxArray.ArraySubscription<Publisher<? extends T>>(merger, this.sources));
    }

    FluxMerge<T> mergeAdditionalSource(Publisher<? extends T> source, IntFunction<Supplier<? extends Queue<T>>> newQueueSupplier) {
        int n = this.sources.length;
        Publisher[] newArray = new Publisher[n + 1];
        System.arraycopy(this.sources, 0, newArray, 0, n);
        newArray[n] = source;
        int mc = this.maxConcurrency;
        Supplier<Queue<Object>> newMainQueue = mc != Integer.MAX_VALUE ? newQueueSupplier.apply(++mc) : this.mainQueueSupplier;
        return new FluxMerge<T>(newArray, this.delayError, mc, newMainQueue, this.prefetch, this.innerQueueSupplier);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.DELAY_ERROR) {
            return this.delayError;
        }
        if (key == Scannable.Attr.PREFETCH) {
            return this.prefetch;
        }
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return null;
    }
}

