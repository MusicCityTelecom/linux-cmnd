/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.expression.Expression
 *  org.springframework.util.Assert
 */
package org.springframework.integration.aggregator;

import org.springframework.expression.Expression;
import org.springframework.integration.aggregator.ReleaseStrategy;
import org.springframework.integration.store.MessageGroup;
import org.springframework.integration.util.AbstractExpressionEvaluator;
import org.springframework.util.Assert;

public class ExpressionEvaluatingReleaseStrategy
extends AbstractExpressionEvaluator
implements ReleaseStrategy {
    private final Expression expression;

    public ExpressionEvaluatingReleaseStrategy(String expression) {
        Assert.hasText((String)expression, (String)"'expression' must not be empty");
        this.expression = EXPRESSION_PARSER.parseExpression(expression);
    }

    public ExpressionEvaluatingReleaseStrategy(Expression expression) {
        Assert.notNull((Object)expression, (String)"'expression' must not be null");
        this.expression = expression;
    }

    @Override
    public boolean canRelease(MessageGroup messages) {
        return Boolean.TRUE.equals(this.evaluateExpression(this.expression, (Object)messages, Boolean.class));
    }
}

