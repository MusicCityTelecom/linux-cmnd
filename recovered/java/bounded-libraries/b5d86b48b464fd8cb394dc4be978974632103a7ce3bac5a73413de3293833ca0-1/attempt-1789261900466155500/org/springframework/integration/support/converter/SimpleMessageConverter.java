/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.BeansException
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.BeanFactoryAware
 *  org.springframework.lang.Nullable
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessageHeaders
 *  org.springframework.messaging.converter.MessageConversionException
 *  org.springframework.messaging.converter.MessageConverter
 */
package org.springframework.integration.support.converter;

import java.util.Map;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.integration.mapping.InboundMessageMapper;
import org.springframework.integration.mapping.OutboundMessageMapper;
import org.springframework.integration.support.DefaultMessageBuilderFactory;
import org.springframework.integration.support.MessageBuilderFactory;
import org.springframework.integration.support.utils.IntegrationUtils;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.converter.MessageConversionException;
import org.springframework.messaging.converter.MessageConverter;

public class SimpleMessageConverter
implements MessageConverter,
BeanFactoryAware {
    private volatile InboundMessageMapper inboundMessageMapper;
    private volatile OutboundMessageMapper outboundMessageMapper;
    private volatile MessageBuilderFactory messageBuilderFactory = new DefaultMessageBuilderFactory();
    private volatile boolean messageBuilderFactorySet;
    private BeanFactory beanFactory;

    public SimpleMessageConverter() {
        this(null, null);
    }

    public SimpleMessageConverter(InboundMessageMapper<?> inboundMessageMapper) {
        this(inboundMessageMapper, inboundMessageMapper instanceof OutboundMessageMapper ? (OutboundMessageMapper)((Object)inboundMessageMapper) : null);
    }

    public SimpleMessageConverter(OutboundMessageMapper<?> outboundMessageMapper) {
        this(outboundMessageMapper instanceof InboundMessageMapper ? (InboundMessageMapper)((Object)outboundMessageMapper) : null, outboundMessageMapper);
    }

    public SimpleMessageConverter(InboundMessageMapper<?> inboundMessageMapper, OutboundMessageMapper<?> outboundMessageMapper) {
        this.setInboundMessageMapper(inboundMessageMapper);
        this.setOutboundMessageMapper(outboundMessageMapper);
    }

    public final void setInboundMessageMapper(InboundMessageMapper<?> inboundMessageMapper) {
        this.inboundMessageMapper = inboundMessageMapper != null ? inboundMessageMapper : new DefaultInboundMessageMapper();
    }

    public final void setOutboundMessageMapper(OutboundMessageMapper<?> outboundMessageMapper) {
        this.outboundMessageMapper = outboundMessageMapper != null ? outboundMessageMapper : new DefaultOutboundMessageMapper();
    }

    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    protected MessageBuilderFactory getMessageBuilderFactory() {
        if (!this.messageBuilderFactorySet) {
            if (this.beanFactory != null) {
                this.messageBuilderFactory = IntegrationUtils.getMessageBuilderFactory(this.beanFactory);
            }
            this.messageBuilderFactorySet = true;
        }
        return this.messageBuilderFactory;
    }

    @Nullable
    public Message<?> toMessage(Object object, @Nullable MessageHeaders headers) {
        try {
            return this.inboundMessageMapper.toMessage(object, (Map<String, Object>)headers);
        }
        catch (Exception e) {
            throw new MessageConversionException("failed to convert object to Message", (Throwable)e);
        }
    }

    @Nullable
    public Object fromMessage(Message<?> message, Class<?> targetClass) {
        try {
            return this.outboundMessageMapper.fromMessage(message);
        }
        catch (Exception e) {
            throw new MessageConversionException(message, "failed to convert Message to object", (Throwable)e);
        }
    }

    private class DefaultInboundMessageMapper
    implements InboundMessageMapper<Object> {
        DefaultInboundMessageMapper() {
        }

        @Override
        public Message<?> toMessage(@Nullable Object object, @Nullable Map<String, Object> headers) {
            if (object == null) {
                return null;
            }
            if (object instanceof Message) {
                return (Message)object;
            }
            return SimpleMessageConverter.this.getMessageBuilderFactory().withPayload(object).copyHeadersIfAbsent(headers).build();
        }
    }

    private static class DefaultOutboundMessageMapper
    implements OutboundMessageMapper<Object> {
        DefaultOutboundMessageMapper() {
        }

        @Override
        public Object fromMessage(@Nullable Message<?> message) {
            return message != null ? message.getPayload() : null;
        }
    }
}

