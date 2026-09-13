/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonCreator
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package org.apereo.inspektr.audit;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.Date;

public class AuditActionContext
implements Serializable {
    private static final long serialVersionUID = -3530737409883959089L;
    @JsonProperty
    private final String principal;
    @JsonProperty
    private final String resourceOperatedUpon;
    @JsonProperty
    private final String actionPerformed;
    @JsonProperty
    private final String applicationCode;
    @JsonProperty
    private final Date whenActionWasPerformed;
    @JsonProperty
    private final String clientIpAddress;
    @JsonProperty
    private final String serverIpAddress;
    @JsonProperty
    private final String userAgent;

    @JsonCreator
    public AuditActionContext(@JsonProperty(value="principal") String principal, @JsonProperty(value="resourceOperatedUpon") String resourceOperatedUpon, @JsonProperty(value="actionPerformed") String actionPerformed, @JsonProperty(value="applicationCode") String applicationCode, @JsonProperty(value="whenActionWasPerformed") Date whenActionWasPerformed, @JsonProperty(value="clientIpAddress") String clientIpAddress, @JsonProperty(value="serverIpAddress") String serverIpAddress, @JsonProperty(value="userAgent") String userAgent) {
        this.principal = principal;
        this.resourceOperatedUpon = resourceOperatedUpon;
        this.actionPerformed = actionPerformed;
        this.applicationCode = applicationCode;
        this.whenActionWasPerformed = new Date(whenActionWasPerformed.getTime());
        this.clientIpAddress = clientIpAddress;
        this.serverIpAddress = serverIpAddress;
        this.userAgent = userAgent;
    }

    public String getPrincipal() {
        return this.principal;
    }

    public String getResourceOperatedUpon() {
        return this.resourceOperatedUpon;
    }

    public String getActionPerformed() {
        return this.actionPerformed;
    }

    public String getApplicationCode() {
        return this.applicationCode;
    }

    public Date getWhenActionWasPerformed() {
        return new Date(this.whenActionWasPerformed.getTime());
    }

    public String getClientIpAddress() {
        return this.clientIpAddress;
    }

    public String getServerIpAddress() {
        return this.serverIpAddress;
    }

    public String getUserAgent() {
        return this.userAgent;
    }
}

