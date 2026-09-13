/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.stream.Stream;
import reactor.core.Scannable;
import reactor.core.publisher.ContextHolder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.InternalManySink;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.SinksSpecs;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

final class SinkManySerialized<T>
extends SinksSpecs.AbstractSerializedSink
implements InternalManySink<T>,
Scannable {
    final Sinks.Many<T> sink;
    final ContextHolder contextHolder;

    SinkManySerialized(Sinks.Many<T> sink, ContextHolder contextHolder) {
        this.sink = sink;
        this.contextHolder = contextHolder;
    }

    @Override
    public int currentSubscriberCount() {
        return this.sink.currentSubscriberCount();
    }

    @Override
    public Flux<T> asFlux() {
        return this.sink.asFlux();
    }

    @Override
    public Context currentContext() {
        return this.contextHolder.currentContext();
    }

    public boolean isCancelled() {
        return Scannable.from(this.sink).scanOrDefault(Scannable.Attr.CANCELLED, false);
    }

    @Override
    public final Sinks.EmitResult tryEmitComplete() {
        Thread currentThread = Thread.currentThread();
        if (!this.tryAcquire(currentThread)) {
            return Sinks.EmitResult.FAIL_NON_SERIALIZED;
        }
        try {
            Sinks.EmitResult emitResult = this.sink.tryEmitComplete();
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

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public final Sinks.EmitResult tryEmitNext(T t) {
        Objects.requireNonNull(t, "t is null in sink.next(t)");
        Thread currentThread = Thread.currentThread();
        if (!this.tryAcquire(currentThread)) {
            return Sinks.EmitResult.FAIL_NON_SERIALIZED;
        }
        try {
            Sinks.EmitResult emitResult = this.sink.tryEmitNext(t);
            return emitResult;
        }
        finally {
            if (WIP.decrementAndGet(this) == 0) {
                LOCKED_AT.compareAndSet(this, currentThread, null);
            }
        }
    }

    @Override
    @Nullable
    public Object scanUnsafe(Scannable.Attr key) {
        return this.sink.scanUnsafe(key);
    }

    @Override
    public Stream<? extends Scannable> inners() {
        return Scannable.from(this.sink).inners();
    }

    public String toString() {
        return this.sink.toString();
    }
}

