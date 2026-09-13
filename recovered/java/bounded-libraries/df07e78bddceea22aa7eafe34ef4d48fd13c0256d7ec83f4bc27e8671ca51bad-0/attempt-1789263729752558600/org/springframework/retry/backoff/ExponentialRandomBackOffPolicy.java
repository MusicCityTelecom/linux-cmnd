/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.retry.backoff;

import java.util.Random;
import org.springframework.retry.RetryContext;
import org.springframework.retry.backoff.BackOffContext;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;

public class ExponentialRandomBackOffPolicy
extends ExponentialBackOffPolicy {
    @Override
    public BackOffContext start(RetryContext context) {
        return new ExponentialRandomBackOffContext(this.getInitialInterval(), this.getMultiplier(), this.getMaxInterval());
    }

    @Override
    protected ExponentialBackOffPolicy newInstance() {
        return new ExponentialRandomBackOffPolicy();
    }

    static class ExponentialRandomBackOffContext
    extends ExponentialBackOffPolicy.ExponentialBackOffContext {
        private final Random r = new Random();

        public ExponentialRandomBackOffContext(long expSeed, double multiplier, long maxInterval) {
            super(expSeed, multiplier, maxInterval);
        }

        @Override
        public synchronized long getSleepAndIncrement() {
            long next = super.getSleepAndIncrement();
            if ((next = (long)((double)next * (1.0 + (double)this.r.nextFloat() * (this.getMultiplier() - 1.0)))) > super.getMaxInterval()) {
                next = super.getMaxInterval();
            }
            return next;
        }
    }
}

