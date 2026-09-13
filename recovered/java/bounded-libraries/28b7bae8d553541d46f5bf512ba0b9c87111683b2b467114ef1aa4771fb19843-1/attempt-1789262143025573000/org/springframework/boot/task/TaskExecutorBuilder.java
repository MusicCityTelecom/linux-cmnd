/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.BeanUtils
 *  org.springframework.core.task.TaskDecorator
 *  org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
 *  org.springframework.util.Assert
 *  org.springframework.util.CollectionUtils
 */
package org.springframework.boot.task;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.boot.task.TaskExecutorCustomizer;
import org.springframework.core.task.TaskDecorator;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

public class TaskExecutorBuilder {
    private final Integer queueCapacity;
    private final Integer corePoolSize;
    private final Integer maxPoolSize;
    private final Boolean allowCoreThreadTimeOut;
    private final Duration keepAlive;
    private final Boolean awaitTermination;
    private final Duration awaitTerminationPeriod;
    private final String threadNamePrefix;
    private final TaskDecorator taskDecorator;
    private final Set<TaskExecutorCustomizer> customizers;

    public TaskExecutorBuilder() {
        this.queueCapacity = null;
        this.corePoolSize = null;
        this.maxPoolSize = null;
        this.allowCoreThreadTimeOut = null;
        this.keepAlive = null;
        this.awaitTermination = null;
        this.awaitTerminationPeriod = null;
        this.threadNamePrefix = null;
        this.taskDecorator = null;
        this.customizers = null;
    }

    private TaskExecutorBuilder(Integer queueCapacity, Integer corePoolSize, Integer maxPoolSize, Boolean allowCoreThreadTimeOut, Duration keepAlive, Boolean awaitTermination, Duration awaitTerminationPeriod, String threadNamePrefix, TaskDecorator taskDecorator, Set<TaskExecutorCustomizer> customizers) {
        this.queueCapacity = queueCapacity;
        this.corePoolSize = corePoolSize;
        this.maxPoolSize = maxPoolSize;
        this.allowCoreThreadTimeOut = allowCoreThreadTimeOut;
        this.keepAlive = keepAlive;
        this.awaitTermination = awaitTermination;
        this.awaitTerminationPeriod = awaitTerminationPeriod;
        this.threadNamePrefix = threadNamePrefix;
        this.taskDecorator = taskDecorator;
        this.customizers = customizers;
    }

    public TaskExecutorBuilder queueCapacity(int queueCapacity) {
        return new TaskExecutorBuilder(queueCapacity, this.corePoolSize, this.maxPoolSize, this.allowCoreThreadTimeOut, this.keepAlive, this.awaitTermination, this.awaitTerminationPeriod, this.threadNamePrefix, this.taskDecorator, this.customizers);
    }

    public TaskExecutorBuilder corePoolSize(int corePoolSize) {
        return new TaskExecutorBuilder(this.queueCapacity, corePoolSize, this.maxPoolSize, this.allowCoreThreadTimeOut, this.keepAlive, this.awaitTermination, this.awaitTerminationPeriod, this.threadNamePrefix, this.taskDecorator, this.customizers);
    }

    public TaskExecutorBuilder maxPoolSize(int maxPoolSize) {
        return new TaskExecutorBuilder(this.queueCapacity, this.corePoolSize, maxPoolSize, this.allowCoreThreadTimeOut, this.keepAlive, this.awaitTermination, this.awaitTerminationPeriod, this.threadNamePrefix, this.taskDecorator, this.customizers);
    }

    public TaskExecutorBuilder allowCoreThreadTimeOut(boolean allowCoreThreadTimeOut) {
        return new TaskExecutorBuilder(this.queueCapacity, this.corePoolSize, this.maxPoolSize, allowCoreThreadTimeOut, this.keepAlive, this.awaitTermination, this.awaitTerminationPeriod, this.threadNamePrefix, this.taskDecorator, this.customizers);
    }

    public TaskExecutorBuilder keepAlive(Duration keepAlive) {
        return new TaskExecutorBuilder(this.queueCapacity, this.corePoolSize, this.maxPoolSize, this.allowCoreThreadTimeOut, keepAlive, this.awaitTermination, this.awaitTerminationPeriod, this.threadNamePrefix, this.taskDecorator, this.customizers);
    }

    public TaskExecutorBuilder awaitTermination(boolean awaitTermination) {
        return new TaskExecutorBuilder(this.queueCapacity, this.corePoolSize, this.maxPoolSize, this.allowCoreThreadTimeOut, this.keepAlive, awaitTermination, this.awaitTerminationPeriod, this.threadNamePrefix, this.taskDecorator, this.customizers);
    }

    public TaskExecutorBuilder awaitTerminationPeriod(Duration awaitTerminationPeriod) {
        return new TaskExecutorBuilder(this.queueCapacity, this.corePoolSize, this.maxPoolSize, this.allowCoreThreadTimeOut, this.keepAlive, this.awaitTermination, awaitTerminationPeriod, this.threadNamePrefix, this.taskDecorator, this.customizers);
    }

    public TaskExecutorBuilder threadNamePrefix(String threadNamePrefix) {
        return new TaskExecutorBuilder(this.queueCapacity, this.corePoolSize, this.maxPoolSize, this.allowCoreThreadTimeOut, this.keepAlive, this.awaitTermination, this.awaitTerminationPeriod, threadNamePrefix, this.taskDecorator, this.customizers);
    }

    public TaskExecutorBuilder taskDecorator(TaskDecorator taskDecorator) {
        return new TaskExecutorBuilder(this.queueCapacity, this.corePoolSize, this.maxPoolSize, this.allowCoreThreadTimeOut, this.keepAlive, this.awaitTermination, this.awaitTerminationPeriod, this.threadNamePrefix, taskDecorator, this.customizers);
    }

    public TaskExecutorBuilder customizers(TaskExecutorCustomizer ... customizers) {
        Assert.notNull((Object)customizers, (String)"Customizers must not be null");
        return this.customizers(Arrays.asList(customizers));
    }

    public TaskExecutorBuilder customizers(Iterable<TaskExecutorCustomizer> customizers) {
        Assert.notNull(customizers, (String)"Customizers must not be null");
        return new TaskExecutorBuilder(this.queueCapacity, this.corePoolSize, this.maxPoolSize, this.allowCoreThreadTimeOut, this.keepAlive, this.awaitTermination, this.awaitTerminationPeriod, this.threadNamePrefix, this.taskDecorator, this.append(null, customizers));
    }

    public TaskExecutorBuilder additionalCustomizers(TaskExecutorCustomizer ... customizers) {
        Assert.notNull((Object)customizers, (String)"Customizers must not be null");
        return this.additionalCustomizers(Arrays.asList(customizers));
    }

    public TaskExecutorBuilder additionalCustomizers(Iterable<TaskExecutorCustomizer> customizers) {
        Assert.notNull(customizers, (String)"Customizers must not be null");
        return new TaskExecutorBuilder(this.queueCapacity, this.corePoolSize, this.maxPoolSize, this.allowCoreThreadTimeOut, this.keepAlive, this.awaitTermination, this.awaitTerminationPeriod, this.threadNamePrefix, this.taskDecorator, this.append(this.customizers, customizers));
    }

    public ThreadPoolTaskExecutor build() {
        return this.configure(new ThreadPoolTaskExecutor());
    }

    public <T extends ThreadPoolTaskExecutor> T build(Class<T> taskExecutorClass) {
        return (T)this.configure((ThreadPoolTaskExecutor)BeanUtils.instantiateClass(taskExecutorClass));
    }

    public <T extends ThreadPoolTaskExecutor> T configure(T taskExecutor) {
        PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
        map.from(this.queueCapacity).to(arg_0 -> taskExecutor.setQueueCapacity(arg_0));
        map.from(this.corePoolSize).to(arg_0 -> taskExecutor.setCorePoolSize(arg_0));
        map.from(this.maxPoolSize).to(arg_0 -> taskExecutor.setMaxPoolSize(arg_0));
        map.from(this.keepAlive).asInt(Duration::getSeconds).to(arg_0 -> taskExecutor.setKeepAliveSeconds(arg_0));
        map.from(this.allowCoreThreadTimeOut).to(arg_0 -> taskExecutor.setAllowCoreThreadTimeOut(arg_0));
        map.from(this.awaitTermination).to(arg_0 -> taskExecutor.setWaitForTasksToCompleteOnShutdown(arg_0));
        map.from(this.awaitTerminationPeriod).as(Duration::toMillis).to(arg_0 -> taskExecutor.setAwaitTerminationMillis(arg_0));
        map.from(this.threadNamePrefix).whenHasText().to(arg_0 -> taskExecutor.setThreadNamePrefix(arg_0));
        map.from(this.taskDecorator).to(arg_0 -> taskExecutor.setTaskDecorator(arg_0));
        if (!CollectionUtils.isEmpty(this.customizers)) {
            this.customizers.forEach(customizer -> customizer.customize(taskExecutor));
        }
        return taskExecutor;
    }

    private <T> Set<T> append(Set<T> set, Iterable<? extends T> additions) {
        LinkedHashSet<T> result = new LinkedHashSet<T>(set != null ? set : Collections.emptySet());
        additions.forEach(result::add);
        return Collections.unmodifiableSet(result);
    }
}

