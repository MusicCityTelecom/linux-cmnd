/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.retry.support;

import java.util.ArrayList;
import java.util.List;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryPolicy;
import org.springframework.retry.backoff.BackOffPolicy;
import org.springframework.retry.backoff.Sleeper;
import org.springframework.retry.backoff.SleepingBackOffPolicy;
import org.springframework.retry.support.RetrySimulation;
import org.springframework.retry.support.RetryTemplate;

public class RetrySimulator {
    private final SleepingBackOffPolicy<?> backOffPolicy;
    private final RetryPolicy retryPolicy;

    public RetrySimulator(SleepingBackOffPolicy<?> backOffPolicy, RetryPolicy retryPolicy) {
        this.backOffPolicy = backOffPolicy;
        this.retryPolicy = retryPolicy;
    }

    public RetrySimulation executeSimulation(int numSimulations) {
        RetrySimulation simulation = new RetrySimulation();
        for (int i = 0; i < numSimulations; ++i) {
            simulation.addSequence(this.executeSingleSimulation());
        }
        return simulation;
    }

    public List<Long> executeSingleSimulation() {
        StealingSleeper stealingSleeper = new StealingSleeper();
        Object stealingBackoff = this.backOffPolicy.withSleeper(stealingSleeper);
        RetryTemplate template = new RetryTemplate();
        template.setBackOffPolicy((BackOffPolicy)stealingBackoff);
        template.setRetryPolicy(this.retryPolicy);
        try {
            template.execute(new FailingRetryCallback());
        }
        catch (FailingRetryException failingRetryException) {
        }
        catch (Throwable e) {
            throw new RuntimeException("Unexpected exception", e);
        }
        return stealingSleeper.getSleeps();
    }

    static class StealingSleeper
    implements Sleeper {
        private final List<Long> sleeps = new ArrayList<Long>();

        StealingSleeper() {
        }

        @Override
        public void sleep(long backOffPeriod) throws InterruptedException {
            this.sleeps.add(backOffPeriod);
        }

        public List<Long> getSleeps() {
            return this.sleeps;
        }
    }

    static class FailingRetryException
    extends Exception {
        FailingRetryException() {
        }
    }

    static class FailingRetryCallback
    implements RetryCallback<Object, Exception> {
        FailingRetryCallback() {
        }

        @Override
        public Object doWithRetry(RetryContext context) throws Exception {
            throw new FailingRetryException();
        }
    }
}

