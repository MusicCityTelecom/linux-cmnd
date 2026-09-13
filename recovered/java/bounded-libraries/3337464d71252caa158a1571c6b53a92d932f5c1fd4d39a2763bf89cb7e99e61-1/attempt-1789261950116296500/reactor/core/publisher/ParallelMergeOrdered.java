/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.Comparator;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxMergeComparing;
import reactor.core.publisher.ParallelFlux;
import reactor.util.annotation.Nullable;

final class ParallelMergeOrdered<T>
extends Flux<T>
implements Scannable {
    final ParallelFlux<? extends T> source;
    final int prefetch;
    final Comparator<? super T> valueComparator;

    ParallelMergeOrdered(ParallelFlux<? extends T> source, int prefetch, Comparator<? super T> valueComparator) {
        if (prefetch <= 0) {
            throw new IllegalArgumentException("prefetch > 0 required but it was " + prefetch);
        }
        this.source = source;
        this.prefetch = prefetch;
        this.valueComparator = valueComparator;
    }

    @Override
    public int getPrefetch() {
        return this.prefetch;
    }

    @Override
    @Nullable
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.PARENT) {
            return this.source;
        }
        if (key == Scannable.Attr.PREFETCH) {
            return this.prefetch;
        }
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return null;
    }

    @Override
    public void subscribe(CoreSubscriber<? super T> actual) {
        FluxMergeComparing.MergeOrderedMainProducer<? super T> main = new FluxMergeComparing.MergeOrderedMainProducer<T>(actual, this.valueComparator, this.prefetch, this.source.parallelism(), true);
        actual.onSubscribe(main);
        this.source.subscribe(main.subscribers);
    }
}

