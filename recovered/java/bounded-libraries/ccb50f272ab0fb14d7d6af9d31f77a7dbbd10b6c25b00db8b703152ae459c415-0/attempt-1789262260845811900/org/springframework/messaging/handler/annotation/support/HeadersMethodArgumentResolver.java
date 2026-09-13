/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.MethodParameter
 *  org.springframework.lang.Nullable
 *  org.springframework.util.ReflectionUtils
 */
package org.springframework.messaging.handler.annotation.support;

import java.lang.reflect.Method;
import java.util.Map;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.util.ReflectionUtils;

public class HeadersMethodArgumentResolver
implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        Class paramType = parameter.getParameterType();
        return parameter.hasParameterAnnotation(Headers.class) && Map.class.isAssignableFrom(paramType) || MessageHeaders.class == paramType || MessageHeaderAccessor.class.isAssignableFrom(paramType);
    }

    @Override
    @Nullable
    public Object resolveArgument(MethodParameter parameter, Message<?> message) throws Exception {
        Class paramType = parameter.getParameterType();
        if (Map.class.isAssignableFrom(paramType)) {
            return message.getHeaders();
        }
        if (MessageHeaderAccessor.class == paramType) {
            MessageHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, MessageHeaderAccessor.class);
            return accessor != null ? accessor : new MessageHeaderAccessor(message);
        }
        if (MessageHeaderAccessor.class.isAssignableFrom(paramType)) {
            MessageHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, MessageHeaderAccessor.class);
            if (accessor != null && paramType.isAssignableFrom(accessor.getClass())) {
                return accessor;
            }
            Method method = ReflectionUtils.findMethod((Class)paramType, (String)"wrap", (Class[])new Class[]{Message.class});
            if (method == null) {
                throw new IllegalStateException("Cannot create accessor of type " + paramType + " for message " + message);
            }
            return ReflectionUtils.invokeMethod((Method)method, null, (Object[])new Object[]{message});
        }
        throw new IllegalStateException("Unexpected parameter of type " + paramType + " in method " + parameter.getMethod() + ". ");
    }
}

