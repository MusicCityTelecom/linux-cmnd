/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.retry.backoff;

import org.springframework.retry.backoff.BackOffPolicy;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.backoff.ExponentialRandomBackOffPolicy;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.backoff.Sleeper;
import org.springframework.retry.backoff.UniformRandomBackOffPolicy;

public class BackOffPolicyBuilder {
    private static final long DEFAULT_INITIAL_DELAY = 1000L;
    private long delay = 1000L;
    private long maxDelay;
    private double multiplier;
    private boolean random;
    private Sleeper sleeper;

    private BackOffPolicyBuilder() {
    }

    public static BackOffPolicyBuilder newBuilder() {
        return new BackOffPolicyBuilder();
    }

    public static BackOffPolicy newDefaultPolicy() {
        return new BackOffPolicyBuilder().build();
    }

    public BackOffPolicyBuilder delay(long delay) {
        this.delay = delay;
        return this;
    }

    public BackOffPolicyBuilder maxDelay(long maxDelay) {
        this.maxDelay = maxDelay;
        return this;
    }

    public BackOffPolicyBuilder multiplier(double multiplier) {
        this.multiplier = multiplier;
        return this;
    }

    public BackOffPolicyBuilder random(boolean random) {
        this.random = random;
        return this;
    }

    public BackOffPolicyBuilder sleeper(Sleeper sleeper) {
        this.sleeper = sleeper;
        return this;
    }

    public BackOffPolicy build() {
        if (this.multiplier > 0.0) {
            ExponentialBackOffPolicy policy = new ExponentialBackOffPolicy();
            if (this.random) {
                policy = new ExponentialRandomBackOffPolicy();
            }
            policy.setInitialInterval(this.delay);
            policy.setMultiplier(this.multiplier);
            policy.setMaxInterval(this.maxDelay > this.delay ? this.maxDelay : 30000L);
            if (this.sleeper != null) {
                policy.setSleeper(this.sleeper);
            }
            return policy;
        }
        if (this.maxDelay > this.delay) {
            UniformRandomBackOffPolicy policy = new UniformRandomBackOffPolicy();
            policy.setMinBackOffPeriod(this.delay);
            policy.setMaxBackOffPeriod(this.maxDelay);
            if (this.sleeper != null) {
                policy.setSleeper(this.sleeper);
            }
            return policy;
        }
        FixedBackOffPolicy policy = new FixedBackOffPolicy();
        policy.setBackOffPeriod(this.delay);
        if (this.sleeper != null) {
            policy.setSleeper(this.sleeper);
        }
        return policy;
    }
}

