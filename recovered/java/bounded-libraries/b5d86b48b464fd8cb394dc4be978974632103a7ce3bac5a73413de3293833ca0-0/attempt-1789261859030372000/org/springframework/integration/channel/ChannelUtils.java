/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.util.Assert
 *  org.springframework.util.ErrorHandler
 */
package org.springframework.integration.channel;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.integration.channel.MessagePublishingErrorHandler;
import org.springframework.integration.support.channel.ChannelResolverUtils;
import org.springframework.util.Assert;
import org.springframework.util.ErrorHandler;

public final class ChannelUtils {
    public static final String MESSAGE_PUBLISHING_ERROR_HANDLER_BEAN_NAME = "integrationMessagePublishingErrorHandler";

    private ChannelUtils() {
    }

    public static ErrorHandler getErrorHandler(BeanFactory beanFactory) {
        Assert.notNull((Object)beanFactory, (String)"'beanFactory' must not be null");
        if (!beanFactory.containsBean(MESSAGE_PUBLISHING_ERROR_HANDLER_BEAN_NAME)) {
            return new MessagePublishingErrorHandler(ChannelResolverUtils.getChannelResolver(beanFactory));
        }
        return (ErrorHandler)beanFactory.getBean(MESSAGE_PUBLISHING_ERROR_HANDLER_BEAN_NAME, ErrorHandler.class);
    }
}

