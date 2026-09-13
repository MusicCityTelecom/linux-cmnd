/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.process.internal;

import org.glassfish.jersey.process.internal.ChainableStage;
import org.glassfish.jersey.process.internal.Stage;

public abstract class AbstractChainableStage<DATA>
implements ChainableStage<DATA> {
    private Stage<DATA> nextStage;

    protected AbstractChainableStage() {
        this(null);
    }

    protected AbstractChainableStage(Stage<DATA> nextStage) {
        this.nextStage = nextStage;
    }

    @Override
    public final void setDefaultNext(Stage<DATA> next) {
        this.nextStage = next;
    }

    public final Stage<DATA> getDefaultNext() {
        return this.nextStage;
    }
}

