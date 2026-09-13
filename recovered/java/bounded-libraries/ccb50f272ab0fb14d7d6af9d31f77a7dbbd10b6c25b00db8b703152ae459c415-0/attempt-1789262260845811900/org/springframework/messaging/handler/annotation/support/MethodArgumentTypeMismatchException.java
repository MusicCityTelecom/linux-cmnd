/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.MethodParameter
 */
package org.springframework.messaging.handler.annotation.support;

import org.springframework.core.MethodParameter;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.invocation.MethodArgumentResolutionException;

public class MethodArgumentTypeMismatchException
extends MethodArgumentResolutionException {
    public MethodArgumentTypeMismatchException(Message<?> message, MethodParameter parameter, String description) {
        super(message, parameter, description);
    }
}

