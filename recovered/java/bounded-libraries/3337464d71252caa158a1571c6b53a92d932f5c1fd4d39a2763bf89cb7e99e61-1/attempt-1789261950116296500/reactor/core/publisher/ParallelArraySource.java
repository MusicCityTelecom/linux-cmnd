/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Publisher
 */
package reactor.core.publisher;

import org.reactivestreams.Publisher;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.ParallelFlux;
import reactor.core.publisher.SourceProducer;

final class ParallelArraySource<T>
extends ParallelFlux<T>
implements SourceProducer<T> {
    final Publisher<T>[] sources;

    ParallelArraySource(Publisher<T>[] sources) {
        if (sources == null || sources.length == 0) {
            throw new IllegalArgumentException("Zero publishers not supported");
        }
        this.sources = sources;
    }

    @Override
    public int parallelism() {
        return this.sources.length;
    }

    @Override
    public void subscribe(CoreSubscriber<? super T>[] subscribers) {
        if (!this.validate(subscribers)) {
            return;
        }
        int n = subscribers.length;
        for (int i = 0; i < n; ++i) {
            Flux.from(this.sources[i]).subscribe(subscribers[i]);
        }
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return SourceProducer.super.scanUnsafe(key);
    }
}

