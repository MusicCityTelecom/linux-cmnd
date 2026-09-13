/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jmx.export.annotation.ManagedAttribute
 *  org.springframework.jmx.export.annotation.ManagedOperation
 */
package org.springframework.integration.core;

import org.springframework.integration.support.management.ManageableLifecycle;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;

public interface Pausable
extends ManageableLifecycle {
    @ManagedOperation(description="Pause the component")
    public void pause();

    @ManagedOperation(description="Resume the component")
    public void resume();

    @ManagedAttribute(description="Is the component paused?")
    default public boolean isPaused() {
        throw new UnsupportedOperationException("This component does not implement this method");
    }
}

