/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Publisher
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.function.Supplier;
import org.reactivestreams.Publisher;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Operators;
import reactor.core.publisher.SourceProducer;

final class FluxDefer<T>
extends Flux<T>
implements SourceProducer<T> {
    final Supplier<? extends Publisher<? extends T>> supplier;

    FluxDefer(Supplier<? extends Publisher<? extends T>> supplier) {
        this.supplier = Objects.requireNonNull(supplier, "supplier");
    }

    @Override
    public void subscribe(CoreSubscriber<? super T> actual) {
        Publisher<? extends T> p;
        try {
            p = Objects.requireNonNull(this.supplier.get(), "The Publisher returned by the supplier is null");
        }
        catch (Throwable e) {
            Operators.error(actual, Operators.onOperatorError(e, actual.currentContext()));
            return;
        }
        FluxDefer.from(p).subscribe(actual);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return null;
    }
}

