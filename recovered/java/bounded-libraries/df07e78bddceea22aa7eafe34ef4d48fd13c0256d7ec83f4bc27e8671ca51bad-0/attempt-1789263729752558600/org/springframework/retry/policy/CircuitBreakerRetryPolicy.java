/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 */
package org.springframework.retry.policy;

import java.util.concurrent.atomic.AtomicInteger;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryPolicy;
import org.springframework.retry.context.RetryContextSupport;
import org.springframework.retry.policy.SimpleRetryPolicy;

public class CircuitBreakerRetryPolicy
implements RetryPolicy {
    public static final String CIRCUIT_OPEN = "circuit.open";
    public static final String CIRCUIT_SHORT_COUNT = "circuit.shortCount";
    private static Log logger = LogFactory.getLog(CircuitBreakerRetryPolicy.class);
    private final RetryPolicy delegate;
    private long resetTimeout = 20000L;
    private long openTimeout = 5000L;

    public CircuitBreakerRetryPolicy() {
        this(new SimpleRetryPolicy());
    }

    public CircuitBreakerRetryPolicy(RetryPolicy delegate) {
        this.delegate = delegate;
    }

    public void setResetTimeout(long timeout) {
        this.resetTimeout = timeout;
    }

    public void setOpenTimeout(long timeout) {
        this.openTimeout = timeout;
    }

    @Override
    public boolean canRetry(RetryContext context) {
        CircuitBreakerRetryContext circuit = (CircuitBreakerRetryContext)context;
        if (circuit.isOpen()) {
            circuit.incrementShortCircuitCount();
            return false;
        }
        circuit.reset();
        return this.delegate.canRetry(circuit.context);
    }

    @Override
    public RetryContext open(RetryContext parent) {
        return new CircuitBreakerRetryContext(parent, this.delegate, this.resetTimeout, this.openTimeout);
    }

    @Override
    public void close(RetryContext context) {
        CircuitBreakerRetryContext circuit = (CircuitBreakerRetryContext)context;
        this.delegate.close(circuit.context);
    }

    @Override
    public void registerThrowable(RetryContext context, Throwable throwable) {
        CircuitBreakerRetryContext circuit = (CircuitBreakerRetryContext)context;
        circuit.registerThrowable(throwable);
        this.delegate.registerThrowable(circuit.context, throwable);
    }

    static class CircuitBreakerRetryContext
    extends RetryContextSupport {
        private volatile RetryContext context;
        private final RetryPolicy policy;
        private volatile long start = System.currentTimeMillis();
        private final long timeout;
        private final long openWindow;
        private final AtomicInteger shortCircuitCount = new AtomicInteger();

        public CircuitBreakerRetryContext(RetryContext parent, RetryPolicy policy, long timeout, long openWindow) {
            super(parent);
            this.policy = policy;
            this.timeout = timeout;
            this.openWindow = openWindow;
            this.context = this.createDelegateContext(policy, parent);
            this.setAttribute("state.global", true);
        }

        public void reset() {
            this.shortCircuitCount.set(0);
            this.setAttribute(CircuitBreakerRetryPolicy.CIRCUIT_SHORT_COUNT, this.shortCircuitCount.get());
        }

        public void incrementShortCircuitCount() {
            this.shortCircuitCount.incrementAndGet();
            this.setAttribute(CircuitBreakerRetryPolicy.CIRCUIT_SHORT_COUNT, this.shortCircuitCount.get());
        }

        private RetryContext createDelegateContext(RetryPolicy policy, RetryContext parent) {
            RetryContext context = policy.open(parent);
            this.reset();
            return context;
        }

        public boolean isOpen() {
            long time = System.currentTimeMillis() - this.start;
            boolean retryable = this.policy.canRetry(this.context);
            if (!retryable) {
                if (time > this.timeout) {
                    logger.trace((Object)"Closing");
                    this.context = this.createDelegateContext(this.policy, this.getParent());
                    this.start = System.currentTimeMillis();
                    retryable = this.policy.canRetry(this.context);
                } else if (time < this.openWindow) {
                    if (!this.hasAttribute(CircuitBreakerRetryPolicy.CIRCUIT_OPEN) || !((Boolean)this.getAttribute(CircuitBreakerRetryPolicy.CIRCUIT_OPEN)).booleanValue()) {
                        logger.trace((Object)"Opening circuit");
                        this.setAttribute(CircuitBreakerRetryPolicy.CIRCUIT_OPEN, true);
                        this.start = System.currentTimeMillis();
                    }
                    return true;
                }
            } else if (time > this.openWindow) {
                logger.trace((Object)"Resetting context");
                this.start = System.currentTimeMillis();
                this.context = this.createDelegateContext(this.policy, this.getParent());
            }
            if (logger.isTraceEnabled()) {
                logger.trace((Object)("Open: " + !retryable));
            }
            this.setAttribute(CircuitBreakerRetryPolicy.CIRCUIT_OPEN, !retryable);
            return !retryable;
        }

        @Override
        public int getRetryCount() {
            return this.context.getRetryCount();
        }

        @Override
        public String toString() {
            return this.context.toString();
        }
    }
}

