/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessagingException
 */
package org.springframework.integration.handler.advice;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.integration.handler.advice.AbstractRequestHandlerAdvice;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;

public class RequestHandlerCircuitBreakerAdvice
extends AbstractRequestHandlerAdvice {
    public static final int DEFAULT_THRESHOLD = 5;
    public static final int DEFAULT_HALF_OPEN_AFTER = 1000;
    private int threshold = 5;
    private long halfOpenAfter = 1000L;
    private final ConcurrentMap<Object, AdvisedMetadata> metadataMap = new ConcurrentHashMap<Object, AdvisedMetadata>();

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    public void setHalfOpenAfter(long halfOpenAfter) {
        this.halfOpenAfter = halfOpenAfter;
    }

    @Override
    protected Object doInvoke(AbstractRequestHandlerAdvice.ExecutionCallback callback, Object target, Message<?> message) {
        AdvisedMetadata metadata = (AdvisedMetadata)this.metadataMap.get(target);
        if (metadata == null) {
            this.metadataMap.putIfAbsent(target, new AdvisedMetadata());
            metadata = (AdvisedMetadata)this.metadataMap.get(target);
        }
        if (metadata.getFailures().get() >= this.threshold && System.currentTimeMillis() - metadata.getLastFailure() < this.halfOpenAfter) {
            throw new CircuitBreakerOpenException(message, "Circuit Breaker is Open for " + target);
        }
        try {
            Object result = callback.execute();
            if (metadata.getFailures().get() > 0) {
                this.logger.debug(() -> "Closing Circuit Breaker for " + target);
            }
            metadata.getFailures().set(0);
            return result;
        }
        catch (Exception e) {
            metadata.getFailures().incrementAndGet();
            metadata.setLastFailure(System.currentTimeMillis());
            if (e instanceof AbstractRequestHandlerAdvice.ThrowableHolderException) {
                throw (AbstractRequestHandlerAdvice.ThrowableHolderException)e;
            }
            throw new AbstractRequestHandlerAdvice.ThrowableHolderException(e);
        }
    }

    private static class AdvisedMetadata {
        private final AtomicInteger failures = new AtomicInteger();
        private volatile long lastFailure;

        AdvisedMetadata() {
        }

        private long getLastFailure() {
            return this.lastFailure;
        }

        private void setLastFailure(long lastFailure) {
            this.lastFailure = lastFailure;
        }

        private AtomicInteger getFailures() {
            return this.failures;
        }
    }

    public static final class CircuitBreakerOpenException
    extends MessagingException {
        private static final long serialVersionUID = 1L;

        public CircuitBreakerOpenException(Message<?> message, String description) {
            super(message, description);
        }
    }
}

