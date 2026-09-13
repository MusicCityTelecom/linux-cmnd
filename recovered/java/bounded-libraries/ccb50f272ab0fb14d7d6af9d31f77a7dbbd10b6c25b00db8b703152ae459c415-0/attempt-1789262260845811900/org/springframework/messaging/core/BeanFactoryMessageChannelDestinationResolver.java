/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.BeansException
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.BeanFactoryAware
 *  org.springframework.lang.Nullable
 *  org.springframework.util.Assert
 */
package org.springframework.messaging.core;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.lang.Nullable;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.core.DestinationResolutionException;
import org.springframework.messaging.core.DestinationResolver;
import org.springframework.util.Assert;

public class BeanFactoryMessageChannelDestinationResolver
implements DestinationResolver<MessageChannel>,
BeanFactoryAware {
    @Nullable
    private BeanFactory beanFactory;

    public BeanFactoryMessageChannelDestinationResolver() {
    }

    public BeanFactoryMessageChannelDestinationResolver(BeanFactory beanFactory) {
        Assert.notNull((Object)beanFactory, (String)"beanFactory must not be null");
        this.beanFactory = beanFactory;
    }

    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public MessageChannel resolveDestination(String name) {
        Assert.state((this.beanFactory != null ? 1 : 0) != 0, (String)"No BeanFactory configured");
        try {
            return (MessageChannel)this.beanFactory.getBean(name, MessageChannel.class);
        }
        catch (BeansException ex) {
            throw new DestinationResolutionException("Failed to find MessageChannel bean with name '" + name + "'", (Throwable)ex);
        }
    }
}

