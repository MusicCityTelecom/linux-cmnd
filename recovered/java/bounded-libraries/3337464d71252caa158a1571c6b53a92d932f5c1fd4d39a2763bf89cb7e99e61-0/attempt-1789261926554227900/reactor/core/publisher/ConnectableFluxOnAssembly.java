/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.function.Consumer;
import reactor.core.CoreSubscriber;
import reactor.core.Disposable;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.AssemblyOp;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.FluxOnAssembly;
import reactor.core.publisher.InternalConnectableFluxOperator;
import reactor.util.annotation.Nullable;

final class ConnectableFluxOnAssembly<T>
extends InternalConnectableFluxOperator<T, T>
implements Fuseable,
AssemblyOp,
Scannable {
    final FluxOnAssembly.AssemblySnapshot stacktrace;

    ConnectableFluxOnAssembly(ConnectableFlux<T> source, FluxOnAssembly.AssemblySnapshot stacktrace) {
        super(source);
        this.stacktrace = stacktrace;
    }

    @Override
    public final CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> actual) {
        return FluxOnAssembly.wrapSubscriber(actual, this.source, this, this.stacktrace);
    }

    @Override
    public void connect(Consumer<? super Disposable> cancelSupport) {
        this.source.connect(cancelSupport);
    }

    @Override
    @Nullable
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.PREFETCH) {
            return this.getPrefetch();
        }
        if (key == Scannable.Attr.PARENT) {
            return this.source;
        }
        if (key == Scannable.Attr.ACTUAL_METADATA) {
            return !this.stacktrace.isCheckpoint;
        }
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return null;
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

