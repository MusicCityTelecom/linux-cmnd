/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
 */
package org.springframework.boot.task;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@FunctionalInterface
public interface TaskExecutorCustomizer {
    public void customize(ThreadPoolTaskExecutor var1);
}

