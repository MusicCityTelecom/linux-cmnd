/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.retry;

public interface RetryStatistics {
    public int getCompleteCount();

    public int getStartedCount();

    public int getErrorCount();

    public int getAbortCount();

    public int getRecoveryCount();

    public String getName();
}

