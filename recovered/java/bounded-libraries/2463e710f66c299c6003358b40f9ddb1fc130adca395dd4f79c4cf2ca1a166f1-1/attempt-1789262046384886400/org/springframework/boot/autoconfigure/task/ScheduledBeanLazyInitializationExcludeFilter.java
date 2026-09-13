/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.aop.framework.AopInfrastructureBean
 *  org.springframework.beans.factory.config.BeanDefinition
 *  org.springframework.boot.LazyInitializationExcludeFilter
 *  org.springframework.core.MethodIntrospector
 *  org.springframework.core.annotation.AnnotatedElementUtils
 *  org.springframework.core.annotation.AnnotationUtils
 *  org.springframework.scheduling.TaskScheduler
 *  org.springframework.scheduling.annotation.Scheduled
 *  org.springframework.scheduling.annotation.Schedules
 *  org.springframework.util.ClassUtils
 */
package org.springframework.boot.autoconfigure.task;

import java.lang.reflect.AnnotatedElement;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import org.springframework.aop.framework.AopInfrastructureBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.LazyInitializationExcludeFilter;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.Schedules;
import org.springframework.util.ClassUtils;

class ScheduledBeanLazyInitializationExcludeFilter
implements LazyInitializationExcludeFilter {
    private final Set<Class<?>> nonAnnotatedClasses = Collections.newSetFromMap(new ConcurrentHashMap(64));

    ScheduledBeanLazyInitializationExcludeFilter() {
        this.nonAnnotatedClasses.add(AopInfrastructureBean.class);
        this.nonAnnotatedClasses.add(TaskScheduler.class);
        this.nonAnnotatedClasses.add(ScheduledExecutorService.class);
    }

    public boolean isExcluded(String beanName, BeanDefinition beanDefinition, Class<?> beanType) {
        return this.hasScheduledTask(beanType);
    }

    private boolean hasScheduledTask(Class<?> type) {
        Class targetType = ClassUtils.getUserClass(type);
        if (!this.nonAnnotatedClasses.contains(targetType) && AnnotationUtils.isCandidateClass((Class)targetType, Arrays.asList(Scheduled.class, Schedules.class))) {
            Map annotatedMethods = MethodIntrospector.selectMethods((Class)targetType, method -> {
                Set scheduledAnnotations = AnnotatedElementUtils.getMergedRepeatableAnnotations((AnnotatedElement)method, Scheduled.class, Schedules.class);
                return !scheduledAnnotations.isEmpty() ? scheduledAnnotations : null;
            });
            if (annotatedMethods.isEmpty()) {
                this.nonAnnotatedClasses.add(targetType);
            }
            return !annotatedMethods.isEmpty();
        }
        return false;
    }
}

