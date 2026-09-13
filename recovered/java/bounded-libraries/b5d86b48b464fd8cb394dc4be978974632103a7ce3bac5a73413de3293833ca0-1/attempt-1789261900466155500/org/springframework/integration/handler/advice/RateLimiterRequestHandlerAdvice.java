/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.github.resilience4j.ratelimiter.RateLimiter
 *  io.github.resilience4j.ratelimiter.RateLimiter$Metrics
 *  io.github.resilience4j.ratelimiter.RateLimiterConfig
 *  io.github.resilience4j.ratelimiter.RequestNotPermitted
 *  io.vavr.CheckedFunction0
 *  io.vavr.control.Try
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessagingException
 *  org.springframework.util.Assert
 */
package org.springframework.integration.handler.advice;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import io.vavr.CheckedFunction0;
import io.vavr.control.Try;
import java.time.Duration;
import org.springframework.integration.handler.advice.AbstractRequestHandlerAdvice;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;
import org.springframework.util.Assert;

public class RateLimiterRequestHandlerAdvice
extends AbstractRequestHandlerAdvice {
    public static final String DEFAULT_NAME = "RateLimiterRequestHandlerAdvice";
    private final RateLimiter rateLimiter;

    public RateLimiterRequestHandlerAdvice() {
        this(RateLimiter.ofDefaults((String)DEFAULT_NAME));
    }

    public RateLimiterRequestHandlerAdvice(String name) {
        this(RateLimiter.ofDefaults((String)name));
        Assert.hasText((String)name, (String)"'name' must not be empty");
    }

    public RateLimiterRequestHandlerAdvice(RateLimiter rateLimiter) {
        Assert.notNull((Object)rateLimiter, (String)"'rateLimiter' must not be null");
        this.rateLimiter = rateLimiter;
    }

    public RateLimiterRequestHandlerAdvice(RateLimiterConfig rateLimiterConfig) {
        this(rateLimiterConfig, DEFAULT_NAME);
    }

    public RateLimiterRequestHandlerAdvice(RateLimiterConfig rateLimiterConfig, String name) {
        Assert.notNull((Object)rateLimiterConfig, (String)"'rateLimiterConfig' must not be null");
        Assert.hasText((String)name, (String)"'name' must not be empty");
        this.rateLimiter = RateLimiter.of((String)name, (RateLimiterConfig)rateLimiterConfig);
    }

    public void setLimitForPeriod(int limitForPeriod) {
        this.rateLimiter.changeLimitForPeriod(limitForPeriod);
    }

    public void setTimeoutDuration(Duration timeoutDuration) {
        this.rateLimiter.changeTimeoutDuration(timeoutDuration);
    }

    public RateLimiter.Metrics getMetrics() {
        return this.rateLimiter.getMetrics();
    }

    public RateLimiter getRateLimiter() {
        return this.rateLimiter;
    }

    @Override
    protected Object doInvoke(AbstractRequestHandlerAdvice.ExecutionCallback callback, Object target, Message<?> message) {
        CheckedFunction0 restrictedCall = RateLimiter.decorateCheckedSupplier((RateLimiter)this.rateLimiter, callback::execute);
        try {
            return Try.of((CheckedFunction0)restrictedCall).get();
        }
        catch (RequestNotPermitted ex) {
            throw new RateLimitExceededException(message, "Rate limit exceeded for: " + target, ex);
        }
    }

    public static class RateLimitExceededException
    extends MessagingException {
        private static final long serialVersionUID = 1L;

        RateLimitExceededException(Message<?> message, String description, RequestNotPermitted cause) {
            super(message, description, (Throwable)cause);
        }
    }
}

