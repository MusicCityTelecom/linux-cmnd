/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.BeansException
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.BeanFactoryAware
 *  org.springframework.core.convert.ConversionService
 *  org.springframework.core.convert.support.DefaultConversionService
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessageHeaders
 *  org.springframework.messaging.converter.MessageConverter
 *  org.springframework.util.Assert
 */
package org.springframework.integration.support.converter;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.integration.support.utils.IntegrationUtils;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.util.Assert;

public class DefaultDatatypeChannelMessageConverter
implements MessageConverter,
BeanFactoryAware {
    private volatile ConversionService conversionService = DefaultConversionService.getSharedInstance();
    private volatile boolean conversionServiceSet;

    public void setConversionService(ConversionService conversionService) {
        Assert.notNull((Object)conversionService, (String)"'conversionService' must not be null");
        this.conversionService = conversionService;
        this.conversionServiceSet = true;
    }

    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        ConversionService integrationConversionService;
        if (!this.conversionServiceSet && beanFactory != null && (integrationConversionService = IntegrationUtils.getConversionService(beanFactory)) != null) {
            this.conversionService = integrationConversionService;
        }
    }

    public Object fromMessage(Message<?> message, Class<?> targetClass) {
        if (this.conversionService.canConvert(message.getPayload().getClass(), targetClass)) {
            return this.conversionService.convert(message.getPayload(), targetClass);
        }
        return null;
    }

    public Message<?> toMessage(Object payload, MessageHeaders header) {
        throw new UnsupportedOperationException("This converter does not support this method");
    }
}

