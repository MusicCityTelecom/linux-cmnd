/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.inspektr.audit.spi.AuditActionResolver
 *  org.apereo.inspektr.audit.spi.AuditResourceResolver
 *  org.apereo.inspektr.common.spi.PrincipalResolver
 */
package org.apereo.cas.audit;

import java.util.Arrays;
import java.util.Map;
import org.apereo.inspektr.audit.spi.AuditActionResolver;
import org.apereo.inspektr.audit.spi.AuditResourceResolver;
import org.apereo.inspektr.common.spi.PrincipalResolver;

public interface AuditTrailRecordResolutionPlan {
    default public void registerAuditResourceResolver(AuditResourceResolver resolver, String ... keys) {
        Arrays.stream(keys).forEach(k -> this.registerAuditResourceResolver((String)k, resolver));
    }

    public void registerAuditResourceResolver(String var1, AuditResourceResolver var2);

    public void registerAuditPrincipalResolver(String var1, PrincipalResolver var2);

    public void registerAuditActionResolver(String var1, AuditActionResolver var2);

    default public void registerAuditActionResolvers(AuditActionResolver resolver, String ... keys) {
        Arrays.stream(keys).forEach(k -> this.registerAuditActionResolver((String)k, resolver));
    }

    public void registerAuditActionResolvers(Map<String, AuditActionResolver> var1);

    public void registerAuditResourceResolvers(Map<String, AuditResourceResolver> var1);

    default public void registerAuditResourceResolvers(AuditResourceResolver resolver, String ... keys) {
        Arrays.stream(keys).forEach(k -> this.registerAuditResourceResolver((String)k, resolver));
    }

    public Map<String, AuditResourceResolver> getAuditResourceResolvers();

    public Map<String, AuditActionResolver> getAuditActionResolvers();

    public Map<String, PrincipalResolver> getAuditPrincipalResolvers();
}

