/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jmx.export.annotation.ManagedOperation
 *  org.springframework.jmx.export.annotation.ManagedResource
 */
package org.springframework.integration.scheduling;

import org.springframework.integration.scheduling.PollSkipStrategy;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;

@ManagedResource
public class SimplePollSkipStrategy
implements PollSkipStrategy {
    private volatile boolean skip;

    @Override
    public boolean skipPoll() {
        return this.skip;
    }

    @ManagedOperation
    public void skipPolls() {
        this.skip = true;
    }

    @ManagedOperation
    public void reset() {
        this.skip = false;
    }
}

