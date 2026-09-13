/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.BeansException
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.BeanFactoryAware
 *  org.springframework.lang.Nullable
 *  org.springframework.messaging.Message
 */
package org.springframework.integration.support.json;

import java.lang.reflect.Type;
import java.util.Map;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.integration.support.DefaultMessageBuilderFactory;
import org.springframework.integration.support.MessageBuilderFactory;
import org.springframework.integration.support.json.JsonInboundMessageMapper;
import org.springframework.integration.support.json.JsonObjectMapper;
import org.springframework.integration.support.utils.IntegrationUtils;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;

abstract class AbstractJacksonJsonMessageParser<P>
implements JsonInboundMessageMapper.JsonMessageParser<P>,
BeanFactoryAware {
    private final JsonObjectMapper<?, P> objectMapper;
    private volatile JsonInboundMessageMapper messageMapper;
    private volatile MessageBuilderFactory messageBuilderFactory = new DefaultMessageBuilderFactory();
    private BeanFactory beanFactory;
    private volatile boolean messageBuilderFactorySet;

    protected AbstractJacksonJsonMessageParser(JsonObjectMapper<?, P> objectMapper) {
        this.objectMapper = objectMapper;
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

    @Override
    public Message<?> doInParser(JsonInboundMessageMapper messageMapperToUse, String jsonMessage, @Nullable Map<String, Object> headers) {
        if (this.messageMapper == null) {
            this.messageMapper = messageMapperToUse;
        }
        P parser = this.createJsonParser(jsonMessage);
        if (messageMapperToUse.isMapToPayload()) {
            Object payload = this.readPayload(parser, jsonMessage);
            return this.getMessageBuilderFactory().withPayload(payload).copyHeaders(headers).build();
        }
        return this.parseWithHeaders(parser, jsonMessage, headers);
    }

    protected Object readPayload(P parser, String jsonMessage) {
        try {
            return this.objectMapper.fromJson(parser, this.messageMapper.getPayloadType());
        }
        catch (Exception e) {
            throw new IllegalArgumentException("Mapping of JSON message '" + jsonMessage + "' to payload type '" + this.messageMapper.getPayloadType() + "' failed.", e);
        }
    }

    protected Object readHeader(P parser, String headerName, String jsonMessage) {
        Class<Object> headerType = this.messageMapper.getHeaderTypes().getOrDefault(headerName, Object.class);
        try {
            return this.objectMapper.fromJson(parser, (Type)((Object)headerType));
        }
        catch (Exception e) {
            throw new IllegalArgumentException("Mapping header '" + headerName + "' of JSON message '" + jsonMessage + "' to header type '" + headerType + "' failed.", e);
        }
    }

    protected abstract Message<?> parseWithHeaders(P var1, String var2, @Nullable Map<String, Object> var3);

    protected abstract P createJsonParser(String var1);
}

