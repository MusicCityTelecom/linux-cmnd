/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.function.Supplier;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Operators;
import reactor.core.publisher.SourceProducer;
import reactor.util.annotation.Nullable;

final class MonoSupplier<T>
extends Mono<T>
implements Callable<T>,
Fuseable,
SourceProducer<T> {
    final Supplier<? extends T> supplier;

    MonoSupplier(Supplier<? extends T> callable) {
        this.supplier = Objects.requireNonNull(callable, "callable");
    }

    @Override
    public void subscribe(CoreSubscriber<? super T> actual) {
        Operators.MonoSubscriber sds = new Operators.MonoSubscriber(actual);
        actual.onSubscribe(sds);
        if (sds.isCancelled()) {
            return;
        }
        try {
            T t = this.supplier.get();
            if (t == null) {
                sds.onComplete();
            } else {
                sds.complete(t);
            }
        }
        catch (Throwable e) {
            actual.onError(Operators.onOperatorError(e, actual.currentContext()));
        }
    }

    @Override
    @Nullable
    public T block(Duration m) {
        return this.supplier.get();
    }

    @Override
    @Nullable
    public T block() {
        return this.block(Duration.ZERO);
    }

    @Override
    @Nullable
    public T call() throws Exception {
        return this.supplier.get();
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return null;
    }
}

