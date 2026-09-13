/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.expression.Expression
 *  org.springframework.expression.ParseException
 *  org.springframework.messaging.Message
 *  org.springframework.util.Assert
 */
package org.springframework.integration.aggregator;

import java.util.Collection;
import org.springframework.expression.Expression;
import org.springframework.expression.ParseException;
import org.springframework.integration.aggregator.MessageListProcessor;
import org.springframework.integration.util.AbstractExpressionEvaluator;
import org.springframework.messaging.Message;
import org.springframework.util.Assert;

public class ExpressionEvaluatingMessageListProcessor
extends AbstractExpressionEvaluator
implements MessageListProcessor {
    private final Expression expression;
    private volatile Class<?> expectedType = null;

    public ExpressionEvaluatingMessageListProcessor(String expression, Class<?> expectedType) {
        this(expression);
        this.expectedType = expectedType;
    }

    public ExpressionEvaluatingMessageListProcessor(String expression) {
        try {
            this.expression = EXPRESSION_PARSER.parseExpression(expression);
        }
        catch (ParseException e) {
            throw new IllegalArgumentException("Failed to parse expression.", e);
        }
    }

    public ExpressionEvaluatingMessageListProcessor(Expression expression, Class<?> expectedType) {
        this(expression);
        this.expectedType = expectedType;
    }

    public ExpressionEvaluatingMessageListProcessor(Expression expression) {
        Assert.notNull((Object)expression, (String)"'expression' must not be null.");
        this.expression = expression;
    }

    public void setExpectedType(Class<?> expectedType) {
        this.expectedType = expectedType;
    }

    @Override
    public Object process(Collection<? extends Message<?>> messages) {
        return this.evaluateExpression(this.expression, messages, this.expectedType);
    }
}

