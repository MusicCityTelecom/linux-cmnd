/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.integration.graph;

import org.springframework.integration.graph.TimerStats;

public class SendTimers {
    private final TimerStats successes;
    private final TimerStats failures;

    public SendTimers(TimerStats successes, TimerStats failures) {
        this.successes = successes;
        this.failures = failures;
    }

    public TimerStats getSuccesses() {
        return this.successes;
    }

    public TimerStats getFailures() {
        return this.failures;
    }
}

