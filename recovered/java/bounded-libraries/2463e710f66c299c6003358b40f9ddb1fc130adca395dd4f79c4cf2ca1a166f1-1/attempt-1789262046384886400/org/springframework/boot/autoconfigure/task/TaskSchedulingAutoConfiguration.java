/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.ObjectProvider
 *  org.springframework.boot.LazyInitializationExcludeFilter
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.boot.task.TaskSchedulerBuilder
 *  org.springframework.boot.task.TaskSchedulerCustomizer
 *  org.springframework.context.annotation.Bean
 *  org.springframework.scheduling.TaskScheduler
 *  org.springframework.scheduling.annotation.SchedulingConfigurer
 *  org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler
 */
package org.springframework.boot.autoconfigure.task;

import java.util.concurrent.ScheduledExecutorService;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.LazyInitializationExcludeFilter;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.task.ScheduledBeanLazyInitializationExcludeFilter;
import org.springframework.boot.autoconfigure.task.TaskExecutionAutoConfiguration;
import org.springframework.boot.autoconfigure.task.TaskSchedulingProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.task.TaskSchedulerBuilder;
import org.springframework.boot.task.TaskSchedulerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@ConditionalOnClass(value={ThreadPoolTaskScheduler.class})
@AutoConfiguration(after={TaskExecutionAutoConfiguration.class})
@EnableConfigurationProperties(value={TaskSchedulingProperties.class})
public class TaskSchedulingAutoConfiguration {
    @Bean
    @ConditionalOnBean(name={"org.springframework.context.annotation.internalScheduledAnnotationProcessor"})
    @ConditionalOnMissingBean(value={SchedulingConfigurer.class, TaskScheduler.class, ScheduledExecutorService.class})
    public ThreadPoolTaskScheduler taskScheduler(TaskSchedulerBuilder builder) {
        return builder.build();
    }

    @Bean
    public static LazyInitializationExcludeFilter scheduledBeanLazyInitializationExcludeFilter() {
        return new ScheduledBeanLazyInitializationExcludeFilter();
    }

    @Bean
    @ConditionalOnMissingBean
    public TaskSchedulerBuilder taskSchedulerBuilder(TaskSchedulingProperties properties, ObjectProvider<TaskSchedulerCustomizer> taskSchedulerCustomizers) {
        TaskSchedulerBuilder builder = new TaskSchedulerBuilder();
        builder = builder.poolSize(properties.getPool().getSize());
        TaskSchedulingProperties.Shutdown shutdown = properties.getShutdown();
        builder = builder.awaitTermination(shutdown.isAwaitTermination());
        builder = builder.awaitTerminationPeriod(shutdown.getAwaitTerminationPeriod());
        builder = builder.threadNamePrefix(properties.getThreadNamePrefix());
        builder = builder.customizers(taskSchedulerCustomizers);
        return builder;
    }
}

