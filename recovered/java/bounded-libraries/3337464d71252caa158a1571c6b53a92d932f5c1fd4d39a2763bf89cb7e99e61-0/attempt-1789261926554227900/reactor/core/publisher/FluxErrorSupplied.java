/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.function.Supplier;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Operators;
import reactor.core.publisher.SourceProducer;

final class FluxErrorSupplied<T>
extends Flux<T>
implements Fuseable.ScalarCallable,
SourceProducer<T> {
    final Supplier<? extends Throwable> errorSupplier;

    FluxErrorSupplied(Supplier<? extends Throwable> errorSupplier) {
        this.errorSupplier = Objects.requireNonNull(errorSupplier, "errorSupplier");
    }

    @Override
    public void subscribe(CoreSubscriber<? super T> actual) {
        Throwable error = Objects.requireNonNull(this.errorSupplier.get(), "errorSupplier produced a null Throwable");
        Operators.error(actual, error);
    }

    @Override
    public Object call() throws Exception {
        Throwable error = Objects.requireNonNull(this.errorSupplier.get(), "errorSupplier produced a null Throwable");
        if (error instanceof Exception) {
            throw (Exception)error;
        }
        throw Exceptions.propagate(error);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return null;
    }
}

