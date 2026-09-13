/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import reactor.core.CorePublisher;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoOperator;
import reactor.core.publisher.Operators;
import reactor.core.publisher.OptimizableOperator;
import reactor.util.annotation.Nullable;

abstract class InternalMonoOperator<I, O>
extends MonoOperator<I, O>
implements Scannable,
OptimizableOperator<O, I> {
    @Nullable
    final OptimizableOperator<?, I> optimizableOperator;

    protected InternalMonoOperator(Mono<? extends I> source) {
        super(source);
        OptimizableOperator optimSource;
        this.optimizableOperator = source instanceof OptimizableOperator ? (optimSource = (OptimizableOperator)((Object)source)) : null;
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
    public abstract CoreSubscriber<? super I> subscribeOrReturn(CoreSubscriber<? super O> var1) throws Throwable;

    @Override
    public final CorePublisher<? extends I> source() {
        return this.source;
    }

    @Override
    public final OptimizableOperator<?, ? extends I> nextOptimizableSource() {
        return this.optimizableOperator;
    }
}

