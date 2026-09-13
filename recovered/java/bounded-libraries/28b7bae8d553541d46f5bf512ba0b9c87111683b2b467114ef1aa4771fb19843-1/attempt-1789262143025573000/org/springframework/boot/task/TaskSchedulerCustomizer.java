/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler
 */
package org.springframework.boot.task;

import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@FunctionalInterface
public interface TaskSchedulerCustomizer {
    public void customize(ThreadPoolTaskScheduler var1);
}

