/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Publisher
 *  org.reactivestreams.Subscriber
 */
package reactor.core.publisher;

import java.util.function.Function;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import reactor.core.CorePublisher;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.FluxContextWrite;
import reactor.core.publisher.Operators;
import reactor.util.context.Context;

class ContextTrackingFunctionWrapper<T, V>
implements Function<Publisher<T>, CorePublisher<V>> {
    static final String CONTEXT_MARKER_PREFIX = "reactor.core.context.marker.";
    final Function<? super Publisher<T>, ? extends Publisher<V>> transformer;
    final String marker;

    ContextTrackingFunctionWrapper(Function<? super Publisher<T>, ? extends Publisher<V>> transformer) {
        this(transformer, transformer.toString());
    }

    ContextTrackingFunctionWrapper(Function<? super Publisher<T>, ? extends Publisher<V>> transformer, String marker) {
        this.transformer = transformer;
        this.marker = marker;
    }

    @Override
    public CorePublisher<V> apply(Publisher<T> source) {
        final String key = CONTEXT_MARKER_PREFIX + System.identityHashCode(source);
        source = Operators.liftPublisher((p, actual) -> {
            Context ctx = actual.currentContext();
            if (!ctx.hasKey(key)) {
                throw new IllegalStateException("Context loss after applying " + this.marker);
            }
            Context newContext = ctx.delete(key);
            return new FluxContextWrite.ContextWriteSubscriber(actual, newContext);
        }).apply(source);
        final Publisher<V> result = this.transformer.apply((Publisher<Publisher<T>>)source);
        return new CorePublisher<V>(){

            @Override
            public void subscribe(CoreSubscriber<? super V> actual) {
                Context ctx = actual.currentContext().put(key, true);
                FluxContextWrite.ContextWriteSubscriber subscriber = new FluxContextWrite.ContextWriteSubscriber(actual, ctx);
                if (result instanceof CorePublisher) {
                    ((CorePublisher)result).subscribe(subscriber);
                } else {
                    result.subscribe(subscriber);
                }
            }

            @Override
            public void subscribe(Subscriber<? super V> subscriber) {
                this.subscribe(Operators.toCoreSubscriber(subscriber));
            }
        };
    }
}

