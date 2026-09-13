/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jmx.export.annotation.ManagedAttribute
 *  org.springframework.jmx.export.annotation.ManagedOperation
 *  org.springframework.jmx.export.annotation.ManagedResource
 */
package org.springframework.integration.router;

import java.util.Collection;
import java.util.Map;
import java.util.Properties;
import org.springframework.integration.support.management.IntegrationManagedResource;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;

@ManagedResource
@IntegrationManagedResource
public interface RecipientListRouterManagement {
    @ManagedOperation
    public void addRecipient(String var1, String var2);

    @ManagedOperation
    public void addRecipient(String var1);

    @ManagedOperation
    public int removeRecipient(String var1);

    @ManagedOperation
    public int removeRecipient(String var1, String var2);

    @ManagedAttribute
    public Collection<?> getRecipients();

    @ManagedOperation
    public void replaceRecipients(Properties var1);

    @ManagedAttribute
    public void setRecipientMappings(Map<String, String> var1);
}

