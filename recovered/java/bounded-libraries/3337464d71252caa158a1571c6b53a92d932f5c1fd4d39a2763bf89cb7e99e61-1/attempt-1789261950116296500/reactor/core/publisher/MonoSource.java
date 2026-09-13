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
import reactor.core.publisher.OptimizableOperator;
import reactor.core.publisher.SourceProducer;
import reactor.util.annotation.Nullable;

final class MonoSource<I>
extends Mono<I>
implements Scannable,
SourceProducer<I>,
OptimizableOperator<I, I> {
    final Publisher<? extends I> source;
    @Nullable
    final OptimizableOperator<?, I> optimizableOperator;

    MonoSource(Publisher<? extends I> source) {
        OptimizableOperator optimSource;
        this.source = Objects.requireNonNull(source);
        this.optimizableOperator = source instanceof OptimizableOperator ? (optimSource = (OptimizableOperator)source) : null;
    }

    @Override
    public void subscribe(CoreSubscriber<? super I> actual) {
        this.source.subscribe(actual);
    }

    @Override
    public CoreSubscriber<? super I> subscribeOrReturn(CoreSubscriber<? super I> actual) {
        return actual;
    }

    @Override
    public final CorePublisher<? extends I> source() {
        return this;
    }

    @Override
    public final OptimizableOperator<?, ? extends I> nextOptimizableSource() {
        return this.optimizableOperator;
    }

    @Override
    @Nullable
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.PARENT) {
            return this.source;
        }
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.from(this.source).scanUnsafe(key);
        }
        return null;
    }
}

