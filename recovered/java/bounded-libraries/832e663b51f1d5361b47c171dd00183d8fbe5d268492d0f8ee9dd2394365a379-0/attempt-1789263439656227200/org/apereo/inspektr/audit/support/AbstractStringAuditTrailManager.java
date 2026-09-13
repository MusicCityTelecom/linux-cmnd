/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.PrettyPrinter
 *  com.fasterxml.jackson.core.util.MinimalPrettyPrinter
 *  com.fasterxml.jackson.databind.ObjectWriter
 */
package org.apereo.inspektr.audit.support;

import com.fasterxml.jackson.core.PrettyPrinter;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import org.apereo.inspektr.audit.AuditActionContext;
import org.apereo.inspektr.audit.AuditTrailManager;

public abstract class AbstractStringAuditTrailManager
implements AuditTrailManager {
    private AuditTrailManager.AuditFormats auditFormat = AuditTrailManager.AuditFormats.DEFAULT;
    private boolean useSingleLine = false;
    private String entrySeparator = ",";

    public void setUseSingleLine(boolean useSingleLine) {
        this.useSingleLine = useSingleLine;
    }

    @Override
    public void setAuditFormat(AuditTrailManager.AuditFormats auditFormat) {
        this.auditFormat = auditFormat;
    }

    @Override
    public Set<? extends AuditActionContext> getAuditRecords(Map<AuditTrailManager.WhereClauseFields, Object> whereClause) {
        return new HashSet();
    }

    protected String getEntrySeparator() {
        return this.entrySeparator;
    }

    public void setEntrySeparator(String separator) {
        this.entrySeparator = separator;
    }

    protected String toString(AuditActionContext auditActionContext) {
        if (this.auditFormat == AuditTrailManager.AuditFormats.JSON) {
            StringBuilder builder = new StringBuilder();
            try {
                if (this.useSingleLine) {
                    ObjectWriter writer = MAPPER.writer((PrettyPrinter)new MinimalPrettyPrinter());
                    builder.append(writer.writeValueAsString((Object)this.getJsonObjectForAudit(auditActionContext)));
                } else {
                    builder.append(MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString((Object)this.getJsonObjectForAudit(auditActionContext)));
                    builder.append("\n");
                }
            }
            catch (Exception e) {
                throw new IllegalArgumentException(e.getMessage(), e);
            }
            return builder.toString();
        }
        if (this.useSingleLine) {
            return this.getSingleLineAuditString(auditActionContext);
        }
        return this.getMultiLineAuditString(auditActionContext);
    }

    protected String getMultiLineAuditString(AuditActionContext auditActionContext) {
        StringBuilder builder = new StringBuilder();
        builder.append("Audit trail record BEGIN\n");
        builder.append("=============================================================\n");
        builder.append("WHO: ");
        builder.append(auditActionContext.getPrincipal());
        builder.append("\n");
        builder.append("WHAT: ");
        builder.append(auditActionContext.getResourceOperatedUpon());
        builder.append("\n");
        builder.append("ACTION: ");
        builder.append(auditActionContext.getActionPerformed());
        builder.append("\n");
        builder.append("APPLICATION: ");
        builder.append(auditActionContext.getApplicationCode());
        builder.append("\n");
        builder.append("WHEN: ");
        builder.append(auditActionContext.getWhenActionWasPerformed());
        builder.append("\n");
        builder.append("CLIENT IP ADDRESS: ");
        builder.append(auditActionContext.getClientIpAddress());
        builder.append("\n");
        builder.append("SERVER IP ADDRESS: ");
        builder.append(auditActionContext.getServerIpAddress());
        builder.append("\n");
        builder.append("=============================================================");
        builder.append("\n\n");
        return builder.toString();
    }

    protected String getSingleLineAuditString(AuditActionContext auditActionContext) {
        StringBuilder builder = new StringBuilder();
        builder.append(auditActionContext.getWhenActionWasPerformed());
        builder.append(this.getEntrySeparator());
        builder.append(auditActionContext.getApplicationCode());
        builder.append(this.getEntrySeparator());
        builder.append(auditActionContext.getResourceOperatedUpon());
        builder.append(this.getEntrySeparator());
        builder.append(auditActionContext.getActionPerformed());
        builder.append(this.getEntrySeparator());
        builder.append(auditActionContext.getPrincipal());
        builder.append(this.getEntrySeparator());
        builder.append(auditActionContext.getClientIpAddress());
        builder.append(this.getEntrySeparator());
        builder.append(auditActionContext.getServerIpAddress());
        return builder.toString();
    }

    protected Map getJsonObjectForAudit(AuditActionContext auditActionContext) {
        LinkedHashMap<String, String> jsonObject = new LinkedHashMap<String, String>();
        jsonObject.put("who", auditActionContext.getPrincipal());
        jsonObject.put("what", auditActionContext.getResourceOperatedUpon());
        jsonObject.put("action", auditActionContext.getActionPerformed());
        jsonObject.put("application", auditActionContext.getApplicationCode());
        jsonObject.put("when", auditActionContext.getWhenActionWasPerformed().toString());
        jsonObject.put("clientIpAddress", auditActionContext.getClientIpAddress());
        jsonObject.put("serverIpAddress", auditActionContext.getServerIpAddress());
        return jsonObject;
    }
}

