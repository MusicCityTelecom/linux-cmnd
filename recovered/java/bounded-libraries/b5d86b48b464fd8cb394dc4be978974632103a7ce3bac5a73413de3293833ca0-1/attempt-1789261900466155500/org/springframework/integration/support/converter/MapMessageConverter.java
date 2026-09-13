/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.BeanFactoryAware
 *  org.springframework.lang.Nullable
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessageHeaders
 *  org.springframework.messaging.converter.MessageConverter
 *  org.springframework.util.Assert
 */
package org.springframework.integration.support.converter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.integration.support.AbstractIntegrationMessageBuilder;
import org.springframework.integration.support.DefaultMessageBuilderFactory;
import org.springframework.integration.support.MessageBuilderFactory;
import org.springframework.integration.support.utils.IntegrationUtils;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.util.Assert;

public class MapMessageConverter
implements MessageConverter,
BeanFactoryAware {
    private volatile String[] headerNames;
    private volatile boolean filterHeadersInToMessage;
    private volatile BeanFactory beanFactory;
    private volatile MessageBuilderFactory messageBuilderFactory = new DefaultMessageBuilderFactory();
    private volatile boolean messageBuilderFactorySet;

    public final void setBeanFactory(BeanFactory beanFactory) {
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

    public void setHeaderNames(String ... headerNames) {
        Assert.notEmpty((Object[])headerNames, (String)"at least one header name is required");
        this.headerNames = Arrays.copyOf(headerNames, headerNames.length);
    }

    public void setFilterHeadersInToMessage(boolean filterHeadersInToMessage) {
        this.filterHeadersInToMessage = filterHeadersInToMessage;
    }

    @Nullable
    public Message<?> toMessage(Object object, @Nullable MessageHeaders messageHeaders) {
        Assert.isInstanceOf(Map.class, (Object)object, (String)"This converter expects a Map");
        Map map = (Map)object;
        Object payload = map.get("payload");
        Assert.notNull(payload, (String)"'payload' entry cannot be null");
        AbstractIntegrationMessageBuilder messageBuilder = this.getMessageBuilderFactory().withPayload(payload);
        Map headers = (Map)map.get("headers");
        if (headers != null) {
            if (this.filterHeadersInToMessage) {
                headers.keySet().retainAll(Arrays.asList(this.headerNames));
            }
            messageBuilder.copyHeaders(headers);
        }
        return messageBuilder.copyHeadersIfAbsent((Map<String, ?>)messageHeaders).build();
    }

    @Nullable
    public Object fromMessage(Message<?> message, Class<?> clazz) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("payload", message.getPayload());
        HashMap<String, Object> headers = new HashMap<String, Object>();
        for (String headerName : this.headerNames) {
            Object header = message.getHeaders().get((Object)headerName);
            if (header == null) continue;
            headers.put(headerName, header);
        }
        map.put("headers", headers);
        return map;
    }
}

