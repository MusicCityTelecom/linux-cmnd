/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.retry.interceptor;

public interface MethodInvocationRecoverer<T> {
    public T recover(Object[] var1, Throwable var2);
}

