/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.Assert
 */
package org.springframework.integration.transformer;

import java.lang.reflect.Method;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.handler.MessageProcessor;
import org.springframework.integration.handler.MethodInvokingMessageProcessor;
import org.springframework.integration.transformer.AbstractMessageProcessingTransformer;
import org.springframework.util.Assert;

public class MethodInvokingTransformer
extends AbstractMessageProcessingTransformer {
    public MethodInvokingTransformer(Object object, Method method) {
        super(new MethodInvokingMessageProcessor(object, method));
        Assert.state((!Void.class.isAssignableFrom(method.getReturnType()) ? 1 : 0) != 0, (String)"'transformer' method must not be 'void'.");
    }

    public MethodInvokingTransformer(Object object, String methodName) {
        super(new MethodInvokingMessageProcessor(object, methodName));
    }

    public MethodInvokingTransformer(Object object) {
        super(object instanceof MessageProcessor ? (MessageProcessor)object : new MethodInvokingMessageProcessor(object, Transformer.class));
    }
}

