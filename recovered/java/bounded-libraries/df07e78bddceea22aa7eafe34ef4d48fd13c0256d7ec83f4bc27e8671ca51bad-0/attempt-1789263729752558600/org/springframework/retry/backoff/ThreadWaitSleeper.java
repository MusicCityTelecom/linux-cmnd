/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.retry.backoff;

import org.springframework.retry.backoff.Sleeper;

public class ThreadWaitSleeper
implements Sleeper {
    @Override
    public void sleep(long backOffPeriod) throws InterruptedException {
        Thread.sleep(backOffPeriod);
    }
}

