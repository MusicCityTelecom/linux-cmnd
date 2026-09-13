/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.expression.Expression
 */
package org.springframework.integration.splitter;

import org.springframework.expression.Expression;
import org.springframework.integration.handler.ExpressionEvaluatingMessageProcessor;
import org.springframework.integration.splitter.AbstractMessageProcessingSplitter;

public class ExpressionEvaluatingSplitter
extends AbstractMessageProcessingSplitter {
    public ExpressionEvaluatingSplitter(Expression expression) {
        super(new ExpressionEvaluatingMessageProcessor(expression));
        this.setPrimaryExpression(expression);
    }
}

