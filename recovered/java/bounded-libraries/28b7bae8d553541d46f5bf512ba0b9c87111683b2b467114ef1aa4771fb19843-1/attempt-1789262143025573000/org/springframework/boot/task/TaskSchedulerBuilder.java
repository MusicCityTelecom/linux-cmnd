/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler
 *  org.springframework.util.Assert
 *  org.springframework.util.CollectionUtils
 */
package org.springframework.boot.task;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.boot.task.TaskSchedulerCustomizer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

public class TaskSchedulerBuilder {
    private final Integer poolSize;
    private final Boolean awaitTermination;
    private final Duration awaitTerminationPeriod;
    private final String threadNamePrefix;
    private final Set<TaskSchedulerCustomizer> customizers;

    public TaskSchedulerBuilder() {
        this.poolSize = null;
        this.awaitTermination = null;
        this.awaitTerminationPeriod = null;
        this.threadNamePrefix = null;
        this.customizers = null;
    }

    public TaskSchedulerBuilder(Integer poolSize, Boolean awaitTermination, Duration awaitTerminationPeriod, String threadNamePrefix, Set<TaskSchedulerCustomizer> taskSchedulerCustomizers) {
        this.poolSize = poolSize;
        this.awaitTermination = awaitTermination;
        this.awaitTerminationPeriod = awaitTerminationPeriod;
        this.threadNamePrefix = threadNamePrefix;
        this.customizers = taskSchedulerCustomizers;
    }

    public TaskSchedulerBuilder poolSize(int poolSize) {
        return new TaskSchedulerBuilder(poolSize, this.awaitTermination, this.awaitTerminationPeriod, this.threadNamePrefix, this.customizers);
    }

    public TaskSchedulerBuilder awaitTermination(boolean awaitTermination) {
        return new TaskSchedulerBuilder(this.poolSize, awaitTermination, this.awaitTerminationPeriod, this.threadNamePrefix, this.customizers);
    }

    public TaskSchedulerBuilder awaitTerminationPeriod(Duration awaitTerminationPeriod) {
        return new TaskSchedulerBuilder(this.poolSize, this.awaitTermination, awaitTerminationPeriod, this.threadNamePrefix, this.customizers);
    }

    public TaskSchedulerBuilder threadNamePrefix(String threadNamePrefix) {
        return new TaskSchedulerBuilder(this.poolSize, this.awaitTermination, this.awaitTerminationPeriod, threadNamePrefix, this.customizers);
    }

    public TaskSchedulerBuilder customizers(TaskSchedulerCustomizer ... customizers) {
        Assert.notNull((Object)customizers, (String)"Customizers must not be null");
        return this.customizers(Arrays.asList(customizers));
    }

    public TaskSchedulerBuilder customizers(Iterable<TaskSchedulerCustomizer> customizers) {
        Assert.notNull(customizers, (String)"Customizers must not be null");
        return new TaskSchedulerBuilder(this.poolSize, this.awaitTermination, this.awaitTerminationPeriod, this.threadNamePrefix, this.append(null, customizers));
    }

    public TaskSchedulerBuilder additionalCustomizers(TaskSchedulerCustomizer ... customizers) {
        Assert.notNull((Object)customizers, (String)"Customizers must not be null");
        return this.additionalCustomizers(Arrays.asList(customizers));
    }

    public TaskSchedulerBuilder additionalCustomizers(Iterable<TaskSchedulerCustomizer> customizers) {
        Assert.notNull(customizers, (String)"Customizers must not be null");
        return new TaskSchedulerBuilder(this.poolSize, this.awaitTermination, this.awaitTerminationPeriod, this.threadNamePrefix, this.append(this.customizers, customizers));
    }

    public ThreadPoolTaskScheduler build() {
        return this.configure(new ThreadPoolTaskScheduler());
    }

    public <T extends ThreadPoolTaskScheduler> T configure(T taskScheduler) {
        PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
        map.from(this.poolSize).to(arg_0 -> taskScheduler.setPoolSize(arg_0));
        map.from(this.awaitTermination).to(arg_0 -> taskScheduler.setWaitForTasksToCompleteOnShutdown(arg_0));
        map.from(this.awaitTerminationPeriod).asInt(Duration::getSeconds).to(arg_0 -> taskScheduler.setAwaitTerminationSeconds(arg_0));
        map.from(this.threadNamePrefix).to(arg_0 -> taskScheduler.setThreadNamePrefix(arg_0));
        if (!CollectionUtils.isEmpty(this.customizers)) {
            this.customizers.forEach(customizer -> customizer.customize(taskScheduler));
        }
        return taskScheduler;
    }

    private <T> Set<T> append(Set<T> set, Iterable<? extends T> additions) {
        LinkedHashSet<T> result = new LinkedHashSet<T>(set != null ? set : Collections.emptySet());
        additions.forEach(result::add);
        return Collections.unmodifiableSet(result);
    }
}

