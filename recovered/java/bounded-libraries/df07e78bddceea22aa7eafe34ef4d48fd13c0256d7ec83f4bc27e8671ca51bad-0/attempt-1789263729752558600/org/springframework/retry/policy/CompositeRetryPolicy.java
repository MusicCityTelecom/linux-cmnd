/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.retry.policy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryPolicy;
import org.springframework.retry.context.RetryContextSupport;

public class CompositeRetryPolicy
implements RetryPolicy {
    RetryPolicy[] policies = new RetryPolicy[0];
    private boolean optimistic = false;

    public void setOptimistic(boolean optimistic) {
        this.optimistic = optimistic;
    }

    public void setPolicies(RetryPolicy[] policies) {
        this.policies = Arrays.asList(policies).toArray(new RetryPolicy[policies.length]);
    }

    @Override
    public boolean canRetry(RetryContext context) {
        RetryContext[] contexts = ((CompositeRetryContext)context).contexts;
        RetryPolicy[] policies = ((CompositeRetryContext)context).policies;
        boolean retryable = true;
        if (this.optimistic) {
            retryable = false;
            for (int i = 0; i < contexts.length; ++i) {
                if (!policies[i].canRetry(contexts[i])) continue;
                retryable = true;
            }
        } else {
            for (int i = 0; i < contexts.length; ++i) {
                if (policies[i].canRetry(contexts[i])) continue;
                retryable = false;
            }
        }
        return retryable;
    }

    @Override
    public void close(RetryContext context) {
        RetryContext[] contexts = ((CompositeRetryContext)context).contexts;
        RetryPolicy[] policies = ((CompositeRetryContext)context).policies;
        RuntimeException exception = null;
        for (int i = 0; i < contexts.length; ++i) {
            try {
                policies[i].close(contexts[i]);
                continue;
            }
            catch (RuntimeException e) {
                if (exception != null) continue;
                exception = e;
            }
        }
        if (exception != null) {
            throw exception;
        }
    }

    @Override
    public RetryContext open(RetryContext parent) {
        ArrayList<RetryContext> list = new ArrayList<RetryContext>();
        for (RetryPolicy policy : this.policies) {
            list.add(policy.open(parent));
        }
        return new CompositeRetryContext(parent, list, this.policies);
    }

    @Override
    public void registerThrowable(RetryContext context, Throwable throwable) {
        RetryContext[] contexts = ((CompositeRetryContext)context).contexts;
        RetryPolicy[] policies = ((CompositeRetryContext)context).policies;
        for (int i = 0; i < contexts.length; ++i) {
            policies[i].registerThrowable(contexts[i], throwable);
        }
        ((RetryContextSupport)context).registerThrowable(throwable);
    }

    private static class CompositeRetryContext
    extends RetryContextSupport {
        RetryContext[] contexts;
        RetryPolicy[] policies;

        public CompositeRetryContext(RetryContext parent, List<RetryContext> contexts, RetryPolicy[] policies) {
            super(parent);
            this.contexts = contexts.toArray(new RetryContext[contexts.size()]);
            this.policies = policies;
        }
    }
}

