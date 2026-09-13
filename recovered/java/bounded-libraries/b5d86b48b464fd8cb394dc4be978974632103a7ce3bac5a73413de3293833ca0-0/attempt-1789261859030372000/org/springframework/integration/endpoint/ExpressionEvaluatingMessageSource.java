/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.expression.Expression
 *  org.springframework.util.Assert
 */
package org.springframework.integration.endpoint;

import org.springframework.expression.Expression;
import org.springframework.integration.context.ExpressionCapable;
import org.springframework.integration.endpoint.AbstractMessageSource;
import org.springframework.util.Assert;

public class ExpressionEvaluatingMessageSource<T>
extends AbstractMessageSource<T>
implements ExpressionCapable {
    private final Expression expression;
    private final Class<T> expectedType;

    public ExpressionEvaluatingMessageSource(Expression expression, Class<T> expectedType) {
        Assert.notNull((Object)expression, (String)"expression must not be null");
        this.expression = expression;
        this.expectedType = expectedType;
    }

    @Override
    public String getComponentType() {
        return "inbound-channel-adapter";
    }

    @Override
    public T doReceive() {
        return this.evaluateExpression(this.expression, this.expectedType);
    }

    @Override
    public Expression getExpression() {
        return this.expression;
    }
}

