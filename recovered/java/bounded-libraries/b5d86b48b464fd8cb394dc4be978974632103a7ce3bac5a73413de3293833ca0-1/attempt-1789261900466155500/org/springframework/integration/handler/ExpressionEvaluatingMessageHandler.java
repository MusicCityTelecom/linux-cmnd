/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.expression.Expression
 *  org.springframework.messaging.Message
 *  org.springframework.util.Assert
 */
package org.springframework.integration.handler;

import org.springframework.expression.Expression;
import org.springframework.integration.handler.AbstractMessageHandler;
import org.springframework.integration.handler.ExpressionEvaluatingMessageProcessor;
import org.springframework.messaging.Message;
import org.springframework.util.Assert;

public class ExpressionEvaluatingMessageHandler
extends AbstractMessageHandler {
    private final ExpressionEvaluatingMessageProcessor<Void> processor;
    private String componentType;

    public ExpressionEvaluatingMessageHandler(Expression expression) {
        Assert.notNull((Object)expression, (String)"'expression' must not be null");
        this.processor = new ExpressionEvaluatingMessageProcessor<Void>(expression, Void.class);
        this.setPrimaryExpression(expression);
    }

    public void setComponentType(String componentType) {
        this.componentType = componentType;
    }

    @Override
    public String getComponentType() {
        return this.componentType;
    }

    @Override
    protected void onInit() {
        this.processor.setBeanFactory(this.getBeanFactory());
    }

    @Override
    protected void handleMessageInternal(Message<?> message) {
        this.processor.processMessage(message);
    }
}

