/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.beans.BeansException
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.BeanFactoryAware
 *  org.springframework.beans.factory.NoSuchBeanDefinitionException
 *  org.springframework.messaging.MessageChannel
 *  org.springframework.messaging.core.DestinationResolutionException
 *  org.springframework.messaging.core.DestinationResolver
 *  org.springframework.util.Assert
 */
package org.springframework.integration.support.channel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.integration.support.channel.HeaderChannelRegistry;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.core.DestinationResolutionException;
import org.springframework.messaging.core.DestinationResolver;
import org.springframework.util.Assert;

public class BeanFactoryChannelResolver
implements DestinationResolver<MessageChannel>,
BeanFactoryAware {
    private static final Log LOGGER = LogFactory.getLog(BeanFactoryChannelResolver.class);
    private BeanFactory beanFactory;
    private HeaderChannelRegistry replyChannelRegistry;
    private volatile boolean initialized;

    public BeanFactoryChannelResolver() {
    }

    public BeanFactoryChannelResolver(BeanFactory beanFactory) {
        Assert.notNull((Object)beanFactory, (String)"BeanFactory must not be null");
        this.beanFactory = beanFactory;
    }

    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public MessageChannel resolveDestination(String name) {
        Assert.state((this.beanFactory != null ? 1 : 0) != 0, (String)"BeanFactory is required");
        try {
            return (MessageChannel)this.beanFactory.getBean(name, MessageChannel.class);
        }
        catch (BeansException e) {
            MessageChannel channel;
            if (!(e instanceof NoSuchBeanDefinitionException)) {
                throw new DestinationResolutionException("A bean definition with name '" + name + "' exists, but failed to be created", (Throwable)e);
            }
            if (!this.initialized) {
                BeanFactoryChannelResolver beanFactoryChannelResolver = this;
                synchronized (beanFactoryChannelResolver) {
                    if (!this.initialized) {
                        try {
                            this.replyChannelRegistry = (HeaderChannelRegistry)this.beanFactory.getBean("integrationHeaderChannelRegistry", HeaderChannelRegistry.class);
                        }
                        catch (Exception ex) {
                            LOGGER.debug((Object)"No HeaderChannelRegistry found");
                        }
                        this.initialized = true;
                    }
                }
            }
            if (this.replyChannelRegistry != null && (channel = this.replyChannelRegistry.channelNameToChannel(name)) != null) {
                return channel;
            }
            throw new DestinationResolutionException("failed to look up MessageChannel with name '" + name + "' in the BeanFactory" + (this.replyChannelRegistry == null ? " (and there is no HeaderChannelRegistry present)." : "."), (Throwable)e);
        }
    }
}

