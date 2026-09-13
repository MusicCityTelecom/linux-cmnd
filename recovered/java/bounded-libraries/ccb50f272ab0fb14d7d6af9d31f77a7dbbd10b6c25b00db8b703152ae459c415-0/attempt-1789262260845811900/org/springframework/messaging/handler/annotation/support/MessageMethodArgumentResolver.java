/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.MethodParameter
 *  org.springframework.core.ResolvableType
 *  org.springframework.lang.Nullable
 *  org.springframework.util.ClassUtils
 *  org.springframework.util.StringUtils
 */
package org.springframework.messaging.handler.annotation.support;

import java.lang.reflect.Type;
import org.springframework.core.MethodParameter;
import org.springframework.core.ResolvableType;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.converter.MessageConversionException;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.converter.SmartMessageConverter;
import org.springframework.messaging.handler.annotation.support.MethodArgumentTypeMismatchException;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

public class MessageMethodArgumentResolver
implements HandlerMethodArgumentResolver {
    @Nullable
    private final MessageConverter converter;

    public MessageMethodArgumentResolver() {
        this(null);
    }

    public MessageMethodArgumentResolver(@Nullable MessageConverter converter) {
        this.converter = converter;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return Message.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, Message<?> message) throws Exception {
        Class targetMessageType = parameter.getParameterType();
        Class<?> targetPayloadType = this.getPayloadType(parameter, message);
        if (!targetMessageType.isAssignableFrom(message.getClass())) {
            throw new MethodArgumentTypeMismatchException(message, parameter, "Actual message type '" + ClassUtils.getDescriptiveType(message) + "' does not match expected type '" + ClassUtils.getQualifiedName((Class)targetMessageType) + "'");
        }
        Object payload = message.getPayload();
        if (targetPayloadType.isInstance(payload)) {
            return message;
        }
        if (this.isEmptyPayload(payload)) {
            throw new MessageConversionException(message, "Cannot convert from actual payload type '" + ClassUtils.getDescriptiveType(payload) + "' to expected payload type '" + ClassUtils.getQualifiedName(targetPayloadType) + "' when payload is empty");
        }
        payload = this.convertPayload(message, parameter, targetPayloadType);
        return MessageBuilder.createMessage(payload, message.getHeaders());
    }

    protected Class<?> getPayloadType(MethodParameter parameter, Message<?> message) {
        Type genericParamType = parameter.getGenericParameterType();
        ResolvableType resolvableType = ResolvableType.forType((Type)genericParamType).as(Message.class);
        return resolvableType.getGeneric(new int[0]).toClass();
    }

    protected boolean isEmptyPayload(@Nullable Object payload) {
        if (payload == null) {
            return true;
        }
        if (payload instanceof byte[]) {
            return ((byte[])payload).length == 0;
        }
        if (payload instanceof String) {
            return !StringUtils.hasText((String)((String)payload));
        }
        return false;
    }

    private Object convertPayload(Message<?> message, MethodParameter parameter, Class<?> targetPayloadType) {
        Object result = null;
        if (this.converter instanceof SmartMessageConverter) {
            SmartMessageConverter smartConverter = (SmartMessageConverter)this.converter;
            result = smartConverter.fromMessage(message, targetPayloadType, parameter);
        } else if (this.converter != null) {
            result = this.converter.fromMessage(message, targetPayloadType);
        }
        if (result == null) {
            throw new MessageConversionException(message, "No converter found from actual payload type '" + ClassUtils.getDescriptiveType(message.getPayload()) + "' to expected payload type '" + ClassUtils.getQualifiedName(targetPayloadType) + "'");
        }
        return result;
    }
}

