/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Processor
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import java.time.Duration;
import java.util.concurrent.CancellationException;
import java.util.stream.Stream;
import org.reactivestreams.Processor;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Disposable;
import reactor.core.Scannable;
import reactor.core.publisher.InnerProducer;
import reactor.core.publisher.Mono;
import reactor.core.publisher.NextProcessor;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

@Deprecated
public abstract class MonoProcessor<O>
extends Mono<O>
implements Processor<O, O>,
CoreSubscriber<O>,
Disposable,
Subscription,
Scannable {
    @Deprecated
    public static <T> MonoProcessor<T> create() {
        return new NextProcessor(null);
    }

    @Deprecated
    public void cancel() {
    }

    @Deprecated
    public boolean isCancelled() {
        return false;
    }

    @Deprecated
    public void request(long n) {
        Operators.validate(n);
    }

    @Override
    public void dispose() {
        this.onError(new CancellationException("Disposed"));
    }

    @Override
    @Nullable
    public O block() {
        return this.block(null);
    }

    @Override
    @Nullable
    public O block(@Nullable Duration timeout) {
        return this.peek();
    }

    @Nullable
    public Throwable getError() {
        return null;
    }

    public final boolean isError() {
        return this.getError() != null;
    }

    public final boolean isSuccess() {
        return this.isTerminated() && !this.isError();
    }

    public boolean isTerminated() {
        return false;
    }

    @Override
    public boolean isDisposed() {
        return this.isTerminated() || this.isCancelled();
    }

    @Nullable
    @Deprecated
    public O peek() {
        return null;
    }

    @Override
    public Context currentContext() {
        InnerProducer[] innerProducersArray = (InnerProducer[])this.inners().filter(InnerProducer.class::isInstance).map(InnerProducer.class::cast).toArray(InnerProducer[]::new);
        return Operators.multiSubscribersContext(innerProducersArray);
    }

    @Override
    @Nullable
    public Object scanUnsafe(Scannable.Attr key) {
        boolean t = this.isTerminated();
        if (key == Scannable.Attr.TERMINATED) {
            return t;
        }
        if (key == Scannable.Attr.ERROR) {
            return this.getError();
        }
        if (key == Scannable.Attr.PREFETCH) {
            return Integer.MAX_VALUE;
        }
        if (key == Scannable.Attr.CANCELLED) {
            return this.isCancelled();
        }
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return null;
    }

    public long downstreamCount() {
        return this.inners().count();
    }

    public final boolean hasDownstreams() {
        return this.downstreamCount() != 0L;
    }

    @Override
    public Stream<? extends Scannable> inners() {
        return Stream.empty();
    }
}

