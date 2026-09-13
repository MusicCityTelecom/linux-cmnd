/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.asm.Type
 *  org.springframework.web.method.HandlerMethod
 */
package org.springframework.boot.actuate.web.mappings;

import java.lang.reflect.Method;
import org.springframework.asm.Type;
import org.springframework.web.method.HandlerMethod;

public class HandlerMethodDescription {
    private final String className;
    private final String name;
    private final String descriptor;

    public HandlerMethodDescription(HandlerMethod handlerMethod) {
        this.name = handlerMethod.getMethod().getName();
        this.className = handlerMethod.getMethod().getDeclaringClass().getCanonicalName();
        this.descriptor = Type.getMethodDescriptor((Method)handlerMethod.getMethod());
    }

    public String getName() {
        return this.name;
    }

    public String getDescriptor() {
        return this.descriptor;
    }

    public String getClassName() {
        return this.className;
    }
}

