/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.aopalliance.intercept.MethodInterceptor
 *  org.springframework.util.Assert
 */
package org.springframework.retry.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.classify.BinaryExceptionClassifier;
import org.springframework.classify.Classifier;
import org.springframework.retry.RetryOperations;
import org.springframework.retry.RetryPolicy;
import org.springframework.retry.backoff.BackOffPolicy;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.interceptor.MethodArgumentsKeyGenerator;
import org.springframework.retry.interceptor.MethodInvocationRecoverer;
import org.springframework.retry.interceptor.NewMethodArgumentsIdentifier;
import org.springframework.retry.interceptor.RetryOperationsInterceptor;
import org.springframework.retry.interceptor.StatefulRetryOperationsInterceptor;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.util.Assert;

public abstract class RetryInterceptorBuilder<T extends MethodInterceptor> {
    protected final RetryTemplate retryTemplate = new RetryTemplate();
    protected final SimpleRetryPolicy simpleRetryPolicy = new SimpleRetryPolicy();
    protected RetryOperations retryOperations;
    protected MethodInvocationRecoverer<?> recoverer;
    private boolean templateAltered;
    private boolean backOffPolicySet;
    private boolean retryPolicySet;
    private boolean backOffOptionsSet;
    protected String label;

    public static StatefulRetryInterceptorBuilder stateful() {
        return new StatefulRetryInterceptorBuilder();
    }

    public static CircuitBreakerInterceptorBuilder circuitBreaker() {
        return new CircuitBreakerInterceptorBuilder();
    }

    public static StatelessRetryInterceptorBuilder stateless() {
        return new StatelessRetryInterceptorBuilder();
    }

    public RetryInterceptorBuilder<T> retryOperations(RetryOperations retryOperations) {
        Assert.isTrue((!this.templateAltered ? 1 : 0) != 0, (String)"Cannot set retryOperations when the default has been modified");
        this.retryOperations = retryOperations;
        return this;
    }

    public RetryInterceptorBuilder<T> maxAttempts(int maxAttempts) {
        Assert.isNull((Object)this.retryOperations, (String)"cannot alter the retry policy when a custom retryOperations has been set");
        Assert.isTrue((!this.retryPolicySet ? 1 : 0) != 0, (String)"cannot alter the retry policy when a custom retryPolicy has been set");
        this.simpleRetryPolicy.setMaxAttempts(maxAttempts);
        this.retryTemplate.setRetryPolicy(this.simpleRetryPolicy);
        this.templateAltered = true;
        return this;
    }

    public RetryInterceptorBuilder<T> backOffOptions(long initialInterval, double multiplier, long maxInterval) {
        Assert.isNull((Object)this.retryOperations, (String)"cannot set the back off policy when a custom retryOperations has been set");
        Assert.isTrue((!this.backOffPolicySet ? 1 : 0) != 0, (String)"cannot set the back off options when a back off policy has been set");
        ExponentialBackOffPolicy policy = new ExponentialBackOffPolicy();
        policy.setInitialInterval(initialInterval);
        policy.setMultiplier(multiplier);
        policy.setMaxInterval(maxInterval);
        this.retryTemplate.setBackOffPolicy(policy);
        this.backOffOptionsSet = true;
        this.templateAltered = true;
        return this;
    }

    public RetryInterceptorBuilder<T> retryPolicy(RetryPolicy policy) {
        Assert.isNull((Object)this.retryOperations, (String)"cannot set the retry policy when a custom retryOperations has been set");
        Assert.isTrue((!this.templateAltered ? 1 : 0) != 0, (String)"cannot set the retry policy if max attempts or back off policy or options changed");
        this.retryTemplate.setRetryPolicy(policy);
        this.retryPolicySet = true;
        this.templateAltered = true;
        return this;
    }

    public RetryInterceptorBuilder<T> backOffPolicy(BackOffPolicy policy) {
        Assert.isNull((Object)this.retryOperations, (String)"cannot set the back off policy when a custom retryOperations has been set");
        Assert.isTrue((!this.backOffOptionsSet ? 1 : 0) != 0, (String)"cannot set the back off policy when the back off policy options have been set");
        this.retryTemplate.setBackOffPolicy(policy);
        this.templateAltered = true;
        this.backOffPolicySet = true;
        return this;
    }

    public RetryInterceptorBuilder<T> recoverer(MethodInvocationRecoverer<?> recoverer) {
        this.recoverer = recoverer;
        return this;
    }

    public RetryInterceptorBuilder<T> label(String label) {
        this.label = label;
        return this;
    }

    public abstract T build();

    private RetryInterceptorBuilder() {
    }

    public static class StatelessRetryInterceptorBuilder
    extends RetryInterceptorBuilder<RetryOperationsInterceptor> {
        private final RetryOperationsInterceptor interceptor = new RetryOperationsInterceptor();

        @Override
        public RetryOperationsInterceptor build() {
            if (this.recoverer != null) {
                this.interceptor.setRecoverer(this.recoverer);
            }
            if (this.retryOperations != null) {
                this.interceptor.setRetryOperations(this.retryOperations);
            } else {
                this.interceptor.setRetryOperations(this.retryTemplate);
            }
            if (this.label != null) {
                this.interceptor.setLabel(this.label);
            }
            return this.interceptor;
        }

        private StatelessRetryInterceptorBuilder() {
        }
    }

    public static class CircuitBreakerInterceptorBuilder
    extends RetryInterceptorBuilder<StatefulRetryOperationsInterceptor> {
        private final StatefulRetryOperationsInterceptor interceptor = new StatefulRetryOperationsInterceptor();
        private MethodArgumentsKeyGenerator keyGenerator;

        public CircuitBreakerInterceptorBuilder retryOperations(RetryOperations retryOperations) {
            super.retryOperations(retryOperations);
            return this;
        }

        public CircuitBreakerInterceptorBuilder maxAttempts(int maxAttempts) {
            super.maxAttempts(maxAttempts);
            return this;
        }

        public CircuitBreakerInterceptorBuilder retryPolicy(RetryPolicy policy) {
            super.retryPolicy(policy);
            return this;
        }

        public CircuitBreakerInterceptorBuilder keyGenerator(MethodArgumentsKeyGenerator keyGenerator) {
            this.keyGenerator = keyGenerator;
            return this;
        }

        public CircuitBreakerInterceptorBuilder recoverer(MethodInvocationRecoverer<?> recoverer) {
            super.recoverer(recoverer);
            return this;
        }

        @Override
        public StatefulRetryOperationsInterceptor build() {
            if (this.recoverer != null) {
                this.interceptor.setRecoverer(this.recoverer);
            }
            if (this.retryOperations != null) {
                this.interceptor.setRetryOperations(this.retryOperations);
            } else {
                this.interceptor.setRetryOperations(this.retryTemplate);
            }
            if (this.keyGenerator != null) {
                this.interceptor.setKeyGenerator(this.keyGenerator);
            }
            if (this.label != null) {
                this.interceptor.setLabel(this.label);
            }
            this.interceptor.setRollbackClassifier(new BinaryExceptionClassifier(false));
            return this.interceptor;
        }

        private CircuitBreakerInterceptorBuilder() {
        }
    }

    public static class StatefulRetryInterceptorBuilder
    extends RetryInterceptorBuilder<StatefulRetryOperationsInterceptor> {
        private final StatefulRetryOperationsInterceptor interceptor = new StatefulRetryOperationsInterceptor();
        private MethodArgumentsKeyGenerator keyGenerator;
        private NewMethodArgumentsIdentifier newMethodArgumentsIdentifier;
        private Classifier<? super Throwable, Boolean> rollbackClassifier;

        public StatefulRetryInterceptorBuilder keyGenerator(MethodArgumentsKeyGenerator keyGenerator) {
            this.keyGenerator = keyGenerator;
            return this;
        }

        public StatefulRetryInterceptorBuilder newMethodArgumentsIdentifier(NewMethodArgumentsIdentifier newMethodArgumentsIdentifier) {
            this.newMethodArgumentsIdentifier = newMethodArgumentsIdentifier;
            return this;
        }

        public StatefulRetryInterceptorBuilder rollbackFor(Classifier<? super Throwable, Boolean> rollbackClassifier) {
            this.rollbackClassifier = rollbackClassifier;
            return this;
        }

        public StatefulRetryInterceptorBuilder retryOperations(RetryOperations retryOperations) {
            super.retryOperations(retryOperations);
            return this;
        }

        public StatefulRetryInterceptorBuilder maxAttempts(int maxAttempts) {
            super.maxAttempts(maxAttempts);
            return this;
        }

        public StatefulRetryInterceptorBuilder backOffOptions(long initialInterval, double multiplier, long maxInterval) {
            super.backOffOptions(initialInterval, multiplier, maxInterval);
            return this;
        }

        public StatefulRetryInterceptorBuilder retryPolicy(RetryPolicy policy) {
            super.retryPolicy(policy);
            return this;
        }

        public StatefulRetryInterceptorBuilder backOffPolicy(BackOffPolicy policy) {
            super.backOffPolicy(policy);
            return this;
        }

        public StatefulRetryInterceptorBuilder recoverer(MethodInvocationRecoverer<?> recoverer) {
            super.recoverer(recoverer);
            return this;
        }

        @Override
        public StatefulRetryOperationsInterceptor build() {
            if (this.recoverer != null) {
                this.interceptor.setRecoverer(this.recoverer);
            }
            if (this.retryOperations != null) {
                this.interceptor.setRetryOperations(this.retryOperations);
            } else {
                this.interceptor.setRetryOperations(this.retryTemplate);
            }
            if (this.keyGenerator != null) {
                this.interceptor.setKeyGenerator(this.keyGenerator);
            }
            if (this.rollbackClassifier != null) {
                this.interceptor.setRollbackClassifier(this.rollbackClassifier);
            }
            if (this.newMethodArgumentsIdentifier != null) {
                this.interceptor.setNewItemIdentifier(this.newMethodArgumentsIdentifier);
            }
            if (this.label != null) {
                this.interceptor.setLabel(this.label);
            }
            return this.interceptor;
        }

        private StatefulRetryInterceptorBuilder() {
        }
    }
}

