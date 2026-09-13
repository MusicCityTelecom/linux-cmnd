/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.AttributeAccessorSupport
 */
package org.springframework.retry.stats;

import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.core.AttributeAccessorSupport;
import org.springframework.retry.RetryStatistics;
import org.springframework.retry.stats.MutableRetryStatistics;

public class DefaultRetryStatistics
extends AttributeAccessorSupport
implements RetryStatistics,
MutableRetryStatistics {
    private String name;
    private AtomicInteger startedCount = new AtomicInteger();
    private AtomicInteger completeCount = new AtomicInteger();
    private AtomicInteger recoveryCount = new AtomicInteger();
    private AtomicInteger errorCount = new AtomicInteger();
    private AtomicInteger abortCount = new AtomicInteger();

    DefaultRetryStatistics() {
    }

    public DefaultRetryStatistics(String name) {
        this.name = name;
    }

    @Override
    public int getCompleteCount() {
        return this.completeCount.get();
    }

    @Override
    public int getStartedCount() {
        return this.startedCount.get();
    }

    @Override
    public int getErrorCount() {
        return this.errorCount.get();
    }

    @Override
    public int getAbortCount() {
        return this.abortCount.get();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getRecoveryCount() {
        return this.recoveryCount.get();
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void incrementStartedCount() {
        this.startedCount.incrementAndGet();
    }

    @Override
    public void incrementCompleteCount() {
        this.completeCount.incrementAndGet();
    }

    @Override
    public void incrementRecoveryCount() {
        this.recoveryCount.incrementAndGet();
    }

    @Override
    public void incrementErrorCount() {
        this.errorCount.incrementAndGet();
    }

    @Override
    public void incrementAbortCount() {
        this.abortCount.incrementAndGet();
    }

    public String toString() {
        return "DefaultRetryStatistics [name=" + this.name + ", startedCount=" + this.startedCount + ", completeCount=" + this.completeCount + ", recoveryCount=" + this.recoveryCount + ", errorCount=" + this.errorCount + ", abortCount=" + this.abortCount + "]";
    }
}

