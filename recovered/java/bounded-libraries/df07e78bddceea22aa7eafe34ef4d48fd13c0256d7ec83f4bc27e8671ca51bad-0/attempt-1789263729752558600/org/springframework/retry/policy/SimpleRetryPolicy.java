/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.ClassUtils
 */
package org.springframework.retry.policy;

import java.util.Map;
import org.springframework.classify.BinaryExceptionClassifier;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryPolicy;
import org.springframework.retry.context.RetryContextSupport;
import org.springframework.util.ClassUtils;

public class SimpleRetryPolicy
implements RetryPolicy {
    public static final int DEFAULT_MAX_ATTEMPTS = 3;
    private volatile int maxAttempts;
    private BinaryExceptionClassifier retryableClassifier = new BinaryExceptionClassifier(false);

    public SimpleRetryPolicy() {
        this(3, BinaryExceptionClassifier.defaultClassifier());
    }

    public SimpleRetryPolicy(int maxAttempts) {
        this(maxAttempts, BinaryExceptionClassifier.defaultClassifier());
    }

    public SimpleRetryPolicy(int maxAttempts, Map<Class<? extends Throwable>, Boolean> retryableExceptions) {
        this(maxAttempts, retryableExceptions, false);
    }

    public SimpleRetryPolicy(int maxAttempts, Map<Class<? extends Throwable>, Boolean> retryableExceptions, boolean traverseCauses) {
        this(maxAttempts, retryableExceptions, traverseCauses, false);
    }

    public SimpleRetryPolicy(int maxAttempts, Map<Class<? extends Throwable>, Boolean> retryableExceptions, boolean traverseCauses, boolean defaultValue) {
        this.maxAttempts = maxAttempts;
        this.retryableClassifier = new BinaryExceptionClassifier(retryableExceptions, defaultValue);
        this.retryableClassifier.setTraverseCauses(traverseCauses);
    }

    public SimpleRetryPolicy(int maxAttempts, BinaryExceptionClassifier classifier) {
        this.maxAttempts = maxAttempts;
        this.retryableClassifier = classifier;
    }

    public void setMaxAttempts(int maxAttempts) {
        this.maxAttempts = maxAttempts;
    }

    public int getMaxAttempts() {
        return this.maxAttempts;
    }

    @Override
    public boolean canRetry(RetryContext context) {
        Throwable t = context.getLastThrowable();
        return (t == null || this.retryForException(t)) && context.getRetryCount() < this.getMaxAttempts();
    }

    @Override
    public void close(RetryContext status) {
    }

    @Override
    public void registerThrowable(RetryContext context, Throwable throwable) {
        SimpleRetryContext simpleContext = (SimpleRetryContext)context;
        simpleContext.registerThrowable(throwable);
    }

    @Override
    public RetryContext open(RetryContext parent) {
        return new SimpleRetryContext(parent);
    }

    private boolean retryForException(Throwable ex) {
        return this.retryableClassifier.classify(ex);
    }

    public String toString() {
        return ClassUtils.getShortName(this.getClass()) + "[maxAttempts=" + this.maxAttempts + "]";
    }

    private static class SimpleRetryContext
    extends RetryContextSupport {
        public SimpleRetryContext(RetryContext parent) {
            super(parent);
        }
    }
}

