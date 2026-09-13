/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.Lifecycle
 *  org.springframework.integration.endpoint.AbstractEndpoint
 *  org.springframework.integration.support.management.IntegrationManagedResource
 *  org.springframework.jmx.export.annotation.ManagedAttribute
 *  org.springframework.jmx.export.annotation.ManagedOperation
 */
package org.springframework.integration.monitor;

import org.springframework.context.Lifecycle;
import org.springframework.integration.endpoint.AbstractEndpoint;
import org.springframework.integration.support.management.IntegrationManagedResource;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;

@Deprecated
@IntegrationManagedResource
public class ManagedEndpoint
implements Lifecycle {
    private final AbstractEndpoint delegate;

    public ManagedEndpoint(AbstractEndpoint delegate) {
        this.delegate = delegate;
    }

    @ManagedAttribute
    public final boolean isRunning() {
        return this.delegate.isRunning();
    }

    @ManagedOperation
    public final void start() {
        this.delegate.start();
    }

    @ManagedOperation
    public final void stop() {
        this.delegate.stop();
    }
}

