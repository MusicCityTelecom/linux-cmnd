/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.audit;

import org.apereo.cas.audit.AuditTrailRecordResolutionPlan;

@FunctionalInterface
public interface AuditTrailRecordResolutionPlanConfigurer {
    public void configureAuditTrailRecordResolutionPlan(AuditTrailRecordResolutionPlan var1);

    default public String getName() {
        return this.getClass().getSimpleName();
    }
}

