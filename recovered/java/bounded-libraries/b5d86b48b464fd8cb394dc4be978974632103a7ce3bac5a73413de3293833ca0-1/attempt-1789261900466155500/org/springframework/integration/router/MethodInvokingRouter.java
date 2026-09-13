/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.integration.router;

import java.lang.reflect.Method;
import org.springframework.integration.annotation.Router;
import org.springframework.integration.handler.MessageProcessor;
import org.springframework.integration.handler.MethodInvokingMessageProcessor;
import org.springframework.integration.router.AbstractMessageProcessingRouter;

public class MethodInvokingRouter
extends AbstractMessageProcessingRouter {
    public MethodInvokingRouter(Object object, Method method) {
        super(new MethodInvokingMessageProcessor(object, method));
    }

    public MethodInvokingRouter(Object object, String methodName) {
        super(new MethodInvokingMessageProcessor(object, methodName));
    }

    public MethodInvokingRouter(Object object) {
        super(object instanceof MessageProcessor ? (MessageProcessor)object : new MethodInvokingMessageProcessor(object, Router.class));
    }
}

