/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.scheduling.quartz.SchedulerFactoryBean
 */
package org.springframework.boot.autoconfigure.quartz;

import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@FunctionalInterface
public interface SchedulerFactoryBeanCustomizer {
    public void customize(SchedulerFactoryBean var1);
}

