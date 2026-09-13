/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.pac4j;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.model.support.delegation.DelegationAutoRedirectTypes;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-pac4j-webflow")
@JsonFilter(value="Pac4jBaseClientProperties")
public class Pac4jBaseClientProperties
implements Serializable {
    private static final long serialVersionUID = -7885975876831784206L;
    private String clientName;
    private DelegationAutoRedirectTypes autoRedirectType = DelegationAutoRedirectTypes.NONE;
    private String principalAttributeId;
    private boolean enabled = true;
    private String cssClass;
    private String displayName;
    private CallbackUrlTypes callbackUrlType = CallbackUrlTypes.QUERY_PARAMETER;
    private String callbackUrl;

    @Generated
    public String getClientName() {
        return this.clientName;
    }

    @Generated
    public DelegationAutoRedirectTypes getAutoRedirectType() {
        return this.autoRedirectType;
    }

    @Generated
    public String getPrincipalAttributeId() {
        return this.principalAttributeId;
    }

    @Generated
    public boolean isEnabled() {
        return this.enabled;
    }

    @Generated
    public String getCssClass() {
        return this.cssClass;
    }

    @Generated
    public String getDisplayName() {
        return this.displayName;
    }

    @Generated
    public CallbackUrlTypes getCallbackUrlType() {
        return this.callbackUrlType;
    }

    @Generated
    public String getCallbackUrl() {
        return this.callbackUrl;
    }

    @Generated
    public Pac4jBaseClientProperties setClientName(String clientName) {
        this.clientName = clientName;
        return this;
    }

    @Generated
    public Pac4jBaseClientProperties setAutoRedirectType(DelegationAutoRedirectTypes autoRedirectType) {
        this.autoRedirectType = autoRedirectType;
        return this;
    }

    @Generated
    public Pac4jBaseClientProperties setPrincipalAttributeId(String principalAttributeId) {
        this.principalAttributeId = principalAttributeId;
        return this;
    }

    @Generated
    public Pac4jBaseClientProperties setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    @Generated
    public Pac4jBaseClientProperties setCssClass(String cssClass) {
        this.cssClass = cssClass;
        return this;
    }

    @Generated
    public Pac4jBaseClientProperties setDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    @Generated
    public Pac4jBaseClientProperties setCallbackUrlType(CallbackUrlTypes callbackUrlType) {
        this.callbackUrlType = callbackUrlType;
        return this;
    }

    @Generated
    public Pac4jBaseClientProperties setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
        return this;
    }

    public static enum CallbackUrlTypes {
        PATH_PARAMETER,
        QUERY_PARAMETER,
        NONE;

    }
}

