/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.expression.Expression
 *  org.springframework.expression.ExpressionParser
 *  org.springframework.expression.spel.standard.SpelExpressionParser
 */
package org.springframework.integration.aop;

import java.lang.reflect.Method;
import java.util.Map;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

interface PublisherMetadataSource {
    public static final String METHOD_NAME_VARIABLE_NAME = "method";
    public static final String ARGUMENT_MAP_VARIABLE_NAME = "args";
    public static final String RETURN_VALUE_VARIABLE_NAME = "return";
    public static final String EXCEPTION_VARIABLE_NAME = "exception";
    public static final ExpressionParser EXPRESSION_PARSER = new SpelExpressionParser();
    public static final Expression RETURN_VALUE_EXPRESSION = EXPRESSION_PARSER.parseExpression("#return");

    public String getChannelName(Method var1);

    public Expression getExpressionForPayload(Method var1);

    public Map<String, Expression> getExpressionsForHeaders(Method var1);
}

