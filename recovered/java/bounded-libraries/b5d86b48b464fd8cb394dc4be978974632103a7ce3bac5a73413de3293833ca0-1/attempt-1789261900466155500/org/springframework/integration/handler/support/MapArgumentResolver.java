/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.MethodParameter
 *  org.springframework.core.convert.TypeDescriptor
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.handler.annotation.Headers
 *  org.springframework.messaging.handler.annotation.Payload
 *  org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver
 */
package org.springframework.integration.handler.support;

import java.util.Map;
import java.util.Properties;
import org.springframework.core.MethodParameter;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.integration.util.AbstractExpressionEvaluator;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;

public class MapArgumentResolver
extends AbstractExpressionEvaluator
implements HandlerMethodArgumentResolver {
    private static final TypeDescriptor PROPERTIES_TYPE = TypeDescriptor.valueOf(Properties.class);

    public boolean supportsParameter(MethodParameter parameter) {
        return !parameter.hasParameterAnnotation(Payload.class) && Map.class.isAssignableFrom(parameter.getParameterType());
    }

    public Object resolveArgument(MethodParameter parameter, Message<?> message) {
        Object payload = message.getPayload();
        if (Properties.class.isAssignableFrom(parameter.getParameterType())) {
            Object map = message.getHeaders();
            if (!parameter.hasParameterAnnotation(Headers.class)) {
                if (payload instanceof Map) {
                    map = (Map)payload;
                } else if (payload instanceof String && ((String)payload).contains("=")) {
                    return this.getEvaluationContext().getTypeConverter().convertValue(payload, TypeDescriptor.valueOf(String.class), PROPERTIES_TYPE);
                }
            }
            Properties properties = new Properties();
            properties.putAll((Map<?, ?>)map);
            return properties;
        }
        if (!parameter.hasParameterAnnotation(Headers.class) && payload instanceof Map) {
            return payload;
        }
        return message.getHeaders();
    }
}

