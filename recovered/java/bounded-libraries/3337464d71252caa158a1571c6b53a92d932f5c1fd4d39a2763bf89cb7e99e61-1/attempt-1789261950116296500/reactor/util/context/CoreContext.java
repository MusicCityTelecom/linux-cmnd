/*
 * Decompiled with CFR 0.152.
 */
package reactor.util.context;

import java.util.stream.Stream;
import reactor.util.context.Context;
import reactor.util.context.ContextN;
import reactor.util.context.ContextView;

interface CoreContext
extends Context {
    @Override
    default public boolean isEmpty() {
        return false;
    }

    @Override
    default public Context putAll(ContextView other) {
        if (other.isEmpty()) {
            return this;
        }
        if (other instanceof CoreContext) {
            CoreContext coreContext = (CoreContext)other;
            return coreContext.putAllInto(this);
        }
        ContextN newContext = new ContextN(this.size() + other.size());
        this.unsafePutAllInto(newContext);
        ((Stream)other.stream().sequential()).forEach(newContext);
        if (newContext.size() <= 5) {
            return Context.of(newContext);
        }
        return newContext;
    }

    public Context putAllInto(Context var1);

    public void unsafePutAllInto(ContextN var1);
}

