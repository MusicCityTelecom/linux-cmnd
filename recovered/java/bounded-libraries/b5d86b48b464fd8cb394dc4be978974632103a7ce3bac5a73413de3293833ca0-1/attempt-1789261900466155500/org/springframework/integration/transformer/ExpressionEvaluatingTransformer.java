/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.expression.Expression
 */
package org.springframework.integration.transformer;

import org.springframework.expression.Expression;
import org.springframework.integration.handler.ExpressionEvaluatingMessageProcessor;
import org.springframework.integration.transformer.AbstractMessageProcessingTransformer;

public class ExpressionEvaluatingTransformer
extends AbstractMessageProcessingTransformer {
    public ExpressionEvaluatingTransformer(Expression expression) {
        super(new ExpressionEvaluatingMessageProcessor(expression));
    }
}

