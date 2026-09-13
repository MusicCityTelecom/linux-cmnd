/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 *  org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
 *  org.springframework.util.Assert
 */
package org.springframework.messaging.simp.config;

import org.springframework.lang.Nullable;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.Assert;

public class TaskExecutorRegistration {
    private final ThreadPoolTaskExecutor taskExecutor;
    @Nullable
    private Integer corePoolSize;
    @Nullable
    private Integer maxPoolSize;
    @Nullable
    private Integer keepAliveSeconds;
    @Nullable
    private Integer queueCapacity;

    public TaskExecutorRegistration() {
        this.taskExecutor = new ThreadPoolTaskExecutor();
        this.taskExecutor.setCorePoolSize(Runtime.getRuntime().availableProcessors() * 2);
        this.taskExecutor.setAllowCoreThreadTimeOut(true);
    }

    public TaskExecutorRegistration(ThreadPoolTaskExecutor taskExecutor) {
        Assert.notNull((Object)taskExecutor, (String)"ThreadPoolTaskExecutor must not be null");
        this.taskExecutor = taskExecutor;
    }

    public TaskExecutorRegistration corePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
        return this;
    }

    public TaskExecutorRegistration maxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
        return this;
    }

    public TaskExecutorRegistration keepAliveSeconds(int keepAliveSeconds) {
        this.keepAliveSeconds = keepAliveSeconds;
        return this;
    }

    public TaskExecutorRegistration queueCapacity(int queueCapacity) {
        this.queueCapacity = queueCapacity;
        return this;
    }

    protected ThreadPoolTaskExecutor getTaskExecutor() {
        if (this.corePoolSize != null) {
            this.taskExecutor.setCorePoolSize(this.corePoolSize.intValue());
        }
        if (this.maxPoolSize != null) {
            this.taskExecutor.setMaxPoolSize(this.maxPoolSize.intValue());
        }
        if (this.keepAliveSeconds != null) {
            this.taskExecutor.setKeepAliveSeconds(this.keepAliveSeconds.intValue());
        }
        if (this.queueCapacity != null) {
            this.taskExecutor.setQueueCapacity(this.queueCapacity.intValue());
        }
        return this.taskExecutor;
    }
}

