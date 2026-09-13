/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.BeanFactoryAware
 *  org.springframework.expression.Expression
 *  org.springframework.expression.ExpressionParser
 *  org.springframework.expression.spel.SpelParserConfiguration
 *  org.springframework.expression.spel.standard.SpelExpressionParser
 *  org.springframework.messaging.Message
 */
package org.springframework.integration.transformer.support;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.integration.handler.ExpressionEvaluatingMessageProcessor;
import org.springframework.integration.transformer.support.AbstractHeaderValueMessageProcessor;
import org.springframework.messaging.Message;

public class ExpressionEvaluatingHeaderValueMessageProcessor<T>
extends AbstractHeaderValueMessageProcessor<T>
implements BeanFactoryAware {
    private static final ExpressionParser EXPRESSION_PARSER = new SpelExpressionParser(new SpelParserConfiguration(true, true));
    private final ExpressionEvaluatingMessageProcessor<T> targetProcessor;

    public ExpressionEvaluatingHeaderValueMessageProcessor(Expression expression, Class<T> expectedType) {
        this.targetProcessor = new ExpressionEvaluatingMessageProcessor<T>(expression, expectedType);
    }

    public ExpressionEvaluatingHeaderValueMessageProcessor(String expressionString, Class<T> expectedType) {
        Expression expression = EXPRESSION_PARSER.parseExpression(expressionString);
        this.targetProcessor = new ExpressionEvaluatingMessageProcessor<T>(expression, expectedType);
    }

    public void setBeanFactory(BeanFactory beanFactory) {
        this.targetProcessor.setBeanFactory(beanFactory);
    }

    @Override
    public T processMessage(Message<?> message) {
        return this.targetProcessor.processMessage(message);
    }
}

