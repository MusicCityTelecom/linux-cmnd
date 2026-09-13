/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.saml.idp.metadata;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.DurationCapable;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-saml-idp")
@JsonFilter(value="CoreSamlMetadataProperties")
public class CoreSamlMetadataProperties
implements Serializable {
    private static final long serialVersionUID = -8116473583467202828L;
    private boolean failFast = true;
    private long cacheMaximumSize = 10000L;
    @DurationCapable
    private String cacheExpiration = "PT24H";
    private boolean requireValidMetadata = true;
    private boolean ssoServicePostBindingEnabled = true;
    private boolean ssoServicePostSimpleSignBindingEnabled = true;
    private boolean ssoServiceRedirectBindingEnabled = true;
    private boolean ssoServiceSoapBindingEnabled = true;
    private boolean sloServicePostBindingEnabled = true;
    private boolean sloServiceRedirectBindingEnabled = true;

    @Generated
    public boolean isFailFast() {
        return this.failFast;
    }

    @Generated
    public long getCacheMaximumSize() {
        return this.cacheMaximumSize;
    }

    @Generated
    public String getCacheExpiration() {
        return this.cacheExpiration;
    }

    @Generated
    public boolean isRequireValidMetadata() {
        return this.requireValidMetadata;
    }

    @Generated
    public boolean isSsoServicePostBindingEnabled() {
        return this.ssoServicePostBindingEnabled;
    }

    @Generated
    public boolean isSsoServicePostSimpleSignBindingEnabled() {
        return this.ssoServicePostSimpleSignBindingEnabled;
    }

    @Generated
    public boolean isSsoServiceRedirectBindingEnabled() {
        return this.ssoServiceRedirectBindingEnabled;
    }

    @Generated
    public boolean isSsoServiceSoapBindingEnabled() {
        return this.ssoServiceSoapBindingEnabled;
    }

    @Generated
    public boolean isSloServicePostBindingEnabled() {
        return this.sloServicePostBindingEnabled;
    }

    @Generated
    public boolean isSloServiceRedirectBindingEnabled() {
        return this.sloServiceRedirectBindingEnabled;
    }

    @Generated
    public CoreSamlMetadataProperties setFailFast(boolean failFast) {
        this.failFast = failFast;
        return this;
    }

    @Generated
    public CoreSamlMetadataProperties setCacheMaximumSize(long cacheMaximumSize) {
        this.cacheMaximumSize = cacheMaximumSize;
        return this;
    }

    @Generated
    public CoreSamlMetadataProperties setCacheExpiration(String cacheExpiration) {
        this.cacheExpiration = cacheExpiration;
        return this;
    }

    @Generated
    public CoreSamlMetadataProperties setRequireValidMetadata(boolean requireValidMetadata) {
        this.requireValidMetadata = requireValidMetadata;
        return this;
    }

    @Generated
    public CoreSamlMetadataProperties setSsoServicePostBindingEnabled(boolean ssoServicePostBindingEnabled) {
        this.ssoServicePostBindingEnabled = ssoServicePostBindingEnabled;
        return this;
    }

    @Generated
    public CoreSamlMetadataProperties setSsoServicePostSimpleSignBindingEnabled(boolean ssoServicePostSimpleSignBindingEnabled) {
        this.ssoServicePostSimpleSignBindingEnabled = ssoServicePostSimpleSignBindingEnabled;
        return this;
    }

    @Generated
    public CoreSamlMetadataProperties setSsoServiceRedirectBindingEnabled(boolean ssoServiceRedirectBindingEnabled) {
        this.ssoServiceRedirectBindingEnabled = ssoServiceRedirectBindingEnabled;
        return this;
    }

    @Generated
    public CoreSamlMetadataProperties setSsoServiceSoapBindingEnabled(boolean ssoServiceSoapBindingEnabled) {
        this.ssoServiceSoapBindingEnabled = ssoServiceSoapBindingEnabled;
        return this;
    }

    @Generated
    public CoreSamlMetadataProperties setSloServicePostBindingEnabled(boolean sloServicePostBindingEnabled) {
        this.sloServicePostBindingEnabled = sloServicePostBindingEnabled;
        return this;
    }

    @Generated
    public CoreSamlMetadataProperties setSloServiceRedirectBindingEnabled(boolean sloServiceRedirectBindingEnabled) {
        this.sloServiceRedirectBindingEnabled = sloServiceRedirectBindingEnabled;
        return this;
    }
}

