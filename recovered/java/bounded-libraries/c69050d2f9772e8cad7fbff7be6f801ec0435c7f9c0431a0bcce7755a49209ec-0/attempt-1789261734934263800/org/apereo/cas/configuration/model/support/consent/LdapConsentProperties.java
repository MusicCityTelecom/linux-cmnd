/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.consent;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Generated;
import org.apereo.cas.configuration.model.support.ldap.AbstractLdapProperties;
import org.apereo.cas.configuration.model.support.ldap.AbstractLdapSearchProperties;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-consent-ldap")
@JsonFilter(value="LdapConsentProperties")
public class LdapConsentProperties
extends AbstractLdapSearchProperties {
    private static final long serialVersionUID = 1L;
    @RequiredProperty
    private AbstractLdapProperties.LdapType type;
    @RequiredProperty
    private String consentAttributeName = "casConsentDecision";

    @Generated
    public AbstractLdapProperties.LdapType getType() {
        return this.type;
    }

    @Generated
    public String getConsentAttributeName() {
        return this.consentAttributeName;
    }

    @Generated
    public LdapConsentProperties setType(AbstractLdapProperties.LdapType type) {
        this.type = type;
        return this;
    }

    @Generated
    public LdapConsentProperties setConsentAttributeName(String consentAttributeName) {
        this.consentAttributeName = consentAttributeName;
        return this;
    }
}

