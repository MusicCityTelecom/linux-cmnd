/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.retry.policy;

import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryPolicy;
import org.springframework.retry.context.RetryContextSupport;

public class NeverRetryPolicy
implements RetryPolicy {
    @Override
    public boolean canRetry(RetryContext context) {
        return !((NeverRetryContext)context).isFinished();
    }

    @Override
    public void close(RetryContext context) {
    }

    @Override
    public RetryContext open(RetryContext parent) {
        return new NeverRetryContext(parent);
    }

    @Override
    public void registerThrowable(RetryContext context, Throwable throwable) {
        ((NeverRetryContext)context).setFinished();
        ((RetryContextSupport)context).registerThrowable(throwable);
    }

    private static class NeverRetryContext
    extends RetryContextSupport {
        private boolean finished = false;

        public NeverRetryContext(RetryContext parent) {
            super(parent);
        }

        public boolean isFinished() {
            return this.finished;
        }

        public void setFinished() {
            this.finished = true;
        }
    }
}

