/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.Iterator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Supplier;
import java.util.stream.Stream;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxIterable;
import reactor.core.publisher.Operators;
import reactor.core.publisher.SourceProducer;

final class FluxStream<T>
extends Flux<T>
implements Fuseable,
SourceProducer<T> {
    final Supplier<? extends Stream<? extends T>> streamSupplier;

    FluxStream(Supplier<? extends Stream<? extends T>> streamSupplier) {
        this.streamSupplier = Objects.requireNonNull(streamSupplier, "streamSupplier");
    }

    @Override
    public void subscribe(CoreSubscriber<? super T> actual) {
        Iterator it;
        boolean knownToBeFinite;
        Stream<T> stream;
        try {
            stream = Objects.requireNonNull(this.streamSupplier.get(), "The stream supplier returned a null Stream");
        }
        catch (Throwable e) {
            Operators.error(actual, Operators.onOperatorError(e, actual.currentContext()));
            return;
        }
        try {
            Spliterator spliterator = Objects.requireNonNull(stream.spliterator(), "The stream returned a null Spliterator");
            knownToBeFinite = spliterator.hasCharacteristics(64);
            it = Spliterators.iterator(spliterator);
        }
        catch (Throwable e) {
            Operators.error(actual, Operators.onOperatorError(e, actual.currentContext()));
            return;
        }
        FluxIterable.subscribe(actual, it, knownToBeFinite, stream::close);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return null;
    }
}

