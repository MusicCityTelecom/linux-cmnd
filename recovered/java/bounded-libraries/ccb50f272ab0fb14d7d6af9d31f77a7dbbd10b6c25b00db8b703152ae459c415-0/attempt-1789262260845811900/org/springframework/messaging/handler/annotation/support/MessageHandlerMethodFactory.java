/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.messaging.handler.annotation.support;

import java.lang.reflect.Method;
import org.springframework.messaging.handler.invocation.InvocableHandlerMethod;

public interface MessageHandlerMethodFactory {
    public InvocableHandlerMethod createInvocableHandlerMethod(Object var1, Method var2);
}

