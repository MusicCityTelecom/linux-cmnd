/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.concurrent.Callable;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.AssemblyOp;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxOnAssembly;
import reactor.core.publisher.InternalFluxOperator;

final class FluxCallableOnAssembly<T>
extends InternalFluxOperator<T, T>
implements Fuseable,
Callable<T>,
AssemblyOp {
    final FluxOnAssembly.AssemblySnapshot stacktrace;

    FluxCallableOnAssembly(Flux<? extends T> source, FluxOnAssembly.AssemblySnapshot stacktrace) {
        super(source);
        this.stacktrace = stacktrace;
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> actual) {
        return FluxOnAssembly.wrapSubscriber(actual, this.source, this, this.stacktrace);
    }

    @Override
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

