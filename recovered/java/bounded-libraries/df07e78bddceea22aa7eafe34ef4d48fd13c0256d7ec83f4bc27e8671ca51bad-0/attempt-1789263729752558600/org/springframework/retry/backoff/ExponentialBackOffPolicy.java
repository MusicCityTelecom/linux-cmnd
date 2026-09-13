/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.util.ClassUtils
 */
package org.springframework.retry.backoff;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.retry.RetryContext;
import org.springframework.retry.backoff.BackOffContext;
import org.springframework.retry.backoff.BackOffInterruptedException;
import org.springframework.retry.backoff.Sleeper;
import org.springframework.retry.backoff.SleepingBackOffPolicy;
import org.springframework.retry.backoff.ThreadWaitSleeper;
import org.springframework.util.ClassUtils;

public class ExponentialBackOffPolicy
implements SleepingBackOffPolicy<ExponentialBackOffPolicy> {
    protected final Log logger = LogFactory.getLog(this.getClass());
    public static final long DEFAULT_INITIAL_INTERVAL = 100L;
    public static final long DEFAULT_MAX_INTERVAL = 30000L;
    public static final double DEFAULT_MULTIPLIER = 2.0;
    private volatile long initialInterval = 100L;
    private volatile long maxInterval = 30000L;
    private volatile double multiplier = 2.0;
    private Sleeper sleeper = new ThreadWaitSleeper();

    public void setSleeper(Sleeper sleeper) {
        this.sleeper = sleeper;
    }

    @Override
    public ExponentialBackOffPolicy withSleeper(Sleeper sleeper) {
        ExponentialBackOffPolicy res = this.newInstance();
        this.cloneValues(res);
        res.setSleeper(sleeper);
        return res;
    }

    protected ExponentialBackOffPolicy newInstance() {
        return new ExponentialBackOffPolicy();
    }

    protected void cloneValues(ExponentialBackOffPolicy target) {
        target.setInitialInterval(this.getInitialInterval());
        target.setMaxInterval(this.getMaxInterval());
        target.setMultiplier(this.getMultiplier());
        target.setSleeper(this.sleeper);
    }

    public void setInitialInterval(long initialInterval) {
        this.initialInterval = initialInterval > 1L ? initialInterval : 1L;
    }

    public void setMultiplier(double multiplier) {
        this.multiplier = multiplier > 1.0 ? multiplier : 1.0;
    }

    public void setMaxInterval(long maxInterval) {
        this.maxInterval = maxInterval > 0L ? maxInterval : 1L;
    }

    public long getInitialInterval() {
        return this.initialInterval;
    }

    public long getMaxInterval() {
        return this.maxInterval;
    }

    public double getMultiplier() {
        return this.multiplier;
    }

    @Override
    public BackOffContext start(RetryContext context) {
        return new ExponentialBackOffContext(this.initialInterval, this.multiplier, this.maxInterval);
    }

    @Override
    public void backOff(BackOffContext backOffContext) throws BackOffInterruptedException {
        ExponentialBackOffContext context = (ExponentialBackOffContext)backOffContext;
        try {
            long sleepTime = context.getSleepAndIncrement();
            if (this.logger.isDebugEnabled()) {
                this.logger.debug((Object)("Sleeping for " + sleepTime));
            }
            this.sleeper.sleep(sleepTime);
        }
        catch (InterruptedException e) {
            throw new BackOffInterruptedException("Thread interrupted while sleeping", e);
        }
    }

    public String toString() {
        return ClassUtils.getShortName(this.getClass()) + "[initialInterval=" + this.initialInterval + ", multiplier=" + this.multiplier + ", maxInterval=" + this.maxInterval + "]";
    }

    static class ExponentialBackOffContext
    implements BackOffContext {
        private final double multiplier;
        private long interval;
        private long maxInterval;

        public ExponentialBackOffContext(long interval, double multiplier, long maxInterval) {
            this.interval = interval;
            this.multiplier = multiplier;
            this.maxInterval = maxInterval;
        }

        public synchronized long getSleepAndIncrement() {
            long sleep = this.interval;
            if (sleep > this.maxInterval) {
                sleep = this.maxInterval;
            } else {
                this.interval = this.getNextInterval();
            }
            return sleep;
        }

        protected long getNextInterval() {
            return (long)((double)this.interval * this.multiplier);
        }

        public double getMultiplier() {
            return this.multiplier;
        }

        public long getInterval() {
            return this.interval;
        }

        public long getMaxInterval() {
            return this.maxInterval;
        }
    }
}

