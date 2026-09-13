/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.Objects;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Operators;
import reactor.core.publisher.SourceProducer;

final class FluxError<T>
extends Flux<T>
implements Fuseable.ScalarCallable,
SourceProducer<T> {
    final Throwable error;

    FluxError(Throwable error) {
        this.error = Objects.requireNonNull(error);
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

