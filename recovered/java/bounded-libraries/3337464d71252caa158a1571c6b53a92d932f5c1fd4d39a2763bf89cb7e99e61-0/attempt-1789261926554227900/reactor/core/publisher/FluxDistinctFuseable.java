/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxDistinct;
import reactor.core.publisher.InternalFluxOperator;

final class FluxDistinctFuseable<T, K, C>
extends InternalFluxOperator<T, T>
implements Fuseable {
    final Function<? super T, ? extends K> keyExtractor;
    final Supplier<C> collectionSupplier;
    final BiPredicate<C, K> distinctPredicate;
    final Consumer<C> cleanupCallback;

    FluxDistinctFuseable(Flux<? extends T> source, Function<? super T, ? extends K> keyExtractor, Supplier<C> collectionSupplier, BiPredicate<C, K> distinctPredicate, Consumer<C> cleanupCallback) {
        super(source);
        this.keyExtractor = Objects.requireNonNull(keyExtractor, "keyExtractor");
        this.collectionSupplier = Objects.requireNonNull(collectionSupplier, "collectionSupplier");
        this.distinctPredicate = Objects.requireNonNull(distinctPredicate, "distinctPredicate");
        this.cleanupCallback = Objects.requireNonNull(cleanupCallback, "cleanupCallback");
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> actual) {
        C collection = Objects.requireNonNull(this.collectionSupplier.get(), "The collectionSupplier returned a null collection");
        return new FluxDistinct.DistinctFuseableSubscriber<T, K, C>(actual, collection, this.keyExtractor, this.distinctPredicate, this.cleanupCallback);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }
}

