/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Publisher
 */
package reactor.core.publisher;

import org.reactivestreams.Publisher;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Operators;
import reactor.core.publisher.SourceProducer;

final class FluxNever
extends Flux<Object>
implements SourceProducer<Object> {
    static final Publisher<Object> INSTANCE = new FluxNever();

    FluxNever() {
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

    static <T> Flux<T> instance() {
        return (Flux)INSTANCE;
    }
}

