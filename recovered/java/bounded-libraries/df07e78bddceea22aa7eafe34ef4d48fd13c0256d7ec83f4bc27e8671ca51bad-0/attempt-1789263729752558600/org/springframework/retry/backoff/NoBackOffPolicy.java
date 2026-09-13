/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.retry.backoff;

import org.springframework.retry.backoff.BackOffInterruptedException;
import org.springframework.retry.backoff.StatelessBackOffPolicy;

public class NoBackOffPolicy
extends StatelessBackOffPolicy {
    @Override
    protected void doBackOff() throws BackOffInterruptedException {
    }

    public String toString() {
        return "NoBackOffPolicy []";
    }
}

