/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.time.Duration;
import java.util.Objects;
import java.util.function.Supplier;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Operators;
import reactor.core.publisher.SourceProducer;

final class MonoErrorSupplied<T>
extends Mono<T>
implements Fuseable.ScalarCallable<T>,
SourceProducer<T> {
    final Supplier<? extends Throwable> errorSupplier;

    MonoErrorSupplied(Supplier<? extends Throwable> errorSupplier) {
        this.errorSupplier = Objects.requireNonNull(errorSupplier, "errorSupplier");
    }

    @Override
    public T block(Duration m) {
        Throwable error = Objects.requireNonNull(this.errorSupplier.get(), "the errorSupplier returned null");
        throw Exceptions.propagate(error);
    }

    @Override
    public T block() {
        Throwable error = Objects.requireNonNull(this.errorSupplier.get(), "the errorSupplier returned null");
        throw Exceptions.propagate(error);
    }

    @Override
    public void subscribe(CoreSubscriber<? super T> actual) {
        Throwable error = Objects.requireNonNull(this.errorSupplier.get(), "the errorSupplier returned null");
        Operators.error(actual, error);
    }

    @Override
    public T call() throws Exception {
        Throwable error = Objects.requireNonNull(this.errorSupplier.get(), "the errorSupplier returned null");
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

