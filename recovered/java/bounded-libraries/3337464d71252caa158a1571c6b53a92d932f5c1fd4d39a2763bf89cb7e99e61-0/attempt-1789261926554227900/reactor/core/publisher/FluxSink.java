/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.function.LongConsumer;
import reactor.core.Disposable;
import reactor.util.context.Context;
import reactor.util.context.ContextView;

public interface FluxSink<T> {
    public FluxSink<T> next(T var1);

    public void complete();

    public void error(Throwable var1);

    @Deprecated
    public Context currentContext();

    default public ContextView contextView() {
        return this.currentContext();
    }

    public long requestedFromDownstream();

    public boolean isCancelled();

    public FluxSink<T> onRequest(LongConsumer var1);

    public FluxSink<T> onCancel(Disposable var1);

    public FluxSink<T> onDispose(Disposable var1);

    public static enum OverflowStrategy {
        IGNORE,
        ERROR,
        DROP,
        LATEST,
        BUFFER;

    }
}

