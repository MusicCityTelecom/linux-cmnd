/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.Assert
 */
package org.springframework.integration.filter;

import java.lang.reflect.Method;
import org.springframework.integration.annotation.Filter;
import org.springframework.integration.filter.AbstractMessageProcessingSelector;
import org.springframework.integration.handler.MessageProcessor;
import org.springframework.integration.handler.MethodInvokingMessageProcessor;
import org.springframework.util.Assert;

public class MethodInvokingSelector
extends AbstractMessageProcessingSelector {
    public MethodInvokingSelector(Object object, Method method) {
        super(new MethodInvokingMessageProcessor<Boolean>(object, method));
        Class<?> returnType = method.getReturnType();
        Assert.isTrue((Boolean.TYPE.isAssignableFrom(returnType) || Boolean.class.isAssignableFrom(returnType) ? 1 : 0) != 0, (String)"MethodInvokingSelector method must return a boolean result.");
    }

    public MethodInvokingSelector(Object object, String methodName) {
        super(new MethodInvokingMessageProcessor<Boolean>(object, methodName));
    }

    public MethodInvokingSelector(Object object) {
        super(object instanceof MessageProcessor ? (MessageProcessor)object : new MethodInvokingMessageProcessor(object, Filter.class));
    }
}

