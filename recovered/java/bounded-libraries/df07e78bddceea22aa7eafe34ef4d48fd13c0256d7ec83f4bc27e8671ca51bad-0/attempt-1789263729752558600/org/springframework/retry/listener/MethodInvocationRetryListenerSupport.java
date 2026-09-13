/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.retry.listener;

import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryListener;
import org.springframework.retry.interceptor.MethodInvocationRetryCallback;

public class MethodInvocationRetryListenerSupport
implements RetryListener {
    @Override
    public <T, E extends Throwable> void close(RetryContext context, RetryCallback<T, E> callback, Throwable throwable) {
        if (callback instanceof MethodInvocationRetryCallback) {
            MethodInvocationRetryCallback methodInvocationRetryCallback = (MethodInvocationRetryCallback)callback;
            this.doClose(context, methodInvocationRetryCallback, throwable);
        }
    }

    @Override
    public <T, E extends Throwable> void onError(RetryContext context, RetryCallback<T, E> callback, Throwable throwable) {
        if (callback instanceof MethodInvocationRetryCallback) {
            MethodInvocationRetryCallback methodInvocationRetryCallback = (MethodInvocationRetryCallback)callback;
            this.doOnError(context, methodInvocationRetryCallback, throwable);
        }
    }

    @Override
    public <T, E extends Throwable> boolean open(RetryContext context, RetryCallback<T, E> callback) {
        if (callback instanceof MethodInvocationRetryCallback) {
            MethodInvocationRetryCallback methodInvocationRetryCallback = (MethodInvocationRetryCallback)callback;
            return this.doOpen(context, methodInvocationRetryCallback);
        }
        return true;
    }

    protected <T, E extends Throwable> void doClose(RetryContext context, MethodInvocationRetryCallback<T, E> callback, Throwable throwable) {
    }

    protected <T, E extends Throwable> void doOnError(RetryContext context, MethodInvocationRetryCallback<T, E> callback, Throwable throwable) {
    }

    protected <T, E extends Throwable> boolean doOpen(RetryContext context, MethodInvocationRetryCallback<T, E> callback) {
        return true;
    }
}

