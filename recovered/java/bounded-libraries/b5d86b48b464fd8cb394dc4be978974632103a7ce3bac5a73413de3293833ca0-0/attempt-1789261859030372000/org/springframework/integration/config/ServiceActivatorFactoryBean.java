/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.expression.Expression
 *  org.springframework.messaging.MessageHandler
 *  org.springframework.messaging.ReactiveMessageHandler
 *  org.springframework.util.StringUtils
 */
package org.springframework.integration.config;

import java.util.Arrays;
import org.springframework.expression.Expression;
import org.springframework.integration.config.AbstractStandardMessageHandlerFactoryBean;
import org.springframework.integration.handler.AbstractMessageProducingHandler;
import org.springframework.integration.handler.ExpressionEvaluatingMessageProcessor;
import org.springframework.integration.handler.MessageProcessor;
import org.springframework.integration.handler.ReactiveMessageHandlerAdapter;
import org.springframework.integration.handler.ReplyProducingMessageHandlerWrapper;
import org.springframework.integration.handler.ServiceActivatingHandler;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.ReactiveMessageHandler;
import org.springframework.util.StringUtils;

public class ServiceActivatorFactoryBean
extends AbstractStandardMessageHandlerFactoryBean {
    private String[] headers;

    public void setNotPropagatedHeaders(String ... headers) {
        this.headers = Arrays.copyOf(headers, headers.length);
    }

    @Override
    protected MessageHandler createMethodInvokingHandler(Object targetObject, String targetMethodName) {
        MessageHandler handler = this.createDirectHandlerIfPossible(targetObject, targetMethodName);
        if (handler == null) {
            handler = this.configureHandler(StringUtils.hasText((String)targetMethodName) ? new ServiceActivatingHandler(targetObject, targetMethodName) : new ServiceActivatingHandler(targetObject));
        }
        return handler;
    }

    protected MessageHandler createDirectHandlerIfPossible(Object targetObject, String targetMethodName) {
        Object handler = null;
        if ((targetObject instanceof MessageHandler || targetObject instanceof ReactiveMessageHandler) && this.methodIsHandleMessageOrEmpty(targetMethodName)) {
            if (targetObject instanceof AbstractMessageProducingHandler) {
                return (MessageHandler)targetObject;
            }
            handler = targetObject instanceof ReactiveMessageHandler ? new ReactiveMessageHandlerAdapter((ReactiveMessageHandler)targetObject) : new ReplyProducingMessageHandlerWrapper((MessageHandler)targetObject);
        }
        return handler;
    }

    @Override
    protected MessageHandler createExpressionEvaluatingHandler(Expression expression) {
        ExpressionEvaluatingMessageProcessor processor = new ExpressionEvaluatingMessageProcessor(expression);
        processor.setBeanFactory(this.getBeanFactory());
        ServiceActivatingHandler handler = new ServiceActivatingHandler(processor);
        handler.setPrimaryExpression(expression);
        return this.configureHandler(handler);
    }

    @Override
    protected <T> MessageHandler createMessageProcessingHandler(MessageProcessor<T> processor) {
        return this.configureHandler(new ServiceActivatingHandler(processor));
    }

    protected MessageHandler configureHandler(ServiceActivatingHandler handler) {
        this.postProcessReplyProducer(handler);
        return handler;
    }

    @Override
    protected boolean canBeUsedDirect(AbstractMessageProducingHandler handler) {
        return true;
    }

    @Override
    protected void postProcessReplyProducer(AbstractMessageProducingHandler handler) {
        super.postProcessReplyProducer(handler);
        if (this.headers != null) {
            handler.setNotPropagatedHeaders(this.headers);
        }
    }
}

