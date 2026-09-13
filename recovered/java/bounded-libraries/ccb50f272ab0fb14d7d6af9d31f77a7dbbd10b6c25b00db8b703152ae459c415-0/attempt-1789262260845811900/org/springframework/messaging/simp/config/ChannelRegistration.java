/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 *  org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
 */
package org.springframework.messaging.simp.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.lang.Nullable;
import org.springframework.messaging.simp.config.TaskExecutorRegistration;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

public class ChannelRegistration {
    @Nullable
    private TaskExecutorRegistration registration;
    private final List<ChannelInterceptor> interceptors = new ArrayList<ChannelInterceptor>();

    public TaskExecutorRegistration taskExecutor() {
        return this.taskExecutor(null);
    }

    public TaskExecutorRegistration taskExecutor(@Nullable ThreadPoolTaskExecutor taskExecutor) {
        if (this.registration == null) {
            this.registration = taskExecutor != null ? new TaskExecutorRegistration(taskExecutor) : new TaskExecutorRegistration();
        }
        return this.registration;
    }

    public ChannelRegistration interceptors(ChannelInterceptor ... interceptors) {
        this.interceptors.addAll(Arrays.asList(interceptors));
        return this;
    }

    @Deprecated
    public ChannelRegistration setInterceptors(ChannelInterceptor ... interceptors) {
        if (interceptors != null) {
            this.interceptors.addAll(Arrays.asList(interceptors));
        }
        return this;
    }

    protected boolean hasTaskExecutor() {
        return this.registration != null;
    }

    protected boolean hasInterceptors() {
        return !this.interceptors.isEmpty();
    }

    protected List<ChannelInterceptor> getInterceptors() {
        return this.interceptors;
    }
}

