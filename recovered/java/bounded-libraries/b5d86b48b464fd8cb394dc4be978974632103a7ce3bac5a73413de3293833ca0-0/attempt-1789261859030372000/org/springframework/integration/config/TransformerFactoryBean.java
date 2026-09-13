/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.expression.Expression
 *  org.springframework.messaging.MessageHandler
 *  org.springframework.util.Assert
 *  org.springframework.util.StringUtils
 */
package org.springframework.integration.config;

import org.springframework.expression.Expression;
import org.springframework.integration.config.AbstractStandardMessageHandlerFactoryBean;
import org.springframework.integration.handler.AbstractMessageProducingHandler;
import org.springframework.integration.transformer.ExpressionEvaluatingTransformer;
import org.springframework.integration.transformer.MessageTransformingHandler;
import org.springframework.integration.transformer.MethodInvokingTransformer;
import org.springframework.integration.transformer.Transformer;
import org.springframework.messaging.MessageHandler;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public class TransformerFactoryBean
extends AbstractStandardMessageHandlerFactoryBean {
    public TransformerFactoryBean() {
        this.setRequiresReply(true);
    }

    @Override
    protected MessageHandler createMethodInvokingHandler(Object targetObject, String targetMethodName) {
        Assert.notNull((Object)targetObject, (String)"targetObject must not be null");
        Transformer transformer = null;
        if (targetObject instanceof Transformer) {
            transformer = (Transformer)targetObject;
        } else {
            this.checkForIllegalTarget(targetObject, targetMethodName);
            transformer = StringUtils.hasText((String)targetMethodName) ? new MethodInvokingTransformer(targetObject, targetMethodName) : new MethodInvokingTransformer(targetObject);
        }
        return this.createHandler(transformer);
    }

    @Override
    protected MessageHandler createExpressionEvaluatingHandler(Expression expression) {
        ExpressionEvaluatingTransformer transformer = new ExpressionEvaluatingTransformer(expression);
        MessageTransformingHandler handler = this.createHandler(transformer);
        handler.setPrimaryExpression(expression);
        return handler;
    }

    protected MessageTransformingHandler createHandler(Transformer transformer) {
        MessageTransformingHandler handler = new MessageTransformingHandler(transformer);
        this.postProcessReplyProducer(handler);
        return handler;
    }

    @Override
    protected boolean canBeUsedDirect(AbstractMessageProducingHandler handler) {
        return true;
    }

    @Override
    protected Class<? extends MessageHandler> getPreCreationHandlerType() {
        return MessageTransformingHandler.class;
    }
}

