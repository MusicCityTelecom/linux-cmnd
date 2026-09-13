/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.Assert
 */
package org.springframework.retry.support;

import java.util.ArrayList;
import java.util.List;
import org.springframework.classify.BinaryExceptionClassifier;
import org.springframework.classify.BinaryExceptionClassifierBuilder;
import org.springframework.retry.RetryListener;
import org.springframework.retry.RetryPolicy;
import org.springframework.retry.backoff.BackOffPolicy;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.backoff.ExponentialRandomBackOffPolicy;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.backoff.NoBackOffPolicy;
import org.springframework.retry.backoff.UniformRandomBackOffPolicy;
import org.springframework.retry.policy.AlwaysRetryPolicy;
import org.springframework.retry.policy.BinaryExceptionClassifierRetryPolicy;
import org.springframework.retry.policy.CompositeRetryPolicy;
import org.springframework.retry.policy.MaxAttemptsRetryPolicy;
import org.springframework.retry.policy.TimeoutRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.util.Assert;

public class RetryTemplateBuilder {
    private RetryPolicy baseRetryPolicy;
    private BackOffPolicy backOffPolicy;
    private List<RetryListener> listeners;
    private BinaryExceptionClassifierBuilder classifierBuilder;

    public RetryTemplateBuilder maxAttempts(int maxAttempts) {
        Assert.isTrue((maxAttempts > 0 ? 1 : 0) != 0, (String)"Number of attempts should be positive");
        Assert.isNull((Object)this.baseRetryPolicy, (String)"You have already selected another retry policy");
        this.baseRetryPolicy = new MaxAttemptsRetryPolicy(maxAttempts);
        return this;
    }

    public RetryTemplateBuilder withinMillis(long timeout) {
        Assert.isTrue((timeout > 0L ? 1 : 0) != 0, (String)"Timeout should be positive");
        Assert.isNull((Object)this.baseRetryPolicy, (String)"You have already selected another retry policy");
        TimeoutRetryPolicy timeoutRetryPolicy = new TimeoutRetryPolicy();
        timeoutRetryPolicy.setTimeout(timeout);
        this.baseRetryPolicy = timeoutRetryPolicy;
        return this;
    }

    public RetryTemplateBuilder infiniteRetry() {
        Assert.isNull((Object)this.baseRetryPolicy, (String)"You have already selected another retry policy");
        this.baseRetryPolicy = new AlwaysRetryPolicy();
        return this;
    }

    public RetryTemplateBuilder customPolicy(RetryPolicy policy) {
        Assert.notNull((Object)policy, (String)"Policy should not be null");
        Assert.isNull((Object)this.baseRetryPolicy, (String)"You have already selected another retry policy");
        this.baseRetryPolicy = policy;
        return this;
    }

    public RetryTemplateBuilder exponentialBackoff(long initialInterval, double multiplier, long maxInterval) {
        return this.exponentialBackoff(initialInterval, multiplier, maxInterval, false);
    }

    public RetryTemplateBuilder exponentialBackoff(long initialInterval, double multiplier, long maxInterval, boolean withRandom) {
        Assert.isNull((Object)this.backOffPolicy, (String)"You have already selected backoff policy");
        Assert.isTrue((initialInterval >= 1L ? 1 : 0) != 0, (String)"Initial interval should be >= 1");
        Assert.isTrue((multiplier > 1.0 ? 1 : 0) != 0, (String)"Multiplier should be > 1");
        Assert.isTrue((maxInterval > initialInterval ? 1 : 0) != 0, (String)"Max interval should be > than initial interval");
        ExponentialBackOffPolicy policy = withRandom ? new ExponentialRandomBackOffPolicy() : new ExponentialBackOffPolicy();
        policy.setInitialInterval(initialInterval);
        policy.setMultiplier(multiplier);
        policy.setMaxInterval(maxInterval);
        this.backOffPolicy = policy;
        return this;
    }

    public RetryTemplateBuilder fixedBackoff(long interval) {
        Assert.isNull((Object)this.backOffPolicy, (String)"You have already selected backoff policy");
        Assert.isTrue((interval >= 1L ? 1 : 0) != 0, (String)"Interval should be >= 1");
        FixedBackOffPolicy policy = new FixedBackOffPolicy();
        policy.setBackOffPeriod(interval);
        this.backOffPolicy = policy;
        return this;
    }

    public RetryTemplateBuilder uniformRandomBackoff(long minInterval, long maxInterval) {
        Assert.isNull((Object)this.backOffPolicy, (String)"You have already selected backoff policy");
        Assert.isTrue((minInterval >= 1L ? 1 : 0) != 0, (String)"Min interval should be >= 1");
        Assert.isTrue((maxInterval >= 1L ? 1 : 0) != 0, (String)"Max interval should be >= 1");
        Assert.isTrue((maxInterval > minInterval ? 1 : 0) != 0, (String)"Max interval should be > than min interval");
        UniformRandomBackOffPolicy policy = new UniformRandomBackOffPolicy();
        policy.setMinBackOffPeriod(minInterval);
        policy.setMaxBackOffPeriod(maxInterval);
        this.backOffPolicy = policy;
        return this;
    }

    public RetryTemplateBuilder noBackoff() {
        Assert.isNull((Object)this.backOffPolicy, (String)"You have already selected backoff policy");
        this.backOffPolicy = new NoBackOffPolicy();
        return this;
    }

    public RetryTemplateBuilder customBackoff(BackOffPolicy backOffPolicy) {
        Assert.isNull((Object)this.backOffPolicy, (String)"You have already selected backoff policy");
        Assert.notNull((Object)backOffPolicy, (String)"You should provide non null custom policy");
        this.backOffPolicy = backOffPolicy;
        return this;
    }

    public RetryTemplateBuilder retryOn(Class<? extends Throwable> throwable) {
        this.classifierBuilder().retryOn(throwable);
        return this;
    }

    public RetryTemplateBuilder notRetryOn(Class<? extends Throwable> throwable) {
        this.classifierBuilder().notRetryOn(throwable);
        return this;
    }

    public RetryTemplateBuilder retryOn(List<Class<? extends Throwable>> throwables) {
        for (Class<? extends Throwable> throwable : throwables) {
            this.classifierBuilder().retryOn(throwable);
        }
        return this;
    }

    public RetryTemplateBuilder notRetryOn(List<Class<? extends Throwable>> throwables) {
        for (Class<? extends Throwable> throwable : throwables) {
            this.classifierBuilder().notRetryOn(throwable);
        }
        return this;
    }

    public RetryTemplateBuilder traversingCauses() {
        this.classifierBuilder().traversingCauses();
        return this;
    }

    public RetryTemplateBuilder withListener(RetryListener listener) {
        Assert.notNull((Object)listener, (String)"Listener should not be null");
        this.listenersList().add(listener);
        return this;
    }

    public RetryTemplateBuilder withListeners(List<RetryListener> listeners) {
        for (RetryListener listener : listeners) {
            Assert.notNull((Object)listener, (String)"Listener should not be null");
        }
        this.listenersList().addAll(listeners);
        return this;
    }

    public RetryTemplate build() {
        BinaryExceptionClassifier exceptionClassifier;
        RetryTemplate retryTemplate = new RetryTemplate();
        BinaryExceptionClassifier binaryExceptionClassifier = exceptionClassifier = this.classifierBuilder != null ? this.classifierBuilder.build() : BinaryExceptionClassifier.defaultClassifier();
        if (this.baseRetryPolicy == null) {
            this.baseRetryPolicy = new MaxAttemptsRetryPolicy();
        }
        CompositeRetryPolicy finalPolicy = new CompositeRetryPolicy();
        finalPolicy.setPolicies(new RetryPolicy[]{this.baseRetryPolicy, new BinaryExceptionClassifierRetryPolicy(exceptionClassifier)});
        retryTemplate.setRetryPolicy(finalPolicy);
        if (this.backOffPolicy == null) {
            this.backOffPolicy = new NoBackOffPolicy();
        }
        retryTemplate.setBackOffPolicy(this.backOffPolicy);
        if (this.listeners != null) {
            retryTemplate.setListeners(this.listeners.toArray(new RetryListener[0]));
        }
        return retryTemplate;
    }

    private BinaryExceptionClassifierBuilder classifierBuilder() {
        if (this.classifierBuilder == null) {
            this.classifierBuilder = new BinaryExceptionClassifierBuilder();
        }
        return this.classifierBuilder;
    }

    private List<RetryListener> listenersList() {
        if (this.listeners == null) {
            this.listeners = new ArrayList<RetryListener>();
        }
        return this.listeners;
    }
}

