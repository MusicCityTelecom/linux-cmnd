/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.retry.backoff;

import java.util.Random;
import org.springframework.retry.backoff.BackOffInterruptedException;
import org.springframework.retry.backoff.Sleeper;
import org.springframework.retry.backoff.SleepingBackOffPolicy;
import org.springframework.retry.backoff.StatelessBackOffPolicy;
import org.springframework.retry.backoff.ThreadWaitSleeper;

public class UniformRandomBackOffPolicy
extends StatelessBackOffPolicy
implements SleepingBackOffPolicy<UniformRandomBackOffPolicy> {
    private static final long DEFAULT_BACK_OFF_MIN_PERIOD = 500L;
    private static final long DEFAULT_BACK_OFF_MAX_PERIOD = 1500L;
    private volatile long minBackOffPeriod = 500L;
    private volatile long maxBackOffPeriod = 1500L;
    private Random random = new Random(System.currentTimeMillis());
    private Sleeper sleeper = new ThreadWaitSleeper();

    @Override
    public UniformRandomBackOffPolicy withSleeper(Sleeper sleeper) {
        UniformRandomBackOffPolicy res = new UniformRandomBackOffPolicy();
        res.setMinBackOffPeriod(this.minBackOffPeriod);
        res.setMaxBackOffPeriod(this.maxBackOffPeriod);
        res.setSleeper(sleeper);
        return res;
    }

    public void setSleeper(Sleeper sleeper) {
        this.sleeper = sleeper;
    }

    public void setMinBackOffPeriod(long backOffPeriod) {
        this.minBackOffPeriod = backOffPeriod > 0L ? backOffPeriod : 1L;
    }

    public long getMinBackOffPeriod() {
        return this.minBackOffPeriod;
    }

    public void setMaxBackOffPeriod(long backOffPeriod) {
        this.maxBackOffPeriod = backOffPeriod > 0L ? backOffPeriod : 1L;
    }

    public long getMaxBackOffPeriod() {
        return this.maxBackOffPeriod;
    }

    @Override
    protected void doBackOff() throws BackOffInterruptedException {
        try {
            long delta = this.maxBackOffPeriod == this.minBackOffPeriod ? 0L : (long)this.random.nextInt((int)(this.maxBackOffPeriod - this.minBackOffPeriod));
            this.sleeper.sleep(this.minBackOffPeriod + delta);
        }
        catch (InterruptedException e) {
            throw new BackOffInterruptedException("Thread interrupted while sleeping", e);
        }
    }

    public String toString() {
        return "RandomBackOffPolicy[backOffPeriod=" + this.minBackOffPeriod + ", " + this.maxBackOffPeriod + "]";
    }
}

