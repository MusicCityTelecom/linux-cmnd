/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.MethodParameter
 *  org.springframework.core.convert.TypeDescriptor
 *  org.springframework.expression.Expression
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver
 *  org.springframework.util.Assert
 *  org.springframework.util.StringUtils
 */
package org.springframework.integration.handler.support;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.core.MethodParameter;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.expression.Expression;
import org.springframework.integration.annotation.Payloads;
import org.springframework.integration.util.AbstractExpressionEvaluator;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public class PayloadsArgumentResolver
extends AbstractExpressionEvaluator
implements HandlerMethodArgumentResolver {
    private final Map<MethodParameter, Expression> expressionCache = new HashMap<MethodParameter, Expression>();

    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Payloads.class);
    }

    public Object resolveArgument(MethodParameter parameter, Message<?> message) {
        Expression expression;
        Object payload = message.getPayload();
        Assert.state((boolean)(payload instanceof Collection), (String)"This Argument Resolver support only messages with payload as Collection<Message<?>>");
        Collection messages = (Collection)payload;
        if (!this.expressionCache.containsKey(parameter)) {
            Payloads payloads = (Payloads)parameter.getParameterAnnotation(Payloads.class);
            String expression2 = payloads.value();
            if (StringUtils.hasText((String)expression2)) {
                this.expressionCache.put(parameter, EXPRESSION_PARSER.parseExpression("![payload." + expression2 + "]"));
            } else {
                this.expressionCache.put(parameter, null);
            }
        }
        if ((expression = this.expressionCache.get(parameter)) != null) {
            return this.evaluateExpression(expression, (Object)messages, parameter.getParameterType());
        }
        try (Stream messageStream = messages.stream();){
            List payloads = messageStream.map(Message::getPayload).collect(Collectors.toList());
            Object object = this.getEvaluationContext().getTypeConverter().convertValue(payloads, TypeDescriptor.forObject(payloads), TypeDescriptor.valueOf((Class)parameter.getParameterType()));
            return object;
        }
    }
}

