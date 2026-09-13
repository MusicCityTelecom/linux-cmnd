/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.jms.JMSException
 *  javax.jms.Message
 *  javax.jms.Session
 *  org.springframework.beans.factory.InitializingBean
 *  org.springframework.lang.Nullable
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessageHeaders
 *  org.springframework.messaging.support.MessageBuilder
 *  org.springframework.util.Assert
 */
package org.springframework.jms.support.converter;

import java.util.Map;
import javax.jms.JMSException;
import javax.jms.Session;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jms.support.JmsHeaderMapper;
import org.springframework.jms.support.SimpleJmsHeaderMapper;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.SimpleMessageConverter;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.Assert;

public class MessagingMessageConverter
implements MessageConverter,
InitializingBean {
    private MessageConverter payloadConverter;
    private JmsHeaderMapper headerMapper;

    public MessagingMessageConverter() {
        this(new SimpleMessageConverter(), new SimpleJmsHeaderMapper());
    }

    public MessagingMessageConverter(MessageConverter payloadConverter) {
        this(payloadConverter, new SimpleJmsHeaderMapper());
    }

    public MessagingMessageConverter(MessageConverter payloadConverter, JmsHeaderMapper headerMapper) {
        Assert.notNull((Object)payloadConverter, (String)"PayloadConverter must not be null");
        Assert.notNull((Object)headerMapper, (String)"HeaderMapper must not be null");
        this.payloadConverter = payloadConverter;
        this.headerMapper = headerMapper;
    }

    public void setPayloadConverter(MessageConverter payloadConverter) {
        this.payloadConverter = payloadConverter;
    }

    public void setHeaderMapper(JmsHeaderMapper headerMapper) {
        this.headerMapper = headerMapper;
    }

    public void afterPropertiesSet() {
        Assert.notNull((Object)this.payloadConverter, (String)"Property 'payloadConverter' is required");
        Assert.notNull((Object)this.headerMapper, (String)"Property 'headerMapper' is required");
    }

    @Override
    public javax.jms.Message toMessage(Object object, Session session) throws JMSException, MessageConversionException {
        if (!(object instanceof Message)) {
            throw new IllegalArgumentException("Could not convert [" + object + "] - only [" + Message.class.getName() + "] is handled by this converter");
        }
        Message input = (Message)object;
        MessageHeaders headers = input.getHeaders();
        Object conversionHint = headers.get((Object)"conversionHint");
        javax.jms.Message reply = this.createMessageForPayload(input.getPayload(), session, conversionHint);
        this.headerMapper.fromHeaders(headers, reply);
        return reply;
    }

    @Override
    public Object fromMessage(javax.jms.Message message) throws JMSException, MessageConversionException {
        MessageHeaders mappedHeaders = this.extractHeaders(message);
        Object convertedObject = this.extractPayload(message);
        MessageBuilder builder = convertedObject instanceof Message ? MessageBuilder.fromMessage((Message)((Message)convertedObject)) : MessageBuilder.withPayload((Object)convertedObject);
        return builder.copyHeadersIfAbsent((Map)mappedHeaders).build();
    }

    protected Object extractPayload(javax.jms.Message message) throws JMSException {
        return this.payloadConverter.fromMessage(message);
    }

    protected javax.jms.Message createMessageForPayload(Object payload, Session session, @Nullable Object conversionHint) throws JMSException {
        return this.payloadConverter.toMessage(payload, session);
    }

    protected final MessageHeaders extractHeaders(javax.jms.Message message) {
        return this.headerMapper.toHeaders(message);
    }
}

