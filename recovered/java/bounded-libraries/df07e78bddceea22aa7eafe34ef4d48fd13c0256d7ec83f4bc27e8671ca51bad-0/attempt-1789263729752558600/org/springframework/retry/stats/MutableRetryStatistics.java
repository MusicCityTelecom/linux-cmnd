/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.AttributeAccessor
 */
package org.springframework.retry.stats;

import org.springframework.core.AttributeAccessor;
import org.springframework.retry.RetryStatistics;

public interface MutableRetryStatistics
extends RetryStatistics,
AttributeAccessor {
    public void incrementStartedCount();

    public void incrementCompleteCount();

    public void incrementRecoveryCount();

    public void incrementErrorCount();

    public void incrementAbortCount();
}

