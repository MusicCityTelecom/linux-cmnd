/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.MethodParameter
 *  org.springframework.expression.Expression
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.handler.annotation.Payload
 *  org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver
 *  org.springframework.util.StringUtils
 */
package org.springframework.integration.handler.support;

import java.util.HashMap;
import java.util.Map;
import org.springframework.core.MethodParameter;
import org.springframework.expression.Expression;
import org.springframework.integration.util.AbstractExpressionEvaluator;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;
import org.springframework.util.StringUtils;

public class PayloadExpressionArgumentResolver
extends AbstractExpressionEvaluator
implements HandlerMethodArgumentResolver {
    private final Map<MethodParameter, Expression> expressionCache = new HashMap<MethodParameter, Expression>();

    public boolean supportsParameter(MethodParameter parameter) {
        Payload ann = (Payload)parameter.getParameterAnnotation(Payload.class);
        return ann != null && StringUtils.hasText((String)ann.expression());
    }

    public Object resolveArgument(MethodParameter parameter, Message<?> message) {
        Expression expression = this.expressionCache.get(parameter);
        if (expression == null) {
            Payload ann = (Payload)parameter.getParameterAnnotation(Payload.class);
            expression = EXPRESSION_PARSER.parseExpression(ann.expression());
            this.expressionCache.put(parameter, expression);
        }
        return this.evaluateExpression(expression, message.getPayload(), parameter.getParameterType());
    }
}

