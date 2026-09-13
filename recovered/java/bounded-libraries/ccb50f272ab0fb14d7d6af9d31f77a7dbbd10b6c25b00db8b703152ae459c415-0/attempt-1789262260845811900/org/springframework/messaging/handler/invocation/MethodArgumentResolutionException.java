/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.MethodParameter
 *  org.springframework.lang.Nullable
 */
package org.springframework.messaging.handler.invocation;

import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;

public class MethodArgumentResolutionException
extends MessagingException {
    private final MethodParameter parameter;

    public MethodArgumentResolutionException(Message<?> message, MethodParameter parameter) {
        super(message, MethodArgumentResolutionException.getMethodParameterMessage(parameter));
        this.parameter = parameter;
    }

    public MethodArgumentResolutionException(Message<?> message, MethodParameter parameter, String description) {
        super(message, MethodArgumentResolutionException.getMethodParameterMessage(parameter) + ": " + description);
        this.parameter = parameter;
    }

    public MethodArgumentResolutionException(Message<?> message, MethodParameter parameter, String description, @Nullable Throwable cause) {
        super(message, MethodArgumentResolutionException.getMethodParameterMessage(parameter) + ": " + description, cause);
        this.parameter = parameter;
    }

    public final MethodParameter getMethodParameter() {
        return this.parameter;
    }

    private static String getMethodParameterMessage(MethodParameter parameter) {
        return "Could not resolve method parameter at index " + parameter.getParameterIndex() + " in " + parameter.getExecutable().toGenericString();
    }
}

