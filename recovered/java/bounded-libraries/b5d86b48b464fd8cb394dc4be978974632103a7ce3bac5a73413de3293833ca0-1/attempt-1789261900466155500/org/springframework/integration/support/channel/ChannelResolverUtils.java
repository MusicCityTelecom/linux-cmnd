/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.messaging.MessageChannel
 *  org.springframework.messaging.core.DestinationResolver
 *  org.springframework.util.Assert
 */
package org.springframework.integration.support.channel;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.integration.support.channel.BeanFactoryChannelResolver;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.core.DestinationResolver;
import org.springframework.util.Assert;

public final class ChannelResolverUtils {
    public static final String CHANNEL_RESOLVER_BEAN_NAME = "integrationChannelResolver";

    private ChannelResolverUtils() {
    }

    public static DestinationResolver<MessageChannel> getChannelResolver(BeanFactory beanFactory) {
        Assert.notNull((Object)beanFactory, (String)"'beanFactory' must not be null");
        if (!beanFactory.containsBean(CHANNEL_RESOLVER_BEAN_NAME)) {
            return new BeanFactoryChannelResolver(beanFactory);
        }
        return (DestinationResolver)beanFactory.getBean(CHANNEL_RESOLVER_BEAN_NAME, DestinationResolver.class);
    }
}

