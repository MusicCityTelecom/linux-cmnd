/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.expression.Expression
 *  org.springframework.expression.ParseException
 *  org.springframework.lang.Nullable
 *  org.springframework.messaging.Message
 *  org.springframework.util.Assert
 */
package org.springframework.integration.handler;

import org.springframework.expression.Expression;
import org.springframework.expression.ParseException;
import org.springframework.integration.handler.AbstractMessageProcessor;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.util.Assert;

public class ExpressionEvaluatingMessageProcessor<T>
extends AbstractMessageProcessor<T> {
    private final Expression expression;
    private final Class<T> expectedType;

    public ExpressionEvaluatingMessageProcessor(Expression expression) {
        this(expression, null);
    }

    public ExpressionEvaluatingMessageProcessor(Expression expression, @Nullable Class<T> expectedType) {
        Assert.notNull((Object)expression, (String)"The expression must not be null");
        try {
            this.expression = expression;
            this.expectedType = expectedType;
        }
        catch (ParseException e) {
            throw new IllegalArgumentException("Failed to parse expression.", e);
        }
    }

    public ExpressionEvaluatingMessageProcessor(String expression) {
        try {
            this.expression = EXPRESSION_PARSER.parseExpression(expression);
            this.expectedType = null;
        }
        catch (ParseException e) {
            throw new IllegalArgumentException("Failed to parse expression.", e);
        }
    }

    public ExpressionEvaluatingMessageProcessor(String expression, @Nullable Class<T> expectedType) {
        try {
            this.expression = EXPRESSION_PARSER.parseExpression(expression);
            this.expectedType = expectedType;
        }
        catch (ParseException e) {
            throw new IllegalArgumentException("Failed to parse expression.", e);
        }
    }

    @Override
    public T processMessage(Message<?> message) {
        return this.evaluateExpression(this.expression, message, this.expectedType);
    }

    public String toString() {
        return "ExpressionEvaluatingMessageProcessor for: [" + this.expression.getExpressionString() + "]";
    }
}

