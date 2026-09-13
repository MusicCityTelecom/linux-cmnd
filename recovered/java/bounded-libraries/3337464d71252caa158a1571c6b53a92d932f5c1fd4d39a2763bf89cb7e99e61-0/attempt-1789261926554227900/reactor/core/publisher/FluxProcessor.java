/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Processor
 *  org.reactivestreams.Publisher
 *  org.reactivestreams.Subscriber
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.concurrent.CancellationException;
import java.util.stream.Stream;
import org.reactivestreams.Processor;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import reactor.core.CoreSubscriber;
import reactor.core.Disposable;
import reactor.core.Scannable;
import reactor.core.publisher.ContextHolder;
import reactor.core.publisher.DelegateProcessor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxCreate;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Operators;
import reactor.core.publisher.UnicastProcessor;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

@Deprecated
public abstract class FluxProcessor<IN, OUT>
extends Flux<OUT>
implements Processor<IN, OUT>,
CoreSubscriber<IN>,
Scannable,
Disposable,
ContextHolder {
    @Deprecated
    public static <T> FluxProcessor<Publisher<? extends T>, T> switchOnNext() {
        UnicastProcessor emitter = UnicastProcessor.create();
        FluxProcessor p = FluxProcessor.wrap(emitter, FluxProcessor.switchOnNext(emitter));
        return p;
    }

    public static <IN, OUT> FluxProcessor<IN, OUT> wrap(Subscriber<IN> upstream, Publisher<OUT> downstream) {
        return new DelegateProcessor<IN, OUT>(downstream, upstream);
    }

    @Override
    public void dispose() {
        this.onError(new CancellationException("Disposed"));
    }

    public long downstreamCount() {
        return this.inners().count();
    }

    public int getBufferSize() {
        return Integer.MAX_VALUE;
    }

    @Nullable
    public Throwable getError() {
        return null;
    }

    public boolean hasDownstreams() {
        return this.downstreamCount() != 0L;
    }

    public final boolean hasCompleted() {
        return this.isTerminated() && this.getError() == null;
    }

    public final boolean hasError() {
        return this.isTerminated() && this.getError() != null;
    }

    @Override
    public Stream<? extends Scannable> inners() {
        return Stream.empty();
    }

    public boolean isTerminated() {
        return false;
    }

    public boolean isSerialized() {
        return false;
    }

    @Override
    @Nullable
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.TERMINATED) {
            return this.isTerminated();
        }
        if (key == Scannable.Attr.ERROR) {
            return this.getError();
        }
        if (key == Scannable.Attr.CAPACITY) {
            return this.getBufferSize();
        }
        return null;
    }

    @Override
    public Context currentContext() {
        return Context.empty();
    }

    public final FluxProcessor<IN, OUT> serialize() {
        return new DelegateProcessor(this, Operators.serialize(this));
    }

    @Deprecated
    public final FluxSink<IN> sink() {
        return this.sink(FluxSink.OverflowStrategy.IGNORE);
    }

    @Deprecated
    public final FluxSink<IN> sink(FluxSink.OverflowStrategy strategy) {
        Objects.requireNonNull(strategy, "strategy");
        if (this.getBufferSize() == Integer.MAX_VALUE) {
            strategy = FluxSink.OverflowStrategy.IGNORE;
        }
        FluxCreate.BaseSink s = FluxCreate.createSink(this, strategy);
        this.onSubscribe(s);
        if (s.isCancelled() || this.isSerialized() && this.getBufferSize() == Integer.MAX_VALUE) {
            return s;
        }
        if (this.serializeAlways()) {
            return new FluxCreate.SerializedFluxSink(s);
        }
        return new FluxCreate.SerializeOnRequestSink(s);
    }

    protected boolean serializeAlways() {
        return true;
    }

    protected boolean isIdentityProcessor() {
        return false;
    }
}

