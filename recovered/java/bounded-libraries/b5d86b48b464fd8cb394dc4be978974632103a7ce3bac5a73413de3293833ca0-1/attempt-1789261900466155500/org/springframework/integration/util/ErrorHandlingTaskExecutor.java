/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.task.SyncTaskExecutor
 *  org.springframework.core.task.TaskExecutor
 *  org.springframework.util.Assert
 *  org.springframework.util.ErrorHandler
 */
package org.springframework.integration.util;

import java.util.concurrent.Executor;
import org.springframework.core.task.SyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.util.Assert;
import org.springframework.util.ErrorHandler;

public class ErrorHandlingTaskExecutor
implements TaskExecutor {
    private final Executor executor;
    private final ErrorHandler errorHandler;

    public ErrorHandlingTaskExecutor(Executor executor, ErrorHandler errorHandler) {
        Assert.notNull((Object)executor, (String)"executor must not be null");
        Assert.notNull((Object)errorHandler, (String)"errorHandler must not be null");
        this.executor = executor;
        this.errorHandler = errorHandler;
    }

    public boolean isSyncExecutor() {
        return this.executor instanceof SyncTaskExecutor;
    }

    public void execute(Runnable task) {
        this.executor.execute(() -> {
            try {
                task.run();
            }
            catch (Throwable t) {
                this.errorHandler.handleError(t);
            }
        });
    }
}

