/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.audit;

import org.apereo.cas.audit.AuditTrailExecutionPlan;

@FunctionalInterface
public interface AuditTrailExecutionPlanConfigurer {
    public void configureAuditTrailExecutionPlan(AuditTrailExecutionPlan var1);

    default public String getName() {
        return this.getClass().getSimpleName();
    }
}

