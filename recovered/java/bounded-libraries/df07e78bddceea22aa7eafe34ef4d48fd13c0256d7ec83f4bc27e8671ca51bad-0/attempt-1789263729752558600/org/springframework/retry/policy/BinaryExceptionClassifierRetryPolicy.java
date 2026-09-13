/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.retry.policy;

import org.springframework.classify.BinaryExceptionClassifier;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryPolicy;
import org.springframework.retry.context.RetryContextSupport;

public class BinaryExceptionClassifierRetryPolicy
implements RetryPolicy {
    private final BinaryExceptionClassifier exceptionClassifier;

    public BinaryExceptionClassifierRetryPolicy(BinaryExceptionClassifier exceptionClassifier) {
        this.exceptionClassifier = exceptionClassifier;
    }

    public BinaryExceptionClassifier getExceptionClassifier() {
        return this.exceptionClassifier;
    }

    @Override
    public boolean canRetry(RetryContext context) {
        Throwable t = context.getLastThrowable();
        return t == null || this.exceptionClassifier.classify(t) != false;
    }

    @Override
    public void close(RetryContext status) {
    }

    @Override
    public void registerThrowable(RetryContext context, Throwable throwable) {
        RetryContextSupport simpleContext = (RetryContextSupport)context;
        simpleContext.registerThrowable(throwable);
    }

    @Override
    public RetryContext open(RetryContext parent) {
        return new RetryContextSupport(parent);
    }
}

