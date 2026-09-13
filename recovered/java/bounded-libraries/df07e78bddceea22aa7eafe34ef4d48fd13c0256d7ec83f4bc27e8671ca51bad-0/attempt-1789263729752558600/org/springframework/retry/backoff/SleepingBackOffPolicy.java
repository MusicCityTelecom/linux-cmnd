/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.retry.backoff;

import org.springframework.retry.backoff.BackOffPolicy;
import org.springframework.retry.backoff.Sleeper;

public interface SleepingBackOffPolicy<T extends SleepingBackOffPolicy<T>>
extends BackOffPolicy {
    public T withSleeper(Sleeper var1);
}

