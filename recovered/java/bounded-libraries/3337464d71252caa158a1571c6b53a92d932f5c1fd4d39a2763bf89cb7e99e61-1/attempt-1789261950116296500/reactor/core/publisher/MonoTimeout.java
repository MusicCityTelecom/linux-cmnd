/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Publisher
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.function.Function;
import org.reactivestreams.Publisher;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxTimeout;
import reactor.core.publisher.InternalMonoOperator;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Operators;

final class MonoTimeout<T, U, V>
extends InternalMonoOperator<T, T> {
    final Publisher<U> firstTimeout;
    final Publisher<? extends T> other;
    final String timeoutDescription;
    static final Function NEVER = e -> Flux.never();

    MonoTimeout(Mono<? extends T> source, Publisher<U> firstTimeout, String timeoutDescription) {
        super(source);
        this.firstTimeout = Objects.requireNonNull(firstTimeout, "firstTimeout");
        this.other = null;
        this.timeoutDescription = timeoutDescription;
    }

    MonoTimeout(Mono<? extends T> source, Publisher<U> firstTimeout, Publisher<? extends T> other) {
        super(source);
        this.firstTimeout = Objects.requireNonNull(firstTimeout, "firstTimeout");
        this.other = Objects.requireNonNull(other, "other");
        this.timeoutDescription = null;
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> actual) {
        return new FluxTimeout.TimeoutMainSubscriber(Operators.serialize(actual), this.firstTimeout, NEVER, this.other, FluxTimeout.addNameToTimeoutDescription(this.source, this.timeoutDescription));
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }
}

