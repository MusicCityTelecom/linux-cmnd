/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import reactor.core.publisher.ContextHolder;
import reactor.core.publisher.InternalOneSink;
import reactor.core.publisher.SinkEmptySerialized;
import reactor.core.publisher.Sinks;

public class SinkOneSerialized<T>
extends SinkEmptySerialized<T>
implements InternalOneSink<T>,
ContextHolder {
    final Sinks.One<T> sinkOne;

    public SinkOneSerialized(Sinks.One<T> sinkOne, ContextHolder contextHolder) {
        super(sinkOne, contextHolder);
        this.sinkOne = sinkOne;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Sinks.EmitResult tryEmitValue(T t) {
        Thread currentThread = Thread.currentThread();
        if (!this.tryAcquire(currentThread)) {
            return Sinks.EmitResult.FAIL_NON_SERIALIZED;
        }
        try {
            Sinks.EmitResult emitResult = this.sinkOne.tryEmitValue(t);
            return emitResult;
        }
        finally {
            if (WIP.decrementAndGet(this) == 0) {
                LOCKED_AT.compareAndSet(this, currentThread, null);
            }
        }
    }
}

