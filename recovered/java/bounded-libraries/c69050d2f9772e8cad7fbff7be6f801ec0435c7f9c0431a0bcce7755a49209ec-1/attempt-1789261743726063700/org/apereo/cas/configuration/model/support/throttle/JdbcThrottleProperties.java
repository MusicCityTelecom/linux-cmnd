/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.throttle;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Generated;
import org.apereo.cas.configuration.model.support.jpa.AbstractJpaProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-throttle-jdbc")
@JsonFilter(value="JdbcThrottleProperties")
public class JdbcThrottleProperties
extends AbstractJpaProperties {
    public static final String SQL_AUDIT_QUERY_ALL = "SELECT * FROM COM_AUDIT_TRAIL WHERE AUD_ACTION = ? AND APPLIC_CD = ? AND AUD_DATE >= ? ORDER BY AUD_DATE DESC";
    private static final String SQL_AUDIT_QUERY_BY_USER_AND_IP = "SELECT * FROM COM_AUDIT_TRAIL WHERE AUD_CLIENT_IP = ? AND AUD_USER = ? AND AUD_ACTION = ? AND APPLIC_CD = ? AND AUD_DATE >= ? ORDER BY AUD_DATE DESC";
    private static final long serialVersionUID = -9199878384425691919L;
    private boolean enabled = true;
    private String auditQuery = "SELECT * FROM COM_AUDIT_TRAIL WHERE AUD_CLIENT_IP = ? AND AUD_USER = ? AND AUD_ACTION = ? AND APPLIC_CD = ? AND AUD_DATE >= ? ORDER BY AUD_DATE DESC";

    @Generated
    public boolean isEnabled() {
        return this.enabled;
    }

    @Generated
    public String getAuditQuery() {
        return this.auditQuery;
    }

    @Generated
    public JdbcThrottleProperties setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    @Generated
    public JdbcThrottleProperties setAuditQuery(String auditQuery) {
        this.auditQuery = auditQuery;
        return this;
    }
}

