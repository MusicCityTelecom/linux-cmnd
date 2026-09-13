/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.AttributeAccessor
 */
package org.springframework.retry;

import org.springframework.core.AttributeAccessor;

public interface RetryContext
extends AttributeAccessor {
    public static final String NAME = "context.name";
    public static final String STATE_KEY = "context.state";
    public static final String CLOSED = "context.closed";
    public static final String RECOVERED = "context.recovered";
    public static final String EXHAUSTED = "context.exhausted";

    public void setExhaustedOnly();

    public boolean isExhaustedOnly();

    public RetryContext getParent();

    public int getRetryCount();

    public Throwable getLastThrowable();
}

