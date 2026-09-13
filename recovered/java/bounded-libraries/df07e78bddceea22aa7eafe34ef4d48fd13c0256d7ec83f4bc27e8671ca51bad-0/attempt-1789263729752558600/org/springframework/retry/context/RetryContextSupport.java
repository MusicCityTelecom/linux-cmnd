/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.AttributeAccessorSupport
 */
package org.springframework.retry.context;

import org.springframework.core.AttributeAccessorSupport;
import org.springframework.retry.RetryContext;

public class RetryContextSupport
extends AttributeAccessorSupport
implements RetryContext {
    private final RetryContext parent;
    private volatile boolean terminate = false;
    private volatile int count;
    private volatile Throwable lastException;

    public RetryContextSupport(RetryContext parent) {
        this.parent = parent;
    }

    @Override
    public RetryContext getParent() {
        return this.parent;
    }

    @Override
    public boolean isExhaustedOnly() {
        return this.terminate;
    }

    @Override
    public void setExhaustedOnly() {
        this.terminate = true;
    }

    @Override
    public int getRetryCount() {
        return this.count;
    }

    @Override
    public Throwable getLastThrowable() {
        return this.lastException;
    }

    public void registerThrowable(Throwable throwable) {
        this.lastException = throwable;
        if (throwable != null) {
            ++this.count;
        }
    }

    public String toString() {
        return String.format("[RetryContext: count=%d, lastException=%s, exhausted=%b]", this.count, this.lastException, this.terminate);
    }
}

