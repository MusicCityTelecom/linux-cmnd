/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.retry;

import java.io.Serializable;
import org.springframework.retry.RetryContext;

public interface RetryPolicy
extends Serializable {
    public boolean canRetry(RetryContext var1);

    public RetryContext open(RetryContext var1);

    public void close(RetryContext var1);

    public void registerThrowable(RetryContext var1, Throwable var2);
}

