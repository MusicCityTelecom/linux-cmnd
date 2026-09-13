/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.aopalliance.intercept.MethodInterceptor
 *  org.aopalliance.intercept.MethodInvocation
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 */
package org.springframework.integration.scheduling;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.integration.scheduling.PollSkipStrategy;

public class PollSkipAdvice
implements MethodInterceptor {
    private static final Log LOGGER = LogFactory.getLog(PollSkipAdvice.class);
    private final PollSkipStrategy pollSkipStrategy;

    public PollSkipAdvice() {
        this(new DefaultPollSkipStrategy());
    }

    public PollSkipAdvice(PollSkipStrategy strategy) {
        this.pollSkipStrategy = strategy;
    }

    public Object invoke(MethodInvocation invocation) throws Throwable {
        if ("call".equals(invocation.getMethod().getName()) && this.pollSkipStrategy.skipPoll()) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug((Object)("Skipping poll because " + this.pollSkipStrategy.getClass().getName() + ".skipPoll() returned true"));
            }
            return null;
        }
        return invocation.proceed();
    }

    private static final class DefaultPollSkipStrategy
    implements PollSkipStrategy {
        DefaultPollSkipStrategy() {
        }

        @Override
        public boolean skipPoll() {
            return false;
        }
    }
}

