/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.Objects;
import reactor.core.CorePublisher;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Operators;
import reactor.core.publisher.OptimizableOperator;
import reactor.util.annotation.Nullable;

abstract class MonoFromFluxOperator<I, O>
extends Mono<O>
implements Scannable,
OptimizableOperator<O, I> {
    protected final Flux<? extends I> source;
    @Nullable
    final OptimizableOperator<?, I> optimizableOperator;

    protected MonoFromFluxOperator(Flux<? extends I> source) {
        OptimizableOperator sourceOptim;
        this.source = Objects.requireNonNull(source);
        this.optimizableOperator = source instanceof OptimizableOperator ? (sourceOptim = (OptimizableOperator)((Object)source)) : null;
    }

    @Override
    @Nullable
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.PREFETCH) {
            return Integer.MAX_VALUE;
        }
        if (key == Scannable.Attr.PARENT) {
            return this.source;
        }
        return null;
    }

    @Override
    public final void subscribe(CoreSubscriber<? super O> subscriber) {
        OptimizableOperator<Object, Object> operator = this;
        try {
            while (true) {
                if ((subscriber = operator.subscribeOrReturn(subscriber)) == null) {
                    return;
                }
                OptimizableOperator newSource = operator.nextOptimizableSource();
                if (newSource == null) {
                    operator.source().subscribe(subscriber);
                    return;
                }
                operator = newSource;
            }
        }
        catch (Throwable e) {
            Operators.reportThrowInSubscribe(subscriber, e);
            return;
        }
    }

    @Override
    @Nullable
    public abstract CoreSubscriber<? super I> subscribeOrReturn(CoreSubscriber<? super O> var1);

    @Override
    public final CorePublisher<? extends I> source() {
        return this.source;
    }

    @Override
    public final OptimizableOperator<?, ? extends I> nextOptimizableSource() {
        return this.optimizableOperator;
    }
}

