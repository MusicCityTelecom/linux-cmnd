/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.expression.Expression
 *  org.springframework.messaging.MessageChannel
 *  org.springframework.messaging.MessageHandler
 *  org.springframework.util.Assert
 *  org.springframework.util.StringUtils
 */
package org.springframework.integration.config;

import org.springframework.expression.Expression;
import org.springframework.integration.config.AbstractStandardMessageHandlerFactoryBean;
import org.springframework.integration.core.MessageSelector;
import org.springframework.integration.filter.ExpressionEvaluatingSelector;
import org.springframework.integration.filter.MessageFilter;
import org.springframework.integration.filter.MethodInvokingSelector;
import org.springframework.integration.handler.AbstractMessageProducingHandler;
import org.springframework.integration.handler.AbstractReplyProducingMessageHandler;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public class FilterFactoryBean
extends AbstractStandardMessageHandlerFactoryBean {
    private volatile MessageChannel discardChannel;
    private volatile Boolean throwExceptionOnRejection;
    private volatile Boolean discardWithinAdvice;

    public void setDiscardChannel(MessageChannel discardChannel) {
        this.discardChannel = discardChannel;
    }

    public void setThrowExceptionOnRejection(Boolean throwExceptionOnRejection) {
        this.throwExceptionOnRejection = throwExceptionOnRejection;
    }

    public void setDiscardWithinAdvice(boolean discardWithinAdvice) {
        this.discardWithinAdvice = discardWithinAdvice;
    }

    @Override
    protected MessageHandler createMethodInvokingHandler(Object targetObject, String targetMethodName) {
        MessageSelector selector = null;
        if (targetObject instanceof MessageSelector) {
            selector = (MessageSelector)targetObject;
        } else if (StringUtils.hasText((String)targetMethodName)) {
            this.checkForIllegalTarget(targetObject, targetMethodName);
            selector = new MethodInvokingSelector(targetObject, targetMethodName);
        } else {
            selector = new MethodInvokingSelector(targetObject);
        }
        return this.createFilter(selector);
    }

    @Override
    protected void checkForIllegalTarget(Object targetObject, String targetMethodName) {
        if (targetObject instanceof AbstractReplyProducingMessageHandler && this.methodIsHandleMessageOrEmpty(targetMethodName)) {
            throw new IllegalArgumentException("You cannot use 'AbstractReplyProducingMessageHandler.handleMessage()' as a filter - it does not return a result");
        }
    }

    @Override
    protected MessageHandler createExpressionEvaluatingHandler(Expression expression) {
        return this.createFilter(new ExpressionEvaluatingSelector(expression));
    }

    protected MessageFilter createFilter(MessageSelector selector) {
        MessageFilter filter2 = new MessageFilter(selector);
        this.postProcessReplyProducer(filter2);
        return filter2;
    }

    protected void postProcessFilter(MessageFilter filter2) {
        if (this.throwExceptionOnRejection != null) {
            filter2.setThrowExceptionOnRejection(this.throwExceptionOnRejection);
        }
        if (this.discardChannel != null) {
            filter2.setDiscardChannel(this.discardChannel);
        }
        if (this.discardWithinAdvice != null) {
            filter2.setDiscardWithinAdvice(this.discardWithinAdvice);
        }
    }

    @Override
    protected void postProcessReplyProducer(AbstractMessageProducingHandler handler) {
        super.postProcessReplyProducer(handler);
        if (!(handler instanceof MessageFilter)) {
            Assert.isNull((Object)this.throwExceptionOnRejection, (String)"Cannot set throwExceptionOnRejection if the referenced bean is an AbstractReplyProducingMessageHandler, but not a MessageFilter");
            Assert.isNull((Object)this.discardChannel, (String)"Cannot set discardChannel if the referenced bean is an AbstractReplyProducingMessageHandler, but not a MessageFilter");
            Assert.isNull((Object)this.discardWithinAdvice, (String)"Cannot set discardWithinAdvice if the referenced bean is an AbstractReplyProducingMessageHandler, but not a MessageFilter");
        } else {
            this.postProcessFilter((MessageFilter)handler);
        }
    }

    @Override
    protected boolean canBeUsedDirect(AbstractMessageProducingHandler handler) {
        return handler instanceof MessageFilter || !(handler instanceof MessageSelector) && this.noFilterAttributesProvided();
    }

    private boolean noFilterAttributesProvided() {
        return this.discardChannel == null && this.throwExceptionOnRejection == null && this.discardWithinAdvice == null;
    }

    @Override
    protected Class<? extends MessageHandler> getPreCreationHandlerType() {
        return MessageFilter.class;
    }
}

