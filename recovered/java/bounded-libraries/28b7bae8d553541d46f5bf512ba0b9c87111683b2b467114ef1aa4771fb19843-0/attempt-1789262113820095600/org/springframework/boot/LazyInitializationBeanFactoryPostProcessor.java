/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.BeansException
 *  org.springframework.beans.factory.NoSuchBeanDefinitionException
 *  org.springframework.beans.factory.SmartInitializingSingleton
 *  org.springframework.beans.factory.config.BeanDefinition
 *  org.springframework.beans.factory.config.BeanFactoryPostProcessor
 *  org.springframework.beans.factory.config.ConfigurableListableBeanFactory
 *  org.springframework.beans.factory.support.AbstractBeanDefinition
 *  org.springframework.core.Ordered
 */
package org.springframework.boot;

import java.util.ArrayList;
import java.util.Collection;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.boot.LazyInitializationExcludeFilter;
import org.springframework.core.Ordered;

public final class LazyInitializationBeanFactoryPostProcessor
implements BeanFactoryPostProcessor,
Ordered {
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        Collection<LazyInitializationExcludeFilter> filters = this.getFilters(beanFactory);
        for (String beanName : beanFactory.getBeanDefinitionNames()) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
            if (!(beanDefinition instanceof AbstractBeanDefinition)) continue;
            this.postProcess(beanFactory, filters, beanName, (AbstractBeanDefinition)beanDefinition);
        }
    }

    private Collection<LazyInitializationExcludeFilter> getFilters(ConfigurableListableBeanFactory beanFactory) {
        ArrayList<LazyInitializationExcludeFilter> filters = new ArrayList<LazyInitializationExcludeFilter>(beanFactory.getBeansOfType(LazyInitializationExcludeFilter.class, false, false).values());
        filters.add(LazyInitializationExcludeFilter.forBeanTypes(SmartInitializingSingleton.class));
        return filters;
    }

    private void postProcess(ConfigurableListableBeanFactory beanFactory, Collection<LazyInitializationExcludeFilter> filters, String beanName, AbstractBeanDefinition beanDefinition) {
        Boolean lazyInit = beanDefinition.getLazyInit();
        if (lazyInit != null) {
            return;
        }
        Class<?> beanType = this.getBeanType(beanFactory, beanName);
        if (!this.isExcluded(filters, beanName, beanDefinition, beanType)) {
            beanDefinition.setLazyInit(true);
        }
    }

    private Class<?> getBeanType(ConfigurableListableBeanFactory beanFactory, String beanName) {
        try {
            return beanFactory.getType(beanName, false);
        }
        catch (NoSuchBeanDefinitionException ex) {
            return null;
        }
    }

    private boolean isExcluded(Collection<LazyInitializationExcludeFilter> filters, String beanName, AbstractBeanDefinition beanDefinition, Class<?> beanType) {
        if (beanType != null) {
            for (LazyInitializationExcludeFilter filter : filters) {
                if (!filter.isExcluded(beanName, (BeanDefinition)beanDefinition, beanType)) continue;
                return true;
            }
        }
        return false;
    }

    public int getOrder() {
        return Integer.MIN_VALUE;
    }
}

