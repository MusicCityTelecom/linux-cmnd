/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.Iterator;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.function.Function;
import java.util.function.Supplier;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.FluxFlattenIterable;
import reactor.core.publisher.FluxFromMonoOperator;
import reactor.core.publisher.FluxIterable;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Operators;

final class MonoFlattenIterable<T, R>
extends FluxFromMonoOperator<T, R>
implements Fuseable {
    final Function<? super T, ? extends Iterable<? extends R>> mapper;
    final int prefetch;
    final Supplier<Queue<T>> queueSupplier;

    MonoFlattenIterable(Mono<? extends T> source, Function<? super T, ? extends Iterable<? extends R>> mapper, int prefetch, Supplier<Queue<T>> queueSupplier) {
        super(source);
        if (prefetch <= 0) {
            throw new IllegalArgumentException("prefetch > 0 required but it was " + prefetch);
        }
        this.mapper = Objects.requireNonNull(mapper, "mapper");
        this.prefetch = prefetch;
        this.queueSupplier = Objects.requireNonNull(queueSupplier, "queueSupplier");
    }

    @Override
    public int getPrefetch() {
        return this.prefetch;
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super R> actual) throws Exception {
        if (this.source instanceof Callable) {
            Object v = ((Callable)((Object)this.source)).call();
            if (v == null) {
                Operators.complete(actual);
                return null;
            }
            Iterable<R> iter = this.mapper.apply(v);
            Iterator<? extends R> it = iter.iterator();
            boolean itFinite = FluxIterable.checkFinite(iter);
            FluxIterable.subscribe(actual, it, itFinite);
            return null;
        }
        return new FluxFlattenIterable.FlattenIterableSubscriber<T, R>(actual, this.mapper, this.prefetch, this.queueSupplier);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }
}

