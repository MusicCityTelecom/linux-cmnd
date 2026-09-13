/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.function.LongConsumer;
import reactor.core.Disposable;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;
import reactor.util.context.ContextView;

public interface MonoSink<T> {
    public void success();

    public void success(@Nullable T var1);

    public void error(Throwable var1);

    @Deprecated
    public Context currentContext();

    default public ContextView contextView() {
        return this.currentContext();
    }

    public MonoSink<T> onRequest(LongConsumer var1);

    public MonoSink<T> onCancel(Disposable var1);

    public MonoSink<T> onDispose(Disposable var1);
}

