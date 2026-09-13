/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.Callable;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Scannable;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Operators;
import reactor.core.publisher.SourceProducer;
import reactor.util.annotation.Nullable;

final class MonoSingleCallable<T>
extends Mono<T>
implements Callable<T>,
SourceProducer<T> {
    final Callable<? extends T> callable;
    @Nullable
    final T defaultValue;

    MonoSingleCallable(Callable<? extends T> source) {
        this.callable = Objects.requireNonNull(source, "source");
        this.defaultValue = null;
    }

    MonoSingleCallable(Callable<? extends T> source, T defaultValue) {
        this.callable = Objects.requireNonNull(source, "source");
        this.defaultValue = Objects.requireNonNull(defaultValue, "defaultValue");
    }

    @Override
    public void subscribe(CoreSubscriber<? super T> actual) {
        Operators.MonoInnerProducerBase<T> sds = new Operators.MonoInnerProducerBase<T>(actual);
        actual.onSubscribe(sds);
        if (sds.isCancelled()) {
            return;
        }
        try {
            T t = this.callable.call();
            if (t == null && this.defaultValue == null) {
                actual.onError(new NoSuchElementException("Source was empty"));
            } else if (t == null) {
                sds.complete(this.defaultValue);
            } else {
                sds.complete(t);
            }
        }
        catch (Throwable e) {
            actual.onError(Operators.onOperatorError(e, actual.currentContext()));
        }
    }

    @Override
    public T block() {
        return this.block(Duration.ZERO);
    }

    @Override
    public T block(Duration m) {
        T v;
        try {
            v = this.callable.call();
        }
        catch (Throwable e) {
            throw Exceptions.propagate(e);
        }
        if (v == null && this.defaultValue == null) {
            throw new NoSuchElementException("Source was empty");
        }
        if (v == null) {
            return this.defaultValue;
        }
        return v;
    }

    @Override
    public T call() throws Exception {
        T v = this.callable.call();
        if (v == null && this.defaultValue == null) {
            throw new NoSuchElementException("Source was empty");
        }
        if (v == null) {
            return this.defaultValue;
        }
        return v;
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return null;
    }
}

