/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.Assert
 */
package org.springframework.retry.policy;

import java.util.HashMap;
import java.util.Map;
import org.springframework.classify.Classifier;
import org.springframework.classify.ClassifierSupport;
import org.springframework.classify.SubclassClassifier;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryPolicy;
import org.springframework.retry.context.RetryContextSupport;
import org.springframework.retry.policy.NeverRetryPolicy;
import org.springframework.util.Assert;

public class ExceptionClassifierRetryPolicy
implements RetryPolicy {
    private Classifier<Throwable, RetryPolicy> exceptionClassifier = new ClassifierSupport<Throwable, NeverRetryPolicy>(new NeverRetryPolicy());

    public void setPolicyMap(Map<Class<? extends Throwable>, RetryPolicy> policyMap) {
        this.exceptionClassifier = new SubclassClassifier<Throwable, NeverRetryPolicy>(policyMap, new NeverRetryPolicy());
    }

    public void setExceptionClassifier(Classifier<Throwable, RetryPolicy> exceptionClassifier) {
        this.exceptionClassifier = exceptionClassifier;
    }

    @Override
    public boolean canRetry(RetryContext context) {
        RetryPolicy policy = (RetryPolicy)((Object)context);
        return policy.canRetry(context);
    }

    @Override
    public void close(RetryContext context) {
        RetryPolicy policy = (RetryPolicy)((Object)context);
        policy.close(context);
    }

    @Override
    public RetryContext open(RetryContext parent) {
        return new ExceptionClassifierRetryContext(parent, this.exceptionClassifier).open(parent);
    }

    @Override
    public void registerThrowable(RetryContext context, Throwable throwable) {
        RetryPolicy policy = (RetryPolicy)((Object)context);
        policy.registerThrowable(context, throwable);
        ((RetryContextSupport)context).registerThrowable(throwable);
    }

    private static class ExceptionClassifierRetryContext
    extends RetryContextSupport
    implements RetryPolicy {
        private final Classifier<Throwable, RetryPolicy> exceptionClassifier;
        private RetryPolicy policy;
        private RetryContext context;
        private final Map<RetryPolicy, RetryContext> contexts = new HashMap<RetryPolicy, RetryContext>();

        public ExceptionClassifierRetryContext(RetryContext parent, Classifier<Throwable, RetryPolicy> exceptionClassifier) {
            super(parent);
            this.exceptionClassifier = exceptionClassifier;
        }

        @Override
        public boolean canRetry(RetryContext context) {
            return this.context == null || this.policy.canRetry(this.context);
        }

        @Override
        public void close(RetryContext context) {
            for (RetryPolicy policy : this.contexts.keySet()) {
                policy.close(this.getContext(policy, context.getParent()));
            }
        }

        @Override
        public RetryContext open(RetryContext parent) {
            return this;
        }

        @Override
        public void registerThrowable(RetryContext context, Throwable throwable) {
            this.policy = this.exceptionClassifier.classify(throwable);
            Assert.notNull((Object)this.policy, (String)("Could not locate policy for exception=[" + throwable + "]."));
            this.context = this.getContext(this.policy, context.getParent());
            this.policy.registerThrowable(this.context, throwable);
        }

        private RetryContext getContext(RetryPolicy policy, RetryContext parent) {
            RetryContext context = this.contexts.get(policy);
            if (context == null) {
                context = policy.open(parent);
                this.contexts.put(policy, context);
            }
            return context;
        }
    }
}

