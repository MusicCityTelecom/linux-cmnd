/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.function.Consumer;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.FluxDoOnEach;
import reactor.core.publisher.InternalMonoOperator;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Signal;

final class MonoDoOnEachFuseable<T>
extends InternalMonoOperator<T, T>
implements Fuseable {
    final Consumer<? super Signal<T>> onSignal;

    MonoDoOnEachFuseable(Mono<? extends T> source, Consumer<? super Signal<T>> onSignal) {
        super(source);
        this.onSignal = Objects.requireNonNull(onSignal, "onSignal");
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> actual) {
        return FluxDoOnEach.createSubscriber(actual, this.onSignal, true, true);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }
}

