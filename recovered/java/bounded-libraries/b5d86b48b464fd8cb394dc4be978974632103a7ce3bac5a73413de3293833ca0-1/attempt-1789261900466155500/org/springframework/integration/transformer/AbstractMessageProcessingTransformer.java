/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.BeanFactoryAware
 *  org.springframework.context.Lifecycle
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessageHeaders
 *  org.springframework.util.Assert
 *  org.springframework.util.ObjectUtils
 */
package org.springframework.integration.transformer;

import java.util.Arrays;
import java.util.Map;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.Lifecycle;
import org.springframework.integration.handler.MessageProcessor;
import org.springframework.integration.support.AbstractIntegrationMessageBuilder;
import org.springframework.integration.support.DefaultMessageBuilderFactory;
import org.springframework.integration.support.MessageBuilderFactory;
import org.springframework.integration.support.management.ManageableLifecycle;
import org.springframework.integration.support.utils.IntegrationUtils;
import org.springframework.integration.transformer.Transformer;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

public abstract class AbstractMessageProcessingTransformer
implements Transformer,
BeanFactoryAware,
ManageableLifecycle {
    private final MessageProcessor<?> messageProcessor;
    private BeanFactory beanFactory;
    private MessageBuilderFactory messageBuilderFactory = new DefaultMessageBuilderFactory();
    private boolean messageBuilderFactorySet;
    private String[] notPropagatedHeaders;
    private boolean selectiveHeaderPropagation;

    protected AbstractMessageProcessingTransformer(MessageProcessor<?> messageProcessor) {
        Assert.notNull(messageProcessor, (String)"messageProcessor must not be null");
        this.messageProcessor = messageProcessor;
    }

    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
        if (this.messageProcessor instanceof BeanFactoryAware) {
            ((BeanFactoryAware)this.messageProcessor).setBeanFactory(beanFactory);
        }
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
    public void start() {
        if (this.messageProcessor instanceof Lifecycle) {
            ((Lifecycle)this.messageProcessor).start();
        }
    }

    @Override
    public void stop() {
        if (this.messageProcessor instanceof Lifecycle) {
            ((Lifecycle)this.messageProcessor).stop();
        }
    }

    @Override
    public boolean isRunning() {
        return !(this.messageProcessor instanceof Lifecycle) || ((Lifecycle)this.messageProcessor).isRunning();
    }

    public void setNotPropagatedHeaders(String ... headers) {
        if (!ObjectUtils.isEmpty((Object[])headers)) {
            Assert.noNullElements((Object[])headers, (String)"null elements are not allowed in 'headers'");
            this.notPropagatedHeaders = Arrays.copyOf(headers, headers.length);
        }
        this.selectiveHeaderPropagation = !ObjectUtils.isEmpty((Object[])this.notPropagatedHeaders);
    }

    @Override
    public final Message<?> transform(Message<?> message) {
        Object result = this.messageProcessor.processMessage(message);
        if (result == null) {
            return null;
        }
        if (result instanceof Message) {
            return (Message)result;
        }
        AbstractIntegrationMessageBuilder<?> messageBuilder = result instanceof AbstractIntegrationMessageBuilder ? (AbstractIntegrationMessageBuilder<?>)result : this.getMessageBuilderFactory().withPayload(result);
        MessageHeaders requestHeaders = message.getHeaders();
        return messageBuilder.filterAndCopyHeadersIfAbsent((Map<String, ?>)requestHeaders, this.selectiveHeaderPropagation ? this.notPropagatedHeaders : null).build();
    }
}

