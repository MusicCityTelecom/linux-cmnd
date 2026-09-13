/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.retry.backoff;

import org.springframework.retry.backoff.BackOffInterruptedException;
import org.springframework.retry.backoff.Sleeper;
import org.springframework.retry.backoff.SleepingBackOffPolicy;
import org.springframework.retry.backoff.StatelessBackOffPolicy;
import org.springframework.retry.backoff.ThreadWaitSleeper;

public class FixedBackOffPolicy
extends StatelessBackOffPolicy
implements SleepingBackOffPolicy<FixedBackOffPolicy> {
    private static final long DEFAULT_BACK_OFF_PERIOD = 1000L;
    private volatile long backOffPeriod = 1000L;
    private Sleeper sleeper = new ThreadWaitSleeper();

    @Override
    public FixedBackOffPolicy withSleeper(Sleeper sleeper) {
        FixedBackOffPolicy res = new FixedBackOffPolicy();
        res.setBackOffPeriod(this.backOffPeriod);
        res.setSleeper(sleeper);
        return res;
    }

    public void setSleeper(Sleeper sleeper) {
        this.sleeper = sleeper;
    }

    public void setBackOffPeriod(long backOffPeriod) {
        this.backOffPeriod = backOffPeriod > 0L ? backOffPeriod : 1L;
    }

    public long getBackOffPeriod() {
        return this.backOffPeriod;
    }

    @Override
    protected void doBackOff() throws BackOffInterruptedException {
        try {
            this.sleeper.sleep(this.backOffPeriod);
        }
        catch (InterruptedException e) {
            throw new BackOffInterruptedException("Thread interrupted while sleeping", e);
        }
    }

    public String toString() {
        return "FixedBackOffPolicy[backOffPeriod=" + this.backOffPeriod + "]";
    }
}

