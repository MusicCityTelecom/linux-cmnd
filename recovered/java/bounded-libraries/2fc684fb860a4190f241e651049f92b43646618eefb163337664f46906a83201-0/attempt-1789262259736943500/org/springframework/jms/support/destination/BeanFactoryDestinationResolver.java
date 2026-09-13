/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.jms.Destination
 *  javax.jms.JMSException
 *  javax.jms.Session
 *  org.springframework.beans.BeansException
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.BeanFactoryAware
 *  org.springframework.lang.Nullable
 *  org.springframework.util.Assert
 */
package org.springframework.jms.support.destination;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Session;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.jms.support.destination.DestinationResolutionException;
import org.springframework.jms.support.destination.DestinationResolver;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

public class BeanFactoryDestinationResolver
implements DestinationResolver,
BeanFactoryAware {
    @Nullable
    private BeanFactory beanFactory;

    public BeanFactoryDestinationResolver() {
    }

    public BeanFactoryDestinationResolver(BeanFactory beanFactory) {
        Assert.notNull((Object)beanFactory, (String)"BeanFactory is required");
        this.beanFactory = beanFactory;
    }

    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public Destination resolveDestinationName(@Nullable Session session, String destinationName, boolean pubSubDomain) throws JMSException {
        Assert.state((this.beanFactory != null ? 1 : 0) != 0, (String)"BeanFactory is required");
        try {
            return (Destination)this.beanFactory.getBean(destinationName, Destination.class);
        }
        catch (BeansException ex) {
            throw new DestinationResolutionException("Failed to look up Destination bean with name '" + destinationName + "'", ex);
        }
    }
}

