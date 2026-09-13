/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.integration.splitter;

import java.lang.reflect.Method;
import org.springframework.integration.annotation.Splitter;
import org.springframework.integration.handler.MessageProcessor;
import org.springframework.integration.handler.MethodInvokingMessageProcessor;
import org.springframework.integration.splitter.AbstractMessageProcessingSplitter;

public class MethodInvokingSplitter
extends AbstractMessageProcessingSplitter {
    public MethodInvokingSplitter(Object object, Method method) {
        super(new MethodInvokingMessageProcessor(object, method));
    }

    public MethodInvokingSplitter(Object object, String methodName) {
        super(new MethodInvokingMessageProcessor(object, methodName));
    }

    public MethodInvokingSplitter(Object object) {
        super(object instanceof MessageProcessor ? (MessageProcessor)object : new MethodInvokingMessageProcessor(object, Splitter.class));
    }
}

