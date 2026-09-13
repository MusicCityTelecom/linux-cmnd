/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.retry.backoff;

import org.springframework.retry.RetryContext;
import org.springframework.retry.backoff.BackOffContext;
import org.springframework.retry.backoff.BackOffInterruptedException;

public interface BackOffPolicy {
    public BackOffContext start(RetryContext var1);

    public void backOff(BackOffContext var1) throws BackOffInterruptedException;
}

