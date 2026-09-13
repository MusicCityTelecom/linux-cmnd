/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jmx.export.annotation.ManagedAttribute
 *  org.springframework.jmx.export.annotation.ManagedOperation
 */
package org.springframework.integration.support.management;

import java.util.Collection;
import java.util.Map;
import java.util.Properties;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;

public interface MappingMessageRouterManagement {
    @ManagedOperation
    public void setChannelMapping(String var1, String var2);

    @ManagedOperation
    public void removeChannelMapping(String var1);

    @ManagedOperation
    public void replaceChannelMappings(Properties var1);

    @ManagedAttribute
    public Map<String, String> getChannelMappings();

    @ManagedAttribute
    public void setChannelMappings(Map<String, String> var1);

    @ManagedAttribute
    public Collection<String> getDynamicChannelNames();
}

