/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.Callable;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Operators;
import reactor.core.publisher.SourceProducer;
import reactor.util.annotation.Nullable;

final class MonoCallable<T>
extends Mono<T>
implements Callable<T>,
Fuseable,
SourceProducer<T> {
    final Callable<? extends T> callable;

    MonoCallable(Callable<? extends T> callable) {
        this.callable = Objects.requireNonNull(callable, "callable");
    }

    @Override
    public void subscribe(CoreSubscriber<? super T> actual) {
        Operators.MonoSubscriber sds = new Operators.MonoSubscriber(actual);
        actual.onSubscribe(sds);
        if (sds.isCancelled()) {
            return;
        }
        try {
            T t = this.callable.call();
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
    public T block() {
        return this.block(Duration.ZERO);
    }

    @Override
    @Nullable
    public T block(Duration m) {
        try {
            return this.callable.call();
        }
        catch (Throwable e) {
            throw Exceptions.propagate(e);
        }
    }

    @Override
    @Nullable
    public T call() throws Exception {
        return this.callable.call();
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return null;
    }
}

