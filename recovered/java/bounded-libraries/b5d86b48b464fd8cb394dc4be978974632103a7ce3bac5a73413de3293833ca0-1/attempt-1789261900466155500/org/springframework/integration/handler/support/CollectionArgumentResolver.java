/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.MethodParameter
 *  org.springframework.core.convert.TypeDescriptor
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver
 *  org.springframework.util.Assert
 */
package org.springframework.integration.handler.support;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.core.MethodParameter;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.integration.util.AbstractExpressionEvaluator;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;
import org.springframework.util.Assert;

public class CollectionArgumentResolver
extends AbstractExpressionEvaluator
implements HandlerMethodArgumentResolver {
    private final boolean canProcessMessageList;

    public CollectionArgumentResolver(boolean canProcessMessageList) {
        this.canProcessMessageList = canProcessMessageList;
    }

    public boolean supportsParameter(MethodParameter parameter) {
        Class parameterType = parameter.getParameterType();
        return Collection.class.isAssignableFrom(parameterType) || Iterator.class.isAssignableFrom(parameterType) || parameterType.isArray();
    }

    public Object resolveArgument(MethodParameter parameter, Message<?> message) {
        List value = message.getPayload();
        if (this.canProcessMessageList) {
            Assert.state((boolean)(value instanceof Collection), (String)("This Argument Resolver only supports messages with a payload of Collection<Message<?>>, payload is: " + value.getClass()));
            Collection messages = value;
            if (!Message.class.isAssignableFrom(parameter.nested().getNestedParameterType())) {
                try (Stream messageStream = messages.stream();){
                    value = messageStream.map(Message::getPayload).collect(Collectors.toList());
                }
            }
        }
        if (Iterator.class.isAssignableFrom(parameter.getParameterType())) {
            if (value instanceof Iterable) {
                return ((Iterable)value).iterator();
            }
            return Collections.singleton(value).iterator();
        }
        return this.getEvaluationContext().getTypeConverter().convertValue((Object)value, TypeDescriptor.forObject((Object)value), TypeDescriptor.valueOf((Class)parameter.getParameterType()));
    }
}

