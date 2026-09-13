/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.Assert
 */
package org.springframework.integration.util;

import java.util.concurrent.Executor;
import org.springframework.util.Assert;

public class CompositeExecutor
implements Executor {
    private final Executor primaryTaskExecutor;
    private final Executor secondaryTaskExecutor;

    public CompositeExecutor(Executor primaryTaskExecutor, Executor secondaryTaskExecutor) {
        Assert.notNull((Object)primaryTaskExecutor, (String)"'primaryTaskExecutor' cannot be null");
        Assert.notNull((Object)primaryTaskExecutor, (String)"'secondaryTaskExecutor' cannot be null");
        this.primaryTaskExecutor = primaryTaskExecutor;
        this.secondaryTaskExecutor = secondaryTaskExecutor;
    }

    @Override
    public void execute(Runnable task) {
        this.primaryTaskExecutor.execute(task);
    }

    public void execute2(Runnable task) {
        this.secondaryTaskExecutor.execute(task);
    }
}

