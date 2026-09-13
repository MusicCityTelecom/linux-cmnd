/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.function.Function;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Operators;
import reactor.core.publisher.SourceProducer;
import reactor.util.context.Context;
import reactor.util.context.ContextView;

final class MonoDeferContextual<T>
extends Mono<T>
implements SourceProducer<T> {
    final Function<ContextView, ? extends Mono<? extends T>> contextualMonoFactory;

    MonoDeferContextual(Function<ContextView, ? extends Mono<? extends T>> contextualMonoFactory) {
        this.contextualMonoFactory = Objects.requireNonNull(contextualMonoFactory, "contextualMonoFactory");
    }

    @Override
    public void subscribe(CoreSubscriber<? super T> actual) {
        Mono<T> p;
        Context ctx = actual.currentContext();
        try {
            p = Objects.requireNonNull(this.contextualMonoFactory.apply(ctx), "The Mono returned by the contextualMonoFactory is null");
        }
        catch (Throwable e) {
            Operators.error(actual, Operators.onOperatorError(e, ctx));
            return;
        }
        p.subscribe(actual);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return null;
    }
}

