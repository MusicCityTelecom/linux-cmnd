/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.integration.gateway;

import java.lang.reflect.Method;

public final class MethodArgsHolder {
    private final Method method;
    private final Object[] args;

    public MethodArgsHolder(Method method, Object[] args) {
        this.method = method;
        this.args = args;
    }

    public Method getMethod() {
        return this.method;
    }

    public Object[] getArgs() {
        return this.args;
    }
}

