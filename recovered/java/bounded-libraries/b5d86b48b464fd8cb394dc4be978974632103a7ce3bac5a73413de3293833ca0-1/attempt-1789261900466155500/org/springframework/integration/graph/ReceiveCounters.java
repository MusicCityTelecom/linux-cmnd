/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.integration.graph;

public class ReceiveCounters {
    private final long successes;
    private final long failures;

    public ReceiveCounters(long successes, long failures) {
        this.successes = successes;
        this.failures = failures;
    }

    public long getSuccesses() {
        return this.successes;
    }

    public long getFailures() {
        return this.failures;
    }
}

