/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.function.BiConsumer;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.FluxHandle;
import reactor.core.publisher.InternalMonoOperator;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SynchronousSink;

final class MonoHandle<T, R>
extends InternalMonoOperator<T, R> {
    final BiConsumer<? super T, SynchronousSink<R>> handler;

    MonoHandle(Mono<? extends T> source, BiConsumer<? super T, SynchronousSink<R>> handler) {
        super(source);
        this.handler = Objects.requireNonNull(handler, "handler");
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super R> actual) {
        return new FluxHandle.HandleSubscriber<T, R>(actual, this.handler);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }
}

