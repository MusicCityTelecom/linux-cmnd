/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.Lifecycle
 *  org.springframework.jmx.export.annotation.ManagedAttribute
 *  org.springframework.jmx.export.annotation.ManagedOperation
 */
package org.springframework.integration.support.management;

import org.springframework.context.Lifecycle;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;

public interface ManageableLifecycle
extends Lifecycle {
    @ManagedOperation(description="Start the component")
    public void start();

    @ManagedOperation(description="Stop the component")
    public void stop();

    @ManagedAttribute(description="Is the component running?")
    public boolean isRunning();
}

