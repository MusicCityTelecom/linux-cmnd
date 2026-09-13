/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.DisposableBean
 *  org.springframework.expression.Expression
 *  org.springframework.expression.ExpressionParser
 *  org.springframework.expression.spel.standard.SpelExpressionParser
 *  org.springframework.messaging.MessageHandler
 *  org.springframework.util.Assert
 *  org.springframework.util.StringUtils
 */
package org.springframework.integration.config;

import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.integration.config.AbstractSimpleMessageHandlerFactoryBean;
import org.springframework.integration.context.IntegrationObjectSupport;
import org.springframework.integration.handler.AbstractMessageProducingHandler;
import org.springframework.integration.handler.AbstractReplyProducingMessageHandler;
import org.springframework.integration.handler.MessageProcessor;
import org.springframework.messaging.MessageHandler;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public abstract class AbstractStandardMessageHandlerFactoryBean
extends AbstractSimpleMessageHandlerFactoryBean<MessageHandler>
implements DisposableBean {
    private static final ExpressionParser EXPRESSION_PARSER = new SpelExpressionParser();
    private static final Set<MessageHandler> REFERENCED_REPLY_PRODUCERS = new HashSet<MessageHandler>();
    private Boolean requiresReply;
    private Object targetObject;
    private String targetMethodName;
    private Expression expression;
    private Long sendTimeout;
    private MessageHandler replyHandler;

    public void setTargetObject(Object targetObject) {
        this.targetObject = targetObject;
    }

    public void setTargetMethodName(String targetMethodName) {
        this.targetMethodName = targetMethodName;
    }

    public void setExpressionString(String expressionString) {
        this.expression = EXPRESSION_PARSER.parseExpression(expressionString);
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    public void setRequiresReply(Boolean requiresReply) {
        this.requiresReply = requiresReply;
    }

    public void setSendTimeout(Long sendTimeout) {
        this.sendTimeout = sendTimeout;
    }

    public Long getSendTimeout() {
        return this.sendTimeout;
    }

    public void destroy() {
        if (this.replyHandler != null) {
            REFERENCED_REPLY_PRODUCERS.remove(this.replyHandler);
        }
    }

    @Override
    protected MessageHandler createHandler() {
        MessageHandler handler;
        if (this.targetObject == null) {
            Assert.isTrue((!StringUtils.hasText((String)this.targetMethodName) ? 1 : 0) != 0, (String)"The target method is only allowed when a target object (ref or inner bean) is also provided.");
        }
        if (this.targetObject != null) {
            boolean targetIsDirectReplyProducingHandler;
            Assert.state((this.expression == null ? 1 : 0) != 0, (String)"The 'targetObject' and 'expression' properties are mutually exclusive.");
            AbstractMessageProducingHandler actualHandler = IntegrationObjectSupport.extractTypeIfPossible(this.targetObject, AbstractMessageProducingHandler.class);
            boolean bl = targetIsDirectReplyProducingHandler = actualHandler != null && this.canBeUsedDirect(actualHandler) && this.methodIsHandleMessageOrEmpty(this.targetMethodName);
            if (this.targetObject instanceof MessageProcessor) {
                handler = this.createMessageProcessingHandler((MessageProcessor)this.targetObject);
            } else if (targetIsDirectReplyProducingHandler) {
                if (this.logger.isDebugEnabled()) {
                    this.logger.debug((Object)("Wiring handler (" + this.targetObject + ") directly into endpoint"));
                }
                this.checkReuse(actualHandler);
                this.postProcessReplyProducer(actualHandler);
                handler = (MessageHandler)this.targetObject;
            } else {
                handler = this.createMethodInvokingHandler(this.targetObject, this.targetMethodName);
            }
        } else {
            handler = this.expression != null ? this.createExpressionEvaluatingHandler(this.expression) : this.createDefaultHandler();
        }
        return handler;
    }

    protected void checkForIllegalTarget(Object targetObject, String targetMethodName) {
        if (targetObject instanceof AbstractReplyProducingMessageHandler && this.methodIsHandleMessageOrEmpty(targetMethodName)) {
            throw new IllegalArgumentException("AbstractReplyProducingMessageHandler.handleMessage() is not allowed for a MethodInvokingHandler");
        }
    }

    private void checkReuse(AbstractMessageProducingHandler replyHandler) {
        Assert.isTrue((!REFERENCED_REPLY_PRODUCERS.contains(replyHandler) ? 1 : 0) != 0, (String)("An AbstractMessageProducingMessageHandler may only be referenced once (" + replyHandler.getBeanName() + ") - use scope=\"prototype\""));
        REFERENCED_REPLY_PRODUCERS.add(replyHandler);
        this.replyHandler = replyHandler;
    }

    protected abstract MessageHandler createMethodInvokingHandler(Object var1, String var2);

    protected MessageHandler createExpressionEvaluatingHandler(Expression expression) {
        throw new UnsupportedOperationException(this.getClass().getName() + " does not support expressions.");
    }

    protected <T> MessageHandler createMessageProcessingHandler(MessageProcessor<T> processor) {
        return this.createMethodInvokingHandler(processor, null);
    }

    protected MessageHandler createDefaultHandler() {
        throw new IllegalArgumentException("Exactly one of the 'targetObject' or 'expression' property is required.");
    }

    protected boolean methodIsHandleMessageOrEmpty(String targetMethodName) {
        return !StringUtils.hasText((String)targetMethodName) || "handleMessage".equals(targetMethodName);
    }

    protected boolean canBeUsedDirect(AbstractMessageProducingHandler handler) {
        return false;
    }

    protected void postProcessReplyProducer(AbstractMessageProducingHandler handler) {
        if (this.sendTimeout != null) {
            handler.setSendTimeout(this.sendTimeout);
        }
        if (this.requiresReply != null) {
            if (handler instanceof AbstractReplyProducingMessageHandler) {
                ((AbstractReplyProducingMessageHandler)handler).setRequiresReply(this.requiresReply);
            } else if (this.requiresReply.booleanValue() && this.logger.isDebugEnabled()) {
                this.logger.debug((Object)("requires-reply can only be set to AbstractReplyProducingMessageHandler or its subclass, " + handler.getBeanName() + " doesn't support it."));
            }
        }
    }
}

