/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.retry.backoff;

import org.springframework.retry.RetryContext;
import org.springframework.retry.backoff.BackOffContext;
import org.springframework.retry.backoff.BackOffInterruptedException;
import org.springframework.retry.backoff.BackOffPolicy;

public abstract class StatelessBackOffPolicy
implements BackOffPolicy {
    @Override
    public final void backOff(BackOffContext backOffContext) throws BackOffInterruptedException {
        this.doBackOff();
    }

    @Override
    public BackOffContext start(RetryContext status) {
        return null;
    }

    protected abstract void doBackOff() throws BackOffInterruptedException;
}

