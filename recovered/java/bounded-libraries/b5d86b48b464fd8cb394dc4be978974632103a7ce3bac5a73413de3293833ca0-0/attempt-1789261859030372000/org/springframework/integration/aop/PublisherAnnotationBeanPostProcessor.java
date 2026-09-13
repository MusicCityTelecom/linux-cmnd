/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.aop.framework.autoproxy.AbstractBeanFactoryAwareAdvisingPostProcessor
 *  org.springframework.beans.factory.BeanCreationException
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.BeanNameAware
 *  org.springframework.beans.factory.NoUniqueBeanDefinitionException
 *  org.springframework.beans.factory.SmartInitializingSingleton
 */
package org.springframework.integration.aop;

import org.springframework.aop.framework.autoproxy.AbstractBeanFactoryAwareAdvisingPostProcessor;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.integration.aop.PublisherAnnotationAdvisor;

public class PublisherAnnotationBeanPostProcessor
extends AbstractBeanFactoryAwareAdvisingPostProcessor
implements BeanNameAware,
SmartInitializingSingleton {
    private String defaultChannelName;
    private String beanName;
    private BeanFactory beanFactory;

    public void setDefaultChannelName(String defaultChannelName) {
        this.defaultChannelName = defaultChannelName;
    }

    public void setBeanName(String name) {
        this.beanName = name;
    }

    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
        super.setBeanFactory(beanFactory);
        PublisherAnnotationAdvisor publisherAnnotationAdvisor = new PublisherAnnotationAdvisor();
        publisherAnnotationAdvisor.setBeanFactory(beanFactory);
        publisherAnnotationAdvisor.setDefaultChannelName(this.defaultChannelName);
        this.advisor = publisherAnnotationAdvisor;
    }

    public void afterSingletonsInstantiated() {
        try {
            this.beanFactory.getBean(PublisherAnnotationBeanPostProcessor.class);
        }
        catch (NoUniqueBeanDefinitionException ex) {
            throw new BeanCreationException(this.beanName, "Only one 'PublisherAnnotationBeanPostProcessor' bean can be defined in the application context. Do not use '@EnablePublisher' (or '<int:enable-publisher>') if you declare a 'PublisherAnnotationBeanPostProcessor' bean definition manually.", (Throwable)ex);
        }
    }
}

