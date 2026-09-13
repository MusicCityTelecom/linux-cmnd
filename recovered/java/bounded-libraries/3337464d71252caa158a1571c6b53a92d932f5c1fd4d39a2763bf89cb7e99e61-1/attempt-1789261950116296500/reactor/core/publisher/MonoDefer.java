/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.function.Supplier;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Operators;
import reactor.core.publisher.SourceProducer;

final class MonoDefer<T>
extends Mono<T>
implements SourceProducer<T> {
    final Supplier<? extends Mono<? extends T>> supplier;

    MonoDefer(Supplier<? extends Mono<? extends T>> supplier) {
        this.supplier = Objects.requireNonNull(supplier, "supplier");
    }

    @Override
    public void subscribe(CoreSubscriber<? super T> actual) {
        Mono<? super T> p;
        try {
            p = Objects.requireNonNull(this.supplier.get(), "The Mono returned by the supplier is null");
        }
        catch (Throwable e) {
            Operators.error(actual, Operators.onOperatorError(e, actual.currentContext()));
            return;
        }
        p.subscribe(actual);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return null;
    }
}

