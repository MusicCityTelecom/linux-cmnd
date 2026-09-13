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
import reactor.core.publisher.Operators;
import reactor.core.publisher.SourceProducer;
import reactor.util.context.Context;
import reactor.util.context.ContextView;

final class FluxDeferContextual<T>
extends Flux<T>
implements SourceProducer<T> {
    final Function<ContextView, ? extends Publisher<? extends T>> contextualPublisherFactory;

    FluxDeferContextual(Function<ContextView, ? extends Publisher<? extends T>> contextualPublisherFactory) {
        this.contextualPublisherFactory = Objects.requireNonNull(contextualPublisherFactory, "contextualPublisherFactory");
    }

    @Override
    public void subscribe(CoreSubscriber<? super T> actual) {
        Publisher<? extends T> p;
        Context ctx = actual.currentContext();
        try {
            p = Objects.requireNonNull(this.contextualPublisherFactory.apply(ctx.readOnly()), "The Publisher returned by the contextualPublisherFactory is null");
        }
        catch (Throwable e) {
            Operators.error(actual, Operators.onOperatorError(e, ctx));
            return;
        }
        FluxDeferContextual.from(p).subscribe(actual);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return null;
    }
}

