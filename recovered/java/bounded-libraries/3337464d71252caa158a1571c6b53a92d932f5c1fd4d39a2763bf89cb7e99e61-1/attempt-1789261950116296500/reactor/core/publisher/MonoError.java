/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.time.Duration;
import java.util.Objects;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Operators;
import reactor.core.publisher.SourceProducer;

final class MonoError<T>
extends Mono<T>
implements Fuseable.ScalarCallable,
SourceProducer<T> {
    final Throwable error;

    MonoError(Throwable error) {
        this.error = Objects.requireNonNull(error, "error");
    }

    @Override
    public T block(Duration m) {
        throw Exceptions.propagate(this.error);
    }

    @Override
    public T block() {
        throw Exceptions.propagate(this.error);
    }

    @Override
    public void subscribe(CoreSubscriber<? super T> actual) {
        Operators.error(actual, this.error);
    }

    @Override
    public Object call() throws Exception {
        if (this.error instanceof Exception) {
            throw (Exception)this.error;
        }
        throw Exceptions.propagate(this.error);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return null;
    }
}

