/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.expression.Expression
 *  org.springframework.expression.ExpressionParser
 *  org.springframework.expression.spel.SpelParserConfiguration
 *  org.springframework.expression.spel.standard.SpelExpressionParser
 */
package org.springframework.integration.filter;

import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.integration.filter.AbstractMessageProcessingSelector;
import org.springframework.integration.handler.ExpressionEvaluatingMessageProcessor;

public class ExpressionEvaluatingSelector
extends AbstractMessageProcessingSelector {
    private static final ExpressionParser EXPRESSION_PARSER = new SpelExpressionParser(new SpelParserConfiguration(true, true));
    private final String expressionString;

    public ExpressionEvaluatingSelector(String expressionString) {
        super(new ExpressionEvaluatingMessageProcessor<Boolean>(EXPRESSION_PARSER.parseExpression(expressionString), Boolean.class));
        this.expressionString = expressionString;
    }

    public ExpressionEvaluatingSelector(Expression expression) {
        super(new ExpressionEvaluatingMessageProcessor<Boolean>(expression, Boolean.class));
        this.expressionString = expression.getExpressionString();
    }

    public String getExpressionString() {
        return this.expressionString;
    }

    public String toString() {
        return "ExpressionEvaluatingSelector for: [" + this.expressionString + "]";
    }
}

