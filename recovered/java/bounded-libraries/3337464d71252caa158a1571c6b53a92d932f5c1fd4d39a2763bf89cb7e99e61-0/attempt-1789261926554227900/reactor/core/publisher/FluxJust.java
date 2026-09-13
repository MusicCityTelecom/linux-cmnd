/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.Objects;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Operators;
import reactor.core.publisher.SourceProducer;

final class FluxJust<T>
extends Flux<T>
implements Fuseable.ScalarCallable<T>,
Fuseable,
SourceProducer<T> {
    final T value;

    FluxJust(T value) {
        this.value = Objects.requireNonNull(value, "value");
    }

    @Override
    public T call() throws Exception {
        return this.value;
    }

    @Override
    public void subscribe(CoreSubscriber<? super T> actual) {
        actual.onSubscribe(Operators.scalarSubscription(actual, this.value, "just"));
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.BUFFERED) {
            return 1;
        }
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return null;
    }
}

