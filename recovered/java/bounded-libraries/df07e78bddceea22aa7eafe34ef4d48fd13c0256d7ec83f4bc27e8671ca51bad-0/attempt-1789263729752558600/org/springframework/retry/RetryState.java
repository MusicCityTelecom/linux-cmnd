/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.retry;

public interface RetryState {
    public Object getKey();

    public boolean isForceRefresh();

    public boolean rollbackFor(Throwable var1);
}

