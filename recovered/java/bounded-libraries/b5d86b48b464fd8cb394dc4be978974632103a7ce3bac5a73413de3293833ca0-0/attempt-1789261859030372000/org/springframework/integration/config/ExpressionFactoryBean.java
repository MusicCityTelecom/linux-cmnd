/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.config.AbstractFactoryBean
 *  org.springframework.expression.Expression
 *  org.springframework.expression.ExpressionParser
 *  org.springframework.expression.spel.SpelParserConfiguration
 *  org.springframework.expression.spel.standard.SpelExpressionParser
 *  org.springframework.util.Assert
 */
package org.springframework.integration.config;

import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.util.Assert;

public class ExpressionFactoryBean
extends AbstractFactoryBean<Expression> {
    private static final ExpressionParser DEFAULT_PARSER = new SpelExpressionParser();
    private final String expressionString;
    private ExpressionParser parser = DEFAULT_PARSER;

    public ExpressionFactoryBean(String expressionString) {
        Assert.hasText((String)expressionString, (String)"'expressionString' must not be empty or null");
        this.expressionString = expressionString;
    }

    public void setParserConfiguration(SpelParserConfiguration parserConfiguration) {
        Assert.notNull((Object)parserConfiguration, (String)"'parserConfiguration' must not be null");
        this.parser = new SpelExpressionParser(parserConfiguration);
    }

    public Class<?> getObjectType() {
        return Expression.class;
    }

    protected Expression createInstance() {
        return this.parser.parseExpression(this.expressionString);
    }
}

