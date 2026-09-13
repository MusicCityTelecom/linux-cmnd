/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Publisher
 */
package reactor.core.publisher;

import java.time.Duration;
import java.util.concurrent.Callable;
import org.reactivestreams.Publisher;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.AssemblyOp;
import reactor.core.publisher.FluxOnAssembly;
import reactor.core.publisher.InternalMonoOperator;
import reactor.core.publisher.Mono;
import reactor.util.annotation.Nullable;

final class MonoCallableOnAssembly<T>
extends InternalMonoOperator<T, T>
implements Callable<T>,
AssemblyOp {
    final FluxOnAssembly.AssemblySnapshot stacktrace;

    MonoCallableOnAssembly(Mono<? extends T> source, FluxOnAssembly.AssemblySnapshot stacktrace) {
        super(source);
        this.stacktrace = stacktrace;
    }

    @Override
    @Nullable
    public T block() {
        return this.block(Duration.ZERO);
    }

    @Override
    @Nullable
    public T block(Duration timeout) {
        try {
            return (T)((Callable)((Object)this.source)).call();
        }
        catch (Throwable e) {
            throw Exceptions.propagate(e);
        }
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> actual) {
        if (actual instanceof Fuseable.ConditionalSubscriber) {
            Fuseable.ConditionalSubscriber cs = (Fuseable.ConditionalSubscriber)actual;
            return new FluxOnAssembly.OnAssemblyConditionalSubscriber(cs, this.stacktrace, (Publisher<?>)this.source, (Publisher<?>)this);
        }
        return new FluxOnAssembly.OnAssemblySubscriber<T>(actual, this.stacktrace, this.source, this);
    }

    @Override
    @Nullable
    public T call() throws Exception {
        return (T)((Callable)((Object)this.source)).call();
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.ACTUAL_METADATA) {
            return !this.stacktrace.isCheckpoint;
        }
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    @Override
    public String stepName() {
        return this.stacktrace.operatorAssemblyInformation();
    }

    @Override
    public String toString() {
        return this.stacktrace.operatorAssemblyInformation();
    }
}

