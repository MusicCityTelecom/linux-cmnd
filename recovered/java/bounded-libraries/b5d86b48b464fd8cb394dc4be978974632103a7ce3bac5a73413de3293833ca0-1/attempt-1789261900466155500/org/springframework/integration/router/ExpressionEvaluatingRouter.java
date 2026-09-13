/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.expression.Expression
 */
package org.springframework.integration.router;

import org.springframework.expression.Expression;
import org.springframework.integration.handler.ExpressionEvaluatingMessageProcessor;
import org.springframework.integration.router.AbstractMessageProcessingRouter;

public class ExpressionEvaluatingRouter
extends AbstractMessageProcessingRouter {
    public ExpressionEvaluatingRouter(String expressionString) {
        this(EXPRESSION_PARSER.parseExpression(expressionString));
    }

    public ExpressionEvaluatingRouter(Expression expression) {
        super(new ExpressionEvaluatingMessageProcessor(expression));
        this.setPrimaryExpression(expression);
    }
}

