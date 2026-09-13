/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import reactor.util.context.Context;
import reactor.util.context.ContextView;

public interface SynchronousSink<T> {
    public void complete();

    @Deprecated
    public Context currentContext();

    default public ContextView contextView() {
        return this.currentContext();
    }

    public void error(Throwable var1);

    public void next(T var1);
}

