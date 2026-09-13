/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jmx.export.annotation.ManagedAttribute
 *  org.springframework.jmx.export.annotation.ManagedOperation
 */
package org.springframework.integration.handler;

import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;

public interface DelayHandlerManagement {
    @ManagedAttribute
    public int getDelayedMessageCount();

    @ManagedOperation
    public void reschedulePersistedMessages();
}

