/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jmx.export.annotation.ManagedAttribute
 */
package org.springframework.integration.support.management;

import org.springframework.integration.support.management.IntegrationManagedResource;
import org.springframework.jmx.export.annotation.ManagedAttribute;

@IntegrationManagedResource
public interface MessageSourceManagement {
    @ManagedAttribute(description="Maximum objects to fetch")
    public void setMaxFetchSize(int var1);

    @ManagedAttribute
    public int getMaxFetchSize();
}

