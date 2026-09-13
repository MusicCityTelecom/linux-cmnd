/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.web.reactive.function.server.HandlerFunction
 */
package org.springframework.boot.actuate.web.mappings.reactive;

import org.springframework.web.reactive.function.server.HandlerFunction;

public class HandlerFunctionDescription {
    private final String className;

    HandlerFunctionDescription(HandlerFunction<?> handlerFunction) {
        this.className = HandlerFunctionDescription.getHandlerFunctionClassName(handlerFunction);
    }

    private static String getHandlerFunctionClassName(HandlerFunction<?> handlerFunction) {
        Class<?> functionClass = handlerFunction.getClass();
        String canonicalName = functionClass.getCanonicalName();
        return canonicalName != null ? canonicalName : functionClass.getName();
    }

    public String getClassName() {
        return this.className;
    }
}

