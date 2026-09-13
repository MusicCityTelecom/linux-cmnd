/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Publisher
 */
package reactor.core.publisher;

import java.util.Objects;
import org.reactivestreams.Publisher;
import reactor.core.CorePublisher;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoIgnoreElements;
import reactor.core.publisher.Operators;
import reactor.core.publisher.OptimizableOperator;
import reactor.util.annotation.Nullable;

final class MonoIgnorePublisher<T>
extends Mono<T>
implements Scannable,
OptimizableOperator<T, T> {
    final Publisher<? extends T> source;
    @Nullable
    final OptimizableOperator<?, T> optimizableOperator;

    MonoIgnorePublisher(Publisher<? extends T> source) {
        OptimizableOperator optimSource;
        this.source = Objects.requireNonNull(source, "publisher");
        this.optimizableOperator = source instanceof OptimizableOperator ? (optimSource = (OptimizableOperator)source) : null;
    }

    @Override
    public void subscribe(CoreSubscriber<? super T> actual) {
        try {
            this.source.subscribe(this.subscribeOrReturn((CoreSubscriber<? super T>)actual));
        }
        catch (Throwable e) {
            Operators.error(actual, Operators.onOperatorError(e, actual.currentContext()));
            return;
        }
    }

    @Override
    public final CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> actual) throws Throwable {
        return new MonoIgnoreElements.IgnoreElementsSubscriber<T>(actual);
    }

    @Override
    public final CorePublisher<? extends T> source() {
        return this;
    }

    @Override
    public final OptimizableOperator<?, ? extends T> nextOptimizableSource() {
        return this.optimizableOperator;
    }

    @Override
    @Nullable
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.PARENT) {
            return this.source;
        }
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return null;
    }
}

