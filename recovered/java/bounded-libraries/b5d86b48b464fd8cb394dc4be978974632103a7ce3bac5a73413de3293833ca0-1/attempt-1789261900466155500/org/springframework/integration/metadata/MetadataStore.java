/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jmx.export.annotation.ManagedAttribute
 *  org.springframework.jmx.export.annotation.ManagedResource
 */
package org.springframework.integration.metadata;

import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;

@ManagedResource
public interface MetadataStore {
    public void put(String var1, String var2);

    @ManagedAttribute
    public String get(String var1);

    @ManagedAttribute
    public String remove(String var1);
}

