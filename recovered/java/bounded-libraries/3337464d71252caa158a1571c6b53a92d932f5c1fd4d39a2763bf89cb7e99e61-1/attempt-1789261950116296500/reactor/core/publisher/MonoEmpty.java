/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Publisher
 */
package reactor.core.publisher;

import java.time.Duration;
import org.reactivestreams.Publisher;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Operators;
import reactor.core.publisher.SourceProducer;
import reactor.util.annotation.Nullable;

final class MonoEmpty
extends Mono<Object>
implements Fuseable.ScalarCallable<Object>,
SourceProducer<Object> {
    static final Publisher<Object> INSTANCE = new MonoEmpty();

    MonoEmpty() {
    }

    @Override
    public void subscribe(CoreSubscriber<? super Object> actual) {
        Operators.complete(actual);
    }

    static <T> Mono<T> instance() {
        return (Mono)INSTANCE;
    }

    @Override
    @Nullable
    public Object call() throws Exception {
        return null;
    }

    @Override
    @Nullable
    public Object block(Duration m) {
        return null;
    }

    @Override
    @Nullable
    public Object block() {
        return null;
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return null;
    }
}

