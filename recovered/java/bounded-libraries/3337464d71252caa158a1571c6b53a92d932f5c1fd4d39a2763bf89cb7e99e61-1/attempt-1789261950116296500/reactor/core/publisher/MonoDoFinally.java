/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.function.Consumer;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.FluxDoFinally;
import reactor.core.publisher.InternalMonoOperator;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SignalType;

final class MonoDoFinally<T>
extends InternalMonoOperator<T, T> {
    final Consumer<SignalType> onFinally;

    MonoDoFinally(Mono<? extends T> source, Consumer<SignalType> onFinally) {
        super(source);
        this.onFinally = onFinally;
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> actual) {
        return FluxDoFinally.createSubscriber(actual, this.onFinally);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }
}

