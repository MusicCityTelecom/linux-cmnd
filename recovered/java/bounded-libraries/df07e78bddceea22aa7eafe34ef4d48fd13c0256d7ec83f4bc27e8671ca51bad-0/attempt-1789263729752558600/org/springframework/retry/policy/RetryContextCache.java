/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.retry.policy;

import org.springframework.retry.RetryContext;
import org.springframework.retry.policy.RetryCacheCapacityExceededException;

public interface RetryContextCache {
    public RetryContext get(Object var1);

    public void put(Object var1, RetryContext var2) throws RetryCacheCapacityExceededException;

    public void remove(Object var1);

    public boolean containsKey(Object var1);
}

