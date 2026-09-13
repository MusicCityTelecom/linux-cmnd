/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.process.internal;

import org.glassfish.jersey.process.internal.Stage;

public interface ChainableStage<DATA>
extends Stage<DATA> {
    public void setDefaultNext(Stage<DATA> var1);
}

