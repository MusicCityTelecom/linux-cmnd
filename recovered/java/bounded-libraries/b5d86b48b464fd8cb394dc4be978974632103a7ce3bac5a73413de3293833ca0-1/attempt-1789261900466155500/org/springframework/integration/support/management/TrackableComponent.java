/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jmx.export.annotation.ManagedOperation
 */
package org.springframework.integration.support.management;

import org.springframework.integration.support.context.NamedComponent;
import org.springframework.integration.support.management.IntegrationManagedResource;
import org.springframework.jmx.export.annotation.ManagedOperation;

@IntegrationManagedResource
public interface TrackableComponent
extends NamedComponent {
    @ManagedOperation
    public void setShouldTrack(boolean var1);
}

