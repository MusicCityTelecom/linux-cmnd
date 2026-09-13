/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.azuread;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-azuread-authentication")
@JsonFilter(value="AzureActiveDirectoryAttributesProperties")
public class AzureActiveDirectoryAttributesProperties
implements Serializable {
    private static final long serialVersionUID = -12055975558426360L;
    private int order;
    private boolean caseInsensitive;
    private String id;
    private String tenant;
    private String resource;
    private String scope;
    private String grantType;
    private String attributes;
    private String apiBaseUrl;
    private String loginBaseUrl;
    private String domain;
    private String loggingLevel;
    @RequiredProperty
    private String clientId;
    @RequiredProperty
    private String clientSecret;

    @Generated
    public int getOrder() {
        return this.order;
    }

    @Generated
    public boolean isCaseInsensitive() {
        return this.caseInsensitive;
    }

    @Generated
    public String getId() {
        return this.id;
    }

    @Generated
    public String getTenant() {
        return this.tenant;
    }

    @Generated
    public String getResource() {
        return this.resource;
    }

    @Generated
    public String getScope() {
        return this.scope;
    }

    @Generated
    public String getGrantType() {
        return this.grantType;
    }

    @Generated
    public String getAttributes() {
        return this.attributes;
    }

    @Generated
    public String getApiBaseUrl() {
        return this.apiBaseUrl;
    }

    @Generated
    public String getLoginBaseUrl() {
        return this.loginBaseUrl;
    }

    @Generated
    public String getDomain() {
        return this.domain;
    }

    @Generated
    public String getLoggingLevel() {
        return this.loggingLevel;
    }

    @Generated
    public String getClientId() {
        return this.clientId;
    }

    @Generated
    public String getClientSecret() {
        return this.clientSecret;
    }

    @Generated
    public AzureActiveDirectoryAttributesProperties setOrder(int order) {
        this.order = order;
        return this;
    }

    @Generated
    public AzureActiveDirectoryAttributesProperties setCaseInsensitive(boolean caseInsensitive) {
        this.caseInsensitive = caseInsensitive;
        return this;
    }

    @Generated
    public AzureActiveDirectoryAttributesProperties setId(String id) {
        this.id = id;
        return this;
    }

    @Generated
    public AzureActiveDirectoryAttributesProperties setTenant(String tenant) {
        this.tenant = tenant;
        return this;
    }

    @Generated
    public AzureActiveDirectoryAttributesProperties setResource(String resource) {
        this.resource = resource;
        return this;
    }

    @Generated
    public AzureActiveDirectoryAttributesProperties setScope(String scope) {
        this.scope = scope;
        return this;
    }

    @Generated
    public AzureActiveDirectoryAttributesProperties setGrantType(String grantType) {
        this.grantType = grantType;
        return this;
    }

    @Generated
    public AzureActiveDirectoryAttributesProperties setAttributes(String attributes) {
        this.attributes = attributes;
        return this;
    }

    @Generated
    public AzureActiveDirectoryAttributesProperties setApiBaseUrl(String apiBaseUrl) {
        this.apiBaseUrl = apiBaseUrl;
        return this;
    }

    @Generated
    public AzureActiveDirectoryAttributesProperties setLoginBaseUrl(String loginBaseUrl) {
        this.loginBaseUrl = loginBaseUrl;
        return this;
    }

    @Generated
    public AzureActiveDirectoryAttributesProperties setDomain(String domain) {
        this.domain = domain;
        return this;
    }

    @Generated
    public AzureActiveDirectoryAttributesProperties setLoggingLevel(String loggingLevel) {
        this.loggingLevel = loggingLevel;
        return this;
    }

    @Generated
    public AzureActiveDirectoryAttributesProperties setClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    @Generated
    public AzureActiveDirectoryAttributesProperties setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
        return this;
    }
}

