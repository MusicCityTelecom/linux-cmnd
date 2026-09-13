/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.Queue;
import java.util.function.Supplier;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.FluxPublishOn;
import reactor.core.publisher.ParallelFlux;
import reactor.core.scheduler.Scheduler;
import reactor.util.annotation.Nullable;

final class ParallelRunOn<T>
extends ParallelFlux<T>
implements Scannable {
    final ParallelFlux<? extends T> source;
    final Scheduler scheduler;
    final int prefetch;
    final Supplier<Queue<T>> queueSupplier;

    ParallelRunOn(ParallelFlux<? extends T> parent, Scheduler scheduler, int prefetch, Supplier<Queue<T>> queueSupplier) {
        if (prefetch <= 0) {
            throw new IllegalArgumentException("prefetch > 0 required but it was " + prefetch);
        }
        this.source = parent;
        this.scheduler = scheduler;
        this.prefetch = prefetch;
        this.queueSupplier = queueSupplier;
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
            return Scannable.Attr.RunStyle.ASYNC;
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
            Scheduler.Worker w = this.scheduler.createWorker();
            parents[i] = conditional ? new FluxPublishOn.PublishOnConditionalSubscriber<T>((Fuseable.ConditionalSubscriber)subscribers[i], this.scheduler, w, true, this.prefetch, this.prefetch, this.queueSupplier) : new FluxPublishOn.PublishOnSubscriber<T>(subscribers[i], this.scheduler, w, true, this.prefetch, this.prefetch, this.queueSupplier);
        }
        this.source.subscribe(parents);
    }

    @Override
    public int getPrefetch() {
        return this.prefetch;
    }

    @Override
    public int parallelism() {
        return this.source.parallelism();
    }
}

