/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.quartz.Scheduler
 *  org.springframework.boot.sql.init.dependency.AbstractBeansOfTypeDependsOnDatabaseInitializationDetector
 *  org.springframework.scheduling.quartz.SchedulerFactoryBean
 */
package org.springframework.boot.autoconfigure.quartz;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.quartz.Scheduler;
import org.springframework.boot.sql.init.dependency.AbstractBeansOfTypeDependsOnDatabaseInitializationDetector;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

class SchedulerDependsOnDatabaseInitializationDetector
extends AbstractBeansOfTypeDependsOnDatabaseInitializationDetector {
    SchedulerDependsOnDatabaseInitializationDetector() {
    }

    protected Set<Class<?>> getDependsOnDatabaseInitializationBeanTypes() {
        return new HashSet(Arrays.asList(Scheduler.class, SchedulerFactoryBean.class));
    }
}

