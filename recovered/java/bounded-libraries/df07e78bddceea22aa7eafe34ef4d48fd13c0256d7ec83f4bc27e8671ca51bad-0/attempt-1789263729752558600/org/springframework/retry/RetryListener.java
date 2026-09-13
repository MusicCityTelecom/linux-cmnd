/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.retry;

import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;

public interface RetryListener {
    public <T, E extends Throwable> boolean open(RetryContext var1, RetryCallback<T, E> var2);

    public <T, E extends Throwable> void close(RetryContext var1, RetryCallback<T, E> var2, Throwable var3);

    public <T, E extends Throwable> void onError(RetryContext var1, RetryCallback<T, E> var2, Throwable var3);
}

