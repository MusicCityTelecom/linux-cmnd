/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.BeanFactoryAware
 *  org.springframework.beans.factory.BeanInitializationException
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessageHeaders
 */
package org.springframework.integration.transformer;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.integration.IntegrationPattern;
import org.springframework.integration.IntegrationPatternType;
import org.springframework.integration.context.IntegrationObjectSupport;
import org.springframework.integration.handler.MessageProcessor;
import org.springframework.integration.support.AbstractIntegrationMessageBuilder;
import org.springframework.integration.support.DefaultMessageBuilderFactory;
import org.springframework.integration.transformer.Transformer;
import org.springframework.integration.transformer.support.HeaderValueMessageProcessor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

public class HeaderEnricher
extends IntegrationObjectSupport
implements Transformer,
IntegrationPattern {
    private final Map<String, ? extends HeaderValueMessageProcessor<?>> headersToAdd;
    private volatile MessageProcessor<?> messageProcessor;
    private volatile boolean defaultOverwrite = false;
    private volatile boolean shouldSkipNulls = true;

    public HeaderEnricher() {
        this(null);
    }

    public HeaderEnricher(Map<String, ? extends HeaderValueMessageProcessor<?>> headersToAdd) {
        this.headersToAdd = headersToAdd != null ? headersToAdd : new HashMap();
    }

    public <T> void setMessageProcessor(MessageProcessor<T> messageProcessor) {
        this.messageProcessor = messageProcessor;
    }

    public void setDefaultOverwrite(boolean defaultOverwrite) {
        this.defaultOverwrite = defaultOverwrite;
    }

    public void setShouldSkipNulls(boolean shouldSkipNulls) {
        this.shouldSkipNulls = shouldSkipNulls;
    }

    @Override
    public String getComponentType() {
        return "header-enricher";
    }

    @Override
    public IntegrationPatternType getIntegrationPatternType() {
        return IntegrationPatternType.header_enricher;
    }

    @Override
    public void onInit() {
        boolean shouldOverwrite = this.defaultOverwrite;
        boolean checkReadOnlyHeaders = this.getMessageBuilderFactory() instanceof DefaultMessageBuilderFactory;
        shouldOverwrite = this.initializeHeadersToAdd(shouldOverwrite, checkReadOnlyHeaders);
        BeanFactory beanFactory = this.getBeanFactory();
        if (this.messageProcessor != null && this.messageProcessor instanceof BeanFactoryAware && beanFactory != null) {
            ((BeanFactoryAware)this.messageProcessor).setBeanFactory(beanFactory);
        }
        if (!shouldOverwrite && !this.shouldSkipNulls) {
            this.logger.warn(() -> this.getComponentName() + " is configured to not overwrite existing headers. 'shouldSkipNulls = false' will have no effect");
        }
    }

    private boolean initializeHeadersToAdd(boolean shouldOverwrite, boolean checkReadOnlyHeaders) {
        boolean overwrite = shouldOverwrite;
        BeanFactory beanFactory = this.getBeanFactory();
        for (Map.Entry<String, HeaderValueMessageProcessor<?>> entry : this.headersToAdd.entrySet()) {
            Boolean processorOverwrite;
            if (checkReadOnlyHeaders && ("id".equals(entry.getKey()) || "timestamp".equals(entry.getKey()))) {
                throw new BeanInitializationException("HeaderEnricher cannot override 'id' and 'timestamp' read-only headers.\nWrong 'headersToAdd' [" + this.headersToAdd + "] configuration for " + this.getComponentName());
            }
            HeaderValueMessageProcessor<?> processor = entry.getValue();
            if (processor instanceof BeanFactoryAware && beanFactory != null) {
                ((BeanFactoryAware)processor).setBeanFactory(beanFactory);
            }
            if ((processorOverwrite = processor.isOverwrite()) == null) continue;
            overwrite |= processorOverwrite.booleanValue();
        }
        return overwrite;
    }

    @Override
    public Message<?> transform(Message<?> message) {
        MessageHeaders messageHeaders = message.getHeaders();
        AbstractIntegrationMessageBuilder<?> messageBuilder = this.getMessageBuilderFactory().fromMessage(message);
        this.addHeadersFromMessageProcessor(message, messageBuilder);
        for (Map.Entry<String, HeaderValueMessageProcessor<?>> entry : this.headersToAdd.entrySet()) {
            Object value;
            boolean headerDoesNotExist;
            String key = entry.getKey();
            HeaderValueMessageProcessor<?> valueProcessor = entry.getValue();
            Boolean shouldOverwrite = valueProcessor.isOverwrite();
            if (shouldOverwrite == null) {
                shouldOverwrite = this.defaultOverwrite;
            }
            boolean bl = headerDoesNotExist = messageHeaders.get((Object)key) == null;
            if (!headerDoesNotExist && !shouldOverwrite.booleanValue() || (value = valueProcessor.processMessage(message)) == null && this.shouldSkipNulls) continue;
            messageBuilder.setHeader(key, value);
        }
        return messageBuilder.build();
    }

    private void addHeadersFromMessageProcessor(Message<?> message, AbstractIntegrationMessageBuilder<?> messageBuilder) {
        if (this.messageProcessor != null) {
            Object result = this.messageProcessor.processMessage(message);
            if (result instanceof Map) {
                MessageHeaders messageHeaders = message.getHeaders();
                Map resultMap = (Map)result;
                for (Map.Entry entry : resultMap.entrySet()) {
                    Object key = entry.getKey();
                    if (key instanceof String) {
                        if (!this.defaultOverwrite && messageHeaders.get(key) != null) continue;
                        messageBuilder.setHeader((String)key, entry.getValue());
                        continue;
                    }
                    this.logger.debug(() -> "ignoring value for non-String key: " + key);
                }
            } else {
                this.logger.debug(() -> "expected a Map result from processor, but received: " + result);
            }
        }
    }
}

