/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.retry;

import org.springframework.retry.RetryContext;

public interface RetryCallback<T, E extends Throwable> {
    public T doWithRetry(RetryContext var1) throws E;
}

