/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.retry.policy;

import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryPolicy;
import org.springframework.retry.context.RetryContextSupport;

public class TimeoutRetryPolicy
implements RetryPolicy {
    public static final long DEFAULT_TIMEOUT = 1000L;
    private long timeout = 1000L;

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public long getTimeout() {
        return this.timeout;
    }

    @Override
    public boolean canRetry(RetryContext context) {
        return ((TimeoutRetryContext)context).isAlive();
    }

    @Override
    public void close(RetryContext context) {
    }

    @Override
    public RetryContext open(RetryContext parent) {
        return new TimeoutRetryContext(parent, this.timeout);
    }

    @Override
    public void registerThrowable(RetryContext context, Throwable throwable) {
        ((RetryContextSupport)context).registerThrowable(throwable);
    }

    private static class TimeoutRetryContext
    extends RetryContextSupport {
        private long timeout;
        private long start = System.currentTimeMillis();

        public TimeoutRetryContext(RetryContext parent, long timeout) {
            super(parent);
            this.timeout = timeout;
        }

        public boolean isAlive() {
            return System.currentTimeMillis() - this.start <= this.timeout;
        }
    }
}

