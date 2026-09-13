/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import reactor.core.CorePublisher;
import reactor.core.CoreSubscriber;
import reactor.util.annotation.Nullable;

interface OptimizableOperator<IN, OUT>
extends CorePublisher<IN> {
    @Nullable
    public CoreSubscriber<? super OUT> subscribeOrReturn(CoreSubscriber<? super IN> var1) throws Throwable;

    public CorePublisher<? extends OUT> source();

    @Nullable
    public OptimizableOperator<?, ? extends OUT> nextOptimizableSource();
}

