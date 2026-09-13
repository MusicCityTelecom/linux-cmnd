/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.retry;

import org.springframework.retry.RetryContext;

public interface RecoveryCallback<T> {
    public T recover(RetryContext var1) throws Exception;
}

