/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.inspektr.audit.AuditActionContext
 *  org.apereo.inspektr.audit.AuditTrailManager
 *  org.apereo.inspektr.audit.AuditTrailManager$WhereClauseFields
 */
package org.apereo.cas.audit;

import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apereo.inspektr.audit.AuditActionContext;
import org.apereo.inspektr.audit.AuditTrailManager;

public interface AuditTrailExecutionPlan {
    public static final String BEAN_NAME = "auditTrailExecutionPlan";

    public void registerAuditTrailManager(AuditTrailManager var1);

    public List<AuditTrailManager> getAuditTrailManagers();

    public void record(AuditActionContext var1);

    public Set<AuditActionContext> getAuditRecords(Map<AuditTrailManager.WhereClauseFields, Object> var1);
}

