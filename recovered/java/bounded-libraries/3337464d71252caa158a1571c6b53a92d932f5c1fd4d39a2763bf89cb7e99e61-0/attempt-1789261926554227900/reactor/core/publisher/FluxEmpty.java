/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Operators;
import reactor.core.publisher.SourceProducer;
import reactor.util.annotation.Nullable;

final class FluxEmpty
extends Flux<Object>
implements Fuseable.ScalarCallable<Object>,
SourceProducer<Object> {
    private static final Flux<Object> INSTANCE = new FluxEmpty();

    private FluxEmpty() {
    }

    @Override
    public void subscribe(CoreSubscriber<? super Object> actual) {
        Operators.complete(actual);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return null;
    }

    public static <T> Flux<T> instance() {
        return INSTANCE;
    }

    @Override
    @Nullable
    public Object call() throws Exception {
        return null;
    }
}

