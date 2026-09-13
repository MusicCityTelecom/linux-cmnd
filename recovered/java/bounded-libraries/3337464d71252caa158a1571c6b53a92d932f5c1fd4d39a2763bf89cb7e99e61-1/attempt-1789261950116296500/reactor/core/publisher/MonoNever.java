/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Operators;
import reactor.core.publisher.SourceProducer;

final class MonoNever
extends Mono<Object>
implements SourceProducer<Object> {
    static final Mono<Object> INSTANCE = new MonoNever();

    MonoNever() {
    }

    @Override
    public void subscribe(CoreSubscriber<? super Object> actual) {
        actual.onSubscribe(Operators.emptySubscription());
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return null;
    }

    static <T> Mono<T> instance() {
        return INSTANCE;
    }
}

