/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.BeansException
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.BeanFactoryAware
 *  org.springframework.messaging.Message
 */
package org.springframework.integration.transformer.support;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.integration.handler.MessageProcessor;
import org.springframework.integration.handler.MethodInvokingMessageProcessor;
import org.springframework.integration.transformer.support.AbstractHeaderValueMessageProcessor;
import org.springframework.messaging.Message;

public class MessageProcessingHeaderValueMessageProcessor
extends AbstractHeaderValueMessageProcessor<Object>
implements BeanFactoryAware {
    private final MessageProcessor<?> targetProcessor;

    public <T> MessageProcessingHeaderValueMessageProcessor(MessageProcessor<T> targetProcessor) {
        this.targetProcessor = targetProcessor;
    }

    public MessageProcessingHeaderValueMessageProcessor(Object targetObject) {
        this(targetObject, null);
    }

    public MessageProcessingHeaderValueMessageProcessor(Object targetObject, String method) {
        this.targetProcessor = new MethodInvokingMessageProcessor(targetObject, method);
    }

    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        if (this.targetProcessor instanceof BeanFactoryAware) {
            ((BeanFactoryAware)this.targetProcessor).setBeanFactory(beanFactory);
        }
    }

    @Override
    public Object processMessage(Message<?> message) {
        return this.targetProcessor.processMessage(message);
    }
}

