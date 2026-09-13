/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.Objects;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.FluxSwitchIfEmpty;
import reactor.core.publisher.InternalMonoOperator;
import reactor.core.publisher.Mono;

final class MonoSwitchIfEmpty<T>
extends InternalMonoOperator<T, T> {
    final Mono<? extends T> other;

    MonoSwitchIfEmpty(Mono<? extends T> source, Mono<? extends T> other) {
        super(source);
        this.other = Objects.requireNonNull(other, "other");
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> actual) {
        FluxSwitchIfEmpty.SwitchIfEmptySubscriber<? extends T> parent = new FluxSwitchIfEmpty.SwitchIfEmptySubscriber<T>(actual, this.other);
        actual.onSubscribe(parent);
        return parent;
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }
}

