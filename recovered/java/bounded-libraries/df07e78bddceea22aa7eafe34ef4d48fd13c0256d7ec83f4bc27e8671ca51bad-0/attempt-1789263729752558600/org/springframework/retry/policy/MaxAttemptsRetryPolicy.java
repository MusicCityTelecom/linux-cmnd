/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.retry.policy;

import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryPolicy;
import org.springframework.retry.context.RetryContextSupport;

public class MaxAttemptsRetryPolicy
implements RetryPolicy {
    public static final int DEFAULT_MAX_ATTEMPTS = 3;
    private volatile int maxAttempts;

    public MaxAttemptsRetryPolicy() {
        this.maxAttempts = 3;
    }

    public MaxAttemptsRetryPolicy(int maxAttempts) {
        this.maxAttempts = maxAttempts;
    }

    public void setMaxAttempts(int maxAttempts) {
        this.maxAttempts = maxAttempts;
    }

    public int getMaxAttempts() {
        return this.maxAttempts;
    }

    @Override
    public boolean canRetry(RetryContext context) {
        return context.getRetryCount() < this.maxAttempts;
    }

    @Override
    public void close(RetryContext status) {
    }

    @Override
    public void registerThrowable(RetryContext context, Throwable throwable) {
        ((RetryContextSupport)context).registerThrowable(throwable);
    }

    @Override
    public RetryContext open(RetryContext parent) {
        return new RetryContextSupport(parent);
    }
}

