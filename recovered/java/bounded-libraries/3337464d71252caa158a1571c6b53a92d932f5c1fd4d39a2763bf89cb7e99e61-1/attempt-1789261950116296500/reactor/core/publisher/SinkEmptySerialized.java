/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.stream.Stream;
import reactor.core.Scannable;
import reactor.core.publisher.ContextHolder;
import reactor.core.publisher.InternalEmptySink;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.SinksSpecs;
import reactor.util.context.Context;

class SinkEmptySerialized<T>
extends SinksSpecs.AbstractSerializedSink
implements InternalEmptySink<T>,
ContextHolder {
    final Sinks.Empty<T> sink;
    final ContextHolder contextHolder;

    SinkEmptySerialized(Sinks.Empty<T> sink, ContextHolder contextHolder) {
        this.sink = sink;
        this.contextHolder = contextHolder;
    }

    @Override
    public final Sinks.EmitResult tryEmitEmpty() {
        Thread currentThread = Thread.currentThread();
        if (!this.tryAcquire(currentThread)) {
            return Sinks.EmitResult.FAIL_NON_SERIALIZED;
        }
        try {
            Sinks.EmitResult emitResult = this.sink.tryEmitEmpty();
            return emitResult;
        }
        finally {
            if (WIP.decrementAndGet(this) == 0) {
                LOCKED_AT.compareAndSet(this, currentThread, null);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public final Sinks.EmitResult tryEmitError(Throwable t) {
        Objects.requireNonNull(t, "t is null in sink.error(t)");
        Thread currentThread = Thread.currentThread();
        if (!this.tryAcquire(currentThread)) {
            return Sinks.EmitResult.FAIL_NON_SERIALIZED;
        }
        try {
            Sinks.EmitResult emitResult = this.sink.tryEmitError(t);
            return emitResult;
        }
        finally {
            if (WIP.decrementAndGet(this) == 0) {
                LOCKED_AT.compareAndSet(this, currentThread, null);
            }
        }
    }

    @Override
    public int currentSubscriberCount() {
        return this.sink.currentSubscriberCount();
    }

    @Override
    public Mono<T> asMono() {
        return this.sink.asMono();
    }

    @Override
    public Stream<? extends Scannable> inners() {
        return this.sink.inners();
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        return this.sink.scanUnsafe(key);
    }

    @Override
    public Context currentContext() {
        return this.contextHolder.currentContext();
    }
}

