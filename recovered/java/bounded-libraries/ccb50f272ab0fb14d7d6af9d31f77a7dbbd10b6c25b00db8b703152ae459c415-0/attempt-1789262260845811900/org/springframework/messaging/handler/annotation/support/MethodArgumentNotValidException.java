/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.MethodParameter
 *  org.springframework.lang.Nullable
 *  org.springframework.validation.BindingResult
 *  org.springframework.validation.ObjectError
 */
package org.springframework.messaging.handler.annotation.support;

import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.invocation.MethodArgumentResolutionException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

public class MethodArgumentNotValidException
extends MethodArgumentResolutionException {
    @Nullable
    private final BindingResult bindingResult;

    public MethodArgumentNotValidException(Message<?> message, MethodParameter parameter) {
        super(message, parameter);
        this.bindingResult = null;
    }

    public MethodArgumentNotValidException(Message<?> message, MethodParameter parameter, BindingResult bindingResult) {
        super(message, parameter, MethodArgumentNotValidException.getValidationErrorMessage(bindingResult));
        this.bindingResult = bindingResult;
    }

    @Nullable
    public final BindingResult getBindingResult() {
        return this.bindingResult;
    }

    private static String getValidationErrorMessage(BindingResult bindingResult) {
        StringBuilder sb = new StringBuilder();
        sb.append(bindingResult.getErrorCount()).append(" error(s): ");
        for (ObjectError error : bindingResult.getAllErrors()) {
            sb.append('[').append(error).append("] ");
        }
        return sb.toString();
    }
}

