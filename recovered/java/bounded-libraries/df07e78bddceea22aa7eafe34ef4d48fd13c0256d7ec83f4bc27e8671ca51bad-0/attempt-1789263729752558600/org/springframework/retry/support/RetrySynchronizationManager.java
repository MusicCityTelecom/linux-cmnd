/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.retry.support;

import org.springframework.retry.RetryContext;

public final class RetrySynchronizationManager {
    private static final ThreadLocal<RetryContext> context = new ThreadLocal();

    private RetrySynchronizationManager() {
    }

    public static RetryContext getContext() {
        RetryContext result = context.get();
        return result;
    }

    public static RetryContext register(RetryContext context) {
        RetryContext oldContext = RetrySynchronizationManager.getContext();
        RetrySynchronizationManager.context.set(context);
        return oldContext;
    }

    public static RetryContext clear() {
        RetryContext value = RetrySynchronizationManager.getContext();
        RetryContext parent = value == null ? null : value.getParent();
        context.set(parent);
        return value;
    }
}

