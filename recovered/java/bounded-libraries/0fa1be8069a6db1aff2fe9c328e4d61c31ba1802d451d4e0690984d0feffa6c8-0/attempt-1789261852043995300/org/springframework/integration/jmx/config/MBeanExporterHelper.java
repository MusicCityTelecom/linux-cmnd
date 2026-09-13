/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.aop.support.AopUtils
 *  org.springframework.beans.BeansException
 *  org.springframework.beans.DirectFieldAccessor
 *  org.springframework.beans.factory.config.DestructionAwareBeanPostProcessor
 *  org.springframework.core.Ordered
 *  org.springframework.core.annotation.AnnotatedElementUtils
 *  org.springframework.integration.support.management.IntegrationManagedResource
 *  org.springframework.jmx.export.MBeanExporter
 */
package org.springframework.integration.jmx.config;

import java.lang.reflect.AnnotatedElement;
import java.util.Collection;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.DirectFieldAccessor;
import org.springframework.beans.factory.config.DestructionAwareBeanPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.integration.monitor.IntegrationMBeanExporter;
import org.springframework.integration.support.management.IntegrationManagedResource;
import org.springframework.jmx.export.MBeanExporter;

class MBeanExporterHelper
implements DestructionAwareBeanPostProcessor,
Ordered {
    private final Queue<MBeanExporter> mBeanExportersForExcludes = new ConcurrentLinkedQueue<MBeanExporter>();
    private final Set<String> siBeanNames = ConcurrentHashMap.newKeySet();

    MBeanExporterHelper() {
    }

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if ("$autoCreateChannelCandidates".equals(beanName)) {
            Collection autoCreateChannelCandidatesNames = (Collection)new DirectFieldAccessor(bean).getPropertyValue("channelNames");
            this.siBeanNames.addAll(autoCreateChannelCandidatesNames);
            if (!this.mBeanExportersForExcludes.isEmpty()) {
                autoCreateChannelCandidatesNames.stream().map(candidateName -> mBeanExporter -> mBeanExporter.addExcludedBean(candidateName)).forEach(this.mBeanExportersForExcludes::forEach);
            }
        }
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (AnnotatedElementUtils.isAnnotated((AnnotatedElement)AopUtils.getTargetClass((Object)bean), (String)IntegrationManagedResource.class.getName())) {
            this.siBeanNames.add(beanName);
            this.mBeanExportersForExcludes.forEach(mBeanExporter -> mBeanExporter.addExcludedBean(beanName));
        }
        if (bean instanceof MBeanExporter && !(bean instanceof IntegrationMBeanExporter)) {
            MBeanExporter mBeanExporter2 = (MBeanExporter)bean;
            this.mBeanExportersForExcludes.add(mBeanExporter2);
            this.siBeanNames.forEach(arg_0 -> ((MBeanExporter)mBeanExporter2).addExcludedBean(arg_0));
        }
        return bean;
    }

    public boolean requiresDestruction(Object bean) {
        return bean instanceof MBeanExporter && !(bean instanceof IntegrationMBeanExporter) || AnnotatedElementUtils.isAnnotated((AnnotatedElement)AopUtils.getTargetClass((Object)bean), (String)IntegrationManagedResource.class.getName());
    }

    public void postProcessBeforeDestruction(Object bean, String beanName) throws BeansException {
        if (bean instanceof MBeanExporter) {
            this.mBeanExportersForExcludes.remove(bean);
        } else {
            this.siBeanNames.remove(beanName);
        }
    }

    public int getOrder() {
        return Integer.MIN_VALUE;
    }
}

